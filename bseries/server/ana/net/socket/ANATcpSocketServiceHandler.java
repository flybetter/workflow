package com.calix.bseries.server.ana.net.socket;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.ana.process.ANACommandProcess;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.AbstractCMSDatabase;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.exception.EMSException;
import com.calix.ems.security.SecurityUtils;
import com.calix.system.server.dbmodel.BaseCMSNetwork;
import com.calix.system.server.dbmodel.CalixUserSession;

/**
 * Handles a server-side channel.
 */
@Sharable
public class ANATcpSocketServiceHandler extends
		SimpleChannelInboundHandler<String> {
	private static final Logger log = Logger
			.getLogger(ANATcpSocketServiceHandler.class);
	private static final String LOGIN_PATTERN_STR = "user=(.*) password=(.*)";
	private static final Pattern LOGIN_PATTERN = Pattern
			.compile(LOGIN_PATTERN_STR);
	private static final String PROXYPARAM_PATTERN_STR = "userName=(.*) userIp=(.*)";
	private static final Pattern PROXYPARAM_PATTERN = Pattern
			.compile(PROXYPARAM_PATTERN_STR);
	private static final String PARAM_PATTERN_STR = "Name=(.*)\\)";
	private static final Pattern PARAM_PATTERN = Pattern
			.compile(PARAM_PATTERN_STR);
	private static final String SYNC_DEV_COMMAND = "syncdevice";

	// Session Info
	private static Map<String, ANAProcessResult> session = new HashMap<String, ANAProcessResult>();
	private static Map<String, ANATcpSocketClient> clientSession = new HashMap<String, ANATcpSocketClient>();
	private String response = "";

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		String key = ctx.channel().remoteAddress().toString();
		// ANA client process will send . and '' which do not know the reason
		// so we drop those requests
		if (".".equals(request.trim()) || "".equals(request.trim())) {
			ctx.write(request + "\n");
			return;
		}

		log.info("[ANA Socket][key]" + key + "[request]\r\n"
				+ formatXml(request));
		String response = "";
		// sync device
		if (request.equals(SYNC_DEV_COMMAND)) {
			response = getB6DeviceNameList();
			ctx.write(response + "\n");
			return;
		}
		// add cache exception
		try {
			response = processForSocket(key, request);
			if (StringUtils.isEmpty(response)) {
				response = ANAConstants.AnaErrorCode
						.getErrorMessage(ANAConstants.AnaErrorCode.REQUEST_PATTERN_NOT_RIGHT
								.toString());
			}
		} catch (Exception e) {
			log.error("[ANA Socket][Exception]" + e.getMessage() + "\r\n"
					+ e.getCause() + "\r\n" + e.getStackTrace());
			response = ANAConstants.AnaErrorCode
					.getErrorMessage(ANAConstants.AnaErrorCode.UNKNOWN_ERROR
							.toString());
		} finally {
			log.info("[key]" + key + "[response]" + response);
			ctx.write(response + "\n");
		}
	}

	private static String formatXml(String str) {
		if (str == null || !str.startsWith("<?xml")) {
			return str;
		}
		Document document = null;
		try {
			document = DocumentHelper.parseText(str);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			StringWriter writer = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(writer, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return writer.toString();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Function:processForSocket<br/>
	 * 
	 * @author Tony Ben
	 * @param key
	 * @param request
	 * @return
	 * @since JDK 1.6
	 */
	private String processForSocket(String key, String request) {
		ANAProcessResult result = session.get(key);
		String userName = "";
		String userIp = "";
		// first check whether the client is auth successful, when true can do
		// others
		if (request.startsWith("from_proxy_to_CMS:")) {
			// have authenticated on proxy
			String proxyParams = request.substring(request.indexOf("[")+1, request.indexOf("]"));

			Matcher matcher = PROXYPARAM_PATTERN.matcher(proxyParams);
			if (matcher.find()) {
				userName = matcher.group(1).trim();
				userIp= matcher.group(2).trim();
			}
			// have authenticated on proxy
			request = request.substring(request.indexOf("<"));
			result = new ANAProcessResult(userName, key.split(":")[0]
					.substring(1), request);
			session.put(key, result);
		}else{
			if (result == null && !checkLogin(request, key)) {
				return response;
			}
		}
		if (!request.contains("<?xml")) {
			return "success.";
		}
		// get the request command type, there will be GetWorkflowOutput and
		// RunWorkflow, if not , will not handle
		String name = null;
		try {
			result.setIpaddress(userIp);
			Document doc = DocumentHelper.parseText(request);
			Element rootElt = doc.getRootElement();
			name = rootElt.attributeValue("name");
			doc = null;
			rootElt = null;
		} catch (DocumentException e) {
			log.error(e);
		}

		// Request
		if (name == null || "".equals(name.trim())) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.REQUEST_PATTERN_NOT_RIGHT);
			response = returnXml(result.getLogs());
			return response;
		}
		return processLocal(key, request, result, name);
	}

	private String getDeviceHostName(String request) {
		try {
			Document doc = DocumentHelper.parseText(request);
			Element root = doc.getRootElement();
			List<Element> list = root.elements("param");
			for (Element e : list) {
				String name = e.attributeValue("name").trim();
				// TemplateName
				if ("workflowAttributes".equals(name)) {
					List<Element> elist = e.elements();
					for (Element ee : elist) {
						if ("value".equals(ee.getName().toLowerCase())) {
							List<Element> vlist = ee.elements();
							for (Element eee : vlist) {
								String id = nameFind(eee.elementText("ID"));
								if (id.equalsIgnoreCase("device_host_name")) {
									return eee.elementText("Value");
								}
							}
						}
					}
				}
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private static String nameFind(String name) {
		if (CommonStringUtils.isEmpty(name)) {
			return name;
		}
		Matcher m = PARAM_PATTERN.matcher(name);
		if (m.find()) {
			return m.group(1);
		}
		return name;
	}

	private String processLocal(String key, String request,
			ANAProcessResult result, String name) {
		if ("RunWorkflow".equals(name.trim())) {
			String response = "";
			String deviceName = getDeviceHostName(request);
			if (ANACommandProcess.getInstance().getDeviceInfo(deviceName) == null) {
				result.setErrorInfo(ANAConstants.AnaErrorCode.NE_NOT_EXISTING);
				response = returnXml(result.getLogs());
				return response;
			}
			//2017/9/1 get password form CalixB6device
			CalixB6Device device=ANACommandProcess.getInstance().getDeviceInfo(deviceName);
			result.setDevice(device);
			ANACommandProcess.getInstance().executeCommand(request, result);
            //2017/8/7 to adapt ASAP,CMS-15439 and CMS-15654
			if(!result.getResult()&&!result.getIsCommon()){
				if(request.contains("FPCMaster.template")){
					result.addLogs("\r\nUser Remark: FAILURE - Residential HSI service failed");
				}else if(request.contains("FPC_MASTER_DSL_BONDED.template")){
					result.addLogs("\r\nUser Remark: FAILURE - Bonded DSL service failed");
				}
			}
			response = returnXml(result.getLogs());
			ANAProcessResult result1 = new ANAProcessResult(result.getUsername(),
					result.getIpaddress(), result.getAuthMsg());
			session.put(key, result1);
			return response;
		}
		return null;
	}
	
	private String returnLocalResponse(String key, ANAProcessResult result) {
		String response = returnXml(result.getLogs());
		ANAProcessResult result1 = new ANAProcessResult(result.getUsername(),
				result.getIpaddress(), result.getAuthMsg());
		session.put(key, result1);
		result = null;
		return response;
	}
	
	private String returnXml(String response) {
		if (response == null) {
			response = "";
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("IMO");
		root.addAttribute("type", "IMO");
		root.addAttribute("instance_id", "0");

		Element book = root.addElement("Output");
		book.addAttribute("type", "String");
		book.addText(response);
//		OutputFormat format = OutputFormat.createCompactFormat();
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer);
		try {
			output.write(doc);
			writer.close();
			output.close();
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private boolean checkLoginFromCMS(String request, String key) {
		if (request != null && !request.trim().isEmpty()) {
			Matcher matcher = LOGIN_PATTERN.matcher(request);
			if (matcher.find()) {
				String userName = matcher.group(1).trim();
				String password = matcher.group(2).trim();
				log.info("[ANA][checkLogin] username=" + userName
						+ " password:" + password);
				CalixUserSession loginObj = new CalixUserSession();
				loginObj.setUserId(userName);
				byte[] pwd = null;
				java.security.MessageDigest md;
				boolean success = false;
				try {
					md = java.security.MessageDigest.getInstance("MD5");
					md.update(password.getBytes());
					pwd = md.digest();
					loginObj.setUserPidDigest(pwd);
					loginObj.setClientHost(key.split("/")[0]);
					loginObj.setPassword(password);
					success = SecurityUtils.getInstance().login(loginObj, null);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (EMSException e) {
					e.printStackTrace();
				}
				if (success) {
					response = "success.\r\n";
					session.put(
							key,
							new ANAProcessResult(userName, key.split(":")[0]
									.substring(1), request));
					return true;
				}
				return success;
			}
		}
		session.remove(key);
		response = "username or password is not correct or the user have no privileage.\r\n";
		return false;
	}

	/**
	 * Function:checkLogin<br/>
	 * 
	 * @author Tony Ben
	 * @param request
	 * @param key
	 * @return
	 * @since JDK 1.6
	 */
	private boolean checkLogin(String request, String key) {
		response = "";
		if (session.get(key) != null) {
			return true;
		}
		return checkLoginFromCMS(request, key);
	}

	/**
	 * Function:getB6DeviceNameList<br/>
	 * 
	 * @author Tony Ben
	 * @return
	 * @since JDK 1.6
	 */
	public static String getB6DeviceNameList() {
		String deviceList = "";
		ICMSDatabase database = null;
		DbTransaction txn = null;
		try {
			database = DatabaseManager.getCMSDatabase();
			txn = database.getReadTransaction();
			txn.begin();
			com.calix.ems.query.ICMSQuery query = AbstractCMSDatabase
					.getDBQuery(null, CalixB6Device.TYPE_NAME, "");
			Collection results = query.exec(null, txn);
			Iterator iter = results.iterator();
			StringBuffer sb = new StringBuffer();
			while (iter.hasNext()) {
				BaseCMSNetwork network = (BaseCMSNetwork) iter.next();
				sb.append(",").append(network.getDbIdentity());
			}
			if (sb.length() < 1) {
				deviceList = "";
			} else {
				deviceList = sb.toString().substring(1);
			}
			txn.commit();
		} catch (Exception ex) {
			log.warn("get all b6 device error: ", ex);
		} finally {
			if (txn != null && txn.isActive())
				txn.abort();
		}
		return deviceList;
	}

	/**
	 * Function:nameFind<br/>
	 * 
	 * @author Tony Ben
	 * @param name
	 * @return
	 * @since JDK 1.6
	 */

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		sessionClose(ctx);
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		sessionClose(ctx);
		super.channelInactive(ctx);
	}

	/**
	 * Function:sessionClose<br/>
	 * 
	 * @author Tony Ben
	 * @param ctx
	 * @since JDK 1.6
	 */
	private void sessionClose(ChannelHandlerContext ctx) {
		String key = ctx.channel().remoteAddress().toString();
		session.remove(key);
		ANATcpSocketClient client = clientSession.get(key);
		if (client != null) {
			client.disconnect();
			client = null;
		}
		clientSession.remove(key);
	}
}
