package com.calix.bseries.gui.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.calix.bseries.gui.app.servicemanagement.BseriesSearchFilter;
import com.calix.system.gui.model.BaseCalixNetwork;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;

public class BseriesEthMainWebPanel extends BseriesCutThroughWebPanel {

	
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
	            	//ontIp = "10.245.7.54";
	            	if(ontIp != null){
	            		networkIp = BseriesSearchFilter.getInstance().getB6IP(ontIp);//"10.245.7.16";
	            		if(networkIp != null) {
	            			OSBaseObject calixNetwork =  BseriesSearchFilter.getInstance().getB6Device(networkIp);
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
        	super.populateFieldsFromData(params);
    		String url = "https://"+ networkIp + "/?content=/ethernet/eth_main.html"; 
    		m_browser.loadURL(url);
    	}else{
            CalixMessageUtils.showErrorMessage("Unable to get the OLT for AeOnt " + ontId + " whose ip address is " + ontIp);    		
    	}
    	if(cacheObjects != null) {
    		super.setObjects(cacheObjects);
    	}
    }
}
