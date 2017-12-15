package com.calix.bseries.gui.utils;

import java.util.Map;

import org.apache.log4j.Logger;

import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.client.util.gui.BseriesGenericClientProxy;

public class BseriesGenericClientImpl implements BseriesGenericClientProxy {

	
	private static Logger logger = Logger.getLogger(BseriesGenericClientImpl.class);
	@Override
	public String getFtpDirPath(Map wizardData) {
		Map editDataMap= (Map)wizardData.get(ConfigUIConstants.EDIT_WIZARD_DATA_KEY);
    	BaseEMSObject ftpOptions=(BaseEMSObject)editDataMap.get(ConfigUIConstants.KEY_FTP_OPTIONS);
    	String ftpPath = null;
    	try {
    		if( ftpOptions.getAttributeValue("B6FwFtpPath") != null)
    			ftpPath = ftpOptions.getAttributeValue("B6FwFtpPath").convertTo(String.class, "EMS");

		} catch (MappingException e) {
			// TODO Auto-generated catch block
			logger.error("load ftp path error");
			e.printStackTrace();
		}
		return ftpPath;
	}

}
