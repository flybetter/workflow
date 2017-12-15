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
 * CircuidCheck <br/>
 * Function: TODO MOD FUNCTION. <br/>
 * Reason: TODO MOD REASON. <br/>
 * Date: 13 Oct, 2017 <br/>
 * 
 * @author Zheng Li
 * @version
 * @since JDK 1.6
 * @see
 */
public class CircuidCheck implements ICheck {
	private static final Logger log = Logger
			.getLogger(CircuidCheck.class);
	private static final String command = "show running-config int dsl ";
	private IB6Connet connect = null;

	public CircuidCheck(IB6Connet connect) {
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
		Object dsl_interface_number = ClassReflectionUtils.getFieldValue(po, "m_" + script).toString();
		Object key_info = ClassReflectionUtils.getFieldValue(po, "m_key_info");
		Object key_info_old = ClassReflectionUtils.getFieldValue(po, "m_key_info_old");
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " check info exist command:" + command
				+ dsl_interface_number);
		String response = connect.executeCommandWithoutLog(command
				+ dsl_interface_number);
		log.info(result.getDeviceName() + "/" + result.getIpaddress()
				+ " response:" + response);
		if (StringUtils.isBlank(response)) {
			return false;
		}
		for (String s : response.split("\n")) {
			s = s.replace("\r", "");
			if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())) {
				continue;
			}
			//exist, run next work flow,CIRCUITID 73/ARDA/063247//NH/
			if (s.contains("info ")) {
				//MOD
				if(key_info_old!=null){
					if(key_info_old.toString().equals(s.substring(s.indexOf("info ")+5))){
						return true;
					}
				}else if(key_info_old==null){
					//DEL,under the premise of DEL request doesn't include the key_info_old attribute
					if(key_info.toString().equals(s.substring(s.indexOf("info ")+5))){
						return true;
					}
				}
			}
			if(s.trim().startsWith("%")){
				if (script.equals("dsl_interface_number")) {
					result.setErrorInfo(ANAConstants.AnaErrorCode.PORT_NOT_EXISTING
							.toString());
				}
				return false;
			}
		}
		//MOD
		if(key_info_old!=null){
			result.setErrorInfo(ANAConstants.AnaErrorCode.KEYINFO_OLD_NOT_EXIST
						.toString());
		}else if(key_info_old==null){
			//DEL
			result.setErrorInfo(ANAConstants.AnaErrorCode.KEYINFO_NOT_EXIST
					.toString());
		}
		return false;
	}

}
