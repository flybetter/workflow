package com.calix.bseries.server.task;


import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import com.occam.ems.common.dataclasses.*;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 11:32 PM
 *
 * Handle B6 link discovery
 */
public class B6ChassisDiscoveryTask extends AbstractBSeriesTask {
    private static Logger logger = Logger.getLogger(B6ChassisDiscoveryTask.class);
    String transName = MediationOperationNames.OP_CHASSIS_DISCOVERY;
    
    OccamProtocolProperty protocalProperty = null;
    
    public B6ChassisDiscoveryTask(String ip) {
        super(ip);
    }

    public B6ChassisDiscoveryTask(BSeriesTaskSignal signal) {
        super(signal);
    }

    protected void init() {
        DevProperty devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        //devProperty.setProperty("AbstractDeviceType", deviceType);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);

        reqRespObj = new OccamProtocolRequestResponse();
        reqRespObj.setDeviceProperty(devProperty);
        OccamSystemData newOcmSystem=new OccamSystemData();
        reqRespObj.setParameter(MediationOperationNames.KEY_SYSTEM_INFO, newOcmSystem);
        
        reqRespObj.setOperationName(transName);
        reqRespObj.medLogPrefix=ipAddr;
        reqRespObj.medAssLogger= OccamLoggerUtility.getLogger("discovery");

        protocalProperty = new OccamProtocolProperty();
        protocalProperty.setRequestResponseObject(reqRespObj);
    }
    
    @Override
    protected String getOperationName() {
        return MediationOperationNames.OP_CHASSIS_DISCOVERY;
    }
    public void execute() {
        try{
            init();
            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
            logger.info("Received Chassis discovery response");
            List errors = protocalProperty.getRequestResponseObject().getErrorInfo();
            if(errors != null) {
                for(Iterator it = errors.iterator();it.hasNext();) {
                    logger.info("Error: " + it.next());
                }
            }
        }catch(Exception e){
            logger.warn("Error in discoverying Chassis");
        }
    }
    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        logger.info("Received Chassis response for Network " + ipAddr);
        BSeriesChassisDiscoveryResponseSignal signal =  new BSeriesChassisDiscoveryResponseSignal();
        if (protocalProperty == null)
            return signal;
        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_CHASSIS_DISCOVERY_RESP);
        if(protocalProperty.getRequestResponseObject().getErrorInfo() == null) {
         
            OccamSystemData sysData = (OccamSystemData) protocalProperty.getRequestResponseObject().getParameter(MediationOperationNames.KEY_SYSTEM_INFO);
            Properties retProp = sysData.getProperties();
            if(retProp != null) {
                ChassisDataHolder chassis = (ChassisDataHolder)retProp.get(MediationOperationNames.CHASSIS);
                signal.setChassisType(chassis.getChassisType());
                signal.setDisplayString(chassis.getDisplayString());
                signal.setManufacturingDate(chassis.getManufacturingDate());
                signal.setSerialNumber(chassis.getSerialNumber());
            }
        }
        return signal;
    }
   
}


