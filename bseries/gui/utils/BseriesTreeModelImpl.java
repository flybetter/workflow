package com.calix.bseries.gui.utils;

import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.constants.EventValues;
import com.calix.ems.gui.util.EmsDeviceModuleInitImpl;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.events.DbChangeEvent;
import com.calix.system.gui.exceptions.CalixException;
import com.calix.system.gui.model.BaseCalixNetwork;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.navtree.CalixTreeModelHandler;
import com.calix.system.gui.navtree.CalixTreeModelImpl;
import com.calix.system.gui.navtree.CalixTreeNode;
import com.calix.system.gui.navtree.ITreeModel;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.Config;
import com.objectsavvy.base.util.debug.Code;


import java.util.ArrayList;
import java.util.Collection;

public class BseriesTreeModelImpl extends CalixTreeModelImpl {

    public BseriesTreeModelImpl(CalixTreeModelHandler pHandler) {
        super(pHandler);
    }

    @Override
    public void getStartupModel(CalixTreeNode rootNode) throws CalixException {

    }

    @Override
    public boolean loadChildren(CalixTreeNode pParent) {
        boolean bNodeChanged = false;
        OSBaseObject parentObj = (OSBaseObject) pParent.getUserObject();
        if (parentObj instanceof EMSRegion) {
            // load & add the third party nodes to the region/network group
            boolean activated = false;
            try {
                if ( !m_db.isActive() ) {
                    m_db.begin();
                    activated = true;
                }
                Collection<CalixTreeNode> nodes = getB6Nodes((EMSRegion)parentObj);
                for (CalixTreeNode node: nodes) {
                    pParent.add(node);
                }
                nodes = getB6ChassisNodes((EMSRegion)parentObj);
                for (CalixTreeNode node: nodes) {
                    pParent.add(node);
                }
                bNodeChanged = (nodes.size() > 0);
            } catch (Exception e) {
                Code.warning(e);
            } finally {
                if ( activated )
                    try {
                        m_db.rollback();
                    } catch (Exception ignore) {}
            }
        }else if (parentObj instanceof CalixB6Chassis) {
            // load & add the third party nodes to the region/network group
            boolean activated = false;
            try {
                if ( !m_db.isActive() ) {
                    m_db.begin();
                    activated = true;
                }
                Collection<CalixTreeNode> nodes = getB6Nodes((CalixB6Chassis)parentObj);
                for (CalixTreeNode node: nodes) {
                    pParent.add(node);
                }               
                bNodeChanged = (nodes.size() > 0);
            } catch (Exception e) {
                Code.warning(e);
            } finally {
                if ( activated )
                    try {
                        m_db.rollback();
                    } catch (Exception ignore) {}
            }
        }
        return bNodeChanged;
    }
    

    protected Collection<CalixTreeNode> getB6Nodes(EMSRegion parentRegion) {
        EMSRoot rootNode = EMSInit.getReadonlyEMSRoot(m_db);
        Collection<BaseEMSDevice> B6s = null;
        B6s = parentRegion.getB6Networks(rootNode,false);
        Collection<CalixTreeNode> retVal = new ArrayList<CalixTreeNode>();

        if (B6s != null) {
            for (OSBaseObject b6: B6s)  {
                CalixTreeNode node = createRoot(b6);
                if (node != null) {
                    retVal.add(node);
                }
            }
        }
        return retVal;
    }
    protected Collection<CalixTreeNode> getB6Nodes(CalixB6Chassis parentChassis) {
        EMSRoot rootNode = EMSInit.getReadonlyEMSRoot(m_db);
        Collection<BaseEMSDevice> B6s = null;
        B6s = parentChassis.getB6Networks(rootNode);
        Collection<CalixTreeNode> retVal = new ArrayList<CalixTreeNode>();

        if (B6s != null) {
            for (OSBaseObject b6: B6s)  {
                CalixTreeNode node = createRoot(b6);
                if (node != null) {
                    retVal.add(node);
                }
            }
        }
        return retVal;
    }
    protected Collection<CalixTreeNode> getB6ChassisNodes(EMSRegion parentRegion) {
        EMSRoot rootNode = EMSInit.getReadonlyEMSRoot(m_db);
        Collection<BaseEMSDevice> B6s = null;
        B6s = parentRegion.getB6Chassis(rootNode,false);
        Collection<CalixTreeNode> retVal = new ArrayList<CalixTreeNode>();

        if (B6s != null) {
            for (OSBaseObject b6: B6s)  {
                CalixTreeNode node = createChassisRoot(b6);
                if (node != null) {
                    retVal.add(node);
                }
            }
        }
        return retVal;
    }
    protected CalixTreeNode createChassisRoot(OSBaseObject rootObject) {
        CalixTreeNode node = null;
        try {
            CalixB6Chassis emsNetwork = (CalixB6Chassis)rootObject;
//            OSBaseObject calixNetwork = convertToCalixChassis(emsNetwork);
            String id = (String) rootObject.getIdentityValue().convertTo(String.class, "gui");
            node = new CalixTreeNode("B6 CHASSIS:" + emsNetwork.getDisplayName(), id, emsNetwork);
        } catch (Exception ex) {
            Code.warning("Unable to creeate root for Bseries device " + ex );
        }
        return node;
    }
    protected CalixTreeNode createRoot(OSBaseObject rootObject) {
        CalixTreeNode node = null;
        try {
            CalixB6Device emsNetwork = (CalixB6Device)rootObject;
            OSBaseObject calixNetwork = convertToCalixNetwork(emsNetwork);
            String id = (String) rootObject.getIdentityValue().convertTo(String.class, "gui");
            node = new CalixTreeNode("B6:" + id, id, calixNetwork);
        } catch (Exception ex) {
            Code.warning("Unable to creeate root for Bseries device " + ex );
        }
        return node;
    }
    
    public OSBaseObject convertToCalixNetwork(CalixB6Device emsNetwork) throws Exception {
        OSBaseObject calixNetwork = null;
        calixNetwork = (OSBaseObject) TypeRegistry.getInstance().getRecordType("CalixB6Node").newInstance();
        calixNetwork.setAttributeValue("Name", emsNetwork.getNetworkName());
        calixNetwork.setIdentityValue(BaseCalixNetwork.getNetworkAid(emsNetwork.getNetworkName()));

        return calixNetwork;
    }
//    public OSBaseObject convertToCalixChassis(CalixB6Chassis emsNetwork) throws Exception {
//        OSBaseObject calixNetwork = null;
//        calixNetwork = (OSBaseObject) TypeRegistry.getInstance().getRecordType("CalixB6Chassis").newInstance();
//        calixNetwork.setAttributeValue("Name", emsNetwork.getDisplayName());
//        calixNetwork.setIdentityValue(BaseCalixNetwork.getNetworkAid(emsNetwork.getDisplayName()));
//
//        return calixNetwork;
//    }

    // Begin bug CMS-4273 fix by James Wang 20120221
    // To handle tree navigator auto-refresh
    // Do the same thing as what E5 does
    @Override
    public void processDbChangeEvents(final Collection<DbChangeEvent> events) {
        // For certain events on the device root objects we will pass
        // the handling to the "ems" device implementation:
        // e.g i) moving a network between regions, (I don't know how to do it, just keep these code.)
        //     ii) creation/deletion of device roots
        // This is because the impacted nodes are in the "ems" device
        // module.
        Collection<DbChangeEvent> fwdEvents = new ArrayList<DbChangeEvent>();
        Collection<DbChangeEvent> myEvents = new ArrayList<DbChangeEvent>();

        for (DbChangeEvent event : events) {
            RecordType eventObjectType = event.getRecordType();
            if ((event.getId() == DbChangeEvent.QUERY_CHANGED) && 
                    ((eventObjectType == TypeRegistry.getInstance().getRecordType("CalixB6Device")))) {
                //If dbchange event id is QUERY_CHANGED, and type is CalixB6Device, do a specific event.  
                fwdEvents.add(event);
            } else if (event.getId() == EventValues.EVENT_NETWORK_REGION_CHANGED) {
                fwdEvents.add(event);
            } else {
                // Default event action
                myEvents.add(event);
            }
        }

        // Call super, CalixTreeModelImpl
        super.processDbChangeEvents(myEvents);

        if (fwdEvents.size() > 0) {
            //Call dbchange in EMSCalixTreeModelImpl
            ITreeModel emsImpl = m_modelHandlerRef.getDeviceTreeModel(EmsDeviceModuleInitImpl.DEVICE_TYPE_OBJ);
            if (emsImpl != null) {
                emsImpl.processDbChangeEvents(fwdEvents);
            }
        }
    }
    // End bug CMS-4273 fix by James Wang 20120221
    
}
