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
 * Class B6BLCSIPService.
 * 
 * @version $Revision$ $Date$
 */
public class B6BLCSIPService extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CallWating
     */
    public Integer m_CallWating;

    /**
     * Field m_CallerId
     */
    public Integer m_CallerId;

    /**
     * Field m_MessageWaitIndication
     */
    public Integer m_MessageWaitIndication;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_ThreeWayCalling
     */
    public Integer m_ThreeWayCalling;

    /**
     * Field m_description
     */
    public String m_description;

    /**
     * Field m_digitMap
     */
    public String m_digitMap;

    /**
     * Field m_domain_SIPService
     */
    public String m_domain_SIPService;

    /**
     * Field m_gateway
     */
    public Integer m_gateway;

    /**
     * Field m_interface_SIP
     */
    public String m_interface_SIP;

    /**
     * Field m_port
     */
    public Integer m_port;

    /**
     * Field m_primaryProxyIP
     */
    public String m_primaryProxyIP;

    /**
     * Field m_primaryProxyPort
     */
    public Integer m_primaryProxyPort;

    /**
     * Field m_primaryRegistrarIP
     */
    public String m_primaryRegistrarIP;

    /**
     * Field m_primaryRegistrarPort
     */
    public Integer m_primaryRegistrarPort;

    /**
     * Field m_protocol
     */
    public Integer m_protocol;

    /**
     * Field m_registrationTimeOut
     */
    public Integer m_registrationTimeOut;

    /**
     * Field m_secondaryProxyIP
     */
    public String m_secondaryProxyIP;

    /**
     * Field m_secondaryProxyPort
     */
    public Integer m_secondaryProxyPort;

    /**
     * Field m_secondaryRegistrarIP
     */
    public String m_secondaryRegistrarIP;

    /**
     * Field m_secondaryRegistrarPort
     */
    public Integer m_secondaryRegistrarPort;

    /**
     * Field m_transport
     */
    public Integer m_transport;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6BLCSIPService";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6BLCSIPService() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6BLCSIPService()


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
        if( obj1 instanceof B6BLCSIPService ) {
            super.copyFields(obj1);
            B6BLCSIPService obj = (B6BLCSIPService)obj1;
            setCallWating((Integer)Helper.copy(obj.getCallWating()));
            setCallerId((Integer)Helper.copy(obj.getCallerId()));
            setMessageWaitIndication((Integer)Helper.copy(obj.getMessageWaitIndication()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setThreeWayCalling((Integer)Helper.copy(obj.getThreeWayCalling()));
            setdescription((String)Helper.copy(obj.getdescription()));
            setdigitMap((String)Helper.copy(obj.getdigitMap()));
            setdomain_SIPService((String)Helper.copy(obj.getdomain_SIPService()));
            setgateway((Integer)Helper.copy(obj.getgateway()));
            setinterface_SIP((String)Helper.copy(obj.getinterface_SIP()));
            setport((Integer)Helper.copy(obj.getport()));
            setprimaryProxyIP((String)Helper.copy(obj.getprimaryProxyIP()));
            setprimaryProxyPort((Integer)Helper.copy(obj.getprimaryProxyPort()));
            setprimaryRegistrarIP((String)Helper.copy(obj.getprimaryRegistrarIP()));
            setprimaryRegistrarPort((Integer)Helper.copy(obj.getprimaryRegistrarPort()));
            setprotocol((Integer)Helper.copy(obj.getprotocol()));
            setregistrationTimeOut((Integer)Helper.copy(obj.getregistrationTimeOut()));
            setsecondaryProxyIP((String)Helper.copy(obj.getsecondaryProxyIP()));
            setsecondaryProxyPort((Integer)Helper.copy(obj.getsecondaryProxyPort()));
            setsecondaryRegistrarIP((String)Helper.copy(obj.getsecondaryRegistrarIP()));
            setsecondaryRegistrarPort((Integer)Helper.copy(obj.getsecondaryRegistrarPort()));
            settransport((Integer)Helper.copy(obj.gettransport()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCallWating
     */
    public Integer getCallWating()
    {
        return this.m_CallWating;
    } //-- Integer getCallWating() 

    /**
     * Method getCallerId
     */
    public Integer getCallerId()
    {
        return this.m_CallerId;
    } //-- Integer getCallerId() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getMessageWaitIndication
     */
    public Integer getMessageWaitIndication()
    {
        return this.m_MessageWaitIndication;
    } //-- Integer getMessageWaitIndication() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

    /**
     * Method getThreeWayCalling
     */
    public Integer getThreeWayCalling()
    {
        return this.m_ThreeWayCalling;
    } //-- Integer getThreeWayCalling() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6BLCSIPService;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getdescription
     */
    public String getdescription()
    {
        return this.m_description;
    } //-- String getdescription() 

    /**
     * Method getdigitMap
     */
    public String getdigitMap()
    {
        return this.m_digitMap;
    } //-- String getdigitMap() 

    /**
     * Method getdomain_SIPService
     */
    public String getdomain_SIPService()
    {
        return this.m_domain_SIPService;
    } //-- String getdomain_SIPService() 

    /**
     * Method getgateway
     */
    public Integer getgateway()
    {
        return this.m_gateway;
    } //-- Integer getgateway() 

    /**
     * Method getinterface_SIP
     */
    public String getinterface_SIP()
    {
        return this.m_interface_SIP;
    } //-- String getinterface_SIP() 

    /**
     * Method getport
     */
    public Integer getport()
    {
        return this.m_port;
    } //-- Integer getport() 

    /**
     * Method getprimaryProxyIP
     */
    public String getprimaryProxyIP()
    {
        return this.m_primaryProxyIP;
    } //-- String getprimaryProxyIP() 

    /**
     * Method getprimaryProxyPort
     */
    public Integer getprimaryProxyPort()
    {
        return this.m_primaryProxyPort;
    } //-- Integer getprimaryProxyPort() 

    /**
     * Method getprimaryRegistrarIP
     */
    public String getprimaryRegistrarIP()
    {
        return this.m_primaryRegistrarIP;
    } //-- String getprimaryRegistrarIP() 

    /**
     * Method getprimaryRegistrarPort
     */
    public Integer getprimaryRegistrarPort()
    {
        return this.m_primaryRegistrarPort;
    } //-- Integer getprimaryRegistrarPort() 

    /**
     * Method getprotocol
     */
    public Integer getprotocol()
    {
        return this.m_protocol;
    } //-- Integer getprotocol() 

    /**
     * Method getregistrationTimeOut
     */
    public Integer getregistrationTimeOut()
    {
        return this.m_registrationTimeOut;
    } //-- Integer getregistrationTimeOut() 

    /**
     * Method getsecondaryProxyIP
     */
    public String getsecondaryProxyIP()
    {
        return this.m_secondaryProxyIP;
    } //-- String getsecondaryProxyIP() 

    /**
     * Method getsecondaryProxyPort
     */
    public Integer getsecondaryProxyPort()
    {
        return this.m_secondaryProxyPort;
    } //-- Integer getsecondaryProxyPort() 

    /**
     * Method getsecondaryRegistrarIP
     */
    public String getsecondaryRegistrarIP()
    {
        return this.m_secondaryRegistrarIP;
    } //-- String getsecondaryRegistrarIP() 

    /**
     * Method getsecondaryRegistrarPort
     */
    public Integer getsecondaryRegistrarPort()
    {
        return this.m_secondaryRegistrarPort;
    } //-- Integer getsecondaryRegistrarPort() 

    /**
     * Method gettransport
     */
    public Integer gettransport()
    {
        return this.m_transport;
    } //-- Integer gettransport() 

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
            case 0x34A5:
                if (m_ThreeWayCalling == null) {
                    m_ThreeWayCalling = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A6:
                if (m_CallWating == null) {
                    m_CallWating = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A7:
                if (m_CallerId == null) {
                    m_CallerId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34A8:
                if (m_MessageWaitIndication == null) {
                    m_MessageWaitIndication = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AE:
                if (m_description == null) {
                    m_description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34AF:
                if (m_protocol == null) {
                    m_protocol = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B0:
                if (m_gateway == null) {
                    m_gateway = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B1:
                if (m_interface_SIP == null) {
                    m_interface_SIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B2:
                if (m_port == null) {
                    m_port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B3:
                if (m_transport == null) {
                    m_transport = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B4:
                if (m_primaryProxyIP == null) {
                    m_primaryProxyIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B5:
                if (m_primaryProxyPort == null) {
                    m_primaryProxyPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B6:
                if (m_secondaryProxyIP == null) {
                    m_secondaryProxyIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B7:
                if (m_secondaryProxyPort == null) {
                    m_secondaryProxyPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B8:
                if (m_primaryRegistrarIP == null) {
                    m_primaryRegistrarIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34B9:
                if (m_primaryRegistrarPort == null) {
                    m_primaryRegistrarPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34BA:
                if (m_secondaryRegistrarIP == null) {
                    m_secondaryRegistrarIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34BB:
                if (m_secondaryRegistrarPort == null) {
                    m_secondaryRegistrarPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34BC:
                if (m_domain_SIPService == null) {
                    m_domain_SIPService = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34BD:
                if (m_digitMap == null) {
                    m_digitMap = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34BF:
                if (m_registrationTimeOut == null) {
                    m_registrationTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x34A6, m_CallWating);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A7, m_CallerId);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A8, m_MessageWaitIndication);
        TLVHelper.addEmbeddedTLV(tlv, 0x34A5, m_ThreeWayCalling);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AE, m_description);
        TLVHelper.addEmbeddedTLV(tlv, 0x34BD, m_digitMap);
        TLVHelper.addEmbeddedTLV(tlv, 0x34BC, m_domain_SIPService);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B0, m_gateway);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B1, m_interface_SIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B2, m_port);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B4, m_primaryProxyIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B5, m_primaryProxyPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B8, m_primaryRegistrarIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B9, m_primaryRegistrarPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x34AF, m_protocol);
        TLVHelper.addEmbeddedTLV(tlv, 0x34BF, m_registrationTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B6, m_secondaryProxyIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B7, m_secondaryProxyPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x34BA, m_secondaryRegistrarIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x34BB, m_secondaryRegistrarPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x34B3, m_transport);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCallWating
     * 
     * @param CallWating
     */
    public void setCallWating(Integer CallWating)
    {
        this.m_CallWating = CallWating;
    } //-- void setCallWating(Integer) 

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
     * Method setMessageWaitIndication
     * 
     * @param MessageWaitIndication
     */
    public void setMessageWaitIndication(Integer MessageWaitIndication)
    {
        this.m_MessageWaitIndication = MessageWaitIndication;
    } //-- void setMessageWaitIndication(Integer) 

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
     * Method setThreeWayCalling
     * 
     * @param ThreeWayCalling
     */
    public void setThreeWayCalling(Integer ThreeWayCalling)
    {
        this.m_ThreeWayCalling = ThreeWayCalling;
    } //-- void setThreeWayCalling(Integer) 

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
     * Method setdescription
     * 
     * @param description
     */
    public void setdescription(String description)
    {
        this.m_description = description;
    } //-- void setdescription(String) 

    /**
     * Method setdigitMap
     * 
     * @param digitMap
     */
    public void setdigitMap(String digitMap)
    {
        this.m_digitMap = digitMap;
    } //-- void setdigitMap(String) 

    /**
     * Method setdomain_SIPService
     * 
     * @param domain_SIPService
     */
    public void setdomain_SIPService(String domain_SIPService)
    {
        this.m_domain_SIPService = domain_SIPService;
    } //-- void setdomain_SIPService(String) 

    /**
     * Method setgateway
     * 
     * @param gateway
     */
    public void setgateway(Integer gateway)
    {
        this.m_gateway = gateway;
    } //-- void setgateway(Integer) 

    /**
     * Method setinterface_SIP
     * 
     * @param interface_SIP
     */
    public void setinterface_SIP(String interface_SIP)
    {
        this.m_interface_SIP = interface_SIP;
    } //-- void setinterface_SIP(String) 

    /**
     * Method setport
     * 
     * @param port
     */
    public void setport(Integer port)
    {
        this.m_port = port;
    } //-- void setport(Integer) 

    /**
     * Method setprimaryProxyIP
     * 
     * @param primaryProxyIP
     */
    public void setprimaryProxyIP(String primaryProxyIP)
    {
        this.m_primaryProxyIP = primaryProxyIP;
    } //-- void setprimaryProxyIP(String) 

    /**
     * Method setprimaryProxyPort
     * 
     * @param primaryProxyPort
     */
    public void setprimaryProxyPort(Integer primaryProxyPort)
    {
        this.m_primaryProxyPort = primaryProxyPort;
    } //-- void setprimaryProxyPort(Integer) 

    /**
     * Method setprimaryRegistrarIP
     * 
     * @param primaryRegistrarIP
     */
    public void setprimaryRegistrarIP(String primaryRegistrarIP)
    {
        this.m_primaryRegistrarIP = primaryRegistrarIP;
    } //-- void setprimaryRegistrarIP(String) 

    /**
     * Method setprimaryRegistrarPort
     * 
     * @param primaryRegistrarPort
     */
    public void setprimaryRegistrarPort(Integer primaryRegistrarPort)
    {
        this.m_primaryRegistrarPort = primaryRegistrarPort;
    } //-- void setprimaryRegistrarPort(Integer) 

    /**
     * Method setprotocol
     * 
     * @param protocol
     */
    public void setprotocol(Integer protocol)
    {
        this.m_protocol = protocol;
    } //-- void setprotocol(Integer) 

    /**
     * Method setregistrationTimeOut
     * 
     * @param registrationTimeOut
     */
    public void setregistrationTimeOut(Integer registrationTimeOut)
    {
        this.m_registrationTimeOut = registrationTimeOut;
    } //-- void setregistrationTimeOut(Integer) 

    /**
     * Method setsecondaryProxyIP
     * 
     * @param secondaryProxyIP
     */
    public void setsecondaryProxyIP(String secondaryProxyIP)
    {
        this.m_secondaryProxyIP = secondaryProxyIP;
    } //-- void setsecondaryProxyIP(String) 

    /**
     * Method setsecondaryProxyPort
     * 
     * @param secondaryProxyPort
     */
    public void setsecondaryProxyPort(Integer secondaryProxyPort)
    {
        this.m_secondaryProxyPort = secondaryProxyPort;
    } //-- void setsecondaryProxyPort(Integer) 

    /**
     * Method setsecondaryRegistrarIP
     * 
     * @param secondaryRegistrarIP
     */
    public void setsecondaryRegistrarIP(String secondaryRegistrarIP)
    {
        this.m_secondaryRegistrarIP = secondaryRegistrarIP;
    } //-- void setsecondaryRegistrarIP(String) 

    /**
     * Method setsecondaryRegistrarPort
     * 
     * @param secondaryRegistrarPort
     */
    public void setsecondaryRegistrarPort(Integer secondaryRegistrarPort)
    {
        this.m_secondaryRegistrarPort = secondaryRegistrarPort;
    } //-- void setsecondaryRegistrarPort(Integer) 

    /**
     * Method settransport
     * 
     * @param transport
     */
    public void settransport(Integer transport)
    {
        this.m_transport = transport;
    } //-- void settransport(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6BLCSIPService ) {
            super.updateFields(obj1);
            B6BLCSIPService obj = (B6BLCSIPService)obj1;
           if (obj.getCallWating() != null )
               setCallWating((Integer)Helper.copy(obj.getCallWating()));
           if (obj.getCallerId() != null )
               setCallerId((Integer)Helper.copy(obj.getCallerId()));
           if (obj.getMessageWaitIndication() != null )
               setMessageWaitIndication((Integer)Helper.copy(obj.getMessageWaitIndication()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getThreeWayCalling() != null )
               setThreeWayCalling((Integer)Helper.copy(obj.getThreeWayCalling()));
           if (obj.getdescription() != null )
               setdescription((String)Helper.copy(obj.getdescription()));
           if (obj.getdigitMap() != null )
               setdigitMap((String)Helper.copy(obj.getdigitMap()));
           if (obj.getdomain_SIPService() != null )
               setdomain_SIPService((String)Helper.copy(obj.getdomain_SIPService()));
           if (obj.getgateway() != null )
               setgateway((Integer)Helper.copy(obj.getgateway()));
           if (obj.getinterface_SIP() != null )
               setinterface_SIP((String)Helper.copy(obj.getinterface_SIP()));
           if (obj.getport() != null )
               setport((Integer)Helper.copy(obj.getport()));
           if (obj.getprimaryProxyIP() != null )
               setprimaryProxyIP((String)Helper.copy(obj.getprimaryProxyIP()));
           if (obj.getprimaryProxyPort() != null )
               setprimaryProxyPort((Integer)Helper.copy(obj.getprimaryProxyPort()));
           if (obj.getprimaryRegistrarIP() != null )
               setprimaryRegistrarIP((String)Helper.copy(obj.getprimaryRegistrarIP()));
           if (obj.getprimaryRegistrarPort() != null )
               setprimaryRegistrarPort((Integer)Helper.copy(obj.getprimaryRegistrarPort()));
           if (obj.getprotocol() != null )
               setprotocol((Integer)Helper.copy(obj.getprotocol()));
           if (obj.getregistrationTimeOut() != null )
               setregistrationTimeOut((Integer)Helper.copy(obj.getregistrationTimeOut()));
           if (obj.getsecondaryProxyIP() != null )
               setsecondaryProxyIP((String)Helper.copy(obj.getsecondaryProxyIP()));
           if (obj.getsecondaryProxyPort() != null )
               setsecondaryProxyPort((Integer)Helper.copy(obj.getsecondaryProxyPort()));
           if (obj.getsecondaryRegistrarIP() != null )
               setsecondaryRegistrarIP((String)Helper.copy(obj.getsecondaryRegistrarIP()));
           if (obj.getsecondaryRegistrarPort() != null )
               setsecondaryRegistrarPort((Integer)Helper.copy(obj.getsecondaryRegistrarPort()));
           if (obj.gettransport() != null )
               settransport((Integer)Helper.copy(obj.gettransport()));
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
