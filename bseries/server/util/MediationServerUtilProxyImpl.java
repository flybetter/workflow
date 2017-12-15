package com.calix.bseries.server.util;

import com.calix.bseries.server.task.BSeriesMediationSignal;
import com.calix.bseries.server.task.BSeriesTaskSignal;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.server.process.CMSProcess;
import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import com.occam.ems.common.dataclasses.OperationFailure;
import com.occam.ems.common.defines.ErrorDefines;
import com.occam.ems.common.proxy.MediationServerUtilProxy;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 8/19/11
 * Time: 5:04 PM
 */
public class MediationServerUtilProxyImpl implements MediationServerUtilProxy {
    private static Logger log = Logger.getLogger(MediationServerUtilProxyImpl.class);
    private static long TIME_OUT = 1 * 90 * 1000L;
    private static MediationServerUtilProxyImpl instance = new MediationServerUtilProxyImpl();
    private AtomicLong requestId;
    private Map<Long, BSeriesMediationSignal> requests = new ConcurrentHashMap<Long, BSeriesMediationSignal>();
    public static MediationServerUtilProxyImpl getInstance() {
        return instance;
    }

    private MediationServerUtilProxyImpl() {
        requestId = new AtomicLong(0);
        ResourceBundle occamCommonRes = ResourceBundle.getBundle(ResourceBundleUtil.RES_COMMON);
        try {
        	TIME_OUT = Long.parseLong(occamCommonRes.getString(ServiceMgmtConstants.MEDIATION_TIMEOUT));
        }catch(Exception ex) {        	
        	log.error("Error when gettting property for mediationserver.timeout. ", ex);
        	TIME_OUT = 1 * 90 * 1000L;
        }
    }

    @Override
    public void performMediationRequest(OccamProtocolRequestResponse requestResponseObject) {
        long id = requestId.getAndIncrement();        
        try {
            BSeriesMediationSignal signal = new BSeriesMediationSignal();
            signal.setType(BSeriesTaskSignal.SIG_TYPE_B6_MEDIATION_REQ);
            signal.setRequestId(id);
            signal.setOccamProtocolRequestResponse(requestResponseObject);
            requests.put(id, signal);    
            synchronized (signal) {
                try { 
                	JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.BSERIES_MGR_ID), signal);
                    signal.wait(TIME_OUT); 
                } catch (InterruptedException ie) {
                    log.warn("MediationServerUtilProxyImpl:Interrupted", ie);
                }
            }                       
            if(signal.getType() == BSeriesMediationSignal.SIG_TYPE_B6_MEDIATION_REQ) {
                //todo: timed out 
                log.warn("MediationServerUtilProxyImpl: Request timedout ");
			}
		} finally {
			requests.remove(id);
		}
    }

    public void handleMediationResponse(BSeriesTaskSignal signal) {
        BSeriesMediationSignal respSignal = (BSeriesMediationSignal) signal;
        BSeriesMediationSignal reqSignal = requests.get(respSignal.getRequestId());       
        if(reqSignal != null) { 
            reqSignal.setType(BSeriesMediationSignal.SIG_TYPE_B6_MEDIATION_RESP);
            reqSignal.getOccamProtocolRequestResponse().copyFrom(respSignal.getOccamProtocolRequestResponse());            
            synchronized (reqSignal) {
                reqSignal.notifyAll();
            }
        } else {
            log.warn("MediationServerUtilProxyImpl:signal not in the request map: " + respSignal.getRequestId() );
        }
    }
}