package com.calix.bseries.server.task;

import com.occam.ems.common.dataclasses.OccamSystemData;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.mediation.protocol.ocm.transactioncommand.v1.misc.FindSubscriberByIPCmd;

import java.util.Properties;

import org.apache.log4j.Logger;

public class AeDiscoveryTask extends BSeriesDiscoveryTask {
	
	private static final Logger log = Logger.getLogger(AeDiscoveryTask.class);
	public static final String PROP_NAME_SYSNAME = "sysName";
	public static final String PROP_NAME_IPADDRESS = "ipAddress";
	public static final String PROP_NAME_MACADDRESS = "entityMacAddress";
	public static final String PROP_NAME_NETWORK = "networkName";
	private String port;
	
	public AeDiscoveryTask(BSeriesTaskSignal signal) {
		super(signal);
		if(signal instanceof AeDiscoveryReqSignal){
			this.port = ((AeDiscoveryReqSignal) signal).getPort();
		}
	}
	public void execute() {
		super.execute();
		
	}
    @Override
    protected BSeriesTaskSignal getResponseSignal() {
    	AeDiscoveryRespSignal signal = new AeDiscoveryRespSignal();
        if(reqRespObj.getErrorInfo() == null) {
            OccamSystemData sysData = (OccamSystemData) reqRespObj.getParameter(OccamStaticDef.DEV_PROPERTY);
            Properties retProp = sysData.getProperties();
            if(retProp != null) {
            	if( log.isDebugEnabled() ){
            		log.debug("response properties:\n" + retProp);
            	}
            	signal.setIpAddress(retProp.getProperty(PROP_NAME_IPADDRESS));
            	signal.setModel(retProp.getProperty(PROP_NAME_EQ_TYPE));
                signal.setDescription(retProp.getProperty(PROP_NAME_SYSNAME));
                signal.setSerialNum(retProp.getProperty(PROP_NAME_SERIAL_NUM));
                signal.setMacAddress(retProp.getProperty(PROP_NAME_MACADDRESS));
                signal.setVersion(retProp.getProperty(PROP_NAME_SW_VERSION));
                signal.setPort(port);
                signal.setNetworkName(retProp.getProperty(PROP_NAME_NETWORK));
             
            }
        }else {
        	log.error("receive Ae Discovery response with error info " + reqRespObj.getErrorInfo());
        }
        return signal;
    }
}
