/** 
 * Project Name:socket_netty 
 * File Name:CheckFactory.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:9 Nov, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.script.Echo;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:CheckFactory <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     9 Nov, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class CheckFactory {
	private static final String CHECK_PATTERN_STR="check_(.*)\\(\\$\\{(.*)\\}(.*)\\)";
	private static final Pattern CHECK_PATTERN=Pattern.compile(CHECK_PATTERN_STR);
	
	public static boolean executeCheck(String script, B6Template po, ANAProcessResult result,IB6Connet connect){
		if(script!=null && script.trim().contains("check_")){
			for(String s:CommonStringUtils.handleScript(script)){
				Matcher m = CHECK_PATTERN.matcher(s);
				if(m.find()){
					ICheck check=getCheckInstance(m.group(1).toLowerCase().trim(),connect,m.group(3));
					if(check!=null && !check.check(m.group(2).trim(), po, result)){
						return false;
					}
				}
				while(m.find()){
				    new Echo().execute(m.group(1), po, result);
				}
			}
		}
		return true;
	}
	
	private static ICheck getCheckInstance(String param,IB6Connet connect,String ... others){
		if(param.equals("info")){
			return new INFOCheck();
		}else if(param.equals("notnull")){
			return new NotNullCheck();
		}else if(param.equals("number")){
			return new NumberCheck();
		}else if(param.equals("pvc")){
			return new PVCCheck();
		}else if(param.equals("dsl_interface")){
			return new DSLInterfaceCheck();
		}else if(param.equals("service_number")){
			return new ServiceNumberCheck();
		}else if(param.equals("dsl_interface_inuse")){
			return new DSLInterfaceInUseCheck(connect);
		}else if(param.equals("boundgroup")){
			return new BoundGroupCheck();
		}else if(param.equals("boundgroup_exist")){
			return new BoundGroupExistCheck(connect);
		}else if(param.equals("compare") && others.length>0){
			return new CompareCheck(others[0]);
		}else if(param.equals("service_not_exist")){
			return new ServiceNotExistCheck(connect);
		}else if(param.equals("circuid")){
			return new CircuidCheck(connect);
		}else if(param.equals("bonded_service_not_exist")){
			return new BondedServiceNotExistCheck(connect);
		}else if(param.equals("dsl_profile_not_exist")){
			return new DSLProfileNotExistCheck(connect);
		}
		return null;
	}
}
