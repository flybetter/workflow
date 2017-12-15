/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Class CalixB6Chassis.
 * 
 * @version $Revision$ $Date$
 */
public class CalixB6Chassis extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Chassis Type
     */
    public String m_ChassisType;

    /**
     * Field m_DisplayName
     */
    public String m_DisplayName;

    /**
     * Icon Height
     */
    public Integer m_Height;

    /**
     * Date and Time
     */
    public String m_ManufactureDate;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Reference to a Region
     */
    public String m_Region;

    /**
     * Serial Number
     */
    public String m_SerialNumber;

    /**
     * Icon Width
     */
    public Integer m_Width;

    /**
     * X coordinate
     */
    public Integer m_XOffset;

    /**
     * Y coordinate
     */
    public Integer m_YOffset;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "CalixB6Chassis";

    /**
     * new int[]{empty networkid, CalixB6Chassis}
     */
    public static int[] m_hierarchy = new int[]{0, 13517};

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public CalixB6Chassis() {
        super();
    } //-- com.calix.bseries.server.dbmodel.CalixB6Chassis()


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
        if( obj1 instanceof CalixB6Chassis ) {
            super.copyFields(obj1);
            CalixB6Chassis obj = (CalixB6Chassis)obj1;
            setChassisType((String)Helper.copy(obj.getChassisType()));
            setDisplayName((String)Helper.copy(obj.getDisplayName()));
            setHeight((Integer)Helper.copy(obj.getHeight()));
            setManufactureDate((String)Helper.copy(obj.getManufactureDate()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setRegion((String)Helper.copy(obj.getRegion()));
            setSerialNumber((String)Helper.copy(obj.getSerialNumber()));
            setWidth((Integer)Helper.copy(obj.getWidth()));
            setXOffset((Integer)Helper.copy(obj.getXOffset()));
            setYOffset((Integer)Helper.copy(obj.getYOffset()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getChassisType
     */
    public String getChassisType()
    {
        return this.m_ChassisType;
    } //-- String getChassisType() 

    /**
     * Method getDisplayName
     */
    public String getDisplayName()
    {
        return this.m_DisplayName;
    } //-- String getDisplayName() 

    /**
     * Method getHeight
     */
    public Integer getHeight()
    {
        return this.m_Height;
    } //-- Integer getHeight() 

    /**
     * Method getHierarchy
     */
    public int[] getHierarchy()
    {
        return m_hierarchy;
    } //-- int[] getHierarchy() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getManufactureDate
     */
    public String getManufactureDate()
    {
        return this.m_ManufactureDate;
    } //-- String getManufactureDate() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

    /**
     * Method getRegion
     */
    public String getRegion()
    {
        return this.m_Region;
    } //-- String getRegion() 

    /**
     * Method getSerialNumber
     */
    public String getSerialNumber()
    {
        return this.m_SerialNumber;
    } //-- String getSerialNumber() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.CalixB6Chassis;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getWidth
     */
    public Integer getWidth()
    {
        return this.m_Width;
    } //-- Integer getWidth() 

    /**
     * Method getXOffset
     */
    public Integer getXOffset()
    {
        return this.m_XOffset;
    } //-- Integer getXOffset() 

    /**
     * Method getYOffset
     */
    public Integer getYOffset()
    {
        return this.m_YOffset;
    } //-- Integer getYOffset() 

    /**
     * Method isEMSCacheSupported
     */
    public boolean isEMSCacheSupported()
    {
        return true;
    } //-- boolean isEMSCacheSupported() 

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
            case 0x0DBC:
                if (m_YOffset == null) {
                    m_YOffset = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x0DBD:
                if (m_Width == null) {
                    m_Width = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x0DBE:
                if (m_Height == null) {
                    m_Height = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x0DC1:
                if (m_Region == null) {
                    m_Region = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x0DC4:
                if (m_XOffset == null) {
                    m_XOffset = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34E4:
                if (m_SerialNumber == null) {
                    m_SerialNumber = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34E5:
                if (m_ChassisType == null) {
                    m_ChassisType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34E6:
                if (m_ManufactureDate == null) {
                    m_ManufactureDate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34E7:
                if (m_DisplayName == null) {
                    m_DisplayName = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34E5, m_ChassisType);
        TLVHelper.addEmbeddedTLV(tlv, 0x34E7, m_DisplayName);
        TLVHelper.addEmbeddedTLV(tlv, 0x0DBE, m_Height);
        TLVHelper.addEmbeddedTLV(tlv, 0x34E6, m_ManufactureDate);
        TLVHelper.addEmbeddedTLV(tlv, 0x0DC1, m_Region);
        TLVHelper.addEmbeddedTLV(tlv, 0x34E4, m_SerialNumber);
        TLVHelper.addEmbeddedTLV(tlv, 0x0DBD, m_Width);
        TLVHelper.addEmbeddedTLV(tlv, 0x0DC4, m_XOffset);
        TLVHelper.addEmbeddedTLV(tlv, 0x0DBC, m_YOffset);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setChassisType
     * 
     * @param ChassisType
     */
    public void setChassisType(String ChassisType)
    {
        this.m_ChassisType = ChassisType;
    } //-- void setChassisType(String) 

    /**
     * Method setDisplayName
     * 
     * @param DisplayName
     */
    public void setDisplayName(String DisplayName)
    {
        this.m_DisplayName = DisplayName;
    } //-- void setDisplayName(String) 

    /**
     * Method setHeight
     * 
     * @param Height
     */
    public void setHeight(Integer Height)
    {
        this.m_Height = Height;
    } //-- void setHeight(Integer) 

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
     * Method setManufactureDate
     * 
     * @param ManufactureDate
     */
    public void setManufactureDate(String ManufactureDate)
    {
        this.m_ManufactureDate = ManufactureDate;
    } //-- void setManufactureDate(String) 

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
     * Method setRegion
     * 
     * @param Region
     */
    public void setRegion(String Region)
    {
        this.m_Region = Region;
    } //-- void setRegion(String) 

    /**
     * Method setSerialNumber
     * 
     * @param SerialNumber
     */
    public void setSerialNumber(String SerialNumber)
    {
        this.m_SerialNumber = SerialNumber;
    } //-- void setSerialNumber(String) 

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
     * Method setWidth
     * 
     * @param Width
     */
    public void setWidth(Integer Width)
    {
        this.m_Width = Width;
    } //-- void setWidth(Integer) 

    /**
     * Method setXOffset
     * 
     * @param XOffset
     */
    public void setXOffset(Integer XOffset)
    {
        this.m_XOffset = XOffset;
    } //-- void setXOffset(Integer) 

    /**
     * Method setYOffset
     * 
     * @param YOffset
     */
    public void setYOffset(Integer YOffset)
    {
        this.m_YOffset = YOffset;
    } //-- void setYOffset(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof CalixB6Chassis ) {
            super.updateFields(obj1);
            CalixB6Chassis obj = (CalixB6Chassis)obj1;
           if (obj.getChassisType() != null )
               setChassisType((String)Helper.copy(obj.getChassisType()));
           if (obj.getDisplayName() != null )
               setDisplayName((String)Helper.copy(obj.getDisplayName()));
           if (obj.getHeight() != null )
               setHeight((Integer)Helper.copy(obj.getHeight()));
           if (obj.getManufactureDate() != null )
               setManufactureDate((String)Helper.copy(obj.getManufactureDate()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getRegion() != null )
               setRegion((String)Helper.copy(obj.getRegion()));
           if (obj.getSerialNumber() != null )
               setSerialNumber((String)Helper.copy(obj.getSerialNumber()));
           if (obj.getWidth() != null )
               setWidth((Integer)Helper.copy(obj.getWidth()));
           if (obj.getXOffset() != null )
               setXOffset((Integer)Helper.copy(obj.getXOffset()));
           if (obj.getYOffset() != null )
               setYOffset((Integer)Helper.copy(obj.getYOffset()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
    public Collection getStatic_Links() {
        return null;
    }
}
