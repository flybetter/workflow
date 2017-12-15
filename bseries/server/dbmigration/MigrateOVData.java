/*
 * MigrateOVData.java
 *
 * Created on October 18, 2011, 9:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.server.dbmigration;

import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.system.common.log.Log;
import com.calix.system.server.dbmodel.BaseEMSObject;
import com.calix.system.server.dbmodel.CMSObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author brao
 */
public class MigrateOVData {
    
    private Connection connection = null;
    
    /** Creates a new instance of MigrateOVData */
    public MigrateOVData() {
        initialize();
    }
    
    private void initialize(){
        if (connection != null) {
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            
            System.out.println("Where is your PostgreSQL JDBC Driver? "+ "Include in your library path!");
            e.printStackTrace();
            return;
            
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","123456");
            
        } catch (SQLException e) {
            
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
            
        }
        System.out.println("Connection established to DB");
        
    }
    public Connection getConnection(){
        return connection;
    }
    
    public void closeStmtAndResultSet(Statement stmt,ResultSet resultSet){
        try {
            if(stmt != null){
                stmt.close();
            }
            if(resultSet != null){
                resultSet.close();
            }
            
        } catch(SQLException sq) {
            sq.printStackTrace();
        }
    }
    
    public void commit(List baseLst){        
       
        com.calix.ems.database.ICMSDatabase db = com.calix.ems.database.DatabaseManager.getCMSDatabase();
        if (db == null){
            System.out.println("DB null");
            return;
        }
        com.calix.ems.database.DbTransaction tx = db.getTransaction("ems",com.calix.ems.database.DbTransaction.UPDATE);
        if (tx == null){
            System.out.println("Tx null");
            return;
        }
        try {
            tx.begin();
            for(int n = 0;n<baseLst.size(); n++)  {
                try{
                    CMSObject cmsObj = ((BaseEMSObject)baseLst.get(n)).doLoad(tx);
					if (cmsObj != null) {
						System.out.println( "Object " + cmsObj.getIdentityValue() + " exists");
						continue;
					}
                }catch(Exception e){
                	if(baseLst.get(n) instanceof CalixB6Device)
                	{
                		CalixB6Device device = (CalixB6Device)baseLst.get(n);
                        System.out.println("Object " + device.getIdentityValue() + " does not exist");
                	}else{
                        System.out.println("Object does not exist");	
                	}
                }
                
                ((BaseEMSObject)baseLst.get(n)).doCreate(tx);
                 
            }

            tx.commit();
            Log.db().warn("Commit is complete!!");
            tx = null;
        }catch(Exception e) {
            Log.db().warn("Exception = "+e,e);
            e.printStackTrace();
            tx.abort();
        }finally {
          if (tx != null && tx.isActive()) {
              tx.abort();
          }
          tx = null;
      }
    }
    
    public void closeConnection(){
        try{
            connection.close();
        } catch(SQLException sq) {
            sq.printStackTrace();
        }        
    }
    public void migrateOVData(){        
    }
}
