package com.calix.bseries.gui.panels.scheduler;

import com.calix.ems.gui.EMSGui;
import com.calix.ems.gui.panels.layout.scheduler.CreateScheduledTaskDialog;
import com.calix.ems.gui.panels.layout.scheduler.PMDownloadTaskStatusDialog;
import com.calix.ems.gui.panels.layout.scheduler.ScheduleTaskHelper;
import com.calix.ems.gui.panels.layout.scheduler.ScheduledTaskPanel;
import com.calix.system.gui.components.treetable.JTreeTable;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.util.debug.Code;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Panel for B6 scheduled task.
 * 
 * @author sshi
 * Date: Oct 16, 2007
 * Time: 11:31:44 AM
 */
@SuppressWarnings("serial")
public class B6ScheduledTaskPanel extends ScheduledTaskPanel
{
    public static final String TYPE_NAME = "B6ScheduledTask";
    public static final String NAME_SUFFIX = " Task";
    private static String[] m_displayColumns = {"JobID", "TaskName","TaskType","Status", "CreatedBy", "Start Time", "End Time", "Note"};
    
    @Override
    protected String[] getDisplayColumns() {
        return m_displayColumns;
    }
    
    @Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
    	m_hasCreatePermissions =hasTaskCreatePermission();
    	if(!m_hasCreatePermissions){
         	return new ArrayList<JMenuItem>();
         }
        ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();  // super.buildContextCreateMenuItems();

        
        //B6
        AbstractAction createB6UpgradeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCreate(ScheduleTaskHelper.getNetworkType(ScheduleTaskHelper.DEVICE_TYPE_B6));
            }
        };
        createB6UpgradeAction.putValue(Action.NAME, ScheduleTaskHelper.DEVICE_TYPE_B6 + NAME_SUFFIX);
        JMenuItem createB6UpgradeItem = new JMenuItem(createB6UpgradeAction);
        menuItems.add(createB6UpgradeItem);
       
        return menuItems;
    }

    private void doCreate(int networkType) {
        doCreate(getCreateObject(networkType));
    }

    protected OSBaseObject getCreateObject(int networkType) {
    	CreateScheduledTaskDialog dialog = null;
        if(networkType == ScheduleTaskHelper.getNetworkType(ScheduleTaskHelper.DEVICE_TYPE_B6)){
            dialog = new CreateB6ScheduledTaskDialog(EMSGui.frame, getPanelController(), null, DIALOG_TYPE_CREATE, networkType);
        }
        
        else {
            dialog = new CreateScheduledTaskDialog(EMSGui.frame, getPanelController(), null, DIALOG_TYPE_CREATE, networkType);
            dialog.setVisible(true);
        }
        
        if (dialog.isOKPressed()) {
            return dialog.getObject();
        }
        return null;
    }
    public JTreeTable createTreeTable() {
        class B6ScheduledTaskTreeTable extends CalixBaseTreeTable{
                @Override
            protected void processDoubleClicks(MouseEvent me) {
                try {
                    if (me.getButton() != MouseEvent.BUTTON1)
                        return; // only for left button double click
                    //         if (e.getClickCount() >= 2) {
                    int row = this.rowAtPoint(me.getPoint());
                    int actualRow = this.getActualRow(row);
                    showJob(actualRow);
                } catch (Exception ex) {
                    Code.warning(ex);
                }
            }

            @Override
            protected void processSingleClicks(MouseEvent me) {
                if ((me.getButton() == MouseEvent.BUTTON3) && (me.getClickCount() == 1)) {
                    int row = this.rowAtPoint(me.getPoint());
                    final int actualRow = this.getActualRow(row);
                    final OSBaseObject obj = getObjectAtRow(actualRow);

                    IValue status = obj.getAttributeValue("Status");
                    String jobType = getJobType(obj);
                    if (status != null) {
                        String val = null;
                        try {
                            val = status.convertTo(String.class, "TL1");
                        } catch (MappingException e) {
                            Code.warning("Unable to get PM job status", e);
                        }
                        // Re-run allowed only for failed, partial or cancelled jobs (except ESA configuration, it also for successfull)
                        if ((val != null) &&
                                (val.equalsIgnoreCase("FAILED") || val.equalsIgnoreCase("PARTIAL") || val.equalsIgnoreCase("CANCELLED"))||(val.equalsIgnoreCase("SUCCESSFUL")&&jobType.equalsIgnoreCase("ESA Configuration"))) {
                            JPopupMenu popup = new JPopupMenu();

                            AbstractAction reRunAction = new AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    reRun(e,obj);
                                }
                            };
                            reRunAction.putValue(Action.NAME, "Re-run");
                            JMenuItem menuItem = new JMenuItem(reRunAction);
                            popup.add(menuItem);
                            
                            if (val.equalsIgnoreCase("FAILED") || val.equalsIgnoreCase("PARTIAL")||(val.equalsIgnoreCase("SUCCESSFUL")&&jobType.equalsIgnoreCase("ESA Configuration"))){
                                AbstractAction detailedStatusAction = new AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        showDetailedStatus(e,obj);
                                    }
                                };
                                
                                detailedStatusAction.putValue(Action.NAME, "Detailed Status");
                                menuItem = new JMenuItem(detailedStatusAction);
                                popup.add(menuItem);
                            }
                            popup.show(me.getComponent(), me.getX(), me.getY());
                        } else if ((val != null) &&
                                val.equalsIgnoreCase("INPROGRESS")) {
                            JPopupMenu popup = new JPopupMenu();

                            AbstractAction abortAction = new AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    doAbort(e);
                                }
                            };
                            abortAction.putValue(Action.NAME, "Abort");
                            JMenuItem menuItem = new JMenuItem(abortAction);
                            popup.add(menuItem);
                            popup.show(me.getComponent(), me.getX(), me.getY());
                        }
                    }
                }
            }
        }
        
        return new B6ScheduledTaskTreeTable();
    }
    protected OSBaseObject getObjectAtRow(int row) {
        return super.getObjectAtRow(row);
    }
    protected void doAbort(ActionEvent e) {
        super.doAbort(e);
    }

    protected void reRun(ActionEvent e,OSBaseObject obj) {
        super.reRun(e,obj);
    }
    private void showDetailedStatus(ActionEvent e,OSBaseObject obj) {
        PMDownloadTaskStatusDialog dialog = new PMDownloadTaskStatusDialog(getPanelController(), getRecordType().getTypeName(), obj, new JDialog(), true);
        dialog.setVisible(true);
    }
    protected void showJob(int row) {
        super.showJob(row);
    }
    
    protected String getJobType(OSBaseObject job)
    {
        if (job != null) {
            IValue status = job.getAttributeValue("TaskType");
            if (status != null) {
                try {
                    return status.convertTo(String.class, "TL1");
                } catch (MappingException e) {
                    Code.warning("Unable to get job status", e);
                }
            }
        }
        return "";
    }
    
    @Override
    protected void showJob(OSBaseObject job) {
        int actionType = 0;
        if ((getJobStatus(job).equals("SCHEDULED") || getJobType(job).equals("ESA Configuration")) && m_hasCreatePermissions) {
            actionType = DIALOG_TYPE_UPDATE;
        } else {
            actionType = DIALOG_TYPE_VIEW;
        }

        CreateScheduledTaskDialog dialog = null;
		
        
        //B6
     //   if (ScheduleTaskHelper.getNetworkType(ScheduleTaskHelper.DEVICE_TYPE_B6) == this.getNetworkType(job)){
            dialog = new CreateB6ScheduledTaskDialog(EMSGui.frame, getPanelController(), job, actionType, getNetworkType(job));            
     //   } 
        
//        else {
//            dialog = new CreateScheduledTaskDialog(EMSGui.frame, getPanelController(), job, actionType, getNetworkType(job));
//            ((CreateNetworkUpgradeTaskDialog)dialog).disableTxtFilePathField();
//            dialog.setVisible(true);
//        }

        
        if (actionType == DIALOG_TYPE_UPDATE) {
            if (dialog.isOKPressed()) {
                dialog.populateObjectFromDialog(job);
                updateObject();
            }
        }
    }
}
