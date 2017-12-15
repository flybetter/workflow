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
import com.calix.system.common.log.Log;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6PWE3Service.
 * 
 * @version $Revision$ $Date$
 */
public class B6PWE3Service extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_C_VID
     */
    public Integer m_C_VID;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_Dscp
     */
    public Integer m_Dscp;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_S_VID
     */
    public Integer m_S_VID;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6PWE3Service";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6PWE3Service() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6PWE3Service()


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
        if( obj1 instanceof B6PWE3Service ) {
            super.copyFields(obj1);
            B6PWE3Service obj = (B6PWE3Service)obj1;
            setC_VID((Integer)Helper.copy(obj.getC_VID()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setDscp((Integer)Helper.copy(obj.getDscp()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setS_VID((Integer)Helper.copy(obj.getS_VID()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getC_VID
     */
    public Integer getC_VID()
    {
        return this.m_C_VID;
    } //-- Integer getC_VID() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getDscp
     */
    public Integer getDscp()
    {
        return this.m_Dscp;
    } //-- Integer getDscp() 

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
     * Method getS_VID
     */
    public Integer getS_VID()
    {
        return this.m_S_VID;
    } //-- Integer getS_VID() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6PWE3Service;
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
            case 0x343F:
                if (m_S_VID == null) {
                    m_S_VID = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3440:
                if (m_Dscp == null) {
                    m_Dscp = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3441:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3442:
                if (m_C_VID == null) {
                    m_C_VID = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3442, m_C_VID);
        TLVHelper.addEmbeddedTLV(tlv, 0x3441, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3440, m_Dscp);
        TLVHelper.addEmbeddedTLV(tlv, 0x343F, m_S_VID);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setC_VID
     * 
     * @param C_VID
     */
    public void setC_VID(Integer C_VID)
    {
        this.m_C_VID = C_VID;
    } //-- void setC_VID(Integer) 

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
     * Method setDscp
     * 
     * @param Dscp
     */
    public void setDscp(Integer Dscp)
    {
        this.m_Dscp = Dscp;
    } //-- void setDscp(Integer) 

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
     * Method setS_VID
     * 
     * @param S_VID
     */
    public void setS_VID(Integer S_VID)
    {
        this.m_S_VID = S_VID;
    } //-- void setS_VID(Integer) 

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
        if( obj1 instanceof B6PWE3Service ) {
            super.updateFields(obj1);
            B6PWE3Service obj = (B6PWE3Service)obj1;
           if (obj.getC_VID() != null )
               setC_VID((Integer)Helper.copy(obj.getC_VID()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getDscp() != null )
               setDscp((Integer)Helper.copy(obj.getDscp()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getS_VID() != null )
               setS_VID((Integer)Helper.copy(obj.getS_VID()));
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