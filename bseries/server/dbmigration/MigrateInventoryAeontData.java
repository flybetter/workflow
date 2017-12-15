package com.calix.bseries.server.dbmigration;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.ems.server.util.MetaDataUtil;
import com.calix.system.common.log.Log;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

/**
*
* @author hzhang
*/
public class MigrateInventoryAeontData extends MigrateOVData {

    private ResourceBundle resBundle = null;
    private Connection connection = null;
    public static final String DEVICE_TYPE_AE = "ae";
	private HashMap<String, String> sysdesc2ontprofId = new HashMap<String, String>();
    Statement stmt = null;
    
    /** Creates a new instance of calix ae ont */
    public MigrateInventoryAeontData() {
        super();
        try{
            resBundle = ResourceBundle.getBundle(DBMigrationConstants.DBMIG_PROPERTY_FILE_NAME, Locale.US);
            getONTTypes();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("CalixAeOnt Data Instance created");
    }
    
    private void getONTTypes() {
		String supportedONTTypes = resBundle
				.getString(DBMigrationConstants.SUPPORTED_ONT_TYPES);
    	if(supportedONTTypes != null){
    		String[] types = supportedONTTypes.split(",");
			for (int i = 0; i < types.length; i++) {
				if (types[i] != null && types[i].length() > 0) {
					String ID = resBundle.getString("ON" + types[i]);
					if(ID != null && ID.length() > 0){
						sysdesc2ontprofId.put(types[i], ID);
					}
				}
			}
    	}
	}

	public void migrateOVData(){
    	
    	preMigrateOnt();
    	
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            //String groupQuery = "SELECT * FROM EQUIPMENT where equipmenttype LIKE 'ON%'";
            //To exclude GPON ONT whose name with suffix _PONx/x.
            String groupQuery = "SELECT * FROM EQUIPMENT where entitymacaddress is not null and entitymacaddress <> '' and equipmenttype LIKE 'ON%'";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(groupQuery);
            
            if (resultSet != null){
                while (resultSet.next()) {
                    System.out.println("----------------------------------------------------------");
                    String dbIdentity = "NTWK-" + resultSet.getString("name");
                    String ip = resultSet.getString("name");
                    
                    System.out.println("dbIdentity " + dbIdentity );
                    
                    Ae_Ont ont = new Ae_Ont();
                    ont.setIpAddress(ip);
                    ont.setExternalProvisioned(2);
                    // ont.setserno(resultSet.getString("entityserialnum"));
                    String serno = resultSet.getString("entityserialnum");
                    
                    if(serno != null && !serno.contains("N/A")){
                        ont.setserno(serno);
                        ont.setaeontid("SN"+serno);
                    }else{
                        String mac = resultSet.getString("entitymacaddress");
                        // no SN, using MAC
                        if (null != mac){
                            //Mac is used to be this: 00 02 86 10 e8 80
                            String tmp = mac.replaceAll(" ", "").toUpperCase();
                            ont.setserno(tmp);
                        } else {
                            // Mac is null
                            ont.setserno("SerialNum" + new Random().nextInt(10000));
                        }
                    }
                    
                    ont.setvendor("CXNK");
                    ont.setdeviceProfile(new GenericCMSAid(SystemTlvConstants.tSNMPDeviceProfileAid, "42"));
                    ont.setFirmwareVersion(resultSet.getString("entitysoftwarerev"));
                    ont.setRegion("REG-" + MigrationUtils.reConfigureRegion(resultSet.getString("location")));
                    ont.setpwe3config(1);
                    ont.settimezone("US/Pacific");
                    ont.setSyncStatus(2);
                    ont.setBandwidthMetering(1);// to check
                    
                    //James test for new logic, to set model
                    ont.setModel(resultSet.getString("equipmenttype"));
                    
                    String sysDesc = resultSet.getString("equipmentsysdescr");
                    for (String model : sysdesc2ontprofId.keySet()){
                    	if (sysDesc.contains(model)){
                    		ont.setontprof(new GenericCMSAid(4948, sysdesc2ontprofId.get(model)));
                    		break;
                    	}
                    }
			        commit(ont);
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);
            closeConnection();
        }
    }
    
    private void preMigrateOnt() {
        MetaDataUtil.getInstance().loadAndFillFldIdx(DEVICE_TYPE_AE);
        MetaDataUtil.getInstance().initAeOntActionMapping();
		
	}

	public void commit(Ae_Ont ont){
        
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
                try{
                	if(isDuplicate(ont))
                        return;
                }catch(Exception e){
                	System.out.println("Object does not exist");
                }
                ont.doCreateOccamAeONT(tx);
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

	private boolean isDuplicate(Ae_Ont ont) {
		ResultSet resultSet = null;
		String groupQuery = "SELECT * FROM Ae_Ont WHERE ipaddress = '"
				+ ont.getIpAddress() + "'";
		try {
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery(groupQuery);

			if (resultSet != null) {
				if (resultSet.next()){
					System.out.println("Object exist. ");
					return true;
				}else{
                   System.out.println("Object does not exist");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("Excpetion when checking duplicate for ae_ont. ");
		}
		closeStmtAndResultSet(stmt,resultSet);
		return false;
	}
    
}


