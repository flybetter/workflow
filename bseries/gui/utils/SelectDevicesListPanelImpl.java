package com.calix.bseries.gui.utils;

import java.awt.Frame;

import org.apache.log4j.Logger;

import com.calix.bseries.gui.panels.InventoryTreeDialog;
import com.occam.ems.client.util.gui.SelectDevicesListPanelIfc;
import com.occam.ems.client.util.gui.SelectDevicesListPanelProxy;

/**
 * User: hzhang
 * Date: 2/22/2012
 */
public class SelectDevicesListPanelImpl implements SelectDevicesListPanelProxy {
private Object treePanelInstance = null;
    private static Logger logger = Logger.getLogger(SelectDevicesListPanelImpl.class);
	private InventoryTreeDialog inventoryDlg = null;
    private static SelectDevicesListPanelImpl instance = new SelectDevicesListPanelImpl();
	public static SelectDevicesListPanelImpl getInstance() {
		return instance;
	}
	
	@Override
	public void initInventoryTreeDialog(SelectDevicesListPanelIfc panel, Frame parent, boolean singleMode) {
		inventoryDlg = new InventoryTreeDialog(panel, parent, singleMode);
                treePanelInstance = inventoryDlg.getTreePanelInstance();;
		inventoryDlg.setVisible(true);
	}
        public Object getTreePanelInstance(){
            return treePanelInstance;
        }
}
