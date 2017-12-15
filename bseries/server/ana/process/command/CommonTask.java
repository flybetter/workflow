/** 
 * Project Name:socket_netty 
 * File Name:CommonTask.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:18 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.check.INFOCheck;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:CommonTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 18 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public abstract class CommonTask {
	public static final String DEFAULT_PROMPT = "	";
	public static final String START_KEY = "start";
	public static final String END_KEY = "end";
	private static final String PATTERN_PARAM_STR = "(\\{[^\\}]+})";
	private static final Pattern PATTERN_PARAM = Pattern
			.compile(PATTERN_PARAM_STR);
	private String name;
	private String type;
	private String id;
	private String state;
	private String nextId;
	private boolean isStart;
	private boolean isEnd;
	protected boolean cmdParamExistFlg=true;

	// **************************************//
	// Common Tools [Begin]
	// **************************************//
	public static void checkParamExist(List<String> list, String param,
			B6Template po) {
		Matcher m = PATTERN_PARAM.matcher(param);
		String str;
		while (m.find()) {
			str = m.group(1).replace("{", "").replace("}", "");
			Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + str);
			if (obj == null || String.valueOf(obj).trim().equals("")) {
				if (!list.contains(str)) {
					list.add(str);
				}
			}
		}
		m = null;
	}

	public String replaceParameter(String param, B6Template po) {
		Matcher m = PATTERN_PARAM.matcher(param);
		String str;
		cmdParamExistFlg=true;
		while (m.find()) {
			Object obj=null;
			str = m.group(1).replace("{", "").replace("}", "");
			if(str!=null && str.toLowerCase().equals("info_key")){
				new INFOCheck().check("key_info", po, new ANAProcessResult());
				obj=po.getParam("info_key");
				if(obj!=null){
					param = param.replace("${" + str + "}", obj.toString());
					param = param.replace("%{" + str + "}", obj.toString());
					param = param.replace("{" + str + "}", obj.toString());
					continue;
				}else{
					return "no info CIRCUITID";
				}
			}
			if(str!=null && str.toLowerCase().equals("info_key_old")){
				new INFOCheck().check("key_info_old", po, new ANAProcessResult());
				obj=po.getParam("info_key_old");
				if(obj!=null){
					param = param.replace("${" + str + "}", obj.toString());
					param = param.replace("%{" + str + "}", obj.toString());
					param = param.replace("{" + str + "}", obj.toString());
					continue;
				}
			}
			obj=po.getParam(str);
			if(obj!=null){
				param = param.replace("${" + str + "}", obj.toString());
				param = param.replace("%{" + str + "}", obj.toString());
				param = param.replace("{" + str + "}", obj.toString());
				continue;
			}
			obj = ClassReflectionUtils.getFieldValue(po, "m_" + str);
			if (obj != null && !String.valueOf(obj).trim().equals("")) {
				param = param.replace("${" + str + "}", obj.toString());
				param = param.replace("%{" + str + "}", obj.toString());
				param = param.replace("{" + str + "}", obj.toString());
			}else{
				if(str.contains("tpstc_mode")){
					Object match_vlan = ClassReflectionUtils.getFieldValue(po, "m_match_vlan");
					if(match_vlan != null && !String.valueOf(match_vlan).trim().equals("")){
							param="tpstc mode ptm";
					}else{
						cmdParamExistFlg=false;
					}
				}else{
					cmdParamExistFlg=false;
				}
			}
			// just for B6216 CMS-15595
			if(str.contains("tpstc_mode")){
//				Object match_vlan = ClassReflectionUtils.getFieldValue(po, "m_match_vlan");
//				if(match_vlan != null && !String.valueOf(match_vlan).trim().equals("")){		
//						param="tpstc mode ptm";
//				}
				if(param.contains("0")){
					param="tpstc mode auto";
				}else if(param.contains("1")){
					param="tpstc mode ptm";
				}else if(param.contains("2")){
					param="tpstc mode atm";
				}
			}
		}
		m = null;
		return param;
	}

	protected boolean executeScript(String script, B6Template po,
			ANAProcessResult result) {

		return false;
	}

	// **************************************//
	// Common Tools [End]
	// **************************************//
	/**
	 * Function:init<br/>
	 * Remark:Initial parameters<br/>
	 * 
	 * @author Tony Ben
	 * @param e
	 * @since JDK 1.6
	 */
	public void init(Element e, Map<String, List<String>> commandMap) {
		// initial parameters
		setStart(false);
		setEnd(false);
		setName(e.elementText("name"));
		setType(e.elementText("type"));
		setId(e.elementText("id"));
		setState(e.elementText("state"));
		setNextId(e.elementText("nextId"));
		initParam(e, commandMap);
	}

	/**
	 * Function:initParam<br/>
	 * Remark:Initial Parameters for subclasses<br/>
	 * 
	 * @author Tony Ben
	 * @param e
	 * @since JDK 1.6
	 */
	protected abstract void initParam(Element e,
			Map<String, List<String>> commandMap);

	/**
	 * Function:getTaskDetail<br/>
	 * Remark:get all the commands and scripts of tempalte<br/>
	 * 
	 * @author Tony Ben
	 * @param prompt
	 * @param commandList
	 * @param map
	 * @since JDK 1.6
	 */
	public abstract void getTaskDetail(String prompt, List<String> commandList,
			Map<String, CommonTask> map);

	/**
	 * Function:preExecuteTask<br/>
	 * 
	 * @author Tony Ben
	 * @param prompt
	 * @param commandList
	 * @param map
	 * @param templatePO
	 * @since JDK 1.6
	 */
	public abstract void preExecuteTask(String prompt,
			List<String> commandList, Map<String, CommonTask> map,
			B6Template templatePO);

	public abstract void executeTask(Map<String, CommonTask> map,
			B6Template po, ANAProcessResult result, IB6Connet connect);

	/**
	 * name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * state.
	 * 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * state
	 * 
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * nextId.
	 * 
	 * @return the nextId
	 */
	public String getNextId() {
		return nextId.toLowerCase();
	}

	/**
	 * nextId
	 * 
	 * @param nextId
	 *            the nextId to set
	 */
	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	/**
	 * type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * type
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * isStart.
	 * 
	 * @return the isStart
	 */
	public boolean isStart() {
		return isStart;
	}

	/**
	 * isStart
	 * 
	 * @param isStart
	 *            the isStart to set
	 */
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	/**
	 * isEnd.
	 * 
	 * @return the isEnd
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * isEnd
	 * 
	 * @param isEnd
	 *            the isEnd to set
	 */
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	// /* (non-Javadoc)
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// String str="\r id:"+id
	// +"\r name:"+name
	// +"\r type:"+type
	// +"\r nextId:"+nextId
	// +"\r isStart:"+isStart
	// +"\r isEnd:"+isEnd+"\r";
	// return str;
	// }
}
