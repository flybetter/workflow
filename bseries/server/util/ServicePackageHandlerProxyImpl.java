package com.calix.bseries.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.C7Database;
import com.calix.ems.util.ScheduledTaskUtils;
import com.calix.system.server.dbmodel.TLVClassMap;
import com.occam.ems.common.dataclasses.ServiceMgmtRequestResponse;
import com.occam.ems.common.proxy.ServicePackageHandlerProxy;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;

/**
 * User: hzhang
 * Date: 2/21/2012
 */
public class ServicePackageHandlerProxyImpl implements ServicePackageHandlerProxy {
    private static Logger logger = Logger.getLogger(ServicePackageHandlerProxyImpl.class);
	
    private static ServicePackageHandlerProxyImpl instance = new ServicePackageHandlerProxyImpl();
	public static ServicePackageHandlerProxy getInstance() {
		return instance;
	}
    
	@SuppressWarnings("unchecked")
	public ServiceMgmtRequestResponse getDeviceDetails(
			ServiceMgmtRequestResponse reqRes) {
		try {
			logger.debug("ServiceMgmtAPIHandler: Received request to get B6 Inventory for push from client ");
			
			HashMap<String, List<String>> data = (HashMap<String, List<String>>)reqRes.getParams();
			
			List<String> m_SelectedRegions = data.get("SelectedRegions");
			List<String> m_ExcludedRegions = data.get("ExcludedRegions");
			List<String> m_SelectedNetworks = data.get("SelectedNetworks");
			List<String> m_ExcludedNetworks = data.get("ExcludedNetworks");

		    List<String> m_networksReady = ScheduledTaskUtils.getNetworks(m_SelectedRegions,
        			m_ExcludedRegions,
                    m_SelectedNetworks,
                    m_ExcludedNetworks,
                    null,
                    TLVClassMap.getClassForType("CalixB6Device"));
        	
		    List<String[]> devicesHashList = getDetails(m_networksReady);
            reqRes.setData(devicesHashList);
			return reqRes;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ServiceMgmtAPIHandler: error when getting B6 Inventory for push");
		}
		return reqRes;
	}

	private List<String[]> getDetails(List<String> m_networksReady) {
        List<CalixB6Device> b6Devices = getB6Inventory();

        List<String[]> devicesHashList = new ArrayList<String[]>();
        for (int i = 0; i < b6Devices.size(); i++) {
        	for(int j=0; j < m_networksReady.size(); j++){
        		CalixB6Device calixB6Device = (CalixB6Device) b6Devices.get(i);
        		String oldName = calixB6Device.getDbIdentity();
                String newName = (String) m_networksReady.get(j);
                if(oldName.equals(newName)){
                    String[] deviceInfo = new String[ServiceMgmtConstants.GET_B6_INVENTORY_STRING_LENGTH];
                    deviceInfo[0] = calixB6Device.getIPAddress1();
                    deviceInfo[1] = calixB6Device.getModel();
                    deviceInfo[2] = calixB6Device.getSWVersion();
                	devicesHashList.add(deviceInfo);
                }
        	}
        }
		return devicesHashList;
	}
	
	private List<CalixB6Device> getB6Inventory() {
		List calixB6Devices = null;
        try {
            C7Database.getInstance().beginTransaction();
            calixB6Devices = C7Database.getInstance().findAllObjects("CalixB6Device");
            C7Database.getInstance().commitTransaction();
        } catch (Exception e) {
        	logger.error("Unable to load all B6 Devices from database", e);
            C7Database.getInstance().rollbackTransaction();
        } finally {
            C7Database.getInstance().close();
        }
        return calixB6Devices;
	}

}
