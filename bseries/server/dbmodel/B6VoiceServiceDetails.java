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
 * Class B6VoiceServiceDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6VoiceServiceDetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_authUserName
     */
    public String m_authUserName;

    /**
     * Field m_callWating_Voice
     */
    public Integer m_callWating_Voice;

    /**
     * Field m_callerId_Voice
     */
    public Integer m_callerId_Voice;

    /**
     * Field m_displayName
     */
    public String m_displayName;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field m_loopLength
     */
    public Integer m_loopLength;

    /**
     * Field m_messageWaitIndication_Voice
     */
    public Integer m_messageWaitIndication_Voice;

    /**
     * Field m_mode
     */
    public Integer m_mode;

    /**
     * Field m_overWriteAttributes
     */
    public String m_overWriteAttributes;

    /**
     * Field m_passWord_Voice
     */
    public String m_passWord_Voice;

    /**
     * Field m_potsProfile
     */
    public String m_potsProfile;

    /**
     * Field m_ringFrequency
     */
    public Integer m_ringFrequency;

    /**
     * Field m_ringType
     */
    public Integer m_ringType;

    /**
     * Field m_serviceIdentifier
     */
    public Integer m_serviceIdentifier;

    /**
     * Field m_sipProfile
     */
    public String m_sipProfile;

    /**
     * Field m_threeWayCalling_Voice
     */
    public Integer m_threeWayCalling_Voice;

    /**
     * Field m_userName_Voice
     */
    public String m_userName_Voice;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6VoiceServiceDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6VoiceServiceDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6VoiceServiceDetails()


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
        if( obj1 instanceof B6VoiceServiceDetails ) {
            super.copyFields(obj1);
            B6VoiceServiceDetails obj = (B6VoiceServiceDetails)obj1;
            setauthUserName((String)Helper.copy(obj.getauthUserName()));
            setcallWating_Voice((Integer)Helper.copy(obj.getcallWating_Voice()));
            setcallerId_Voice((Integer)Helper.copy(obj.getcallerId_Voice()));
            setdisplayName((String)Helper.copy(obj.getdisplayName()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setloopLength((Integer)Helper.copy(obj.getloopLength()));
            setmessageWaitIndication_Voice((Integer)Helper.copy(obj.getmessageWaitIndication_Voice()));
            setmode((Integer)Helper.copy(obj.getmode()));
            setoverWriteAttributes((String)Helper.copy(obj.getoverWriteAttributes()));
            setpassWord_Voice((String)Helper.copy(obj.getpassWord_Voice()));
            setpotsProfile((String)Helper.copy(obj.getpotsProfile()));
            setringFrequency((Integer)Helper.copy(obj.getringFrequency()));
            setringType((Integer)Helper.copy(obj.getringType()));
            setserviceIdentifier((Integer)Helper.copy(obj.getserviceIdentifier()));
            setsipProfile((String)Helper.copy(obj.getsipProfile()));
            setthreeWayCalling_Voice((Integer)Helper.copy(obj.getthreeWayCalling_Voice()));
            setuserName_Voice((String)Helper.copy(obj.getuserName_Voice()));
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
        return BseriesTlvConstants.B6VoiceServiceDetails;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getauthUserName
     */
    public String getauthUserName()
    {
        return this.m_authUserName;
    } //-- String getauthUserName() 

    /**
     * Method getcallWating_Voice
     */
    public Integer getcallWating_Voice()
    {
        return this.m_callWating_Voice;
    } //-- Integer getcallWating_Voice() 

    /**
     * Method getcallerId_Voice
     */
    public Integer getcallerId_Voice()
    {
        return this.m_callerId_Voice;
    } //-- Integer getcallerId_Voice() 

    /**
     * Method getdisplayName
     */
    public String getdisplayName()
    {
        return this.m_displayName;
    } //-- String getdisplayName() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

    /**
     * Method getloopLength
     */
    public Integer getloopLength()
    {
        return this.m_loopLength;
    } //-- Integer getloopLength() 

    /**
     * Method getmessageWaitIndication_Voice
     */
    public Integer getmessageWaitIndication_Voice()
    {
        return this.m_messageWaitIndication_Voice;
    } //-- Integer getmessageWaitIndication_Voice() 

    /**
     * Method getmode
     */
    public Integer getmode()
    {
        return this.m_mode;
    } //-- Integer getmode() 

    /**
     * Method getoverWriteAttributes
     */
    public String getoverWriteAttributes()
    {
        return this.m_overWriteAttributes;
    } //-- String getoverWriteAttributes() 

    /**
     * Method getpassWord_Voice
     */
    public String getpassWord_Voice()
    {
        return this.m_passWord_Voice;
    } //-- String getpassWord_Voice() 

    /**
     * Method getpotsProfile
     */
    public String getpotsProfile()
    {
        return this.m_potsProfile;
    } //-- String getpotsProfile() 

    /**
     * Method getringFrequency
     */
    public Integer getringFrequency()
    {
        return this.m_ringFrequency;
    } //-- Integer getringFrequency() 

    /**
     * Method getringType
     */
    public Integer getringType()
    {
        return this.m_ringType;
    } //-- Integer getringType() 

    /**
     * Method getserviceIdentifier
     */
    public Integer getserviceIdentifier()
    {
        return this.m_serviceIdentifier;
    } //-- Integer getserviceIdentifier() 

    /**
     * Method getsipProfile
     */
    public String getsipProfile()
    {
        return this.m_sipProfile;
    } //-- String getsipProfile() 

    /**
     * Method getthreeWayCalling_Voice
     */
    public Integer getthreeWayCalling_Voice()
    {
        return this.m_threeWayCalling_Voice;
    } //-- Integer getthreeWayCalling_Voice() 

    /**
     * Method getuserName_Voice
     */
    public String getuserName_Voice()
    {
        return this.m_userName_Voice;
    } //-- String getuserName_Voice() 

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
            case 0x348C:
                if (m_potsProfile == null) {
                    m_potsProfile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348D:
                if (m_serviceIdentifier == null) {
                    m_serviceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348E:
                if (m_ringFrequency == null) {
                    m_ringFrequency = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348F:
                if (m_ringType == null) {
                    m_ringType = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3490:
                if (m_mode == null) {
                    m_mode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3491:
                if (m_loopLength == null) {
                    m_loopLength = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3492:
                if (m_sipProfile == null) {
                    m_sipProfile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3493:
                if (m_authUserName == null) {
                    m_authUserName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3494:
                if (m_displayName == null) {
                    m_displayName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3495:
                if (m_userName_Voice == null) {
                    m_userName_Voice = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3496:
                if (m_passWord_Voice == null) {
                    m_passWord_Voice = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3497:
                if (m_callerId_Voice == null) {
                    m_callerId_Voice = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3498:
                if (m_callWating_Voice == null) {
                    m_callWating_Voice = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3499:
                if (m_threeWayCalling_Voice == null) {
                    m_threeWayCalling_Voice = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349A:
                if (m_messageWaitIndication_Voice == null) {
                    m_messageWaitIndication_Voice = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349B:
                if (m_overWriteAttributes == null) {
                    m_overWriteAttributes = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3493, m_authUserName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3498, m_callWating_Voice);
        TLVHelper.addEmbeddedTLV(tlv, 0x3497, m_callerId_Voice);
        TLVHelper.addEmbeddedTLV(tlv, 0x3494, m_displayName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3491, m_loopLength);
        TLVHelper.addEmbeddedTLV(tlv, 0x349A, m_messageWaitIndication_Voice);
        TLVHelper.addEmbeddedTLV(tlv, 0x3490, m_mode);
        TLVHelper.addEmbeddedTLV(tlv, 0x349B, m_overWriteAttributes);
        TLVHelper.addEmbeddedTLV(tlv, 0x3496, m_passWord_Voice);
        TLVHelper.addEmbeddedTLV(tlv, 0x348C, m_potsProfile);
        TLVHelper.addEmbeddedTLV(tlv, 0x348E, m_ringFrequency);
        TLVHelper.addEmbeddedTLV(tlv, 0x348F, m_ringType);
        TLVHelper.addEmbeddedTLV(tlv, 0x348D, m_serviceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x3492, m_sipProfile);
        TLVHelper.addEmbeddedTLV(tlv, 0x3499, m_threeWayCalling_Voice);
        TLVHelper.addEmbeddedTLV(tlv, 0x3495, m_userName_Voice);
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
     * Method setauthUserName
     * 
     * @param authUserName
     */
    public void setauthUserName(String authUserName)
    {
        this.m_authUserName = authUserName;
    } //-- void setauthUserName(String) 

    /**
     * Method setcallWating_Voice
     * 
     * @param callWating_Voice
     */
    public void setcallWating_Voice(Integer callWating_Voice)
    {
        this.m_callWating_Voice = callWating_Voice;
    } //-- void setcallWating_Voice(Integer) 

    /**
     * Method setcallerId_Voice
     * 
     * @param callerId_Voice
     */
    public void setcallerId_Voice(Integer callerId_Voice)
    {
        this.m_callerId_Voice = callerId_Voice;
    } //-- void setcallerId_Voice(Integer) 

    /**
     * Method setdisplayName
     * 
     * @param displayName
     */
    public void setdisplayName(String displayName)
    {
        this.m_displayName = displayName;
    } //-- void setdisplayName(String) 

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
     * Method setloopLength
     * 
     * @param loopLength
     */
    public void setloopLength(Integer loopLength)
    {
        this.m_loopLength = loopLength;
    } //-- void setloopLength(Integer) 

    /**
     * Method setmessageWaitIndication_Voice
     * 
     * @param messageWaitIndication_Voice
     */
    public void setmessageWaitIndication_Voice(Integer messageWaitIndication_Voice)
    {
        this.m_messageWaitIndication_Voice = messageWaitIndication_Voice;
    } //-- void setmessageWaitIndication_Voice(Integer) 

    /**
     * Method setmode
     * 
     * @param mode
     */
    public void setmode(Integer mode)
    {
        this.m_mode = mode;
    } //-- void setmode(Integer) 

    /**
     * Method setoverWriteAttributes
     * 
     * @param overWriteAttributes
     */
    public void setoverWriteAttributes(String overWriteAttributes)
    {
        this.m_overWriteAttributes = overWriteAttributes;
    } //-- void setoverWriteAttributes(String) 

    /**
     * Method setpassWord_Voice
     * 
     * @param passWord_Voice
     */
    public void setpassWord_Voice(String passWord_Voice)
    {
        this.m_passWord_Voice = passWord_Voice;
    } //-- void setpassWord_Voice(String) 

    /**
     * Method setpotsProfile
     * 
     * @param potsProfile
     */
    public void setpotsProfile(String potsProfile)
    {
        this.m_potsProfile = potsProfile;
    } //-- void setpotsProfile(String) 

    /**
     * Method setringFrequency
     * 
     * @param ringFrequency
     */
    public void setringFrequency(Integer ringFrequency)
    {
        this.m_ringFrequency = ringFrequency;
    } //-- void setringFrequency(Integer) 

    /**
     * Method setringType
     * 
     * @param ringType
     */
    public void setringType(Integer ringType)
    {
        this.m_ringType = ringType;
    } //-- void setringType(Integer) 

    /**
     * Method setserviceIdentifier
     * 
     * @param serviceIdentifier
     */
    public void setserviceIdentifier(Integer serviceIdentifier)
    {
        this.m_serviceIdentifier = serviceIdentifier;
    } //-- void setserviceIdentifier(Integer) 

    /**
     * Method setsipProfile
     * 
     * @param sipProfile
     */
    public void setsipProfile(String sipProfile)
    {
        this.m_sipProfile = sipProfile;
    } //-- void setsipProfile(String) 

    /**
     * Method setthreeWayCalling_Voice
     * 
     * @param threeWayCalling_Voice
     */
    public void setthreeWayCalling_Voice(Integer threeWayCalling_Voice)
    {
        this.m_threeWayCalling_Voice = threeWayCalling_Voice;
    } //-- void setthreeWayCalling_Voice(Integer) 

    /**
     * Method setuserName_Voice
     * 
     * @param userName_Voice
     */
    public void setuserName_Voice(String userName_Voice)
    {
        this.m_userName_Voice = userName_Voice;
    } //-- void setuserName_Voice(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6VoiceServiceDetails ) {
            super.updateFields(obj1);
            B6VoiceServiceDetails obj = (B6VoiceServiceDetails)obj1;
           if (obj.getauthUserName() != null )
               setauthUserName((String)Helper.copy(obj.getauthUserName()));
           if (obj.getcallWating_Voice() != null )
               setcallWating_Voice((Integer)Helper.copy(obj.getcallWating_Voice()));
           if (obj.getcallerId_Voice() != null )
               setcallerId_Voice((Integer)Helper.copy(obj.getcallerId_Voice()));
           if (obj.getdisplayName() != null )
               setdisplayName((String)Helper.copy(obj.getdisplayName()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getloopLength() != null )
               setloopLength((Integer)Helper.copy(obj.getloopLength()));
           if (obj.getmessageWaitIndication_Voice() != null )
               setmessageWaitIndication_Voice((Integer)Helper.copy(obj.getmessageWaitIndication_Voice()));
           if (obj.getmode() != null )
               setmode((Integer)Helper.copy(obj.getmode()));
           if (obj.getoverWriteAttributes() != null )
               setoverWriteAttributes((String)Helper.copy(obj.getoverWriteAttributes()));
           if (obj.getpassWord_Voice() != null )
               setpassWord_Voice((String)Helper.copy(obj.getpassWord_Voice()));
           if (obj.getpotsProfile() != null )
               setpotsProfile((String)Helper.copy(obj.getpotsProfile()));
           if (obj.getringFrequency() != null )
               setringFrequency((Integer)Helper.copy(obj.getringFrequency()));
           if (obj.getringType() != null )
               setringType((Integer)Helper.copy(obj.getringType()));
           if (obj.getserviceIdentifier() != null )
               setserviceIdentifier((Integer)Helper.copy(obj.getserviceIdentifier()));
           if (obj.getsipProfile() != null )
               setsipProfile((String)Helper.copy(obj.getsipProfile()));
           if (obj.getthreeWayCalling_Voice() != null )
               setthreeWayCalling_Voice((Integer)Helper.copy(obj.getthreeWayCalling_Voice()));
           if (obj.getuserName_Voice() != null )
               setuserName_Voice((String)Helper.copy(obj.getuserName_Voice()));
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