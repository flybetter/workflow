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
import com.calix.system.server.dbmodel.ICMSAid;

/**
 * Class B6CommandResult.
 * 
 * @version $Revision$ $Date$
 */
public class B6CommandResult extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_ID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_ID;

    /**
     * Field m_Request
     */
    public String m_Request;

    /**
     * Field m_Response
     */
    public String m_Response;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6CommandResult";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6CommandResult() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6CommandResult()


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
        if( obj1 instanceof B6CommandResult ) {
            super.copyFields(obj1);
            B6CommandResult obj = (B6CommandResult)obj1;
            setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
            setRequest((String)Helper.copy(obj.getRequest()));
            setResponse((String)Helper.copy(obj.getResponse()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

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
     * Method getRequest
     */
    public String getRequest()
    {
        return this.m_Request;
    } //-- String getRequest() 

    /**
     * Method getResponse
     */
    public String getResponse()
    {
        return this.m_Response;
    } //-- String getResponse() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6CommandResult;
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
            case 0x353B:
                if (m_Request == null) {
                    m_Request = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x353C:
                if (m_Response == null) {
                    m_Response = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x353B, m_Request);
        TLVHelper.addEmbeddedTLV(tlv, 0x353C, m_Response);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

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
     * Method setRequest
     * 
     * @param Request
     */
    public void setRequest(String Request)
    {
        this.m_Request = Request;
    } //-- void setRequest(String) 

    /**
     * Method setResponse
     * 
     * @param Response
     */
    public void setResponse(String Response)
    {
        this.m_Response = Response;
    } //-- void setResponse(String) 

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
        if( obj1 instanceof B6CommandResult ) {
            super.updateFields(obj1);
            B6CommandResult obj = (B6CommandResult)obj1;
           if (obj.getID() != null )
               setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
           if (obj.getRequest() != null )
               setRequest((String)Helper.copy(obj.getRequest()));
           if (obj.getResponse() != null )
               setResponse((String)Helper.copy(obj.getResponse()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

}
