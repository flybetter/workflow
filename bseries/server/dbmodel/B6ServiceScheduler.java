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
 * Class B6ServiceScheduler.
 * 
 * @version $Revision$ $Date$
 */
public class B6ServiceScheduler extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_OperationName
     */
    public String m_OperationName;

    /**
     * Field m_ScheduledTime
     */
    public Long m_ScheduledTime;

    /**
     * Field m_ServiceProperties
     */
    public String m_ServiceProperties;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6ServiceScheduler";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6ServiceScheduler() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6ServiceScheduler()


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
        if( obj1 instanceof B6ServiceScheduler ) {
            super.copyFields(obj1);
            B6ServiceScheduler obj = (B6ServiceScheduler)obj1;
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setOperationName((String)Helper.copy(obj.getOperationName()));
            setScheduledTime((Long)Helper.copy(obj.getScheduledTime()));
            setServiceProperties((String)Helper.copy(obj.getServiceProperties()));
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
     * Method getOperationName
     */
    public String getOperationName()
    {
        return this.m_OperationName;
    } //-- String getOperationName() 

    /**
     * Method getScheduledTime
     */
    public Long getScheduledTime()
    {
        return this.m_ScheduledTime;
    } //-- Long getScheduledTime() 

    /**
     * Method getServiceProperties
     */
    public String getServiceProperties()
    {
        return this.m_ServiceProperties;
    } //-- String getServiceProperties() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6ServiceScheduler;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

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
            case 0x34C4:
                if (m_ScheduledTime == null) {
                    m_ScheduledTime = TLVHelper.getLongValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34C5:
                if (m_OperationName == null) {
                    m_OperationName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34C6:
                if (m_ServiceProperties == null) {
                    m_ServiceProperties = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34C5, m_OperationName);
        TLVHelper.addEmbeddedTLV(tlv, 0x34C4, m_ScheduledTime);
        TLVHelper.addEmbeddedTLV(tlv, 0x34C6, m_ServiceProperties);
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
     * Method setOperationName
     * 
     * @param OperationName
     */
    public void setOperationName(String OperationName)
    {
        this.m_OperationName = OperationName;
    } //-- void setOperationName(String) 

    /**
     * Method setScheduledTime
     * 
     * @param ScheduledTime
     */
    public void setScheduledTime(Long ScheduledTime)
    {
        this.m_ScheduledTime = ScheduledTime;
    } //-- void setScheduledTime(Long) 

    /**
     * Method setServiceProperties
     * 
     * @param ServiceProperties
     */
    public void setServiceProperties(String ServiceProperties)
    {
        this.m_ServiceProperties = ServiceProperties;
    } //-- void setServiceProperties(String) 

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
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6ServiceScheduler ) {
            super.updateFields(obj1);
            B6ServiceScheduler obj = (B6ServiceScheduler)obj1;
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getOperationName() != null )
               setOperationName((String)Helper.copy(obj.getOperationName()));
           if (obj.getScheduledTime() != null )
               setScheduledTime((Long)Helper.copy(obj.getScheduledTime()));
           if (obj.getServiceProperties() != null )
               setServiceProperties((String)Helper.copy(obj.getServiceProperties()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
//BEGIN CODE
    
    public void setconvertServiceproperties(byte[] Serviceproperties ){
    	if(Serviceproperties != null)
    		this.m_ServiceProperties = new String(Serviceproperties);
    	else
    		this.m_ServiceProperties = null;
    }
    
    public byte[] getconvertServiceproperties(){
    	if(this.m_ServiceProperties != null)
    		return this.m_ServiceProperties.getBytes();
    	else
    		return null;
    }
    
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
			Collection resultList = db.executeQuery(this.getClass(), "taskname = '"
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