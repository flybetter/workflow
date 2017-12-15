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
 * Class B6BLCPOTSService.
 * 
 * @version $Revision$ $Date$
 */
public class B6BLCPOTSService extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_BlockNewCalls
     */
    public Integer m_BlockNewCalls;

    /**
     * Field m_Description
     */
    public String m_Description;

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
     * Field m_Gateway
     */
    public Integer m_Gateway;

    /**
     * Field m_InterfaceStr
     */
    public String m_InterfaceStr;

    /**
     * Field m_MaxWaitingDeply
     */
    public Integer m_MaxWaitingDeply;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Field m_NcsMode2833
     */
    public Integer m_NcsMode2833;

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
     * Field m_Passthru
     */
    public Integer m_Passthru;

    /**
     * Field m_PrimaryCallAgent
     */
    public String m_PrimaryCallAgent;

    /**
     * Field m_PrimaryCallPort
     */
    public Integer m_PrimaryCallPort;

    /**
     * Field m_Protocol
     */
    public Integer m_Protocol;

    /**
     * Field m_ProtocolOptions
     */
    public Integer m_ProtocolOptions;

    /**
     * Field m_ProtocolType
     */
    public Integer m_ProtocolType;

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
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6BLCPOTSService";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6BLCPOTSService() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6BLCPOTSService()


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
        if( obj1 instanceof B6BLCPOTSService ) {
            super.copyFields(obj1);
            B6BLCPOTSService obj = (B6BLCPOTSService)obj1;
            setBlockNewCalls((Integer)Helper.copy(obj.getBlockNewCalls()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setEndpointPrefix((String)Helper.copy(obj.getEndpointPrefix()));
            setExplicitdetection((Integer)Helper.copy(obj.getExplicitdetection()));
            setFlashhook((Integer)Helper.copy(obj.getFlashhook()));
            setGateway((Integer)Helper.copy(obj.getGateway()));
            setInterfaceStr((String)Helper.copy(obj.getInterfaceStr()));
            setMaxWaitingDeply((Integer)Helper.copy(obj.getMaxWaitingDeply()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setNcsMode2833((Integer)Helper.copy(obj.getNcsMode2833()));
            setOccamSingleContext((Integer)Helper.copy(obj.getOccamSingleContext()));
            setOffhook((Integer)Helper.copy(obj.getOffhook()));
            setOnhook((Integer)Helper.copy(obj.getOnhook()));
            setPassthru((Integer)Helper.copy(obj.getPassthru()));
            setPrimaryCallAgent((String)Helper.copy(obj.getPrimaryCallAgent()));
            setPrimaryCallPort((Integer)Helper.copy(obj.getPrimaryCallPort()));
            setProtocol((Integer)Helper.copy(obj.getProtocol()));
            setProtocolOptions((Integer)Helper.copy(obj.getProtocolOptions()));
            setProtocolType((Integer)Helper.copy(obj.getProtocolType()));
            setRestartDelay((Integer)Helper.copy(obj.getRestartDelay()));
            setRetryTimeOut((Integer)Helper.copy(obj.getRetryTimeOut()));
            setSecondaryCallPort((Integer)Helper.copy(obj.getSecondaryCallPort()));
            setSecondarycallagent((String)Helper.copy(obj.getSecondarycallagent()));
            setSecurity((Integer)Helper.copy(obj.getSecurity()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getBlockNewCalls
     */
    public Integer getBlockNewCalls()
    {
        return this.m_BlockNewCalls;
    } //-- Integer getBlockNewCalls() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

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
     * Method getInterfaceStr
     */
    public String getInterfaceStr()
    {
        return this.m_InterfaceStr;
    } //-- String getInterfaceStr() 

    /**
     * Method getMaxWaitingDeply
     */
    public Integer getMaxWaitingDeply()
    {
        return this.m_MaxWaitingDeply;
    } //-- Integer getMaxWaitingDeply() 

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
     * Method getPassthru
     */
    public Integer getPassthru()
    {
        return this.m_Passthru;
    } //-- Integer getPassthru() 

    /**
     * Method getPrimaryCallAgent
     */
    public String getPrimaryCallAgent()
    {
        return this.m_PrimaryCallAgent;
    } //-- String getPrimaryCallAgent() 

    /**
     * Method getPrimaryCallPort
     */
    public Integer getPrimaryCallPort()
    {
        return this.m_PrimaryCallPort;
    } //-- Integer getPrimaryCallPort() 

    /**
     * Method getProtocol
     */
    public Integer getProtocol()
    {
        return this.m_Protocol;
    } //-- Integer getProtocol() 

    /**
     * Method getProtocolOptions
     */
    public Integer getProtocolOptions()
    {
        return this.m_ProtocolOptions;
    } //-- Integer getProtocolOptions() 

    /**
     * Method getProtocolType
     */
    public Integer getProtocolType()
    {
        return this.m_ProtocolType;
    } //-- Integer getProtocolType() 

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
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6BLCPOTSService;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

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
            case 0x3444:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3445:
                if (m_Protocol == null) {
                    m_Protocol = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3446:
                if (m_Gateway == null) {
                    m_Gateway = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3447:
                if (m_PrimaryCallAgent == null) {
                    m_PrimaryCallAgent = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3448:
                if (m_PrimaryCallPort == null) {
                    m_PrimaryCallPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3449:
                if (m_Secondarycallagent == null) {
                    m_Secondarycallagent = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344A:
                if (m_SecondaryCallPort == null) {
                    m_SecondaryCallPort = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344B:
                if (m_ProtocolType == null) {
                    m_ProtocolType = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344C:
                if (m_ProtocolOptions == null) {
                    m_ProtocolOptions = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344D:
                if (m_InterfaceStr == null) {
                    m_InterfaceStr = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344E:
                if (m_EndpointPrefix == null) {
                    m_EndpointPrefix = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344F:
                if (m_MaxWaitingDeply == null) {
                    m_MaxWaitingDeply = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3450:
                if (m_RetryTimeOut == null) {
                    m_RetryTimeOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3451:
                if (m_RestartDelay == null) {
                    m_RestartDelay = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3452:
                if (m_BlockNewCalls == null) {
                    m_BlockNewCalls = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3453:
                if (m_NcsMode2833 == null) {
                    m_NcsMode2833 = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3454:
                if (m_Security == null) {
                    m_Security = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3455:
                if (m_Passthru == null) {
                    m_Passthru = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3456:
                if (m_Explicitdetection == null) {
                    m_Explicitdetection = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3457:
                if (m_OccamSingleContext == null) {
                    m_OccamSingleContext = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3458:
                if (m_Flashhook == null) {
                    m_Flashhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3459:
                if (m_Offhook == null) {
                    m_Offhook = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x345A:
                if (m_Onhook == null) {
                    m_Onhook = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3452, m_BlockNewCalls);
        TLVHelper.addEmbeddedTLV(tlv, 0x3444, m_Description);
        TLVHelper.addEmbeddedTLV(tlv, 0x344E, m_EndpointPrefix);
        TLVHelper.addEmbeddedTLV(tlv, 0x3456, m_Explicitdetection);
        TLVHelper.addEmbeddedTLV(tlv, 0x3458, m_Flashhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x3446, m_Gateway);
        TLVHelper.addEmbeddedTLV(tlv, 0x344D, m_InterfaceStr);
        TLVHelper.addEmbeddedTLV(tlv, 0x344F, m_MaxWaitingDeply);
        TLVHelper.addEmbeddedTLV(tlv, 0x3453, m_NcsMode2833);
        TLVHelper.addEmbeddedTLV(tlv, 0x3457, m_OccamSingleContext);
        TLVHelper.addEmbeddedTLV(tlv, 0x3459, m_Offhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x345A, m_Onhook);
        TLVHelper.addEmbeddedTLV(tlv, 0x3455, m_Passthru);
        TLVHelper.addEmbeddedTLV(tlv, 0x3447, m_PrimaryCallAgent);
        TLVHelper.addEmbeddedTLV(tlv, 0x3448, m_PrimaryCallPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x3445, m_Protocol);
        TLVHelper.addEmbeddedTLV(tlv, 0x344C, m_ProtocolOptions);
        TLVHelper.addEmbeddedTLV(tlv, 0x344B, m_ProtocolType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3451, m_RestartDelay);
        TLVHelper.addEmbeddedTLV(tlv, 0x3450, m_RetryTimeOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x344A, m_SecondaryCallPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x3449, m_Secondarycallagent);
        TLVHelper.addEmbeddedTLV(tlv, 0x3454, m_Security);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setBlockNewCalls
     * 
     * @param BlockNewCalls
     */
    public void setBlockNewCalls(Integer BlockNewCalls)
    {
        this.m_BlockNewCalls = BlockNewCalls;
    } //-- void setBlockNewCalls(Integer) 

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
     * Method setInterfaceStr
     * 
     * @param InterfaceStr
     */
    public void setInterfaceStr(String InterfaceStr)
    {
        this.m_InterfaceStr = InterfaceStr;
    } //-- void setInterfaceStr(String) 

    /**
     * Method setMaxWaitingDeply
     * 
     * @param MaxWaitingDeply
     */
    public void setMaxWaitingDeply(Integer MaxWaitingDeply)
    {
        this.m_MaxWaitingDeply = MaxWaitingDeply;
    } //-- void setMaxWaitingDeply(Integer) 

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
     * Method setPassthru
     * 
     * @param Passthru
     */
    public void setPassthru(Integer Passthru)
    {
        this.m_Passthru = Passthru;
    } //-- void setPassthru(Integer) 

    /**
     * Method setPrimaryCallAgent
     * 
     * @param PrimaryCallAgent
     */
    public void setPrimaryCallAgent(String PrimaryCallAgent)
    {
        this.m_PrimaryCallAgent = PrimaryCallAgent;
    } //-- void setPrimaryCallAgent(String) 

    /**
     * Method setPrimaryCallPort
     * 
     * @param PrimaryCallPort
     */
    public void setPrimaryCallPort(Integer PrimaryCallPort)
    {
        this.m_PrimaryCallPort = PrimaryCallPort;
    } //-- void setPrimaryCallPort(Integer) 

    /**
     * Method setProtocol
     * 
     * @param Protocol
     */
    public void setProtocol(Integer Protocol)
    {
        this.m_Protocol = Protocol;
    } //-- void setProtocol(Integer) 

    /**
     * Method setProtocolOptions
     * 
     * @param ProtocolOptions
     */
    public void setProtocolOptions(Integer ProtocolOptions)
    {
        this.m_ProtocolOptions = ProtocolOptions;
    } //-- void setProtocolOptions(Integer) 

    /**
     * Method setProtocolType
     * 
     * @param ProtocolType
     */
    public void setProtocolType(Integer ProtocolType)
    {
        this.m_ProtocolType = ProtocolType;
    } //-- void setProtocolType(Integer) 

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
        if( obj1 instanceof B6BLCPOTSService ) {
            super.updateFields(obj1);
            B6BLCPOTSService obj = (B6BLCPOTSService)obj1;
           if (obj.getBlockNewCalls() != null )
               setBlockNewCalls((Integer)Helper.copy(obj.getBlockNewCalls()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getEndpointPrefix() != null )
               setEndpointPrefix((String)Helper.copy(obj.getEndpointPrefix()));
           if (obj.getExplicitdetection() != null )
               setExplicitdetection((Integer)Helper.copy(obj.getExplicitdetection()));
           if (obj.getFlashhook() != null )
               setFlashhook((Integer)Helper.copy(obj.getFlashhook()));
           if (obj.getGateway() != null )
               setGateway((Integer)Helper.copy(obj.getGateway()));
           if (obj.getInterfaceStr() != null )
               setInterfaceStr((String)Helper.copy(obj.getInterfaceStr()));
           if (obj.getMaxWaitingDeply() != null )
               setMaxWaitingDeply((Integer)Helper.copy(obj.getMaxWaitingDeply()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getNcsMode2833() != null )
               setNcsMode2833((Integer)Helper.copy(obj.getNcsMode2833()));
           if (obj.getOccamSingleContext() != null )
               setOccamSingleContext((Integer)Helper.copy(obj.getOccamSingleContext()));
           if (obj.getOffhook() != null )
               setOffhook((Integer)Helper.copy(obj.getOffhook()));
           if (obj.getOnhook() != null )
               setOnhook((Integer)Helper.copy(obj.getOnhook()));
           if (obj.getPassthru() != null )
               setPassthru((Integer)Helper.copy(obj.getPassthru()));
           if (obj.getPrimaryCallAgent() != null )
               setPrimaryCallAgent((String)Helper.copy(obj.getPrimaryCallAgent()));
           if (obj.getPrimaryCallPort() != null )
               setPrimaryCallPort((Integer)Helper.copy(obj.getPrimaryCallPort()));
           if (obj.getProtocol() != null )
               setProtocol((Integer)Helper.copy(obj.getProtocol()));
           if (obj.getProtocolOptions() != null )
               setProtocolOptions((Integer)Helper.copy(obj.getProtocolOptions()));
           if (obj.getProtocolType() != null )
               setProtocolType((Integer)Helper.copy(obj.getProtocolType()));
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
