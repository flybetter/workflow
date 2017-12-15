package com.calix.bseries.server.task;

import org.apache.log4j.Logger;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.ae.server.session.AeONTProxy;
import com.calix.bseries.server.boot.BseriesProcessHandler;
import com.calix.bseries.server.dbmodel.B6ScheduledTask;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.connection.NetworkState;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
import com.calix.ems.server.process.CMSProcess;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;

import java.util.Properties;

/**
 * B6 Reload Schedule Task class
 * 20111124
 */

public class BSeriesReloadChildTask extends CMSScheduledTask implements DeviceChildTask {
    private static final Logger log = Logger.getLogger(BSeriesReloadChildTask.class);

    private B6ScheduledTask m_parent;
    private String m_network;
    private BseriesDeviceSBSession m_session;
    private BaseCMSNetwork network;
    private static final long TIME_OUT = 10 * 60 * 1000L; // allow max 10 minutes
    private String m_status = null;

    // Flag to indicate continue even if error occurs. Default true
   // public boolean m_ForceUpgradeFlag = true;


    public BSeriesReloadChildTask() {
        super();
    }

    public void setParent(Object task) {
        this.m_parent = (B6ScheduledTask) task;
    }

    public int getParentNetworkCounts(){
    	
        return this.m_parent.getNetworkCount();
    	//return 1;
    }
    
    public void setNetwork(String network) {
        this.m_network = network;
        BseriesProcessHandler handle = (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
        handle.addReloadTaskToMap(((GenericCMSAid) m_parent.getJobID()).getStrAid(), network, this);
        // TBD add the Task to a map to be handled later. 
        
    }

  public void run() {

       try {
   			//CMS-7896 - support Audit log
   			DBHandler.getInstance().addAuditLog(m_parent.getTaskId(),
   				m_parent.getTaskName(), m_parent.getTaskType(),
   				m_parent.getBseriesIPAddress(m_network), m_parent.getCreatedBy(),
   				m_parent.getDescription());
   		
    	   if(m_network != null && m_network.startsWith("AEONT-")){
    		   log.info("Start to AEONT network Reload .");
	    	   String ont_id = m_network.substring("AEONT-".length());
	           Ae_Ont ae_ont = AeONTProxy.getInstance().getAeONTFromDB(ont_id);
	           if(ae_ont != null && OccamUtils.isOntDevice(ae_ont.getModel())){
	        	   try {
					BSeriesReloadResponseSignal signal = new BSeriesReloadResponseSignal();
					log.info("AEONT IP " + ae_ont.getIpAddress());
					   
					   signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());					   
					   signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_RELOAD_REQ);
					   signal.setNetworkName(this.m_network);
					   signal.setIpAddress(ae_ont.getIpAddress());
					   signal.setEquipType(ae_ont.getModel());
					   signal.setVersion(ae_ont.getFirmwareVersion());
					   
					   String addtlInfo = m_parent.getAdditionalInfo();
					   Properties info = CommonUtil.getProperties(addtlInfo);
					   String restoreConfig = info.getProperty(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT);
					   signal.setRestoreConfig(restoreConfig);
					   signal.setTaskName(m_parent.getTaskName());
					   log.info("Sending AE reload request for :" + m_network);
					   JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
					   verifyStatus();
					   
					  return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
    	   }
//           m_session = SessionManager.getInstance().getSouthboundSession(m_network);
           m_session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(m_network);
           log.info("Start to B6 network Reload .");
              if(m_session==null ||m_session.isBSeriesMgrProcessDown()) {
                  log.warn("BSeries manager process down.");
                  m_parent.onNetworkFailed(m_network, "BSeries manager process down.");
//                  taskComplete(m_network);
                  return;
              }
              
              network = m_session.getEMSNetwork();
              
              if (network ==null || network.getConnectionState().intValue() < NetworkState.STATE_CONNECTED) {
                  log.warn("network[ "+m_network+" ] is not connected.");
                  m_parent.onNetworkFailed(m_network, "network[ "+m_network+" ] is not connected.");
//                  taskComplete(m_network);
                   return;
                }
              String eqptType = ((CalixB6Device)network).getModel();
              String version = ((CalixB6Device)network).getSWVersion();
              if(eqptType== null || version==null){
                  log.warn("Get eqptType or version failed");
                  m_parent.onNetworkFailed(m_network, "Get eqptType or version failed");
//                  taskComplete(m_network);
                  return;
              }
              
              BSeriesReloadResponseSignal signal = new BSeriesReloadResponseSignal();
              
              signal.setIpAddress(network.getIPAddress1());
              signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());
              signal.setSnmpReadCommunity( ((CalixB6Device)network).getReadCommunity());
              signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_RELOAD_REQ);
              signal.setNetworkName(this.m_network);
              signal.setEquipType(eqptType);
              signal.setVersion(version);
              
              String addtlInfo = m_parent.getAdditionalInfo();
              Properties info = CommonUtil.getProperties(addtlInfo);
              String restoreConfig = info.getProperty(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT);
              signal.setRestoreConfig(restoreConfig);
              signal.setTaskName(m_parent.getTaskName());
              
              JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
            //  sendReloadRequest(m_parent.getTaskName(),restoreConfig);
              verifyStatus();//verify Status after send the signal.
              log.info("Status verification complete for " + m_network);
          } catch (Exception ex) {
              log.warn("Error sending Network Reload request. network: " + m_parent.getNetwork(), ex);
          }
          
          log.info("finish send signal to B6 Network for Network Reload.");
          
          //Need do verify?
          //verifyStatus(); //verify Status after send the signal.
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
  
  public void onCancel(){
      m_parent.cancel();
      DBHandler.getInstance().updateAuditLog(
    		  m_parent.getTaskId(), m_parent.getBseriesIPAddress(m_network), ScheduleConstants.JOB_STATUS_CANCELLED, ScheduleConstants.MSG_CANCEL);

  }
  
  
  private void verifyStatus() {
		 synchronized (this) {
			 if(m_status == null){
	        	try {
					this.wait(TIME_OUT);
				} catch (InterruptedException e) {
					log.error("Error verifying reload status.", e);
				}
			 }
		 }
	     if (m_status == null) {
			updateNetworksFailureStatus(m_network,"Check reload status timed out");
		 }
	 }
}