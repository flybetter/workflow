/** 
 * Project Name:socket_netty 
 * File Name:NotNullCheck.java 
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
 * ClassName:NotNullCheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 21 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class NotNullCheck implements ICheck {

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		if (obj == null || String.valueOf(obj).trim().equals("")) {
			if("access_profile_name_int".equals(script)){
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.INVALID_ACCESS_PROFILE
								.toString(), script);
				return false;
			}
			if("dsl_profile_name".equals(script)){
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.INVALID_DSL_PROFILE
								.toString(), script);
				return false;
			}
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.PARAMETER_VALUE_INVALID
							.toString(), script);
			return false;
		}
		return true;
	}

}
