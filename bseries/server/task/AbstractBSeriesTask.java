package com.calix.bseries.server.task;

import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.server.process.CMSProcess;
import com.occam.ems.common.dataclasses.DevProperty;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.logging.OccamLoggerUtility;
import com.occam.ems.mediation.protocol.OccamProtocolProperty;
import com.occam.ems.mediation.protocol.OccamProtocolProvider;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 11:38 PM
 *
 * Base class for B6 tasks
 */
public abstract class AbstractBSeriesTask implements Runnable {
    private static final Logger log = Logger.getLogger(AbstractBSeriesTask.class);
    protected String networkName;
    protected String ipAddr;
    protected String eqptType;
    protected String version;
    protected String snmpReadCommunity;
    protected OccamProtocolRequestResponse reqRespObj;

    protected AbstractBSeriesTask(){
    }

    public AbstractBSeriesTask(String ip) {
       this.ipAddr = ip;
    }

    public AbstractBSeriesTask(BSeriesTaskSignal signal) {
        this.ipAddr = signal.getIpAddress();
        this.eqptType = signal.getEquipType();
        this.version = signal.getVersion();
        this.networkName = signal.getNetworkName();
        this.snmpReadCommunity = signal.getSnmpReadCommunity();
    }

    protected void init() {
        DevProperty devProperty = new DevProperty();
        devProperty.setIPAddress(ipAddr);
        devProperty.setProperty(DevProperty.DEVICE_VERSION_ATTR_NAME, version);
        devProperty.setProperty(DevProperty.DEVICE_TYPE_ATTR_NAME, eqptType);

        reqRespObj = new OccamProtocolRequestResponse();
        reqRespObj.setDeviceProperty(devProperty);
        reqRespObj.setOperationName(getOperationName());
        reqRespObj.medLogPrefix=ipAddr;
        reqRespObj.medAssLogger= OccamLoggerUtility.getLogger("discovery");
    }

    protected abstract String getOperationName();

    public void execute() {
        init();
        OccamProtocolProvider provider = new OccamProtocolProvider();
        OccamProtocolProperty protocolProperty = new OccamProtocolProperty();
        protocolProperty.setRequestResponseObject(reqRespObj);
        provider.syncSend(protocolProperty);
    }

    @Override
    public void run() {
        try {
            execute();
            handleResponse();
        } catch (Throwable th) {
            log.error("Error processing B6 task. IP: " + ipAddr, th);
        }
    }

    protected abstract BSeriesTaskSignal getResponseSignal();

    protected void handleResponse() {
        BSeriesTaskSignal signal = getResponseSignal();
        signal.setIpAddress(ipAddr);
        signal.setNetworkName(networkName);
        List error = reqRespObj.getErrorInfo();
        if(error != null && !error.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for(Iterator it = error.iterator(); it.hasNext();) {
                sb.append(it.next()).append(";");
            }
            sb.setLength(sb.length() - 1);
            signal.setError(sb.toString());
        }

        JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.CMS_SERVER_ID), signal);
    }
}
