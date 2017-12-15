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
 * Class B6OccamGponOLT.
 * 
 * @version $Revision$ $Date$
 */
public class B6OccamGponOLT extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_discoveredOntsList
     */
    public String m_discoveredOntsList;

    /**
     * Field m_maxNumOntPerPon
     */
    public Integer m_maxNumOntPerPon;

    /**
     * Field m_numOfPons
     */
    public Integer m_numOfPons;

    /**
     * Field m_swVersion
     */
    public String m_swVersion;

    /**
     * Field m_type
     */
    public String m_type;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6OccamGponOLT";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6OccamGponOLT() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6OccamGponOLT()


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
        if( obj1 instanceof B6OccamGponOLT ) {
            super.copyFields(obj1);
            B6OccamGponOLT obj = (B6OccamGponOLT)obj1;
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setdiscoveredOntsList((String)Helper.copy(obj.getdiscoveredOntsList()));
            setmaxNumOntPerPon((Integer)Helper.copy(obj.getmaxNumOntPerPon()));
            setnumOfPons((Integer)Helper.copy(obj.getnumOfPons()));
            setswVersion((String)Helper.copy(obj.getswVersion()));
            settype((String)Helper.copy(obj.gettype()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

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
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6OccamGponOLT;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getdiscoveredOntsList
     */
    public String getdiscoveredOntsList()
    {
        return this.m_discoveredOntsList;
    } //-- String getdiscoveredOntsList() 

    /**
     * Method getmaxNumOntPerPon
     */
    public Integer getmaxNumOntPerPon()
    {
        return this.m_maxNumOntPerPon;
    } //-- Integer getmaxNumOntPerPon() 

    /**
     * Method getnumOfPons
     */
    public Integer getnumOfPons()
    {
        return this.m_numOfPons;
    } //-- Integer getnumOfPons() 

    /**
     * Method getswVersion
     */
    public String getswVersion()
    {
        return this.m_swVersion;
    } //-- String getswVersion() 

    /**
     * Method gettype
     */
    public String gettype()
    {
        return this.m_type;
    } //-- String gettype() 

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
            case 0x340C:
                if (m_swVersion == null) {
                    m_swVersion = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x340D:
                if (m_type == null) {
                    m_type = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x340E:
                if (m_numOfPons == null) {
                    m_numOfPons = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x340F:
                if (m_maxNumOntPerPon == null) {
                    m_maxNumOntPerPon = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3410:
                if (m_discoveredOntsList == null) {
                    m_discoveredOntsList = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3410, m_discoveredOntsList);
        TLVHelper.addEmbeddedTLV(tlv, 0x340F, m_maxNumOntPerPon);
        TLVHelper.addEmbeddedTLV(tlv, 0x340E, m_numOfPons);
        TLVHelper.addEmbeddedTLV(tlv, 0x340C, m_swVersion);
        TLVHelper.addEmbeddedTLV(tlv, 0x340D, m_type);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

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
     * Method setTypeName
     * 
     * @param typeName
     */
    public boolean setTypeName(String typeName)
    {
        return false;
    } //-- boolean setTypeName(String) 

    /**
     * Method setdiscoveredOntsList
     * 
     * @param discoveredOntsList
     */
    public void setdiscoveredOntsList(String discoveredOntsList)
    {
        this.m_discoveredOntsList = discoveredOntsList;
    } //-- void setdiscoveredOntsList(String) 

    /**
     * Method setmaxNumOntPerPon
     * 
     * @param maxNumOntPerPon
     */
    public void setmaxNumOntPerPon(Integer maxNumOntPerPon)
    {
        this.m_maxNumOntPerPon = maxNumOntPerPon;
    } //-- void setmaxNumOntPerPon(Integer) 

    /**
     * Method setnumOfPons
     * 
     * @param numOfPons
     */
    public void setnumOfPons(Integer numOfPons)
    {
        this.m_numOfPons = numOfPons;
    } //-- void setnumOfPons(Integer) 

    /**
     * Method setswVersion
     * 
     * @param swVersion
     */
    public void setswVersion(String swVersion)
    {
        this.m_swVersion = swVersion;
    } //-- void setswVersion(String) 

    /**
     * Method settype
     * 
     * @param type
     */
    public void settype(String type)
    {
        this.m_type = type;
    } //-- void settype(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6OccamGponOLT ) {
            super.updateFields(obj1);
            B6OccamGponOLT obj = (B6OccamGponOLT)obj1;
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getdiscoveredOntsList() != null )
               setdiscoveredOntsList((String)Helper.copy(obj.getdiscoveredOntsList()));
           if (obj.getmaxNumOntPerPon() != null )
               setmaxNumOntPerPon((Integer)Helper.copy(obj.getmaxNumOntPerPon()));
           if (obj.getnumOfPons() != null )
               setnumOfPons((Integer)Helper.copy(obj.getnumOfPons()));
           if (obj.getswVersion() != null )
               setswVersion((String)Helper.copy(obj.getswVersion()));
           if (obj.gettype() != null )
               settype((String)Helper.copy(obj.gettype()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

    
//BEGIN CODE
    
    
	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_Name.toString();
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
	
//END CODE
}
