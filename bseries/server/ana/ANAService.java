package com.calix.bseries.server.ana;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.calix.bseries.server.ana.net.socket.ANATcpSocketClient;
import com.calix.bseries.server.ana.net.socket.ANATcpSocketService;
import com.calix.bseries.server.ana.net.socket.ProxyTimer;
import com.calix.bseries.server.ana.process.ANAExecuteLogArchiveJob;
import com.calix.bseries.server.dbmodel.B6NewProxySettings;
import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.system.server.dbmodel.CMSObject;

/**
 * ClassName: ANAService <br/>
 * Function: this class to start service for ANA project <br/>
 * date: 13 Sep, 2016 <br/>
 * 
 * @author Tony Ben
 * @version
 * @since JDK 1.6
 */
public final class ANAService {
	private static final Logger log = Logger.getLogger(ANAService.class);
	private static final int DEFAULT_LOG_ARCHIVE_DAY = 30;
	private static final int DEFAULT_SOCKET_PROTOCOL = 0;// 0:TCP 1:SSL
	private static final int DEFAULT_SOCKET_PORT = 9002;// TCP 9002 SSL 9003
	private static ExecutorService cachedThreadPool = Executors
			.newCachedThreadPool();
	private static ANATcpSocketService service;
	private static int socketPort = DEFAULT_SOCKET_PORT;
	private static int socketProtocol = DEFAULT_SOCKET_PROTOCOL;

	/**
	 * Function:startService<br/>
	 * Conditions:None<br/>
	 * WorkFlow:TODO<br/>
	 * UserGuide:TODO<br/>
	 * Remark:TODO<br/>
	 * 
	 * @author Tony Ben
	 * @since JDK 1.6
	 */
	public static void startService() {
		// get configure for ANA

		B6Settings b6setting = getB6Setting();
		// StartSocketService
		startANASocketService(b6setting.getAnaSocketProtocol(),
				b6setting.getAnaSocketPort());
		// start delete service
		startLogArchiveJob();
		// CMS send connect info to proxy server every minutes
		new ProxyTimer();
		b6setting = null;
	}

	public static void restartSocketService(B6Settings b6setting) {
		if (b6setting.getAnaSocketPort() != socketPort
				|| b6setting.getAnaSocketProtocol() != socketProtocol) {
			log.info("[ANA][restart socket service] orignal port:" + socketPort
					+ " orignal protocol:" + socketProtocol + " new port:"
					+ b6setting.getAnaSocketPort() + " new protocol:"
					+ b6setting.getAnaSocketProtocol());
			stopSocketService();
			startANASocketService(b6setting.getAnaSocketProtocol(),
					b6setting.getAnaSocketPort());
		}
	}

	private static void stopSocketService() {
		log.info("[ANA] stop socket service start");
		if (service != null) {
			service.stopService();
		}
		service = null;
		log.info("[ANA] stop socket service successful");
	}

	private static B6Settings getB6Setting() {
		B6Settings b6setting = null;
		ICMSDatabase database = null;
		DbTransaction txn = null;
		try {
			database = DatabaseManager.getCMSDatabase();
			txn = database.getReadTransaction();
			txn.begin();
			b6setting = (B6Settings) database.readSingletonCMSObject(
					B6Settings.TYPE_NAME, txn);
		} catch (Exception e) {
			log.error("get B6 setting error", e);
		} finally {
			if (txn != null && txn.isActive())
				txn.abort();
		}
		// reset b6setting
		if (b6setting == null) {
			b6setting = new B6Settings();
			b6setting.setDeleteTempLogForOld(DEFAULT_LOG_ARCHIVE_DAY);
			b6setting.setAnaSocketProtocol(DEFAULT_SOCKET_PROTOCOL);
			b6setting.setAnaSocketPort(DEFAULT_SOCKET_PORT);
			// b6setting.setIsProxyServer(ANAConstants.INT_FALSE);
		}
		// reset archive days
		if (b6setting.getDeleteTempLogForOld() == null) {
			b6setting.setDeleteTempLogForOld(DEFAULT_LOG_ARCHIVE_DAY);
		}
		// reset socket protocol
		if (b6setting.getAnaSocketProtocol() == null) {
			b6setting.setAnaSocketProtocol(DEFAULT_SOCKET_PROTOCOL);
		}
		// reset socket port
		if (b6setting.getAnaSocketPort() == null) {
			b6setting.setAnaSocketPort(DEFAULT_SOCKET_PORT);
		}
		return b6setting;
	}

	private static void startLogArchiveJob() {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;
		try {
			sched = sf.getScheduler();
			JobDetail job = new JobDetail("ANAExecuteLogArchiveJOB",
					"ANAExecuteLogArchiveGroup", ANAExecuteLogArchiveJob.class);
			CronTrigger trigger = new CronTrigger(
					"ANAExecuteLogArchiveTrigger", "ANAExecuteLogArchiveGroup",
					"ANAExecuteLogArchiveJOB", "ANAExecuteLogArchiveGroup",
					"0 0 1 * * ?");
			sched.addJob(job, true);
			sched.scheduleJob(trigger);
			sched.start();
		} catch (SchedulerException e) {
			log.error("[ANA] start archive job error", e);
		} catch (ParseException e) {
			log.error("[ANA] start archive job error", e);
		}
	}

	/**
	 * Function:startANASocketService<br/>
	 * 
	 * @author Tony Ben
	 * @since JDK 1.6
	 */
	private static void startANASocketService(int protocol, int port) {
		log.info("[ANA] start socket service protocol:" + protocol + " port:"
				+ port);
		socketPort = port;
		socketProtocol = protocol;
		boolean ssl = protocol == 0 ? false : true;
		service = new ANATcpSocketService(ssl, socketPort);
		cachedThreadPool.execute(service);
	}
	
	/**
	 * Function:syncToProxy<br/>
	 * 
	 * @author Tony Ben
	 * @since JDK 1.6
	 */
	public static void syncToProxy(B6NewProxySettings setting) {
		if (setting == null) {
			setting = getB6NewProxySettings();
		}
		if (setting == null) {
			log.info("[ANA] syncToProxy: there is no B6NewProxySettings data for sync");
			return;
		}
		String localIp = "";
		B6Settings b6Settings = getB6Setting();
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error("getLocalHost Ip failed,error:" + e.getMessage());
		}
		String CMSRegisterInfo = "CMS:" + localIp + ";protocol:"
				+ b6Settings.getAnaSocketProtocol() + ";port:"
				+ b6Settings.getAnaSocketPort();

		String serverA = setting.getProxyServerA();
		String serverB = setting.getProxyServerB();
		String portA = setting.getPortA();
		String portB = setting.getPortB();

		log.info("[ANA] start syncToProxy serverA:" + serverA + " portA:"
				+ portA + ";serverB:" + serverB + " portB:" + portB);

		if(null!=serverA && serverA.trim().length()>0 && null!=portA && portA.trim().length()>0){
			ANATcpSocketClient clientA = new ANATcpSocketClient(serverA, Integer
					.valueOf(portA).intValue(), false);

			boolean connectAStatus = clientA.connect();
			if (!connectAStatus) {
				setting.setStatusA("connect serverA failed");
				log.info("connect serverA:" + serverA + ":" + portA + " failed");
			} else {
				setting.setStatusA("connection of proxyA works");
				clientA.send(CMSRegisterInfo);
				log.info("send registerInfo to serverA:" + CMSRegisterInfo);
			}
			clientA.disconnect();
		}else{
			log.info("serverA not allowable");
			setting.setStatusA("");
		}

		if(null!=serverB && serverB.trim().length()>0  && null!=portB && portB.trim().length()>0){
			ANATcpSocketClient clientB = new ANATcpSocketClient(serverB, Integer
					.valueOf(portB).intValue(), false);

			boolean connectBStatus = clientB.connect();
			if (!connectBStatus) {
				setting.setStatusB("connect serverB failed");
				log.info("connect serverB:" + serverB + ":" + portB + " failed");
			} else {
				setting.setStatusB("connection of proxyB works");
				clientB.send(CMSRegisterInfo);
				log.info("send registerInfo to serverB:" + CMSRegisterInfo);
			}
			clientB.disconnect();
		}else{
			log.info("serverB not allowable");
			setting.setStatusB("");
		}
		updateSyncStatus(setting);
	}

	public static B6NewProxySettings getB6NewProxySettings() {
		B6NewProxySettings settings = null;
		ICMSDatabase database = null;
		DbTransaction txn = null;
		try {
			database = DatabaseManager.getCMSDatabase();
			txn = database.getReadTransaction();
			txn.begin();
			settings = (B6NewProxySettings) database.readSingletonCMSObject(
					B6NewProxySettings.TYPE_NAME, txn);
		} catch (Exception e) {
			log.error("get B6 proxy setting error", e);
		} finally {
			if ((txn != null) && (txn.isActive()))
				txn.abort();
		}
		return settings;
	}
	
	public static void updateSyncStatus(B6NewProxySettings settings) {
		ICMSDatabase database = null;
		DbTransaction txn = null;
		try {
			database = DatabaseManager.getCMSDatabase();
			txn = database.getTransaction("ems", DbTransaction.READ);
			txn.begin();
			//use dbUpdateANA instead of dbUpdate, otherwise it will cause infinite loops.
			settings.dbUpdateANA(txn);
			txn.commit();
			txn = null;
			log.info("update B6 proxy setting data in database successfully");
		} catch (Exception ex) {
			log.error("Failed in load B6 proxy setting data: ", ex);
		} finally {
			if (txn != null && txn.isActive()) {
				txn.abort();
			}
		}
	}


	/**
	 * Function:stopService<br/>
	 * Conditions:TODO<br/>
	 * WorkFlow:TODO<br/>
	 * UserGuide:TODO<br/>
	 * Remark:TODO<br/>
	 * 
	 * @author Tony Ben
	 * @since JDK 1.6
	 */
	public void stopService() {
		// stop socket service
		stopSocketService();
		// stop archive service
	}

}
