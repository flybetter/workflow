package com.calix.bseries.server.util;

import com.calix.bseries.server.dbmodel.CalixB6Chassis;
import com.calix.ems.security.utils.bseries.BseriesSecurityProxy;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.system.server.dbmodel.EMSAid;

public class BseriesSecurityProxyUtilImpl implements BseriesSecurityProxy {

	@Override
	public String getCalixB6Chassis(String chassisName) {
		return ((CalixB6Chassis) CMSCacheManager.getCacheManager()
				.getEMSObject((new CalixB6Chassis()).getHierarchyIntegers(),
						new EMSAid(chassisName))).getRegion();
	}

}
