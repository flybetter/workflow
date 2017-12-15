package com.calix.bseries.gui.utils;

import com.calix.bseries.gui.tool.BseriesRangeDiscoveryDialog;
import com.calix.bseries.gui.tool.RangeDiscoverySender;
import com.calix.bseries.util.IPUtils;
import com.calix.system.gui.components.dialogs.CalixCancelProgressDialog;
import com.calix.system.gui.components.menu.CalixMenuMgr.EAppMenuType;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.model.CalixCitGUIHandler;
import com.calix.system.gui.model.DeviceGuiMenuImpl;
import com.calix.system.gui.util.IpAddressUtils;
import com.objectsavvy.base.gui.util.GuiUtil;
import com.objectsavvy.base.gui.util.OSCallbackUtils;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.persistence.TxPersistEvent;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.BaseScalarValue;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.app.security.GenerateManifestFile;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BseriesDeviceGuiMenuImpl extends DeviceGuiMenuImpl {
    
    public BseriesDeviceGuiMenuImpl(CalixCitGUIHandler citHandler) {
        super(citHandler);
    }

    @Override
    public void addDeviceAppMenu(JMenu menu) {
        if (EAppMenuType.TOOLS.getName().equalsIgnoreCase(menu.getName())) {
            AbstractAction action = OSCallbackUtils.getActionObject(this, "rangeDiscovery");
            action.putValue(Action.NAME, "Bseries/E5-306/308/520 Range Discovery...");  // MenuItem name

            JMenuItem rangeDiscoveryMenuItem = new JMenuItem("B-Series Range Discovery...");
            rangeDiscoveryMenuItem.setAction(action);
            menu.add(rangeDiscoveryMenuItem);
            AbstractAction action2 = OSCallbackUtils.getActionObject(this, "manifestFileGenerator");
            action2.putValue(Action.NAME, "ManiFest File Generator");  // MenuItem name

            JMenuItem manifestFileMenuItem = new JMenuItem("ManiFest File Generator");
            manifestFileMenuItem.setAction(action2);
            menu.add(manifestFileMenuItem);
        }
    }
    
    public void rangeDiscovery(ActionEvent event)   {
        new BseriesRangeDiscoveryDialog(GuiUtil.getTopFrame());
    }
    public void manifestFileGenerator (ActionEvent event)   {
   	 new GenerateManifestFile();
   }

}
