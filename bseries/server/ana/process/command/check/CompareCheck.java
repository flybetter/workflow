/** 
 * Project Name:socket_netty 
 * File Name:CompareCheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:9 Nov, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.check;

import org.apache.commons.lang.StringUtils;


import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.compare.CompareFactory;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:CompareCheck <br/> 
 * Date:     9 Nov, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class CompareCheck implements ICheck{
	private static final Logger log=Logger.getLogger(CompareCheck.class);
	private String param;
	public CompareCheck(String param){
		this.param=param;
	}
	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		//param should be ,==,xxx
		if(StringUtils.isBlank(param)){
			return true;
		}
		String[] tmp=param.split(",");
		if(tmp.length!=3){
			result.setErrorInfo(ANAConstants.AnaErrorCode.UNKNOWN_RESPONSE
					.toString());
			return false;
		}
		
		String op=tmp[1];
		String val=tmp[2];
		String opstr=getOpStr(op);
		if(StringUtils.isBlank(opstr)){
			result.setErrorInfo(ANAConstants.AnaErrorCode.UNKNOWN_RESPONSE
					.toString());
			return false;
		}
		Object obj=ClassReflectionUtils.getFieldValue(po, "m_" + script);
		if(obj==null){
			result.setErrorInfo(ANAConstants.AnaErrorCode.PARAM_SHOULD_BE
					.toString(),script,opstr,val);
			return false;
		}
		log.debug("[ANA CompareCheck] left:"+obj+" op:"+op+" right:"+val);
		boolean flg=CompareFactory.compare(String.valueOf(obj),val,op, "string");
		if(!flg){
			result.setErrorInfo(ANAConstants.AnaErrorCode.PARAM_SHOULD_BE
					.toString(),script,opstr,val);
			return false;
		}
		return true;
	}
	
	private String getOpStr(String op){
		if(StringUtils.isBlank(op)){
			return null;
		}
		op=op.trim();
		if("==".equals(op) || "=".equals(op)){
			return "be";
		}
		if(">".equals(op) || ">=".equals(op)){
			return "more than";
		}
		if("<".equals(op)||"<=".equals(op)){
			return "less than";
		}
		return null;
	}

}
