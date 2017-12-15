/** 
 * Project Name:socket_netty 
 * File Name:NumberCheck.java 
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
 * ClassName:NumberCheck <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     21 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class NumberCheck implements ICheck {

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		if (obj == null || String.valueOf(obj).trim().equals("")) {
			if(script.equals("service")){
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.PARAMETER_SHOULD_BENUMBER
								.toString(), "service_number");
			}else{
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.PARAMETER_SHOULD_BENUMBER
								.toString(), script);
			}
			return false;
		}
		if(!check(obj.toString(),result,script)){
			return false;
		}
		return true;
	}
	
	public static boolean check(String val,ANAProcessResult result,String param){
		try{
			Integer.parseInt(val);
		}catch(Exception e){
			if(param.equals("service")){
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.PARAMETER_SHOULD_BENUMBER
								.toString(), "service_number");
			}else{
				result.setErrorInfo(
						ANAConstants.AnaErrorCode.PARAMETER_SHOULD_BENUMBER
								.toString(), param);
			}
			return false;
		}
		return true;
	}

}
