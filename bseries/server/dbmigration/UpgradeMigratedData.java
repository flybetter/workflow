package com.calix.bseries.server.dbmigration;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.occam.ems.common.mo.servicemanagement.DataService;
/**
*
* @author hzhang
*/
public class UpgradeMigratedData extends MigrateOVData {

    private Connection connection = null;
	
    /** Creates a new instance of calix emsRegion */
    public UpgradeMigratedData() {
        super();
    }
    
    public void migrateOVData(){
    	
        Statement stmt = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            System.out.println("Creating ServiceModel column in Data Service Table");            
            PreparedStatement pstmt = connection.prepareStatement("ALTER TABLE DATASERVICE ADD COLUMN SERVICEMODEL INTEGER");
            pstmt.executeUpdate();
            
            pstmt = connection.prepareStatement("UPDATE DATASERVICE SET SERVICEMODEL=0");
            pstmt.executeUpdate();
            
            System.out.println("Creating One2OneProfile & CvidMatchClause columns in DSLFiberPortServiceDetails Table");            
            pstmt = connection.prepareStatement("ALTER TABLE DSLFIBERPORTSERVICEDETAILS ADD COLUMN ONE2ONEPROFILENAME VARCHAR(125)");
            pstmt.executeUpdate();
            
            pstmt = connection.prepareStatement("ALTER TABLE DSLFIBERPORTSERVICEDETAILS ADD COLUMN CVIDMATCHCLAUSE INTEGER");
            pstmt.executeUpdate();
            
            pstmt = connection.prepareStatement("UPDATE DSLFIBERPORTSERVICEDETAILS SET CVIDMATCHCLAUSE=-99");
            pstmt.executeUpdate();
            
            pstmt = connection.prepareStatement("ALTER TABLE AONTPROFILE ALTER COLUMN SNMPTRAPHOST TYPE VARCHAR(255)");
            pstmt.executeUpdate();
            
            pstmt = connection.prepareStatement(" ALTER TABLE AONTPROFILE ALTER COLUMN SNMPTRAPHOST SET DEFAULT NULL");
            pstmt.executeUpdate();
            

           /* //update dsl profile new attributes default value.
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPSTATE=-99 WHERE USGINPSTATE IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPSTATE=-99 WHERE DSGINPSTATE IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPETRMIN=-99 WHERE USGINPETRMIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPETRMIN=-99 WHERE DSGINPETRMIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPNDRMAX=-99 WHERE USGINPNDRMAX IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPNDRMAX=-99 WHERE DSGINPNDRMAX IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPMAXDELAY=-99 WHERE USGINPMAXDELAY IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPMAXDELAY=-99 WHERE DSGINPMAXDELAY IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPMINDELAY=-99 WHERE USGINPMINDELAY IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPMINDELAY=-99 WHERE DSGINPMINDELAY IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPINPMIN=-99 WHERE USGINPINPMIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPINPMIN=-99 WHERE DSGINPINPMIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPSHINERATIO =-99 WHERE USGINPSHINERATIO IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPSHINERATIO=-99 WHERE DSGINPSHINERATIO IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPINPMINREIN=-99 WHERE USGINPINPMINREIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPINPMINREIN=-99 WHERE DSGINPINPMINREIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET USGINPIATREIN=-99 WHERE USGINPIATREIN IS NULL");
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("UPDATE DSLPROFILE SET DSGINPIATREIN=-99 WHERE DSGINPIATREIN IS NULL");
            pstmt.executeUpdate();
            */
            
        } catch(SQLException sq) {
            sq.printStackTrace();
        } finally {
            closeStmtAndResultSet(stmt,resultSet);
            closeConnection();
        }
        
     //   commit(dataServiceList);
    }
}

