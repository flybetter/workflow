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
 * Class B6DHCPIpHost.
 * 
 * @version $Revision$ $Date$
 */
public class B6DHCPIpHost extends com.calix.system.server.dbmodel.CMSObject 
implements com.calix.ems.server.dbmodel.IEMSIpHost
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Calix Ip Host Identifier
     */
    public com.calix.system.server.dbmodel.ICMSAid m_ID;

    /**
     * Calix Ip Host Identifier
     */
    public transient com.calix.system.server.dbmodel.ICMSAid m_L2IFAID;

    /**
     * Field m_StrIpAddress
     */
    public String m_StrIpAddress;

    /**
     * Field m_StrL2IFAID
     */
    public String m_StrL2IFAID;

    /**
     * Field m_StrMacAddress
     */
    public String m_StrMacAddress;

    /**
     * Field m_StrNodeName
     */
    public String m_StrNodeName;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6DHCPIpHost";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6DHCPIpHost() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6DHCPIpHost()


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
        if( obj1 instanceof B6DHCPIpHost ) {
            super.copyFields(obj1);
            B6DHCPIpHost obj = (B6DHCPIpHost)obj1;
            setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
            setL2IFAID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getL2IFAID()));
            setStrIpAddress((String)Helper.copy(obj.getStrIpAddress()));
            setStrL2IFAID((String)Helper.copy(obj.getStrL2IFAID()));
            setStrMacAddress((String)Helper.copy(obj.getStrMacAddress()));
            setStrNodeName((String)Helper.copy(obj.getStrNodeName()));
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
     * Method getL2IFAID
     */
    public com.calix.system.server.dbmodel.ICMSAid getL2IFAID()
    {
        return this.m_L2IFAID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getL2IFAID() 

    /**
     * Method getStrIpAddress
     */
    public String getStrIpAddress()
    {
        return this.m_StrIpAddress;
    } //-- String getStrIpAddress() 

    /**
     * Method getStrL2IFAID
     */
    public String getStrL2IFAID()
    {
        return this.m_StrL2IFAID;
    } //-- String getStrL2IFAID() 

    /**
     * Method getStrMacAddress
     */
    public String getStrMacAddress()
    {
        return this.m_StrMacAddress;
    } //-- String getStrMacAddress() 

    /**
     * Method getStrNodeName
     */
    public String getStrNodeName()
    {
        return this.m_StrNodeName;
    } //-- String getStrNodeName() 

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
     * Method setL2IFAID
     * 
     * @param L2IFAID
     */
    public void setL2IFAID(com.calix.system.server.dbmodel.ICMSAid L2IFAID)
    {
        this.m_L2IFAID = L2IFAID;
    } //-- void setL2IFAID(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setStrIpAddress
     * 
     * @param StrIpAddress
     */
    public void setStrIpAddress(String StrIpAddress)
    {
        this.m_StrIpAddress = StrIpAddress;
    } //-- void setStrIpAddress(String) 

    /**
     * Method setStrL2IFAID
     * 
     * @param StrL2IFAID
     */
    public void setStrL2IFAID(String StrL2IFAID)
    {
        this.m_StrL2IFAID = StrL2IFAID;
    } //-- void setStrL2IFAID(String) 

    /**
     * Method setStrMacAddress
     * 
     * @param StrMacAddress
     */
    public void setStrMacAddress(String StrMacAddress)
    {
        this.m_StrMacAddress = StrMacAddress;
    } //-- void setStrMacAddress(String) 

    /**
     * Method setStrNodeName
     * 
     * @param StrNodeName
     */
    public void setStrNodeName(String StrNodeName)
    {
        this.m_StrNodeName = StrNodeName;
    } //-- void setStrNodeName(String) 

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
        if( obj1 instanceof B6DHCPIpHost ) {
            super.updateFields(obj1);
            B6DHCPIpHost obj = (B6DHCPIpHost)obj1;
           if (obj.getID() != null )
               setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
           if (obj.getL2IFAID() != null )
               setL2IFAID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getL2IFAID()));
           if (obj.getStrIpAddress() != null )
               setStrIpAddress((String)Helper.copy(obj.getStrIpAddress()));
           if (obj.getStrL2IFAID() != null )
               setStrL2IFAID((String)Helper.copy(obj.getStrL2IFAID()));
           if (obj.getStrMacAddress() != null )
               setStrMacAddress((String)Helper.copy(obj.getStrMacAddress()));
           if (obj.getStrNodeName() != null )
               setStrNodeName((String)Helper.copy(obj.getStrNodeName()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 

    // BEGIN CODE
    public long m_UID;
	@Override
	public long getUID() {
		return m_UID;
	}
	@Override
	public void setUID(long UID) {
		this.m_UID = UID;
	}
	
	public short getNetworkRef(){
		return getL2IFAID().getNetworkRef();
	}
	public void setNetworkRef(short networkRef){
		getL2IFAID().setNetworkRef(networkRef);
	}
	
	private String L2IFPortAid = null;

	@Override
	public String getL2IFPortAid() {
		return L2IFPortAid;
	}


	@Override
	public void setL2IFPortAid(String L2IFPortAid) {
		L2IFPortAid =L2IFPortAid;
		
	}
	
	public String toString(){
        StringBuffer sb = new StringBuffer();

        sb.append("ID=").append(getID());
        sb.append(", objClass=").append(getObjectClass());
        sb.append(", StrL2IFAID=").append(getStrL2IFAID());
        sb.append(", StrIpAddress=").append(getStrIpAddress());
        sb.append(", StrMacAddress=").append(getStrMacAddress());
        sb.append(", StrNodeName=").append(getStrNodeName());
        sb.append(", IdentityValue=").append(getIdentityValue());
        return sb.toString();
	}
	 // END CODE

}