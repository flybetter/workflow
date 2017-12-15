package com.calix.bseries.server.task;

import com.occam.ems.common.defines.MediationOperationNames;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 11:18 PM
 *
 * Handle 2300 AE ONT trap registration
 */
public class AeTrapRegTask extends AbstractBSeriesTask {
    private boolean isReg;

    public AeTrapRegTask(String ip, boolean isReg) {
        super(ip);
        this.isReg = isReg;
    }

    public AeTrapRegTask(BSeriesTaskSignal signal) {
        super(signal);
        this.isReg = ((BSeriesTrapRegSignal)signal).isReg();
    }

    protected void init() {
        super.init();

        reqRespObj.setOntTrapHostRegReq(isReg);
        reqRespObj.setOntTrapHostDeRegReq(!isReg);
        reqRespObj.setParameter(MediationOperationNames.SNMP_REQUEST_PARAMS,
                BSeriesUtil.getSnmpRequestParams(snmpReadCommunity));
    }

    @Override
    protected String getOperationName() {
        return MediationOperationNames.ONT_SNMP_TRAPHOST_REGISTRATION;
    }

    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        BSeriesTaskSignal signal = new BSeriesTaskSignal();
        signal.setType(BSeriesTaskSignal.SIG_TYPE_AE_TRAP_REG_RESP);
        return signal;
    }

    @Override
    protected void handleResponse() {
        if(isReg) {
            super.handleResponse();
        }
    }
}
