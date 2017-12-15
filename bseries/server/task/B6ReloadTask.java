package com.calix.bseries.server.task;


import org.apache.log4j.Logger;

import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.DefaultConfiguration;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Vector;

public class B6ReloadTask extends AbstractBSeriesTask {
    
    private static final Logger log = Logger.getLogger(BSeriesReloadChildTask.class);
    
    
    private BSeriesReloadResponseSignal signal = null;
    private String resultMessage = "";
    private boolean reloadSuccess = true;
    public B6ReloadTask(String ip) {
        super(ip);
    }
    
    public B6ReloadTask(BSeriesTaskSignal signal) {
        super(signal);
        this.signal = (BSeriesReloadResponseSignal)signal;
//        this.devProperty = new DevProperty();
//        devProperty.setIPAddress(ipAddr);
//        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
//        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);
        
    }
    @Override
	protected String getOperationName() {
	    //In this case, cause of init() get overwrote, this value is none-sense.  
		return "RELOAD_DEVICE";
	}
    /**
     *
     */
    public void execute() {
        try {
            
            sendReloadRequest();
        } catch (Exception e) {
            log.error("Error in B6ReloadTask");
        }
    }
    
    
    private void sendReloadRequest(){
        
        
      //  OccamProtocolRequestResponse reloadReq = null;
        boolean finalResult = false;
        
        String restoreConfigDefFlag = signal.getRestoreConfig();
        String taskName = signal.getTaskName();
        try{
            log.debug("B6ReloadTask : sendReloadRequest : Configuration execution started for the Task "+taskName+
                    " to the device "+ipAddr);
            /** creating occamprotocol-request-response object  **/
            reqRespObj = new OccamProtocolRequestResponse();
            /** creating devproperty object  **/
            DevProperty devProperty = new DevProperty();
            /** setting device info  **/
            devProperty.setIPAddress(ipAddr);
            devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
            devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);
            /** adding SNMP Auth parameters */
            Vector v = new Vector();
            v.add(DefaultConfiguration.getDefaultConfig());//BSeriesUtil.getSNMPAuthData(snmpReadCommunity));
            devProperty.getProperties().put(DevProperty.SNMP_AUTH_PARAMS, v);
            
            reqRespObj.setDeviceProperty(devProperty);
            
            /** setting operation type to the occamprotocol-request-response object **/
            reqRespObj.setOperationName(MediationOperationNames.OP_REBOOT);
            reqRespObj.setParameter(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT, restoreConfigDefFlag);
            
            OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
            protocalProperty.setRequestResponseObject(reqRespObj);
            /** placing the request for mediation server **/            
            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
            /** check for the response **/
            
            log.debug("B6ReloadTask : sendReloadRequest : operationstate : "+reqRespObj.getOperState());
            log.debug("B6ReloadTask : sendReloadRequest :  reload errorinfo "+reqRespObj.getErrorInfo());
            
            //if((reloadReq.getOperState() != OccamStaticDef.OPER_FAILED) &&
            if(reqRespObj.getErrorInfo() == null) {
                //Check heartbeat only when reload is Success
            	reloadSuccess = waitForReloadComplete(devProperty);
                
            } else {
                resultMessage = OccamUtils.getFormattedErrorMessage(reqRespObj.getErrorInfo());
            }
            if(new Boolean(restoreConfigDefFlag).booleanValue() && OccamUtils.isJungoONT(eqptType,version )) {
                resultMessage = "Reload operation was performed with 'Restore default configuration' option\n" + resultMessage;
            }
            /** generating event based on the status **/
            if(reqRespObj.getErrorInfo() == null && (reloadSuccess)){
                log.debug("B6ReloadTask : sendReloadRequest : Reload Succeeded" );
            }else{
                log.debug("B6ReloadTask : sendReloadRequest : Reload Failed" );
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private boolean waitForReloadComplete(DevProperty devProperty){
        boolean result = true;
        
        PropertyResourceBundle timeOutProps = null;
        
        /** if the task name is not available in abort list then only go for reboot **/
        /** reading the reload timeout values from properties file **/
        try{
            timeOutProps = loadReloadTimeoutProperties();
            long minWaitPeriod = new Long(timeOutProps.getString("RELOAD_MIN_TIME")).longValue();
            long waitInterval = new Long(timeOutProps.getString("RELOAD_CHECK_INTERVAL")).longValue();
            long maxWaitPeriod = new Long(timeOutProps.getString("RELOAD_MAX_TIMEOUT")).longValue();
            
            log.debug("B6ReloadTask : waitForReloadComplete :  mintimeoutPeriod : "+minWaitPeriod
                    +" timeinterval : "+waitInterval+" maxWaitPeriod : "+maxWaitPeriod);
            
            
            try{
                /** waiting for the response with the min time interval **/
                log.debug("B6ReloadTask : waitForReloadComplete :  waiting for the response with " +
                        " min time out period : "+minWaitPeriod);
                Thread.sleep(minWaitPeriod);
            }catch(Exception ex){
                log.error("B6ReloadTask : waitForReloadComplete :  exception in waiting for the response with the" +
                        "min time out period "+ex);
            }
           // OccamProtocolRequestResponse heartBeatReq  = null ;
            for(long i = minWaitPeriod;i <= maxWaitPeriod; i+=waitInterval){
            	reqRespObj = new OccamProtocolRequestResponse();
                log.debug("B6ReloadTask : waitForReloadComplete :  executing ping i="+i);
                /** setting the ipaddress of device **/
                reqRespObj.setDeviceProperty(devProperty);
                /** setting the device type (simulator (or) real device **/
                reqRespObj.setParameter(MediationOperationNames.SIMULATOR_DEVICE,new Boolean(false));
                /** setting operation type to the occamprotocol-request-response object **/
                reqRespObj.setOperationName(MediationOperationNames.OP_HEART_BEAT);
                /** placing the request for mediation server **/
                OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
                protocalProperty.setRequestResponseObject(reqRespObj);
                
                OccamProtocolProvider provider = new OccamProtocolProvider();
                provider.syncSend(protocalProperty);
                /** parsing the response object **/
                
                /** check for heartbeat after wiating for response with min time interval. If success
                 * break the maxtimeout, otherwise wait for the response until max timeout reached **/
                if(reqRespObj.getErrorInfo() == null){
                    log.debug("B6ReloadTask : waitForReloadComplete : Reload Device is Success ");
                    result = true;
                    break;
                }
                
                result = false;
                log.debug("B6ReloadTask : waitForReloadComplete : errorinfo : "+reqRespObj.getErrorInfo());
                
                
                try{
                    Thread.sleep(waitInterval);
                }catch(Exception ex){
                    log.error("B6ReloadTask : waitForReloadComplete :  exception in waiting for the response while in" +
                            "interval period "+ex);
                }
            }
            
            if(result){
               // resultMessage = "Device rebooted successfully.";
                log.debug("B6ReloadTask : waitForReloadComplete : Device rebooted successfully");
                
            }else{
                log.debug("B6ReloadTask : waitForReloadComplete : Reboot Failed");
                if(reqRespObj.getErrorInfo() != null){
                    resultMessage = "Failed to reload device : " + OccamUtils.getFormattedErrorMessage(reqRespObj.getErrorInfo());
                }
            }
            
        }catch(Exception ex){
            log.error("B6ReloadTask : waitForReloadComplete :  exception in waiting for the response with the" +
                    "min time out period "+ex);
        }
        return result;
    }
    
    private PropertyResourceBundle loadReloadTimeoutProperties(){
        PropertyResourceBundle timeOutProps = null;
        /** reading the reload timeout values **/
        try{
            String occamConfFilePath = CommonUtil.rootDir + File.separator + "occamViewEMS" + File.separator + "conf" +
                    File.separator + "OccamConfig.conf";
            timeOutProps = new PropertyResourceBundle(new FileInputStream(occamConfFilePath));
        }catch(Exception e) {
            log.error("B6ReloadTask : loadReloadTimeoutProperties :  Error while Reading timeout properties : " +e);
        }
        return timeOutProps;
    }
    
    
    
    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        BSeriesReloadResponseSignal resSignal = new BSeriesReloadResponseSignal();
        resSignal.setType(BSeriesTaskSignal.SIG_TYPE_B6_RELOAD_RESP);
        
        if (!reloadSuccess){        	
        	resSignal.setError(resultMessage);
        }
        resSignal.setJobID(signal.getJobID());
        return resSignal;
    }
    
}
