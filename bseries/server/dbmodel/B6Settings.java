/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//BEGIN IMPORTS
import com.calix.bseries.server.ana.ANAService;
import com.calix.ems.core.CMSobjectRequestSignal;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.security.CMSDESCipher;
// END IMPORTS
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6Settings.
 * 
 * @version $Revision$ $Date$
 */
public class B6Settings extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Ana Socket Port
     */
    public Integer m_AnaSocketPort;

    /**
     * Ana Socket Protocol
     */
    public Integer m_AnaSocketProtocol;

    /**
     * Field m_B6SettingsAID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_B6SettingsAID;

    /**
     * CLI Password
     */
    public String m_CliPassword;

    /**
     * CLI Username
     */
    public String m_CliUsername;

    /**
     * Delete template log old older than .... days
     */
    public Integer m_DeleteTempLogForOld;

    /**
     * Enable Password
     */
    public String m_EnablePassword;

    /**
     * Default manifest file path
     */
    public String m_ManifestFilePath;

    /**
     * Prefix for user desc(for fiber/dsl)
     */
    public String m_SubDescPrefix;

    /**
     * Prefix for user desc(for gpon)
     */
    public String m_SubDescPrefixforGpon;

    /**
     * Prefix for sub Id(for fiber/dsl)
     */
    public String m_SubIdPrefix;

    /**
     * Prefix for sub Id(for gpon)
     */
    public String m_SubIdPrefixforGpon;

    /**
     * Web Password
     */
    public String m_WebPassword;

    /**
     * Web Username
     */
    public String m_WebUsername;

    /**
     * Default read community
     */
    public String m_readCommunity;

    /**
     * Default write community
     */
    public String m_writeCommunity;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6Settings";

    /**
     * new int[]{empty networkid, B6Settings}
     */
    public static int[] m_hierarchy = new int[]{0, 13235};

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6Settings() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6Settings()


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
        if( obj1 instanceof B6Settings ) {
            super.copyFields(obj1);
            B6Settings obj = (B6Settings)obj1;
            setAnaSocketPort((Integer)Helper.copy(obj.getAnaSocketPort()));
            setAnaSocketProtocol((Integer)Helper.copy(obj.getAnaSocketProtocol()));
            setB6SettingsAID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getB6SettingsAID()));
            setCliPassword((String)Helper.copy(obj.getCliPassword()));
            setCliUsername((String)Helper.copy(obj.getCliUsername()));
            setDeleteTempLogForOld((Integer)Helper.copy(obj.getDeleteTempLogForOld()));
            setEnablePassword((String)Helper.copy(obj.getEnablePassword()));
            setManifestFilePath((String)Helper.copy(obj.getManifestFilePath()));
            setSubDescPrefix((String)Helper.copy(obj.getSubDescPrefix()));
            setSubDescPrefixforGpon((String)Helper.copy(obj.getSubDescPrefixforGpon()));
            setSubIdPrefix((String)Helper.copy(obj.getSubIdPrefix()));
            setSubIdPrefixforGpon((String)Helper.copy(obj.getSubIdPrefixforGpon()));
            setWebPassword((String)Helper.copy(obj.getWebPassword()));
            setWebUsername((String)Helper.copy(obj.getWebUsername()));
            setreadCommunity((String)Helper.copy(obj.getreadCommunity()));
            setwriteCommunity((String)Helper.copy(obj.getwriteCommunity()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAnaSocketPort
     */
    public Integer getAnaSocketPort()
    {
        return this.m_AnaSocketPort;
    } //-- Integer getAnaSocketPort() 

    /**
     * Method getAnaSocketProtocol
     */
    public Integer getAnaSocketProtocol()
    {
        return this.m_AnaSocketProtocol;
    } //-- Integer getAnaSocketProtocol() 

    /**
     * Method getB6SettingsAID
     */
    public com.calix.system.server.dbmodel.ICMSAid getB6SettingsAID()
    {
        return this.m_B6SettingsAID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getB6SettingsAID() 

    /**
     * Method getCliPassword
     */
    public String getCliPassword()
    {
        //BEGIN_FUNCTION
        if (m_CliPassword == null && m_encryptedCliPassword != null){
            m_CliPassword = new String(CMSDESCipher.getInstance().decrypt(m_encryptedCliPassword));
        }
        return this.m_CliPassword;
      //END_FUNCTION
    } //-- String getCliPassword() 

    /**
     * Method getCliUsername
     */
    public String getCliUsername()
    {
        return this.m_CliUsername;
    } //-- String getCliUsername() 

    /**
     * Method getDeleteTempLogForOld
     */
    public Integer getDeleteTempLogForOld()
    {
        return this.m_DeleteTempLogForOld;
    } //-- Integer getDeleteTempLogForOld() 

    /**
     * Method getEnablePassword
     */
    public String getEnablePassword()
    {
    	//BEGIN_FUNCTION
        if(m_EnablePassword  == null && m_encryptedEnablePassword != null) {
        	m_EnablePassword  = new String(CMSDESCipher.getInstance().decrypt(m_encryptedEnablePassword));
        }
        return this.m_EnablePassword;
        //END_FUNCTION
    } //-- String getEnablePassword() 

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
        return this.m_B6SettingsAID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getManifestFilePath
     */
    public String getManifestFilePath()
    {
        return this.m_ManifestFilePath;
    } //-- String getManifestFilePath() 

    /**
     * Method getSubDescPrefix
     */
    public String getSubDescPrefix()
    {
        return this.m_SubDescPrefix;
    } //-- String getSubDescPrefix() 

    /**
     * Method getSubDescPrefixforGpon
     */
    public String getSubDescPrefixforGpon()
    {
        return this.m_SubDescPrefixforGpon;
    } //-- String getSubDescPrefixforGpon() 

    /**
     * Method getSubIdPrefix
     */
    public String getSubIdPrefix()
    {
        return this.m_SubIdPrefix;
    } //-- String getSubIdPrefix() 

    /**
     * Method getSubIdPrefixforGpon
     */
    public String getSubIdPrefixforGpon()
    {
        return this.m_SubIdPrefixforGpon;
    } //-- String getSubIdPrefixforGpon() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6Settings;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getWebPassword
     */
    public String getWebPassword()
    {
        //BEGIN_FUNCTION
        if (m_WebPassword == null && m_encryptedWebPassword != null){
            m_WebPassword = new String(CMSDESCipher.getInstance().decrypt(m_encryptedWebPassword));
        }
        return this.m_WebPassword;
      //END_FUNCTION
    } //-- String getWebPassword() 

    /**
     * Method getWebUsername
     */
    public String getWebUsername()
    {
        return this.m_WebUsername;
    } //-- String getWebUsername() 

    /**
     * Method getreadCommunity
     */
    public String getreadCommunity()
    {
        //BEGIN_FUNCTION
        if (m_readCommunity == null && m_encryptedReadCommunity != null){
            m_readCommunity = new String(CMSDESCipher.getInstance().decrypt(m_encryptedReadCommunity));
        }
        
        return this.m_readCommunity;
        //END_FUNCTION
    } //-- String getreadCommunity() 

    /**
     * Method getwriteCommunity
     */
    public String getwriteCommunity()
    {
        //BEGIN_FUNCTION
        if (m_writeCommunity == null && m_encryptedWriteCommunity != null){
            m_writeCommunity = new String(CMSDESCipher.getInstance().decrypt(m_encryptedWriteCommunity));
        }
        return this.m_writeCommunity;
      //END_FUNCTION
    } //-- String getwriteCommunity() 

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
            case 0x33F1:
                if (m_WebUsername == null) {
                    m_WebUsername = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F2:
                if (m_WebPassword == null) {
                    m_WebPassword = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F3:
                if (m_CliUsername == null) {
                    m_CliUsername = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F4:
                if (m_CliPassword == null) {
                    m_CliPassword = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F5:
                if (m_readCommunity == null) {
                    m_readCommunity = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F6:
                if (m_writeCommunity == null) {
                    m_writeCommunity = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3413:
                if (m_ManifestFilePath == null) {
                    m_ManifestFilePath = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34FA:
                if (m_SubIdPrefix == null) {
                    m_SubIdPrefix = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34FB:
                if (m_SubDescPrefix == null) {
                    m_SubDescPrefix = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34FC:
                if (m_SubIdPrefixforGpon == null) {
                    m_SubIdPrefixforGpon = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34FD:
                if (m_SubDescPrefixforGpon == null) {
                    m_SubDescPrefixforGpon = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3521:
                if (m_DeleteTempLogForOld == null) {
                    m_DeleteTempLogForOld = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3534:
                if (m_AnaSocketProtocol == null) {
                    m_AnaSocketProtocol = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3535:
                if (m_AnaSocketPort == null) {
                    m_AnaSocketPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3561:
                if (m_EnablePassword == null) {
                    m_EnablePassword = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3535, m_AnaSocketPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x3534, m_AnaSocketProtocol);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F4, m_CliPassword);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F3, m_CliUsername);
        TLVHelper.addEmbeddedTLV(tlv, 0x3521, m_DeleteTempLogForOld);
        TLVHelper.addEmbeddedTLV(tlv, 0x3561, m_EnablePassword);
        TLVHelper.addEmbeddedTLV(tlv, 0x3413, m_ManifestFilePath);
        TLVHelper.addEmbeddedTLV(tlv, 0x34FB, m_SubDescPrefix);
        TLVHelper.addEmbeddedTLV(tlv, 0x34FD, m_SubDescPrefixforGpon);
        TLVHelper.addEmbeddedTLV(tlv, 0x34FA, m_SubIdPrefix);
        TLVHelper.addEmbeddedTLV(tlv, 0x34FC, m_SubIdPrefixforGpon);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F2, m_WebPassword);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F1, m_WebUsername);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F5, m_readCommunity);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F6, m_writeCommunity);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAnaSocketPort
     * 
     * @param AnaSocketPort
     */
    public void setAnaSocketPort(Integer AnaSocketPort)
    {
        this.m_AnaSocketPort = AnaSocketPort;
    } //-- void setAnaSocketPort(Integer) 

    /**
     * Method setAnaSocketProtocol
     * 
     * @param AnaSocketProtocol
     */
    public void setAnaSocketProtocol(Integer AnaSocketProtocol)
    {
        this.m_AnaSocketProtocol = AnaSocketProtocol;
    } //-- void setAnaSocketProtocol(Integer) 

    /**
     * Method setB6SettingsAID
     * 
     * @param B6SettingsAID
     */
    public void setB6SettingsAID(com.calix.system.server.dbmodel.ICMSAid B6SettingsAID)
    {
        this.m_B6SettingsAID = B6SettingsAID;
    } //-- void setB6SettingsAID(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setCliPassword
     * 
     * @param CliPassword
     */
    public void setCliPassword(String CliPassword)
    {
        //BEGIN_FUNCTION
        this.m_CliPassword = CliPassword;
        if (m_CliPassword != null){
            setEncryptedCliPassword(CMSDESCipher.getInstance().encrypt(m_CliPassword.getBytes()));
        }
        //END_FUNCTION
    } //-- void setCliPassword(String) 

    /**
     * Method setCliUsername
     * 
     * @param CliUsername
     */
    public void setCliUsername(String CliUsername)
    {
        this.m_CliUsername = CliUsername;
    } //-- void setCliUsername(String) 

    /**
     * Method setDeleteTempLogForOld
     * 
     * @param DeleteTempLogForOld
     */
    public void setDeleteTempLogForOld(Integer DeleteTempLogForOld)
    {
        this.m_DeleteTempLogForOld = DeleteTempLogForOld;
    } //-- void setDeleteTempLogForOld(Integer) 

    /**
     * Method setEnablePassword
     * 
     * @param EnablePassword
     */
    public void setEnablePassword(String EnablePassword)
    {
        this.m_EnablePassword = EnablePassword;
        if (m_EnablePassword != null){
            setEncryptedEnablePassword(CMSDESCipher.getInstance().encrypt(m_EnablePassword.getBytes()));
        }
    } //-- void setEnablePassword(String) 

    /**
     * Method setIdentityValue
     * 
     * @param B6SettingsAID
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid B6SettingsAID)
    {
        this.m_B6SettingsAID = (com.calix.system.server.dbmodel.ICMSAid)B6SettingsAID;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setManifestFilePath
     * 
     * @param ManifestFilePath
     */
    public void setManifestFilePath(String ManifestFilePath)
    {
        this.m_ManifestFilePath = ManifestFilePath;
    } //-- void setManifestFilePath(String) 

    /**
     * Method setSubDescPrefix
     * 
     * @param SubDescPrefix
     */
    public void setSubDescPrefix(String SubDescPrefix)
    {
        this.m_SubDescPrefix = SubDescPrefix;
    } //-- void setSubDescPrefix(String) 

    /**
     * Method setSubDescPrefixforGpon
     * 
     * @param SubDescPrefixforGpon
     */
    public void setSubDescPrefixforGpon(String SubDescPrefixforGpon)
    {
        this.m_SubDescPrefixforGpon = SubDescPrefixforGpon;
    } //-- void setSubDescPrefixforGpon(String) 

    /**
     * Method setSubIdPrefix
     * 
     * @param SubIdPrefix
     */
    public void setSubIdPrefix(String SubIdPrefix)
    {
        this.m_SubIdPrefix = SubIdPrefix;
    } //-- void setSubIdPrefix(String) 

    /**
     * Method setSubIdPrefixforGpon
     * 
     * @param SubIdPrefixforGpon
     */
    public void setSubIdPrefixforGpon(String SubIdPrefixforGpon)
    {
        this.m_SubIdPrefixforGpon = SubIdPrefixforGpon;
    } //-- void setSubIdPrefixforGpon(String) 

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
     * Method setWebPassword
     * 
     * @param WebPassword
     */
    public void setWebPassword(String WebPassword)
    {
        //BEGIN_FUNCTION
        this.m_WebPassword = WebPassword;
        if (m_WebPassword != null){
            setEncryptedWebPassword(CMSDESCipher.getInstance().encrypt(m_WebPassword.getBytes()));
        }
        //END_FUNCTION
    } //-- void setWebPassword(String) 

    /**
     * Method setWebUsername
     * 
     * @param WebUsername
     */
    public void setWebUsername(String WebUsername)
    {
        this.m_WebUsername = WebUsername;
    } //-- void setWebUsername(String) 

    /**
     * Method setreadCommunity
     * 
     * @param readCommunity
     */
    public void setreadCommunity(String readCommunity)
    {
        //BEGIN_FUNCTION
        this.m_readCommunity = readCommunity;
        if (m_readCommunity != null){
            setEncryptedReadCommunity(CMSDESCipher.getInstance().encrypt(m_readCommunity.getBytes()));
        }
        //END_FUNCTION
    } //-- void setreadCommunity(String) 

    /**
     * Method setwriteCommunity
     * 
     * @param writeCommunity
     */
    public void setwriteCommunity(String writeCommunity)
    {
        //BEGIN_FUNCTION
        this.m_writeCommunity = writeCommunity;
        if (m_writeCommunity != null){
            setEncryptedWriteCommunity(CMSDESCipher.getInstance().encrypt(m_writeCommunity.getBytes()));
        }
        //END_FUNCTION
    } //-- void setwriteCommunity(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6Settings ) {
            super.updateFields(obj1);
            B6Settings obj = (B6Settings)obj1;
           if (obj.getAnaSocketPort() != null )
               setAnaSocketPort((Integer)Helper.copy(obj.getAnaSocketPort()));
           if (obj.getAnaSocketProtocol() != null )
               setAnaSocketProtocol((Integer)Helper.copy(obj.getAnaSocketProtocol()));
           if (obj.getB6SettingsAID() != null )
               setB6SettingsAID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getB6SettingsAID()));
           if (obj.getCliPassword() != null )
               setCliPassword((String)Helper.copy(obj.getCliPassword()));
           if (obj.getCliUsername() != null )
               setCliUsername((String)Helper.copy(obj.getCliUsername()));
           if (obj.getDeleteTempLogForOld() != null )
               setDeleteTempLogForOld((Integer)Helper.copy(obj.getDeleteTempLogForOld()));
           if (obj.getEnablePassword() != null )
               setEnablePassword((String)Helper.copy(obj.getEnablePassword()));
           if (obj.getManifestFilePath() != null )
               setManifestFilePath((String)Helper.copy(obj.getManifestFilePath()));
           if (obj.getSubDescPrefix() != null )
               setSubDescPrefix((String)Helper.copy(obj.getSubDescPrefix()));
           if (obj.getSubDescPrefixforGpon() != null )
               setSubDescPrefixforGpon((String)Helper.copy(obj.getSubDescPrefixforGpon()));
           if (obj.getSubIdPrefix() != null )
               setSubIdPrefix((String)Helper.copy(obj.getSubIdPrefix()));
           if (obj.getSubIdPrefixforGpon() != null )
               setSubIdPrefixforGpon((String)Helper.copy(obj.getSubIdPrefixforGpon()));
           if (obj.getWebPassword() != null )
               setWebPassword((String)Helper.copy(obj.getWebPassword()));
           if (obj.getWebUsername() != null )
               setWebUsername((String)Helper.copy(obj.getWebUsername()));
           if (obj.getreadCommunity() != null )
               setreadCommunity((String)Helper.copy(obj.getreadCommunity()));
           if (obj.getwriteCommunity() != null )
               setwriteCommunity((String)Helper.copy(obj.getwriteCommunity()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
    // BEGIN CODE
    private byte [] m_encryptedWebPassword;
    private byte [] m_encryptedCliPassword;
    private byte [] m_encryptedReadCommunity;
    private byte [] m_encryptedWriteCommunity;
    
    private byte [] m_encryptedEnablePassword;
    
    public byte [] getEncryptedEnablePassword() {
        return m_encryptedEnablePassword;
    }
    public void setEncryptedEnablePassword(byte [] encryptedEnalePassword) {
    	this.m_encryptedEnablePassword = encryptedEnalePassword;
    }

    
    public byte[] getEncryptedWebPassword() {
        return m_encryptedWebPassword;
    }
    
    public void dbUpdate(DbTransaction tx) throws EMSDatabaseException {
    	super.dbUpdate(tx); 
    }
    
    @Override
    public void postRequest(int requestType) {
    	super.postRequest(requestType);
    	switch(requestType){
        case CMSobjectRequestSignal.UPDATE:
        	final B6Settings settings=this;
             Thread t = new Thread(new Runnable() {
                 public void run() {
                	 ANAService.restartSocketService(settings);                 
                }
             });
             t.start();
            break;
    	 }	      
    }
    
    public void setEncryptedWebPassword(byte[] m_encryptedWebPassword) {
        this.m_encryptedWebPassword = m_encryptedWebPassword;
    }


    public byte[] getEncryptedCliPassword() {
        return m_encryptedCliPassword;
    }


    public void setEncryptedCliPassword(byte[] m_encryptedCliPassword) {
        this.m_encryptedCliPassword = m_encryptedCliPassword;
    }


    public byte[] getEncryptedReadCommunity() {
        return m_encryptedReadCommunity;
    }


    public void setEncryptedReadCommunity(byte[] m_encryptedReadCommunity) {
        this.m_encryptedReadCommunity = m_encryptedReadCommunity;
    }


    public byte[] getEncryptedWriteCommunity() {
        return m_encryptedWriteCommunity;
    }


    public void setEncryptedWriteCommunity(byte[] m_encryptedWriteCommunity) {
        this.m_encryptedWriteCommunity = m_encryptedWriteCommunity;
    }
    
    // END CODE
}