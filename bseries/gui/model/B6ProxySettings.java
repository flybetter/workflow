package com.calix.bseries.gui.model;

import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;

public class B6ProxySettings extends BaseEMSObject{
    
    public static String TYPE = "B6ProxySettings";

    public static String ATTR_IPADDRESS0 = "IpAddress0";
    public static String ATTR_PORT0 = "Port0";
    public static String ATTR_IPADDRESS1 = "IpAddress1";
    public static String ATTR_PORT1 = "Port1";
    public static String ATTR_IPADDRESS2 = "IpAddress2";
    public static String ATTR_PORT2 = "Port2";
    public static String ATTR_IPADDRESS3 = "IpAddress3";
    public static String ATTR_PORT3 = "Port3";
    public static String ATTR_IPADDRESS4 = "IpAddress4";
    public static String ATTR_PORT4 = "Port4";

    public B6ProxySettings(RecordType pType) {
        super(pType);
    }

    public static RecordType getRecordType(){
        return TypeRegistry.getInstance().getRecordType(TYPE);
    }
    
}
