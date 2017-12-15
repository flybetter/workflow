package com.calix.bseries.server.task;

import com.occam.ems.be.app.discovery.DiscoveryUtil;
import com.occam.ems.be.mo.OccamGponOnt;
import com.occam.ems.common.CommonUtil;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.common.mo.servicemanagement.SwAutoUpgradeOntStatusInfo;
import com.occam.ems.common.proxy.config.xml.Device;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import com.occam.ems.mediation.protocol.rmi.commandfactory.v60.gpon.GponOntImageUpgradeOntStatus;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;

public class B6NetworkGponUpgradeTask extends AbstractBSeriesTask {

    private static final Logger log = Logger.getLogger(BSeriesUpgradeChildTask.class);

    public static final String TRANSACTION_NAME = "RmiGponOntImageUpgrade";

    //James to change properties
    private String ftp_ip = null;
    private String ftp_user = null;
    private String ftp_password =null;
    private String ftp_path = null;
    private String jobID = null;
    private String ponPortList = null;
    private String gponUpgradeType = null;
   
    private String gponUpgradeActivateAction = null;
    private String gponUpgradeDownloadAction = null;
    private StringBuffer resultMessage  = new StringBuffer();
    boolean operationResult = false;
       private Map upgradeStatus;
        private static Long stateChangeTimeout = null;
        private HashMap gponOntObjs = null;
    private DevProperty devProperty = null;
    private String networkName = "";
    public B6NetworkGponUpgradeTask(String ip) {
        super(ip);
    }

    public B6NetworkGponUpgradeTask(BSeriesTaskSignal signal) {
        super(signal);
        
        this.ponPortList = ((BSeriesGponOntUpgradeResponseSignal)signal).getGponOntListToUpgrade();
        this.gponUpgradeType=((BSeriesGponOntUpgradeResponseSignal)signal).getGponOntUpgradeType();
        gponOntObjs = ((BSeriesGponOntUpgradeResponseSignal)signal).getGponOntObjs();
        networkName = ((BSeriesGponOntUpgradeResponseSignal)signal).getNetworkName();
        
        this.devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        devProperty.setProperty(DevProperty.GPON_ONT_ID,ponPortList);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);

        ftp_ip=((BSeriesGponOntUpgradeResponseSignal)signal).getFtpIP();
        ftp_user=((BSeriesGponOntUpgradeResponseSignal)signal).getFtpUser();
        ftp_password=((BSeriesGponOntUpgradeResponseSignal)signal).getFtpPassword();
        ftp_path =((BSeriesGponOntUpgradeResponseSignal)signal).getPath();
        log.debug(new StringBuffer().append("B6NetworkUpgradeTask : get ftp info: ")
                .append(". ftp_ip: ").append(ftp_ip)
                .append(". ftp_user: ").append(ftp_user)
                .append(". ftp_password: ").append(ftp_password)
                .append(". ftp_path: ").append(ftp_path)
                );
        
        jobID=((BSeriesGponOntUpgradeResponseSignal)signal).getJobID();
        if(gponUpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_AUTO_UPGRADE)){
        	gponUpgradeActivateAction = ((BSeriesGponOntUpgradeResponseSignal)signal).getGponActivateAction();
        	gponUpgradeDownloadAction = ((BSeriesGponOntUpgradeResponseSignal)signal).getGponDownloadAction();
        }
    }

    /**
     * execute upgrade
     * @return boolean
     * @Author: jawang
     * @Date: Jan 9, 2012
     */
    private void executeUpgrade() {
        boolean rtn = true;
        String[] ontsSelectedForUpgrade = ponPortList.split(",");
        List ontMoNameList=new ArrayList();
        for(int i=0; i < ontsSelectedForUpgrade.length; i++) {
            try {
                String[] ponNont = ontsSelectedForUpgrade[i].split("\\.");
                String ponPortPart = ponNont[0].trim();
                String ontIdPart = ponNont[1].trim();
                String ontMoName=ipAddr+DiscoveryUtil.NAME_BUILDER_SEPARATOR+OccamStaticDef.PON_KEYNAME + ponPortPart+DiscoveryUtil.GPON_ONT_NAME_BUILDER_SEPARATOR + ontIdPart;
                ontMoNameList.add(ontMoName);
            } catch(Exception e) {
                log.debug("B6NetworkGponUpgradeTask:executeUpgrade()");
                e.printStackTrace();
                
            }
        }
    log.debug("B6NetworkGponUpgradeTask : executeUpgrade : Configuration execution started for the Task '"
                    + jobID + "' on Device '" + ipAddr + "' with ONT list " + ponPortList);
        try {
            Thread.sleep(5000);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        if(stateChangeTimeout == null) {
            try{
                String occamConfFilePath = CommonUtil.rootDir + File.separator +  "occamViewEMS" + File.separator + "conf" +
                File.separator + "OccamConfig.conf";
                PropertyResourceBundle timeOutProps = new PropertyResourceBundle(new FileInputStream(occamConfFilePath));
                stateChangeTimeout = new Long(timeOutProps.getString("GPON_ONT_UPGRADE_CHANGE_STATE_TIMEOUT"));
            }catch(Exception e) {
                stateChangeTimeout = new Long(10*60*1000);
                log.debug("B6NetworkGponUpgradeTask : Error while reading timeout properties : " +e);
            }
        }
        String[] onts2Upgrade = ponPortList.split(",");

                upgradeStatus = new HashMap();

                for(int i=0; i < onts2Upgrade.length; i++) {
                    try {
                        String[] ponNont = onts2Upgrade[i].split("\\.");
                        String ponPortPart = ponNont[0].trim();
                        String ontIdPart = ponNont[1].trim();
                        
                        String ontName = ponPortPart + "." + ontIdPart;                        
                        OccamGponOnt moOnt = (OccamGponOnt)gponOntObjs.get(ontName);
                         String ontModels = "";
                        String ontSwVersion = "";
                        if ( moOnt!=null ) {
                            ontModels = moOnt.getEquipmentType();
                            ontSwVersion = moOnt.getEntitySoftwareRev();
                            if(gponUpgradeType.equals(OccamStaticDef.GPON_OLT_MANUAL_UPGRADE))
                                upgradeStatus.put(ontName,new GponOntUpgradeState(ponPortPart, ontIdPart, ontSwVersion));
                            else if(gponUpgradeType.equals(OccamStaticDef.GPON_OLT_AUTO_UPGRADE))
                                upgradeStatus.put(ontName, new GponOntUpgradeState(ponPortPart, ontIdPart, ontSwVersion));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(upgradeStatus.size() == 0) {                  
                    resultMessage.append("No suitable ONT for upgrade. Image doesn't correspond with. ");                    
                }
                if (sendImageConfigRequest(devProperty))
                        operationResult = true;
                    else
                        resultMessage.append("Send upgrade request for ONT '" + ponPortList + "' fail. ");
                boolean statusPollingComplete = false;
                while(operationResult && isOntInProcess()) { // continue getting status
                    try {
                        Thread.sleep(statusPollingComplete?3L*1000L:15L*1000L); // 15 sec or stateChangeTimeout/10
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    if(!statusPollingComplete) {
                        operationResult = getImageConfigState(devProperty);
                        if(!operationResult)
                            resultMessage.append("Error in getting status of upgrading ONTs. ");
                        else
                            statusPollingComplete = isAllStatusPollingComplete();
                    }
                    if(statusPollingComplete/* && isAllHaveSWVersion(device, swVersion)*/)
                        break;
                    if(isAllTimeouts()) {
                        resultMessage.append("Timeout occured. ");
                        operationResult = false;
                    }
                }
                if(operationResult)
                    operationResult= isAllUpgradeSuccess();
                addReportByONT(resultMessage);
                addONTSyncRequest(ipAddr);
                
                if(operationResult) {
                    resultMessage.append("Image upgrade successful. ");
                    reqRespObj.setParameter(MediationOperationNames.GPON_ONT_ADDITIONAL_INFO, resultMessage.toString());
                } else {
                    resultMessage.append("Image upgrade fail. ");    
                    List resError = new ArrayList();
                    resError.add(resultMessage);
                    reqRespObj.setErrorInfo(resError);
                }        
        
    }

    /**
     * 
     */
    public void execute() {
        try {
            
            executeUpgrade();
            
        } catch (Exception e) {
            log.error("Error in B6NetworkUpgradeTask");
        }
    }
  
    private boolean sendImageConfigRequest(DevProperty devProperty) {
        Properties resultProps = new Properties();
        boolean result = false;
        
        try {
            reqRespObj = new OccamProtocolRequestResponse();
            reqRespObj.setDeviceProperty(devProperty);
            reqRespObj.setOperationName(MediationOperationNames.OP_GPON_ONT_IMAGE_UPGRADE);
            if(gponUpgradeType.equals(OccamStaticDef.GPON_OLT_AUTO_UPGRADE))
                reqRespObj.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE, MediationOperationNames.GPON_OLT_AUTO_UPGRADE);
            else
                reqRespObj.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE, MediationOperationNames.GPON_OLT_MANUAL_UPGRADE);
            // reqRespObj.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE,gponUpgradeType);
            if(gponUpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_MANUAL_UPGRADE)){
            	//String imagePath = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, ServiceMgmtConstants.FTP_B6_IMAGE_PATH);
            	String ftp_info = "ftp://" + ftp_user + ":" + ftp_password + "@" + ftp_ip + "/" + ftp_path;
                reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_MODE, MediationOperationNames.MODE_UPGRADE_SOFTWARE);
                reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_URL, ftp_info);
                reqRespObj.setParameter(MediationOperationNames.KEY_GPON_ONT_IMAGE_UPGRADE_SUBOP, MediationOperationNames.GPON_ONT_IMAGE_UPGRADE_OP_UPGRADE);
                
                reqRespObj.medLogPrefix=ipAddr;
                reqRespObj.medAssLogger= OccamLoggerUtility.getLogger("config");
            }else if(gponUpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_AUTO_UPGRADE)){
                reqRespObj.setParameter(MediationOperationNames.GPONOLT_ACTIVATE_ACTION,gponUpgradeActivateAction);
                reqRespObj.setParameter(MediationOperationNames.GPONOLT_DOWNLOAD_ACTION,gponUpgradeDownloadAction);
            }
            OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
            protocalProperty.setRequestResponseObject(reqRespObj);
            
            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
            
            log.debug("B6NetworkGponUpgradeTask : sendImageConfigRequest : operation state : " + reqRespObj.getOperState());
            result = (reqRespObj.getOperState() == OccamStaticDef.OPER_SUCCESSFUL);
        } catch(Exception ex) {
            ex.printStackTrace();
            log.debug("B6NetworkGponUpgradeTask : sendImageConfigRequest : Failed in executing upgrade request" + ex);
            result=false;
        }
        return result;
    }
    
    private boolean getImageConfigState(DevProperty devProperty)
    {
        Properties resultProps = null;
        OccamProtocolRequestResponse statusRequest  = null;
        boolean result = true;

        try {
            
            resultProps = new Properties();
            statusRequest  = new OccamProtocolRequestResponse();
            statusRequest.setDeviceProperty(devProperty);
            statusRequest.setOperationName(MediationOperationNames.OP_GPON_ONT_IMAGE_UPGRADE);
            if(gponUpgradeType.equals(OccamStaticDef.GPON_OLT_AUTO_UPGRADE)){
            	statusRequest.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE, MediationOperationNames.GPON_OLT_AUTO_UPGRADE);
            }else
            	statusRequest.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE, MediationOperationNames.GPON_OLT_MANUAL_UPGRADE);
            
            statusRequest.setParameter(MediationOperationNames.KEY_GPON_ONT_IMAGE_UPGRADE_SUBOP, MediationOperationNames.GPON_ONT_IMAGE_UPGRADE_OP_STATUS);
            OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
            protocalProperty.setRequestResponseObject(statusRequest);
            
            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
            
            Map ontStatus = (Map)statusRequest.getParameter(MediationOperationNames.KEY_GPON_ONT_IMAGE_UPGRADE_ONTS_STATUS);
            if( (statusRequest.getOperState() == OccamStaticDef.OPER_SUCCESSFUL) && (ontStatus != null) ) {
            	Iterator it = upgradeStatus.entrySet().iterator();
                while(it.hasNext())
                { 
                		Map.Entry entry = (Map.Entry)it.next();
                        String wantedKey = (String)entry.getKey();
                        if(true){
                        	GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
                            if(!stored.reachEndStatus) {
                                StringBuffer messageOnt = new StringBuffer();
                                String hrStatus = stored.getHSatus();
                                int percent = stored.getProgressPercent();
                                String freshRawStat = (String)ontStatus.get(wantedKey);
                                if(freshRawStat != null) {
                                    if(stored.updateStatus(freshRawStat)) {
                                        messageOnt.append(" recieved ONT '" + wantedKey + "' status " + freshRawStat);
                                        messageOnt.append(" changed from " + percent + "% '" + hrStatus + "'");
                                    }
                                }  else if(!stored.reachEndStatus) {
                                    result = false;
                                    messageOnt.append(" needed ONT '" + wantedKey + "' not present");
                                    break;
                                }
                                log.debug("B6NetworkGponUpgradeTask: task " + jobID + ": " + messageOnt.toString());
                            }
                        }else{
                        	SwAutoUpgradeOntStatusInfo sfAutoUpStatus=(SwAutoUpgradeOntStatusInfo)entry.getValue();
                        	SwAutoUpgradeOntStatusInfo frshAutoStatusObj = (SwAutoUpgradeOntStatusInfo)ontStatus.get(wantedKey);
                        	if(!frshAutoStatusObj.getStatus().equals(SwAutoUpgradeOntStatusInfo.STATUS_UPGRADE_COMPLETE))
                        		result=false;
                        }
                		
                }
            } else {
                log.debug("B6NetworkGponUpgradeTask: task " + jobID + ": getImageConfigState didn't receive data from device");
                result = false;
            }
         } catch(Exception ex) {
                        ex.printStackTrace();
                        log.debug("B6NetworkGponUpgradeTask : getImageConfigState : Failed in executing get status request" + ex);
            result=false;
        }
        return result;
    }
   
    private boolean isOntInProcess()
    {
        Iterator it = upgradeStatus.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            if(!( stored.finishedOk || stored.finishedFail ))
                return true;
        }
        return false;
    }


    private boolean isAllHaveSWVersion(Device device, String version)
    {
        Iterator it = upgradeStatus.entrySet().iterator();
        boolean someMismatch = false;
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            if(!stored.finishedOk && stored.reachEndStatus) {
                String ontName = stored.ponId + "." + stored.ontId;
                OccamGponOnt moOnt = (OccamGponOnt)gponOntObjs.get(ontName);
                if(moOnt != null) {
                    final String realSwRevision = moOnt.getEntitySoftwareRev();
                    stored.setFinalSwRevision(realSwRevision);
                    if(version.equals(realSwRevision))
                        stored.finishedOk = true;
                    else {
                        boolean theSameSW = stored.swVersion.equals(realSwRevision);
                        if ( !theSameSW ) {
                            log.debug("B6NetworkGponUpgradeTask: ont " + moOnt.getEntitySerialNum()
                                    + " have not new '" + version + "' and not old '" + stored.swVersion + "' SW version. ");
                        }
                        someMismatch = true;
                    }
                }
            }
        }
        return !someMismatch;
    }

    private boolean isAllTimeouts()
    {
        long currentTimeStamp = System.currentTimeMillis();
        long smallestDiff = stateChangeTimeout.longValue()+1;
        Iterator it = upgradeStatus.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            String wantedKey = (String)entry.getKey();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            if( !stored.finishedOk ) {
                long diffTime = stored.diffTime(currentTimeStamp);
                if(diffTime < smallestDiff)
                    smallestDiff = diffTime;
            }
        }
        if(smallestDiff > stateChangeTimeout.longValue()) {
            log.debug("B6NetworkGponUpgradeTask : change state timeout");
            return true;
        }
        return false;
    }

    private boolean isAllStatusPollingComplete()
    {
        Iterator it = upgradeStatus.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            if(!stored.reachEndStatus)
                return false;
        }
        return true;
    }

    private boolean isAllUpgradeSuccess()
    {
        Iterator it = upgradeStatus.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            if(stored.finishedFail)
                return false;
        }
        return true;
    }
    private void addReportByONT(StringBuffer message) {
        Iterator it = upgradeStatus.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            String result = "ONT '" + stored.ponId + "." + stored.ontId + "'";
            if(stored.finishedOk){
                result += " - upgrade successfull. ";           
            }else{
                result += " - upgrade fail with status '" + stored.getHSatus() + "'. ";
            }
            message.append(result);
        }
    }

    private void addONTSyncRequest(String oltIP) {
        HashMap updateOntMap = new HashMap();
        Iterator it = upgradeStatus.entrySet().iterator();
        long t1=System.currentTimeMillis();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            GponOntUpgradeState stored = (GponOntUpgradeState)entry.getValue();
            if(stored.finishedOk){
                Properties updateOntProp = new Properties();
                updateOntProp.setProperty(DevProperty.DEVICE_IP_ATTR_NAME, oltIP);
                updateOntProp.setProperty(DevProperty.GPON_ONT_PON_PORT, stored.ponId);
                updateOntProp.setProperty(DevProperty.GPON_ONT_ID, stored.ontId);
                updateOntProp.setProperty("timestamp", ""+t1) ;
                String ontName = stored.ponId + "." + stored.ontId;
                updateOntMap.put(ontName,updateOntProp);
             //   GponOntInfoUpdateHandler.getInstance().addOntToQueueForUpdate(updateOntProp);
            }
        }
        reqRespObj.setParameter(networkName,updateOntMap);
    }
	@Override
	protected String getOperationName() {
	    //In this case, cause of init() get overwrote, this value is none-sense.  
		return "GPON_ONT_IMAGE_UPGRADE";
	}

	@Override
	protected BSeriesTaskSignal getResponseSignal() {
		BSeriesGponOntUpgradeResponseSignal signal = new BSeriesGponOntUpgradeResponseSignal();
                signal.setResObj(reqRespObj);
	        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_GPON_DEVICE_UPGRADE_RESP);
	        signal.setJobID(jobID);
	        return signal;
	}
}

// TODO check ont online-offline for quick completion

class GponOntUpgradeState {
    private String hrStatus;
    private long   timestampChanged;
    private int    progressPercent;
    String    ponId, ontId, swVersion;
    boolean finishedOk, finishedFail, reachEndStatus;
    String finalSwRevision;

    GponOntUpgradeState(String pon, String ont, String sw) {
        progressPercent = 0;
        hrStatus = GponOntImageUpgradeOntStatus.STATE_UNKNOWN;
        timestampChanged = System.currentTimeMillis();
        ponId = pon;
        ontId = ont;
        finishedOk = false;
        finishedFail = false;
        reachEndStatus = false;
        swVersion = sw;
    }

    public String getFinalSwRevision() {
        return finalSwRevision;
    }

    public void setFinalSwRevision(String finalSwRevision) {
        this.finalSwRevision = finalSwRevision;
    }

    public String getHSatus() {
        return hrStatus;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public String toString() {
        return hrStatus + " " + progressPercent + "%";
    }

    public long diffTime(long current) {
        return current - timestampChanged;
    }

    private boolean isBadStatus()
    {
        return (hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_NOT_STARTED) ||
                hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_DOWNLOAD_FAIL) ||
                hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_DOWNLOAD_CANCEL) ||
                hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_APPLY_FAIL) ||
                hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_ACTIVATE_FAIL) ||
                hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_COMMIT_FAIL) ||
                hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_TIMEOUT));
    }

    private boolean isCompleteStatus()
    {
        return hrStatus.equalsIgnoreCase(GponOntImageUpgradeOntStatus.STATE_ACTIVATE_COMPLETE);
    }

    public boolean updateStatus(String freshRawStat) {
        String[] parts = freshRawStat.split("\\|");
        String updated = null;
        int percent = 0;
        if(parts.length > 1 ) {
            updated = parts[1].trim();
            percent = Integer.parseInt(parts[0]);
        } else
            updated = parts[0].trim();
        boolean rc = false;
        if(!hrStatus.equalsIgnoreCase(updated)) {
            hrStatus = updated;
            rc = true;
            boolean complete = isCompleteStatus();
            boolean bad = isBadStatus();
            reachEndStatus =  complete || bad;
            if(reachEndStatus) {
                finishedOk = complete;
                finishedFail = bad;
            }
        }
        if(parts.length > 1  && percent != progressPercent) {
            progressPercent = percent;
            rc = true;
        }
        if(rc)
            timestampChanged = System.currentTimeMillis();
        return rc;
    }

}
