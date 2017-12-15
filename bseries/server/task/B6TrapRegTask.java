package com.calix.bseries.server.task;

import com.occam.ems.common.defines.MediationOperationNames;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 10:54 PM
 *
 * Handle B6 trap registration
 */
public class B6TrapRegTask extends AbstractBSeriesTask {
    private static final String TRAP_REG_TASK_NAME = "RmiTrapRegistration";
    private String opName;
    private boolean isReg;

    public B6TrapRegTask(String ip, boolean isReg) {
        super(ip);
        this.isReg = isReg;
        if(isReg) { //add trap registration
            opName = MediationOperationNames.OP_DEEP_DISCOVERY;
        } else { //delete trap registration
            opName = MediationOperationNames.OP_DELETE_NE;
        }
    }

    public B6TrapRegTask(BSeriesTaskSignal signal) {
        super(signal);
        this.isReg = ((BSeriesTrapRegSignal)signal).isReg();
        if(isReg) { //add trap registration
            opName = MediationOperationNames.OP_DEEP_DISCOVERY;
        } else { //delete trap registration
            opName = MediationOperationNames.OP_DELETE_NE;
        }
    }

    @Override
    protected String getOperationName() {
        return opName;
    }

    public void execute() {
        init();
        OccamProtocolProvider provider = new OccamProtocolProvider();
        OccamProtocolProperty protocolProperty = new OccamProtocolProperty();
        protocolProperty.setRequestResponseObject(reqRespObj);
        provider.syncSendTasks(new String[]{TRAP_REG_TASK_NAME}, protocolProperty);
    }

    @Override
    protected BSeriesTaskSignal getResponseSignal() {
        BSeriesTaskSignal signal = new BSeriesTaskSignal();
        signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_TRAP_REG_RESP);
        return signal;
    }

    @Override
    protected void handleResponse() {
        if(isReg) {
            super.handleResponse();
        }
    }
}
