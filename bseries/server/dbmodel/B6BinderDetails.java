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

/**
 * Class B6BinderDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6BinderDetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_BinderId
     */
    public Integer m_BinderId;

    /**
     * Field m_Device
     */
    public String m_Device;

    /**
     * Field m_PortId
     */
    public Integer m_PortId;

    /**
     * Field m_PortType
     */
    public Integer m_PortType;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6BinderDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6BinderDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6BinderDetails()


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
        if( obj1 instanceof B6BinderDetails ) {
            super.copyFields(obj1);
            B6BinderDetails obj = (B6BinderDetails)obj1;
            setBinderId((Integer)Helper.copy(obj.getBinderId()));
            setDevice((String)Helper.copy(obj.getDevice()));
            setPortId((Integer)Helper.copy(obj.getPortId()));
            setPortType((Integer)Helper.copy(obj.getPortType()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getBinderId
     */
    public Integer getBinderId()
    {
        return this.m_BinderId;
    } //-- Integer getBinderId() 

    /**
     * Method getDevice
     */
    public String getDevice()
    {
        return this.m_Device;
    } //-- String getDevice() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getPortId
     */
    public Integer getPortId()
    {
        return this.m_PortId;
    } //-- Integer getPortId() 

    /**
     * Method getPortType
     */
    public Integer getPortType()
    {
        return this.m_PortType;
    } //-- Integer getPortType() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6BinderDetails;
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
            case 0x349C:
                if (m_BinderId == null) {
                    m_BinderId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349D:
                if (m_Device == null) {
                    m_Device = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349E:
                if (m_PortId == null) {
                    m_PortId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349F:
                if (m_PortType == null) {
                    m_PortType = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x349C, m_BinderId);
        TLVHelper.addEmbeddedTLV(tlv, 0x349D, m_Device);
        TLVHelper.addEmbeddedTLV(tlv, 0x349E, m_PortId);
        TLVHelper.addEmbeddedTLV(tlv, 0x349F, m_PortType);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setBinderId
     * 
     * @param BinderId
     */
    public void setBinderId(Integer BinderId)
    {
        this.m_BinderId = BinderId;
    } //-- void setBinderId(Integer) 

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
     * Method setPortId
     * 
     * @param PortId
     */
    public void setPortId(Integer PortId)
    {
        this.m_PortId = PortId;
    } //-- void setPortId(Integer) 

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
        if( obj1 instanceof B6BinderDetails ) {
            super.updateFields(obj1);
            B6BinderDetails obj = (B6BinderDetails)obj1;
           if (obj.getBinderId() != null )
               setBinderId((Integer)Helper.copy(obj.getBinderId()));
           if (obj.getDevice() != null )
               setDevice((String)Helper.copy(obj.getDevice()));
           if (obj.getPortId() != null )
               setPortId((Integer)Helper.copy(obj.getPortId()));
           if (obj.getPortType() != null )
               setPortType((Integer)Helper.copy(obj.getPortType()));
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