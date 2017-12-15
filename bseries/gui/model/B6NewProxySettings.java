package com.calix.bseries.gui.model;

import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;

public class B6NewProxySettings extends BaseEMSObject{
    
    public static String TYPE = "B6NewProxySettings";

    public static String ATTR_IPADDRESS1 = "ProxyServerA";
    public static String ATTR_PORT1 = "PortA";
    public static String ATTR_IPADDRESS2 = "ProxyServerB";
    public static String ATTR_PORT3 = "PortB";

    public B6NewProxySettings(RecordType pType) {
        super(pType);
    }

    public static RecordType getRecordType(){
        return TypeRegistry.getInstance().getRecordType(TYPE);
    }
    
}
