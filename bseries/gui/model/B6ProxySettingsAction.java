package com.calix.bseries.gui.model;

import com.calix.ems.datastore.BaseEMSAction;
import com.objectsavvy.base.persistence.meta.RecordType;

public class B6ProxySettingsAction extends BaseEMSAction {
    
    public static final String TYPE_NAME = "B6ProxySettingsAction";
    
    public static final String ATTR_ACTIONTYPE = "ActionType";


    public B6ProxySettingsAction(RecordType recordType) {
        super(recordType);
    }
}
