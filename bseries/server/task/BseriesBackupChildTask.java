package com.calix.bseries.server.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.calix.bseries.server.boot.BseriesProcessHandler;
import com.calix.bseries.server.dbmodel.B6ScheduledTask;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.connection.NetworkState;
import com.calix.ems.server.dbmodel.C7BackupTask;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
import com.calix.ems.server.process.CMSProcess;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.BaseCMSNetwork;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.be.util.MediationServerUtil;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.MediationDefines;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.mo.DeviceAudit;
import com.occam.ems.common.mo.servicemanagement.BLCServiceAudit;
import com.occam.ems.common.mo.servicemanagement.CustomUserObject;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtDBConstants;
import com.occam.ems.server.DataBaseAPI;
import com.occam.ems.server.TaskStatusAuditGetter;

public class BseriesBackupChildTask  extends CMSScheduledTask implements DeviceChildTask{

	private static final Logger log = Logger.getLogger(BseriesBackupChildTask.class);

	private C7BackupTask m_parent;
	private String m_network = null;
    private String m_status = null;

    private static final long TIME_OUT = 5 * 60 * 1000L; // allow max 5 minutes
//    private static final long CHECK_INTERVAL = 10000L; // check every interval in sec
//    private static final long INIT_WAIT = 5000L;

	public BseriesBackupChildTask(){
		super();
	}
	
	public void setParent(Object task) {
		this.m_parent = (C7BackupTask) task;
	}
	
	
	public void setNetwork(String network) {
        this.m_network = network;
        BseriesProcessHandler handle= (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
        handle.addTaskToMap(((GenericCMSAid)m_parent.getJobID()).getStrAid(),network, this);
    }
	public void run() {
		 try {
			 BseriesDeviceSBSession session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(m_network);
			 
			//CMS-7896 - support Audit log
			DBHandler.getInstance().addAuditLog(m_parent.getTaskId(), "",
					"Backup", getBseriesIPAddress(m_network),
					m_parent.getCreatedBy(),
					"");
			 
			 log.info("Start to B6 network backup .");
	            if(session==null ||session.isBSeriesMgrProcessDown()) {
	                log.warn("BSeries manager process down.");
	                updateNetworksFailureStatus(m_network, "BSeries manager process down.");
	                taskComplete(m_network);
	                return;
	            }
	            
	            BaseCMSNetwork network = session.getEMSNetwork();
	            
                if (network ==null || network.getConnectionState().intValue() < NetworkState.STATE_CONNECTED) {
                    log.warn("network[ "+m_network+" ] is not connected.");
                    updateNetworksFailureStatus(m_network, "network[ "+m_network+" ] is not connected.");
    	            taskComplete(m_network);
    	             return;
                  }
                String ipAddr = network.getIPAddress1();
                String eqptType = ((CalixB6Device)network).getModel();
                String version = ((CalixB6Device)network).getSWVersion();
			if ((m_parent.getisSaveConfig() == null
					|| (m_parent.getisSaveConfig() != null && m_parent.getisSaveConfig() == 1 ))
					&& deviceSupportsSaveRunningConfig(version)){
                    saveB6RunningConfig(ipAddr, eqptType, version);
                }
                if(eqptType== null || version==null){
                	log.warn("Get eqptType or version failed");
                    updateNetworksFailureStatus(m_network, "Get eqptType or version failed");
    	            taskComplete(m_network);
    	            return;
                }
	            
	            BSeriesNetworkBackupResponseSignal signal = new BSeriesNetworkBackupResponseSignal();
	            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_BACKUP_REQ);
	            signal.setNetworkName(this.m_network);
	            signal.setIp(m_parent.getIp());
	            signal.setFtpPassword(m_parent.getPass());
	            signal.setFtpUser(m_parent.getFTPUser());
	            signal.setPath(m_parent.getPath());
	            signal.setEquipType(eqptType);
	            signal.setVersion(version);
	            
				signal.setIpAddress(network.getIPAddress1());
	            signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());
	            signal.setSnmpReadCommunity( ((CalixB6Device)network).getReadCommunity());
	            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
	        } catch (Exception ex) {
	            log.warn("Error sending network backup request. network: " + m_parent.getNetwork(), ex);
	        }
	        
	        log.info("finish send signal to B6 network for network backup .");
	        
	        verifyStatus(); //verify Status after send the signal.
	}
	
	public void updateNetworksFailureStatus(String networkName, String details) {
	        m_parent.getFailedStatus().put(networkName, details);
	        this.m_status = ScheduleConstants.JOB_STATUS_FAILED;
	        DBHandler.getInstance().updateAuditLog(
	        		m_parent.getTaskId(), getBseriesIPAddress(networkName), ScheduleConstants.JOB_STATUS_FAILED, details);
	 }
	 
	public void updateNetworksSucceedStatus(String networkName) {
	        this.m_status = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
	        DBHandler.getInstance().updateAuditLog(
	        		m_parent.getTaskId(), getBseriesIPAddress(networkName), ScheduleConstants.JOB_STATUS_SUCCESSFUL, ScheduleConstants.MSG_SUCCESSFUL);
	 }
	 public void taskComplete(String networkName) {
		 m_parent.postNetworkComplete(m_network, m_status);
	 }
	 
	 private void verifyStatus() {
		 synchronized (this) {
			 if(m_status == null){
	        	try {
					this.wait(TIME_OUT);
				} catch (InterruptedException e) {
					log.error("Error verifying backup status.", e);
				}
			 }
		 }
	     if (m_status == null) {
			updateNetworksFailureStatus(m_network,"Check backup status timed out");
			taskComplete(m_network);
		}

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
	            log.debug("B6Backupchildtask::saveB6RunningConfig - Failed for device: " + ipAddress);
	        }
	        log.debug("B6Backupchildtask::saveB6RunningConfig - Success for device: " + ipAddress);
	    }

     private boolean deviceSupportsSaveRunningConfig(String swRev){
        List versionList = Arrays.asList(OccamUtils.getVersionsForAutoSaveConfig().split(","));
        if (swRev != null && !versionList.contains(swRev)) {
            //save running config should be true in this case
            return true;
        }
        return false;
    }
   
     /**
      * Get Ip Address using network for Bseries device
      * @param note
      */
 	public String getBseriesIPAddress(String m_network) {
		String ipAddress = "";
		try {
			SouthboundSession m_session = SessionManager.getInstance().getSouthboundSession(m_network);
			ipAddress = m_session.getEMSNetwork().getIPAddress1();
		} catch (Throwable e) {
			log.warn("BseriesBackupChildTask.getBseriesIPAddress: Exception when get ip from m_network: " + m_network + ", Exception: " + e);
		}
		return ipAddress;
	}
}
