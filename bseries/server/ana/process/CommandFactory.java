/** 
 * Project Name:socket_netty 
 * File Name:CommandFactory.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:18 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.command.CommonTask;
import com.calix.bseries.server.ana.process.command.TemplateCommandDetails;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:CommandFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 18 Oct, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class CommandFactory {
	// Log
	private static final Logger log = Logger.getLogger(CommandFactory.class);
	// Map to restore the command list
	private static Map<String, TemplateCommandDetails> tempMap = new HashMap<String, TemplateCommandDetails>();
	private static Map<String,String> templateClassMap=new HashMap<String,String>();
	// sync lock for initial template
	private static final String lock = "";

	/**
	 * Function:getCommandTemplate<br/>
	 * 
	 * @author Tony Ben
	 * @param tempName
	 * @param operation
	 * @return
	 * @since JDK 1.6
	 */
	public static TemplateCommandDetails getCommandList(String tempName,
			String operation) {
		TemplateCommandDetails template = tempMap.get(tempName.toLowerCase()
				+ "-" + operation.toLowerCase());
		if (template == null) {
			synchronized (lock) {
				initTemplate();
			}
			template = tempMap.get(tempName.toLowerCase() + "-"
					+ operation.toLowerCase());
		}
		return template;
	}

	public static String getClassNameViaTemplateName(String tempName){
		String className=templateClassMap.get(tempName.toLowerCase());
		if(className==null){
			synchronized (lock) {
				initTemplate();
			}
			className=templateClassMap.get(tempName.toLowerCase());
		}
		return className;
	}
	/**
	 * Function:preCheckParameters<br/>
	 * 
	 * @author Tony Ben
	 * @param map
	 * @param po
	 * @return
	 * @since JDK 1.6
	 */
	public static List<String> preCheckParameters(Map<String, CommonTask> map,
			B6Template po) {
		List<String> errList = new ArrayList<String>();
		for (String s : preExecute(map, po)) {
			CommonTask.checkParamExist(errList, s, po);
		}
		return errList;
	}

	/**
	 * Function:preExecute<br/>
	 * 
	 * @author Tony Ben
	 * @param map
	 * @param po
	 * @return
	 * @since JDK 1.6
	 */
	public static List<String> preExecute(Map<String, CommonTask> map,
			B6Template po) {
		List<String> list = new ArrayList<String>();
		map.get(CommonTask.START_KEY).preExecuteTask("", list, map, po);
		return list;
	}

	/**
	 * Function:preExecute<br/>
	 * 
	 * @author Tony Ben
	 * @param map
	 * @return
	 * @since JDK 1.6
	 */
	public static List<String> getTemplateModuleDetails(
			Map<String, CommonTask> map) {
		List<String> list = new ArrayList<String>();
		map.get(CommonTask.START_KEY).getTaskDetail("", list, map);
		return list;
	}

	public static void executeCommand(Map<String, CommonTask> map,
			B6Template po, ANAProcessResult result, IB6Connet connect) {
		map.get(CommonTask.START_KEY).executeTask(map, po, result, connect);
	}

	/**
	 * Function:getWorkFlow<br/>
	 * 
	 * @author Tony Ben
	 * @param element
	 * @param tempName
	 * @since JDK 1.6
	 */
	@SuppressWarnings("unchecked")
	private static void getWorkFlow(Element element, String templateName,
			String className, List<String> moduleList,
			Map<String, List<String>> commandMap) {
		// check parameters
		if (element == null) {
			return;
		}
		// tempName
		if (StringUtils.isEmpty(templateName)) {
			templateName = element.attributeValue("templateName");
		}
		if (commandMap == null) {
			commandMap = new HashMap<String, List<String>>();
		}
		TemplateCommandDetails template = new TemplateCommandDetails(
				templateName, className);
		template.setModules(moduleList);
		String operation = element.attributeValue("operation");
		template.setOperation(operation);
		Map<String, CommonTask> map = new HashMap<String, CommonTask>();
		List<Element> tasks = element.elements("task");
		for (Element e : tasks) {
			String type = e.elementText("type");
			if (StringUtils.isEmpty(type)) {
				log.error("init [Template]" + templateName
						+ " workflow Error, didn't set type name");
				return;
			}
			CommonTask task = null;
			try {
				task = (CommonTask) ClassReflectionUtils.getClassObject(
						"com.calix.bseries.server.ana.process.command", type);
			} catch (ClassNotFoundException e1) {
				log.error("[Template]" + templateName + " type:" + type
						+ " is not define", e1);
				return;
			} catch (InstantiationException e1) {
				log.error("Instantiation CommandTask[" + type + "] Error", e1);
				return;
			} catch (IllegalAccessException e1) {
				log.error("IllegalAccess CommandTask[" + type + "] Error", e1);
				return;
			}
			task.init(e, commandMap);
			if (task.isStart()) {
				map.put("start", task);
			} else if (task.isEnd()) {
				map.put("end", task);
			}
			map.put(task.getId().toLowerCase(), task);
		}
		template.setTaskMap(map);
		tasks = null;
		tempMap.put(templateName.toLowerCase() + "-" + operation.toLowerCase(),
				template);
	}

	/**
	 * Function:getCommandMap<br/>
	 * Remark:get all commands
	 * 
	 * @author Tony Ben
	 * @param element
	 * @return
	 * @since JDK 1.6
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, List<String>> getCommandMap(Element element) {
		// define map to restore the relationship for command list
		// key is command chain name , value is the list of commands
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		// check whether the parameter of element is null
		if (element == null) {
			return map;
		}
		List<Element> commands = element.elements("command");
		for (Element e : commands) {
			if (StringUtils.isEmpty(e.attributeValue("name"))
					|| StringUtils.isEmpty(e.attributeValue("name"))) {
				continue;
			}
			List<String> list = new ArrayList<String>();
			for (String s : e.getText().split("\n")) {
				if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())) {
					continue;
				}
				list.add(s.trim());
			}
			map.put(e.attributeValue("name").trim(), list);
			list = null;
		}
		// clean parameters
		commands = null;
		return map;
	}

	private static List<String> getTemplateModuleList(Element element) {
		List<String> list = new ArrayList<String>();
		if (element == null) {
			return list;
		}
		List<Element> modules = element.elements("module");
		for (Element e : modules) {
			// template.addModule(e.getTextTrim());
			list.add(e.getTextTrim());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private static void initTemplate() {
		File files = new File(ANAConstants.B6_COMMAND_CONFIG_FILE);
		if (!files.isDirectory()) {
			return;
		}
		for (File f : files.listFiles()) {
			if (f.getName().endsWith("template.xml")) {
				try {
					SAXReader reader = new SAXReader();
					Document document = reader.read(f);
					Element root = document.getRootElement();
					String tempName = root.element("desc")
							.element("templateName").getTextTrim();
					String className = root.element("desc")
							.element("templateObjectClass").getTextTrim();
					templateClassMap.put(tempName.toLowerCase(),className);
					// initial module list
					List<String> moduleList = getTemplateModuleList(root
							.element("modules"));
					// initial command List
					Map<String, List<String>> commandMap = getCommandMap(root
							.element("commands"));
					List<Element> workflowElements = root.elements("workflow");
					for (Element e : workflowElements) {
						getWorkFlow(e, tempName, className, moduleList,
								commandMap);
					}
					// clean temporary parameters
					commandMap = null;
					workflowElements = null;
					tempName = null;
					className = null;
					root = null;
					reader = null;
					document = null;
					// chains = null;
					// commands = null;
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
