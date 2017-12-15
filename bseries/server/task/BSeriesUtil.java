package com.calix.bseries.server.task;

import com.occam.ems.common.dataclasses.SNMPV2AuthData;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 10:17 PM
 *
 * Utility class
 */
public class BSeriesUtil {
    //todo: remove hard coded values
    public static Properties getSnmpRequestParams(String readCommunity) {
        if(readCommunity == null) {
            readCommunity = "public";
        }

        Properties prop = new Properties();
        prop.put("version", "2");
        prop.put("retries", "1");
        prop.put("port", "161");
        prop.put("readCommunity", readCommunity);
        prop.put("writeCommunity", "private");
        prop.put("timeout", "10");
        return prop;
    }

    //todo: remove hard coded values
    public static SNMPV2AuthData getSNMPAuthData(String readCommunity) {
        if(readCommunity == null) {
            readCommunity = "public";
        }

        SNMPV2AuthData data = new SNMPV2AuthData();
        Properties prop = new Properties();
        prop.put("Https.retries", "1");
        prop.put("retries", "0");
        //prop.put("Snmp.writeCommunity", "private");
        //prop.put("writeCommunity", "private");
        prop.put("Ssh.user", "cli");
        prop.put("Ssh.timeout", "5");
        prop.put("Telnet.user", "cli");
        prop.put("version", "2");
        //prop.put("Telnet.password", "occam");
        prop.put("Telnet.timeout", "5");
        prop.put("readCommunity", readCommunity);
        //prop.put("Https.password", "razor123");
        prop.put("Https.timeout", "5");
        prop.put("port", "161");
        prop.put("timeout", "10");
        prop.put("Snmp.retries", "0");
        prop.put("Ssh.port", "22");
        prop.put("Telnet.port", "23");
        //prop.put("Ssh.password", "occam");
        prop.put("Snmp.version", "2c");
        prop.put("Ssh.retries", "1");
        prop.put("Snmp.timeout", "10");
        prop.put("Snmp.port", "161");
        prop.put("Telnet.retries", "1");
        prop.put("Https.port", "443");
        prop.put("Snmp.readCommunity", readCommunity);
        data.setProperties(prop);
        return data;
    }
}
