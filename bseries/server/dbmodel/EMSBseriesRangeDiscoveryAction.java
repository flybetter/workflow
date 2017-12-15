/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

//BEGIN IMPORTS
import com.calix.bseries.server.rangediscovery.SimpleSnmpClient;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.server.dbmodel.BaseEMSAction;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.log.Log;
import com.calix.system.common.protocol.tlv.ResultCode;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.CMSObject;
import com.calix.system.server.dbmodel.Helper;
import com.calix.system.server.dbmodel.SwVersionNo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.snmp4j.smi.OID;
import com.calix.system.server.rangediscovery.RangeDiscoveryHandlerA;
import com.calix.system.server.rangediscovery.RangeDiscoveryHandlerManager;
//END IMPORTS

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;
import com.calix.system.server.dbmodel.ICMSAid;

/**
 * Class EMSBseriesRangeDiscoveryAction.
 * 
 * @version $Revision$ $Date$
 */
public class EMSBseriesRangeDiscoveryAction extends BaseEMSAction {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * IP to range discovery
     */
    public String m_IP;
    public Integer m_autoChassis;

    /**
     * allow discover bseries
     */
    public Integer m_allowBseries;

    /**
     * allow discover exa
     */
    public Integer m_allowExa;

    /**
     * allow discover ont
     */
    public Integer m_allowOnt;

    /**
     * read community
     */
    public String m_community;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "EMSBseriesRangeDiscoveryAction";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public EMSBseriesRangeDiscoveryAction() {
        super();
    } //-- com.calix.bseries.server.dbmodel.EMSBseriesRangeDiscoveryAction()


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
        if( obj1 instanceof EMSBseriesRangeDiscoveryAction ) {
            super.copyFields(obj1);
            EMSBseriesRangeDiscoveryAction obj = (EMSBseriesRangeDiscoveryAction)obj1;
            setIP((String)Helper.copy(obj.getIP()));
            setautoChassis((Integer)Helper.copy(obj.getautoChassis()));
            setallowBseries((Integer)Helper.copy(obj.getallowBseries()));
            setallowExa((Integer)Helper.copy(obj.getallowExa()));
            setallowOnt((Integer)Helper.copy(obj.getallowOnt()));
            setcommunity((String)Helper.copy(obj.getcommunity()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getIP
     */
    public String getIP()
    {
        return this.m_IP;
    } //-- String getIP() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.EMSBseriesRangeDiscoveryAction;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getautoChassis
     */
    public Integer getautoChassis()
    {
        return this.m_autoChassis;
    } //-- Integer getautoChassis() 

    /**
     * Method getallowBseries
     */
    public Integer getallowBseries()
    {
        return this.m_allowBseries;
    } //-- Integer getallowBseries() 

    /**
     * Method getallowExa
     */
    public Integer getallowExa()
    {
        return this.m_allowExa;
    } //-- Integer getallowExa() 

    /**
     * Method getallowOnt
     */
    public Integer getallowOnt()
    {
        return this.m_allowOnt;
    } //-- Integer getallowOnt() 

    /**
     * Method getcommunity
     */
    public String getcommunity()
    {
        return this.m_community;
    } //-- String getcommunity() 

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
		    case 0x33E9:
                if (m_autoChassis == null) {
                    m_autoChassis = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3400:
                if (m_IP == null) {
                    m_IP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
			case 0x33D1:
                if (m_allowBseries == null) {
                    m_allowBseries = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D2:
                if (m_allowExa == null) {
                    m_allowExa = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33D3:
                if (m_allowOnt == null) {
                    m_allowOnt = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3401:
                if (m_community == null) {
                    m_community = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3400, m_IP);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E9, m_autoChassis);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D1, m_allowBseries);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D2, m_allowExa);
        TLVHelper.addEmbeddedTLV(tlv, 0x33D3, m_allowOnt);
        TLVHelper.addEmbeddedTLV(tlv, 0x3401, m_community);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setIP
     * 
     * @param IP
     */
    public void setIP(String IP)
    {
        this.m_IP = IP;
    } //-- void setIP(String) 

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
     * Method setautoChassis
     * 
     * @param autoChassis
     */
    public void setautoChassis(Integer autoChassis)
    {
        this.m_autoChassis = autoChassis;
    } //-- void setautoChassis(Integer) 

    /**
     * Method setallowBseries
     * 
     * @param allowBseries
     */
    public void setallowBseries(Integer allowBseries)
    {
        this.m_allowBseries = allowBseries;
    } //-- void setallowBseries(Integer) 

    /**
     * Method setallowExa
     * 
     * @param allowExa
     */
    public void setallowExa(Integer allowExa)
    {
        this.m_allowExa = allowExa;
    } //-- void setallowExa(Integer) 

    /**
     * Method setallowOnt
     * 
     * @param allowOnt
     */
    public void setallowOnt(Integer allowOnt)
    {
        this.m_allowOnt = allowOnt;
    } //-- void setallowOnt(Integer) 

    /**
     * Method setcommunity
     * 
     * @param community
     */
    public void setcommunity(String community)
    {
        this.m_community = community;
    } //-- void setcommunity(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof EMSBseriesRangeDiscoveryAction ) {
            super.updateFields(obj1);
            EMSBseriesRangeDiscoveryAction obj = (EMSBseriesRangeDiscoveryAction)obj1;
           if (obj.getIP() != null )
               setIP((String)Helper.copy(obj.getIP()));
           if (obj.getautoChassis() != null )
               setautoChassis((Integer)Helper.copy(obj.getautoChassis()));
           if (obj.getallowBseries() != null )
               setallowBseries((Integer)Helper.copy(obj.getallowBseries()));
           if (obj.getallowExa() != null )
               setallowExa((Integer)Helper.copy(obj.getallowExa()));
           if (obj.getallowOnt() != null )
               setallowOnt((Integer)Helper.copy(obj.getallowOnt()));
           if (obj.getcommunity() != null )
               setcommunity((String)Helper.copy(obj.getcommunity()));
        }
    } //-- void updateFields(CMSObject) 
    // BEGIN CODE

    private static Map<String, OID> SYSGROUP_MAP = new HashMap<String, OID>();
    static {
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSDESC_NAME, new OID(RangeDiscoveryHandlerA.SYSDESC_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSOBJID_NAME, new OID(RangeDiscoveryHandlerA.SYSOBJID_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSUPTIME_NAME, new OID(RangeDiscoveryHandlerA.SYSUPTIME_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSCONTACT_NAME, new OID(RangeDiscoveryHandlerA.SYSCONTACT_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSNAME_NAME, new OID(RangeDiscoveryHandlerA.SYSNAME_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSLOCATION_NAME, new OID(RangeDiscoveryHandlerA.SYSLOCATION_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSSERVICES_NAME, new OID(RangeDiscoveryHandlerA.SYSSERVICES_OID));
    	SYSGROUP_MAP.put(RangeDiscoveryHandlerA.SYSORLASTCHANGE_NAME, new OID(RangeDiscoveryHandlerA.SYSORLASTCHANGE_OID));
    }
    
    public void doExecute(DbTransaction tx, Integer nbSessionID) throws EMSException
    {
        //1. if the IP exists already
        //2. if no SNMP response at all
        //3. if the response is of interest of us
        //4. create corresponding device
        SimpleSnmpClient client = new SimpleSnmpClient(m_IP);
        try {
            client.start();
            if (Log.jobs().isDebugEnabled()){
            	Log.jobs().debug("send SNMP Get sysDesc request to " + m_IP);
            }
//          String sysDesc = null;
//          if (getcommunity() != null){
//              sysDesc = client.getAsString(new OID(SYSDESC_OID), getcommunity());
//          }else {
//              sysDesc = client.getAsString(new OID(SYSDESC_OID));
//          }
            // Load the whole system group instead of sysDescr only. This is for B6 3rd party device support, since
            // some of the B6 3rd party devices need check on sysObjectID value instead of sysDescr.
            Map<String, String> sysGroupValues = null;
			if (getcommunity() != null) {
				sysGroupValues = client.getAsMap(SYSGROUP_MAP,getcommunity());
			} else {
				sysGroupValues = client.getAsMap(SYSGROUP_MAP);
			}
            if (Log.jobs().isDebugEnabled()){
            	Log.jobs().debug("get sysGroup response: " + sysGroupValues);
            }
            
        	RangeDiscoveryHandlerA handler = null;
            if(this.getallowExa()!=null && this.getallowExa() == 1){
                handler = RangeDiscoveryHandlerManager.getInstance().getMatchedDeviceHandlerByObjectId(sysGroupValues);
            }else{
    			Log.jobs().warn("AllowExa is false. " );
            }
            
            if (handler == null && this.getallowBseries()!=null && this.getallowBseries() == 1){
                handler = RangeDiscoveryHandlerManager.getInstance().getMatchedDeviceHandler(sysGroupValues);
            }else{
    			Log.jobs().warn("AllowBseries is false. " );
            }
//            RangeDiscoveryHandlerA handler = RangeDiscoveryHandlerManager.getInstance().getMatchedDeviceHandler(sysGroupValues);
            if (handler == null){
                throw new EMSException(ResultCode._RcFail_, "Unknown device, sysGroup is: " + sysGroupValues);
            }
            if (Log.jobs().isDebugEnabled()){
            	Log.jobs().debug("by sysDesc, found the handler to handle it: " + handler.getClass().getName());
            }
			if (getcommunity() != null) {
				handler.setCommunity(getcommunity());
			}
			if (getautoChassis() != null) {
				handler.setAutoChassis(getautoChassis());
			}
            handler.process(m_IP, sysGroupValues);
        } catch (IOException e) {
        	Log.jobs().warn("Fail to discover [" + m_IP + "], Error: " + e.getMessage(), e);
            throw new EMSException(ResultCode._RcFail_, e.getMessage());
        } catch (Exception e) {
        	Log.jobs().warn("Fail to discover [" + m_IP + "], Error: " + e.getMessage(), e);
            throw new EMSException(ResultCode._RcFail_, e.getMessage());
        } finally{
            try {
                client.stop();
            } catch (IOException e) {
                Log.jobs().error(e.getMessage(), e);
            }
        }
    }
  // END CODE
}