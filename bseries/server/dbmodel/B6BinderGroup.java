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
 * Class B6BinderGroup.
 * 
 * @version $Revision$ $Date$
 */
public class B6BinderGroup extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_Am
     */
    public Integer m_Am;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_ExternaldisturInfo
     */
    public String m_ExternaldisturInfo;

    /**
     * Field m_Fm
     */
    public Integer m_Fm;

    /**
     * Field m_Name
     */
    public String m_Name;

    /**
     * Field m_Size
     */
    public Integer m_Size;

    /**
     * Field m_T1
     */
    public Integer m_T1;

    /**
     * Field m_dsBitrate
     */
    public String m_dsBitrate;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field m_maxDistance
     */
    public String m_maxDistance;

    /**
     * Field m_usBitrate
     */
    public String m_usBitrate;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6BinderGroup";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6BinderGroup() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6BinderGroup()


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
        if( obj1 instanceof B6BinderGroup ) {
            super.copyFields(obj1);
            B6BinderGroup obj = (B6BinderGroup)obj1;
            setAm((Integer)Helper.copy(obj.getAm()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setExternaldisturInfo((String)Helper.copy(obj.getExternaldisturInfo()));
            setFm((Integer)Helper.copy(obj.getFm()));
            setName((String)Helper.copy(obj.getName()));
            setSize((Integer)Helper.copy(obj.getSize()));
            setT1((Integer)Helper.copy(obj.getT1()));
            setdsBitrate((String)Helper.copy(obj.getdsBitrate()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setmaxDistance((String)Helper.copy(obj.getmaxDistance()));
            setusBitrate((String)Helper.copy(obj.getusBitrate()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAm
     */
    public Integer getAm()
    {
        return this.m_Am;
    } //-- Integer getAm() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getExternaldisturInfo
     */
    public String getExternaldisturInfo()
    {
        return this.m_ExternaldisturInfo;
    } //-- String getExternaldisturInfo() 

    /**
     * Method getFm
     */
    public Integer getFm()
    {
        return this.m_Fm;
    } //-- Integer getFm() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getName
     */
    public String getName()
    {
        return this.m_Name;
    } //-- String getName() 

    /**
     * Method getSize
     */
    public Integer getSize()
    {
        return this.m_Size;
    } //-- Integer getSize() 

    /**
     * Method getT1
     */
    public Integer getT1()
    {
        return this.m_T1;
    } //-- Integer getT1() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6BinderGroup;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getdsBitrate
     */
    public String getdsBitrate()
    {
        return this.m_dsBitrate;
    } //-- String getdsBitrate() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

    /**
     * Method getmaxDistance
     */
    public String getmaxDistance()
    {
        return this.m_maxDistance;
    } //-- String getmaxDistance() 

    /**
     * Method getusBitrate
     */
    public String getusBitrate()
    {
        return this.m_usBitrate;
    } //-- String getusBitrate() 

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
            case 0x3465:
                if (m_Name == null) {
                    m_Name = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3466:
                if (m_Size == null) {
                    m_Size = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3467:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3468:
                if (m_T1 == null) {
                    m_T1 = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3469:
                if (m_Am == null) {
                    m_Am = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346A:
                if (m_Fm == null) {
                    m_Fm = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346B:
                if (m_ExternaldisturInfo == null) {
                    m_ExternaldisturInfo = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346C:
                if (m_dsBitrate == null) {
                    m_dsBitrate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346D:
                if (m_usBitrate == null) {
                    m_usBitrate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x346E:
                if (m_maxDistance == null) {
                    m_maxDistance = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3469, m_Am);
        TLVHelper.addEmbeddedTLV(tlv, 0x3467, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x346B, m_ExternaldisturInfo);
        TLVHelper.addEmbeddedTLV(tlv, 0x346A, m_Fm);
        TLVHelper.addEmbeddedTLV(tlv, 0x3465, m_Name);
        TLVHelper.addEmbeddedTLV(tlv, 0x3466, m_Size);
        TLVHelper.addEmbeddedTLV(tlv, 0x3468, m_T1);
        TLVHelper.addEmbeddedTLV(tlv, 0x346C, m_dsBitrate);
        TLVHelper.addEmbeddedTLV(tlv, 0x346E, m_maxDistance);
        TLVHelper.addEmbeddedTLV(tlv, 0x346D, m_usBitrate);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAm
     * 
     * @param Am
     */
    public void setAm(Integer Am)
    {
        this.m_Am = Am;
    } //-- void setAm(Integer) 

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
     * Method setExternaldisturInfo
     * 
     * @param ExternaldisturInfo
     */
    public void setExternaldisturInfo(String ExternaldisturInfo)
    {
        this.m_ExternaldisturInfo = ExternaldisturInfo;
    } //-- void setExternaldisturInfo(String) 

    /**
     * Method setFm
     * 
     * @param Fm
     */
    public void setFm(Integer Fm)
    {
        this.m_Fm = Fm;
    } //-- void setFm(Integer) 

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
     * Method setName
     * 
     * @param Name
     */
    public void setName(String Name)
    {
        this.m_Name = Name;
    } //-- void setName(String) 

    /**
     * Method setSize
     * 
     * @param Size
     */
    public void setSize(Integer Size)
    {
        this.m_Size = Size;
    } //-- void setSize(Integer) 

    /**
     * Method setT1
     * 
     * @param T1
     */
    public void setT1(Integer T1)
    {
        this.m_T1 = T1;
    } //-- void setT1(Integer) 

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
     * Method setdsBitrate
     * 
     * @param dsBitrate
     */
    public void setdsBitrate(String dsBitrate)
    {
        this.m_dsBitrate = dsBitrate;
    } //-- void setdsBitrate(String) 

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
     * Method setmaxDistance
     * 
     * @param maxDistance
     */
    public void setmaxDistance(String maxDistance)
    {
        this.m_maxDistance = maxDistance;
    } //-- void setmaxDistance(String) 

    /**
     * Method setusBitrate
     * 
     * @param usBitrate
     */
    public void setusBitrate(String usBitrate)
    {
        this.m_usBitrate = usBitrate;
    } //-- void setusBitrate(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6BinderGroup ) {
            super.updateFields(obj1);
            B6BinderGroup obj = (B6BinderGroup)obj1;
           if (obj.getAm() != null )
               setAm((Integer)Helper.copy(obj.getAm()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getExternaldisturInfo() != null )
               setExternaldisturInfo((String)Helper.copy(obj.getExternaldisturInfo()));
           if (obj.getFm() != null )
               setFm((Integer)Helper.copy(obj.getFm()));
           if (obj.getName() != null )
               setName((String)Helper.copy(obj.getName()));
           if (obj.getSize() != null )
               setSize((Integer)Helper.copy(obj.getSize()));
           if (obj.getT1() != null )
               setT1((Integer)Helper.copy(obj.getT1()));
           if (obj.getdsBitrate() != null )
               setdsBitrate((String)Helper.copy(obj.getdsBitrate()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getmaxDistance() != null )
               setmaxDistance((String)Helper.copy(obj.getmaxDistance()));
           if (obj.getusBitrate() != null )
               setusBitrate((String)Helper.copy(obj.getusBitrate()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
//BEGIN CODE
    
     
    public void setDSBitrate(Double dsBitrate){
    	if(dsBitrate != null)
    		this.m_dsBitrate = String.valueOf(dsBitrate);
    	else
    		this.m_dsBitrate = null;
    }
    public void setUSBitrate(Double usBitrate){
    	if(usBitrate != null)
    		this.m_usBitrate = String.valueOf(usBitrate);
    	else
    		this.m_usBitrate = null;
    }
    public void setMaxDistance(Double maxDistance){
    	if(maxDistance != null)
    		this.m_maxDistance = String.valueOf(maxDistance);
    	else
    		this.m_maxDistance = null;
    }
    public Double getDSBitrate(){
    	if(this.m_dsBitrate != null){
    		return Double.valueOf(m_dsBitrate).doubleValue();
    	}
    	else{
    		return null;
    	}
    }
    public Double getUSBitrate(){
    	if(this.m_usBitrate != null){
    		return Double.valueOf(m_usBitrate).doubleValue();
    	}
    	else{
    		return null;
    	}
    }
    public Double getMaxDistance(){
    	if(this.m_maxDistance != null){
    		return Double.valueOf(m_maxDistance).doubleValue();
    	}
    	else{
    		return null;
    	}
    }

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