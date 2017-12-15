package com.calix.bseries.server.ana.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public final class CommonStringUtils {

	/**
	 * replace string {0} to object <br>
	 * Note: The objects must begin replace begin from {0}
	 * 
	 * @param str
	 * @param objs
	 * @return
	 */
	public static String replaceStringCodes(String str, Object... objs) {
		if (isEmpty(str) || objs == null) {
			return str;
		}
		int num = 0;
		for (Object obj : objs) {
			if(obj==null){
				continue;
			}
			str = str.replace("{" + num + "}", obj.toString());
			num++;
		}
		return str;
	}

	/**
	 * check whether the string is null or empty
	 * 
	 * @param str
	 * @return true : the string is null or empty</br> false: the string is not
	 *         null nor empty
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}
	
	/**
	 * check whether the list is empty
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list){
		return list==null || list.isEmpty() ?true:false;
	}
	
	/**
	 * check whether the map is empty
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?,?> map){
		return map==null || map.isEmpty()?true:false;
	}
	
	public static List<String> handleScript(String script) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isEmpty(script)) {
			return list;
		}
		for (String s : script.split("\n")) {
			if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())) {
				continue;
			}
			list.add(s.trim());
		}
		return list;
	}
}
