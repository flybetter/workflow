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
 * Class B6_GePort.
 * 
 * @version $Revision$ $Date$
 */
public class B6_GePort extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * ID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_ID;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6_GePort";


      //----------------/
     //- Constructors -/
    //----------------/

    public B6_GePort() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6_GePort()


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
        if( obj1 instanceof B6_GePort ) {
            super.copyFields(obj1);
            B6_GePort obj = (B6_GePort)obj1;
            setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
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
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6_GePort;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

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
        if( obj1 instanceof B6_GePort ) {
            super.updateFields(obj1);
            B6_GePort obj = (B6_GePort)obj1;
           if (obj.getID() != null )
               setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

}
