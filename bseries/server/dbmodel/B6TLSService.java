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
 * Class B6TLSService.
 * 
 * @version $Revision$ $Date$
 */
public class B6TLSService extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CustomerVLAN
     */
    public Integer m_CustomerVLAN;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_DotPrority
     */
    public Integer m_DotPrority;

    /**
     * Field m_EpsVLAN
     */
    public Integer m_EpsVLAN;

    /**
     * Field m_MaxPrority
     */
    public Integer m_MaxPrority;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_Onttag
     */
    public String m_Onttag;

    /**
     * Field m_Pvc
     */
    public String m_Pvc;

    /**
     * Field m_Type
     */
    public Integer m_Type;

    /**
     * Field m_dsBitrate
     */
    public String m_dsBitrate;

    /**
     * Field m_usBitrate
     */
    public String m_usBitrate;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6TLSService";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6TLSService() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6TLSService()


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
        if( obj1 instanceof B6TLSService ) {
            super.copyFields(obj1);
            B6TLSService obj = (B6TLSService)obj1;
            setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setDotPrority((Integer)Helper.copy(obj.getDotPrority()));
            setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
            setMaxPrority((Integer)Helper.copy(obj.getMaxPrority()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setOnttag((String)Helper.copy(obj.getOnttag()));
            setPvc((String)Helper.copy(obj.getPvc()));
            setType((Integer)Helper.copy(obj.getType()));
            setdsBitrate((String)Helper.copy(obj.getdsBitrate()));
            setusBitrate((String)Helper.copy(obj.getusBitrate()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCustomerVLAN
     */
    public Integer getCustomerVLAN()
    {
        return this.m_CustomerVLAN;
    } //-- Integer getCustomerVLAN() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getDotPrority
     */
    public Integer getDotPrority()
    {
        return this.m_DotPrority;
    } //-- Integer getDotPrority() 

    /**
     * Method getEpsVLAN
     */
    public Integer getEpsVLAN()
    {
        return this.m_EpsVLAN;
    } //-- Integer getEpsVLAN() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getMaxPrority
     */
    public Integer getMaxPrority()
    {
        return this.m_MaxPrority;
    } //-- Integer getMaxPrority() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

    /**
     * Method getOnttag
     */
    public String getOnttag()
    {
        return this.m_Onttag;
    } //-- String getOnttag() 

    /**
     * Method getPvc
     */
    public String getPvc()
    {
        return this.m_Pvc;
    } //-- String getPvc() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6TLSService;
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
     * Method getdsBitrate
     */
    public String getdsBitrate()
    {
        return this.m_dsBitrate;
    } //-- String getdsBitrate() 

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
            case 0x3443:
                if (m_EpsVLAN == null) {
                    m_EpsVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3444:
                if (m_Onttag == null) {
                    m_Onttag = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3445:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3446:
                if (m_Pvc == null) {
                    m_Pvc = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3447:
                if (m_CustomerVLAN == null) {
                    m_CustomerVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3448:
                if (m_dsBitrate == null) {
                    m_dsBitrate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3449:
                if (m_usBitrate == null) {
                    m_usBitrate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344A:
                if (m_DotPrority == null) {
                    m_DotPrority = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344B:
                if (m_MaxPrority == null) {
                    m_MaxPrority = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344C:
                if (m_Type == null) {
                    m_Type = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3447, m_CustomerVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3445, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x344A, m_DotPrority);
        TLVHelper.addEmbeddedTLV(tlv, 0x3443, m_EpsVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x344B, m_MaxPrority);
        TLVHelper.addEmbeddedTLV(tlv, 0x3444, m_Onttag);
        TLVHelper.addEmbeddedTLV(tlv, 0x3446, m_Pvc);
        TLVHelper.addEmbeddedTLV(tlv, 0x344C, m_Type);
        TLVHelper.addEmbeddedTLV(tlv, 0x3448, m_dsBitrate);
        TLVHelper.addEmbeddedTLV(tlv, 0x3449, m_usBitrate);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCustomerVLAN
     * 
     * @param CustomerVLAN
     */
    public void setCustomerVLAN(Integer CustomerVLAN)
    {
        this.m_CustomerVLAN = CustomerVLAN;
    } //-- void setCustomerVLAN(Integer) 

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
     * Method setDotPrority
     * 
     * @param DotPrority
     */
    public void setDotPrority(Integer DotPrority)
    {
        this.m_DotPrority = DotPrority;
    } //-- void setDotPrority(Integer) 

    /**
     * Method setEpsVLAN
     * 
     * @param EpsVLAN
     */
    public void setEpsVLAN(Integer EpsVLAN)
    {
        this.m_EpsVLAN = EpsVLAN;
    } //-- void setEpsVLAN(Integer) 

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
     * Method setMaxPrority
     * 
     * @param MaxPrority
     */
    public void setMaxPrority(Integer MaxPrority)
    {
        this.m_MaxPrority = MaxPrority;
    } //-- void setMaxPrority(Integer) 

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
     * Method setOnttag
     * 
     * @param Onttag
     */
    public void setOnttag(String Onttag)
    {
        this.m_Onttag = Onttag;
    } //-- void setOnttag(String) 

    /**
     * Method setPvc
     * 
     * @param Pvc
     */
    public void setPvc(String Pvc)
    {
        this.m_Pvc = Pvc;
    } //-- void setPvc(String) 

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
     * Method setdsBitrate
     * 
     * @param dsBitrate
     */
    public void setdsBitrate(String dsBitrate)
    {
        this.m_dsBitrate = dsBitrate;
    } //-- void setdsBitrate(String) 

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
        if( obj1 instanceof B6TLSService ) {
            super.updateFields(obj1);
            B6TLSService obj = (B6TLSService)obj1;
           if (obj.getCustomerVLAN() != null )
               setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getDotPrority() != null )
               setDotPrority((Integer)Helper.copy(obj.getDotPrority()));
           if (obj.getEpsVLAN() != null )
               setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
           if (obj.getMaxPrority() != null )
               setMaxPrority((Integer)Helper.copy(obj.getMaxPrority()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getOnttag() != null )
               setOnttag((String)Helper.copy(obj.getOnttag()));
           if (obj.getPvc() != null )
               setPvc((String)Helper.copy(obj.getPvc()));
           if (obj.getType() != null )
               setType((Integer)Helper.copy(obj.getType()));
           if (obj.getdsBitrate() != null )
               setdsBitrate((String)Helper.copy(obj.getdsBitrate()));
           if (obj.getusBitrate() != null )
               setusBitrate((String)Helper.copy(obj.getusBitrate()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
//BEGIN CODE

    public void setDSBitrate(Double m_DSBiterate){
    	if(m_DSBiterate != null)
    		this.m_dsBitrate = String.valueOf(m_DSBiterate);
    	else
    		this.m_dsBitrate = null;
    }
    public Double getDSBitrate(){
    	if(m_dsBitrate != null)
    		return Double.valueOf(this.m_dsBitrate).doubleValue();
    	else
    		return null ;
    }
    
    public void setUSBitrate(Double m_USBitrate){
    	if(m_USBitrate != null)
    		this.m_usBitrate = String.valueOf(m_USBitrate);
    	else
    		this.m_usBitrate = null;
    }
    public Double getUSBitrate(){
    	if (m_usBitrate != null)
    		return Double.valueOf(this.m_usBitrate).doubleValue();
    	else
    		return null;
    }
	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_Name.toString();
	}
		
		public CMSObject doLoad(DbTransaction tx) throws EMSException {
	    	C7Database db =C7Database.getInstance(); 
	    	CMSObject obj = null;
	    	try{
	    	db.beginTransaction();
	    	Collection resultList = db.executeQuery(this.getClass(), "name = '"+this.getIdentityValue().toString()+"'", -1, -1);
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
		
		public Collection doQuery(DbTransaction tx, String filter)throws EMSException {
				if (Log.db().isDebugEnabled())
					Log.db().debug("Inside doQuery");
					ICMSQuery query = getDBQuery(this.getTypeName(), null);
					Collection coll = query.exec((Object) tx.getDatabase(), (Object) tx);
				if (Log.db().isDebugEnabled())
					Log.db().debug("Out of doQuery");
					return coll;
			}
		
		public boolean isIdentityValuePrimaryKey() {
			return true;
		}
	
//END CODE
}
