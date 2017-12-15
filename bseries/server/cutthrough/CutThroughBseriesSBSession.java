package com.calix.bseries.server.cutthrough;

import com.calix.ems.server.process.cutthrough.CutThroughSBSession;
import com.calix.system.common.protocol.telnet.TelnetNioClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class CutThroughBseriesSBSession extends CutThroughSBSession{

    private static Logger logger = Logger.getLogger(CutThroughBseriesSBSession.class);
    private TelnetNioClient telnetClient = null;
	
	public CutThroughBseriesSBSession(SocketChannel channel) {
		super(channel);
		telnetClient = new TelnetNioClient(channel);
		registerTelnetControlCommand();
		telnetClient.setUserPrompt(":");
        telnetClient.setPasswdPrompt(":");
        telnetClient.setPrompt(">");
	}
	
	private void registerTelnetControlCommand(){
	    //Echo
	    telnetClient.registerControlData(TelnetNioClient.DO_ECHO, TelnetNioClient.WILL_ECHO );
	    
	    //Suppress go ahead
        telnetClient.registerControlData(TelnetNioClient.WILL_SUPRESS_GO_AHEAD, TelnetNioClient.DO_SUPRESS_GO_AHEAD);
        
        //Terminal type
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte) 24}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WILL,(byte) 24});
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.SB, (byte)24, TelnetNioClient.ECHO, TelnetNioClient.IAC, TelnetNioClient.SE}, 
                new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.SB,(byte) 24, TelnetNioClient.NOP, (byte) 0X56,(byte) 0X54, (byte) 0X31,(byte) 0X30,(byte) 0X30,(byte) 0Xff,(byte) 0Xf0});
        
        //Terminal speed
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)32}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 32});
        
        //X display location
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)35}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 35});
        
        //New Environment Option
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)39}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 39});
       
        //Environment Option
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)36}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 36});
        
        //Linemode 
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)0X22}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 0X22});
        
        //Status
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.WILL, (byte)5}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.DONT,(byte) 5});
        
        //Remote Flow Control
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)0X21}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 0X21});
        
        //Unknown so far, but important
        telnetClient.registerControlData(new byte[]{TelnetNioClient.IAC, TelnetNioClient.DO, (byte)31}, new byte[ ]{TelnetNioClient.IAC, TelnetNioClient.WONT,(byte) 31});
	}

	@Override
	protected boolean connect(String ip, int port,
			String userName, byte[] pwd) {
		boolean connected = false;
		// Though the method name is "isSSHEnabled", what it really does is just
		// test if the socket can be established to the given ip:port.
		if (isSSHEnabled(ip, port)) {
			// Use insecure connection if it is enabled on B6.
			connected = connectInsecurely(ip, port, userName, pwd);
		} else {
			// If insecure connection is disabled on B6, try port forwarding over SSH.
			connected = connectSecurely(ip, port, userName, pwd);
		}
		return connected;
	}

	/**
	 * Establish SSH connection with given username and password by SBCM, 
	 * and enable port forwarding from a random local port to the given 
	 * remote port. So in CT the socket channel will be established to that 
	 * random local port listened by SBCM. 
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param pwd
	 * @return
	 */
	protected boolean connectSecurely(String ip, int port, String userName, byte[] pwd) {
		// The socket channel will be connected in super.connectSecure() method. No need
		// to explicitly connect the telnetClient again.
		setSecure();
		return this.connectSecure(ip, port, userName, pwd);
	}

	/**
	 * Establish telnet connection with given parameters.
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param pwd
	 * @return
	 */
	protected boolean connectInsecurely(String ip, int port, String userName, byte[] pwd) {
		boolean connected = false;
        
		logger.info("Parameters for Bseries connection: IP=[" + ip + "],PORT=[" + port + "]");
		try {
			connected = telnetClient.connect(ip, port);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return connected;
	}

	@Override
    protected boolean login(String networkName, int networkPort, String userName, byte[] pwd){
        logger.info("Start to login Bseries ...");
//        logger.info("username:" + userName + ", pwd: " + new String(pwd));
        return telnetClient.login(userName, pwd);
	}
    
    	
	@Override
	public void close(){
		logger.debug("Close TelnetClient for Bseries Session.");
		super.close();
		telnetClient.close();
	}
	
	
	public static void main(String[] args) throws IOException{
	    CutThroughBseriesSBSession cutThroughBseriesSBSession = new CutThroughBseriesSBSession(SocketChannel.open());
	    System.out.println(cutThroughBseriesSBSession.connect("10.11.42.23", 22, null, null));
	    System.out.println(cutThroughBseriesSBSession.login("", 0, "cli", "frpocc".getBytes()));
	    cutThroughBseriesSBSession.close();
	}
}
