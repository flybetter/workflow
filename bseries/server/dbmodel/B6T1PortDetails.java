/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
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
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.dbmodel.ICMSAid;

/**
 * Class B6T1PortDetails.
 * 
 * @version $Revision$ $Date$
 */
public class B6T1PortDetails extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AdminState
     */
    public Integer m_AdminState;

    /**
     * Field m_AisAlarm
     */
    public Integer m_AisAlarm;

    /**
     * Field m_AontProfileName
     */
    public String m_AontProfileName;

    /**
     * Field m_CesOAMMode
     */
    public Integer m_CesOAMMode;

    /**
     * Field m_ClockSource
     */
    public Integer m_ClockSource;

    /**
     * Field m_ConnectToHostPort
     */
    public String m_ConnectToHostPort;

    /**
     * Field m_EndpointType
     */
    public Integer m_EndpointType;

    /**
     * Field m_FacilityDataLink
     */
    public Integer m_FacilityDataLink;

    /**
     * Field m_Framing
     */
    public Integer m_Framing;

    /**
     * Field m_Info
     */
    public String m_Info;

    /**
     * Field m_JitterBuffer
     */
    public Integer m_JitterBuffer;

    /**
     * Field m_LineBuildOut
     */
    public Integer m_LineBuildOut;

    /**
     * Field m_LineCode
     */
    public Integer m_LineCode;

    /**
     * Field m_LocalLoopback
     */
    public Integer m_LocalLoopback;

    /**
     * Field m_Mode
     */
    public Integer m_Mode;

    /**
     * Field m_OntID
     */
    public String m_OntID;

    /**
     * Field m_Port
     */
    public Integer m_Port;

    /**
     * Field m_RemoteAlarm
     */
    public Integer m_RemoteAlarm;

    /**
     * Field m_RemoteConnectIdentifier
     */
    public Integer m_RemoteConnectIdentifier;

    /**
     * Field m_RemoteLoopBack
     */
    public Integer m_RemoteLoopBack;

    /**
     * Field m_SaServiceIdentifier
     */
    public Integer m_SaServiceIdentifier;

    /**
     * Field m_T1OverrideAttributes
     */
    public String m_T1OverrideAttributes;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6T1PortDetails";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6T1PortDetails() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6T1PortDetails()


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
        if( obj1 instanceof B6T1PortDetails ) {
            super.copyFields(obj1);
            B6T1PortDetails obj = (B6T1PortDetails)obj1;
            setAdminState((Integer)Helper.copy(obj.getAdminState()));
            setAisAlarm((Integer)Helper.copy(obj.getAisAlarm()));
            setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
            setCesOAMMode((Integer)Helper.copy(obj.getCesOAMMode()));
            setClockSource((Integer)Helper.copy(obj.getClockSource()));
            setConnectToHostPort((String)Helper.copy(obj.getConnectToHostPort()));
            setEndpointType((Integer)Helper.copy(obj.getEndpointType()));
            setFacilityDataLink((Integer)Helper.copy(obj.getFacilityDataLink()));
            setFraming((Integer)Helper.copy(obj.getFraming()));
            setInfo((String)Helper.copy(obj.getInfo()));
            setJitterBuffer((Integer)Helper.copy(obj.getJitterBuffer()));
            setLineBuildOut((Integer)Helper.copy(obj.getLineBuildOut()));
            setLineCode((Integer)Helper.copy(obj.getLineCode()));
            setLocalLoopback((Integer)Helper.copy(obj.getLocalLoopback()));
            setMode((Integer)Helper.copy(obj.getMode()));
            setOntID((String)Helper.copy(obj.getOntID()));
            setPort((Integer)Helper.copy(obj.getPort()));
            setRemoteAlarm((Integer)Helper.copy(obj.getRemoteAlarm()));
            setRemoteConnectIdentifier((Integer)Helper.copy(obj.getRemoteConnectIdentifier()));
            setRemoteLoopBack((Integer)Helper.copy(obj.getRemoteLoopBack()));
            setSaServiceIdentifier((Integer)Helper.copy(obj.getSaServiceIdentifier()));
            setT1OverrideAttributes((String)Helper.copy(obj.getT1OverrideAttributes()));
            setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAdminState
     */
    public Integer getAdminState()
    {
        return this.m_AdminState;
    } //-- Integer getAdminState() 

    /**
     * Method getAisAlarm
     */
    public Integer getAisAlarm()
    {
        return this.m_AisAlarm;
    } //-- Integer getAisAlarm() 

    /**
     * Method getAontProfileName
     */
    public String getAontProfileName()
    {
        return this.m_AontProfileName;
    } //-- String getAontProfileName() 

    /**
     * Method getCesOAMMode
     */
    public Integer getCesOAMMode()
    {
        return this.m_CesOAMMode;
    } //-- Integer getCesOAMMode() 

    /**
     * Method getClockSource
     */
    public Integer getClockSource()
    {
        return this.m_ClockSource;
    } //-- Integer getClockSource() 

    /**
     * Method getConnectToHostPort
     */
    public String getConnectToHostPort()
    {
        return this.m_ConnectToHostPort;
    } //-- String getConnectToHostPort() 

    /**
     * Method getEndpointType
     */
    public Integer getEndpointType()
    {
        return this.m_EndpointType;
    } //-- Integer getEndpointType() 

    /**
     * Method getFacilityDataLink
     */
    public Integer getFacilityDataLink()
    {
        return this.m_FacilityDataLink;
    } //-- Integer getFacilityDataLink() 

    /**
     * Method getFraming
     */
    public Integer getFraming()
    {
        return this.m_Framing;
    } //-- Integer getFraming() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getInfo
     */
    public String getInfo()
    {
        return this.m_Info;
    } //-- String getInfo() 

    /**
     * Method getJitterBuffer
     */
    public Integer getJitterBuffer()
    {
        return this.m_JitterBuffer;
    } //-- Integer getJitterBuffer() 

    /**
     * Method getLineBuildOut
     */
    public Integer getLineBuildOut()
    {
        return this.m_LineBuildOut;
    } //-- Integer getLineBuildOut() 

    /**
     * Method getLineCode
     */
    public Integer getLineCode()
    {
        return this.m_LineCode;
    } //-- Integer getLineCode() 

    /**
     * Method getLocalLoopback
     */
    public Integer getLocalLoopback()
    {
        return this.m_LocalLoopback;
    } //-- Integer getLocalLoopback() 

    /**
     * Method getMode
     */
    public Integer getMode()
    {
        return this.m_Mode;
    } //-- Integer getMode() 

    /**
     * Method getOntID
     */
    public String getOntID()
    {
        return this.m_OntID;
    } //-- String getOntID() 

    /**
     * Method getPort
     */
    public Integer getPort()
    {
        return this.m_Port;
    } //-- Integer getPort() 

    /**
     * Method getRemoteAlarm
     */
    public Integer getRemoteAlarm()
    {
        return this.m_RemoteAlarm;
    } //-- Integer getRemoteAlarm() 

    /**
     * Method getRemoteConnectIdentifier
     */
    public Integer getRemoteConnectIdentifier()
    {
        return this.m_RemoteConnectIdentifier;
    } //-- Integer getRemoteConnectIdentifier() 

    /**
     * Method getRemoteLoopBack
     */
    public Integer getRemoteLoopBack()
    {
        return this.m_RemoteLoopBack;
    } //-- Integer getRemoteLoopBack() 

    /**
     * Method getSaServiceIdentifier
     */
    public Integer getSaServiceIdentifier()
    {
        return this.m_SaServiceIdentifier;
    } //-- Integer getSaServiceIdentifier() 

    /**
     * Method getT1OverrideAttributes
     */
    public String getT1OverrideAttributes()
    {
        return this.m_T1OverrideAttributes;
    } //-- String getT1OverrideAttributes() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6T1PortDetails;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getid
     */
    public com.calix.system.server.dbmodel.ICMSAid getid()
    {
        return this.m_id;
    } //-- com.calix.system.server.dbmodel.ICMSAid getid() 

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
            case 0x3446:
                if (m_SaServiceIdentifier == null) {
                    m_SaServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3447:
                if (m_OntID == null) {
                    m_OntID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3448:
                if (m_AdminState == null) {
                    m_AdminState = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3449:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344A:
                if (m_ConnectToHostPort == null) {
                    m_ConnectToHostPort = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344B:
                if (m_LineCode == null) {
                    m_LineCode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344C:
                if (m_LineBuildOut == null) {
                    m_LineBuildOut = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344D:
                if (m_ClockSource == null) {
                    m_ClockSource = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344E:
                if (m_LocalLoopback == null) {
                    m_LocalLoopback = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x344F:
                if (m_JitterBuffer == null) {
                    m_JitterBuffer = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3450:
                if (m_Framing == null) {
                    m_Framing = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3451:
                if (m_CesOAMMode == null) {
                    m_CesOAMMode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3452:
                if (m_RemoteAlarm == null) {
                    m_RemoteAlarm = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3453:
                if (m_AisAlarm == null) {
                    m_AisAlarm = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3454:
                if (m_RemoteLoopBack == null) {
                    m_RemoteLoopBack = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3455:
                if (m_FacilityDataLink == null) {
                    m_FacilityDataLink = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3456:
                if (m_Mode == null) {
                    m_Mode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3457:
                if (m_RemoteConnectIdentifier == null) {
                    m_RemoteConnectIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3458:
                if (m_EndpointType == null) {
                    m_EndpointType = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3459:
                if (m_AontProfileName == null) {
                    m_AontProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x345A:
                if (m_T1OverrideAttributes == null) {
                    m_T1OverrideAttributes = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x345B:
                if (m_Info == null) {
                    m_Info = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3448, m_AdminState);
        TLVHelper.addEmbeddedTLV(tlv, 0x3453, m_AisAlarm);
        TLVHelper.addEmbeddedTLV(tlv, 0x3459, m_AontProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3451, m_CesOAMMode);
        TLVHelper.addEmbeddedTLV(tlv, 0x344D, m_ClockSource);
        TLVHelper.addEmbeddedTLV(tlv, 0x344A, m_ConnectToHostPort);
        TLVHelper.addEmbeddedTLV(tlv, 0x3458, m_EndpointType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3455, m_FacilityDataLink);
        TLVHelper.addEmbeddedTLV(tlv, 0x3450, m_Framing);
        TLVHelper.addEmbeddedTLV(tlv, 0x345B, m_Info);
        TLVHelper.addEmbeddedTLV(tlv, 0x344F, m_JitterBuffer);
        TLVHelper.addEmbeddedTLV(tlv, 0x344C, m_LineBuildOut);
        TLVHelper.addEmbeddedTLV(tlv, 0x344B, m_LineCode);
        TLVHelper.addEmbeddedTLV(tlv, 0x344E, m_LocalLoopback);
        TLVHelper.addEmbeddedTLV(tlv, 0x3456, m_Mode);
        TLVHelper.addEmbeddedTLV(tlv, 0x3447, m_OntID);
        TLVHelper.addEmbeddedTLV(tlv, 0x3449, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x3452, m_RemoteAlarm);
        TLVHelper.addEmbeddedTLV(tlv, 0x3457, m_RemoteConnectIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x3454, m_RemoteLoopBack);
        TLVHelper.addEmbeddedTLV(tlv, 0x3446, m_SaServiceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x345A, m_T1OverrideAttributes);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAdminState
     * 
     * @param AdminState
     */
    public void setAdminState(Integer AdminState)
    {
        this.m_AdminState = AdminState;
    } //-- void setAdminState(Integer) 

    /**
     * Method setAisAlarm
     * 
     * @param AisAlarm
     */
    public void setAisAlarm(Integer AisAlarm)
    {
        this.m_AisAlarm = AisAlarm;
    } //-- void setAisAlarm(Integer) 

    /**
     * Method setAontProfileName
     * 
     * @param AontProfileName
     */
    public void setAontProfileName(String AontProfileName)
    {
        this.m_AontProfileName = AontProfileName;
    } //-- void setAontProfileName(String) 

    /**
     * Method setCesOAMMode
     * 
     * @param CesOAMMode
     */
    public void setCesOAMMode(Integer CesOAMMode)
    {
        this.m_CesOAMMode = CesOAMMode;
    } //-- void setCesOAMMode(Integer) 

    /**
     * Method setClockSource
     * 
     * @param ClockSource
     */
    public void setClockSource(Integer ClockSource)
    {
        this.m_ClockSource = ClockSource;
    } //-- void setClockSource(Integer) 

    /**
     * Method setConnectToHostPort
     * 
     * @param ConnectToHostPort
     */
    public void setConnectToHostPort(String ConnectToHostPort)
    {
        this.m_ConnectToHostPort = ConnectToHostPort;
    } //-- void setConnectToHostPort(String) 

    /**
     * Method setEndpointType
     * 
     * @param EndpointType
     */
    public void setEndpointType(Integer EndpointType)
    {
        this.m_EndpointType = EndpointType;
    } //-- void setEndpointType(Integer) 

    /**
     * Method setFacilityDataLink
     * 
     * @param FacilityDataLink
     */
    public void setFacilityDataLink(Integer FacilityDataLink)
    {
        this.m_FacilityDataLink = FacilityDataLink;
    } //-- void setFacilityDataLink(Integer) 

    /**
     * Method setFraming
     * 
     * @param Framing
     */
    public void setFraming(Integer Framing)
    {
        this.m_Framing = Framing;
    } //-- void setFraming(Integer) 

    /**
     * Method setIdentityValue
     * 
     * @param id
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid id)
    {
        this.m_id = (com.calix.system.server.dbmodel.ICMSAid)id;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setInfo
     * 
     * @param Info
     */
    public void setInfo(String Info)
    {
        this.m_Info = Info;
    } //-- void setInfo(String) 

    /**
     * Method setJitterBuffer
     * 
     * @param JitterBuffer
     */
    public void setJitterBuffer(Integer JitterBuffer)
    {
        this.m_JitterBuffer = JitterBuffer;
    } //-- void setJitterBuffer(Integer) 

    /**
     * Method setLineBuildOut
     * 
     * @param LineBuildOut
     */
    public void setLineBuildOut(Integer LineBuildOut)
    {
        this.m_LineBuildOut = LineBuildOut;
    } //-- void setLineBuildOut(Integer) 

    /**
     * Method setLineCode
     * 
     * @param LineCode
     */
    public void setLineCode(Integer LineCode)
    {
        this.m_LineCode = LineCode;
    } //-- void setLineCode(Integer) 

    /**
     * Method setLocalLoopback
     * 
     * @param LocalLoopback
     */
    public void setLocalLoopback(Integer LocalLoopback)
    {
        this.m_LocalLoopback = LocalLoopback;
    } //-- void setLocalLoopback(Integer) 

    /**
     * Method setMode
     * 
     * @param Mode
     */
    public void setMode(Integer Mode)
    {
        this.m_Mode = Mode;
    } //-- void setMode(Integer) 

    /**
     * Method setOntID
     * 
     * @param OntID
     */
    public void setOntID(String OntID)
    {
        this.m_OntID = OntID;
    } //-- void setOntID(String) 

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
     * Method setRemoteAlarm
     * 
     * @param RemoteAlarm
     */
    public void setRemoteAlarm(Integer RemoteAlarm)
    {
        this.m_RemoteAlarm = RemoteAlarm;
    } //-- void setRemoteAlarm(Integer) 

    /**
     * Method setRemoteConnectIdentifier
     * 
     * @param RemoteConnectIdentifier
     */
    public void setRemoteConnectIdentifier(Integer RemoteConnectIdentifier)
    {
        this.m_RemoteConnectIdentifier = RemoteConnectIdentifier;
    } //-- void setRemoteConnectIdentifier(Integer) 

    /**
     * Method setRemoteLoopBack
     * 
     * @param RemoteLoopBack
     */
    public void setRemoteLoopBack(Integer RemoteLoopBack)
    {
        this.m_RemoteLoopBack = RemoteLoopBack;
    } //-- void setRemoteLoopBack(Integer) 

    /**
     * Method setSaServiceIdentifier
     * 
     * @param SaServiceIdentifier
     */
    public void setSaServiceIdentifier(Integer SaServiceIdentifier)
    {
        this.m_SaServiceIdentifier = SaServiceIdentifier;
    } //-- void setSaServiceIdentifier(Integer) 

    /**
     * Method setT1OverrideAttributes
     * 
     * @param T1OverrideAttributes
     */
    public void setT1OverrideAttributes(String T1OverrideAttributes)
    {
        this.m_T1OverrideAttributes = T1OverrideAttributes;
    } //-- void setT1OverrideAttributes(String) 

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
     * Method setid
     * 
     * @param id
     */
    public void setid(com.calix.system.server.dbmodel.ICMSAid id)
    {
        this.m_id = id;
    } //-- void setid(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6T1PortDetails ) {
            super.updateFields(obj1);
            B6T1PortDetails obj = (B6T1PortDetails)obj1;
           if (obj.getAdminState() != null )
               setAdminState((Integer)Helper.copy(obj.getAdminState()));
           if (obj.getAisAlarm() != null )
               setAisAlarm((Integer)Helper.copy(obj.getAisAlarm()));
           if (obj.getAontProfileName() != null )
               setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
           if (obj.getCesOAMMode() != null )
               setCesOAMMode((Integer)Helper.copy(obj.getCesOAMMode()));
           if (obj.getClockSource() != null )
               setClockSource((Integer)Helper.copy(obj.getClockSource()));
           if (obj.getConnectToHostPort() != null )
               setConnectToHostPort((String)Helper.copy(obj.getConnectToHostPort()));
           if (obj.getEndpointType() != null )
               setEndpointType((Integer)Helper.copy(obj.getEndpointType()));
           if (obj.getFacilityDataLink() != null )
               setFacilityDataLink((Integer)Helper.copy(obj.getFacilityDataLink()));
           if (obj.getFraming() != null )
               setFraming((Integer)Helper.copy(obj.getFraming()));
           if (obj.getInfo() != null )
               setInfo((String)Helper.copy(obj.getInfo()));
           if (obj.getJitterBuffer() != null )
               setJitterBuffer((Integer)Helper.copy(obj.getJitterBuffer()));
           if (obj.getLineBuildOut() != null )
               setLineBuildOut((Integer)Helper.copy(obj.getLineBuildOut()));
           if (obj.getLineCode() != null )
               setLineCode((Integer)Helper.copy(obj.getLineCode()));
           if (obj.getLocalLoopback() != null )
               setLocalLoopback((Integer)Helper.copy(obj.getLocalLoopback()));
           if (obj.getMode() != null )
               setMode((Integer)Helper.copy(obj.getMode()));
           if (obj.getOntID() != null )
               setOntID((String)Helper.copy(obj.getOntID()));
           if (obj.getPort() != null )
               setPort((Integer)Helper.copy(obj.getPort()));
           if (obj.getRemoteAlarm() != null )
               setRemoteAlarm((Integer)Helper.copy(obj.getRemoteAlarm()));
           if (obj.getRemoteConnectIdentifier() != null )
               setRemoteConnectIdentifier((Integer)Helper.copy(obj.getRemoteConnectIdentifier()));
           if (obj.getRemoteLoopBack() != null )
               setRemoteLoopBack((Integer)Helper.copy(obj.getRemoteLoopBack()));
           if (obj.getSaServiceIdentifier() != null )
               setSaServiceIdentifier((Integer)Helper.copy(obj.getSaServiceIdentifier()));
           if (obj.getT1OverrideAttributes() != null )
               setT1OverrideAttributes((String)Helper.copy(obj.getT1OverrideAttributes()));
           if (obj.getid() != null )
               setid((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getid()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
//BEGIN CODE
    
    
	public void setconvertId(Integer id) {
		this.m_id = new BSeriesAid(String.valueOf(id));
	} // -- void setconvertName(String)

	public Integer getconvertId() {
		return Integer.valueOf(this.m_id.toString());
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
    	C7Database db =C7Database.getInstance(); 
    	CMSObject obj = null;
    	try{
    	db.beginTransaction();
    	Collection resultList = db.executeQuery(this.getClass(), "id = "+this.getIdentityValue().toString(), -1, -1);
    	if(resultList != null&&!resultList.isEmpty()){
    			Iterator itr = resultList.iterator();
    			obj = (CMSObject)itr.next();
    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		db.close();
    	}
    	finally{
    		db.close();
    	}
    	if(obj == null)
    		throw new EMSDatabaseException(EMSDatabaseException._loadNonExistentFail_, "Object does not exist");
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