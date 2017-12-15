package com.calix.bseries.server.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.ae.server.task.AeSaveConfigChildTask;
import com.calix.bseries.server.boot.BseriesProcessHandler;
import com.calix.bseries.server.task.AeDiscoveryReqSignal;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
import com.calix.ems.server.process.CMSProcess;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.be.mo.SNMPParams;
import com.occam.ems.be.mo.SNMPV2Params;
import com.occam.ems.be.util.MediationServerUtil;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.DefaultConfiguration;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.mediation.transactionengine.common.OccamTransactionTypeDef;

public class AeResyncOcmManagedChildTask extends CMSScheduledTask implements
		DeviceChildTask {
	
	private static final Logger logger = Logger.getLogger(AeResyncOcmManagedChildTask.class);

	private AeSaveConfigChildTask m_parent;
	
	private Ae_Ont m_network = null;
    private String m_status = null;
    //TODO: Get strings from proper source
    private static final String ATTR_RESYNC_ONT_CONFIG_WITH_TFTP_SERVER = "ResyncOntConfigWithTFTP";
    private static final String ATTR_SAVE_CONFIGURATION = "SaveConfiguration";

    boolean resyncOntConfigWithTFTPServer = false;
    boolean saveRunningConfigBeforeResync = false;

    private String location;

    protected String networkName;
    protected String ipAddr;
    protected String eqptType;
    protected String version;
    protected String snmpReadCommunity;
    protected OccamProtocolRequestResponse reqRespObj;

    private static final long TIME_OUT = 5 * 60 * 1000L; // allow max 5 minutes
//    private static final long CHECK_INTERVAL = 10000L; // check every interval in sec
//    private static final long INIT_WAIT = 5000L;



	public AeResyncOcmManagedChildTask(){
		super();
	}
	
	public void setParent(Object task) {
		this.m_parent = (AeSaveConfigChildTask) task;
	}
	
	public void setNetwork(Ae_Ont network) {
        this.m_network = network;
        this.networkName = "AEONT-"+network.getStrAid();
        BseriesProcessHandler handle= (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
        handle.addTaskToMap(((GenericCMSAid)m_parent.getParent().getJobID()).getStrAid(),this.networkName, this);
    }
	
	public void setOption(Integer saveCfgBeforeSync, Integer syncCfgWithTftp){
		if(saveCfgBeforeSync != null && saveCfgBeforeSync == 1)
			saveRunningConfigBeforeResync = true;
		if(syncCfgWithTftp != null && syncCfgWithTftp == 1)
			resyncOntConfigWithTFTPServer = true;
	}

	
	public void run() {
	       if( m_network!= null && OccamUtils.isOntDevice(m_network.getModel())){
               //resync on Occam ONT
	       	 try {
				if (saveRunningConfigBeforeResync
						&& OccamUtils.isOccamAeONT(m_network.getModel())
						&& deviceSupportsSaveRunningConfig(m_network
								.getFirmwareVersion())) {
					sendOntCopyRunningConfigurationRequest(
							m_network.getIpAddress(), m_network.getModel(),
							m_network.getFirmwareVersion());
				}
				if (resyncOntConfigWithTFTPServer
						&& !OccamUtils.isOccamAeONT(m_network.getModel())) {
					List errList = resyncOntConfigWithTFTPServer(
							m_network.getIpAddress(), m_network.getModel(),
							m_network.getFirmwareVersion());
					if (errList != null && !errList.isEmpty()) {
						updateNetworksFailureStatus(networkName,
								errList.toString());
						return;
					} else {
						updateNetworksSucceedStatus(networkName);
						return;
					}
				} else if (!OccamUtils.isOccamAeONT(m_network.getModel())) {
					updateNetworkIgnoredStatus(networkName);
					return;
				}
	                 if(resyncOntConfigWithTFTPServer && deviceSupportsResyncOntConfigWithTFTPServer(m_network.getFirmwareVersion())){
	                    resyncOntConfigWithTFTPServer(m_network.getIpAddress(), m_network.getModel(), m_network.getFirmwareVersion());
	                 }
	                 AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();
	                 signal.setNetworkName(networkName);
	                 signal.setIpAddress(m_network.getIpAddress());
	                 //signal.setSnmpReadCommunity(m_network.getS);
	                 logger.info("Sending AE discovery request for :" + m_network.getaeontid());
	                 JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
	                 logger.info("Sent AE discovery request for :" + m_network.getaeontid()+ " . Waiting to verify status...");
	                 verifyStatus();
	                 logger.info("Status verification complete for " + m_network.getaeontid());
	                 return;
	            } 
	       	 catch (Exception ex) {
	                   logger.warn("Error sending discovery request. network: " + m_network.getaeontid(), ex);
	                   updateNetworksFailureStatus(networkName,"Error sending discovery request. network: " + m_network.getaeontid());
	            }
	       }
	}
	
	 public boolean deviceSupportsSaveRunningConfig(String swRev){
	        List versionList = Arrays.asList(OccamUtils.getVersionsForAutoSaveConfig().split(","));
	        if (swRev != null && !versionList.contains(swRev)) {
	            //save running config should be true in this case
	            return true;
	        }
	        return false;
	    }
	    
	    public boolean deviceSupportsResyncOntConfigWithTFTPServer(String swRev){
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
	    
	    public void  sendOntCopyRunningConfigurationRequest(String ipAddress, String equipmentType, String swRev){
	        logger.info("B6ResyncInventoryChildTask.sendOntCopyRunningConfigurationRequest - Start: " + "ipAddress=" + ipAddress +", equipmentType=" + equipmentType + ",swRev=" + swRev);
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
	                    logger.debug("B6ResyncInventoryChildTask - sendOntCopyRunningToStartupSetRequest : Exception occured while getting SNMPV2Params for device : "+ ipAddress, e);
	                    e.printStackTrace();
	                }
	                logger.debug("SnmpUtil - sendOntCopyRunningToStartupSetRequest : SNMP Properties for the device : "+snmpParams.getProperties().toString());
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
	                logger.debug("B6ResyncInventoryChildTask.sendOntCopyRunningToStartupSetRequest : " + ontSaveRunningConfigRequest.getErrorInfo());
	             }catch (Exception e){
	                 e.printStackTrace();
	             }
	        }
	        logger.info("B6ResyncInventoryChildTask.sendOntCopyRunningConfigurationRequest - End");
	    }
	    
	    public List resyncOntConfigWithTFTPServer(String ipAddress, String equipmentType, String swRev) {
	        logger.info("B6ResyncInventoryChildTask.resyncOntConfigWithTFTPServer - Start: " + "ipAddress=" + ipAddress +", equipmentType=" + equipmentType + ",swRev=" + swRev);
	    	OccamProtocolRequestResponse occamProtocolRequestResponseObj = new OccamProtocolRequestResponse();

	    	List errList = new ArrayList();

	        String ocmIP=OccamUtils.getOCMIP();
	    	DevProperty devProp = new DevProperty();
	        devProp.setProperty(DevProperty.DEVICE_IP_ATTR_NAME, ocmIP);
	        if(equipmentType != null) devProp.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, equipmentType);
	        if(swRev != null) devProp.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, swRev);

	        logger.debug("B6ResyncInventoryChildTask::resyncOntConfigWithTFTPServer: IP" + ipAddress);

	        occamProtocolRequestResponseObj.setDeviceProperty(devProp);
	        occamProtocolRequestResponseObj.setOperationName(OccamStaticDef.AONT_RESYNCCONFIG_TFTP);
	        occamProtocolRequestResponseObj.setOperationType(OccamTransactionTypeDef.ACTION_MODIFY);
	        occamProtocolRequestResponseObj.setParameter(OccamStaticDef.OCM_IP, ocmIP);
	        occamProtocolRequestResponseObj.setParameter(OccamStaticDef.KEY_ONT_IP, ipAddress);

	        MediationServerUtil.getInstance().performMediationRequest(occamProtocolRequestResponseObj);
	        errList=occamProtocolRequestResponseObj.getErrorInfo();

	        logger.debug("B6ResyncInventoryChildTask::resyncOntConfigWithTFTPServer: " + errList);
	        logger.info("B6ResyncInventoryChildTask.resyncOntConfigWithTFTPServer - End");
	    	return errList;
	    }
	
	    
	 public void updateNetworksSucceedStatus(String networkName) {
	        m_parent.onNetworkComplete(networkName, null, 0);
		    this.m_status = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
	 }
	 
	 public void updateNetworksFailureStatus(String networkName, String details) {
	       	m_parent.onNetworkComplete(networkName, details, 1);
	        this.m_status = ScheduleConstants.JOB_STATUS_FAILED;
	 }
 
	 public void updateNetworkIgnoredStatus(String networkName) {
		 m_parent.onNetworkComplete(networkName, null, 2);
		 
	 }
	 

	 private void verifyStatus() {
		 synchronized (this) {
			 if(m_status == null){
	        	try {
					this.wait(TIME_OUT);
				} catch (InterruptedException e) {
					logger.error("Error verifying resynch status.", e);
				}
			 }
		 }
	     if (m_status == null) {
			updateNetworksFailureStatus(m_network.getNetwork(),"Check resync status timed out");
		 }
	 }

}
