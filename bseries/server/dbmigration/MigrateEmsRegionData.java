package com.calix.bseries.server.dbmigration;

import com.calix.ems.server.dbmodel.EMSRegion;
import com.calix.system.common.log.Log;

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
public class MigrateEmsRegionData extends MigrateOVData {

    private Connection connection = null;
	
    /** Creates a new instance of calix emsRegion */
    public MigrateEmsRegionData() {
        super();
    }
    
    public void migrateOVData(){
    	
        Statement stmt = null;
        ResultSet resultSet = null;
        List<EMSRegion> regionList = new ArrayList<EMSRegion>();
        try {
            connection = getConnection();
            String groupQuery = "SELECT * FROM LOCATION";
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(groupQuery);
            
            if (resultSet != null){
                while (resultSet.next()) {
                    System.out.println("----------------------------------------------------------");
                    String dbIdentity = resultSet.getString("name");
					dbIdentity =  "REG-" + MigrationUtils.reConfigureRegion(dbIdentity);
                    System.out.println("dbIdentity " + dbIdentity );
                    
                    EMSRegion region = new EMSRegion();
                    region.setDbIdentity(dbIdentity);
                    region.setSID(dbIdentity);
                    
                    String parentRegion = resultSet.getString("parentlocation");
                    if(parentRegion.equalsIgnoreCase("Maps")){
                    	region.setParentRegion("REG-root");
                    }else{
                    	region.setParentRegion("REG-" + MigrationUtils.reConfigureRegion(parentRegion));
                    }
                    
                    region.setNetworkGroup(0);
                    region.setWidth(100);
                    region.setHeight(100);
                    region.setXOffset(100);
                    region.setYOffset(100);

                    regionList.add(region);
                    
                    String ngIdentity = dbIdentity + " NG";
                    EMSRegion ngregion = new EMSRegion();
                    ngregion.setDbIdentity(ngIdentity);
                    ngregion.setSID(ngIdentity);                    
                    
                    ngregion.setParentRegion(dbIdentity);
                    
                    ngregion.setNetworkGroup(1);
                    ngregion.setWidth(100);
                    ngregion.setHeight(100);
                    ngregion.setXOffset(100);
                    ngregion.setYOffset(100);

                    regionList.add(ngregion);
                }
            }
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);
            closeConnection();
        }
        ArrayList newRegionList = sortRegion(regionList);
        
        commit(newRegionList);
    }
    
	private ArrayList<EMSRegion> sortRegion(List<EMSRegion> oldRegionList) {
		ArrayList<EMSRegion> newRegtionList = new ArrayList<EMSRegion>();

		for (EMSRegion region : oldRegionList) {
			if (region.getParentRegion() != null && region.getParentRegion().equals("REG-root")) {
				newRegtionList.add(region);
				break;
			}
		}
		int delta = oldRegionList.size();
		if (newRegtionList.size() > 0) {
			while (true) {
				ArrayList<EMSRegion> tmpRegtionList = new ArrayList<EMSRegion>();
				for (EMSRegion newRegion : newRegtionList) {
					tmpRegtionList.add(newRegion);
				}

				for (EMSRegion newRegion : tmpRegtionList) {
					for (EMSRegion oldRegion : oldRegionList) {
						if (newRegion.getDbIdentity() != null && newRegion.getDbIdentity().equals( oldRegion.getParentRegion())) {
							if (!newRegtionList.contains(oldRegion)) {
								newRegtionList.add(oldRegion);
							}
						}
					}
				}
				int newDelta = oldRegionList.size() - newRegtionList.size();
				if (newDelta == 0 || newDelta == delta) {
					if (newDelta == 0) {
						Log.db().warn( "All the regions will be migrated to CMS. ");
					} else {
						Log.db().warn("Some regions can not be migrated to CMS because the parent region can not be found. ");
						printMissingRegions(newRegtionList, oldRegionList);
					}
					break;
				} else {
					delta = newDelta;
					continue;
				}
			}
		}
		return newRegtionList;
	}

	private void printMissingRegions(ArrayList<EMSRegion> newRegtionList,
			List<EMSRegion> regionList) {
		for(EMSRegion region: regionList){
			boolean isExist = false;
			for(EMSRegion exportRegion: newRegtionList){
				if(region.getDbIdentity().equals(exportRegion.getDbIdentity())){
					isExist = true; 
					break;
				}					
			}
			if(!isExist){
				Log.db().warn("Region can not be migrated. Name: " + region.getDbIdentity());
			}
		}
	}
}

