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

/**
 * Class B6DSLFiberPortServiceDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6DSLFiberPortServiceDetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AccessProfileName
     */
    public String m_AccessProfileName;

    /**
     * Field m_CvidMatchClause
     */
    public Integer m_CvidMatchClause;

    /**
     * Field m_IgmpGroupLimit_DSLFiber
     */
    public Integer m_IgmpGroupLimit_DSLFiber;

    /**
     * Field m_MacLearning
     */
    public Integer m_MacLearning;

    /**
     * Field m_MacLimit
     */
    public Integer m_MacLimit;

    /**
     * Field m_One2oneProfileName
     */
    public String m_One2oneProfileName;

    /**
     * Field m_OverWriteAttributes
     */
    public String m_OverWriteAttributes;

    /**
     * Field m_Service
     */
    public Integer m_Service;

    /**
     * Field m_ServiceId
     */
    public Integer m_ServiceId;

    /**
     * Field m_ServiceIdentifier
     */
    public Integer m_ServiceIdentifier;

    /**
     * Field m_ServiceStatus
     */
    public Integer m_ServiceStatus;

    /**
     * Field m_TlsProfileName
     */
    public String m_TlsProfileName;

    /**
     * Field m_VlanOrPVC
     */
    public String m_VlanOrPVC;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6DSLFiberPortServiceDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6DSLFiberPortServiceDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6DSLFiberPortServiceDetails()


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
        if( obj1 instanceof B6DSLFiberPortServiceDetails ) {
            super.copyFields(obj1);
            B6DSLFiberPortServiceDetails obj = (B6DSLFiberPortServiceDetails)obj1;
            setAccessProfileName((String)Helper.copy(obj.getAccessProfileName()));
            setCvidMatchClause((Integer)Helper.copy(obj.getCvidMatchClause()));
            setIgmpGroupLimit_DSLFiber((Integer)Helper.copy(obj.getIgmpGroupLimit_DSLFiber()));
            setMacLearning((Integer)Helper.copy(obj.getMacLearning()));
            setMacLimit((Integer)Helper.copy(obj.getMacLimit()));
            setOne2oneProfileName((String)Helper.copy(obj.getOne2oneProfileName()));
            setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
            setService((Integer)Helper.copy(obj.getService()));
            setServiceId((Integer)Helper.copy(obj.getServiceId()));
            setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
            setServiceStatus((Integer)Helper.copy(obj.getServiceStatus()));
            setTlsProfileName((String)Helper.copy(obj.getTlsProfileName()));
            setVlanOrPVC((String)Helper.copy(obj.getVlanOrPVC()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAccessProfileName
     */
    public String getAccessProfileName()
    {
        return this.m_AccessProfileName;
    } //-- String getAccessProfileName() 

    /**
     * Method getCvidMatchClause
     */
    public Integer getCvidMatchClause()
    {
        return this.m_CvidMatchClause;
    } //-- Integer getCvidMatchClause() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getIgmpGroupLimit_DSLFiber
     */
    public Integer getIgmpGroupLimit_DSLFiber()
    {
        return this.m_IgmpGroupLimit_DSLFiber;
    } //-- Integer getIgmpGroupLimit_DSLFiber() 

    /**
     * Method getMacLearning
     */
    public Integer getMacLearning()
    {
        return this.m_MacLearning;
    } //-- Integer getMacLearning() 

    /**
     * Method getMacLimit
     */
    public Integer getMacLimit()
    {
        return this.m_MacLimit;
    } //-- Integer getMacLimit() 

    /**
     * Method getOne2oneProfileName
     */
    public String getOne2oneProfileName()
    {
        return this.m_One2oneProfileName;
    } //-- String getOne2oneProfileName() 

    /**
     * Method getOverWriteAttributes
     */
    public String getOverWriteAttributes()
    {
        return this.m_OverWriteAttributes;
    } //-- String getOverWriteAttributes() 

    /**
     * Method getService
     */
    public Integer getService()
    {
        return this.m_Service;
    } //-- Integer getService() 

    /**
     * Method getServiceId
     */
    public Integer getServiceId()
    {
        return this.m_ServiceId;
    } //-- Integer getServiceId() 

    /**
     * Method getServiceIdentifier
     */
    public Integer getServiceIdentifier()
    {
        return this.m_ServiceIdentifier;
    } //-- Integer getServiceIdentifier() 

    /**
     * Method getServiceStatus
     */
    public Integer getServiceStatus()
    {
        return this.m_ServiceStatus;
    } //-- Integer getServiceStatus() 

    /**
     * Method getTlsProfileName
     */
    public String getTlsProfileName()
    {
        return this.m_TlsProfileName;
    } //-- String getTlsProfileName() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6DSLFiberPortServiceDetails;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getVlanOrPVC
     */
    public String getVlanOrPVC()
    {
        return this.m_VlanOrPVC;
    } //-- String getVlanOrPVC() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

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
            case 0x3436:
                if (m_ServiceIdentifier == null) {
                    m_ServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3437:
                if (m_Service == null) {
                    m_Service = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3438:
                if (m_AccessProfileName == null) {
                    m_AccessProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3439:
                if (m_TlsProfileName == null) {
                    m_TlsProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343A:
                if (m_ServiceStatus == null) {
                    m_ServiceStatus = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343B:
                if (m_IgmpGroupLimit_DSLFiber == null) {
                    m_IgmpGroupLimit_DSLFiber = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343C:
                if (m_MacLimit == null) {
                    m_MacLimit = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343D:
                if (m_MacLearning == null) {
                    m_MacLearning = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343E:
                if (m_VlanOrPVC == null) {
                    m_VlanOrPVC = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343F:
                if (m_ServiceId == null) {
                    m_ServiceId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3440:
                if (m_OverWriteAttributes == null) {
                    m_OverWriteAttributes = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3441:
                if (m_One2oneProfileName == null) {
                    m_One2oneProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3442:
                if (m_CvidMatchClause == null) {
                    m_CvidMatchClause = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3438, m_AccessProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3442, m_CvidMatchClause);
        TLVHelper.addEmbeddedTLV(tlv, 0x343B, m_IgmpGroupLimit_DSLFiber);
        TLVHelper.addEmbeddedTLV(tlv, 0x343D, m_MacLearning);
        TLVHelper.addEmbeddedTLV(tlv, 0x343C, m_MacLimit);
        TLVHelper.addEmbeddedTLV(tlv, 0x3441, m_One2oneProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3440, m_OverWriteAttributes);
        TLVHelper.addEmbeddedTLV(tlv, 0x3437, m_Service);
        TLVHelper.addEmbeddedTLV(tlv, 0x343F, m_ServiceId);
        TLVHelper.addEmbeddedTLV(tlv, 0x3436, m_ServiceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x343A, m_ServiceStatus);
        TLVHelper.addEmbeddedTLV(tlv, 0x3439, m_TlsProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x343E, m_VlanOrPVC);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAccessProfileName
     * 
     * @param AccessProfileName
     */
    public void setAccessProfileName(String AccessProfileName)
    {
        this.m_AccessProfileName = AccessProfileName;
    } //-- void setAccessProfileName(String) 

    /**
     * Method setCvidMatchClause
     * 
     * @param CvidMatchClause
     */
    public void setCvidMatchClause(Integer CvidMatchClause)
    {
        this.m_CvidMatchClause = CvidMatchClause;
    } //-- void setCvidMatchClause(Integer) 

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
     * Method setIgmpGroupLimit_DSLFiber
     * 
     * @param IgmpGroupLimit_DSLFiber
     */
    public void setIgmpGroupLimit_DSLFiber(Integer IgmpGroupLimit_DSLFiber)
    {
        this.m_IgmpGroupLimit_DSLFiber = IgmpGroupLimit_DSLFiber;
    } //-- void setIgmpGroupLimit_DSLFiber(Integer) 

    /**
     * Method setMacLearning
     * 
     * @param MacLearning
     */
    public void setMacLearning(Integer MacLearning)
    {
        this.m_MacLearning = MacLearning;
    } //-- void setMacLearning(Integer) 

    /**
     * Method setMacLimit
     * 
     * @param MacLimit
     */
    public void setMacLimit(Integer MacLimit)
    {
        this.m_MacLimit = MacLimit;
    } //-- void setMacLimit(Integer) 

    /**
     * Method setOne2oneProfileName
     * 
     * @param One2oneProfileName
     */
    public void setOne2oneProfileName(String One2oneProfileName)
    {
        this.m_One2oneProfileName = One2oneProfileName;
    } //-- void setOne2oneProfileName(String) 

    /**
     * Method setOverWriteAttributes
     * 
     * @param OverWriteAttributes
     */
    public void setOverWriteAttributes(String OverWriteAttributes)
    {
        this.m_OverWriteAttributes = OverWriteAttributes;
    } //-- void setOverWriteAttributes(String) 

    /**
     * Method setService
     * 
     * @param Service
     */
    public void setService(Integer Service)
    {
        this.m_Service = Service;
    } //-- void setService(Integer) 

    /**
     * Method setServiceId
     * 
     * @param ServiceId
     */
    public void setServiceId(Integer ServiceId)
    {
        this.m_ServiceId = ServiceId;
    } //-- void setServiceId(Integer) 

    /**
     * Method setServiceIdentifier
     * 
     * @param ServiceIdentifier
     */
    public void setServiceIdentifier(Integer ServiceIdentifier)
    {
        this.m_ServiceIdentifier = ServiceIdentifier;
    } //-- void setServiceIdentifier(Integer) 

    /**
     * Method setServiceStatus
     * 
     * @param ServiceStatus
     */
    public void setServiceStatus(Integer ServiceStatus)
    {
        this.m_ServiceStatus = ServiceStatus;
    } //-- void setServiceStatus(Integer) 

    /**
     * Method setTlsProfileName
     * 
     * @param TlsProfileName
     */
    public void setTlsProfileName(String TlsProfileName)
    {
        this.m_TlsProfileName = TlsProfileName;
    } //-- void setTlsProfileName(String) 

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
     * Method setVlanOrPVC
     * 
     * @param VlanOrPVC
     */
    public void setVlanOrPVC(String VlanOrPVC)
    {
        this.m_VlanOrPVC = VlanOrPVC;
    } //-- void setVlanOrPVC(String) 

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
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6DSLFiberPortServiceDetails ) {
            super.updateFields(obj1);
            B6DSLFiberPortServiceDetails obj = (B6DSLFiberPortServiceDetails)obj1;
           if (obj.getAccessProfileName() != null )
               setAccessProfileName((String)Helper.copy(obj.getAccessProfileName()));
           if (obj.getCvidMatchClause() != null )
               setCvidMatchClause((Integer)Helper.copy(obj.getCvidMatchClause()));
           if (obj.getIgmpGroupLimit_DSLFiber() != null )
               setIgmpGroupLimit_DSLFiber((Integer)Helper.copy(obj.getIgmpGroupLimit_DSLFiber()));
           if (obj.getMacLearning() != null )
               setMacLearning((Integer)Helper.copy(obj.getMacLearning()));
           if (obj.getMacLimit() != null )
               setMacLimit((Integer)Helper.copy(obj.getMacLimit()));
           if (obj.getOne2oneProfileName() != null )
               setOne2oneProfileName((String)Helper.copy(obj.getOne2oneProfileName()));
           if (obj.getOverWriteAttributes() != null )
               setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
           if (obj.getService() != null )
               setService((Integer)Helper.copy(obj.getService()));
           if (obj.getServiceId() != null )
               setServiceId((Integer)Helper.copy(obj.getServiceId()));
           if (obj.getServiceIdentifier() != null )
               setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
           if (obj.getServiceStatus() != null )
               setServiceStatus((Integer)Helper.copy(obj.getServiceStatus()));
           if (obj.getTlsProfileName() != null )
               setTlsProfileName((String)Helper.copy(obj.getTlsProfileName()));
           if (obj.getVlanOrPVC() != null )
               setVlanOrPVC((String)Helper.copy(obj.getVlanOrPVC()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
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
	
//END CODE
}