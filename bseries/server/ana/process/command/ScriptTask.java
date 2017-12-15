/** 
 * Project Name:socket_netty 
 * File Name:ScriptTask.java 
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

import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.script.ScriptCommandFactory;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:ScriptTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 18 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class ScriptTask extends CommonTask {
	/**
	 * pre-activate script
	 */
	private String preactivateScript;
	/**
	 * done script
	 */
	private String doneScript;
	/**
	 * running script
	 */
	private String script;

	@Override
	protected void initParam(Element e, Map<String, List<String>> commandMap) {
		setPreactivateScript(e.elementText("preactivateScript"));
		setDoneScript(e.elementText("doneScript"));
		setScript(e.elementText("script"));
	}

	@Override
	public void getTaskDetail(String prompt, List<String> commandList,
			Map<String, CommonTask> map) {
		commandList.add(prompt + "[ScriptTask]:");
		String prompt1 = prompt + DEFAULT_PROMPT;
		if (!StringUtils.isEmpty(preactivateScript)) {
			commandList.add(prompt1 + "preactivateScript:");
			for (String s : CommonStringUtils.handleScript(preactivateScript)) {
				commandList.add(prompt1 + DEFAULT_PROMPT + s);
			}

		}
		if (!StringUtils.isEmpty(script)) {
			commandList.add(prompt1 + "script:");
			for (String s : CommonStringUtils.handleScript(script)) {
				commandList.add(prompt1 + DEFAULT_PROMPT + s);
			}
		}
		if (!StringUtils.isEmpty(doneScript)) {
			commandList.add(prompt1 + "doneScript:");
			for (String s : CommonStringUtils.handleScript(doneScript)) {
				commandList.add(prompt1 + DEFAULT_PROMPT + s);
			}
		}
		map.get(getNextId().toLowerCase()).getTaskDetail(prompt, commandList,
				map);
	}

	@Override
	public void preExecuteTask(String prompt, List<String> commandList,
			Map<String, CommonTask> map, B6Template templatePO) {
		commandList.add(prompt + "[ScriptTask]:");
		String prompt1 = prompt + DEFAULT_PROMPT;
		if (!StringUtils.isEmpty(preactivateScript)) {
			commandList.add(prompt1 + "preactivateScript:");
			for (String s : CommonStringUtils.handleScript(preactivateScript)) {
				commandList.add(prompt1 + DEFAULT_PROMPT
						+ replaceParameter(s, templatePO));
			}

		}
		if (!StringUtils.isEmpty(script)) {
			commandList.add(prompt1 + "script:");
			for (String s : CommonStringUtils.handleScript(script)) {
				commandList.add(prompt1 + DEFAULT_PROMPT
						+ replaceParameter(s, templatePO));
			}
		}
		if (!StringUtils.isEmpty(doneScript)) {
			commandList.add(prompt1 + "doneScript:");
			for (String s : CommonStringUtils.handleScript(doneScript)) {
				commandList.add(prompt1 + DEFAULT_PROMPT
						+ replaceParameter(s, templatePO));
			}
		}
		map.get(getNextId().toLowerCase()).preExecuteTask(prompt, commandList,
				map, templatePO);

	}

	@Override
	public void executeTask(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result, IB6Connet connect) {
		boolean flg = true;
		// pre script
		if (!StringUtils.isEmpty(preactivateScript)) {
			flg = ScriptCommandFactory.execute(preactivateScript, po, result,
					connect);
			if (!flg) {
				result.setResult(false);
			}
		}
		// script
		if (!StringUtils.isEmpty(script)) {
			flg = ScriptCommandFactory.execute(script, po, result, connect);
			if (!flg) {
				result.setResult(false);
			}
		}
		// done script
		if (!StringUtils.isEmpty(doneScript)) {
			flg = ScriptCommandFactory.execute(doneScript, po, result, connect);
			if (!flg) {
				result.setResult(false);
			}
		}
		map.get(getNextId().toLowerCase())
				.executeTask(map, po, result, connect);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ScriptTask";
	}

	/**
	 * preactivateScript.
	 * 
	 * @return the preactivateScript
	 */
	public String getPreactivateScript() {
		return preactivateScript;
	}

	/**
	 * preactivateScript
	 * 
	 * @param preactivateScript
	 *            the preactivateScript to set
	 */
	public void setPreactivateScript(String preactivateScript) {
		this.preactivateScript = preactivateScript;
	}

	/**
	 * doneScript.
	 * 
	 * @return the doneScript
	 */
	public String getDoneScript() {
		return doneScript;
	}

	/**
	 * doneScript
	 * 
	 * @param doneScript
	 *            the doneScript to set
	 */
	public void setDoneScript(String doneScript) {
		this.doneScript = doneScript;
	}

	/**
	 * script.
	 * 
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * script
	 * 
	 * @param script
	 *            the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}
}
