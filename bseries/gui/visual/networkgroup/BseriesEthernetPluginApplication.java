package com.calix.bseries.gui.visual.networkgroup;

import java.awt.Image;
import java.util.Collection;

import javax.swing.SwingUtilities;

import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.bseries.gui.visual.application.BseriesTopologyElement;
import com.calix.bseries.gui.visual.components.BseriesResource;
import com.calix.ems.gui.visual.networkgroup.NeEthernetInfoPluginApplication;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.system.gui.components.controls.CalixProgressWindow;
import com.calix.system.gui.database.IApplicationHandler;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.visual.application.VisualConstants;
import com.calix.system.gui.visual.element.VElement;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TreeValueType;
import com.objectsavvy.base.util.debug.Code;

public class BseriesEthernetPluginApplication extends NeEthernetInfoPluginApplication {
    
    public BseriesEthernetPluginApplication(String pDeviceType,
            IApplicationHandler pApplicationHandler) {
        super(pDeviceType, pApplicationHandler);
    }

    @Override
    public String getUserDeviceString() {
        return "B6";
    }

    @Override
    public Image getDeviceIcon(BaseEMSDevice pShelf) {
    	if (pShelf instanceof CalixB6Device){
    		return BseriesResource.bseriesDeviceImage;
    	}else if (pShelf instanceof CalixB6Chassis){
    		return BseriesResource.bseriesChassisImage;
    	}
    	return BseriesResource.bseriesDeviceImage;
    }

    @Override
    public String getInnerElementString(BaseEMSDevice pShelf) {
    	if (pShelf instanceof CalixB6Device){
    		return getUserDeviceString();
    	}else if (pShelf instanceof CalixB6Chassis){
    		return "";
    	}
        return getUserDeviceString();
    }
    
    @Override
    public String getEthernetInfoVlansAttributeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEthernetInfoInterfacesAttributeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEthernetInfoVlanInterfacesAttributeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TreeValueType getVlanTreeType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVlanTreeComponent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RecordType getVlanType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVlanDescriptionAttributeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isVlanEditable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public RecordType getVlanIfType() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void setupVisuals(final Collection<OSBaseObject> pObjects, final CalixProgressWindow pProgressWindow) {
        m_progressWindow = pProgressWindow;
        for (final OSBaseObject pObject : pObjects) {
            CalixCit.getCalixCitInstance().getWorkerForDb(m_applicationHandler.getDatabase(m_deviceType)).invoke(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        if (m_progressWindow != null) {
                            m_progressWindow.setMessage("Loading " + getUserDeviceString() + " data ...");
                            m_progressWindow.resetTicks();
                            m_progressWindow.showIt();
                        }
                        if (pObject instanceof EMSRegion){
                            EMSRegion region = (EMSRegion) pObject;
                            // hack, why not in progress?
                            if (!m_applicationHandler.getDatabase(m_deviceType).isActive()) {
                                m_applicationHandler.getDatabase(m_deviceType).begin();
                            }
                            region = (EMSRegion) m_applicationHandler.getDatabase(m_deviceType).load(EMSRegion.getRecordType(), region.getIdentityValue());
                            for (BaseEMSDevice device : region.getAllDevicesByType(m_deviceType)) {
                                try {
                                    loadBaseEMSDevice(device);
                                } catch (PersistenceException e) {
                                    Code.warning("Could not load " + device);
                                    Code.warning(e);
                                }
                            }
                        }else {
                            CalixB6Chassis chassis = (CalixB6Chassis) pObject;
                            // hack, why not in progress?
                            if (!m_applicationHandler.getDatabase(m_deviceType).isActive()) {
                                m_applicationHandler.getDatabase(m_deviceType).begin();
                            }
                            chassis = (CalixB6Chassis) m_applicationHandler.getDatabase(m_deviceType).load(CalixB6Chassis.getRecordType(), chassis.getIdentityValue());
                            for (BaseEMSDevice device : chassis.getAllDevicesByType(m_deviceType)) {
                                try {
                                    loadBaseEMSDevice(device);
                                } catch (PersistenceException e) {
                                    Code.warning("Could not load " + device);
                                    Code.warning(e);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Code.warning(e);
                    } finally {
                        if (m_progressWindow != null) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    m_progressWindow.hideIt();
                                }
                            });
                        }
                        m_applicationHandler.endOfData(m_deviceType);
                    }
                }
            });
        }
    }
    protected void loadBaseEMSDevice(BaseEMSDevice pBaseDevice) throws PersistenceException, MappingException {
        BaseEMSDevice emsNetwork = (BaseEMSDevice) m_applicationHandler.getDatabase(m_deviceType).load(
                (RecordType) pBaseDevice.getType(), pBaseDevice.getIdentityValue());
        if (emsNetwork instanceof CalixB6Chassis){
            handleDisconnectedDevice(emsNetwork);
        }else{
            if (emsNetwork.isConnected()) {
                handleBaseEMSDevice(emsNetwork);
            } else {
                handleDisconnectedDevice(emsNetwork);
            }
        }
    }

    protected VElement getElement(BaseEMSDevice pShelf, OSBaseObject pNeEthernetInfo) {
        VElement visualElement = new BseriesTopologyElement(pShelf, getDeviceIcon(pShelf), getInnerElementString(pShelf));
        processNeEthernetInfo(visualElement, pNeEthernetInfo);
        return visualElement;
    }
    
    protected void handleDisconnectedDevice(BaseEMSDevice pBaseDevice) {
        VElement visualShelf = new BseriesTopologyElement(pBaseDevice, getDeviceIcon(pBaseDevice), getInnerElementString(pBaseDevice));
        visualShelf.setProperty(VisualConstants.PROPERTY_DISCONNECTED, true);
        visualShelf.setProperty(PROPERTY_DEVICE_PLUGIN, this);
        getVisualApplicationHandler().getVisualData().addElement(visualShelf);
    }
}
