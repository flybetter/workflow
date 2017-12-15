/** 
 * Project Name:socket_netty 
 * File Name:ANAExecuteLogArchiveJob.java 
 * Package Name:com.calix.bseries.server.ana.process 
 * Date:29 Sep, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.common.CommonDateTimeUtils;
import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.DBUtil;
import com.calix.ems.exception.InfrastructureException;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.system.server.dbmodel.EMSAid;

/**
 * ClassName:ANAExecuteLogArchiveJob <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 29 Sep, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class ANAExecuteLogArchiveJob implements Job {
	private static final Logger log = Logger
			.getLogger(ANAExecuteLogArchiveJob.class);
	private static final int DEFAULT_LOG_ARCHIVE_DAY = 30;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		log.info("[Execute JOB][" + arg0.getTrigger().getFullName() + "]"
				+ CommonDateTimeUtils.getDateTime());
		long time = getArchiveDayTime();
		
		for (String tableName : getArchiveTableName()) {
			archiveLog(tableName, time);
			archiveLog(tableName, time);
		}
	}

	private long getArchiveDayTime() {
		int day = DEFAULT_LOG_ARCHIVE_DAY;
		B6Settings b6setting = null;
        try {
            b6setting = (B6Settings)CMSCacheManager.getCacheManager().getEMSObject
                (new B6Settings().getHierarchyIntegers(), new EMSAid("B6Setting") );
            if (b6setting != null && b6setting.getDeleteTempLogForOld()!=null){
            	day=b6setting.getDeleteTempLogForOld();
            }
        }catch(Exception e){
            log.error("fail to read days based on B6 setting",e);
        }
        b6setting=null;
		log.info("[Archive ANA Execute Log] Days"+day);
		return CommonDateTimeUtils.getTimeToDate(
				CommonDateTimeUtils.getDate(CommonDateTimeUtils
						.getDateOffSet(-day))).getTime();
	}

	private void archiveLog(String tableName, long time) {
		C7Database db = C7Database.getInstance();
		try {
			db.beginTransaction();
			removeArchiveLog(db, tableName, time);
			db.flush();
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackTransaction();
			String msg = "delete data from [B6FPCMasterTemplate] failed: "
					+ e.getMessage();
			log.error(msg);
		} finally {
			db.close();
		}
	}

	/**
	 * Function:getArchiveTableName<br/> 
	 * @author Tony Ben 
	 * @return 
	 * @since JDK 1.6
	 */
	private List<String> getArchiveTableName() {
		List<Class<?>> list = ClassReflectionUtils
				.getClasses(ANAConstants.TEMPLATE_PACKAGE_NAME);
		List<String> tableNameList = new ArrayList<String>();
		String className;
		for (Class<?> cls : list) {
			className = cls.getSimpleName();
			if (!CommonStringUtils.isEmpty(className)
					&& className.toLowerCase().startsWith("b6")
					&& className.toLowerCase().endsWith("template")
					&& !className.equals("B6Template")) {
				if (!tableNameList.contains(className)) {
					tableNameList.add(className);
				}
			}
		}
		list = null;
		return tableNameList;
	}

	/**
	 * Function:removeArchiveLog<br/> 
	 * @author Tony Ben 
	 * @param db
	 * @param tableName
	 * @param time 
	 * @since JDK 1.6
	 */
	private void removeArchiveLog(C7Database db, String tableName, long time) {
		String delQuery = "delete from " + tableName + " where endtimelong <"
				+ time;
		log.info("[Archive ANA Execute Log]"+delQuery);
		PreparedStatement statement = null;
		try {
			Connection conn = db.getSession().connection();
			statement = conn.prepareStatement(delQuery);
			statement.executeUpdate();
		} catch (Exception ex) {
			throw new InfrastructureException(
					"Error removing objects with query: " + delQuery, ex);
		} finally {
			DBUtil.closeQuietly(null, statement, null);
		}

	}
}
