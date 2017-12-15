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
 * Class B6AONTSIPSD.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTSIPSD extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CallTransfer
     */
    public Integer m_CallTransfer;

    /**
     * Field m_CallWaiting
     */
    public Integer m_CallWaiting;

    /**
     * Field m_CallerId
     */
    public Integer m_CallerId;

    /**
     * Field m_CustomerVLAN
     */
    public Integer m_CustomerVLAN;

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
     * Field m_EchoCancel
     */
    public Integer m_EchoCancel;

    /**
     * Field m_EpsVLAN
     */
    public Integer m_EpsVLAN;

    /**
     * Field m_Gateway
     */
    public Integer m_Gateway;

    /**
     * Field m_MWI
     */
    public Integer m_MWI;

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
     * Field m_NspPrimaryRx
     */
    public Integer m_NspPrimaryRx;

    /**
     * Field m_NspSecondaryRx
     */
    public Integer m_NspSecondaryRx;

    /**
     * Field m_Port
     */
    public Integer m_Port;

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
     * Field m_SecondaryProxyIP
     */
    public String m_SecondaryProxyIP;

    /**
     * Field m_SecondaryRegistrarIP
     */
    public String m_SecondaryRegistrarIP;

    /**
     * Field m_ThreeWayCalling
     */
    public Integer m_ThreeWayCalling;

    /**
     * Field m_TimeZone
     */
    public String m_TimeZone;

    /**
     * Field m_Transport
     */
    public Integer m_Transport;

    /**
     * Field m_VoiceBVI
     */
    public Integer m_VoiceBVI;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTSIPSD";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTSIPSD() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTSIPSD()


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
        if( obj1 instanceof B6AONTSIPSD ) {
            super.copyFields(obj1);
            B6AONTSIPSD obj = (B6AONTSIPSD)obj1;
            setCallTransfer((Integer)Helper.copy(obj.getCallTransfer()));
            setCallWaiting((Integer)Helper.copy(obj.getCallWaiting()));
            setCallerId((Integer)Helper.copy(obj.getCallerId()));
            setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setDigitMap((String)Helper.copy(obj.getDigitMap()));
            setDigitTimeOut((Integer)Helper.copy(obj.getDigitTimeOut()));
            setEchoCancel((Integer)Helper.copy(obj.getEchoCancel()));
            setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
            setGateway((Integer)Helper.copy(obj.getGateway()));
            setMWI((Integer)Helper.copy(obj.getMWI()));
            setManagementVLAN((Integer)Helper.copy(obj.getManagementVLAN()));
            setNTPServer((String)Helper.copy(obj.getNTPServer()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setNspPrimaryRx((Integer)Helper.copy(obj.getNspPrimaryRx()));
            setNspSecondaryRx((Integer)Helper.copy(obj.getNspSecondaryRx()));
            setPort((Integer)Helper.copy(obj.getPort()));
            setProxy((String)Helper.copy(obj.getProxy()));
            setRegTimeout((Integer)Helper.copy(obj.getRegTimeout()));
            setRegistrar((String)Helper.copy(obj.getRegistrar()));
            setSecondaryProxyIP((String)Helper.copy(obj.getSecondaryProxyIP()));
            setSecondaryRegistrarIP((String)Helper.copy(obj.getSecondaryRegistrarIP()));
            setThreeWayCalling((Integer)Helper.copy(obj.getThreeWayCalling()));
            setTimeZone((String)Helper.copy(obj.getTimeZone()));
            setTransport((Integer)Helper.copy(obj.getTransport()));
            setVoiceBVI((Integer)Helper.copy(obj.getVoiceBVI()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCallTransfer
     */
    public Integer getCallTransfer()
    {
        return this.m_CallTransfer;
    } //-- Integer getCallTransfer() 

    /**
     * Method getCallWaiting
     */
    public Integer getCallWaiting()
    {
        return this.m_CallWaiting;
    } //-- Integer getCallWaiting() 

    /**
     * Method getCallerId
     */
    public Integer getCallerId()
    {
        return this.m_CallerId;
    } //-- Integer getCallerId() 

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
     * Method getEchoCancel
     */
    public Integer getEchoCancel()
    {
        return this.m_EchoCancel;
    } //-- Integer getEchoCancel() 

    /**
     * Method getEpsVLAN
     */
    public Integer getEpsVLAN()
    {
        return this.m_EpsVLAN;
    } //-- Integer getEpsVLAN() 

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
     * Method getMWI
     */
    public Integer getMWI()
    {
        return this.m_MWI;
    } //-- Integer getMWI() 

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
     * Method getPort
     */
    public Integer getPort()
    {
        return this.m_Port;
    } //-- Integer getPort() 

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
     * Method getThreeWayCalling
     */
    public Integer getThreeWayCalling()
    {
        return this.m_ThreeWayCalling;
    } //-- Integer getThreeWayCalling() 

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
        return BseriesTlvConstants.B6AONTSIPSD;
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
            case 0x3489:
                if (m_EpsVLAN == null) {
                    m_EpsVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348A:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348B:
                if (m_ThreeWayCalling == null) {
                    m_ThreeWayCalling = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348C:
                if (m_Proxy == null) {
                    m_Proxy = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348D:
                if (m_CallWaiting == null) {
                    m_CallWaiting = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348E:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x348F:
                if (m_DigitTimeOut == null) {
                    m_DigitTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3490:
                if (m_Registrar == null) {
                    m_Registrar = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3491:
                if (m_NTPServer == null) {
                    m_NTPServer = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3492:
                if (m_TimeZone == null) {
                    m_TimeZone = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3493:
                if (m_DigitMap == null) {
                    m_DigitMap = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3494:
                if (m_EchoCancel == null) {
                    m_EchoCancel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3495:
                if (m_MWI == null) {
                    m_MWI = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3496:
                if (m_RegTimeout == null) {
                    m_RegTimeout = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3497:
                if (m_NspPrimaryRx == null) {
                    m_NspPrimaryRx = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3498:
                if (m_CallTransfer == null) {
                    m_CallTransfer = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3499:
                if (m_NspSecondaryRx == null) {
                    m_NspSecondaryRx = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349A:
                if (m_CustomerVLAN == null) {
                    m_CustomerVLAN = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349B:
                if (m_Gateway == null) {
                    m_Gateway = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349C:
                if (m_SecondaryRegistrarIP == null) {
                    m_SecondaryRegistrarIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349D:
                if (m_SecondaryProxyIP == null) {
                    m_SecondaryProxyIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349E:
                if (m_Transport == null) {
                    m_Transport = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x349F:
                if (m_CallerId == null) {
                    m_CallerId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A0:
                if (m_VoiceBVI == null) {
                    m_VoiceBVI = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A1:
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3498, m_CallTransfer);
        TLVHelper.addEmbeddedTLV(tlv, 0x348D, m_CallWaiting);
        TLVHelper.addEmbeddedTLV(tlv, 0x349F, m_CallerId);
        TLVHelper.addEmbeddedTLV(tlv, 0x349A, m_CustomerVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x348A, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x3493, m_DigitMap);
        TLVHelper.addEmbeddedTLV(tlv, 0x348F, m_DigitTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x3494, m_EchoCancel);
        TLVHelper.addEmbeddedTLV(tlv, 0x3489, m_EpsVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x349B, m_Gateway);
        TLVHelper.addEmbeddedTLV(tlv, 0x3495, m_MWI);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A1, m_ManagementVLAN);
        TLVHelper.addEmbeddedTLV(tlv, 0x3491, m_NTPServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x3497, m_NspPrimaryRx);
        TLVHelper.addEmbeddedTLV(tlv, 0x3499, m_NspSecondaryRx);
        TLVHelper.addEmbeddedTLV(tlv, 0x348E, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x348C, m_Proxy);
        TLVHelper.addEmbeddedTLV(tlv, 0x3496, m_RegTimeout);
        TLVHelper.addEmbeddedTLV(tlv, 0x3490, m_Registrar);
        TLVHelper.addEmbeddedTLV(tlv, 0x349D, m_SecondaryProxyIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x349C, m_SecondaryRegistrarIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x348B, m_ThreeWayCalling);
        TLVHelper.addEmbeddedTLV(tlv, 0x3492, m_TimeZone);
        TLVHelper.addEmbeddedTLV(tlv, 0x349E, m_Transport);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A0, m_VoiceBVI);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCallTransfer
     * 
     * @param CallTransfer
     */
    public void setCallTransfer(Integer CallTransfer)
    {
        this.m_CallTransfer = CallTransfer;
    } //-- void setCallTransfer(Integer) 

    /**
     * Method setCallWaiting
     * 
     * @param CallWaiting
     */
    public void setCallWaiting(Integer CallWaiting)
    {
        this.m_CallWaiting = CallWaiting;
    } //-- void setCallWaiting(Integer) 

    /**
     * Method setCallerId
     * 
     * @param CallerId
     */
    public void setCallerId(Integer CallerId)
    {
        this.m_CallerId = CallerId;
    } //-- void setCallerId(Integer) 

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
     * Method setEchoCancel
     * 
     * @param EchoCancel
     */
    public void setEchoCancel(Integer EchoCancel)
    {
        this.m_EchoCancel = EchoCancel;
    } //-- void setEchoCancel(Integer) 

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
     * Method setMWI
     * 
     * @param MWI
     */
    public void setMWI(Integer MWI)
    {
        this.m_MWI = MWI;
    } //-- void setMWI(Integer) 

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
     * Method setPort
     * 
     * @param Port
     */
    public void setPort(Integer Port)
    {
        this.m_Port = Port;
    } //-- void setPort(Integer) 

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
     * Method setThreeWayCalling
     * 
     * @param ThreeWayCalling
     */
    public void setThreeWayCalling(Integer ThreeWayCalling)
    {
        this.m_ThreeWayCalling = ThreeWayCalling;
    } //-- void setThreeWayCalling(Integer) 

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
        if( obj1 instanceof B6AONTSIPSD ) {
            super.updateFields(obj1);
            B6AONTSIPSD obj = (B6AONTSIPSD)obj1;
           if (obj.getCallTransfer() != null )
               setCallTransfer((Integer)Helper.copy(obj.getCallTransfer()));
           if (obj.getCallWaiting() != null )
               setCallWaiting((Integer)Helper.copy(obj.getCallWaiting()));
           if (obj.getCallerId() != null )
               setCallerId((Integer)Helper.copy(obj.getCallerId()));
           if (obj.getCustomerVLAN() != null )
               setCustomerVLAN((Integer)Helper.copy(obj.getCustomerVLAN()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getDigitMap() != null )
               setDigitMap((String)Helper.copy(obj.getDigitMap()));
           if (obj.getDigitTimeOut() != null )
               setDigitTimeOut((Integer)Helper.copy(obj.getDigitTimeOut()));
           if (obj.getEchoCancel() != null )
               setEchoCancel((Integer)Helper.copy(obj.getEchoCancel()));
           if (obj.getEpsVLAN() != null )
               setEpsVLAN((Integer)Helper.copy(obj.getEpsVLAN()));
           if (obj.getGateway() != null )
               setGateway((Integer)Helper.copy(obj.getGateway()));
           if (obj.getMWI() != null )
               setMWI((Integer)Helper.copy(obj.getMWI()));
           if (obj.getManagementVLAN() != null )
               setManagementVLAN((Integer)Helper.copy(obj.getManagementVLAN()));
           if (obj.getNTPServer() != null )
               setNTPServer((String)Helper.copy(obj.getNTPServer()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getNspPrimaryRx() != null )
               setNspPrimaryRx((Integer)Helper.copy(obj.getNspPrimaryRx()));
           if (obj.getNspSecondaryRx() != null )
               setNspSecondaryRx((Integer)Helper.copy(obj.getNspSecondaryRx()));
           if (obj.getPort() != null )
               setPort((Integer)Helper.copy(obj.getPort()));
           if (obj.getProxy() != null )
               setProxy((String)Helper.copy(obj.getProxy()));
           if (obj.getRegTimeout() != null )
               setRegTimeout((Integer)Helper.copy(obj.getRegTimeout()));
           if (obj.getRegistrar() != null )
               setRegistrar((String)Helper.copy(obj.getRegistrar()));
           if (obj.getSecondaryProxyIP() != null )
               setSecondaryProxyIP((String)Helper.copy(obj.getSecondaryProxyIP()));
           if (obj.getSecondaryRegistrarIP() != null )
               setSecondaryRegistrarIP((String)Helper.copy(obj.getSecondaryRegistrarIP()));
           if (obj.getThreeWayCalling() != null )
               setThreeWayCalling((Integer)Helper.copy(obj.getThreeWayCalling()));
           if (obj.getTimeZone() != null )
               setTimeZone((String)Helper.copy(obj.getTimeZone()));
           if (obj.getTransport() != null )
               setTransport((Integer)Helper.copy(obj.getTransport()));
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
