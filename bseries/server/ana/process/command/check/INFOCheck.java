/** 
 * Project Name:socket_netty 
 * File Name:INFOCheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:21 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.check;

import org.apache.commons.lang.StringUtils;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:INFOCheck <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     21 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class INFOCheck implements ICheck {
	
	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		if (obj == null || String.valueOf(obj).trim().equals("")) {
			if(script.equals("key_info_old")){
				result.setErrorInfo(ANAConstants.AnaErrorCode.KEYINFO_OLD_NOT_EXIST);
				return false;
			}
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_KEYINFO_FORMAT);
			return false;
		}
		
		if(StringUtils.isEmpty(obj.toString()) || obj.toString().split(" ").length<2){
			if(script.equals("key_info_old")){
				result.setErrorInfo(ANAConstants.AnaErrorCode.KEYINFO_EVALUATION_FAILED);
				return false;
			}
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_KEYINFO_FORMAT);
			return false;
		}
		if(script.equals("key_info_old")){
			po.addParam("info_key_old",obj.toString().split(" ")[0].trim());	
			po.addParam("len_key_info_old", String.valueOf(String.valueOf(obj).length()));
		}
		if(script.equals("key_info")){
			po.addParam("info_key",obj.toString().split(" ")[0].trim());	
			po.addParam("len_key_info", String.valueOf(String.valueOf(obj).length()));
		}
		return true;
	}

}
