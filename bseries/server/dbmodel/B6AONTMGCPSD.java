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
 * Class B6AONTMGCPSD.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTMGCPSD extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CallAgent
     */
    public String m_CallAgent;

    /**
     * Field m_CustomerVLAN
     */
    public Integer m_CustomerVLAN;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * Field m_EchoCancel
     */
    public Integer m_EchoCancel;

    /**
     * Field m_EndpointIP
     */
    public Integer m_EndpointIP;

    /**
     * Field m_EndpointPrefix
     */
    public String m_EndpointPrefix;

    /**
     * Field m_EpsVLAN
     */
    public Integer m_EpsVLAN;

    /**
     * Field m_Explicitdetection
     */
    public Integer m_Explicitdetection;

    /**
     * Field m_Flashhook
     */
    public Integer m_Flashhook;

    /**
     * Field m_Gateway
     */
    public Integer m_Gateway;

    /**
     * Field m_ManagementVLAN
     */
    public Integer m_ManagementVLAN;

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
     * Field m_Secondarycallagent
     */
    public String m_Secondarycallagent;

    /**
     * Field m_Security
     */
    public Integer m_Security;

    /**
     * Field m_TimeZone
     */
    public String m_TimeZone;

    /**
     * Field m_VoiceBVI
     */
    public Integer m_VoiceBVI;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTMGCPSD";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTMGCPSD() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTMGCPSD()


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
        if( obj1 instanceof B6AONTMGCPSD ) {
            super.copyFields(obj1);
            B6AONTMGCPSD obj = (B6AONTMGCPSD)obj1;
            setCallAgent((String)Helper.copy(obj.getCallAgent()));
            setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setEchoCancel((Integer)Helper.copy(obj.getEchoCancel()));
            setEndpointIP((Integer)Helper.copy(obj.getEndpointIP()));
            setEndpointPrefix((String)Helper.copy(obj.getEndpointPrefix()));
            setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
            setExplicitdetection((Integer)Helper.copy(obj.getExplicitdetection()));
            setFlashhook((Integer)Helper.copy(obj.getFlashhook()));
            setGateway((Integer)Helper.copy(obj.getGateway()));
            setManagementVLAN((Integer)Helper.copy(obj.getManagementVLAN()));
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
            setRestartDelay((Integer)Helper.copy(obj.getRestartDelay()));
            setRetryTimeOut((Integer)Helper.copy(obj.getRetryTimeOut()));
            setSecondaryCallPort((Integer)Helper.copy(obj.getSecondaryCallPort()));
            setSecondarycallagent((String)Helper.copy(obj.getSecondarycallagent()));
            setSecurity((Integer)Helper.copy(obj.getSecurity()));
            setTimeZone((String)Helper.copy(obj.getTimeZone()));
            setVoiceBVI((Integer)Helper.copy(obj.getVoiceBVI()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCallAgent
     */
    public String getCallAgent()
    {
        return this.m_CallAgent;
    } //-- String getCallAgent() 

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
     * Method getEchoCancel
     */
    public Integer getEchoCancel()
    {
        return this.m_EchoCancel;
    } //-- Integer getEchoCancel() 

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
     * Method getEpsVLAN
     */
    public Integer getEpsVLAN()
    {
        return this.m_EpsVLAN;
    } //-- Integer getEpsVLAN() 

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
     * Method getGateway
     */
    public Integer getGateway()
    {
        return this.m_Gateway;
    } //-- Integer getGateway() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getManagementVLAN
     */
    public Integer getManagementVLAN()
    {
        return this.m_ManagementVLAN;
    } //-- Integer getManagementVLAN() 

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
        return BseriesTlvConstants.B6AONTMGCPSD;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getVoiceBVI
     */
    public Integer getVoiceBVI()
    {
        return this.m_VoiceBVI;
    } //-- Integer getVoiceBVI() 

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
            case 0x346F:
                if (m_EpsVLAN == null) {
                    m_EpsVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3470:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3471:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3472:
                if (m_EchoCancel == null) {
                    m_EchoCancel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3473:
                if (m_CustomerVLAN == null) {
                    m_CustomerVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3474:
                if (m_TimeZone == null) {
                    m_TimeZone = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3475:
                if (m_NspPrimaryRx == null) {
                    m_NspPrimaryRx = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3476:
                if (m_NspSecondaryRx == null) {
                    m_NspSecondaryRx = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3477:
                if (m_NTPServer == null) {
                    m_NTPServer = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3478:
                if (m_CallAgent == null) {
                    m_CallAgent = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3479:
                if (m_EndpointPrefix == null) {
                    m_EndpointPrefix = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347A:
                if (m_Secondarycallagent == null) {
                    m_Secondarycallagent = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347B:
                if (m_NcsMode2833 == null) {
                    m_NcsMode2833 = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347C:
                if (m_Flashhook == null) {
                    m_Flashhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347D:
                if (m_RetryTimeOut == null) {
                    m_RetryTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347E:
                if (m_Explicitdetection == null) {
                    m_Explicitdetection = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x347F:
                if (m_ProtocolService == null) {
                    m_ProtocolService = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3480:
                if (m_EndpointIP == null) {
                    m_EndpointIP = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3481:
                if (m_Onhook == null) {
                    m_Onhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3482:
                if (m_Offhook == null) {
                    m_Offhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3483:
                if (m_SecondaryCallPort == null) {
                    m_SecondaryCallPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3484:
                if (m_OccamSingleContext == null) {
                    m_OccamSingleContext = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3485:
                // More than one attributes with same tlv type!
                // These will be processed in the order they arrive
                if (m_RestartDelay == null) {
                    m_RestartDelay = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                if (m_Security == null) {
                    m_Security = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3487:
                if (m_Gateway == null) {
                    m_Gateway = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3488:
                if (m_VoiceBVI == null) {
                    m_VoiceBVI = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3489:
                if (m_ManagementVLAN == null) {
                    m_ManagementVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3478, m_CallAgent);
        TLVHelper.addEmbeddedTLV(tlv, 0x3473, m_CustomerVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3470, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3472, m_EchoCancel);
        TLVHelper.addEmbeddedTLV(tlv, 0x3480, m_EndpointIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3479, m_EndpointPrefix);
        TLVHelper.addEmbeddedTLV(tlv, 0x346F, m_EpsVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x347E, m_Explicitdetection);
        TLVHelper.addEmbeddedTLV(tlv, 0x347C, m_Flashhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x3487, m_Gateway);
        TLVHelper.addEmbeddedTLV(tlv, 0x3489, m_ManagementVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3477, m_NTPServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x347B, m_NcsMode2833);
        TLVHelper.addEmbeddedTLV(tlv, 0x3475, m_NspPrimaryRx);
        TLVHelper.addEmbeddedTLV(tlv, 0x3476, m_NspSecondaryRx);
        TLVHelper.addEmbeddedTLV(tlv, 0x3484, m_OccamSingleContext);
        TLVHelper.addEmbeddedTLV(tlv, 0x3482, m_Offhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x3481, m_Onhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x3471, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x347F, m_ProtocolService);
        TLVHelper.addEmbeddedTLV(tlv, 0x3485, m_RestartDelay);
        TLVHelper.addEmbeddedTLV(tlv, 0x347D, m_RetryTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x3483, m_SecondaryCallPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x347A, m_Secondarycallagent);
        TLVHelper.addEmbeddedTLV(tlv, 0x3485, m_Security);
        TLVHelper.addEmbeddedTLV(tlv, 0x3474, m_TimeZone);
        TLVHelper.addEmbeddedTLV(tlv, 0x3488, m_VoiceBVI);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

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
     * Method setEchoCancel
     * 
     * @param EchoCancel
     */
    public void setEchoCancel(Integer EchoCancel)
    {
        this.m_EchoCancel = EchoCancel;
    } //-- void setEchoCancel(Integer) 

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
     * Method setEpsVLAN
     * 
     * @param EpsVLAN
     */
    public void setEpsVLAN(Integer EpsVLAN)
    {
        this.m_EpsVLAN = EpsVLAN;
    } //-- void setEpsVLAN(Integer) 

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
     * Method setGateway
     * 
     * @param Gateway
     */
    public void setGateway(Integer Gateway)
    {
        this.m_Gateway = Gateway;
    } //-- void setGateway(Integer) 

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
     * Method setManagementVLAN
     * 
     * @param ManagementVLAN
     */
    public void setManagementVLAN(Integer ManagementVLAN)
    {
        this.m_ManagementVLAN = ManagementVLAN;
    } //-- void setManagementVLAN(Integer) 

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
     * Method setTimeZone
     * 
     * @param TimeZone
     */
    public void setTimeZone(String TimeZone)
    {
        this.m_TimeZone = TimeZone;
    } //-- void setTimeZone(String) 

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
     * Method setVoiceBVI
     * 
     * @param VoiceBVI
     */
    public void setVoiceBVI(Integer VoiceBVI)
    {
        this.m_VoiceBVI = VoiceBVI;
    } //-- void setVoiceBVI(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6AONTMGCPSD ) {
            super.updateFields(obj1);
            B6AONTMGCPSD obj = (B6AONTMGCPSD)obj1;
           if (obj.getCallAgent() != null )
               setCallAgent((String)Helper.copy(obj.getCallAgent()));
           if (obj.getCustomerVLAN() != null )
               setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getEchoCancel() != null )
               setEchoCancel((Integer)Helper.copy(obj.getEchoCancel()));
           if (obj.getEndpointIP() != null )
               setEndpointIP((Integer)Helper.copy(obj.getEndpointIP()));
           if (obj.getEndpointPrefix() != null )
               setEndpointPrefix((String)Helper.copy(obj.getEndpointPrefix()));
           if (obj.getEpsVLAN() != null )
               setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
           if (obj.getExplicitdetection() != null )
               setExplicitdetection((Integer)Helper.copy(obj.getExplicitdetection()));
           if (obj.getFlashhook() != null )
               setFlashhook((Integer)Helper.copy(obj.getFlashhook()));
           if (obj.getGateway() != null )
               setGateway((Integer)Helper.copy(obj.getGateway()));
           if (obj.getManagementVLAN() != null )
               setManagementVLAN((Integer)Helper.copy(obj.getManagementVLAN()));
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
           if (obj.getRestartDelay() != null )
               setRestartDelay((Integer)Helper.copy(obj.getRestartDelay()));
           if (obj.getRetryTimeOut() != null )
               setRetryTimeOut((Integer)Helper.copy(obj.getRetryTimeOut()));
           if (obj.getSecondaryCallPort() != null )
               setSecondaryCallPort((Integer)Helper.copy(obj.getSecondaryCallPort()));
           if (obj.getSecondarycallagent() != null )
               setSecondarycallagent((String)Helper.copy(obj.getSecondarycallagent()));
           if (obj.getSecurity() != null )
               setSecurity((Integer)Helper.copy(obj.getSecurity()));
           if (obj.getTimeZone() != null )
               setTimeZone((String)Helper.copy(obj.getTimeZone()));
           if (obj.getVoiceBVI() != null )
               setVoiceBVI((Integer)Helper.copy(obj.getVoiceBVI()));
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
