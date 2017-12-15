package com.calix.bseries.server.ana.net;

import com.calix.bseries.server.ana.common.error.AnaException;


/**
 * 
 * @author zheng
 *
 */
public interface IB6Connet {
	/**
	 * excute command via SSH/Telnt protocol
	 * @param command
	 * @return result
	 */
	String excuteCommand(String command) throws AnaException;
	boolean executeCommand(String command);
	String executeCommandWithoutLog(String command);
	boolean disconnect();
	
	boolean connectWithPassword(String ip,String enablepassword,String clipassword) throws AnaException;
	
}
