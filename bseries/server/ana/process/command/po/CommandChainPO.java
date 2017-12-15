package com.calix.bseries.server.ana.process.command.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.calix.bseries.server.ana.common.CommonStringUtils;

public class CommandChainPO {
	private String templateName;
	private String operation;
	private String serviceName;
	private List<Map<String,String>> commandList;

	public CommandChainPO() {

	}
	
	public CommandChainPO(String templateName){
		this.templateName=templateName;
	}

	public CommandChainPO(String templateName, List<Map<String,String>> commandList) {
		this.templateName = templateName;
		this.commandList = commandList;
	}

	public void addCommand(String command,String type){
		if(CommonStringUtils.isEmpty(commandList)){
			commandList=new ArrayList<Map<String,String>>();
		}
		if(type==null || "".equals(type.trim())){
			type="command";
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("command", command);
		map.put("type", type);
		commandList.add(map);
	}
	
	public List<Map<String,String>> getCommandList() {
		return commandList;
	}

	@Override
	public String toString() {
		return templateName+":"+commandList;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
