package com.calix.bseries.server.task;

import com.occam.ems.be.mo.OccamGponOnt;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.server.DataBaseAPI;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.calix.ex.server.tasks.ExUpgradeTaskUtil;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.be.app.discovery.DiscoveryUtil;
import com.occam.ems.be.app.fault.GponOntInfoUpdateHandler;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.defines.AttributeNames;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;

import java.util.Properties;

/**
 * B6 Upgrade Schedule Task class Added by James Wang 
 * 20111124
 */

public class BSeriesGponUpgradeChildTask extends CMSScheduledTask implements DeviceChildTask {
    private static final Logger log = Logger.getLogger(BSeriesUpgradeChildTask.class);

    private B6ScheduledTask m_parent;
    private String m_network;
    private BseriesDeviceSBSession m_session;
    
    private static final String ATTR_SAVE_CONF_FLAG = "SaveConfigurationFlag";
    private static final String ATTR_RELOAD_DEVICE_FLAG = "ReloadDeviceFlag";
    private  String GPONONT_UPGRADE_ERROR_MSG="";
//    private String m_status = null;

//    private static final long TIME_OUT = 5 * 60 * 1000L; // allow max 5 minutes

    // Flag to indicate continue even if error occurs. Default true
    public boolean m_ForceUpgradeFlag = true;


    public BSeriesGponUpgradeChildTask() {
        super();
    }

    public void setParent(Object task) {
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
        handle.addGponUpgradeTaskToMap(((GenericCMSAid) m_parent.getJobID()).getStrAid(), network, this);
    }

    public boolean isForceUpgradeFlag() {
        return m_ForceUpgradeFlag;
    }
    
  public void run() {
      
//      if(m_parent.isAborted()) {
//          m_parent.onNetworkAborted(m_network);
//          return;
//      }
//      

	  try {
		  	//CMS-7896 - support Audit log
			DBHandler.getInstance().addAuditLog(m_parent.getTaskId(),
					m_parent.getTaskName(), m_parent.getTaskType(),
					m_parent.getBseriesIPAddress(m_network), m_parent.getCreatedBy(),
					m_parent.getDescription());

           m_session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(m_network);
           log.info("Start to B6 network Upgrade .");
              if(m_session==null ||m_session.isBSeriesMgrProcessDown()) {
                  log.warn("BSeries manager process down.");
                  onFailed("BSeries manager process down.");
//                  m_parent.onNetworkFailed(m_network, "BSeries manager process down.");
                  return;
              }
              
              BaseCMSNetwork network = m_session.getEMSNetwork();
              
              if (network ==null || network.getConnectionState().intValue() < NetworkState.STATE_CONNECTED) {
                  log.warn("network[ "+m_network+" ] is not connected.");
                  onFailed("network[ "+m_network+" ] is not connected.");
//                  m_parent.onNetworkFailed(m_network, "network[ "+m_network+" ] is not connected.");
                   return;
                }
              String eqptType = ((CalixB6Device)network).getModel();
              String version = ((CalixB6Device)network).getSWVersion();
              if(eqptType== null || version==null){
                  log.warn("Get eqptType or version failed");
                  onFailed("Get eqptType or version failed");
//                  m_parent.onNetworkFailed(m_network, "Get eqptType or version failed");
                  return;
              }
              
              //To be changed to set attributes
            
              String orgPath;
        	  String ftpPath= ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, ServiceMgmtConstants.FTP_B6_IMAGE_PATH)+m_parent.getSourceDirectoryPath();
      		  orgPath = OccamUtils.getDefaultFTPValues().get(OccamStaticDef.CMS_FTP_PATH);
      		  if(orgPath!=null)
      			if(orgPath.endsWith("/"))
      				ftpPath = orgPath+"b6/fw/"+m_parent.getSourceDirectoryPath();
      			else
      				ftpPath = orgPath+"/b6/fw/"+m_parent.getSourceDirectoryPath();
              
              BSeriesGponOntUpgradeResponseSignal signal = new BSeriesGponOntUpgradeResponseSignal();
              signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_GPON_DEVICE_UPGRADE_REQ);
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
              String gponOntKey = OccamStaticDef.GPON_ONT_LIST + "_" + network.getIPAddress1();
              
              String gponOntList =info.getProperty(gponOntKey);
              gponOntList= gponOntList.replaceAll("#", ",");
              
              String gponUpgradeType = (String)info.getProperty(OccamStaticDef.GPON_OLT_UPGRADE_TYPE);
              signal.setGponOntUpgradeType(gponUpgradeType);
              HashMap gponOntObjs = getGponOntObjects(network.getIPAddress1(),gponOntList);
              
              if(gponUpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_MANUAL_UPGRADE)){
            	  HashMap respMap=  (HashMap) validateImage(gponOntObjs,gponOntList,m_parent.getSourceDirectoryPath());
            	  if(respMap!=null){
            		  String gponOntToUpgrade= (String) respMap.get("GponOntToUpgrade");
            		  GPONONT_UPGRADE_ERROR_MSG= (String) respMap.get("ErrorMessage");
            		  if(gponOntToUpgrade!=null && gponOntToUpgrade.length()>0){
            			  signal.setGponOntListToUpgrade(gponOntToUpgrade);
            			  signal.setIpAddress(network.getIPAddress1());
                          signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());
                          signal.setSnmpReadCommunity( ((CalixB6Device)network).getReadCommunity());
                          HashMap gponOntObjectToUpgrade = (HashMap) respMap.get("GponOntObjectToUpgrade");
                          signal.setGponOntObjs(gponOntObjectToUpgrade);
                          JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
            		  }else{
            			  onFailed("No GPON ONT need to be upgraded");
            		  }
            	  }
              }else if(gponUpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_AUTO_UPGRADE)){
            	    if(!OccamUtils.isSupportAutoUpgrade(version)){
                  	  log.warn("Auto Upgrade only support GPON OLT firmware above 7.1");
                  	  	onFailed("Auto Upgrade only support GPON OLT firmware above 7.1");
//                        m_parent.onNetworkFailed(m_network, "Auto Upgrade only support GPON OLT firmware above 7.1");
                        return;
                    }
            	  log.info("finish send signal to B6 network for network Upgrade.");
            	  signal.setGponActivateAction(info.getProperty(OccamStaticDef.GPONOLT_ACTIVATE_ACTION));
            	  signal.setGponDownloadAction(info.getProperty(OccamStaticDef.GPONOLT_DOWNLOAD_ACTION));
            	  signal.setGponOntListToUpgrade(gponOntList);
            	  signal.setIpAddress(network.getIPAddress1());
                  signal.setJobID(((GenericCMSAid)m_parent.getJobID()).getStrAid());
                  signal.setSnmpReadCommunity( ((CalixB6Device)network).getReadCommunity());
                  signal.setGponOntObjs(gponOntObjs);
                  JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
              }
              
          } catch (Exception ex) {
              log.warn("Error sending network Upgrade request. network: " + m_parent.getNetwork(), ex);
          }
          
          log.info("finish send signal to B6 network for network Upgrade.");
          
          //Need do verify?
          verifyStatus(); //verify Status after send the signal.
  }
  private Map validateImage(Map gponOntObjects,String gponOntList ,String imagename ){
	
	  log.info("B6 Gpon Upgrade validateImage : gponOntList : "+gponOntList);
	  log.info("B6 Gpon Upgrade validateImage : imagename : "+imagename);
	  StringBuffer sb = new StringBuffer();
	  String strErrorMsg="";
	  Map responseMap= new HashMap();
	  HashMap<String, OccamGponOnt> gponOntObjsToUpgrade = new HashMap<String, OccamGponOnt>();
	  String [] gponOnt=gponOntList.split(",");
	  String gponOntNotToUpgrade="";
	  boolean upgradeCanbeDone=true;
	  String gponOntToUpgrade="";
	  for(int i=0;i<gponOnt.length;i++){
		  String key= gponOnt[i];
		  OccamGponOnt gponOntObj= (OccamGponOnt)gponOntObjects.get(key);
		  String eqptType= gponOntObj.getModel();
		  String OntName= gponOntObj.getName();
		  try {
			upgradeCanbeDone = DBHandler.getInstance().isModelMatches(eqptType,m_parent.getSourceDirectoryPath());
			if(!upgradeCanbeDone){
				if(gponOntNotToUpgrade!=null && gponOntNotToUpgrade.length()>0)
					gponOntNotToUpgrade=gponOntNotToUpgrade+","+OntName;
				else
					gponOntNotToUpgrade=OntName;
			}else{
				if(gponOntToUpgrade.equals(""))
					gponOntToUpgrade=key;
				else
					gponOntToUpgrade=gponOntToUpgrade+","+key;
				
				gponOntObjsToUpgrade.put(key, gponOntObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  if(gponOntNotToUpgrade!=null && gponOntNotToUpgrade.length()>0){
		  strErrorMsg="Gpon Onts "+gponOntNotToUpgrade+" ignored since device model is incompatible with selected OS Image.";
	  }
	  log.info("B6 Gpon Upgrade validateImage :  GponOntToUpgrade : "+gponOntToUpgrade);
	  log.info("B6 Gpon Upgrade validateImage : ErrorMessage : "+strErrorMsg);
	  
	  responseMap.put("GponOntToUpgrade", gponOntToUpgrade);
	  responseMap.put("GponOntObjectToUpgrade", gponOntObjsToUpgrade);
	  responseMap.put("ErrorMessage", strErrorMsg);
	  return responseMap;
  }
  
  private void verifyStatus() {
      (new B6UpgradePostCheck()).run();
  }
  
  public void onSucceededAndReset() {
     // m_parent.onNetworkSucceededAndReset(m_network);
  }

  public void onSucceeded(BSeriesGponOntUpgradeResponseSignal signal) {
      OccamProtocolRequestResponse resObj = signal.getResObj();
      HashMap updateOntMap = (HashMap)resObj.getParameter(m_network);
      if (updateOntMap != null){
          Iterator itr = updateOntMap.keySet().iterator();
          while (itr.hasNext()){
              String key = (String)itr.next();
              Properties updateOntProp = (Properties)updateOntMap.get(key);
              GponOntInfoUpdateHandler.getInstance().addOntToQueueForUpdate(updateOntProp);
          }
      }
      String additionInfo = (String)resObj.getParameter(MediationOperationNames.GPON_ONT_ADDITIONAL_INFO);
      if(additionInfo != null && additionInfo.length()>0)
      {
          if(GPONONT_UPGRADE_ERROR_MSG !=null && GPONONT_UPGRADE_ERROR_MSG.length()>0){
        	  log.info("B6 Gpon Upgrade onSucceeded : GPONONT_UPGRADE_ERROR_MSG : "+GPONONT_UPGRADE_ERROR_MSG);
        	  m_parent.onNetworkSucceeded(m_network, GPONONT_UPGRADE_ERROR_MSG, additionInfo);
        	  GPONONT_UPGRADE_ERROR_MSG="";
          }else{
        	  m_parent.onNetworkSucceeded(m_network,"",additionInfo);
          }
      } else {
          if(GPONONT_UPGRADE_ERROR_MSG !=null && GPONONT_UPGRADE_ERROR_MSG.length()>0){
        	  log.info("B6 Gpon Upgrade onSucceeded : GPONONT_UPGRADE_ERROR_MSG : "+GPONONT_UPGRADE_ERROR_MSG);
        	  m_parent.onNetworkSucceeded(m_network, GPONONT_UPGRADE_ERROR_MSG);
        	  GPONONT_UPGRADE_ERROR_MSG="";
          }else{
        	  m_parent.onNetworkSucceeded(m_network);
          }
      }
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
      if(GPONONT_UPGRADE_ERROR_MSG !=null && GPONONT_UPGRADE_ERROR_MSG.length()>0){
    	  msg= msg+". "+GPONONT_UPGRADE_ERROR_MSG;
      }
      log.error(msg);
      m_parent.onNetworkFailed(m_network, msg);
      DBHandler.getInstance().updateAuditLog(m_parent.getTaskId(), m_parent.getBseriesIPAddress(m_network), ScheduleConstants.JOB_STATUS_FAILED, msg);

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
                            break;

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
  private HashMap getGponOntObjects(String deviceIp,String ponPortList){
      HashMap gponOntObjs = new HashMap();
      String[] onts2Upgrade = ponPortList.split(",");
      
      for(int i=0; i < onts2Upgrade.length; i++) {
          try {
              String[] ponNont = onts2Upgrade[i].split("\\.");
              String ponPortPart = ponNont[0].trim();
              String ontIdPart = ponNont[1].trim();
              String ontName = ponPortPart + "." + ontIdPart;
              OccamGponOnt moOnt = getOntInfo(deviceIp, ponPortPart, ontIdPart);
              if (moOnt != null){
                  gponOntObjs.put(ontName,moOnt);                  
              }
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      }
      return gponOntObjs;
  }
  private OccamGponOnt getOntInfo(String deviceName, String pon, String ont) {
      OccamGponOnt gponOnt = null;
      try {
          String ponName = deviceName + DiscoveryUtil.NAME_BUILDER_SEPARATOR
                  + OccamStaticDef.PON_KEYNAME + pon + DiscoveryUtil.GPON_ONT_NAME_BUILDER_SEPARATOR + ont;
          gponOnt = getGponOntDevice(ponName);         
      } catch (Exception ex) {
          ex.printStackTrace();
      }
      return gponOnt;
  }
  private OccamGponOnt getGponOntDevice(String ponName){
      
      OccamGponOnt occamGponOntObj = null;
      try {
          Properties prop =new Properties();
          prop.put("name", ponName);
          
          List<OccamGponOnt> occamGponOnts = DataBaseAPI.getInstance().getObjects(AttributeNames.OCCAM_GPON_ONT_DEVICE, prop);
          if (occamGponOnts != null && occamGponOnts.size() > 0){
              for (OccamGponOnt occamGponOnt : occamGponOnts) {
                  try{
                      
                      if (occamGponOnt.getName().equals(ponName)) {
                          occamGponOntObj = occamGponOnt;
                      }
                      
                  }catch(Exception e){
                      System.out.println("Object does not exist");
                  }
              }
          }
          
      } catch (Exception e) {
          log.warn("Got exception while fetching OccamGponOnts ", e);
      }
      return occamGponOntObj;
  }
}