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
 * Class BseriesDataService.
 * 
 * @version $Revision$ $Date$
 */
public class BseriesDataService extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_DSBitrate
     */
    public String m_DSBitrate;

    /**
     * Field m_DotP
     */
    public Integer m_DotP;

    /**
     * Field m_DscpToDotpMap
     */
    public String m_DscpToDotpMap;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_ServiceModel
     */
    public Integer m_ServiceModel;

    /**
     * Field m_USBitrate
     */
    public String m_USBitrate;

    /**
     * Field m_connectivity
     */
    public Integer m_connectivity;

    /**
     * Field m_customerVLAN
     */
    public Integer m_customerVLAN;

    /**
     * Field m_description
     */
    public String m_description;

    /**
     * Field m_epsVLAN
     */
    public Integer m_epsVLAN;

    /**
     * Field m_maxPriority
     */
    public Integer m_maxPriority;

    /**
     * Field m_pvc
     */
    public String m_pvc;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "BseriesDataService";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public BseriesDataService() {
        super();
    } //-- com.calix.bseries.server.dbmodel.BseriesDataService()


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
        if( obj1 instanceof BseriesDataService ) {
            super.copyFields(obj1);
            BseriesDataService obj = (BseriesDataService)obj1;
            setDSBitrate((String)Helper.copy(obj.getDSBitrate()));
            setDotP((Integer)Helper.copy(obj.getDotP()));
            setDscpToDotpMap((String)Helper.copy(obj.getDscpToDotpMap()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setServiceModel((Integer)Helper.copy(obj.getServiceModel()));
            setUSBitrate((String)Helper.copy(obj.getUSBitrate()));
            setconnectivity((Integer)Helper.copy(obj.getconnectivity()));
            setcustomerVLAN((Integer)Helper.copy(obj.getcustomerVLAN()));
            setdescription((String)Helper.copy(obj.getdescription()));
            setepsVLAN((Integer)Helper.copy(obj.getepsVLAN()));
            setmaxPriority((Integer)Helper.copy(obj.getmaxPriority()));
            setpvc((String)Helper.copy(obj.getpvc()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getDSBitrate
     */
    public String getDSBitrate()
    {
        return this.m_DSBitrate;
    } //-- String getDSBitrate() 

    /**
     * Method getDotP
     */
    public Integer getDotP()
    {
        return this.m_DotP;
    } //-- Integer getDotP() 

    /**
     * Method getDscpToDotpMap
     */
    public String getDscpToDotpMap()
    {
        return this.m_DscpToDotpMap;
    } //-- String getDscpToDotpMap() 

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
     * Method getServiceModel
     */
    public Integer getServiceModel()
    {
        return this.m_ServiceModel;
    } //-- Integer getServiceModel() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.BseriesDataService;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getUSBitrate
     */
    public String getUSBitrate()
    {
        return this.m_USBitrate;
    } //-- String getUSBitrate() 

    /**
     * Method getconnectivity
     */
    public Integer getconnectivity()
    {
        return this.m_connectivity;
    } //-- Integer getconnectivity() 

    /**
     * Method getcustomerVLAN
     */
    public Integer getcustomerVLAN()
    {
        return this.m_customerVLAN;
    } //-- Integer getcustomerVLAN() 

    /**
     * Method getdescription
     */
    public String getdescription()
    {
        return this.m_description;
    } //-- String getdescription() 

    /**
     * Method getepsVLAN
     */
    public Integer getepsVLAN()
    {
        return this.m_epsVLAN;
    } //-- Integer getepsVLAN() 

    /**
     * Method getmaxPriority
     */
    public Integer getmaxPriority()
    {
        return this.m_maxPriority;
    } //-- Integer getmaxPriority() 

    /**
     * Method getpvc
     */
    public String getpvc()
    {
        return this.m_pvc;
    } //-- String getpvc() 

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
            case 0x3414:
                if (m_description == null) {
                    m_description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3415:
                if (m_connectivity == null) {
                    m_connectivity = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3416:
                if (m_pvc == null) {
                    m_pvc = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3417:
                if (m_customerVLAN == null) {
                    m_customerVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3418:
                if (m_DotP == null) {
                    m_DotP = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3419:
                if (m_DSBitrate == null) {
                    m_DSBitrate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3420:
                if (m_USBitrate == null) {
                    m_USBitrate = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3421:
                if (m_maxPriority == null) {
                    m_maxPriority = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3422:
                if (m_ServiceModel == null) {
                    m_ServiceModel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3423:
                if (m_DscpToDotpMap == null) {
                    m_DscpToDotpMap = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3424:
                if (m_epsVLAN == null) {
                    m_epsVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3419, m_DSBitrate);
        TLVHelper.addEmbeddedTLV(tlv, 0x3418, m_DotP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3423, m_DscpToDotpMap);
        TLVHelper.addEmbeddedTLV(tlv, 0x3422, m_ServiceModel);
        TLVHelper.addEmbeddedTLV(tlv, 0x3420, m_USBitrate);
        TLVHelper.addEmbeddedTLV(tlv, 0x3415, m_connectivity);
        TLVHelper.addEmbeddedTLV(tlv, 0x3417, m_customerVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3414, m_description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3424, m_epsVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3421, m_maxPriority);
        TLVHelper.addEmbeddedTLV(tlv, 0x3416, m_pvc);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setDSBitrate
     * 
     * @param DSBitrate
     */
    public void setDSBitrate(String DSBitrate)
    {
        this.m_DSBitrate = DSBitrate;
    } //-- void setDSBitrate(String) 

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
     * Method setDscpToDotpMap
     * 
     * @param DscpToDotpMap
     */
    public void setDscpToDotpMap(String DscpToDotpMap)
    {
        this.m_DscpToDotpMap = DscpToDotpMap;
    } //-- void setDscpToDotpMap(String) 

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
     * Method setServiceModel
     * 
     * @param ServiceModel
     */
    public void setServiceModel(Integer ServiceModel)
    {
        this.m_ServiceModel = ServiceModel;
    } //-- void setServiceModel(Integer) 

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
     * Method setUSBitrate
     * 
     * @param USBitrate
     */
    public void setUSBitrate(String USBitrate)
    {
        this.m_USBitrate = USBitrate;
    } //-- void setUSBitrate(String) 

    /**
     * Method setconnectivity
     * 
     * @param connectivity
     */
    public void setconnectivity(Integer connectivity)
    {
        this.m_connectivity = connectivity;
    } //-- void setconnectivity(Integer) 

    /**
     * Method setcustomerVLAN
     * 
     * @param customerVLAN
     */
    public void setcustomerVLAN(Integer customerVLAN)
    {
        this.m_customerVLAN = customerVLAN;
    } //-- void setcustomerVLAN(Integer) 

    /**
     * Method setdescription
     * 
     * @param description
     */
    public void setdescription(String description)
    {
        this.m_description = description;
    } //-- void setdescription(String) 

    /**
     * Method setepsVLAN
     * 
     * @param epsVLAN
     */
    public void setepsVLAN(Integer epsVLAN)
    {
        this.m_epsVLAN = epsVLAN;
    } //-- void setepsVLAN(Integer) 

    /**
     * Method setmaxPriority
     * 
     * @param maxPriority
     */
    public void setmaxPriority(Integer maxPriority)
    {
        this.m_maxPriority = maxPriority;
    } //-- void setmaxPriority(Integer) 

    /**
     * Method setpvc
     * 
     * @param pvc
     */
    public void setpvc(String pvc)
    {
        this.m_pvc = pvc;
    } //-- void setpvc(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof BseriesDataService ) {
            super.updateFields(obj1);
            BseriesDataService obj = (BseriesDataService)obj1;
           if (obj.getDSBitrate() != null )
               setDSBitrate((String)Helper.copy(obj.getDSBitrate()));
           if (obj.getDotP() != null )
               setDotP((Integer)Helper.copy(obj.getDotP()));
           if (obj.getDscpToDotpMap() != null )
               setDscpToDotpMap((String)Helper.copy(obj.getDscpToDotpMap()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getServiceModel() != null )
               setServiceModel((Integer)Helper.copy(obj.getServiceModel()));
           if (obj.getUSBitrate() != null )
               setUSBitrate((String)Helper.copy(obj.getUSBitrate()));
           if (obj.getconnectivity() != null )
               setconnectivity((Integer)Helper.copy(obj.getconnectivity()));
           if (obj.getcustomerVLAN() != null )
               setcustomerVLAN((Integer)Helper.copy(obj.getcustomerVLAN()));
           if (obj.getdescription() != null )
               setdescription((String)Helper.copy(obj.getdescription()));
           if (obj.getepsVLAN() != null )
               setepsVLAN((Integer)Helper.copy(obj.getepsVLAN()));
           if (obj.getmaxPriority() != null )
               setmaxPriority((Integer)Helper.copy(obj.getmaxPriority()));
           if (obj.getpvc() != null )
               setpvc((String)Helper.copy(obj.getpvc()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
	// BEGIN CODE


	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_Name.toString();
	} // -- String getconvertName()
    
	public Double getconvertDSBitrate() {
		if(m_DSBitrate != null)
			return Double.valueOf(m_DSBitrate).doubleValue();
		else 
			return null;
	}

	public void setconvertDSBitrate(Double m_convertDSBitrate) {
		this.m_DSBitrate = String.valueOf(m_convertDSBitrate);
	}

	public Double getconvertUSBitrate() {
		if(m_USBitrate !=null)
			return Double.valueOf(m_USBitrate).doubleValue();
		else
			return null;
	}

	public void setconvertUSBitrate(Double m_convertUSBitrate) {
		this.m_USBitrate = String.valueOf(m_convertUSBitrate);
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
    	Collection resultList = db.executeQuery(this.getClass(), "name = '"+this.getIdentityValue().toString()+"'", -1, -1);
    	if(resultList != null&&!resultList.isEmpty()){
    			Iterator itr = resultList.iterator();
    			obj = (CMSObject)itr.next();
    	}
		db.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			db.rollbackTransaction();
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
    // END CODE

}

