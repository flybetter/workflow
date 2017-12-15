package com.calix.bseries.server.task;

import java.util.Iterator;
import java.util.List;

import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.server.process.CMSProcess;
import com.occam.ems.common.dataclasses.OccamSystemData;
import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;

public class BseriesGponOntDiscoveryTask extends AbstractBSeriesTask{
	private static final String GPON_ONT_DIS_TASK_NAME = "GponOntDiscovery";
    private String opName=MediationOperationNames.OP_DEEP_DISCOVERY;
    private boolean isReg;
    
    public BseriesGponOntDiscoveryTask(BSeriesTaskSignal signal) {
		// TODO Auto-generated constructor stub
    	super(signal);
	}

	@Override
	protected String getOperationName() {
		// TODO Auto-generated method stub
		return opName;
	}
	public void execute() {
        init();
        OccamProtocolProvider provider = new OccamProtocolProvider();
        OccamProtocolProperty protocolProperty = new OccamProtocolProperty();
        OccamSystemData occamSystemData = new OccamSystemData();
        reqRespObj.setParameter(MediationOperationNames.KEY_SYSTEM_INFO, occamSystemData);
        protocolProperty.setRequestResponseObject(reqRespObj);
        provider.syncSendTasks(new String[]{GPON_ONT_DIS_TASK_NAME}, protocolProperty);
    }
	@Override
	protected void handleResponse() {
		// TODO Auto-generated method stub
		super.handleResponse();
	}
	@Override
	protected BSeriesMediationSignal getResponseSignal() {
		// TODO Auto-generated method stub
		BSeriesMediationSignal signal = new BSeriesMediationSignal();
        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_GPON_ONT_DISCOVERY_RESP);
        signal.setOccamProtocolRequestResponse(reqRespObj);
        return signal;
	}

}
