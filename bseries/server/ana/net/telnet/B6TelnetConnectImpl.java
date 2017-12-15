package com.calix.bseries.server.ana.net.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.error.AnaException;
import com.calix.bseries.server.ana.net.IB6Connet;
import com.calix.bseries.server.ana.process.ANAProcessResult;

public class B6TelnetConnectImpl implements IB6Connet,TelnetNotificationHandler {
	private static final Logger log = Logger
			.getLogger(B6TelnetConnectImpl.class);
	private TelnetClient tc = null;
	private StringBuffer responseStr;
	private ANAProcessResult result;
	// private FileOutputStream fout;
	private String devicePrompt;
	private OutputStream outputStream;
	private InputStream inputStream;
	private AtomicLong beginTime;

	/**
	 * Default Construct Method Initial TelnetClient
	 * 
	 * @throws IOException
	 * @throws InvalidTelnetOptionException
	 */
	public B6TelnetConnectImpl(ANAProcessResult result) {
		responseStr = new StringBuffer();
		this.result = result;
		tc = new TelnetClient();
		try {
			tc.addOptionHandler(new TerminalTypeOptionHandler(
					ANAConstants.DEFAULT_TERMTYPE, false, false, true, false));
			tc.addOptionHandler(new EchoOptionHandler(true, false, true, false));
			tc.addOptionHandler(new SuppressGAOptionHandler(true, true, true,
					true));
		} catch (InvalidTelnetOptionException e) {
			log.error("please check telnet option of command", e);
		}
	}

	@Override
	public boolean connectWithPassword(String ip, String enablepassword, String clipassword)throws AnaException {
		try {
			// fout = new FileOutputStream(ip + "_" + "_telnet.log", true);
			tc.connect(ip, ANAConstants.DEFAULT_TELNET_PORT);
			tc.setConnectTimeout(ANAConstants.DEFAULT_TIMEOUT);
			tc.registerNotifHandler(this);
			// tc.registerSpyStream(fout);
			excuteCommand(clipassword, false);
			excuteCommand(ANAConstants.DEFAULT_ENABLE_COMMAND, false);
			String str = excuteCommand(enablepassword, false);
			devicePrompt = str;
			result.setDevicePrompt(devicePrompt);
			//result.addPrompt(str);
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean executeCommand(String command) {
		try {
			String response = excuteCommand(command);
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
		} else if(response.contains(ANAConstants.TIMEOUTERROR)){
			result.setErrorInfo(ANAConstants.AnaErrorCode.DEVICE_RESPONSE_TIMEOUT
					.toString());
			return false;
		}else if (response.trim().startsWith("%")) {
			result.setErrorInfo(ANAConstants.AnaErrorCode.UNKNOWN_RESPONSE
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
		String response = interaction(command);

		response = response.toString().replaceAll("--More--", "")
				.replaceAll("", "");
		response = response.replaceAll("", "");
		response = response.replaceAll("&#27;\\[J", "");
		response = response.replaceAll("&#27", "");
		response = response.replaceAll("\\[J", "");
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
	
	private String interaction(String command){
		String response="";
		inputStream = tc.getInputStream();
		outputStream = tc.getOutputStream();
		beginTime = new AtomicLong(System.currentTimeMillis());
		write(outputStream, command);
		try {
			byte[] buff = new byte[2048];
			int ret_read = 0;
			do {
				ret_read = inputStream.read(buff);
				if (ret_read > 0) {
					responseStr.append(new String(buff, 0, ret_read));
					response = responseStr.toString();
					if ((System.currentTimeMillis() - beginTime.get()) > ANAConstants.LONG_TIMEOUT) {
						log.error(ANAConstants.TIMEOUTERROR + ",[command : " + command + "]");
						responseStr.append(ANAConstants.TIMEOUTERROR + ",[command : " + command + "]");
					} else {
						if (response.trim().endsWith("--More--")) {
							write(outputStream, " ");
						}
						if (response.endsWith("#") || response.endsWith(">")|| response.endsWith("Password: ")|| response.endsWith("Incomplete command\n")) {
							break;
						}
					}
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e.getMessage());
		}
		return response;
	}
	
	private void write(OutputStream out, String str) {
		if (out == null) {
			return;
		}
		try {
			out.write(str.getBytes());
			// out.write(13);
			out.write(10);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String excuteCommand(String command) throws AnaException {
		return excuteCommand(command, true);
	}

	@Override
	public boolean disconnect() {
		try {
			if (tc != null && tc.isConnected())
				tc.disconnect();
			return true;
		} catch (Exception e) {
			log.error("Exception when close connecting:" + e.getMessage());
		}
		return false;
	}

	@Override
	public void receivedNegotiation(int negotiation_code, int option_code) {

	}

}
