/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.Collection;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANACommandProcess;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.exception.EMSException;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6CommandAction.
 * 
 * @version $Revision$ $Date$
 */
public class B6CommandAction extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CommandType
     */
    public String m_CommandType;

    /**
     * Field m_ID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_ID;

    /**
     * Field m_NetworkIP
     */
    public String m_NetworkIP;

    /**
     * Field m_NetworkName
     */
    public String m_NetworkName;

    /**
     * Field m_Port
     */
    public String m_Port;

    /**
     * Field m_Vlan
     */
    public String m_Vlan;
    
    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6CommandAction";

    /**
     * Field flowID
     */
    public static final int flowID = 1;

	//private static AtomicBoolean isEnd;
	private static B6CommandResult obj;

      //----------------/
     //- Constructors -/
    //----------------/

    public B6CommandAction() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6CommandAction()


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
        if( obj1 instanceof B6CommandAction ) {
            super.copyFields(obj1);
            B6CommandAction obj = (B6CommandAction)obj1;
            setCommandType((String)Helper.copy(obj.getCommandType()));
            setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
            setNetworkIP((String)Helper.copy(obj.getNetworkIP()));
            setNetworkName((String)Helper.copy(obj.getNetworkName()));
            setPort((String)Helper.copy(obj.getPort()));
            setVlan((String)Helper.copy(obj.getVlan()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCommandType
     */
    public String getCommandType()
    {
        return this.m_CommandType;
    } //-- String getCommandType() 

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
     * Method getNetworkIP
     */
    public String getNetworkIP()
    {
        return this.m_NetworkIP;
    } //-- String getNetworkIP() 

    /**
     * Method getNetworkName
     */
    public String getNetworkName()
    {
        return this.m_NetworkName;
    } //-- String getNetworkName() 

    /**
     * Method getPort
     */
    public String getPort()
    {
        return this.m_Port;
    } //-- String getPort() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6CommandAction;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getVlan
     */
    public String getVlan()
    {
        return this.m_Vlan;
    } //-- String getVlan()
    
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
            case 0x3537:
                if (m_CommandType == null) {
                    m_CommandType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3538:
                if (m_NetworkName == null) {
                    m_NetworkName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3539:
                if (m_NetworkIP == null) {
                    m_NetworkIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3555:
                if (m_Port == null) {
                    m_Port = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3560:
                if (m_Vlan == null) {
                    m_Vlan = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3537, m_CommandType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3539, m_NetworkIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x3538, m_NetworkName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3555, m_Port);
        TLVHelper.addEmbeddedTLV(tlv, 0x3560, m_Vlan);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCommandType
     * 
     * @param CommandType
     */
    public void setCommandType(String CommandType)
    {
        this.m_CommandType = CommandType;
    } //-- void setCommandType(String) 

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
     * Method setNetworkIP
     * 
     * @param NetworkIP
     */
    public void setNetworkIP(String NetworkIP)
    {
        this.m_NetworkIP = NetworkIP;
    } //-- void setNetworkIP(String) 

    /**
     * Method setNetworkName
     * 
     * @param NetworkName
     */
    public void setNetworkName(String NetworkName)
    {
        this.m_NetworkName = NetworkName;
    } //-- void setNetworkName(String) 

    /**
     * Method setPort
     * 
     * @param Port
     */
    public void setPort(String Port)
    {
        this.m_Port = Port;
    } //-- void setPort(String) 

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
     * Method setVlan
     * 
     * @param Vlan
     */
    public void setVlan(String Vlan)
    {
        this.m_Vlan = Vlan;
    } //-- void setVlan(String)
    
    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6CommandAction ) {
            super.updateFields(obj1);
            B6CommandAction obj = (B6CommandAction)obj1;
           if (obj.getCommandType() != null )
               setCommandType((String)Helper.copy(obj.getCommandType()));
           if (obj.getID() != null )
               setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
           if (obj.getNetworkIP() != null )
               setNetworkIP((String)Helper.copy(obj.getNetworkIP()));
           if (obj.getNetworkName() != null )
               setNetworkName((String)Helper.copy(obj.getNetworkName()));
           if (obj.getPort() != null )
               setPort((String)Helper.copy(obj.getPort()));
           if (obj.getVlan() != null )
               setVlan((String)Helper.copy(obj.getVlan()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
 // BEGIN CODE
    @Override
    public Collection doQuery(DbTransaction tx, String filter) throws EMSException
    
    {
    	obj = new B6CommandResult();
    	Collection resultCollection = new ArrayList();
    	obj = ANACommandProcess.getInstance().executeCommand(this);
    	obj.setIdentityValue(this.getIdentityValue());
	 	resultCollection.add(obj);
	 	return resultCollection;
    }
    
 // END CODE
}