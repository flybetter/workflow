/** 
 * Project Name:socket_netty 
 * File Name:DSLInterfaceCheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:21 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.check;

import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:DSLInterfaceCheck <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     21 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class DSLInterfaceCheck implements ICheck {
	private static final int MIN_DSL_INTERFACE_NUM=1;
	private static final int MAX_DSL_INTERFACE_NUM=48;
	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		//check number
		boolean flg=new NumberCheck().check(script, po, result);
		if(!flg){
			return false;
		}
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		//check range
		if(!NumberRangeCheck.check(Integer.parseInt(obj.toString()), MIN_DSL_INTERFACE_NUM, MAX_DSL_INTERFACE_NUM, result, script)){
			return false;
		}
		obj=null;
		return true;
	}

}
