/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/
//BEGIN IMPORTS
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.calix.ems.database.C7Database;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.exception.EMSException;
import com.calix.ems.query.EMSGetAllQuery;
import com.calix.ems.query.ICMSQuery;
import com.calix.system.common.log.Log;
//END IMPORTS
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6BLCSIPProfile.
 * 
 * @version $Revision$ $Date$
 */
public class B6BLCSIPProfile extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_callWating
     */
    public Integer m_callWating;

    /**
     * Field m_callerId
     */
    public Integer m_callerId;

    /**
     * Field m_description
     */
    public String m_description;

    /**
     * Field m_digitMap
     */
    public String m_digitMap;

    /**
     * Field m_domain_SIPProfile
     */
    public String m_domain_SIPProfile;

    /**
     * Field m_isAutogenerated
     */
    public Integer m_isAutogenerated;

    /**
     * Field m_messageWaitIndication
     */
    public Integer m_messageWaitIndication;

    /**
     * Field m_primaryProxyIP
     */
    public String m_primaryProxyIP;

    /**
     * Field m_primaryProxyPort
     */
    public Integer m_primaryProxyPort;

    /**
     * Field m_primaryRegistrarIP
     */
    public String m_primaryRegistrarIP;

    /**
     * Field m_primaryRegistrarPort
     */
    public Integer m_primaryRegistrarPort;

    /**
     * Field m_registrationTimeOut
     */
    public Integer m_registrationTimeOut;

    /**
     * Field m_secondaryProxyIP
     */
    public String m_secondaryProxyIP;

    /**
     * Field m_secondaryProxyPort
     */
    public Integer m_secondaryProxyPort;

    /**
     * Field m_secondaryRegistrarIP
     */
    public String m_secondaryRegistrarIP;

    /**
     * Field m_secondaryRegistrarPort
     */
    public Integer m_secondaryRegistrarPort;

    /**
     * Field m_servicepackage_SIPProfile
     */
    public String m_servicepackage_SIPProfile;

    /**
     * Field m_syncstatus
     */
    public String m_syncstatus;

    /**
     * Field m_threeWayCalling
     */
    public Integer m_threeWayCalling;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6BLCSIPProfile";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6BLCSIPProfile() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6BLCSIPProfile()


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
        if( obj1 instanceof B6BLCSIPProfile ) {
            super.copyFields(obj1);
            B6BLCSIPProfile obj = (B6BLCSIPProfile)obj1;
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setcallWating((Integer)Helper.copy(obj.getcallWating()));
            setcallerId((Integer)Helper.copy(obj.getcallerId()));
            setdescription((String)Helper.copy(obj.getdescription()));
            setdigitMap((String)Helper.copy(obj.getdigitMap()));
            setdomain_SIPProfile((String)Helper.copy(obj.getdomain_SIPProfile()));
            setisAutogenerated((Integer)Helper.copy(obj.getisAutogenerated()));
            setmessageWaitIndication((Integer)Helper.copy(obj.getmessageWaitIndication()));
            setprimaryProxyIP((String)Helper.copy(obj.getprimaryProxyIP()));
            setprimaryProxyPort((Integer)Helper.copy(obj.getprimaryProxyPort()));
            setprimaryRegistrarIP((String)Helper.copy(obj.getprimaryRegistrarIP()));
            setprimaryRegistrarPort((Integer)Helper.copy(obj.getprimaryRegistrarPort()));
            setregistrationTimeOut((Integer)Helper.copy(obj.getregistrationTimeOut()));
            setsecondaryProxyIP((String)Helper.copy(obj.getsecondaryProxyIP()));
            setsecondaryProxyPort((Integer)Helper.copy(obj.getsecondaryProxyPort()));
            setsecondaryRegistrarIP((String)Helper.copy(obj.getsecondaryRegistrarIP()));
            setsecondaryRegistrarPort((Integer)Helper.copy(obj.getsecondaryRegistrarPort()));
            setservicepackage_SIPProfile((String)Helper.copy(obj.getservicepackage_SIPProfile()));
            setsyncstatus((String)Helper.copy(obj.getsyncstatus()));
            setthreeWayCalling((Integer)Helper.copy(obj.getthreeWayCalling()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6BLCSIPProfile;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getcallWating
     */
    public Integer getcallWating()
    {
        return this.m_callWating;
    } //-- Integer getcallWating() 

    /**
     * Method getcallerId
     */
    public Integer getcallerId()
    {
        return this.m_callerId;
    } //-- Integer getcallerId() 

    /**
     * Method getdescription
     */
    public String getdescription()
    {
        return this.m_description;
    } //-- String getdescription() 

    /**
     * Method getdigitMap
     */
    public String getdigitMap()
    {
        return this.m_digitMap;
    } //-- String getdigitMap() 

    /**
     * Method getdomain_SIPProfile
     */
    public String getdomain_SIPProfile()
    {
        return this.m_domain_SIPProfile;
    } //-- String getdomain_SIPProfile() 

    /**
     * Method getisAutogenerated
     */
    public Integer getisAutogenerated()
    {
        return this.m_isAutogenerated;
    } //-- Integer getisAutogenerated() 

    /**
     * Method getmessageWaitIndication
     */
    public Integer getmessageWaitIndication()
    {
        return this.m_messageWaitIndication;
    } //-- Integer getmessageWaitIndication() 

    /**
     * Method getprimaryProxyIP
     */
    public String getprimaryProxyIP()
    {
        return this.m_primaryProxyIP;
    } //-- String getprimaryProxyIP() 

    /**
     * Method getprimaryProxyPort
     */
    public Integer getprimaryProxyPort()
    {
        return this.m_primaryProxyPort;
    } //-- Integer getprimaryProxyPort() 

    /**
     * Method getprimaryRegistrarIP
     */
    public String getprimaryRegistrarIP()
    {
        return this.m_primaryRegistrarIP;
    } //-- String getprimaryRegistrarIP() 

    /**
     * Method getprimaryRegistrarPort
     */
    public Integer getprimaryRegistrarPort()
    {
        return this.m_primaryRegistrarPort;
    } //-- Integer getprimaryRegistrarPort() 

    /**
     * Method getregistrationTimeOut
     */
    public Integer getregistrationTimeOut()
    {
        return this.m_registrationTimeOut;
    } //-- Integer getregistrationTimeOut() 

    /**
     * Method getsecondaryProxyIP
     */
    public String getsecondaryProxyIP()
    {
        return this.m_secondaryProxyIP;
    } //-- String getsecondaryProxyIP() 

    /**
     * Method getsecondaryProxyPort
     */
    public Integer getsecondaryProxyPort()
    {
        return this.m_secondaryProxyPort;
    } //-- Integer getsecondaryProxyPort() 

    /**
     * Method getsecondaryRegistrarIP
     */
    public String getsecondaryRegistrarIP()
    {
        return this.m_secondaryRegistrarIP;
    } //-- String getsecondaryRegistrarIP() 

    /**
     * Method getsecondaryRegistrarPort
     */
    public Integer getsecondaryRegistrarPort()
    {
        return this.m_secondaryRegistrarPort;
    } //-- Integer getsecondaryRegistrarPort() 

    /**
     * Method getservicepackage_SIPProfile
     */
    public String getservicepackage_SIPProfile()
    {
        return this.m_servicepackage_SIPProfile;
    } //-- String getservicepackage_SIPProfile() 

    /**
     * Method getsyncstatus
     */
    public String getsyncstatus()
    {
        return this.m_syncstatus;
    } //-- String getsyncstatus() 

    /**
     * Method getthreeWayCalling
     */
    public Integer getthreeWayCalling()
    {
        return this.m_threeWayCalling;
    } //-- Integer getthreeWayCalling() 

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
            case 0x345C:
                if (m_description == null) {
                    m_description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x345D:
                if (m_servicepackage_SIPProfile == null) {
                    m_servicepackage_SIPProfile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x345E:
                if (m_isAutogenerated == null) {
                    m_isAutogenerated = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x345F:
                if (m_primaryProxyIP == null) {
                    m_primaryProxyIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3460:
                if (m_primaryProxyPort == null) {
                    m_primaryProxyPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3461:
                if (m_secondaryProxyIP == null) {
                    m_secondaryProxyIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3462:
                if (m_secondaryProxyPort == null) {
                    m_secondaryProxyPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3463:
                if (m_primaryRegistrarIP == null) {
                    m_primaryRegistrarIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3464:
                if (m_primaryRegistrarPort == null) {
                    m_primaryRegistrarPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3465:
                if (m_secondaryRegistrarIP == null) {
                    m_secondaryRegistrarIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3466:
                if (m_secondaryRegistrarPort == null) {
                    m_secondaryRegistrarPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3467:
                if (m_domain_SIPProfile == null) {
                    m_domain_SIPProfile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3468:
                if (m_digitMap == null) {
                    m_digitMap = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3469:
                if (m_callerId == null) {
                    m_callerId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346A:
                if (m_callWating == null) {
                    m_callWating = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346B:
                if (m_threeWayCalling == null) {
                    m_threeWayCalling = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346C:
                if (m_messageWaitIndication == null) {
                    m_messageWaitIndication = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346D:
                if (m_registrationTimeOut == null) {
                    m_registrationTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346E:
                if (m_syncstatus == null) {
                    m_syncstatus = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x346A, m_callWating);
        TLVHelper.addEmbeddedTLV(tlv, 0x3469, m_callerId);
        TLVHelper.addEmbeddedTLV(tlv, 0x345C, m_description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3468, m_digitMap);
        TLVHelper.addEmbeddedTLV(tlv, 0x3467, m_domain_SIPProfile);
        TLVHelper.addEmbeddedTLV(tlv, 0x345E, m_isAutogenerated);
        TLVHelper.addEmbeddedTLV(tlv, 0x346C, m_messageWaitIndication);
        TLVHelper.addEmbeddedTLV(tlv, 0x345F, m_primaryProxyIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3460, m_primaryProxyPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x3463, m_primaryRegistrarIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3464, m_primaryRegistrarPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x346D, m_registrationTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x3461, m_secondaryProxyIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3462, m_secondaryProxyPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x3465, m_secondaryRegistrarIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3466, m_secondaryRegistrarPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x345D, m_servicepackage_SIPProfile);
        TLVHelper.addEmbeddedTLV(tlv, 0x346E, m_syncstatus);
        TLVHelper.addEmbeddedTLV(tlv, 0x346B, m_threeWayCalling);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setIdentityValue
     * 
     * @param Name
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid Name)
    {
        this.m_Name = (com.calix.system.server.dbmodel.ICMSAid)Name;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setName
     * 
     * @param Name
     */
    public void setName(com.calix.system.server.dbmodel.ICMSAid Name)
    {
        this.m_Name = Name;
    } //-- void setName(com.calix.system.server.dbmodel.ICMSAid) 

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
     * Method setcallWating
     * 
     * @param callWating
     */
    public void setcallWating(Integer callWating)
    {
        this.m_callWating = callWating;
    } //-- void setcallWating(Integer) 

    /**
     * Method setcallerId
     * 
     * @param callerId
     */
    public void setcallerId(Integer callerId)
    {
        this.m_callerId = callerId;
    } //-- void setcallerId(Integer) 

    /**
     * Method setdescription
     * 
     * @param description
     */
    public void setdescription(String description)
    {
        this.m_description = description;
    } //-- void setdescription(String) 

    /**
     * Method setdigitMap
     * 
     * @param digitMap
     */
    public void setdigitMap(String digitMap)
    {
        this.m_digitMap = digitMap;
    } //-- void setdigitMap(String) 

    /**
     * Method setdomain_SIPProfile
     * 
     * @param domain_SIPProfile
     */
    public void setdomain_SIPProfile(String domain_SIPProfile)
    {
        this.m_domain_SIPProfile = domain_SIPProfile;
    } //-- void setdomain_SIPProfile(String) 

    /**
     * Method setisAutogenerated
     * 
     * @param isAutogenerated
     */
    public void setisAutogenerated(Integer isAutogenerated)
    {
        this.m_isAutogenerated = isAutogenerated;
    } //-- void setisAutogenerated(Integer) 

    /**
     * Method setmessageWaitIndication
     * 
     * @param messageWaitIndication
     */
    public void setmessageWaitIndication(Integer messageWaitIndication)
    {
        this.m_messageWaitIndication = messageWaitIndication;
    } //-- void setmessageWaitIndication(Integer) 

    /**
     * Method setprimaryProxyIP
     * 
     * @param primaryProxyIP
     */
    public void setprimaryProxyIP(String primaryProxyIP)
    {
        this.m_primaryProxyIP = primaryProxyIP;
    } //-- void setprimaryProxyIP(String) 

    /**
     * Method setprimaryProxyPort
     * 
     * @param primaryProxyPort
     */
    public void setprimaryProxyPort(Integer primaryProxyPort)
    {
        this.m_primaryProxyPort = primaryProxyPort;
    } //-- void setprimaryProxyPort(Integer) 

    /**
     * Method setprimaryRegistrarIP
     * 
     * @param primaryRegistrarIP
     */
    public void setprimaryRegistrarIP(String primaryRegistrarIP)
    {
        this.m_primaryRegistrarIP = primaryRegistrarIP;
    } //-- void setprimaryRegistrarIP(String) 

    /**
     * Method setprimaryRegistrarPort
     * 
     * @param primaryRegistrarPort
     */
    public void setprimaryRegistrarPort(Integer primaryRegistrarPort)
    {
        this.m_primaryRegistrarPort = primaryRegistrarPort;
    } //-- void setprimaryRegistrarPort(Integer) 

    /**
     * Method setregistrationTimeOut
     * 
     * @param registrationTimeOut
     */
    public void setregistrationTimeOut(Integer registrationTimeOut)
    {
        this.m_registrationTimeOut = registrationTimeOut;
    } //-- void setregistrationTimeOut(Integer) 

    /**
     * Method setsecondaryProxyIP
     * 
     * @param secondaryProxyIP
     */
    public void setsecondaryProxyIP(String secondaryProxyIP)
    {
        this.m_secondaryProxyIP = secondaryProxyIP;
    } //-- void setsecondaryProxyIP(String) 

    /**
     * Method setsecondaryProxyPort
     * 
     * @param secondaryProxyPort
     */
    public void setsecondaryProxyPort(Integer secondaryProxyPort)
    {
        this.m_secondaryProxyPort = secondaryProxyPort;
    } //-- void setsecondaryProxyPort(Integer) 

    /**
     * Method setsecondaryRegistrarIP
     * 
     * @param secondaryRegistrarIP
     */
    public void setsecondaryRegistrarIP(String secondaryRegistrarIP)
    {
        this.m_secondaryRegistrarIP = secondaryRegistrarIP;
    } //-- void setsecondaryRegistrarIP(String) 

    /**
     * Method setsecondaryRegistrarPort
     * 
     * @param secondaryRegistrarPort
     */
    public void setsecondaryRegistrarPort(Integer secondaryRegistrarPort)
    {
        this.m_secondaryRegistrarPort = secondaryRegistrarPort;
    } //-- void setsecondaryRegistrarPort(Integer) 

    /**
     * Method setservicepackage_SIPProfile
     * 
     * @param servicepackage_SIPProfile
     */
    public void setservicepackage_SIPProfile(String servicepackage_SIPProfile)
    {
        this.m_servicepackage_SIPProfile = servicepackage_SIPProfile;
    } //-- void setservicepackage_SIPProfile(String) 

    /**
     * Method setsyncstatus
     * 
     * @param syncstatus
     */
    public void setsyncstatus(String syncstatus)
    {
        this.m_syncstatus = syncstatus;
    } //-- void setsyncstatus(String) 

    /**
     * Method setthreeWayCalling
     * 
     * @param threeWayCalling
     */
    public void setthreeWayCalling(Integer threeWayCalling)
    {
        this.m_threeWayCalling = threeWayCalling;
    } //-- void setthreeWayCalling(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6BLCSIPProfile ) {
            super.updateFields(obj1);
            B6BLCSIPProfile obj = (B6BLCSIPProfile)obj1;
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getcallWating() != null )
               setcallWating((Integer)Helper.copy(obj.getcallWating()));
           if (obj.getcallerId() != null )
               setcallerId((Integer)Helper.copy(obj.getcallerId()));
           if (obj.getdescription() != null )
               setdescription((String)Helper.copy(obj.getdescription()));
           if (obj.getdigitMap() != null )
               setdigitMap((String)Helper.copy(obj.getdigitMap()));
           if (obj.getdomain_SIPProfile() != null )
               setdomain_SIPProfile((String)Helper.copy(obj.getdomain_SIPProfile()));
           if (obj.getisAutogenerated() != null )
               setisAutogenerated((Integer)Helper.copy(obj.getisAutogenerated()));
           if (obj.getmessageWaitIndication() != null )
               setmessageWaitIndication((Integer)Helper.copy(obj.getmessageWaitIndication()));
           if (obj.getprimaryProxyIP() != null )
               setprimaryProxyIP((String)Helper.copy(obj.getprimaryProxyIP()));
           if (obj.getprimaryProxyPort() != null )
               setprimaryProxyPort((Integer)Helper.copy(obj.getprimaryProxyPort()));
           if (obj.getprimaryRegistrarIP() != null )
               setprimaryRegistrarIP((String)Helper.copy(obj.getprimaryRegistrarIP()));
           if (obj.getprimaryRegistrarPort() != null )
               setprimaryRegistrarPort((Integer)Helper.copy(obj.getprimaryRegistrarPort()));
           if (obj.getregistrationTimeOut() != null )
               setregistrationTimeOut((Integer)Helper.copy(obj.getregistrationTimeOut()));
           if (obj.getsecondaryProxyIP() != null )
               setsecondaryProxyIP((String)Helper.copy(obj.getsecondaryProxyIP()));
           if (obj.getsecondaryProxyPort() != null )
               setsecondaryProxyPort((Integer)Helper.copy(obj.getsecondaryProxyPort()));
           if (obj.getsecondaryRegistrarIP() != null )
               setsecondaryRegistrarIP((String)Helper.copy(obj.getsecondaryRegistrarIP()));
           if (obj.getsecondaryRegistrarPort() != null )
               setsecondaryRegistrarPort((Integer)Helper.copy(obj.getsecondaryRegistrarPort()));
           if (obj.getservicepackage_SIPProfile() != null )
               setservicepackage_SIPProfile((String)Helper.copy(obj.getservicepackage_SIPProfile()));
           if (obj.getsyncstatus() != null )
               setsyncstatus((String)Helper.copy(obj.getsyncstatus()));
           if (obj.getthreeWayCalling() != null )
               setthreeWayCalling((Integer)Helper.copy(obj.getthreeWayCalling()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

//BEGIN CODE
    
    
	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setcName(String)

	public String getconvertName() {
		return this.m_Name.toString();
	} // -- void getcName()
	
	public Collection doQuery(DbTransaction tx, String filter)
			throws EMSException {
		if (Log.db().isDebugEnabled())
			Log.db().debug("Inside doQuery");
		ICMSQuery query = getDBQuery(this.getTypeName(), null);
		Collection coll = query.exec((Object) tx.getDatabase(), (Object) tx);
		if (Log.db().isDebugEnabled())
			Log.db().debug("Out of doQuery");
		return coll;
	}

	public CMSObject doLoad(DbTransaction tx) throws EMSException {
		C7Database db = C7Database.getInstance();
		CMSObject obj = null;
		try {
			db.beginTransaction();
			Collection resultList = db.executeQuery(this.getClass(), "name = '"
					+ this.getIdentityValue().toString() + "'", -1, -1);
			if (resultList != null && !resultList.isEmpty()) {
				Iterator itr = resultList.iterator();
				obj = (CMSObject) itr.next();
			}
			db.commitTransaction();
			} catch (Exception e) {
				e.printStackTrace();
				db.rollbackTransaction();
			}finally{
				db.close();
			}
		if (obj == null)
			throw new EMSDatabaseException(
					EMSDatabaseException._loadNonExistentFail_,
					"Object does not exist");
		else
			return obj;
	}

	public ICMSQuery getDBQuery(String type, String filter) {
		if (filter == null || filter.equals(""))
			return new EMSGetAllQuery(type);
		else {
			Log.meta().error("BaseEMSObject: filterQuery is not supported");
			return null;
		}
	}

	public boolean isIdentityValuePrimaryKey() {
		return true;
	}
//END CODE
}