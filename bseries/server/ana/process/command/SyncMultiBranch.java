/** 
 * Project Name:socket_netty 
 * File Name:SyncMultiBranch.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:18 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:SyncMultiBranch <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 18 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class SyncMultiBranch extends CommonTask {
	private String paramName;
	private Map<String, String> conditionMap = new HashMap<String, String>();
	private String defaultId;

	@Override
	protected void initParam(Element e, Map<String, List<String>> commandMap) {
		setParamName(e.elementText("paramName"));
		setDefaultId(e.elementText("defaultId"));
	}

	@Override
	public void getTaskDetail(String prompt, List<String> commandList,
			Map<String, CommonTask> map) {
		// TODO Auto-generated method stub

	}

	/**
	 * paramName.
	 * 
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * paramName
	 * 
	 * @param paramName
	 *            the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * conditionMap.
	 * 
	 * @return the conditionMap
	 */
	public Map<String, String> getConditionMap() {
		return conditionMap;
	}

	/**
	 * conditionMap
	 * 
	 * @param conditionMap
	 *            the conditionMap to set
	 */
	public void setConditionMap(Map<String, String> conditionMap) {
		this.conditionMap = conditionMap;
	}

	/**
	 * defaultId.
	 * 
	 * @return the defaultId
	 */
	public String getDefaultId() {
		return defaultId;
	}

	/**
	 * defaultId
	 * 
	 * @param defaultId
	 *            the defaultId to set
	 */
	public void setDefaultId(String defaultId) {
		this.defaultId = defaultId;
	}

	@Override
	public void preExecuteTask(String prompt, List<String> commandList,
			Map<String, CommonTask> map, B6Template templatePO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeTask(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result, IB6Connet connect) {
		// TODO Auto-generated method stub
		
	}
}
