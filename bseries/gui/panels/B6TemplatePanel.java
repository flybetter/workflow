/*
  This is the confidential, unpublished property of Calix Networks.  Receipt
  or possession of it does not convey any rights to divulge, reproduce, use,
  or allow others to use it without the specific written authorization of
  Calix Networks and use must conform strictly to the license agreement
  between user and Calix Networks.

  Copyright (c) 2008-2011 Calix Networks.  All rights reserved.
*/
package com.calix.bseries.gui.panels;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import com.calix.system.common.constants.CalixConstants;
import com.calix.system.gui.components.menu.CalixForcedDeleteAttributeMenuAction;
import com.calix.system.gui.components.menu.CalixMenuMgr;
import com.calix.system.gui.components.panels.CalixContainerPanel;
import com.calix.system.gui.components.panels.CalixDefaultEditableTablePanel;
import com.calix.system.gui.components.panels.CalixFormGeneratorPanel;
import com.calix.system.gui.panels.CalixPanelController;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.ObjectSelectionEvent;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.util.debug.Code;

@SuppressWarnings("serial")
public abstract class B6TemplatePanel extends CalixContainerPanel
{
    public B6TemplatePanel() {
        try {
            initialize();
        } catch (Exception ex) {
            Code.warning(ex);
        }
        setOpaque(false);
    }

    void initialize() throws Exception {
        super.initialize(getTablePanelComponent(), getFormPanelComponent());
        getObjectSelectionEventSupport().addObjectSelectionListener(this);
    }

    protected BasePanel getFormPanelComponent() {
        return new CalixFormGeneratorPanel(CalixConstants.DEFAULT_FORM_WIDTH);
    }

    protected BasePanel getTablePanelComponent() {
        return new CalixDefaultEditableTablePanel();
    }

    @Override
    public void objectSelectionChanged(ObjectSelectionEvent selEvent) {
        ((CalixPanelController) getPanelController()).possiblySetupMenus(this);
    }

    /*@Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildContextCreateMenuItems();
        menuItems.add(new JMenuItem(getGlobalEx1ProfileCreateAction()));
        return menuItems;
    }*/

    /*@Override
    public ArrayList<JMenuItem> buildDeleteMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildDeleteMenuItems();
        if (CalixMenuMgr.haveSelections(this)) {
            menuItems.add(new JMenuItem(getGlobalEx1ProfileDeleteAction()));
        }
        return menuItems;
    }*/

  /*  @Override
    public ArrayList<JMenuItem> buildActionMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildActionMenuItems();
        if (CalixMenuMgr.haveSelections(this)) {
        	OSBaseObject[] selectedItems = getCurrentObjects();
        	for(Object item :selectedItems){
        		try {
        			Integer value=((OSBaseObject)item).getAttributeValue("Enabled", Integer.class);
					if( value ==null || 0 == value.intValue()){
						return menuItems;
						}
				} catch (Exception e) {
					Code.warning("Get Enabled attribute failed.");
				}
        	}
            menuItems.addAll(GlobalEx1ProfilePanelSupport.instance.buildActionMenuItems(this));
        }
        return menuItems;
    }*/

    protected abstract B6TemplateCreateAction getGlobalEx1ProfileCreateAction();

    private AbstractAction getGlobalEx1ProfileDeleteAction() {
        return new CalixForcedDeleteAttributeMenuAction(
            "Delete " + getMenuActionName() + " Dialog",
            getMenuActionName(),
            (CalixPanelController) getPanelController(),
            this,
            null,
            (RecordType) m_type,
            "IncludeForced");
    }

    protected abstract String getMenuActionName();

    /*public void detailsAction(final ActionEvent evt) {
        GlobalEx1ProfilePanelSupport.instance.detailsAction(evt, getCurrentObjects(), this);
    }

    public void syncAction(final ActionEvent evt) {
        GlobalEx1ProfilePanelSupport.instance.syncAction(evt, getCurrentObjects(), this);
    }*/
    
    @Override
    public boolean isflipViewAllowed() {
        return false;
    }
	@Override
    public void populateFieldsFromData(IParameterHolder[][] params) throws ValueException {
		super.populateFieldsFromData(params);
		showMultiSelectPanel(true);
		firePanelSelectionChanged();
	   }
	   

}
