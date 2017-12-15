/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/


import org.apache.commons.lang.StringUtils;

import com.calix.bseries.server.ana.ANAService;
import com.calix.ems.core.CMSobjectRequestSignal;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6ProxySettings.
 * 
 * @version $Revision$ $Date$
 */
public class B6ProxySettings extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_B6ProxySettingsAID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_B6ProxySettingsAID;

    /**
     * IpAddress1
     */
    public String m_IpAddress1;

    /**
     * IpAddress2
     */
    public String m_IpAddress2;

    /**
     * IpAddres3
     */
    public String m_IpAddress3;

    /**
     * Is Proxy Server
     */
    public Integer m_IsProxyServer;

    /**
     * Port1
     */
    public String m_Port1;

    /**
     * Port2
     */
    public String m_Port2;

    /**
     * Port3
     */
    public String m_Port3;

    /**
     * Port1
     */
    public String m_Status1;

    /**
     * Port1
     */
    public String m_Status2;

    /**
     * Port1
     */
    public String m_Status3;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6ProxySettings";

    /**
     * new int[]{empty networkid, B6ProxySettings}
     */
    public static int[] m_hierarchy = new int[]{0, 13640};

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6ProxySettings() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6ProxySettings()


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
        if( obj1 instanceof B6ProxySettings ) {
            super.copyFields(obj1);
            B6ProxySettings obj = (B6ProxySettings)obj1;
            setB6ProxySettingsAID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getB6ProxySettingsAID()));
            setIpAddress1((String)Helper.copy(obj.getIpAddress1()));
            setIpAddress2((String)Helper.copy(obj.getIpAddress2()));
            setIpAddress3((String)Helper.copy(obj.getIpAddress3()));
            setIsProxyServer((Integer)Helper.copy(obj.getIsProxyServer()));
            setPort1((String)Helper.copy(obj.getPort1()));
            setPort2((String)Helper.copy(obj.getPort2()));
            setPort3((String)Helper.copy(obj.getPort3()));
            setStatus1((String)Helper.copy(obj.getStatus1()));
            setStatus2((String)Helper.copy(obj.getStatus2()));
            setStatus3((String)Helper.copy(obj.getStatus3()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getB6ProxySettingsAID
     */
    public com.calix.system.server.dbmodel.ICMSAid getB6ProxySettingsAID()
    {
        return this.m_B6ProxySettingsAID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getB6ProxySettingsAID() 

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
        return this.m_B6ProxySettingsAID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getIpAddress1
     */
    public String getIpAddress1()
    {
        return this.m_IpAddress1;
    } //-- String getIpAddress1() 

    /**
     * Method getIpAddress2
     */
    public String getIpAddress2()
    {
        return this.m_IpAddress2;
    } //-- String getIpAddress2() 

    /**
     * Method getIpAddress3
     */
    public String getIpAddress3()
    {
        return this.m_IpAddress3;
    } //-- String getIpAddress3() 

    /**
     * Method getIsProxyServer
     */
    public Integer getIsProxyServer()
    {
        return this.m_IsProxyServer;
    } //-- Integer getIsProxyServer() 

    /**
     * Method getPort1
     */
    public String getPort1()
    {
        return this.m_Port1;
    } //-- String getPort1() 

    /**
     * Method getPort2
     */
    public String getPort2()
    {
        return this.m_Port2;
    } //-- String getPort2() 

    /**
     * Method getPort3
     */
    public String getPort3()
    {
        return this.m_Port3;
    } //-- String getPort3() 

    /**
     * Method getStatus1
     */
    public String getStatus1()
    {
        return this.m_Status1;
    } //-- String getStatus1() 

    /**
     * Method getStatus2
     */
    public String getStatus2()
    {
        return this.m_Status2;
    } //-- String getStatus2() 

    /**
     * Method getStatus3
     */
    public String getStatus3()
    {
        return this.m_Status3;
    } //-- String getStatus3() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6ProxySettings;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

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
            case 0x354A:
                if (m_IsProxyServer == null) {
                    m_IsProxyServer = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x354B:
                if (m_IpAddress1 == null) {
                    m_IpAddress1 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x354C:
                if (m_Port1 == null) {
                    m_Port1 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x354D:
                if (m_Status1 == null) {
                    m_Status1 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x354E:
                if (m_IpAddress2 == null) {
                    m_IpAddress2 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x354F:
                if (m_Port2 == null) {
                    m_Port2 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3550:
                if (m_Status2 == null) {
                    m_Status2 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3551:
                if (m_IpAddress3 == null) {
                    m_IpAddress3 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3552:
                if (m_Port3 == null) {
                    m_Port3 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3553:
                if (m_Status3 == null) {
                    m_Status3 = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x354B, m_IpAddress1);
        TLVHelper.addEmbeddedTLV(tlv, 0x354E, m_IpAddress2);
        TLVHelper.addEmbeddedTLV(tlv, 0x3551, m_IpAddress3);
        TLVHelper.addEmbeddedTLV(tlv, 0x354A, m_IsProxyServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x354C, m_Port1);
        TLVHelper.addEmbeddedTLV(tlv, 0x354F, m_Port2);
        TLVHelper.addEmbeddedTLV(tlv, 0x3552, m_Port3);
        TLVHelper.addEmbeddedTLV(tlv, 0x354D, m_Status1);
        TLVHelper.addEmbeddedTLV(tlv, 0x3550, m_Status2);
        TLVHelper.addEmbeddedTLV(tlv, 0x3553, m_Status3);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setB6ProxySettingsAID
     * 
     * @param B6ProxySettingsAID
     */
    public void setB6ProxySettingsAID(com.calix.system.server.dbmodel.ICMSAid B6ProxySettingsAID)
    {
        this.m_B6ProxySettingsAID = B6ProxySettingsAID;
    } //-- void setB6ProxySettingsAID(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setIdentityValue
     * 
     * @param B6ProxySettingsAID
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid B6ProxySettingsAID)
    {
        this.m_B6ProxySettingsAID = (com.calix.system.server.dbmodel.ICMSAid)B6ProxySettingsAID;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setIpAddress1
     * 
     * @param IpAddress1
     */
    public void setIpAddress1(String IpAddress1)
    {
        this.m_IpAddress1 = IpAddress1;
    } //-- void setIpAddress1(String) 

    /**
     * Method setIpAddress2
     * 
     * @param IpAddress2
     */
    public void setIpAddress2(String IpAddress2)
    {
        this.m_IpAddress2 = IpAddress2;
    } //-- void setIpAddress2(String) 

    /**
     * Method setIpAddress3
     * 
     * @param IpAddress3
     */
    public void setIpAddress3(String IpAddress3)
    {
        this.m_IpAddress3 = IpAddress3;
    } //-- void setIpAddress3(String) 

    /**
     * Method setIsProxyServer
     * 
     * @param IsProxyServer
     */
    public void setIsProxyServer(Integer IsProxyServer)
    {
        this.m_IsProxyServer = IsProxyServer;
    } //-- void setIsProxyServer(Integer) 

    /**
     * Method setPort1
     * 
     * @param Port1
     */
    public void setPort1(String Port1)
    {
        this.m_Port1 = Port1;
    } //-- void setPort1(String) 

    /**
     * Method setPort2
     * 
     * @param Port2
     */
    public void setPort2(String Port2)
    {
        this.m_Port2 = Port2;
    } //-- void setPort2(String) 

    /**
     * Method setPort3
     * 
     * @param Port3
     */
    public void setPort3(String Port3)
    {
        this.m_Port3 = Port3;
    } //-- void setPort3(String) 

    /**
     * Method setStatus1
     * 
     * @param Status1
     */
    public void setStatus1(String Status1)
    {
        this.m_Status1 = Status1;
    } //-- void setStatus1(String) 

    /**
     * Method setStatus2
     * 
     * @param Status2
     */
    public void setStatus2(String Status2)
    {
        this.m_Status2 = Status2;
    } //-- void setStatus2(String) 

    /**
     * Method setStatus3
     * 
     * @param Status3
     */
    public void setStatus3(String Status3)
    {
        this.m_Status3 = Status3;
    } //-- void setStatus3(String) 

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
        if( obj1 instanceof B6ProxySettings ) {
            super.updateFields(obj1);
            B6ProxySettings obj = (B6ProxySettings)obj1;
           if (obj.getB6ProxySettingsAID() != null )
               setB6ProxySettingsAID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getB6ProxySettingsAID()));
           if (obj.getIpAddress1() != null )
               setIpAddress1((String)Helper.copy(obj.getIpAddress1()));
           if (obj.getIpAddress2() != null )
               setIpAddress2((String)Helper.copy(obj.getIpAddress2()));
           if (obj.getIpAddress3() != null )
               setIpAddress3((String)Helper.copy(obj.getIpAddress3()));
           if (obj.getIsProxyServer() != null )
               setIsProxyServer((Integer)Helper.copy(obj.getIsProxyServer()));
           if (obj.getPort1() != null )
               setPort1((String)Helper.copy(obj.getPort1()));
           if (obj.getPort2() != null )
               setPort2((String)Helper.copy(obj.getPort2()));
           if (obj.getPort3() != null )
               setPort3((String)Helper.copy(obj.getPort3()));
           if (obj.getStatus1() != null )
               setStatus1((String)Helper.copy(obj.getStatus1()));
           if (obj.getStatus2() != null )
               setStatus2((String)Helper.copy(obj.getStatus2()));
           if (obj.getStatus3() != null )
               setStatus3((String)Helper.copy(obj.getStatus3()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
 // BEGIN CODE
	public void dbUpdate(DbTransaction tx) throws EMSDatabaseException {
		if(StringUtils.isEmpty(getIpAddress1())){
			setStatus1("");
		}
		if(StringUtils.isEmpty(getIpAddress2())){
			setStatus2("");
		}
		if(StringUtils.isEmpty(getIpAddress3())){
			setStatus3("");
		}
		super.dbUpdate(tx); 
	}
	
    @Override
    public void postRequest(int requestType) {
    	super.postRequest(requestType);
    	if(updateFromANAProcess){
    		updateFromANAProcess=false;
    		return;
    	}
    	switch(requestType){
        case CMSobjectRequestSignal.UPDATE:
        	final B6ProxySettings settings = this;
             Thread t = new Thread(new Runnable() {
                 public void run() {
             		//ANAService.changeProxySettings(settings);
                }
             });
             t.start();
            break;
    	 }	      
    }
    private boolean updateFromANAProcess=false;
    
    public void setUpdateFromANAProcess(boolean flg){
    	this.updateFromANAProcess=flg;
    }
    
    protected boolean isUpdateFromANAProcess() {
        return updateFromANAProcess;
    }
 // END CODE
}
