package com.calix.bseries.server.task;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/21/11
 * Time: 3:29 PM
 *
 * B6/2300 AE ONT discovery response signal
 */
public class BSeriesDiscoveryResponseSignal extends BSeriesTaskSignal {
    private String hwVersion;
    private String serialNum;
    private String macAddress;
    private String manufactureDate;
    private String shelfId;
    private Integer slotId;
        
    public String getHwVersion() {
        return hwVersion;
    }

    public void setHwVersion(String hwVersion) {
        this.hwVersion = hwVersion;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }
}
