package com.calix.bseries.gui.utils;

import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.bseries.gui.navtree.BseriesDeviceFilterImpl;
import com.calix.bseries.gui.navtree.BseriesNavControllerImpl;
import com.calix.bseries.gui.panels.BseriesPanelControlImpl;
import com.calix.bseries.gui.panels.BseriesWebCutThroughDialog;
import com.calix.bseries.gui.visual.networkgroup.BseriesEthernetPluginApplication;
import com.calix.ems.gui.constants.EventValues;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.connection.NetworkInfo;
import com.calix.system.gui.cutthrough.CutThroughTelnetHelper;
import com.calix.system.gui.database.IApplicationHandler;
import com.calix.system.gui.database.TLVDatabase;
import com.calix.system.gui.events.ActiveDbEvent;
import com.calix.system.gui.events.DbChangeEvent;
import com.calix.system.gui.events.DbChangeEventDispatcher;
import com.calix.system.gui.events.IActiveDbEventListener;
import com.calix.system.gui.events.IEventNotificationFilter;
import com.calix.system.gui.model.*;
import com.calix.system.gui.model.persistence.CommonTLVAddressUtils;
import com.calix.system.gui.model.persistence.TLVAddressUtilsImpls;
import com.calix.system.gui.navtree.*;
import com.calix.system.gui.panels.CalixPanelController;
import com.calix.system.gui.panels.IDevicePanelControl;
import com.calix.system.gui.util.CalixMessageUtils;
import com.calix.system.gui.util.DeviceModuleInitImpl;
import com.calix.system.gui.util.IPluginApplication;
import com.objectsavvy.base.persistence.CacheEngine;
import com.objectsavvy.base.persistence.ILockOwner;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.util.gui.B6SecurityUtil;
import com.occam.ems.client.util.gui.BseriesGenericClientUtil;
import com.occam.ems.client.util.gui.CalixButtonMgrUtil;
import com.occam.ems.client.util.gui.FtpFileListDialogUtil;
import com.occam.ems.client.util.gui.SchedulerPanelUtil;
import com.occam.ems.client.util.gui.SelectDevicesListPanelUtil;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.common.util.ResourceBundleUtil;

import java.util.Collection;

import javax.swing.SwingUtilities;

public class BseriesDeviceModuleInitImpl extends DeviceModuleInitImpl {
	public static int CUT_THROUGH_TELNET_PORT = 9199;

	public static String DEVICE_TYPE_OBJ = "bseries";

	@Override
	public IDeviceNavController getNavControlImpl(NavigationController ctrl) {
		return new BseriesNavControllerImpl(ctrl);
	}

	@Override
	public IDeviceSelectionFilter getSelectionFilterImpl() {
		return new BseriesDeviceFilterImpl();
	}

	@Override
	public ITreeModel getNavTreeModelImpl(CalixTreeModelHandler ctrl) {
		return new BseriesTreeModelImpl(ctrl);
	}

	@Override
	public IDevicePanelControl getPanelControlImpl(CalixPanelController ctrl) {
		return new BseriesPanelControlImpl(ctrl);
	}


	@Override
	protected void addTLVAddressUtils() {
		TLVAddressUtilsImpls.getInstance().addTlvAddressUtils(BseriesDeviceModuleInitImpl.DEVICE_TYPE_OBJ, CommonTLVAddressUtils.getInstance());
	}

	@Override
	protected void initPrePlugin() {
		initBseriesPanels();
		registerBseriesFunctions();
	}

	private void registerBseriesFunctions() {
		SelectDevicesListPanelUtil.getInstance().setSelectDevicesListPanelProxy(SelectDevicesListPanelImpl.getInstance());
                SchedulerPanelUtil.getInstance().setSchedulerPanelProxy(SchedulerPanelImpl.getInstance());
                FtpFileListDialogUtil.setProxy(new FtpFileListDialogImpl());
                CalixButtonMgrUtil.setProxy(new CalixButtonMgrImpl());
                BseriesGenericClientUtil.setProxy(new BseriesGenericClientImpl());
	}

	private void initBseriesPanels() {
                ResourceBundleUtil rsb_util = ResourceBundleUtil.getInstance(ResourceBundleUtil.UTIL_TEXT);
                System.setProperty("WizardDisplayer.default","com.occam.ems.client.util.gui.wizard.OccamWizardDisplayerImpl");
                System.setProperty("wizard.sidebar.image",rsb_util.getString(ConfigUIConstants.WIZARD_LEFT_PANEL_IMAGE));	
                
                B6SecurityUtil.setB6SecurityUtilProxy(new B6SecurityUtilImpl());
	}

	@Override
	public Collection<String> getDevicePlatforms() {
		return CalixCit.getCalixCitInstance().getConfig().getDevicePlatforms(getDeviceType());
	}

	@Override
	public IPluginApplication getPluginApplication(IApplicationHandler.Types pType, IApplicationHandler pHandler) {
		if (IApplicationHandler.Types.EthernetTopology.equals(pType)) {
			return new BseriesEthernetPluginApplication(getDeviceType(), pHandler);
		}
		return super.getPluginApplication(pType, pHandler);
	}

	@Override
	public IDeviceManager getDeviceManagerImpl() {
		return new BseriesDeviceManagerImpl();
	}

	@Override
	public IDeviceGuiMenu getDeviceGuiMenuImpl(CalixCitGUIHandler citHandler) {
		return new BseriesDeviceGuiMenuImpl(citHandler);
	}

	@Override
	public String getDeviceType() {
		return DEVICE_TYPE_OBJ;
	}

	@Override
	public boolean isSupportedDeviceType(IValue device) {
		IValueType pType = TypeRegistry.getInstance().getType("DeviceType");
		IValue b6IValue = null;
		try {
			b6IValue = pType.convertFrom("B6", DomainValues.DOMAIN_GUI);
		} catch (MappingException e) {
			Code.warning(e.getMessage(),e);
		}
		return (b6IValue != null && b6IValue.sameValue(device));
	}

	public void cutThroughTL1(final BaseEMSDevice network, final int cutThroughMode) {
		if ( !network.isConnected() ){
			CalixMessageUtils.showErrorMessage("Cut-through is only allowed for connected devices, Please connect it first.");
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CalixCit guiInst = CalixCit.getCalixCitInstance();
				String cmsHost = guiInst.getConnectionInfo().getConnection().getTargetHost();
				int ctPort = CUT_THROUGH_TELNET_PORT;
				boolean isSecure = guiInst.getConnectionInfo().getConnection().isSecure();
				if (isSecure) {
					try {
						cmsHost = "127.0.0.1";
						ctPort = createSshTunnel(guiInst, ctPort);
					} catch (Exception e) {
						Code.warning("Unable to open SSH tunnel", e);
					}
				}
				CutThroughTelnetHelper.multiLaunchTL1(guiInst.getLoginUsername(), guiInst.getLoginPassword(),
						cmsHost, ctPort, network, isSecure, cutThroughMode);
			}
		});
	}
	
    @Override
    public void postInitDevicePlugin() {
        registerListeners();
    }
    
    protected void registerListeners() {
    	BseriesNotificationListener userListener = new BseriesNotificationListener();
        registerSystemFilterListener(userListener, userListener);
        BseriesChangeListener bseriesChangeListener = new BseriesChangeListener();
        registerSystemFilterListener(bseriesChangeListener, bseriesChangeListener);
    }

	private int createSshTunnel(CalixCit guiInst, int remotePort) throws Exception {
		return guiInst.getConnectionInfo().getConnection().getSingleSocketPortNo(remotePort);
	}
    
    @Override
    public ITreeModel getSecurityRegionTreeModelImpl(CalixTreeModelHandler ctrl) {
        return new BseriesTreeModelImpl(ctrl);
    }
    
    // Begin bug 51355 fix by James Wang 20111219
    public void cutThroughWeb(final BaseEMSDevice network) {
        if ( !network.isConnected() ){
            CalixMessageUtils.showErrorMessage("Cut-through is only allowed for connected devices, Please connect it first.");
            return;
        }
        
        BseriesWebCutThroughDialog dialog = new BseriesWebCutThroughDialog(CalixCit.getCalixCitInstance().getMainView(),network);
        dialog.setRunning(true);
    }
    // End bug 51355 fix by James Wang 20111219
    /**
     * This listener is used to listen for modify events on EMSUser to check if parent Group has changed.
     * If changed, it generates a user Event  EVENT_USER_PARENTGROUP_CHANGED for Security Tree to update
     * Also looks for changes to EMSRegion to see if parent Region has changed. If yes, generates a user Event
     */
    class BseriesChangeListener implements IEventNotificationFilter, IActiveDbEventListener {
        protected ILockOwner m_lockOwner = new TLVDatabase.DbLockOwner();

        @Override
        public boolean acceptEvent(ActiveDbEvent pEvent) {
            if (((DbChangeEvent) pEvent).isModifyOp() &&
                    pEvent.getId() == DbChangeEvent.EXTERNAL_UPDATE &&
                    ( ((DbChangeEvent) pEvent).getObject() instanceof CalixB6Device ||
                       ((DbChangeEvent) pEvent).getObject() instanceof CalixB6Chassis ) ) {
                return true;
            }
            return false;
        }

        @Override
        public void processEvents(ActiveDbEvent[] pEvents) {
            CacheEngine engine = TLVDatabase.getEngine();
            if (engine == null) {
                Code.warning("Cannot find engine for network " + NetworkInfo.DEFAULT_CMS_NETWORK);
                return;
            }
            for (int iCount = 0; iCount < pEvents.length; iCount++) {
                DbChangeEvent pEvent = (DbChangeEvent) pEvents[iCount];
                OSBaseObject obj = pEvent.getObject();
                try {
               //     if (engine.isObjectInCache(obj, m_lockOwner)) {
                        IDatabase db = CalixCit.getCalixCitInstance().getReadOnlyDatabase();
                        boolean bActivated = false;
                        if (db == null) {
                            continue;
                        }
                        try {
                            if (!db.isActive()) {
                                db.begin();
                                bActivated = true;
                            }
                            if (obj.getIdentityValue() == null)
                            	continue;

                            OSBaseObject cachedObj =  db.load((RecordType) obj.getType(), obj.getIdentityValue(), IDatabase.ReadOnly);
                            if (cachedObj == null) {
                                continue;
                            }
                            if ( cachedObj instanceof CalixB6Device ) {
                            	CalixB6Device device = (CalixB6Device)obj;
                            	CalixB6Device cachedDevice = (CalixB6Device)cachedObj;
                            	if (cachedDevice.getParentRegionByName() != null &&
                                        !cachedDevice.getParentRegionByName().equals(device.getParentRegionByName())) {
                                    // create a DbChg Event of type PARENTGROU_CHANGED
                                    DbChangeEventDispatcher.getInstance().addDbChangeEvent(DbChangeEvent.createUserEvent(EventValues.EVENT_REGION_PARENT_CHANGED, device));
                                }

                            } else if ( cachedObj instanceof CalixB6Chassis ) {
                            	CalixB6Chassis chassisObj = (CalixB6Chassis)obj;
                            	CalixB6Chassis cachedChassis = (CalixB6Chassis)cachedObj;
                                if (cachedChassis.getParentRegionByName() != null &&
                                        !cachedChassis.getParentRegionByName().equals(chassisObj.getParentRegionByName())) {
                                    // create a DbChg Event of type PARENTGROU_CHANGED
                                    DbChangeEventDispatcher.getInstance().addDbChangeEvent(DbChangeEvent.createUserEvent(EventValues.EVENT_REGION_PARENT_CHANGED, chassisObj));
                                }

                            }
                        } catch (Exception ex) {
                            Code.warning(ex);
                            continue;
                        } finally {
                            if (bActivated) {
                                try {
                                    db.rollback();
                                } catch (Exception ignore) {

                                }
                            }
                        }

                  //  }
                } catch (Exception ex) {
                    Code.warning(ex);
                }
            }
        }
    }
}
