package com.calix.bseries.server.task;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.occam.ems.be.app.configuration.FtpOperationsUtil;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Vector;

public class B6NetworkUpgradeTask extends AbstractBSeriesTask {

	private static final Logger log = Logger.getLogger(BSeriesUpgradeChildTask.class);

    public static final String TRANSACTION_NAME = "RmiImageUpgrade";

    //James to change properties
    private String ftp_ip = null;
    private String ftp_user = null;
    private String ftp_password =null;
    private String ftp_path = null;
    private String jobID = null;
    private boolean saveConfigurationFlag = false;
//    private boolean forceUpgradeFlag = false;
    private boolean reloadDeviceFlag = false;
    private String upgradedVersion = null;
    private DevProperty devProperty = null;
    private String upgradeStatusMessage = null;
    public B6NetworkUpgradeTask(String ip) {
        super(ip);
    }

    public B6NetworkUpgradeTask(BSeriesTaskSignal signal) {
        super(signal);
        this.devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);

        ftp_ip=((BSeriesNetworkUpgradeResponseSignal)signal).getFtpIP();
        ftp_user=((BSeriesNetworkUpgradeResponseSignal)signal).getFtpUser();
        ftp_password=((BSeriesNetworkUpgradeResponseSignal)signal).getFtpPassword();
        ftp_path = ((BSeriesNetworkUpgradeResponseSignal)signal).getPath();
        log.info(new StringBuffer().append("B6NetworkUpgradeTask : get ftp info: ")
                .append(". ftp_ip: ").append(ftp_ip)
                .append(". ftp_user: ").append(ftp_user)
                .append(". ftp_password: ").append(ftp_password)
                .append(". ftp_path: ").append(ftp_path)
                );
        
        jobID=((BSeriesNetworkUpgradeResponseSignal)signal).getJobID();
      //  int tmp = ((BSeriesNetworkUpgradeResponseSignal)signal).getSaveConfigurationFlag();
        this.saveConfigurationFlag = ((BSeriesNetworkUpgradeResponseSignal)signal).getSaveConfigurationFlag();

//        tmp = ((BSeriesNetworkUpgradeResponseSignal)signal).getForceUpgradeFlag();
//        this.forceUpgradeFlag = (tmp == 1) ? true : false;
        
      //  tmp = ((BSeriesNetworkUpgradeResponseSignal)signal).getReloadDeviceFlag();
        this.reloadDeviceFlag = ((BSeriesNetworkUpgradeResponseSignal)signal).getReloadDeviceFlag();
    }

//    private OccamProtocolProperty protocalProperty = null;
    protected void init() {
        
        //Save configuration first, if needed
        if (this.saveConfigurationFlag) {
            //Don't care about return
            executeSaveConfiguration();
        }
    }

    /**
     * execute upgrade
     * @return boolean
     * @Author: jawang
     * @Date: Jan 9, 2012
     */
    private boolean executeUpgrade() {
        boolean rtn = true;
        reqRespObj = new OccamProtocolRequestResponse();
        reqRespObj.setDeviceProperty(devProperty);
        //As we support user to specify directory of ftp. needn't read directory information from config file.
        //String imagePath = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, ServiceMgmtConstants.FTP_B6_IMAGE_PATH);
		 String	ftp_info = "ftp://" + ftp_user + ":" + ftp_password + "@" + ftp_ip + "/" + ftp_path;
		log.info("get ftp path complete ftp_info is "+ftp_info);
//        String ftp_info = "ftp://cmsftp:cmsftp@10.201.15.16//B6/key.txt";
//        reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_MODE, MediationOperationNames.MODE_UPGRADE_ALL);
        reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_MODE, MediationOperationNames.MODE_UPGRADE_SOFTWARE);
//        reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_URL, "ftp://cmsuser:cmsuser@10.245.15.132//opt/cms/B6Upgrade/OCCAMOS.V6_1R1");
        reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_URL, ftp_info);
        
//        String Test = "";
        //James, should add this attribution? Get from GUI
//        reqRespObj.setParameter(MediationOperationNames.KEY_SAVE_RUNNING_CONFIG_TO_STARTUP, new Boolean(false));
        reqRespObj.setOperationName("IMAGE_UPGRADE");
        reqRespObj.medLogPrefix=ipAddr;
        reqRespObj.medAssLogger= OccamLoggerUtility.getLogger("config");

        OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
        protocalProperty.setRequestResponseObject(reqRespObj);
        
        OccamProtocolProvider provider = new OccamProtocolProvider();
        provider.syncSend(protocalProperty);

        /** check for the response **/
        if (reqRespObj.getOperState() == OccamStaticDef.OPER_FAILED) {
            log.error("B6NetworkUpgradeTask : executeUpgrade : operationstate : " + reqRespObj.getOperState());
            rtn = false;
        }

        if (reqRespObj.getErrorInfo() != null) {
            /** setting the result to failed **/
            log.error("B6NetworkUpgradeTask : executeUpgrade : get error...");
            for (Iterator it = reqRespObj.getErrorInfo().iterator(); it.hasNext();) {
                log.error("Error: " + it.next());
            }
            rtn = false;
            upgradeStatusMessage = "B6 Image Upgrade Failed for " + ipAddr;
        }
        return rtn;
    }

    /**
     * 
     */
    public void execute() {
        try {
            init();
            boolean flag = executeUpgrade();
            // Reboot B6 if needed
            if ((true == this.reloadDeviceFlag) 
                    && (true == flag)) {
                // Reboot B6
                executeReloadDevice();
            }
        } catch (Exception e) {
            log.error("Error in B6NetworkUpgradeTask");
        }
    }
    
    /**
     * Reboot B6
     * @Return: boolean
     * @Author: jawang
     * @Date: Jan 9, 2012
     */
    private boolean executeReloadDevice() {
        boolean rtn = true;
        OccamProtocolRequestResponse occamRequest = new OccamProtocolRequestResponse();
        occamRequest.setDeviceProperty(devProperty);
        //Command defined in OccamTransactionRes.properties
        occamRequest.setOperationName("REBOOT_NE");

        OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
        protocalProperty.setRequestResponseObject(occamRequest);

        OccamProtocolProvider provider = new OccamProtocolProvider();
        provider.syncSend(protocalProperty);

        /** check for the response **/
        if (occamRequest.getOperState() == OccamStaticDef.OPER_FAILED) {
            log.error("B6NetworkUpgradeTask : executeReloadDevice : operationstate : " + occamRequest.getOperState());
            rtn = false;
        }

        if (occamRequest.getErrorInfo() != null) {
            log.error("B6NetworkUpgradeTask : executeReloadDevice : get error...");
            for (Iterator it = occamRequest.getErrorInfo().iterator(); it.hasNext();) {
                log.error("Error: " + it.next());
            }
            rtn = false;
            upgradeStatusMessage = "B6 Reload during Image Upgrade Failed for " + ipAddr;
        }else{
            waitForReloadComplete();
        }
        return rtn;
    }

    /**
     * To save configuration in B6
     * 
     * @return
     * @Return: boolean
     * @Author: jawang
     * @Date: Jan 9, 2012
     */
    private boolean executeSaveConfiguration() {
        boolean rtn = true;
        /** creating occamprotocol-request-response object **/
        OccamProtocolRequestResponse occamRequest = new OccamProtocolRequestResponse();
        /** setting the ipaddress of devcice **/
        occamRequest.setDeviceProperty(devProperty);
        /** setting operation type to the occamprotocol-request-response object **/
        occamRequest.setOperationName(MediationOperationNames.OP_CONFIG_FILE);
        occamRequest.setParameter(MediationOperationNames.KEY_SAVE_RUNNING_CONFIG_TO_STARTUP, new Boolean(true));

        OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
        protocalProperty.setRequestResponseObject(occamRequest);

        OccamProtocolProvider provider = new OccamProtocolProvider();
        provider.syncSend(protocalProperty);

        /** check for the response **/
        if (occamRequest.getOperState() == OccamStaticDef.OPER_FAILED) {
            log.error("B6NetworkUpgradeTask : executeSaveConfiguration : operationstate : " + occamRequest.getOperState());
            rtn = false;
        }

        if (occamRequest.getErrorInfo() != null) {
            log.error("B6NetworkUpgradeTask : executeSaveConfiguration : get error...");
            for (Iterator it = occamRequest.getErrorInfo().iterator(); it.hasNext();) {
                log.error("Error: " + it.next());
            }
            rtn = false;
        }
        return rtn;
    }

	@Override
	protected String getOperationName() {
	    //In this case, cause of init() get overwrote, this value is none-sense.  
		return "IMAGE_UPGRADE";
	}

	

        private void waitForReloadComplete(){
            boolean result = true;
            PropertyResourceBundle timeOutProps = null;

            /** if the task name is not available in abort list then only go for reboot **/
					/** reading the reload timeout values from properties file **/
            try{
                timeOutProps = loadReloadTimeoutProperties();
                long minWaitPeriod = new Long(timeOutProps.getString("RELOAD_MIN_TIME")).longValue();
                long waitInterval = new Long(timeOutProps.getString("RELOAD_CHECK_INTERVAL")).longValue();
                long maxWaitPeriod = new Long(timeOutProps.getString("RELOAD_MAX_TIMEOUT")).longValue();
                log.debug("B6NetworkUpgradeTask : waitForReloadComplete :  mintimeoutPeriod : "+minWaitPeriod
                        +" timeinterval : "+waitInterval+" maxWaitPeriod : "+maxWaitPeriod);
                log.debug("B6NetworkUpgradeTask : waitForReloadComplete : option to reload device after upgrade was chosen ");
                
                try{
                    /** waiting for the response with the min time interval **/
                    log.debug("B6NetworkUpgradeTask : waitForReloadComplete :  waiting for the response with " +
                            " min time out period : "+minWaitPeriod);
                    Thread.sleep(minWaitPeriod);
                }catch(Exception ex){
                    log.error("B6NetworkUpgradeTask : waitForReloadComplete :  exception in waiting for the response with the" +
                            "min time out period "+ex);
                }
                OccamProtocolRequestResponse heartBeatReq  = null ;
                for(long i = minWaitPeriod;i <= maxWaitPeriod; i+=waitInterval){
                    heartBeatReq = new OccamProtocolRequestResponse();
                    log.debug("B6NetworkUpgradeTask : waitForReloadComplete :  executing ping i="+i);
                    /** setting the ipaddress of device **/
                    heartBeatReq.setDeviceProperty(devProperty);
                    /** setting the device type (simulator (or) real device **/
                    heartBeatReq.setParameter(MediationOperationNames.SIMULATOR_DEVICE,new Boolean(false));
                    /** setting operation type to the occamprotocol-request-response object **/
                    heartBeatReq.setOperationName(MediationOperationNames.OP_HEART_BEAT);
                    /** placing the request for mediation server **/
                    OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
                    protocalProperty.setRequestResponseObject(heartBeatReq);
                    
                    OccamProtocolProvider provider = new OccamProtocolProvider();
                    provider.syncSend(protocalProperty);
                    /** parsing the response object **/
                    
                    /** check for heartbeat after wiating for response with min time interval. If success
                     * break the maxtimeout, otherwise wait for the response until max timeout reached **/
                    if(heartBeatReq.getErrorInfo() == null){
                        log.debug("B6NetworkUpgradeTask : waitForReloadComplete : Reload Device is Success ");
                        result = true;
                        break;
                    }
                    
                    result = false;
                    log.debug("B6NetworkUpgradeTask : waitForReloadComplete : errorinfo : "+heartBeatReq.getErrorInfo());
                    
                    
                    try{
                        Thread.sleep(waitInterval);
                    }catch(Exception ex){
                        log.error("B6NetworkUpgradeTask : waitForReloadComplete :  exception in waiting for the response while in" +
                                "interval period "+ex);
                    }
                }
                
                if(result){
                    String resultMsg = "Device rebooted successfully after operational image upgrade.";
                    log.debug("B6NetworkUpgradeTask : waitForReloadComplete : " + resultMsg);
                    verifyUpgradeComplete();
                }else{
                    log.debug("B6NetworkUpgradeTask : waitForReloadComplete : Reboot Failed");
                    upgradeStatusMessage = "B6 Reload during Image Upgrade Failed for " + ipAddr;
                }

            }catch(Exception ex){
                log.error("B6NetworkUpgradeTask : waitForReloadComplete :  exception in waiting for the response with the" +
                        "min time out period "+ex);
            }
        }
        private void verifyUpgradeComplete(){
        try {
            OccamProtocolRequestResponse request=null;
            int retryCount = OccamStaticDef.DEFAULT_RETRIES_TO_VERIFY_UPGRADE_ON_BLC;
            String retryCountString = OccamUtils.getComminResource().getString("imageUpgradeVerifyRetries.blc");
            
            if( retryCountString != null && retryCountString.trim().length() > 0 ) {
                try {
                    retryCount = Integer.parseInt(retryCountString);
                } catch (Exception e) {
                    String msg = "Could not find \"imageUpgradeVerifyRetries.blc\" " +
                            "in OccamComResource.properties. " +
                            "Setting default value "+ retryCount;
                    log.info(msg);
                    log.debug(msg, e);
                }
            }
            int retrySleep = OccamStaticDef.DEFAULT_RETRY_SLEEP_TO_VERIFY_UPGRADE_ON_BLC;
            String retrySleepString = OccamUtils.getComminResource().getString("imageUpgradeVerifyRetryWaitPeriod.blc");
            
            if( retrySleepString != null && retrySleepString.trim().length() > 0 ) {
                try {
                    retrySleep = Integer.parseInt(retrySleepString);
                } catch (Exception e) {
                    String msg = "Could not find \"imageUpgradeVerifyRetryWaitPeriod.blc\" " +
                            "in OccamComResource.properties. " +
                            "Setting default value "+ retrySleep;
                    log.info(msg);
                    log.debug(msg, e);
                }
            }
            retrySleep *= 1000; //convert to milliseconds
            while( retryCount > 0 ) {
                boolean retry = false;
                request = getUpgradedBLCVersion(ipAddr);
                
                if( request.getParameter(OccamStaticDef.BLC_UPGRADED_VERSION).toString() ==null || request.getParameter(OccamStaticDef.BLC_UPGRADED_VERSION).toString().equals("null")) {
                    retry = true;
                } else {
                    
                    upgradedVersion=request.getParameter(OccamStaticDef.BLC_UPGRADED_VERSION).toString();
                    log.debug("B6NetworkUpgradeTask : verifyUpgradeComplete : New Version " + upgradedVersion);
                    //  setUpgradedVersionOnBLC(upgradedVersion,ipAddr);
                    
                }
                if( retry ) {
                    Thread.sleep(retrySleep);
                    retryCount--;
                } else {
                    break;
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        }
        private PropertyResourceBundle loadReloadTimeoutProperties(){
            PropertyResourceBundle timeOutProps = null;
//            if(timeOutProps != null)
//                return;
            /** reading the reload timeout values **/
            try{
                String occamConfFilePath = CommonUtil.rootDir + File.separator + "occamViewEMS" + File.separator + "conf" +
                        File.separator + "OccamConfig.conf";
                timeOutProps = new PropertyResourceBundle(new FileInputStream(occamConfFilePath));
            }catch(Exception e) {
                log.error("B6NetworkUpgradeTask : loadReloadTimeoutProperties :  Error while Reading timeout properties : " +e);
            }
            return timeOutProps;
        }

         private OccamProtocolRequestResponse getUpgradedBLCVersion(String blcIp) {          
            
             OccamProtocolRequestResponse request = new OccamProtocolRequestResponse();
             DevProperty info = new DevProperty();
             info.setIPAddress(blcIp);
             
             Properties snmpProp = null;
             Properties retProp = null;
             // Get the configured auth profile for this node
             Vector authVec = new Vector();
             authVec.add(BSeriesUtil.getSNMPAuthData(snmpReadCommunity));
             
             info.getProperties().put(DevProperty.SNMP_AUTH_PARAMS, authVec);
             request.setDeviceProperty(info);
             request.setOperationName(OccamStaticDef.GET_BLC_SOFTWARE_VERSION);
             
             OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
            protocalProperty.setRequestResponseObject(request);

            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
             
             return request;
             
         }
         /*private void ftpImageToFtpServer(String imageName){
        	 FtpOperationsUtil ftpOpers = new FtpOperationsUtil();
        	 String imagePath = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, ServiceMgmtConstants.B6_IMAGE_PATH);
        	 ftpOpers.setUname(ftp_user);
        	 ftpOpers.setPassword(ftp_password);
        	 ftpOpers.setHost(ftp_ip);
        	 ftpOpers.setBinary(true);        	 
        	 ftpOpers.setFtpPath(imagePath);
        	 log.debug("B6NetworkUpgradeTask : ftpImageToFtpServer imagePath" + imagePath);
             try {					
					 ftpOpers.doConnect();
					 ftpOpers.doLogin();				 
					 ftpOpers.ftpCreateDirectoryTree(imagePath);
					 String localImgPath = CommonUtil.rootDir + imagePath + imageName;
					 ftpOpers.doUploadSingleFile(localImgPath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
	            	ftpOpers.doClose();
	            }
         }*/
       
          
          @Override
	protected BSeriesTaskSignal getResponseSignal() {
		BSeriesNetworkUpgradeResponseSignal signal = new BSeriesNetworkUpgradeResponseSignal();
	        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_UPGRADE_RESP);
                if (upgradedVersion != null)
                    signal.setVersion(upgradedVersion);
                if (upgradeStatusMessage != null){
                    signal.setError(upgradeStatusMessage);
                }
	        signal.setJobID(jobID);
	        return signal;
	}

}
