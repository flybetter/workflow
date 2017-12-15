package com.calix.bseries.gui.panels.scheduler;

import com.calix.ems.EMSInit;
import com.calix.ems.gui.panels.layout.ftp.FtpFileListDialog;
import com.calix.ems.gui.panels.layout.scheduler.CreateScheduledTaskDialog;
import com.calix.ems.gui.panels.layout.scheduler.NonRecurScheduleOptionsPanel;
import com.calix.ems.gui.panels.layout.scheduler.ScheduleOptionsPanel;
import com.calix.ems.gui.panels.layout.scheduler.ScheduleTaskHelper;
import com.calix.ems.gui.panels.layout.scheduler.ScheduledTaskNetworkTreePanel;
import com.calix.ems.gui.panels.layout.scheduler.ScheduledTaskPanel;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.util.CalixMessageUtils;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.objectsavvy.base.gui.panels.BasePanelController;
import com.objectsavvy.base.gui.util.GuiUtil;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.app.scheduler.SchedulerWizard;
import com.occam.ems.client.ccl.ServiceMgmtCustomerIfc;
import com.occam.ems.client.ccl.ServiceMgmtSessionManager;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.client.util.ResourceBundleConstants;
import com.occam.ems.client.util.gui.WaitDialog;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.dataclasses.ServiceMgmtRequestResponse;
import com.occam.ems.common.defines.APINames;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.common.defines.OccamStaticDef;


import javax.swing.*;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import org.netbeans.api.wizard.WizardDisplayer;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: Oct 16, 2007
 * Time: 11:41:11 AM
 * <p/>
 * Task dialog for scheduled network upgrade
 */
@SuppressWarnings("serial")
public class CreateB6ScheduledTaskDialog extends CreateScheduledTaskDialog  implements ServiceMgmtCustomerIfc {

    protected ScheduledTaskNetworkTreePanel m_treePanel;

    private JTextField sftpServer;
    private JTextField sftpUser;
    private JPasswordField sftpPassword;
    private JTextField sftpPath;
    
    private static final String SYSTEM_EMS = "EMS";
    private List<String> selectedRegions;
    private List<String> selectedNetworks;
    private List<String> excludedRegions;
    private List<String> excludedNetworks;
    
    private List<String> selectedNetworksIP;
    
    ResourceBundleUtil labelResource = ResourceBundleUtil.getInstance(ResourceBundleConstants.SCHEDULER_TEXT);
    private int mode = 1;
    private java.util.Map taskAllProp = null;
    private OSBaseObject ftpOptions = null;
    private OSBaseObject job = null;
    private Properties sentProps = null;
    public CreateB6ScheduledTaskDialog(Frame parent, BasePanelController pController, OSBaseObject job, int dialogType, int networkType) {
        super(parent, pController, job, dialogType, networkType);
        this.job = job;
        m_treePanel = new ScheduledTaskNetworkTreePanel(pController.getDatabase(), job, B6ScheduledTaskPanel.TYPE_NAME, dialogType, m_networkType);
        mode = dialogType;      
        if (dialogType != DIALOG_TYPE_CREATE) {
            populateDialogFromObject(job);
        }
        ftpOptions = getEMSBARFtpInfo();
        initTaskProps();
    }

    @Override
    protected String getDialogName() {
        return ScheduleTaskHelper.getDeviceName(m_networkType) + B6ScheduledTaskPanel.NAME_SUFFIX;
    }

    @Override
    protected JPanel getCenterPanel() {
        final JPanel panel1 = new JPanel();

        if (m_type == DIALOG_TYPE_VIEW) {
            GuiUtil.enableDisableContainer(panel1, false);           
        }
        return panel1;
    }

    protected void showFtpFileListDialog() {
        FtpFileListDialog dialog = new FtpFileListDialog(this, true);
        // set host/user/password
        dialog.setHost(this.sftpServer.getText());
        dialog.setUser(this.sftpUser.getText());
        dialog.setPassword(new String(this.sftpPassword.getPassword()));
        dialog.setDisplayFile(true);
        dialog.setVisible(true);
        if (dialog.isOKPressed() && null != dialog.getReturnPath()) {
            this.sftpPath.setText(dialog.getReturnPath());
        }
    }
    
    //Get data from default FTP 
    private void populateDefaultValues() {
//        OSBaseObject ftpOptions = getEMSBARFtpInfo();
//        if (ftpOptions != null) {
//            try {
//
//                IValue value = ftpOptions.getAttributeValue("Host");
//                if (value != null)
//                    this.sftpServer.setText((String) value.convertTo(String.class, "EMS"));
//                value = ftpOptions.getAttributeValue("UserName");
//                if (value != null)
//                    this.sftpUser.setText((String) value.convertTo(String.class, "EMS"));
//                value = ftpOptions.getAttributeValue("Password");
//                if (value != null)
//                    this.sftpPassword.setText((String) value.convertTo(String.class, "EMS"));
//            } catch (MappingException e) {
//                Code.warning("Unable to get the default ftp user info", e);
//            }
//        }
    }

    private OSBaseObject getEMSBARFtpInfo() {
        EMSRoot root = EMSInit.getReadonlyEMSRoot(m_db);
        if (root != null) {
            return (OSBaseObject) root.getAttributeValue("ftpOptions");
        }
        return null;
    }
 
    @Override
    protected void createScheduleOptionsPanel(int dialogType) {
        m_ScheduleOptions = new NonRecurScheduleOptionsPanel();
        m_ScheduleOptions.setNonRecurringOnly();
    }

    @Override
    protected void populateDialogFromObject(OSBaseObject job) {
        this.job = job;    
    }
    private void populateReloadAttributes(OSBaseObject job){
        
        Properties prop = new Properties();
        
        if (taskAllProp.containsKey(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT)){            
            prop.setProperty(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT,(String)taskAllProp.get(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT));
        }
        
        try {
            if (!prop.isEmpty())
                job.setAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO, prop.toString());
        } catch (Exception e) {
            Code.warning("Unable to set upgrade info", e);
        }
    }
    private void populateResyncAttributes(OSBaseObject job){
        
        Properties prop = new Properties();
        
        if (taskAllProp.containsKey(OccamStaticDef.KEY_SAVE_CONFGN)){
       //     boolean saveConfig = ((Boolean)taskAllProp.get(OccamStaticDef.KEY_SAVE_CONFGN)).booleanValue();
            prop.setProperty(OccamStaticDef.KEY_SAVE_CONFGN,((Boolean)taskAllProp.get(OccamStaticDef.KEY_SAVE_CONFGN)).toString());
        }
       
        if (taskAllProp.containsKey(OccamStaticDef.RESYNC_ONT_CFG_WITH_TFTP)){
         //   boolean resyncOntDevice = ((Boolean)taskAllProp.get(OccamStaticDef.RESYNC_ONT_CFG_WITH_TFTP)).booleanValue();
            prop.setProperty(OccamStaticDef.RESYNC_ONT_CFG_WITH_TFTP,((Boolean)taskAllProp.get(OccamStaticDef.RESYNC_ONT_CFG_WITH_TFTP)).toString());
        }
        try {
            if (!prop.isEmpty())
                job.setAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO, prop.toString());
        } catch (Exception e) {
            Code.warning("Unable to set upgrade info", e);
        }
    }
    private void populateImageUpgradeAttributes(OSBaseObject job){
        
        Properties prop = new Properties();
        if (taskAllProp.containsKey(OccamStaticDef.BLC_IMAGE_NAME))
            prop.setProperty(OccamStaticDef.BLC_IMAGE_NAME,(String)taskAllProp.get(OccamStaticDef.BLC_IMAGE_NAME));
        try {
            
            if (taskAllProp.containsKey(OccamStaticDef.BLC_IMAGE_PATH))                
                job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PATH, (String)taskAllProp.get(OccamStaticDef.BLC_IMAGE_PATH));
        } catch (MappingException ex) {
            ex.printStackTrace();
        }    
        if (taskAllProp.containsKey(OccamStaticDef.KEY_SAVE_CONFGN)){
            prop.setProperty(OccamStaticDef.KEY_SAVE_CONFGN,((Boolean)taskAllProp.get(OccamStaticDef.KEY_SAVE_CONFGN)).toString());
        }
        if (taskAllProp.containsKey(OccamStaticDef.KEY_RELOAD_DEVICE)){
            prop.setProperty(OccamStaticDef.KEY_RELOAD_DEVICE,((Boolean)taskAllProp.get(OccamStaticDef.KEY_RELOAD_DEVICE)).toString());
        }
        if (taskAllProp.containsKey(OccamStaticDef.KEY_UPGRADE_FIRMWARE))
            prop.setProperty(OccamStaticDef.KEY_UPGRADE_FIRMWARE,((Boolean)taskAllProp.get(OccamStaticDef.KEY_UPGRADE_FIRMWARE)).toString());
        try {
            if (!prop.isEmpty())
                job.setAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO, prop.toString());
        } catch (Exception e) {
            Code.warning("Unable to set upgrade info", e);
        }
    }
    private void populateGpopnOntImageUpgradeAttributes(OSBaseObject job){
        
        Properties prop = new Properties();
        
        Properties taskProp = (Properties)taskAllProp.get("Task Properties");
        String gponUpgradeType="";
        if(taskAllProp.containsKey(OccamStaticDef.GPON_OLT_UPGRADE_TYPE)){
        	gponUpgradeType=(String)taskAllProp.get(OccamStaticDef.GPON_OLT_UPGRADE_TYPE);
        	prop.setProperty(OccamStaticDef.GPON_OLT_UPGRADE_TYPE,(String)taskAllProp.get(OccamStaticDef.GPON_OLT_UPGRADE_TYPE));
        }
       try {
            
            if (taskAllProp.containsKey(OccamStaticDef.BLC_IMAGE_PATH))                
                job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PATH, (String)taskAllProp.get(OccamStaticDef.BLC_IMAGE_PATH));
        } catch (MappingException ex) {
            ex.printStackTrace();
        }
        if(!gponUpgradeType.equals("")&&gponUpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_AUTO_UPGRADE)){
        	if (taskAllProp.containsKey(OccamStaticDef.GPONOLT_DOWNLOAD_ACTION))
                prop.setProperty(OccamStaticDef.GPONOLT_DOWNLOAD_ACTION,((Boolean)taskAllProp.get(OccamStaticDef.GPONOLT_DOWNLOAD_ACTION)).toString());
            if (taskAllProp.containsKey(OccamStaticDef.GPONOLT_ACTIVATE_ACTION))
                prop.setProperty(OccamStaticDef.GPONOLT_ACTIVATE_ACTION,((Boolean)taskAllProp.get(OccamStaticDef.GPONOLT_ACTIVATE_ACTION)).toString());
            
        }else{
        	 if (taskAllProp.containsKey(OccamStaticDef.BLC_IMAGE_NAME))
                 prop.setProperty(OccamStaticDef.BLC_IMAGE_NAME,(String)taskAllProp.get(OccamStaticDef.BLC_IMAGE_NAME));
             if (taskAllProp.containsKey(OccamStaticDef.KEY_SAVE_CONFGN)){
                 //boolean saveConfig = ((Boolean)taskAllProp.get(OccamStaticDef.KEY_SAVE_CONFGN)).booleanValue();
                 prop.setProperty(OccamStaticDef.KEY_SAVE_CONFGN,((Boolean)taskAllProp.get(OccamStaticDef.KEY_SAVE_CONFGN)).toString());
             }
             if (taskAllProp.containsKey(OccamStaticDef.KEY_RELOAD_DEVICE)){
                 //boolean reloadDevice = ((Boolean)taskAllProp.get(OccamStaticDef.KEY_RELOAD_DEVICE)).booleanValue();
                 prop.setProperty(OccamStaticDef.KEY_RELOAD_DEVICE,((Boolean)taskAllProp.get(OccamStaticDef.KEY_RELOAD_DEVICE)).toString());
             }
             if (taskAllProp.containsKey(OccamStaticDef.KEY_UPGRADE_FIRMWARE))
                 prop.setProperty(OccamStaticDef.KEY_UPGRADE_FIRMWARE,((Boolean)taskAllProp.get(OccamStaticDef.KEY_UPGRADE_FIRMWARE)).toString());
        }
        
        String blcList=taskProp.getProperty(OccamStaticDef.GPON_BLC_LIST);
        if (blcList != null){
            String[] blcListArr = blcList.split(",");
            if (blcListArr.length > 0){
                for (int i = 0; i < blcListArr.length;i++){
                    String blcIP = blcListArr[i];
                    if (blcIP.length() > 0){
                        String blcKey = OccamStaticDef.GPON_ONT_LIST + "_" + blcIP;
                        if (taskProp.containsKey(blcKey)){
                                prop.setProperty(blcKey,(String)taskProp.get(blcKey));
                        } 
                    }
                }
            }
        }
        System.out.println(" populateGpopnOntImageUpgradeAttributes : >> "+prop.toString());
        try {
            if (!prop.isEmpty())
                job.setAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO, prop.toString());
        } catch (Exception e) {
            Code.warning("Unable to set upgrade info", e);
        }
    }
    
    private void populateRepositoryPathConfigAttributes(OSBaseObject job){
    	Properties prop = new Properties();
        Properties taskProp = new Properties();
        taskProp = (Properties)taskAllProp.get("Task Properties");
    	if (taskAllProp.containsKey(OccamStaticDef.GPONOLT_REPOSITRY_PATH))
            prop.setProperty(OccamStaticDef.GPONOLT_REPOSITRY_PATH,(String)taskAllProp.get(OccamStaticDef.GPONOLT_REPOSITRY_PATH));
    	 try {
             if (!prop.isEmpty())
                 job.setAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO, prop.toString());
         } catch (Exception e) {
             Code.warning("Unable to set upgrade info", e);
         }
    }
    
    private void populateESAConfigAttributes(OSBaseObject job){
        Properties prop = new Properties();
        
        if (taskAllProp.containsKey(OccamStaticDef.ESA_HOST_IP)){
            prop.setProperty(OccamStaticDef.ESA_HOST_IP,(String)taskAllProp.get(OccamStaticDef.ESA_HOST_IP));
        }
        if (taskAllProp.containsKey(OccamStaticDef.ESA_SWITCH_LOGIN_NAME)){
        	prop.setProperty(OccamStaticDef.ESA_SWITCH_LOGIN_NAME,(String)taskAllProp.get(OccamStaticDef.ESA_SWITCH_LOGIN_NAME));
        }
        if (taskAllProp.containsKey(OccamStaticDef.ESA_SWITCH_PASSWORD)){
            prop.setProperty(OccamStaticDef.ESA_SWITCH_PASSWORD,(String)taskAllProp.get(OccamStaticDef.ESA_SWITCH_PASSWORD));
        }     
        if (taskAllProp.containsKey(OccamStaticDef.ESA_DB_FILE_NAME)){
            prop.setProperty(OccamStaticDef.ESA_DB_FILE_NAME,(String)taskAllProp.get(OccamStaticDef.ESA_DB_FILE_NAME));
        }
        if (taskAllProp.containsKey(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW)){
               prop.setProperty(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW,((Boolean)taskAllProp.get(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW)).toString());
           }
        if (taskAllProp.containsKey(OccamStaticDef.ESA_DOMAIN_PROPS_MAP)){
            prop.put(OccamStaticDef.ESA_DOMAIN_PROPS_MAP, taskAllProp.get(OccamStaticDef.ESA_DOMAIN_PROPS_MAP));
        }
        try {
            if (!prop.isEmpty())
                job.setAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO, prop.toString());
        } catch (Exception e) {
            Code.warning("Unable to set upgrade info", e);
        }
    }

    private void populateCommonAttributes(OSBaseObject job){
        try {
                
                if (taskAllProp.containsKey(ConfigUIConstants.KEY_TASK_INSTANCE))
                    job.setAttributeValue(ConfigUIConstants.KEY_TASK_INSTANCE, (String)taskAllProp.get(ConfigUIConstants.KEY_TASK_INSTANCE));
                if (taskAllProp.containsKey(ConfigUIConstants.KEY_TASK_NAME))
                    job.setAttributeValue(ConfigUIConstants.KEY_TASK_NAME, (String)taskAllProp.get(ConfigUIConstants.KEY_TASK_NAME));
                if (taskAllProp.containsKey(ConfigUIConstants.KEY_TASK_DESCRIPTION))
                    job.setAttributeValue(ConfigUIConstants.KEY_TASK_DESCRIPTION,(String) taskAllProp.get(ConfigUIConstants.KEY_TASK_DESCRIPTION));
                if (taskAllProp.containsKey(ConfigUIConstants.KEY_FAILURE_ACTION))
                    job.setAttributeValue(ConfigUIConstants.KEY_FAILURE_ACTION, ((Boolean)taskAllProp.get(ConfigUIConstants.KEY_FAILURE_ACTION)).toString());
                
                if (ftpOptions != null){
                    IValue value = ftpOptions.getAttributeValue("Host");
                    if (value != null)
                        job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_SERVER,(String) value.convertTo(String.class, "EMS"));
                    else{
                        job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_SERVER,"10.2.10.135");//Just for testing
                    }
                    value = ftpOptions.getAttributeValue("UserName");
                    if (value != null)
                        job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_USER, (String) value.convertTo(String.class, "EMS"));
                    else
                        job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_USER, "ems");
                    value = ftpOptions.getAttributeValue("Password");
                    if (value != null)
                        job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PASSWORD, (String) value.convertTo(String.class, "EMS"));
                    else
                        job.setAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PASSWORD, "ems");                      
                }
        } catch (Exception e) {
            Code.warning("Unable to set upgrade info", e);
        }
    }
    @Override
    public void populateObjectFromDialog(OSBaseObject job) {
    	if (job != null) {
		    	this.job = job;
		        super.populateObjectFromDialog(job);
        
            
                populateCommonAttributes(job);
                
                String taskType = (String)taskAllProp.get(OccamStaticDef.KEY_TASK_NAME);
                System.out.println("B6 Create Task taskType : "+taskType);
                if (taskType.equalsIgnoreCase(ConfigUIConstants.IMAGE_UPGRADE))
                    populateImageUpgradeAttributes(job); 
                else if (taskType.equalsIgnoreCase(ConfigUIConstants.RESYNC_INVENTORY))
                    populateResyncAttributes(job); 
                else if (taskType.equalsIgnoreCase(ConfigUIConstants.RELOAD_DEVICE))
                    populateReloadAttributes(job); 
                else if(taskType.equalsIgnoreCase(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)){
                	sendRequestToGetGponDevice();
                	populateGpopnOntImageUpgradeAttributes(job);
                	setGponDeviceObjectAttribute(job);
                }else if(taskType.equalsIgnoreCase(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)){
                	sendRequestToGetGponDevice();
                	populateRepositoryPathConfigAttributes(job);
                	//populateGpopnOntImageUpgradeAttributes(job);
                	setGponDeviceObjectAttribute(job);
                }else if(taskType.equalsIgnoreCase(ConfigUIConstants.ESA_CONFIGURATION)){
		    		populateESAConfigAttributes(job);
                }

                if(!taskType.equalsIgnoreCase(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)&&!taskType.equalsIgnoreCase(ConfigUIConstants.ESA_CONFIGURATION)){    
                    if (taskAllProp.get(ConfigUIConstants.KEY_TREE_PANEL_INSTANCE) !=  null){
                    	
                        m_treePanel = (ScheduledTaskNetworkTreePanel)taskAllProp.get(ConfigUIConstants.KEY_TREE_PANEL_INSTANCE);
                        m_treePanel.populateObjectFromDialog(job); 
                        if (mode != ScheduledTaskPanel.DIALOG_TYPE_CREATE){
                        	updateSelectedNetworksWithOldList(job);
                        }
                        sendRequestToGetExcludesNetworks();
                        if(excludedNetworks !=null && excludedNetworks.size()>0)
                        	updateExcludedNetworks(job);                        
                    }
                }
        }
    }

	private void updateSelectedNetworksWithOldList(OSBaseObject job) {

		String staticDeviceCount = (String) sentProps
				.get(OccamStaticDef.SCHEDULER_STATIC_DEVICE_COUNT);
		if (staticDeviceCount != null) {
			IValue[] newSelectedNetworksList = null;
			List<String> newSelectedNetworksArryList = new ArrayList<String>();
			IValue[] selectedNetworksList = m_treePanel.getSelectedNetworks();
			
			int staticDevCount = Integer.parseInt(staticDeviceCount);

			for (int j = 0; j < staticDevCount; j++) {
				Object devIp = sentProps
						.get(OccamStaticDef.SCHEDULER_STATIC_DEVICE_PREFIX + j);
				String strDevIp = devIp.toString();
			//	IValue iValDevIP = null;
				boolean alreadyExists = false;
				for (int i = 0; i < selectedNetworksList.length; i++) {
					try {
						//IValueType type = selectedNetworksList[i].getType();
						//iValDevIP = type.convertFrom(strDevIp, null);
						String selNL = selectedNetworksList[i].convertTo(
								String.class, null);
						if (selNL.equals(strDevIp)) {
							alreadyExists = true;
						}
					} catch (MappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (!alreadyExists) {
					newSelectedNetworksArryList.add(strDevIp);
				}
			}

			if (newSelectedNetworksArryList != null) {
				try {
					IValue[] newSelNetLst = m_treePanel.getValueCollection(newSelectedNetworksArryList, "SelectedNetworks");
					newSelectedNetworksList = new IValue[selectedNetworksList.length
							+ newSelNetLst.length];
					for (int i = 0; i < selectedNetworksList.length; i++) {
						newSelectedNetworksList[i] = selectedNetworksList[i];
					}
					int newCount = selectedNetworksList.length;
					for (int i = 0; i < newSelNetLst.length; i++) {
						newSelectedNetworksList[newCount++] = newSelNetLst[i];
					}
					job.setAttributeValue("SelectedNetworks",
							newSelectedNetworksList);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
    
     private void sendRequestToGetGponDevice(){
    	Properties taskProp = (Properties)taskAllProp.get("Task Properties");
    	HashMap<String, String> map = new HashMap<String, String>();
    	if(taskProp.containsKey("GponBLCList")){
    		String blcList=taskProp.getProperty("GponBLCList");
    		map.put("OltIpAddress", blcList);
    		map.put("requestFor", "SelectedNetworks");
    	}
    		
    	ServiceMgmtRequestResponse req = new ServiceMgmtRequestResponse();
        req.setAPIName(APINames.GET_B6_EQUIPMENT_INFO);
        req.setParams(map);
        ServiceMgmtSessionManager.getInstance().placeRequest(req,this);
      WaitDialog.getInstance().setDisplay("Fetching B6 details", "", "Please wait ..");
        System.out.println("Sending request to Get B6 Inventory");
    }
     private void sendRequestToGetExcludesNetworks(){
     	Properties taskProp = (Properties)taskAllProp.get("Task Properties");
     	 System.out.println("sendRequestToGetExcludesNetworks : taskProp : "+taskProp);
     	HashMap<String, String> map = new HashMap<String, String>();
     	if(taskProp.containsKey(OccamStaticDef.EXCLUDED_NETWORKS_LIST)){
     		List blcList=(List)taskProp.get("ExcludedNetworksList");
     		
     		String strBlcIpAddress="";
     		if(blcList!=null){
     			for(int i=0;i<blcList.size();i++){
     				if(i==0)
     					strBlcIpAddress=(String) blcList.get(i);
     				else
     					strBlcIpAddress=strBlcIpAddress+","+(String) blcList.get(i);
     			}
     		}     		
     		map.put("OltIpAddress", strBlcIpAddress);
     		map.put("requestFor", "ExcludesNetworks");
     	}
     		
     	ServiceMgmtRequestResponse req = new ServiceMgmtRequestResponse();
         req.setAPIName(APINames.GET_B6_EQUIPMENT_INFO);
         req.setParams(map);
         ServiceMgmtSessionManager.getInstance().placeRequest(req,this);
       WaitDialog.getInstance().setDisplay("Fetching B6 details", "", "Please wait ..");
         System.out.println("Sending request to Get B6 Inventory");
     }
    private void setGponDeviceObjectAttribute(OSBaseObject job){
    	   try {
			   IValue[] list = m_treePanel.getValueCollection(selectedNetworks,"SelectedNetworks");
			   if(selectedNetworks!=null)
				   job.setAttributeValue("SelectedNetworks", list);
		} catch (Exception e) {
			// TODO: handle exception
		}  
    }
    private void updateExcludedNetworks(OSBaseObject job){
    	IValue[] list = m_treePanel.getExcludedNetworks();
    	IValue[] selectedNetworksList = m_treePanel.getSelectedNetworks();
    	IValue[] removedList = m_treePanel.getValueCollection(excludedNetworks,"ExcludedNetworks");		
		if(selectedNetworksList !=null && removedList !=null){
			int len=0;
			IValue[] newSelectedNetworksList = null;
			List<IValue> newSelectedNetworksArryList =new ArrayList<IValue>();
			for(int i=0;i<selectedNetworksList.length;i++){
				boolean isRemoved= false;
				for(int k=0;k<removedList.length;k++){
					if(selectedNetworksList[i].equals(removedList[k])){
						isRemoved=true;
						break;
					}	
				}
				if(!isRemoved){
					newSelectedNetworksArryList.add(selectedNetworksList[i]);
				}
			}
			if(newSelectedNetworksArryList!=null){
				try {
					newSelectedNetworksList = new IValue[newSelectedNetworksArryList.size()];
					for(int i=0;i<newSelectedNetworksArryList.size();i++){
						newSelectedNetworksList[i]=newSelectedNetworksArryList.get(i);
					}
//					newSelectedNetworksList=(IValue[]) newSelectedNetworksArryList.toArray();
					job.setAttributeValue("SelectedNetworks", newSelectedNetworksList);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		
		
		if(list !=null){
			int cunt=list.length;
			int cunt2=removedList.length;
			IValue[] newList = new IValue[cunt+cunt2];
			int j=0;
			for(int i=0;i<newList.length;i++){
				if(i<list.length){
					newList[j]= list[i];
					j++;
				}
				if(i<removedList.length){
					newList[j]= removedList[i];
					j++;
				}
			}
			try {
				job.setAttributeValue("ExcludedNetworks", newList);
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else{
        	 try {
        		 if(removedList !=null)
        			 job.setAttributeValue("ExcludedNetworks", removedList);
 			} catch (MappingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
    }
   }

    private boolean isIllegalString(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }
    
    @Override
    protected boolean isDialogValid() {
        if (super.isDialogValid()) {
     
            return m_treePanel.isInputValid();
        }
        return false;

    }
    
    protected String versionFormatDesc() {
    	return "&lt;major&gt;.&lt;minor&gt;.&lt;point&gt;";
    }

    protected boolean isVersionNumberValid(String versionStr) {
        StringTokenizer tokenizer = new StringTokenizer(versionStr, ".");
        if(tokenizer.countTokens() != 3) {
            return false;
        }
        if (tokenizer.hasMoreTokens()) {
            try {
                new Integer(tokenizer.nextToken()); //major
                if (tokenizer.hasMoreTokens()) {
                    new Integer(tokenizer.nextToken()); //minor
                    if (tokenizer.hasMoreTokens()) {
                        new Integer(tokenizer.nextToken());  //point
                    }
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
    @Override
    protected RecordType getRecordType() {
        return TypeRegistry.getInstance().getRecordType(B6ScheduledTaskPanel.TYPE_NAME);
    }
 
    private void populateESATaskInfoFromObject(Properties prop){
        try{
            
            
            String addtlInfo = (String) job.getAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO).convertTo(String.class, "EMS");
            Properties addtlInfoProps = CommonUtil.getESAProperties(addtlInfo);
            prop.setProperty(OccamStaticDef.ESA_HOST_IP,addtlInfoProps.getProperty(OccamStaticDef.ESA_HOST_IP));
            prop.setProperty(OccamStaticDef.ESA_SWITCH_LOGIN_NAME,addtlInfoProps.getProperty(OccamStaticDef.ESA_SWITCH_LOGIN_NAME));
            prop.setProperty(OccamStaticDef.ESA_SWITCH_PASSWORD,addtlInfoProps.getProperty(OccamStaticDef.ESA_SWITCH_PASSWORD));
            prop.setProperty(OccamStaticDef.ESA_DB_FILE_NAME,addtlInfoProps.getProperty(OccamStaticDef.ESA_DB_FILE_NAME));
            prop.setProperty(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW,addtlInfoProps.getProperty(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW));
            prop.put(OccamStaticDef.ESA_DOMAIN_PROPS_MAP, addtlInfoProps.get(OccamStaticDef.ESA_DOMAIN_PROPS_MAP));
         //   prop.setProperty(OccamStaticDef.ESA_DOMAIN_PROPS_MAP,addtlInfoProps.getProperty(OccamStaticDef.ESA_DOMAIN_PROPS_MAP));
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
    
    private void populateReloadPropsFromObject(Properties prop){
        try{
            
            
            String addtlInfo = (String) job.getAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO).convertTo(String.class, "EMS");
            Properties addtlInfoProps = CommonUtil.getProperties(addtlInfo);
            prop.setProperty(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT,addtlInfoProps.getProperty(OccamStaticDef.KEY_RESTORE_CONFIG_DEFAULT));
            
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
    private void populateRepositoryPathObject(Properties prop){
        try{
            
            
            String addtlInfo = (String) job.getAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO).convertTo(String.class, "EMS");
            Properties addtlInfoProps = CommonUtil.getProperties(addtlInfo);
            prop.setProperty(OccamStaticDef.GPONOLT_REPOSITRY_PATH,addtlInfoProps.getProperty(OccamStaticDef.GPONOLT_REPOSITRY_PATH));
            
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
    private void populateResyncPropsFromObject(Properties prop){
        try{
            
            
            String addtlInfo = (String) job.getAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO).convertTo(String.class, "EMS");
            Properties addtlInfoProps = CommonUtil.getProperties(addtlInfo);
            
            prop.setProperty(OccamStaticDef.KEY_SAVE_CONFGN,addtlInfoProps.getProperty(OccamStaticDef.KEY_SAVE_CONFGN));
            prop.setProperty(OccamStaticDef.RESYNC_ONT_CFG_WITH_TFTP,addtlInfoProps.getProperty(OccamStaticDef.RESYNC_ONT_CFG_WITH_TFTP));
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
   
    private void populateImageUpgradePropsFromObject(Properties prop){
        try{
            
            
            String addtlInfo = (String) job.getAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO).convertTo(String.class, "EMS");
            Properties addtlInfoProps = CommonUtil.getProperties(addtlInfo);
            prop.setProperty(OccamStaticDef.BLC_IMAGE_NAME,addtlInfoProps.getProperty(OccamStaticDef.BLC_IMAGE_NAME));
            prop.setProperty(OccamStaticDef.KEY_SAVE_CONFGN,addtlInfoProps.getProperty(OccamStaticDef.KEY_SAVE_CONFGN));
            prop.setProperty(OccamStaticDef.KEY_RELOAD_DEVICE,addtlInfoProps.getProperty(OccamStaticDef.KEY_RELOAD_DEVICE));
            prop.setProperty(OccamStaticDef.KEY_UPGRADE_FIRMWARE,addtlInfoProps.getProperty(OccamStaticDef.KEY_UPGRADE_FIRMWARE));
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
    private void populateGponImageUpgradePropsFromObject(Properties prop){
        try{
            
        	System.out.println("Gpon Ont info : populateGponImageUpgradePropsFromObject ");
            String addtlInfo = (String) job.getAttributeValue(OccamStaticDef.KEY_ADDITIONAL_INFO).convertTo(String.class, "EMS");
            Properties addtlInfoProps = CommonUtil.getProperties(addtlInfo);
            String UpgradeType=(String) addtlInfoProps.getProperty(OccamStaticDef.GPON_OLT_UPGRADE_TYPE);
            System.out.println("Gpon Ont info : UpgradeType: "+UpgradeType);
            if(UpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_AUTO_UPGRADE)){
            	prop.setProperty(OccamStaticDef.GPONOLT_DOWNLOAD_ACTION,addtlInfoProps.getProperty(OccamStaticDef.GPONOLT_DOWNLOAD_ACTION));
            	prop.setProperty(OccamStaticDef.GPONOLT_ACTIVATE_ACTION,addtlInfoProps.getProperty(OccamStaticDef.GPONOLT_ACTIVATE_ACTION));
            }else if(UpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_MANUAL_UPGRADE)){
            	prop.setProperty(OccamStaticDef.BLC_IMAGE_NAME,addtlInfoProps.getProperty(OccamStaticDef.BLC_IMAGE_NAME));
                prop.setProperty(OccamStaticDef.KEY_SAVE_CONFGN,addtlInfoProps.getProperty(OccamStaticDef.KEY_SAVE_CONFGN));
                prop.setProperty(OccamStaticDef.KEY_RELOAD_DEVICE,addtlInfoProps.getProperty(OccamStaticDef.KEY_RELOAD_DEVICE));
                prop.setProperty(OccamStaticDef.KEY_UPGRADE_FIRMWARE,addtlInfoProps.getProperty(OccamStaticDef.KEY_UPGRADE_FIRMWARE));
            }
            prop.setProperty(OccamStaticDef.GPON_OLT_UPGRADE_TYPE,addtlInfoProps.getProperty(OccamStaticDef.GPON_OLT_UPGRADE_TYPE));
           
            IValue[] networks = m_job.getAttributeValue("SelectedNetworks", IValue[].class);                    
            if ((networks != null) && (networks.length != 0)) {
                int deviceCount = networks.length;
                List<String> selectedDev= new ArrayList<String>();
                //prop.put(OccamStaticDef.SCHEDULER_STATIC_DEVICE_COUNT,String.valueOf(deviceCount));
                for (int i = 0; i < networks.length; i++) {
                    String device = networks[i].convertTo(String.class, null);
                    selectedDev.add(device);
                }
                if(UpgradeType.equalsIgnoreCase(OccamStaticDef.GPON_OLT_AUTO_UPGRADE))
                	prop.put(OccamStaticDef.GPON_OLT_LIST, selectedDev);
                
                if(selectedDev!=null && selectedDev.size()>0){
                	HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                    map.put("SelectedNetworks",selectedDev);
                	getB6Inventory(map);
                	String devIp="";
                	int gponDeviceCount=0;
                	if(selectedNetworksIP!=null &&selectedNetworksIP.size()>0){
                		for(int j=0;j<selectedNetworksIP.size();j++){
                			devIp= selectedNetworksIP.get(j);
                			String OntList= (String)addtlInfoProps.getProperty(OccamStaticDef.GPON_ONT_LIST+"_"+devIp);
                			if(OntList !=null && OntList.length()>0){
                				String ontArray[]= OntList.split("#");
                				String gponOnt="";
                				String gponOntDevice="";
                				for(int k=0; k<ontArray.length;k++){
                					gponOnt= ontArray[k];
                					
                					if(!gponOnt.equalsIgnoreCase("ALL"))
                						gponOnt= gponOnt.replace(".","/");
                					gponOntDevice = devIp+"_"+OccamStaticDef.PON_KEYNAME+gponOnt;
                					prop.put(OccamStaticDef.SCHEDULER_STATIC_DEVICE_PREFIX+gponDeviceCount,gponOntDevice);
                					gponDeviceCount++;
                				}
                			}
                		}
                		
                	}
                	prop.put(OccamStaticDef.SCHEDULER_STATIC_DEVICE_COUNT,String.valueOf(gponDeviceCount));
                }
                
            }
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
    private void populateCommonPropsFromObject(Properties prop){
        try{
                    String taskName = (String) job.getAttributeValue(ConfigUIConstants.KEY_TASK_INSTANCE).convertTo(String.class, "EMS");
                    String taskType = (String) job.getAttributeValue(ConfigUIConstants.KEY_TASK_NAME).convertTo(String.class, "EMS");
                    String title = "Edit " + taskType + " Task - " + taskName;
                    prop.put(ConfigUIConstants.TASK_TITLE,title);
                    
                    prop.put(ConfigUIConstants.KEY_GROUP_NAME,taskType);
                    prop.put(ConfigUIConstants.KEY_TASK_NAME, taskType);
                    
                    if (job.getAttributeValue(ConfigUIConstants.KEY_TASK_DESCRIPTION) != null){
                        String taskDesc = (String) job.getAttributeValue(ConfigUIConstants.KEY_TASK_DESCRIPTION).convertTo(String.class, "EMS");
                        prop.put(ConfigUIConstants.KEY_TASK_DESCRIPTION,taskDesc);
                    }
                    prop.put(ConfigUIConstants.KEY_NAME,taskName);
                                        
                    if (job.getAttributeValue(ConfigUIConstants.KEY_FAILURE_ACTION) != null){
                        String taskDesc = (String) job.getAttributeValue(ConfigUIConstants.KEY_FAILURE_ACTION).convertTo(String.class, "EMS");
                        prop.put(ConfigUIConstants.KEY_FAILURE_ACTION,taskDesc);
                    }
                    if (job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_SERVER) != null){
                        String taskDesc = (String) job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_SERVER).convertTo(String.class, "EMS");
                        prop.put(ConfigUIConstants.KEY_SOURCE_SFTP_SERVER,taskDesc);
                    }
                    if (job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_USER) != null){
                        String taskDesc = (String) job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_USER).convertTo(String.class, "EMS");
                        prop.put(ConfigUIConstants.KEY_SOURCE_SFTP_USER,taskDesc);
                    }
                    if (job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PASSWORD) != null){
                        String taskDesc = (String) job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PASSWORD).convertTo(String.class, "EMS");
                        prop.put(ConfigUIConstants.KEY_SOURCE_SFTP_PASSWORD,taskDesc);
                    }
                    if (job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PATH) != null){
                        String taskDesc = (String) job.getAttributeValue(ConfigUIConstants.KEY_SOURCE_SFTP_PATH).convertTo(String.class, "EMS");
                        prop.put(ConfigUIConstants.KEY_SOURCE_SFTP_PATH,taskDesc);
                    }
                    
                    if (taskType .equalsIgnoreCase(ConfigUIConstants.RESYNC_INVENTORY)||taskType .equalsIgnoreCase(ConfigUIConstants.ESA_CONFIGURATION)){
                        m_ScheduleOptions = new ScheduleOptionsPanel();
                        m_ScheduleOptions.addScheduleOptions(ScheduleOptionsPanel.RECURRING_TYPE_DAILY);
                        m_ScheduleOptions.addScheduleOptions(ScheduleOptionsPanel.RECURRING_TYPE_MONTHLY);
                    }else{
                        m_ScheduleOptions = new NonRecurScheduleOptionsPanel();
                        m_ScheduleOptions.setNonRecurringOnly();
                    }
                    if(!taskType.equalsIgnoreCase(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)){  
	                    HashMap<String, List<String>> map = m_treePanel.populateMapFromDialog();
	                    getB6Inventory(map);
	                    if ((selectedNetworksIP != null) && (!selectedNetworksIP.isEmpty())) {
	                    	prop.put(OccamStaticDef.SCHEDULER_STATIC_DEVICE_COUNT,String.valueOf(selectedNetworksIP.size()));
	                    	Iterator<String> itr = selectedNetworksIP.iterator();
	                    	int i = 0;
	                    	while (itr.hasNext()){                       
	                    		String device = itr.next();
	                            prop.put(OccamStaticDef.SCHEDULER_STATIC_DEVICE_PREFIX+i,device);
	                            i++;
	                    	}
	                    }
                    }
                    super.populateDialogFromObject(job);
                    final JPanel schedulerPanel  = getScheduledTimePanel();
                    prop.put(ConfigUIConstants.KEY_SCHEDULE_PANEL_INSTANCE, schedulerPanel);
                    
        } catch (Exception e) {
            Code.warning("Unable to get file information", e);
        }
    }
    private void initTaskProps(){
        
            Properties prop = new Properties();
            
            if (mode == ScheduledTaskPanel.DIALOG_TYPE_CREATE)
                prop.put(ConfigUIConstants.MODE,ConfigUIConstants.CREATE);
            else if (mode == ScheduledTaskPanel.DIALOG_TYPE_UPDATE)
                prop.put(ConfigUIConstants.MODE,ConfigUIConstants.EDIT);
            else if (mode == ScheduledTaskPanel.DIALOG_TYPE_VIEW)
                prop.put(ConfigUIConstants.MODE,ConfigUIConstants.VIEW);
            
            String[] policyNames = new String[6];
            policyNames[0] = ConfigUIConstants.IMAGE_UPGRADE;            
            policyNames[1] = ConfigUIConstants.RELOAD_DEVICE;
            policyNames[2] = ConfigUIConstants.RESYNC_INVENTORY;            
            policyNames[3] = ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE;
//            policyNames[4] = ConfigUIConstants.ONT_IMAGE_UPGRADE;
            policyNames[4] = ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG;
            policyNames[5] = ConfigUIConstants.ESA_CONFIGURATION;
            
            prop.put(ConfigUIConstants.TASK_NAMES, policyNames);
            prop.put(ConfigUIConstants.KEY_TASK_NAME, policyNames[0]);
            
          
            if (mode != ScheduledTaskPanel.DIALOG_TYPE_CREATE){
                try{
                    populateCommonPropsFromObject(prop);                    
                    
                    String taskType = (String)prop.get(ConfigUIConstants.KEY_GROUP_NAME);
                    if (taskType .equalsIgnoreCase(ConfigUIConstants.IMAGE_UPGRADE)){
                        populateImageUpgradePropsFromObject(prop);
                    }else if (taskType .equalsIgnoreCase(ConfigUIConstants.RESYNC_INVENTORY)){
                        populateResyncPropsFromObject(prop);
                    }else if (taskType .equalsIgnoreCase(ConfigUIConstants.RELOAD_DEVICE)){
                        populateReloadPropsFromObject(prop);
                    }else if (taskType .equalsIgnoreCase(ConfigUIConstants.GPON_ONT_IMAGE_UPGRADE)){
                    	populateGponImageUpgradePropsFromObject(prop);
                    }else if (taskType .equalsIgnoreCase(ConfigUIConstants.GPON_REPOSITORY_PATH_CONFIG)){
                    	populateRepositoryPathObject(prop);
                    }else if(taskType .equalsIgnoreCase(ConfigUIConstants.ESA_CONFIGURATION)){
                    	populateESATaskInfoFromObject(prop);
                    }
                } catch (Exception e) {
                    Code.warning("Unable to get file information", e);
                }
            }else{
                String title = ConfigUIConstants.CREATE_TASK_TITLE;   
                prop.put(ConfigUIConstants.TASK_TITLE,title);
            }
 
            if (ftpOptions != null){
                prop.put(ConfigUIConstants.KEY_FTP_OPTIONS, ftpOptions);
            }
            invokeWizard(prop);
    }
    private void invokeWizard(Properties prop){
        
        String title = prop.get(ConfigUIConstants.TASK_TITLE).toString();
        String[] policyNames = (String[])prop.get(ConfigUIConstants.TASK_NAMES);
        String firstStep = labelResource.getString("TaskWizard.Step1");
        String taskName = (String)prop.get(ConfigUIConstants.KEY_GROUP_NAME);
        Object result = null;
        if (policyNames != null)
        {
            
            	result = WizardDisplayer.showWizard(new SchedulerWizard(policyNames,prop,title,firstStep).createWizard(),new Rectangle(20, 20, 600, 450));
                
                if (result != null){
                    m_isOKPressed = true;
                    taskAllProp = (java.util.Map)result;
                    sentProps = prop;
                    if (taskAllProp.get(ConfigUIConstants.KEY_SCHEDULE_PANEL_INSTANCE) !=  null){
                      if (mode == ScheduledTaskPanel.DIALOG_TYPE_CREATE){  
                        m_ScheduleOptions = (ScheduleOptionsPanel)taskAllProp.get(ConfigUIConstants.KEY_SCHEDULE_PANEL_INSTANCE);
                      }
                    }
                }

        }

    }
//    @Override
	
    private void getB6Inventory(HashMap<String, List<String>> map) {
        
        ServiceMgmtRequestResponse req = new ServiceMgmtRequestResponse();
        req.setAPIName(APINames.GET_B6_INVENTORY);
        req.setParams(map);
        ServiceMgmtSessionManager.getInstance().placeRequest(req,this);
        WaitDialog.getInstance().setDisplay("Fetching B6 details", "", "Please wait ..");
        System.out.println("Sending request to Get B6 Inventory");
    }
//    @Override
	@SuppressWarnings("unchecked")
	public void deliverResponse(long requestID,
			ServiceMgmtRequestResponse response) {
		// TODO Auto-generated method stub
		//Equipment b6Equipment;
		Properties devProp = new Properties();
		Properties responceProp = new Properties();
		List<Properties> devPropList = new ArrayList<Properties>();
		try {
			if(response.getAPIName().equals(APINames.GET_B6_EQUIPMENT_INFO)){
				String responceFor="";
				responceProp= (Properties) response.getData();
//				devPropList= (ArrayList<Properties>)response.getData();
				devPropList= (ArrayList<Properties>)responceProp.get("DeviceList");
				responceFor= (String) responceProp.get("requestFor");
//				devProp = (Properties)response.getData();
				   selectedRegions= new ArrayList<String>();
				   selectedNetworks=new ArrayList<String>();
				   excludedRegions=new ArrayList<String>();
				   excludedNetworks=new ArrayList<String>();
	            if (devPropList != null && responceFor.equalsIgnoreCase("SelectedNetworks")){
	            	for(int i=0;i<=devPropList.size();i++){
	            		devProp=(Properties)devPropList.get(i);
	            		selectedRegions.add(devProp.getProperty("reasion"));
		            	selectedNetworks.add(devProp.getProperty("networkName"));
	            	}
	            }else if(devPropList != null && responceFor.equalsIgnoreCase("ExcludesNetworks")){
	            	for(int i=0;i<=devPropList.size();i++){
	            		devProp=(Properties)devPropList.get(i);
	            		excludedNetworks.add(devProp.getProperty("networkName"));
	            	}
	            }
			}else if(response.getAPIName().equals(APINames.GET_B6_INVENTORY)){
	            List b6List = (ArrayList<String[]>)response.getData();	            
	            selectedNetworksIP = new ArrayList<String>();
	            if (b6List != null && b6List.size() > 0){
	                for(int i=0; i< b6List.size(); i++){
	                    String[] deviceInfo = (String[])b6List.get(i);
	                    if (deviceInfo.length == ServiceMgmtConstants.GET_B6_INVENTORY_STRING_LENGTH) {
	                        selectedNetworksIP.add(deviceInfo[0]);
	                    }
	                }
	            }

	        } 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		WaitDialog.getInstance().dispose();
//		dispose();
		//rollbackDb(idb); 
	}
}
