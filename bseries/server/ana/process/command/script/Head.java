/** 
 * Project Name:socket_netty 
 * File Name:Head.java 
 * Package Name:com.calix.bseries.server.ana.process.command.script 
 * Date:11 Nov, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.script;

import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.Start;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:Head <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     11 Nov, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class Head implements ISciptCommand{

	@Override
	public boolean execute(String script, B6Template po, ANAProcessResult result) {
		String command=new Start().replaceParameter(script, po);
		result.addLogHead(command);
		return true;
	}

}
