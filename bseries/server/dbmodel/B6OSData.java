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

/**
 * Class B6OSData.
 * 
 * @version $Revision$ $Date$
 */
public class B6OSData extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CreationDate
     */
    public Long m_CreationDate;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_ImageLocation
     */
    public String m_ImageLocation;

    /**
     * Field m_ImageType
     */
    public String m_ImageType;

    /**
     * Field m_Model
     */
    public String m_Model;

    /**
     * Field m_OSDataName
     */
    public com.calix.system.server.dbmodel.ICMSAid m_OSDataName;

    /**
     * Field m_TftpServer
     */
    public String m_TftpServer;

    /**
     * Field m_Version
     */
    public String m_Version;

    /**
     * Field m_imageFile
     */
    public String m_imageFile;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6OSData";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6OSData() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6OSData()


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
        if( obj1 instanceof B6OSData ) {
            super.copyFields(obj1);
            B6OSData obj = (B6OSData)obj1;
            setCreationDate((Long)Helper.copy(obj.getCreationDate()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setImageLocation((String)Helper.copy(obj.getImageLocation()));
            setImageType((String)Helper.copy(obj.getImageType()));
            setModel((String)Helper.copy(obj.getModel()));
            setOSDataName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getOSDataName()));
            setTftpServer((String)Helper.copy(obj.getTftpServer()));
            setVersion((String)Helper.copy(obj.getVersion()));
            setimageFile((String)Helper.copy(obj.getimageFile()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCreationDate
     */
    public Long getCreationDate()
    {
        return this.m_CreationDate;
    } //-- Long getCreationDate() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_OSDataName;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getImageLocation
     */
    public String getImageLocation()
    {
        return this.m_ImageLocation;
    } //-- String getImageLocation() 

    /**
     * Method getImageType
     */
    public String getImageType()
    {
        return this.m_ImageType;
    } //-- String getImageType() 

    /**
     * Method getModel
     */
    public String getModel()
    {
        return this.m_Model;
    } //-- String getModel() 

    /**
     * Method getOSDataName
     */
    public com.calix.system.server.dbmodel.ICMSAid getOSDataName()
    {
        return this.m_OSDataName;
    } //-- com.calix.system.server.dbmodel.ICMSAid getOSDataName() 

    /**
     * Method getTftpServer
     */
    public String getTftpServer()
    {
        return this.m_TftpServer;
    } //-- String getTftpServer() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6OSData;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getVersion
     */
    public String getVersion()
    {
        return this.m_Version;
    } //-- String getVersion() 

    /**
     * Method getimageFile
     */
    public String getimageFile()
    {
        return this.m_imageFile;
    } //-- String getimageFile() 

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
            case 0x3425:
                if (m_Model == null) {
                    m_Model = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3426:
                if (m_Version == null) {
                    m_Version = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3427:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3428:
                if (m_CreationDate == null) {
                    m_CreationDate = TLVHelper.getLongValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3429:
                if (m_imageFile == null) {
                    m_imageFile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3430:
                if (m_ImageLocation == null) {
                    m_ImageLocation = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3431:
                if (m_ImageType == null) {
                    m_ImageType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3432:
                if (m_TftpServer == null) {
                    m_TftpServer = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3428, m_CreationDate);
        TLVHelper.addEmbeddedTLV(tlv, 0x3427, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3430, m_ImageLocation);
        TLVHelper.addEmbeddedTLV(tlv, 0x3431, m_ImageType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3425, m_Model);
        TLVHelper.addEmbeddedTLV(tlv, 0x3432, m_TftpServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x3426, m_Version);
        TLVHelper.addEmbeddedTLV(tlv, 0x3429, m_imageFile);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCreationDate
     * 
     * @param CreationDate
     */
    public void setCreationDate(Long CreationDate)
    {
        this.m_CreationDate = CreationDate;
    } //-- void setCreationDate(Long) 

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
     * Method setIdentityValue
     * 
     * @param OSDataName
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid OSDataName)
    {
        this.m_OSDataName = (com.calix.system.server.dbmodel.ICMSAid)OSDataName;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setImageLocation
     * 
     * @param ImageLocation
     */
    public void setImageLocation(String ImageLocation)
    {
        this.m_ImageLocation = ImageLocation;
    } //-- void setImageLocation(String) 

    /**
     * Method setImageType
     * 
     * @param ImageType
     */
    public void setImageType(String ImageType)
    {
        this.m_ImageType = ImageType;
    } //-- void setImageType(String) 

    /**
     * Method setModel
     * 
     * @param Model
     */
    public void setModel(String Model)
    {
        this.m_Model = Model;
    } //-- void setModel(String) 

    /**
     * Method setOSDataName
     * 
     * @param OSDataName
     */
    public void setOSDataName(com.calix.system.server.dbmodel.ICMSAid OSDataName)
    {
        this.m_OSDataName = OSDataName;
    } //-- void setOSDataName(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setTftpServer
     * 
     * @param TftpServer
     */
    public void setTftpServer(String TftpServer)
    {
        this.m_TftpServer = TftpServer;
    } //-- void setTftpServer(String) 

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
     * Method setVersion
     * 
     * @param Version
     */
    public void setVersion(String Version)
    {
        this.m_Version = Version;
    } //-- void setVersion(String) 

    /**
     * Method setimageFile
     * 
     * @param imageFile
     */
    public void setimageFile(String imageFile)
    {
        this.m_imageFile = imageFile;
    } //-- void setimageFile(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6OSData ) {
            super.updateFields(obj1);
            B6OSData obj = (B6OSData)obj1;
           if (obj.getCreationDate() != null )
               setCreationDate((Long)Helper.copy(obj.getCreationDate()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getImageLocation() != null )
               setImageLocation((String)Helper.copy(obj.getImageLocation()));
           if (obj.getImageType() != null )
               setImageType((String)Helper.copy(obj.getImageType()));
           if (obj.getModel() != null )
               setModel((String)Helper.copy(obj.getModel()));
           if (obj.getOSDataName() != null )
               setOSDataName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getOSDataName()));
           if (obj.getTftpServer() != null )
               setTftpServer((String)Helper.copy(obj.getTftpServer()));
           if (obj.getVersion() != null )
               setVersion((String)Helper.copy(obj.getVersion()));
           if (obj.getimageFile() != null )
               setimageFile((String)Helper.copy(obj.getimageFile()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
 // BEGIN CODE
   
    
    public byte[] getImageFile() {
    	if(m_imageFile != null)
    		return m_imageFile.getBytes();
    	else 
    		return null;
	}

	public void setImageFile(byte[] m_image_File) {
		if(m_image_File != null) 
			this.m_imageFile = new String(m_image_File);
	}

	public void setconvertName(String convertName) {
		this.m_OSDataName = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_OSDataName.toString();
	} // -- String getconvertName()
    
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
		C7Database db = C7Database.getInstance();
		CMSObject obj = null;
		try {
			db.beginTransaction();
			Collection resultList = db.executeQuery(this.getClass(), "name = '"
					+ this.getIdentityValue().toString() + "'", -1, -1);
			if (resultList != null && !resultList.isEmpty()) {
				Iterator itr = resultList.iterator();
				obj = (CMSObject) itr.next();
			}
			db.commitTransaction();
			} catch (Exception e) {
				e.printStackTrace();
				db.rollbackTransaction();
			}finally{
				db.close();
			}
		if (obj == null)
			throw new EMSDatabaseException(
					EMSDatabaseException._loadNonExistentFail_,
					"Object does not exist");
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
	
    // END CODE
}