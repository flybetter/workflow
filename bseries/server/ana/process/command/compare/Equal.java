/** 
 * Project Name:socket_netty 
 * File Name:Equal.java 
 * Package Name:com.calix.bseries.server.ana.process.command.compare 
 * Date:20 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.compare;

/**
 * ClassName:Equal <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 20 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class Equal implements ICompare {

	@Override
	public boolean compare(Object left, Object right) {
		//default value is null
		if (left == null || right == null) {
			return true;
		}
		//check boolean
		if(left.toString().equalsIgnoreCase("true")){
			left="1";
		}else if(left.toString().equalsIgnoreCase("false")){
			left="0";
		}
		if(right.toString().equalsIgnoreCase("true")){
			right="1";
		}else if(right.toString().equalsIgnoreCase("false")){
			right="0";
		}
		if (left instanceof Integer) {
			return left == right;
		} else if (left instanceof Float) {
			return left == right;
		} else if (left instanceof Double) {
			return left == right;
		} else if (left instanceof Long) {
			return left == right;
		} else {
			return String.valueOf(left).equals(String.valueOf(right));
		}
	}

}
