package com.calix.bseries.gui.model;

import java.util.Collection;
import java.util.Iterator;

import com.calix.ems.EMSInit;
import com.calix.ems.gui.navigation.EMSTreeModelHandler;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.BaseSNMPDevice;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TreeValue;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

public class CalixB6Device extends BaseSNMPDevice {
    
    public static String TYPE_NAME = "CalixB6Device";
    

    public static String ATTR_HWVERSION = "HWVersion";
    public static String ATTR_SWVERSION = "SWVersion";
    public static String ATTR_MACADDRESS = "MACAddress";
    public static String ATTR_SERIALNUMBER = "SerialNumber";
    public static String ATTR_MODEL = "Model";
    public static String ATTR_NETWORKGROUP = "NetworkGroup";
    public static String ATTR_PROFILE ="profile";
    public static String ATTR_WEBUSERNAME ="WebUsername";
    public static String ATTR_WEBPASSWORD ="WebPassword";
    public static String ATTR_ENABLEPASSWORD ="EnablePassword";
    public static String ATTR_MANUFACTUREDATE ="ManufactureDate";
    public static String ATTR_KEEP_CHASSIS ="keepChassis";
    public static String ATTR_TIMEZONE ="TimeZone";
    public static String ATTR_IPADRESS ="IPAddress1";
    
    
    private String parentDisplayName = "";
    public CalixB6Device(RecordType pType) {
        super(pType);
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    public CalixB6Chassis getParentChassis(String chassisName,IDatabase db) {
        
        if (chassisName == null) {
            return null;
        }
        try {
           
            Collection<BaseEMSDevice> chassisObjs = EMSInit.getReadonlyEMSRoot(db).getB6Chassis();
            for (BaseEMSDevice chassis : chassisObjs) {
                if (chassis.getName().equals(chassisName)) {
                    return (CalixB6Chassis)chassis;
                }
            }
        } catch (Exception e) {
            Code.warning(e);
        }
        return null;
    }
    public void setParentDisplayName(String displayName){
    	this.parentDisplayName = displayName;
    }
    
    public String getParentDisplayName(){
    	return parentDisplayName;
    }
    
    // add the method of web cut-through privilege for CMS-8910 by dchen at 11/07/2013
    public String getWebUserName(){
    	try {
            IValue parentReg = this.getAttributeValue(ATTR_WEBUSERNAME);
            if (parentReg != null)
                return (String) parentReg.convertTo(String.class, null);
        } catch (MappingException mex) {
            Code.warning(mex);
        }
        return null;
    }
    
    public String getWebPassword(){
    	try {
            IValue parentReg = this.getAttributeValue(ATTR_WEBPASSWORD);
            if (parentReg != null)
                return (String) parentReg.convertTo(String.class, null);
        } catch (MappingException mex) {
            Code.warning(mex);
        }
        return null;
    }
    
    // CMS-15515 add enable password
    public String getEnablePassword(){
    	try {
            IValue parentReg = this.getAttributeValue(ATTR_ENABLEPASSWORD);
            if (parentReg != null)
                return (String) parentReg.convertTo(String.class, null);
        } catch (MappingException mex) {
            Code.warning(mex);
        }
        return null;
    }
    
    //CMS-15595 just for B6-216
    
    public String getModel(){
    	try {
            IValue parentReg = this.getAttributeValue(ATTR_MODEL);
            if (parentReg != null)
                return (String) parentReg.convertTo(String.class, null);
        } catch (MappingException mex) {
            Code.warning(mex);
        }
        return "???";
    }
    //end the add by dchen at 11/07/2013
    
    public String getName() {
        try {
            TreeValue tv = (TreeValue) getAttributeValue(ATTR_NAME);
            if (tv != null) {
                IValue nameComp = tv.getComponent("EMSId");
                if (nameComp != null) {
                    return nameComp.convertTo(String.class, null);
                }
            }
        } catch (Exception ex) {
            Code.warning(ex);
        }
        return "???";
    }
}
