/** 
 * Project Name:socket_netty 
 * File Name:ScriptCommandFactory.java 
 * Package Name:com.calix.bseries.server.ana.process.command.script 
 * Date:20 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.check.CheckFactory;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:ScriptCommandFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 20 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class ScriptCommandFactory {
	private static final String ECHO_PATTERN_STR="(.*)\\((.*)\\)";
	private static final Pattern ECHO_PATTERN=Pattern.compile(ECHO_PATTERN_STR);

	/**
	 * Function:execute<br/> 
	 * @author Tony Ben 
	 * @param script
	 * @param po
	 * @param result
	 * @return 
	 * @since JDK 1.6
	 */
	public static boolean execute(String script, B6Template po, ANAProcessResult result,IB6Connet connect) {
		boolean flg=executeHead(script,po,result);
		if(flg){
			flg=CheckFactory.executeCheck(script,po,result,connect);
		}
		if(flg){
			flg=executeEcho(script,po,result);
		}
		return flg;
	}
	
	private static boolean executeHead(String script, B6Template po, ANAProcessResult result){
		if(script!=null && script.trim().contains("head(")){
			for(String s:CommonStringUtils.handleScript(script)){
				Matcher m = ECHO_PATTERN.matcher(s);
				while(m.find()){
				    new Head().execute(m.group(2), po, result);
				}
			}
		}
		return true;
	}
	
	private static boolean executeEcho(String script, B6Template po, ANAProcessResult result){
		if(script!=null && script.trim().contains("echo(")){
			for(String s:CommonStringUtils.handleScript(script)){
				Matcher m = ECHO_PATTERN.matcher(s);
				while(m.find()){
				    new Echo().execute(m.group(2), po, result);
				}
			}
		}
		return true;
	}
}
