package com.calix.bseries.server.session;

import com.opticalsolutions.eh.snmp.SNMPParameters;
import com.opticalsolutions.nodecomm.snmp.BaseReq;
import com.opticalsolutions.nodecomm.snmp.ThirdPartyWalkTableReq;
import com.opticalsolutions.properties.EmsProperties;
import com.opticalsolutions.util.Base_SNMPUtil;
import com.opticalsolutions.util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.jms.*;

public class B6TriggerActiveAlarmReq extends BaseReq {
    private static String RESYNCACTIVEALARMSOID = "1.3.6.1.4.1.6066.2.1.3.3.1.16.0";
    private static String CLASS = "B6TriggerActiveAlarmReq";
    
    public B6TriggerActiveAlarmReq (SNMPParameters snmpParams){
        this.snmpParams = snmpParams;
    }

    @Override
    public void init() {
        try
        {
            snmpUtil.initContext(snmpParams);
            snmpUtil.setOwner(this);    
            
            Vector pduVarbinds = new Vector();
            Object variableBinding = snmpUtil.mkVarbind(RESYNCACTIVEALARMSOID, new Integer(Base_SNMPUtil.OCTET_STRING), new String("0"));
            pduVarbinds.add(variableBinding);
            
            snmpUtil.sendSetPdu(pduVarbinds);
            setActive();
        }
        catch (Exception exc){
            Logger.error(B6TriggerActiveAlarmReq.class.getName() + ".init Exception: " + exc.getMessage());
        }
    }

    @Override
    public void handleResp(Object[] respVarbinds, int status, String statusStr) {

        if (statusStr == null){
            Logger.debug(1, B6TriggerActiveAlarmReq.CLASS + ".handleResp", "Get SNMPSet Response WITHOUT error.");
        }else {
            Logger.debug(1, B6TriggerActiveAlarmReq.CLASS + ".handleResp", "Get SNMPSet Response WITH error: " + statusStr);
        }
        setInactive();
        // Done - clean up.
        snmpUtil.destroyContext();
        snmpUtil = null;
    }
    
}
