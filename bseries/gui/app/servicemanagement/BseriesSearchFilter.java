
/*
 * DSLSASummaryScreen.java
 *
 * Created on November 27, 2007, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.gui.app.servicemanagement;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.ems.EMSInit;
import com.calix.ems.model.EMSRoot;
import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.model.BaseCalixNetwork;
import com.calix.system.gui.model.BaseEMSDevice;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.IValuePanel;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.TreeValue;
import com.objectsavvy.base.persistence.meta.TreeValueType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.app.servicemanagement.serviceactivation.FiberServiceSummaryPanel;
import com.occam.ems.client.ccl.ServiceMgmtCustomerIfc;
import com.occam.ems.client.ccl.ServiceMgmtSessionManager;
import com.occam.ems.client.util.ClientUtil;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.common.dataclasses.ServiceMgmtRequestResponse;
import com.occam.ems.common.defines.APINames;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import java.util.Properties;

import javax.swing.JPanel;
/**
 *
 * @author brao
 */
public class BseriesSearchFilter {
    private static BseriesSearchFilter bseriesSearchFilter = null;
    private Map<String,JPanel> smMap = new HashMap<String,JPanel>();
    /**
     * Creates a new instance of BseriesSearchFilter
     */
    public BseriesSearchFilter() {
    	smMap.put("Fiber Service Activation", new BseriesFiberSASummaryScreen());
    	smMap.put("Copper Service Activation", new BseriesDSLSASummaryScreen());
    	smMap.put("PWE3 Service Activation", new BseriesPWE3SASummaryScreen());
    	smMap.put("TLS Service Activation", new BseriesTLSSASummaryScreen());
    }
 
    public static BseriesSearchFilter getInstance(){
    	if (bseriesSearchFilter == null)
    		bseriesSearchFilter = new BseriesSearchFilter();
    	return bseriesSearchFilter;
    }
    public Map<String,JPanel> getPanelMap(BaseEMSDevice emsNetwork,Map<String,JPanel> networkPanelMap){
    	if (emsNetwork == null)
    		return networkPanelMap;
    	CalixB6Device b6Obj = (CalixB6Device)emsNetwork;  
    	IValue model = b6Obj.getAttributeValue("Model");
    	IValue swVer = b6Obj.getAttributeValue("SWVersion");
    //	Map<String,JPanel> networkPanelMap = new HashMap<String,JPanel>();
    	try {
			String eqpType = (String) model.convertTo(String.class, "EMS");
			String swVersion = (String) swVer.convertTo(String.class, "EMS");
			System.out.println("Equipment Type " + eqpType);
			System.out.println("SW Version " + swVersion);
			if (eqpType == null || ClientUtil.isGponOlt(eqpType) || ClientUtil.isValereDevice(eqpType) 
					|| ClientUtil.isWestronicsDevice(eqpType)){
				return networkPanelMap;
			}
			if (ClientUtil.isFiberDevice(eqpType)){
				BseriesFiberSASummaryScreen fiberScreen = (BseriesFiberSASummaryScreen)smMap.get("Fiber Service Activation");
		    	fiberScreen.setSelectOltPort(false, null);	
		    	networkPanelMap.put("Fiber Service Activation",fiberScreen);				
			}else if (ClientUtil.isADSLDevice(eqpType) || ClientUtil.isVDSLDevice(eqpType) || 
					ClientUtil.isPOTSDevice(eqpType)){
				networkPanelMap.put("Copper Service Activation", smMap.get("Copper Service Activation"));
			}
			if (ClientUtil.isFiberDevice(eqpType) || ClientUtil.isPWE3BLC(eqpType)){
				networkPanelMap.put("PWE3 Service Activation", smMap.get("PWE3 Service Activation"));
			}
			if (ClientUtil.isVDSLDevice(eqpType) || (
					ClientUtil.isFiberDevice(eqpType) && ClientUtil.isTLSSupported(swVersion))){
				networkPanelMap.put("TLS Service Activation", smMap.get("TLS Service Activation"));
			}
			
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return networkPanelMap;
    	
    }
    public JPanel getFiberServiceSummaryPanel(String ontIP){
    	BseriesFiberSASummaryScreen fiberScreen = (BseriesFiberSASummaryScreen)smMap.get("Fiber Service Activation");
    	fiberScreen.setSelectOltPort(true, ontIP);		
    	return fiberScreen;
    }
    
    public static String getB6IP(String ontIP){
    	System.out.println("getB6IP : ONT IP " + ontIP);
    	ServiceMgmtRequestResponse req = new ServiceMgmtRequestResponse();
        req.setAPIName(APINames.GET_B6_OLT_IP_AND_PORT);
        req.setParams(ontIP);
        ServiceMgmtRequestResponse res = ServiceMgmtSessionManager.getInstance().syncSend(req);
        System.out.println("Sending request to Get B6 Inventory");
        HashMap<String, Object> oltInfo = (HashMap<String, Object>)res.getData();
		String oltIP = (String)oltInfo.get(ServiceMgmtConstants.IP);
		if (oltIP != null){
			System.out.println("getB6IP : OLT IP " + oltIP);
			return oltIP;
		}
		return null;
    }
    public static OSBaseObject getB6Device(String ipAddress) {
        OSBaseObject calixNetwork = null;
    	if(ipAddress != null) {
    		boolean activated = false;
    		IDatabase db = EMSInit.getReadonlyDatabase();
            try {             
                if (!db.isActive()) {
                    db.begin();
                    activated = true;
                }
                EMSRoot root = EMSInit.getReadonlyEMSRoot(db);
                Collection<BaseEMSDevice> networks = root.getB6Networks();
                db.rollback();
                // convert the networks to a hashmap of name and network
                HashMap<String, BaseEMSDevice> networksMap = new HashMap<String, BaseEMSDevice>();
                BaseEMSDevice b6Network = null;
                for (Iterator<BaseEMSDevice> iter = networks.iterator(); iter.hasNext();) {
                	BaseEMSDevice emsNetwork= iter.next();
                    String ipAddress1 = null;
                    if(emsNetwork.getAttributeValue("IPAddress1") != null){
                    	ipAddress1 = emsNetwork.getAttributeValue("IPAddress1").convertTo(String.class, DomainValues.DOMAIN_TL1);
                    }
                    if(ipAddress1 != null && ipAddress.equals(ipAddress1)) {
                    	b6Network = emsNetwork;
                    	break;
                    }else {
                    	String ipAddress2 = null;
                    	if(emsNetwork.getAttributeValue("IPAddress2") != null){
                    		ipAddress2 = emsNetwork.getAttributeValue("IPAddress2").convertTo(String.class, DomainValues.DOMAIN_TL1);
                    	}
                    	if(ipAddress2 != null && ipAddress.equals(ipAddress2)){
                    		b6Network = emsNetwork;
                    		break;
                    	}
                    }                    	
                }   
                
                if(b6Network != null) {
			        calixNetwork = (OSBaseObject) TypeRegistry.getInstance().getRecordType("CalixB6Node").newInstance();
			        calixNetwork.setAttributeValue("Name", b6Network.getNetworkName());
			        calixNetwork.setIdentityValue(BaseCalixNetwork.getNetworkAid(b6Network.getNetworkName()));
                }
		        
            } catch (Exception pex) {
                Code.warning(pex);
            } finally {
                if (activated)
                    try {
                        db.rollback();
                    } catch (Exception ignore) {
                    }
            }            
    	}
    	return calixNetwork;
    }
}
