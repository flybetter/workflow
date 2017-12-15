package com.calix.bseries.server.rangediscovery;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SimpleSnmpClient {
    Snmp snmp;
    String ip;
    
    public SimpleSnmpClient(String address){
        this.ip = address;
    }

    public void start() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        // Do not forget this line!
        transport.listen();
    }
    
    public void stop() throws IOException {
        snmp.close();
    }
    
    public String getAsString(OID oid) throws IOException {
        return getAsString(oid, "public");
    }
    
    public String getAsString(OID oid, String community) throws IOException {
        String resp = null;
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(oid));
        CommunityTarget target = getTarget();
        target.setCommunity(new OctetString(community));
        ResponseEvent event = snmp.get(pdu, target);
        PDU response = event.getResponse();
        if(response != null) {
            resp = response.get(0).getVariable().toString();
        }else {
            throw new IOException("No Response");
        }
        return resp;
    }
    
    public void getAsString(OID oid, ResponseListener listener) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(oid));
        snmp.get(pdu, getTarget(), null, listener);
    }
    
    public Map<String, String> getAsMap(Map<String, OID> oids) throws IOException {
        return getAsMap(oids, "public");
    }
    
    /**
     * Load varbind values for all given OIDs. 
     * @param oids: The OIDs need to load from remote snmp device. Each OID have a key in the map, the key will be 
     * used as key in return map as well.
     * @param community: the community string of the remote snmp device.
     * @return: A Map whose key is the key from the oids parameter, and whose value is varbind string value from remote
     * snmp device.
     * @throws IOException
     */
    public Map<String, String> getAsMap(Map<String, OID> oids, String community) throws IOException {
        Map<String, String> resps = new HashMap<String, String>();
        // Use array to remember the varbind sequence in PDU.
        String[] keys = new String[oids.size()];
        PDU pdu = new PDU();
        int index = 0;
        for (Map.Entry<String, OID> entry : oids.entrySet()) {
        	pdu.add(new VariableBinding(entry.getValue()));
        	keys[index++] = entry.getKey();
        }
        CommunityTarget target = getTarget();
        target.setCommunity(new OctetString(community));
        ResponseEvent event = snmp.get(pdu, target);
        PDU response = event.getResponse();
        if(response != null) {
        	for (int i=0; i<oids.size(); i++) {
        		String key = keys[i];
        		resps.put(key, response.get(i).getVariable().toString());
        	}
        }else {
            throw new IOException("No Response");
        }
        return resps;
    }
    
    private CommunityTarget getTarget() {
        Address targetAddress = GenericAddress.parse("udp:" + ip + "/161");
        CommunityTarget target = new CommunityTarget();
        target.setAddress(targetAddress);
        target.setRetries(3);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public static void main(String[] args){
        SimpleSnmpClient client = new SimpleSnmpClient("10.11.67.26");
        try {
            client.start();
            String sysDescr = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
            System.out.println(sysDescr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                client.stop();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
