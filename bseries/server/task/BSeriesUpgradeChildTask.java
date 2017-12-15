package com.calix.bseries.server.task;

import org.apache.log4j.Logger;

import com.calix.bseries.server.boot.BseriesProcessHandler;
import com.calix.bseries.server.dbmodel.B6ScheduledTask;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.connection.NetworkState;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
import com.calix.ems.server.process.CMSProcess;
import com.calix.ex.server.tasks.ExUpgradeTaskUtil;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;

import java.rmi.RemoteException;
import java.util.Properties;


/**
 * B6 Upgrade Schedule Task class Added by James Wang
 * 20111124
 */

public class BSeriesUpgradeChildTask extends CMSScheduledTask implements DeviceChildTask {
    private static final Logger log = Logger.getLogger(BSeriesUpgradeChildTask.class);

    private B6ScheduledTask m_parent;
    private String m_network;
    private BseriesDeviceSBSession m_session;
    private BaseCMSNetwork network;
    private static final String ATTR_SAVE_CONF_FLAG = "SaveConfigurationFlag";
    private static final String ATTR_RELOAD_DEVICE_FLAG = "ReloadDeviceFlag";
//    private String m_status = null;

//    private static final long TIME_OUT = 5 * 60 * 1000L; // allow max 5 minutes

    // Flag to indicate continue even if error occurs. Default true
    public boolean m_ForceUpgradeFlag = true;


    public BSeriesUpgradeChildTask() {
        super();
    }

    public void setParent(Object task) {
        this.m_parent = (B6ScheduledTask) task;
    }

    public int getParentNetworkCounts(){
    	//homer
//        return this.m_parent.getNetworkCount();
    	return 1;
    }
    
    public void setNetwork(String network) {
        this.m_network = network;
        BseriesProcessHandler handle = (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
        handle.addUpgradeTaskToMap(((GenericCMSAid) m_parent.getJobID()).getStrAid(), network, this);
    }

    public boolean isForceUpgradeFlag() {
        return m_ForceUpgradeFlag;
    }
    
  public void run() {
      
//      if(m_parent.isAborted()) {
//          m_parent.onNetworkAborted(m_network);
//          return;
//      }
	  boolean  upgradeCanbeDone=false;
       try {
   			//Added by Homer: Audit log
   			DBHandler.getInstance().addAuditLog(m_parent.getTaskId(),
   				m_parent.getTaskName(), m_parent.getTaskType(),
   				m_parent.getBseriesIPAddress(m_network), m_parent.getCreatedBy(),
				m_parent.getDescription());
   		
//           m_session = SessionManager.getInstance().getSouthboundSession(m_network);
           m_session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(m_network);
           log.info("Start to B6 network Upgrade .");
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
               upgradeCanbeDone = DBHandler.getInstance().isModelMatches(eqptType,m_parent.getSourceDirectoryPath());
              if(upgradeCanbeDone){
            	//To be changed to set attributes
            	  String orgPath;
            	  String ftpPath= ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, ServiceMgmtConstants.FTP_B6_IMAGE_PATH)+m_parent.getSourceDirectoryPath();
          		  orgPath = OccamUtils.getDefaultFTPValues().get(OccamStaticDef.CMS_FTP_PATH);
          		  if(orgPath!=null){
          			if(orgPath.endsWith("/"))
          				ftpPath = orgPath+"b6/fw/"+m_parent.getSourceDirectoryPath();
          			else
          				ftpPath = orgPath+"/b6/fw/"+m_parent.getSourceDirectoryPath();
          		  }
                  BSeriesNetworkUpgradeResponseSignal signal = new BSeriesNetworkUpgradeResponseSignal();
                  signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_UPGRADE_REQ);
                  signal.setNetworkName(this.m_network);
                  
                  signal.setFtpIP(m_parent.getSourceFtpServer());
                  signal.setFtpPassword(m_parent.getSourceFtpPassword());
                  signal.setFtpUser(m_parent.getSourceFtpUser());
                  signal.setPath(ftpPath);
                  
                  signal.setEquipType(eqptType);
                  signal.setVersion(version);
                  
                  //parameters' passing
                  String addtlInfo = m_parent.getAdditionalInfo();
                  Properties info = CommonUtil.getProperties(addtlInfo);
                  String saveConfiguration = info.getProperty(OccamStaticDef.KEY_SAVE_CONFGN);
                  String reloadDevice = info.getProperty(OccamStaticDef.KEY_RELOAD_DEVICE);
                  signal.setSaveConfigurationFlag(Boolean.valueOf(saveConfiguration));
                  signal.setReloadDeviceFlag(Boolean.valueOf(reloadDevice));
                 
//                  Set flag of forcing upgrade
                  this.m_ForceUpgradeFlag = false;//(m_parent.getForceUpgradeFlag().intValue() == 1) ? true : false;
                  
                  signal.setIpAddress(network.getIPAddress1());
                  signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());
                  signal.setSnmpReadCommunity( ((CalixB6Device)network).getReadCommunity());
                  JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
              }else{
            	  String compatibleErroeMessage ="OS image entry \""+m_parent.getSourceDirectoryPath()+"\" not compatible with model \""+eqptType+"\"";
            	  onFailed(compatibleErroeMessage);
              }
              
              
          } catch (Exception ex) {
              log.warn("Error sending network Upgrade request. network: " + m_parent.getNetwork(), ex);
          }
          
          log.info("finish send signal to B6 network for network Upgrade.");
          
          //Need do verify?
          if(upgradeCanbeDone)
        	  verifyStatus(); //verify Status after send the signal.
  }
  
  private void verifyStatus() {
      (new B6UpgradePostCheck()).run();
  }
  
  public void onSucceededAndReset() {
     // m_parent.onNetworkSucceededAndReset(m_network);
  }

  public void onSucceeded(BSeriesNetworkUpgradeResponseSignal signal) {
      String version = signal.getVersion();
      if (version != null){
                    
          ICMSDatabase database = null;
          DbTransaction txn = null;
          try {              
        
              database = DatabaseManager.getCMSDatabase();
              txn = database.getTransaction("ems", DbTransaction.UPDATE);
              txn.begin();
              
              ((CalixB6Device)network).setSWVersion(version);              
              network.doUpdate(txn);
              
              txn.commit();
              txn  = null;              
              
          } catch (Exception ex) {
              log.warn("Unable to update network for. network name: " + m_network, ex);
          } finally {
              if (txn != null && txn.isActive())
                  txn.abort();
          }
      }
      m_parent.onNetworkSucceeded(m_network);
      DBHandler.getInstance().updateAuditLog(
    		  m_parent.getTaskId(), m_parent.getBseriesIPAddress(m_network), ScheduleConstants.JOB_STATUS_SUCCESSFUL, ScheduleConstants.MSG_SUCCESSFUL);

  }
  
  public void onCancel(){
      m_parent.cancel();
      DBHandler.getInstance().updateAuditLog(
    		  m_parent.getTaskId(), m_parent.getBseriesIPAddress(m_network), ScheduleConstants.JOB_STATUS_CANCELLED, ScheduleConstants.MSG_CANCEL);

  }
  
  public void onFailed(String msg) {
      log.error(msg);
      m_parent.onNetworkFailed(m_network, msg);
      DBHandler.getInstance().updateAuditLog(
    		  m_parent.getTaskId(), m_parent.getBseriesIPAddress(m_network), ScheduleConstants.JOB_STATUS_FAILED, msg);

  }

  //To be changed..
  private class B6UpgradePostCheck extends java.util.TimerTask implements Runnable {
      static final long TIME_OUT = 5 * 60 * 60 * 1000L;
      static final long NO_STATUS_TIME_OUT = 60 * 60 * 1000L;
      static final int CHECK_INTERVAL = 120;
      static final int INIT_WAIT = 30;

      public void run() {
          
          try {
              long start = System.currentTimeMillis();
              long lastInProgress = start;
              ExUpgradeTaskUtil.sleep(INIT_WAIT);
              while (true) {
                  try {
                      //Not in B6...
//                      int status = ExUpgradeTaskUtil.getUpgradeOverallStatus(m_session);
//                      if (status > 0) {
//                          if (status == ExUpgradeTaskUtil.UPGRADE_OVERALL_STATUS_SUCCESS) {
//                              log.info("File transfer completed for network:" + m_network);
//                              onSucceededAndReset();
                              break;
//                          } else if (status == ExUpgradeTaskUtil.UPGRADE_OVERALL_STATUS_FAIL) {
//                              log.error("File transfer failed for network:" + m_network);
//                              onFailed("File transfer failed for network:" + m_network);
//                              break;
//                          } else if (status == ExUpgradeTaskUtil.UPGRADE_OVERALL_STATUS_IN_PROGRESS) {
//                              lastInProgress = System.currentTimeMillis();
//                          }
//                      }
                  } catch (Throwable th) {
                      log.error("To get upgrade status unsuccessfully: " + m_network, th);
                  }
                  ExUpgradeTaskUtil.sleep(CHECK_INTERVAL);
                  long now = System.currentTimeMillis();
                  if ( (now - lastInProgress) > NO_STATUS_TIME_OUT || (now - start) > TIME_OUT) {
                      onFailed("File Transfer: File ssTransfer timeout.");
                      break;
                  }
              }
          } catch (Throwable t) {
              onFailed("File Transfer: File Transfer Status Verification thread interrupted:" + t.getMessage());
          }
      }
  }
  
//    public void updateNetworksFailureStatus(String networkName, String details) {
//        m_parent.onNetworkFailed(networkName, details);
////        m_parent.getFailedStatus().put(networkName, details);
//        this.m_status = ScheduleConstants.JOB_STATUS_FAILED;
//    }
//
//    public void updateNetworksSucceedStatus(String networkName) {
////        m_parent.onNetworkSucceeded(networkName);
//        this.m_status = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
//    }
//
//    public void taskComplete(String networkName) {
//        m_parent.postNetworkComplete(m_network, m_status);
//    }
//
//    private void verifyStatus() {
//        synchronized (this) {
//            if (m_status == null) {
//                try {
//                    this.wait(TIME_OUT);
//                } catch (InterruptedException e) {
//                    log.error("Error verifying backup status.", e);
//                }
//            }
//        }
//        if (m_status == null) {
//            updateNetworksFailureStatus(m_network,
//                    "Check backup status timed out");
//            taskComplete(m_network);
//        }
//    }
 
}