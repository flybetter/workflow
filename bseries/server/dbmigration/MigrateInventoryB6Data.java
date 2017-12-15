package com.calix.bseries.server.dbmigration;

import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.exception.EMSException;
import com.calix.system.common.log.Log;
import com.calix.system.common.protocol.tlv.ResultCode;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
*
* @author hzhang
*/
public class MigrateInventoryB6Data extends MigrateOVData {

    private Connection connection = null;
    /** Creates a new instance of MigrateSecurityData */
    public MigrateInventoryB6Data() {
        super();
        System.out.println("Security Data Instance created");
    }
    
    public void migrateOVData(){
        migrateCalixB6Device();
        closeConnection();
    }

	private void migrateCalixB6Device() {
		
        ICMSDatabase database = null;
        DbTransaction txn = null;
        B6Settings b6setting = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            
            b6setting = (B6Settings)database.readSingletonCMSObject(B6Settings.TYPE_NAME, txn);
            txn.commit();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            if (txn != null && txn.isActive())
                txn.abort();
        }
		
        Statement stmt = null;
        ResultSet resultSet = null;
        List<CalixB6Device> calixB6DeviceList = new ArrayList<CalixB6Device>();
        try {
            connection = getConnection();
          String groupQuery = "select * from EQUIPMENT where equipmenttype LIKE 'BLC%' or equipmenttype = 'Unknown'";
          stmt = connection.createStatement();
          resultSet = stmt.executeQuery(groupQuery);
			
          if (resultSet != null){
              // EMSUserGroup emsGroup = new EMSUserGroup();
              while (resultSet.next()) {
                  System.out.println("----------------------------------------------------------");
                  String ip = resultSet.getString("name");
                  String sysdesc = resultSet.getString("EQUIPMENTSYSNAME");
                  String dbIdentity = "NTWK-" + getSysName(ip, sysdesc);
//                  System.out.println("dbIdentity " + dbIdentity );
                    
                    CalixB6Device calixB6Device = new CalixB6Device();
                    calixB6Device.setDbIdentity(dbIdentity);
                    calixB6Device.setSID(dbIdentity);
                    calixB6Device.setIPAddress1(ip);
                    
                    calixB6Device.setModel(resultSet.getString("EQUIPMENTTYPE"));
                    calixB6Device.setMACAddress(resultSet.getString("entitymacaddress"));
                    calixB6Device.setSerialNumber(resultSet.getString("entityserialnum"));
                    calixB6Device.setSWVersion(resultSet.getString("entitysoftwarerev"));
                    calixB6Device.setManufactureDate(resultSet.getString("entitymfgdate"));
                    calixB6Device.setHWVersion(resultSet.getString("entityhardwarerev"));                    
             
                    calixB6Device.setRegion("REG-" + MigrationUtils.reConfigureRegion(resultSet.getString("location")) + " NG");
                    calixB6Device.setprofile(new GenericCMSAid(SystemTlvConstants.tSNMPDeviceProfileAid, "51"));
                    calixB6Device.setAutoConnect(1);
                    // CMS-9837  set default time zone as GMT -8, refer to C7's implementation.
                    calixB6Device.setTimeZone(420);
                    if(b6setting!= null){
                        calixB6Device.setReadCommunity(b6setting.getreadCommunity());
                        calixB6Device.setWriteCommunity(b6setting.getwriteCommunity());
                        calixB6Device.setNetworkLoginName(b6setting.getCliUsername());
                        calixB6Device.setNetworkLoginPassword(b6setting.getCliPassword());
                        calixB6Device.setWebUsername(b6setting.getWebUsername());
                        calixB6Device.setWebPassword(b6setting.getWebPassword()); 
                        calixB6Device.setEnablePassword(b6setting.getEnablePassword());
                    }else{
                        calixB6Device.setReadCommunity("public");
                        calixB6Device.setWriteCommunity("private");
                        calixB6Device.setNetworkLoginName("cli");
                        calixB6Device.setNetworkLoginPassword("frpocc");
                        calixB6Device.setWebUsername("Administrator");
                        calixB6Device.setWebPassword("razor123");  
                        calixB6Device.setEnablePassword("ocenable");
                    }
                    calixB6DeviceList.add(calixB6Device);
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);
        }
        
        commit(calixB6DeviceList);
		
	}
	
	// CMS-6806
	//re-use some of  B6RangeDiscoveryHandler.getB6DeviceName()
	public String getSysName(String deviceIp, String sysName) {
		if (sysName == null) {
			sysName = "";
		}
		// Replace all "-" with "_".
		if (sysName.indexOf("-") != -1) {
			sysName = sysName.replaceAll("-", "_");
		}
		// Replace all non-word character with " ".
		if (!sysName.matches("^[a-z0-9A-Z_ \\.]+$")) {
    		// \w A word character: [a-zA-Z_0-9] 
    		// \W A non-word character: [^\w] 
    		sysName = sysName.replaceAll("[\\W&&[^ ]&&[^\\.]]", " ");
    	}
		return sysName + "_" + deviceIp;
	}

}

