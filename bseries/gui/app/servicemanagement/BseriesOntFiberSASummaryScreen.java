package com.calix.bseries.gui.app.servicemanagement;

import java.util.ArrayList;
import java.util.Collection;

import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.util.debug.Code;

public class BseriesOntFiberSASummaryScreen extends BseriesFiberSASummaryScreen {
    @Override
    public void populateFieldsFromData(IParameterHolder[][] params) throws ValueException {
    	String networkIp = null;
    	String ontId = null;
    	String ontIp = null;
    	Collection<?> cacheObjects = null;
    	try{
	        if (super.getObjects() != null && super.getObjects().size() > 0) {
	            OSBaseObject localObj = (OSBaseObject) super.getObjects().iterator().next();
	            if(localObj != null && "AeONTPseudoNode".equalsIgnoreCase(localObj.getTypeName())){
	            	ontId = localObj.getAttributeValue("aeontid",String.class);
	            	ontIp = localObj.getAttributeValue("IpAddress", String.class);	            	
	            	if(ontIp != null){
	            		networkIp = BseriesSearchFilter.getB6IP(ontIp);
	            		if(networkIp != null) {
	            			OSBaseObject calixNetwork =  BseriesSearchFilter.getB6Device(networkIp);
	            			if(calixNetwork != null) {
	            		        cacheObjects = super.getObjects();
	            				super.resetObjects();
	            				ArrayList<? super OSBaseObject> emsNetworkList = new ArrayList<OSBaseObject>();
	            				emsNetworkList.add(calixNetwork);
	            				super.setObjects(emsNetworkList);            				
	            			}
	            		}
	            	}
	            }
	        }
	        
    	}catch(Exception e) {
    		Code.warning(e);
    	}
    	if(networkIp != null) {
    		setSelectOltPort(true, ontIp);
        	super.populateFieldsFromData(params);      
    	}else{
            CalixMessageUtils.showErrorMessage("Unable to get the OLT for AeOnt " + ontId + " whose ip address is " + ontIp);    		
    	}
    	if(cacheObjects != null) {
    		super.setObjects(cacheObjects);
    	}
    }
}
