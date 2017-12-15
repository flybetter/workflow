package com.calix.bseries.gui.model;

import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;

public class B6CommandResult extends BaseEMSObject {
	public static final String TYPE_NAME = "B6CommandResult";
	
	public static final String ATTR_RESPONSE = "Response";
	public static final String ATTR_REQUEST = "Request";
	

    public B6CommandResult(RecordType pType) {
        super(pType);
    }

    public static B6CommandResult newInstance() {
    	B6CommandResult instance = null;
        try {
            instance = (B6CommandResult)TypeRegistry.getInstance().getRecordType(TYPE_NAME).newInstance();
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