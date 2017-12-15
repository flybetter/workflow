/** 
 * Project Name:socket_netty 
 * File Name:ServiceNumberCheck.java 
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
 * ClassName:ServiceNumberCheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 21 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class ServiceNumberCheck implements ICheck {
	private static final int MIN_SERVICE_NUM=1;
	private static final int MAX_SERVICE_NUM=4;

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		// check number
		boolean flg = new NumberCheck().check(script, po, result);
		if (!flg) {
			return false;
		}
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		// check range
		int service_num=Integer.parseInt(obj.toString());
		if (service_num < MIN_SERVICE_NUM || service_num > MAX_SERVICE_NUM) {
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.NUMBER_SHOULD_INRANGE.toString(),script);
			return false;
		}
		obj=null;
		return true;
	}

}
