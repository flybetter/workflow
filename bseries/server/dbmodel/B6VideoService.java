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
 * Class B6VideoService.
 * 
 * @version $Revision$ $Date$
 */
public class B6VideoService extends com.calix.system.server.dbmodel.CMSObject {


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
     * Field m_DotP
     */
    public Integer m_DotP;

    /**
     * Field m_EpsVLAN
     */
    public Integer m_EpsVLAN;

    /**
     * Field m_IgmpVLAN
     */
    public Integer m_IgmpVLAN;

    /**
     * Field m_MaxPriority
     */
    public Integer m_MaxPriority;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_Pvc
     */
    public String m_Pvc;

    /**
     * Field m_dsBitRateMidWare
     */
    public String m_dsBitRateMidWare;

    /**
     * Field m_hdBitRate
     */
    public String m_hdBitRate;

    /**
     * Field m_pipBitRate
     */
    public String m_pipBitRate;

    /**
     * Field m_sdBitRate
     */
    public String m_sdBitRate;

    /**
     * Field m_usBitRateMidWare
     */
    public String m_usBitRateMidWare;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6VideoService";

    /**
     * Field flowID
     */
    public static final int flowID = 1;



      //----------------/
     //- Constructors -/
    //----------------/

    public B6VideoService() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6VideoService()


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
        if( obj1 instanceof B6VideoService ) {
            super.copyFields(obj1);
            B6VideoService obj = (B6VideoService)obj1;
            setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setDotP((Integer)Helper.copy(obj.getDotP()));
            setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
            setIgmpVLAN((Integer)Helper.copy(obj.getIgmpVLAN()));
            setMaxPriority((Integer)Helper.copy(obj.getMaxPriority()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setPvc((String)Helper.copy(obj.getPvc()));
            setdsBitRateMidWare((String)Helper.copy(obj.getdsBitRateMidWare()));
            sethdBitRate((String)Helper.copy(obj.gethdBitRate()));
            setpipBitRate((String)Helper.copy(obj.getpipBitRate()));
            setsdBitRate((String)Helper.copy(obj.getsdBitRate()));
            setusBitRateMidWare((String)Helper.copy(obj.getusBitRateMidWare()));
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
     * Method getDotP
     */
    public Integer getDotP()
    {
        return this.m_DotP;
    } //-- Integer getDotP() 

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
     * Method getIgmpVLAN
     */
    public Integer getIgmpVLAN()
    {
        return this.m_IgmpVLAN;
    } //-- Integer getIgmpVLAN() 

    /**
     * Method getMaxPriority
     */
    public Integer getMaxPriority()
    {
        return this.m_MaxPriority;
    } //-- Integer getMaxPriority() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

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
        return BseriesTlvConstants.B6VideoService;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getdsBitRateMidWare
     */
    public String getdsBitRateMidWare()
    {
        return this.m_dsBitRateMidWare;
    } //-- String getdsBitRateMidWare() 

    /**
     * Method gethdBitRate
     */
    public String gethdBitRate()
    {
        return this.m_hdBitRate;
    } //-- String gethdBitRate() 

    /**
     * Method getpipBitRate
     */
    public String getpipBitRate()
    {
        return this.m_pipBitRate;
    } //-- String getpipBitRate() 

    /**
     * Method getsdBitRate
     */
    public String getsdBitRate()
    {
        return this.m_sdBitRate;
    } //-- String getsdBitRate() 

    /**
     * Method getusBitRateMidWare
     */
    public String getusBitRateMidWare()
    {
        return this.m_usBitRateMidWare;
    } //-- String getusBitRateMidWare() 

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
            case 0x3433:
                if (m_EpsVLAN == null) {
                    m_EpsVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3434:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3435:
                if (m_Pvc == null) {
                    m_Pvc = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3436:
                if (m_CustomerVLAN == null) {
                    m_CustomerVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3437:
                if (m_dsBitRateMidWare == null) {
                    m_dsBitRateMidWare = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3438:
                if (m_pipBitRate == null) {
                    m_pipBitRate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3439:
                if (m_usBitRateMidWare == null) {
                    m_usBitRateMidWare = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343A:
                if (m_sdBitRate == null) {
                    m_sdBitRate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343B:
                if (m_hdBitRate == null) {
                    m_hdBitRate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343C:
                if (m_MaxPriority == null) {
                    m_MaxPriority = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343D:
                if (m_IgmpVLAN == null) {
                    m_IgmpVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343E:
                if (m_DotP == null) {
                    m_DotP = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3436, m_CustomerVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3434, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x343E, m_DotP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3433, m_EpsVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x343D, m_IgmpVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x343C, m_MaxPriority);
        TLVHelper.addEmbeddedTLV(tlv, 0x3435, m_Pvc);
        TLVHelper.addEmbeddedTLV(tlv, 0x3437, m_dsBitRateMidWare);
        TLVHelper.addEmbeddedTLV(tlv, 0x343B, m_hdBitRate);
        TLVHelper.addEmbeddedTLV(tlv, 0x3438, m_pipBitRate);
        TLVHelper.addEmbeddedTLV(tlv, 0x343A, m_sdBitRate);
        TLVHelper.addEmbeddedTLV(tlv, 0x3439, m_usBitRateMidWare);
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
     * Method setDotP
     * 
     * @param DotP
     */
    public void setDotP(Integer DotP)
    {
        this.m_DotP = DotP;
    } //-- void setDotP(Integer) 

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
     * Method setIgmpVLAN
     * 
     * @param IgmpVLAN
     */
    public void setIgmpVLAN(Integer IgmpVLAN)
    {
        this.m_IgmpVLAN = IgmpVLAN;
    } //-- void setIgmpVLAN(Integer) 

    /**
     * Method setMaxPriority
     * 
     * @param MaxPriority
     */
    public void setMaxPriority(Integer MaxPriority)
    {
        this.m_MaxPriority = MaxPriority;
    } //-- void setMaxPriority(Integer) 

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
     * Method setPvc
     * 
     * @param Pvc
     */
    public void setPvc(String Pvc)
    {
        this.m_Pvc = Pvc;
    } //-- void setPvc(String) 

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
     * Method setdsBitRateMidWare
     * 
     * @param dsBitRateMidWare
     */
    public void setdsBitRateMidWare(String dsBitRateMidWare)
    {
        this.m_dsBitRateMidWare = dsBitRateMidWare;
    } //-- void setdsBitRateMidWare(String) 

    /**
     * Method sethdBitRate
     * 
     * @param hdBitRate
     */
    public void sethdBitRate(String hdBitRate)
    {
        this.m_hdBitRate = hdBitRate;
    } //-- void sethdBitRate(String) 

    /**
     * Method setpipBitRate
     * 
     * @param pipBitRate
     */
    public void setpipBitRate(String pipBitRate)
    {
        this.m_pipBitRate = pipBitRate;
    } //-- void setpipBitRate(String) 

    /**
     * Method setsdBitRate
     * 
     * @param sdBitRate
     */
    public void setsdBitRate(String sdBitRate)
    {
        this.m_sdBitRate = sdBitRate;
    } //-- void setsdBitRate(String) 

    /**
     * Method setusBitRateMidWare
     * 
     * @param usBitRateMidWare
     */
    public void setusBitRateMidWare(String usBitRateMidWare)
    {
        this.m_usBitRateMidWare = usBitRateMidWare;
    } //-- void setusBitRateMidWare(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6VideoService ) {
            super.updateFields(obj1);
            B6VideoService obj = (B6VideoService)obj1;
           if (obj.getCustomerVLAN() != null )
               setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getDotP() != null )
               setDotP((Integer)Helper.copy(obj.getDotP()));
           if (obj.getEpsVLAN() != null )
               setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
           if (obj.getIgmpVLAN() != null )
               setIgmpVLAN((Integer)Helper.copy(obj.getIgmpVLAN()));
           if (obj.getMaxPriority() != null )
               setMaxPriority((Integer)Helper.copy(obj.getMaxPriority()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getPvc() != null )
               setPvc((String)Helper.copy(obj.getPvc()));
           if (obj.getdsBitRateMidWare() != null )
               setdsBitRateMidWare((String)Helper.copy(obj.getdsBitRateMidWare()));
           if (obj.gethdBitRate() != null )
               sethdBitRate((String)Helper.copy(obj.gethdBitRate()));
           if (obj.getpipBitRate() != null )
               setpipBitRate((String)Helper.copy(obj.getpipBitRate()));
           if (obj.getsdBitRate() != null )
               setsdBitRate((String)Helper.copy(obj.getsdBitRate()));
           if (obj.getusBitRateMidWare() != null )
               setusBitRateMidWare((String)Helper.copy(obj.getusBitRateMidWare()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
//BEGIN CODE
    
    public void setDSBitRateMidWare(Double dsBitRateMidWare)
    {
    	if(dsBitRateMidWare != null)
    		this.m_dsBitRateMidWare = String.valueOf(dsBitRateMidWare);
    	else
    		this.m_dsBitRateMidWare = null;
    } //-- void setdsBitRateMidWare(String) 

 
    public void setHDBitRate(Double hdBitRate)
    {
        if(hdBitRate != null)
    		this.m_hdBitRate = String.valueOf(hdBitRate);
        else
        	this.m_hdBitRate = null;
    } //-- void sethdBitRate(String) 

    public void setPIPBitRate(Double pipBitRate)
    {
        if(pipBitRate != null)
    		this.m_pipBitRate = String.valueOf(pipBitRate);
        else
        	this.m_pipBitRate = null;
    } //-- void setpipBitRate(String) 

    public void setSDBitRate(Double sdBitRate)
    {
        if(sdBitRate != null)
    		this.m_sdBitRate = String.valueOf(sdBitRate);
        else
        	this.m_sdBitRate = null;
    } //-- void setsdBitRate(String) 

    public void setUSBitRateMidWare(Double usBitRateMidWare)
    {
       if(usBitRateMidWare != null)
    		this.m_usBitRateMidWare = String.valueOf(usBitRateMidWare);
       else
    	   	this.m_usBitRateMidWare = null;
    } //-- void setusBitRateMidWare(String) 
    
    public Double getDSBitRateMidWare()
    {
        if(m_dsBitRateMidWare != null)	
    		return Double.valueOf(this.m_dsBitRateMidWare);
        else
    		return null;
    } //-- String getdsBitRateMidWare() 

    public Double getHDBitRate()
    {
        if(m_hdBitRate != null)
    		return Double.valueOf(this.m_hdBitRate);
        else
    		return null;
    } //-- String gethdBitRate() 

    public Double getPIPBitRate()
    {
        if(m_pipBitRate != null)
    		return Double.valueOf(this.m_pipBitRate);
        else
    		return null;
    } //-- String getpipBitRate() 

    public Double getSDBitRate()
    {
    	if(m_sdBitRate != null)
    		return Double.valueOf(this.m_sdBitRate);
    	else
    		return null;
    } //-- String getsdBitRate() 

    public Double getUSBitRateMidWare()
    {
    	if(m_usBitRateMidWare != null)
        	return Double.valueOf(this.m_usBitRateMidWare);
        else
    		return null;
    } //-- String getusBitRateMidWare() 

    
	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_Name.toString();
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
		} finally {
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
//END CODE
	
}
