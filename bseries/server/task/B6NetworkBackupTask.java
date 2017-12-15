package com.calix.bseries.server.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;

public class B6NetworkBackupTask extends AbstractBSeriesTask {

	private static final Logger log = Logger.getLogger(B6NetworkBackupTask.class);

    public static final String TRANSACTION_NAME = "RmiConfigFile";

    private String ftp_ip = null;
    private String ftp_user = null;
    private String ftp_password =null;
    private String ftp_path = null;
    private String jobID = null;
    
    public B6NetworkBackupTask(String ip) {
        super(ip);
    }

    public B6NetworkBackupTask(BSeriesTaskSignal signal) {
        super(signal);
        ftp_ip=((BSeriesNetworkBackupResponseSignal)signal).getIp();
        ftp_user=((BSeriesNetworkBackupResponseSignal)signal).getFtpUser();
        ftp_password=((BSeriesNetworkBackupResponseSignal)signal).getFtpPassword();
        ftp_path =((BSeriesNetworkBackupResponseSignal)signal).getPath();
        jobID=((BSeriesNetworkBackupResponseSignal)signal).getJobID();
    }
    


    private OccamProtocolProperty protocalProperty = null;
    protected void init() {

        DevProperty devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);

        reqRespObj = new OccamProtocolRequestResponse();
        reqRespObj.setDeviceProperty(devProperty);

        String ftp_info = "ftp://" + ftp_user + ":" + ftp_password + "@" + ftp_ip + "/" + ftp_path;
        reqRespObj.setParameter("KeyConfigBackupRunningToURL", ftp_info+"/"+getFileName());
        reqRespObj.setOperationName("CONFIG_FILE");
        reqRespObj.medLogPrefix=ipAddr;
        reqRespObj.medAssLogger= OccamLoggerUtility.getLogger("config");

        protocalProperty = new OccamProtocolProperty();
        protocalProperty.setRequestResponseObject(reqRespObj);

    }

	private String getFileName() {
		SimpleDateFormat formatter
	     = new SimpleDateFormat ("yyyyMMdd-hhmmss");
		 Date currentTime = new Date();
		 
		 /*We will replace all the special chars with _*/
		 String patternStr="[!@#$%^&*()><.,;'\":{}[ ]]";
		 String replacementStr = "-";
		 Pattern pattern = Pattern.compile(patternStr);
		 Matcher matcher = pattern.matcher(networkName);
		 String ofileName = matcher.replaceAll(replacementStr);
		 StringBuffer fileName=new StringBuffer(ofileName);
		 
		 fileName.append("-backup-");
		 fileName.append(formatter.format(currentTime));
		 return fileName.toString();
	}


	@Override
	protected String getOperationName() {
		return "CONFIG_FILE";
	}



	@Override
	protected BSeriesTaskSignal getResponseSignal() {
		BSeriesNetworkBackupResponseSignal signal = new BSeriesNetworkBackupResponseSignal();
	        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_BACKUP_RESP);
	        signal.setJobID(jobID);
	        return signal;
	}



}
