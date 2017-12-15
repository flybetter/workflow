package com.calix.bseries.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.calix.bseries.gui.model.B6FPCMasterTemplate;
import com.calix.bseries.gui.panels.B6FPCMasterTemplateCreateAction.B6FPCMasterTemplateCreateDialog;
import com.calix.system.gui.components.menu.CalixButtonMgr;
import com.calix.system.gui.components.menu.CalixMenuMgr;
import com.calix.system.gui.components.panels.CalixDefaultTablePanel;
import com.calix.system.gui.components.treetable.JTreeTable;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.persistence.OSBaseObject;


public class B6FPCMasterTemplatePanel extends B6TemplatePanel{
	private static String[] m_displayColumns = {"device_host_name", "operation","IPAddress1","TemplateSource", "CMSUserName", "UserIp", "StartTime", "EndTime", "Result",
		"service_type", "dsl_interface_number", "dsl_profile_name", "access_profile_name_int", "service_number_int" ,
		"pvc_service_int", "key_info", "mac_learning_int", "mac_limit_int", "igmp_group_limit_int", "mac_address_int", 
		"ip_address_int", "ipmask_int", "ipdg_int", "key_info_old", "activate_service_int", "activate_dsl_port","tpstc_mode","match_vlan"};
	public B6FPCMasterTemplatePanel() {
		super();	
	}

	protected B6TemplateCreateAction getGlobalEx1ProfileCreateAction() {
		return new B6FPCMasterTemplateCreateAction(getPanelController());
	}

	protected String getMenuActionName() {
		return "B6 FPCMaster Template";
	}
	
	@Override
    public ArrayList<JMenuItem> buildActionMenuItems() {
         CalixButtonMgr.getInstance().disableApplyButton();
         CalixButtonMgr.getInstance().disableRevertButton();
        ArrayList<JMenuItem> menuItems = super.buildActionMenuItems();
        if (CalixMenuMgr.haveSelections(this)) {
        	OSBaseObject[] selectedItems = getCurrentObjects();
        	/*for(Object item :selectedItems){
        		try {
        			Integer value=((OSBaseObject)item).getAttributeValue("Enabled", Integer.class);
					if( value ==null || 0 == value.intValue()){
						return menuItems;
						}
				} catch (Exception e) {
					Code.warning("Get Enabled attribute failed.");
				}
        	}*/
            menuItems.add(new JMenuItem(getDetailAction(selectedItems[0])));
        	
        }
        return menuItems;
    }
	 
	
	
	   final class B6FPCMasterTemplateTablePanel extends CalixDefaultTablePanel {
           @Override
           public JTreeTable createTreeTable() {
               final JTreeTable tree = super.createTreeTable();
               tree.addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseClicked(MouseEvent me) {
                       if ((me.getButton() == MouseEvent.BUTTON3) && (me.getClickCount() == 1)) {
                           int row = tree.rowAtPoint(me.getPoint());
                 
                           JPopupMenu popup = new JPopupMenu();
                           OSBaseObject[] selectedItems = getCurrentObjects();
                           JMenuItem menuItem1 = new JMenuItem(getDetailAction(selectedItems[0]));

                           popup.add(menuItem1);                                                    
                           popup.show(me.getComponent(), me.getX(), me.getY());
                       }
                   }
               });
               return tree;
           }
           
           @Override
           protected String[] getDisplayColumns() {
               return m_displayColumns;
           }
       }
	   
	 private AbstractAction getDetailAction(final OSBaseObject obj){
	        AbstractAction action = null;

	        action = new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					B6FPCMasterTemplateCreateAction b6create = new B6FPCMasterTemplateCreateAction(getPanelController());
					B6FPCMasterTemplateCreateDialog dialog = b6create.new B6FPCMasterTemplateCreateDialog();
					dialog.CreateDetailDialog((B6FPCMasterTemplate) obj);	
	            }
	        };
	        action.putValue(Action.NAME, "DETAIL");
	        return action;
	    }
	 
	 
	@Override
    protected BasePanel getTablePanelComponent() {
        return new B6FPCMasterTemplateTablePanel();
    }

}
