/*
 * OVCMSDBTableMigration.java
 *
 * Created on October 18, 2011, 4:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.server.dbmigration;

import com.calix.ems.database.DatabaseSessionFactory;
import com.calix.ems.mbeans.SchemaManager;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.common.log.Log;
import com.calix.system.gui.model.CalixConditionBase;

import java.io.*;
import java.util.*;
import java.util.Locale;
import java.util.ResourceBundle;

import com.objectsavvy.base.persistence.meta.BaseScalarValueType;
import com.objectsavvy.base.persistence.meta.TypeLoader;
import com.objectsavvy.base.util.Config;

/**
 *
 * @author brao
 */
public class OVCMSDBTableMigration {
    
    /** Creates a new instance of OVCMSDBTableMigration */
    public OVCMSDBTableMigration() {
        
    }
    public static void main(String[] args) {
        try {
            Log.initialize("/config/log.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            Config.init(CalixConstants.FACTORY_CONFIG_FILE_EMS);
            TypeLoader.load(
                    new OVCMSDBTableMigration().getClass().getResource(
                    CalixConstants.VALUE_DEF_FILE_EMS), null);
            TypeLoader.endLoading();
            
            DatabaseSessionFactory
                    .addSource("database/hibernate/system/system-hibernate.cfg.xml");
            DatabaseSessionFactory
                    .addSource("database/hibernate/ems/ems-hibernate.cfg.xml");
            DatabaseSessionFactory
                    .addSource("database/hibernate/bseries/bseries-hibernate.cfg.xml");
            DatabaseSessionFactory
                    .addSource("database/hibernate/ae/ae-hibernate.cfg.xml");
            DatabaseSessionFactory
                    .addSource("database/hibernate/thirdparty/thirdparty-hibernate.cfg.xml");
            DatabaseSessionFactory
                    .addSource("database/hibernate/msap/msap-hibernate.cfg.xml");
            
            BaseScalarValueType.disableValueCaching();
            CalixConditionBase.initializeStaticConstants();
            new SchemaManager().start();
            
            ResourceBundle resBundle = ResourceBundle.getBundle(
                    DBMigrationConstants.DBMIG_PROPERTY_FILE_NAME, Locale.US);
            String migrationClasses = resBundle
                    .getString(DBMigrationConstants.DB_MIG_CLASSES);
            
            if (migrationClasses != null) {
                String[] migClassArr = migrationClasses.split(",");
                for (int i = 0; i < migClassArr.length; i++) {
                    if (migClassArr[i].length() > 0) {
                        String migClassName = migClassArr[i];
                        MigrateOVData migOVDataInstance = (MigrateOVData) Class
                                .forName(migClassName).newInstance();
                        if (migOVDataInstance != null) {
                            migOVDataInstance.migrateOVData();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Migration failed, prepare to write log file");
            writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_STATUS, DBMigrationConstants.LogFile.STATUS_FAIL);
            writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_DETAIL, e.getMessage() == null ? DBMigrationConstants.LogFile.DETAIL_FAIL : e.getMessage());
            writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_DATE, new Date().toString());    
            writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_CONDITION, DBMigrationConstants.LogFile.COND_CMS_MIGRATION_FAIL);
            System.out.println("Migration Finished.");
            System.exit(1);
        }
        
        //Migration Finished without error        
        System.out.println("Migration is done without error.");
        
        writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_STATUS, DBMigrationConstants.LogFile.STATUS_SUCCESS);
        writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_DETAIL, DBMigrationConstants.LogFile.DETAIL_SUCCESS);
        writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_DATE, new Date().toString());
        writeDBMigrationLog(DBMigrationConstants.LogFile.KEY_CONDITION, DBMigrationConstants.LogFile.COND_CMS_MIGRATION_SUCCCESS);
        //CAUTION: Signal for script. If changed, please change migrateov.sh as well.
        System.out.println("Migration Finished.");
        System.exit(0);
    }
    
    private static void writeDBMigrationLog(String key, String value) {
        
        String path = ClassLoader.getSystemResource("").getPath() + "/" + DBMigrationConstants.LogFile.LOGNAME_PREFIX + ".properties";
//        System.out.println("path is: " + path);
        writeProperties(path, key, value);
    }
    
    /**
     * Check does properties file exist
     * @param filePrefix
     * @return
     * @Author: jawang
     * @Date: Feb 3, 2012
     */
    public static boolean isPropertiesExists(String filePrefix) {
        String path = ClassLoader.getSystemResource("").getPath() + "/" + filePrefix + ".properties";
        
//        System.out.println("in isPropertiesExists(). path=" + path);
        File f = new File(path);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Write Key=Value into properties file
     * @param filePath
     * @param key
     * @param value
     * @Author: jawang
     * @Date: Feb 3, 2012
     */
    public static void writeProperties(String filePath, String key, String value) {
        
//        System.out.println("in writeProperties(). filePath=" + filePath + ";key=" + key + ";value=" + value);
        Properties prop = new Properties();
        InputStream fis = null;
        OutputStream fos = null;
        try {
            //Create file if not exists
            File f = new File(filePath);
            if (!f.exists()) {
                System.out.println(filePath + " does not exist. create it.");
                f.createNewFile();
            }
            
            fis = new FileInputStream(filePath);
            prop.load(fis);
            fos = new FileOutputStream(filePath);
            prop.setProperty(key, value);
            prop.store(fos, "DB migration log");
            fos.flush();
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for " + key + " error");
            e.printStackTrace();
        } finally {
            try {
                if (null != fos)
                    fos.close();
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get date from properties file
     * @param fileName
     * @param key
     * @return Value of Key
     * @Author: jawang
     * @Date: Feb 3, 2012
     */
    public static String readProperties(String fileName, String key) {
        
//        System.out.println("in readProperties(). filename=" + fileName + "key=" + key);
        ResourceBundle resBundle = ResourceBundle.getBundle(fileName, Locale.US);
        try {
            return resBundle.getString(key);
        } catch (MissingResourceException e) {
            // Key is not exist.
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

