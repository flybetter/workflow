package com.calix.bseries.gui.model;

import com.calix.ems.datastore.BaseEMSAction;
import com.objectsavvy.base.persistence.meta.RecordType;

public class EMSBseriesRangeDiscoveryAction extends BaseEMSAction {
    
    public static String  TYPE = "EMSBseriesRangeDiscoveryAction";
    public static String  ATTR_COMMUNITY = "community";
    public static String  ATTR_AUTOCHASSIS = "autoChassis";

    public static String  ATTR_ALLOWBSERIES = "allowBseries";
    public static String  ATTR_ALLOWEXA = "allowExa";

    public EMSBseriesRangeDiscoveryAction(RecordType recordType) {
        super(recordType);
    }

}
