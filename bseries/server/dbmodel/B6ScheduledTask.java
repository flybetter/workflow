/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

//BEGIN IMPORTS
import com.calix.bseries.server.session.BseriesDeviceSBSession;
import com.calix.bseries.server.task.B6ESAConfigurationTask;
import com.calix.bseries.server.task.B6ResyncInventoryChildTask;
import com.calix.bseries.server.task.BSeriesGponUpgradeChildTask;
import com.calix.bseries.server.task.BSeriesReloadChildTask;
import com.calix.bseries.server.task.BSeriesGponUpgradeChildTask;
import com.calix.bseries.server.task.BSeriesRepositoryPathConfigChildTask;
import com.calix.bseries.server.task.BSeriesUpgradeChildTask;
import com.calix.bseries.server.task.BseriesBackupChildTask;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.common.CMSConfig;
import com.calix.ems.server.connection.NetworkState;
import com.calix.ems.server.dbmodel.CMSScheduledTask;
//END IMPORTS
import com.calix.ems.util.ScheduledTaskUtils;
import com.calix.system.common.log.Log;
import com.calix.system.server.session.SessionManager;
import com.calix.system.server.session.SouthboundSession;
import com.calix.system.server.util.DeviceChildTask;


import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;
import com.occam.ems.client.util.ClientUtil;
import com.occam.ems.client.util.ConfigUIConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Class B6ScheduledTask.
 * 
 * @version $Revision$ $Date$
 */
public class B6ScheduledTask extends CMSScheduledTask {

    
      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
    
   /**
     * Field m_AdditionalInfo
     */
    public String m_AdditionalInfo;

    /**
     * Field m_Description
     */
    public String m_Description;

    /**
     * List of excluded Networks from bseries devices
     */
    public List m_ExcludedNetworks;

    /**
     * List of excluded Regions from bseries devices
     */
    public List m_ExcludedRegions;

    /**
     * Field m_FailureAction
     */
    public String m_FailureAction;

    /**
     * List of Networks selected for bseries devices
     */
    public List m_SelectedNetworks;

    /**
     * List of Regions selected for bseries devices
     */
    public List m_SelectedRegions;

    /**
     * Source Ftp Directory Path
     */
    public String m_SourceDirectoryPath;

    /**
     * Source Ftp Password
     */
    public String m_SourceFtpPassword;

    /**
     * Source Ftp Server
     */
    public String m_SourceFtpServer;

    /**
     * Source Ftp User
     */
    public String m_SourceFtpUser;

    /**
     * Field m_TaskName
     */
    public String m_TaskName;

    /**
     * Field m_TaskType
     */
    public String m_TaskType;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6ScheduledTask";

    /**
     * new int[]{empty networkid, B6ScheduledTask}
     */
    public static int[] m_hierarchy = new int[]{0, 13518};

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6ScheduledTask() {
        super();
        m_JobType = new Integer(0x002D);
    } //-- com.calix.bseries.server.dbmodel.B6ScheduledTask()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method copyFields
     * 
     * @param obj1
     */
    protected void copyFields(CMSObject obj1)
    {
        if( obj1 instanceof B6ScheduledTask ) {
            super.copyFields(obj1);
            B6ScheduledTask obj = (B6ScheduledTask)obj1;
            setAdditionalInfo((String)Helper.copy(obj.getAdditionalInfo()));
            setDescription((String)Helper.copy(obj.getDescription()));
            setExcludedNetworks((List)Helper.copy(obj.getExcludedNetworks()));
            setExcludedRegions((List)Helper.copy(obj.getExcludedRegions()));
            setFailureAction((String)Helper.copy(obj.getFailureAction()));
            setSelectedNetworks((List)Helper.copy(obj.getSelectedNetworks()));
            setSelectedRegions((List)Helper.copy(obj.getSelectedRegions()));
            setSourceDirectoryPath((String)Helper.copy(obj.getSourceDirectoryPath()));
            setSourceFtpPassword((String)Helper.copy(obj.getSourceFtpPassword()));
            setSourceFtpServer((String)Helper.copy(obj.getSourceFtpServer()));
            setSourceFtpUser((String)Helper.copy(obj.getSourceFtpUser()));
            setTaskName((String)Helper.copy(obj.getTaskName()));
            setTaskType((String)Helper.copy(obj.getTaskType()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getAdditionalInfo
     */
    public String getAdditionalInfo()
    {
        return this.m_AdditionalInfo;
    } //-- String getAdditionalInfo() 

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return this.m_Description;
    } //-- String getDescription() 

    /**
     * Method getExcludedNetworks
     */
    public List getExcludedNetworks()
    {
        return this.m_ExcludedNetworks;
    } //-- List getExcludedNetworks() 

    /**
     * Method getExcludedRegions
     */
    public List getExcludedRegions()
    {
        return this.m_ExcludedRegions;
    } //-- List getExcludedRegions() 

    /**
     * Method getFailureAction
     */
    public String getFailureAction()
    {
        return this.m_FailureAction;
    } //-- String getFailureAction() 

    /**
     * Method getHierarchy
     */
    public int[] getHierarchy()
    {
        return m_hierarchy;
    } //-- int[] getHierarchy() 

    /**
     * Method getSelectedNetworks
     */
    public List getSelectedNetworks()
    {
        return this.m_SelectedNetworks;
    } //-- List getSelectedNetworks() 

    /**
     * Method getSelectedRegions
     */
    public List getSelectedRegions()
    {
        return this.m_SelectedRegions;
    } //-- List getSelectedRegions() 

    /**
     * Method getSourceDirectoryPath
     */
    public String getSourceDirectoryPath()
    {
        return this.m_SourceDirectoryPath;
    } //-- String getSourceDirectoryPath() 

    /**
     * Method getSourceFtpPassword
     */
    public String getSourceFtpPassword()
    {
        return this.m_SourceFtpPassword;
    } //-- String getSourceFtpPassword() 

    /**
     * Method getSourceFtpServer
     */
    public String getSourceFtpServer()
    {
        return this.m_SourceFtpServer;
    } //-- String getSourceFtpServer() 

    /**
     * Method getSourceFtpUser
     */
    public String getSourceFtpUser()
    {
        return this.m_SourceFtpUser;
    } //-- String getSourceFtpUser() 

    /**
     * Method getTaskName
     */
    public String getTaskName()
    {
        return this.m_TaskName;
    } //-- String getTaskName() 

    /**
     * Method getTaskType
     */
    public String getTaskType()
    {
        return this.m_TaskType;
    } //-- String getTaskType() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6ScheduledTask;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method isEMSCacheSupported
     */
    public boolean isEMSCacheSupported()
    {
        return true;
    } //-- boolean isEMSCacheSupported() 

    /**
     * Method populateAttributeFromTLV
     * 
     * @param tlv
     * @param from_version
     */
    public void populateAttributeFromTLV(TLV tlv, SwVersionNo from_version)
    {
        if (tlv == null)
            return;
        switch (tlv.getType()) {
            case 0x0EE1:
                if (m_SelectedRegions == null) {
                    m_SelectedRegions = TLVHelper.getValueCollectionOfTLV(tlv, from_version, TLVHelper.String);
                    return;
                }
                break;
            case 0x0EE2:
                if (m_SelectedNetworks == null) {
                    m_SelectedNetworks = TLVHelper.getValueCollectionOfTLV(tlv, from_version, TLVHelper.String);
                    return;
                }
                break;
            case 0x0EE3:
                if (m_ExcludedRegions == null) {
                    m_ExcludedRegions = TLVHelper.getValueCollectionOfTLV(tlv, from_version, TLVHelper.String);
                    return;
                }
                break;
            case 0x0EE4:
                if (m_ExcludedNetworks == null) {
                    m_ExcludedNetworks = TLVHelper.getValueCollectionOfTLV(tlv, from_version, TLVHelper.String);
                    return;
                }
                break;
            case 0x1F66:
                if (m_SourceFtpServer == null) {
                    m_SourceFtpServer = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x1F67:
                if (m_SourceFtpUser == null) {
                    m_SourceFtpUser = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x1F68:
                if (m_SourceFtpPassword == null) {
                    m_SourceFtpPassword = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x1F69:
                if (m_SourceDirectoryPath == null) {
                    m_SourceDirectoryPath = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3408:
                if (m_TaskType == null) {
                    m_TaskType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3409:
                if (m_TaskName == null) {
                    m_TaskName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3410:
                if (m_Description == null) {
                    m_Description = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3411:
                if (m_FailureAction == null) {
                    m_FailureAction = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3412:
                if (m_AdditionalInfo == null) {
                    m_AdditionalInfo = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
        }
        super.populateAttributeFromTLV(tlv, from_version);
    } //-- void populateAttributeFromTLV(TLV, SwVersionNo) 

    /**
     * Method populateTLVFromAttributes
     * 
     * @param tlv
     * @param to_version
     */
    public void populateTLVFromAttributes(TLV tlv, SwVersionNo to_version)
    {
        if (tlv == null)
            return;
        super.populateTLVFromAttributes(tlv, to_version);
        TLVHelper.addEmbeddedTLV(tlv, 0x3412, m_AdditionalInfo);
        TLVHelper.addEmbeddedTLV(tlv, 0x3410, m_Description);
        TLVHelper.addEmbeddedTLVCollection(tlv, to_version, 0x0EE4, 0xFFFF, TLVHelper.String, m_ExcludedNetworks);
        TLVHelper.addEmbeddedTLVCollection(tlv, to_version, 0x0EE3, 0xFFFF, TLVHelper.String, m_ExcludedRegions);
        TLVHelper.addEmbeddedTLV(tlv, 0x3411, m_FailureAction);
        TLVHelper.addEmbeddedTLVCollection(tlv, to_version, 0x0EE2, 0xFFFF, TLVHelper.String, m_SelectedNetworks);
        TLVHelper.addEmbeddedTLVCollection(tlv, to_version, 0x0EE1, 0xFFFF, TLVHelper.String, m_SelectedRegions);
        TLVHelper.addEmbeddedTLV(tlv, 0x1F69, m_SourceDirectoryPath);
        TLVHelper.addEmbeddedTLV(tlv, 0x1F68, m_SourceFtpPassword);
        TLVHelper.addEmbeddedTLV(tlv, 0x1F66, m_SourceFtpServer);
        TLVHelper.addEmbeddedTLV(tlv, 0x1F67, m_SourceFtpUser);
        TLVHelper.addEmbeddedTLV(tlv, 0x3409, m_TaskName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3408, m_TaskType);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setAdditionalInfo
     * 
     * @param AdditionalInfo
     */
    public void setAdditionalInfo(String AdditionalInfo)
    {
        this.m_AdditionalInfo = AdditionalInfo;
    } //-- void setAdditionalInfo(String) 

    /**
     * Method setDescription
     * 
     * @param Description
     */
    public void setDescription(String Description)
    {
        this.m_Description = Description;
    } //-- void setDescription(String) 

    /**
     * Method setExcludedNetworks
     * 
     * @param ExcludedNetworks
     */
    public void setExcludedNetworks(List ExcludedNetworks)
    {
        this.m_ExcludedNetworks = ExcludedNetworks;
    } //-- void setExcludedNetworks(List) 

    /**
     * Method setExcludedRegions
     * 
     * @param ExcludedRegions
     */
    public void setExcludedRegions(List ExcludedRegions)
    {
        this.m_ExcludedRegions = ExcludedRegions;
    } //-- void setExcludedRegions(List) 

    /**
     * Method setFailureAction
     * 
     * @param FailureAction
     */
    public void setFailureAction(String FailureAction)
    {
        this.m_FailureAction = FailureAction;
    } //-- void setFailureAction(String) 

    /**
     * Method setSelectedNetworks
     * 
     * @param SelectedNetworks
     */
    public void setSelectedNetworks(List SelectedNetworks)
    {
        this.m_SelectedNetworks = SelectedNetworks;
    } //-- void setSelectedNetworks(List) 

    /**
     * Method setSelectedRegions
     * 
     * @param SelectedRegions
     */
    public void setSelectedRegions(List SelectedRegions)
    {
        this.m_SelectedRegions = SelectedRegions;
    } //-- void setSelectedRegions(List) 

    /**
     * Method setSourceDirectoryPath
     * 
     * @param SourceDirectoryPath
     */
    public void setSourceDirectoryPath(String SourceDirectoryPath)
    {
        this.m_SourceDirectoryPath = SourceDirectoryPath;
    } //-- void setSourceDirectoryPath(String) 

    /**
     * Method setSourceFtpPassword
     * 
     * @param SourceFtpPassword
     */
    public void setSourceFtpPassword(String SourceFtpPassword)
    {
        this.m_SourceFtpPassword = SourceFtpPassword;
    } //-- void setSourceFtpPassword(String) 

    /**
     * Method setSourceFtpServer
     * 
     * @param SourceFtpServer
     */
    public void setSourceFtpServer(String SourceFtpServer)
    {
        this.m_SourceFtpServer = SourceFtpServer;
    } //-- void setSourceFtpServer(String) 

    /**
     * Method setSourceFtpUser
     * 
     * @param SourceFtpUser
     */
    public void setSourceFtpUser(String SourceFtpUser)
    {
        this.m_SourceFtpUser = SourceFtpUser;
    } //-- void setSourceFtpUser(String) 

    /**
     * Method setTaskName
     * 
     * @param TaskName
     */
    public void setTaskName(String TaskName)
    {
        this.m_TaskName = TaskName;
    } //-- void setTaskName(String) 

    /**
     * Method setTaskType
     * 
     * @param TaskType
     */
    public void setTaskType(String TaskType)
    {
        this.m_TaskType = TaskType;
    } //-- void setTaskType(String) 

    /**
     * Method setTypeName
     * 
     * @param typeName
     */
    public boolean setTypeName(String typeName)
    {
        return false;
    } //-- boolean setTypeName(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6ScheduledTask ) {
            super.updateFields(obj1);
            B6ScheduledTask obj = (B6ScheduledTask)obj1;
           if (obj.getAdditionalInfo() != null )
               setAdditionalInfo((String)Helper.copy(obj.getAdditionalInfo()));
           if (obj.getDescription() != null )
               setDescription((String)Helper.copy(obj.getDescription()));
           if (obj.getExcludedNetworks() != null )
               setExcludedNetworks((List)Helper.copy(obj.getExcludedNetworks()));
           if (obj.getExcludedRegions() != null )
               setExcludedRegions((List)Helper.copy(obj.getExcludedRegions()));
           if (obj.getFailureAction() != null )
               setFailureAction((String)Helper.copy(obj.getFailureAction()));
           if (obj.getSelectedNetworks() != null )
               setSelectedNetworks((List)Helper.copy(obj.getSelectedNetworks()));
           if (obj.getSelectedRegions() != null )
               setSelectedRegions((List)Helper.copy(obj.getSelectedRegions()));
           if (obj.getSourceDirectoryPath() != null )
               setSourceDirectoryPath((String)Helper.copy(obj.getSourceDirectoryPath()));
           if (obj.getSourceFtpPassword() != null )
               setSourceFtpPassword((String)Helper.copy(obj.getSourceFtpPassword()));
           if (obj.getSourceFtpServer() != null )
               setSourceFtpServer((String)Helper.copy(obj.getSourceFtpServer()));
           if (obj.getSourceFtpUser() != null )
               setSourceFtpUser((String)Helper.copy(obj.getSourceFtpUser()));
           if (obj.getTaskName() != null )
               setTaskName((String)Helper.copy(obj.getTaskName()));
           if (obj.getTaskType() != null )
               setTaskType((String)Helper.copy(obj.getTaskType()));
        }
    } //-- void updateFields(CMSObject) 

    
     // BEGIN CODE
    
	public static final String BASE_DIR = "..";

	private static final Logger log = Logger.getLogger(B6ScheduledTask.class);
	public static final String RESET_SOFTWARE_TYPE_NAME = "EMSResetSoftwareTask";
	private static final int MAX_RETRIES = 1;

	private transient List m_networksReady;
	private transient Map<String, Integer> m_networksFailedMap; // store failed
																// networks and
																// number
	private transient ArrayList<String> m_networksResetFailed; // store reset
																// failed
																// networks
	private transient Map<String, String> m_errorStatusMap; // map with network
															// and error
															// message.
	private transient Map<String, String> m_infoStatusMap; // map with network
															// and info message.
	private transient Set<String> m_networksSuccess; // store networks upgrade
														// successfully
	private transient Set<String> m_networksIgnored; // store networks do not
														// need upgrade because
	// it runs the current or higher version
	// already
	private transient Set<String> m_networksSkipped; // store networks skipped
														// due to different
	// model type
	private transient Set<String> m_networksAborted; // store networks did not
														// upgrade due to task
	// cancelling

	private transient Set<String> AeONTSuccess; // store AeONT upgrade
												// successfully
	private transient Set<String> AeONTIgnored; // store AeONT do not need
												// upgrade because
	private transient Set<String> AeONTFailed; // store AeONT did not upgrade
												// due to task

	private transient Map<String, String> aeErrorStatusMap; // map with network
															// and error
															// message.
	private transient Map<String, String> aeInfoStatusMap; // map with network
															// and info message.
    private transient ArrayList m_networksSkippedList;
    // a network - status map to record network failure status.
    private transient Map<String,String>m_networkSkippedStatus;
	private int m_networkCount = 0;
	private boolean isAborted = false;
	private Boolean isDone = false;

	public void run() {
		B6ScheduledTask task = (B6ScheduledTask) prepareTaskForRun();
		if (task != null) {
                        //cancelPendingJobs(getTaskId(), task.getTaskId());
			task.executeTask();
		}
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.calix.ems.server.dbmodel.CMSScheduledTask#rerun()
     */
    @Override
    public void rerun() {
        log.info("Rerun in B6ScheduledTask");
//        super.rerun();

        if (prepareTaskForRerun()) {
            // run task in separate thread
            new Thread(new Runnable() {
                public void run() {
                    executeTask();
                }
            }, "B6UpgradeTask-" + getJobID()).start();
        }
    }
    
	public void executeTask() {
            
		try {
			m_networksFailedMap = new HashMap<String, Integer>();
			m_networksResetFailed = new ArrayList<String>();
			m_errorStatusMap = new HashMap<String, String>();
			m_infoStatusMap = new HashMap<String, String>();
			m_networksSuccess = new HashSet<String>();
			m_networksIgnored = new HashSet<String>();
			m_networksSkipped = new HashSet<String>();
			m_networksAborted = new HashSet<String>();
            m_networksFailedMap = new HashMap<String,Integer>();
            m_networkFailedStatus = new HashMap<String,String>();
            m_networksSkippedList =new ArrayList();
            m_networkSkippedStatus = new HashMap<String,String>();
            
			m_networksReady = ScheduledTaskUtils.getNetworks(
					getSelectedRegions(), getExcludedRegions(),
					getSelectedNetworks(), getExcludedNetworks(), null,
					TLVClassMap.getClassForType("CalixB6Device"));
            /*if ( this.m_TaskType.equals("Resync Inventory")){

                //Since selecting the region implicitly selects the AE-ONTs in case of Resync, only send the
                List selectedOccamAeOnts = ScheduledTaskUtils.getNetworks(
                        getSelectedRegions(), getExcludedRegions(),
                        null, null, null,
                        TLVClassMap.getClassForType("Ae_Ont"));
                if(selectedOccamAeOnts != null && !selectedOccamAeOnts.isEmpty()) {



                    m_networksReady.addAll(selectedOccamAeOnts);
                }
            }*/
			if (null == m_networksReady&&!this.m_TaskType.equals(ConfigUIConstants.ESA_CONFIGURATION)) {
				setNote("No networks selected for upgrade");
				updateStatus(STATUS_FAILED);
				log.warn("B6ScheduledTask: No networks selected for upgrade.");
				return;
			}
			if(m_networksReady != null)
				m_networkCount = m_networksReady.size();

			this.doExecute();
		} catch (Exception ex) {
			log.error("Error in running upgrade task.", ex);
			setNote("Scheduled Task failed. Internal error: " + ex.getMessage());
			updateStatus(STATUS_FAILED);
		}

	}

   	/**
	 * executeB6Task
	 */
	private void doExecute() {
            try {
            	if(this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG))
            		m_networkCount = 0;
                while (m_networksReady != null&&!m_networksReady.isEmpty()) {
                    String network = null;
                    synchronized (m_networksReady) {
                        if (!m_networksReady.isEmpty()) {
                            network = (String) m_networksReady.remove(0);
                        }
                    }
                    
                    if (network != null) {
                        if (isSkipedNetwork(network)) {
                            onNetworkSkipped(network,
                                    "disconnected with auto-connect disabled,skip this network.");
                            continue;
                        }
                        
                        DeviceChildTask child;
                        // Homer dispatch
                        log.warn("B6ScheduledTask: Task Name for upgrade : "+this.m_TaskType);
                        if (this.m_TaskType.equals(ConfigUIConstants.IMAGE_UPGRADE)) {
                            child = new BSeriesUpgradeChildTask();
                        } else if (this.m_TaskType.equals(ConfigUIConstants.RELOAD_DEVICE)) {
                            child = new BSeriesReloadChildTask();
                        }else if (this.m_TaskType.equals("BackUp")) {
                            child = new BseriesBackupChildTask();
                        } else if (this.m_TaskType.equals("Resync Inventory")) {
                        	if(network.startsWith("AEONT-")){
                        		onNetworkSkipped(network,"Don't do resync for ae ont");
                        			continue;	
                        	}
                            child = new B6ResyncInventoryChildTask();
                        } else if (this.m_TaskType.equals("GPON ONT Image Upgrade")) {
                            child = new BSeriesGponUpgradeChildTask();
                        }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)) {
                        	BseriesDeviceSBSession m_session = (BseriesDeviceSBSession) SessionManager.getInstance().getSouthboundSession(network);
                        	 log.info("Start to filt B6 deviece for GPON .");
           	              if(m_session==null ||m_session.isBSeriesMgrProcessDown()) {
           	                  log.warn("BSeries manager process down.");
           	                  continue;
           	              }
           	              BaseCMSNetwork networkObject = m_session.getEMSNetwork();
           	              if(networkObject == null){
           	            	  log.warn("can not get network object for ["+network+"] from sbsession");
           	            	  continue;
           	              }
           	              String eqptType = ((CalixB6Device)networkObject).getModel();
           	              String version = ((CalixB6Device)networkObject).getSWVersion();
           	              if(eqptType != null&&version != null&&ClientUtil.isB6NodeSupported(eqptType, version, "B6GPON")){
           	            	  m_networkCount++;	
           	            	  child = new BSeriesRepositoryPathConfigChildTask();
           	              }
                          else{
                        	  log.warn("the network["+network+"] device information was null or it does't support GPON");
                        	  continue;
                          }
                        }else {
                            child = new BseriesBackupChildTask();
                        }
                        
                        child.setParent(this);
                        if (child != null) {
                            child.setNetwork(network);
                            try {
                                B6ScheduledTaskThreadPool.getInstance().run(child);
                            } catch (Throwable e) {
                                log.error(
                                        "Unable to run B6 child upgrade task in a thread pool",
                                        e);
                                onNetworkFailed(network,
                                        "Internal error: " + e.getMessage());
                            }
                        } else {
                            onNetworkFailed(network,
                                    "Failed to create upgrade task handlers");
                        }
                    }
                }
                if(this.m_TaskType.equals(ConfigUIConstants.ESA_CONFIGURATION)){
                	DeviceChildTask child = new B6ESAConfigurationTask();
                	child.setParent(this);
                	child.setNetwork(m_TaskName);
                    if (child != null) {
   
                        try {
                            B6ScheduledTaskThreadPool.getInstance().run(child);
                        } catch (Throwable e) {
                            log.error(
                                    "Unable to run B6 child upgrade task in a thread pool",
                                    e);
                            updateESAStatus(false,
                                    "Internal error: " + e.getMessage());
                        }
                    } 
                }
            } catch (Exception ex) {
                Log.scheduled().error("B6ScheduledTask:execute: exception = " + ex, ex);
                if (0 == m_networksSuccess.size()) {
                    updateStatus(STATUS_FAILED);
                    addJobStatus(ERROR, "", ex.getMessage());
                } else {
                    updateStatus(STATUS_PARTIAL);
                    addJobStatus(ERROR, "", ex.getMessage());
                }
            }
	}
	
    private int receivedUpdateJobStatusCountSoFar = 0;
	private void updateJobStatus() {
		// prevent race condition so that job status updated only once
		String device = " network(s) ";
		int networkSucessSize = m_networksSuccess.size();
		int networkFailedSize = m_networksFailedMap.size();
		int networkIgnoredSize = m_networksIgnored.size();
        receivedUpdateJobStatusCountSoFar++;
        if(receivedUpdateJobStatusCountSoFar < m_networkCount) {
            //Have not received update job status from all connected networks yet.  Wait until all of them are done.
            return;
        }
		boolean updateStatus = false;
		synchronized (isDone) {
			if (isDone == false) {
				isDone = true;
				updateStatus = true;
			}
		}
		if (updateStatus) {
			// add job status

			if (!m_errorStatusMap.isEmpty()) {
				for (String networkName : m_errorStatusMap.keySet()) {
					if (null != m_networksFailedMap.get(networkName)) {
						addJobStatus(ERROR, networkName,
								m_errorStatusMap.get(networkName));
					}
				}
			}
			if (!m_infoStatusMap.isEmpty()) {
				for (String networkName : m_infoStatusMap.keySet()) {
					addJobStatus(INFO, networkName,
							m_infoStatusMap.get(networkName));
				}
			}

			if (0 == networkFailedSize) {
				StringBuffer sb = new StringBuffer();
				if (this.m_TaskType.equals(ConfigUIConstants.IMAGE_UPGRADE)) {
					sb.append(networkSucessSize).append(device + " upgraded successfully. ");
                } else if (this.m_TaskType.equals(ConfigUIConstants.RELOAD_DEVICE)) {
                	sb.append(networkSucessSize).append(device + " reloaded successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.RESYNC_INVENTORY)) {
                	sb.append(networkSucessSize).append(device + " resynced successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)) {
                	sb.append(networkSucessSize).append("GPON ONT " + device + " upgraded successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.ONT_IMAGE_UPGRADE)) {
                	sb.append(networkSucessSize).append("Active ONT " + device + " upgraded successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)) {
                	sb.append(networkSucessSize).append("GPON OLT " + device + " repository path configured successfully. ");
                }
				
				if (!m_networksIgnored.isEmpty()) {
					sb.append(networkIgnoredSize)
							.append(device
									+ " ignored since they are running the current or later software version.");
				}
				setNote(sb.toString());
				updateStatus(STATUS_SUCCESSFUL);
			} else {
				StringBuffer sb = new StringBuffer();
				if (this.m_TaskType.equals(ConfigUIConstants.IMAGE_UPGRADE)) {
					sb.append(m_networksSuccess.isEmpty() ? "Upgrade failed."
							: "Upgrade completed.");
                } else if (this.m_TaskType.equals(ConfigUIConstants.RELOAD_DEVICE)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Reload failed."
    						: "Reload completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.RESYNC_INVENTORY)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Resync failed."
    						: "Resync completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Gpon Ont Upgrade failed."
    						: "Gpon Ont Upgrade completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.ONT_IMAGE_UPGRADE)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Active Ont Upgrade failed."
    						: "Active Ont Upgrade completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)) {
                	sb.append(m_networksSuccess.isEmpty() ? "GPON OLT Repository Path Configuration failed."
    						: "GPON OLT Repository Path Configuration completed.");
                }
				
				sb.append(" Failed: ").append(networkFailedSize)
						.append(device + ".");
				sb.append(" Succeeded: ").append(networkSucessSize)
						.append(device + ".");
				if (!m_networksIgnored.isEmpty()) {
					sb.append(" Ignored: ")
							.append(networkIgnoredSize)
							.append(device
									+ " because they are running the current or later software version.");
				}
				setNote(sb.toString());
				updateStatus(networkSucessSize == 0 ? STATUS_FAILED
						: STATUS_PARTIAL);
			}
		}
	}
	private void updateJobStatus(String strMessage) {
		// prevent race condition so that job status updated only once
		String device = " network(s) ";
		int networkSucessSize = m_networksSuccess.size();
		int networkFailedSize = m_networksFailedMap.size();
		int networkIgnoredSize = m_networksIgnored.size();
        receivedUpdateJobStatusCountSoFar++;
        if(receivedUpdateJobStatusCountSoFar < m_networkCount) {
            //Have not received update job status from all connected networks yet.  Wait until all of them are done.
            return;
        }
		boolean updateStatus = false;
		synchronized (isDone) {
			if (isDone == false) {
				isDone = true;
				updateStatus = true;
			}
		}
		if (updateStatus) {
			// add job status

			if (!m_errorStatusMap.isEmpty()) {
				for (String networkName : m_errorStatusMap.keySet()) {
					if (null != m_networksFailedMap.get(networkName)) {
						addJobStatus(ERROR, networkName,
								m_errorStatusMap.get(networkName));
					}
				}
			}
			if (!m_infoStatusMap.isEmpty()) {
				for (String networkName : m_infoStatusMap.keySet()) {
					addJobStatus(INFO, networkName,
							m_infoStatusMap.get(networkName));
				}
			}

			if (0 == networkFailedSize) {
				StringBuffer sb = new StringBuffer();
				if (this.m_TaskType.equals(ConfigUIConstants.IMAGE_UPGRADE)) {
					sb.append(networkSucessSize).append(device + " upgraded successfully. ");
                } else if (this.m_TaskType.equals(ConfigUIConstants.RELOAD_DEVICE)) {
                	sb.append(networkSucessSize).append(device + " reloaded successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.RESYNC_INVENTORY)) {
                	sb.append(networkSucessSize).append(device + " resynced successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)) {
                	sb.append(networkSucessSize).append("GPON ONT " + device + " upgraded successfully. ");
                	if(strMessage !=null && strMessage.length()>0){
                		sb.append(" "+strMessage);
                	}
                }else if (this.m_TaskType.equals(ConfigUIConstants.ONT_IMAGE_UPGRADE)) {
                	sb.append(networkSucessSize).append("Active ONT " + device + " upgraded successfully. ");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)) {
                	sb.append(networkSucessSize).append("GPON OLT " + device + " repository path configured successfully. ");
                }
				
				if (!m_networksIgnored.isEmpty()) {
					sb.append(networkIgnoredSize)
							.append(device
									+ " ignored since they are running the current or later software version.");
				}
				setNote(sb.toString());
				updateStatus(STATUS_SUCCESSFUL);
			} else {
				StringBuffer sb = new StringBuffer();
				if (this.m_TaskType.equals(ConfigUIConstants.IMAGE_UPGRADE)) {
					sb.append(m_networksSuccess.isEmpty() ? "Upgrade failed."
							: "Upgrade completed.");
                } else if (this.m_TaskType.equals(ConfigUIConstants.RELOAD_DEVICE)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Reload failed."
    						: "Reload completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.RESYNC_INVENTORY)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Resync failed."
    						: "Resync completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Gpon Ont Upgrade failed."
    						: "Gpon Ont Upgrade completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.ONT_IMAGE_UPGRADE)) {
                	sb.append(m_networksSuccess.isEmpty() ? "Active Ont Upgrade failed."
    						: "Active Ont Upgrade completed.");
                }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)) {
                	sb.append(m_networksSuccess.isEmpty() ? "GPON OLT Repository Path Configuration failed."
    						: "GPON OLT Repository Path Configuration completed.");
                }
				
				sb.append(" Failed: ").append(networkFailedSize)
						.append(device + ".");
				sb.append(" Succeeded: ").append(networkSucessSize)
						.append(device + ".");
				if (!m_networksIgnored.isEmpty()) {
					sb.append(" Ignored: ")
							.append(networkIgnoredSize)
							.append(device
									+ " because they are running the current or later software version.");
				}
				setNote(sb.toString());
				updateStatus(networkSucessSize == 0 ? STATUS_FAILED
						: STATUS_PARTIAL);
			}
		}
	}

        public void onNetworkSucceeded(String network) {
		m_infoStatusMap.put(network, "Success");
		m_networksSuccess.add(network);

		synchronized (m_networksFailedMap) {
			m_networksFailedMap.remove(network);
		}
		postNetworkComplete(network);
	}
        
        public void onNetworkSucceeded(String network ,String strMsg) {
    		m_infoStatusMap.put(network, "Success");
    		m_networksSuccess.add(network);

    		synchronized (m_networksFailedMap) {
    			m_networksFailedMap.remove(network);
    		}
    		updateJobStatus(strMsg);
    	}
        
        public void onNetworkSucceeded(String network ,String strMsg, String additionMsg) {
        	m_infoStatusMap.put(network, additionMsg);
        	m_networksSuccess.add(network);
        	
        	synchronized (m_networksFailedMap) {
        		m_networksFailedMap.remove(network);
        	}
        	if(strMsg!= null && strMsg.length()>0)
        		updateJobStatus(strMsg);
        	else
        		postNetworkComplete(network);
        }
        
	public void onNetworkSkipped(String network, String detail) {
		m_infoStatusMap.put(network, detail);
		m_networksSkipped.add(network);
		synchronized (m_networksFailedMap) {
			if (m_networksFailedMap.containsKey(network)) {
				m_networksFailedMap.remove(network);
			}
		}
		 postNetworkComplete(network);
	}

        public void onNetworkFailed(String network, String detail) {
		m_errorStatusMap.put(network, detail);
		boolean retry = false;
		synchronized (m_networksFailedMap) {
			Integer times = m_networksFailedMap.get(network);
			if (times == null || times < MAX_RETRIES) {
				retry = true;
			}
			m_networksFailedMap.put(network, times == null ? 1 : times + 1);
		}
		if (retry) {
			synchronized (m_networksReady) {
				m_networksReady.add(network);
			}
		}
		 postNetworkComplete(network);
	}

        
        public void updateESAStatus( boolean isSucceed, String strMsg){
        	
        	StringBuilder sb = new StringBuilder();
        	if(isSucceed){
            	sb.append("ESA Configuration task: ").append(m_TaskName).append( " executed successfully. ");
            	setNote(sb.toString());
    			updateStatus(STATUS_SUCCESSFUL);
        	} else{
        		sb.append("ESA Configuration task: ").append(m_TaskName).append( ": ").append(strMsg==null?"":strMsg);
            	setNote(sb.toString());
    			updateStatus(STATUS_FAILED);
        		
        	}

        }
        
        public void updateESAStatus(Properties detailProps){
        	if(detailProps!=null ){
        		HashMap detailMap = null;
        		if((detailMap = (HashMap)detailProps.get(ScheduleConstants.ESA_DETAILS_MAP))!=null){
        			Set<String> keys = detailMap.keySet();
        			Properties prop;
        			for(String key: keys){
        				prop = (Properties)detailMap.get(key);
        				if(prop.getProperty(ScheduleConstants.ESA_RESULT_SINGALE,"").equals(ScheduleConstants.JOB_STATUS_SUCCESSFUL)){
        					addJobStatus(INFO, key,prop.getProperty(ScheduleConstants.ESA_DETAILS_SINGLE, ""));
        				}else{
        					addJobStatus(ERROR,key,prop.getProperty(ScheduleConstants.ESA_DETAILS_SINGLE, ""));
        				}
        				
        			}
        		}
        		
        		StringBuffer sb = new StringBuffer();
	        	String result = detailProps.getProperty(ScheduleConstants.ESA_RESULT,"");
	        	String count = detailProps.getProperty(ScheduleConstants.ESA_SUCCEED_COUNT,"0");
	        	if(result.equals(ScheduleConstants.JOB_STATUS_SUCCESSFUL)){
	        		sb.append("ESA Configuration task: ").append(m_TaskName).append( "executed successfully. ").append(count).append(" Domains configured");
	        		setNote(sb.toString());
	        		updateStatus(STATUS_SUCCESSFUL);
	        	} else if(result.equals(ScheduleConstants.JOB_STATUS_PARTIAL)){
	        		sb.append("ESA Configuration task: ").append(m_TaskName).append( "executed partially. ").append(count).append(" Domains configured");
	        		setNote(sb.toString());
	        		updateStatus(STATUS_PARTIAL);
	        	}else if(result.equals(ScheduleConstants.JOB_STATUS_FAILED)){
	        		if(detailProps.getProperty(ScheduleConstants.ESA_DETAILS)!= null){	        			
	        			sb.append("ESA Configuration task: ").append(m_TaskName).append(" ").append(detailProps.getProperty(ScheduleConstants.ESA_DETAILS));
	        		}else{
	        			sb.append("ESA Configuration task: ").append(m_TaskName).append( "executed failed. ").append(count).append(" Domains configured");
	        		}
	        		setNote(sb.toString());
	        		updateStatus(STATUS_FAILED);
	        	}
        	}

        }
        
//        public void onNetworkSucceededAndReset(String network) {
//		if (this.getUpgradeOptions().intValue() == CalixUpgradeTaskUtils.UPGRADE_OPTION_INDEX_DISTRIBUTION_ONLY) {
//			this.onNetworkSucceeded(network);
//		}
//		if (this.getUpgradeOptions().intValue() == CalixUpgradeTaskUtils.UPGRADE_OPTION_INDEX_BOTH 
//				|| this.getUpgradeOptions().intValue() == CalixUpgradeTaskUtils.UPGRADE_OPTION_INDEX_RESET_ONLY) {
//			if (this.getHowToReset().intValue() == CalixUpgradeTaskUtils.HOW_TO_RESET_INDEX_IN_PARALLEL) {
//				this.doResetInParallel(network);
//			} else if (this.getHowToReset().intValue() == CalixUpgradeTaskUtils.HOW_TO_RESET_INDEX_SEQUENTIALLY) {
//				if (!m_networksUpgradeSuccess.contains(network)) {
//					m_networksUpgradeSuccess.add(network);
//				}
////				if (m_networksUpgradeSuccess.size() >= m_networkCount) {
//				if (!resetSequentiallyRunningFlag && isReadyToResetSequentially()) {
//					doResetSequentially(m_networksUpgradeSuccess);
//				} 
////				else if (this.m_networksFailedMap.size() > 0) {
////					onNetworkFailed(network, "Failed to create reset task handlers, because some upgrade task failed:"
////							+ m_networksFailedMap);
////				}
//			}
//		}
//	}
//        private void doResetSequentially(final List readlyResetList) {
//		resetSequentiallyRunningFlag = true;
//		resetThreadPool.execute(new Runnable() {
//			public void run() {
//				while (!readlyResetList.isEmpty()) {
//					String network = null;
//					synchronized (readlyResetList) {
//						if (!readlyResetList.isEmpty()) {
//							network = (String) readlyResetList.remove(0);
//						}
//					}
//
//					if (network != null) {
//					    if (isSkipedNetwork(network)) {
//					        onNetworkSkipped(network,
//                                    "disconnected with auto-connect disabled,skip this network.");
//                            continue;
//                        }
//						DeviceChildTask child = getResetSoftwareChildTaskHandler();
//						if (child != null) {
//							child.setNetwork(network);
//							try {
//								child.run();
//							} catch (Throwable e) {
//								log.error("Faild to reset the network:" + network, e);
//								onNetworkResetFailed(network, "Faild to reset the network:" + e.getMessage());
//							}
//						} else {
//							onNetworkResetFailed(network, "Failed to create reset task handlers");
//						}
//
//						if (getAbortResetFailed().intValue() == ABORT_RESET_FAILED_SELECTED) {
//							if (m_errorStatusMap.get(network) != null) {								
//								while (!readlyResetList.isEmpty()) {
//									synchronized (readlyResetList) {
//										if (!readlyResetList.isEmpty()) {
//											network = (String) readlyResetList.remove(0);
//										}
//									}
//									onNetworkAborted(network);
//									log.warn("Network [" + network + "] reset aborted due to previous failure");
//								}
//								break;
//							}
//						}
//					}
//				}
//				resetSequentiallyRunningFlag = false;
//			}
//		});
//	}
	private boolean isSkipedNetwork(String network) {
        if (network.startsWith("AEONT-")) {
            return false;
        }
		SouthboundSession m_session = SessionManager.getInstance()
				.getSouthboundSession(network);
		if ( (m_session == null || m_session.getEMSNetwork() == null)) {
			/*
			 * log.warn("There is no southbound sesson for network: " +
			 * network); onNetworkFailed(network, "Network no longer exist.");
			 */
			return false;
		}
		if (!m_session.isConnected()
				&& m_session.getEMSNetwork().m_AutoConnect == 0) {
			return true;
		}
		return false;
	}

	static class B6ScheduledTaskThreadPool {
		private static B6ScheduledTaskThreadPool instance;

		// thread pool
		private static final long KEEP_ALIVE_IN_SEC = 600L;
		private int poolSize = 20;

		private ExecutorService threadPool;

        private static final String B6SCHEDULE_MAX_POOL_SIZE_TAG = "/ScheduledTask/B6ScheduledTask/B6ThreadPoolSize";

		public static synchronized B6ScheduledTaskThreadPool getInstance() {
			if (instance == null) {
				instance = new B6ScheduledTaskThreadPool();
			}
			return instance;
		}

		private B6ScheduledTaskThreadPool() {
            poolSize = CMSConfig.getInstance().getConfigIntVal(B6SCHEDULE_MAX_POOL_SIZE_TAG, poolSize);
			log.info("B6ScheduledTaskThreadPool,  poolSize: " + poolSize + ". ");

			// CMS-11872 by hzhang:
			// change core size from 5 yo 20(poolSize); and change ArrayBlockingQueue to LinkedBlockingQueue
			threadPool = new ThreadPoolExecutor(poolSize, poolSize,
					KEEP_ALIVE_IN_SEC, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>());
		}

		public void run(DeviceChildTask task) {
			threadPool.execute(task);
		}
	}
	
    public Map<String, String> getFailedStatus() {
        return m_networkFailedStatus;
    }
    private transient Map<String,String>m_networkFailedStatus;
    private static final Integer ONE = new Integer(1);
    private static final Integer TWO = new Integer(2);
    /** if needed to change to three, open this line and change the MAX to three
     * Also change runAgain=true when it reaches TWO.
     * */
    //private static final Integer THREE = new Integer(3);
    private static final Integer MAX = TWO;
    /**
     * called by child task when complete one network backup
     * @param network
     */
    
    
    protected boolean logJobInfoStatus() {
		return true;
	}
    
    private boolean resetSequentiallyRunningFlag = false;
    
    private boolean isRetryDone() {
		boolean isRetryDone = true;
		synchronized(m_networksFailedMap) {
			if (false == m_networksFailedMap.isEmpty()) {
				for (Integer count : m_networksFailedMap.values()) {
					if (count == null || count <= MAX_RETRIES) {
						isRetryDone = false;
						break;
					}
				}
			}
		}
		return isRetryDone;
	}
    private void postNetworkComplete(String network) {
//		if (!isRetryDone()) {
//			if (m_networksFailedMap.containsKey(network)) {
//				sleep(30);
//				doExecute();
//			} else {
//				sleep(5);
//			}
//			return;
//		}
//		if (!resetSequentiallyRunningFlag
//				&& this.getHowToReset() != null 
//				&& this.getHowToReset().intValue() == CalixUpgradeTaskUtils.HOW_TO_RESET_INDEX_SEQUENTIALLY
//				&& m_networksUpgradeSuccess.size() > 0
//				&& isReadyToResetSequentially()) {
//			doResetSequentially(m_networksUpgradeSuccess);
//			return;
//		}
//		if (m_networksFailedMap.size() + m_networksSuccess.size() + m_networksIgnored.size() + m_networksSkipped.size()
//				+ m_networksAborted.size() < m_networkCount) {
//			sleep(5);
//			// Do we really need this doExecute() call ???
//			// doExecute();
//			return;
//		}

		// all done. update job status
		updateJobStatus();
	}
    /**
	 * Reset sequentially should start after all networks are processed.
	 * Count all lists, since some networks may be skipped due to network type or 
	 * version mismatch, some networks may fail to distribute file, etc..
	 * @return
	 */
//	private boolean isReadyToResetSequentially() {
//		if (m_networksUpgradeSuccess.size() > 0 && isRetryDone()) {
//			int networkCounts = m_networksUpgradeSuccess.size() 
//					+ m_networksFailedMap.size()
//					+ m_networksSuccess.size()
//					+ m_networksIgnored.size()
//					+ m_networksSkipped.size()
//					+ m_networksAborted.size();
//			if (networkCounts >= m_networkCount) {
//				return true;
//			}
//		}
//		return false;
//	}
    //home need change
   
    public void cancel() {
		if (log.isDebugEnabled()) {
			log.debug("Cancel requested for network upgrade task.");
		}
		if (this.m_TaskType.equals(ConfigUIConstants.IMAGE_UPGRADE)) {
			setNote("Network Upgrade task canceled");
        } else if (this.m_TaskType.equals(ConfigUIConstants.RELOAD_DEVICE)) {
        	setNote("Reload task canceled");
        }else if (this.m_TaskType.equals(ConfigUIConstants.RESYNC_INVENTORY)) {
        	setNote("Resync task canceled");
        }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)) {
        	setNote("GPON ONT Upgrade task canceled");
        }else if (this.m_TaskType.equals(ConfigUIConstants.ONT_IMAGE_UPGRADE)) {
        	setNote("Active Ont Upgrade task canceled");
        }else if (this.m_TaskType.equals(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)) {
        	setNote("GPON Repository Path Config task canceled");
        }
		
		updateStatus(STATUS_CANCELLED);
		isAborted = true;
	}

	public boolean isAborted() {
		return isAborted;
	}
    
        private void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000L);
		} catch (InterruptedException iex) {
			log.warn("Thread interrupted.", iex);
		}
	}
    
    public int getNetworkCount(){
        return m_networkCount;
    }
    
    /**
     * Get Ip Address using network for Bseries device
     * @param note
     */
	public String getBseriesIPAddress(String m_network) {
		String ipAddress = "";
		try {
			SouthboundSession m_session = SessionManager.getInstance().getSouthboundSession(m_network);
			ipAddress = m_session.getEMSNetwork().getIPAddress1();
		} catch (Throwable e) {
			log.warn("B6ScheduledTask.getBseriesIPAddress: Exception when get ip from m_network: " + m_network + ", Exception: " + e);
		}
		return ipAddress;
	}
    
    // END CODE

}
