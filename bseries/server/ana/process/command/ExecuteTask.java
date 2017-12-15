/** 
 * Project Name:socket_netty 
 * File Name:ExecuteTask.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:18 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.CommonStringUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANACommandProcess;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.ana.process.command.script.ScriptCommandFactory;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:ExecuteTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 18 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class ExecuteTask extends CommonTask {
	private static final Logger log = Logger.getLogger(ExecuteTask.class);
	/**
	 * pre-activate script
	 */
	private String preactivateScript;
	/**
	 * done script
	 */
	private String doneScript;
	/**
	 * Exception Script
	 */
	private String exceptionScript;
	/**
	 * Rollback Enable
	 */
	private boolean rollbackEnable;
	/**
	 * Rollback TaskName
	 */
	private String rollbackTaskName;
	private String command;
	private List<String> commandList;
	private String commandscripts;

	@Override
	protected void initParam(Element e, Map<String, List<String>> commandMap) {
		setPreactivateScript(e.elementText("preactivateScript"));
		setDoneScript(e.elementText("doneScript"));
		setExceptionScript(e.elementText("exceptionScript"));
		if (!StringUtils.isEmpty(e.elementText("rollbackEnable"))
				&& (e.elementText("rollbackEnable").equalsIgnoreCase("true") || e
						.elementText("rollbackEnable")
						.equalsIgnoreCase("false"))) {
			setRollbackEnable(Boolean.parseBoolean(e
					.elementText("rollbackEnable")));
			setRollbackTaskName(e.elementText("rollbackTaskName"));
		}
		setCommand(e.elementText("command"));
		if (!StringUtils.isEmpty(command)) {
			commandList = commandMap.get(command);
		}
		setCommandscripts(e.elementText("commandscripts"));
		// set command scripts
		if (!StringUtils.isEmpty(commandscripts)) {
			if (commandList == null) {
				commandList = new ArrayList<String>();
			}
			for (String s : CommonStringUtils.handleScript(commandscripts)) {
				commandList.add(s);
			}
		}
	}

	@Override
	public void getTaskDetail(String prompt, List<String> list,
			Map<String, CommonTask> map) {
		list.add(prompt + "[ExecuteTask]:");
		String prompt1 = prompt + DEFAULT_PROMPT;
		if (!StringUtils.isEmpty(preactivateScript)) {
			list.add(prompt1 + "preactivateScript:");
			list.add(prompt1 + DEFAULT_PROMPT
					+ CommonStringUtils.handleScript(preactivateScript));
		}
		if (commandList != null && commandList.size() > 0) {
			list.add(prompt1 + "command:");
			for (String s : commandList) {
				list.add(prompt1 + DEFAULT_PROMPT + s);
			}
		}
		if (!StringUtils.isEmpty(doneScript)) {
			list.add(prompt1 + "doneScript:");
			for (String s : CommonStringUtils.handleScript(doneScript)) {
				list.add(prompt1 + DEFAULT_PROMPT + s);
			}
		}

		if (rollbackEnable && rollbackTaskName != null) {
			list.add(prompt1 + "RollBach when Exception:");
			map.get(rollbackTaskName.toLowerCase()).getTaskDetail(
					prompt1 + DEFAULT_PROMPT, list, map);
			list.add(prompt1 + "Exception:");
			for (String s : CommonStringUtils.handleScript(exceptionScript)) {
				list.add(prompt1 + DEFAULT_PROMPT + s);
			}
		}
		map.get(getNextId()).getTaskDetail(prompt, list, map);
	}

	@Override
	public void preExecuteTask(String prompt, List<String> list,
			Map<String, CommonTask> map, B6Template templatePO) {
		list.add(prompt + "[ExecuteTask]:");
		String prompt1 = prompt + DEFAULT_PROMPT;
		if (!StringUtils.isEmpty(preactivateScript)) {
			list.add(prompt1 + "preactivateScript:");
			list.add(prompt1 + DEFAULT_PROMPT
					+ CommonStringUtils.handleScript(preactivateScript));
		}
		if (commandList != null && commandList.size() > 0) {
			list.add(prompt1 + "command:");
			for (String s : commandList) {
				String cmd = replaceParameter(s, templatePO);
				if (!cmdParamExistFlg) {
					list.add(prompt1 + DEFAULT_PROMPT + "[ERROR]" + cmd);
					continue;
				}

			}
		}
		if (!StringUtils.isEmpty(doneScript)) {
			list.add(prompt1 + "doneScript:");
			for (String s : CommonStringUtils.handleScript(doneScript)) {
				list.add(prompt1 + DEFAULT_PROMPT
						+ replaceParameter(s, templatePO));
			}
		}
		if (rollbackEnable && rollbackTaskName != null) {
			list.add(prompt1 + "RollBach when Exception:");
			map.get(rollbackTaskName.toLowerCase()).preExecuteTask(
					prompt1 + DEFAULT_PROMPT, list, map, templatePO);
			list.add(prompt1 + "Exception:");
			for (String s : CommonStringUtils.handleScript(exceptionScript)) {
				list.add(prompt1 + DEFAULT_PROMPT
						+ replaceParameter(s, templatePO));
			}
		}
		map.get(getNextId()).preExecuteTask(prompt, list, map, templatePO);

	}

	@Override
	public void executeTask(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result, IB6Connet connect) {
		boolean flg = true;
		// do Pre-active Script
		if (!StringUtils.isEmpty(preactivateScript)) {
			flg = ScriptCommandFactory.execute(preactivateScript, po, result,
					connect);
			if (!flg) {
				result.setResult(false);
				map.get(END_KEY).executeTask(map, po, result, connect);
				return;
			}
		}
		// do command
		if (commandList != null && commandList.size() > 0) {
			for (String s : commandList) {
				String cmd = replaceParameter(s, po);
				if (!cmdParamExistFlg) {
					log.error("[Execute Command]" + cmd
							+ " ignorge, the parameter is not set.");
					continue;
				}
				// log.debug("[Execute Command]"+cmd);
				flg = connect.executeCommand(cmd);
				log.info("[ANA Execute Command][" + po.getdevice_host_name()
						+ "][" + cmd + "][result:" + flg + "]");
				if (!flg) {
					result.setResult(false);
					rollback(map, po, result, connect);
					return;
				}
			}
		}
		po.setResponseDetail(result.getLogs());
		log.info("[ANA Execute doUpdatePOToDB Command][" + result.getLogs()+ "]");
		//ANACommandProcess.getInstance().doUpdatePOToDB(po);
		
		// done script
		if (!StringUtils.isEmpty(doneScript)) {
			flg = ScriptCommandFactory.execute(doneScript, po, result, connect);
			if (!flg) {
				result.setResult(false);
				rollback(map, po, result, connect);
				return;
			}
		}
		// do next step
		map.get(getNextId()).executeTask(map, po, result, connect);
	}

	/**
	 * Function:rollback<br/>
	 * 
	 * @author Tony Ben
	 * @param map
	 * @param po
	 * @param result
	 * @param connect
	 * @since JDK 1.6
	 */
	private void rollback(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result, IB6Connet connect) {
		if (rollbackEnable && rollbackTaskName != null) {
			boolean flg = ScriptCommandFactory.execute(preactivateScript, po,
					result, connect);
			if (!flg) {
				result.setErrorInfo(ANAConstants.AnaErrorCode.ROLLBACK_FAILED
						.toString());
				map.get(END_KEY).executeTask(map, po, result, connect);
				return;
			}
			map.get(rollbackTaskName.toLowerCase()).executeTask(map, po,
					result, connect);
			result.setErrorInfo(ANAConstants.AnaErrorCode.ROLLBACK_SUCCESS
					.toString());
			map.get(END_KEY).executeTask(map, po, result, connect);
		} else {
			map.get(END_KEY).executeTask(map, po, result, connect);
		}
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
	 * exceptionScript.
	 * 
	 * @return the exceptionScript
	 */
	public String getExceptionScript() {
		return exceptionScript;
	}

	/**
	 * exceptionScript
	 * 
	 * @param exceptionScript
	 *            the exceptionScript to set
	 */
	public void setExceptionScript(String exceptionScript) {
		this.exceptionScript = exceptionScript;
	}

	/**
	 * rollbackEnable.
	 * 
	 * @return the rollbackEnable
	 */
	public boolean isRollbackEnable() {
		return rollbackEnable;
	}

	/**
	 * rollbackEnable
	 * 
	 * @param rollbackEnable
	 *            the rollbackEnable to set
	 */
	public void setRollbackEnable(boolean rollbackEnable) {
		this.rollbackEnable = rollbackEnable;
	}

	/**
	 * rollbackTaskName.
	 * 
	 * @return the rollbackTaskName
	 */
	public String getRollbackTaskName() {
		return rollbackTaskName;
	}

	/**
	 * rollbackTaskName
	 * 
	 * @param rollbackTaskName
	 *            the rollbackTaskName to set
	 */
	public void setRollbackTaskName(String rollbackTaskName) {
		this.rollbackTaskName = rollbackTaskName;
	}

	/**
	 * command.
	 * 
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command
	 * 
	 * @param command
	 *            the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * commandList.
	 * 
	 * @return the commandList
	 */
	public List<String> getCommandList() {
		return commandList;
	}

	/**
	 * commandList
	 * 
	 * @param commandList
	 *            the commandList to set
	 */
	public void setCommandList(List<String> commandList) {
		this.commandList = commandList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "[ExecuteTask] " + "\r    preactivateScript:"
				+ preactivateScript + "\r    doneScript:" + doneScript
				+ "\r    exceptionScript:" + exceptionScript
				+ "\r    rollbackEnable:" + rollbackEnable + "\r    command:"
				+ command + "\r        " + commandList
				+ "\r    rollbackTaskName" + rollbackTaskName;
		return str;
	}

	public String getCommandscripts() {
		return commandscripts;
	}

	public void setCommandscripts(String commandscripts) {
		this.commandscripts = commandscripts;
	}
}
