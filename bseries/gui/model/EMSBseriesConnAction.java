package com.calix.bseries.gui.model;

import com.calix.ems.datastore.BaseEMSAction;
import com.objectsavvy.base.persistence.meta.RecordType;

public class EMSBseriesConnAction extends BaseEMSAction {
    
    public static final String TYPE_NAME = "EMSBseriesConnAction";
    
    public static final String ATTR_NETWORK = "Network";
    public static final String ATTR_CONNECTTYPE = "Type";
    public static final String ATTR_ISRETRIEVEALARMS = "IsRetrieveAlarms";

    public EMSBseriesConnAction(RecordType recordType) {
        super(recordType);
    }
    
    

}
