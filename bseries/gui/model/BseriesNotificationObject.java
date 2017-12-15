package com.calix.bseries.gui.model;

import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.meta.RecordType;

public class BseriesNotificationObject extends BaseEMSObject {

	private static final long serialVersionUID = -775549823108694719L;
	public static String ACTION_MODULEID = "moduleId";
	public static String ACTION_PROPERTYSTRING = "propertyString";
	
	public BseriesNotificationObject(RecordType pType) {
		super(pType);
	}
	
}
