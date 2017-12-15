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
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.dbmodel.ICMSAid;

/**
 * Class B6ServiceAssociation.
 * 
 * @version $Revision$ $Date$
 */
public class B6ServiceAssociation extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AdminStatus
     */
    public Integer m_AdminStatus;

    /**
     * Field m_AssociatedPhoneNumbers
     */
    public String m_AssociatedPhoneNumbers;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_Device
     */
    public String m_Device;

    /**
     * Field m_ForcedPushState
     */
    public Integer m_ForcedPushState;

    /**
     * Field m_IsActivated
     */
    public Integer m_IsActivated;

    /**
     * Field m_IsBondId
     */
    public Integer m_IsBondId;

    /**
     * Field m_LastConfigState
     */
    public Integer m_LastConfigState;

    /**
     * Field m_PortInfo
     */
    public Integer m_PortInfo;

    /**
     * Field m_PortInfo2
     */
    public Integer m_PortInfo2;

    /**
     * Field m_PortOrBondId
     */
    public Integer m_PortOrBondId;

    /**
     * Field m_PortType
     */
    public Integer m_PortType;

    /**
     * Field m_PsdProfileName
     */
    public String m_PsdProfileName;

    /**
     * Field m_Quarantine
     */
    public Integer m_Quarantine;

    /**
     * Field m_ServiceIdInfo
     */
    public String m_ServiceIdInfo;

    /**
     * Field m_ServicePackageName
     */
    public String m_ServicePackageName;

    /**
     * Field m_SubscriberId
     */
    public String m_SubscriberId;

    /**
     * Field m_TlsServiceName
     */
    public String m_TlsServiceName;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6ServiceAssociation";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6ServiceAssociation() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6ServiceAssociation()


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
        if( obj1 instanceof B6ServiceAssociation ) {
            super.copyFields(obj1);
            B6ServiceAssociation obj = (B6ServiceAssociation)obj1;
            setAdminStatus((Integer)Helper.copy(obj.getAdminStatus()));
            setAssociatedPhoneNumbers((String)Helper.copy(obj.getAssociatedPhoneNumbers()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setDevice((String)Helper.copy(obj.getDevice()));
            setForcedPushState((Integer)Helper.copy(obj.getForcedPushState()));
            setIsActivated((Integer)Helper.copy(obj.getIsActivated()));
            setIsBondId((Integer)Helper.copy(obj.getIsBondId()));
            setLastConfigState((Integer)Helper.copy(obj.getLastConfigState()));
            setPortInfo((Integer)Helper.copy(obj.getPortInfo()));
            setPortInfo2((Integer)Helper.copy(obj.getPortInfo2()));
            setPortOrBondId((Integer)Helper.copy(obj.getPortOrBondId()));
            setPortType((Integer)Helper.copy(obj.getPortType()));
            setPsdProfileName((String)Helper.copy(obj.getPsdProfileName()));
            setQuarantine((Integer)Helper.copy(obj.getQuarantine()));
            setServiceIdInfo((String)Helper.copy(obj.getServiceIdInfo()));
            setServicePackageName((String)Helper.copy(obj.getServicePackageName()));
            setSubscriberId((String)Helper.copy(obj.getSubscriberId()));
            setTlsServiceName((String)Helper.copy(obj.getTlsServiceName()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAdminStatus
     */
    public Integer getAdminStatus()
    {
        return this.m_AdminStatus;
    } //-- Integer getAdminStatus() 

    /**
     * Method getAssociatedPhoneNumbers
     */
    public String getAssociatedPhoneNumbers()
    {
        return this.m_AssociatedPhoneNumbers;
    } //-- String getAssociatedPhoneNumbers() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getDevice
     */
    public String getDevice()
    {
        return this.m_Device;
    } //-- String getDevice() 

    /**
     * Method getForcedPushState
     */
    public Integer getForcedPushState()
    {
        return this.m_ForcedPushState;
    } //-- Integer getForcedPushState() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getIsActivated
     */
    public Integer getIsActivated()
    {
        return this.m_IsActivated;
    } //-- Integer getIsActivated() 

    /**
     * Method getIsBondId
     */
    public Integer getIsBondId()
    {
        return this.m_IsBondId;
    } //-- Integer getIsBondId() 

    /**
     * Method getLastConfigState
     */
    public Integer getLastConfigState()
    {
        return this.m_LastConfigState;
    } //-- Integer getLastConfigState() 

    /**
     * Method getPortInfo
     */
    public Integer getPortInfo()
    {
        return this.m_PortInfo;
    } //-- Integer getPortInfo() 

    /**
     * Method getPortInfo2
     */
    public Integer getPortInfo2()
    {
        return this.m_PortInfo2;
    } //-- Integer getPortInfo2() 

    /**
     * Method getPortOrBondId
     */
    public Integer getPortOrBondId()
    {
        return this.m_PortOrBondId;
    } //-- Integer getPortOrBondId() 

    /**
     * Method getPortType
     */
    public Integer getPortType()
    {
        return this.m_PortType;
    } //-- Integer getPortType() 

    /**
     * Method getPsdProfileName
     */
    public String getPsdProfileName()
    {
        return this.m_PsdProfileName;
    } //-- String getPsdProfileName() 

    /**
     * Method getQuarantine
     */
    public Integer getQuarantine()
    {
        return this.m_Quarantine;
    } //-- Integer getQuarantine() 

    /**
     * Method getServiceIdInfo
     */
    public String getServiceIdInfo()
    {
        return this.m_ServiceIdInfo;
    } //-- String getServiceIdInfo() 

    /**
     * Method getServicePackageName
     */
    public String getServicePackageName()
    {
        return this.m_ServicePackageName;
    } //-- String getServicePackageName() 

    /**
     * Method getSubscriberId
     */
    public String getSubscriberId()
    {
        return this.m_SubscriberId;
    } //-- String getSubscriberId() 

    /**
     * Method getTlsServiceName
     */
    public String getTlsServiceName()
    {
        return this.m_TlsServiceName;
    } //-- String getTlsServiceName() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6ServiceAssociation;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

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
            case 0x346F:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3470:
                if (m_ServicePackageName == null) {
                    m_ServicePackageName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3471:
                if (m_TlsServiceName == null) {
                    m_TlsServiceName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3472:
                if (m_Device == null) {
                    m_Device = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3473:
                if (m_PortOrBondId == null) {
                    m_PortOrBondId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3474:
                if (m_IsBondId == null) {
                    m_IsBondId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3475:
                if (m_AdminStatus == null) {
                    m_AdminStatus = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3476:
                if (m_PortType == null) {
                    m_PortType = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3477:
                if (m_Quarantine == null) {
                    m_Quarantine = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3478:
                if (m_SubscriberId == null) {
                    m_SubscriberId = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3479:
                if (m_AssociatedPhoneNumbers == null) {
                    m_AssociatedPhoneNumbers = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347A:
                if (m_PortInfo == null) {
                    m_PortInfo = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347B:
                if (m_PortInfo2 == null) {
                    m_PortInfo2 = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347C:
                if (m_IsActivated == null) {
                    m_IsActivated = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347D:
                if (m_ForcedPushState == null) {
                    m_ForcedPushState = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347E:
                if (m_LastConfigState == null) {
                    m_LastConfigState = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347F:
                if (m_ServiceIdInfo == null) {
                    m_ServiceIdInfo = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3480:
                if (m_PsdProfileName == null) {
                    m_PsdProfileName = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3475, m_AdminStatus);
        TLVHelper.addEmbeddedTLV(tlv, 0x3479, m_AssociatedPhoneNumbers);
        TLVHelper.addEmbeddedTLV(tlv, 0x346F, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3472, m_Device);
        TLVHelper.addEmbeddedTLV(tlv, 0x347D, m_ForcedPushState);
        TLVHelper.addEmbeddedTLV(tlv, 0x347C, m_IsActivated);
        TLVHelper.addEmbeddedTLV(tlv, 0x3474, m_IsBondId);
        TLVHelper.addEmbeddedTLV(tlv, 0x347E, m_LastConfigState);
        TLVHelper.addEmbeddedTLV(tlv, 0x347A, m_PortInfo);
        TLVHelper.addEmbeddedTLV(tlv, 0x347B, m_PortInfo2);
        TLVHelper.addEmbeddedTLV(tlv, 0x3473, m_PortOrBondId);
        TLVHelper.addEmbeddedTLV(tlv, 0x3476, m_PortType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3480, m_PsdProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3477, m_Quarantine);
        TLVHelper.addEmbeddedTLV(tlv, 0x347F, m_ServiceIdInfo);
        TLVHelper.addEmbeddedTLV(tlv, 0x3470, m_ServicePackageName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3478, m_SubscriberId);
        TLVHelper.addEmbeddedTLV(tlv, 0x3471, m_TlsServiceName);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAdminStatus
     * 
     * @param AdminStatus
     */
    public void setAdminStatus(Integer AdminStatus)
    {
        this.m_AdminStatus = AdminStatus;
    } //-- void setAdminStatus(Integer) 

    /**
     * Method setAssociatedPhoneNumbers
     * 
     * @param AssociatedPhoneNumbers
     */
    public void setAssociatedPhoneNumbers(String AssociatedPhoneNumbers)
    {
        this.m_AssociatedPhoneNumbers = AssociatedPhoneNumbers;
    } //-- void setAssociatedPhoneNumbers(String) 

    /**
     * Method setDescription
     * 
     * @param Description
     */
    public void setDescription(String Description)
    {
        this.m_Description = Description;
    } //-- void setDescription(String) 

    /**
     * Method setDevice
     * 
     * @param Device
     */
    public void setDevice(String Device)
    {
        this.m_Device = Device;
    } //-- void setDevice(String) 

    /**
     * Method setForcedPushState
     * 
     * @param ForcedPushState
     */
    public void setForcedPushState(Integer ForcedPushState)
    {
        this.m_ForcedPushState = ForcedPushState;
    } //-- void setForcedPushState(Integer) 

    /**
     * Method setIdentityValue
     * 
     * @param id
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid id)
    {
        this.m_id = (com.calix.system.server.dbmodel.ICMSAid)id;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setIsActivated
     * 
     * @param IsActivated
     */
    public void setIsActivated(Integer IsActivated)
    {
        this.m_IsActivated = IsActivated;
    } //-- void setIsActivated(Integer) 

    /**
     * Method setIsBondId
     * 
     * @param IsBondId
     */
    public void setIsBondId(Integer IsBondId)
    {
        this.m_IsBondId = IsBondId;
    } //-- void setIsBondId(Integer) 

    /**
     * Method setLastConfigState
     * 
     * @param LastConfigState
     */
    public void setLastConfigState(Integer LastConfigState)
    {
        this.m_LastConfigState = LastConfigState;
    } //-- void setLastConfigState(Integer) 

    /**
     * Method setPortInfo
     * 
     * @param PortInfo
     */
    public void setPortInfo(Integer PortInfo)
    {
        this.m_PortInfo = PortInfo;
    } //-- void setPortInfo(Integer) 

    /**
     * Method setPortInfo2
     * 
     * @param PortInfo2
     */
    public void setPortInfo2(Integer PortInfo2)
    {
        this.m_PortInfo2 = PortInfo2;
    } //-- void setPortInfo2(Integer) 

    /**
     * Method setPortOrBondId
     * 
     * @param PortOrBondId
     */
    public void setPortOrBondId(Integer PortOrBondId)
    {
        this.m_PortOrBondId = PortOrBondId;
    } //-- void setPortOrBondId(Integer) 

    /**
     * Method setPortType
     * 
     * @param PortType
     */
    public void setPortType(Integer PortType)
    {
        this.m_PortType = PortType;
    } //-- void setPortType(Integer) 

    /**
     * Method setPsdProfileName
     * 
     * @param PsdProfileName
     */
    public void setPsdProfileName(String PsdProfileName)
    {
        this.m_PsdProfileName = PsdProfileName;
    } //-- void setPsdProfileName(String) 

    /**
     * Method setQuarantine
     * 
     * @param Quarantine
     */
    public void setQuarantine(Integer Quarantine)
    {
        this.m_Quarantine = Quarantine;
    } //-- void setQuarantine(Integer) 

    /**
     * Method setServiceIdInfo
     * 
     * @param ServiceIdInfo
     */
    public void setServiceIdInfo(String ServiceIdInfo)
    {
        this.m_ServiceIdInfo = ServiceIdInfo;
    } //-- void setServiceIdInfo(String) 

    /**
     * Method setServicePackageName
     * 
     * @param ServicePackageName
     */
    public void setServicePackageName(String ServicePackageName)
    {
        this.m_ServicePackageName = ServicePackageName;
    } //-- void setServicePackageName(String) 

    /**
     * Method setSubscriberId
     * 
     * @param SubscriberId
     */
    public void setSubscriberId(String SubscriberId)
    {
        this.m_SubscriberId = SubscriberId;
    } //-- void setSubscriberId(String) 

    /**
     * Method setTlsServiceName
     * 
     * @param TlsServiceName
     */
    public void setTlsServiceName(String TlsServiceName)
    {
        this.m_TlsServiceName = TlsServiceName;
    } //-- void setTlsServiceName(String) 

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
     * Method setid
     * 
     * @param id
     */
    public void setid(com.calix.system.server.dbmodel.ICMSAid id)
    {
        this.m_id = id;
    } //-- void setid(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6ServiceAssociation ) {
            super.updateFields(obj1);
            B6ServiceAssociation obj = (B6ServiceAssociation)obj1;
           if (obj.getAdminStatus() != null )
               setAdminStatus((Integer)Helper.copy(obj.getAdminStatus()));
           if (obj.getAssociatedPhoneNumbers() != null )
               setAssociatedPhoneNumbers((String)Helper.copy(obj.getAssociatedPhoneNumbers()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getDevice() != null )
               setDevice((String)Helper.copy(obj.getDevice()));
           if (obj.getForcedPushState() != null )
               setForcedPushState((Integer)Helper.copy(obj.getForcedPushState()));
           if (obj.getIsActivated() != null )
               setIsActivated((Integer)Helper.copy(obj.getIsActivated()));
           if (obj.getIsBondId() != null )
               setIsBondId((Integer)Helper.copy(obj.getIsBondId()));
           if (obj.getLastConfigState() != null )
               setLastConfigState((Integer)Helper.copy(obj.getLastConfigState()));
           if (obj.getPortInfo() != null )
               setPortInfo((Integer)Helper.copy(obj.getPortInfo()));
           if (obj.getPortInfo2() != null )
               setPortInfo2((Integer)Helper.copy(obj.getPortInfo2()));
           if (obj.getPortOrBondId() != null )
               setPortOrBondId((Integer)Helper.copy(obj.getPortOrBondId()));
           if (obj.getPortType() != null )
               setPortType((Integer)Helper.copy(obj.getPortType()));
           if (obj.getPsdProfileName() != null )
               setPsdProfileName((String)Helper.copy(obj.getPsdProfileName()));
           if (obj.getQuarantine() != null )
               setQuarantine((Integer)Helper.copy(obj.getQuarantine()));
           if (obj.getServiceIdInfo() != null )
               setServiceIdInfo((String)Helper.copy(obj.getServiceIdInfo()));
           if (obj.getServicePackageName() != null )
               setServicePackageName((String)Helper.copy(obj.getServicePackageName()));
           if (obj.getSubscriberId() != null )
               setSubscriberId((String)Helper.copy(obj.getSubscriberId()));
           if (obj.getTlsServiceName() != null )
               setTlsServiceName((String)Helper.copy(obj.getTlsServiceName()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
// BEGIN CODE

	public void setconvertId(Integer id) {
		this.m_id = new BSeriesAid(String.valueOf(id));
	} // -- void setconvertName(String)

	public Integer getconvertId() {
		return Integer.valueOf(this.m_id.toString());
	}

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
    	C7Database db =C7Database.getInstance(); 
    	CMSObject obj = null;
    	try{
    	db.beginTransaction();
    	Collection resultList = db.executeQuery(this.getClass(), "id = "+this.getIdentityValue().toString(), -1, -1);
    	if(resultList != null&&!resultList.isEmpty()){
    			Iterator itr = resultList.iterator();
    			obj = (CMSObject)itr.next();
    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		db.close();
    	}
    	finally{
    		db.close();
    	}
    	if(obj == null)
    		throw new EMSDatabaseException(EMSDatabaseException._loadNonExistentFail_, "Object does not exist");
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

	// END CODE
}