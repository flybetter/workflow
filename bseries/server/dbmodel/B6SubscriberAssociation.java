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
 * Class B6SubscriberAssociation.
 * 
 * @version $Revision$ $Date$
 */
public class B6SubscriberAssociation extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field m_interfaceIP
     */
    public String m_interfaceIP;

    /**
     * Field m_interfaceMAC
     */
    public String m_interfaceMAC;

    /**
     * Field m_serviceAssociationId
     */
    public Integer m_serviceAssociationId;

    /**
     * Field m_vlan
     */
    public Integer m_vlan;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6SubscriberAssociation";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6SubscriberAssociation() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6SubscriberAssociation()


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
        if( obj1 instanceof B6SubscriberAssociation ) {
            super.copyFields(obj1);
            B6SubscriberAssociation obj = (B6SubscriberAssociation)obj1;
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setinterfaceIP((String)Helper.copy(obj.getinterfaceIP()));
            setinterfaceMAC((String)Helper.copy(obj.getinterfaceMAC()));
            setserviceAssociationId((Integer)Helper.copy(obj.getserviceAssociationId()));
            setvlan((Integer)Helper.copy(obj.getvlan()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6SubscriberAssociation;
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
     * Method getinterfaceIP
     */
    public String getinterfaceIP()
    {
        return this.m_interfaceIP;
    } //-- String getinterfaceIP() 

    /**
     * Method getinterfaceMAC
     */
    public String getinterfaceMAC()
    {
        return this.m_interfaceMAC;
    } //-- String getinterfaceMAC() 

    /**
     * Method getserviceAssociationId
     */
    public Integer getserviceAssociationId()
    {
        return this.m_serviceAssociationId;
    } //-- Integer getserviceAssociationId() 

    /**
     * Method getvlan
     */
    public Integer getvlan()
    {
        return this.m_vlan;
    } //-- Integer getvlan() 

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
            case 0x34C7:
                if (m_serviceAssociationId == null) {
                    m_serviceAssociationId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34C8:
                if (m_vlan == null) {
                    m_vlan = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34C9:
                if (m_interfaceIP == null) {
                    m_interfaceIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34CA:
                if (m_interfaceMAC == null) {
                    m_interfaceMAC = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34C9, m_interfaceIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34CA, m_interfaceMAC);
        TLVHelper.addEmbeddedTLV(tlv, 0x34C7, m_serviceAssociationId);
        TLVHelper.addEmbeddedTLV(tlv, 0x34C8, m_vlan);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

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
     * Method setinterfaceIP
     * 
     * @param interfaceIP
     */
    public void setinterfaceIP(String interfaceIP)
    {
        this.m_interfaceIP = interfaceIP;
    } //-- void setinterfaceIP(String) 

    /**
     * Method setinterfaceMAC
     * 
     * @param interfaceMAC
     */
    public void setinterfaceMAC(String interfaceMAC)
    {
        this.m_interfaceMAC = interfaceMAC;
    } //-- void setinterfaceMAC(String) 

    /**
     * Method setserviceAssociationId
     * 
     * @param serviceAssociationId
     */
    public void setserviceAssociationId(Integer serviceAssociationId)
    {
        this.m_serviceAssociationId = serviceAssociationId;
    } //-- void setserviceAssociationId(Integer) 

    /**
     * Method setvlan
     * 
     * @param vlan
     */
    public void setvlan(Integer vlan)
    {
        this.m_vlan = vlan;
    } //-- void setvlan(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6SubscriberAssociation ) {
            super.updateFields(obj1);
            B6SubscriberAssociation obj = (B6SubscriberAssociation)obj1;
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getinterfaceIP() != null )
               setinterfaceIP((String)Helper.copy(obj.getinterfaceIP()));
           if (obj.getinterfaceMAC() != null )
               setinterfaceMAC((String)Helper.copy(obj.getinterfaceMAC()));
           if (obj.getserviceAssociationId() != null )
               setserviceAssociationId((Integer)Helper.copy(obj.getserviceAssociationId()));
           if (obj.getvlan() != null )
               setvlan((Integer)Helper.copy(obj.getvlan()));
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