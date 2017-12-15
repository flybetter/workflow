package com.calix.bseries.server.task;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.dataclasses.OccamSystemData;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.common.logging.OccamLoggerUtility;

import com.occam.ems.common.xml.cliParser.Attribute;
import com.occam.ems.common.xml.cliParser.AttributeGroup;
import com.occam.ems.common.xml.cliParser.CLIParser;
import com.occam.ems.common.xml.cliParser.CLIParserConfig;
import com.occam.ems.common.xml.cliParser.Command;
import com.occam.ems.common.xml.cliParser.impl.AttributeImpl;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;


public class B6AeAssociationConfigTask extends AbstractBSeriesTask{

    private static final Logger logger = Logger.getLogger(B6AeAssociationConfigTask.class);
    String transName = MediationOperationNames.OP_DEEP_DISCOVERY;
    OccamProtocolProperty protocalProperty = null;
    boolean operationResult = false;
    
    public B6AeAssociationConfigTask(BSeriesTaskSignal signal) {
        super(signal);
    }

    protected void init() {
        DevProperty devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
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

    public void execute() {
        try {
            init();
            OccamProtocolProvider provider = new OccamProtocolProvider();
            provider.syncSend(protocalProperty);
            logger.info("Received ae ont association discovery response");
            List errors = protocalProperty.getRequestResponseObject().getErrorInfo();
            if(errors != null) {
                for(Iterator it = errors.iterator();it.hasNext();) {
                    logger.info("Error: " + it.next());
                }
            }
        } catch (Exception e) {
            logger.error("Error in discovering ae ont association");
        }
    }
 
	@Override
	protected BSeriesTaskSignal getResponseSignal() {
        logger.info("Received ae ont association response");
        BSeriesAeOntAssociationResponseSignal signal =  new BSeriesAeOntAssociationResponseSignal();
        if (protocalProperty == null)
            return signal;
        signal.setType(BSeriesTaskSignal.SIG_TYPE_AE_ASSOCIATE_DISCOVERY_RESP);
        
        if(protocalProperty.getRequestResponseObject().getErrorInfo() == null) {
            OccamSystemData sysData = (OccamSystemData) protocalProperty.getRequestResponseObject().getParameter(MediationOperationNames.KEY_SYSTEM_INFO);

            Properties retProp = sysData.getProperties();
			if (retProp != null) {
				String association = (String) retProp.get("SHOW ASSOCIATION");
				logger.info("ae ont association: " + association);
				List<String> ips = parseOntIP(association);

				signal.setIpList(ips);
				signal.setNetworkName(networkName);
				signal.setIpAddress(this.ipAddr);
			} else {
				logger.warn("There is no ae ont attached with the device: " + signal.getIpAddress());
			}
        }else{
			logger.error("B6AeAssociationConfigTask: error in getResponseSignal(). Error Info: " + protocalProperty.getRequestResponseObject().getErrorInfo());
        }
        return signal;
	}

	private List parseOntIP(String association) {
		
		ArrayList ipAddresses = new ArrayList();

        try{
            CLIParserConfig cliData = null;
            cliData = CLIParser.getInstance().parse("ONT", "show associations", association);                    
            Command command = (Command) cliData.getCommand().get(0);
            List newAttrGrpList = command.getAttributeGroup();
            logger.debug("B6AeAssociationConfigTask : parseOntIP() : newAttrGrpList.size() = " + newAttrGrpList.size());
            AttributeGroup attrGrp = null;
            for(int i=0 ; i < newAttrGrpList.size() ; i++)
            {
            	String ipAddress = null;
            	attrGrp = (AttributeGroup) newAttrGrpList.get(i);
            	
            	if(attrGrp!=null && attrGrp.getName()!=null && attrGrp.getName().equalsIgnoreCase("Interface")){
            		for(int j=0 ; j < attrGrp.getAttribute().size() ; j++) {
            			Attribute attr = (AttributeImpl)attrGrp.getAttribute().get(j);
            			logger.debug("B6AeAssociationConfigTask : parseOntIP() : 		["+attr.getName()+" = " + attr.getValue()+"]");
            			if(attr.getName().equalsIgnoreCase("IP Address")) {
            				ipAddress = attr.getValue(); 
            				if(ipAddress != null && !ipAddresses.contains(ipAddress)){
            					if(attrGrp.getValue()!=null){
            						ipAddresses.add(ipAddress+":"+attrGrp.getValue());
            						logger.info("ipaddress and Port = " + ipAddress+":"+attrGrp.getValue() );
            					}else{
            						ipAddresses.add(ipAddress);
            						logger.info("ipaddress = " + ipAddress );
            					}
            				}
            			}
            		}
            	}
            }            
        }catch(Exception e){
            e.printStackTrace();
            logger.error("B6AeAssociationConfigTask : parseOntIP() : encounter error: " + e.getMessage() );
 
        }
        return ipAddresses;
	}

	@Override
	protected String getOperationName() {
		// TODO Auto-generated method stub
		return null;
	}
}
