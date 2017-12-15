package com.calix.bseries.server.ana.net.socket;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAConstants;
import com.calix.bseries.server.ana.ANAService;
import com.calix.bseries.server.dbmodel.B6NewProxySettings;
import com.calix.ems.server.cache.CMSCacheManager;

/**
 * ClassName: ProxyTimer <br/>
 * Function: this class to start service for heart beat with proxy server <br/>
 * date: 13 Sep, 2016 <br/>
 * 
 * @author Zheng Li
 * @version
 * @since JDK 1.6
 */
public class ProxyTimer extends TimerTask {
	private static final Logger log = Logger.getLogger(ProxyTimer.class);

	public ProxyTimer() {
		Timer timer = new Timer();
		timer.schedule(this, 1000*600, 1000*60);
	}

	@Override
	public void run() {
		B6NewProxySettings setting = ANAService.getB6NewProxySettings();
		if (setting != null) {
			String serverA = setting.getProxyServerA();
			String serverB = setting.getProxyServerB();
			String portA = setting.getPortA();
			String portB = setting.getPortB();
			String oldStatusA = setting.getStatusA();
			String oldStatusB = setting.getStatusB();
			
			if(null !=serverA&&null != portA&&serverA.trim().length()>0&&portA.trim().length()>0 ){
				ANATcpSocketClient clientA = new ANATcpSocketClient(serverA,
						Integer.valueOf(portA).intValue(), false);
				boolean connectAStatus = clientA.connect();
				if (!connectAStatus) {
					setting.setStatusA(ANAConstants.CONNECT_PROXYA_FAILED);
					log.info("connect serverA:" + serverA + ":" + portA + " failed");
				} else {
					setting.setStatusA(ANAConstants.CONNECT_PROXYA_SUCCESS);
					log.info("connect serverA:" + serverA + ":" + portA + " success");
				}
				clientA.disconnect();
			}else{
				log.info("serverA and portA is null ");
			}
		

			if(null !=serverB&&null != portB&&serverB.trim().length()>0&&portB.trim().length()>0){
				ANATcpSocketClient clientB = new ANATcpSocketClient(serverB,
						Integer.valueOf(portB).intValue(), false);

				boolean connectBStatus = clientB.connect();
				if (!connectBStatus) {
					setting.setStatusB(ANAConstants.CONNECT_PROXYB_FAILED);
					log.info("connect serverB:" + serverB + ":" + portB + " failed");
				} else {
					setting.setStatusB(ANAConstants.CONNECT_PROXYB_SUCCESS);
					log.info("connect serverB:" + serverB + ":" + portB + " success");
				}
				clientB.disconnect();
				if(oldStatusA.equals(setting.getStatusA())&&oldStatusB.equals(setting.getStatusB())){
					return;
				}
				ANAService.updateSyncStatus(setting);
			}else{
				log.info("serverB and portB is null");
			}
			
		}
	}

}
