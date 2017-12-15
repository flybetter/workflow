/** 
 * Project Name:socket_netty 
 * File Name:BoundGroupExistCheck.java 
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
 * ClassName:BoundGroupExistCheck <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 8 Nov, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class BoundGroupExistCheck implements ICheck {
	private static final Logger log = Logger
			.getLogger(BoundGroupExistCheck.class);
	private static final String command = "show dsl bonding-group status ";
	private IB6Connet connect = null;

	public BoundGroupExistCheck(IB6Connet connect) {
		this.connect = connect;
	}

	@Override
	public boolean check(String script, B6Template po, ANAProcessResult result) {
		//connect first
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
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " check boundgroup exist command:" + command + obj.toString());
		String response = connect.executeCommandWithoutLog(command
				+ obj.toString());
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " response:" + response);
		if (StringUtils.isBlank(response)) {
			return false;
		}
		if (response.trim().startsWith("%")
				&& response.contains("Bonding group")
				&& response.contains("does not exist")) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.BONDING_GROUP_NOT_EXISTING
					.toString());
			return false;
		}
		// check port
		return checkPort(response, po, result);
	}

	private boolean checkPort(String response, B6Template po,
			ANAProcessResult result) {
		String[] ports = new String[2];
		String[] tmp;
		for (String s : response.split("\n")) {
			if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())) {
				continue;
			}
			// check 1
			if (s.contains("Assigned ports")) {
				tmp = s.trim().split(":");
				ports = tmp[1].trim().split(",");
				return checkBothPorts(ports, po, result);
			}
			// check 2
			if (s.contains("|") && s.contains(",")) {
				tmp = s.split("\\|");
				ports = tmp[tmp.length - 1].split(",");
				return checkBothPorts(ports, po, result);
			}
		}
		return true;
	}

	private boolean checkBothPorts(String[] ports, B6Template po,
			ANAProcessResult result) {
		boolean flg = checkPortExist(ports[0],
				ANAConstants.AnaErrorCode.FIRST_INTF_NOT_MAPPED_TO_BG
						.toString(), po, result);
		if (!flg) {
			return false;
		}
		if (ports.length > 1) {
			flg = checkPortExist(ports[1],
					ANAConstants.AnaErrorCode.SECOND_INTF_NOT_MAPPED_TO_BG
							.toString(), po, result);
			if (!flg) {
				return false;
			}
		}
		return true;
	}

	private boolean checkPortExist(String port, String errorCode,
			B6Template po, ANAProcessResult result) {
		port = port.replace("(m)", "");
		Object dsl_interface_number = ClassReflectionUtils.getFieldValue(po,
				"m_dsl_interface_number");
		if (dsl_interface_number == null) {
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.PARAMETER_VALUE_INVALID
							.toString(), "dsl_interface_number");
			return false;
		}
		Object dsl_interface_number_2 = ClassReflectionUtils.getFieldValue(po,
				"m_dsl_interface_number_2");
		if (dsl_interface_number_2 == null) {
			result.setErrorInfo(
					ANAConstants.AnaErrorCode.PARAMETER_VALUE_INVALID
							.toString(), "dsl_interface_number_2");
			return false;
		}
		if (!port.trim().equals(dsl_interface_number.toString())
				&& !port.trim().equals(dsl_interface_number_2.toString())) {
			result.setErrorInfo(errorCode);
			return false;
		}
		return true;
	}

}
