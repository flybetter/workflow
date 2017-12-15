/** 
 * Project Name:socket_netty 
 * File Name:ICheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:21 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.check;

import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:ICheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 21 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public interface ICheck {
	public boolean check(String script, B6Template po, ANAProcessResult result);
}
