package com.calix.bseries.server.ana.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.common.CommonDateTimeUtils;
import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.ana.common.error.AnaException;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.net.ssh.B6SSHConnectImpl;
import com.calix.bseries.server.ana.process.command.CommonTask;
import com.calix.bseries.server.ana.process.command.TemplateCommandDetails;
import com.calix.bseries.server.dbmodel.B6CommandAction;
import com.calix.bseries.server.dbmodel.B6CommandResult;
import com.calix.bseries.server.dbmodel.B6CommandResultData;
import com.calix.bseries.server.dbmodel.B6Template;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.ems.server.connection.NetworkState;
import com.calix.system.server.dbmodel.EMSAid;

public class ANACommandProcess {
	// log
	private static final Logger log = Logger.getLogger(ANACommandProcess.class);
	private static final String PARAM_PATTERN_STR = "Name=(.*)\\)";
	private static final Pattern PARAM_PATTERN = Pattern
			.compile(PARAM_PATTERN_STR);
	private static ANACommandProcess instance = new ANACommandProcess();
	private static String COMMAND_PATH = System.getenv().get("CMS_HOME")
			+ "/lib/config/ems/B6/B6CLICommand.xml";
	private static Map<String, String> OperateMap;
	private static Map<String, Integer> DeviceSessionMap = new HashMap<String, Integer>();
	static {
		OperateMap = new HashMap<String, String>();
		OperateMap.put("add", "");
		OperateMap.put("mod", "");
		OperateMap.put("del", "");
	}

	public static ANACommandProcess getInstance() {
		return instance;
	}

	/**
	 * Function:doCreatePOToDB<br/>
	 * Remark:Insert to Object to DB<br/>
	 * 
	 * @author Tony Ben
	 * @param templatePO
	 * @since JDK 1.6
	 */
	public void doCreatePOToDB(B6Template templatePO) {
		templatePO.setdevice_host_name(templatePO.getdevice_host_name()
				.startsWith(ANAConstants.CALIX_DEVICE_PREF) ? templatePO
				.getdevice_host_name() : ANAConstants.CALIX_DEVICE_PREF
				+ templatePO.m_device_host_name);
		templatePO.setResult(B6Template.STATUS_RUNNING);
		C7Database db = C7Database.getInstance();
		try {
			db.beginTransaction();
			db.createCMSObject(templatePO);
			db.commitTransaction();
		} catch (Exception e) {
			log.error("ANACommandProcess: create B6 template failure", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
	}

	/**
	 * Function:updateDB4CLI <br/>
	 * Conditions:TODO<br/>
	 * WorkFlow:TODO<br/>
	 * UserGuide:TODO<br/>
	 * Remark:TODO<br/>
	 * 
	 * @author Zheng Li
	 * @param B6CommandResultData
	 * @since JDK 1.6
	 */
	public void updateDB4CLI(B6CommandResultData data) {
		log.info("[show dsl port status ]updateDB4CLI :" + data.m_NetworkName
				+ " results:" + data.m_Status);
		C7Database db = C7Database.getInstance();
		Session session = C7Database.getInstance().getSession();
		try {
			db.beginTransaction();
			session.update(data);
			db.commitTransaction();
		} catch (Exception e) {
			log.error("ANACommandProcess: update CLICmd failure", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
	}

	/**
	 * Function:doUpdatePOToDB<br/>
	 * Conditions:TODO<br/>
	 * WorkFlow:TODO<br/>
	 * UserGuide:TODO<br/>
	 * Remark:TODO<br/>
	 * 
	 * @author Tony Ben
	 * @param templatePO
	 * @since JDK 1.6
	 */
	public void doUpdatePOToDB(B6Template templatePO) {
		log.info("[ANA Template ]doUpdatePOToDB :"
				+ templatePO.getconvertName() + " results:"
				+ templatePO.getResult());
		ICMSDatabase database = null;
		DbTransaction txn = null;
		try {
			database = DatabaseManager.getCMSDatabase();
			txn = database.getTransaction("ems", DbTransaction.READ);
			txn.begin();
			templatePO.doUpdate(txn);
			txn.commit();
			txn = null;
		} catch (Exception ex) {
			log.error("ANACommandProcess: update B6tempalte failure", ex);
		} finally {
			if (txn != null && txn.isActive()) {
				txn.abort();
			}
		}
	}

	private String nameFind(String name) {
		if (CommonStringUtils.isEmpty(name)) {
			return name;
		}
		Matcher m = PARAM_PATTERN.matcher(name);
		if (m.find()) {
			return m.group(1);
		}
		return name;
	}

	private B6Template initTemplateViaXMLRequest(String request,
			ANAProcessResult result) {
		SAXReader reader = new SAXReader();
		Document document;
		B6Template po = null;
		try {
			document = reader.read(new StringReader(request));
			Element root = document.getRootElement();// command
			List<Element> elements = root.elements("param");// param
			for (Element e : elements) {
				String name = e.attributeValue("name").trim();
				// TemplateName
				if ("templateOid".equals(name)) {
					name = e.elementText("value");
					String templateName = nameFind(name);
					String ClassName = CommandFactory
							.getClassNameViaTemplateName(templateName);
					try {
						po = (B6Template) ClassReflectionUtils.getClassObject(
								ANAConstants.TEMPLATE_PACKAGE_NAME, ClassName);
					} catch (ClassNotFoundException e1) {
						log.error(e1);
						return null;
					} catch (InstantiationException e1) {
						log.error(e1);
						return null;
					} catch (IllegalAccessException e1) {
						log.error(e1);
						return null;
					}
				}
				// Attributes
				else if ("workflowAttributes".equals(name)) {
					List<Element> elist = e.elements();
					for (Element ee : elist) {
						if ("value".equals(ee.getName().toLowerCase())) {
							List<Element> vlist = ee.elements();
							for (Element eee : vlist) {
								if (po == null) {
									result.setErrorInfo(ANAConstants.AnaErrorCode.TEMPATE_NOT_FOUND
											.toString());
									return null;
								}
								String id = nameFind(eee.elementText("ID"));
								String value = eee.elementText("Value");
								try {
									ClassReflectionUtils.setFieldValue(po, "m_"
											+ id, value);
								} catch (NumberFormatException e1) {
									result.setErrorInfo(
											ANAConstants.AnaErrorCode.PARAMETER_SHOULD_BENUMBER
													.toString(), id);
									return null;
								} catch (IllegalArgumentException e1) {
									result.setErrorInfo(
											ANAConstants.AnaErrorCode.PARAMETER_VALUE_INVALID
													.toString(), id);
									return null;
								} catch (IllegalAccessException e1) {
									result.setErrorInfo(
											ANAConstants.AnaErrorCode.PARAMETER_VALUE_INVALID
													.toString(), id);
									return null;
								} catch (Exception e1) {
									result.setErrorInfo(
											ANAConstants.AnaErrorCode.PARAMETER_VALUE_INVALID
													.toString(), id);
									return null;
								}

							}
							vlist = null;
						} else {
							// IMObject_Array
							// TODO
						}
					}
					elist = null;
				}
			}
			elements = null;
			root = null;
			reader = null;
			document = null;
		} catch (DocumentException e) {
			log.error(e);
			return null;
		}
		return po;
	}

	/**
	 * Function:execute 8th CLI cmd<br/>
	 * 
	 * @author Zheng Li
	 * @param request
	 * @return B6CommandResult
	 * @since JDK 1.6
	 */
	public B6CommandResultData executeCommand(B6CommandResultData data) {
		// check device exist
		CalixB6Device device = getDeviceInfo(data.getNetworkName());
		if (device == null || device.getIPAddress1().trim().equals("")) {
			log.info("device:" + data.getNetworkName() + "/"
					+ data.getAddressId() + " didn't exist in CMS.");
			data.setRequest(data.getCommandType());
			data.setResponse("query failed,device:" + data.getNetworkName()
					+ "/" + data.getAddressId() + " didn't exist in CMS.");
			return data;
		}
		// connect to device
		ANAProcessResult result = new ANAProcessResult();
		AtomicBoolean timeout = new AtomicBoolean(false);
		B6SSHConnectImpl connect = new B6SSHConnectImpl(result);
		try {
			Boolean connectResult = connect.connectWithPassword(data
					.getNetworkIP(),device.getEnablePassword(),device.getNetworkLoginPassword());
			//String command = "sh dsl service interface atm ";B6-216 don't support this command
			String command = "show interfaces dsl ";
			if (!connectResult) {
				data.setResponse("connect failed");
				data.setStatus("fail");
				data.setEndTime(CommonDateTimeUtils.getUSDateTime());
				return data;
			}
			String device_name = null;
			Date d = new Date();
			StringBuffer response = new StringBuffer();
			log.info("Inside DSL status");
			response.append("Inside DSL status\n");
			int count_N_A = 0; // Number of non assigned(N/A) ports
			int count_avail = 0; // Number of available ports
			int count_assign = 0; // Number of assigned/used ports
			int count_total = 0; // Total number of ports
			int num_ports_int = 0;
			String temp = "";
			String num_ports_str = null;
			num_ports_str = connect.excuteCLICommand(command + "?");
			if (!num_ports_str.contains(">")) {
				data.setStatus("fail");
				data.setResponse(num_ports_str);
				data.setEndTime(CommonDateTimeUtils.getUSDateTime());
				return data;
			}
			String [] semicolons=num_ports_str.split("-");
			if(semicolons.length>1){
				String num_String=semicolons[1];
				String []parenthesis=num_String.split(">");
				try{
					num_ports_int=Integer.parseInt(parenthesis[0]);
				}catch(Exception e){
					log.error("Interger.parseInt:"+parenthesis[0],e);
				}
				count_total = num_ports_int;
			}
			
			
//			StringTokenizer stnz = new StringTokenizer(num_ports_str, "-");

//			for (int i = 0; stnz.hasMoreTokens(); i++) {
//				temp = stnz.nextToken().trim();
//			}
//			// check if temp is empty
//			if (temp != null) {
//				StringTokenizer num_ports_temp = new StringTokenizer(temp, ">");
//				num_ports_int = Integer.parseInt(num_ports_temp.nextToken()
//						.trim());
//				count_total = num_ports_int;
//			}
			// Calculate number of used and unused ports using
			// "sh dsl service interface atm <port_number>" CLI
			for (int i = 1; i <= count_total; i++) {
				String output = null;
				output = connect.excuteCommand(command + i);
				response.append(output + "\n");
				StringTokenizer s = new StringTokenizer(output, " ");
				count_N_A = 0;

				for (int j = 0; s.hasMoreTokens(); j++) {
					String var = null;
					var = s.nextToken().trim();
					// telnetInterface.println("var="+var);
					if (var.equals("N/A")) {
						count_N_A++;
						// telnetInterface.println("inside if----count_N_A="+count_N_A);
					}
				}
				if (count_N_A == 5) // if all fields are N/A, the port is
									// notused.So increment count_avail value
				{
					count_avail++;
					// telnetInterface.println("inside if----avail count="+count_avail);
				} else // else increment count_assign value
				{
					count_assign++;
					// telnetInterface.println("inside if----init="+count_init);
				}
			}
			// Get the device host name using "sh hosts" CLI
			String outMsg = "";
			outMsg = connect.excuteCommand("sh hosts");
			StringTokenizer stn = new StringTokenizer(outMsg, "\n");
			// Declare a string array "params" and initialise its elements to ""
			String[] params = new String[20];
			for (int k = 0; stn.hasMoreTokens(); k++)
				params[k] = stn.nextToken().trim();
			if (params[2] != null) {
				StringTokenizer stn2 = new StringTokenizer(params[2], " ");
				device_name = stn2.nextToken().trim();
			}
			response.append("TARGET_IDENTIFIER TOTAL_PORTS ASSIGNED_PORTS AVAILABLE_PORTS DATE TIME\n");
			response.append(device_name + " " + count_total + "	"
					+ count_assign + "	" + count_avail + "	"
					+ (d.getMonth() + 1) + "/" + d.getDate() + "/"
					+ (d.getYear() + 1900) + " " + d.getHours() + ":"
					+ d.getMinutes() + ":" + d.getSeconds());
			data.setResponse(response.toString());
			data.setStatus("success");
			data.setEndTime(CommonDateTimeUtils.getUSDateTime());
			return data;
		} catch (AnaException e) {
			log.error(
					"device:" + data.getNetworkName() + "/"
							+ data.getAddressId() + " show dsl status failed",
					e);
			data.setStatus("fail");
			data.setResponse("fail");
			data.setEndTime(CommonDateTimeUtils.getUSDateTime());
			return data;
		} finally {
			updateDB4CLI(data);
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 * Function:execute CLI cmd<br/>
	 * 
	 * @author Zheng Li
	 * @param request
	 * @return B6CommandResult
	 * @since JDK 1.6
	 */
	public B6CommandResult executeCommand(B6CommandAction request) {
		B6CommandResult res = new B6CommandResult();
		// check device exist
		CalixB6Device device = getDeviceInfo(request.getNetworkName());
		if (device == null || device.getIPAddress1().trim().equals("")) {
			log.info("device:" + request.getNetworkName() + "/"
					+ request.getAddressId() + " didn't exist in CMS.");
			res.setRequest(request.getCommandType());
			res.setResponse("query failed,device:" + request.getNetworkName()
					+ "/" + request.getAddressId() + " didn't exist in CMS.");
			return res;
		}
		// connect to device
		ANAProcessResult result = new ANAProcessResult();
		B6SSHConnectImpl connect = new B6SSHConnectImpl(result);
		try {
			log.info("Begin to login B6 " + request.getNetworkIP());
			Boolean connectResult = connect.connectWithPassword(request.getNetworkIP(),device.getEnablePassword(),device.getNetworkLoginPassword());
			log.info("Login B6 Result : " + connectResult);
			if (!connectResult) {
				res.setRequest(request.getCommandType());
				res.setResponse("connect failed,please check the cli and enable password of this device");
				return res;
			}
			String command = getXMLCMD(request.getCommandType());
			if (StringUtils.isEmpty(command)) {
				command = request.getCommandType();
			}
			if (!StringUtils.isEmpty(request.getPort())) {
				command = command + request.getPort();
				if(command.contains("pppoe")){
					String vlan = request.getVlan();
					if(vlan != null){
						vlan = vlan.substring(13,14);//dispaly value "Dynamic(VLAN 1)"
					}
					command = command + " "+vlan;
				}
			}

			String resStr = null;
			resStr = connect.excuteCLICommand(command);
			if ("show dsl bonding-group status".equals(command)) {
				resStr = "! Show Bonding Groups\r\n" + resStr;
			}
			res.setRequest(command);
			res.setResponse(resStr);
			return res;
		} catch (Exception e1) {
			log.error("device" + request.getNetworkName()
					+ " can't be connect.", e1);
			connect.disconnect();
			res.setRequest(request.getCommandType());
			res.setResponse("query failed,device" + request.getNetworkName()
					+ " can't be connect.");
			return res;
		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	public void executeCommand(String request, ANAProcessResult result) {
		result.setResult(true);
		B6Template requestPO = initTemplateViaXMLRequest(request, result);
		if (!result.getResult()) {
			return;
		}
		if (requestPO == null) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.TEMPATE_NOT_FOUND
					.toString());
			return;
		}
		// Set UserName
		if (result.getUsername() != null
				&& !result.getUsername().contains("USR-")) {
			requestPO.m_CMSUserName = "USR-" + result.getUsername();
		} else {
			requestPO.m_CMSUserName = result.getUsername();
		}
		// check device exist
		CalixB6Device device = getDeviceInfo(requestPO.getdevice_host_name());
		// device is not exist
		if (device == null) {
			log.info("device:" + requestPO.getdevice_host_name()
					+ " didn't exist in CMS.");
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.NE_NOT_EXISTING.getErrorCode(),
					requestPO.m_device_host_name);
			return;
		}
		requestPO.setIPAddress1(device.getIPAddress1());
		requestPO.m_UserIp = result.getIpaddress();
		requestPO.m_TemplateSource = ANAConstants.ANA_SOCKET_SOURCE;
		requestPO.setconvertName(UUID.randomUUID().toString());
		requestPO.m_StartTime = CommonDateTimeUtils.getUSDateTime();
		requestPO.m_StartTimeLong = System.currentTimeMillis();
		doCreatePOToDB(requestPO);
		executeCommand(requestPO, result);
		log.info("[ANA Template ]executeCommand :\r\n" + result.getLogs());
	}

	public void executeCommand(B6Template templatePO) {
		log.info("[ANA Template ]executeCommand :"
				+ templatePO.getconvertName());
		ANAProcessResult result = new ANAProcessResult();
		CalixB6Device device=getDeviceInfo(templatePO.getdevice_host_name());
		result.setDevice(device);
		executeCommand(templatePO, result);
		log.info("[ANA Template ]executeCommand :\r\n" + result.getLogs());
	}

	public CalixB6Device getDeviceInfo(String deviceName) {
		if (deviceName == null || StringUtils.isEmpty(deviceName)) {
			return null;
		}
		// handle with NTWK
		if (!deviceName.startsWith(ANAConstants.CALIX_DEVICE_PREF)) {
			deviceName = ANAConstants.CALIX_DEVICE_PREF + deviceName;
		}
		CalixB6Device device = null;
		try {
			device = (CalixB6Device) CMSCacheManager.getCacheManager()
					.getEMSObject(new CalixB6Device().getHierarchyIntegers(),
							new EMSAid(deviceName));
		} catch (Exception e) {
			log.error("fail to read days based on B6 setting", e);
		}
		return device;
	}

	private boolean preCheck(B6Template templatePO, ANAProcessResult result,
			TemplateCommandDetails template) {
		// check service type
		if (!"HSI".equals(templatePO.getservice_type())) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_SERVICE_TYPE
					.getErrorCode());
			return false;
		}
		// check operation
		if (templatePO.getoperation() == null
				|| OperateMap.get(templatePO.getoperation().trim()
						.toLowerCase()) == null) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_OPERATION
					.getErrorCode());
			return false;
		}

		// Template Not Exist
		if (template == null || template.getTaskMap().isEmpty()) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.TEMPATE_NOT_FOUND
					.getErrorCode());
			return false;
		}

		// check device exist
		CalixB6Device device = getDeviceInfo(templatePO.getdevice_host_name());
		if (device == null || device.getIPAddress1().trim().equals("")) {
			log.info("device:" + templatePO.getdevice_host_name() + "/"
					+ templatePO.getIPAddress1() + " didn't exist in CMS.");
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.NE_NOT_EXISTING.getErrorCode(),
					templatePO.m_device_host_name);
			return false;
		}
		templatePO.setIPAddress1(device.getIPAddress1());
		// check module
		if (!template.checkModule(device.getModel())) {
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.NE_NOT_SUPPORT_TEMPLATE
							.getErrorCode(), templatePO.getdevice_host_name());
			return false;
		}
		templatePO.addParam("model", device.getModel());
		// check device is online
		if (device.getConnectionState() != NetworkState.STATE_CONNECTED) {
			log.error("[ANA Template] device "
					+ templatePO.getdevice_host_name()
					+ " is not connected ,status:"
					+ device.getConnectionState());
			result.setErrorInfo(ANAConstants.AnaErrorCode.CONNECTION_ERROR
					.getErrorCode());
			return false;
		}
		return true;
	}

	private void executeCommandDetail(B6Template templatePO,
			ANAProcessResult result) {
		log.info("[ANA Template ]executeCommand :"
				+ templatePO.getconvertName());
		TemplateCommandDetails template = CommandFactory.getCommandList(
				templatePO.getTemplateName(), templatePO.getoperation());
		if (!preCheck(templatePO, result, template)) {
			result.setIsCommon(true);
			setResultToTemplatePO(templatePO, false, result.getLogs());
			return;
		}
		if (template == null) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.TEMPATE_NOT_FOUND
					.getErrorCode());
			setResultToTemplatePO(templatePO, false, result.getLogs());
			return;
		}
		Map<String, CommonTask> map = template.getTaskMap();
		result.setResult(ANAProcessResult.SUCCESS);
		// result.addLogHead(templatePO.m_service, templatePO.getServiceDesc(),
		// templatePO.m_service_type, templatePO.getoperation(),
		// templatePO.getdevice_host_name());
		// excute command chain
		log.info("[ANA Command ]Request From "
				+ templatePO.getdevice_host_name() + "/"
				+ templatePO.getIPAddress1() + " serviceType:"
				+ templatePO.getservice_type() + " service:"
				+ templatePO.getservice() + " Operation:"
				+ templatePO.getoperation());
		// connect to device
		IB6Connet connect = new B6SSHConnectImpl(result);
		try {
			//result.addDeviceName(templatePO.getdevice_host_name());
			
			//connect.connect(templatePO.getIPAddress1());
			connect.connectWithPassword(templatePO.getIPAddress1(),result.getDevice().getEnablePassword(),result.getDevice().getNetworkLoginPassword());
			CommandFactory.executeCommand(map, templatePO, result, connect);
		} catch (Exception e) {
			log.error(e);
			result.setErrorInfo(ANAConstants.AnaErrorCode.CONNECTION_ERROR
					.getErrorCode(), templatePO.m_device_host_name);
			result.setIsCommon(true);
			connect.disconnect();
		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		if (result.getResult()) {
			setResultToTemplatePO(templatePO, true, result.getLogs());
		} else {
			setResultToTemplatePO(templatePO, false, result.getLogs());
		}
	}

	public void executeCommand(B6Template templatePO, ANAProcessResult result) {
		// check session
		if (!StringUtils.isEmpty(templatePO.getdevice_host_name())
				&& DeviceSessionMap.get(templatePO.getdevice_host_name()) != null) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.MAX_USER_SESSIONS_EXCEEDED
					.getErrorCode());
			setResultToTemplatePO(templatePO, false, result.getLogs());
			return;
		}
		try {
			addSession(templatePO.getdevice_host_name());
			executeCommandDetail(templatePO, result);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			removeSession(templatePO.getdevice_host_name());
			doUpdatePOToDB(templatePO);
		}
	}

	private synchronized void removeSession(String deviceName) {
		if (deviceName == null || deviceName.isEmpty()) {
			return;
		}
		DeviceSessionMap.remove(deviceName);
	}

	private synchronized void addSession(String deviceName) {
		if (deviceName == null || deviceName.isEmpty()) {
			return;
		}
		DeviceSessionMap.put(deviceName, 1);
	}

	/**
	 * Function:setResultToTemplatePO<br/>
	 * 
	 * @author Tony Ben
	 * @param templatePO
	 * @param result
	 * @param details
	 * @since JDK 1.6
	 */
	private void setResultToTemplatePO(B6Template templatePO, boolean result,
			String details) {
		// Set result
		templatePO.m_Result = result ? "SUCCESS" : "FAILURE";
		// Set endtime
		templatePO.m_EndTime = CommonDateTimeUtils.getUSDateTime();
		templatePO.m_EndTimeLong = System.currentTimeMillis();
		// set details
		templatePO.m_ResponseDetail = details;
		doUpdatePOToDB(templatePO);
	}

	private String getXMLCMD(String commandKey) {
		try {
			StringBuffer requestXML = new StringBuffer("");
			File file = new File(COMMAND_PATH);
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String line;
			while ((line = buffReader.readLine()) != null) {
				requestXML.append(line);
			}
			buffReader.close();
			SAXReader reader = new SAXReader();
			Document document;
			document = reader.read(new StringReader(requestXML.toString()));
			Element root = document.getRootElement();// command
			List<Element> elements = root.elements("command");// param
			for (Element e : elements) {
				if (commandKey.equals(e.attributeValue("key"))) {
					return (e.attributeValue("value"));
				}
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
	// just for B6216
//	if(result.getDevice().getModel().equals("B6-216")){
//	 Object	obj = ClassReflectionUtils.getFieldValue(templatePO, "m_tpstc_mode");
//	 String tpstc_mode=String.valueOf(obj).trim();
	 //m_match_vlan	
//	 Object	obj = ClassReflectionUtils.getFieldValue(templatePO, "m_match_vlan");
//	 String match_vlan=String.valueOf(obj).trim();
//	 	if(match_vlan.equals("null")|| StringUtils.isEmpty(match_vlan)){
//	 		result.setErrorInfo(ANAConstants.AnaErrorCode.MISMATCHFORB6216
//					.getErrorCode());
//			setResultToTemplatePO(templatePO, false, result.getLogs());
//			return;
//	 	}
	 //m_tpstc_mode
//	 Object	obj2 = ClassReflectionUtils.getFieldValue(templatePO, "m_tpstc_mode");
//	 String tpstc_mode=String.valueOf(obj2).trim();
//		if(tpstc_mode.equals("null")|| StringUtils.isEmpty(tpstc_mode)){
//			try {
//				ClassReflectionUtils.setFieldValue(templatePO, "m_tpstc_mode", "ptm");
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	 	}
//	}

}
