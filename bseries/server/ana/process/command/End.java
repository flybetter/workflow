/** 
 * Project Name:socket_netty 
 * File Name:End.java 
 * Package Name:com.calix.bseries.server.ana.process.command 
 * Date:19 Oct, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
*/ 
package com.calix.bseries.server.ana.process.command;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:End <br/> 
 * Date:     19 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class End extends CommonTask{

	@Override
	protected void initParam(Element e,Map<String, List<String>> commandMap) {
		setStart(false);
		setEnd(true);
		setNextId(null);	
	}

	@Override
	public void getTaskDetail(String prompt,List<String> commandList,
			Map<String, CommonTask> map) {
		commandList.add(prompt+"[END]");
	}

	@Override
	public void preExecuteTask(String prompt, List<String> commandList,
			Map<String, CommonTask> map, B6Template templatePO) {
		
	}

	@Override
	public void executeTask(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result,IB6Connet connect) {
	}

}
