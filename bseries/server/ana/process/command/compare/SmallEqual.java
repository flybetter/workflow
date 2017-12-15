/** 
 * Project Name:socket_netty 
 * File Name:SmallEqual.java 
 * Package Name:com.calix.bseries.server.ana.process.command.compare 
 * Date:20 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command.compare;
/** 
 * ClassName:SmallEqual <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     20 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class SmallEqual implements ICompare {

	@Override
	public boolean compare(Object left, Object right) {
		if (left == null || right == null) {
			return false;
		}
		if (left instanceof Integer) {
			return (Integer)left <= (Integer)right;
		} else if (left instanceof Float) {
			return (Float)left <= (Float)right;
		} else if (left instanceof Double) {
			return (Double)left <= (Double)right;
		} else if (left instanceof Long) {
			return (Long)left <= (Long)right;
		} else {
			return false;
		}
	}

}
