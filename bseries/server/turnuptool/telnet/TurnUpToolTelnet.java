package com.calix.bseries.server.turnuptool.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.common.error.AnaException;

public class TurnUpToolTelnet implements Runnable, TelnetNotificationHandler {
	private static final Logger log = Logger.getLogger(TurnUpToolTelnet.class);
	private TelnetClient tc = null;
	private StringBuffer responseStr;
	private AtomicBoolean end = new AtomicBoolean(false);

	/**
	 * Default Construct Method Initial TelnetClient
	 * 
	 * @throws IOException
	 * @throws InvalidTelnetOptionException
	 */
	public TurnUpToolTelnet() {
		responseStr = new StringBuffer();
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

	public String connect(String ip) throws AnaException {
		try {
			tc.setConnectTimeout(ANAConstants.DEFAULT_TIMEOUT);
			tc.connect(ip, ANAConstants.DEFAULT_TELNET_PORT);
			Thread reader = new Thread(this);
			tc.registerNotifHandler(this);
			reader.start();
			excuteCommand(ANAConstants.DEFAULT_B6_TELECT_PASSWD);
			end.set(false);
			return "SUCCESS";
		} catch (SocketException e) {
			log.error("CONNECT FAILED", e);
			return "CONNECT FAILED";
		} catch (IOException e) {
			log.error("CONNECT FAILED", e);
			return "CONNECT FAILED";
		}
	}
	
	
	public String connect(String ip,String enablePassword) throws AnaException {
		try {
			tc.setConnectTimeout(ANAConstants.DEFAULT_TIMEOUT);
			tc.connect(ip, ANAConstants.DEFAULT_TELNET_PORT);
			Thread reader = new Thread(this);
			tc.registerNotifHandler(this);
			reader.start();
			excuteCommand(enablePassword);
			end.set(false);
			return "SUCCESS";
		} catch (SocketException e) {
			log.error("CONNECT FAILED", e);
			return "CONNECT FAILED";
		} catch (IOException e) {
			log.error("CONNECT FAILED", e);
			return "CONNECT FAILED";
		}
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
		String response = doCommandChain(command);
		response = response.trim();
		end.set(false);
		log.info(response);
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
			//wait read inputStream 
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

	@Override
	public void run() {
		InputStream instr = tc.getInputStream();
		try {
			byte[] buff = new byte[2048];
			int ret_read = 0;
			do {
				ret_read = instr.read(buff);
				if (ret_read > 0) {
					responseStr.append(new String(buff, 0, ret_read));
					String response = responseStr.toString();
					if (response.endsWith(">")
							|| response.endsWith("Password: ")) {
						end.set(true);
					}
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			log.error("Exception while reading socket:" + e.getMessage());
		}

	}
}
