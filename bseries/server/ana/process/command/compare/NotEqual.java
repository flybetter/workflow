/** 
 * Project Name:socket_netty 
 * File Name:NotEqual.java 
 * Package Name:com.calix.bseries.server.ana.process.command.compare 
 * Date:20 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.compare;
/** 
 * ClassName:NotEqual <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     20 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class NotEqual implements ICompare {

	@Override
	public boolean compare(Object left, Object right) {
		if(left==null || right==null){
			return false;
		}
		return String.valueOf(left).equals(String.valueOf(right))?false:true;
	}

}
