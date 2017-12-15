package com.calix.bseries.server.task;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/21/11
 * Time: 3:29 PM
 *
 * B6 Chassis discovery response signal
 */
public class BSeriesChassisDiscoveryResponseSignal extends BSeriesTaskSignal {
    private String chassisType;
    private String displayString;
    private String serialNumber;
    private String manufacturingDate;
    
    public String getChassisType() {
        return chassisType;
    }

    public void setChassisType(String chassisType) {
        this.chassisType = chassisType;
    }

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

}
