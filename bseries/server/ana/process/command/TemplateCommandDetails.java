/** 
 * Project Name:socket_netty 
 * File Name:TemplateCommandDetails.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:25 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:TemplateCommandDetails <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 25 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class TemplateCommandDetails {
	private String templateName;
	private String className;
	private String operation;
	private List<String> modules;
	private Map<String, CommonTask> taskMap;

	public TemplateCommandDetails(String templateName, String className) {
		taskMap = new HashMap<String, CommonTask>();
		this.templateName = templateName;
		this.className = className;

	}
	
	public boolean checkModule(String module){
		if(modules==null || modules.isEmpty()){
			return true;
		}
		if(modules.contains(module)){
			return true;
		}
		return false;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	public Map<String, CommonTask> getTaskMap() {
		return taskMap;
	}

	public void setTaskMap(Map<String, CommonTask> taskMap) {
		this.taskMap = taskMap;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
