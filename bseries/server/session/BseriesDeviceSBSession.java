package com.calix.bseries.server.session;

import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.bseries.server.dbmodel.CalixB6Chassis;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.bseries.server.task.BSeriesAeOntAssociationResponseSignal;
import com.calix.bseries.server.task.BSeriesDiscoveryResponseSignal;
import com.calix.bseries.server.task.BSeriesMediationSignal;
import com.calix.bseries.server.task.BSeriesTaskSignal;
import com.calix.bseries.server.task.BSeriesTrapRegSignal;
import com.calix.bseries.server.task.BSeriesAllLinksDiscoveryResponseSignal;
import com.calix.bseries.server.task.BSeriesLinkDiscoveryResponseSignal;
import com.calix.bseries.server.task.BSeriesChassisDiscoveryResponseSignal;
import com.calix.bseries.server.util.BseriesGenericInvokeUtilImpl;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.exception.EMSException;
import com.calix.ems.core.CoreUtilities;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.exception.SessionException;
import com.calix.ems.flow.JMSConnectionManager;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.query.DBCMSQuery;
import com.calix.ems.query.ICMSQuery;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.ems.server.fault.CMSAlarmClosureMgr;
import com.calix.system.common.constants.DomainValues;
import com.calix.ems.server.process.CMSProcess;
import com.calix.ems.session.DBChangeListener;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.session.SnmpDeviceSBSession;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.BaseScalarValueType;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.occam.ems.be.app.discovery.DiscoveryUtil;
import com.occam.ems.be.mo.OccamSystem;
import com.occam.ems.be.util.BSeriesDbChangeUtil;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamGPon;
import com.occam.ems.common.dataclasses.OccamGponONT;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.dataclasses.OccamSystemData;
import com.occam.ems.common.defines.AttributeNames;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.server.DataBaseAPI;
import com.opticalsolutions.eh.snmp.SNMPParameters;
import com.opticalsolutions.eh.snmp.dummy.CalixThirdPartyNetworkDummy;
import com.opticalsolutions.eh.snmp.dummy.ThirdPartyNetworkRequestTypeDummy;
import com.opticalsolutions.eh.snmp.dummy.ThirdPartyNetworkSignalDummy;
import com.calix.system.server.dbmodel.NEStaticLink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;
import java.util.Collection;

import org.apache.log4j.Logger;

public class BseriesDeviceSBSession extends SnmpDeviceSBSession {
    private static Logger logger = Logger.getLogger(BseriesDeviceSBSession.class);
    static HashMap<String,Integer> alarmString2alarmValue = new HashMap<String,Integer>();
    static HashMap<String,Integer> facilityString2facilityValue = new HashMap<String,Integer>();
    static final String STATIC_EPS_LINK="EPS";
    
    public BseriesDeviceSBSession(Integer sessionID, BaseCMSNetwork networkData)
            throws SessionException {
        super(sessionID, networkData);
        init("BseriesCondTypeAll", alarmString2alarmValue);
        init("BseriesFacility", facilityString2facilityValue);
    }
    
    private void init(String tlvTypeName, HashMap<String, Integer> map){
        try {
            BaseScalarValueType conditionType = (BaseScalarValueType)TypeRegistry.getInstance().getType(tlvTypeName);
            Iterator<IValue> values = conditionType.getEnumConstraint().getEnumValues();
            while ( values.hasNext() ) {
                IValue value = values.next();
                String stringValue = (String)value.convertTo(String.class, DomainValues.DOMAIN_TL1);
                Integer intValue = (Integer)value.convertTo(Integer.class, null);
                //Begin bug 51952 fix by James Wang 20111228
                //In case of char mismatch of capitalization, store lower case only 
                stringValue = stringValue.toLowerCase();
                //End bug 51952 fix by James Wang 20111228
                map.put(stringValue,intValue);
            }
        } catch (MappingException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
	public void processAlarm(CalixConditionBase alarm) {
    	// Preprocess to fill in alarm and facility based on string values.
    	preprocessAlarm(alarm);
    	if (alarm != null && alarm.getSeverity() != null && alarm.getSeverity().intValue() == 10) {
    		CalixEventBase event = ccb2ceb(alarm);
    		event.setDevice(this.getDeviceType());
    		processEvent(event);
    	} else {
    		super.processAlarm(alarm);
    	}
	}

	public static CalixEventBase ccb2ceb(CalixConditionBase alarm) {
		CalixEventBase ceb = new CalixEventBase();
		ceb.setTypeName("CalixEventResponse");
		ceb.setTlvType(SystemTlvConstants.CalixEventResponse);
        ceb.setIdentityValue(alarm.getIdentityValue());
        ceb.setFacilityString(alarm.getFacilityString());
        ceb.setFacility(alarm.getFacility());
        ceb.setEventTypeString(alarm.getAlarmTypeString());
        ceb.setEvent(alarm.getAlarm());
        ceb.setDescription(alarm.getDescription());
        ceb.setDate_and_Time(alarm.getDate_and_Time());
        ceb.setDeviceTime(alarm.getDeviceTime());
        ceb.setLocation(alarm.getLocation());
		return ceb;
	}

	@Override
	public void processEvent(CalixEventBase event) {
		preprocessEvent(event);
		 if(event.getEvent() == CMSObject.getAliasValue("_B6_CondType_OntDiscovered_")||event.getEvent() == CMSObject.getAliasValue("_B6_CondType_OntSwapOut_")){
			 try {
				 //CMS-8260	device may not have all the information ready when device is being discovered
		         // so sleep 12 seconds, the same behavior with occamView
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.warn("Exception occured when process GPON ONT discover/swap out event", e);
			}
			 doGponOntDiscovery();
		 }		 		 
		super.processEvent(event);
	}

	@Override
    public CalixThirdPartyNetworkDummy tpnCopyToDummy(
            BaseSNMPDeviceNetwork baseNetwork) {
        CalixThirdPartyNetworkDummy tpNet = new CalixThirdPartyNetworkDummy();
        CalixB6Device tpNetwork = (CalixB6Device)baseNetwork;
        tpNet.setIdentityValue(tpNetwork.getIdentityValue().toString());
        tpNet.setIPAddress1(tpNetwork.getIPAddress1());
        tpNet.setTypeName(tpNetwork.getTypeName());
        tpNet.setProfile(tpNetwork.getprofile().toString());
        tpNet.setSnmpParam(networkToParam(tpNetwork));
        return tpNet;
    }

    @Override
    public int getDeviceType() {
        return DeviceClassMap.DEVICE_MODULE_BSERIES;
    }

    @Override
    public synchronized void connect() throws SessionException {
        super.connect();
        
        if (m_connected){
            logger.debug("network is connected already, so nothing to do");
            return;
        }
        logger.info("Connecting to network: " + getNetworkName());
        try {
            ThirdPartyNetworkSignalDummy respSignal = connectNetwork();
            if (respSignal.getRequestType() == ThirdPartyNetworkRequestTypeDummy.NETWORK_CONNECTED) {
                logger.info("successfully connect network: " + getNetworkName());
                connected();
                doSyncAlarms();
                doDiscovery();
            } else {
                throw new SessionException(SessionException._SouthSessionUpdateError, "Unable to connect network: " + getNetworkName() + " due to :" + respSignal.getErrorText());
            }
        } catch (JMSException jms) {
            throw new SessionException(SessionException._SouthSessionUpdateError, "Unable to connect network: " + getNetworkName() + " due to :" + jms.getMessage());
        }
    }


    @Override
    public void disconnect() throws Exception {
    	CMSAlarmClosureMgr.getInstance().clearContactClosureAlarms(this.getEMSNetwork().getIPAddress1());
    	//deleteExistinglinkforNetwork();
        super.disconnect();
        doRemoveTrapListener();
    }

    @Override
    protected void doSyncAlarms() throws SessionException {
    	CMSAlarmClosureMgr.getInstance().clearContactClosureAlarms(this.getEMSNetwork().getIPAddress1());
    	// Clear out standing alarm cache first
        m_alarmContainer.clearAlarms();
        SNMPParameters snmpParameters = networkToParam((BaseSNMPDeviceNetwork)m_network);
        B6TriggerActiveAlarmReq b6TriggerActiveAlarmReq = new B6TriggerActiveAlarmReq(snmpParameters);
        b6TriggerActiveAlarmReq.init();
    }
    
    protected void preprocessAlarm(CalixConditionBase alarm) {
    	// CMS-5675 Comment this fix, because it will lead some alarm discarded for the same alarm ID. 
    	// CMS-5192 always set network name as alarm aid to avoid confusion.
//    	ICMSAid newID = new EMSAid(getNetworkName()); 
//    	alarm.setIdentityValue(newID);
    	alarm.setAidDisplayString(getNetworkName());
    	//Begin bug 51952 fix by James Wang 20111228
    	String alarmTypeStr = alarm.getAlarmTypeString();
    	if (alarmTypeStr != null && alarmString2alarmValue.size() > 0){
//            alarmTypeStr = alarmTypeStr.replace(" ", "");
    		// CMS-4527, for there are spaces in B6 alarm definitions, it is not wise to trim spaces.
    		//Just let it be
//            alarmTypeStr = alarmTypeStr.replace(" ", "").toLowerCase();
    		alarmTypeStr = alarmTypeStr.toLowerCase();
    		if (alarmTypeStr.contains(" ")) {
    			alarmTypeStr = alarmTypeStr.replaceAll(" ", "");
    		}
    		
    		if (alarmString2alarmValue.containsKey(alarmTypeStr)){
    			alarm.setAlarm(alarmString2alarmValue.get(alarmTypeStr));
    			alarm.setAlarmTypeString(null);
    		}
    	}
    	
    	String facilityStr = alarm.getFacilityString();
    	if (facilityStr != null && facilityString2facilityValue.size() > 0){
    		facilityStr = facilityStr.toLowerCase();
    		if (facilityString2facilityValue.containsKey(facilityStr)){
    			alarm.setFacility(facilityString2facilityValue.get(facilityStr));
    			alarm.setFacilityString(null);
    		}
    	}
    	//End bug 51952 fix by James Wang 20111228
    	// CMS-5144 set region here to support suppression rule with network group.
    	alarm.setRegion(getEMSNetwork().getRegion());
    	
    }
    
    /**
     *  Copied from preprocessEvent()
     * @param event
     */
    protected void preprocessEvent(CalixEventBase event) {
    	// CMS-5192 always set network name as event aid to avoid confusion.
    	ICMSAid newID = new EMSAid(getNetworkName());
    	event.setIdentityValue(newID);
    	String eventTypeStr = event.getEventTypeString();
    	if (event.getEvent() == null && eventTypeStr != null && alarmString2alarmValue.size() > 0){
    		eventTypeStr = eventTypeStr.toLowerCase();
    		if (eventTypeStr.contains(" ")) {
    			eventTypeStr = eventTypeStr.replaceAll(" ", "");
    		}
    		if (alarmString2alarmValue.containsKey(eventTypeStr)){
    			event.setEvent(alarmString2alarmValue.get(eventTypeStr));
    			event.setEventTypeString(null);
    		}
    	}
    	String facilityStr = event.getFacilityString();
    	if (event.getFacility() == null && facilityStr != null && facilityString2facilityValue.size() > 0){
    		facilityStr = facilityStr.toLowerCase();
    		if (facilityString2facilityValue.containsKey(facilityStr)){
    			event.setFacility(facilityString2facilityValue.get(facilityStr));
    			event.setFacilityString(null);
    		}
    	}
    }

    protected void setDefaultSetting(SNMPParameters params){
        //set default value per B6 setting
        DbTransaction txn = null;
        B6Settings b6setting = null;
        try {
         
            b6setting = (B6Settings)CMSCacheManager.getCacheManager().getEMSObject
                (new B6Settings().getHierarchyIntegers(), new EMSAid("B6Setting") );
            
            if (b6setting != null){
                String writeCommunity = b6setting.getwriteCommunity();
                if (writeCommunity != null && writeCommunity.length() > 0){
                    params.setWriteCommunity(writeCommunity);
                }
                String readCommunity = b6setting.getreadCommunity();
                if (readCommunity != null && readCommunity.length() > 0){
                    params.setReadCommunity(readCommunity);
                }
            }
        }catch(Exception e){
            logger.error("fail to set default value to SNMPParameters based on B6 setting",e);
        }
    }

    public void doDiscovery() {
        try {
            if(isBSeriesMgrProcessDown()) {
                logger.warn("BSeries manager process down.");
                return;
            }
            BSeriesTaskSignal signal = new BSeriesTaskSignal();
            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_DISCOVERY_REQ);
            signal.setNetworkName(getNetworkName());
            signal.setIpAddress(getEMSNetwork().getIPAddress1());
            signal.setSnmpReadCommunity( ((CalixB6Device)getEMSNetwork()).getReadCommunity());
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
        } catch (Exception ex) {
            logger.warn("Error sending discovery request. network: " + getNetworkName(), ex);
        }
    }

    public synchronized void onDiscoveryResponse(BSeriesTaskSignal signal) throws SessionException {
        if(signal.getError() == null) {
            
                
                String swVersion = signal.getVersion();
                String eqptType = signal.getEquipType();
                logger.info("Received B6 discovery response " + swVersion);
                ICMSDatabase database = null;
                DbTransaction txn = null;
                try {
                    BSeriesDiscoveryResponseSignal respSignal = (BSeriesDiscoveryResponseSignal) signal;
                    database = DatabaseManager.getCMSDatabase();
                    txn = database.getTransaction("ems", DbTransaction.UPDATE);
                    txn.begin();
                    
                    ((CalixB6Device)m_network).setModel(eqptType);
                    ((CalixB6Device)m_network).setMACAddress(respSignal.getMacAddress());
                    ((CalixB6Device)m_network).setSerialNumber(respSignal.getSerialNum());
                    ((CalixB6Device)m_network).setSWVersion(signal.getVersion());
                    ((CalixB6Device)m_network).setManufactureDate(respSignal.getManufactureDate());
                    ((CalixB6Device)m_network).setHWVersion(respSignal.getHwVersion());
                    ((CalixB6Device)m_network).setShelfId(respSignal.getShelfId());
                    ((CalixB6Device)m_network).setSlotId(respSignal.getSlotId());
                    m_network.doUpdate(txn);
                    txn.commit();
                    txn  = null;                    
                    onNetworkUpdate(m_network);
                } catch (Exception ex) {
                    logger.warn("Unable to update network for. network name: " + getNetworkName(), ex);
                } finally {
                    if (txn != null && txn.isActive())
                        txn.abort();
                }
                
                //CMS-9633 keep chassis flag
                CalixB6Device device = (CalixB6Device)m_network;
				if (device.getkeepChassis() != null && device.getkeepChassis() == 1) {
					doChassisDiscovery();
				} else {
					logger.warn("Do not update chassis information for device" + getNetworkName() + ", because auto-Chassis flag is set to N. ");
					doLinkDiscovery();
				}
				
                if(eqptType != null && OccamUtils.isGponOlt(eqptType)){
                	logger.info("GPON ONT discovery for : " +eqptType);
                	doGponOntDiscovery();
                } 
                
                if(eqptType != null && OccamUtils.isFiberDevice(eqptType)){
                	logger.info("Ae ONT Association discovery for : " +eqptType);
                	doAeOntAssociationDiscovery();
                }
                
                if(swVersion != null && eqptType != null) {
                    doRegisterTrapListener();
                }  
                
        } else {
            logger.warn("Error in discoverying B6. IP: " + signal.getIpAddress() + ". Error: " + signal.getError());
        }
    }

    protected void doRegisterTrapListener() throws SessionException {
        addRemoveTrapDest(true);
    }

    private void addRemoveTrapDest(boolean isReg) throws SessionException {
        try {
        	String version = ((CalixB6Device)getEMSNetwork()).getSWVersion();
        	String networkName = getNetworkName();
        	if (!isTrapRegistrationSupported(version, networkName)) {
        		if (logger.isDebugEnabled()) {
        			String action = isReg?"add":"remove";
        			logger.debug("Skip " + action + " trap destination on B6 [" + networkName + "] version [" + version + "] since trap registration is not supported.");
        		}
        		return;
        	}
            if(isBSeriesMgrProcessDown()) {
                logger.warn("BSeries manager process down.");
                return;
            }
            BSeriesTrapRegSignal signal = new BSeriesTrapRegSignal();
            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_TRAP_REG_REQ);
            signal.setNetworkName(getNetworkName());
            signal.setIpAddress(getEMSNetwork().getIPAddress1());
            signal.setEquipType(((CalixB6Device)getEMSNetwork()).getModel());
            signal.setVersion(((CalixB6Device)getEMSNetwork()).getSWVersion());
            signal.setReg(isReg);
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
        } catch (Exception ex) {
            logger.warn("Error sending discovery request. network: " + getNetworkName(), ex);
        }
    }
    
    public static final int TRAP_REG_SUPPORT_VERSION_MAJOR = 6;
    public static final int TRAP_REG_SUPPORT_VERSION_MINOR = 2;
    
    /**
     * Only B6 version higher than 6.2R4 (6.2R4 is not included) support trap registration.
     * This method expect B6 version string starts with [major].[minor], and will check if
     * B6 version is higher than 6.2.  
     *  
     * @return
     * 	<b>true</b>: if B6 version is higher than 6.2<br/>
     * 	<b>false</b>: if B6 version is equal or lower than 6.2
     */
    private static boolean isTrapRegistrationSupported(String rawVerStr, String networkName) {
    	if (rawVerStr != null) {
    		try {
	    		Pattern p = Pattern.compile("^(\\d+)\\.(\\d+)");
	    		Matcher m = p.matcher(rawVerStr.trim());
	    		if (m.find() && m.groupCount() >= 2) {
	    			Integer major = Integer.valueOf(m.group(1));
	    			Integer minor = Integer.valueOf(m.group(2));
	    			if (major.intValue() > TRAP_REG_SUPPORT_VERSION_MAJOR) {
	    				return true;
	    			} else if (major.intValue() == TRAP_REG_SUPPORT_VERSION_MAJOR && minor.intValue() > TRAP_REG_SUPPORT_VERSION_MINOR) {
	    				return true;
	    			}
	    		}
    		} catch (Exception e) {
    			logger.warn("Failed to check if trap registration is supported for B6 [" + networkName + "] version [" + rawVerStr + "]. Error: " + e.getMessage(), e);
    		}
    	}
    	if (logger.isDebugEnabled()) {
    		logger.debug("B6 [" + networkName + "] version [" + rawVerStr + "] doesn't support trap registration.");
    	}
    	return false;
    }
    
    public void onTrapRegResponse(BSeriesTaskSignal signal) throws SessionException {
        if(signal.getError() == null) {
            resyncAlarms();
            clearTrapRegFailedAlarm();
        } else {
            raiseTrapRegFailedAlarm();
            logger.warn("Error registering trap. IP: " + signal.getIpAddress() + ". Error: " + signal.getError());
        }
    }

    protected void doRemoveTrapListener() throws SessionException {
        addRemoveTrapDest(false);
    }

    public  boolean isBSeriesMgrProcessDown() {
        if (CoreUtilities.isCMSAlarmExists(JMSConnectionManager.DEFAULT_CMS_AID, CMSObject.getAliasValue("_CMSCondTypeBMGRProcessDown_"))) {
            return true;
        }
        return false;
    }
    private synchronized void doLinkDiscovery() {
        try {
            if(isBSeriesMgrProcessDown()) {
                logger.warn("BSeries manager process down.");
                return;
            }
            deleteExistinglinkforNetwork();
            logger.info("Starting link discovery operation for ipAddress " + getEMSNetwork().getIPAddress1());
            BSeriesTaskSignal signal = new BSeriesTaskSignal();
            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_LINK_DISCOVERY_REQ);
            signal.setNetworkName(getNetworkName());
            signal.setEquipType(((CalixB6Device)getEMSNetwork()).getModel());
            signal.setVersion(((CalixB6Device)getEMSNetwork()).getSWVersion());
            signal.setIpAddress(getEMSNetwork().getIPAddress1());
          //  signal.setSnmpReadCommunity( ((CalixB6Device)getEMSNetwork()).getReadCommunity());
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
        } catch (Exception ex) {
            logger.warn("Error sending discovery request. network: " + getNetworkName(), ex);
        }
    }
    private void deleteExistinglinkforNetwork(){
        //If exists Static Links for network, then Cascade delete them.
        ICMSDatabase database = null;
         DbTransaction txn = null;
        try {
            database = DatabaseManager.getCMSDatabase();
             txn = database.getTransaction("ems", DbTransaction.UPDATE);
             txn.begin();
             logger.info("deleteExistinglinkforNetwork:: IPAddress " + getEMSNetwork().getIPAddress1());
            Collection slinks = findStaticLinks4Network(getNetworkName(),txn);
            if(slinks!=null && slinks.size()>0) {
                Iterator it =slinks.iterator();
                while(it.hasNext()) {
                  NEStaticLink slink = (NEStaticLink) it.next();
                  slink.doRemove(txn);
                }
            }
            txn.commit();
               txn = null;
        } catch(Throwable t) {
                logger.warn("Exceptions on Cascasde delete StaticLinks for " + getNetworkName(),t);
        }         
         finally{
        	 if (txn != null && txn.isActive())
					txn.abort();
         }
    }
    private Collection findStaticLinks4Network(String ntwkName, DbTransaction tx) throws EMSException, EMSDatabaseException {
        ICMSQuery query = new DBCMSQuery(null, "NEStaticLink", "ne1Name='" + ntwkName +
                                                          "' or ne2Name='" + ntwkName + 
                                                          "' and linktype=" + STATIC_EPS_LINK + "'");
        ICMSDatabase db = tx.getDatabase();
        Collection linksCol = query.exec((Object) db, (Object) tx);
        return linksCol;
    }
     public synchronized void onLinkDiscoveryResponse(BSeriesTaskSignal signal) throws SessionException {
         if(signal.getError() == null) {             
             logger.info("onLinkDiscoveryResponse : Received B6 link discovery response");            
           
                 BSeriesAllLinksDiscoveryResponseSignal respSignal = (BSeriesAllLinksDiscoveryResponseSignal) signal;
                 
                 List epsInfoLst = respSignal.getEpsInfoLst();
                  if (epsInfoLst != null && epsInfoLst.size() > 0){
                     processLinkReponse(epsInfoLst);
                  }else{
                     logger.info("onLinkDiscoveryResponse : No links discovered");
                  }
         } else {
             logger.warn("Error in discoverying B6 links . IP: " + signal.getIpAddress() + ". Error: " + signal.getError());
         }
    }
         
     private void doAeOntAssociationDiscovery() {
         try {
             if(isBSeriesMgrProcessDown()) {
                 logger.warn("BSeries manager process down.");
                 return;
             }
             logger.info("Starting ae ont association discovery operation for ipAddress " + getEMSNetwork().getIPAddress1());
             BSeriesTaskSignal signal = new BSeriesTaskSignal();
             signal.setType(BSeriesTaskSignal.SIG_TYPE_AE_ASSOCIATE_DISCOVERY_REQ);
             signal.setNetworkName(getNetworkName());
             signal.setEquipType(((CalixB6Device)getEMSNetwork()).getModel());
             signal.setVersion(((CalixB6Device)getEMSNetwork()).getSWVersion());
             signal.setIpAddress(getEMSNetwork().getIPAddress1());
             JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
         } catch (Exception ex) {
             logger.warn("Error sending ae ont discovery request. network: " + getNetworkName(), ex);
         }
	}
     
     public synchronized void onAeOntAssociationDiscoveryResponse(BSeriesTaskSignal signal) throws SessionException {
         if(signal.getError() == null) {        
        	 
             logger.info("onAeOntAssociationDiscoveryResponse : Received B6 ae ont association discovery response");            
             
             BSeriesAeOntAssociationResponseSignal respSignal = (BSeriesAeOntAssociationResponseSignal) signal;
             ArrayList ipList = (ArrayList)respSignal.getIpList();
             if(ipList!= null){
            	 for(int i = 0; i< ipList.size(); i++){
            		 String ip = (String)ipList.get(i);
            		 if(ip != null && ip.contains(":")){
            			 String[] ipAndPort = ip.split(":");
            			 if(ipAndPort !=null && ipAndPort.length>1){
            				 BseriesGenericInvokeUtilImpl.getInstance().doAeOntDiscovery(ipAndPort[0],ipAndPort[1],respSignal.getNetworkName());
            			 }else{
            				 BseriesGenericInvokeUtilImpl.getInstance().doAeOntDiscovery(ip);
            			 }
            		 }else{
            			 BseriesGenericInvokeUtilImpl.getInstance().doAeOntDiscovery(ip);
            		 }
            	 }
             }else{
                 logger.warn("There is no ae ont association for the device. ");
             }
         }else {
             logger.warn("Error in discoverying B6 ae ont association.  " + getEMSNetwork().getIPAddress1() + ". Error: " + signal.getError());
         }
    }

	private  void processLinkReponse(List epsInfoList){
         ICMSDatabase database = null;
         DbTransaction txn = null;
         try{      
        	 database = DatabaseManager.getCMSDatabase();
             Iterator iterator = epsInfoList.iterator();
             while (iterator.hasNext()){
                 BSeriesLinkDiscoveryResponseSignal respSignal = (BSeriesLinkDiscoveryResponseSignal)iterator.next();
                 //  String destIPAddress = (String)linkProp.get(MediationOperationNames.EPS_NEIGHBOR_IP_ADDRESS);
                 String ne2Name = getNEName(respSignal.getNe2Name());   
                 if (ne2Name != null){
                     logger.info("Source IP " + respSignal.getNe1Name());
                     logger.info("Dest IP " + respSignal.getNe2Name());
                     String region = ((CalixB6Device)m_network).getRegion();
                     logger.info("Source Port " + respSignal.getNe1Port());
                     logger.info("Dest Port " + respSignal.getNe2Port());
                     logger.info("link Type " + respSignal.getLinkType());
                     String ne1Name = getNetworkName();
                                       
                     String ne1Port = respSignal.getNe1Port();
                     String ne2Port = respSignal.getNe2Port();
                     if (!checkForLink(ne1Name,ne2Name,ne1Port,ne2Port)){
                    	 logger.info("processLinkReponse : Link does not exist...creating new link between " + respSignal.getNe1Name()
                    			 + " and " + respSignal.getNe2Name());
                    	 region = isLinkRegionSame(respSignal.getNe1Name(),respSignal.getNe2Name()); 
                    	 if (region != null){
		                         txn = database.getTransaction("ems", DbTransaction.UPDATE);
		                         txn.begin();
		                         NEStaticLink linkObj = new NEStaticLink();
		                         linkObj.setIdentityValue("1");  
		                         linkObj.setNe1Name(ne1Name);
		                         linkObj.setNe2Name(ne2Name);
		                         linkObj.setParentGroupName(region);    
		
		                         linkObj.setNe1Port(new EMSAid(ne1Port));
		                         linkObj.setNe2Port(new EMSAid(ne2Port));
		                         linkObj.setIsUserCreated(new Integer(0));
		                         linkObj.setLinkType(respSignal.getLinkType());
		                         String  linkDesc = ne1Name + ":" + respSignal.getNe1Port() + "<-->" +  ne2Name + ":" + respSignal.getNe2Port();
		                         linkObj.setDescription(linkDesc);
		                         linkObj.doCreate(txn);
		                         txn.commit();
		                         txn = null;
//                                String mediaType = (String)linkProp.get(MediationOperationNames.EPS_MEDIA_TYPE);
//                                String pathGroup = (String)linkProp.get(MediationOperationNames.EPS_PATHGROUP) + "#" + (String)linkProp.get(MediationOperationNames.EPS_NEIGHBOR_PATH_GROUP);
//                                String localDeviceMode = ((Equipment)sourceObj).getUserProperty(MediationOperationNames.EPS_DEVICE_MODE);
//                                String deviceMode = localDeviceMode + "#" + (String)linkProp.get(MediationOperationNames.EPS_NEIGHBOR_DEVICE_MODE);


//                                if (mediaType .equalsIgnoreCase("copper") || (mediaType .equalsIgnoreCase("fiber"))){
//                                    String portType = getPortType(sourceIPAddress,sourcePort);
//                                    if (portType != null)
//                                        linkProps.put(AttributeNames.SOURCE_PORT_TYPE, portType);
//                                    else
//                                        linkProps.put(AttributeNames.SOURCE_PORT_TYPE,"gigabitEthernet");
//                                    portType = getPortType(destIPAddress,destPort);
//                                    if (portType != null)
//                                        linkProps.put(AttributeNames.DEST_PORT_TYPE, portType);
//                                    else
//                                        linkProps.put(AttributeNames.DEST_PORT_TYPE,"gigabitEthernet");
//                                    linkProps.put(AttributeNames.PORT_TYPE, "ethernet");
//                                }else if (mediaType .equalsIgnoreCase("xg_cop") || (mediaType .equalsIgnoreCase("xg_fib"))){
//                                    linkProps.put(AttributeNames.SOURCE_PORT_TYPE,"XggigabitEthernet");
//                                    linkProps.put(AttributeNames.DEST_PORT_TYPE,"XggigabitEthernet");
//                                    linkProps.put(AttributeNames.PORT_TYPE, "XggigabitEthernet");
//                                }else if (mediaType .equalsIgnoreCase("mlppp")){
//                                    linkProps.put(AttributeNames.SOURCE_PORT_TYPE,"ds1");
//                                    linkProps.put(AttributeNames.DEST_PORT_TYPE,"ds1");
//                                    linkProps.put(AttributeNames.PORT_TYPE, "mlppp");
//                                }
                    	 }else{
                    		 logger.info("onLinkDiscoveryResponse : EP2 Link region not same...skipping link creation with " + respSignal.getNe2Name());
                    	 }
                     }
                 }else{
                     logger.info("onLinkDiscoveryResponse : EP2 does not exist " + respSignal.getNe2Name());
                 }
             }
                 
             	
         } catch (Exception e) {
             logger.warn("Unable to create links for" + getEMSNetwork().getIPAddress1(), e);
         } finally{
        	 if (txn != null && txn.isActive())
					txn.abort();
         }
         notifyAll();
    }
   
    private boolean checkForLink(String ntwk1,String ntwk2,String ne1Port,String ne2Port) {
        Collection<NEStaticLink> neStaticLinks = null;
        DbTransaction txn = null;
		try {
			logger.info("  Inside  checkForLink ..ntwk1 " + ntwk1 + " ntwk2"
					+ ntwk2);
			txn = DatabaseManager.getCMSDatabase().getTransaction("ems",
					DbTransaction.READ);			

			ICMSQuery query = new DBCMSQuery(null, "NEStaticLink", "ne1Name='" + ntwk2
					+ "' and ne2Name='" + ntwk1 + "'");
			neStaticLinks = query
					.exec((Object) txn.getDatabase(), (Object) txn);
			if (neStaticLinks != null && neStaticLinks.size() > 0) {				
					return true;
				/*for (NEStaticLink link : neStaticLinks) {
					logger.info(" checkForLink : link.getNe1Name() "
							+ link.getNe1Name() + " link.getNe2Name() "
							+ link.getNe2Name());
					if (link.getNe1Port().equals(ne2Port)
							&& link.getNe2Port().equals(ne1Port)) {
						logger.info(" checkForLink : Link exists ");
						
					}
				}*/
			}
			query = new DBCMSQuery(null, "NEStaticLink", "ne1Name='"
					+ ntwk1 + "' and ne2Name='" + ntwk2 + "'");
			ICMSDatabase db = txn.getDatabase();
			neStaticLinks = query.exec((Object) db, (Object) txn);
			if (neStaticLinks != null && neStaticLinks.size() > 0) {
				return true;			
				/*for (NEStaticLink link : neStaticLinks) {
					logger.info(" checkForLink : link.getNe1Name() "
							+ link.getNe1Name() + " link.getNe2Name() "
							+ link.getNe2Name());
					// Check if any link is associated with this AID
					if (link.getNe1Port().equals(ne1Port)
							&& link.getNe2Port().equals(ne2Port)) {
						logger.info(" checkForLink : Link exists ");
						return true;
					}
				}*/
			}

		} catch (Exception e) {

			logger.warn("Got exception while searching the Static links ", e);
		} finally {
			if (txn != null && txn.isActive())
				txn.abort();
		}
        return false;

    }
    
    /**
     * Return the chassis/network group name if the two B6 networks are under the 
     * same chassis/network group.
     * 
     * Otherwise return network group name if the two B6 networks are in different 
     * chassis but their parent chassis are under the same network group.
     * 
     * @param ntwk1
     * @param ntwk2
     * @return
     */
    private String isLinkRegionSame(String ntwk1,String ntwk2){
    	Collection<CalixB6Device> ne1CalixB6Devices = null;
    	Collection<CalixB6Device> ne2CalixB6Devices = null;
    	logger.info("isLinkRegionSame : Checking is region is same for both EP links: NE1 " + ntwk1 + " NE2 " + ntwk2);
    	DbTransaction tx = DatabaseManager.getCMSDatabase().getTransaction("ems", DbTransaction.READ);
    	try{
    		ICMSQuery query = new DBCMSQuery(null, "CalixB6Device", "ipaddress1='" + ntwk1 +
                    "'");
			ICMSDatabase db = tx.getDatabase();
			ne1CalixB6Devices = query.exec((Object) db, (Object) tx);
			query = new DBCMSQuery(null, "CalixB6Device", "ipaddress1='" + ntwk2 +
                    "'");
			
			ne2CalixB6Devices = query.exec((Object) db, (Object) tx);
			
          if (ne1CalixB6Devices != null && !ne1CalixB6Devices.isEmpty()
        		  && ne2CalixB6Devices != null && !ne2CalixB6Devices.isEmpty()) {                
              for (CalixB6Device ne1CalixB6Device : ne1CalixB6Devices) {
            	  for (CalixB6Device ne2CalixB6Device : ne2CalixB6Devices) {
            		  logger.info("NE1 Region " +  ne1CalixB6Device.getRegion() + 
            				  " NE2 Region " + ne2CalixB6Device.getRegion());
                  //Check if any link is associated with this AID
	            	  if (ne1CalixB6Device.getRegion().equals(ne2CalixB6Device.getRegion())) {
	                      return ne1CalixB6Device.getRegion();
	                  }
            	  }
              }
              // If B6 is under chassis, check chassis's parent
              CalixB6Device ne1 = null;
              CalixB6Device ne2 = null;            
              for (CalixB6Device ne1CalixB6Device : ne1CalixB6Devices) {
            	  if (ntwk1.equalsIgnoreCase(ne1CalixB6Device.getIPAddress1())) {
            		  ne1 = ne1CalixB6Device;
            		  break;
            	  }
              }            
              for (CalixB6Device ne2CalixB6Device : ne2CalixB6Devices) {
            	  if (ntwk2.equalsIgnoreCase(ne2CalixB6Device.getIPAddress1())) {
            		  ne2 = ne2CalixB6Device;
            		  break;
            	  }
              }
              if (ne1 != null && ne2 != null) {
            	  String ne1Region = ne1.getRegion();
            	  String ne2Region = ne2.getRegion();
            	  if (ne1Region.startsWith("CHASSIS-")) {
            		  query = new DBCMSQuery(null, "CalixB6Chassis chassis", "chassis.dbidentity='" + ne1Region + "'");
            		  Collection<CalixB6Chassis> results = query.exec((Object) db, (Object) tx);
            		  if (results != null && results.size() > 0) {
	            		  CalixB6Chassis ne1Chassis = results.iterator().next();
	            		  if (ne1Chassis != null) {
	            			  ne1Region = ne1Chassis.getRegion();
	            		  }
            		  }
            	  }
            	  if (ne2Region.startsWith("CHASSIS-")) {
            		  query = new DBCMSQuery(null, "CalixB6Chassis chassis", "chassis.dbidentity='" + ne2Region + "'");
            		  Collection<CalixB6Chassis> results = query.exec((Object) db, (Object) tx);
            		  if (results != null && results.size() > 0) {
	            		  CalixB6Chassis ne2Chassis = results.iterator().next();
	            		  if (ne2Chassis != null) {
	            			  ne2Region = ne2Chassis.getRegion();
	            		  }
            		  }
            	  }
            	  if (ne1Region != null && ne2Region != null && ne1Region.equals(ne2Region)) {
            		  return ne1Region;
            	  }
              }
          }
      } catch (Exception e) {
          
          logger.warn("Got exception while searching the NE names ", e);
      } finally {
			if (tx != null && tx.isActive())
				tx.abort();
		}

    	return null;
    }
    private String getNEName(String neName){
    	Collection<CalixB6Device> calixB6Devices = null;
    	DbTransaction tx = DatabaseManager.getCMSDatabase().getTransaction("ems", DbTransaction.READ);
    	try{
    		ICMSQuery query = new DBCMSQuery(null, "CalixB6Device", "ipaddress1='" + neName +
                    "'");
			ICMSDatabase db = tx.getDatabase();
			calixB6Devices = query.exec((Object) db, (Object) tx);
          if (calixB6Devices != null && !calixB6Devices.isEmpty()) {                
              for (CalixB6Device calixB6Device : calixB6Devices) {
                  //Check if any link is associated with this AID
            	  if (calixB6Device.getIPAddress1().equals(neName)) {
                      return calixB6Device.getDbIdentity();
                  }
              }
          }
      } catch (Exception e) {
          
          logger.warn("Got exception while searching the NE names ", e);
      } finally {
			if (tx != null && tx.isActive())
				tx.abort();
		}

    	return null;
    }
    private void doGponOntDiscovery(){
    	try {
    		logger.info("GPON ONT discovery Started for :");
            if(isBSeriesMgrProcessDown()) {
                logger.warn("BSeries manager process down.");
                return;
            }
            logger.info("Starting GPON ONT discovery for ipAddress " + getEMSNetwork().getIPAddress1());
            BSeriesTaskSignal signal = new BSeriesTaskSignal();
            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_GPON_ONT_DISCOVERY_REQ);
            signal.setNetworkName(getNetworkName());
            signal.setEquipType(((CalixB6Device)getEMSNetwork()).getModel());
            signal.setVersion(((CalixB6Device)getEMSNetwork()).getSWVersion());
            signal.setIpAddress(getEMSNetwork().getIPAddress1());
            logger.info("GPON ONT discovery Started for : "+getEMSNetwork().getIPAddress1());
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
        } catch (Exception ex) {
            logger.warn("Error sending discovery request. network: " + getNetworkName(), ex);
        }
    }
    public synchronized void onGponOntDiscoveryResponse(BSeriesMediationSignal signal){
    	logger.info("GPON ONT discovery recived signal from mediation :");
    	BSeriesMediationSignal b6MediationSignal = (BSeriesMediationSignal)signal;
    	OccamProtocolRequestResponse responceObj= (OccamProtocolRequestResponse)b6MediationSignal.getOccamProtocolRequestResponse();
    	OccamSystem systemData = new OccamSystem ();
    	systemData.setType("OccamGponOLT");
    	systemData.setHostname(signal.getNetworkName());
    	systemData.setName(signal.getIpAddress());
    	OccamSystemData sysData=(OccamSystemData)responceObj.getParameter(MediationOperationNames.KEY_SYSTEM_INFO);
    	 Properties sysDataProp =(Properties)sysData.getProperties();
    	//initGponInfo(systemData, sysData);
    	//logger.info("Received B6 Gpon Ont discovery response OccamSystemData : " + systemData);
    	//logger.info("Received B6 Gpon Ont discovery response OccamSystemData Properties() : " + systemData.getProperties());
    	//boolean dbStatus = dbApi.addObject(systemData);
    	 DevProperty devProperty = (DevProperty)responceObj.getDeviceProperty();
     	Properties devProp= (Properties)devProperty.getProperties();
     	int oltRetVal = DataBaseAPI.getInstance().deleteGponInfo(devProperty.getIPAddress());
     	initGponOLTInfo(devProperty, sysData);
        addGponInventory(systemData, sysData);
        Properties properties = new Properties();
        properties.put(AttributeNames.MO_KEY, signal.getIpAddress());
        BSeriesDbChangeUtil.getInstance().fireDbChange(properties, OccamStaticDef.MODULE_ID_GPON_ONT);
    }
    private void initGponOLTInfo(DevProperty devProp, OccamSystemData systemData){
    	DataBaseAPI dataBaseAPI = null;
    	try {
    		dataBaseAPI = DataBaseAPI.getInstance();
    		com.occam.ems.be.mo.OccamGponOLT gponOltObj = new com.occam.ems.be.mo.OccamGponOLT();
    		if(devProp.getIPAddress()!=null){
    			gponOltObj.setName(devProp.getIPAddress());
        		gponOltObj.setSwVersion(devProp.getVersion());
        		gponOltObj.setType(devProp.getDeviceType());
        		gponOltObj.setNumOfPons(systemData.getNumPonPorts());
        		gponOltObj.setMaxNumOntPerPon(systemData.getMaxNumOntPerPon()) ;
        		gponOltObj.setDiscoveredOntsList(systemData.getDiscoveredOntsXml());
        		try {
					dataBaseAPI.addObject(gponOltObj);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					logger.warn("Error while adding into OccamGponOLT discovery request. network: "+devProp.getIPAddress(),e);
					e.printStackTrace();
				}
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    private boolean addGponInventory(OccamSystem occamSystem, OccamSystemData systemData){
        DataBaseAPI dataBaseAPI = null;
        try{
        	dataBaseAPI = DataBaseAPI.getInstance();
                Vector ponList = new Vector();
                Vector ontList = new Vector();
                int numPons = systemData.getNumPonPorts();
                String hostName = occamSystem.getHostname();
                String systemName = occamSystem.getName();// key used in topoDB  IP address
                String ponHostNamePrefix = hostName + DiscoveryUtil.NAME_BUILDER_SEPARATOR + OccamStaticDef.PON_KEYNAME;
                String ponNamePrefix = systemName + DiscoveryUtil.NAME_BUILDER_SEPARATOR + OccamStaticDef.PON_KEYNAME;
                String ponName = null;
                String ontName = null;
                String ponHostName = null;
                String ontHostName = null;
                String ontEquipType = null;
                String ontProfName = null;
                List ponDataObjs = systemData.getPons();
                Iterator ponIter = ponDataObjs.iterator();
                // com.occam.ems.common.dataclasses.OccamGPon
                OccamGPon ponData = null;
                // com.occam.ems.common.dataclasses.OccamGponOnt
                com.occam.ems.common.dataclasses.OccamGponONT ontData = null;
                List ontDataObjs = null;
                //Iterator ontIter = null;
                com.occam.ems.be.mo.OccamGpon ponMO = null;
                //com.occam.ems.be.mo.OccamGponOnt ontMO = null;
                com.occam.ems.be.mo.OccamGponOnt ontMO=null;
                int ontId;
                int ponPortNum;

                // creating PON and ONT MO
                while (ponIter.hasNext()) {
                    // ems.common.dataclasses.OccamGpon
                    ponData = (OccamGPon) ponIter.next();
                    ponPortNum = ponData.getPonPortNum();
                    ponName = ponNamePrefix + ponPortNum;
                    ponHostName = ponHostNamePrefix + ponPortNum;
                    ponMO =
                            new com.occam.ems.be.mo.OccamGpon();
                    //ponMO.setName(ponName);
                    //ponMO.setType("OccamGpon");
                    //ponMO.setParentKey(systemName);
                    ponMO.setPonId(ponData.getPonPortNum());
                    //ponMO.setIsContainer(true);
                    //ponMO.setStatusPollEnabled(false);
                    //ponMO.setDisplayName(ponHostName);
                    ponList.add(ponMO);
                    ontDataObjs = ponData.getGponOnts();
                    Iterator ontIter = ontDataObjs.iterator();
                    while (ontIter.hasNext()) {
                        ontData = (com.occam.ems.common.dataclasses.OccamGponONT) ontIter.next();
                         ontMO= new com.occam.ems.be.mo.OccamGponOnt();
                        //ontMO = new com.occam.ems.be.mo.OccamGponOnt();
                        ontId = ontData.getOntId();
                        ontEquipType = ontData.getEquipmentType();
                        ontProfName = ontData.getOntProfile();
                        ontName = ponName + DiscoveryUtil.GPON_ONT_NAME_BUILDER_SEPARATOR + ontId;
                        ontHostName = ontData.getOntProperty(OccamGponONT.GPONONT_PROP_NAME);
                        if (ontHostName == null || ontHostName.length()== 0)
                        {
                            ontHostName = ponHostName + DiscoveryUtil.GPON_ONT_NAME_BUILDER_SEPARATOR + ontId;
                        }
                        ontMO.setName(ontName);// key for topoDB
                        
                        //ontMO.setLocation(occamSystem.getLocation());
                        ontMO.setPonPortNum(ontData.getPonPortNum());
                        ontMO.setOntPonId(ontId);
                        ontMO.setOntProfile(ontProfName);
                        ontMO.setModel(ontEquipType);
                        // use the provision ont profile type as equip type for phy model can be different from the provision ont profile model
                        // this is for ESW 6.1 and above
                        if (ontProfName != null && ontProfName.length() >0)
                        {
                            ontMO.setEquipmentType(ontProfName);
                        }
                        else
                        {
                            ontMO.setEquipmentType(ontEquipType);
                        }
                        ontMO.setRegistrationId(ontData.getRegId());
                        ontMO.setManageFlag(ontData.getManageFlag());
                        ontMO.setOltKey(systemName);
                        ontMO.setEntityHardwareRev(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_HW_REV));
                        ontMO.setEntitySoftwareRev(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_SW_REV));
                        // this is the xml fragment store the slot info
                        ontMO.setSlotInfo(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_SLOT_INFO));
                        ontMO.setVendorID(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_VENDOR_ID));
                        ontMO.setEntitySerialNum(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_SERIAL_NUM));
                        ontMO.setOntDistance(ontData.getOntDistance());                        
                        if (ontProfName != null && ontProfName.length() > 0) {
                           // ontMO.setDisplayName(ontHostName + "(" + ontProfName + ")");
                        	ontMO.setDisplayName(ontHostName);
                        } else {
                            ontMO.setDisplayName(ontHostName);
                        }
                        ontMO.setAdminState(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_ADMIN_STATUS));
                        ontMO.setOperationState(ontData.getOntProperty(OccamGponONT.GPONONT_PROP_OPER_STATUS));
                       
                        ontList.add(ontMO);
                       
                    }
                }

                if (ponList.size() > 0) {
                    //dbstatus = dbApi.addGponObjects(ponList);
                }
                //dbApi.addGponOntObjects(Vector vec)
                try {
                	logger.info("GPON ONT discovery :Delete All ONTs Exception e : ");
                	//int retVal =dataBaseAPI.deleteGponInfo(AttributeNames.OCCAM_GPON_ONT_DEVICE,systemName);
                	//logger.info("GPON ONT discovery :Delete All ONTs Exception e : number of rows deleted : "+retVal);
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("GPON ONT discovery : Exception e : "+e);
				}
                if (ontList.size() > 0) {
                	try {
                		
						dataBaseAPI.addObjects(ontList);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    try{
                    	 
                        final Properties prop = new Properties();
                       // prop.put(AttributeNames.MO_KEY, occamSystem.getName());
                       // BENotifierImpl.getInstance().notifyAllTargets(prop, OccamStaticDef.MODULE_ID_GPON_ONT);
                    }catch(Exception e){
                    	return false;
                    }
                }
            return true;
    }catch (Exception e) {
		// TODO: handle exception
    	logger.info("GPON ONT discovery : Exception e : "+e);
    	return false;
	}
  }
    private void doChassisDiscovery() {
        try {
            if(isBSeriesMgrProcessDown()) {
                logger.warn("BSeries manager process down.");
                return;
            }
            logger.info("Starting chassis discovery operation for ipAddress " + getEMSNetwork().getIPAddress1());
            BSeriesTaskSignal signal = new BSeriesTaskSignal();
            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_CHASSIS_DISCOVERY_REQ);
            signal.setNetworkName(getNetworkName());
            signal.setEquipType(((CalixB6Device)getEMSNetwork()).getModel());
            signal.setVersion(((CalixB6Device)getEMSNetwork()).getSWVersion());
            signal.setIpAddress(getEMSNetwork().getIPAddress1());
          //  signal.setSnmpReadCommunity( ((CalixB6Device)getEMSNetwork()).getReadCommunity());
            JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
        } catch (Exception ex) {
            logger.warn("Error sending chassis discovery request. network: " + getNetworkName(), ex);
        }
    }
    
    public synchronized void onChassisDiscoveryResponse(BSeriesTaskSignal signal) throws SessionException {
        
         if(signal.getError() == null) {             
             logger.info("onChassisDiscoveryResponse : Received B6 Chassis discovery response for Network " + getNetworkName());            
             ICMSDatabase database = null;
         DbTransaction txn = null;
         boolean createSuccess = false;
         BSeriesChassisDiscoveryResponseSignal respSignal = (BSeriesChassisDiscoveryResponseSignal) signal;
         String chassisName = "CHASSIS-" + respSignal.getSerialNumber();
         if (respSignal.getSerialNumber() != null){			
			try {
				CalixB6Chassis calixB6Chassis = checkChassisExists(respSignal.getSerialNumber());
				if (calixB6Chassis == null) {	
					logger.info("onChassisDiscoveryResponse : Creating new Chassis for network " + getNetworkName());
					database = DatabaseManager.getCMSDatabase();
					txn = database.getTransaction("ems", DbTransaction.UPDATE);
					txn.begin();
					
					CalixB6Chassis chassis = new CalixB6Chassis();
					chassis.setDbIdentity(chassisName);
					chassis.setSID(chassisName);					
					chassis.setChassisType(respSignal.getChassisType());
					chassis.setDisplayName(respSignal.getSerialNumber());
					chassis.setSerialNumber(respSignal.getSerialNumber());
					chassis.setManufactureDate(respSignal
							.getManufacturingDate());
					
					chassis.setRegion(getEMSNetwork().getRegion());
					chassis.setWidth(100);
					chassis.setHeight(100);
					chassis.setXOffset(100);
					chassis.setYOffset(100);
					chassis.doCreate(txn);
					
				}else{
					database = DatabaseManager.getCMSDatabase();
					txn = database.getTransaction("ems", DbTransaction.UPDATE);
					txn.begin();
					calixB6Chassis.setChassisType(respSignal.getChassisType());
				//CMS-CMS-7822	if the chassis name has been changed by user, keep it ,needn't to update display name
				//	calixB6Chassis.setDisplayName(respSignal.getSerialNumber());					
					calixB6Chassis.setManufactureDate(respSignal.getManufacturingDate());
					calixB6Chassis.doUpdate(txn);
					logger.info("onChassisDiscoveryResponse : Updating existing Chassis for network " + getNetworkName());
				}
				txn.commit();
				txn = null;
			} catch (Exception e) {
				logger.warn("Unable to create chassis for"
						+ getEMSNetwork().getIPAddress1(), e);
			} finally {
				if (txn != null && txn.isActive())
					txn.abort();
			}
			//if (createSuccess)
			updateRegionForB6Links(chassisName);
			updateRegionForB6Device(chassisName);
         } 
         } else {
             logger.warn("Error in discoverying B6 Chassis .  " + getEMSNetwork().getIPAddress1() + ". Error: " + signal.getError());
         }
         doLinkDiscovery();
    }
    
    /**
     * Only update b6 link's parent region when the ne1 and ne2 are not in the same chassis
     * @param regionName
     */
    private void updateRegionForB6Links(String regionName){
    	 Collection<NEStaticLink> neStaticLinks = null;
         DbTransaction txn = null;
         ICMSDatabase database = null;
 		try {
 			logger.info("  Inside  updateRegionForB6Links for Network " + getNetworkName() );
 			database = DatabaseManager.getCMSDatabase();
			txn = database.getTransaction("ems", DbTransaction.UPDATE);
			txn.begin();		

 			ICMSQuery query = new DBCMSQuery(null, "NEStaticLink", "ne1Name='" + getNetworkName() + "'");
 			neStaticLinks = query.exec((Object) database, (Object) txn);
 			if (neStaticLinks != null && neStaticLinks.size() > 0) {			
 				
 	                Iterator it =neStaticLinks.iterator();
 	                while(it.hasNext()) {
 	                  NEStaticLink slink = (NEStaticLink) it.next();
 	                  	String ne2Name = slink.getNe2Name();
 	                  	// Load B6 ne2 and check if they have the same parent region. 
 	                  	// Only change link's parent when both ne1 and ne2's parent 
 	                  	// region are the same, otherwise keep original parent region.
 	      				query = new DBCMSQuery(null, "CalixB6Device device", "device=dbidentity='" + ne2Name + "'");
 	      				Collection<CalixB6Device> results = query.exec((Object) database, (Object) txn);
 	      				if (results != null && results.size() > 0) {
	 	      				CalixB6Device ne2 = results.iterator().next();
	 	      				if (ne2 != null && ne2.getRegion() != null && ne2.getRegion().equals(regionName)) {
	 	      					slink.setParentGroupName(regionName);
	 	      					slink.doUpdate(txn);
	 	      				}
 	      				}
 	                }
 	           
 	            
 			
 			}
 			query = new DBCMSQuery(null, "NEStaticLink","ne2Name='" + getNetworkName() + "'");
 			//ICMSDatabase db = txn.getDatabase();
 			neStaticLinks = query.exec((Object) database, (Object) txn);
 			if (neStaticLinks != null && neStaticLinks.size() > 0) {
 				Iterator it =neStaticLinks.iterator();
	                while(it.hasNext()) {
	                  NEStaticLink slink = (NEStaticLink) it.next();
	                  	String ne1Name = slink.getNe1Name();
	                  	// Load B6 ne1 and check if they have the same parent region. 
	                  	// Only change link's parent when both ne1 and ne2's parent 
	                  	// region are the same, otherwise keep original parent region.
	      				query = new DBCMSQuery(null, "CalixB6Device device", "device.dbidentity='" + ne1Name + "'");
 	      				Collection<CalixB6Device> results = query.exec((Object) database, (Object) txn);
 	      				if (results != null && results.size() > 0) {
		      				CalixB6Device ne1 = results.iterator().next();
	 	      				if (ne1 != null && ne1.getRegion() != null && ne1.getRegion().equals(regionName)) {
	 	      					slink.setParentGroupName(regionName);
			                  	slink.doUpdate(txn);
	 	      				}
 	      				}
	                }			
 				
 			}
 			txn.commit();
            txn = null;
 		} catch (Exception e) {

 			logger.warn("Got exception while searching the Static links ", e);
 		} finally {
 			if (txn != null && txn.isActive())
 				txn.abort();
 		}         
    }
    private void updateRegionForB6Device(String regionName){
		ICMSDatabase database = null;
		DbTransaction txn = null;
		try {
			logger.info("updateRegionForB6Device : updating region for network " + getNetworkName());
			database = DatabaseManager.getCMSDatabase();
			txn = database.getTransaction("ems", DbTransaction.UPDATE);
			txn.begin();
			((CalixB6Device) m_network).setRegion(regionName);
			m_network.doUpdate(txn);
			txn.commit();
			txn = null;
			onNetworkUpdate(m_network);
			new DBChangeListener().updateObject(m_network, null);
		} catch (Exception ex) {
			logger.warn("Unable to update network for. network name: "
					+ getNetworkName(), ex);
		} finally {
			if (txn != null && txn.isActive())
				txn.abort();
		}
    }
    private CalixB6Chassis checkChassisExists(String chassisSerialNumber){
        Collection<CalixB6Chassis> calixB6ChassisObjs = null;
        DbTransaction txn = null;
        try {
        	ICMSDatabase database = DatabaseManager.getCMSDatabase();
            txn = database.getTransaction("ems", DbTransaction.READ);            
        	ICMSQuery query = new DBCMSQuery(null, "CalixB6Chassis", "serialnumber='" + chassisSerialNumber +
                    "'");			
			calixB6ChassisObjs = query.exec((Object) database, (Object) txn);           
            if (calixB6ChassisObjs != null && calixB6ChassisObjs.size() > 0){
            	logger.info("checkChassisExists :: " + calixB6ChassisObjs.size());
                for (CalixB6Chassis calixB6Chassis : calixB6ChassisObjs) {
                	if (calixB6Chassis.getSerialNumber().equals(chassisSerialNumber)) {
                        return calixB6Chassis;
                    }                                        
                }
            }
            
        } catch (Exception e) {            
            logger.warn("Got exception while searching the B6 Chassis ", e);
        } finally {
			if (txn != null && txn.isActive())
				txn.abort();
		}
        return null;
    }   



}
