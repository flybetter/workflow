package com.calix.bseries.server.boot;

import com.calix.bseries.server.task.*;
import com.calix.ems.core.signal.InterProcessSignal;
import com.calix.ems.server.process.CMSProcess;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/5/11
 * Time: 5:26 PM
 *
 * B6 device manager main class
 */
public class BseriesDeviceMgr extends CMSProcess {
    private static final String LOG_CONFIG_FILE = "lib/config/log4j-bseries.xml";
    private static final Logger logger = Logger.getLogger(BseriesDeviceMgr.class);
    private static final String PROCESS_NAME = "BSERIES_DEVICE_MGR";
    private static final int MAX_POOL_SIZE = 10;
    private ExecutorService taskExecutor;

    protected BseriesDeviceMgr() {
        super();
        taskExecutor = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        sendProcessStartedSucessSignal();
        logger.debug("BSeries device manager started");
    }

    @Override
    public short getIpcId() {
        return CMSProcess.BSERIES_MGR_ID;
    }

    @Override
    protected String getProcessName() {
        return PROCESS_NAME;
    }

    public static void main(String [] args) {
        new BseriesDeviceMgr();
    }

    protected void initializeLogging() {
        DOMConfigurator.configure(LOG_CONFIG_FILE);
    }

    @Override
    public void onMessage(InterProcessSignal signal) {
        AbstractBSeriesTask task = null;
        switch (signal.getType()) {
            case BSeriesTaskSignal.SIG_TYPE_B6_TRAP_REG_REQ:
                task = new B6TrapRegTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_AE_TRAP_REG_REQ:
                task = new AeTrapRegTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_DISCOVERY_REQ:
                task = new BSeriesDiscoveryTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_LINK_DISCOVERY_REQ:
                task = new B6LinkDiscoveryTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_MEDIATION_REQ:
                task = new BSeriesMediationRequestTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_BACKUP_REQ:
            	task = new B6NetworkBackupTask((BSeriesTaskSignal) signal);
                break;
            //James
            case BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_UPGRADE_REQ:
                task = new B6NetworkUpgradeTask((BSeriesTaskSignal) signal);
                break;
            //~James
            case BSeriesTaskSignal.SIG_TYPE_AE_DISCOVERY_REQ:
                task = new AeDiscoveryTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_GPON_ONT_DISCOVERY_REQ:
                task = new BseriesGponOntDiscoveryTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_CHASSIS_DISCOVERY_REQ:
                task = new B6ChassisDiscoveryTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_RELOAD_REQ:
                task = new B6ReloadTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_GPON_DEVICE_UPGRADE_REQ:
                task = new B6NetworkGponUpgradeTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_REPOSITORY_PATH_CONFIG_REQ:
                task = new B6NetworkRepositortPathConfigTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_AE_ASSOCIATE_DISCOVERY_REQ:
                task = new B6AeAssociationConfigTask((BSeriesTaskSignal) signal);
                break;
            case BSeriesTaskSignal.SIG_TYPE_B6_ESA_CONFIGURATION_REQ:
                task = new B6HandleESAConfigTask((BSeriesTaskSignal) signal);
                break;
	default:
                super.onMessage(signal);
        }

        try {
            if(task != null) {
                taskExecutor.execute(task);
            }
        } catch (Throwable th) {
            logger.error("Error handling signal. signal type: " + signal.getType(), th);
        }
    }
}
