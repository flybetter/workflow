/*
  This is the confidential, unpublished property of Calix Networks.  Receipt
  or possession of it does not convey any rights to divulge, reproduce, use,
  or allow others to use it without the specific written authorization of
  Calix Networks and use must conform strictly to the license agreement
  between user and Calix Networks.

  Copyright (c) 2000-2010 Calix Networks.  All rights reserved.
*/

package com.calix.bseries.gui;

import com.calix.ems.EMSInit;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.graphics.graph.model.BasicGraph;
import com.objectsavvy.base.gui.graphics.graph.model.CompositeNode;
import com.objectsavvy.base.gui.graphics.graph.model.GraphImpl;
import com.objectsavvy.base.gui.graphics.graph.model.Node;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

import java.util.Iterator;

@SuppressWarnings("serial")
public class BseriesTopologyGraph extends BasicGraph
{
    private IDatabase m_database = null;
    private CalixB6Chassis m_chassis = null;
    private GraphImpl m_graphImpl = null;

    // private boolean m_modified = false;

    public BseriesTopologyGraph(GraphImpl graphImpl, CalixB6Chassis chassis) {
        if (Code.debug())
            Code.debug("TopologyGraph - " + chassis + " - selected node");
        m_chassis = chassis;
        m_graphImpl = graphImpl;
    }

    public void setDatabase(IDatabase pDatabase) {
        m_database = pDatabase;
    }

    public void setModified() {
        // m_modified = true;
    }

    BaseEMSDevice getNetworkById(IValue pValue) {
        for (Iterator<BaseEMSDevice> networks = m_chassis.getNetworks().iterator(); networks.hasNext();) {
            BaseEMSDevice cnetwork = networks.next();
            if (pValue.equals(cnetwork.getIdentityValue())) {
                return cnetwork;
            }
        }
        return null;
    }

    void loadData() {
        super.clear();
        if (m_database != null) {
            try {
                if (!m_database.isActive()) {
                    m_database.begin();
                }
                EMSRoot root = EMSInit.getReadonlyEMSRoot(m_database);
                m_chassis = (CalixB6Chassis) m_database.load((RecordType) m_chassis.getType(), m_chassis.getIdentityValue(), IDatabase.ReadOnly);
                setSemanticObject(m_chassis);
                CompositeNode cnode = m_graphImpl.createCompositeNode(m_chassis);
                m_graphImpl.addNode(cnode, this);
                
                for (Iterator<BaseEMSDevice> networks = m_chassis.getAllDevices().iterator(); networks.hasNext();) {
                    BaseEMSDevice cnetwork = networks.next();
                    cnetwork = (BaseEMSDevice) m_database.load((RecordType) cnetwork.getType(), cnetwork.getIdentityValue());
                    Node networkNode = m_graphImpl.createNode(cnetwork);
                    m_graphImpl.addNode(networkNode, cnode);
                }
             
                // add by hongguo end
            } catch (Exception ex) {
                Code.warning(ex);
            }
        }
    }

    void saveData() {
        /*
         * if((m_database != null) && m_database.isActive()) { try {
         * if(m_modified) { if(Code.debug()) Code.debug("Commit changes");
         * m_database.commit(); } else { if(Code.debug())
         * Code.debug("Rollback changes"); m_database.rollback(); } }
         * catch(Exception ex) { Code.warning(ex); } }
         */
    }

    void deleteData(OSBaseObject obj) {
        try {
            if ((m_database != null) && m_database.isActive()) {
                obj = m_database.load((RecordType) obj.getType(), obj.getIdentityValue());
                m_database.remove(obj);
                m_database.commit();
            }
        } catch (Exception e) {
            try {
                m_database.rollback();
            } catch (Exception ee) {
                Code.warning(ee.getMessage(), ee);
            }
            Code.warning("Error deleting Region/Network", e);
            CalixMessageUtils.showErrorMessage("Failed to delete the Region/Network");
        }
    }

}
