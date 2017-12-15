package com.calix.bseries.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;



public class PingUtility {
	private static Logger logger = Logger.getLogger(PingUtility.class);
	public PingUtility(){
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PingUtility.isDeviceReachable("10.245.7.17",3,30);
//		PingUtility.isDeviceReachableUsingJDK("10.245.7.26", 22,  3, 30);
//		PingUtility.isDeviceReachableUsingJDK("10.245.7.28", 22, 3, 30);
		System.out.println("start");
		boolean ping = PingUtility.isDeviceReachableUsingJDK("10.245.7.17", 22, 3, 10);
		System.out.println("finish: " + ping);
		
		System.out.println("start");
		ping = PingUtility.isDeviceReachableUsingJDK("10.245.7.28", 22, 3, 10);
		System.out.println("finish: " + ping);
	}

	
	/**
	 * @param args
	 * 1) first try ICMP, 
	 * 2) if retry >0; then try to connect it by socket. 
	 * - Do not use Runtime.exec, it will impact Jboss's performance
	 */
	public static boolean isDeviceReachableUsingJDK(String ipAddress, int port, int retries,int timeout){  
    	int m_retries = 0;
    	int m_timeout = 1;
    	int m_port = 22;
		if(retries > 0)
		{
        	m_retries = retries;
        }
		if (timeout > 1){
			m_timeout = timeout;
		}
		if(port > 0){
			m_port = port;
		}
        
        //First Try with ICMP Message
        boolean icmpreply=false;
        if (logger.isDebugEnabled())
            logger.debug("Sending ICMP ping  to the device " + ipAddress + " : " + m_port);
        try {
            icmpreply=InetAddress.getByName(ipAddress).isReachable(3000);
        } catch (IOException e) {
            logger.warn("Exception when ending ICMP ping  to the device " + ipAddress + " : " + m_port, e);
        }
        
        if(icmpreply){
        	return true;
        }else{
            if (logger.isDebugEnabled())
                logger.debug("isDeviceReachableUsingJDK: ipAddress: " + ipAddress + " : " 
                		+ m_port + ", m_retries: " + m_retries + ", m_timeout: " + m_timeout);
            //If ICMP is disabled or failed then try Socket connection
        	for (int i = 0; i < m_retries ;i++){
        		if (isReachableBySocket(ipAddress, m_port, m_timeout)){
        			logger.info("Ping successful for " + ipAddress);
        			return true;
        		}
        	}
        	logger.info("Ping failed for " + ipAddress);
        	return false;
        }
    }
	
    private static boolean isReachableBySocket(String ip, int sshPort, int m_timeout) {
        Socket soc = new Socket();
        if (logger.isDebugEnabled())
            logger.debug("ICMP ping failed trying socket to the device "+ ip + " : " + sshPort );
		try {
			soc.connect(new InetSocketAddress(ip, sshPort), m_timeout * 1000);
		} catch (IOException e) {
			return false;
		} catch (Exception e) {
			return false;
		}

		if (soc.isConnected()) {
			try {
				soc.close();
			} catch (Exception e) {
			}
			return true;
		}
        return false;
    }
	
	@Deprecated
	public static boolean isDeviceReachable(String ipAddress,int retries,int timeout){   	
    	int m_retries = 1;
    	int m_timeout = 1;
		if(retries > 1)
		{
        	m_retries = retries;
        }
		if (timeout > 1){
			m_timeout = timeout;
		}
    	String pingCmd = "/bin/ping -c " + 1 + " -w " + m_timeout + " " + ipAddress;
    	for (int i = 0; i < m_retries + 1;i++){
    		if (ping(pingCmd)){
    			logger.info("Ping successful for " + ipAddress);
    			return true;
    		}
    	}
    	logger.info("Ping failed for " + ipAddress);
    	return false;
    }
	
	private static boolean ping(String pingCmd){
		Process p = null;
		String pingResult = "";
    	try {
	    	Runtime r = Runtime.getRuntime();
	    	p = r.exec(pingCmd);
	    	
	    	BufferedReader in = null;
	    	try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					logger.info(inputLine);
					pingResult += inputLine;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("Error reading command " + e);
				return false;
			}finally{
				in.close();
			}
	    	try
	        {	        	
	            Thread.sleep(25000);
	            
	        }
	        catch(Exception exception) {
	        		        	
	        }
	    	
	    	logger.info(" Exit value " + p.exitValue());	     
	        if (p.exitValue() != 0 || pingResult.indexOf("64 bytes from") == -1)
	        	return false;

    	}//try
    	catch (IOException e) {
    		logger.info("Could not execute command" + e);
    		return false;
    	}finally
        {      
    		
            p.destroy();
        }
    	return true;
	}
	
}
