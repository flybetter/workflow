/*
   This is the confidential, unpublished property of Calix Networks.  Receipt
   or possession of it does not convey any rights to divulge, reproduce, use,
   or allow others to use it without the specific written authorization of
   Calix Networks and use must conform strictly to the license agreement
   between user and Calix Networks.

   Copyright (c) 2007-2010 Calix Networks.  All rights reserved.
 */

package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.app.servicemanagement.BseriesFiberSASummaryScreen;
import com.calix.bseries.gui.app.servicemanagement.BseriesSearchFilter;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.EMSGui;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.components.panels.CalixTabbedPanel;
import com.calix.system.gui.model.BaseCalixNetwork;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.tlv.TLVUtils;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.app.servicemanagement.serviceactivation.FiberSASummaryScreen;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

/**
 * Created for bug 51611 fix
 * To insert different panel after navi-tree panel selection
 * @author jawang
 * 20120118
 */
@SuppressWarnings("serial")
public class B6NetworkTabbedPanel extends CalixTabbedPanel {

    private BseriesNetworkDetailsPanel m_networkTablePanel;
    private JPanel networkPanel = null;

    public B6NetworkTabbedPanel() {
        m_networkTablePanel = new BseriesNetworkDetailsPanel();
    }

    @Override
    public void addPanel(String s, JPanel networkPanel) {
        // Get called at the first time initialize
        this.networkPanel = networkPanel;
        super.addPanel(s, networkPanel);
    }

    @Override
    public void setObjects(Collection<? extends Object> pObjects) {
        try {
            IDatabase db = EMSInit.getReadonlyDatabase();
            if (!db.isActive()) {
                db.begin();
            }
            EMSRoot root = EMSInit.getReadonlyEMSRoot(db);
            Collection<BaseEMSDevice> networks = root.getB6Networks();
            db.rollback();
            // convert the networks to a hashmap of name and network
            HashMap<String, BaseEMSDevice> networksMap = new HashMap<String, BaseEMSDevice>();
            for (Iterator<BaseEMSDevice> iter = networks.iterator(); iter.hasNext();) {
                BaseEMSDevice emsNetwork = iter.next();
                networksMap.put(emsNetwork.getNetworkName(), emsNetwork);
            }
            Code.warning("Network Map size: " + networksMap.size());

            try {
                // if the size of the objects is more than 1, set the Region tab
                if (pObjects != null && pObjects.size() == 1) {
                    // get the object, check if it is CalixNetwork/EMSNetwork
                    // if CalixNetwork, we need to find the corresponding
                    // EMSNetwork
                    Object obj = pObjects.iterator().next();
                    if (obj instanceof BaseCalixNetwork) {
                        BaseCalixNetwork network = (BaseCalixNetwork) obj;
                        String networkName = TLVUtils.extractNetworkComponentString(network.getIdentityValue());

                        // find out the corresponding EMSNetwork by getting EMSRoot
                        if (networksMap.containsKey(networkName)) {
                            BaseEMSDevice emsNetwork = networksMap.get(networkName);
                            // check if the network is connected
                            if (emsNetwork.isConnected()) {
                                //Draw cut-through panel                            	
                            	Map<String,JPanel> networkPanelMap = new LinkedHashMap<String,JPanel>();
                            	networkPanelMap.put("B6",this.networkPanel);
                            	BseriesSearchFilter.getInstance().getPanelMap(emsNetwork,networkPanelMap);                        	
                                reDrawPanel(pObjects,networkPanelMap);
                                return;
                            }
                        }
                    }
                }
            } catch (Exception mex) {
                Code.warning(mex);
            }

            ArrayList<? super OSBaseObject> emsNetworkList = new ArrayList<OSBaseObject>();
            // convert all the network objects to EMSNetwork objects
            for (Iterator<? extends Object> iter = pObjects.iterator(); iter.hasNext();) {
                Object obj = iter.next();
                if (obj instanceof BaseCalixNetwork) {
                    String networkName = TLVUtils.extractNetworkComponentString(((BaseCalixNetwork) obj).getIdentityValue());
                    if (networksMap.containsKey(networkName)) {
                        emsNetworkList.add(networksMap.get(networkName));
                    }
                } else if (obj instanceof BaseEMSDevice) {
                    emsNetworkList.add((BaseEMSDevice) obj);
                }
            }
            m_networkTablePanel.setObjects(emsNetworkList);
            Map<String,JPanel> networkTablePanelMap = new HashMap<String,JPanel>();
            networkTablePanelMap.put("B6",m_networkTablePanel);
            //Draw detail panel
            reDrawPanel(emsNetworkList, networkTablePanelMap);
        } catch (Exception ex) {
            Code.warning(ex);
        }
    }

    /**
     * Redraw panel
     * @Author: jawang
     * @Date: Jan 21, 2012
     */
    private void reDrawPanel(Collection<? extends Object> emsNetworkList,Map<String,JPanel> networkTablePanelMap) {
        // when a component is removed from Tabbed Pane, Swing is calling setVisible on that
        // component. Since Component is a BasePanel, it is generating showHideEvent
        // and Panel Controller is thinking that panel is being visible calling all the necessary API
        // for the panel like setupMenuItem,populateFieldsFromData etc.
        // setVisible(false) is never getting called in this case and these
        // menu items are staying in tact causing all kinds of problems like LogPanel hijacking refresh Menu and
        // refresh doesn't work after that at all etc etc
        // DTS_P00028410
        for (int iCount = 0; iCount < getTabbedPane().getTabCount(); iCount++) {
            Component panel = getTabbedPane().getComponentAt(iCount);
            if (panel instanceof BasePanel) {
                ((BasePanel) panel).resetShowHideListener();
            }
        }
        getTabbedPane().removeAll();
        super.setObjects(emsNetworkList);
        for (Iterator<String> iter = networkTablePanelMap.keySet().iterator(); iter.hasNext();) {
        	String key = (String)iter.next();;
        	JPanel networkPanel = (JPanel)networkTablePanelMap.get(key);
        	super.addPanel(key, networkPanel);   
        }        
       // super.addPanel("B6", networkPanel);        
        super.setAttributes(EMSGui.getInstance().getMainView().getPanelController().getAllAttributes(emsNetworkList));
    }
}
