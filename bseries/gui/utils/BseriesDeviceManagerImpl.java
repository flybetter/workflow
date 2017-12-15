package com.calix.bseries.gui.utils;

import com.calix.ems.EMSInit;
import com.calix.ems.gui.DeviceManagerImpl;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.system.gui.exceptions.VersionNotSupportedException;
import com.calix.system.gui.model.BaseCalixNetwork;
import com.calix.system.gui.model.BaseEMSDevice;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;

import java.util.Collection;

public class BseriesDeviceManagerImpl extends DeviceManagerImpl {
    @Override
    public Collection<? extends BaseEMSDevice> loadDevices(OSBaseObject parent) throws PersistenceException {        
        if (parent instanceof EMSRegion) {
            Collection<BaseEMSDevice> B6s = ((EMSRegion)parent).getB6Networks(EMSInit.getReadonlyEMSRoot(parent.getDatabase()), false);
            Collection<BaseEMSDevice> B6Chassis = ((EMSRegion)parent).getB6Chassis(EMSInit.getReadonlyEMSRoot(parent.getDatabase()), false);
            if (!B6Chassis.isEmpty())
                B6s.addAll(B6Chassis);
            return B6s;
        }else if (parent instanceof CalixB6Chassis) {
            Collection<BaseEMSDevice> B6s = ((CalixB6Chassis)parent).getB6Networks(EMSInit.getReadonlyEMSRoot(parent.getDatabase()));            
            
            return B6s;
        }
        
        return super.loadDevices(parent);
    }
    
    @Override
    public BaseCalixNetwork convertFromBaseEMSDevice(BaseEMSDevice deviceObj) throws Exception {
        if(deviceObj != null) {
            BaseCalixNetwork network = (BaseCalixNetwork) TypeRegistry.getInstance().getRecordType("BaseCalixNetwork").newInstance();
            network.setName(deviceObj.getNetworkName());
            network.setIdentityValue(BaseCalixNetwork.getNetworkAid(deviceObj.getName()));
            return network;
        } else {
        	return null;
        }
    }
    
    // Begin bug CMS-5079 fix by James Wang 20120223
    // To provide feature of B6 connection and disconnection from Topology panel
    @Override
    public void connectDisconnectDevice(BaseEMSDevice device, IDatabase db, String action) throws PersistenceException, VersionNotSupportedException {
        super.connectDisconnectDevice(device, db, action, false);
    }
    // End bug CMS-5079 fix by James Wang 20120223
    
}
