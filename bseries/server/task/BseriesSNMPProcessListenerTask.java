package com.calix.bseries.server.task;

import com.calix.ems.database.AbstractCMSDatabase;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.server.util.ThirdPartyProfilesMap;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.util.GenericSNMPProcessListenerTask;
import com.calix.system.server.dbmodel.CalixBaseSNMPDeviceProfile;
import com.calix.system.server.dbmodel.CalixSNMPDeviceProfile;

import java.util.Collection;

import org.apache.log4j.Logger;

public class BseriesSNMPProcessListenerTask extends
        GenericSNMPProcessListenerTask {
    private static final Logger logger = Logger.getLogger(BseriesSNMPProcessListenerTask.class);

    @Override
    protected Collection<CalixBaseSNMPDeviceProfile> getDeviceProfiles() {
        AbstractCMSDatabase database = null;
        DbTransaction txn = null;
        Collection<CalixBaseSNMPDeviceProfile> bseriesProfiles = null;
        try {
            logger.info("begin to retrieve Bseries SNMP Device profiles...");
            
            database = DatabaseManager.getCMSDatabase();
            txn = database.getTransaction("ems", DbTransaction.READ);            
            txn.begin();            
            bseriesProfiles = database.getFilterCollection(CalixSNMPDeviceProfile.TYPE_NAME, "addressid BETWEEN 50 AND 59" ,txn);
            txn.commit();
            
            for(CalixBaseSNMPDeviceProfile prof: bseriesProfiles) {
                prof.extractUpdateIcons();
                ThirdPartyProfilesMap.getInstance().addIconNameList(prof.assembleIconNameList());
            }
            
            logger.info("successfully retrieved SNMP Device profiles, the number of profiles are: " + bseriesProfiles.size());
            
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        } finally {
            if (txn != null && txn.isActive()){
                txn.abort();
            }
        }
        return bseriesProfiles;
    }

    @Override
    protected int getDeviceModule() {
        return DeviceClassMap.DEVICE_MODULE_BSERIES;
    }

}
