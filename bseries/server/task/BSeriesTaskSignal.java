package com.calix.bseries.server.task;

import java.util.HashMap;
import java.util.Properties;

import com.calix.ems.core.signal.InterProcessSignal;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/6/11
 * Time: 9:38 AM
 *
 * B6 Task signal. This class servers as both request signal and response signal
 */
public class BSeriesTaskSignal extends InterProcessSignal {
    private String ipAddress;
    private String equipType;
    private String version;
    private String networkName;
    private String snmpReadCommunity;

    private String error;
 

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getSnmpReadCommunity() {
        return snmpReadCommunity;
    }

    public void setSnmpReadCommunity(String snmpReadCommunity) {
        this.snmpReadCommunity = snmpReadCommunity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    
}
