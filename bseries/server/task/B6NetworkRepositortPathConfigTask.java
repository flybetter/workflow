package com.calix.bseries.server.task;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;

import com.occam.ems.be.app.discovery.DiscoveryUtil;
import com.occam.ems.be.mo.OccamGponOnt;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.common.mo.servicemanagement.SwAutoUpgradeOntStatusInfo;
import com.occam.ems.common.proxy.config.xml.Device;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;

public class B6NetworkRepositortPathConfigTask extends AbstractBSeriesTask{

    private static final Logger log = Logger.getLogger(BSeriesUpgradeChildTask.class);

    public static final String TRANSACTION_NAME = "RmiGponOntImageUpgrade";

    //James to change properties
    private String ftp_ip = null;
    private String ftp_user = null;
    private String ftp_password =null;
    private String ftp_path = null;
    private String jobID = null;
  
    private String gponRepositoryPath = null;

    private String resultMessage  = "";
    boolean operationResult = false;
       private Map upgradeStatus;
        private static Long stateChangeTimeout = null;
        private HashMap gponOntObjs = null;
    private DevProperty devProperty = null;
    
    public B6NetworkRepositortPathConfigTask(String ip) {
        super(ip);
    }

    public B6NetworkRepositortPathConfigTask(BSeriesTaskSignal signal) {
        super(signal);
        this.gponRepositoryPath=((BSeriesNetworkUpgradeResponseSignal)signal).getGponRepositoryPath();
        this.devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);

        ftp_ip=((BSeriesNetworkUpgradeResponseSignal)signal).getFtpIP();
        ftp_user=((BSeriesNetworkUpgradeResponseSignal)signal).getFtpUser();
        ftp_password=((BSeriesNetworkUpgradeResponseSignal)signal).getFtpPassword();
        ftp_path =((BSeriesNetworkUpgradeResponseSignal)signal).getPath();
        log.debug(new StringBuffer().append("B6NetworkUpgradeTask : get ftp info: ")
                .append(". ftp_ip: ").append(ftp_ip)
                .append(". ftp_user: ").append(ftp_user)
                .append(". ftp_password: ").append(ftp_password)
                .append(". ftp_path: ").append(ftp_path)
                );
        
        jobID=((BSeriesNetworkUpgradeResponseSignal)signal).getJobID();  
    }

    /**
     * execute upgrade
     * @return boolean
     * @Author: jawang
     * @Date: Jan 9, 2012
     */
    private void executeUpgrade() {
    	boolean operationResult =false;
    	operationResult=sendRepositoryPathConfigReq(devProperty,gponRepositoryPath);
        if(operationResult) {
            resultMessage="";
        } else {
            resultMessage="Repository Path Configuration fail. ";
        }
        List resError = new ArrayList();
        resError.add(resultMessage);
        reqRespObj.setErrorInfo(resError);

    }
        private boolean sendRepositoryPathConfigReq(DevProperty	devProperty,String repositryPath){
        	Properties resultProps = new Properties();
        	boolean result = false;
        	 try {
                 reqRespObj = new OccamProtocolRequestResponse();
                 reqRespObj.setDeviceProperty(devProperty);
                 reqRespObj.setOperationName(MediationOperationNames.OP_GPON_ONT_IMAGE_UPGRADE);
                 reqRespObj.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE, MediationOperationNames.GPON_OLT_AUTO_UPGRADE);
                 // reqRespObj.setParameter(MediationOperationNames.GPON_OLT_UPGRADE_TYPE,gponUpgradeType);
                 reqRespObj.setParameter(MediationOperationNames.KEY_GPON_ONT_IMAGE_UPGRADE_SUBOP, MediationOperationNames.GPONOLT_REPOSITORY_PATH_CONFIGURATION);
                 reqRespObj.setParameter(MediationOperationNames.KEY_UPGRADE_URL, gponRepositoryPath);
                 
                 log.debug("B6NetworkRepositortPathConfigTask :  gponRepositoryPath : " + gponRepositoryPath);
                 log.debug("B6NetworkRepositortPathConfigTask :  devProperty : " + devProperty);
                 reqRespObj.medLogPrefix=ipAddr;
                 reqRespObj.medAssLogger= OccamLoggerUtility.getLogger("config");
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
 
	@Override
	protected String getOperationName() {
	    //In this case, cause of init() get overwrote, this value is none-sense.  
		return "GPON_ONT_IMAGE_UPGRADE";
	}

	@Override
	protected BSeriesTaskSignal getResponseSignal() {
		BSeriesGponOntUpgradeResponseSignal signal = new BSeriesGponOntUpgradeResponseSignal();
	        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_REPOSITORY_PATH_CONFIG_RESP);
	        signal.setJobID(jobID);
	        signal.setError(resultMessage);
	        signal.setResObj(reqRespObj);
	        return signal;
	}
}
