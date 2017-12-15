/*
 * DBMigrationConstants.java
 *
 * Created on October 20, 2011, 5:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.server.dbmigration;

/**
 *
 * @author brao
 */
public interface DBMigrationConstants {
    public final static String FAULT_MODULE = "FaultModule";
    public final static String CONFIG_MODULE = "ConfigModule";
    public final static String ADMIN_MODULE = "AdminModule";
    public final static String PERFORMANCE_MODULE = "PerformanceModule";
    public final static String SECURITY_MODULE = "SecurityModule";
    public final static String DBMIG_PROPERTY_FILE_NAME = "dbmigration";
    public final static String DB_MIG_CLASSES = "DBMigrationClasses";
    public final static String SUPPORTED_ONT_TYPES = "SupportedONTTypes";
    
    public interface LogFile{
        public final static String LOGNAME_PREFIX = "DBMigrationLog";
        public final static String KEY_STATUS = "Status";
        public final static String KEY_DETAIL = "Detail";
        public final static String KEY_DATE = "Date";
        public final static String KEY_CONDITION = "Condition";
        
        public final static String STATUS_SUCCESS = "Success";
        public final static String STATUS_FAIL = "Failure";

        public final static String DETAIL_SUCCESS = "DBMigration succeeded";
        public final static String DETAIL_FAIL = "DBMigration failed";
        public final static String COND_CMS_BACKUP_FAIL="_CMSCondTypeDBMigrationCMSBackupFail_";
        public final static String COND_OV_CONN_FAIL="_CMSCondTypeDBMigrationOVServerConnFail_";
        public final static String COND_MYSQL_CONN_FAIL="_CMSCondTypeDBMigrationMYSQLServerConnFail_";
        public final static String COND_OCM_CONFIG_FILE_FAIL="_CMSCondTypeDBMigrationOCMConfigFileFail_";
        public final static String COND_OCM_BACKUP_FILES_FAIL="_CMSCondTypeDBMigrationOCMBackupFilesFail_";
        public final static String COND_OV_DB_BACKUP_FAIL="_CMSCondTypeDBMigrationOVDBBackupFail_";
        public final static String COND_OV_DB_FETCH_FAIL="_CMSCondTypeDBMigrationOVDBRetrieveFail_";
        public final static String COND_CMS_RESTORE_FAIL="_CMSCondTypeDBMigrationOVDBRestoreFail_";
        public final static String COND_CMS_MIGRATION_FAIL="_CMSCondTypeDBMigrationFail_";
        public final static String COND_CMS_MIGRATION_SUCCCESS="_CMSCondTypeDBMigrationSucceed_";
    }
}
