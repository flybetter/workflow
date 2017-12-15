package com.calix.bseries.server.ana.net.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.error.AnaException;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class B6SSHConnectImpl implements IB6Connet {
	private static final Logger log = Logger
			.getLogger(B6SSHConnectImpl.class);
	
	private StringBuffer responseStr;
	private ANAProcessResult result;
	private String devicePrompt;
	private ChannelShell channel;
	private Session session;
	private JSch jsch;
	private InputStream inputStream;
	private OutputStream outputStream;
	private AtomicLong beginTime;

	/**
	 * Default Construct Method Initial TelnetClient
	 * 
	 * @throws IOException
	 * @throws InvalidTelnetOptionException
	 */
	public B6SSHConnectImpl(ANAProcessResult result) {
			responseStr = new StringBuffer();
			this.result = result;
			jsch = new JSch();	
	}

	@Override
	public boolean connectWithPassword(String ip, String enablepassword, String clipassword)throws AnaException {
		try{
			session = jsch.getSession("cli", ip, ANAConstants.DEFAULT_SSH_PORT);
	        session.setPassword(clipassword);
	        java.util.Properties config = new java.util.Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	        session.connect();
	        channel = (ChannelShell) session.openChannel("shell");
	        channel.connect();
	        inputStream = channel.getInputStream();
			outputStream = channel.getOutputStream();
			//end string of the response is different from other command,deal with separately
			String CLIResult = loginSSHInteraction(clipassword,"cli");
			if("connect failed".equals(CLIResult)){
				return false;
			}
	        excuteCommand(ANAConstants.DEFAULT_ENABLE_COMMAND, false);
	        String enableResult = loginSSHInteraction(enablepassword,"enable");
			if("connect failed".equals(enableResult)){
				return false;
			}
			devicePrompt = enableResult;
			result.setDevicePrompt(devicePrompt);
	        return true;
		}catch (Exception e){
			log.error("Connect failed with ssh protocol to device IP:" + ip,e);
		}
		return false;
	}
	
	@Override
	public boolean executeCommand(String command) {
		try {
			String response = excuteCommand(command);
			if(command.contains("no info")){
				if(response.contains("% Command failed")){
					log.info("there is no info on this port,execute next command");
				}
				return true;
			}
			if(command.contains("tpstc mode atm")){
				if(response.contains("% Unrecognized command")){
					log.info("don't care about the error message");
				}
				return true;
			}
			if(command.contains("show dsl profile")){
				for (String s : response.split("\n")) {
					if(!checkResult(s)){
						return false;
					}
				}
			}
			return checkResult(response);
		} catch (AnaException e) {
			return false;
		}
	}

	public String executeCommandWithoutLog(String command) {
		try {
			String response = excuteCommand(command, false);
			return response;
		} catch (AnaException e) {
			return null;
		}
	}

	private boolean checkResult(String response) {
		if (response.contains(ANAConstants.ERROR_INVALID_COMMAND)) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.UNKNOWN_ERROR
					.toString());
			return false;
		} else if (response.contains(ANAConstants.INVALID_ACCESS_PROFILE)) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_ACCESS_PROFILE
					.toString());
			return false;
		} else if (response.contains(ANAConstants.SERVICE_NUM_ERROR)) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.SERVICE_NOT_EXISTING
					.toString());
			return false;
		}else if(response.contains(ANAConstants.BOUNDING_GROUP_INUSE_216)||response.contains(ANAConstants.BOUNDING_GROUP_INUSE_214)){
			result.setErrorInfo(ANAConstants.AnaErrorCode.BONDING_GROUP_ALREADY_IN_USE
					.toString());
			return false;
		} else if(response.contains(ANAConstants.SERVICE_NOT_DEFINE)){
			result.setErrorInfo(ANAConstants.AnaErrorCode.NO_SERVICE_CONFIG_ON_PORT
					.toString());
			return false;
		}else if (response.trim().startsWith("%")) {
			if(response.trim().contains("DSL profile") && response.trim().contains("does not exist")){
				result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_DSL_PROFILE
						.toString());
				return false;
			}
			result.setErrorInfo(ANAConstants.AnaErrorCode.UNKNOWN_RESPONSE
					.toString());
			return false;
		}else if (response.trim().contains("No dsl profile")){
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_DSL_PROFILE
					.toString());
			return false;
		}else if (response.trim().contains("Profile") && response.trim().contains("does not exist")){
			result.setErrorInfo(ANAConstants.AnaErrorCode.INVALID_DSL_PROFILE
					.toString());
			return false;
		}
		return true;
	}

	/**
	 * Execute Command For Initial
	 * 
	 * @param command
	 * @param needLog
	 * @return
	 * @throws AnaException
	 */
	private String excuteCommand(String command, boolean needLog)
			throws AnaException {
		
		responseStr.delete(0, responseStr.capacity() + 1);
		
		String response=interaction(command);
		response = response.toString().replaceAll("--More--", "")
				.replaceAll(" ", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("&#27;\\[J", "");
		response = response.replaceAll("&#27", "");
		response = response.replaceAll("\\[J", "");
		response = response.replaceAll("\\^J", "");
		response = response.replaceAll(command.trim(), "");
		if (devicePrompt != null) {
			response = response.replaceAll(devicePrompt, "");
		}
		response = response.trim();
		if (needLog) {
			result.addCommandResult(command, response);
		}
		return response;
	}
	
	/**
	 * Execute CLI Command 
	 * 
	 * @param command
	 * @return
	 * @throws AnaException
	 */
	public String excuteCLICommand(String command)
			throws AnaException {
		
		responseStr.delete(0, responseStr.capacity() + 1);
		
		String response=interactionCLI(command);
		response = response.toString().replaceAll("--More--", "")
				.replaceAll(" ", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("&#27;\\[J", "");
		response = response.replaceAll("&#27", "");
		response = response.replaceAll("\\[J", "");
		response = response.replaceAll("\\^J", "");
		response = response.replaceAll(command.trim(), "");

		if (devicePrompt != null) {
			response = response.replaceAll(devicePrompt, "");
		}
		if(response.contains("Unrecognized command")){
			response = "the device can't support this command";
		}
		response = response.trim();
		log.info("[CLI Command]:" + command + "\r\n[CLI Response]:" + response);
		return response;
	}
	
	private String interaction(String command){
		String response="";
		try {
			beginTime = new AtomicLong(System.currentTimeMillis());
			write(outputStream, command);
	        outputStream.flush();
	        if(command.startsWith("show access-profile")||command.startsWith("show dsl profile")){
	        	Thread.sleep(1000);
	        }
			byte[] buff = new byte[2048];
			int ret_read = 0;
			do {
				ret_read = inputStream.read(buff);
				if (ret_read > 0) {
					response= responseStr.append(new String(buff, 0, ret_read)).toString();

					//copy command cost more time,return directly
					if("copy running-config startup-config".equals(command)){
						return "\nBuilding configuration...    Done (05:20)\nVerified copy to flash";
					}else{
						if ((System.currentTimeMillis() - beginTime.get()) > ANAConstants.LONG_TIMEOUT) {
							log.error(ANAConstants.TIMEOUTERROR + ",[command : " + command + "]");
							responseStr.append(ANAConstants.TIMEOUTERROR + ",[command : " + command + "]");
							return responseStr.toString();
						}
					}
					//avoid response lstnmeasw08-oc-0020-1e05# 
					if(!replaceMessyCode(response).contains(command)){
						continue;
					}
					if(command.startsWith("show access-profile") && !response.contains("Access profile")){
						continue;
					}
					if (response.trim().endsWith("--More--")) {
						write(outputStream, " ");
		            }
		            if (response.trim().endsWith("#") || response.trim().endsWith(">") || response.trim().endsWith("Password:")|| response.trim().endsWith("Incomplete command\n")) {
		            	log.info("[END]read response from device successfully,[command] : " + command+ ",[response] : " + response);
		            	break;
		            }
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e);
		}
		return response;
	}
	
	private String interactionCLI(String command){
		String response="";
		try {
			beginTime = new AtomicLong(System.currentTimeMillis());
			write(outputStream, command);
			outputStream.flush();
			if("show running-config".equals(command)){
				Thread.sleep(10000);
			}
			byte[] buff = new byte[2048];
			int ret_read = 0;
			do {
				ret_read = inputStream.read(buff);
				if (ret_read > 0) {
					response= responseStr.append(new String(buff, 0, ret_read)).toString();
					
					if ((System.currentTimeMillis() - beginTime.get()) > ANAConstants.CLI_TIMEOUT) {
						log.error(ANAConstants.TIMEOUTERROR + ",[command : " + command + "]");
						responseStr.append("\n...\nIt takes to long time to get infomation from device,If you need more detail, please run the command on device side.");
						return responseStr.toString();
					}
					//avoid response lstnmeasw08-oc-0020-1e05# 
					//show interfaces dsl ? , response don't contain ?
					if(!response.contains(command)&&!response.contains("show interfaces dsl")){
						continue;
					}
					if (response.trim().endsWith("--More--")) {
						if (response.length() > 32000) {
					          response = response.substring(0, 32000);
					          response = response + "\n...\nResponse is too long, CMS can only display a maximum of 32767 bytes ot text. If you need more detail, please use cut-through tool.";
					          return response;
					    }
						write(outputStream, " ");
					}
					if (response.trim().endsWith("#") || response.trim().endsWith(">") || response.trim().endsWith("Password:")|| response.trim().endsWith("Incomplete command\n")) {
						log.info("[END]read response from device successfully,[command] : " + command+ ",[response] : " + response);
						break;
					}
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e);
		}
		return response;
	}
	
	//deal with login ssh command separately
	private String loginSSHInteraction(String command, String passwordType){
		responseStr.delete(0, responseStr.capacity() + 1);
		String response="";
		try {
			write(outputStream, command);
	        outputStream.flush();
			byte[] buff = new byte[2048];
			int ret_read = 0;
			do {
				ret_read = inputStream.read(buff);
				if (ret_read > 0) {
					response= responseStr.append(new String(buff, 0, ret_read)).toString();
					if("cli".equals(passwordType)){
						if (response.contains("Password: \r\nPassword")) {
			            	log.info("connect failed,response : "+response);
			            	return "connect failed";
			            }
					}else if("enable".equals(passwordType)){
						if (response.contains("Password:")) {
			            	log.info("connect failed,response : "+response);
			            	return "connect failed";
			            }
					}
		            if (response.trim().endsWith("#") || response.trim().endsWith(">")|| response.trim().endsWith("Incomplete command\n")) {
		            	log.info("[END]read response from device successfully,[command] : " + command+ ",[response] : " + response);
		            	break;
		            }
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e);
		}
		return response;
	}

	private void write(OutputStream out, String str) {
		if (out == null) {
			log.info("outputStream is null , please check it for command:" + str);
			return;
		}
		try {
			out.write(str.getBytes());
			// out.write(13);
			out.write(10);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	private String replaceMessyCode(String response){
		response = response.replaceAll(" ", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("&#27;\\[J", "");
		response = response.replaceAll("&#27", "");
		response = response.replaceAll("\\[J", "");
		response = response.replaceAll("\\^J", "");
		return response;
	}
	
	@Override
	public String excuteCommand(String command) throws AnaException {
		return excuteCommand(command, true);
	}

	@Override
	public boolean disconnect() {
		try {
		   if(session != null && session.isConnected()){
	            session.disconnect();
	        }
			return true;
		} catch (Exception e) {
			log.error("Exception when close connecting:" + e);
		}
		return false;
	}

}
