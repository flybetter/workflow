/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

//---------------------------------/
//- Imported classes and packages -/
//BEGIN IMPORTS
import java.util.Collection;

import com.calix.ems.exception.EMSException;
import com.calix.ems.exception.SemanticException;
import com.calix.ems.exception.SessionException;
import com.calix.ems.database.CMSDBQuery;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.system.common.log.Log;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.ems.query.DBCMSQuery;
import com.calix.ems.query.ICMSQuery;
import com.calix.ems.security.CMSDESCipher;
import com.calix.ems.database.C7Database;
import com.calix.ems.server.dbmodel.CMSEmailAndAlarmCNotifSub;
import com.calix.system.common.protocol.tlv.ResultCode;
import com.calix.system.server.dbmodel.BaseSNMPDeviceNetwork;
import com.calix.system.server.dbmodel.CMSObject;
import com.calix.system.server.dbmodel.Helper;
import com.calix.system.server.dbmodel.SwVersionNo;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
//END IMPORTS
//---------------------------------/




import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.server.DataBaseAPI;

/**
 * Class CalixB6Device.
 * 
 * @version $Revision$ $Date$
 */
public class CalixB6Device extends BaseSNMPDeviceNetwork {



	private static final Logger log = Logger.getLogger(CalixB6Device.class);
      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Enable Password
     */
    public String m_EnablePassword;

    /**
     * HW Version
     */
    public String m_HWVersion;

    /**
     * MAC Address
     */
    public String m_MACAddress;

    /**
     * Date and Time
     */
    public String m_ManufactureDate;

    /**
     * Model
     */
    public String m_Model;

    /**
     * Date and Time
     */
    public String m_SWVersion;

    /**
     * Serial Number
     */
    public String m_SerialNumber;

    /**
     * Shelf Id
     */
    public String m_ShelfId;

    /**
     * Slot Id
     */
    public Integer m_SlotId;

    /**
     * Synchronize on connect
     */
    public Integer m_TimeZone;

    /**
     * Web Password
     */
    public String m_WebPassword;

    /**
     * Web Username
     */
    public String m_WebUsername;

    
    /**
     */
    public Integer m_keepChassis;

    /**
     * SNMP Device Profile ID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_profile;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "CalixB6Device";

    /**
     * new int[]{empty networkid, CalixB6Device}
     */
    public static int[] m_hierarchy = new int[]{0, 13233};

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public CalixB6Device() {
        super();
        m_port = new Integer(0x0017);
        m_PluginModule = "bseries";
        m_SnmpPort = new Integer(0x00A1);
    } //-- com.calix.bseries.server.dbmodel.CalixB6Device()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method copyFields
     * 
     * @param obj1
     */
    protected void copyFields(CMSObject obj1)
    {
        if( obj1 instanceof CalixB6Device ) {
            super.copyFields(obj1);
            CalixB6Device obj = (CalixB6Device)obj1;
            setEnablePassword((String)Helper.copy(obj.getEnablePassword()));
            setHWVersion((String)Helper.copy(obj.getHWVersion()));
            setMACAddress((String)Helper.copy(obj.getMACAddress()));
            setManufactureDate((String)Helper.copy(obj.getManufactureDate()));
            setModel((String)Helper.copy(obj.getModel()));
            setSWVersion((String)Helper.copy(obj.getSWVersion()));
            setSerialNumber((String)Helper.copy(obj.getSerialNumber()));
            setShelfId((String)Helper.copy(obj.getShelfId()));
            setSlotId((Integer)Helper.copy(obj.getSlotId()));
            setTimeZone((Integer)Helper.copy(obj.getTimeZone()));
            setWebPassword((String)Helper.copy(obj.getWebPassword()));
            setWebUsername((String)Helper.copy(obj.getWebUsername()));
            setkeepChassis((Integer)Helper.copy(obj.getkeepChassis()));
            setprofile((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getprofile()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getEnablePassword
     */
    public String getEnablePassword()
    {
    	//BEGIN_FUNCTION
        if(m_EnablePassword  == null && m_encryptedEnablePassword != null) {
        	m_EnablePassword  = new String(CMSDESCipher.getInstance().decrypt(m_encryptedEnablePassword));
        }
        return m_EnablePassword;
        //END_FUNCTION
    } //-- String getEnablePassword() 

    /**
     * Method getHWVersion
     */
    public String getHWVersion()
    {
        return this.m_HWVersion;
    } //-- String getHWVersion() 

    /**
     * Method getHierarchy
     */
    public int[] getHierarchy()
    {
        return m_hierarchy;
    } //-- int[] getHierarchy() 

    /**
     * Method getMACAddress
     */
    public String getMACAddress()
    {
        return this.m_MACAddress;
    } //-- String getMACAddress() 

    /**
     * Method getManufactureDate
     */
    public String getManufactureDate()
    {
        return this.m_ManufactureDate;
    } //-- String getManufactureDate() 

    /**
     * Method getModel
     */
    public String getModel()
    {
        return this.m_Model;
    } //-- String getModel() 

    /**
     * Method getSWVersion
     */
    public String getSWVersion()
    {
        return this.m_SWVersion;
    } //-- String getSWVersion() 

    /**
     * Method getSerialNumber
     */
    public String getSerialNumber()
    {
        return this.m_SerialNumber;
    } //-- String getSerialNumber() 

    /**
     * Method getShelfId
     */
    public String getShelfId()
    {
        return this.m_ShelfId;
    } //-- String getShelfId() 

    /**
     * Method getSlotId
     */
    public Integer getSlotId()
    {
        return this.m_SlotId;
    } //-- Integer getSlotId() 

    /**
     * Method getTimeZone
     */
    public Integer getTimeZone()
    {
        return this.m_TimeZone;
    } //-- Integer getTimeZone() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.CalixB6Device;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getWebPassword
     */
    public String getWebPassword()
    {
        //BEGIN_FUNCTION
        if(m_WebPassword == null && m_encryptedWebPassword != null) {
            m_WebPassword = new String(CMSDESCipher.getInstance().decrypt(m_encryptedWebPassword));
        }
        return m_WebPassword;
        //END_FUNCTION
    } //-- String getWebPassword() 

    /**
     * Method getWebUsername
     */
    public String getWebUsername()
    {
        return this.m_WebUsername;
    } //-- String getWebUsername() 

    /**
     * Method getkeepChassis
     */
    public Integer getkeepChassis()
    {
        return this.m_keepChassis;
    } //-- Integer getkeepChassis() 

    /**
     * Method getprofile
     */
    public com.calix.system.server.dbmodel.ICMSAid getprofile()
    {
        return this.m_profile;
    } //-- com.calix.system.server.dbmodel.ICMSAid getprofile() 

    /**
     * Method isEMSCacheSupported
     */
    public boolean isEMSCacheSupported()
    {
        return true;
    } //-- boolean isEMSCacheSupported() 

    /**
     * Method populateAttributeFromTLV
     * 
     * @param tlv
     * @param from_version
     */
    public void populateAttributeFromTLV(TLV tlv, SwVersionNo from_version)
    {
        if (tlv == null)
            return;
        switch (tlv.getType()) {
            case 0x0881:
                if (m_ManufactureDate == null) {
                    m_ManufactureDate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x20BE:
                if (m_profile == null) {
                    m_profile = TLVHelper.getAidValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D1:
                if (m_SerialNumber == null) {
                    m_SerialNumber = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D2:
                if (m_Model == null) {
                    m_Model = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D3:
                if (m_HWVersion == null) {
                    m_HWVersion = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D4:
                if (m_MACAddress == null) {
                    m_MACAddress = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D6:
                if (m_SWVersion == null) {
                    m_SWVersion = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E7:
                if (m_keepChassis == null) {
                    m_keepChassis = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E8:
                if (m_TimeZone == null) {
                    m_TimeZone = TLVHelper.getShortValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F1:
                if (m_WebUsername == null) {
                    m_WebUsername = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F2:
                if (m_WebPassword == null) {
                    m_WebPassword = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3402:
                if (m_ShelfId == null) {
                    m_ShelfId = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3403:
                if (m_SlotId == null) {
                    m_SlotId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3561:
                if (m_EnablePassword == null) {
                    m_EnablePassword = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
        }
        super.populateAttributeFromTLV(tlv, from_version);
    } //-- void populateAttributeFromTLV(TLV, SwVersionNo) 

    /**
     * Method populateTLVFromAttributes
     * 
     * @param tlv
     * @param to_version
     */
    public void populateTLVFromAttributes(TLV tlv, SwVersionNo to_version)
    {
        if (tlv == null)
            return;
        super.populateTLVFromAttributes(tlv, to_version);
        TLVHelper.addEmbeddedTLV(tlv, 0x3561, m_EnablePassword);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D3, m_HWVersion);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D4, m_MACAddress);
        TLVHelper.addEmbeddedTLV(tlv, 0x0881, m_ManufactureDate);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D2, m_Model);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D6, m_SWVersion);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D1, m_SerialNumber);
        TLVHelper.addEmbeddedTLV(tlv, 0x3402, m_ShelfId);
        TLVHelper.addEmbeddedTLV(tlv, 0x3403, m_SlotId);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E8, m_TimeZone, TLVHelper.Short);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F2, m_WebPassword);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F1, m_WebUsername);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E7, m_keepChassis);
        TLVHelper.addEmbeddedTLV(tlv, 0x20BE, m_profile);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setEnablePassword
     * 
     * @param EnablePassword
     */
    public void setEnablePassword(String EnablePassword)
    {
        this.m_EnablePassword = EnablePassword;
        if (m_EnablePassword != null){
            setEncryptedEnablePassword(CMSDESCipher.getInstance().encrypt(m_EnablePassword.getBytes()));
        }
    } //-- void setEnablePassword(String) 

    /**
     * Method setHWVersion
     * 
     * @param HWVersion
     */
    public void setHWVersion(String HWVersion)
    {
        this.m_HWVersion = HWVersion;
    } //-- void setHWVersion(String) 

    /**
     * Method setMACAddress
     * 
     * @param MACAddress
     */
    public void setMACAddress(String MACAddress)
    {
        this.m_MACAddress = MACAddress;
    } //-- void setMACAddress(String) 

    /**
     * Method setManufactureDate
     * 
     * @param ManufactureDate
     */
    public void setManufactureDate(String ManufactureDate)
    {
        this.m_ManufactureDate = ManufactureDate;
    } //-- void setManufactureDate(String) 

    /**
     * Method setModel
     * 
     * @param Model
     */
    public void setModel(String Model)
    {
        this.m_Model = Model;
    } //-- void setModel(String) 

    /**
     * Method setSWVersion
     * 
     * @param SWVersion
     */
    public void setSWVersion(String SWVersion)
    {
        this.m_SWVersion = SWVersion;
    } //-- void setSWVersion(String) 

    /**
     * Method setSerialNumber
     * 
     * @param SerialNumber
     */
    public void setSerialNumber(String SerialNumber)
    {
        this.m_SerialNumber = SerialNumber;
    } //-- void setSerialNumber(String) 

    /**
     * Method setShelfId
     * 
     * @param ShelfId
     */
    public void setShelfId(String ShelfId)
    {
        this.m_ShelfId = ShelfId;
    } //-- void setShelfId(String) 

    /**
     * Method setSlotId
     * 
     * @param SlotId
     */
    public void setSlotId(Integer SlotId)
    {
        this.m_SlotId = SlotId;
    } //-- void setSlotId(Integer) 

    /**
     * Method setTimeZone
     * 
     * @param TimeZone
     */
    public void setTimeZone(Integer TimeZone)
    {
        this.m_TimeZone = TimeZone;
    } //-- void setTimeZone(Integer) 

    /**
     * Method setTypeName
     * 
     * @param typeName
     */
    public boolean setTypeName(String typeName)
    {
        return false;
    } //-- boolean setTypeName(String) 

    /**
     * Method setWebPassword
     * 
     * @param WebPassword
     */
    public void setWebPassword(String WebPassword)
    {
        //BEGIN_FUNCTION
        this.m_WebPassword = WebPassword;
        if (m_WebPassword != null){
            setEncryptedWebPassword(CMSDESCipher.getInstance().encrypt(m_WebPassword.getBytes()));
        }
        //END_FUNCTION
    } //-- void setWebPassword(String) 

    /**
     * Method setWebUsername
     * 
     * @param WebUsername
     */
    public void setWebUsername(String WebUsername)
    {
        this.m_WebUsername = WebUsername;
    } //-- void setWebUsername(String) 

    /**
     * Method setkeepChassis
     * 
     * @param keepChassis
     */
    public void setkeepChassis(Integer keepChassis)
    {
        this.m_keepChassis = keepChassis;
    } //-- void setkeepChassis(Integer) 

    /**
     * Method setprofile
     * 
     * @param profile
     */
    public void setprofile(com.calix.system.server.dbmodel.ICMSAid profile)
    {
        this.m_profile = profile;
    } //-- void setprofile(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof CalixB6Device ) {
            super.updateFields(obj1);
            CalixB6Device obj = (CalixB6Device)obj1;
           if (obj.getEnablePassword() != null )
               setEnablePassword((String)Helper.copy(obj.getEnablePassword()));
           if (obj.getHWVersion() != null )
               setHWVersion((String)Helper.copy(obj.getHWVersion()));
           if (obj.getMACAddress() != null )
               setMACAddress((String)Helper.copy(obj.getMACAddress()));
           if (obj.getManufactureDate() != null )
               setManufactureDate((String)Helper.copy(obj.getManufactureDate()));
           if (obj.getModel() != null )
               setModel((String)Helper.copy(obj.getModel()));
           if (obj.getSWVersion() != null )
               setSWVersion((String)Helper.copy(obj.getSWVersion()));
           if (obj.getSerialNumber() != null )
               setSerialNumber((String)Helper.copy(obj.getSerialNumber()));
           if (obj.getShelfId() != null )
               setShelfId((String)Helper.copy(obj.getShelfId()));
           if (obj.getSlotId() != null )
               setSlotId((Integer)Helper.copy(obj.getSlotId()));
           if (obj.getTimeZone() != null )
               setTimeZone((Integer)Helper.copy(obj.getTimeZone()));
           if (obj.getWebPassword() != null )
               setWebPassword((String)Helper.copy(obj.getWebPassword()));
           if (obj.getWebUsername() != null )
               setWebUsername((String)Helper.copy(obj.getWebUsername()));
           if (obj.getkeepChassis() != null )
               setkeepChassis((Integer)Helper.copy(obj.getkeepChassis()));
           if (obj.getprofile() != null )
               setprofile((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getprofile()));
        }
    } //-- void updateFields(CMSObject) 
    // BEGIN CODE
    @Override
    public void processExecute(DbTransaction tx) throws EMSException {
        super.processExecute(tx);
        SessionManager sessionManager = SessionManager.getInstance();
        final SouthboundSession sbSession = sessionManager.createNewSouthboundSession(this, null);

        if(m_AutoConnect != null && m_AutoConnect == 1) {
            //try to connect network with a seperate thread
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        sbSession.connect();
                    } catch (SessionException e) {
                        Log.session().warn("Unable to connect network for " + sbSession.getNetworkName());
                    }
                }
            });
            t.start();
        }
    }


    @Override
    public SouthboundSession createSouthboundSession() throws SessionException {
        return new BseriesDeviceSBSession(new Integer(this.getReferenceId()),this);
    }
    
    private byte [] m_encryptedWebPassword;
    private byte [] m_encryptedReadCommunity;
    private byte [] m_encryptedWriteCommunity;
    
    private byte [] m_encryptedEnablePassword;
    
    public byte [] getEncryptedEnablePassword() {
        return m_encryptedEnablePassword;
    }
    public void setEncryptedEnablePassword(byte [] encryptedEnalePassword) {
       m_encryptedEnablePassword = encryptedEnalePassword;
        if(m_EnablePassword == null && m_encryptedEnablePassword != null) {
              m_EnablePassword = new String(CMSDESCipher.getInstance().decrypt(m_encryptedEnablePassword));
        }
    }


    public byte [] getEncryptedWebPassword() {
        return m_encryptedWebPassword;
    }

    public void setEncryptedWebPassword(byte [] encryptedWebPassword) {
        m_encryptedWebPassword = encryptedWebPassword;
        //CMS-12345 by Homer
        // after cache clear, when cache manager get the data from db, set webpassword also. 
        if(m_WebPassword == null && m_encryptedWebPassword != null) {
            m_WebPassword = new String(CMSDESCipher.getInstance().decrypt(m_encryptedWebPassword));
        }
    }
    
    public void setNetworkLoginPassword(String NetworkLoginPassword) {
        super.setNetworkLoginPassword(NetworkLoginPassword);
        if (getNetworkLoginPassword() != null)
            setEncryptedNetworkPassword(CMSDESCipher.getInstance().encrypt(getNetworkLoginPassword().getBytes()));
    }
    
    public byte[] getEncryptedReadCommunity() {
        return m_encryptedReadCommunity;
    }


    public void setEncryptedReadCommunity(byte[] m_encryptedReadCommunity) {
        this.m_encryptedReadCommunity = m_encryptedReadCommunity;
    }


    public byte[] getEncryptedWriteCommunity() {
        return m_encryptedWriteCommunity;
    }


    public void setEncryptedWriteCommunity(byte[] m_encryptedWriteCommunity) {
        this.m_encryptedWriteCommunity = m_encryptedWriteCommunity;
    }
    

    @Override
    public void setReadCommunity(String readCommunity)
    {
        m_ReadCommunity = readCommunity;
        if (m_ReadCommunity != null){
            setEncryptedReadCommunity(CMSDESCipher.getInstance().encrypt(m_ReadCommunity.getBytes()));
        }
    } 

    @Override
    public void setWriteCommunity(String writeCommunity)
    {
        this.m_WriteCommunity = writeCommunity;
        if (m_WriteCommunity != null){
            setEncryptedWriteCommunity(CMSDESCipher.getInstance().encrypt(m_WriteCommunity.getBytes()));
        }
    }


    @Override
    public String getReadCommunity() {
        if (m_ReadCommunity == null && m_encryptedReadCommunity != null){
            m_ReadCommunity = new String(CMSDESCipher.getInstance().decrypt(m_encryptedReadCommunity));
        }
        return m_ReadCommunity;
    }


    @Override
    public String getWriteCommunity() {
        if (m_WriteCommunity == null && m_encryptedWriteCommunity != null){
            m_WriteCommunity = new String(CMSDESCipher.getInstance().decrypt(m_encryptedWriteCommunity));
        }
        return m_WriteCommunity;
    } 
    @Override
    public void semanticRemove() throws SemanticException, EMSException {
        super.semanticRemove();
        
        //Check Alarm Closure
        BseriesDeviceSBSession session = (BseriesDeviceSBSession)SessionManager.getInstance().getSouthboundSession(this.getNetworkName().toString());
		log.warn("CalixB6Device : semanticRemove : Deleting device :"+ session.getEMSNetwork().getIPAddress1());
        
		String erryMsg = checkACInUseForIP(session.getEMSNetwork().getIPAddress1());
        if(erryMsg != null){
       	 	throw new SemanticException(ResultCode._RcFail_, "The B6 is been used for Alarm Closure: " + erryMsg + ", please remove it first: WEB GUI, Administration > E-mail and Alarm Closure. ");
        }
    }
    
    @Override
    protected void processRemove(DbTransaction tx) throws SemanticException,
    		EMSException {
    	// TODO Auto-generated method stub
    	CalixB6Device calixB6Device = (CalixB6Device)dbLoad(tx);
    	super.processRemove(tx);
    	
    	//Fix for bug cms-7723
    	String region = calixB6Device.getRegion();
    	if (region.startsWith("CHASSIS-")){
    		if (!checkB6WithSameChassisExists(tx,region)){
    			deleteChassis(tx,region);
    		}
    	}
        
    	 if(OccamUtils.isGponOlt(calixB6Device.getModel())){
    		 log.warn("CalixB6Device : processRemove : Deleting Gpon ONT for device :"+calixB6Device.getIPAddress1());
    		 DataBaseAPI.getInstance().deleteGponInfo(calixB6Device.getIPAddress1());
    	 }
         //NOTE: When a device deletion is successful and If the device
					//doesn't contain any Pushed SPs or Activated Ports, then we need to clear port
					//info from ServiceAssociation table for that particular device
         OccamUtils.deletePortInfoforDevice(calixB6Device.getIPAddress1());
		 DataBaseAPI.getInstance().deleteGponInfo(calixB6Device.getIPAddress1());
		 cleanUpIphostRecords(calixB6Device);
    }
   
    private void cleanUpIphostRecords(CalixB6Device device) {
	    log.info("CalixB6Device : processRemove : Deleting CalixIpHost record for device: "+ device.getIPAddress1());
		short referenceid = -1;
		C7Database db = C7Database.getInstance();
		try {
			referenceid = device.getReferenceId();
			if (referenceid == -1) {
				log.warn("Can not find the related B6 device, deviceName: " + device.getIPAddress1());
			}
			db.beginTransaction();
			db.removeObjects("CalixIpHost", referenceid);
			db.commitTransaction();
		} catch (Exception e) {
			log.error("clean CalixIpHost for device: " + device.getIPAddress1(), e);
			db.rollbackTransaction();
		} finally {
			db.close();
		}
	}


	private String checkACInUseForIP(String ipAddress1) {
    	Collection subList = list(CMSEmailAndAlarmCNotifSub.TYPE_NAME);
    	if(subList!=null && subList.size()!= 0){
            for (Iterator it = subList.iterator(); it.hasNext();) {
            	CMSEmailAndAlarmCNotifSub emailNotif = (CMSEmailAndAlarmCNotifSub) it.next();
				if (emailNotif.getEnabled() == 1
						&& ((emailNotif.getPrimaryIp() != null && emailNotif
								.getPrimaryIp().equals(ipAddress1)) || (emailNotif
								.getSecondaryIp() != null && emailNotif
								.getSecondaryIp().equals(ipAddress1)))) {
					return emailNotif.getName();
				}
            }
    	}
    	return null;
	}
    
    public Collection list(String name ) {
        List subscriptions = null;
        try {
            C7Database.getInstance().beginTransaction();
            subscriptions = C7Database.getInstance().findAllObjects(name);
            C7Database.getInstance().commitTransaction();
            return subscriptions;
        } catch (Exception ex) {
            log.error("Error reloading subscriptions for type: " + name, ex);
            C7Database.getInstance().rollbackTransaction();
        } finally {
            C7Database.getInstance().close();
        }
        return subscriptions;
    }


	private boolean checkB6WithSameChassisExists(DbTransaction tx,String region){
           
        try {
        	
        	ICMSQuery query = new DBCMSQuery(null, "CalixB6Device", "region='" + region +
                    "'");			
        	Collection<CalixB6Device> calixB6DeviceObjs = query.exec(tx.getDatabase(), tx);           
            if (calixB6DeviceObjs != null && calixB6DeviceObjs.size() > 1){
            	//The size will be one for the last chassis device that is being deleted
            	return true;
            }
            
        } catch (Exception e) {            
        	log.warn("Got exception while searching the B6 Device ", e);
        } 
        return false;
    }   
    private boolean deleteChassis(DbTransaction txn,String region){
        
        try {
        	          
        	ICMSQuery query = new DBCMSQuery(null, "CalixB6Chassis", "");			
        	Collection<CalixB6Chassis> calixB6ChassisObjs = query.exec(txn.getDatabase(), txn);           
            if (calixB6ChassisObjs != null && calixB6ChassisObjs.size() > 0){
            	log.info("deleteChassis :: " + calixB6ChassisObjs.size());
                for (CalixB6Chassis calixB6Chassis : calixB6ChassisObjs) {
                	if (calixB6Chassis.getDbIdentity().equals(region)) {
                		calixB6Chassis.doRemove(txn);
                    }                                        
                }
            }
            return true;
        } catch (Exception e) {            
        	log.warn("Got exception while searching the B6 Chassis ", e);
        }
        return false;
    } 
    // END CODE
}