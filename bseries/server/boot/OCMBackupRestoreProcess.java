
package com.calix.bseries.server.boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.calix.ems.database.DatabaseSessionFactory;
import com.calix.ems.security.CMSDESCipher;
import com.calix.system.common.log.Log;
//import com.calix.system.server.dbmodel.CalixConditionBase
import com.occam.ems.be.app.configuration.FtpOperationsUtil;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.mediation.protocol.ocm.transactioncommand.v1.misc.BackupRestoreCmd;

public class OCMBackupRestoreProcess {
    
    private static String userName = null;
    private static String passWord = null;

	public OCMBackupRestoreProcess(){
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String operationName= args[0];
		int argsLength = args.length;
		String operationType = "";
		// To get username and password specified by user if args =2 use default setting
		if(argsLength == 4)
		{
		    userName = args[2];
		    passWord = args[3];
		}
		try {
            Log.initialize("/config/log.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		FtpOperationsUtil ftpOpers = (FtpOperationsUtil)getDefaultFTPValuesObject();
		if (ftpOpers == null){
			System.out.println("Backup Restore Process failed");
			System.exit(1);
		}
		try {
			String ftpPath = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, OccamStaticDef.CMS_FTP_RESTORE_PATH);
			
		//	ftpOpers.setFtpPath(ftpPath); 
			if(operationName.equals("1")||operationName.equals("backup")){
				operationType = "backup";
				ftpPath = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, OccamStaticDef.CMS_FTP_BACKUP_PATH);
			}else{
				operationType = "restore";
				String restoreFileName= args[1];
				ftpPath = ResourceBundleUtil.getResString(ResourceBundleUtil.RES_COMMON, OccamStaticDef.CMS_FTP_RESTORE_PATH);
				if(restoreFileName==null || restoreFileName.equals("")){
					System.out.println("File name is required for restore");
					System.exit(1);
				}else
					ftpOpers.setRestoreFileName(restoreFileName);
			}
			if(ftpOpers.getFtpPath().endsWith("/"))
				 ftpPath = ftpOpers.getFtpPath()+ ftpPath;
			 else
				 ftpPath = ftpOpers.getFtpPath()+ "/" + ftpPath;
			ftpOpers.setFtpPath(ftpPath);
			
			new BackupRestoreCmd(ftpOpers,operationType);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			System.out.println("Backup Restore Process failed");
			System.exit(1);
		}
		
	}
	
	 private static FtpOperationsUtil getDefaultFTPValuesObject(){
		 Connection connection = null;
	     Statement statment = null;
	     ResultSet resultSet = null;
	     FtpOperationsUtil ftpOpers = new FtpOperationsUtil();
	     try {  
	            URL pUrl = OCMBackupRestoreProcess.class.getResource("/lib/cms/jdbc/hibernate.properties");
	            String pPath = pUrl.getPath();
	            Properties prop = loadProperties(pPath);
	            DatabaseSessionFactory.addSource("database/hibernate/system/system-hibernate.cfg.xml");
	            DatabaseSessionFactory.addSource("database/hibernate/ems/ems-hibernate.cfg.xml");
	            DatabaseSessionFactory.addConfigurationProperties(prop);
		        try {
		            Class.forName("org.postgresql.Driver");
		        } catch (ClassNotFoundException e) {
		            
		            System.out.println("Where is your PostgreSQL JDBC Driver? "+ "Include in your library path!");
		            e.printStackTrace();
		            
		        }
		        System.out.println("PostgreSQL JDBC Driver Registered!");
		        
		        try {
		            if(userName != null && passWord != null)
		                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", userName, passWord);
		            else
		                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","123456");
		            
		        } catch (SQLException e) {
		            
		            System.out.println("Connection Failed! Check output console");
		            e.printStackTrace();
		        }
		        String getFtpInfo = "SELECT * from  EMSBarFtpInfo";
	            statment = connection.createStatement();
	            resultSet = statment.executeQuery(getFtpInfo);
	            while (resultSet.next()) {
	                // Only one line
	                String password = null;
	                String serverIp = resultSet.getString("Host");
	                String userName = resultSet.getString("Username");
	                String ftpPath = resultSet.getString("b6fwftppath");
	                System.out.println("serverIp : "+serverIp);
	                System.out.println("userName : "+userName);
	                byte[] passwd = resultSet.getBytes("password");
	               
	                if (null != passwd) {
	                    // Call CMS package
	                	
	                	try{
	                		String pwd= new String(CMSDESCipher.getInstance().decrypt(passwd));
	                		password=pwd;
	                		System.out.println("Password decryption " + password);
                        }catch(Exception ex){
                            System.out.println("Password decryption failed!");
                            ex.printStackTrace();
                            return null;
                        }                                      
	                }
	                //Set output
	                ftpOpers.setHost(serverIp);
	                ftpOpers.setPassword(password);
	                ftpOpers.setUname(userName);
	                ftpOpers.setFtpPath(ftpPath);
	            }            
		        System.out.println("Connection established to DB");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	    finally{
	        try {
	            if(resultSet != null)
	                resultSet.close();
	            if(statment != null)
	                statment.close();
	            if(connection != null)
	                connection.close();  
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }
	     return ftpOpers;
	 }
	 
	  public static Properties loadProperties( String filename) throws Exception{
          Properties prop = new Properties();
          FileInputStream in = null;
          try {
            File file = new File(filename);
            in = new FileInputStream(file);
            prop.load(in);
          } catch (Exception x) {
            throw new Exception(x);
          } finally {
            if (in != null) {
              try { in.close(); } catch (IOException e) {}
            }
          }
          return prop;
      }

}
