package com.calix.bseries.gui.utils;

import com.calix.ems.common.constants.SecurityConstants;
import com.calix.ems.gui.EMSGui;
import com.calix.ems.model.EMSRoot;
import com.calix.ems.model.EMSUserGroup;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.util.debug.Code;

public class B6SecurityHelper {

	public static int getPriviledge(EMSRoot root) {

		try {
			EMSUserGroup group = root.getCurrentUser().getUserGroup();
			Integer b6SecurityLevel = group.getAttributeValue(
					EMSUserGroup.ATTR_B6_SECURITY, Integer.class);
			if (b6SecurityLevel != null) {
				return b6SecurityLevel.intValue();
			}
		} catch (Exception e) {
			Code.warning(
					"Error while checking privilege for current user, error: "
							+ e.getMessage(), e);
		}
		return -1;
	}
	
	 public static boolean isCreate() {
          boolean isCreate = true;
          int value = 1;
          try {
                  EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                  if (usrGrp != null) {
                          IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_CREATE);
                          if (val != null) {
                                  Integer sLvl = val.convertTo(Integer.class, null);
                                  if (sLvl != null) {
                                          value = sLvl.intValue();
                                  }
                          }
                  }
                  if (value == 0) {
                	  isCreate = false;
                  }
          } catch (Throwable e) {
                  Code.warning("Error while checking privilege for current user, error: "
                                                  + e.getMessage(), e);
          }
          return isCreate; // default read-only
  }
  
	 public static boolean isDAA() {
         boolean isDAA = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_DAA);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
               	  isDAA = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isDAA; // default read-only
         }
	 
	 public static boolean isAction() {
         boolean isAct = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_ACTION);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
               	  isAct = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isAct; // default read-only
         }
	 
	 public static boolean isService() {
         boolean isService = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_SERVICE);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isService = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isService; // default read-only
         }
	 
	 public static boolean isB6_214() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_214);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_216() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_216);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_316() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_316);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_318() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_318);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_450() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_450);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_452() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_452);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_252() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_252);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isB6_256() {
         boolean isB6 = true;
         int value = 1;
         try {
                 EMSUserGroup usrGrp = EMSGui.getInstance().getCurrentUserGroup();
                 if (usrGrp != null) {
                         IValue val = usrGrp.getAttributeValue(EMSUserGroup.ATTR_B6_256);
                         if (val != null) {
                                 Integer sLvl = val.convertTo(Integer.class, null);
                                 if (sLvl != null) {
                                         value = sLvl.intValue();
                                 }
                         }
                 }
                 if (value == 0) {
                	 isB6 = false;
                 }
         } catch (Throwable e) {
                 Code.warning("Error while checking privilege for current user, error: "
                                                 + e.getMessage(), e);
         }
         return isB6; // default read-only
         }
	 
	 public static boolean isAllB6Write(){
		 return isB6_214() && isB6_216() && isB6_316() && isB6_318() && isB6_450() && isB6_452() && isB6_252() && isB6_256();
	 }
	 
	 public static boolean isB6write(String model){
		 boolean write = true;
         if(model != null ){
           if(model.equals("B6-214")){
                   write = isB6_214();
           }else if(model.equals("B6-216")){
                   write = isB6_216();
           }else if(model.equals("B6-316")){
                   write = isB6_316();
           }else if(model.equals("B6-318")){
                   write = isB6_318();
           }else if(model.equals("B6-450")){
                   write = isB6_450();
           }else if(model.equals("B6-452")){
                   write = isB6_452();
           }else if(model.equals("B6-252")){
                   write = isB6_252();
           }else if(model.equals("B6-256")){
                   write = isB6_256();
           }
         }
           return write;
	 }
	 
	    public Integer getCurrentUserTopologyPriviledge() {
	        return EMSGui.getInstance().getCurrentUserTopologyPriviledge();
	    }
	 
	  	public static boolean isReadPriviledge(OSBaseObject obj) {
		boolean read = false;
		if(SecurityConstants.SECURITY_READ_PRIVILEGE.equals(EMSGui.getInstance().getCurrentUserPrivilege(obj))) {
			read = true;
		}
		return read;
	}
}
