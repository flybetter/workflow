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
 * Class B6Subscriber.
 * 
 * @version $Revision$ $Date$
 */
public class B6Subscriber extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_Address
     */
    public String m_Address;

    /**
     * Field m_Phonenumbers
     */
    public String m_Phonenumbers;

    /**
     * Field m_Properties
     */
    public String m_Properties;

    /**
     * Field m_firstName
     */
    public String m_firstName;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field m_secondName
     */
    public String m_secondName;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6Subscriber";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6Subscriber() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6Subscriber()


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
        if( obj1 instanceof B6Subscriber ) {
            super.copyFields(obj1);
            B6Subscriber obj = (B6Subscriber)obj1;
            setAddress((String)Helper.copy(obj.getAddress()));
            setPhonenumbers((String)Helper.copy(obj.getPhonenumbers()));
            setProperties((String)Helper.copy(obj.getProperties()));
            setfirstName((String)Helper.copy(obj.getfirstName()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setsecondName((String)Helper.copy(obj.getsecondName()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAddress
     */
    public String getAddress()
    {
        return this.m_Address;
    } //-- String getAddress() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getPhonenumbers
     */
    public String getPhonenumbers()
    {
        return this.m_Phonenumbers;
    } //-- String getPhonenumbers() 

    /**
     * Method getProperties
     */
    public String getProperties()
    {
        return this.m_Properties;
    } //-- String getProperties() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6Subscriber;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getfirstName
     */
    public String getfirstName()
    {
        return this.m_firstName;
    } //-- String getfirstName() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

    /**
     * Method getsecondName
     */
    public String getsecondName()
    {
        return this.m_secondName;
    } //-- String getsecondName() 

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
            case 0x3460:
                if (m_Properties == null) {
                    m_Properties = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3461:
                if (m_Phonenumbers == null) {
                    m_Phonenumbers = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3462:
                if (m_secondName == null) {
                    m_secondName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3463:
                if (m_firstName == null) {
                    m_firstName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3464:
                if (m_Address == null) {
                    m_Address = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3464, m_Address);
        TLVHelper.addEmbeddedTLV(tlv, 0x3461, m_Phonenumbers);
        TLVHelper.addEmbeddedTLV(tlv, 0x3460, m_Properties);
        TLVHelper.addEmbeddedTLV(tlv, 0x3463, m_firstName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3462, m_secondName);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAddress
     * 
     * @param Address
     */
    public void setAddress(String Address)
    {
        this.m_Address = Address;
    } //-- void setAddress(String) 

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
     * Method setPhonenumbers
     * 
     * @param Phonenumbers
     */
    public void setPhonenumbers(String Phonenumbers)
    {
        this.m_Phonenumbers = Phonenumbers;
    } //-- void setPhonenumbers(String) 

    /**
     * Method setProperties
     * 
     * @param Properties
     */
    public void setProperties(String Properties)
    {
        this.m_Properties = Properties;
    } //-- void setProperties(String) 

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
     * Method setfirstName
     * 
     * @param firstName
     */
    public void setfirstName(String firstName)
    {
        this.m_firstName = firstName;
    } //-- void setfirstName(String) 

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
     * Method setsecondName
     * 
     * @param secondName
     */
    public void setsecondName(String secondName)
    {
        this.m_secondName = secondName;
    } //-- void setsecondName(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6Subscriber ) {
            super.updateFields(obj1);
            B6Subscriber obj = (B6Subscriber)obj1;
           if (obj.getAddress() != null )
               setAddress((String)Helper.copy(obj.getAddress()));
           if (obj.getPhonenumbers() != null )
               setPhonenumbers((String)Helper.copy(obj.getPhonenumbers()));
           if (obj.getProperties() != null )
               setProperties((String)Helper.copy(obj.getProperties()));
           if (obj.getfirstName() != null )
               setfirstName((String)Helper.copy(obj.getfirstName()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getsecondName() != null )
               setsecondName((String)Helper.copy(obj.getsecondName()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
    //BEGIN CODE
    
    public void setconvertphonenumbers(byte[] Phonenumbers)
    {
    	if(Phonenumbers != null)
    		this.m_Phonenumbers = new String(Phonenumbers);
    	else 
    		this.m_Phonenumbers = null;
    } //-- void setPhonenumbers(String) 

  
    public void setconvertproperties(byte[] Properties)
    {	
    	if(Properties != null)
    		this.m_Properties = new String(Properties);
    	else 
    		this.m_Properties = null;
    } //-- void setProperties(String) 
    
    public void setconvertaddress(byte[] Address)
    {
    	if(Address != null)
    		this.m_Address = new String(Address);
    	else
    		this.m_Address = null;
    } //-- void setAddress(String) 

    public byte[] getconvertphonenumbers()
    {
    	if(m_Phonenumbers != null)
    		return this.m_Phonenumbers.getBytes();
    	else
    		return null;
    } //-- String getPhonenumbers() 

    /**
     * Method getProperties
     */
    public byte[] getconvertproperties()
    {
    	if(m_Properties != null)
    		return this.m_Properties.getBytes();
    	else
    		return null;
    } //-- String getProperties() 
    
    public byte[] getconvertaddress()
    {
    	if(m_Address != null)
    		return this.m_Address.getBytes();
    	else
    		return null;
    } //-- String getAddress() 

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
    
    
    //END CODE
}