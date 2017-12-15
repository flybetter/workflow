package com.calix.bseries.server.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.calix.ems.server.common.CMSConfig;
import com.occam.ems.server.DataBaseAPI;

public class PurgeTaskAudit implements Job{
    private static final Logger logger = Logger.getLogger(PurgeTaskAudit.class);

	private static final int DEFAULT_DAYS = 10;
	public static long ONE_DAY_MILLIS = 86400000L;
	public static long CURRENT_TIME_IN_MILLIS = System.currentTimeMillis(); 
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		boolean allowAudit = getAuditLogEnabled();

		if (!allowAudit) { 
	        logger.info("PurgeTaskAudit - BSERIES_AUDIT_LOG_ENABLE: " + allowAudit);
			return;
		}
		
        String jobName = context.getJobDetail().getFullName();	
        logger.info("Start to execute job: " + jobName);     
        try {
			boolean result = this.cleanData();
			logger.info("PurgeTaskAudit: result="+result);
		} catch (Exception e) {
	        logger.error("Exception when running log Purge Task Audit job.", e);
		}
	}
	
	public boolean cleanData() throws Exception{
		
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		ArrayList taskExecutionIdList = new ArrayList();
		
		logger.info("PurgeTaskAudit : cleanData : called  ");
		long taskAuditPurgeTime = new Integer(getDaysKept()).intValue()* ONE_DAY_MILLIS ;
		long purgeTime = CURRENT_TIME_IN_MILLIS - taskAuditPurgeTime;
		Date purgedDate =  new Date(purgeTime);
		logger.debug("PurgeTaskAudit : purge period  : called : "+purgedDate+" purge long : "+purgeTime);

		try {
			/** getting the connection **/
			connection = DataBaseAPI.getInstance().getapi().getConnection();
			connection.setAutoCommit(true);
			
			String getExecutionId = "select id from DeviceAudit WHERE timeoffinish <=  ? ";
			logger.info("PurgeTaskAudit : cleanData : query to get executionid list : " + getExecutionId);
			
			pstmt = connection.prepareStatement(getExecutionId);
			pstmt.setLong(1, purgeTime);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				taskExecutionIdList.add(resultSet.getInt("id") + "");
			}
			if (taskExecutionIdList.size() != 0) {
				String executionIdQueryString = formatQueryString("id", taskExecutionIdList);

				/** deleting the DeviceAudit**/
				String deleteDeviceAudit = "delete from DeviceAudit where " + executionIdQueryString;
				logger.info("PurgeTaskAudit : cleanData : query to deleteDeviceAudit : " + deleteDeviceAudit);
				pstmt = connection.prepareStatement(deleteDeviceAudit);
				pstmt.execute();
			}
			
			result = true;
			return result; 
		}catch(Exception ex){
			logger.error("PurgeTaskAudit : cleanData  : failed to purge configdata", ex);
			return false;
		}finally{
			if(pstmt != null){
				pstmt.close();
			}
			if(resultSet != null){
				resultSet.close();
			}
		}
	}

	private String formatQueryString(String columnName, ArrayList columnValueList) {
        StringBuffer queryBuffer = new StringBuffer();
        try {
            if (columnValueList.size() > 0) {
                queryBuffer.append(columnName +" in ('");
                for (int i = 0; i < columnValueList.size(); i++) {
                    queryBuffer.append(columnValueList.get(i) +"'" );
                    if (i < (columnValueList.size() - 1)) {
                        queryBuffer.append(",'");
                    }
                }
                queryBuffer.append(") ");
            }
            logger.debug("PurgeTaskAudit : formatQueryString : finalQueryString : " + queryBuffer.toString());
        } catch (Exception ex) {
            logger.error( "PurgeTaskAudit : formatQueryString : error during formatting query string. ", ex);
        }
        return queryBuffer.toString();
    
	}

	public int getDaysKept(){
        int keepDaysFromProperty = CMSConfig.getInstance().
        		getConfigIntVal("/QuartzProperty/PurgeTaskAudit/PURGE_TASK_AUDIT_LOG_DAYS", DEFAULT_DAYS);
        
        logger.info("PurgeTaskAudit - PURGE_TASK_AUDIT_LOG_DAYS: " + keepDaysFromProperty);
		return keepDaysFromProperty;
	}

	private boolean getAuditLogEnabled() {
		String auditLogEnabled = CMSConfig.getInstance().getProperty("/QuartzProperty/PurgeTaskAudit/BSERIES_AUDIT_LOG_ENABLE","value","true");
		if (auditLogEnabled != null && auditLogEnabled.equalsIgnoreCase("false")) {
			return false;
		} else {
			return true;
		}
	}
	
}
