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
import com.calix.system.server.dbmodel.ICMSAid;

/**
 * Class B6AONTProfile.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTProfile extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CVlan
     */
    public Integer m_CVlan;

    /**
     * Field m_CallAgent
     */
    public String m_CallAgent;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_DigitMap
     */
    public String m_DigitMap;

    /**
     * Field m_DigitTimeOut
     */
    public Integer m_DigitTimeOut;

    /**
     * Field m_EndpointIP
     */
    public Integer m_EndpointIP;

    /**
     * Field m_EndpointPrefix
     */
    public String m_EndpointPrefix;

    /**
     * Field m_Explicitdetection
     */
    public Integer m_Explicitdetection;

    /**
     * Field m_Flashhook
     */
    public Integer m_Flashhook;

    /**
     * Field m_FullBridge
     */
    public Integer m_FullBridge;

    /**
     * Field m_ImageName
     */
    public String m_ImageName;

    /**
     * Field m_Model
     */
    public String m_Model;

    /**
     * Field m_NTPServer
     */
    public String m_NTPServer;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_NcsMode2833
     */
    public Integer m_NcsMode2833;

    /**
     * Field m_NspPrimaryRx
     */
    public Integer m_NspPrimaryRx;

    /**
     * Field m_NspSecondaryRx
     */
    public Integer m_NspSecondaryRx;

    /**
     * Field m_OccamSingleContext
     */
    public Integer m_OccamSingleContext;

    /**
     * Field m_Offhook
     */
    public Integer m_Offhook;

    /**
     * Field m_Onhook
     */
    public Integer m_Onhook;

    /**
     * Field m_Port
     */
    public Integer m_Port;

    /**
     * Field m_ProtocolService
     */
    public Integer m_ProtocolService;

    /**
     * Field m_Proxy
     */
    public String m_Proxy;

    /**
     * Field m_RegTimeout
     */
    public Integer m_RegTimeout;

    /**
     * Field m_Registrar
     */
    public String m_Registrar;

    /**
     * Field m_RestartDelay
     */
    public Integer m_RestartDelay;

    /**
     * Field m_RetryTimeOut
     */
    public Integer m_RetryTimeOut;

    /**
     * Field m_SecondaryCallPort
     */
    public Integer m_SecondaryCallPort;

    /**
     * Field m_SecondaryProxyIP
     */
    public String m_SecondaryProxyIP;

    /**
     * Field m_SecondaryRegistrarIP
     */
    public String m_SecondaryRegistrarIP;

    /**
     * Field m_Secondarycallagent
     */
    public String m_Secondarycallagent;

    /**
     * Field m_Security
     */
    public Integer m_Security;

    /**
     * Field m_ServicePackage_ontprofile
     */
    public String m_ServicePackage_ontprofile;

    /**
     * Field m_SIPDomain
     */
    public String m_SIPDomain;

    /**

    /**
     * Field m_SnmpTrapHost
     */
    public String m_SnmpTrapHost;

    /**
     * Field m_Snmprocommunity
     */
    public String m_Snmprocommunity;

    /**
     * Field m_Snmprwcommunity
     */
    public String m_Snmprwcommunity;

    /**
     * Field m_SysLogServer
     */
    public String m_SysLogServer;

    /**
     * Field m_TimeZone
     */
    public String m_TimeZone;

    /**
     * Field m_Transport
     */
    public Integer m_Transport;

    /**
     * Field m_Version
     */
    public Integer m_Version;

    /**
     * Field m_VoiceBVI
     */
    public Integer m_VoiceBVI;

    /**
     * Field m_VoiceMode
     */
    public Integer m_VoiceMode;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTProfile";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTProfile() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTProfile()


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
        if( obj1 instanceof B6AONTProfile ) {
            super.copyFields(obj1);
            B6AONTProfile obj = (B6AONTProfile)obj1;
            setCVlan((Integer)Helper.copy(obj.getCVlan()));
            setCallAgent((String)Helper.copy(obj.getCallAgent()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setDigitMap((String)Helper.copy(obj.getDigitMap()));
            setDigitTimeOut((Integer)Helper.copy(obj.getDigitTimeOut()));
            setEndpointIP((Integer)Helper.copy(obj.getEndpointIP()));
            setEndpointPrefix((String)Helper.copy(obj.getEndpointPrefix()));
            setExplicitdetection((Integer)Helper.copy(obj.getExplicitdetection()));
            setFlashhook((Integer)Helper.copy(obj.getFlashhook()));
            setFullBridge((Integer)Helper.copy(obj.getFullBridge()));
            setImageName((String)Helper.copy(obj.getImageName()));
            setModel((String)Helper.copy(obj.getModel()));
            setNTPServer((String)Helper.copy(obj.getNTPServer()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setNcsMode2833((Integer)Helper.copy(obj.getNcsMode2833()));
            setNspPrimaryRx((Integer)Helper.copy(obj.getNspPrimaryRx()));
            setNspSecondaryRx((Integer)Helper.copy(obj.getNspSecondaryRx()));
            setOccamSingleContext((Integer)Helper.copy(obj.getOccamSingleContext()));
            setOffhook((Integer)Helper.copy(obj.getOffhook()));
            setOnhook((Integer)Helper.copy(obj.getOnhook()));
            setPort((Integer)Helper.copy(obj.getPort()));
            setProtocolService((Integer)Helper.copy(obj.getProtocolService()));
            setProxy((String)Helper.copy(obj.getProxy()));
            setRegTimeout((Integer)Helper.copy(obj.getRegTimeout()));
            setRegistrar((String)Helper.copy(obj.getRegistrar()));
            setRestartDelay((Integer)Helper.copy(obj.getRestartDelay()));
            setRetryTimeOut((Integer)Helper.copy(obj.getRetryTimeOut()));
            setSecondaryCallPort((Integer)Helper.copy(obj.getSecondaryCallPort()));
            setSecondaryProxyIP((String)Helper.copy(obj.getSecondaryProxyIP()));
            setSecondaryRegistrarIP((String)Helper.copy(obj.getSecondaryRegistrarIP()));
            setSecondarycallagent((String)Helper.copy(obj.getSecondarycallagent()));
            setSecurity((Integer)Helper.copy(obj.getSecurity()));
            setServicePackage_ontprofile((String)Helper.copy(obj.getServicePackage_ontprofile()));
            setSIPDomain((String)Helper.copy(obj.getSIPDomain()));
            setSnmpTrapHost((String)Helper.copy(obj.getSnmpTrapHost()));
            setSnmprocommunity((String)Helper.copy(obj.getSnmprocommunity()));
            setSnmprwcommunity((String)Helper.copy(obj.getSnmprwcommunity()));
            setSysLogServer((String)Helper.copy(obj.getSysLogServer()));
            setTimeZone((String)Helper.copy(obj.getTimeZone()));
            setTransport((Integer)Helper.copy(obj.getTransport()));
            setVersion((Integer)Helper.copy(obj.getVersion()));
            setVoiceBVI((Integer)Helper.copy(obj.getVoiceBVI()));
            setVoiceMode((Integer)Helper.copy(obj.getVoiceMode()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCVlan
     */
    public Integer getCVlan()
    {
        return this.m_CVlan;
    } //-- Integer getCVlan() 

    /**
     * Method getCallAgent
     */
    public String getCallAgent()
    {
        return this.m_CallAgent;
    } //-- String getCallAgent() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getDigitMap
     */
    public String getDigitMap()
    {
        return this.m_DigitMap;
    } //-- String getDigitMap() 

    /**
     * Method getDigitTimeOut
     */
    public Integer getDigitTimeOut()
    {
        return this.m_DigitTimeOut;
    } //-- Integer getDigitTimeOut() 

    /**
     * Method getEndpointIP
     */
    public Integer getEndpointIP()
    {
        return this.m_EndpointIP;
    } //-- Integer getEndpointIP() 

    /**
     * Method getEndpointPrefix
     */
    public String getEndpointPrefix()
    {
        return this.m_EndpointPrefix;
    } //-- String getEndpointPrefix() 

    /**
     * Method getExplicitdetection
     */
    public Integer getExplicitdetection()
    {
        return this.m_Explicitdetection;
    } //-- Integer getExplicitdetection() 

    /**
     * Method getFlashhook
     */
    public Integer getFlashhook()
    {
        return this.m_Flashhook;
    } //-- Integer getFlashhook() 

    /**
     * Method getFullBridge
     */
    public Integer getFullBridge()
    {
        return this.m_FullBridge;
    } //-- Integer getFullBridge() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getImageName
     */
    public String getImageName()
    {
        return this.m_ImageName;
    } //-- String getImageName() 

    /**
     * Method getModel
     */
    public String getModel()
    {
        return this.m_Model;
    } //-- String getModel() 

    /**
     * Method getNTPServer
     */
    public String getNTPServer()
    {
        return this.m_NTPServer;
    } //-- String getNTPServer() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

    /**
     * Method getNcsMode2833
     */
    public Integer getNcsMode2833()
    {
        return this.m_NcsMode2833;
    } //-- Integer getNcsMode2833() 

    /**
     * Method getNspPrimaryRx
     */
    public Integer getNspPrimaryRx()
    {
        return this.m_NspPrimaryRx;
    } //-- Integer getNspPrimaryRx() 

    /**
     * Method getNspSecondaryRx
     */
    public Integer getNspSecondaryRx()
    {
        return this.m_NspSecondaryRx;
    } //-- Integer getNspSecondaryRx() 

    /**
     * Method getOccamSingleContext
     */
    public Integer getOccamSingleContext()
    {
        return this.m_OccamSingleContext;
    } //-- Integer getOccamSingleContext() 

    /**
     * Method getOffhook
     */
    public Integer getOffhook()
    {
        return this.m_Offhook;
    } //-- Integer getOffhook() 

    /**
     * Method getOnhook
     */
    public Integer getOnhook()
    {
        return this.m_Onhook;
    } //-- Integer getOnhook() 

    /**
     * Method getPort
     */
    public Integer getPort()
    {
        return this.m_Port;
    } //-- Integer getPort() 

    /**
     * Method getProtocolService
     */
    public Integer getProtocolService()
    {
        return this.m_ProtocolService;
    } //-- Integer getProtocolService() 

    /**
     * Method getProxy
     */
    public String getProxy()
    {
        return this.m_Proxy;
    } //-- String getProxy() 

    /**
     * Method getRegTimeout
     */
    public Integer getRegTimeout()
    {
        return this.m_RegTimeout;
    } //-- Integer getRegTimeout() 

    /**
     * Method getRegistrar
     */
    public String getRegistrar()
    {
        return this.m_Registrar;
    } //-- String getRegistrar() 

    /**
     * Method getRestartDelay
     */
    public Integer getRestartDelay()
    {
        return this.m_RestartDelay;
    } //-- Integer getRestartDelay() 

    /**
     * Method getRetryTimeOut
     */
    public Integer getRetryTimeOut()
    {
        return this.m_RetryTimeOut;
    } //-- Integer getRetryTimeOut() 

    /**
     * Method getSecondaryCallPort
     */
    public Integer getSecondaryCallPort()
    {
        return this.m_SecondaryCallPort;
    } //-- Integer getSecondaryCallPort() 

    /**
     * Method getSecondaryProxyIP
     */
    public String getSecondaryProxyIP()
    {
        return this.m_SecondaryProxyIP;
    } //-- String getSecondaryProxyIP() 

    /**
     * Method getSecondaryRegistrarIP
     */
    public String getSecondaryRegistrarIP()
    {
        return this.m_SecondaryRegistrarIP;
    } //-- String getSecondaryRegistrarIP() 

    /**
     * Method getSecondarycallagent
     */
    public String getSecondarycallagent()
    {
        return this.m_Secondarycallagent;
    } //-- String getSecondarycallagent() 

    /**
     * Method getSecurity
     */
    public Integer getSecurity()
    {
        return this.m_Security;
    } //-- Integer getSecurity() 

    /**
     * Method getServicePackage_ontprofile
     */
    public String getServicePackage_ontprofile()
    {
        return this.m_ServicePackage_ontprofile;
    } //-- String getServicePackage_ontprofile() 

    /**
     * Method getSIPDomain
     */
    public String getSIPDomain()
    {
        return this.m_SIPDomain;
    } //-- String getSIPDomain() 

    /**
     * Method getSnmpTrapHost
     */
    public String getSnmpTrapHost()
    {
        return this.m_SnmpTrapHost;
    } //-- String getSnmpTrapHost() 

    /**
     * Method getSnmprocommunity
     */
    public String getSnmprocommunity()
    {
        return this.m_Snmprocommunity;
    } //-- String getSnmprocommunity() 

    /**
     * Method getSnmprwcommunity
     */
    public String getSnmprwcommunity()
    {
        return this.m_Snmprwcommunity;
    } //-- String getSnmprwcommunity() 

    /**
     * Method getSysLogServer
     */
    public String getSysLogServer()
    {
        return this.m_SysLogServer;
    } //-- String getSysLogServer() 

    /**
     * Method getTimeZone
     */
    public String getTimeZone()
    {
        return this.m_TimeZone;
    } //-- String getTimeZone() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6AONTProfile;
    } //-- int getTlvType() 

    /**
     * Method getTransport
     */
    public Integer getTransport()
    {
        return this.m_Transport;
    } //-- Integer getTransport() 

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
    public Integer getVersion()
    {
        return this.m_Version;
    } //-- Integer getVersion() 

    /**
     * Method getVoiceBVI
     */
    public Integer getVoiceBVI()
    {
        return this.m_VoiceBVI;
    } //-- Integer getVoiceBVI() 

    /**
     * Method getVoiceMode
     */
    public Integer getVoiceMode()
    {
        return this.m_VoiceMode;
    } //-- Integer getVoiceMode() 

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
            case 0x33D1:
                if (m_SnmpTrapHost == null) {
                    m_SnmpTrapHost = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D2:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D3:
                if (m_CallAgent == null) {
                    m_CallAgent = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D4:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D5:
                if (m_Proxy == null) {
                    m_Proxy = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D6:
                if (m_NspPrimaryRx == null) {
                    m_NspPrimaryRx = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D7:
                if (m_NspSecondaryRx == null) {
                    m_NspSecondaryRx = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D8:
                if (m_TimeZone == null) {
                    m_TimeZone = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D9:
                if (m_NTPServer == null) {
                    m_NTPServer = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DA:
                if (m_Registrar == null) {
                    m_Registrar = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DB:
                if (m_DigitMap == null) {
                    m_DigitMap = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DC:
                if (m_RegTimeout == null) {
                    m_RegTimeout = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DD:
                if (m_Model == null) {
                    m_Model = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DE:
                if (m_DigitTimeOut == null) {
                    m_DigitTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33DF:
                if (m_ServicePackage_ontprofile == null) {
                    m_ServicePackage_ontprofile = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E0:
                if (m_VoiceMode == null) {
                    m_VoiceMode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E1:
                if (m_Secondarycallagent == null) {
                    m_Secondarycallagent = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E2:
                if (m_ProtocolService == null) {
                    m_ProtocolService = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E3:
                if (m_NcsMode2833 == null) {
                    m_NcsMode2833 = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E4:
                if (m_EndpointIP == null) {
                    m_EndpointIP = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E5:
                if (m_EndpointPrefix == null) {
                    m_EndpointPrefix = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E6:
                if (m_SecondaryCallPort == null) {
                    m_SecondaryCallPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E7:
                if (m_RetryTimeOut == null) {
                    m_RetryTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E8:
                if (m_RestartDelay == null) {
                    m_RestartDelay = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E9:
                if (m_Security == null) {
                    m_Security = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33EA:
                if (m_Explicitdetection == null) {
                    m_Explicitdetection = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33EB:
                if (m_VoiceBVI == null) {
                    m_VoiceBVI = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33EC:
                if (m_OccamSingleContext == null) {
                    m_OccamSingleContext = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33ED:
                if (m_Version == null) {
                    m_Version = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33EE:
                if (m_Flashhook == null) {
                    m_Flashhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33EF:
                if (m_ImageName == null) {
                    m_ImageName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F0:
                if (m_Offhook == null) {
                    m_Offhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F1:
                if (m_SecondaryProxyIP == null) {
                    m_SecondaryProxyIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F2:
                if (m_Onhook == null) {
                    m_Onhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F3:
                if (m_SecondaryRegistrarIP == null) {
                    m_SecondaryRegistrarIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F4:
                if (m_Transport == null) {
                    m_Transport = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F5:
                if (m_Snmprocommunity == null) {
                    m_Snmprocommunity = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F6:
                if (m_Snmprwcommunity == null) {
                    m_Snmprwcommunity = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A9:
                if (m_SIPDomain == null) {
                    m_SIPDomain = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AA:
                if (m_SysLogServer == null) {
                    m_SysLogServer = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AB:
                if (m_CVlan == null) {
                    m_CVlan = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34CF:
                if (m_FullBridge == null) {
                    m_FullBridge = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34AB, m_CVlan);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D3, m_CallAgent);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D2, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DB, m_DigitMap);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DE, m_DigitTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E4, m_EndpointIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E5, m_EndpointPrefix);
        TLVHelper.addEmbeddedTLV(tlv, 0x33EA, m_Explicitdetection);
        TLVHelper.addEmbeddedTLV(tlv, 0x33EE, m_Flashhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x34CF, m_FullBridge);
        TLVHelper.addEmbeddedTLV(tlv, 0x33EF, m_ImageName);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DD, m_Model);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D9, m_NTPServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E3, m_NcsMode2833);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D6, m_NspPrimaryRx);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D7, m_NspSecondaryRx);
        TLVHelper.addEmbeddedTLV(tlv, 0x33EC, m_OccamSingleContext);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F0, m_Offhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F2, m_Onhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D4, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E2, m_ProtocolService);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D5, m_Proxy);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DC, m_RegTimeout);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DA, m_Registrar);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E8, m_RestartDelay);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E7, m_RetryTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E6, m_SecondaryCallPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F1, m_SecondaryProxyIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F3, m_SecondaryRegistrarIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E1, m_Secondarycallagent);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E9, m_Security);
        TLVHelper.addEmbeddedTLV(tlv, 0x33DF, m_ServicePackage_ontprofile);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A9, m_SIPDomain);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D1, m_SnmpTrapHost);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F5, m_Snmprocommunity);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F6, m_Snmprwcommunity);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AA, m_SysLogServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D8, m_TimeZone);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F4, m_Transport);
        TLVHelper.addEmbeddedTLV(tlv, 0x33ED, m_Version);
        TLVHelper.addEmbeddedTLV(tlv, 0x33EB, m_VoiceBVI);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E0, m_VoiceMode);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCVlan
     * 
     * @param CVlan
     */
    public void setCVlan(Integer CVlan)
    {
        this.m_CVlan = CVlan;
    } //-- void setCVlan(Integer) 
    
    /**
     * Method setCallAgent
     * 
     * @param CallAgent
     */
    public void setCallAgent(String CallAgent)
    {
        this.m_CallAgent = CallAgent;
    } //-- void setCallAgent(String) 

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
     * Method setDigitMap
     * 
     * @param DigitMap
     */
    public void setDigitMap(String DigitMap)
    {
        this.m_DigitMap = DigitMap;
    } //-- void setDigitMap(String) 

    /**
     * Method setDigitTimeOut
     * 
     * @param DigitTimeOut
     */
    public void setDigitTimeOut(Integer DigitTimeOut)
    {
        this.m_DigitTimeOut = DigitTimeOut;
    } //-- void setDigitTimeOut(Integer) 

    /**
     * Method setEndpointIP
     * 
     * @param EndpointIP
     */
    public void setEndpointIP(Integer EndpointIP)
    {
        this.m_EndpointIP = EndpointIP;
    } //-- void setEndpointIP(Integer) 

    /**
     * Method setEndpointPrefix
     * 
     * @param EndpointPrefix
     */
    public void setEndpointPrefix(String EndpointPrefix)
    {
        this.m_EndpointPrefix = EndpointPrefix;
    } //-- void setEndpointPrefix(String) 

    /**
     * Method setExplicitdetection
     * 
     * @param Explicitdetection
     */
    public void setExplicitdetection(Integer Explicitdetection)
    {
        this.m_Explicitdetection = Explicitdetection;
    } //-- void setExplicitdetection(Integer) 

    /**
     * Method setFlashhook
     * 
     * @param Flashhook
     */
    public void setFlashhook(Integer Flashhook)
    {
        this.m_Flashhook = Flashhook;
    } //-- void setFlashhook(Integer) 

    /**
     * Method setFullBridge
     * 
     * @param FullBridge
     */
    public void setFullBridge(Integer FullBridge)
    {
        this.m_FullBridge = FullBridge;
    } //-- void setFullBridge(Integer) 

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
     * Method setImageName
     * 
     * @param ImageName
     */
    public void setImageName(String ImageName)
    {
        this.m_ImageName = ImageName;
    } //-- void setImageName(String) 

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
     * Method setNTPServer
     * 
     * @param NTPServer
     */
    public void setNTPServer(String NTPServer)
    {
        this.m_NTPServer = NTPServer;
    } //-- void setNTPServer(String) 

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
     * Method setNcsMode2833
     * 
     * @param NcsMode2833
     */
    public void setNcsMode2833(Integer NcsMode2833)
    {
        this.m_NcsMode2833 = NcsMode2833;
    } //-- void setNcsMode2833(Integer) 

    /**
     * Method setNspPrimaryRx
     * 
     * @param NspPrimaryRx
     */
    public void setNspPrimaryRx(Integer NspPrimaryRx)
    {
        this.m_NspPrimaryRx = NspPrimaryRx;
    } //-- void setNspPrimaryRx(Integer) 

    /**
     * Method setNspSecondaryRx
     * 
     * @param NspSecondaryRx
     */
    public void setNspSecondaryRx(Integer NspSecondaryRx)
    {
        this.m_NspSecondaryRx = NspSecondaryRx;
    } //-- void setNspSecondaryRx(Integer) 

    /**
     * Method setOccamSingleContext
     * 
     * @param OccamSingleContext
     */
    public void setOccamSingleContext(Integer OccamSingleContext)
    {
        this.m_OccamSingleContext = OccamSingleContext;
    } //-- void setOccamSingleContext(Integer) 

    /**
     * Method setOffhook
     * 
     * @param Offhook
     */
    public void setOffhook(Integer Offhook)
    {
        this.m_Offhook = Offhook;
    } //-- void setOffhook(Integer) 

    /**
     * Method setOnhook
     * 
     * @param Onhook
     */
    public void setOnhook(Integer Onhook)
    {
        this.m_Onhook = Onhook;
    } //-- void setOnhook(Integer) 

    /**
     * Method setPort
     * 
     * @param Port
     */
    public void setPort(Integer Port)
    {
        this.m_Port = Port;
    } //-- void setPort(Integer) 

    /**
     * Method setProtocolService
     * 
     * @param ProtocolService
     */
    public void setProtocolService(Integer ProtocolService)
    {
        this.m_ProtocolService = ProtocolService;
    } //-- void setProtocolService(Integer) 

    /**
     * Method setProxy
     * 
     * @param Proxy
     */
    public void setProxy(String Proxy)
    {
        this.m_Proxy = Proxy;
    } //-- void setProxy(String) 

    /**
     * Method setRegTimeout
     * 
     * @param RegTimeout
     */
    public void setRegTimeout(Integer RegTimeout)
    {
        this.m_RegTimeout = RegTimeout;
    } //-- void setRegTimeout(Integer) 

    /**
     * Method setRegistrar
     * 
     * @param Registrar
     */
    public void setRegistrar(String Registrar)
    {
        this.m_Registrar = Registrar;
    } //-- void setRegistrar(String) 

    /**
     * Method setRestartDelay
     * 
     * @param RestartDelay
     */
    public void setRestartDelay(Integer RestartDelay)
    {
        this.m_RestartDelay = RestartDelay;
    } //-- void setRestartDelay(Integer) 

    /**
     * Method setRetryTimeOut
     * 
     * @param RetryTimeOut
     */
    public void setRetryTimeOut(Integer RetryTimeOut)
    {
        this.m_RetryTimeOut = RetryTimeOut;
    } //-- void setRetryTimeOut(Integer) 

    /**
     * Method setSecondaryCallPort
     * 
     * @param SecondaryCallPort
     */
    public void setSecondaryCallPort(Integer SecondaryCallPort)
    {
        this.m_SecondaryCallPort = SecondaryCallPort;
    } //-- void setSecondaryCallPort(Integer) 

    /**
     * Method setSecondaryProxyIP
     * 
     * @param SecondaryProxyIP
     */
    public void setSecondaryProxyIP(String SecondaryProxyIP)
    {
        this.m_SecondaryProxyIP = SecondaryProxyIP;
    } //-- void setSecondaryProxyIP(String) 

    /**
     * Method setSecondaryRegistrarIP
     * 
     * @param SecondaryRegistrarIP
     */
    public void setSecondaryRegistrarIP(String SecondaryRegistrarIP)
    {
        this.m_SecondaryRegistrarIP = SecondaryRegistrarIP;
    } //-- void setSecondaryRegistrarIP(String) 

    /**
     * Method setSecondarycallagent
     * 
     * @param Secondarycallagent
     */
    public void setSecondarycallagent(String Secondarycallagent)
    {
        this.m_Secondarycallagent = Secondarycallagent;
    } //-- void setSecondarycallagent(String) 

    /**
     * Method setSecurity
     * 
     * @param Security
     */
    public void setSecurity(Integer Security)
    {
        this.m_Security = Security;
    } //-- void setSecurity(Integer) 

    /**
     * Method setServicePackage_ontprofile
     * 
     * @param ServicePackage_ontprofile
     */
    public void setServicePackage_ontprofile(String ServicePackage_ontprofile)
    {
        this.m_ServicePackage_ontprofile = ServicePackage_ontprofile;
    } //-- void setServicePackage_ontprofile(String) 

    /**
     * Method setSIPDomain
     * 
     * @param SIPDomain
     */
    public void setSIPDomain(String SIPDomain)
    {
        this.m_SIPDomain = SIPDomain;
    } //-- void setSIPDomain(String) 

    /**
     * Method setSnmpTrapHost
     * 
     * @param SnmpTrapHost
     */
    public void setSnmpTrapHost(String SnmpTrapHost)
    {
        this.m_SnmpTrapHost = SnmpTrapHost;
    } //-- void setSnmpTrapHost(String) 

    /**
     * Method setSnmprocommunity
     * 
     * @param Snmprocommunity
     */
    public void setSnmprocommunity(String Snmprocommunity)
    {
        this.m_Snmprocommunity = Snmprocommunity;
    } //-- void setSnmprocommunity(String) 

    /**
     * Method setSnmprwcommunity
     * 
     * @param Snmprwcommunity
     */
    public void setSnmprwcommunity(String Snmprwcommunity)
    {
        this.m_Snmprwcommunity = Snmprwcommunity;
    } //-- void setSnmprwcommunity(String) 

    /**
     * Method setSysLogServer
     * 
     * @param SysLogServer
     */
    public void setSysLogServer(String SysLogServer)
    {
        this.m_SysLogServer = SysLogServer;
    } //-- void setSysLogServer(String) 

    /**
     * Method setTimeZone
     * 
     * @param TimeZone
     */
    public void setTimeZone(String TimeZone)
    {
        this.m_TimeZone = TimeZone;
    } //-- void setTimeZone(String) 

    /**
     * Method setTransport
     * 
     * @param Transport
     */
    public void setTransport(Integer Transport)
    {
        this.m_Transport = Transport;
    } //-- void setTransport(Integer) 

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
    public void setVersion(Integer Version)
    {
        this.m_Version = Version;
    } //-- void setVersion(Integer) 

    /**
     * Method setVoiceBVI
     * 
     * @param VoiceBVI
     */
    public void setVoiceBVI(Integer VoiceBVI)
    {
        this.m_VoiceBVI = VoiceBVI;
    } //-- void setVoiceBVI(Integer) 

    /**
     * Method setVoiceMode
     * 
     * @param VoiceMode
     */
    public void setVoiceMode(Integer VoiceMode)
    {
        this.m_VoiceMode = VoiceMode;
    } //-- void setVoiceMode(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6AONTProfile ) {
            super.updateFields(obj1);
            B6AONTProfile obj = (B6AONTProfile)obj1;
            if (obj.getCVlan() != null )
                setCVlan((Integer)Helper.copy(obj.getCVlan()));
           if (obj.getCallAgent() != null )
               setCallAgent((String)Helper.copy(obj.getCallAgent()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getDigitMap() != null )
               setDigitMap((String)Helper.copy(obj.getDigitMap()));
           if (obj.getDigitTimeOut() != null )
               setDigitTimeOut((Integer)Helper.copy(obj.getDigitTimeOut()));
           if (obj.getEndpointIP() != null )
               setEndpointIP((Integer)Helper.copy(obj.getEndpointIP()));
           if (obj.getEndpointPrefix() != null )
               setEndpointPrefix((String)Helper.copy(obj.getEndpointPrefix()));
           if (obj.getExplicitdetection() != null )
               setExplicitdetection((Integer)Helper.copy(obj.getExplicitdetection()));
           if (obj.getFlashhook() != null )
               setFlashhook((Integer)Helper.copy(obj.getFlashhook()));
           if (obj.getFullBridge() != null )
               setFullBridge((Integer)Helper.copy(obj.getFullBridge()));
           if (obj.getImageName() != null )
               setImageName((String)Helper.copy(obj.getImageName()));
           if (obj.getModel() != null )
               setModel((String)Helper.copy(obj.getModel()));
           if (obj.getNTPServer() != null )
               setNTPServer((String)Helper.copy(obj.getNTPServer()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getNcsMode2833() != null )
               setNcsMode2833((Integer)Helper.copy(obj.getNcsMode2833()));
           if (obj.getNspPrimaryRx() != null )
               setNspPrimaryRx((Integer)Helper.copy(obj.getNspPrimaryRx()));
           if (obj.getNspSecondaryRx() != null )
               setNspSecondaryRx((Integer)Helper.copy(obj.getNspSecondaryRx()));
           if (obj.getOccamSingleContext() != null )
               setOccamSingleContext((Integer)Helper.copy(obj.getOccamSingleContext()));
           if (obj.getOffhook() != null )
               setOffhook((Integer)Helper.copy(obj.getOffhook()));
           if (obj.getOnhook() != null )
               setOnhook((Integer)Helper.copy(obj.getOnhook()));
           if (obj.getPort() != null )
               setPort((Integer)Helper.copy(obj.getPort()));
           if (obj.getProtocolService() != null )
               setProtocolService((Integer)Helper.copy(obj.getProtocolService()));
           if (obj.getProxy() != null )
               setProxy((String)Helper.copy(obj.getProxy()));
           if (obj.getRegTimeout() != null )
               setRegTimeout((Integer)Helper.copy(obj.getRegTimeout()));
           if (obj.getRegistrar() != null )
               setRegistrar((String)Helper.copy(obj.getRegistrar()));
           if (obj.getRestartDelay() != null )
               setRestartDelay((Integer)Helper.copy(obj.getRestartDelay()));
           if (obj.getRetryTimeOut() != null )
               setRetryTimeOut((Integer)Helper.copy(obj.getRetryTimeOut()));
           if (obj.getSecondaryCallPort() != null )
               setSecondaryCallPort((Integer)Helper.copy(obj.getSecondaryCallPort()));
           if (obj.getSecondaryProxyIP() != null )
               setSecondaryProxyIP((String)Helper.copy(obj.getSecondaryProxyIP()));
           if (obj.getSecondaryRegistrarIP() != null )
               setSecondaryRegistrarIP((String)Helper.copy(obj.getSecondaryRegistrarIP()));
           if (obj.getSecondarycallagent() != null )
               setSecondarycallagent((String)Helper.copy(obj.getSecondarycallagent()));
           if (obj.getSecurity() != null )
               setSecurity((Integer)Helper.copy(obj.getSecurity()));
           if (obj.getServicePackage_ontprofile() != null )
               setServicePackage_ontprofile((String)Helper.copy(obj.getServicePackage_ontprofile()));
           if (obj.getSIPDomain() != null )
               setSIPDomain((String)Helper.copy(obj.getSIPDomain()));
           if (obj.getSnmpTrapHost() != null )
               setSnmpTrapHost((String)Helper.copy(obj.getSnmpTrapHost()));
           if (obj.getSnmprocommunity() != null )
               setSnmprocommunity((String)Helper.copy(obj.getSnmprocommunity()));
           if (obj.getSnmprwcommunity() != null )
               setSnmprwcommunity((String)Helper.copy(obj.getSnmprwcommunity()));
           if (obj.getSysLogServer() != null )
               setSysLogServer((String)Helper.copy(obj.getSysLogServer()));
           if (obj.getTimeZone() != null )
               setTimeZone((String)Helper.copy(obj.getTimeZone()));
           if (obj.getTransport() != null )
               setTransport((Integer)Helper.copy(obj.getTransport()));
           if (obj.getVersion() != null )
               setVersion((Integer)Helper.copy(obj.getVersion()));
           if (obj.getVoiceBVI() != null )
               setVoiceBVI((Integer)Helper.copy(obj.getVoiceBVI()));
           if (obj.getVoiceMode() != null )
               setVoiceMode((Integer)Helper.copy(obj.getVoiceMode()));
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
