package com.calix.bseries.server.task;

import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.dataclasses.OccamSystemData;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import com.occam.ems.mediation.protocol.rmi.commandfactory.v40.systemiface.EpsDiscoveryCmd;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 11:32 PM
 *
 * Handle B6 link discovery
 */
public class B6LinkDiscoveryTask extends AbstractBSeriesTask {
    private static Logger logger = Logger.getLogger(B6LinkDiscoveryTask.class);
    String transName = MediationOperationNames.OP_LINK_DISCOVERY;
    
    OccamProtocolProperty protocalProperty = null;
    
    public B6LinkDiscoveryTask(String ip) {
        super(ip);
    }

    public B6LinkDiscoveryTask(BSeriesTaskSignal signal) {
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
        return MediationOperationNames.OP_LINK_DISCOVERY;
    }
    public void execute() {
        try{
            init();
            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
            logger.info("Received Link discovery response");
            List errors = protocalProperty.getRequestResponseObject().getErrorInfo();
            if(errors != null) {
                for(Iterator it = errors.iterator();it.hasNext();) {
                    logger.info("Error: " + it.next());
                }
            }
        }catch(Exception e){
            logger.warn("Error in discoverying links");
        }
    }
    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        logger.info("Received link response");
        BSeriesAllLinksDiscoveryResponseSignal signal =  new BSeriesAllLinksDiscoveryResponseSignal();
        if (protocalProperty == null)
            return signal;
        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_LINK_DISCOVERY_RESP);
        if(protocalProperty.getRequestResponseObject().getErrorInfo() == null) {
         
            OccamSystemData sysData = (OccamSystemData) protocalProperty.getRequestResponseObject().getParameter(MediationOperationNames.KEY_SYSTEM_INFO);
            Properties retProp = sysData.getProperties();
            if(retProp != null) {
                List epsInfoList = (List)retProp.get(MediationOperationNames.KEY_EPS_NODE_INFO_LIST);
                logger.info("Link list " + epsInfoList);
                if(epsInfoList != null) {
                    for(Iterator it = epsInfoList.iterator(); it.hasNext();) {
               
                        Properties linkProp = (Properties) it.next();
                        String destIPAddress = (String)linkProp.get(MediationOperationNames.EPS_NEIGHBOR_IP_ADDRESS);                        
                        String sourceLocalID = (String)linkProp.get(MediationOperationNames.EPS_LOCALID);
                        int sourcePortIndex = sourceLocalID.lastIndexOf("/");
                        String sourceSlot = sourceLocalID.substring(0, sourcePortIndex);
                        String sourcePort = sourceLocalID.substring(sourcePortIndex+1, sourceLocalID.length());
                        logger.info("Source Port " + sourcePort + " Source Slot " + sourceSlot );
                        String destLocalID = (String)linkProp.get(MediationOperationNames.EPS_NEIGHBOR_ID);
                        int destPortIndex = destLocalID.lastIndexOf("/");
                        String destSlot = destLocalID.substring(0, destPortIndex);
                        String destPort = destLocalID.substring(destPortIndex+1, destLocalID.length());
                        logger.info("Dest Port " + destPort + " Dest Slot " + destSlot);
                        
                        BSeriesLinkDiscoveryResponseSignal respSignal = new BSeriesLinkDiscoveryResponseSignal();
                        respSignal.setNe1Name(ipAddr);
                        respSignal.setNe1Port(sourcePort);
                        respSignal.setNe2Name(destIPAddress);
                        respSignal.setNe2Port(destPort);
                        respSignal.setLinkType("EPS");   
                        signal.setEpsInfoLst(respSignal);
                    }
                }  else {
                    logger.info("No Links");
                }
            }
        }
        return signal;
    }
   
}


