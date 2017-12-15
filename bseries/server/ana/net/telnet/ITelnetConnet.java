package com.calix.bseries.server.ana.net.telnet;

import com.calix.bseries.server.ana.common.error.AnaException;


/**
 * 
 * @author tben
 *
 */
public interface ITelnetConnet {
	/**
	 * initial device and connect to device
	 * @param po
	 * @return
	 */
	boolean connect(String ip) throws AnaException;
	/**
	 * excute command via telnet protocol
	 * @param command
	 * @return result
	 */
	String excuteCommand(String command) throws AnaException;
	boolean executeCommand(String command);
	String executeCommandWithoutLog(String command);
	boolean disconnect();
	
	boolean connectWithPassword(String ip,String enablepassword,String clipassword) throws AnaException;
	
}
