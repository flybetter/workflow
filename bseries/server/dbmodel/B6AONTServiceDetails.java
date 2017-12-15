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
 * Class B6AONTServiceDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTServiceDetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AssociatedIdentifier
     */
    public Integer m_AssociatedIdentifier;

    /**
     * Field m_DhcpProfile
     */
    public String m_DhcpProfile;

    /**
     * Field m_Dscp
     */
    public Integer m_Dscp;

    /**
     * Field m_Hostname
     */
    public String m_Hostname;

    /**
     * Field m_OntID
     */
    public String m_OntID;

    /**
     * Field m_OntIP
     */
    public String m_OntIP;

    /**
     * Field m_ProfileName
     */
    public String m_ProfileName;

    /**
     * Field m_ServiceIdentifier
     */
    public Integer m_ServiceIdentifier;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTServiceDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTServiceDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTServiceDetails()


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
        if( obj1 instanceof B6AONTServiceDetails ) {
            super.copyFields(obj1);
            B6AONTServiceDetails obj = (B6AONTServiceDetails)obj1;
            setAssociatedIdentifier((Integer)Helper.copy(obj.getAssociatedIdentifier()));
            setDhcpProfile((String)Helper.copy(obj.getDhcpProfile()));
            setDscp((Integer)Helper.copy(obj.getDscp()));
            setHostname((String)Helper.copy(obj.getHostname()));
            setOntID((String)Helper.copy(obj.getOntID()));
            setOntIP((String)Helper.copy(obj.getOntIP()));
            setProfileName((String)Helper.copy(obj.getProfileName()));
            setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAssociatedIdentifier
     */
    public Integer getAssociatedIdentifier()
    {
        return this.m_AssociatedIdentifier;
    } //-- Integer getAssociatedIdentifier() 

    /**
     * Method getDhcpProfile
     */
    public String getDhcpProfile()
    {
        return this.m_DhcpProfile;
    } //-- String getDhcpProfile() 

    /**
     * Method getDscp
     */
    public Integer getDscp()
    {
        return this.m_Dscp;
    } //-- Integer getDscp() 

    /**
     * Method getHostname
     */
    public String getHostname()
    {
        return this.m_Hostname;
    } //-- String getHostname() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getOntID
     */
    public String getOntID()
    {
        return this.m_OntID;
    } //-- String getOntID() 

    /**
     * Method getOntIP
     */
    public String getOntIP()
    {
        return this.m_OntIP;
    } //-- String getOntIP() 

    /**
     * Method getProfileName
     */
    public String getProfileName()
    {
        return this.m_ProfileName;
    } //-- String getProfileName() 

    /**
     * Method getServiceIdentifier
     */
    public Integer getServiceIdentifier()
    {
        return this.m_ServiceIdentifier;
    } //-- Integer getServiceIdentifier() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6AONTServiceDetails;
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
            case 0x34AC:
                if (m_ServiceIdentifier == null) {
                    m_ServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AD:
                if (m_OntIP == null) {
                    m_OntIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AE:
                if (m_OntID == null) {
                    m_OntID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AF:
                if (m_DhcpProfile == null) {
                    m_DhcpProfile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B0:
                if (m_ProfileName == null) {
                    m_ProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B1:
                if (m_Hostname == null) {
                    m_Hostname = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B2:
                if (m_Dscp == null) {
                    m_Dscp = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B3:
                if (m_AssociatedIdentifier == null) {
                    m_AssociatedIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34B3, m_AssociatedIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AF, m_DhcpProfile);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B2, m_Dscp);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B1, m_Hostname);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AE, m_OntID);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AD, m_OntIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B0, m_ProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AC, m_ServiceIdentifier);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAssociatedIdentifier
     * 
     * @param AssociatedIdentifier
     */
    public void setAssociatedIdentifier(Integer AssociatedIdentifier)
    {
        this.m_AssociatedIdentifier = AssociatedIdentifier;
    } //-- void setAssociatedIdentifier(Integer) 

    /**
     * Method setDhcpProfile
     * 
     * @param DhcpProfile
     */
    public void setDhcpProfile(String DhcpProfile)
    {
        this.m_DhcpProfile = DhcpProfile;
    } //-- void setDhcpProfile(String) 

    /**
     * Method setDscp
     * 
     * @param Dscp
     */
    public void setDscp(Integer Dscp)
    {
        this.m_Dscp = Dscp;
    } //-- void setDscp(Integer) 

    /**
     * Method setHostname
     * 
     * @param Hostname
     */
    public void setHostname(String Hostname)
    {
        this.m_Hostname = Hostname;
    } //-- void setHostname(String) 

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
     * Method setOntID
     * 
     * @param OntID
     */
    public void setOntID(String OntID)
    {
        this.m_OntID = OntID;
    } //-- void setOntID(String) 

    /**
     * Method setOntIP
     * 
     * @param OntIP
     */
    public void setOntIP(String OntIP)
    {
        this.m_OntIP = OntIP;
    } //-- void setOntIP(String) 

    /**
     * Method setProfileName
     * 
     * @param ProfileName
     */
    public void setProfileName(String ProfileName)
    {
        this.m_ProfileName = ProfileName;
    } //-- void setProfileName(String) 

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
        if( obj1 instanceof B6AONTServiceDetails ) {
            super.updateFields(obj1);
            B6AONTServiceDetails obj = (B6AONTServiceDetails)obj1;
           if (obj.getAssociatedIdentifier() != null )
               setAssociatedIdentifier((Integer)Helper.copy(obj.getAssociatedIdentifier()));
           if (obj.getDhcpProfile() != null )
               setDhcpProfile((String)Helper.copy(obj.getDhcpProfile()));
           if (obj.getDscp() != null )
               setDscp((Integer)Helper.copy(obj.getDscp()));
           if (obj.getHostname() != null )
               setHostname((String)Helper.copy(obj.getHostname()));
           if (obj.getOntID() != null )
               setOntID((String)Helper.copy(obj.getOntID()));
           if (obj.getOntIP() != null )
               setOntIP((String)Helper.copy(obj.getOntIP()));
           if (obj.getProfileName() != null )
               setProfileName((String)Helper.copy(obj.getProfileName()));
           if (obj.getServiceIdentifier() != null )
               setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
//BEGIN CODE 
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

//END CODE
}