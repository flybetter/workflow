package com.calix.bseries.gui.utils;

import com.calix.ems.gui.EMSGui;
import com.calix.ems.model.EMSUserGroup;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.util.gui.B6SecurityUtilProxy;


public class B6SecurityUtilImpl implements  B6SecurityUtilProxy{



	 public  boolean isAction() {
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
	 
	 public  boolean isService() {
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
	 
	 public  boolean isB6_214() {
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
	 
	 public  boolean isB6_216() {
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
	 
	 public  boolean isB6_316() {
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
	 
	 public  boolean isB6_318() {
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
	 
	 public  boolean isB6_450() {
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
	 
	 public  boolean isB6_452() {
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
	 
	 public  boolean isB6_252() {
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
	 
	 public  boolean isB6_256() {
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
	 
	 public boolean isAllB6Write(){
		 return isB6_214() && isB6_216() && isB6_316() && isB6_318() && isB6_450() && isB6_452() && isB6_252() && isB6_256();
	 }
}
