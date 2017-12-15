package com.calix.bseries.server.boot;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.bseries.server.ana.ANAService;
import com.calix.bseries.server.dbmigration.DBMigrationConstants;
import com.calix.bseries.server.dbmigration.OVCMSDBTableMigration;
import com.calix.bseries.server.dbmodel.B6NewProxySettings;
import com.calix.bseries.server.dbmodel.B6ProxySettings;
import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.bseries.server.dbmodel.BseriesAids;
import com.calix.bseries.server.dbmodel.BseriesClassMap;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.rangediscovery.B6RangeDiscoveryHandler;
import com.calix.bseries.server.task.BSeriesUpgradeChildTask;
import com.calix.bseries.server.task.BseriesBackupChildTask;
import com.calix.bseries.server.task.BseriesSNMPProcessListenerTask;
import com.calix.bseries.server.turnuptool.TurnUptoolService;
import com.calix.bseries.server.util.BSeriesDbChangeUtilImpl;
import com.calix.bseries.server.util.BseriesGenericInvokeUtilImpl;
import com.calix.bseries.server.util.BseriesSecurityProxyUtilImpl;
import com.calix.bseries.server.util.MediationServerUtilProxyImpl;
import com.calix.bseries.server.util.ServicePackageHandlerProxyImpl;
import com.calix.ems.core.CoreUtilities;
import com.calix.ems.database.*;
import com.calix.ems.flow.JMSConnectionManager;
import com.calix.ems.security.utils.bseries.util.BseriesSecurityProxyUtil;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.ems.server.dbmodel.C7BackupTask;
import com.calix.ems.server.fault.CalixConditionRedefManager;
import com.calix.ems.server.dbmodel.EMSNetworkUpgradeTask;
import com.calix.ems.server.process.CutThroughProxyProcess;
import com.calix.ems.server.process.cutthrough.CutThroughSessionManager;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.server.boot.CMSProcessHandlers;
import com.calix.system.server.boot.DeviceStartup;
import com.calix.system.server.boot.db.PluginDeviceStatus;
import com.calix.system.server.boot.db.PluginDeviceStatusImpl;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.rangediscovery.RangeDiscoveryHandlerManager;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.calix.system.server.util.DeviceTaskHandler;
import com.calix.system.server.util.SNMPProcessListenerTask;
import com.occam.ems.be.app.servicemanagement.DefaultTableUpdate;
import com.occam.ems.be.app.servicemanagement.ProfileAutoGenerator;
import com.occam.ems.be.app.servicemanagement.ProfileMgmtHandlerImpl;
import com.occam.ems.be.util.BEUtil;
import com.occam.ems.be.util.BSeriesDbChangeUtil;
import com.occam.ems.be.util.BseriesGenericInvokeUtil;
import com.occam.ems.be.util.MediationServerUtil;
import com.occam.ems.be.util.ServicePackageHandlerUtil;
import com.occam.ems.common.dataclasses.AONTStatus;
import com.occam.ems.common.mo.servicemanagement.AONTDHCPProfile;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtUtil;
import com.occam.ems.mediation.transactionengine.common.OccamTransactionTypeDef;
import com.occam.ems.server.DataBaseAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.jboss.logging.Logger;


public class BseriesStartup extends DeviceStartup implements
        BseriesStartupMBean {
    private String hibernateConfigFile="database/hibernate/bseries/bseries-hibernate.cfg.xml";
    private String populateSQL="/database/hibernate/bseries/bseries-populate-data.sql";
    private Timer ontSychStateTimer;
    private TimerTask ontSychStateTimerTask;
    public static final String DEVICE_TYPE_BSERIES = "Bseries";
    
    public int getDeviceType() {
        return DeviceClassMap.DEVICE_MODULE_BSERIES;
    }


    @Override
    public void loadMetaModel() throws Exception {
        logger.info("in BSeries.loadMetaModel(), do nothing so far");
    }


    @Override
    public void loadDBModel() throws Exception {
        logger.info("Loading Bserial DBModel ...");
        TLVClassMap.addDeviceModule(BseriesClassMap.getInstance(), BseriesAids.getInstance());
        logger.info("Bserial DBModel loaded");
    }


    @Override
    public void loadHibenateMapping() throws Exception {
        logger.info("Loading Bserial hibernate resource ...");
        DatabaseSessionFactory.addSource(hibernateConfigFile);
        logger.info("Loaded Bserial hibernate resource");
    }


    @Override
    public void loadSecurityImpl() throws Exception {
        logger.info("in BSeries.loadSecurityImpl(), do nothing so far");
    }


    @Override
    public void loadTaskFlows() throws Exception {
        logger.info("in BSeries.loadTaskFlows(), do nothing so far");
    }


    @Override
    public void loadDebugShellListeners() throws Exception {
        logger.info("in BSeries.loadDebugShellListeners(), do nothing so far");
    }


    @Override
    public void loadInterProcessCommListeners() throws Exception {
        logger.info("in BSeries.loadInterProcessCommListeners(), do nothing so far");
    }


    @Override
    public void loadCustomComponents() throws Exception {
        logger.info("in BSeries.loadCustomComponents()...");
        CutThroughSessionManager.registerSBSessionClass(CutThroughProxyProcess.AGENT_TYPE_TL1 + new CalixB6Device().getPluginModule(), 
                "com.calix.bseries.server.cutthrough.CutThroughBseriesSBSession");
        logger.info("loadCustomComponents is done successfully");
    }


    @Override
    public void onLoadComplete() throws Exception {
        logger.error("onLoadComplete Begin");
        createDeviceTableAndPopulateData();
        DeviceTaskHandler.getInstance().addDeviceTaskHandler(DeviceClassMap.DEVICE_MODULE_BSERIES, SNMPProcessListenerTask.NAME, BseriesSNMPProcessListenerTask.class);
        DeviceTaskHandler.getInstance().addDeviceChildTaskHandler(DeviceClassMap.DEVICE_MODULE_BSERIES, C7BackupTask.TYPE_NAME, BseriesBackupChildTask.class);
        
        //James
        DeviceTaskHandler.getInstance().addDeviceChildTaskHandler(DeviceClassMap.DEVICE_MODULE_BSERIES, EMSNetworkUpgradeTask.TYPE_NAME, BSeriesUpgradeChildTask.class);
        //~James
        
        CMSProcessHandlers.getInstance().addProcessHandler(new BseriesProcessHandler());
        RangeDiscoveryHandlerManager.getInstance().addDeviceHandler("BLC.*|B6.*", new B6RangeDiscoveryHandler());
        MediationServerUtil.getInstance().setMediationServerUtilProxy(MediationServerUtilProxyImpl.getInstance());
        
        BSeriesDbChangeUtil.getInstance().setBSeriesDbChangeUtilProxy( BSeriesDbChangeUtilImpl.getInstance());
        ServicePackageHandlerUtil.getInstance().setServicePackageHandlerUtilProxy( ServicePackageHandlerProxyImpl.getInstance());
        //Initialize proxy package, just for Occam code
        BseriesGenericInvokeUtil.setBseriesGenericInvokeUtilProxy(new BseriesGenericInvokeUtilImpl());
        BseriesSecurityProxyUtil.setBseriesSecurityProxy(new BseriesSecurityProxyUtilImpl());
        
        loadAllBseriesNetworks();
        loadAlarmRedefinition();
        createB6Setting();
        createB6ProxySetting();
        createB6NewProxySetting();
        
        checkDBmigration();
        defaultValueUpdate(); 
        ServiceMgmtUtil.updateBondedIDCache();
        try {
            createNativeDHCPProfile();
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("BseriesStartup : onLoadComplete : Error while adding native profile");
        }
        /*
        try{
        	new com.occam.ems.fe.ccl.CCLProcessFE().callMain(null);
        }catch(Exception e) {
        	logger.error(e);
        }*/
        // ont heart beat 
        processBseriesOntSync();
        logger.info("onLoadComplete End");
        //add Bseries device model type load 
        // MetaDataUtil.getInstance().loadAndFillFldIdx(DEVICE_TYPE_BSERIES);
        //ADD Load ANA Service
        loadANAService();
        TurnUptoolService.startService();
    }
    
    private void loadANAService(){
    	ANAService.startService();
    }

    private void processBseriesOntSync() {
        logger.info("processBseriesOntSync called");
        ontSychStateTimer=new Timer();
        ontSychStateTimerTask=new OntSyncStateTask();
        
        ResourceBundleUtil commonRes = ResourceBundleUtil.getInstance(ResourceBundleUtil.RES_COMMON);

        if(commonRes.getString("ont.synchstate.enable")!= null){
            boolean enableSyncScheduler = Boolean.parseBoolean(commonRes.getString("ont.synchstate.enable"));
            if(enableSyncScheduler){
                logger.info("processBseriesOntSync enabled");
                int periodInMinutes=Integer.parseInt(commonRes.getString("ont.synchstate.pollperiod"));
                logger.info("processBseriesOntSync ONT Config synch status polling period in minutes:"+periodInMinutes);
//                System.out.println("ServiceMgmt ONT Config synch status polling periodIn Minutes is "+periodInMinutes);
                ontSychStateTimer.schedule(ontSychStateTimerTask,periodInMinutes*60*1000l,periodInMinutes*60*1000l);
            }else{
                logger.info("processBseriesOntSync disabled");
            }
        }else{
        	logger.warn("Property of 'ont.synchstate.enable' not found in " + ResourceBundleUtil.RES_COMMON);
        }
	}


	private void defaultValueUpdate() {
    	DefaultTableUpdate defaultTableUpdate = new DefaultTableUpdate();
    	defaultTableUpdate.queryDataBase();	
	}


	/**
     * Check whether DB migration OK
     * Raise alarm at the same time
     * @Author: jawang
     * @Date: Feb 3, 2012
     */
    private void checkDBmigration() {

        logger.error("checkDBmigration begin");
        // Check whether file exists
        // If not exists, return
        if (!OVCMSDBTableMigration.isPropertiesExists(DBMigrationConstants.LogFile.LOGNAME_PREFIX)) {
            logger.error(DBMigrationConstants.LogFile.LOGNAME_PREFIX + " does not exist");
            return;
        }

        // If exists, check success or failure
        // Get data from properties
        String status = OVCMSDBTableMigration.readProperties(DBMigrationConstants.LogFile.LOGNAME_PREFIX, DBMigrationConstants.LogFile.KEY_STATUS);
        String details = OVCMSDBTableMigration.readProperties(DBMigrationConstants.LogFile.LOGNAME_PREFIX, DBMigrationConstants.LogFile.KEY_DETAIL);
        String date = OVCMSDBTableMigration.readProperties(DBMigrationConstants.LogFile.LOGNAME_PREFIX, DBMigrationConstants.LogFile.KEY_DATE);
        String condition = OVCMSDBTableMigration.readProperties(DBMigrationConstants.LogFile.LOGNAME_PREFIX, DBMigrationConstants.LogFile.KEY_CONDITION);
        logger.error("Get info from properties: status=" + status + " details=" + details + " date=" + date);

        // status can not be null
        if (null == status) {
            logger.error("status is null, return");
            return;
        }

        // Status judgment
        if (status.equals(DBMigrationConstants.LogFile.STATUS_SUCCESS)) {
            if (null == details) {
                logger.error("detail is null, set default value");
                details = DBMigrationConstants.LogFile.DETAIL_SUCCESS;
            }
            if (condition == null){
            	condition = DBMigrationConstants.LogFile.COND_CMS_MIGRATION_SUCCCESS;
            }
            logger.error("raise success alarm");
            // Success alarm
            raiseAlarm(condition, details + date);
        } else if (status.equals(DBMigrationConstants.LogFile.STATUS_FAIL)) {
            if (null == details) {
                logger.error("detail is null, set default value");
                details = DBMigrationConstants.LogFile.DETAIL_FAIL;
            }
            if (condition == null){
            	condition = DBMigrationConstants.LogFile.COND_CMS_MIGRATION_FAIL;
            }
            logger.error("raise failure alarm");
            // Fail alarm
            raiseAlarm(condition, details + date);
        } else {
            logger.error(status + " is not defined. skip alarm");
            // undefined
            return;
        }

        logger.error("checkDBmigration end");
    }

    private void raiseAlarm(String alarmAlias, String msg) {
        CoreUtilities.raiseEMSAlarm(JMSConnectionManager.DEFAULT_CMS_AID, CalixConditionBase.CRITICAL, CMSObject.getAliasValue(alarmAlias), CalixConstants.DEFAULT_CMS_NETWORK, msg);
    }

    @Override
    public void populateData() throws Exception {
        logger.info("Data populating..., execute sql file: " + populateSQL);
        PluginDeviceStatus ddp=new PluginDeviceStatusImpl();
        ddp.executeSQLWithTrans(populateSQL);
        logger.info("Data populated successfully");
    }

    @Override
    public void createTables() throws Exception {
        logger.info("in BSeries.createTables(), do nothing so far");
    }

    private void loadAllBseriesNetworks(){
        ICMSDatabase database = null;
        DbTransaction txn = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            com.calix.ems.query.ICMSQuery query = AbstractCMSDatabase.getDBQuery(null, CalixB6Device.TYPE_NAME, "");
            Collection results = query.exec(null, txn);
            Iterator iter = results.iterator();
            while (iter.hasNext()) {
                BaseCMSNetwork network = (BaseCMSNetwork) iter.next();
                SouthboundSession sbSession = null;
                try {
                    sbSession = SessionManager.getInstance().createNewSouthboundSession(network, null);
                } catch (Throwable ex) {
                    logger.warn("Unable to create a SouthboundSession for network: [" 
                            + network.getDisplayName() + "] as the following exception was thrown ", ex );
                }
                network.setConnectionState(0);
                network.doUpdate(txn);
            }
            txn.commit();
        } catch (Exception ex) {
            logger.warn("creating SouthboundSessions for the boot process failed: ",ex);
        } finally {
            if (txn != null && txn.isActive())
                txn.abort();
        }
    }
    
    private void createB6Setting(){
        logger.info("begin to create B6Setting data to database...");
        ICMSDatabase database = null;
        DbTransaction txn = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            B6Settings b6setting = (B6Settings)database.readSingletonCMSObject(B6Settings.TYPE_NAME, txn);
            if (b6setting == null){
                b6setting = new B6Settings();
                b6setting.setDbIdentity("B6Setting");
                b6setting.setWebUsername("Administrator");
                b6setting.setWebPassword("razor123");
                b6setting.setCliUsername("cli");
                b6setting.setCliPassword("frpocc");
                b6setting.setEnablePassword("ocenable");
                b6setting.setreadCommunity("public");
                b6setting.setwriteCommunity("private");
                b6setting.setManifestFilePath("");                               
                b6setting.setSubIdPrefix("SubscriberName");
                b6setting.setSubDescPrefix("SubscriberPhone");
                b6setting.setDeleteTempLogForOld(30);
                b6setting.setAnaSocketProtocol(0);
                b6setting.setAnaSocketPort(9002);
                b6setting.doCreate(txn);
                txn.commit();
                logger.info("B6 setting data is persistent in database successfully");
            }else {
            	b6setting.getWebPassword();
            	b6setting.getCliPassword();
            	b6setting.getreadCommunity();
            	b6setting.getwriteCommunity();
            	b6setting.getEnablePassword();
                logger.info("B6 setting data is persistent in database already");
            }
            
            logger.info("cache B6Setting data...");
            CMSCacheManager.getCacheManager().addEMSObject(b6setting);
            logger.info("B6 setting data is cached successfully");
        } catch (Exception ex) {
            logger.error("Failed in load B6 setting data: ",ex);
        } finally {
            if (txn != null && txn.isActive())
                txn.abort();
        }
    }
    
    private void createB6ProxySetting(){
        logger.info("begin to create B6ProxySettings data to database...");
        ICMSDatabase database = null;
        DbTransaction txn = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            B6ProxySettings b6setting = (B6ProxySettings)database.readSingletonCMSObject(B6ProxySettings.TYPE_NAME, txn);
            if (b6setting == null){
                b6setting = new B6ProxySettings();
                b6setting.setDbIdentity("B6ProxySetting");
                b6setting.setIsProxyServer(0);
                b6setting.doCreate(txn);
                txn.commit();
                logger.info("B6 proxy setting data is persistent in database successfully");
            }
            
            logger.info("cache B6proxySetting data...");
            CMSCacheManager.getCacheManager().addEMSObject(b6setting);
            logger.info("B6 proxy setting data is cached successfully");
        } catch (Exception ex) {
            logger.error("Failed in load B6 proxy setting data: ",ex);
        } finally {
            if (txn != null && txn.isActive())
                txn.abort();
        }
    }
    
    private void createB6NewProxySetting(){
        logger.info("begin to create B6NewProxySettings data to database...");
        ICMSDatabase database = null;
        DbTransaction txn = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            B6NewProxySettings b6setting = (B6NewProxySettings)database.readSingletonCMSObject(B6NewProxySettings.TYPE_NAME, txn);
            if (b6setting == null){
                b6setting = new B6NewProxySettings();
                b6setting.setDbIdentity("B6NewProxySetting");
                b6setting.doCreate(txn);
                txn.commit();
                logger.info("B6 New proxy setting data is persistent in database successfully");
            }
            
            logger.info("cache B6NewProxySetting data...");
            CMSCacheManager.getCacheManager().addEMSObject(b6setting);
            logger.info("B6 new proxy setting data is cached successfully");
        } catch (Exception ex) {
            logger.error("Failed in load B6 new proxy setting data: ",ex);
        } finally {
            if (txn != null && txn.isActive())
                txn.abort();
        }
    }
	
	public void loadAlarmRedefinition() {
        try {
            C7Database.getInstance().beginTransaction();
            Collection col = C7Database.getInstance().executeQuery(CalixConditionRedef.class, "moduletype=13", new String[][]{{"alarm","ASC"},{"facility","ASC"},{"severity","ASC"}}, 0, 0);
            Iterator iter = col.iterator();
            while (iter.hasNext()) {
            	CalixConditionRedef redef = (CalixConditionRedef) iter.next();
                if (redef != null) {
                    CalixConditionRedefManager.getInstance().getConditionRedefMap().put(redef.getID().toString(), redef);
                }
            }
            C7Database.getInstance().commitTransaction();
        } catch (Exception e) {
            log.error("Unable to load B6 Alarm redefs from database", e);
            C7Database.getInstance().rollbackTransaction();
        } finally {
            C7Database.getInstance().close();
        }
	}
	private void createNativeDHCPProfile() throws Exception
    {
    	 logger.debug("Enter createNativeDHCPProfile method");
        Properties prop=new Properties();
        String resultCause=null;
        boolean exists=false;
        DataBaseAPI api=DataBaseAPI.getInstance();
        List aontdhcpProfileObjects=api.getObjects(AONTDHCPProfile.getTableName(),prop);
        if(aontdhcpProfileObjects!=null && !aontdhcpProfileObjects.isEmpty())
        {
            ArrayList list=ProfileMgmtHandlerImpl.getInstance().getDefaultAONTProfileObect();
            AONTDHCPProfile defaultAONTProfileObject=null;
            if(list!=null && !list.isEmpty())
            {
                defaultAONTProfileObject=(AONTDHCPProfile)list.get(0);
            }
            if(defaultAONTProfileObject!=null)
            {
            for(int recordIndex=0;recordIndex<aontdhcpProfileObjects.size();recordIndex++)
            {
                AONTDHCPProfile aontPropObj=(AONTDHCPProfile)aontdhcpProfileObjects.get(recordIndex);
                
                if(aontPropObj.getNetworkAddress().equals(defaultAONTProfileObject.getNetworkAddress()) && aontPropObj.getNetworkMask().equals(defaultAONTProfileObject.getNetworkMask()))
                        {
                         exists=true;
                         break;
                        }
            }
            }
            
        }
        if(exists)
            logger.debug("ServiceMgmtBEServer : createNativeDHCPProfile:Native profile exists");
        else
        {
            resultCause=ProfileMgmtHandlerImpl.getInstance().configureDefaultDHCPProfile();
            logger.debug("Result Cause"+resultCause);
            if(resultCause.indexOf("start address falls within existing")!=-1 || resultCause.indexOf("DHCP Profile with the same name is already configured at OCM")!=-1 )
            {
                //just looking for the Native profile name Existed on the OCM
                int indexofExisting=resultCause.indexOf("(");
                int lastIndex=resultCause.lastIndexOf(")");
                String nativeProfileName=null;
                if(resultCause.indexOf("start address falls within existing")!=-1)
                nativeProfileName=resultCause.substring(indexofExisting+1,lastIndex);
                else
                nativeProfileName=ServiceMgmtConstants.NATIVE_DHCP_PROFILE_NAME;
                logger.debug("Native Profile Name[ServiceMgmtBEServer : createNativeDHCPProfile]"+nativeProfileName);
                //make call here
                AONTDHCPProfile defaultImported=null;
                
                   
                defaultImported=ProfileAutoGenerator.getInstance().getDhcpProfile(nativeProfileName);
                //
                if(defaultImported!=null) {
                    try {
                        if(defaultImported.getTFTPServer()!=null && defaultImported.getTFTPServer().equals(OccamUtils.getOCMIP())==false) {
                            String existingTftpIp=defaultImported.getTFTPServer();
                            defaultImported.setTFTPServer(OccamUtils.getOCMIP());
                            defaultImported.setCmsServer(OccamUtils.getLocalHostIP());
                            List errorList=ProfileAutoGenerator.getInstance().configureAONTDHCPProfile(defaultImported,OccamTransactionTypeDef.ACTION_MODIFY);
                            if(errorList!=null && errorList.size()>0)
                                
                                defaultImported.setTFTPServer((existingTftpIp!=null && existingTftpIp.length()>0)?existingTftpIp:OccamUtils.getOCMIP());
                            
                            DataBaseAPI.getInstance().addObject(defaultImported);
//                              ServiceMgmtUtil.addToServiceAuditTable(ServiceMgmtDBConstants.SERVICE_AUDIT_DEVICEID_DEFAULT_VAL, System.currentTimeMillis(), OccamUtils.getOCMIP(),  ServiceMgmtDBConstants.SERVICE_AUDIT_PORT_DEFAULT_VAL
//                                      , ServiceMgmtConstants.PROFILEOPERATION_NEW, ServiceMgmtDBConstants.SERVICE_AUDIT_TYPE_PROFILE_DHCP_INT, defaultImported.getName()
//                                      , ServiceMgmtConstants.STATUS_SUCCESS, "Profile(s) added successfully", "root", ServiceMgmtDBConstants.SERVICE_AUDIT_LOGICALPORT_DEFAULT_VAL, new Properties());
                            
                        } else {
                            defaultImported.setCmsServer(OccamUtils.getLocalHostIP());
                            DataBaseAPI.getInstance().addObject(defaultImported);
//                          ServiceMgmtUtil.addToServiceAuditTable(ServiceMgmtDBConstants.SERVICE_AUDIT_DEVICEID_DEFAULT_VAL, System.currentTimeMillis(), OccamUtils.getOCMIP(),  ServiceMgmtDBConstants.SERVICE_AUDIT_PORT_DEFAULT_VAL
//                                  , ServiceMgmtConstants.PROFILEOPERATION_NEW, ServiceMgmtDBConstants.SERVICE_AUDIT_TYPE_PROFILE_DHCP_INT, defaultImported.getName()
//                                  , ServiceMgmtConstants.STATUS_SUCCESS, "Profile(s) added successfully", "root", ServiceMgmtDBConstants.SERVICE_AUDIT_LOGICALPORT_DEFAULT_VAL, new Properties());
                        }
                        
                    } catch(Throwable e) {
                        e.printStackTrace();
                        logger.error("Error during createNativeDHCPProfile(adding imported profile)");
                    }     
                }
            }
            else
              logger.debug("ServiceMgmtBEServer : createNativeDHCPProfile:Native profile Create success");
        }
    }
}

class OntSyncStateTask extends TimerTask
{
	Logger logger = Logger.getLogger(OntSyncStateTask.class);
    public void run() {
    	
    	logger.info("OntSyncStateTask called for updating ONT sync state managed by OCM");
    	
    	//Check enable or not for sync status
        ResourceBundleUtil commonRes = ResourceBundleUtil.getInstance(ResourceBundleUtil.RES_COMMON);

		if (commonRes.getString("ont.synchstate.enable") != null) {
			boolean enableSyncScheduler = Boolean.parseBoolean(commonRes.getString("ont.synchstate.enable"));
			if (enableSyncScheduler) {
                logger.info("processBseriesOntSync enabled");
                doProcessSyncStatus();
			}else{
				logger.info("processBseriesOntSync disabled");
			}
		} else {
			logger.warn("Property of 'ont.synchstate.enable' not found in "	+ ResourceBundleUtil.RES_COMMON);
		}
    }
    
	private void doProcessSyncStatus() {
		CMSDBQuery query = new CMSDBQuery();
		query.addFromList("Ae_Ont", "ae_ont");query.addWhereClause("ae_ont.ExternalProvisioned = " + Ae_Ont.OCM_PROVISIONED);
		Collection<Ae_Ont> aeonts = new Ae_Ont().doQuery(query);
		Collection<Ae_Ont> aeontsChanged = new ArrayList<Ae_Ont>();
		String ipaddress; 
		String syncStatus;
		int sync = Ae_Ont.SYNC_STATE_ERROR;
		for (Ae_Ont aeont : aeonts) {
			if (aeont != null && aeont.getModel() != null) {
				ipaddress = aeont.getIpAddress();
				if (ipaddress != null) {
					syncStatus = BEUtil.getSyncStateForONT(ipaddress);
					if (syncStatus != null) {
						// TODO Map sync status from occamview to CMS.
						if (syncStatus.equals(AONTStatus.INIT)) {
							sync = Ae_Ont.SYNC_STATE_CHANGED;
						} else if (syncStatus.equals(AONTStatus.IN_SYNC)) {
							sync = Ae_Ont.SYNC_STATE_CLEAR;
						} else if (syncStatus.equals(AONTStatus.NOT_IN_SYNC)) {
							sync = Ae_Ont.SYNC_STATE_CHANGED;
						} else if (syncStatus.equals(AONTStatus.QUEUED)) {
							sync = Ae_Ont.SYNC_STATE_CHANGED;
						} else if (syncStatus.equals(AONTStatus.OFFLINE)) {
							sync = Ae_Ont.SYNC_STATE_ERROR;
						} else {
							logger.warn("OntSyncStateTask. Fail to convert sync status for Ae Ont: ["
									+ aeont.getaeontid()
									+ "] with ip address ["
									+ aeont.getIpAddress()
									+ "];   sync status is "
									+ syncStatus
									+ ". ");
						}
						aeont.setSyncStatus(sync);
						aeontsChanged.add(aeont); 
						logger.debug("OntSyncStateTask. Update Ae Ont: ["
								+ aeont.getaeontid() + "] with ip address ["
								+ aeont.getIpAddress() + "] to sync status:"
								+ sync + ". ");
					} else {
						logger.warn("OntSyncStateTask. Fail to get sync status for Ae Ont: ["
								+ aeont.getaeontid()
								+ "] with ip address ["
								+ aeont.getIpAddress()
								+ "];  sync status is "
								+ syncStatus + ". ");
					}
				} else {
					logger.warn("OntSyncStateTask. Ip address is null for Ae Ont: ["
							+ aeont.getaeontid() + "] ");
				}
			}
		}
		C7Database c7db = C7Database.getInstance();
		try {
			c7db.beginTransaction();
			for (Ae_Ont ae : aeontsChanged) {
				c7db.updateObject(ae, ae.getIdentityValue());
			}
			c7db.commitTransaction();
		} catch (Exception e) {
			c7db.rollbackTransaction();
			logger.error(
					"Failed to save sync status for ae ont. Error: " + e.getMessage(), e);
		} finally {
			c7db.close();
		}
		
	}
    
}
