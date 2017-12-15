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
 * Class B6DSLServiceSpecificDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6DSLServiceSpecificDetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_bondDescription
     */
    public String m_bondDescription;

    /**
     * Field m_bondedPorts
     */
    public String m_bondedPorts;

    /**
     * Field m_dsBitRate_DSLService
     */
    public Integer m_dsBitRate_DSLService;

    /**
     * Field m_dslProfileName
     */
    public String m_dslProfileName;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field m_serviceIdentifier
     */
    public Integer m_serviceIdentifier;

    /**
     * Field m_usBitRate_DSLService
     */
    public Integer m_usBitRate_DSLService;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6DSLServiceSpecificDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6DSLServiceSpecificDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6DSLServiceSpecificDetails()


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
        if( obj1 instanceof B6DSLServiceSpecificDetails ) {
            super.copyFields(obj1);
            B6DSLServiceSpecificDetails obj = (B6DSLServiceSpecificDetails)obj1;
            setbondDescription((String)Helper.copy(obj.getbondDescription()));
            setbondedPorts((String)Helper.copy(obj.getbondedPorts()));
            setdsBitRate_DSLService((Integer)Helper.copy(obj.getdsBitRate_DSLService()));
            setdslProfileName((String)Helper.copy(obj.getdslProfileName()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setserviceIdentifier((Integer)Helper.copy(obj.getserviceIdentifier()));
            setusBitRate_DSLService((Integer)Helper.copy(obj.getusBitRate_DSLService()));
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
        return BseriesTlvConstants.B6DSLServiceSpecificDetails;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getbondDescription
     */
    public String getbondDescription()
    {
        return this.m_bondDescription;
    } //-- String getbondDescription() 

    /**
     * Method getbondedPorts
     */
    public String getbondedPorts()
    {
        return this.m_bondedPorts;
    } //-- String getbondedPorts() 

    /**
     * Method getdsBitRate_DSLService
     */
    public Integer getdsBitRate_DSLService()
    {
        return this.m_dsBitRate_DSLService;
    } //-- Integer getdsBitRate_DSLService() 

    /**
     * Method getdslProfileName
     */
    public String getdslProfileName()
    {
        return this.m_dslProfileName;
    } //-- String getdslProfileName() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

    /**
     * Method getserviceIdentifier
     */
    public Integer getserviceIdentifier()
    {
        return this.m_serviceIdentifier;
    } //-- Integer getserviceIdentifier() 

    /**
     * Method getusBitRate_DSLService
     */
    public Integer getusBitRate_DSLService()
    {
        return this.m_usBitRate_DSLService;
    } //-- Integer getusBitRate_DSLService() 

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
            case 0x343E:
                if (m_serviceIdentifier == null) {
                    m_serviceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343F:
                if (m_bondedPorts == null) {
                    m_bondedPorts = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3440:
                if (m_bondDescription == null) {
                    m_bondDescription = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3441:
                if (m_dsBitRate_DSLService == null) {
                    m_dsBitRate_DSLService = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3442:
                if (m_usBitRate_DSLService == null) {
                    m_usBitRate_DSLService = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3443:
                if (m_dslProfileName == null) {
                    m_dslProfileName = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3440, m_bondDescription);
        TLVHelper.addEmbeddedTLV(tlv, 0x343F, m_bondedPorts);
        TLVHelper.addEmbeddedTLV(tlv, 0x3441, m_dsBitRate_DSLService);
        TLVHelper.addEmbeddedTLV(tlv, 0x3443, m_dslProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x343E, m_serviceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x3442, m_usBitRate_DSLService);
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
     * Method setbondDescription
     * 
     * @param bondDescription
     */
    public void setbondDescription(String bondDescription)
    {
        this.m_bondDescription = bondDescription;
    } //-- void setbondDescription(String) 

    /**
     * Method setbondedPorts
     * 
     * @param bondedPorts
     */
    public void setbondedPorts(String bondedPorts)
    {
        this.m_bondedPorts = bondedPorts;
    } //-- void setbondedPorts(String) 

    /**
     * Method setdsBitRate_DSLService
     * 
     * @param dsBitRate_DSLService
     */
    public void setdsBitRate_DSLService(Integer dsBitRate_DSLService)
    {
        this.m_dsBitRate_DSLService = dsBitRate_DSLService;
    } //-- void setdsBitRate_DSLService(Integer) 

    /**
     * Method setdslProfileName
     * 
     * @param dslProfileName
     */
    public void setdslProfileName(String dslProfileName)
    {
        this.m_dslProfileName = dslProfileName;
    } //-- void setdslProfileName(String) 

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
     * Method setserviceIdentifier
     * 
     * @param serviceIdentifier
     */
    public void setserviceIdentifier(Integer serviceIdentifier)
    {
        this.m_serviceIdentifier = serviceIdentifier;
    } //-- void setserviceIdentifier(Integer) 

    /**
     * Method setusBitRate_DSLService
     * 
     * @param usBitRate_DSLService
     */
    public void setusBitRate_DSLService(Integer usBitRate_DSLService)
    {
        this.m_usBitRate_DSLService = usBitRate_DSLService;
    } //-- void setusBitRate_DSLService(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6DSLServiceSpecificDetails ) {
            super.updateFields(obj1);
            B6DSLServiceSpecificDetails obj = (B6DSLServiceSpecificDetails)obj1;
           if (obj.getbondDescription() != null )
               setbondDescription((String)Helper.copy(obj.getbondDescription()));
           if (obj.getbondedPorts() != null )
               setbondedPorts((String)Helper.copy(obj.getbondedPorts()));
           if (obj.getdsBitRate_DSLService() != null )
               setdsBitRate_DSLService((Integer)Helper.copy(obj.getdsBitRate_DSLService()));
           if (obj.getdslProfileName() != null )
               setdslProfileName((String)Helper.copy(obj.getdslProfileName()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getserviceIdentifier() != null )
               setserviceIdentifier((Integer)Helper.copy(obj.getserviceIdentifier()));
           if (obj.getusBitRate_DSLService() != null )
               setusBitRate_DSLService((Integer)Helper.copy(obj.getusBitRate_DSLService()));
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