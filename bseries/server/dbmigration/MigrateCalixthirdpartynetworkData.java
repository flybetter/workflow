/*
 * MigrateCalixthirdpartynetworkData.java
 *
 * Created on October 22, 2011, 9:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.server.dbmigration;

import com.calix.ems.util.TLVHelper;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;
import com.calix.thirdparty.server.dbmodel.CalixThirdPartyNetwork;
import com.calix.thirdparty.server.dbmodel.ThirdPartyTlvConstants;

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
public class MigrateCalixthirdpartynetworkData extends MigrateOVData{
    private Connection connection = null;
    /** Creates a new instance of MigrateCalixthirdpartynetworkData */
    public MigrateCalixthirdpartynetworkData() {
        super();
        System.out.println("Calixthirdpartynetwork Data Instance created");
    }

    public void migrateOVData(){
        migrateThirdPary();
        closeConnection();
    }
    private void migrateThirdPary(){
        Statement stmt = null;
        ResultSet resultSet = null;
        String profileId= "91";
        List<CalixThirdPartyNetwork> thirdPartyLis = new ArrayList<CalixThirdPartyNetwork>();
        try {
        	//chma: for ticket CMS-7534, we should get the third party device profile according to the device name.
            connection = getConnection();
            String profieQuery = "SELECT addressid FROM CALIXTHIRDPARTYPROFILE WHERE DEVICENAME LIKE 'valere' OR DEVICENAME LIKE 'Valere'";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(profieQuery);
            if(resultSet != null&&resultSet.next()){
            	int profile = resultSet.getInt("addressid");
            	System.out.println("Valere profileid = "+profile);
            	profileId = String.valueOf(profile);
            	closeStmtAndResultSet(stmt,resultSet); 
            }else{
            	System.out.println("cann't load thirdparty profile from db. Migration third party device will quit.");
            	closeStmtAndResultSet(stmt,resultSet); 
            	return;
            }  
            String groupQuery = "SELECT * FROM EQUIPMENT where equipmenttype ='ValerePower' or equipmenttype = 'Micropack'";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(groupQuery);
            
            if (resultSet != null){
            	
                while (resultSet.next()) {
                    System.out.println("----------------------------------------------------------");
                    String dbIdentity = "NTWK-" + resultSet.getString("name");
                    String ip = resultSet.getString("name");
                    
                    System.out.println("dbIdentity " + dbIdentity);
                    
                    CalixThirdPartyNetwork profile = 
                    		(CalixThirdPartyNetwork) TLVHelper.createObject(ThirdPartyTlvConstants.CalixThirdPartyNetwork);
                    profile.setDbIdentity(dbIdentity);
                    profile.setIPAddress1(ip);
                    profile.setSID(dbIdentity);
                    profile.setRegion("REG-" + MigrationUtils.reConfigureRegion(resultSet.getString("location")) + " NG");
                    profile.setSnmpPort(161);
                    profile.setprofile(new GenericCMSAid(SystemTlvConstants.tSNMPDeviceProfileAid, profileId));// dummy, need
                    profile.setDisplayName(resultSet.getString("equipmentsysdescr"));
                    
                    profile.setUID(0);
                    profile.setAutoConnect(1);
                    profile.setWidth(100);
                    profile.setHeight(50);
                    profile.setport(50000);
                    profile.setHTTPPort(80);
                    profile.setHTTPSPort(443);
                    profile.setsynchronizeTime(1);
                    profile.setTimezone(420);
                    profile.setUpdateType(1);
                    profile.setConnectionState(0);
                    profile.setCacheEnabled(1);

					thirdPartyLis.add(profile);
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);           
        }
        commit(thirdPartyLis);
    }

}
