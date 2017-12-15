package com.calix.bseries.server.ana.net.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
import com.calix.bseries.server.ana.process.ANAProcessResult;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class B6CLICommandImpl implements Runnable, TelnetNotificationHandler {
	private static final Logger log = Logger.getLogger(B6CLICommandImpl.class);
	private TelnetClient tc = null;
	private StringBuffer responseStr;
	private AtomicLong beginTime;
	private String devicePrompt;
	private AtomicBoolean end = new AtomicBoolean(false);
	private ANAProcessResult result;
	private AtomicBoolean timeout = new AtomicBoolean(false);
	
	private ChannelShell channel;
	
	private Session session;
	
	private JSch jsch;
	
	private InputStream inputStream;
	private OutputStream outputStream;
	
	final ExecutorService exec = Executors.newFixedThreadPool(1);

	/**
	 * Default Construct Method Initial TelnetClient
	 * 
	 * @throws IOException
	 * @throws InvalidTelnetOptionException
	 */
	public B6CLICommandImpl(ANAProcessResult result, AtomicBoolean timeout) {
//		beginTime = new AtomicLong(System.currentTimeMillis());
//		responseStr = new StringBuffer();
//		this.result = result;
//		this.timeout = timeout;
//		tc = new TelnetClient();
//		try {
//			tc.addOptionHandler(new TerminalTypeOptionHandler(
//					ANAConstants.DEFAULT_TERMTYPE, false, false, true, false));
//			tc.addOptionHandler(new EchoOptionHandler(true, false, true, false));
//			tc.addOptionHandler(new SuppressGAOptionHandler(true, true, true,
//					true));
//		} catch (InvalidTelnetOptionException e) {
//			log.error("please check telnet option of command", e);
//		}
		responseStr = new StringBuffer();
		this.result = result;
		jsch = new JSch();	
	}

	public String connect(String ip) throws AnaException {
		try {
			tc.connect(ip, ANAConstants.DEFAULT_TELNET_PORT);
			tc.setConnectTimeout(ANAConstants.DEFAULT_TIMEOUT);
			Thread reader = new Thread(this);
			tc.registerNotifHandler(this);
			reader.start();
			excuteCommand(ANAConstants.DEFAULT_B6_TELECT_PASSWD);
			end.set(false);
			excuteCommand(ANAConstants.DEFAULT_ENABLE_COMMAND);
			end.set(false);
			excuteCommand(ANAConstants.DEFAULT_EN_PASSWORD);
			String str2 = responseStr.toString();
			devicePrompt = str2;
			result.addPrompt(str2);
			end.set(false);
			return "success";
		} catch (SocketException e) {
			e.printStackTrace();
			log.error("connect failed", e);
			return "connect failed";
		} catch (IOException e) {
			e.printStackTrace();
			log.error("connect failed", e);
			return "connect failed";
		}
	}
	
	public String connect(String ip,String enablepassword,String clipassword) throws AnaException {
//		try {
//			tc.connect(ip, ANAConstants.DEFAULT_TELNET_PORT);
//			tc.setConnectTimeout(ANAConstants.DEFAULT_TIMEOUT);
//			Thread reader = new Thread(this);
//			tc.registerNotifHandler(this);
//			reader.start();
//			excuteCommand(clipassword);
//			end.set(false);
//			excuteCommand(ANAConstants.DEFAULT_ENABLE_COMMAND);
//			end.set(false);
//			excuteCommand(enablepassword);
//			String str2 = responseStr.toString();
//			devicePrompt = str2;
//			result.addPrompt(str2);
//			end.set(false);
//			return "success";
//		} catch (SocketException e) {
//			e.printStackTrace();
//			log.error("connect failed", e);
//			return "connect failed";
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error("connect failed", e);
//			return "connect failed";
//		}
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
	        excuteCommand(clipassword);
	        excuteCommand(ANAConstants.DEFAULT_ENABLE_COMMAND);
	        String str = excuteCommand(enablepassword);
			devicePrompt = str;
			result.setDevicePrompt(devicePrompt);
			return "success";
		}catch (Exception e){
			e.printStackTrace();
		}
		return "connect failed";
	}

	/**
	 * Execute Command For Initial
	 * 
	 * @param command
	 * @param needLog
	 * @return
	 * @throws AnaException
	 */
	public String excuteCommand(String command) throws AnaException {
		responseStr.delete(0, responseStr.capacity() + 1);
//		String response = doCommandChain(command);
//		if(command.contains("show running-config")){
//			String configresponse="\n...\nthis command will take longe time ,If you need more detail, please use cut-through tool.";
//			return configresponse;
//		}
		String response=interaction(command);
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
		if(response.contains("Unrecognized command")){
			response = "the device can't support this command";
		}
		response = response.trim();
		log.info(response);
		return response;
	}
	
	
	private String interaction(String command){
		String response="";
		try {
	        outputStream.write((command+"\n\r").getBytes());
	        outputStream.flush();
	        Thread.sleep(1000);
			byte[] buff = new byte[12048];
			int ret_read = 0;
			do {
				ret_read = inputStream.read(buff);
				if (ret_read > 0) {
					// byte[] temp = new String(buff, 0,
					// ret_read).getBytes("iso8859-1");
					response= responseStr.append(new String(buff, 0, ret_read)).toString();
					 if (response.trim().endsWith("--More--")) {
						 	 write(outputStream, " ");
		             }
		             if (response.trim().endsWith("#") || response.trim().endsWith(">")|| response.trim().endsWith("Password:")|| response.trim().endsWith("Incomplete command\n")) {
		                      break;
		             }
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e.getMessage());
		}
		return response;
	}

	/**
	 * Handle More Method
	 * 
	 * @param command
	 * @throws AnaException
	 */
	private String doCommandChain(String command) throws AnaException {
		OutputStream outstr = tc.getOutputStream();
		write(outstr, command);
		while (!end.get()) {
			if (timeout.get()) {
				if ((System.currentTimeMillis() - beginTime.get()) > ANAConstants.CLI_TIMEOUT) {
					responseStr
							.append("\n...\nIt takes to long time to get infomation from device,If you need more detail, please use cut-through tool.");
					end.set(true);
				}
			}
		}
		return responseStr.toString();
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
			log.error(e);
		}
	}

	public boolean disconnect() {
//		try {
//			if (tc != null && tc.isConnected())
//				tc.disconnect();
//			return true;
//		} catch (Exception e) {
//			log.error("Exception when close connecting:" + e.getMessage());
//		}
//		return false;
		try {
			   if(session != null && session.isConnected()){
		            session.disconnect();
		        }
				return true;
			} catch (Exception e) {
				log.error("Exception when close connecting:" + e.getMessage());
			}
			return false;
	}

	@Override
	public void receivedNegotiation(int negotiation_code, int option_code) {

	}

	@Override
	public void run() {
		InputStream instr = tc.getInputStream();
		try {
			byte[] buff = new byte[2048];
			int ret_read = 0;
			do {
				ret_read = instr.read(buff);
				if (ret_read > 0) {
					// byte[] temp = new String(buff, 0,
					// ret_read).getBytes("iso8859-1");
					responseStr.append(new String(buff, 0, ret_read));
					String response = responseStr.toString();
					OutputStream outstr = tc.getOutputStream();
					if (timeout.get()) {
						if ((System.currentTimeMillis() - beginTime.get()) > ANAConstants.CLI_TIMEOUT) {
							response += "\n...\nIt takes to long time to get infomation from device,If you need more detail, please use cut-through tool.";
							end.set(true);
						} else {
							if (response.trim().endsWith("--More--")) {
								write(outstr, " ");
							}
							if (response.endsWith("#")|| response.endsWith(">")|| response.endsWith("Password: ")) {
								end.set(true);
							}
						}
					} else {
						if (response.trim().endsWith("--More--")) {
							write(outstr, " ");
						}
						if (response.endsWith("#") || response.endsWith(">")|| response.endsWith("Password: ")|| response.endsWith("Incomplete command\n")) {
							end.set(true);
						}
					}
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e.getMessage());
		}

	}
}
