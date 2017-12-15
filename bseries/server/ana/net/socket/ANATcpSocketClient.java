/** 
 * Project Name:socket_netty 
 * File Name:ANATcpSocketClient.java 
 * Package Name:com.calix.bseries.server.ana.net.socket 
 * Date:29 Nov, 2016
 * Copyright (c) 2016, Calix All Rights Reserved. 
 * 
 */
package com.calix.bseries.server.ana.net.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAService;

/**
 * ClassName:ANATcpSocketClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 29 Nov, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 * @see
 */
public class ANATcpSocketClient{
	//host ip or host name
	private String host;
	//socket port
	private int port;
	//whether is SSL security link
	private boolean isSSL = false;
	//IO send
	private PrintWriter out = null;
	//IO receive
	private BufferedReader in = null;
	//socket object
	private Socket cSocket = null;
	//socket timeout
	private int timeout = 5*1000 ;
	
	private static final Logger log = Logger.getLogger(ANAService.class);

	public ANATcpSocketClient(String host, int port, boolean ssl) {
		this.host = host;
		this.port = port;
		this.isSSL = ssl;
	}
	public ANATcpSocketClient(String host, int port, boolean ssl,int timeout) {
		this.host = host;
		this.port = port;
		this.isSSL = ssl;
		this.timeout = timeout;
	}

	public boolean isOnline(){
		return cSocket==null?false:true;
	}
	
	public boolean connect() {
		if(isOnline()){
			return true;
		}
		try {
			if (isSSL) {
				SSLContext context = SSLContext.getInstance("SSL");
				context.init(null,
						new TrustManager[] { new MyX509TrustManager() },
						new SecureRandom());
				SSLSocketFactory factory = context.getSocketFactory();
				cSocket = (SSLSocket) factory.createSocket(host, port);
				//TODO 
				//need to add timeout limit
			} else {
				 InetAddress addr = InetAddress.getByName( host ); 
				 cSocket = new Socket();  
				 cSocket.connect( new InetSocketAddress( addr, port ), timeout );  
				//cSocket = new Socket(host, port);
			}
			out = new PrintWriter(cSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					cSocket.getInputStream()));
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			cSocket=null;
			return false;
		}
		return true;
	}

	public String send(String request) {
		out.println(request + "\n.\n");
		out.flush();
		try {
			return readResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String readResponse() throws IOException {
		String line = null;
		StringBuffer response = new StringBuffer("");
		while ((line = in.readLine()) != null) {
			if (line != null && line.equals(".")) {
				break;
			}
			response.append(line);
		}
		return response.toString();
	}

	public boolean disconnect(){
		try {
			if(out!=null){
				out.close();	
				out=null;
			}
			if(in!=null){
				in.close();
				in=null;
			}
			if(cSocket!=null){
				cSocket.close();
				cSocket=null;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
