package com.calix.bseries.gui.utils;


import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.system.gui.model.BaseEMSDevice;
import com.objectsavvy.base.gui.util.GuiUtil;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.occam.ems.client.util.ClientUtil;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.client.util.gui.GponOntInventoryManagerUI;
import java.awt.BorderLayout;

import javax.swing.*;

import java.util.Properties;

public class ViewGponOntPopup extends JDialog {

//    private static ViewGponOntFrame viewGponOntFrame;
    private static ViewGponOntPopup viewGponOntPopup;
    private static GponOntInventoryManagerUI gponMgr;
    /** Creates a new instance of ViewGponOnt Dialog */
    private ViewGponOntPopup()  {   
        super(GuiUtil.getTopFrame(),true);
        gponMgr = GponOntInventoryManagerUI.getInstance();
        gponMgr.setParentPanel(null,this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(gponMgr,BorderLayout.CENTER);
        setSize(1000,550);
        ClientUtil.centerTheDialog(this,getSize());
    }
    
    public static ViewGponOntPopup getViewGponOntPopup(){
    	if(viewGponOntPopup==null){
    		viewGponOntPopup= new ViewGponOntPopup();
    	}
    	return viewGponOntPopup;
    }
   
    public String key() {
        return ConfigUIConstants.GPON_ONT_INVENTORY_PANEL_KEY;
    }

    public void setProperties(Properties[] i_prop) {
        try{
            if(i_prop != null && i_prop[0] != null) {
                setProperties(i_prop[0]);               
            }else { //No row is selected
                //JOptionPane.showMessageDialog(NmsClientUtil.getParentFrame(),"Please select an BLC device to view GPONs","View GPONs", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(Exception e){
            System.out.println("Exception in setProperties() method of ViewGponOntPopup:");
        	e.printStackTrace();
        }
    }
    
    public void setProperties(BaseEMSDevice deviceObj) {
    	Properties devProp = new Properties();
    	 try {
    		    //IValue deviceModel= deviceObj.getAttributeValue(CalixB6Device.ATTR_MODEL);
				//String sNameVal = (String) deviceModel.convertTo(String.class, null);
				
				IValue swVersion= deviceObj.getAttributeValue(CalixB6Device.ATTR_SWVERSION);
				String sSWVersion = (String) swVersion.convertTo(String.class, null);
				
				IValue oltKey= deviceObj.getIPAddress1();
				String sOltIpaddress = (String) oltKey.convertTo(String.class, null);
				
				devProp.put("name", sOltIpaddress);
				devProp.put("entitySoftwareRev", sSWVersion);
				setProperties(devProp);
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    public void setProperties(Properties i_prop) {
        try{
        	gponMgr.setProperties(i_prop);           
        } catch(Exception e){
            System.out.println("Exception in setProperties() method of ViewGponOntPopup:");
        	e.printStackTrace();
        }
    }
    public static GponOntInventoryManagerUI getGponOntInvMgrInstance(){
        return gponMgr = GponOntInventoryManagerUI.getInstance();
    }

}
