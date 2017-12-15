package com.calix.bseries.server.task;

import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.dataclasses.OccamSystemData;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import com.occam.ems.mediation.protocol.snmp.transactioncommand.discovery.OccamShallowDiscoveryTransactionCmd;
import java.util.Iterator;
import org.apache.log4j.Logger;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 10:19 PM
 *
 * Handle B6 discovery
 */
public class BSeriesDiscoveryTask extends AbstractBSeriesTask {
    private static Logger logger = Logger.getLogger(BSeriesDiscoveryTask.class);
    
    public static final String PROP_NAME_SW_VERSION = "entitySoftwareRev";
    public static final String PROP_NAME_EQ_TYPE = "equipmentType";
    public static final String PROP_NAME_HW_VERSION = "entityHardwareRev";
    public static final String PROP_NAME_SERIAL_NUM = "entitySerialNum";
    public static final String PROP_NAME_MAC_ADDRESS = "entityMacAddress";
    public static final String PROP_NAME_MFG_DATE = "entityMfgDate";
    public static final String PROP_NAME_SHELF_ID = "ShelfId";
    public static final String PROP_NAME_SLOT_ID = "SlotId";
    private OccamSystemData sysData = null;
    public BSeriesDiscoveryTask(String ip) {
        super(ip);
    }

    public BSeriesDiscoveryTask(BSeriesTaskSignal signal) {
        super(signal);
    }

    protected void init() {
        DevProperty info = new DevProperty();
        info.setIPAddress(ipAddr);
        Vector v = new Vector();
        v.add(BSeriesUtil.getSNMPAuthData(snmpReadCommunity));
        info.getProperties().put(DevProperty.SNMP_AUTH_PARAMS, v);
        reqRespObj = new OccamProtocolRequestResponse();
        reqRespObj.setDeviceProperty(info);
        reqRespObj.setOperationName(OccamStaticDef.DISCOVERY_OPER);
    }

    @Override
    protected String getOperationName() {
        return OccamStaticDef.DISCOVERY_OPER;
    }

    public void execute() {
        init();
        OccamShallowDiscoveryTransactionCmd cmd = new OccamShallowDiscoveryTransactionCmd();
        cmd.executeTransaction(reqRespObj);
        if(reqRespObj.getErrorInfo() == null) {
            sysData = (OccamSystemData) reqRespObj.getParameter(OccamStaticDef.DEV_PROPERTY);
            Properties retProp = sysData.getProperties();
                if(retProp != null) {
                    version = retProp.getProperty(PROP_NAME_SW_VERSION);
                    eqptType = retProp.getProperty(PROP_NAME_EQ_TYPE);
                }
        }else{
           logger.error("BSeriesDiscoveryTask : execute : get error...");
            for (Iterator it = reqRespObj.getErrorInfo().iterator(); it.hasNext();) {
                logger.error("Error: " + it.next());
            }  
        }
        if(eqptType != null && !(eqptType.startsWith("ON2342") || eqptType.startsWith("ON2343") || eqptType.startsWith("ON2364"))) {
            getProductDetails();
        }
    }
    private void getProductDetails() {
        logger.info("BSeriesDiscoveryTask : getProductDetails : Entered");
        DevProperty devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        //devProperty.setProperty("AbstractDeviceType", deviceType);
        if (version != null)
            devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);
        if (eqptType != null)
            devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);
        
        reqRespObj = new OccamProtocolRequestResponse();
        reqRespObj.setDeviceProperty(devProperty);
        /** setting operation type to the occamprotocol-request-response object **/
        reqRespObj.setOperationName(MediationOperationNames.OP_PRODUCT_DISCOVERY);
        OccamSystemData newOcmSystem=new OccamSystemData();
        reqRespObj.setParameter(MediationOperationNames.KEY_SYSTEM_INFO, newOcmSystem);
        OccamProtocolProperty protocalProperty = new OccamProtocolProperty();
        protocalProperty.setRequestResponseObject(reqRespObj);

        OccamProtocolProvider provider = new OccamProtocolProvider();
        provider.syncSend(protocalProperty);

        /** check for the response **/
        if (reqRespObj.getOperState() == OccamStaticDef.OPER_FAILED) {
            logger.error("BSeriesDiscoveryTask : getProductDetails : operationstate : " + reqRespObj.getOperState());            
        }

        if (reqRespObj.getErrorInfo() != null) {
            logger.error("BSeriesDiscoveryTask : getProductDetails : get error...");
            for (Iterator it = reqRespObj.getErrorInfo().iterator(); it.hasNext();) {
                logger.error("Error: " + it.next());
            }            
        }
        
    }
    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        BSeriesDiscoveryResponseSignal signal = new BSeriesDiscoveryResponseSignal();
        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_DISCOVERY_RESP);
        if(reqRespObj.getErrorInfo() == null) {
            try {
                OccamSystemData sysData = (OccamSystemData) reqRespObj.getParameter(MediationOperationNames.KEY_SYSTEM_INFO);
                Properties retProp = sysData.getProperties();
                if(retProp != null) {
                    String shelf = retProp.getProperty(PROP_NAME_SHELF_ID);
                    String slot = retProp.getProperty(PROP_NAME_SLOT_ID);
                    //int shelfId, slotId;
                    String shelfId;
                    int slotId;
                    if (shelf != null && shelf.trim().length() > 0 &&
                            !"-1".equals(shelf.trim())) {
                        //shelfId = Integer.parseInt(shelf);
                        shelfId = Integer.toHexString(Integer.parseInt(shelf));
                    } else {
                        shelfId = OccamUtils.getDefaultShelfId();
                    }
                    if (slot != null) {
                        slotId = Integer.parseInt(slot);
                    } else {
                        slotId = OccamUtils.getDefaultSlotId();
                    }
                    signal.setShelfId(shelfId);
                    signal.setSlotId(new Integer(slotId));
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            
            Properties retProp = sysData.getProperties();
            if(retProp != null) {
                
                signal.setVersion(retProp.getProperty(PROP_NAME_SW_VERSION));
                signal.setHwVersion(retProp.getProperty(PROP_NAME_HW_VERSION));
                signal.setEquipType(retProp.getProperty(PROP_NAME_EQ_TYPE));
                signal.setSerialNum(retProp.getProperty(PROP_NAME_SERIAL_NUM));
                signal.setMacAddress(retProp.getProperty(PROP_NAME_MAC_ADDRESS));
                signal.setManufactureDate(retProp.getProperty(PROP_NAME_MFG_DATE));
            }
        }
        return signal;
    }
}
