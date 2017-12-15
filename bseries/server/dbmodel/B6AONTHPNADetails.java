/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
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
 * Class B6AONTHPNADetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTHPNADetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AdminStatus
     */
    public Integer m_AdminStatus;

    /**
     * Field m_AontProfileName
     */
    public String m_AontProfileName;

    /**
     * Field m_EgressRateLimit
     */
    public Integer m_EgressRateLimit;

    /**
     * Field m_Info
     */
    public String m_Info;

    /**
     * Field m_IngressRateLimit
     */
    public Integer m_IngressRateLimit;

    /**
     * Field m_OntID
     */
    public String m_OntID;

    /**
     * Field m_OverWriteAttributes
     */
    public String m_OverWriteAttributes;

    /**
     * Field m_ServiceIdentifier
     */
    public Integer m_ServiceIdentifier;

    /**
     * Field m_Services
     */
    public String m_Services;

    /**
     * Field m_TagMode
     */
    public Integer m_TagMode;

    /**
     * Field m_TaggedVLANID
     */
    public String m_TaggedVLANID;

    /**
     * Field m_UntaggedVLANID
     */
    public Integer m_UntaggedVLANID;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTHPNADetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTHPNADetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTHPNADetails()


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
        if( obj1 instanceof B6AONTHPNADetails ) {
            super.copyFields(obj1);
            B6AONTHPNADetails obj = (B6AONTHPNADetails)obj1;
            setAdminStatus((Integer)Helper.copy(obj.getAdminStatus()));
            setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
            setEgressRateLimit((Integer)Helper.copy(obj.getEgressRateLimit()));
            setInfo((String)Helper.copy(obj.getInfo()));
            setIngressRateLimit((Integer)Helper.copy(obj.getIngressRateLimit()));
            setOntID((String)Helper.copy(obj.getOntID()));
            setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
            setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
            setServices((String)Helper.copy(obj.getServices()));
            setTagMode((Integer)Helper.copy(obj.getTagMode()));
            setTaggedVLANID((String)Helper.copy(obj.getTaggedVLANID()));
            setUntaggedVLANID((Integer)Helper.copy(obj.getUntaggedVLANID()));
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
     * Method getAontProfileName
     */
    public String getAontProfileName()
    {
        return this.m_AontProfileName;
    } //-- String getAontProfileName() 

    /**
     * Method getEgressRateLimit
     */
    public Integer getEgressRateLimit()
    {
        return this.m_EgressRateLimit;
    } //-- Integer getEgressRateLimit() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getInfo
     */
    public String getInfo()
    {
        return this.m_Info;
    } //-- String getInfo() 

    /**
     * Method getIngressRateLimit
     */
    public Integer getIngressRateLimit()
    {
        return this.m_IngressRateLimit;
    } //-- Integer getIngressRateLimit() 

    /**
     * Method getOntID
     */
    public String getOntID()
    {
        return this.m_OntID;
    } //-- String getOntID() 

    /**
     * Method getOverWriteAttributes
     */
    public String getOverWriteAttributes()
    {
        return this.m_OverWriteAttributes;
    } //-- String getOverWriteAttributes() 

    /**
     * Method getServiceIdentifier
     */
    public Integer getServiceIdentifier()
    {
        return this.m_ServiceIdentifier;
    } //-- Integer getServiceIdentifier() 

    /**
     * Method getServices
     */
    public String getServices()
    {
        return this.m_Services;
    } //-- String getServices() 

    /**
     * Method getTagMode
     */
    public Integer getTagMode()
    {
        return this.m_TagMode;
    } //-- Integer getTagMode() 

    /**
     * Method getTaggedVLANID
     */
    public String getTaggedVLANID()
    {
        return this.m_TaggedVLANID;
    } //-- String getTaggedVLANID() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6AONTHPNADetails;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getUntaggedVLANID
     */
    public Integer getUntaggedVLANID()
    {
        return this.m_UntaggedVLANID;
    } //-- Integer getUntaggedVLANID() 

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
            case 0x34A0:
                if (m_ServiceIdentifier == null) {
                    m_ServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A1:
                if (m_OntID == null) {
                    m_OntID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A2:
                if (m_AdminStatus == null) {
                    m_AdminStatus = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A3:
                if (m_IngressRateLimit == null) {
                    m_IngressRateLimit = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A4:
                if (m_EgressRateLimit == null) {
                    m_EgressRateLimit = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A5:
                if (m_TagMode == null) {
                    m_TagMode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A6:
                if (m_Services == null) {
                    m_Services = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A7:
                if (m_TaggedVLANID == null) {
                    m_TaggedVLANID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A8:
                if (m_UntaggedVLANID == null) {
                    m_UntaggedVLANID = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A9:
                if (m_AontProfileName == null) {
                    m_AontProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AA:
                if (m_OverWriteAttributes == null) {
                    m_OverWriteAttributes = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AB:
                if (m_Info == null) {
                    m_Info = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34A2, m_AdminStatus);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A9, m_AontProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A4, m_EgressRateLimit);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AB, m_Info);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A3, m_IngressRateLimit);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A1, m_OntID);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AA, m_OverWriteAttributes);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A0, m_ServiceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A6, m_Services);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A5, m_TagMode);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A7, m_TaggedVLANID);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A8, m_UntaggedVLANID);
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
     * Method setAontProfileName
     * 
     * @param AontProfileName
     */
    public void setAontProfileName(String AontProfileName)
    {
        this.m_AontProfileName = AontProfileName;
    } //-- void setAontProfileName(String) 

    /**
     * Method setEgressRateLimit
     * 
     * @param EgressRateLimit
     */
    public void setEgressRateLimit(Integer EgressRateLimit)
    {
        this.m_EgressRateLimit = EgressRateLimit;
    } //-- void setEgressRateLimit(Integer) 

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
     * Method setInfo
     * 
     * @param Info
     */
    public void setInfo(String Info)
    {
        this.m_Info = Info;
    } //-- void setInfo(String) 

    /**
     * Method setIngressRateLimit
     * 
     * @param IngressRateLimit
     */
    public void setIngressRateLimit(Integer IngressRateLimit)
    {
        this.m_IngressRateLimit = IngressRateLimit;
    } //-- void setIngressRateLimit(Integer) 

    /**
     * Method setOntID
     * 
     * @param OntID
     */
    public void setOntID(String OntID)
    {
        this.m_OntID = OntID;
    } //-- void setOntID(String) 

    /**
     * Method setOverWriteAttributes
     * 
     * @param OverWriteAttributes
     */
    public void setOverWriteAttributes(String OverWriteAttributes)
    {
        this.m_OverWriteAttributes = OverWriteAttributes;
    } //-- void setOverWriteAttributes(String) 

    /**
     * Method setServiceIdentifier
     * 
     * @param ServiceIdentifier
     */
    public void setServiceIdentifier(Integer ServiceIdentifier)
    {
        this.m_ServiceIdentifier = ServiceIdentifier;
    } //-- void setServiceIdentifier(Integer) 

    /**
     * Method setServices
     * 
     * @param Services
     */
    public void setServices(String Services)
    {
        this.m_Services = Services;
    } //-- void setServices(String) 

    /**
     * Method setTagMode
     * 
     * @param TagMode
     */
    public void setTagMode(Integer TagMode)
    {
        this.m_TagMode = TagMode;
    } //-- void setTagMode(Integer) 

    /**
     * Method setTaggedVLANID
     * 
     * @param TaggedVLANID
     */
    public void setTaggedVLANID(String TaggedVLANID)
    {
        this.m_TaggedVLANID = TaggedVLANID;
    } //-- void setTaggedVLANID(String) 

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
     * Method setUntaggedVLANID
     * 
     * @param UntaggedVLANID
     */
    public void setUntaggedVLANID(Integer UntaggedVLANID)
    {
        this.m_UntaggedVLANID = UntaggedVLANID;
    } //-- void setUntaggedVLANID(Integer) 

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
        if( obj1 instanceof B6AONTHPNADetails ) {
            super.updateFields(obj1);
            B6AONTHPNADetails obj = (B6AONTHPNADetails)obj1;
           if (obj.getAdminStatus() != null )
               setAdminStatus((Integer)Helper.copy(obj.getAdminStatus()));
           if (obj.getAontProfileName() != null )
               setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
           if (obj.getEgressRateLimit() != null )
               setEgressRateLimit((Integer)Helper.copy(obj.getEgressRateLimit()));
           if (obj.getInfo() != null )
               setInfo((String)Helper.copy(obj.getInfo()));
           if (obj.getIngressRateLimit() != null )
               setIngressRateLimit((Integer)Helper.copy(obj.getIngressRateLimit()));
           if (obj.getOntID() != null )
               setOntID((String)Helper.copy(obj.getOntID()));
           if (obj.getOverWriteAttributes() != null )
               setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
           if (obj.getServiceIdentifier() != null )
               setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
           if (obj.getServices() != null )
               setServices((String)Helper.copy(obj.getServices()));
           if (obj.getTagMode() != null )
               setTagMode((Integer)Helper.copy(obj.getTagMode()));
           if (obj.getTaggedVLANID() != null )
               setTaggedVLANID((String)Helper.copy(obj.getTaggedVLANID()));
           if (obj.getUntaggedVLANID() != null )
               setUntaggedVLANID((Integer)Helper.copy(obj.getUntaggedVLANID()));
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
		db.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			db.rollbackTransaction();
		}finally{
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