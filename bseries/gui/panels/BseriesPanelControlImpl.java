package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.bseries.gui.utils.B6SecurityHelper;
import com.calix.ems.gui.EMSGui;
import com.calix.ems.gui.panels.IEMSDeviceCreation;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.system.gui.components.dialogs.CalixCancelProgressDialog;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.panels.CalixPanelController;
import com.calix.system.gui.panels.DevicePanelControlImpl;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.WorkController;
import com.objectsavvy.base.util.debug.Code;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

public class BseriesPanelControlImpl extends DevicePanelControlImpl implements
        IEMSDeviceCreation {

    public BseriesPanelControlImpl(CalixPanelController panelController) {
        super(panelController);
    }

    @Override
    public boolean canCreateUnderRegion() {
        return false;
    }

    @Override
    public boolean canCreateUnderNetworkGroup() {
        return true;
    }
    
    @Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
    	
        ArrayList<JMenuItem>  menuItems = super.buildContextCreateMenuItems();
        
    	if(!B6SecurityHelper.isCreate()) {
    	    return menuItems ;
    	  }
    	
        if (m_panelControllerRef.getSelection().getSelectedObjects().isEmpty())
    		return menuItems;
        
        Object object = m_panelControllerRef.getSelection().getSelectedObjects().iterator().next();
        if (object instanceof CalixB6Chassis)
            return menuItems;
        AbstractAction newNetworkAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createB6Network(TypeRegistry.getInstance().getRecordType(CalixB6Device.TYPE_NAME));
            }
        };
        newNetworkAction.putValue(Action.NAME, "B6");
        JMenuItem newNetworkItem = new JMenuItem(newNetworkAction);
        menuItems.add(newNetworkItem);
        AbstractAction newChassisAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createB6Network(TypeRegistry.getInstance().getRecordType(CalixB6Chassis.TYPE_NAME));
            }
        };
        newChassisAction.putValue(Action.NAME, "B6 Chassis");
        JMenuItem newChassisItem = new JMenuItem(newChassisAction);
        menuItems.add(newChassisItem);
        
        
        return menuItems;
    }
    
    private void createB6Network(RecordType pNetworkType) {
        BseriesCreationPanel networkPanel = new BseriesCreationPanel(m_panelControllerRef.getDatabase(), pNetworkType);
        networkPanel.showDialog();
        while (networkPanel.isOkPressed()) {
            final BaseEMSDevice network = networkPanel.getNetwork();
            final OSBaseObject oegc = networkPanel.getOgc();
            final boolean isExistingOegc = networkPanel.isExistingOgc();
            // create the new B6 network
            try {
                final IDatabase db = CalixCit.getCalixCitInstance().getCreateDatabase();
                if (!db.isActive())
                    db.begin();
                final CalixCancelProgressDialog dialog = new CalixCancelProgressDialog(false, db);
                WorkController wc = EMSGui.getInstance().getWorkerForDb(db);
                wc.invoke(this, new Runnable() {
                    @Override
                    public void run() {
                        dialog.start("Creating Network");
                        try {
                            db.create(network);
                            if(oegc!=null) if(isExistingOegc) db.update(oegc); else db.create(oegc);
                            db.commit();
                        } catch (PersistenceException pex) {
                            Code.warning(pex);
                        } finally {
                            if (dialog.hasErrors())
                                dialog.done("Errors in creating Network");
                            else {
                                dialog.done("Successfully created the Network");
                            }
                        }
                    }
                });
                dialog.pack();
                dialog.setVisible(true);
                if ( !dialog.hasErrors() || dialog.isCancelled() ) {
                    break;
                } else {
                    networkPanel.resetCreate();
                    networkPanel.setVisible(true);
                }
            } catch (Exception ex) {
                Code.warning(ex);
                break;
            }
        }
    }
}
