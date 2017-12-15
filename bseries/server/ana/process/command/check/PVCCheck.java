/** 
 * Project Name:socket_netty 
 * File Name:PVCCheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:21 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.check;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:PVCCheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 21 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class PVCCheck implements ICheck {
	private static final int VPI_MIN=0;
	private static final int VPI_MAX=15;
	private static final int VCI_MIN=32;
	private static final int VCI_MAX=511;
	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		if (obj == null || String.valueOf(obj).trim().equals("")) {
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.PVC_SERVICE_INT_MANDATORY
							.toString(), script);
			return false;
		}
		String pvc = obj.toString();
		if (pvc.split("/").length != 2) {
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.INVALID_VPI_VCI_FORMAT.toString(),
					script);
			return false;
		}
		String vpi=pvc.split("/")[0];
		String vci=pvc.split("/")[1];
		//VPI check
		if(!NumberCheck.check(vpi, result, "VPI")){
			return false;
		}
		if(!NumberRangeCheck.check(Integer.parseInt(vpi), VPI_MIN, VPI_MAX, result, "VPI")){
			return false;
		}
		//VCI check
		if(!NumberCheck.check(vci, result, "VCI")){
			return false;
		}
		if(!NumberRangeCheck.check(Integer.parseInt(vci), VCI_MIN, VCI_MAX, result, "VCI")){
			return false;
		}
		return true;
	}

	

}
