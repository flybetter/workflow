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
public class DSLProfileNotExistCheck implements ICheck {
	private static final Logger log = Logger
			.getLogger(DSLProfileNotExistCheck.class);
	private IB6Connet connect = null;
	private static final String command = "show running-config dsl-profile ";

	public DSLProfileNotExistCheck(IB6Connet connect) {
		this.connect = connect;
	}

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		//connect=CommandUtils.connect(po, result, connect);
		if (connect==null) {
			return false;
		}
		if (connect == null) {
			log.error(result.getDeviceName() + "/" + result.getIpaddress()
					+ " can't be connect.");
			return false;
		}
		Object dsl_profile_name = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " check dsl profile not exist command:" + command
				+ "dsl profile name:"+dsl_profile_name.toString());
		String response = connect.executeCommandWithoutLog(command);
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
			if (s.contains(dsl_profile_name.toString())) {
				return true;
			}
			
		}
		if (script.equals("dsl_profile_name")) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_DSL_PROFILE
					.toString());
		}
		return false;
	}

}
