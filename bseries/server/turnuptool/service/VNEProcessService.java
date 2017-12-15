package com.calix.bseries.server.turnuptool.service;

import com.calix.bseries.server.ana.common.error.AnaException;
import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.turnuptool.TurnUptoolService;
import com.calix.bseries.server.turnuptool.dao.VNEDaoImpl;
import com.calix.bseries.server.turnuptool.entity.CreateResultBean;
import com.calix.bseries.server.turnuptool.telnet.TurnUpToolTelnet;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.exception.SessionException;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.ems.server.dbmodel.IGlobalProfile;
import com.calix.system.server.dbmodel.CMSObjectAid;
import com.calix.system.server.dbmodel.EMSAid;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.ICMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;
import com.calix.system.server.dbmodel.TLVClassMap;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class VNEProcessService implements ErrorMsgAware {
	private static final Logger log = Logger.getLogger(TurnUptoolService.class);
	private static final VNEProcessService instance = new VNEProcessService();
	private static final String B6_DEVICE_TYPE = "CalixB6Device";

	public static VNEProcessService getInstance() {
		return instance;
	}

	/**
	 * Execute createVNE
	 * 
	 * @param request
	 * @return deviceType
	 * @throws AnaException
	 */
	public CreateResultBean createVNE(String request) {	
		log.info("begin to create VNE : " + request);
		CreateResultBean result = new CreateResultBean();
		VNEDaoImpl dao = VNEDaoImpl.getInstance();
		CalixB6Device b6 = new CalixB6Device();

		String NetworkName = "";
		SAXReader reader = new SAXReader();

		try {
			Document document = reader.read(new StringReader(request));
			Element root = document.getRootElement();
			Element info = root.element("param").element("value")
					.element("management.IElementManagement");

			String SNMPReadCommunity = ((Element) info.elements(
					"SNMPReadCommunity").get(0)).getText();

			String writeCommunity = ((Element) info.elements(
					"SNMPWriteCommunity").get(0)).getText();

			NetworkName = ((Element) info.elements("ElementName").get(0))
					.getText();
			result.setHostName(NetworkName);
			if (NetworkName != null && !NetworkName.startsWith("NTWK-")) {
				NetworkName = "NTWK-" + NetworkName;
			}

			//result.setHostIP(((Element) info.elements("IP").get(0)).getText());

			ICMSAid aid = null;
			try {
				Class clz = TLVClassMap.getClassForType(B6_DEVICE_TYPE);

				aid = toCMSAid(NetworkName, B6_DEVICE_TYPE, clz);
			} catch (Exception e) {
				log.error("Failed to create CMSAid object: ", e);
				result.setResult(FAILURE);
				result.setMessage(SERVICE_INTERNAL_EXCEPTION + e);
				return result;
			}
			String req = ((Element) info.elements("ID").get(0)).getText();
			String IP = req.substring(req.indexOf("IP=") + 3, req.indexOf(")"));
			result.setHostIP(IP);
			// check
			if (!checkIP(IP)) {
				result.setResult(FAILURE);
				result.setMessage(IP_INVALID_);
				return result;
			}
			CalixB6Device result1 = dao.queryCMSObjectByName(NetworkName);
			if (result1 != null) {
				result.setResult(FAILURE);
				result.setMessage(NETWORKNAME_EXISTED);
				return result;
			}
			CalixB6Device result2 = dao.queryCMSObjectByIpAddress1(IP);
			if (result2 != null) {
				result.setResult(FAILURE);
				result.setMessage(VNE_EXISTED);
				return result;
			}
			
			B6Settings b6Settings=getB6Settings();
			
			b6.setNetworkLoginName("cli");
			b6.setNetworkLoginPassword(b6Settings.getCliPassword());
			b6.setEnablePassword(b6Settings.getEnablePassword());
			b6.setSID(NetworkName);
			b6.setReadCommunity(SNMPReadCommunity);
			b6.setWriteCommunity(writeCommunity);
			b6.setNetworkName(aid);
			b6.setIPAddress1(IP);
			b6.setXOffset(Integer.valueOf(100));
			b6.setYOffset(Integer.valueOf(100));
			b6.setWidth(Integer.valueOf(100));
			b6.setHeight(Integer.valueOf(50));
			b6.setsynchronizeTime(1);
			b6.setAutoConnect(Integer.valueOf(1));
			b6.setConnectionState(Integer.valueOf(0));
			b6.setRegion("REG-autodiscovered");
			b6.setWebUsername("Administrator");
			b6.setprofile(new GenericCMSAid(8382, "51"));
			b6.setkeepChassis(0);
			b6.setTimezone(Integer.valueOf(420));
			String daoResult = dao.createCMSObject(b6);
			if (SUCCESS.equals(daoResult)) {
				log.info("create VNE successfully: " + NetworkName);
				result.setResult(SUCCESS);
				result.setMessage(SUCCESS_MSG);
				/*String deviceType = getDeviceType(IP,b6Settings.getEnablePassword());
				if (CONNECT_FAILED.equals(deviceType)) {
					result.setMessage(CONNECT_FAILED_MSG);
					log.info("craete VNE success,but connect failed--"
							+ NetworkName);
				}
				result.setDeviceType(deviceType);*/
				return result;
			} else {
				log.error("Failed to craete VNE: " + NetworkName + ",reason:"
						+ daoResult);
				result.setResult(FAILURE);
				result.setMessage(SERVICE_INTERNAL_EXCEPTION + daoResult);
				return result;
			}
		} catch (Exception e) {
			log.error("Failed to create VNE: " + NetworkName, e);
			result.setResult(FAILURE);
			result.setMessage(SERVICE_INTERNAL_EXCEPTION + e.getMessage());
			return result;
		}
	}
	
	
	public B6Settings getB6Settings(){
        //set default value per B6 setting
        DbTransaction txn = null;
        B6Settings b6setting = null;
        try {
            b6setting = (B6Settings)CMSCacheManager.getCacheManager().getEMSObject
                (new B6Settings().getHierarchyIntegers(), new EMSAid("B6Setting") );
            if (b6setting != null){
               return b6setting;
            }else{
            	 log.error("get B6 setting null value give a default B6 settings");
            	 b6setting=new B6Settings();
            	 b6setting.setCliPassword("frpocc");
            	 b6setting.setEnablePassword("occean");
            	 return b6setting;
            }
        }catch(Exception e){
            log.error("fail to get B6Settings from VNEProcessService getB6Settings",e);
        }
        return null;
    }
	
	public String deleteVNE(String request) {
		log.info("begin to delete VNE,request: " + request);
		SAXReader reader = new SAXReader();
		String networkName = "";
		try {
			Document document = reader.read(new StringReader(request));
			Element root = document.getRootElement();
			String req = root.element("param").element("value").getText();
			networkName = "NTWK-"
					+ req.substring(req.indexOf("Key=") + 4,
							req.lastIndexOf(")"));

			VNEDaoImpl dao = VNEDaoImpl.getInstance();
			CalixB6Device b6 = dao.queryCMSObjectByName(networkName);
			if (b6 == null) {
				return VNE_NOT_EXISTED;
			}
			if (((b6 != null) && (b6.getConnectionState() != null) && (b6
					.getConnectionState().intValue() == 3))) {
				disconnectCMSObject(networkName);
			}
			String result = dao.deleteCMSObject(b6);
			if (SUCCESS.equals(result)) {
				log.info("delete VNE successfully: " + networkName);
				return SUCCESS;
			} else {
				log.error("Failed to delete VNE: " + networkName + ",reason:"
						+ result);
				return result;
			}
		} catch (Exception e) {
			log.error("Failed to delete VNE: " + networkName, e);
			return SERVICE_INTERNAL_EXCEPTION + e.getMessage();
		}
	}

	/**
	 * Method getDeviceType
	 * 
	 * @param IP
	 */
	private String getDeviceType(String IP) {
		TurnUpToolTelnet tel = new TurnUpToolTelnet();
		String deviceType = "ConfANA6214";
		try {
			String connectResult = tel.connect(IP);
			if (!"SUCCESS".equals(connectResult)) {
				return connectResult;
			}
			deviceType = tel.excuteCommand("show version");
			if(deviceType.contains("for")){
				deviceType = deviceType.substring(deviceType.indexOf("for")+3,(deviceType.indexOf("["))).trim();
			}
			return deviceType;
		} catch (Exception e1) {
			log.error("device" + IP + " can't be connect.", e1);
			tel.disconnect();
			return "CONNECT FAILED";
		}
	}
	
	private String getDeviceType(String IP,String enablePassword) {
		TurnUpToolTelnet tel = new TurnUpToolTelnet();
		String deviceType = "ConfANA6214";
		try {
			String connectResult = tel.connect(IP,enablePassword);
			if (!"SUCCESS".equals(connectResult)) {
				return connectResult;
			}
			deviceType = tel.excuteCommand("show version");
			if(deviceType.contains("for")){
				deviceType = deviceType.substring(deviceType.indexOf("for")+3,(deviceType.indexOf("["))).trim();
			}
			return deviceType;
		} catch (Exception e1) {
			log.error("device" + IP + " can't be connect.", e1);
			tel.disconnect();
			return "CONNECT FAILED";
		}
	}

	public static ICMSAid toCMSAid(String strAid, String recordTypeName,
			Class<?> objClz) throws Exception {
		RecordType recordType = TypeRegistry.getInstance().getRecordType(
				recordTypeName);

		IValueType identityValueType = recordType.getIdentityAttribute()
				.getFirstType();

		int aidTlvType = identityValueType.getDomainIntValue("TLV", "type", -1);

		CMSObjectAid aid = new CMSObjectAid(aidTlvType, strAid);
		if (IGlobalProfile.class.isAssignableFrom(objClz)) {
			aid.setAddressId(Long.parseLong(strAid));
		}
		aid.convertTo();
		return aid;
	}

	public void disconnectCMSObject(String NetworkName) {
		SessionManager sessionManager = SessionManager.getInstance();
		try {
			sessionManager.removeSouthBoundSession(NetworkName);
		} catch (SessionException e) {
			log.error("Exception thrown during connect/disconnect: "
					+ e.getMessage());
		}
	}

	public Boolean checkIP(String IP) {
		Pattern pattern = Pattern
				.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

		Matcher matcher = pattern.matcher(IP);
		return matcher.matches();

	}
}