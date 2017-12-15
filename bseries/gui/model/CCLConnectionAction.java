package com.calix.bseries.gui.model;

import com.calix.ems.datastore.BaseEMSAction;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;

@SuppressWarnings("serial")
public class CCLConnectionAction extends BaseEMSAction {
    public static String TYPE_NAME = "CCLConnectionAction";
    public static String ATTR_REQUESTID = "requestId";
    public static String ACTION_BINARYPACKAGE = "binaryPackage";
    public static String ACTION_ISMULTIPLE = "isMultiPle";
    public static int ACTION_ISMULTIPLE_YES = 1;
    public static int ACTION_ISMULTIPLE_NO = 0;
    public static String ACTION_TOTALNUM = "totalNum";   
    public static String ACTION_NUM = "num";   
    public static String ACTION_REQKEY = "reqKey";   

    public CCLConnectionAction(RecordType recordType) {
		super(recordType);
	}
	public static CCLConnectionAction createAction(OSBaseObject obj) {
		CCLConnectionAction action = null;
		try {
			if(obj != null) {
				action = (CCLConnectionAction)TypeRegistry.getInstance().getRecordType(TYPE_NAME).newInstance();
	            action.setIdentityValue(obj.getIdentityValue());
			}
		} catch (Exception e) {
			Code.warning(e);
		}
		return action;
	}

}
