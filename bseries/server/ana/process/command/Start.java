/** 
 * Project Name:socket_netty 
 * File Name:Start.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:18 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.script.ScriptCommandFactory;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:Start <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 18 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class Start extends CommonTask {
	private String preactivateScript;
	private String commandscripts;
	private String doneScript;

	@Override
	public String toString() {

		return "START";
	}

	@Override
	protected void initParam(Element e, Map<String, List<String>> commandMap) {
		setPreactivateScript(e.elementText("preactivateScript"));
		setCommandscripts(e.elementText("commandscripts"));
		setDoneScript(e.elementText("doneScript"));
		setStart(true);
		setEnd(false);
	}

	@Override
	public void getTaskDetail(String prompt, List<String> commandList,
			Map<String, CommonTask> map) {
		commandList.add(prompt + "[START]");
		map.get(getNextId()).getTaskDetail(prompt, commandList, map);
	}

	@Override
	public void preExecuteTask(String prompt, List<String> commandList,
			Map<String, CommonTask> map, B6Template templatePO) {
		map.get(getNextId()).preExecuteTask(prompt, commandList, map,
				templatePO);
	}

	@Override
	public void executeTask(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result, IB6Connet connect) {
		// execute preactive script
		if (!StringUtils.isEmpty(preactivateScript)) {
			boolean flg = ScriptCommandFactory.execute(preactivateScript, po,
					result, connect);
			if (!flg) {
				result.setResult(false);
				map.get(END_KEY).executeTask(map, po, result, connect);
				return;
			}
		}
		//add device prompt
		result.addDeviceName(po.getdevice_host_name());
		result.addPrompt();
		// execute command script
		if (!StringUtils.isEmpty(commandscripts)) {
			boolean flg = ScriptCommandFactory.execute(commandscripts, po,
					result, connect);
			if (!flg) {
				result.setResult(false);
				map.get(END_KEY).executeTask(map, po, result, connect);
				return;
			}
		}
		// execute done script
		if (!StringUtils.isEmpty(doneScript)) {
			boolean flg = ScriptCommandFactory.execute(doneScript, po,
					result, connect);
			if (!flg) {
				result.setResult(false);
				map.get(END_KEY).executeTask(map, po, result, connect);
				return;
			}
		}
		map.get(getNextId()).executeTask(map, po, result, connect);
	}

	public String getPreactivateScript() {
		return preactivateScript;
	}

	public void setPreactivateScript(String preactivateScript) {
		this.preactivateScript = preactivateScript;
	}

	public String getCommandscripts() {
		return commandscripts;
	}

	public void setCommandscripts(String commandscripts) {
		this.commandscripts = commandscripts;
	}

	public String getDoneScript() {
		return doneScript;
	}

	public void setDoneScript(String doneScript) {
		this.doneScript = doneScript;
	}
}
