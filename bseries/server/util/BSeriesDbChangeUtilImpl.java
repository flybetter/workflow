package com.calix.bseries.server.util;


import java.util.Properties;

import org.apache.log4j.Logger;

import com.calix.bseries.server.dbmodel.BseriesNotificationObject;
import com.calix.bseries.server.dbmodel.BseriesTlvConstants;
import com.calix.ems.session.DBChangeListener;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.occam.ems.common.proxy.BSeriesDbChangeUtilProxy;

/**
 * User: hzhang
 * Date: 12/30/2011
 */
public class BSeriesDbChangeUtilImpl implements BSeriesDbChangeUtilProxy {
    private static Logger logger = Logger.getLogger(BSeriesDbChangeUtilImpl.class);
    private static BSeriesDbChangeUtilImpl instance = new BSeriesDbChangeUtilImpl();
    
    public static BSeriesDbChangeUtilImpl getInstance() {
        return instance;
    }
    
	@Override
	public void fireDbChange( String moduleId) {
		fireDbChange(null, moduleId);
	}
	
	@Override
	public void fireDbChange(Properties pro, String moduleId) {
		BseriesNotificationObject obj = new BseriesNotificationObject();
        GenericCMSAid aid = new GenericCMSAid(BseriesTlvConstants.tNetworkAid, "CMS");
        obj.setIdentityValue(aid);
        obj.setmoduleId(moduleId);
        if(pro != null){
        	obj.setpropertyString(pro.toString());
        }
        try {
			new DBChangeListener().updateObject(obj, null);
		} catch (Exception e) {
			logger.warn("Fail to send dbchange for B series operation. Error: " + e.getMessage(), e);
		}
	}

}
