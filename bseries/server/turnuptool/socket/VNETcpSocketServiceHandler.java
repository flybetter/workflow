package com.calix.bseries.server.turnuptool.socket;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.net.socket.ANATcpSocketServiceHandler;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.turnuptool.entity.CreateResultBean;
import com.calix.bseries.server.turnuptool.service.VNEProcessService;
import com.calix.ems.exception.EMSException;
import com.calix.ems.security.SecurityUtils;
import com.calix.system.server.dbmodel.CalixUserSession;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
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

/**
 * Handles a server-side channel.
 */
@Sharable
public class VNETcpSocketServiceHandler extends
		SimpleChannelInboundHandler<String> {
	private static final Logger log = Logger
			.getLogger(VNETcpSocketServiceHandler.class);
	private static final String LOGIN_PATTERN_STR = "user=(.*) password=(.*)";
	private static final Pattern LOGIN_PATTERN = Pattern
			.compile(LOGIN_PATTERN_STR);

	private static Map<String, ANAProcessResult> session = new HashMap<String, ANAProcessResult>();
	private String response = "";

	public void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		String key = ctx.channel().remoteAddress().toString();
		log.info("[key]" + key + "[request]" + request);
		if ((".".equals(request.trim())) || ("".equals(request.trim()))) {
			log.info("[key]" + key + "[response]" + request + "\n");
			ctx.write(request + "\n");
			return;
		}
		String response = "";
		response = processForSocket(key, request);
		if (StringUtils.isEmpty(response)) {
			response = ANAConstants.AnaErrorCode.getErrorMessage(
					ANAConstants.AnaErrorCode.REQUEST_PATTERN_NOT_RIGHT
							.toString(), new Object[0]);
		}

		log.info("[key]" + key + "[response]" + response);
		ctx.write(response + "\n");
	}

	private String processForSocket(String key, String request) {
		ANAProcessResult result = (ANAProcessResult) session.get(key);
		String userName ="";
		if (request.startsWith("from_proxy_to_CMS:")) {
			// have authenticated on proxy
			userName = request.substring(request.indexOf("[")+1, request.indexOf("]"));
			request = request.substring(request.indexOf("]")+1);
			session.put(
					key,
					new ANAProcessResult(userName, key.split(":")[0]
							.substring(1), request));
		} else {
			if ((result == null) && (!checkLoginFromCMS(request, key))) {
				return this.response;
			}
		}
		if (!request.contains("</param>")) {
			return "success.";
		}
		String name = null;
		if (request.endsWith("."))
			request = request.substring(0, request.lastIndexOf("."));
		try {
			Document doc = DocumentHelper.parseText(request);
			Element rootElt = doc.getRootElement();
			name = rootElt.attributeValue("name");
			doc = null;
			rootElt = null;
		} catch (DocumentException e) {
			log.info(e);
			return "<ResultCode>FAILURE</ResultCode><Message>request is invalid,"+e.getMessage()+"</Message>";
		}

		if ((name == null) || ("".equals(name.trim()))) {
			return ANAConstants.AnaErrorCode.getErrorMessage(
					ANAConstants.AnaErrorCode.REQUEST_PATTERN_NOT_RIGHT
							.toString(), new Object[0]);
		}
		String responseStr = "";
		StringBuffer responseXML = new StringBuffer("<?xml version=\"1.0\" ?>\r");
		if ("Create".equals(name.trim())) {
			CreateResultBean createResultBean = VNEProcessService.getInstance().createVNE(request);
			 
			//create
			//responseXML.append("<"+createResultBean.getDeviceType()+">");
			responseXML.append("<ConfCMS>");
			responseXML.append("<ResultCode>"+createResultBean.getResult()+"</ResultCode>");
			responseXML.append("<Message>"+createResultBean.getMessage()+"</Message><Data></Data>");
			responseXML.append("<Input><Username>"+userName+"</Username>");
			responseXML.append("<Hostname>"+createResultBean.getHostName()+"</Hostname>");
			responseXML.append("<HostIP>"+createResultBean.getHostIP()+"</HostIP></Input>");
			//responseXML.append("</"+createResultBean.getDeviceType()+">");
			responseXML.append("<ConfCMS>");
		} else if ("Delete".equals(name.trim())) {
			responseStr = VNEProcessService.getInstance().deleteVNE(request);

			if ("success".equals(responseStr)) {
				responseXML.append("<ResultCode>PASS</ResultCode><Message>Command completed successfully</Message>");
			} else {
				responseXML.append("<ResultCode>FAILURE</ResultCode><Message>"+responseStr+"</Message>");
			}
		}
		return formatXml(responseXML.toString());
	}

	public static String formatXml(String str) {
		if (str == null || !str.startsWith("<?xml")) {
			return str;
		}
		Document document = null;
		try {
			document = DocumentHelper.parseText(str);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			format.setSuppressDeclaration(true); 
	        format.setIndent(true);
			format.setNewlines(true);
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
	
	private boolean checkLoginFromCMS(String request, String key) {
		if ((request != null) && (!request.trim().isEmpty())) {
			Matcher matcher = LOGIN_PATTERN.matcher(request);
			if (matcher.find()) {
				String userName = matcher.group(1).trim();
				String password = matcher.group(2).trim();
				log.info("[ANA][checkLogin] username=" + userName
						+ " password:" + password);

				CalixUserSession loginObj = new CalixUserSession();
				loginObj.setUserId(userName);
				byte[] pwd = null;

				boolean success = false;
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
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
					this.response = "success.\r\n";
					session.put(
							key,
							new ANAProcessResult(userName, key.split(":")[0]
									.substring(1), null));

					return true;
				}
				return success;
			}
		}
		session.remove(key);
		this.response = "username or password is not correct or the user have no privileage.\r\n";
		return false;
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		sessionClose(ctx);
		ctx.close();
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		sessionClose(ctx);
		super.channelInactive(ctx);
	}

	private void sessionClose(ChannelHandlerContext ctx) {
		String key = ctx.channel().remoteAddress().toString();
		session.remove(key);
	}
}