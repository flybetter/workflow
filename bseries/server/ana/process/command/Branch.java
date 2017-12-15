/** 
 * Project Name:socket_netty 
 * File Name:Branch.java 
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
import com.calix.bseries.server.ana.process.command.compare.CompareFactory;
import com.calix.bseries.server.dbmodel.B6Template;

/** 
 * ClassName:Branch <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     18 Oct, 2016 <br/> 
 * @author   Tony Ben 
 * @version  
 * @since    JDK 1.6 
 * @see       
 */
public class Branch extends CommonTask{
	private String trueTarget;
	private String falseTarget;
	private String operator;
	private String left;
	private String right;
	private String objectType="string";
	@Override
	protected void initParam(Element e,Map<String, List<String>> commandMap) {
		//initial parameters 
		setTrueTarget(e.elementText("trueTarget"));
		setFalseTarget(e.elementText("falseTarget"));
		setOperator(e.elementText("operator"));
		setLeft(e.elementText("left"));
		setRight(e.elementText("right"));
		if(!StringUtils.isEmpty(e.elementText("objectType"))){
			setObjectType(e.elementText("objectType"));
		}else{
			setObjectType("string");
		}
		
	}

	@Override
	public void getTaskDetail(String prompt,List<String> commandList,
			Map<String, CommonTask> map) {
		commandList.add(prompt+"[Branch]:");
		prompt=prompt+DEFAULT_PROMPT;
		commandList.add(prompt+"if( "+left+" "+operator+" "+ right+"){");
		map.get(trueTarget).getTaskDetail(prompt+DEFAULT_PROMPT,commandList, map);
		commandList.add(prompt+"}else{");
		map.get(falseTarget).getTaskDetail(prompt+DEFAULT_PROMPT,commandList, map);
		commandList.add(prompt+"}");
	}
	
	@Override
	public void preExecuteTask(String prompt, List<String> commandList,
			Map<String, CommonTask> map, B6Template templatePO) {
		doCompare(templatePO);
		map.get(getNextId()).preExecuteTask(prompt, commandList, map,templatePO);
	}
	
	@Override
	public void executeTask(Map<String, CommonTask> map, B6Template po,
			ANAProcessResult result,IB6Connet connect) {
		doCompare(po);
		map.get(getNextId()).executeTask(map, po, result,connect);
	}
	
	/**
	 * Function:doCompare<br/> 
	 * @author Tony Ben 
	 * @param templatePO 
	 * @since JDK 1.6
	 */
	private void doCompare(B6Template templatePO){
		String leftParam=replaceParameter(left,templatePO);
		String rightParam=replaceParameter(right,templatePO);
		//[CMS-14609] set the default value to null
		if(leftParam.startsWith("${")){
			leftParam=null;
		}
		if(rightParam.startsWith("${")){
			rightParam=null;
		}
		
		if(CompareFactory.compare(leftParam, rightParam, operator, objectType)){
			setNextId(trueTarget);
		}else{
			setNextId(falseTarget);
		}
	}
	
	
	/**
	 * trueTarget.
	 * @return the trueTarget
	 */
	public String getTrueTarget() {
		return trueTarget;
	}
	/**
	 * trueTarget
	 * @param trueTarget the trueTarget to set
	 */
	public void setTrueTarget(String trueTarget) {
		this.trueTarget = trueTarget;
	}
	/**
	 * falseTarget.
	 * @return the falseTarget
	 */
	public String getFalseTarget() {
		return falseTarget;
	}
	/**
	 * falseTarget
	 * @param falseTarget the falseTarget to set
	 */
	public void setFalseTarget(String falseTarget) {
		this.falseTarget = falseTarget;
	}
	/**
	 * operator.
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * operator
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * left.
	 * @return the left
	 */
	public String getLeft() {
		return left;
	}
	/**
	 * left
	 * @param left the left to set
	 */
	public void setLeft(String left) {
		this.left = left;
	}
	/**
	 * right.
	 * @return the right
	 */
	public String getRight() {
		return right;
	}
	/**
	 * right
	 * @param right the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str="[Branch]"
					+"\r      trueTarget: "+trueTarget
					+"\r      falseTarget:"+falseTarget
					+"\r	  operator:"+operator
					+"\r      left:"+left
					+"\r      right:"+right;
		
		return str+super.toString();
	}

	/**
	 * objectType.
	 * @return the objectType
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * objectType
	 * @param objectType the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}
