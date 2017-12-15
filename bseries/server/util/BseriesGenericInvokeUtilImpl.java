package com.calix.bseries.server.util;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.bseries.server.dbmodel.B6DHCPIpHost;
import com.calix.bseries.server.dbmodel.B6EndSubscriber;
import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.task.AeDiscoveryReqSignal;
import com.calix.ems.core.CoreUtilities;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.CMSDBQuery;
import com.calix.ems.database.DBUtil;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.security.CMSDESCipher;
import com.calix.ems.server.common.CMSConfig;
import com.calix.ems.server.dbmodel.PortSubscriber;
import com.calix.ems.server.process.CMSProcess;
import com.calix.msap.server.dbmodel.CalixIpHostIdGenerator;
import com.calix.ont.server.dbmodel.OntTlvConstants;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.common.log.Log;
import com.calix.system.server.dbmodel.CMSObject;
import com.calix.system.server.dbmodel.EMSAid;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;
import com.calix.system.server.session.SessionManager;
import com.occam.ems.be.app.fault.OccamAlarmClosureQueueManager;
import com.occam.ems.be.util.MediationServerUtil;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.dataclasses.OperationFailure;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.mo.servicemanagement.SubscriberAssociation;
import com.occam.ems.common.proxy.BseriesGenericInvokeUtilProxy;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.ResourceBundleUtil;

/**
 * User: hzhang
 * Date: 03/22/2012
 */
public class BseriesGenericInvokeUtilImpl implements BseriesGenericInvokeUtilProxy {
    private static Logger logger = Logger.getLogger(BseriesGenericInvokeUtilImpl.class);
    private static BseriesGenericInvokeUtilImpl instance = new BseriesGenericInvokeUtilImpl();
    
	public static String BSERIES_ETHERNET_PREFIX="ETH";
	public static String BSERIES_DSL_PREFIX="DSL";
	public static String BSERIES_BND_PREFIX="BND";
	public static String BSERIES_GPON_ONT_PREFIX="ONT";
	public static String BSERIES_SUBSCRIBERNAME="SubscriberName";
	public static String BSERIES_SUBSCRIBERPHONE="SubscriberPhone";
	
	private static String ethernet="";
	private static String dsl="";
	private static String bnd="";
	private static String gpon_ont="";
	
	static{
		//Get prefix for association data
		ethernet = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON,"SYNC_ENDPOINT_ETHERNET_PREFIX");
		if(ethernet == null || ethernet.equals("")){
			ethernet=OccamStaticDef.SYNC_ENDPOINT_ETHERNET;
		}
		dsl = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON,"SYNC_ENDPOINT_DSL_PREFIX");
		if(dsl == null || dsl.equals("")){
			dsl=OccamStaticDef.SYNC_ENDPOINT_DSL;
		}
		bnd = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON,"SYNC_ENDPOINT_BND_PREFIX");
		if(bnd == null || bnd.equals("")){
			bnd=OccamStaticDef.SYNC_ENDPOINT_BND;
		}
		gpon_ont = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON,"SYNC_ENDPOINT_GPON_ONT_PREFIX");
		if(gpon_ont == null || gpon_ont.equals("")){
			gpon_ont=OccamStaticDef.SYNC_ENDPOINT_ONT;
		}	
		logger.info("BseriesGenericInvokeUtilImpl. print static predefined prefix. ethernet: "
				+ ethernet
				+ ", dsl: "
				+ dsl
				+ ", bnd: "
				+ bnd
				+ ", gpon_ont: "
				+ gpon_ont);
	}

	
	private static final int SSH_PORT = 22;

    public static BseriesGenericInvokeUtilImpl getInstance() {
        return instance;
    }

    @Override
	public Properties getCalixB6DeviceInfo(String ip) {
		Properties property = new Properties();
		C7Database db = (C7Database) C7Database.getInstance();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select * from Calixb6device where ");
		sqlBuf.append("ipaddress1='").append(ip).append("';");
		logger.info("The CalixB6device Query to be executed is "+ sqlBuf.toString());
		
		ResultSet resultSet = null;
		try {
			db.beginTransaction();
			resultSet = db.executeQuery(sqlBuf.toString());
			if (resultSet != null) {
				if (resultSet.next()) {
					property.setProperty(DevProperty.DEVICE_IP_ATTR_NAME, ip);
					property.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME,
							resultSet.getString("swversion"));
					property.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME,
							resultSet.getString("ipaddress1"));
					property.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME,
							resultSet.getString("model"));
					property.setProperty(OccamStaticDef.SHELF_INDEX,
							resultSet.getString("shelfid"));
					property.setProperty(OccamStaticDef.SLOT_INDEX,
							resultSet.getString("slotid"));
				}
			}
			db.commitTransaction();
		} catch (Exception e) {
			Log.web().error(" getNodes: Unable to get data ", e);
			db.rollbackTransaction();
		} finally {
			DBUtil.closeQuietly(resultSet);
			db.close();
		}
		return property;
	}
    
    @Override
    public String decodePassword(byte [] bPasswd) {
        return new String(CMSDESCipher.getInstance().decrypt(bPasswd));
    }

    @Override
    @Deprecated
    public String getServerIP() {
        //Cannot get ip this way, its client method. 
        //Only server method is supported.
//        return EMSConfig.getLoginInfo().getServer();
        return null;
    }
    public void raiseEvent(String id,String alarmAlias, String msg) {
    	logger.debug("raiseEvent: Raising Event " + msg);
        CoreUtilities.raiseEMSEvent(new EMSAid(id),CMSObject.getAliasValue(alarmAlias),CalixConstants.DEFAULT_CMS_NETWORK,msg,CoreUtilities.DeviceCMS,0x0593,System.currentTimeMillis());
    }
    
    public void doAeOntDiscovery(String ontIP){
    	
		CMSObject ontObj = findONTByONTIPFromDB(ontIP);
		if (ontObj == null){
			
			int timeout = OccamAlarmClosureQueueManager.RESYNC_EQUIPMENT_PING_TIMEOUT*6;
			int retries = OccamAlarmClosureQueueManager.RESYNC_EQUIPMENT_PING_RETRIES*3;
			if (!PingUtility.isDeviceReachableUsingJDK(ontIP,SSH_PORT,retries,timeout)){
				logger.info("doAeOntDiscovery: AE ONT not reachable :" + ontIP );
				return;
			}
			AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();                
            signal.setIpAddress(ontIP);  
            //signal.setExternalProvisioned(Ae_Ont.OCM_PROVISIONED);
            logger.info("doAeOntDiscovery: Sending AE discovery request for :" + ontIP);
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
            logger.info("doAeOntDiscovery: Sent AE discovery request for :" + ontIP );
		}
    }
    
//    public void doAeOntDiscoveryForConnect(String ontIP){
//    	
//		CMSObject ontObj = findONTByONTIPFromDB(ontIP);
//		if (ontObj == null){
//            logger.info("doAeOntDiscovery: start discover ae ont for :" + ontIP);
//			if (!PingUtility.isDeviceReachableUsingJDK(ontIP, SSH_PORT, -1, -1)){
//				logger.info("doAeOntDiscovery: AE ONT not reachable :" + ontIP );
//				return;
//			}
//			AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();                
//            signal.setIpAddress(ontIP);  
//            //signal.setExternalProvisioned(Ae_Ont.OCM_PROVISIONED);
//            logger.info("doAeOntDiscovery: Sending AE discovery request for :" + ontIP);
//            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
//            logger.info("doAeOntDiscovery: Sent AE discovery request for :" + ontIP );
//		}
//    }
    
    public void doAeOntDiscovery(String ontIP,String port,String networkName){
    	
		CMSObject ontObj = findONTByONTIPFromDB(ontIP);
		if (ontObj == null){
			if (!PingUtility.isDeviceReachableUsingJDK(ontIP, SSH_PORT, -1, -1)){
				logger.info("doAeOntDiscovery: AE ONT not reachable :" + ontIP );
				return;
			}
			AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();                
            signal.setIpAddress(ontIP);
            signal.setPort(port);
            signal.setNetworkName(networkName);
            //signal.setExternalProvisioned(Ae_Ont.OCM_PROVISIONED);
            logger.info("doAeOntDiscovery: Sending AE discovery request for :" + ontIP);
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
            logger.info("doAeOntDiscovery: Sent AE discovery request for :" + ontIP );
		}else if(ontObj instanceof Ae_Ont && networkName != null && !networkName.equalsIgnoreCase("")){
			boolean isNeedUpdate = false;
			if(((Ae_Ont)ontObj).getLinked_Network()== null || !((Ae_Ont)ontObj).getLinked_Network().equalsIgnoreCase(networkName)){
				isNeedUpdate = true;
				((Ae_Ont)ontObj).setLinked_Network(networkName);
			}
			if(port!=null && !port.equalsIgnoreCase("")&& port.matches("^[0-9]+$")){
				GenericCMSAid linked_port = getGePortAid(port,SessionManager.getInstance().getNetworkReference(networkName));
				if(linked_port != null){
					if(((Ae_Ont)ontObj).getlinked_port()==null || ((Ae_Ont)ontObj).getlinked_port()!= linked_port){
						isNeedUpdate = true;
						((Ae_Ont)ontObj).setlinked_port(linked_port);
					}
				}
        	}
			try {
				if(isNeedUpdate){
					((Ae_Ont)ontObj).doUpdate();
				}
			} catch (Exception e) {
				logger.error("Failed to update AeOnt for filling B6 networkName and linked Port", e);
			}
		}
    }
    
    private static final int B6Tlv_B6_GePort = 0x34F3;
    private static final int B6Tlv_tB6GEPortAid = 0x34F4;
    private GenericCMSAid getGePortAid(String intfAid,short networkRef) {
        GenericCMSAid portAid = new GenericCMSAid(B6Tlv_B6_GePort,B6Tlv_tB6GEPortAid,intfAid);
        portAid.convertTo();
        portAid.getAddressId();
        portAid.setNetworkRef(networkRef);
        return portAid;
    }
    
    public boolean doAeOntUpdate(String ontIP,boolean isActivated){
    	int exterProv = Ae_Ont.EXTERNAL_PROVISIONED;
    	if (isActivated){
    		exterProv = Ae_Ont.OCM_PROVISIONED;
    	}
    	try {
			CMSObject ontObj = findONTByONTIPFromDB(ontIP);
			if (ontObj != null){
				logger.info("doAeOntUpdate: AEONT already exists " + ontIP);
				Ae_Ont aeontObj = (Ae_Ont)ontObj;
				aeontObj.setExternalProvisioned(exterProv);
				aeontObj.doUpdate();
				return true;
			}else{
				logger.info("doAeOntUpdate: AEONT does not exist " + ontIP);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }
    private CMSObject findONTByONTIPFromDB(String aeontIP) {
		Ae_Ont aeont = new Ae_Ont();
        CMSDBQuery query = new CMSDBQuery();
        query.addFromList(Ae_Ont.TYPE_NAME, Ae_Ont.TYPE_NAME.toLowerCase());
        query.addWhereClause("ipaddress='" + aeontIP + "'");
		Collection result = aeont.doQuery(query);
		if (result != null && result.size() > 0) {
			aeont = (Ae_Ont)result.iterator().next();
		} else {
			aeont = null;
		}
		return aeont;
	}	
    
    public Properties updateCalixIpHostForB6(String devName,
			ArrayList subAssoList, boolean isAssoNeed){
	
		// Find the referenceId using devName, and clean up old data
		List<CalixB6Device> rst = null;
		short referenceid = -1;
		String networkName = null;
		C7Database db = C7Database.getInstance();
		try {
			CMSDBQuery dbQuery = new CMSDBQuery();
			dbQuery.addFromList("CalixB6Device", "device");
			dbQuery.addWhereClause("device.IPAddress1 = '" + devName + "'");
			db.beginTransaction();
			rst = (List<CalixB6Device>) db.executeQuery(dbQuery);
			db.commitTransaction();
			for (CalixB6Device device : rst) {
				referenceid = device.getReferenceId();
				networkName = device.getDbIdentity();
			}

			if (referenceid == -1) {
				logger.warn("Can not find the related B6 device, deviceName: "
						+ devName);
				return new Properties();
			}
			db.beginTransaction();
			db.removeObjects("CalixIpHost", referenceid);
			db.commitTransaction();
		} catch (Exception e) {
			logger.error("Load CalixB6Device and clean CalixIpHost", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}

		ArrayList<B6DHCPIpHost> list = new ArrayList<B6DHCPIpHost>();
		for (int i = 0; i < subAssoList.size(); i++) {
			SubscriberAssociation obj = (SubscriberAssociation) subAssoList
					.get(i);

			B6DHCPIpHost ipHost = new B6DHCPIpHost();
			ipHost.setStrIpAddress(obj.getInterfaceIP());
			ipHost.setStrMacAddress(obj.getInterfaceMAC());
			
			String portIdStr = parsePortIdFromObj(obj.getBondId(), obj);
			if(portIdStr== null){
				logger.warn("BseriesGenericInvokeUtilImpl: portIdStr is null. ");
				continue; 
			}
			logger.info("BseriesGenericInvokeUtilImpl: portIdStr is " + portIdStr + ", original portId: " + obj.getBondId());
			
			if (isAssoNeed) {
				if (networkName != null && networkName.startsWith("NTWK-")) {
					networkName = networkName.substring(5);
				}
				ipHost.setStrNodeName(networkName);
			}
			ipHost.setStrL2IFAID(portIdStr);
			ipHost.setL2IFAID(new GenericCMSAid(SystemTlvConstants.tBseriesAid, portIdStr));
			ipHost.setUID(CalixIpHostIdGenerator.getInstance().getNextSeq());
			ipHost.setIdentityValue(new GenericCMSAid(SystemTlvConstants.tBseriesAid, portIdStr));
			ipHost.getIdentityValue().setNetworkRef(referenceid);
			ipHost.getIdentityValue().convertTo();

			logger.debug("BseriesGenericInvokeUtilImpl. ipHost: " + ipHost.toString());
			list.add(ipHost);
		}
        
		// save the record to the db
		B6DHCPIpHost ipHost = new B6DHCPIpHost();
		try {
			db.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				ipHost = (B6DHCPIpHost) list.get(i);
				db.createCMSObject(ipHost);
			}
			db.commitTransaction();
		}  catch (Exception e) {
			logger.error("Create CalixIpHost", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
        
		logger.info("IP Host Information are resynced for network: " + devName);
		//CMS-9691, support B6 Endpoint
        handleResyncEndPointData(devName, list);
		
		return new Properties();
	}
    
	private String parsePortIdFromObj(String portIdStr, SubscriberAssociation obj) {
		//construct using prefix and number in order. 
		//Ethernet15 --> ETH15-1 
		logger.debug("BseriesGenericInvokeUtilImpl. print predefined prefix. ethernet: "
				+ ethernet
				+ ", dsl: "
				+ dsl
				+ ", bnd: "
				+ bnd
				+ ", gpon_ont: "
				+ gpon_ont);
		
		if (portIdStr != null) {
			if (portIdStr.startsWith(ethernet)) {
				portIdStr = BSERIES_ETHERNET_PREFIX + portIdStr.substring(ethernet.length());
			} else if (portIdStr.startsWith(dsl)) {
				portIdStr = BSERIES_DSL_PREFIX + portIdStr.substring(dsl.length());
			} else if (portIdStr.startsWith(bnd)) {
				portIdStr = BSERIES_BND_PREFIX + portIdStr.substring(bnd.length());
			} else if (portIdStr.startsWith(gpon_ont)) {
				portIdStr = BSERIES_GPON_ONT_PREFIX + portIdStr.substring(gpon_ont.length());
			} else {
				logger.warn("BseriesGenericInvokeUtilImpl: portIdStr format is invalid: "
						+ portIdStr + " . InterfaceIP: "
						+ obj.getInterfaceIP() + ", InterfaceMAC: "
						+ obj.getInterfaceMAC());
				return null;
			}
		}else{
			logger.warn("BseriesGenericInvokeUtilImpl: portIdStr is null for this record. InterfaceIP: " + obj.getInterfaceIP()
					+ ", InterfaceMAC: " + obj.getInterfaceMAC());
			return null;
		}
		return portIdStr;
	}

	private void handleResyncEndPointData(String ipAddress, ArrayList hostList) {
		logger.warn("BseriesGenericInvokeUtilImpl: handleResyncEndPointData for device: " + ipAddress);

    	CalixB6Device device = getB6Device(ipAddress);
    	
		logger.debug("BseriesGenericInvokeUtilImpl: handleResyncEndPointData, start getEndPointData for device: " + ipAddress);
		if (device != null && hostList != null && hostList.size() != 0) {
			HashMap endPointData = (HashMap) getEndPointDataViaMediation(device);
			if (endPointData != null) {
				// If mediation code executed correctly, clean up old data. 
		    	clearAllExistingB6Subscriber(device);
		    	clearAllExistingPortSubscriber(device);	    	
				processSubscriberData(device, hostList, endPointData);
			}
		} else {
			logger.info("BseriesGenericInvokeUtilImpl: There is no end point data need to be updated in port_subscriber. ");
		}
	}

	private void processSubscriberData(CalixB6Device device,
			ArrayList<B6DHCPIpHost> hostList, HashMap resultData) {
		logger.info("BseriesGenericInvokeUtilImpl: processSubscriberData for device: "
				+ device.getIPAddress1() + ", result data: " + resultData.toString());
		
		ArrayList<B6EndSubscriber> b6SubList = new ArrayList<B6EndSubscriber>();
		ArrayList<PortSubscriber> subList = new ArrayList<PortSubscriber>();
		
		//CMS-12237, by ivy
        ICMSDatabase database = null;
        DbTransaction txn = null;
        B6Settings b6setting = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            
            b6setting = (B6Settings)database.readSingletonCMSObject(B6Settings.TYPE_NAME, txn);
            txn.commit();
        }catch (Exception e) {
        	logger.error(e.getMessage());
        }finally{
            if (txn != null && txn.isActive())
                txn.abort();
        }
        
        String subIdKey = BSERIES_SUBSCRIBERNAME;
        String subDescKey = BSERIES_SUBSCRIBERPHONE;
        String subIdGPONKey ="";
        String subDescGPONKey ="";
        
        if(b6setting!=null){
			subIdKey = b6setting.getSubIdPrefix();
			if(subIdKey == null){
				subIdKey=BSERIES_SUBSCRIBERNAME;
			}
			subDescKey = b6setting.getSubDescPrefix();
			if(subDescKey == null){
				subDescKey=BSERIES_SUBSCRIBERPHONE;
			}
			subIdGPONKey = b6setting.getSubIdPrefixforGpon();
			if(subIdGPONKey == null){
				subIdGPONKey="";
			}
			subDescGPONKey = b6setting.getSubDescPrefixforGpon();
			if(subDescGPONKey == null){
				subDescGPONKey="";
			}
        }
		//CMS-12237,end
		logger.warn("BseriesGenericInvokeUtilImpl: processSubscriberData properties. subIdKey: "
				+ subIdKey + ", subDescKey: "
				+ subDescKey + ", subIdGPONKey: "
				+ subIdGPONKey + ", subDescGPONKey: " + subDescGPONKey);
		
		ArrayList<B6DHCPIpHost> newHostList = removeDuplicate(hostList);
		
		for (B6DHCPIpHost host : newHostList) {
			String strL2IFAID = host.getStrL2IFAID();

			String matchId = revertProperPortAid(strL2IFAID);
			logger.info("BseriesGenericInvokeUtilImpl: processSubscriberData strL2IFAID: " + strL2IFAID + ", matchId: " + matchId);
			
			if (strL2IFAID != null && matchId!= null){
				
				ArrayList list = (ArrayList) resultData.get(matchId);
				String subId = "";
				String subDesc = "";
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String str = (String) list.get(i);
						logger.debug("BseriesGenericInvokeUtilImpl: str : " + str + ", strL2IFAID: " + strL2IFAID);
						
						if(OccamUtils.isGponOlt(device.getModel())){
							if (str != null && str.startsWith(subIdGPONKey) && subDesc.equals("")) {
								subDesc = getValueFrom(str, subIdGPONKey);
							} else if (str != null
									&& str.startsWith(subDescGPONKey) && subId.equals("")) {
								subId = getValueFrom(str, subDescGPONKey);
							}
						}else if((subIdKey == null || subIdKey.equals("")) && (subDescKey == null || subDescKey.equals(""))){
							if (str != null && str.startsWith(subIdKey) && subDesc.equals("")) {
								subDesc = getValueFrom(str, subIdKey);
							} else if (str != null
									&& str.startsWith(subDescKey) && subId.equals("")) {
								subId = getValueFrom(str, subDescKey);
							}
						} else {
							if (str != null && str.startsWith(subIdKey)) {
								subDesc = getValueFrom(str, subIdKey);
							} else if (str != null
									&& str.startsWith(subDescKey)) {
								subId = getValueFrom(str, subDescKey);
							}
						}
					}
				}else{
					logger.warn("BseriesGenericInvokeUtilImpl: Did not find the infor for this aid : " + matchId);
					continue;
				}
				if ((subId != null && subId != "")
						|| (subDesc != null && subDesc != "")) {
					B6EndSubscriber b6subscriber = new B6EndSubscriber();
					b6subscriber.setIdentityValue(host.getIdentityValue());
					b6subscriber.setStraid(strL2IFAID);
					b6subscriber.setSubscriberID(subId);
					b6subscriber.setUserDescr(subDesc);
					b6subscriber.setRecordTypeTlvCode(SystemTlvConstants.tBseriesAid);
					b6subscriber.setDevicesRegion(device.getRegion());
					logger.debug("BseriesGenericInvokeUtilImpl:  b6subscriber: " + b6subscriber.toString());

					b6SubList.add(b6subscriber);

					PortSubscriber subscriber = new PortSubscriber();
					subscriber.setIdentityValue(host.getIdentityValue());
					subscriber.setStraid(strL2IFAID);
					subscriber.setSubscriberID(subId);
					subscriber.setUserDescr(subDesc);
					subscriber.setRecordTypeTlvCode(SystemTlvConstants.tBseriesAid);
					subscriber.setDevicesRegion(device.getRegion());
					subList.add(subscriber);
				}else{
					logger.warn("BseriesGenericInvokeUtilImpl: subId is:" + subId + ", subDesc: " + subDesc);
				}
			}else{
				logger.warn("BseriesGenericInvokeUtilImpl: strL2IFAID = " + strL2IFAID + ", matchId: " + matchId);
			}
		}
		add_B6_Subscriber(device, b6SubList);
		add_Port_Subscriber(device, subList);
	}

	private String revertProperPortAid(String strL2IFAID) {
		//revert from internal ID to official
		// ETH15 --> Ethernet15
		if(strL2IFAID!= null){
			if(strL2IFAID.startsWith(BSERIES_ETHERNET_PREFIX)){
				return OccamStaticDef.SYNC_ENDPOINT_ETHERNET + strL2IFAID.substring(BSERIES_ETHERNET_PREFIX.length());
			}else if(strL2IFAID.startsWith(BSERIES_DSL_PREFIX)){
				return OccamStaticDef.SYNC_ENDPOINT_DSL + strL2IFAID.substring(BSERIES_DSL_PREFIX.length());
			}else if(strL2IFAID.startsWith(BSERIES_BND_PREFIX)){
				return OccamStaticDef.SYNC_ENDPOINT_BND + strL2IFAID.substring(BSERIES_BND_PREFIX.length());
			}else if(strL2IFAID.startsWith(BSERIES_GPON_ONT_PREFIX)){
				return OccamStaticDef.SYNC_ENDPOINT_ONT + strL2IFAID.substring(BSERIES_GPON_ONT_PREFIX.length());
			}else{
				logger.warn("BseriesGenericInvokeUtilImpl: strL2IFAID is invalid: " + strL2IFAID);
				return null;
			}	
		}else{
			logger.warn("BseriesGenericInvokeUtilImpl: strL2IFAID is null: " + strL2IFAID);
			return null;
		}
	}

	private ArrayList<B6DHCPIpHost> removeDuplicate(
			ArrayList<B6DHCPIpHost> hostList) {
		ArrayList<B6DHCPIpHost> newHostList = new ArrayList<B6DHCPIpHost>();

		for (B6DHCPIpHost host : hostList) {
			boolean isExist = false;
			for(B6DHCPIpHost newhost : newHostList){
				//The key is composed by networkref, aidType and addressId. aidType are same. so only compare others. 
				if(host.getID().getNetworkRef() == newhost.getID().getNetworkRef()
						&& host.getAddressId() == newhost.getAddressId()){
					isExist = true;
				}
			}
			if(!isExist){
				logger.debug("BseriesGenericInvokeUtilImpl: removeDuplicate, not exist: host " + 
						host.toString());
				newHostList.add(host);
			}else{
				logger.info("BseriesGenericInvokeUtilImpl: removeDuplicate, exist host: " + 
						host.toString());
			}
		}
		return newHostList;
	}
	
	private String getValueFrom(String str, String string) {
		String newStr = "";
		if(str!= null && str.startsWith(string)){
			newStr = str.substring(string.length()).trim();
		}
		return newStr;			
	}

	private void add_B6_Subscriber(CalixB6Device device,
			ArrayList<B6EndSubscriber> list) {
		C7Database db = C7Database.getInstance();
		logger.warn("BseriesGenericInvokeUtilImpl: add_B6_Subscriber for device: " + device.getIPAddress1());

		try {
			db.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				B6EndSubscriber sub = (B6EndSubscriber) list.get(i);
				db.createCMSObject(sub);
			}
			db.commitTransaction();
		}  catch (Exception e) {
			logger.error("BseriesGenericInvokeUtilImpl: add_B6_Subscriber failure", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}			

	}

	private void add_Port_Subscriber(CalixB6Device device, ArrayList<PortSubscriber> list) {
		C7Database db = C7Database.getInstance();
		logger.warn("BseriesGenericInvokeUtilImpl: add_Port_Subscriber for device: " + device.getIPAddress1());

		try {
			db.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				PortSubscriber sub = (PortSubscriber) list.get(i);
				db.createCMSObject(sub);
			}
			db.commitTransaction();
		}  catch (Exception e) {
			logger.error("BseriesGenericInvokeUtilImpl: add_Port_Subscriber failure", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
	}

	private HashMap getEndPointDataViaMediation(CalixB6Device device) {
		
        List errLst = null;
        HashMap resultData = null;
        OccamProtocolRequestResponse endPointRequest  = new OccamProtocolRequestResponse();
        
        //set devProperty
        String devName = device.getIPAddress1();
        String equipType = device.getModel();
        String swVersion = device.getSWVersion();
        DevProperty devProperty = new DevProperty();
        
        logger.debug("BseriesGenericInvokeUtilImpl::getEndPointDataViaMediation:  Input to Dev property : ip - " + devName + ", Eqpt Type - " + equipType + ", swVersion - " + swVersion);
        devProperty.setProperty(DevProperty.DEVICE_IP_ATTR_NAME, devName);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, equipType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, swVersion);
        
        endPointRequest.setDeviceProperty(devProperty);
        logger.debug("BseriesGenericInvokeUtilImpl::getEndPointDataViaMediation - Get interface on device " + devName);
        
        String model = device.getModel();
        if(OccamUtils.isFiberDevice(model)){
            endPointRequest.setOperationName(MediationOperationNames.OP_SYNC_ETHENET_INTERFACE);
        }else if(OccamUtils.isGponOlt(model)){
            endPointRequest.setOperationName(MediationOperationNames.OP_SYNC_GPON_INTERFACE);
        }else if(OccamUtils.isVDSLBlade(model)){
        	endPointRequest.setOperationName(MediationOperationNames.OP_SYNC_XDSL_INTERFACE);
        }else if(OccamUtils.isDslDevice(model)){
            endPointRequest.setOperationName(MediationOperationNames.OP_SYNC_DSL_INTERFACE);
        }else{//TODO for future
            logger.debug("BseriesGenericInvokeUtilImpl::getEndPointDataViaMediation - EndPoint not support in this device" + devName + ", model= " + model);
        	endPointRequest.setOperationName(MediationOperationNames.OP_SYNC_OTHER_INTERFACE);
        }

        MediationServerUtil.getInstance().performMediationRequest(endPointRequest);
        errLst = endPointRequest.getErrorInfo();
        if (errLst != null && errLst.size() > 0) {
            logger.debug("BseriesGenericInvokeUtilImpl::getEndPointDataViaMediation - Get EndPoint on device" + devName);
            
            if (errLst != null) {
                String error = getErrorString(errLst);
                logger.error("BseriesGenericInvokeUtilImpl::getEndPointDataViaMediation taskname : Get EndPoint failed on device " + device.getIPAddress1()
                		+ "\n resync error: " + error);
            }     
            return null;
        }
        
        resultData = (HashMap)endPointRequest.getParameter(MediationOperationNames.SUBSCRIBER_INFO);
        return resultData;
	}
	
	protected String getErrorString(List operationFailureLst) {
        StringBuffer errStrBffr = new StringBuffer();
        for (int j = 0; j < operationFailureLst.size(); j++) {
            if (operationFailureLst.get(j) instanceof OperationFailure) {
                OperationFailure failure = (OperationFailure) operationFailureLst.get(j);
                errStrBffr.append(failure.getMessage()).append("\n");
            }
        }
        return errStrBffr.toString();
    }         
	
	private void clearAllExistingB6Subscriber(CalixB6Device device) {
		logger.info("BseriesGenericInvokeUtilImpl: clearAllExistingB6Subscriber for device: " + device.getIPAddress1());

		Short referenceid = device.getReferenceId();
		C7Database db = C7Database.getInstance();
		try {
			db.beginTransaction();
			String sql ="delete from B6EndSubscriber where networkref = " + referenceid;
			db.nativeSql(sql);
			db.commitTransaction();
		} catch (Exception e) {
			logger.error("error when removing B6EndSubscriber", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
	}

	private void clearAllExistingPortSubscriber(CalixB6Device device) {
		logger.info("BseriesGenericInvokeUtilImpl: clearAllExistingSubscriber for device: " + device.getIPAddress1());

		Short referenceid = device.getReferenceId();
		C7Database db = C7Database.getInstance();
		try {
			db.beginTransaction();
			String sql ="delete from port_subscriber where networkref = " + referenceid;
			db.nativeSql(sql);
			db.commitTransaction();
		} catch (Exception e) {
			logger.error("error when removing port_subscriber", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
	}

	private CalixB6Device getB6Device(String ipAddress) {
		logger.debug("BseriesGenericInvokeUtilImpl: getB6Device for device: " + ipAddress);

		CalixB6Device device = null;
		List<CalixB6Device> rst = null;
		C7Database db = C7Database.getInstance();
		HashMap ipMap = new HashMap<String, String>();
		ipMap.put("ipAddress", ipAddress);
		try {
			CMSDBQuery dbQuery = new CMSDBQuery();
			dbQuery.addFromList("CalixB6Device", "device");
			dbQuery.addWhereClause("device.IPAddress1 = :ipAddress");
			db.beginTransaction();
			rst = (List<CalixB6Device>) db.executeQuery(dbQuery, ipMap);
			db.commitTransaction();

			if (rst !=null && rst.size() >0 && rst.get(0) instanceof CalixB6Device){
				device = (CalixB6Device)rst.get(0);
			}else{
				logger.warn("Load CalixB6Device error. size: " + rst.size());
			}
		} catch (Exception e) {
			logger.error("Load CalixB6Device", e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
		return device;
	}

	@Override
	public boolean getConfigFromCMS(String propertyName) {
		String auditLogEnabled = CMSConfig.getInstance().getProperty(
				"/QuartzProperty/PurgeTaskAudit/" + propertyName,
				"value", "true");
		
		if (auditLogEnabled != null && auditLogEnabled.equalsIgnoreCase("false")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List getB6ScheduledTaskAdditionInfo(String taskName) {
		List ResultList = new ArrayList();
		C7Database db = (C7Database) C7Database.getInstance();
		StringBuffer sqlBuf = new StringBuffer();
		if(taskName!=null&&!taskName.equals("")){
			sqlBuf.append("select * from B6ScheduledTask where ");
			sqlBuf.append("taskname='").append(taskName).append("' and tasktype='ESA Configuration';");
		}else{
			sqlBuf.append("select * from B6ScheduledTask where tasktype='ESA Configuration';");
		}
		logger.info("The B6 scheduled task Query to be executed is "+ sqlBuf.toString());
		
		ResultSet resultSet = null;
		try {
			db.beginTransaction();
			resultSet = db.executeQuery(sqlBuf.toString());
			if (resultSet != null) {
				String additionStr;
				while(resultSet.next()){
					additionStr = resultSet.getString("additionalinfo");
					if(additionStr != null){
						ResultList.add(additionStr);
					}
				}
			}
			db.commitTransaction();
		} catch (Exception e) {
			Log.web().error(" getNodes: Unable to get data ", e);
			db.rollbackTransaction();
		} finally {
			DBUtil.closeQuietly(resultSet);
			db.close();
		}
		return ResultList;
	}

	@Override
	public Properties getB6Settings() {
		Properties b6Settings = new Properties();
		C7Database db = (C7Database) C7Database.getInstance();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select * from B6Settings;");
		logger.info("The B6Settings Query to be executed is "+ sqlBuf.toString());
		
		ResultSet resultSet = null;
		try {
			db.beginTransaction();
			resultSet = db.executeQuery(sqlBuf.toString());
			if (resultSet != null&&resultSet.next()) {
				b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_WEBUSERNAME, resultSet.getString("webusername") == null?"":resultSet.getString("webusername")) ;
				byte[] blob = resultSet.getBytes("encryptedwebpassword");
				if(blob != null){
					String pass = decodePassword(blob);
					if(pass != null){
						b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_WEBPASSWORD, resultSet.getString(pass)) ;
					}
				}
				b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_CLIUSERNAME, resultSet.getString("cliusername")) ;
				blob = resultSet.getBytes("encryptedclipassword");
				if(blob != null){
					String pass = decodePassword(blob);
					if(pass != null){
						b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_CLIPASSWORD, resultSet.getString(pass)) ;
					}
				}
				blob = resultSet.getBytes("encryptedreadcommunity");
				if(blob != null){
					String pass = decodePassword(blob);
					if(pass != null){
						b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_READCOMMUNITY, resultSet.getString(pass)) ;
					}
				}
				blob = resultSet.getBytes("encryptedwritecommunity");
				if(blob != null){
					String pass = decodePassword(blob);
					if(pass != null){
						b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_WRITECOMMUNITY, resultSet.getString(pass)) ;
					}
				}
				blob = resultSet.getBytes("encryptedenablepassword");
				if(blob != null){
					String pass = decodePassword(blob);
					if(pass != null){
						b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_ENABLEPASSWORD, resultSet.getString(pass)) ;
					}
				}
				b6Settings.setProperty(OccamStaticDef.B6_SETTINGS_MANIFESTFILEPATH, resultSet.getString("manifestfilepath") == null?"":resultSet.getString("manifestfilepath")) ;
			}
			db.commitTransaction();
		} catch (Exception e) {
			Log.web().error(" getNodes: Unable to get data ", e);
			db.rollbackTransaction();
		} finally {
			DBUtil.closeQuietly(resultSet);
			db.close();
		}
		return b6Settings;
	}
    
}
    	
