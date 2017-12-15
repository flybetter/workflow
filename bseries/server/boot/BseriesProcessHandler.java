package com.calix.bseries.server.boot;

import com.calix.ae.server.dbmodel.Ae_Ont;
import com.calix.ae.server.session.AeONTProxy;
import com.calix.bseries.server.task.AeResyncOcmManagedChildTask;
import com.calix.bseries.server.task.B6ESAConfigurationTask;
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.bseries.server.task.*;
import com.calix.bseries.server.util.MediationServerUtilProxyImpl;
import com.calix.ems.core.CoreUtilities;
import com.calix.ems.core.signal.InterProcessSignal;
import com.calix.ems.database.*;
import com.calix.ems.exception.SessionException;
import com.calix.system.server.boot.ProcessHandler;
import com.calix.system.server.dbmodel.CMSObject;
import com.calix.system.server.dbmodel.CalixConditionBase;
import com.calix.system.server.dbmodel.DeviceClassMap;
import com.calix.system.server.dbmodel.EMSAid;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.session.CalixONTProxyManager;
import com.calix.system.server.session.IONTProxy;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.occam.ems.common.util.OccamUtils;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/6/11
 * Time: 9:33 AM
 *
 * Handle BSeries task signals
 */
public class BseriesProcessHandler implements ProcessHandler {
    private static final Logger log = Logger.getLogger(BseriesProcessHandler.class);
//  private static final int MAX_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 25;
    private static final int MAX_POOL_SIZE_MEDIATION = 5;

    private ExecutorService taskExecutor;
    private ExecutorService taskExecutor_Mediation;
    
    public Map<String,BseriesBackupChildTask> taskMap;

    //James
    public Map<String,BSeriesUpgradeChildTask> taskUpgradeMap;
    //~James
    public Map<String,BSeriesGponUpgradeChildTask> tasGponkUpgradeMap;
    public Map<String,BSeriesRepositoryPathConfigChildTask> repositoryPathConfigMap;
    public Map<String,BSeriesGponUpgradeChildTask> tasGponUpgradeMap;
    public Map<String,B6ResyncInventoryChildTask> taskResyncMap;
    public Map<String,AeResyncOcmManagedChildTask> taskAeOntMap;
    public Map<String, B6ESAConfigurationTask> taskESAConfigMap;
    private static final String resyncIdNetworkNameDemarcator = "@@@";
    public Map<String,BSeriesReloadChildTask> taskReloadMap;
    
    public BseriesProcessHandler() {
        taskExecutor = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        log.warn("BseriesProcessHandler started with MAX_POOL_SIZE: " + MAX_POOL_SIZE);
        
        taskExecutor_Mediation = Executors.newFixedThreadPool(MAX_POOL_SIZE_MEDIATION);
        log.warn("BseriesProcessHandler started mediation threadPool with MAX_POOL_SIZE: " + MAX_POOL_SIZE_MEDIATION);
        
        
        
        taskMap = new ConcurrentHashMap<String,BseriesBackupChildTask>();
        //James
        taskUpgradeMap = new ConcurrentHashMap<String,BSeriesUpgradeChildTask>();
        //~James
        tasGponUpgradeMap = new ConcurrentHashMap<String,BSeriesGponUpgradeChildTask>();
        tasGponkUpgradeMap = new ConcurrentHashMap<String,BSeriesGponUpgradeChildTask>();
        repositoryPathConfigMap = new ConcurrentHashMap<String,BSeriesRepositoryPathConfigChildTask>();
        
        taskAeOntMap = new ConcurrentHashMap<String,AeResyncOcmManagedChildTask>();
        taskESAConfigMap = new ConcurrentHashMap<String,B6ESAConfigurationTask>();
       

        taskResyncMap = new ConcurrentHashMap<String,B6ResyncInventoryChildTask>();
	    taskReloadMap = new ConcurrentHashMap<String,BSeriesReloadChildTask>();  
  }
    public BseriesBackupChildTask getTaskFromMap(String id,String networkName){
    	return taskMap.get(id + networkName);
    }
    
    public BseriesBackupChildTask addTaskToMap(String id,String networkName, BseriesBackupChildTask task){
    	return taskMap.put(id+networkName, task);
    }
    
    public AeResyncOcmManagedChildTask addTaskToMap(String id,String networkName, AeResyncOcmManagedChildTask task){
    	return taskAeOntMap.put(id + resyncIdNetworkNameDemarcator + networkName, task);
    }
    
    public B6ESAConfigurationTask addTaskToMap(String id,String taskName, B6ESAConfigurationTask task){
    	return taskESAConfigMap.put(id + resyncIdNetworkNameDemarcator + taskName, task);
    }

    public B6ResyncInventoryChildTask addTaskToMap(String id,String networkName, B6ResyncInventoryChildTask task){
    	return taskResyncMap.put(id + resyncIdNetworkNameDemarcator + networkName, task);
    }

    //James
    /**
     * addTaskToMap
     * @param id
     * @param networkName
     * @param task BSeriesUpgradeChildTask
     * @return
     */
    public BSeriesUpgradeChildTask addUpgradeTaskToMap(String id,String networkName, BSeriesUpgradeChildTask task){
        return taskUpgradeMap.put(id+networkName, task);
    }
    
    /**
     * addTaskToMap
     * @param id
     * @param networkName
     * @param task BSeriesGponUpgradeChildTask
     * @return
     */
    public BSeriesUpgradeChildTask getUpgradeTaskFromMap(String id,String networkName){
        return taskUpgradeMap.get(id + networkName);
    }
    //~James
    public BSeriesGponUpgradeChildTask addGponUpgradeTaskToMap(String id,String networkName, BSeriesGponUpgradeChildTask task){
    	log.debug("addGponUpgradeTaskToMap : String id : "+ id);
    	log.debug("addGponUpgradeTaskToMap : String networkName : "+ networkName);
    	//log.debug("addGponUpgradeTaskToMap : BSeriesGponUpgradeChildTask task : "+ task);
    	//log.debug("addGponUpgradeTaskToMap : tasGponkUpgradeMap : "+ tasGponkUpgradeMap);
    	
        return tasGponUpgradeMap.put(id+networkName, task);
    }
    
    /**
     * 
     * @param id
     * @param networkName
     * @return
     */
    public BSeriesGponUpgradeChildTask getGponUpgradeTaskFromMap(String id,String networkName){
        return tasGponUpgradeMap.get(id + networkName);
    }
    
    public BSeriesRepositoryPathConfigChildTask addRepositoryPathConfigTaskToMap(String id,String networkName, BSeriesRepositoryPathConfigChildTask task){
    	log.debug("addGponUpgradeTaskToMap : String id : "+ id);
    	log.debug("addGponUpgradeTaskToMap : String networkName : "+ networkName);
    	//log.debug("addGponUpgradeTaskToMap : BSeriesGponUpgradeChildTask task : "+ task);
    	//log.debug("addGponUpgradeTaskToMap : tasGponkUpgradeMap : "+ tasGponkUpgradeMap);
    	
        return repositoryPathConfigMap.put(id+networkName, task);
    }

    /**
     * 
     * @param id
     * @param networkName
     * @return
     */
    public BSeriesRepositoryPathConfigChildTask getRepositoryPathTaskFromMap(String id,String networkName){
        return repositoryPathConfigMap.get(id + networkName);
    }
    
    /**
     * 
     * @param id
     * @param networkName
     * @return
     */

    @Override
    public int getDeviceType() {
        return DeviceClassMap.DEVICE_MODULE_BSERIES;
    }

    @Override
    public void auditProcesses() {
    }

    @Override
    public boolean handleIpcSignals(final InterProcessSignal signal) {
        switch (signal.getType()) {
            case BSeriesTaskSignal.SIG_TYPE_B6_TRAP_REG_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_TRAP_REG_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BseriesDeviceSBSession sb = getSouthboundSession((BSeriesTaskSignal) signal);
                            sb.onTrapRegResponse( (BSeriesTaskSignal) signal);
                        } catch (Exception ex) {
                            log.warn("Error processing B6 discovery response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_AE_TRAP_REG_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_AE_TRAP_REG_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleAeTrapRegResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_DISCOVERY_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_DISCOVERY_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BseriesDeviceSBSession sb = getSouthboundSession((BSeriesTaskSignal) signal);
                            sb.onDiscoveryResponse((BSeriesTaskSignal) signal);
                        } catch (Exception ex) {
                            log.warn("Error processing B6 discovery response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_LINK_DISCOVERY_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_LINK_DISCOVERY_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BseriesDeviceSBSession sb = getSouthboundSession((BSeriesTaskSignal) signal);
                            sb.onLinkDiscoveryResponse((BSeriesTaskSignal) signal);
                            markResyncCompleteIfApplicable((BSeriesTaskSignal) signal);
                        } catch (Exception ex) {
                            log.warn("Error processing B6 link response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                       // handleB6LinkDiscoveryResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_CHASSIS_DISCOVERY_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_CHASSIS_DISCOVERY_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BseriesDeviceSBSession sb = getSouthboundSession((BSeriesTaskSignal) signal);
                            sb.onChassisDiscoveryResponse((BSeriesTaskSignal) signal);
                            
                        } catch (Exception ex) {
                            log.warn("Error processing B6 discovery response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                       
                    }
                });
                return true;

            case BSeriesTaskSignal.SIG_TYPE_B6_MEDIATION_RESP:
            	taskExecutor_Mediation.execute(new Runnable() {
                    @Override
                    public void run() {
                        MediationServerUtilProxyImpl.getInstance().handleMediationResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_BACKUP_RESP:
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleB6NetworkBackupResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            //James
            case BSeriesTaskSignal.SIG_TYPE_B6_DEVICE_UPGRADE_RESP:
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleB6NetworkUpgradeResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            //~James
            case BSeriesTaskSignal.SIG_TYPE_AE_DISCOVERY_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_AE_DISCOVERY_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleAeDiscoveryTaskResponse((BSeriesTaskSignal) signal);
                        markAeResyncCompleteIfApplicable((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_GPON_ONT_DISCOVERY_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_GPON_ONT_DISCOVERY_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BseriesDeviceSBSession sb = getSouthboundSession((BSeriesTaskSignal) signal);
                            sb.onGponOntDiscoveryResponse((BSeriesMediationSignal) signal);
                        } catch (Exception ex) {
                            log.warn("Error processing B6 GPON ONT discovery response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_RELOAD_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_RELOAD_RESP signal");
            	}
            	taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleB6NetworkReloadResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_GPON_DEVICE_UPGRADE_RESP:
            	log.debug("Receive SIG_TYPE_B6_REPOSITORY_PATH_CONFIG_RESP signal");
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                    	handleB6NetworkGponUpgradeResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_REPOSITORY_PATH_CONFIG_RESP:
            	log.debug("Receive SIG_TYPE_B6_REPOSITORY_PATH_CONFIG_RESP signal");
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                    	handleB6RepositoryConfigPathResponse((BSeriesTaskSignal) signal);
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_AE_ASSOCIATE_DISCOVERY_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_AE_ASSOCIATE_DISCOVERY_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BseriesDeviceSBSession sb = getSouthboundSession((BSeriesTaskSignal) signal);
                            sb.onAeOntAssociationDiscoveryResponse((BSeriesTaskSignal) signal);
                        } catch (Exception ex) {
                            log.warn("Error processing B6 ae ont association response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                    }
                });
                return true;
            case BSeriesTaskSignal.SIG_TYPE_B6_ESA_CONFIGURATION_RESP:
            	if( log.isDebugEnabled() ){
            		log.debug("Receive SIG_TYPE_B6_ESA_CONFIGURATION_RESP signal");
            	}
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        	markESAConfigCompleteIfApplicable((BseriesESAConfigRespSignal)signal);
                        } catch (Exception ex) {
                            log.warn("Error processing B6 ae ont association response signal. Network: " + ((BSeriesTaskSignal)signal).getNetworkName() , ex);
                        }
                    }
                });
                return true;
        }
        log.warn("Receive Unknow signal, signal type: " + signal.getType());
        return false;
    }

    @Override
    public Map<String, Long> getHeartBeatStatus() {
        return null;
    }

    private void handleAeTrapRegResponse(BSeriesTaskSignal signal) {
    	C7Database db =	C7Database.getInstance();
        //todo
    	if(signal.getError() == null) {
            String ip = signal.getIpAddress();
            log.info("begin to handle Ae register response...");
            try {
                //use sql to update ae_ont table to avoid package dependency
                StringBuffer sqlBuf = new StringBuffer();
                sqlBuf.append("update ae_ont set ");
                sqlBuf.append("MgmtStatus=").append(3);//change status to Registered
                sqlBuf.append(" where ipaddress='").append(ip).append("'");

                db.beginTransaction();
                db.nativeSql(sqlBuf.toString());
                db.commitTransaction();
                log.info("successfully handle Ae register response.");
            } catch (Exception ex) {
                log.warn("Unable to update AE ONT: " + ip, ex);
                db.rollbackTransaction();
            } finally {
                db.close();
            }
            String  ontaid = getAeontID(db, ip);
            if(null!= ontaid){
            	//CMS-10471, change the device id from 8 to 41
            	CoreUtilities.clearEMSAlarm(new EMSAid(ontaid), CMSObject.getAliasValue("_CMSCondTypeTrapRegFailed_"), CalixConditionBase.MAJOR,  
                		41, 0x0593, ontaid);	
            }
            
            log.info("successfully handle Ae register response.");
            //clear alarm.
        } else {
            log.warn("Fail in handling Ae register response. IP: " + signal.getIpAddress() + ". Error: " + signal.getError());
            String  ontaid = getAeontID(db, signal.getIpAddress());
            if(null!= ontaid){
            	CoreUtilities.raiseEMSAlarm(new EMSAid(ontaid), CalixConditionBase.MAJOR, CMSObject.getAliasValue("_CMSCondTypeTrapRegFailed_"), 
                		ontaid, "Failed to register trap receiver ip address to the network", DeviceClassMap.DEVICE_MODULE_AE);
            }
            // generate alarm.
        }
    }
	private String getAeontID(C7Database db, String ip) {
		String ontaid=null;
		try {
		    //use sql to update ae_ont table to avoid package dependency
		    StringBuffer sqlBuf = new StringBuffer();
		    sqlBuf.append("select aeontid from ae_ont");
		    sqlBuf.append(" where ipaddress='").append(ip).append("'");

		    db.beginTransaction();
		    ResultSet rs =db.executeQuery(sqlBuf.toString());
		    db.commitTransaction();
		    
		    while (rs.next()) {
		    	ontaid = rs.getString(1);
		        break;
		    }
		    DBUtil.closeQuietly(rs);
		    
		    
		} catch (Exception ex) {
		    log.warn("Unable to update AE ONT: " + ip, ex);
		} finally {
		    db.close();
		}
		return ontaid;
	}

    private void handleB6LinkDiscoveryResponse(BSeriesTaskSignal signal) {
        //todo
    }
  
    /**
     * addTaskToMap
     * @param id
     * @param networkName
     * @param task BSeriesReloadChildTask
     * @return
     */
    public BSeriesReloadChildTask addReloadTaskToMap(String id,String networkName, BSeriesReloadChildTask task){
        return taskReloadMap.put(id+networkName, task);
    }
    
    /**
     * 
     * @param id
     * @param networkName
     * @return
     */
    public BSeriesReloadChildTask getReloadTaskFromMap(String id,String networkName){
        return taskReloadMap.get(id + networkName);
    }
    //~James
    private void handleB6NetworkBackupResponse(BSeriesTaskSignal signal) {
    	BseriesBackupChildTask task = getTaskFromMap(((BSeriesNetworkBackupResponseSignal)signal).getJobID(),signal.getNetworkName());
		if(task==null){
			log.error("Can't get B6 network backup task from the Map. JobID is :"+ ((BSeriesNetworkBackupResponseSignal)signal).getJobID());
			return;
		}
        try {
			if(null==signal.getError()|| "".equals(signal.getError())){
				task.updateNetworksSucceedStatus(signal.getNetworkName());
			}else{
				task.updateNetworksFailureStatus(signal.getNetworkName(), signal.getError());
			}
		} catch (Exception e) {
			log.error("can't process B6 network backup response.");
			task.updateNetworksFailureStatus(signal.getNetworkName(), signal.getError());
		} finally{
		    //Begin bug 51441 fix by James Wang 20111213  
            try {
                // task.taskComplete(signal.getNetworkName());
                // taskMap.remove(((BSeriesNetworkBackupResponseSignal)signal).getJobID()+signal.getNetworkName());
                // task.notify();

                // Remove data in map, in case it is replaced by a new one
                taskMap.remove(((BSeriesNetworkBackupResponseSignal) signal).getJobID() + signal.getNetworkName());
                // Finish task
                task.taskComplete(signal.getNetworkName());
                // Notify thread to move on, it's OK, for parent's status is set.
                synchronized (task) {
                    try {
                        // In case of "com.sun.jdi.InvocationException"
                        task.notify();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        // e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                log.error("can't change task status for B6 network backup.");
            }
            // End bug 51441 fix by James Wang 20111213
		}
    }
    private void handleB6NetworkReloadResponse(BSeriesTaskSignal signal) {
    	String networkName = signal.getNetworkName();
        for(String reloadMapKey : taskReloadMap.keySet()){
            if(networkName != null && reloadMapKey != null && reloadMapKey.endsWith(networkName)){
                try {
                	BSeriesReloadChildTask reloadChildTask = taskReloadMap.get(reloadMapKey);
                    if(signal.getError() == null) {
                    	reloadChildTask.updateNetworksSucceedStatus(networkName);
                    } else {
                    	reloadChildTask.updateNetworksFailureStatus(networkName, signal.getError());
                    }
                    taskReloadMap.remove(reloadMapKey);
                    synchronized (reloadChildTask){
                    	reloadChildTask.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    	/*BSeriesReloadChildTask task = getReloadTaskFromMap(((BSeriesReloadResponseSignal)signal).getJobID(),signal.getNetworkName());
        if(task==null){
            log.error("Can't get B6 Reload task from the Map. JobID is :"+ ((BSeriesReloadResponseSignal)signal).getJobID());
            return;
        }
        try {
            if(null==signal.getError()|| "".equals(signal.getError())){
            	log.info("handleB6NetworkReloadResponse : Reload succeeded for " + signal.getNetworkName());
                task.onSucceeded();
            }else{
                task.onFailed(signal.getError());
                
                int taskCounts = task.getParentNetworkCounts();
                // If reload is set as multi-selected; then, cancel the rest
                if (taskCounts > 1){
                    task.onCancel();
                }                
            }
        } catch (Exception e) {
            log.error("can't process B6 network Reload response." + e.getMessage());

        } finally{
            try {
                
            	taskReloadMap.remove(((BSeriesReloadResponseSignal)signal).getJobID()+signal.getNetworkName());
                task.notify();
            } catch (Exception e) {
                log.error("can't change task status for B6 network Reload." + e.getMessage());
            }
        }*/
    }

    //James
    /**
     * handle B6 Network Upgrade Response
     * @param signal BSeriesTaskSignal
     */
    private void handleB6NetworkUpgradeResponse(BSeriesTaskSignal signal) {
        BSeriesUpgradeChildTask task = getUpgradeTaskFromMap(((BSeriesNetworkUpgradeResponseSignal)signal).getJobID(),signal.getNetworkName());
        if(task==null){
            log.error("Can't get B6 network Upgrade task from the Map. JobID is :"+ ((BSeriesNetworkUpgradeResponseSignal)signal).getJobID());
            return;
        }
        try {
            if(null==signal.getError()|| "".equals(signal.getError())){
                task.onSucceeded((BSeriesNetworkUpgradeResponseSignal)signal);
            }else{
                task.onFailed(signal.getError());
                
                int taskCounts = task.getParentNetworkCounts();
                // If upgrade is set as multi-selected; then, cancel the rest
                if (taskCounts > 1){
                    task.onCancel();
                }                
            }
        } catch (Exception e) {
            log.error("can't process B6 network Upgrade response.");

        } finally{
            try {
                //James, totally different, comparing C7 and E series...
                taskUpgradeMap.remove(((BSeriesNetworkUpgradeResponseSignal)signal).getJobID()+signal.getNetworkName());
                task.notify();
            } catch (Exception e) {
                log.error("can't change task status for B6 network Upgrade.");
            }
        }
    }
    //~James
    /**
     * handle B6 Network GPON ONT Upgrade Response
     * @param signal BSeriesTaskSignal
     */
    private void handleB6NetworkGponUpgradeResponse(BSeriesTaskSignal signal) {
        BSeriesGponUpgradeChildTask task = getGponUpgradeTaskFromMap(((BSeriesGponOntUpgradeResponseSignal)signal).getJobID(),signal.getNetworkName());
        if(task==null){
            log.error("Can't get B6 network Upgrade task from the Map. JobID is :"+ ((BSeriesGponOntUpgradeResponseSignal)signal).getJobID());
            return;
        }
        try {
            if(null==signal.getError()|| "".equals(signal.getError())){
                task.onSucceeded((BSeriesGponOntUpgradeResponseSignal)signal);
//                task.updateNetworksSucceedStatus(signal.getNetworkName());
            }else{
                task.onFailed(signal.getError());
                
                int taskCounts = task.getParentNetworkCounts();
                // If upgrade is set as non-forced action, and multi-selected; then, cancel the rest
                if (!task.isForceUpgradeFlag() && (taskCounts > 1)){
                    task.onCancel();
                }
                // task.updateNetworksFailureStatus(signal.getNetworkName(), signal.getError());
            }
        } catch (Exception e) {
            log.error("can't process Gpon network Upgrade response.");
//            task.onFailed(signal.getError());
//            task.updateNetworksFailureStatus(signal.getNetworkName(), signal.getError());
        } finally{
            try {
                //James, totally different, comparing C7 and E series...
//                task.taskComplete(signal.getNetworkName());
                taskUpgradeMap.remove(((BSeriesGponOntUpgradeResponseSignal)signal).getJobID()+signal.getNetworkName());
                task.notify();
            } catch (Exception e) {
                log.error("can't change task status for Gpon network Upgrade.");
            }
        }
    }

    /**
     * handle B6 Network GPON ONT Upgrade Response
     * @param signal BSeriesTaskSignal
     */
    private void handleB6RepositoryConfigPathResponse(BSeriesTaskSignal signal) {
    	BSeriesRepositoryPathConfigChildTask task = getRepositoryPathTaskFromMap(((BSeriesGponOntUpgradeResponseSignal)signal).getJobID(),signal.getNetworkName());
        if(task==null){
            log.error("Can't get B6 Repository Config task from the Map. JobID is :"+ ((BSeriesGponOntUpgradeResponseSignal)signal).getJobID());
            return;
        }
        log.debug("handleB6RepositoryConfigPathResponse : signal.getError() : "+signal.getError());
        try {
            if(null==signal.getError()|| "".equals(signal.getError())){
            	log.debug("handleB6RepositoryConfigPathResponse : ");
                task.onSucceeded((BSeriesGponOntUpgradeResponseSignal)signal);
//                task.updateNetworksSucceedStatus(signal.getNetworkName());
            }else{
                task.onFailed(signal.getError());
                
                int taskCounts = task.getParentNetworkCounts();
                
            }
        } catch (Exception e) {
            log.error("can't process B6 Repository Config response.");
//            task.onFailed(signal.getError());
//            task.updateNetworksFailureStatus(signal.getNetworkName(), signal.getError());
        } finally{
            try {
                //James, totally different, comparing C7 and E series...
//                task.taskComplete(signal.getNetworkName());
            	repositoryPathConfigMap.remove(((BSeriesGponOntUpgradeResponseSignal)signal).getJobID()+signal.getNetworkName());
                task.notify();
            } catch (Exception e) {
                log.error("can't change task status for B6 Repository Config.");
            }
        }
    }
    private void handleAeDiscoveryTaskResponse(BSeriesTaskSignal signal) {
    	if(signal.getError() == null) {
        	AeDiscoveryRespSignal respSignal = (AeDiscoveryRespSignal) signal;
            String ip = respSignal.getIpAddress();
            String serno = respSignal.getSerialNum();
            String descr = respSignal.getDescription();
            String model = respSignal.getModel();
            String mac = respSignal.getMacAddress();
            String version = respSignal.getVersion();
            String port = respSignal.getPort();
            String linked_Network = respSignal.getNetworkName();
            GenericCMSAid linked_port = null;

            //To avoid effort for 700 ae ont when discovery. 
            if(model== null){
                log.warn("can not handle Ae discover response, model is null for ip: " + ip);
                return;
            }
            
            log.info("begin to handle Ae discover response...");
            
            IONTProxy ontproxy = CalixONTProxyManager.getInstance().getONTProxy(CalixONTProxyManager.ONTPROXY_NAME_AEONT);
            if (ontproxy != null) {
                if(signal.getNetworkName() != null && signal.getNetworkName().startsWith("AEONT-")){
                    String aeontId = signal.getNetworkName().substring("AEONT-".length());
                     Ae_Ont aeOnt = AeONTProxy.getInstance().getAeONTFromDB(aeontId);
                    if(aeOnt != null ){
                        aeOnt.setIpAddress(ip);
                        aeOnt.setserno(serno);
                        aeOnt.setdescr(descr);
                        aeOnt.setModel(model);
                        aeOnt.setMacAddress(mac);
                        aeOnt.setFirmwareVersion(version);
                      //  boolean isExtlProv = respSignal.getExternalProvisioned();
                        Integer externalProvisioned = aeOnt.getExternalProvisioned();
                        
                        if (OccamUtils.isONTActivated(ip)){
                    		log.info("ONT is provisioned via CMS_OCM " + ip);
                    		externalProvisioned = Ae_Ont.OCM_PROVISIONED;
                        }else {
//                    	}else if (OccamUtils.isExternallyManaged(ip)){
                    		log.info("ONT is externally provisioned via OCM " + ip);
                    		externalProvisioned = Ae_Ont.EXTERNAL_PROVISIONED;
                    	}
                        aeOnt.setExternalProvisioned(externalProvisioned);
                        // linked_Network is B6 network.
                        if(linked_Network != null && !linked_Network.equalsIgnoreCase("")){
                        	aeOnt.setLinked_Network(linked_Network);
                        	//Ae Ont was linked to this port in B6 network.
                        	if(port!=null && !port.equalsIgnoreCase("")&& port.matches("^[0-9]+$")){
                        		linked_port = getGePortAid(port,SessionManager.getInstance().getNetworkReference(linked_Network));
                        		if(linked_port != null){
                        			aeOnt.setlinked_port(linked_port);
                        		}
                        	}
                        }
                        aeOnt.doUpdate();
                    } else{
                        log.error("Cannot find AE-ONT for ID: " + aeontId);
                        signal.setError("Cannot find AE-ONT for IP: " + ip +  ", AE-ONT-DB-ID: " + aeontId);
                    }
                } else{      
                	//boolean isExtlProv = respSignal.getExternalProvisioned();
                    Integer externalProvisioned = null;
                    
                    if (OccamUtils.isONTActivated(ip)){
                		log.info("ONT is provisioned via CMS_OCM " + ip);
                		externalProvisioned = Ae_Ont.OCM_PROVISIONED;
                	}else {
//                	}else if (OccamUtils.isExternallyManaged(ip)){
                		log.info("ONT is externally provisioned via OCM " + ip);
                		externalProvisioned = Ae_Ont.EXTERNAL_PROVISIONED;
                	}
                 // linked_Network is B6 network.
                    if(linked_Network != null && !linked_Network.equalsIgnoreCase("")){
                    	if(port!=null && !port.equalsIgnoreCase("")&& port.matches("^[0-9]+$")){
                    		//Ae Ont was linked to this port in B6 network.
                    		linked_port = getGePortAid(port,SessionManager.getInstance().getNetworkReference(linked_Network));
                    	}
                    	ontproxy.createONTFromBseries(ip, serno, descr, model, mac, version,externalProvisioned,linked_port,linked_Network);
                    }else{
                    	ontproxy.createONTFromBseries(ip, serno, descr, model, mac, version,externalProvisioned);
                    }
                }
            }
           /* try {
                //use sql to update ae_ont table to avoid package dependency
                StringBuffer sqlBuf = new StringBuffer();
                sqlBuf.append("update ae_ont set ");
                sqlBuf.append("descr='").append(descr).append("',");
                sqlBuf.append("model='").append(model).append("',");
                sqlBuf.append("serno='").append(serno).append("',");
                sqlBuf.append("macaddress='").append(mac).append("',");
                sqlBuf.append("firmwareversion='").append(version).append("'");
                if(serno != null && !serno.contains("N/A")) {
                    sqlBuf.append(",aeontid='SN").append(serno).append("'");
                }
                sqlBuf.append(" where ipaddress='").append(ip).append("'");

                C7Database.getInstance().beginTransaction();
                C7Database.getInstance().nativeSql(sqlBuf.toString());
                C7Database.getInstance().commitTransaction();
                log.info("successfully handle Ae discover response.");
                trapRegister(respSignal);
            } catch (Exception ex) {
                log.warn("Unable to update AE ONT: " + ip, ex);
                C7Database.getInstance().rollbackTransaction();
            } finally {
                C7Database.getInstance().close();
            }*/
        } else {
            log.warn("Fail in handling Ae discovery response. IP: " + signal.getIpAddress() + ". Error: " + signal.getError());
        }
    }
    

    
    private BseriesDeviceSBSession getSouthboundSession(BSeriesTaskSignal signal) throws SessionException {
        SouthboundSession sb = SessionManager.getInstance().getSouthboundSession(signal.getNetworkName());
        if(sb == null) {
            throw new SessionException(SessionException._invalidSession,
                    "Southbound session does not exist. Network: " + signal.getNetworkName());
        }
        if(sb instanceof BseriesDeviceSBSession) {
            return (BseriesDeviceSBSession) sb;
        } else {
            throw new SessionException(SessionException._invalidSession,
                    "Invalid southbound session. Network: " + signal.getNetworkName());
        }
    }

    private void markResyncCompleteIfApplicable(BSeriesTaskSignal signal) {
        String networkName = signal.getNetworkName();
        for(String resyncMapKey : taskResyncMap.keySet()){
            if(networkName != null && resyncMapKey != null && resyncMapKey.endsWith(resyncIdNetworkNameDemarcator + networkName)){
                try {
                    B6ResyncInventoryChildTask resyncChildTask = taskResyncMap.get(resyncMapKey);
                    if(signal.getError() == null) {
                        resyncChildTask.updateNetworksSucceedStatus(networkName);
                    } else {
                        resyncChildTask.updateNetworksFailureStatus(networkName, signal.getError());
                    }
                    taskResyncMap.remove(resyncMapKey);
                    synchronized (resyncChildTask){
                        resyncChildTask.notify();
                    }
                } catch (Exception e) {
                	log.error("Error when handling response for resync(B6): ",e);
                }
            }
        }
    }
    private void markAeResyncCompleteIfApplicable(BSeriesTaskSignal signal) {
    	String networkName = signal.getNetworkName();
    	for(String resyncMapKey : taskAeOntMap.keySet()){
    		if(networkName != null && resyncMapKey != null && resyncMapKey.endsWith(resyncIdNetworkNameDemarcator + networkName)){
    			try {
    				AeResyncOcmManagedChildTask resyncChildTask = taskAeOntMap.get(resyncMapKey);
    				if(signal.getError() == null) {
    					resyncChildTask.updateNetworksSucceedStatus(networkName);
    				} else {
    					resyncChildTask.updateNetworksFailureStatus(networkName, signal.getError());
    				}
    				taskAeOntMap.remove(resyncMapKey);
    				synchronized (resyncChildTask){
    					resyncChildTask.notify();
    				}
    			} catch (Exception e) {
    				log.error("Error when handling response for ae resynce: ",e);
    			}
    		}
    	}
    }
    private void markESAConfigCompleteIfApplicable(BseriesESAConfigRespSignal signal) {
    	String taskId = signal.getTaskId();
    	String taskName = signal.getTaskName();
    	for(String esaConfigMapKey : taskESAConfigMap.keySet()){
    		if(taskId != null && esaConfigMapKey != null && esaConfigMapKey.equals(taskId)){
    			try {
    				B6ESAConfigurationTask esaConfigChildTask = taskESAConfigMap.get(esaConfigMapKey);
    				esaConfigChildTask.updateNetworksStatus(taskName,signal.getDetailProps());
    				taskESAConfigMap.remove(esaConfigMapKey);
    				synchronized (esaConfigChildTask){
    					esaConfigChildTask.notify();
    				}
    			} catch (Exception e) {
    				log.error("Error when handling response for ESA configuration: ",e);
    			}
    		}
    	}
    }
    
    private static final int B6Tlv_B6_GePort = 0x34F3;
    private static final int B6Tlv_tB6GEPortAid = 0x34F4;
    private GenericCMSAid getGePortAid(String intfAid,short networkRef) {
        GenericCMSAid portAid = new GenericCMSAid(B6Tlv_B6_GePort,B6Tlv_tB6GEPortAid,intfAid);
        portAid.convertTo();
        portAid.getAddressId();
        portAid.setNetworkRef(networkRef);
        return portAid;
    }
}
