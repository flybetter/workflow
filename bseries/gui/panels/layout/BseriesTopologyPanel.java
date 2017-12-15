package com.calix.bseries.gui.panels.layout;

import com.calix.ems.gui.RegionCreatePanel;
import com.calix.ems.gui.TopologyGraphComponent;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.system.gui.components.controls.CalixScrollPane;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.IValuePanel;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseAction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import com.calix.bseries.gui.BseriesTopologyGraphComponent;

@SuppressWarnings("serial")
public class BseriesTopologyPanel extends BasePanel implements IValuePanel
{
	BseriesTopologyGraphComponent m_topologyGraphWidget = null;
    RegionCreatePanel m_regionCreatePanel = null;
    CalixScrollPane m_graphPanel = null;

    public BseriesTopologyPanel(BseriesTopologyGraphComponent pGraphWidget) {
        m_topologyGraphWidget = pGraphWidget;
        m_graphPanel = new CalixScrollPane(m_topologyGraphWidget);
        m_graphPanel.setOpaque(false);
        m_graphPanel.getViewport().setOpaque(false);
        m_graphPanel.getViewport().setBorder(null);
        m_graphPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        m_graphPanel.setPreferredSize(new Dimension(600, 500));
        m_regionCreatePanel = new RegionCreatePanel(null, this);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.add(m_graphPanel, BorderLayout.CENTER);
        this.add(m_regionCreatePanel, BorderLayout.SOUTH);
        this.setName("B6 Chassis");
        this.setBorder(null);
    }

    @Override
    public void populateFieldsFromData(IParameterHolder[][] params) throws ValueException {
        m_topologyGraphWidget.setDatabase(getPanelController().getDatabase());
        m_regionCreatePanel.setDatabase(getPanelController().getDatabase());
        if (super.getObjects() != null && super.getObjects().size() > 0) {
            m_topologyGraphWidget.regionSelectionChanged((CalixB6Chassis) super.getObjects().iterator().next());
        }
        m_topologyGraphWidget.setVisible(true);
    }

    @Override
    public void populateDataFromFields(java.util.List<OSBaseAction> pActions) throws ValueException {
        m_topologyGraphWidget.setVisible(false);
    }

    @Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildContextCreateMenuItems();
        menuItems.addAll(m_topologyGraphWidget.buildContextCreateMenuItems());
        return menuItems;
    }

    @Override
    public ArrayList<JMenuItem> buildDeleteMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildDeleteMenuItems();
        menuItems.addAll(m_topologyGraphWidget.buildDeleteMenuItems());
        return menuItems;
    }

    @Override
    public ArrayList<JMenuItem> buildActionMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildActionMenuItems();
        menuItems.addAll(m_topologyGraphWidget.buildActionMenuItems());
        return menuItems;
    }

}
