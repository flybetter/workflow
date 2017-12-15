/** 
 * Project Name:socket_netty 
 * File Name:DSLInterfaceInUseCheck.java 
 * Package Name:com.calix.bseries.server.ana.process.command.check 
 * Date:8 Nov, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.process.command.check;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.ClassReflectionUtils;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
//import com.calix.bseries.server.ana.process.command.CommandUtils;
import com.calix.bseries.server.dbmodel.B6Template;

/**
 * ClassName:ServiceNotExistCheck <br/>
 * Function: TODO DEL FUNCTION. <br/>
 * Reason: TODO DEL REASON. <br/>
 * Date: 9 Oct, 2017 <br/>
 * 
 * @author Zheng Li
 * @version
 * @since JDK 1.6
 * @see
 */
public class BondedServiceNotExistCheck implements ICheck {
	private static final Logger log = Logger
			.getLogger(BondedServiceNotExistCheck.class);
	private static final String command = "show dsl bonding-group config ";
	private IB6Connet connect = null;

	public BondedServiceNotExistCheck(IB6Connet connect) {
		this.connect = connect;
	}

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		//connect=CommandUtils.connect(po, result, connect);
		if (connect==null) {
			return false;
		}
		boolean flg = new BoundGroupCheck().check(script, po, result);
		if (!flg) {
			return false;
		}
		if (connect == null) {
			log.error(result.getDeviceName() + "/" + result.getIpaddress()
					+ " can't be connect.");
			return false;
		}
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		Object service_number_int = ClassReflectionUtils.getFieldValue(po, "m_service_number_int");
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " check service not exist command:" + command
				+ obj.toString());
		String response = connect.executeCommandWithoutLog(command
				+ obj.toString());
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " response:" + response);
		if (StringUtils.isBlank(response)) {
			return false;
		}
		for (String s : response.split("\n")) {
			if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())) {
				continue;
			}
			//exist, run next work flow
			if (s.contains("Service : "+service_number_int.toString())) {
				return true;
			}
			if(s.trim().startsWith("%")){
				if (script.equals("bonding_group_number")) {
					result.setErrorInfo(ANAConstants.AnaErrorCode.BONDING_GROUP_NOT_EXISTING
							.toString());
				}
				return false;
			}
		}
		//not exist
		if (script.equals("bonding_group_number")) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.SERVICE_NOT_EXISTING
					.toString());
		}
		return false;
	}

}
