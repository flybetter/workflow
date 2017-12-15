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
 * ClassName:DSLInterfaceInUseCheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 8 Nov, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class DSLInterfaceInUseCheck implements ICheck {
	private static final Logger log = Logger
			.getLogger(DSLInterfaceInUseCheck.class);
	private static final String command = "show dsl status ";
	private IB6Connet connect = null;

	public DSLInterfaceInUseCheck(IB6Connet connect) {
		this.connect = connect;
	}

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		//connect=CommandUtils.connect(po, result, connect);
		if (connect==null) {
			return false;
		}
		boolean flg = new DSLInterfaceCheck().check(script, po, result);
		if (!flg) {
			return false;
		}
		if (connect == null) {
			log.error(result.getDeviceName() + "/" + result.getIpaddress()
					+ " can't be connect.");
			return false;
		}
		Object obj = ClassReflectionUtils.getFieldValue(po, "m_" + script);
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " check dsl interface in use command:" + command
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
			if (s.contains("Admin") && s.contains("up")) {
				if (script.equals("dsl_interface_number")) {
					result.setErrorInfo(ANAConstants.AnaErrorCode.PORT_ALREADY_IN_USE
							.toString());
				} else if (script.equals("dsl_interface_number_2")) {
					result.setErrorInfo(ANAConstants.AnaErrorCode.SECOND_PORT_ALREADY_IN_USE
							.toString());
				}
				return false;
			}
			if(s.trim().startsWith("%")){
				if (script.equals("dsl_interface_number")) {
					result.setErrorInfo(ANAConstants.AnaErrorCode.PORT_NOT_EXISTING
							.toString());
				} else if (script.equals("dsl_interface_number_2")) {
					result.setErrorInfo(ANAConstants.AnaErrorCode.SECOND_PORT_NOT_EXISTING
							.toString());
				}
				return false;
			}
		}
		return true;
	}

}
