package com.calix.bseries.gui.panels.layout;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JMenuItem;

import com.calix.bseries.gui.BseriesTopologyGraphComponent;
import com.calix.ems.gui.EMSGuiUtils;
import com.calix.ems.gui.navigation.EMSNavigationController;
import com.calix.ems.gui.panels.layout.NetworkGroupGmapBrowserPanel;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.model.CalixCitConfig;
import com.calix.system.gui.panels.CalixMainView;
import com.calix.system.gui.visual.components.IToolTipPane;
import com.objectsavvy.base.gui.panels.BaseCompositePanel;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.gui.panels.IShowHideListener;
import com.objectsavvy.telco.gui.util.IDisplayManager;
/**
 * @author azhang
 * Date: Jun 13, 2007
 * Time: 5:15:24 PM
 */
@SuppressWarnings("serial")
public class BseriesCompositePanel extends BaseCompositePanel {
    protected BseriesTopologyPanel m_topologyPanel = null;
    protected BseriesChassisGmapBrowserPanel m_gmapPanel = null;
    protected BseriesTopologyGraphComponent m_graphWidget = null;
    protected boolean m_isInternetConnected = false;

    public BseriesCompositePanel(IToolTipPane pane) {
        initialize(pane);
    }

    protected void initialize(IToolTipPane pane) {
        m_isInternetConnected = EMSGuiUtils.isInternetConnected();
        CalixMainView mainView = CalixCit.getCalixCitInstance().getMainView();
        IDisplayManager displayMgr = CalixCitConfig.getInstance();
        m_graphWidget = new BseriesTopologyGraphComponent((EMSNavigationController) mainView.getNavController(),
                mainView.getPanelController(), displayMgr);
        m_graphWidget.setMinimumSize(new Dimension(100, 100));
        setOpaque(false);
        if (!m_isInternetConnected) {
            m_topologyPanel = new BseriesTopologyPanel(m_graphWidget);
            m_topologyPanel.setName("Chassis View");
            add(m_topologyPanel);

        } else {
            m_gmapPanel = new BseriesChassisGmapBrowserPanel(m_graphWidget);
            m_gmapPanel.setName("Chassis View");
            add(m_gmapPanel);
        }
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public String[] getPanelNames() {
        return new String[]{"Chassis View"};
    }


    @Override
    public BasePanel getPanel(String s) {
        if (m_topologyPanel != null && m_topologyPanel.getName().equals(s)) {
            return m_topologyPanel;
        } else if (m_gmapPanel != null && m_gmapPanel.getName().equals(s)) {
            return m_gmapPanel;
        }
        return null;
    }

    @Override
    public void addShowHideListener(IShowHideListener ishowhidelistener, String s) {
        super.addShowHideListener(ishowhidelistener, s);
        if (m_topologyPanel != null) m_topologyPanel.addShowHideListener(ishowhidelistener, s);
        if (m_gmapPanel != null) m_gmapPanel.addShowHideListener(ishowhidelistener, s);
    }

    @Override
    public void resetShowHideListener() {
        super.resetShowHideListener();
        if (m_topologyPanel != null) m_topologyPanel.resetShowHideListener();
        if (m_gmapPanel != null) m_gmapPanel.resetShowHideListener();
    }

    /**
     * This method is overridden because the super class adds duplicate menu
     * items that the contained panels are providing.
     */
    @Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
        return new ArrayList<JMenuItem>();
    }

}
