package com.calix.bseries.gui.utils;

import com.occam.ems.client.app.configuration.OCMConfigurationScreen;
import com.occam.ems.client.app.dhcpprofilemanagement.DHCPProfileManagerDialog;
import com.occam.ems.client.app.dhcpprofilemanagement.DHCPProfileManagerScreen;
import com.occam.ems.client.app.imagemanagement.OSScreen;
import com.occam.ems.client.app.servicemanagement.bindergroupmanager.BinderGroupManagerScreen;
import com.occam.ems.client.app.servicemanagement.subscribermanagement.SubscriberManagementScreen;
import com.occam.ems.client.util.gui.GponOntInventoryManagerUI;
import com.occam.ems.client.util.gui.ViewGponOntFrame;
import java.util.Properties;

import com.calix.bseries.gui.model.BseriesNotificationObject;
import com.calix.bseries.util.BseriesConstants;
import com.calix.system.gui.events.ActiveDbEvent;
import com.calix.system.gui.events.DbChangeEvent;
import com.calix.system.gui.events.IActiveDbEventListener;
import com.calix.system.gui.events.IEventNotificationFilter;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.occam.ems.client.app.servicemanagement.serviceactivation.DSLSASummaryScreen;
import com.occam.ems.client.app.servicemanagement.serviceactivation.FiberSASummaryScreen;
import com.occam.ems.client.app.servicemanagement.serviceactivation.PWE3SASummaryScreen;
import com.occam.ems.client.app.servicemanagement.serviceactivation.TLSSASummaryScreen;
import com.occam.ems.client.app.servicemanagement.servicedefinition.ServiceDefinitionPanel;
import com.occam.ems.client.app.servicemanagement.servicepackagemanager.ServicePackageManager;
import com.occam.ems.common.CommonUtil;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.xml.message.Notification;
import com.occam.ems.common.xml.message.impl.NotificationImpl;

/**
 * User: hzhang
 * Date: 03/12/2012
 */
public class BseriesNotificationListener implements IEventNotificationFilter, IActiveDbEventListener  {

	@Override
	public void processEvents(ActiveDbEvent[] pEvents) {
		if (pEvents != null) {
			DbChangeEvent event = (DbChangeEvent) pEvents[0];
			BseriesNotificationObject action = (BseriesNotificationObject) event
					.getObject();
			if (action != null) {
				IValue idValue = action
						.getAttributeValue(BseriesNotificationObject.ACTION_MODULEID);
				IValue iPro = action
						.getAttributeValue(BseriesNotificationObject.ACTION_PROPERTYSTRING);
				processNotification(idValue, iPro);
			}
		}
	}
	
	private void processNotification(IValue idValue, IValue iPro) {
		Properties pro = null; 
		if(iPro != null){
			String proString = null;
			try {
				proString = iPro.convertTo(String.class, null);
			} catch (MappingException e) {
				e.printStackTrace();
			}
			pro = CommonUtil.getProperties(proString);			
		}
		Notification notification = new NotificationImpl();                
		notification.setProperties(pro);
		
		if (idValue != null) {
			String moduleId = null;
			try {
				moduleId = idValue.convertTo(String.class, null);
			} catch (MappingException e) {
				e.printStackTrace();
			}                        
			if(moduleId == null){
				return; 
			}
                        notification.setType(moduleId);
                        if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_SERVICE_PACKAGE)){
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_PROFILE_MGMT)){//This is no longer used
//				System.out.println("asl");
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_DSL_SERVICE_ASSOCIATION)){
				DSLSASummaryScreen.getDSLServiceSummaryInstance().notification(0, notification);
                                SubscriberManagementScreen.getInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_TLS_SERVICE_ACTIVATION)){
				TLSSASummaryScreen.getTLSServiceSummaryInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_FIBER_SERVICE_ASSOCIATION)){
				FiberSASummaryScreen.getFiberServiceSummaryInstance().notification(0, notification);
                                SubscriberManagementScreen.getInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_VOICE_SERVICE_ASSOCIATION)){
				DSLSASummaryScreen.getDSLServiceSummaryInstance().notification(0, notification);
                                SubscriberManagementScreen.getInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_DATA_SERVICE)){
				ServiceDefinitionPanel.getDataServicePanel().notification(0, notification);
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_VIDEO_SERVICE)){
				ServiceDefinitionPanel.getVideoServicePanel().notification(0, notification);
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_VOIP_SERVICE)){
				ServiceDefinitionPanel.getVideoServicePanel().notification(0, notification);
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_VOICE_SERVICE)){
				ServiceDefinitionPanel.getVoiceServicePanel().notification(0, notification);
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_PWE3_SERVICE)){
				ServiceDefinitionPanel.getPWE3ServicePanel().notification(0, notification);
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);                
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_PWE3_SUMMARY)){
				PWE3SASummaryScreen.getPWE3ServiceSummaryInstance().notification(0, notification);
                                SubscriberManagementScreen.getInstance().notification(0, notification);				
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_TLS_SERVICE)){
				ServiceDefinitionPanel.getTLSServicePanel().notification(0, notification);
				ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_SUBSCRIBER_ID)){
				SubscriberManagementScreen.getInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_OSMANAGER_ID)){
				OSScreen.getInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_DHCP_PROFILE_MGMT)){
				DHCPProfileManagerScreen.getInstance().notification(0, notification);
                                if (DHCPProfileManagerDialog.getInstance() != null){
                                    DHCPProfileManagerDialog.getInstance().notification(0, notification);
                                }
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_BINDER_GROUP_MANAGER)){
				BinderGroupManagerScreen.getBGManagerInstance().notification(0, notification);
                                ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_OCM_CONFIG)){
				OCMConfigurationScreen.getInstance().notification(0, notification);
                                FiberSASummaryScreen.getFiberServiceSummaryInstance().notification(0, notification);
                                TLSSASummaryScreen.getTLSServiceSummaryInstance().notification(0, notification);
                                ServicePackageManager.getSPMgrPanelInstance().notification(0, notification);
                                PWE3SASummaryScreen.getPWE3ServiceSummaryInstance().notification(0, notification);
                                DHCPProfileManagerScreen.getInstance().notification(0, notification);
			}else if(moduleId.equalsIgnoreCase(OccamStaticDef.MODULE_ID_GPON_ONT)){
				ViewGponOntPopup.getGponOntInvMgrInstance().notification(0, notification);
			}
		}
		
	}

	@Override
	public boolean acceptEvent(ActiveDbEvent pEvent) {
        DbChangeEvent event = (DbChangeEvent)pEvent;
        if((event.getRecordType()!= null) && 
        		(BseriesConstants.BSERIES_NOTIFICATION_OBJECT.equals(event.getRecordType().getTypeName()))){
        	return true;
        }
        return false;
	}
}
