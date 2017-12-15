/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import com.calix.ems.server.dbmodel.BaseEMSAction;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class BseriesNotificationObject.
 * 
 * @version $Revision$ $Date$
 */
public class BseriesNotificationObject extends BaseEMSAction {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Module Id for notification
     */
    public String m_moduleId;

    /**
     * Property String used for notification
     */
    public String m_propertyString;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "BseriesNotificationObject";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public BseriesNotificationObject() {
        super();
    } //-- com.calix.bseries.server.dbmodel.BseriesNotificationObject()


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
        if( obj1 instanceof BseriesNotificationObject ) {
            super.copyFields(obj1);
            BseriesNotificationObject obj = (BseriesNotificationObject)obj1;
            setmoduleId((String)Helper.copy(obj.getmoduleId()));
            setpropertyString((String)Helper.copy(obj.getpropertyString()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.BseriesNotificationObject;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getmoduleId
     */
    public String getmoduleId()
    {
        return this.m_moduleId;
    } //-- String getmoduleId() 

    /**
     * Method getpropertyString
     */
    public String getpropertyString()
    {
        return this.m_propertyString;
    } //-- String getpropertyString() 

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
            case 0x33F9:
                if (m_moduleId == null) {
                    m_moduleId = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3400:
                if (m_propertyString == null) {
                    m_propertyString = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x33F9, m_moduleId);
        TLVHelper.addEmbeddedTLV(tlv, 0x3400, m_propertyString);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

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
     * Method setmoduleId
     * 
     * @param moduleId
     */
    public void setmoduleId(String moduleId)
    {
        this.m_moduleId = moduleId;
    } //-- void setmoduleId(String) 

    /**
     * Method setpropertyString
     * 
     * @param propertyString
     */
    public void setpropertyString(String propertyString)
    {
        this.m_propertyString = propertyString;
    } //-- void setpropertyString(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof BseriesNotificationObject ) {
            super.updateFields(obj1);
            BseriesNotificationObject obj = (BseriesNotificationObject)obj1;
           if (obj.getmoduleId() != null )
               setmoduleId((String)Helper.copy(obj.getmoduleId()));
           if (obj.getpropertyString() != null )
               setpropertyString((String)Helper.copy(obj.getpropertyString()));
        }
    } //-- void updateFields(CMSObject) 

    //BEGIN CODE
    public boolean isEMSObject(){
    	return true;
    }
    //END CODE
}
