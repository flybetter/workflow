package com.calix.bseries.gui.model;

import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;

public class B6CommandResultData extends BaseEMSObject {
	public static final String TYPE_NAME = "B6CommandResultData";
	
	public static final String ATTR_RESPONSE = "Response";
	public static final String ATTR_REQUEST = "Request";
	public static final String ATTR_COMMANDTYPE = "CommandType";
	public static final String ATTR_NETWORKNAME = "NetworkName";
	public static final String ATTR_NETWORKIP = "NetworkIP";
	public static final String ATTR_STATUS = "Status";
	public static final String ATTR_STARTTIME = "StartTime";
	public static final String ATTR_ENDTIME = "EndTime";
	

    public B6CommandResultData(RecordType pType) {
        super(pType);
    }

    public static B6CommandResultData newInstance() {
    	B6CommandResultData instance = null;
        try {
            instance = (B6CommandResultData)TypeRegistry.getInstance().getRecordType(TYPE_NAME).newInstance();
        } catch (Exception ex) {
            Code.warning(ex);
        }
        return instance;
    }

    public void resetAttributeValues() {
    	
    }

    public Integer getIntAttributeValue(String attrName) {
        Integer intVal = null;
        IValue val = getAttributeValue(attrName);
        try {
            if(val != null) {
                intVal = (Integer) val.convertTo(Integer.class, DomainValues.DOMAIN_TL1);
            }
        } catch(MappingException mex) {
            Code.warning(mex);
        }
        return intVal;
    }
}