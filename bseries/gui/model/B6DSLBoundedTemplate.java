package com.calix.bseries.gui.model;

import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;

public class B6DSLBoundedTemplate extends BaseEMSObject {
	public static final String TYPE_NAME = "B6DSLBoundedTemplate";
	
	public static final String OPERATION_ADD = "ADD";
	public static final String OPERATION_MODIFY = "MOD";
	public static final String OPERATION_DELETE = "DEL";
	
	public static final String ADD_TAB_NAME = "addbondedDSL_service";
	public static final String MOD_TAB_NAME = "modifybondedDSL_service";
	public static final String DEL_TAB_NAME = "deletebondedDSL_service";

	public static final String ADD_TITLE_NAME = "add_bondedDSL_service";
	public static final String MOD_TITLE_NAME = "modify_bondedDSL_service";
	public static final String DEL_TITLE_NAME = "delete_bondedDSL_service";
	
	public static final String ATTR_RESPONSEDETAIL = "ResponseDetail";
	public static final String ATTR_NAME = "Name";
	public static final String ATTR_DEVICE_HOST_NAME = "device_host_name";
	public static final String ATTR_SERVICE = "service";
	public static final String ATTR_SERVICE_TYPE = "service_type";
	public static final String ATTR_OPERATION = "operation";
	public static final String ATTR_IPADDRESS1 = "IPAddress1";
	public static final String ATTR_TEMPLATESOURCE = "TemplateSource";
	public static final String ATTR_CMSUSERNAME = "CMSUserName";
	public static final String ATTR_USERIP = "UserIp";
	public static final String ATTR_STARTTIME = "StartTime";
	public static final String ATTR_ENDTIME = "EndTime";
	public static final String ATTR_RESULT = "Result";
	public static final String ATTR_STARTTIMELONG = "StartTimeLong";
	public static final String ATTR_ENDTIMELONG = "EndTimeLong";
	public static final String ATTR_DSL_INTERFACE_NUMBER = "dsl_interface_number";
	public static final String ATTR_DSL_INTERFACE_NUMBER_2 = "dsl_interface_number_2";
	public static final String ATTR_DSL_PROFILE_NAME = "dsl_profile_name";
	public static final String ATTR_KEY_INFO = "key_info";
	public static final String ATTR_BONDING_GROUP_NUMBER = "bonding_group_number";
	public static final String ATTR_SERVICE_NUMBER_INT = "service_number_int";
	public static final String ATTR_PVC_SERVICE_INT = "pvc_service_int";
	public static final String ATTR_ACCESS_PROFILE_NAME_INT = "access_profile_name_int";
	public static final String ATTR_ACTIVATE_SERVICE_INT = "activate_service_int";
	public static final String ATTR_ACTIVATE_DSL_PORT = "active_dsl_port";
	public static final String ATTR_MAC_LEARNING_INT = "mac_learning_int";
	public static final String ATTR_MAC_LIMIT_INT = "mac_limit_int";
	public static final String ATTR_IGMP_GROUP_LIMIT_INT = "igmp_group_limit_int";
	public static final String ATTR_MAC_ADDRESS_INT = "mac_address_int";
	public static final String ATTR_IP_ADDRESS_INT = "ip_address_int";
	public static final String ATTR_IPMASK_INT = "ipmask_int";
	public static final String ATTR_IPDG_INT = "ipdg_int";
	public static final String ATTR_TPSTC_MODE = "tpstc_mode";
	public static final String ATTR_MATCH_VLAN= "match_vlan";

    public B6DSLBoundedTemplate(RecordType pType) {
        super(pType);
    }

    public static B6DSLBoundedTemplate newInstance() {
    	B6DSLBoundedTemplate instance = null;
        try {
            instance = (B6DSLBoundedTemplate)TypeRegistry.getInstance().getRecordType(TYPE_NAME).newInstance();
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
    
    public String getMatch_Vlan(){
    	try {
            IValue parentReg = this.getAttributeValue(ATTR_MATCH_VLAN);
            if (parentReg != null)
                return (String) parentReg.convertTo(String.class, null);
        } catch (MappingException mex) {
            Code.warning(mex);
        }
        return null;
    }
}