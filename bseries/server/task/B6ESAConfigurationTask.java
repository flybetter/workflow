package com.calix.bseries.server.task;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.calix.bseries.server.boot.BseriesProcessHandler;
import com.calix.bseries.server.dbmodel.B6ScheduledTask;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.DBUtil;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.security.CMSDESCipher;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
import com.calix.ems.server.process.CMSProcess;
import com.calix.system.common.log.Log;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.util.DeviceChildTask;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.defines.OccamStaticDef;

public class B6ESAConfigurationTask extends CMSScheduledTask implements DeviceChildTask {
	private static final Logger log = Logger.getLogger(B6ESAConfigurationTask.class);
	private B6ScheduledTask m_parent;
	private String m_status = null;
	private String m_taskName = null;
	private String m_taskId = null;
	private boolean isInfoComplete = true;
	private int accessLevel = 2;
	
	private static final long TIME_OUT = 5 * 60 * 1000L; 
	@Override
	public void setParent(Object task) {
		this.m_parent = (B6ScheduledTask) task;
	}
	public void setNetwork(String network) {
        this.m_taskName = network;
        this.m_taskId = ((GenericCMSAid)m_parent.getJobID()).getStrAid()+"@@@"+network;
        BseriesProcessHandler handle= (BseriesProcessHandler) CMSProcessHandlers.getInstance().getProcessHandler(DeviceClassMap.DEVICE_MODULE_BSERIES);
        handle.addTaskToMap(((GenericCMSAid)m_parent.getJobID()).getStrAid(),network, this);
    }
	public void updateNetworksStatus(String taskName, Properties detailProps) {
		if(detailProps!= null){
			this.m_status = detailProps.getProperty(ScheduleConstants.ESA_RESULT);
			m_parent.updateESAStatus(detailProps);
		}	
	}
	public void updateNetworksSucceedStatus(String taskName) {
        m_parent.updateESAStatus(true, null);
	    this.m_status = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
		
	}
	public void updateNetworksFailureStatus(String taskName, String error) {
        m_parent.updateESAStatus(false, error);
	    this.m_status = ScheduleConstants.JOB_STATUS_FAILED;	
	}

	
	public void run() {
		try{
			
			String addtlInfo = m_parent.getAdditionalInfo();
			if(addtlInfo == null){
				updateNetworksFailureStatus(m_taskName,"The configration inforamtion is null");
				return;
			}
            Properties info = CommonUtil.getESAProperties(addtlInfo);
            BSeriesESAConfigSignal signal = new BSeriesESAConfigSignal();
            signal.setTaskId(this.m_taskId);
            signal.setTaskName(this.m_taskName);
            if(info.getProperty(OccamStaticDef.ESA_HOST_IP) != null){
            	signal.setEsaHostIp(info.getProperty(OccamStaticDef.ESA_HOST_IP));
            }else{
				updateNetworksFailureStatus(m_taskName,"EsaHostIp is null");
				return;
            }
            if(info.getProperty(OccamStaticDef.ESA_SWITCH_LOGIN_NAME) != null){
            	signal.setEsaSwitchLoginName(info.getProperty(OccamStaticDef.ESA_SWITCH_LOGIN_NAME));
            }else{
				updateNetworksFailureStatus(m_taskName,"EsaSwitchLoginName is null");
				return;
            }
            if(info.getProperty(OccamStaticDef.ESA_SWITCH_PASSWORD) != null){
            	signal.setEsaSwitchLoginPass(info.getProperty(OccamStaticDef.ESA_SWITCH_PASSWORD));
            }else{
				updateNetworksFailureStatus(m_taskName,"EsaSwitchLoginPass is null");
				return;
            }
            if(info.getProperty(OccamStaticDef.ESA_DB_FILE_NAME) != null){
            	signal.setEsaDbFileName(info.getProperty(OccamStaticDef.ESA_DB_FILE_NAME));
            }else{
				updateNetworksFailureStatus(m_taskName,"EsaDbFileName is null");
				return;
            }
            if(info.getProperty(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW) != null){
            	if(info.getProperty(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW).equalsIgnoreCase("true")){
            		signal.setEsaReganerate(true);
            	}else{
            		signal.setEsaReganerate(false);
            	}
            }
            if(info.get((OccamStaticDef.ESA_DOMAIN_PROPS_MAP)) != null){
            	signal.setEsaDomainPropsMap((HashMap)(info.get((OccamStaticDef.ESA_DOMAIN_PROPS_MAP))));
            }else{
				updateNetworksFailureStatus(m_taskName,"EsaDomainPropsMap is null");
				return;
            }
            String httpsAuthentication = getHttpsAuthentication();
            if(httpsAuthentication!= null){
            	signal.setHttpsAuthentication(httpsAuthentication);
            }
            signal.setAccessLevel(accessLevel);
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
		}catch (Exception ex) {
            log.warn("Error sending ESA Configuration request. task: " + m_taskName, ex);
        }
        
        log.info("finish send signal for ESA Configuration.");
        verifyStatus();
	}
	
	private String getHttpsAuthentication(){
		C7Database db = (C7Database) C7Database.getInstance();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select * from B6Settings;");
		String pass = null;
		ResultSet resultSet = null;
		try {
			db.beginTransaction();
			resultSet = db.executeQuery(sqlBuf.toString());
			if (resultSet != null&&resultSet.next()) {
				String webUserName = resultSet.getString("webusername");
				if(webUserName !=  null&&(webUserName.equals("Administrator")||webUserName.equals("Privileged"))){
					if(webUserName.equals("Privileged")){
						accessLevel = 3;
					}
					byte[] blob = resultSet.getBytes("encryptedwebpassword");
					if(blob != null){
						pass = new String(CMSDESCipher.getInstance().decrypt(blob));
					}
				}
			}
			db.commitTransaction();
		} catch (Exception e) {
			Log.web().error(" getNodes: Unable to get data ", e);
			db.rollbackTransaction();
		} finally {
			DBUtil.closeQuietly(resultSet);
			db.close();
		}
		return pass;
	}

	 private void verifyStatus() {
		 synchronized (this) {
			 if(m_status == null){
	        	try {
					this.wait(TIME_OUT);
				} catch (InterruptedException e) {
					log.error("Error verifying resynch status.", e);
				}
			 }
		 }
	     if (m_status == null) {
			updateNetworksFailureStatus(m_taskName,"Check resync status timed out");
	     }
	 }
}
