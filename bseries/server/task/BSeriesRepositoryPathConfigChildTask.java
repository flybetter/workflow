package com.calix.bseries.server.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

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
import com.calix.system.server.dbmodel.BaseCMSNetwork;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.be.app.fault.GponOntInfoUpdateHandler;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;

public class BSeriesRepositoryPathConfigChildTask extends CMSScheduledTask implements DeviceChildTask {
	  private static final Logger log = Logger.getLogger(BSeriesUpgradeChildTask.class);
	  
	
	private B6ScheduledTask m_parent;
    private String m_network;
    private BseriesDeviceSBSession m_session;
    
    
    public BSeriesRepositoryPathConfigChildTask() {
        super();
    }
	@Override
	public void setParent(Object task) {
		// TODO Auto-generated method stub
		 this.m_parent = (B6ScheduledTask) task;
		
	}
	public int getParentNetworkCounts(){    	
        return this.m_parent.getNetworkCount();    	
    }
	
	 public void setNetwork(String network) {
	        this.m_network = network;
	        log.info("B6 Gpon Upgrade Task network name : "+this.m_network);
	        log.info("B6 Gpon Upgrade Task JOB Id : "+((GenericCMSAid) m_parent.getJobID()).getStrAid());
	        BseriesProcessHandler handle = (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
	        handle.addRepositoryPathConfigTaskToMap(((GenericCMSAid) m_parent.getJobID()).getStrAid(), network, this);
	    }
	 public void run() {
	      
//	      if(m_parent.isAborted()) {
//	          m_parent.onNetworkAborted(m_network);
//	          return;
//	      }
//	      

		 try {
	   			//CMS-7896 - support Audit log
				DBHandler.getInstance().addAuditLog(m_parent.getTaskId(),
						m_parent.getTaskName(), m_parent.getTaskType(),
						m_parent.getBseriesIPAddress(m_network), m_parent.getCreatedBy(),
						m_parent.getDescription());

	           m_session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(m_network);
	           log.info("Start to B6 repository path configuration .");
	              if(m_session==null ||m_session.isBSeriesMgrProcessDown()) {
	                  log.warn("BSeries manager process down.");
	                  onFailed("BSeries manager process down.");
//	                  m_parent.onNetworkFailed(m_network, "BSeries manager process down.");
	                  return;
	              }
	              
	              BaseCMSNetwork network = m_session.getEMSNetwork();
	              
	              if (network ==null || network.getConnectionState().intValue() < NetworkState.STATE_CONNECTED) {
	                  log.warn("network[ "+m_network+" ] is not connected.");
	                  onFailed("network[ "+m_network+" ] is not connected.");
//	                  m_parent.onNetworkFailed(m_network, "network[ "+m_network+" ] is not connected.");
	                   return;
	                }
	              String eqptType = ((CalixB6Device)network).getModel();
	              String version = ((CalixB6Device)network).getSWVersion();
	              if(eqptType== null || version==null){
	                  log.warn("Get eqptType or version failed");
	                  onFailed("Get eqptType or version failed");
//	                  m_parent.onNetworkFailed(m_network, "Get eqptType or version failed");
	                  return;
	              }
	              if(!OccamUtils.isSupportAutoUpgrade(version)){
	            	  log.warn("Repository path configuration resync only support GPON OLT firmware above 7.1");
	                  onFailed("Repository path configuration resync only support GPON OLT firmware above 7.1");
//	                  m_parent.onNetworkFailed(m_network, "Repository path configuration resync only support GPON OLT firmware above 7.1");
	                  return;
	              }
	              //To be changed to set attributes
	              BSeriesNetworkUpgradeResponseSignal signal = new BSeriesNetworkUpgradeResponseSignal();
	              signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_REPOSITORY_PATH_CONFIG_REQ);
	              signal.setNetworkName(this.m_network);
	              
	              signal.setFtpIP(m_parent.getSourceFtpServer());
	              signal.setFtpPassword(m_parent.getSourceFtpPassword());
	              signal.setFtpUser(m_parent.getSourceFtpUser());
	              signal.setPath(m_parent.getSourceDirectoryPath());
	              
	              signal.setEquipType(eqptType);
	              signal.setVersion(version);
					String ftp_ip = m_parent.getSourceFtpServer();
					String ftp_user = m_parent.getSourceFtpUser();
					String ftp_password =m_parent.getSourceFtpPassword();
					String ftp_path = m_parent.getSourceDirectoryPath();
	              
	              //parameters' passing
	              String addtlInfo = m_parent.getAdditionalInfo();
	              log.info("Start to B6 network Upgrade aditional information : "+addtlInfo);
	              Properties info = CommonUtil.getProperties(addtlInfo); 
	              log.info("Start to B6 network Upgrade aditional information Properties: "+info.toString());
	              
	              String repositoryPath= (String)info.getProperty(OccamStaticDef.GPONOLT_REPOSITRY_PATH);
	              log.info("Start to B6 network Upgrade aditional information repositoryPath: "+repositoryPath);
	              if(!repositoryPath.startsWith("ftp://") && repositoryPath.indexOf("@")<0)
	            	  repositoryPath = "ftp://" + ftp_user + ":" + ftp_password + "@" + ftp_ip + "/" + repositoryPath;
	              log.info("Start to B6 network Upgrade aditional information repositoryPath: "+repositoryPath);
	              signal.setIpAddress(network.getIPAddress1());
	              signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());
	              signal.setSnmpReadCommunity( ((CalixB6Device)network).getReadCommunity());
	              signal.setGponRepositoryPath(repositoryPath);
	              JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
	          } catch (Exception ex) {
	              log.warn("Error sending network Upgrade request. network: " + m_parent.getNetwork(), ex);
	          }
	          
	          log.info("finish send signal to B6 network for network Upgrade.");
	          
	          //Need do verify?
	          //verifyStatus(); //verify Status after send the signal.
	  }
	 public void onSucceeded(BSeriesGponOntUpgradeResponseSignal signal) {
	      //OccamProtocolRequestResponse resObj = signal.getResObj();
	     /* HashMap updateOntMap = (HashMap)resObj.getParameter(m_network);
	      if (updateOntMap != null){
	          Iterator itr = updateOntMap.keySet().iterator();
	          while (itr.hasNext()){
	              String key = (String)itr.next();
	              Properties updateOntProp = (Properties)updateOntMap.get(key);
	              GponOntInfoUpdateHandler.getInstance().addOntToQueueForUpdate(updateOntProp);
	          }
	      }*/
		 log.info("onSucceeded method called");
		 log.info("onSucceeded method called : onSucceeded : "+signal.getError());
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
		  log.info("onFailed method called");
			 log.info("onFailed method called : msg  : "+msg);
	      log.error(msg);
	      m_parent.onNetworkFailed(m_network, msg);
	        DBHandler.getInstance().updateAuditLog(m_parent.getTaskId(), m_parent.getBseriesIPAddress(m_network), ScheduleConstants.JOB_STATUS_FAILED, msg);

	  }

	  

}
