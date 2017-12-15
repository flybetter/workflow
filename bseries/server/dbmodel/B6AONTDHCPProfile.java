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
 * Class B6AONTDHCPProfile.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTDHCPProfile extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_DHCPSubscribers
     */
    public String m_DHCPSubscribers;

    /**
     * Field m_EXcludedRange
     */
    public String m_EXcludedRange;

    /**
     * Field m_availClients
     */
    public Integer m_availClients;

    /**
     * Field m_cmsServer
     */
    public String m_cmsServer;

    /**
     * Field m_defaultRouter
     */
    public String m_defaultRouter;

    /**
     * Field m_description
     */
    public String m_description;

    /**
     * Field m_maxClients
     */
    public Integer m_maxClients;

    /**
     * Field m_name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_name;

    /**
     * Field m_networkAddress
     */
    public String m_networkAddress;

    /**
     * Field m_networkMask
     */
    public String m_networkMask;

    /**
     * Field m_numClients
     */
    public Integer m_numClients;

    /**
     * Field m_tftpServer
     */
    public String m_tftpServer;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTDHCPProfile";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTDHCPProfile() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTDHCPProfile()


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
        if( obj1 instanceof B6AONTDHCPProfile ) {
            super.copyFields(obj1);
            B6AONTDHCPProfile obj = (B6AONTDHCPProfile)obj1;
            setDHCPSubscribers((String)Helper.copy(obj.getDHCPSubscribers()));
            setEXcludedRange((String)Helper.copy(obj.getEXcludedRange()));
            setavailClients((Integer)Helper.copy(obj.getavailClients()));
            setcmsServer((String)Helper.copy(obj.getcmsServer()));
            setdefaultRouter((String)Helper.copy(obj.getdefaultRouter()));
            setdescription((String)Helper.copy(obj.getdescription()));
            setmaxClients((Integer)Helper.copy(obj.getmaxClients()));
            setname((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getname()));
            setnetworkAddress((String)Helper.copy(obj.getnetworkAddress()));
            setnetworkMask((String)Helper.copy(obj.getnetworkMask()));
            setnumClients((Integer)Helper.copy(obj.getnumClients()));
            settftpServer((String)Helper.copy(obj.gettftpServer()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getDHCPSubscribers
     */
    public String getDHCPSubscribers()
    {
        return this.m_DHCPSubscribers;
    } //-- String getDHCPSubscribers() 

    /**
     * Method getEXcludedRange
     */
    public String getEXcludedRange()
    {
        return this.m_EXcludedRange;
    } //-- String getEXcludedRange() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6AONTDHCPProfile;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getavailClients
     */
    public Integer getavailClients()
    {
        return this.m_availClients;
    } //-- Integer getavailClients() 

    /**
     * Method getcmsServer
     */
    public String getcmsServer()
    {
        return this.m_cmsServer;
    } //-- String getcmsServer() 

    /**
     * Method getdefaultRouter
     */
    public String getdefaultRouter()
    {
        return this.m_defaultRouter;
    } //-- String getdefaultRouter() 

    /**
     * Method getdescription
     */
    public String getdescription()
    {
        return this.m_description;
    } //-- String getdescription() 

    /**
     * Method getmaxClients
     */
    public Integer getmaxClients()
    {
        return this.m_maxClients;
    } //-- Integer getmaxClients() 

    /**
     * Method getname
     */
    public com.calix.system.server.dbmodel.ICMSAid getname()
    {
        return this.m_name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getname() 

    /**
     * Method getnetworkAddress
     */
    public String getnetworkAddress()
    {
        return this.m_networkAddress;
    } //-- String getnetworkAddress() 

    /**
     * Method getnetworkMask
     */
    public String getnetworkMask()
    {
        return this.m_networkMask;
    } //-- String getnetworkMask() 

    /**
     * Method getnumClients
     */
    public Integer getnumClients()
    {
        return this.m_numClients;
    } //-- Integer getnumClients() 

    /**
     * Method gettftpServer
     */
    public String gettftpServer()
    {
        return this.m_tftpServer;
    } //-- String gettftpServer() 

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
            case 0x3481:
                if (m_tftpServer == null) {
                    m_tftpServer = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3482:
                if (m_networkMask == null) {
                    m_networkMask = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3483:
                if (m_networkAddress == null) {
                    m_networkAddress = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3484:
                if (m_EXcludedRange == null) {
                    m_EXcludedRange = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3485:
                if (m_description == null) {
                    m_description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3486:
                if (m_defaultRouter == null) {
                    m_defaultRouter = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3487:
                if (m_numClients == null) {
                    m_numClients = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3488:
                if (m_maxClients == null) {
                    m_maxClients = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3489:
                if (m_availClients == null) {
                    m_availClients = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348A:
                if (m_DHCPSubscribers == null) {
                    m_DHCPSubscribers = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348B:
                if (m_cmsServer == null) {
                    m_cmsServer = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x348A, m_DHCPSubscribers);
        TLVHelper.addEmbeddedTLV(tlv, 0x3484, m_EXcludedRange);
        TLVHelper.addEmbeddedTLV(tlv, 0x3489, m_availClients);
        TLVHelper.addEmbeddedTLV(tlv, 0x348B, m_cmsServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x3486, m_defaultRouter);
        TLVHelper.addEmbeddedTLV(tlv, 0x3485, m_description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3488, m_maxClients);
        TLVHelper.addEmbeddedTLV(tlv, 0x3483, m_networkAddress);
        TLVHelper.addEmbeddedTLV(tlv, 0x3482, m_networkMask);
        TLVHelper.addEmbeddedTLV(tlv, 0x3487, m_numClients);
        TLVHelper.addEmbeddedTLV(tlv, 0x3481, m_tftpServer);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setDHCPSubscribers
     * 
     * @param DHCPSubscribers
     */
    public void setDHCPSubscribers(String DHCPSubscribers)
    {
        this.m_DHCPSubscribers = DHCPSubscribers;
    } //-- void setDHCPSubscribers(String) 

    /**
     * Method setEXcludedRange
     * 
     * @param EXcludedRange
     */
    public void setEXcludedRange(String EXcludedRange)
    {
        this.m_EXcludedRange = EXcludedRange;
    } //-- void setEXcludedRange(String) 

    /**
     * Method setIdentityValue
     * 
     * @param name
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid name)
    {
        this.m_name = (com.calix.system.server.dbmodel.ICMSAid)name;
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
     * Method setavailClients
     * 
     * @param availClients
     */
    public void setavailClients(Integer availClients)
    {
        this.m_availClients = availClients;
    } //-- void setavailClients(Integer) 

    /**
     * Method setcmsServer
     * 
     * @param cmsServer
     */
    public void setcmsServer(String cmsServer)
    {
        this.m_cmsServer = cmsServer;
    } //-- void setcmsServer(String) 

    /**
     * Method setdefaultRouter
     * 
     * @param defaultRouter
     */
    public void setdefaultRouter(String defaultRouter)
    {
        this.m_defaultRouter = defaultRouter;
    } //-- void setdefaultRouter(String) 

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
     * Method setmaxClients
     * 
     * @param maxClients
     */
    public void setmaxClients(Integer maxClients)
    {
        this.m_maxClients = maxClients;
    } //-- void setmaxClients(Integer) 

    /**
     * Method setname
     * 
     * @param name
     */
    public void setname(com.calix.system.server.dbmodel.ICMSAid name)
    {
        this.m_name = name;
    } //-- void setname(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setnetworkAddress
     * 
     * @param networkAddress
     */
    public void setnetworkAddress(String networkAddress)
    {
        this.m_networkAddress = networkAddress;
    } //-- void setnetworkAddress(String) 

    /**
     * Method setnetworkMask
     * 
     * @param networkMask
     */
    public void setnetworkMask(String networkMask)
    {
        this.m_networkMask = networkMask;
    } //-- void setnetworkMask(String) 

    /**
     * Method setnumClients
     * 
     * @param numClients
     */
    public void setnumClients(Integer numClients)
    {
        this.m_numClients = numClients;
    } //-- void setnumClients(Integer) 

    /**
     * Method settftpServer
     * 
     * @param tftpServer
     */
    public void settftpServer(String tftpServer)
    {
        this.m_tftpServer = tftpServer;
    } //-- void settftpServer(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6AONTDHCPProfile ) {
            super.updateFields(obj1);
            B6AONTDHCPProfile obj = (B6AONTDHCPProfile)obj1;
           if (obj.getDHCPSubscribers() != null )
               setDHCPSubscribers((String)Helper.copy(obj.getDHCPSubscribers()));
           if (obj.getEXcludedRange() != null )
               setEXcludedRange((String)Helper.copy(obj.getEXcludedRange()));
           if (obj.getavailClients() != null )
               setavailClients((Integer)Helper.copy(obj.getavailClients()));
           if (obj.getcmsServer() != null )
               setcmsServer((String)Helper.copy(obj.getcmsServer()));
           if (obj.getdefaultRouter() != null )
               setdefaultRouter((String)Helper.copy(obj.getdefaultRouter()));
           if (obj.getdescription() != null )
               setdescription((String)Helper.copy(obj.getdescription()));
           if (obj.getmaxClients() != null )
               setmaxClients((Integer)Helper.copy(obj.getmaxClients()));
           if (obj.getname() != null )
               setname((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getname()));
           if (obj.getnetworkAddress() != null )
               setnetworkAddress((String)Helper.copy(obj.getnetworkAddress()));
           if (obj.getnetworkMask() != null )
               setnetworkMask((String)Helper.copy(obj.getnetworkMask()));
           if (obj.getnumClients() != null )
               setnumClients((Integer)Helper.copy(obj.getnumClients()));
           if (obj.gettftpServer() != null )
               settftpServer((String)Helper.copy(obj.gettftpServer()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
// BEGIN CODE
   
    public void setdhcpSubscribers(byte[] DHCPSubscribers)
    {
        if(DHCPSubscribers != null)
        	this.m_DHCPSubscribers = new String(DHCPSubscribers);
        else
        	this.m_DHCPSubscribers = null;
    } //-- void setDHCPSubscribers(String) 

    /**
     * Method setEXcludedRange
     * 
     * @param EXcludedRange
     */
    public void setexcludedRange(byte[] EXcludedRange)
    {
    	 if(EXcludedRange != null)
         	this.m_EXcludedRange = new String(EXcludedRange);
         else
         	this.m_EXcludedRange = null;
    } //-- void setEXcludedRange(String) 

    public byte[] getdhcpSubscribers()
    {
        if(this.m_DHCPSubscribers != null)
        	return this.m_DHCPSubscribers .getBytes();
        else
        	return null;
    } //-- void setDHCPSubscribers(String) 

    /**
     * Method setEXcludedRange
     * 
     * @param EXcludedRange
     */
    public byte[] getexcludedRange()
    {
    	 if(this.m_EXcludedRange != null)
         	return this.m_EXcludedRange.getBytes();
         else
         	return null;
    } //-- void setEXcludedRange(String) 
    
   
	public void setconvertName(String convertName) {
		this.m_name = new EMSAid(convertName);
	} // -- void setconvertName(String)

	public String getconvertName() {
		return this.m_name.toString();
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
