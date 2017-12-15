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
 * Class B6OccamGponOnt.
 * 
 * @version $Revision$ $Date$
 */
public class B6OccamGponOnt extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_adminState
     */
    public String m_adminState;

    /**
     * Field m_displayName
     */
    public String m_displayName;

    /**
     * Field m_entityFirmwareRev
     */
    public String m_entityFirmwareRev;

    /**
     * Field m_entityHardwareRev
     */
    public String m_entityHardwareRev;

    /**
     * Field m_entitySerialNum
     */
    public String m_entitySerialNum;

    /**
     * Field m_entitySoftwareRev
     */
    public String m_entitySoftwareRev;

    /**
     * Field m_equipmentType
     */
    public String m_equipmentType;

    /**
     * Field m_isAutoBind
     */
    public String m_isAutoBind;

    /**
     * Field m_isCardPlugable
     */
    public String m_isCardPlugable;

    /**
     * Field m_isDiscovered
     */
    public String m_isDiscovered;

    /**
     * Field m_manageFlag
     */
    public Integer m_manageFlag;

    /**
     * Field m_model
     */
    public String m_model;

    /**
     * Field m_oltKey
     */
    public String m_oltKey;

    /**
     * Field m_ontDistance
     */
    public String m_ontDistance;

    /**
     * Field m_ontPonId
     */
    public Integer m_ontPonId;

    /**
     * Field m_ontProfile
     */
    public String m_ontProfile;

    /**
     * Field m_operationState
     */
    public String m_operationState;

    /**
     * Field m_ponPortNum
     */
    public Integer m_ponPortNum;

    /**
     * Field m_registrationId
     */
    public String m_registrationId;

    /**
     * Field m_slotInfo
     */
    public String m_slotInfo;

    /**
     * Field m_vendorID
     */
    public String m_vendorID;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6OccamGponOnt";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6OccamGponOnt() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6OccamGponOnt()


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
        if( obj1 instanceof B6OccamGponOnt ) {
            super.copyFields(obj1);
            B6OccamGponOnt obj = (B6OccamGponOnt)obj1;
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setadminState((String)Helper.copy(obj.getadminState()));
            setdisplayName((String)Helper.copy(obj.getdisplayName()));
            setentityFirmwareRev((String)Helper.copy(obj.getentityFirmwareRev()));
            setentityHardwareRev((String)Helper.copy(obj.getentityHardwareRev()));
            setentitySerialNum((String)Helper.copy(obj.getentitySerialNum()));
            setentitySoftwareRev((String)Helper.copy(obj.getentitySoftwareRev()));
            setequipmentType((String)Helper.copy(obj.getequipmentType()));
            setisAutoBind((String)Helper.copy(obj.getisAutoBind()));
            setisCardPlugable((String)Helper.copy(obj.getisCardPlugable()));
            setisDiscovered((String)Helper.copy(obj.getisDiscovered()));
            setmanageFlag((Integer)Helper.copy(obj.getmanageFlag()));
            setmodel((String)Helper.copy(obj.getmodel()));
            setoltKey((String)Helper.copy(obj.getoltKey()));
            setontDistance((String)Helper.copy(obj.getontDistance()));
            setontPonId((Integer)Helper.copy(obj.getontPonId()));
            setontProfile((String)Helper.copy(obj.getontProfile()));
            setoperationState((String)Helper.copy(obj.getoperationState()));
            setponPortNum((Integer)Helper.copy(obj.getponPortNum()));
            setregistrationId((String)Helper.copy(obj.getregistrationId()));
            setslotInfo((String)Helper.copy(obj.getslotInfo()));
            setvendorID((String)Helper.copy(obj.getvendorID()));
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
        return BseriesTlvConstants.B6OccamGponOnt;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getadminState
     */
    public String getadminState()
    {
        return this.m_adminState;
    } //-- String getadminState() 

    /**
     * Method getdisplayName
     */
    public String getdisplayName()
    {
        return this.m_displayName;
    } //-- String getdisplayName() 

    /**
     * Method getentityFirmwareRev
     */
    public String getentityFirmwareRev()
    {
        return this.m_entityFirmwareRev;
    } //-- String getentityFirmwareRev() 

    /**
     * Method getentityHardwareRev
     */
    public String getentityHardwareRev()
    {
        return this.m_entityHardwareRev;
    } //-- String getentityHardwareRev() 

    /**
     * Method getentitySerialNum
     */
    public String getentitySerialNum()
    {
        return this.m_entitySerialNum;
    } //-- String getentitySerialNum() 

    /**
     * Method getentitySoftwareRev
     */
    public String getentitySoftwareRev()
    {
        return this.m_entitySoftwareRev;
    } //-- String getentitySoftwareRev() 

    /**
     * Method getequipmentType
     */
    public String getequipmentType()
    {
        return this.m_equipmentType;
    } //-- String getequipmentType() 

    /**
     * Method getisAutoBind
     */
    public String getisAutoBind()
    {
        return this.m_isAutoBind;
    } //-- String getisAutoBind() 

    /**
     * Method getisCardPlugable
     */
    public String getisCardPlugable()
    {
        return this.m_isCardPlugable;
    } //-- String getisCardPlugable() 

    /**
     * Method getisDiscovered
     */
    public String getisDiscovered()
    {
        return this.m_isDiscovered;
    } //-- String getisDiscovered() 

    /**
     * Method getmanageFlag
     */
    public Integer getmanageFlag()
    {
        return this.m_manageFlag;
    } //-- Integer getmanageFlag() 

    /**
     * Method getmodel
     */
    public String getmodel()
    {
        return this.m_model;
    } //-- String getmodel() 

    /**
     * Method getoltKey
     */
    public String getoltKey()
    {
        return this.m_oltKey;
    } //-- String getoltKey() 

    /**
     * Method getontDistance
     */
    public String getontDistance()
    {
        return this.m_ontDistance;
    } //-- String getontDistance() 

    /**
     * Method getontPonId
     */
    public Integer getontPonId()
    {
        return this.m_ontPonId;
    } //-- Integer getontPonId() 

    /**
     * Method getontProfile
     */
    public String getontProfile()
    {
        return this.m_ontProfile;
    } //-- String getontProfile() 

    /**
     * Method getoperationState
     */
    public String getoperationState()
    {
        return this.m_operationState;
    } //-- String getoperationState() 

    /**
     * Method getponPortNum
     */
    public Integer getponPortNum()
    {
        return this.m_ponPortNum;
    } //-- Integer getponPortNum() 

    /**
     * Method getregistrationId
     */
    public String getregistrationId()
    {
        return this.m_registrationId;
    } //-- String getregistrationId() 

    /**
     * Method getslotInfo
     */
    public String getslotInfo()
    {
        return this.m_slotInfo;
    } //-- String getslotInfo() 

    /**
     * Method getvendorID
     */
    public String getvendorID()
    {
        return this.m_vendorID;
    } //-- String getvendorID() 

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
            case 0x33F7:
                if (m_ponPortNum == null) {
                    m_ponPortNum = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F8:
                if (m_ontPonId == null) {
                    m_ontPonId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F9:
                if (m_oltKey == null) {
                    m_oltKey = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33FA:
                if (m_displayName == null) {
                    m_displayName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33FB:
                if (m_slotInfo == null) {
                    m_slotInfo = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33FC:
                if (m_vendorID == null) {
                    m_vendorID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33FD:
                if (m_adminState == null) {
                    m_adminState = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33FE:
                if (m_operationState == null) {
                    m_operationState = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33FF:
                if (m_isAutoBind == null) {
                    m_isAutoBind = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3400:
                if (m_isCardPlugable == null) {
                    m_isCardPlugable = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3401:
                if (m_isDiscovered == null) {
                    m_isDiscovered = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3402:
                if (m_ontDistance == null) {
                    m_ontDistance = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3403:
                if (m_ontProfile == null) {
                    m_ontProfile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3404:
                if (m_registrationId == null) {
                    m_registrationId = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3405:
                if (m_model == null) {
                    m_model = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3406:
                if (m_manageFlag == null) {
                    m_manageFlag = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3407:
                if (m_equipmentType == null) {
                    m_equipmentType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3408:
                if (m_entitySerialNum == null) {
                    m_entitySerialNum = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3409:
                if (m_entityHardwareRev == null) {
                    m_entityHardwareRev = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x340A:
                if (m_entityFirmwareRev == null) {
                    m_entityFirmwareRev = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x340B:
                if (m_entitySoftwareRev == null) {
                    m_entitySoftwareRev = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x33FD, m_adminState);
        TLVHelper.addEmbeddedTLV(tlv, 0x33FA, m_displayName);
        TLVHelper.addEmbeddedTLV(tlv, 0x340A, m_entityFirmwareRev);
        TLVHelper.addEmbeddedTLV(tlv, 0x3409, m_entityHardwareRev);
        TLVHelper.addEmbeddedTLV(tlv, 0x3408, m_entitySerialNum);
        TLVHelper.addEmbeddedTLV(tlv, 0x340B, m_entitySoftwareRev);
        TLVHelper.addEmbeddedTLV(tlv, 0x3407, m_equipmentType);
        TLVHelper.addEmbeddedTLV(tlv, 0x33FF, m_isAutoBind);
        TLVHelper.addEmbeddedTLV(tlv, 0x3400, m_isCardPlugable);
        TLVHelper.addEmbeddedTLV(tlv, 0x3401, m_isDiscovered);
        TLVHelper.addEmbeddedTLV(tlv, 0x3406, m_manageFlag);
        TLVHelper.addEmbeddedTLV(tlv, 0x3405, m_model);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F9, m_oltKey);
        TLVHelper.addEmbeddedTLV(tlv, 0x3402, m_ontDistance);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F8, m_ontPonId);
        TLVHelper.addEmbeddedTLV(tlv, 0x3403, m_ontProfile);
        TLVHelper.addEmbeddedTLV(tlv, 0x33FE, m_operationState);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F7, m_ponPortNum);
        TLVHelper.addEmbeddedTLV(tlv, 0x3404, m_registrationId);
        TLVHelper.addEmbeddedTLV(tlv, 0x33FB, m_slotInfo);
        TLVHelper.addEmbeddedTLV(tlv, 0x33FC, m_vendorID);
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
     * Method setadminState
     * 
     * @param adminState
     */
    public void setadminState(String adminState)
    {
        this.m_adminState = adminState;
    } //-- void setadminState(String) 

    /**
     * Method setdisplayName
     * 
     * @param displayName
     */
    public void setdisplayName(String displayName)
    {
        this.m_displayName = displayName;
    } //-- void setdisplayName(String) 

    /**
     * Method setentityFirmwareRev
     * 
     * @param entityFirmwareRev
     */
    public void setentityFirmwareRev(String entityFirmwareRev)
    {
        this.m_entityFirmwareRev = entityFirmwareRev;
    } //-- void setentityFirmwareRev(String) 

    /**
     * Method setentityHardwareRev
     * 
     * @param entityHardwareRev
     */
    public void setentityHardwareRev(String entityHardwareRev)
    {
        this.m_entityHardwareRev = entityHardwareRev;
    } //-- void setentityHardwareRev(String) 

    /**
     * Method setentitySerialNum
     * 
     * @param entitySerialNum
     */
    public void setentitySerialNum(String entitySerialNum)
    {
        this.m_entitySerialNum = entitySerialNum;
    } //-- void setentitySerialNum(String) 

    /**
     * Method setentitySoftwareRev
     * 
     * @param entitySoftwareRev
     */
    public void setentitySoftwareRev(String entitySoftwareRev)
    {
        this.m_entitySoftwareRev = entitySoftwareRev;
    } //-- void setentitySoftwareRev(String) 

    /**
     * Method setequipmentType
     * 
     * @param equipmentType
     */
    public void setequipmentType(String equipmentType)
    {
        this.m_equipmentType = equipmentType;
    } //-- void setequipmentType(String) 

    /**
     * Method setisAutoBind
     * 
     * @param isAutoBind
     */
    public void setisAutoBind(String isAutoBind)
    {
        this.m_isAutoBind = isAutoBind;
    } //-- void setisAutoBind(String) 

    /**
     * Method setisCardPlugable
     * 
     * @param isCardPlugable
     */
    public void setisCardPlugable(String isCardPlugable)
    {
        this.m_isCardPlugable = isCardPlugable;
    } //-- void setisCardPlugable(String) 

    /**
     * Method setisDiscovered
     * 
     * @param isDiscovered
     */
    public void setisDiscovered(String isDiscovered)
    {
        this.m_isDiscovered = isDiscovered;
    } //-- void setisDiscovered(String) 

    /**
     * Method setmanageFlag
     * 
     * @param manageFlag
     */
    public void setmanageFlag(Integer manageFlag)
    {
        this.m_manageFlag = manageFlag;
    } //-- void setmanageFlag(Integer) 

    /**
     * Method setmodel
     * 
     * @param model
     */
    public void setmodel(String model)
    {
        this.m_model = model;
    } //-- void setmodel(String) 

    /**
     * Method setoltKey
     * 
     * @param oltKey
     */
    public void setoltKey(String oltKey)
    {
        this.m_oltKey = oltKey;
    } //-- void setoltKey(String) 

    /**
     * Method setontDistance
     * 
     * @param ontDistance
     */
    public void setontDistance(String ontDistance)
    {
        this.m_ontDistance = ontDistance;
    } //-- void setontDistance(String) 

    /**
     * Method setontPonId
     * 
     * @param ontPonId
     */
    public void setontPonId(Integer ontPonId)
    {
        this.m_ontPonId = ontPonId;
    } //-- void setontPonId(Integer) 

    /**
     * Method setontProfile
     * 
     * @param ontProfile
     */
    public void setontProfile(String ontProfile)
    {
        this.m_ontProfile = ontProfile;
    } //-- void setontProfile(String) 

    /**
     * Method setoperationState
     * 
     * @param operationState
     */
    public void setoperationState(String operationState)
    {
        this.m_operationState = operationState;
    } //-- void setoperationState(String) 

    /**
     * Method setponPortNum
     * 
     * @param ponPortNum
     */
    public void setponPortNum(Integer ponPortNum)
    {
        this.m_ponPortNum = ponPortNum;
    } //-- void setponPortNum(Integer) 

    /**
     * Method setregistrationId
     * 
     * @param registrationId
     */
    public void setregistrationId(String registrationId)
    {
        this.m_registrationId = registrationId;
    } //-- void setregistrationId(String) 

    /**
     * Method setslotInfo
     * 
     * @param slotInfo
     */
    public void setslotInfo(String slotInfo)
    {
        this.m_slotInfo = slotInfo;
    } //-- void setslotInfo(String) 

    /**
     * Method setvendorID
     * 
     * @param vendorID
     */
    public void setvendorID(String vendorID)
    {
        this.m_vendorID = vendorID;
    } //-- void setvendorID(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6OccamGponOnt ) {
            super.updateFields(obj1);
            B6OccamGponOnt obj = (B6OccamGponOnt)obj1;
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getadminState() != null )
               setadminState((String)Helper.copy(obj.getadminState()));
           if (obj.getdisplayName() != null )
               setdisplayName((String)Helper.copy(obj.getdisplayName()));
           if (obj.getentityFirmwareRev() != null )
               setentityFirmwareRev((String)Helper.copy(obj.getentityFirmwareRev()));
           if (obj.getentityHardwareRev() != null )
               setentityHardwareRev((String)Helper.copy(obj.getentityHardwareRev()));
           if (obj.getentitySerialNum() != null )
               setentitySerialNum((String)Helper.copy(obj.getentitySerialNum()));
           if (obj.getentitySoftwareRev() != null )
               setentitySoftwareRev((String)Helper.copy(obj.getentitySoftwareRev()));
           if (obj.getequipmentType() != null )
               setequipmentType((String)Helper.copy(obj.getequipmentType()));
           if (obj.getisAutoBind() != null )
               setisAutoBind((String)Helper.copy(obj.getisAutoBind()));
           if (obj.getisCardPlugable() != null )
               setisCardPlugable((String)Helper.copy(obj.getisCardPlugable()));
           if (obj.getisDiscovered() != null )
               setisDiscovered((String)Helper.copy(obj.getisDiscovered()));
           if (obj.getmanageFlag() != null )
               setmanageFlag((Integer)Helper.copy(obj.getmanageFlag()));
           if (obj.getmodel() != null )
               setmodel((String)Helper.copy(obj.getmodel()));
           if (obj.getoltKey() != null )
               setoltKey((String)Helper.copy(obj.getoltKey()));
           if (obj.getontDistance() != null )
               setontDistance((String)Helper.copy(obj.getontDistance()));
           if (obj.getontPonId() != null )
               setontPonId((Integer)Helper.copy(obj.getontPonId()));
           if (obj.getontProfile() != null )
               setontProfile((String)Helper.copy(obj.getontProfile()));
           if (obj.getoperationState() != null )
               setoperationState((String)Helper.copy(obj.getoperationState()));
           if (obj.getponPortNum() != null )
               setponPortNum((Integer)Helper.copy(obj.getponPortNum()));
           if (obj.getregistrationId() != null )
               setregistrationId((String)Helper.copy(obj.getregistrationId()));
           if (obj.getslotInfo() != null )
               setslotInfo((String)Helper.copy(obj.getslotInfo()));
           if (obj.getvendorID() != null )
               setvendorID((String)Helper.copy(obj.getvendorID()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

//BEGIN CODE
    
    
	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_Name.toString();
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
