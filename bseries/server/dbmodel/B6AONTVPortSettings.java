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
 * Class B6AONTVPortSettings.
 * 
 * @version $Revision$ $Date$
 */
public class B6AONTVPortSettings extends com.calix.system.server.dbmodel.CMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_AdminState
     */
    public Integer m_AdminState;

    /**
     * Field m_AontProfileName
     */
    public String m_AontProfileName;

    /**
     * Field m_AuthUserName
     */
    public String m_AuthUserName;

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
     * Field m_DelayCompensation
     */
    public Integer m_DelayCompensation;

    /**
     * Field m_DisplayName
     */
    public String m_DisplayName;

    /**
     * Field m_EchoCancel
     */
    public Integer m_EchoCancel;

    /**
     * Field m_EchoCancelNLP
     */
    public Integer m_EchoCancelNLP;

    /**
     * Field m_EchoTailLength
     */
    public Integer m_EchoTailLength;

    /**
     * Field m_FaxCNGDetection
     */
    public Integer m_FaxCNGDetection;

    /**
     * Field m_ForwardDisconnectTime
     */
    public Integer m_ForwardDisconnectTime;

    /**
     * Field m_Info
     */
    public String m_Info;

    /**
     * Field m_InputGain
     */
    public Integer m_InputGain;

    /**
     * Field m_JitterDelay
     */
    public Integer m_JitterDelay;

    /**
     * Field m_LineMode
     */
    public Integer m_LineMode;

    /**
     * Field m_LossPlan
     */
    public Integer m_LossPlan;

    /**
     * Field m_MWI
     */
    public Integer m_MWI;

    /**
     * Field m_Name
     */
    public String m_Name;

    /**
     * Field m_NlpDelay
     */
    public Integer m_NlpDelay;

    /**
     * Field m_OntID
     */
    public String m_OntID;

    /**
     * Field m_OutputGain
     */
    public Integer m_OutputGain;

    /**
     * Field m_OverWriteAttributes
     */
    public String m_OverWriteAttributes;

    /**
     * Field m_PacketizationPeriod
     */
    public Integer m_PacketizationPeriod;

    /**
     * Field m_Password
     */
    public String m_Password;

    /**
     * Field m_Port
     */
    public Integer m_Port;

    /**
     * Field m_ReceiveLevel
     */
    public Integer m_ReceiveLevel;

    /**
     * Field m_RingFrequency
     */
    public Integer m_RingFrequency;

    /**
     * Field m_RtpSignalType
     */
    public Integer m_RtpSignalType;

    /**
     * Field m_ServiceIdentifier
     */
    public Integer m_ServiceIdentifier;

    /**
     * Field m_SlicReceiveLevel
     */
    public Integer m_SlicReceiveLevel;

    /**
     * Field m_SlicTransmitLevel
     */
    public Integer m_SlicTransmitLevel;

    /**
     * Field m_ThreeWayCalling
     */
    public Integer m_ThreeWayCalling;

    /**
     * Field m_ToneVolume
     */
    public Integer m_ToneVolume;

    /**
     * Field m_TransmitLevel
     */
    public Integer m_TransmitLevel;

    /**
     * Field m_id
     */
    public com.calix.system.server.dbmodel.ICMSAid m_id;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6AONTVPortSettings";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6AONTVPortSettings() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6AONTVPortSettings()


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
        if( obj1 instanceof B6AONTVPortSettings ) {
            super.copyFields(obj1);
            B6AONTVPortSettings obj = (B6AONTVPortSettings)obj1;
            setAdminState((Integer)Helper.copy(obj.getAdminState()));
            setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
            setAuthUserName((String)Helper.copy(obj.getAuthUserName()));
            setCallTransfer((Integer)Helper.copy(obj.getCallTransfer()));
            setCallWaiting((Integer)Helper.copy(obj.getCallWaiting()));
            setCallerId((Integer)Helper.copy(obj.getCallerId()));
            setDelayCompensation((Integer)Helper.copy(obj.getDelayCompensation()));
            setDisplayName((String)Helper.copy(obj.getDisplayName()));
            setEchoCancel((Integer)Helper.copy(obj.getEchoCancel()));
            setEchoCancelNLP((Integer)Helper.copy(obj.getEchoCancelNLP()));
            setEchoTailLength((Integer)Helper.copy(obj.getEchoTailLength()));
            setFaxCNGDetection((Integer)Helper.copy(obj.getFaxCNGDetection()));
            setForwardDisconnectTime((Integer)Helper.copy(obj.getForwardDisconnectTime()));
            setInfo((String)Helper.copy(obj.getInfo()));
            setInputGain((Integer)Helper.copy(obj.getInputGain()));
            setJitterDelay((Integer)Helper.copy(obj.getJitterDelay()));
            setLineMode((Integer)Helper.copy(obj.getLineMode()));
            setLossPlan((Integer)Helper.copy(obj.getLossPlan()));
            setMWI((Integer)Helper.copy(obj.getMWI()));
            setName((String)Helper.copy(obj.getName()));
            setNlpDelay((Integer)Helper.copy(obj.getNlpDelay()));
            setOntID((String)Helper.copy(obj.getOntID()));
            setOutputGain((Integer)Helper.copy(obj.getOutputGain()));
            setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
            setPacketizationPeriod((Integer)Helper.copy(obj.getPacketizationPeriod()));
            setPassword((String)Helper.copy(obj.getPassword()));
            setPort((Integer)Helper.copy(obj.getPort()));
            setReceiveLevel((Integer)Helper.copy(obj.getReceiveLevel()));
            setRingFrequency((Integer)Helper.copy(obj.getRingFrequency()));
            setRtpSignalType((Integer)Helper.copy(obj.getRtpSignalType()));
            setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
            setSlicReceiveLevel((Integer)Helper.copy(obj.getSlicReceiveLevel()));
            setSlicTransmitLevel((Integer)Helper.copy(obj.getSlicTransmitLevel()));
            setThreeWayCalling((Integer)Helper.copy(obj.getThreeWayCalling()));
            setToneVolume((Integer)Helper.copy(obj.getToneVolume()));
            setTransmitLevel((Integer)Helper.copy(obj.getTransmitLevel()));
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
     * Method getAontProfileName
     */
    public String getAontProfileName()
    {
        return this.m_AontProfileName;
    } //-- String getAontProfileName() 

    /**
     * Method getAuthUserName
     */
    public String getAuthUserName()
    {
        return this.m_AuthUserName;
    } //-- String getAuthUserName() 

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
     * Method getDelayCompensation
     */
    public Integer getDelayCompensation()
    {
        return this.m_DelayCompensation;
    } //-- Integer getDelayCompensation() 

    /**
     * Method getDisplayName
     */
    public String getDisplayName()
    {
        return this.m_DisplayName;
    } //-- String getDisplayName() 

    /**
     * Method getEchoCancel
     */
    public Integer getEchoCancel()
    {
        return this.m_EchoCancel;
    } //-- Integer getEchoCancel() 

    /**
     * Method getEchoCancelNLP
     */
    public Integer getEchoCancelNLP()
    {
        return this.m_EchoCancelNLP;
    } //-- Integer getEchoCancelNLP() 

    /**
     * Method getEchoTailLength
     */
    public Integer getEchoTailLength()
    {
        return this.m_EchoTailLength;
    } //-- Integer getEchoTailLength() 

    /**
     * Method getFaxCNGDetection
     */
    public Integer getFaxCNGDetection()
    {
        return this.m_FaxCNGDetection;
    } //-- Integer getFaxCNGDetection() 

    /**
     * Method getForwardDisconnectTime
     */
    public Integer getForwardDisconnectTime()
    {
        return this.m_ForwardDisconnectTime;
    } //-- Integer getForwardDisconnectTime() 

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
     * Method getInputGain
     */
    public Integer getInputGain()
    {
        return this.m_InputGain;
    } //-- Integer getInputGain() 

    /**
     * Method getJitterDelay
     */
    public Integer getJitterDelay()
    {
        return this.m_JitterDelay;
    } //-- Integer getJitterDelay() 

    /**
     * Method getLineMode
     */
    public Integer getLineMode()
    {
        return this.m_LineMode;
    } //-- Integer getLineMode() 

    /**
     * Method getLossPlan
     */
    public Integer getLossPlan()
    {
        return this.m_LossPlan;
    } //-- Integer getLossPlan() 

    /**
     * Method getMWI
     */
    public Integer getMWI()
    {
        return this.m_MWI;
    } //-- Integer getMWI() 

    /**
     * Method getName
     */
    public String getName()
    {
        return this.m_Name;
    } //-- String getName() 

    /**
     * Method getNlpDelay
     */
    public Integer getNlpDelay()
    {
        return this.m_NlpDelay;
    } //-- Integer getNlpDelay() 

    /**
     * Method getOntID
     */
    public String getOntID()
    {
        return this.m_OntID;
    } //-- String getOntID() 

    /**
     * Method getOutputGain
     */
    public Integer getOutputGain()
    {
        return this.m_OutputGain;
    } //-- Integer getOutputGain() 

    /**
     * Method getOverWriteAttributes
     */
    public String getOverWriteAttributes()
    {
        return this.m_OverWriteAttributes;
    } //-- String getOverWriteAttributes() 

    /**
     * Method getPacketizationPeriod
     */
    public Integer getPacketizationPeriod()
    {
        return this.m_PacketizationPeriod;
    } //-- Integer getPacketizationPeriod() 

    /**
     * Method getPassword
     */
    public String getPassword()
    {
        return this.m_Password;
    } //-- String getPassword() 

    /**
     * Method getPort
     */
    public Integer getPort()
    {
        return this.m_Port;
    } //-- Integer getPort() 

    /**
     * Method getReceiveLevel
     */
    public Integer getReceiveLevel()
    {
        return this.m_ReceiveLevel;
    } //-- Integer getReceiveLevel() 

    /**
     * Method getRingFrequency
     */
    public Integer getRingFrequency()
    {
        return this.m_RingFrequency;
    } //-- Integer getRingFrequency() 

    /**
     * Method getRtpSignalType
     */
    public Integer getRtpSignalType()
    {
        return this.m_RtpSignalType;
    } //-- Integer getRtpSignalType() 

    /**
     * Method getServiceIdentifier
     */
    public Integer getServiceIdentifier()
    {
        return this.m_ServiceIdentifier;
    } //-- Integer getServiceIdentifier() 

    /**
     * Method getSlicReceiveLevel
     */
    public Integer getSlicReceiveLevel()
    {
        return this.m_SlicReceiveLevel;
    } //-- Integer getSlicReceiveLevel() 

    /**
     * Method getSlicTransmitLevel
     */
    public Integer getSlicTransmitLevel()
    {
        return this.m_SlicTransmitLevel;
    } //-- Integer getSlicTransmitLevel() 

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
        return BseriesTlvConstants.B6AONTVPortSettings;
    } //-- int getTlvType() 

    /**
     * Method getToneVolume
     */
    public Integer getToneVolume()
    {
        return this.m_ToneVolume;
    } //-- Integer getToneVolume() 

    /**
     * Method getTransmitLevel
     */
    public Integer getTransmitLevel()
    {
        return this.m_TransmitLevel;
    } //-- Integer getTransmitLevel() 

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
            case 0x3423:
                if (m_ServiceIdentifier == null) {
                    m_ServiceIdentifier = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3424:
                if (m_OntID == null) {
                    m_OntID = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3425:
                if (m_Name == null) {
                    m_Name = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3426:
                if (m_AdminState == null) {
                    m_AdminState = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3427:
                if (m_Port == null) {
                    m_Port = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3428:
                if (m_Password == null) {
                    m_Password = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3429:
                if (m_InputGain == null) {
                    m_InputGain = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x342A:
                if (m_ReceiveLevel == null) {
                    m_ReceiveLevel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x342B:
                if (m_OutputGain == null) {
                    m_OutputGain = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x342C:
                if (m_TransmitLevel == null) {
                    m_TransmitLevel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x342D:
                if (m_ToneVolume == null) {
                    m_ToneVolume = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x342E:
                if (m_EchoCancel == null) {
                    m_EchoCancel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x342F:
                if (m_CallWaiting == null) {
                    m_CallWaiting = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3430:
                if (m_CallTransfer == null) {
                    m_CallTransfer = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3431:
                if (m_MWI == null) {
                    m_MWI = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3432:
                if (m_ThreeWayCalling == null) {
                    m_ThreeWayCalling = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3433:
                if (m_OverWriteAttributes == null) {
                    m_OverWriteAttributes = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3434:
                if (m_AontProfileName == null) {
                    m_AontProfileName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3435:
                if (m_AuthUserName == null) {
                    m_AuthUserName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3436:
                if (m_DisplayName == null) {
                    m_DisplayName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3437:
                if (m_SlicTransmitLevel == null) {
                    m_SlicTransmitLevel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3438:
                if (m_SlicReceiveLevel == null) {
                    m_SlicReceiveLevel = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3439:
                if (m_RingFrequency == null) {
                    m_RingFrequency = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343A:
                if (m_EchoTailLength == null) {
                    m_EchoTailLength = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343B:
                if (m_LineMode == null) {
                    m_LineMode = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343C:
                if (m_RtpSignalType == null) {
                    m_RtpSignalType = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343D:
                if (m_EchoCancelNLP == null) {
                    m_EchoCancelNLP = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343E:
                if (m_DelayCompensation == null) {
                    m_DelayCompensation = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x343F:
                if (m_NlpDelay == null) {
                    m_NlpDelay = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3440:
                if (m_CallerId == null) {
                    m_CallerId = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3441:
                if (m_FaxCNGDetection == null) {
                    m_FaxCNGDetection = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3442:
                if (m_JitterDelay == null) {
                    m_JitterDelay = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3443:
                if (m_PacketizationPeriod == null) {
                    m_PacketizationPeriod = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3444:
                if (m_ForwardDisconnectTime == null) {
                    m_ForwardDisconnectTime = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3445:
                if (m_Info == null) {
                    m_Info = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x34CE:
                if (m_LossPlan == null) {
                    m_LossPlan = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3426, m_AdminState);
        TLVHelper.addEmbeddedTLV(tlv, 0x3434, m_AontProfileName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3435, m_AuthUserName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3430, m_CallTransfer);
        TLVHelper.addEmbeddedTLV(tlv, 0x342F, m_CallWaiting);
        TLVHelper.addEmbeddedTLV(tlv, 0x3440, m_CallerId);
        TLVHelper.addEmbeddedTLV(tlv, 0x343E, m_DelayCompensation);
        TLVHelper.addEmbeddedTLV(tlv, 0x3436, m_DisplayName);
        TLVHelper.addEmbeddedTLV(tlv, 0x342E, m_EchoCancel);
        TLVHelper.addEmbeddedTLV(tlv, 0x343D, m_EchoCancelNLP);
        TLVHelper.addEmbeddedTLV(tlv, 0x343A, m_EchoTailLength);
        TLVHelper.addEmbeddedTLV(tlv, 0x3441, m_FaxCNGDetection);
        TLVHelper.addEmbeddedTLV(tlv, 0x3444, m_ForwardDisconnectTime);
        TLVHelper.addEmbeddedTLV(tlv, 0x3445, m_Info);
        TLVHelper.addEmbeddedTLV(tlv, 0x3429, m_InputGain);
        TLVHelper.addEmbeddedTLV(tlv, 0x3442, m_JitterDelay);
        TLVHelper.addEmbeddedTLV(tlv, 0x343B, m_LineMode);
        TLVHelper.addEmbeddedTLV(tlv, 0x34CE, m_LossPlan);
        TLVHelper.addEmbeddedTLV(tlv, 0x3431, m_MWI);
        TLVHelper.addEmbeddedTLV(tlv, 0x3425, m_Name);
        TLVHelper.addEmbeddedTLV(tlv, 0x343F, m_NlpDelay);
        TLVHelper.addEmbeddedTLV(tlv, 0x3424, m_OntID);
        TLVHelper.addEmbeddedTLV(tlv, 0x342B, m_OutputGain);
        TLVHelper.addEmbeddedTLV(tlv, 0x3433, m_OverWriteAttributes);
        TLVHelper.addEmbeddedTLV(tlv, 0x3443, m_PacketizationPeriod);
        TLVHelper.addEmbeddedTLV(tlv, 0x3428, m_Password);
        TLVHelper.addEmbeddedTLV(tlv, 0x3427, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x342A, m_ReceiveLevel);
        TLVHelper.addEmbeddedTLV(tlv, 0x3439, m_RingFrequency);
        TLVHelper.addEmbeddedTLV(tlv, 0x343C, m_RtpSignalType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3423, m_ServiceIdentifier);
        TLVHelper.addEmbeddedTLV(tlv, 0x3438, m_SlicReceiveLevel);
        TLVHelper.addEmbeddedTLV(tlv, 0x3437, m_SlicTransmitLevel);
        TLVHelper.addEmbeddedTLV(tlv, 0x3432, m_ThreeWayCalling);
        TLVHelper.addEmbeddedTLV(tlv, 0x342D, m_ToneVolume);
        TLVHelper.addEmbeddedTLV(tlv, 0x342C, m_TransmitLevel);
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
     * Method setAontProfileName
     * 
     * @param AontProfileName
     */
    public void setAontProfileName(String AontProfileName)
    {
        this.m_AontProfileName = AontProfileName;
    } //-- void setAontProfileName(String) 

    /**
     * Method setAuthUserName
     * 
     * @param AuthUserName
     */
    public void setAuthUserName(String AuthUserName)
    {
        this.m_AuthUserName = AuthUserName;
    } //-- void setAuthUserName(String) 

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
     * Method setDelayCompensation
     * 
     * @param DelayCompensation
     */
    public void setDelayCompensation(Integer DelayCompensation)
    {
        this.m_DelayCompensation = DelayCompensation;
    } //-- void setDelayCompensation(Integer) 

    /**
     * Method setDisplayName
     * 
     * @param DisplayName
     */
    public void setDisplayName(String DisplayName)
    {
        this.m_DisplayName = DisplayName;
    } //-- void setDisplayName(String) 

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
     * Method setEchoCancelNLP
     * 
     * @param EchoCancelNLP
     */
    public void setEchoCancelNLP(Integer EchoCancelNLP)
    {
        this.m_EchoCancelNLP = EchoCancelNLP;
    } //-- void setEchoCancelNLP(Integer) 

    /**
     * Method setEchoTailLength
     * 
     * @param EchoTailLength
     */
    public void setEchoTailLength(Integer EchoTailLength)
    {
        this.m_EchoTailLength = EchoTailLength;
    } //-- void setEchoTailLength(Integer) 

    /**
     * Method setFaxCNGDetection
     * 
     * @param FaxCNGDetection
     */
    public void setFaxCNGDetection(Integer FaxCNGDetection)
    {
        this.m_FaxCNGDetection = FaxCNGDetection;
    } //-- void setFaxCNGDetection(Integer) 

    /**
     * Method setForwardDisconnectTime
     * 
     * @param ForwardDisconnectTime
     */
    public void setForwardDisconnectTime(Integer ForwardDisconnectTime)
    {
        this.m_ForwardDisconnectTime = ForwardDisconnectTime;
    } //-- void setForwardDisconnectTime(Integer) 

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
     * Method setInputGain
     * 
     * @param InputGain
     */
    public void setInputGain(Integer InputGain)
    {
        this.m_InputGain = InputGain;
    } //-- void setInputGain(Integer) 

    /**
     * Method setJitterDelay
     * 
     * @param JitterDelay
     */
    public void setJitterDelay(Integer JitterDelay)
    {
        this.m_JitterDelay = JitterDelay;
    } //-- void setJitterDelay(Integer) 

    /**
     * Method setLineMode
     * 
     * @param LineMode
     */
    public void setLineMode(Integer LineMode)
    {
        this.m_LineMode = LineMode;
    } //-- void setLineMode(Integer) 

    /**
     * Method setLossPlan
     * 
     * @param LossPlan
     */
    public void setLossPlan(Integer LossPlan)
    {
        this.m_LossPlan = LossPlan;
    } //-- void setLossPlan(Integer) 

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
     * Method setName
     * 
     * @param Name
     */
    public void setName(String Name)
    {
        this.m_Name = Name;
    } //-- void setName(String) 

    /**
     * Method setNlpDelay
     * 
     * @param NlpDelay
     */
    public void setNlpDelay(Integer NlpDelay)
    {
        this.m_NlpDelay = NlpDelay;
    } //-- void setNlpDelay(Integer) 

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
     * Method setOutputGain
     * 
     * @param OutputGain
     */
    public void setOutputGain(Integer OutputGain)
    {
        this.m_OutputGain = OutputGain;
    } //-- void setOutputGain(Integer) 

    /**
     * Method setOverWriteAttributes
     * 
     * @param OverWriteAttributes
     */
    public void setOverWriteAttributes(String OverWriteAttributes)
    {
        this.m_OverWriteAttributes = OverWriteAttributes;
    } //-- void setOverWriteAttributes(String) 

    /**
     * Method setPacketizationPeriod
     * 
     * @param PacketizationPeriod
     */
    public void setPacketizationPeriod(Integer PacketizationPeriod)
    {
        this.m_PacketizationPeriod = PacketizationPeriod;
    } //-- void setPacketizationPeriod(Integer) 

    /**
     * Method setPassword
     * 
     * @param Password
     */
    public void setPassword(String Password)
    {
        this.m_Password = Password;
    } //-- void setPassword(String) 

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
     * Method setReceiveLevel
     * 
     * @param ReceiveLevel
     */
    public void setReceiveLevel(Integer ReceiveLevel)
    {
        this.m_ReceiveLevel = ReceiveLevel;
    } //-- void setReceiveLevel(Integer) 

    /**
     * Method setRingFrequency
     * 
     * @param RingFrequency
     */
    public void setRingFrequency(Integer RingFrequency)
    {
        this.m_RingFrequency = RingFrequency;
    } //-- void setRingFrequency(Integer) 

    /**
     * Method setRtpSignalType
     * 
     * @param RtpSignalType
     */
    public void setRtpSignalType(Integer RtpSignalType)
    {
        this.m_RtpSignalType = RtpSignalType;
    } //-- void setRtpSignalType(Integer) 

    /**
     * Method setServiceIdentifier
     * 
     * @param ServiceIdentifier
     */
    public void setServiceIdentifier(Integer ServiceIdentifier)
    {
        this.m_ServiceIdentifier = ServiceIdentifier;
    } //-- void setServiceIdentifier(Integer) 

    /**
     * Method setSlicReceiveLevel
     * 
     * @param SlicReceiveLevel
     */
    public void setSlicReceiveLevel(Integer SlicReceiveLevel)
    {
        this.m_SlicReceiveLevel = SlicReceiveLevel;
    } //-- void setSlicReceiveLevel(Integer) 

    /**
     * Method setSlicTransmitLevel
     * 
     * @param SlicTransmitLevel
     */
    public void setSlicTransmitLevel(Integer SlicTransmitLevel)
    {
        this.m_SlicTransmitLevel = SlicTransmitLevel;
    } //-- void setSlicTransmitLevel(Integer) 

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
     * Method setToneVolume
     * 
     * @param ToneVolume
     */
    public void setToneVolume(Integer ToneVolume)
    {
        this.m_ToneVolume = ToneVolume;
    } //-- void setToneVolume(Integer) 

    /**
     * Method setTransmitLevel
     * 
     * @param TransmitLevel
     */
    public void setTransmitLevel(Integer TransmitLevel)
    {
        this.m_TransmitLevel = TransmitLevel;
    } //-- void setTransmitLevel(Integer) 

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
        if( obj1 instanceof B6AONTVPortSettings ) {
            super.updateFields(obj1);
            B6AONTVPortSettings obj = (B6AONTVPortSettings)obj1;
           if (obj.getAdminState() != null )
               setAdminState((Integer)Helper.copy(obj.getAdminState()));
           if (obj.getAontProfileName() != null )
               setAontProfileName((String)Helper.copy(obj.getAontProfileName()));
           if (obj.getAuthUserName() != null )
               setAuthUserName((String)Helper.copy(obj.getAuthUserName()));
           if (obj.getCallTransfer() != null )
               setCallTransfer((Integer)Helper.copy(obj.getCallTransfer()));
           if (obj.getCallWaiting() != null )
               setCallWaiting((Integer)Helper.copy(obj.getCallWaiting()));
           if (obj.getCallerId() != null )
               setCallerId((Integer)Helper.copy(obj.getCallerId()));
           if (obj.getDelayCompensation() != null )
               setDelayCompensation((Integer)Helper.copy(obj.getDelayCompensation()));
           if (obj.getDisplayName() != null )
               setDisplayName((String)Helper.copy(obj.getDisplayName()));
           if (obj.getEchoCancel() != null )
               setEchoCancel((Integer)Helper.copy(obj.getEchoCancel()));
           if (obj.getEchoCancelNLP() != null )
               setEchoCancelNLP((Integer)Helper.copy(obj.getEchoCancelNLP()));
           if (obj.getEchoTailLength() != null )
               setEchoTailLength((Integer)Helper.copy(obj.getEchoTailLength()));
           if (obj.getFaxCNGDetection() != null )
               setFaxCNGDetection((Integer)Helper.copy(obj.getFaxCNGDetection()));
           if (obj.getForwardDisconnectTime() != null )
               setForwardDisconnectTime((Integer)Helper.copy(obj.getForwardDisconnectTime()));
           if (obj.getInfo() != null )
               setInfo((String)Helper.copy(obj.getInfo()));
           if (obj.getInputGain() != null )
               setInputGain((Integer)Helper.copy(obj.getInputGain()));
           if (obj.getJitterDelay() != null )
               setJitterDelay((Integer)Helper.copy(obj.getJitterDelay()));
           if (obj.getLineMode() != null )
               setLineMode((Integer)Helper.copy(obj.getLineMode()));
           if (obj.getLossPlan() != null )
               setLossPlan((Integer)Helper.copy(obj.getLossPlan()));
           if (obj.getMWI() != null )
               setMWI((Integer)Helper.copy(obj.getMWI()));
           if (obj.getName() != null )
               setName((String)Helper.copy(obj.getName()));
           if (obj.getNlpDelay() != null )
               setNlpDelay((Integer)Helper.copy(obj.getNlpDelay()));
           if (obj.getOntID() != null )
               setOntID((String)Helper.copy(obj.getOntID()));
           if (obj.getOutputGain() != null )
               setOutputGain((Integer)Helper.copy(obj.getOutputGain()));
           if (obj.getOverWriteAttributes() != null )
               setOverWriteAttributes((String)Helper.copy(obj.getOverWriteAttributes()));
           if (obj.getPacketizationPeriod() != null )
               setPacketizationPeriod((Integer)Helper.copy(obj.getPacketizationPeriod()));
           if (obj.getPassword() != null )
               setPassword((String)Helper.copy(obj.getPassword()));
           if (obj.getPort() != null )
               setPort((Integer)Helper.copy(obj.getPort()));
           if (obj.getReceiveLevel() != null )
               setReceiveLevel((Integer)Helper.copy(obj.getReceiveLevel()));
           if (obj.getRingFrequency() != null )
               setRingFrequency((Integer)Helper.copy(obj.getRingFrequency()));
           if (obj.getRtpSignalType() != null )
               setRtpSignalType((Integer)Helper.copy(obj.getRtpSignalType()));
           if (obj.getServiceIdentifier() != null )
               setServiceIdentifier((Integer)Helper.copy(obj.getServiceIdentifier()));
           if (obj.getSlicReceiveLevel() != null )
               setSlicReceiveLevel((Integer)Helper.copy(obj.getSlicReceiveLevel()));
           if (obj.getSlicTransmitLevel() != null )
               setSlicTransmitLevel((Integer)Helper.copy(obj.getSlicTransmitLevel()));
           if (obj.getThreeWayCalling() != null )
               setThreeWayCalling((Integer)Helper.copy(obj.getThreeWayCalling()));
           if (obj.getToneVolume() != null )
               setToneVolume((Integer)Helper.copy(obj.getToneVolume()));
           if (obj.getTransmitLevel() != null )
               setTransmitLevel((Integer)Helper.copy(obj.getTransmitLevel()));
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
		db.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			db.rollbackTransaction();
		}finally{
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