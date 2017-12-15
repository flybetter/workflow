/*
  This is the confidential, unpublished property of Calix Networks.  Receipt
  or possession of it does not convey any rights to divulge, reproduce, use,
  or allow others to use it without the specific written authorization of
  Calix Networks and use must conform strictly to the license agreement
  between user and Calix Networks.

  Copyright (c) 2000-2011 Calix Networks.  All rights reserved.
*/

package com.calix.bseries.gui;

import javax.swing.ToolTipManager;

import com.calix.ems.gui.TopologyGraph;
import com.calix.ems.gui.TopologyGraphComponent;
import com.calix.ems.gui.navigation.EMSNavigationController;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.system.gui.navtree.CalixSelectionEvent;
import com.objectsavvy.base.gui.graphics.graph.model.GraphModel;
import com.objectsavvy.base.gui.panels.BasePanelController;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.util.debug.Code;
import com.objectsavvy.telco.gui.util.IDisplayManager;
import com.objectsavvy.telco.gui.util.INode;

/**
 * todo Document
 */
@SuppressWarnings("serial")
public class BseriesTopologyGraphComponent extends TopologyGraphComponent
{
    
    private BseriesTopologyGraphImpl m_graphImpl;   
    private OSBaseObject m_selectedObj = null;
    private OSBaseObject m_currentRegion;

    public BseriesTopologyGraphComponent(EMSNavigationController pController, BasePanelController pPanelController, IDisplayManager pMgr) {
        super(pController,pPanelController,pMgr);
    }

    @Override
    public void calixSelectionChanged(CalixSelectionEvent selEvent) {
        INode node = selEvent.getSelection().getFirstSelected();
        if (node != null) {
            Object obj = node.getUserObject();
            if (obj instanceof EMSRegion) {
                regionSelectionChanged((EMSRegion) obj);
                return;
            }else if (obj instanceof CalixB6Chassis) {
                regionSelectionChanged((CalixB6Chassis) obj);
                return;
            } else {
                Code.warning("Unknown selection " + obj);
            }
        }
        setGraphModel(new GraphModel());
    }

    public void regionSelectionChanged(CalixB6Chassis pChassis) {
        saveData();
        m_currentRegion = pChassis;
        m_graphImpl = new BseriesTopologyGraphImpl(pChassis);
        m_graphImpl.setDatabase(getDatabase());
        setGraphModel(new GraphModel(m_graphImpl));
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setEnabled(true);
        // reset selected obj
        m_selectedObj = null;
    }
    protected void saveData() {
        if (getGraphModel().getGraph() instanceof BseriesTopologyGraph) {
        	BseriesTopologyGraph graph = (BseriesTopologyGraph) getGraphModel().getGraph();
            graph.saveData();
        }
    }

    protected void loadData() {
        if (getGraphModel().getGraph() instanceof BseriesTopologyGraph) {
        	BseriesTopologyGraph graph = (BseriesTopologyGraph) getGraphModel().getGraph();
            graph.loadData();
        }
    }
    protected void applyChanges() {
        saveData();
        loadData();
        setGraphModel(new GraphModel(m_graphImpl));
    }
    @Override
    public void setVisible(boolean pVisible) {
        if (Code.debug())
            Code.debug("setVisible(" + pVisible + ")");
        if (isVisible() && !pVisible) {
            saveData();
        } else if (!isVisible() && pVisible) {
            loadData();
            setGraphModel(new GraphModel(m_graphImpl));
        }
        super.setVisible();
    }

}