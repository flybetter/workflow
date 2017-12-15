/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//BEGIN IMPORTS
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.server.dbmodel.BaseEMSAction;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.calix.system.common.log.Log;
import com.calix.system.common.protocol.tlv.ResultCode;

//END IMPORTS
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class EMSBseriesConnAction.
 * 
 * @version $Revision$ $Date$
 */
public class EMSBseriesConnAction extends BaseEMSAction {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * boolean indicating whether to retrieve the Device alarms or
     * not.
     */
    public Integer m_IsRetrieveAlarms;

    /**
     * Name of Network to act on.
     */
    public String m_Network;

    /**
     * connect, disconnect or update the time.
     */
    public String m_Type;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "EMSBseriesConnAction";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public EMSBseriesConnAction() {
        super();
    } //-- com.calix.bseries.server.dbmodel.EMSBseriesConnAction()


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
        if( obj1 instanceof EMSBseriesConnAction ) {
            super.copyFields(obj1);
            EMSBseriesConnAction obj = (EMSBseriesConnAction)obj1;
            setIsRetrieveAlarms((Integer)Helper.copy(obj.getIsRetrieveAlarms()));
            setNetwork((String)Helper.copy(obj.getNetwork()));
            setType((String)Helper.copy(obj.getType()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getIsRetrieveAlarms
     */
    public Integer getIsRetrieveAlarms()
    {
        return this.m_IsRetrieveAlarms;
    } //-- Integer getIsRetrieveAlarms() 

    /**
     * Method getNetwork
     */
    public String getNetwork()
    {
        return this.m_Network;
    } //-- String getNetwork() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.EMSBseriesConnAction;
    } //-- int getTlvType() 

    /**
     * Method getType
     */
    public String getType()
    {
        return this.m_Type;
    } //-- String getType() 

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
            case 0x33E0:
                if (m_Network == null) {
                    m_Network = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E1:
                if (m_Type == null) {
                    m_Type = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E2:
                if (m_IsRetrieveAlarms == null) {
                    m_IsRetrieveAlarms = TLVHelper.getIntegerValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x33E2, m_IsRetrieveAlarms);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E0, m_Network);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E1, m_Type);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setIsRetrieveAlarms
     * 
     * @param IsRetrieveAlarms
     */
    public void setIsRetrieveAlarms(Integer IsRetrieveAlarms)
    {
        this.m_IsRetrieveAlarms = IsRetrieveAlarms;
    } //-- void setIsRetrieveAlarms(Integer) 

    /**
     * Method setNetwork
     * 
     * @param Network
     */
    public void setNetwork(String Network)
    {
        this.m_Network = Network;
    } //-- void setNetwork(String) 

    /**
     * Method setType
     * 
     * @param Type
     */
    public void setType(String Type)
    {
        this.m_Type = Type;
    } //-- void setType(String) 

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
        if( obj1 instanceof EMSBseriesConnAction ) {
            super.updateFields(obj1);
            EMSBseriesConnAction obj = (EMSBseriesConnAction)obj1;
           if (obj.getIsRetrieveAlarms() != null )
               setIsRetrieveAlarms((Integer)Helper.copy(obj.getIsRetrieveAlarms()));
           if (obj.getNetwork() != null )
               setNetwork((String)Helper.copy(obj.getNetwork()));
           if (obj.getType() != null )
               setType((String)Helper.copy(obj.getType()));
        }
    } //-- void updateFields(CMSObject) 
 // BEGIN CODE
    public void doExecute(DbTransaction tx, Integer nbSessionID) throws EMSException
    {
        super.doExecute(tx, nbSessionID);
        SessionManager sessionManager = SessionManager.getInstance();
        String NetworkName = getNetwork();
        SouthboundSession session = sessionManager.getSouthboundSession(NetworkName);
        if(session == null) {
            throw new EMSException(ResultCode._RcFail_, "Session not exist for network: " + NetworkName);
        }
        try {
            if (getType().equals("connect")) {
                if ( Log.db().isDebugEnabled() ) 
                    Log.db().debug("Connect calling for network " + NetworkName);
                if ( session.isConnected() ){
                    Log.session().warn("tries to connect network is already connected.");
                    session.disconnect();
                    Thread.sleep(3000);     // wait a few seconds before re-connect
                }
                session.connect();
                if ( Log.db().isDebugEnabled() )
                    Log.db().debug("Connect called for network " + NetworkName);
            }
            if (getType().equals("disconnect")) {
                if ( Log.db().isDebugEnabled() ) 
                    Log.db().debug("disconnect calling for network " + NetworkName);
                session.disconnect();
                if ( Log.db().isDebugEnabled() ) 
                    Log.db().debug("Disconnect called for network " + NetworkName);
            }
        } catch (Exception ex) {
            Log.session().warn("Exception thrown during connect/disconnect: " + ex.getMessage());
            throw new EMSException(ResultCode._RcFail_, ex.getMessage());
        }
    }
  // END CODE
}