/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6EndSubscriber.
 * 
 * @version $Revision$ $Date$
 */
public class B6EndSubscriber extends BaseEMSObject {
 

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_DevicesRegion
     */
    public String m_DevicesRegion;

    /**
     * B6 Subscriber Id 
     */
    public com.calix.system.server.dbmodel.ICMSAid m_ID;

    /**
     * Field m_Linked_Network
     */
    public String m_Linked_Network;

    /**
     * RecordType tlvCode
     */
    public Integer m_RecordTypeTlvCode;

    /**
     * Field m_Straid
     */
    public String m_Straid;

    /**
     * Subscriber ID
     */
    public String m_SubscriberID;

    /**
     * User Desc
     */
    public String m_UserDescr;

    /**
     * Field m_linked_port
     */
    public String m_linked_port;

    /**
     * Field m_sid
     */
    public String m_sid;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6EndSubscriber";

    public static final int flowID = 1;

      //----------------/
     //- Constructors -/
    //----------------/

    public B6EndSubscriber() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6EndSubscriber()


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
        if( obj1 instanceof B6EndSubscriber ) {
            super.copyFields(obj1);
            B6EndSubscriber obj = (B6EndSubscriber)obj1;
            setDevicesRegion((String)Helper.copy(obj.getDevicesRegion()));
            setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
            setLinked_Network((String)Helper.copy(obj.getLinked_Network()));
            setRecordTypeTlvCode((Integer)Helper.copy(obj.getRecordTypeTlvCode()));
            setStraid((String)Helper.copy(obj.getStraid()));
            setSubscriberID((String)Helper.copy(obj.getSubscriberID()));
            setUserDescr((String)Helper.copy(obj.getUserDescr()));
            setlinked_port((String)Helper.copy(obj.getlinked_port()));
            setsid((String)Helper.copy(obj.getsid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getDevicesRegion
     */
    public String getDevicesRegion()
    {
        return this.m_DevicesRegion;
    } //-- String getDevicesRegion() 

    /**
     * Method getID
     */
    public com.calix.system.server.dbmodel.ICMSAid getID()
    {
        return this.m_ID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getID() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_ID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getLinked_Network
     */
    public String getLinked_Network()
    {
        return this.m_Linked_Network;
    } //-- String getLinked_Network() 

    /**
     * Method getRecordTypeTlvCode
     */
    public Integer getRecordTypeTlvCode()
    {
        return this.m_RecordTypeTlvCode;
    } //-- Integer getRecordTypeTlvCode() 

    /**
     * Method getStraid
     */
    public String getStraid()
    {
        return this.m_Straid;
    } //-- String getStraid() 

    /**
     * Method getSubscriberID
     */
    public String getSubscriberID()
    {
        return this.m_SubscriberID;
    } //-- String getSubscriberID() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6EndSubscriber;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getUserDescr
     */
    public String getUserDescr()
    {
        return this.m_UserDescr;
    } //-- String getUserDescr() 

    /**
     * Method getlinked_port
     */
    public String getlinked_port()
    {
        return this.m_linked_port;
    } //-- String getlinked_port() 

    /**
     * Method getsid
     */
    public String getsid()
    {
        return this.m_sid;
    } //-- String getsid() 

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
            case 0x34F6:
                if (m_SubscriberID == null) {
                    m_SubscriberID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34F7:
                if (m_Straid == null) {
                    m_Straid = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34F8:
                if (m_UserDescr == null) {
                    m_UserDescr = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34F9:
                if (m_RecordTypeTlvCode == null) {
                    m_RecordTypeTlvCode = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34F9, m_RecordTypeTlvCode);
        TLVHelper.addEmbeddedTLV(tlv, 0x34F7, m_Straid);
        TLVHelper.addEmbeddedTLV(tlv, 0x34F6, m_SubscriberID);
        TLVHelper.addEmbeddedTLV(tlv, 0x34F8, m_UserDescr);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setDevicesRegion
     * 
     * @param DevicesRegion
     */
    public void setDevicesRegion(String DevicesRegion)
    {
        this.m_DevicesRegion = DevicesRegion;
    } //-- void setDevicesRegion(String) 

    /**
     * Method setID
     * 
     * @param ID
     */
    public void setID(com.calix.system.server.dbmodel.ICMSAid ID)
    {
        this.m_ID = ID;
    } //-- void setID(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setIdentityValue
     * 
     * @param ID
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid ID)
    {
        this.m_ID = (com.calix.system.server.dbmodel.ICMSAid)ID;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setLinked_Network
     * 
     * @param Linked_Network
     */
    public void setLinked_Network(String Linked_Network)
    {
        this.m_Linked_Network = Linked_Network;
    } //-- void setLinked_Network(String) 

    /**
     * Method setRecordTypeTlvCode
     * 
     * @param RecordTypeTlvCode
     */
    public void setRecordTypeTlvCode(Integer RecordTypeTlvCode)
    {
        this.m_RecordTypeTlvCode = RecordTypeTlvCode;
    } //-- void setRecordTypeTlvCode(Integer) 

    /**
     * Method setStraid
     * 
     * @param Straid
     */
    public void setStraid(String Straid)
    {
        this.m_Straid = Straid;
    } //-- void setStraid(String) 

    /**
     * Method setSubscriberID
     * 
     * @param SubscriberID
     */
    public void setSubscriberID(String SubscriberID)
    {
        this.m_SubscriberID = SubscriberID;
    } //-- void setSubscriberID(String) 

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
     * Method setUserDescr
     * 
     * @param UserDescr
     */
    public void setUserDescr(String UserDescr)
    {
        this.m_UserDescr = UserDescr;
    } //-- void setUserDescr(String) 

    /**
     * Method setlinked_port
     * 
     * @param linked_port
     */
    public void setlinked_port(String linked_port)
    {
        this.m_linked_port = linked_port;
    } //-- void setlinked_port(String) 

    /**
     * Method setsid
     * 
     * @param sid
     */
    public void setsid(String sid)
    {
        this.m_sid = sid;
    } //-- void setsid(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6EndSubscriber ) {
            super.updateFields(obj1);
            B6EndSubscriber obj = (B6EndSubscriber)obj1;
           if (obj.getDevicesRegion() != null )
               setDevicesRegion((String)Helper.copy(obj.getDevicesRegion()));
           if (obj.getID() != null )
               setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
           if (obj.getLinked_Network() != null )
               setLinked_Network((String)Helper.copy(obj.getLinked_Network()));
           if (obj.getRecordTypeTlvCode() != null )
               setRecordTypeTlvCode((Integer)Helper.copy(obj.getRecordTypeTlvCode()));
           if (obj.getStraid() != null )
               setStraid((String)Helper.copy(obj.getStraid()));
           if (obj.getSubscriberID() != null )
               setSubscriberID((String)Helper.copy(obj.getSubscriberID()));
           if (obj.getUserDescr() != null )
               setUserDescr((String)Helper.copy(obj.getUserDescr()));
           if (obj.getlinked_port() != null )
               setlinked_port((String)Helper.copy(obj.getlinked_port()));
           if (obj.getsid() != null )
               setsid((String)Helper.copy(obj.getsid()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

    
    // BEGIN CODE
	public String toString(){
        StringBuffer sb = new StringBuffer();

        sb.append("ID=").append(getID());
        sb.append(", objClass=").append(getObjectClass());
        sb.append(", DevicesRegion=").append(getDevicesRegion());
        sb.append(", Linked_Network=").append(getLinked_Network());
        sb.append(", RecordTypeTlvCode=").append(getRecordTypeTlvCode());
        sb.append(", Straid=").append(getStraid());
        sb.append(", SubscriberID=").append(getSubscriberID());
        sb.append(", UserDescr=").append(getUserDescr());
        sb.append(", linked_port=").append(getlinked_port());
        sb.append(", sid=").append(getsid());
        sb.append(", IdentityValue=").append(getIdentityValue());

        return sb.toString();
	}
	 // END CODE
	
	
    
}
