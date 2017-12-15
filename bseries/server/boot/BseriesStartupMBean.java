package com.calix.bseries.server.boot;

import com.calix.system.server.boot.DeviceStartupMBean;

import javax.management.ObjectName;

public interface BseriesStartupMBean extends DeviceStartupMBean {
    public void setSystemStartup(ObjectName name);
}
