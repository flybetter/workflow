/** 
 * Project Name:socket_netty 
 * File Name:CompareFactory.java 
 * Package Name:com.calix.bseries.server.ana.process.command.compare 
 * Date:20 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.compare;

import org.apache.log4j.Logger;


/**
 * ClassName:CompareFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 20 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class CompareFactory {
	private static final Logger log=Logger.getLogger(CompareFactory.class);
	public static boolean compare(String left, String right, String operation,
			String objType) {
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
		
		// Equals
		if (operation.equals("==")) {
			return new Equal().compare(transObject(left, objType),
					transObject(right, objType));
		}
		// Large
		if (operation.equals(">")) {
			return new Large().compare(transObject(left, objType),
					transObject(right, objType));
		}
		// Small
		if (operation.equals("<")) {
			return new Small().compare(transObject(left, objType),
					transObject(right, objType));
		}
		// LargeEqual
		if (operation.equals(">=")) {
			return new LargeEqual().compare(transObject(left, objType),
					transObject(right, objType));
		}
		// SmallEqual
		if (operation.equals("<=")) {
			return new SmallEqual().compare(transObject(left, objType),
					transObject(right, objType));
		}
		// NotEqual
		if (operation.equals("<>") || operation.equals("!=")) {
			return new NotEqual().compare(transObject(left, objType),
					transObject(right, objType));
		}
		return false;
	}

	private static Object transObject(String obj, String objType) {
		// check null
		if (obj == null || objType == null) {
			return null;
		}
		try {
			// Integer
			if (objType.equalsIgnoreCase("integer")
					|| objType.equalsIgnoreCase("int")) {
				return Integer.parseInt(obj.toString());
			}
			// Float
			if (objType.equalsIgnoreCase("float")) {
				return Float.parseFloat(obj);
			}
			// Double
			if (objType.equalsIgnoreCase("double")) {
				return Double.parseDouble(obj);
			}
			// Long
			if (objType.equalsIgnoreCase("long")) {
				return Long.parseLong(obj);
			}
		} catch (Exception e) {
			log.error("Parameter["+obj+"] is not define",e);
			return null;
		}
		return String.valueOf(obj);
	}
}
