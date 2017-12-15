package com.calix.bseries.server.task;

import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.server.process.CMSProcess;
import com.occam.ems.be.util.MediationServerUtil;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 8/19/11
 * Time: 5:42 PM
 */
public class BSeriesMediationRequestTask extends AbstractBSeriesTask {
    private long requestId;
    public BSeriesMediationRequestTask(BSeriesTaskSignal reqSignal) {
        this.reqRespObj = ((BSeriesMediationSignal)reqSignal).getOccamProtocolRequestResponse();
        this.requestId = ((BSeriesMediationSignal) reqSignal).getRequestId();
    }

    @Override
    public void execute() {
        MediationServerUtil.getInstance().syncSendMediationRequest(reqRespObj);
    }

    @Override
    protected void handleResponse() {
        BSeriesMediationSignal signal = new BSeriesMediationSignal();
        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_MEDIATION_RESP);
        signal.setOccamProtocolRequestResponse(reqRespObj);
        signal.setRequestId(requestId);
        JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.CMS_SERVER_ID), signal);
    }

    @Override
    protected String getOperationName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
