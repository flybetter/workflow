package com.calix.bseries.gui.model;

import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;

public class B6Settings extends BaseEMSObject{
    
    public static String TYPE = "B6Settings";

    public static String ATTR_WEBUSERNAME = "WebUsername";
    public static String ATTR_WEBPASSWORD = "WebPassword";
    public static String ATTR_CLIUSERNAME = "CliUsername";
    public static String ATTR_CLIPASSWORD = "CliPassword";
    public static String ATTR_ENABLEPASSWORD = "EnablePassword";
    public static String ATTR_READCOMMUNITY = "readCommunity";
    public static String ATTR_WRITECOMMUNITY = "writeCommunity";
    
    public B6Settings(RecordType pType) {
        super(pType);
    }

    public static RecordType getRecordType(){
        return TypeRegistry.getInstance().getRecordType(TYPE);
    }
    
}
