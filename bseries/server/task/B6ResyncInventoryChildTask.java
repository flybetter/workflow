package com.calix.bseries.server.task;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.ae.server.session.AeONTProxy;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.server.process.CMSProcess;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.be.app.fault.OccamAlarmClosureQueueManager;
import com.occam.ems.be.app.security.AuthenticationUtil;
import com.occam.ems.be.app.servicemanagement.ResyncSubscriberAssocConfigProvider;
import com.occam.ems.be.mo.*;
import com.occam.ems.be.util.MediationServerUtil;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.dataclasses.*;
import com.occam.ems.common.defines.MediationDefines;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.mo.DeviceAudit;
import com.occam.ems.common.proxy.config.xml.Attribute;
import com.occam.ems.common.proxy.config.xml.Device;
import com.occam.ems.common.proxy.util.Ping;
import com.occam.ems.common.util.DBUtility;
import com.occam.ems.common.util.DefaultConfiguration;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.mediation.transactionengine.common.OccamTransactionTypeDef;
import com.occam.ems.server.DataBaseAPI;
import com.occam.ems.server.ObjectAlreadyPresentException;

import org.apache.log4j.Logger;

import com.calix.bseries.server.boot.BseriesProcessHandler;
import com.calix.bseries.server.dbmodel.B6ScheduledTask;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.connection.NetworkState;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.BaseCMSNetwork;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.util.DeviceChildTask;

import java.util.*;

public class B6ResyncInventoryChildTask  extends CMSScheduledTask implements DeviceChildTask{

	
	private static final Logger log = Logger.getLogger(B6ResyncInventoryChildTask.class);

	private B6ScheduledTask m_parent;
	private String m_network = null;
    private String m_status = null;
    //TODO: Get strings from proper source
    private static final String ATTR_REDISCOVER_BLCS = "rediscoverBLCs";
    private static final String ATTR_RESYNC_ONT_CONFIG_WITH_TFTP_SERVER = "ResyncOntConfigWithTFTP";
    private static final String ATTR_SAVE_CONFIGURATION = "SaveConfiguration";

    boolean rediscoverBLCs = false;
    boolean resyncOntConfigWithTFTPServer = false;
    boolean saveRunningConfigBeforeResync = false;

    private String location;

    protected String networkName;
    protected String ipAddr;
    protected String eqptType;
    protected String version;
    protected String snmpReadCommunity;
    protected OccamProtocolRequestResponse reqRespObj;
    private Properties taskProps = null;

    private static final long TIME_OUT = 5 * 60 * 1000L; // allow max 5 minutes
//    private static final long CHECK_INTERVAL = 10000L; // check every interval in sec
//    private static final long INIT_WAIT = 5000L;

	public B6ResyncInventoryChildTask(){
		super();
	}
	
	public void setParent(Object task) {
		this.m_parent = (B6ScheduledTask) task;
	}
	
	public void setNetwork(String network) {
        this.m_network = network;
        BseriesProcessHandler handle= (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
        handle.addTaskToMap(((GenericCMSAid)m_parent.getJobID()).getStrAid(),network, this);
    }
	public void run() {
		
	 	try {
	 			//CMS-7896 - support Audit log
	 			DBHandler.getInstance().addAuditLog(m_parent.getTaskId(),
					m_parent.getTaskName(), m_parent.getTaskType(),
					m_parent.getBseriesIPAddress(m_network), m_parent.getCreatedBy(),
					m_parent.getDescription());
			
			    String addtlInfo = m_parent.getAdditionalInfo();
                Properties info = CommonUtil.getProperties(addtlInfo);
                String rediscoverBLCsStr = info.getProperty(ATTR_REDISCOVER_BLCS);
                String resyncOntConfigWithTFTPServerStr = info.getProperty(ATTR_RESYNC_ONT_CONFIG_WITH_TFTP_SERVER);
                String saveConfigurationStr = info.getProperty(ATTR_SAVE_CONFIGURATION);
                rediscoverBLCs =  (rediscoverBLCsStr != null ? rediscoverBLCsStr.trim().equalsIgnoreCase("true"): false);
                resyncOntConfigWithTFTPServer = ( resyncOntConfigWithTFTPServerStr != null ? resyncOntConfigWithTFTPServerStr.trim().equalsIgnoreCase("true"): false);
                saveRunningConfigBeforeResync = ( saveConfigurationStr != null ? saveConfigurationStr.trim().equalsIgnoreCase("true"): false);
//                 move resync loigc for  Ae ont managed by OCM to scheduled task in AE
//                if(m_network != null && m_network.startsWith("AEONT-")){                
//                 move resync loigc for  Ae ont managed by OCM to scheduled task in AE
//                if(m_network != null && m_network.startsWith("AEONT-")){                
//                    String ont_id = m_network.substring("AEONT-".length());
//                    Ae_Ont ae_ont = AeONTProxy.getInstance().getAeONTFromDB(ont_id);
//                    if(ae_ont != null && OccamUtils.isOntDevice(ae_ont.getModel())){
//                        //resync on Occam ONT
//                        try {
//                            if(saveRunningConfigBeforeResync && deviceSupportsSaveRunningConfig(ae_ont.getFirmwareVersion())){
//                                sendOntCopyRunningConfigurationRequest(ae_ont.getIpAddress(), ae_ont.getModel(), ae_ont.getFirmwareVersion());
//                            }
//                            if(resyncOntConfigWithTFTPServer && deviceSupportsResyncOntConfigWithTFTPServer(ae_ont.getFirmwareVersion())){
//                                resyncOntConfigWithTFTPServer(ae_ont.getIpAddress(), ae_ont.getModel(), ae_ont.getFirmwareVersion());
//                            }
//                            AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();
//                            signal.setNetworkName(m_network);
//                            signal.setIpAddress(ae_ont.getIpAddress());
//                            //signal.setSnmpReadCommunity(ae_ont.getS);
//                            log.info("Sending AE discovery request for :" + m_network);
//                            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
//                            log.info("Sent AE discovery request for :" + m_network + " . Waiting to verify status...");
//                            verifyStatus();
//                            log.info("Status verification complete for " + m_network);
//                            return;
//                        } catch (Exception ex) {
//                            log.warn("Error sending discovery request. network: " + m_network, ex);
//                        }
//                    }
//                }
//                    String ont_id = m_network.substring("AEONT-".length());
//                    Ae_Ont ae_ont = AeONTProxy.getInstance().getAeONTFromDB(ont_id);
//                    if(ae_ont != null && OccamUtils.isOntDevice(ae_ont.getModel())){
//                        //resync on Occam ONT
//                        try {
//                            if(saveRunningConfigBeforeResync && deviceSupportsSaveRunningConfig(ae_ont.getFirmwareVersion())){
//                                sendOntCopyRunningConfigurationRequest(ae_ont.getIpAddress(), ae_ont.getModel(), ae_ont.getFirmwareVersion());
//                            }
//                            if(resyncOntConfigWithTFTPServer && deviceSupportsResyncOntConfigWithTFTPServer(ae_ont.getFirmwareVersion())){
//                                resyncOntConfigWithTFTPServer(ae_ont.getIpAddress(), ae_ont.getModel(), ae_ont.getFirmwareVersion());
//                            }
//                            AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();
//                            signal.setNetworkName(m_network);
//                            signal.setIpAddress(ae_ont.getIpAddress());
//                            //signal.setSnmpReadCommunity(ae_ont.getS);
//                            log.info("Sending AE discovery request for :" + m_network);
//                            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
//                            log.info("Sent AE discovery request for :" + m_network + " . Waiting to verify status...");
//                            verifyStatus();
//                            log.info("Status verification complete for " + m_network);
//                            return;
//                        } catch (Exception ex) {
//                            log.warn("Error sending discovery request. network: " + m_network, ex);
//                        }
//                    }
//                }


                BseriesDeviceSBSession session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(m_network);
			    log.info("Start B6 resync .");

	            BaseCMSNetwork network = session.getEMSNetwork();
	            
                if (network ==null || network.getConnectionState().intValue() < NetworkState.STATE_CONNECTED) {
                    log.warn("network[ "+m_network+" ] is not connected.");
                    updateNetworksFailureStatus(m_network, "network[ "+m_network+" ] is not connected.");
    	             return;
                }
                ipAddr = session.getEMSNetwork().getIPAddress1();

                version = session.getEMSNetwork() instanceof CalixB6Device ? ((CalixB6Device) session.getEMSNetwork()).getSWVersion() : "Unknown" ;
                eqptType = session.getEMSNetwork() instanceof CalixB6Device ? ((CalixB6Device) session.getEMSNetwork()).getModel() : "Unknown" ;
                Ping.setPingTimeout(OccamAlarmClosureQueueManager.RESYNC_EQUIPMENT_PING_TIMEOUT);
                log.debug("B6ResyncInventoryChildTask :: Node:" + ipAddr + " :: Pinging the Node");
                boolean isPingSuccess = Ping.ping(ipAddr, OccamAlarmClosureQueueManager.RESYNC_EQUIPMENT_PING_RETRIES);
                if (!isPingSuccess) {
                    log.debug("B6ResyncInventoryChildTask :: Node:" + ipAddr + " :: Ping Failed :: Device Not Reachable");
                    sendFailureResponse(ipAddr);
                    updateNetworksFailureStatus(m_network, "network[ "+m_network+" ]: ping failed. ");
                    return;
                }
                log.debug("B6ResyncInventoryChildTask :: Node:" + ipAddr + " :: Ping Successful :: Device Reachable");

                if(saveRunningConfigBeforeResync && deviceSupportsSaveRunningConfig(version)){
                    saveB6RunningConfig(ipAddr, eqptType, version);
                }

                resyncIpHost();
                session.doDiscovery();
            } catch (Exception ex) {
                log.warn("Error sending network backup request. network: " + m_parent.getNetwork(), ex);
            }
	        
	        log.info("finish send signal to B6 network for network backup .");
	        
	        verifyStatus(); //verify Status after send the signal.
	}

    private void resyncIpHost() {

        Attribute taskAttribute = new Attribute();
        taskProps = new Properties();
        taskProps.setProperty(ServiceMgmtConstants.USER_NAME, "root");//TODO
		taskProps.setProperty(ServiceMgmtConstants.PROTOCOL, ServiceMgmtConstants.RESYNC_SUB_ASSO);
        taskAttribute.setProperties(taskProps);
        Attribute [] attributes = new Attribute[1];
        attributes[0] = taskAttribute;
        
        ResyncSubscriberAssocConfigProvider provider = new ResyncSubscriberAssocConfigProvider();
        provider.configureDevice(ServiceMgmtConstants.SYNC_ASSO, attributes, new Device(ipAddr, "1099"));
	}

private void saveB6RunningConfig(String ipAddress, String equipmentType, String swRev){
        DevProperty devProperty = new DevProperty();
        log.debug("B6ResyncInventoryChildTask::saveB6RunningConfig:  Input to Dev property : ip - " + ipAddress + ", Eqpt Type - " + equipmentType + ", swVersion - " + swRev);
        devProperty.setProperty(DevProperty.DEVICE_IP_ATTR_NAME, ipAddress);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, equipmentType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, swRev);
        OccamProtocolRequestResponse reqRes = new OccamProtocolRequestResponse();
        reqRes.setOperationName(MediationOperationNames.SAVE_RUNNING_CONFIG_TO_STARTUP);
        reqRes.setParameter(MediationOperationNames.KEY_SAVE_RUNNING_CONFIG_TO_STARTUP, new Boolean(true));
        reqRes.setOperationType(OccamStaticDef.ACTION_MODIFY);
        reqRes.setModule(MediationDefines.SERVICE_MGMT);
        reqRes.setDeviceProperty(devProperty);
        MediationServerUtil.getInstance().performMediationRequest(reqRes);
        List errLst = reqRes.getErrorInfo();
        if(errLst != null && errLst.size() > 0) {
            log.debug("B6ResyncInventoryChildTask::saveB6RunningConfig - Failed for device: " + ipAddress);
        }
        log.debug("B6ResyncInventoryChildTask::saveB6RunningConfig - Success for device: " + ipAddress);
    }

    private void  sendOntCopyRunningConfigurationRequest(String ipAddress, String equipmentType, String swRev){
        log.info("B6ResyncInventoryChildTask.sendOntCopyRunningConfigurationRequest - Start: " + "ipAddress=" + ipAddress +", equipmentType=" + equipmentType + ",swRev=" + swRev);
        if (ipAddress != null && ipAddress.length() != 0) {
             try {
                 OccamProtocolRequestResponse ontSaveRunningConfigRequest = new OccamProtocolRequestResponse();
                 SNMPParams snmpParams = new SNMPParams();
                 try {
                    //snmpParams = (SNMPParams) AuthenticationUtil.getAuthProfile(ipAddress, AuthenticationUtil.AUTH_TYPE_SNMP);
                     Properties props = DefaultConfiguration.getDefaultConfig();
                    String version = props.getProperty("Snmp.version");
                    if (OccamStaticDef.SNMP_VERSION_V1.equals(version)) {
                        snmpParams = new SNMPParams();
                    } else {
                        snmpParams = new SNMPV2Params();
                    }
                    String readComm = props.getProperty("Snmp.readCommunity");
                    String writeComm = props.getProperty("Snmp.writeCommunity");
                    String port = props.getProperty("Snmp.port");
                    String timeout = props.getProperty("Snmp.timeout");
                    String retries = props.getProperty("Snmp.retries");
                    ((SNMPParams) snmpParams).setReadCommunity(readComm == null ?
                            "public" : readComm);
                    ((SNMPParams) snmpParams).setWriteCommunity(writeComm == null ?
                            "private" : writeComm);
                    (snmpParams).setPort(Integer.parseInt(port == null ?
                            "161" : port));
                    (snmpParams).setTimeout(Integer.parseInt(timeout == null ?
                            "5" : timeout));
                    (snmpParams).setRetries(Integer.parseInt(retries == null ?
                            "1" : retries));
                } catch (Exception e) {
                    log.debug("B6ResyncInventoryChildTask - sendOntCopyRunningToStartupSetRequest : Exception occured while getting SNMPV2Params for device : "+ ipAddress, e);
                    e.printStackTrace();
                }
                log.debug("SnmpUtil - sendOntCopyRunningToStartupSetRequest : SNMP Properties for the device : "+snmpParams.getProperties().toString());
                ontSaveRunningConfigRequest.setOperationName(MediationOperationNames.ONT_COPY_RUNNING_CONFIG_TO_STARTUP);
                ontSaveRunningConfigRequest.setParameter(ServiceMgmtConstants.IS_COPYRUNNING_TO_STARTUPCONFIG, ServiceMgmtConstants.TRUE);
                ontSaveRunningConfigRequest.setParameter(MediationOperationNames.SNMP_REQUEST_PARAMS, snmpParams.getProperties());
                DevProperty devProperty = new DevProperty();
                devProperty.setIPAddress(ipAddress);
                if(equipmentType != null) devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, equipmentType);
                if(swRev != null) devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, swRev);
                ontSaveRunningConfigRequest.setDeviceProperty(devProperty);
                MediationServerUtil mUtil = MediationServerUtil.getInstance();
                mUtil.performMediationRequest(ontSaveRunningConfigRequest);
                log.debug("B6ResyncInventoryChildTask.sendOntCopyRunningToStartupSetRequest : " + ontSaveRunningConfigRequest.getErrorInfo());
             }catch (Exception e){
                 e.printStackTrace();
             }
        }
        log.info("B6ResyncInventoryChildTask.sendOntCopyRunningConfigurationRequest - End");
    }

    public List resyncOntConfigWithTFTPServer(String ipAddress, String equipmentType, String swRev) {
        log.info("B6ResyncInventoryChildTask.resyncOntConfigWithTFTPServer - Start: " + "ipAddress=" + ipAddress +", equipmentType=" + equipmentType + ",swRev=" + swRev);
    	OccamProtocolRequestResponse occamProtocolRequestResponseObj = new OccamProtocolRequestResponse();

    	List errList = new ArrayList();

        String ocmIP=OccamUtils.getOCMIP();
    	DevProperty devProp = new DevProperty();
        devProp.setProperty(DevProperty.DEVICE_IP_ATTR_NAME, ocmIP);
        if(equipmentType != null) devProp.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, equipmentType);
        if(swRev != null) devProp.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, swRev);

        log.debug("B6ResyncInventoryChildTask::resyncOntConfigWithTFTPServer: IP" + ipAddress);

        occamProtocolRequestResponseObj.setDeviceProperty(devProp);
        occamProtocolRequestResponseObj.setOperationName(OccamStaticDef.AONT_RESYNCCONFIG_TFTP);
        occamProtocolRequestResponseObj.setOperationType(OccamTransactionTypeDef.ACTION_MODIFY);
        occamProtocolRequestResponseObj.setParameter(OccamStaticDef.OCM_IP, ocmIP);
        occamProtocolRequestResponseObj.setParameter(OccamStaticDef.KEY_ONT_IP, ipAddress);

        MediationServerUtil.getInstance().performMediationRequest(occamProtocolRequestResponseObj);
        errList=occamProtocolRequestResponseObj.getErrorInfo();

        log.debug("B6ResyncInventoryChildTask::resyncOntConfigWithTFTPServer: " + errList);
        log.info("B6ResyncInventoryChildTask.resyncOntConfigWithTFTPServer - End");
    	return errList;
    }

    private boolean deviceSupportsSaveRunningConfig(Properties eqProp){
        List versionList = Arrays.asList(OccamUtils.getVersionsForAutoSaveConfig().split(","));
        if (eqProp != null && !versionList.contains(eqProp.getProperty("entitySoftwareRev"))) {
            //save running config should be true in this case
            return true;
        }
        return false;
    }

     private boolean deviceSupportsSaveRunningConfig(String swRev){
        List versionList = Arrays.asList(OccamUtils.getVersionsForAutoSaveConfig().split(","));
        if (swRev != null && !versionList.contains(swRev)) {
            //save running config should be true in this case
            return true;
        }
        return false;
    }

    private boolean deviceSupportsResyncOntConfigWithTFTPServer(String swRev){
        if (swRev != null){
            try {
                String majorRev = swRev.substring(0, swRev.indexOf("."));
                int majorRevInt = Integer.parseInt(majorRev);
                if(majorRevInt > 3) return true;
            }catch(Exception e){

            }

        }
        return false;
    }

    private void sendFailureResponse(String ip) {
        //TODO: implement
    }

    private Properties getDevicePropsFromNode(String node) {
        OccamProtocolRequestResponse request = new
                OccamProtocolRequestResponse();
        DevProperty info = new DevProperty();
        info.setIPAddress(node);

        Properties snmpProp = null;
        //Get the configured auth profile for this node
        try {
            Authentication authParams = AuthenticationUtil.getAuthProfile(node,
                    AuthenticationUtil.AUTH_TYPE_SNMP);
            if (authParams != null) {
                snmpProp = authParams.getProperties();
            }
        } catch (Exception e) {
            log.error("Error while getting auth profile : " + e.getMessage());
        }

        //If device does not exist in DB, (auto-discovery) auth props will be null
        //get the default profile in this case
        if (snmpProp == null) {
            snmpProp = DefaultConfiguration.getDefaultConfig();
            DefaultConfiguration.trimSnmpProps(snmpProp);
        }
        log.debug("Auth params for resync : " + snmpProp);
        SNMPV2AuthData authObj = new SNMPV2AuthData();
        authObj.setProperties(snmpProp);
        Vector authVec = new Vector();
        authVec.add(authObj);

        info.getProperties().put(DevProperty.SNMP_AUTH_PARAMS, authVec);
        request.setDeviceProperty(info);
        request.setOperationName(OccamStaticDef.DISCOVERY_OPER);

        MediationServerUtil mUtil = MediationServerUtil.getInstance();
        mUtil.performMediationRequest(request);

        EquipmentData sysData = (EquipmentData) request.
                getParameter(OccamStaticDef.DEV_PROPERTY);
        return sysData.getProperties();
    }

	
	
	 public void updateNetworksFailureStatus(String networkName, String details) {
	        m_parent.onNetworkFailed(networkName, details);
	        this.m_status = ScheduleConstants.JOB_STATUS_FAILED;
	        DBHandler.getInstance().updateAuditLog(
	        		m_parent.getTaskId(), m_parent.getBseriesIPAddress(networkName), ScheduleConstants.JOB_STATUS_FAILED, details);

	 }
	 
	 public void updateNetworksSucceedStatus(String networkName) {
            m_parent.onNetworkSucceeded(networkName);
	        this.m_status = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
	        DBHandler.getInstance().updateAuditLog(
	        		m_parent.getTaskId(), m_parent.getBseriesIPAddress(networkName), ScheduleConstants.JOB_STATUS_SUCCESSFUL, ScheduleConstants.MSG_SUCCESSFUL);

	 }

	 private void verifyStatus() {
		 synchronized (this) {
			 if(m_status == null){
	        	try {
					this.wait(TIME_OUT);
				} catch (InterruptedException e) {
					log.error("Error verifying resynch status.", e);
				}
			 }
		 }
	     if (m_status == null) {
			updateNetworksFailureStatus(m_network,"Check resync status timed out");
	     }
	 }
}
