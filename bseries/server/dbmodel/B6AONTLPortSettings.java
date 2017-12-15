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
 * Class B6AONTLPortSettings.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTLPortSettings extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AdminState
     */
    public Integer m_AdminState;

    /**
     * Field m_AontProfileName
     */
    public String m_AontProfileName;

    /**
     * Field m_DotP
     */
    public Integer m_DotP;

    /**
     * Field m_Duplex
     */
    public Integer m_Duplex;

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
     * Field m_Media
     */
    public Integer m_Media;

    /**
     * Field m_Mode
     */
    public Integer m_Mode;

    /**
     * Field m_OntID
     */
    public String m_OntID;

    /**
     * Field m_OntTag
     */
    public String m_OntTag;

    /**
     * Field m_OverWriteAttributes
     */
    public String m_OverWriteAttributes;

    /**
     * Field m_Port
     */
    public Integer m_Port;

    /**
     * Field m_ServiceIdentifier
     */
    public Integer m_ServiceIdentifier;

    /**
     * Field m_Services
     */
    public String m_Services;

    /**
     * Field m_Speed
     */
    public Integer m_Speed;

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
    public static String TYPE_NAME = "B6AONTLPortSettings";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTLPortSettings() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTLPortSettings()


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
        if( obj1 instanceof B6AONTLPortSettings ) {
            super.copyFields(obj1);
            B6AONTLPortSettings obj = (B6AONTLPortSettings)obj1;
            setAdminState((Integer)Helper.copy(obj.getAdminState()));
            setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
            setDotP((Integer)Helper.copy(obj.getDotP()));
            setDuplex((Integer)Helper.copy(obj.getDuplex()));
            setEgressRateLimit((Integer)Helper.copy(obj.getEgressRateLimit()));
            setInfo((String)Helper.copy(obj.getInfo()));
            setIngressRateLimit((Integer)Helper.copy(obj.getIngressRateLimit()));
            setMedia((Integer)Helper.copy(obj.getMedia()));
            setMode((Integer)Helper.copy(obj.getMode()));
            setOntID((String)Helper.copy(obj.getOntID()));
            setOntTag((String)Helper.copy(obj.getOntTag()));
            setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
            setPort((Integer)Helper.copy(obj.getPort()));
            setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
            setServices((String)Helper.copy(obj.getServices()));
            setSpeed((Integer)Helper.copy(obj.getSpeed()));
            setTaggedVLANID((String)Helper.copy(obj.getTaggedVLANID()));
            setUntaggedVLANID((Integer)Helper.copy(obj.getUntaggedVLANID()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAdminState
     */
    public Integer getAdminState()
    {
        return this.m_AdminState;
    } //-- Integer getAdminState() 

    /**
     * Method getAontProfileName
     */
    public String getAontProfileName()
    {
        return this.m_AontProfileName;
    } //-- String getAontProfileName() 

    /**
     * Method getDotP
     */
    public Integer getDotP()
    {
        return this.m_DotP;
    } //-- Integer getDotP() 

    /**
     * Method getDuplex
     */
    public Integer getDuplex()
    {
        return this.m_Duplex;
    } //-- Integer getDuplex() 

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
     * Method getMedia
     */
    public Integer getMedia()
    {
        return this.m_Media;
    } //-- Integer getMedia() 

    /**
     * Method getMode
     */
    public Integer getMode()
    {
        return this.m_Mode;
    } //-- Integer getMode() 

    /**
     * Method getOntID
     */
    public String getOntID()
    {
        return this.m_OntID;
    } //-- String getOntID() 

    /**
     * Method getOntTag
     */
    public String getOntTag()
    {
        return this.m_OntTag;
    } //-- String getOntTag() 

    /**
     * Method getOverWriteAttributes
     */
    public String getOverWriteAttributes()
    {
        return this.m_OverWriteAttributes;
    } //-- String getOverWriteAttributes() 

    /**
     * Method getPort
     */
    public Integer getPort()
    {
        return this.m_Port;
    } //-- Integer getPort() 

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
     * Method getSpeed
     */
    public Integer getSpeed()
    {
        return this.m_Speed;
    } //-- Integer getSpeed() 

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
        return BseriesTlvConstants.B6AONTLPortSettings;
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
            case 0x33D1:
                if (m_ServiceIdentifier == null) {
                    m_ServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D2:
                if (m_OntID == null) {
                    m_OntID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D3:
                if (m_AdminState == null) {
                    m_AdminState = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D4:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D5:
                if (m_UntaggedVLANID == null) {
                    m_UntaggedVLANID = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D6:
                if (m_IngressRateLimit == null) {
                    m_IngressRateLimit = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D7:
                if (m_EgressRateLimit == null) {
                    m_EgressRateLimit = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D8:
                if (m_Speed == null) {
                    m_Speed = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D9:
                if (m_Duplex == null) {
                    m_Duplex = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DA:
                if (m_Media == null) {
                    m_Media = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DB:
                if (m_Mode == null) {
                    m_Mode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DC:
                if (m_Services == null) {
                    m_Services = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DD:
                if (m_TaggedVLANID == null) {
                    m_TaggedVLANID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DE:
                if (m_DotP == null) {
                    m_DotP = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DF:
                if (m_Info == null) {
                    m_Info = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E0:
                if (m_OverWriteAttributes == null) {
                    m_OverWriteAttributes = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E1:
                if (m_AontProfileName == null) {
                    m_AontProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E2:
                if (m_OntTag == null) {
                    m_OntTag = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x33D3, m_AdminState);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E1, m_AontProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DE, m_DotP);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D9, m_Duplex);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D7, m_EgressRateLimit);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DF, m_Info);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D6, m_IngressRateLimit);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DA, m_Media);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DB, m_Mode);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D2, m_OntID);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E2, m_OntTag);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E0, m_OverWriteAttributes);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D4, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D1, m_ServiceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DC, m_Services);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D8, m_Speed);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DD, m_TaggedVLANID);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D5, m_UntaggedVLANID);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAdminState
     * 
     * @param AdminState
     */
    public void setAdminState(Integer AdminState)
    {
        this.m_AdminState = AdminState;
    } //-- void setAdminState(Integer) 

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
     * Method setDotP
     * 
     * @param DotP
     */
    public void setDotP(Integer DotP)
    {
        this.m_DotP = DotP;
    } //-- void setDotP(Integer) 

    /**
     * Method setDuplex
     * 
     * @param Duplex
     */
    public void setDuplex(Integer Duplex)
    {
        this.m_Duplex = Duplex;
    } //-- void setDuplex(Integer) 

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
     * Method setMedia
     * 
     * @param Media
     */
    public void setMedia(Integer Media)
    {
        this.m_Media = Media;
    } //-- void setMedia(Integer) 

    /**
     * Method setMode
     * 
     * @param Mode
     */
    public void setMode(Integer Mode)
    {
        this.m_Mode = Mode;
    } //-- void setMode(Integer) 

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
     * Method setOntTag
     * 
     * @param OntTag
     */
    public void setOntTag(String OntTag)
    {
        this.m_OntTag = OntTag;
    } //-- void setOntTag(String) 

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
     * Method setPort
     * 
     * @param Port
     */
    public void setPort(Integer Port)
    {
        this.m_Port = Port;
    } //-- void setPort(Integer) 

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
     * Method setSpeed
     * 
     * @param Speed
     */
    public void setSpeed(Integer Speed)
    {
        this.m_Speed = Speed;
    } //-- void setSpeed(Integer) 

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
        if( obj1 instanceof B6AONTLPortSettings ) {
            super.updateFields(obj1);
            B6AONTLPortSettings obj = (B6AONTLPortSettings)obj1;
           if (obj.getAdminState() != null )
               setAdminState((Integer)Helper.copy(obj.getAdminState()));
           if (obj.getAontProfileName() != null )
               setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
           if (obj.getDotP() != null )
               setDotP((Integer)Helper.copy(obj.getDotP()));
           if (obj.getDuplex() != null )
               setDuplex((Integer)Helper.copy(obj.getDuplex()));
           if (obj.getEgressRateLimit() != null )
               setEgressRateLimit((Integer)Helper.copy(obj.getEgressRateLimit()));
           if (obj.getInfo() != null )
               setInfo((String)Helper.copy(obj.getInfo()));
           if (obj.getIngressRateLimit() != null )
               setIngressRateLimit((Integer)Helper.copy(obj.getIngressRateLimit()));
           if (obj.getMedia() != null )
               setMedia((Integer)Helper.copy(obj.getMedia()));
           if (obj.getMode() != null )
               setMode((Integer)Helper.copy(obj.getMode()));
           if (obj.getOntID() != null )
               setOntID((String)Helper.copy(obj.getOntID()));
           if (obj.getOntTag() != null )
               setOntTag((String)Helper.copy(obj.getOntTag()));
           if (obj.getOverWriteAttributes() != null )
               setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
           if (obj.getPort() != null )
               setPort((Integer)Helper.copy(obj.getPort()));
           if (obj.getServiceIdentifier() != null )
               setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
           if (obj.getServices() != null )
               setServices((String)Helper.copy(obj.getServices()));
           if (obj.getSpeed() != null )
               setSpeed((Integer)Helper.copy(obj.getSpeed()));
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