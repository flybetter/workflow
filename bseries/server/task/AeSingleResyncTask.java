package com.calix.bseries.server.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.server.process.CMSProcess;
import com.occam.ems.common.util.OccamUtils;

public class AeSingleResyncTask extends AeResyncOcmManagedChildTask {

	private static final Logger logger = Logger.getLogger(AeSingleResyncTask.class);
	private Ae_Ont m_network;
	private String m_networkName;
	
	
	public void setNetwork(Ae_Ont network) {
        this.m_network = network;
        this.networkName = "AEONT-"+network.getStrAid();
       
    }
	
	public void runTask() throws Exception  {
	       if( m_network!= null && OccamUtils.isOntDevice(m_network.getModel())){
            //resync on Occam ONT
	       	 try {
	                 if(OccamUtils.isOccamAeONT(m_network.getModel()) && super.deviceSupportsSaveRunningConfig(m_network.getFirmwareVersion())){
	                    sendOntCopyRunningConfigurationRequest(m_network.getIpAddress(), m_network.getModel(), m_network.getFirmwareVersion());
	                 }
	                 if(!OccamUtils.isOccamAeONT(m_network.getModel())){
	                	 List errList = resyncOntConfigWithTFTPServer(m_network.getIpAddress(), m_network.getModel(), m_network.getFirmwareVersion());
//	                	 if(errList.isEmpty()){
//	                		 //updateNetworksSucceedStatus("AEONT-"+m_network.getStrAid());
	                		 return;
//	                	 } else{
//	                		 //updateNetworksFailureStatus("AEONT-"+m_network.getStrAid(),errList.toString());
//	                	 }
	                 }
	                 if( deviceSupportsResyncOntConfigWithTFTPServer(m_network.getFirmwareVersion())){
	                    resyncOntConfigWithTFTPServer(m_network.getIpAddress(), m_network.getModel(), m_network.getFirmwareVersion());
	                 }
	                 AeDiscoveryReqSignal signal = new AeDiscoveryReqSignal();
	                 signal.setNetworkName(networkName);
	                 signal.setIpAddress(m_network.getIpAddress());
	                 //signal.setSnmpReadCommunity(m_network.getS);
	                 logger.info("Sending AE discovery request for :" + m_network.getaeontid());
	                 JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);            
	                 logger.info("Status verification complete for " + m_network.getaeontid());
	                 return;
	            } 
	       	 catch (Exception ex) {
	                   logger.warn("Error sending discovery request. network: " + m_network.getaeontid(), ex);
	                   throw new Exception("Error sending discovery request. network: " + m_network.getaeontid());
	            }
	       }
	}
	
}
