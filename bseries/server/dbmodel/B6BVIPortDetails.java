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
import com.calix.system.server.dbmodel.ICMSAid;

/**
 * Class B6BVIPortDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6BVIPortDetails extends com.calix.system.server.dbmodel.CMSObject {


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
     * Field m_BviOverrideAttributes
     */
    public String m_BviOverrideAttributes;

    /**
     * Field m_Defaultgateway
     */
    public String m_Defaultgateway;

    /**
     * Field m_IPAddressMode
     */
    public Integer m_IPAddressMode;

    /**
     * Field m_IpAddress
     */
    public String m_IpAddress;

    /**
     * Field m_Netmask
     */
    public String m_Netmask;

    /**
     * Field m_OntID
     */
    public String m_OntID;

    /**
     * Field m_Port
     */
    public Integer m_Port;

    /**
     * Field m_SaServiceIdentifier
     */
    public Integer m_SaServiceIdentifier;

    /**
     * Field m_SelectedServices
     */
    public String m_SelectedServices;

    /**
     * Field m_Type
     */
    public Integer m_Type;

    /**
     * Field m_VID
     */
    public Integer m_VID;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6BVIPortDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6BVIPortDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6BVIPortDetails()


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
        if( obj1 instanceof B6BVIPortDetails ) {
            super.copyFields(obj1);
            B6BVIPortDetails obj = (B6BVIPortDetails)obj1;
            setAdminState((Integer)Helper.copy(obj.getAdminState()));
            setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
            setBviOverrideAttributes((String)Helper.copy(obj.getBviOverrideAttributes()));
            setDefaultgateway((String)Helper.copy(obj.getDefaultgateway()));
            setIPAddressMode((Integer)Helper.copy(obj.getIPAddressMode()));
            setIpAddress((String)Helper.copy(obj.getIpAddress()));
            setNetmask((String)Helper.copy(obj.getNetmask()));
            setOntID((String)Helper.copy(obj.getOntID()));
            setPort((Integer)Helper.copy(obj.getPort()));
            setSaServiceIdentifier((Integer)Helper.copy(obj.getSaServiceIdentifier()));
            setSelectedServices((String)Helper.copy(obj.getSelectedServices()));
            setType((Integer)Helper.copy(obj.getType()));
            setVID((Integer)Helper.copy(obj.getVID()));
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
     * Method getBviOverrideAttributes
     */
    public String getBviOverrideAttributes()
    {
        return this.m_BviOverrideAttributes;
    } //-- String getBviOverrideAttributes() 

    /**
     * Method getDefaultgateway
     */
    public String getDefaultgateway()
    {
        return this.m_Defaultgateway;
    } //-- String getDefaultgateway() 

    /**
     * Method getIPAddressMode
     */
    public Integer getIPAddressMode()
    {
        return this.m_IPAddressMode;
    } //-- Integer getIPAddressMode() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getIpAddress
     */
    public String getIpAddress()
    {
        return this.m_IpAddress;
    } //-- String getIpAddress() 

    /**
     * Method getNetmask
     */
    public String getNetmask()
    {
        return this.m_Netmask;
    } //-- String getNetmask() 

    /**
     * Method getOntID
     */
    public String getOntID()
    {
        return this.m_OntID;
    } //-- String getOntID() 

    /**
     * Method getPort
     */
    public Integer getPort()
    {
        return this.m_Port;
    } //-- Integer getPort() 

    /**
     * Method getSaServiceIdentifier
     */
    public Integer getSaServiceIdentifier()
    {
        return this.m_SaServiceIdentifier;
    } //-- Integer getSaServiceIdentifier() 

    /**
     * Method getSelectedServices
     */
    public String getSelectedServices()
    {
        return this.m_SelectedServices;
    } //-- String getSelectedServices() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6BVIPortDetails;
    } //-- int getTlvType() 

    /**
     * Method getType
     */
    public Integer getType()
    {
        return this.m_Type;
    } //-- Integer getType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getVID
     */
    public Integer getVID()
    {
        return this.m_VID;
    } //-- Integer getVID() 

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
            case 0x34A2:
                if (m_Type == null) {
                    m_Type = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A3:
                if (m_AdminState == null) {
                    m_AdminState = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A4:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A5:
                if (m_VID == null) {
                    m_VID = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A6:
                if (m_IPAddressMode == null) {
                    m_IPAddressMode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A7:
                if (m_SelectedServices == null) {
                    m_SelectedServices = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A8:
                if (m_IpAddress == null) {
                    m_IpAddress = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A9:
                if (m_Netmask == null) {
                    m_Netmask = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AA:
                if (m_Defaultgateway == null) {
                    m_Defaultgateway = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AB:
                if (m_AontProfileName == null) {
                    m_AontProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AC:
                if (m_BviOverrideAttributes == null) {
                    m_BviOverrideAttributes = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AD:
                if (m_OntID == null) {
                    m_OntID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x40EC:
                if (m_SaServiceIdentifier == null) {
                    m_SaServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34A3, m_AdminState);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AB, m_AontProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AC, m_BviOverrideAttributes);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AA, m_Defaultgateway);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A6, m_IPAddressMode);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A8, m_IpAddress);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A9, m_Netmask);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AD, m_OntID);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A4, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x40EC, m_SaServiceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A7, m_SelectedServices);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A2, m_Type);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A5, m_VID);
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
     * Method setBviOverrideAttributes
     * 
     * @param BviOverrideAttributes
     */
    public void setBviOverrideAttributes(String BviOverrideAttributes)
    {
        this.m_BviOverrideAttributes = BviOverrideAttributes;
    } //-- void setBviOverrideAttributes(String) 

    /**
     * Method setDefaultgateway
     * 
     * @param Defaultgateway
     */
    public void setDefaultgateway(String Defaultgateway)
    {
        this.m_Defaultgateway = Defaultgateway;
    } //-- void setDefaultgateway(String) 

    /**
     * Method setIPAddressMode
     * 
     * @param IPAddressMode
     */
    public void setIPAddressMode(Integer IPAddressMode)
    {
        this.m_IPAddressMode = IPAddressMode;
    } //-- void setIPAddressMode(Integer) 

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
     * Method setIpAddress
     * 
     * @param IpAddress
     */
    public void setIpAddress(String IpAddress)
    {
        this.m_IpAddress = IpAddress;
    } //-- void setIpAddress(String) 

    /**
     * Method setNetmask
     * 
     * @param Netmask
     */
    public void setNetmask(String Netmask)
    {
        this.m_Netmask = Netmask;
    } //-- void setNetmask(String) 

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
     * Method setPort
     * 
     * @param Port
     */
    public void setPort(Integer Port)
    {
        this.m_Port = Port;
    } //-- void setPort(Integer) 

    /**
     * Method setSaServiceIdentifier
     * 
     * @param SaServiceIdentifier
     */
    public void setSaServiceIdentifier(Integer SaServiceIdentifier)
    {
        this.m_SaServiceIdentifier = SaServiceIdentifier;
    } //-- void setSaServiceIdentifier(Integer) 

    /**
     * Method setSelectedServices
     * 
     * @param SelectedServices
     */
    public void setSelectedServices(String SelectedServices)
    {
        this.m_SelectedServices = SelectedServices;
    } //-- void setSelectedServices(String) 

    /**
     * Method setType
     * 
     * @param Type
     */
    public void setType(Integer Type)
    {
        this.m_Type = Type;
    } //-- void setType(Integer) 

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
     * Method setVID
     * 
     * @param VID
     */
    public void setVID(Integer VID)
    {
        this.m_VID = VID;
    } //-- void setVID(Integer) 

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
        if( obj1 instanceof B6BVIPortDetails ) {
            super.updateFields(obj1);
            B6BVIPortDetails obj = (B6BVIPortDetails)obj1;
           if (obj.getAdminState() != null )
               setAdminState((Integer)Helper.copy(obj.getAdminState()));
           if (obj.getAontProfileName() != null )
               setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
           if (obj.getBviOverrideAttributes() != null )
               setBviOverrideAttributes((String)Helper.copy(obj.getBviOverrideAttributes()));
           if (obj.getDefaultgateway() != null )
               setDefaultgateway((String)Helper.copy(obj.getDefaultgateway()));
           if (obj.getIPAddressMode() != null )
               setIPAddressMode((Integer)Helper.copy(obj.getIPAddressMode()));
           if (obj.getIpAddress() != null )
               setIpAddress((String)Helper.copy(obj.getIpAddress()));
           if (obj.getNetmask() != null )
               setNetmask((String)Helper.copy(obj.getNetmask()));
           if (obj.getOntID() != null )
               setOntID((String)Helper.copy(obj.getOntID()));
           if (obj.getPort() != null )
               setPort((Integer)Helper.copy(obj.getPort()));
           if (obj.getSaServiceIdentifier() != null )
               setSaServiceIdentifier((Integer)Helper.copy(obj.getSaServiceIdentifier()));
           if (obj.getSelectedServices() != null )
               setSelectedServices((String)Helper.copy(obj.getSelectedServices()));
           if (obj.getType() != null )
               setType((Integer)Helper.copy(obj.getType()));
           if (obj.getVID() != null )
               setVID((Integer)Helper.copy(obj.getVID()));
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