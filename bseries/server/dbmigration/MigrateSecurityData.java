/*
 * MigrateSecurityData.java
 *
 * Created on October 18, 2011, 9:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.server.dbmigration;


import com.calix.ems.model.EMSPrincipal;
import com.calix.ems.server.dbmodel.EMSUser;
import com.calix.ems.server.dbmodel.EMSUserGroup;
import com.calix.ems.server.dbmodel.EmsTlvConstants;
import com.calix.ems.util.TLVHelper;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author brao
 */
public class MigrateSecurityData extends MigrateOVData{
    private ResourceBundle resBundle = null;
    private Connection connection = null;
    private List usrGrpLst = new ArrayList();
    /** Creates a new instance of MigrateSecurityData */
    public MigrateSecurityData() {
        super();
        System.out.println("Security Data Instance created");
    }

    public void migrateOVData(){
        try{
            resBundle = ResourceBundle.getBundle("dbmigration", Locale.US);
        }catch(Exception e){
            e.printStackTrace();
        }
        migrateUsrGroup();
        migrateUsrs();
        closeConnection();
    }
    private void migrateUsrGroup(){
        Statement stmt = null;
        ResultSet resultSet = null;
        List userGroupLst = new ArrayList();
        try {
            connection = getConnection();
            String groupQuery = "SELECT * FROM VIEWSTOGROUPTABLE";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(groupQuery);
            
            if (resultSet != null){
                // EMSUserGroup emsGroup = new EMSUserGroup();
                while (resultSet.next()) {
                    System.out.println("----------------------------------------------------------");
                    String groupName = resultSet.getString("groupname");
                    String viewName = resultSet.getString("viewname");
                    System.out.println("GroupName " + groupName + " : viewname " + viewName);
                    if (!groupName.equalsIgnoreCase("Administrators")){
                        usrGrpLst.add(groupName);
                        EMSUserGroup emsUserGroup = createUserGroup(groupName,viewName);
                        userGroupLst.add(emsUserGroup);
                    }
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);           
        }
        commit(userGroupLst);
    }
    private void migrateUsrs(){
        Statement stmt = null;
        ResultSet resultSet = null;
        List userLst = new ArrayList();
        
        try {
            connection = getConnection();
            String userDetailsQuery = "SELECT * FROM USERDETAILS";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(userDetailsQuery);
            
            if (resultSet != null){                
                while (resultSet.next()) {
                    System.out.println("----------------------------------------------------------");
                    String oldUsrName = resultSet.getString("loginname");                    
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String addr1 = resultSet.getString("addr1");
                    String addr2 = resultSet.getString("addr2");
                    String addr3 = resultSet.getString("addr3");
                    String mobile = resultSet.getString("mobilework");
                    String phoneHome = resultSet.getString("phonehome");
                    String email1 = resultSet.getString("emailwork");
                    String email2 = resultSet.getString("emailhome");
                    
                    //CMS-7941
                    String username;
                    if(oldUsrName.length()>10){
                    	username = oldUsrName.substring(0, 10);
                        System.out.println("Length for userName is bigger than 10. Now change it to " + username);
                        System.out.println("UserNameChange-- old name:" +  oldUsrName + ", now change it to: " + username);
                    }else{
                    	username = oldUsrName;
                    }
                    System.out.println("UserName " + username);
                    
                    if(checkDuplicate(new GenericCMSAid(SystemTlvConstants.EMSAid, username), userLst)){
                        System.out.println("UserNameIgnore-- ignore name: " +  oldUsrName + ", because it is duplicate with others after truncate. ");
                        continue;
                    }
                    
                    EMSUser user = (EMSUser) TLVHelper.createObject(EmsTlvConstants.EMSUser);
                    user.setIdentityValue("USR-" + username);
                    user.setUserName(new GenericCMSAid(SystemTlvConstants.EMSAid, username));                    
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    if (mobile != null && !(mobile.equals("NULL")))
                        user.setDayPhone(mobile);
                     if (phoneHome != null && !(phoneHome.equals("NULL")))
                        user.setEveningPhone(phoneHome);
                    if (email1 != null && !(email1.equals("NULL")))
                        user.setEmail1(email1);
                    if (email2 != null && !(email2.equals("NULL")))
                        user.setEmail2(email2);
                    if (addr1 != null && !(addr1.equals("NULL")))
                        user.setAddress(addr1);
                    if (addr2 != null && !(addr2.equals("NULL"))){
                        user.setAddress(user.getAddress() + "," + addr2);
                    }
                    if (addr3 != null && !(addr3.equals("NULL"))){
                        user.setAddress(user.getAddress() + "," + addr3);
                    }
                    //user.setTypeName();
                    user.setAlarmFilter(new Integer(1));
                    user.setThresholdFilter(new Integer(1));
                    user.setEventFilter(new Integer(1));
                    String usrPasswd = getPasswordForUser(oldUsrName);
                    if (usrPasswd == null){
                        System.out.println("ERROR: Passwd for userName: " + username + " is null. Ignore it. ");
                        continue;
                    }else {
                        user.setPassword(EMSPrincipal.encryptPWD(usrPasswd.getBytes()));
                    }
                    
                    String usrGroup = getUserGroupForUser(oldUsrName);
                    if (usrGroup != null){
                        user.setUserGroup(usrGroup);
                    }
                    int passwdExpiryTime = getPasswordExpiryForUser(oldUsrName);
                    user.setPasswordExpiredIn(new Integer(passwdExpiryTime));
                    
                    userLst.add(user);
                }
            }           
  
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);            
        }
        commit(userLst);
    }
    private boolean checkDuplicate(GenericCMSAid genericCMSAid, List userLst) {
    	  for(int   i=0;i<userLst.size();i++){  
    	      EMSUser user=(EMSUser)userLst.get(i);
    	      if(genericCMSAid!= null && genericCMSAid.equals(user.getUserName())){
    	    	  return true;
    	      }
    	  }
		return false;
	}

	private String getPasswordForUser(String userName){
        Statement stmt = null;
        ResultSet resultSet = null;
        String usrPasswd = null;
        try {
            connection = getConnection();
            String userPasswordQuery = "SELECT * FROM USERPASSWORDTABLE WHERE USERNAME = '" + userName + "'";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(userPasswordQuery);
            
            if (resultSet != null){
                while (resultSet.next()) {
                    
                    String password = resultSet.getString("password");
                    
                    System.out.println("UserName " + userName + " password " + password);
                    if (password != null && !(password.equals("NULL"))){
                        String convertedPd = null;
                        
                        try{
                            convertedPd = Coding.convertFromBase(password);
                            System.out.println("After conversion: UserName root :  password " + password + " convertedPd " + convertedPd);
                            
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        if (convertedPd != null)
                            usrPasswd = convertedPd;
                        else
                            usrPasswd = userName;
                    }else{
                    	System.out.println("password is null for user: " + userName);
                    }
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);           
        }
        return usrPasswd;
    }
    private String getUserGroupForUser(String userName){
        Statement stmt = null;
        ResultSet resultSet = null;
        String usrGroupName = null;
        try {
            connection = getConnection();
            String userGroupQuery = "SELECT * FROM USERGROUPTABLE WHERE USERNAME = '" + userName + "'";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(userGroupQuery);
            
            if (resultSet != null){
                while (resultSet.next()) {
                    
                    String groupName = resultSet.getString("groupname");
                    if (usrGrpLst.contains(groupName)){
                        System.out.println("UserName " + userName + " groupName " + groupName);
                        if (groupName != null && !(groupName.equals("NULL")))
                            usrGroupName = "USRGRP-"+groupName;
                    }else if (groupName.equalsIgnoreCase("Administrators")){
                        usrGroupName = "USRGRP-root-Administrators";
                    }
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);            
        }
        return usrGroupName;
    }
    private int getPasswordExpiryForUser(String userName){
        Statement stmt = null;
        ResultSet resultSet = null;
        int passwdExpiryTime = 0;
        try {
            connection = getConnection();
            String userPasswordExpiryQuery = "SELECT * FROM USERCONFTABLE WHERE USERNAME = '" + userName + "'";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(userPasswordExpiryQuery);
            
            if (resultSet != null){
                while (resultSet.next()) {
                    
                    passwdExpiryTime = resultSet.getInt("passwordexpirytime");
                    
                    System.out.println("UserName " + userName + " passwdExpiryTime " + passwdExpiryTime);                    
                                    
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);           
        }
        return passwdExpiryTime;
    }
    private EMSUserGroup createUserGroup(String groupName,String viewName){
        Map permissionMap = getUserGroupPermission(viewName);
        //EMSUserGroup emsUserGroup = new EMSUserGroup();
        EMSUserGroup emsUserGroup = createUserGroup();
        emsUserGroup.setDbIdentity("USRGRP-" + groupName);
        emsUserGroup.setFAULT((Integer)permissionMap.get(DBMigrationConstants.FAULT_MODULE));
        emsUserGroup.setAdministrateEMS((Integer)permissionMap.get(DBMigrationConstants.ADMIN_MODULE));
        emsUserGroup.setCONFIG((Integer)permissionMap.get(DBMigrationConstants.CONFIG_MODULE));
        emsUserGroup.setPERFORMANCE((Integer)permissionMap.get(DBMigrationConstants.PERFORMANCE_MODULE));
        emsUserGroup.setManageUsers((Integer)permissionMap.get(DBMigrationConstants.SECURITY_MODULE));
        emsUserGroup.setTypeName("EMSSecurityUserGroup");
        return emsUserGroup;
    }
    private static EMSUserGroup createUserGroup() {
        Integer full = new Integer(Integer.MAX_VALUE);

        EMSUserGroup ug = (EMSUserGroup) TLVHelper.createObject(EmsTlvConstants.EMSSecurityUserGroup);
        ug.setSecurityDomain("SECUDOM-root");
        ug.setAdministrateEMS(new Integer(0));
        ug.setC7Test(full);
        ug.setC7Maintenance(full);
        ug.setC7Provision(full);
        ug.setC7Security(full);
        ug.setC7System(full);
        ug.setE5400Security(full);
        ug.setAeSecurity(new Integer(1));
        ug.setE5100Security(new Integer(1));
        ug.setMaxLoginSession(new Integer(3));
        return ug;
    }
    private HashMap getUserGroupPermission(String viewName){
        HashMap permMap = new HashMap();
        Statement stmt_OperName = null;
        ResultSet resultSet_OperName = null;
        int faultPerm = 0;
        int adminPerm = 0;
        int configPerm = 0;
        int perfPerm = 0;
        int securityPerm = 0;
        try {
            
            String operationNameQuery = "SELECT * FROM VIEWTOOPERATIONSTABLE WHERE VIEWNAME = '" + viewName + "'";
            stmt_OperName = connection.createStatement();
            resultSet_OperName = stmt_OperName.executeQuery(operationNameQuery);
            
            if (resultSet_OperName != null){
             
                while (resultSet_OperName.next()) {
                    String operationName = resultSet_OperName.getString("operationsname");
                    int operType = resultSet_OperName.getInt("operationstype");
            
                    if (checkModule(DBMigrationConstants.FAULT_MODULE,operationName)){
              
                        faultPerm = faultPerm | operType;
                    }else if (checkModule(DBMigrationConstants.CONFIG_MODULE,operationName)){
           
                        configPerm = configPerm | operType;
                    }else if (checkModule(DBMigrationConstants.ADMIN_MODULE,operationName)){
               
                        adminPerm = adminPerm | operType;
                    }else if (checkModule(DBMigrationConstants.PERFORMANCE_MODULE,operationName)){
               
                        perfPerm = perfPerm | operType;
                    }else if (checkModule(DBMigrationConstants.SECURITY_MODULE,operationName)){
                
                        securityPerm = securityPerm | operType;
                    }
                    
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt_OperName,resultSet_OperName);
        }
        System.out.println("f : " + faultPerm + " c: " + configPerm + " a: " + adminPerm + " p: " + perfPerm + " s: " + securityPerm);
        
        permMap.put(DBMigrationConstants.FAULT_MODULE, new Integer(getOVCMSPermissionMapping(faultPerm)));
        permMap.put(DBMigrationConstants.CONFIG_MODULE, new Integer(getOVCMSPermissionMapping(configPerm)));
        permMap.put(DBMigrationConstants.ADMIN_MODULE, new Integer(getOVCMSPermissionMapping(adminPerm)));
        permMap.put(DBMigrationConstants.PERFORMANCE_MODULE, new Integer(getOVCMSPermissionMapping(perfPerm)));
        permMap.put(DBMigrationConstants.SECURITY_MODULE, new Integer(getOVCMSPermissionMapping(securityPerm)));
        
        return permMap;
    }
//    private boolean isFaultModule(String operationName){
//        if (("Surveillance".equals(operationName)) ||
//                ("Filter Manager".equals(operationName)) ||
//                ("Current Alarms".equals(operationName)) ||
//                ("Event History".equals(operationName)) ||
//                ("OSS Manager".equals(operationName)) ||
//                ("Event Template Manager".equals(operationName)) ||
//                ("Show Alarms".equals(operationName)) ||
//                ("Show Events".equals(operationName))){
//            return true;
//        }
//        return false;
//    }
//    private boolean isConfigurationModule(String operationName){
//        if (("Network Services".equals(operationName)) ||
//                ("Inventory".equals(operationName)) ||
//                ("Topology".equals(operationName)) ||
//                ("Authentication Management".equals(operationName)) ||
//                ("Chassis Management".equals(operationName)) ||
//                ("ONT Collection Manager".equals(operationName)) ||
//                ("GPON Management".equals(operationName)) ||
//                ("Device Group Manager".equals(operationName)) ||
//                ("Equipment Inventory".equals(operationName)) ||
//                ("Equipment Discovery Wizard".equals(operationName)) ||
//                ("CommitAll".equals(operationName)) ||
//                ("System Services".equals(operationName)) ||
//                ("Schedule Manager".equals(operationName)) ||
//                ("Service Management".equals(operationName)) ||
//                ("Service Definition".equals(operationName)) ||
//                ("Service Package Manager".equals(operationName)) ||
//                ("Profile Manager".equals(operationName)) ||
//                ("ACL Manager".equals(operationName)) ||
//                ("Service Activation".equals(operationName)) ||
//                ("DSL Service Summary".equals(operationName)) ||
//                ("Fiber Service Summary".equals(operationName)) ||
//                ("SLA Manager".equals(operationName)) ||
//                ("Service Status".equals(operationName)) ||
//                ("DHCP Profile Manager".equals(operationName)) ||
//                ("OS Manager".equals(operationName))){
//            return true;
//        }
//        return false;
//    }
//    private boolean isAdminModule(String operationName){
//        if (("Administrative Operation".equals(operationName)) ||
//                ("System Services".equals(operationName)) ||
//                ("Operation Settings".equals(operationName)) ||
//                ("Scope Settings".equals(operationName)) ||
//                ("Services".equals(operationName)) ||
//                ("Shutdown Web NMS Server".equals(operationName)) ||
//                ("Configure Log Levels".equals(operationName)) ||
//                ("Cut Through".equals(operationName)) ||
//                ("Reports".equals(operationName)) ||
//                ("Configuration".equals(operationName)) ||
//                ("Group Operations".equals(operationName))){
//            return true;
//        }
//        return false;
//    }
//    private boolean isPerformanceModule(String operationName){
//        if (("Performance".equals(operationName)) ||
//                ("Data Collection Manager".equals(operationName)) ||
//                ("Threshold Configuration Manager".equals(operationName)) ||
//                ("Graph Manager".equals(operationName))){
//            return true;
//        }
//        return false;
//    }
//    private boolean isSecurityModule(String operationName){
//        if (("User Administration".equals(operationName)) ||
//                ("Security Administration".equals(operationName))){
//            return true;
//        }
//        return false;
//    }
   private boolean checkModule(String moduleName,String operationName){
       String operations = resBundle.getString(moduleName);
     //    System.out.println("Module : " + moduleName + " operations " + operations);
        if (operations != null){
            String[] operationsArr = operations.split(",");
            for (int i = 0 ; i < operationsArr.length;i++){
                if (operationsArr[i].length() > 0){
                    String operation = operationsArr[i];
                    if (operation.equalsIgnoreCase(operationName))
                        return true;
                }
            } 
        }
         return false;
   } 
   private int getOVCMSPermissionMapping(int ov_perm){
       switch(ov_perm){
           case 0 : return 0;
           case 1 : return 2;
       }
       return 1;
   }
}