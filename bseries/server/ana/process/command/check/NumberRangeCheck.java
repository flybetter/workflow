/** 
 * Project Name:socket_netty 
 * File Name:NumberRangeCheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:21 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.check;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:NumberRangeCheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 21 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class NumberRangeCheck implements ICheck {

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {

		return true;
	}

	public static boolean check(int num, int min, int max,
			ANAProcessResult result, String param) {
		if (num < min || num > max) {
			//match ASAP error message
			if("dsl_interface_number".equals(param)){
				result.setErrorInfo(ANAConstants.AnaErrorCode.PORT_NOT_EXISTING.toString());
			}else if("dsl_interface_number_2".equals(param)){
				result.setErrorInfo(ANAConstants.AnaErrorCode.SECOND_PORT_NOT_EXISTING.toString());
			}else{
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.NUMBER_SHOULD_INRANGE.toString(),
						param);
			}
			return false;
		}
		return true;
	}

}
