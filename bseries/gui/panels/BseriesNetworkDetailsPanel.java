package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.model.B6CommandResult;
import com.calix.bseries.gui.model.B6CommandResultData;
import com.calix.bseries.gui.model.B6DSLBoundedTemplate;
import com.calix.bseries.gui.model.B6FPCMasterTemplate;
import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.bseries.gui.model.EMSBseriesConnAction;
import com.calix.bseries.gui.model.persistence.tlv.TLVB6CommandActionQueryScope;
import com.calix.bseries.gui.model.persistence.tlv.TLVB6TemplateQueryScope;
import com.calix.bseries.gui.panels.B6DSLBoundedTemplateCreateAction.B6DSLBoundedTemplateCreateDialog;
import com.calix.bseries.gui.panels.B6FPCMasterTemplateCreateAction.B6FPCMasterTemplateCreateDialog;
import com.calix.bseries.gui.utils.B6SecurityHelper;
import com.calix.bseries.gui.utils.BseriesDeviceModuleInitImpl;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.EMSGui;
import com.calix.ems.gui.panels.EMSPanelController;
import com.calix.ems.gui.panels.layout.NetworkDetailsPanel;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.ems.model.EMSRoot;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.gui.components.editors.BaseUserComboEditor;
import com.calix.system.gui.components.menu.CalixButtonMgr;
import com.calix.system.gui.components.menu.CalixMenuAction;
import com.calix.system.gui.components.menu.CalixMenuMgr;
import com.calix.system.gui.components.treetable.JTreeTable;
import com.calix.system.gui.components.util.ImageUtils;
import com.calix.system.gui.database.TLVDatabase;
import com.calix.system.gui.lookandfeel.LookAndFeelMgr;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.BaseEMSObject;
import com.calix.system.gui.model.CalixBaseAction;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.panels.CalixPanelController;
import com.calix.system.gui.util.CalixActionExecutor;
import com.calix.system.gui.util.CalixMessageUtils;
import com.calix.system.gui.util.DeviceModuleImpls;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.MetaTableHeaderRenderer;
import com.objectsavvy.base.gui.panels.ParameterHolder;
import com.objectsavvy.base.gui.panels.TableSelectionEvent;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.gui.util.GuiUtil;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.OSDatabase;
import com.objectsavvy.base.persistence.PrefixInterval;
import com.objectsavvy.base.persistence.TransactionHistory;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.Attribute;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TreeValue;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.app.gponont.AddGponOntPopup;
import com.occam.ems.client.app.gponont.GponDiscoveredONTsPopup;
import com.occam.ems.client.app.maps.MOUNavigatorUI;
import com.occam.ems.client.util.ClientUtil;
import com.calix.bseries.gui.utils.ViewGponOntPopup;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;


public class BseriesNetworkDetailsPanel extends NetworkDetailsPanel {
    AbstractAction m_resynchAlarmAction;
    BseriesAddressCellRenderer m_bseriesCellRenderer = new BseriesAddressCellRenderer();
   
    public BseriesNetworkDetailsPanel(){
    	 m_regionEditor = new BseriesUserComboEditor();
    	 m_regionEditor.getComboBox().setRenderer(new NetworkDetailsPanel.RegionListCellRenderer(m_regionEditor.getComboBox().getRenderer()));
    }
    private static MOUNavigatorUI mouNavigatorUI;

    @Override
    protected void setColumns(Collection<String> attributes) {
        attributes.add(CalixB6Device.ATTR_NAME);
        attributes.add(CalixB6Device.ATTR_IP_ADDR1);
        attributes.add(CalixB6Device.ATTR_REGION);
        attributes.add(CalixB6Device.ATTR_CONNECTION_STATE);        
        attributes.add(CalixB6Device.ATTR_MODEL);
        
        attributes.add(CalixB6Device.ATTR_SWVERSION);
        attributes.add(CalixB6Device.ATTR_USER_NAME);
        attributes.add(CalixB6Device.ATTR_WEBUSERNAME);
        attributes.add(CalixB6Device.ATTR_AUTOCONNECT);
        attributes.add(CalixB6Device.ATTR_HWVERSION);
        attributes.add(CalixB6Device.ATTR_SERIALNUMBER);
        attributes.add(CalixB6Device.ATTR_MACADDRESS);
        attributes.add(CalixB6Device.ATTR_MANUFACTUREDATE);
        attributes.add(CalixB6Device.ATTR_KEEP_CHASSIS);
        attributes.add(CalixB6Device.ATTR_TIMEZONE);
    }

    @Override
    protected String getTypeName() {
        return CalixB6Device.TYPE_NAME;
    }

    @Override
    protected boolean isMyObject(Object obj) {
    	try {
			if (obj instanceof CalixB6Device){
				CalixB6Device calixB6Device = ((CalixB6Device)obj);    	
				String chassisId = ((IValue) calixB6Device.getRegion()).convertTo(String.class, null);
				CalixB6Chassis chassis = calixB6Device.getParentChassis(chassisId,getPanelController().getDatabase());
				if (chassis != null){
					calixB6Device.setParentDisplayName(chassis.getDisplayName());
				}
			}
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ((OSBaseObject)obj).getTypeName().equals(getTypeName());
    }

    @Override
    protected Collection<? extends BaseEMSDevice> getChildren(EMSRegion region, EMSRoot root) {
        if(region.isRoot()){
            return root.getB6Networks();
        }
        return region.getB6NetworksList(root,true);
    }
    protected Collection<? extends BaseEMSDevice> getChildren(CalixB6Chassis chassis, EMSRoot root) {
       
        return chassis.getB6Networks(root);
    }
    @Override
    public ArrayList<JMenuItem> buildDeleteMenuItems() {
        ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
        
        if(!B6SecurityHelper.isDAA()) {
            return menuItems ;
          }
        OSBaseObject[] obj = getCurrentObjects();

        if(obj != null && obj.length > 0){
        	 if (B6SecurityHelper.isReadPriviledge(obj[0])) {
        		 CalixButtonMgr.getInstance().disableApplyButton();
        		 CalixButtonMgr.getInstance().disableRevertButton();
             	return menuItems;
             }
             IValue value = ((obj[0]).getAttributeValue(CalixB6Device.ATTR_MODEL));
             String model = null;
             if (value != null){
                 try {
     				model = (String)value.convertTo(String.class, null);
     			} catch (MappingException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
             }
             
             
           	if(!B6SecurityHelper.isB6write(model)){
           		CalixButtonMgr.getInstance().disableApplyButton();
           		CalixButtonMgr.getInstance().disableRevertButton();
           		return menuItems;
           		}  
             
             
        }
       
        CalixMenuAction delAction = new CalixMenuAction("Selected Network(s)", "Selected Network(s)", (CalixPanelController) getPanelController(),
                CalixMenuAction.ACTION_DELETE,
                this,
                null,
                null);
        m_deleteMenuItem = new JMenuItem(delAction);
        if (CalixMenuMgr.haveSelections(this)) {
            menuItems.add(m_deleteMenuItem);
        }
        return menuItems;
    }
    
    @Override
    public ArrayList<JMenuItem> buildActionMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildDeleteMenuItems();
        
        if(!B6SecurityHelper.isDAA()) {
            return menuItems ;
          }
        OSBaseObject[] obj = getCurrentObjects();

        if(obj != null && obj.length > 0){
        	 if (B6SecurityHelper.isReadPriviledge(obj[0])) {
        		CalixButtonMgr.getInstance().disableApplyButton();
        		CalixButtonMgr.getInstance().disableRevertButton();
             	return menuItems;
             }
             IValue value = ((obj[0]).getAttributeValue(CalixB6Device.ATTR_MODEL));
             String model = null;
             if (value != null){
                 try {
     				model = (String)value.convertTo(String.class, null);
     			} catch (MappingException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
             }
             
             
           	if(!B6SecurityHelper.isB6write(model)){
           		CalixButtonMgr.getInstance().disableApplyButton();
           		CalixButtonMgr.getInstance().disableRevertButton();
           		return menuItems;
           		}  
             
             
        }
        //disconnect
        m_disconnectAction = getConnectionAction("disconnect");
        
        //connect
        m_connectAction = getConnectionAction("connect");
        
        //resync alarms
        m_resynchAlarmAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resynchAlarm(e);
            }
        };
        m_resynchAlarmAction.putValue(Action.NAME, "Sync Alarms");
        
        //change web pwd
        AbstractAction changeWebPwdAction = getChangePwdAction(CalixB6Device.ATTR_WEBPASSWORD);
        changeWebPwdAction.putValue(Action.NAME, "Change WEB password");
        
        //change cli pwd
        AbstractAction changeCliPwdAction = getChangePwdAction(CalixB6Device.ATTR_USER_PASSWD);
        changeCliPwdAction.putValue(Action.NAME, "Change CLI password");
        
        //change community
        CommunityEditAction changeCommunityAction = new CommunityEditAction();
        changeCommunityAction.putValue(Action.NAME, "Change SNMP Community");
        
        //change enable pwd
        AbstractAction changeEnablePwdAction = getChangePwdAction(CalixB6Device.ATTR_ENABLEPASSWORD);
        changeEnablePwdAction.putValue(Action.NAME, "Change ENABLE password");
        
        menuItems.add(new JMenuItem(m_disconnectAction));
        menuItems.add(new JMenuItem(m_connectAction));
        menuItems.add(new JMenuItem(m_resynchAlarmAction));
        menuItems.add(new JMenuItem(changeWebPwdAction));
        menuItems.add(new JMenuItem(changeCliPwdAction));
        menuItems.add(new JMenuItem(changeCommunityAction));        
        
        menuItems.add(new JMenuItem(changeEnablePwdAction));   
        
        return menuItems;
    }
    
    @Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
        ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
        if(!B6SecurityHelper.isDAA()) {
        	CalixButtonMgr.getInstance().disableApplyButton();
        	CalixButtonMgr.getInstance().disableRevertButton();
        	}
        
        if(!B6SecurityHelper.isCreate()) {
            return menuItems ;
          }
        menuItems = super .buildContextCreateMenuItems();
        return menuItems ;
    }

    
    
    @Override
    public void populateDataFromFields(List<OSBaseAction> pActions) throws ValueException {
    	super.populateDataFromFields(pActions);
    	   IParameterHolder[][] params = m_params;
           // check if any of the
           if (params != null && params.length > 0) {
               for (int iX = 0; iX < params.length; iX++) {
            	   //check does the region changed. Do some translation.
            	   if (params[iX] != null && params[iX].length > 2 && params[iX][2].isModified()) {
            		  Object ivObj =((ParameterHolder)params[iX][2]).getValue();
            	       if (ivObj instanceof IValue) {
            	            IValue iv = ((IValue)ivObj);
            	            try {
            	                String ckRegion=iv.getType().convertTo(iv, String.class, "GUI");
            	                String newRegionDName = ckRegion.substring(8,ckRegion.length());
            	        		   Collection<BaseEMSDevice> Chassises = getApplicableChassises();
                        		   if(Chassises!=null){
                               	  Iterator<BaseEMSDevice> chassisIter = Chassises.iterator();
                               	  while(chassisIter.hasNext()){
                                 		CalixB6Chassis chassis =(CalixB6Chassis) chassisIter.next();
                                 		if(newRegionDName.equals(chassis.getDisplayName())){
                                 			RecordType chassisType = TypeRegistry.getInstance().getRecordType(CalixB6Chassis.TYPE_NAME);
                                 			IValueType chassisAttrType = chassisType.getAttribute(CalixB6Chassis.ATTR_REGION).getFirstType();
                                 			Object newValue = chassisAttrType.convertFrom(chassis.getName(), null); 
                                 			params[iX][2].setValue(newValue);
                                 		}
                                 			
                                 	}
                                 }
            	            } catch (Exception e) {
            	            	Code.warning(e);
            	            }
            	        }
         
            	   }
               }
           }
    }
    
   
    
    @Override
    public Collection<BaseEMSDevice> getApplicableChassises(){
    	 return EMSInit.getEMSRoot(getPanelController().getDatabase()).getB6Chassis();
    }

    @Override
    public JTreeTable createTreeTable() {
        final JTreeTable tree = super.createTreeTable();
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if ((me.getButton() == MouseEvent.BUTTON3) && (me.getClickCount() == 1)) {
                    int row = tree.rowAtPoint(me.getPoint());
                    final int actualRow = tree.getActualRow(row);
                    final OSBaseObject obj = getObjectAtRow(actualRow);
                    JPopupMenu popup = new JPopupMenu();
//                    AbstractAction telnetAction = getCutthoughtAction(obj, 1);
//                    JMenuItem menuItem1 = new JMenuItem(telnetAction);
//                    AbstractAction sshAction = getCutthoughtAction(obj, 2);
//                    JMenuItem menuItem2 = new JMenuItem(sshAction);
                    CalixB6Device b6deviceObj = (CalixB6Device)obj;
                    IValue deviceModel= b6deviceObj.getAttributeValue(CalixB6Device.ATTR_MODEL);
                    try {
                    	if(deviceModel !=null){
                    		String sVal = (String) deviceModel.convertTo(String.class, null);
    						if(sVal !=null && ClientUtil.isGponOlt(sVal)){
    							AbstractAction gponOntDisplay =getGponOntDisplay(obj);
    		                    JMenuItem menuItem2 = new JMenuItem(gponOntDisplay);
    		                    popup.add(menuItem2);
    		                    AbstractAction addGponOnt =addGponOnt(obj);
    		                    JMenuItem menuItem3 = new JMenuItem(addGponOnt);
    		                    popup.add(menuItem3);
    		                    AbstractAction viewDiscoveredOnt =viewDiscoveredGponOnt(obj);
    		                    JMenuItem menuItem4 = new JMenuItem(viewDiscoveredOnt);
    		                    popup.add(menuItem4);
                    	}  
						}
					} catch (MappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//                    popup.add(menuItem1);
                    // Begin bug 51355 fix by James Wang 20111219
                    AbstractAction ewiAction = getCutthoughtAction(obj, 2);
                    JMenuItem menuItem2 = new JMenuItem(ewiAction);
                    popup.add(menuItem2);
                    // End bug 51355 fix by James Wang 20111219  
                    
//                    AbstractAction auditAction = getAuditLogAction(obj);
//                    JMenuItem menuItem5 = new JMenuItem(auditAction);
//                    popup.add(menuItem5);
                    
                    JMenu menuItem6 =new JMenu("BLC Info");
                    AbstractAction b6CommandAction = getB6CommandAction(obj,"Show BLC Configuration");
                	JMenuItem menuItem6_1 = new JMenuItem(b6CommandAction);
                	menuItem6.add(menuItem6_1);
                	
                	b6CommandAction = getB6CommandAction(obj,"Show EPS Information");          
                	menuItem6_1 = new JMenuItem(b6CommandAction);
                	menuItem6.add(menuItem6_1);
                	
                  	b6CommandAction = getB6CommandAction(obj,"Show EPS Map(All)");          
                	menuItem6_1 = new JMenuItem(b6CommandAction);
                	menuItem6.add(menuItem6_1);
                	
                  	b6CommandAction = getB6CommandAction(obj,"Show EPS Map(Local)");          
                	menuItem6_1 = new JMenuItem(b6CommandAction);
                	menuItem6.add(menuItem6_1);
                	
                  	b6CommandAction = getB6CommandAction(obj,"Show Modem MAC Address");          
                	menuItem6_1 = new JMenuItem(b6CommandAction);
                	menuItem6.add(menuItem6_1);
                	
                  	b6CommandAction = getB6CommandAction(obj,"Show Voltage & Temp");   
                	menuItem6_1 = new JMenuItem(b6CommandAction);
                	menuItem6.add(menuItem6_1);

                	popup.add(menuItem6);
                    
                    JMenu menuItem7 =new JMenu("Commands");
                    AbstractAction commandsAction = getB6CommandAction(obj,"showbonding_groups");
                	JMenuItem menuItem7_1 = new JMenuItem(commandsAction);
                	menuItem7.add(menuItem7_1);
                	
                	commandsAction = getB6CommandAction(obj,"showdsl_port_status");          
                	menuItem7_1 = new JMenuItem(commandsAction);
                	menuItem7.add(menuItem7_1);                	

                	popup.add(menuItem7);
                	
                	JMenu menuItem8 =new JMenu("DSC Tools");
                	AbstractAction b6DSCToolsCommandAction = getDSCToolsCommandAction(obj,"Show <custom>");
                	JMenuItem menuItem8_1 = new JMenuItem(b6DSCToolsCommandAction);
                	menuItem8.add(menuItem8_1);
                	
                	popup.add(menuItem8);
                	
                	JMenu menuItem9 =new JMenu("DSL Port Info");
                	AbstractAction b6PortCommandAction = getPortB6CommandAction(obj,"Show DSL Port Config");
                	JMenuItem menuItem9_1 = new JMenuItem(b6PortCommandAction);
                	menuItem9.add(menuItem9_1);
                	
                	b6PortCommandAction = getPortB6CommandAction(obj,"Show DSL Port Overview");
                	menuItem9_1 = new JMenuItem(b6PortCommandAction);
                	menuItem9.add(menuItem9_1);
                	
                	b6PortCommandAction = getPortB6CommandAction(obj,"Show DSL Port Status");
                	menuItem9_1 = new JMenuItem(b6PortCommandAction);
                	menuItem9.add(menuItem9_1);
                	
                	b6PortCommandAction = getPortB6CommandAction(obj,"Show DSL Port Summary");
                	menuItem9_1 = new JMenuItem(b6PortCommandAction);
                	menuItem9.add(menuItem9_1);
                	
                	b6PortCommandAction = getPortB6CommandAction(obj,"Show DSL Port info");
                	menuItem9_1 = new JMenuItem(b6PortCommandAction);
                	menuItem9.add(menuItem9_1);
                	
                	b6PortCommandAction = getPortB6CommandAction(obj,"Show PPPoE Interface info");
                	menuItem9_1 = new JMenuItem(b6PortCommandAction);
                	menuItem9.add(menuItem9_1);
                	
                	popup.add(menuItem9);

                    JMenu menuItem10 =new JMenu("FPC-BONDED");
                    JMenuItem menuItem10_1 =new JMenuItem(new B6DSLBoundedTemplateCreateAction(getPanelController(), obj, B6TemplateCreateAction.OPERATION_ADD));
                    JMenuItem menuItem10_2 =new JMenuItem(new B6DSLBoundedTemplateCreateAction(getPanelController(), obj, B6TemplateCreateAction.OPERATION_MODIFY));
                    JMenuItem menuItem10_3 =new JMenuItem(new B6DSLBoundedTemplateCreateAction(getPanelController(), obj, B6TemplateCreateAction.OPERATION_DELETE));
                    
                    AbstractAction boundTemplateRes = getTemplateResultAction(obj,B6DSLBoundedTemplate.TYPE_NAME);
                    JMenuItem menuItem10_4 =new JMenuItem(boundTemplateRes);

                    menuItem10.add(menuItem10_1);
                    menuItem10.add(menuItem10_3);
                    menuItem10.add(menuItem10_2);
                    menuItem10.add(menuItem10_4);
                    popup.add(menuItem10);
                    
                    JMenu menuItem11 =new JMenu("FPC-RES");
                    JMenuItem menuItem11_1 =new JMenuItem(new B6FPCMasterTemplateCreateAction(getPanelController(), obj, B6TemplateCreateAction.OPERATION_ADD));
                    JMenuItem menuItem11_2 =new JMenuItem(new B6FPCMasterTemplateCreateAction(getPanelController(), obj, B6TemplateCreateAction.OPERATION_MODIFY));
                    JMenuItem menuItem11_3 =new JMenuItem(new B6FPCMasterTemplateCreateAction(getPanelController(), obj, B6TemplateCreateAction.OPERATION_DELETE));
                    
                    AbstractAction templateRes = getTemplateResultAction(obj,B6FPCMasterTemplate.TYPE_NAME);
                    JMenuItem menuItem11_4 =new JMenuItem(templateRes);

                    menuItem11.add(menuItem11_1);
                    menuItem11.add(menuItem11_2);
                    menuItem11.add(menuItem11_3);
                    menuItem11.add(menuItem11_4);
                    popup.add(menuItem11);
                	
                    JMenu menuItem12 =new JMenu("CUT-THROUGH Tool for BLC Info");
                    AbstractAction commandCutAction = getCutthoughtAction(obj,3);//show running-config
                	JMenuItem menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                    commandCutAction = getCutthoughtAction(obj,4);//show eps
                	menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                    commandCutAction = getCutthoughtAction(obj,5);//show eps-map-all
                	menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                    commandCutAction = getCutthoughtAction(obj,6);//show eps-map
                	menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                    commandCutAction = getCutthoughtAction(obj,7);//show bridge-source
                	menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                    commandCutAction = getCutthoughtAction(obj,8);//show env-all
                	menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                    commandCutAction = getCutthoughtAction(obj,9);//show bounding-groups
                	menuItem12_1 = new JMenuItem(commandCutAction);
                	menuItem12.add(menuItem12_1);
                	
                	//popup.add(menuItem12);

                    boolean isNetworkWrite = true;	
                    if (B6SecurityHelper.isReadPriviledge(obj) || !B6SecurityHelper.isAction()) {
                    	isNetworkWrite = false;
                    }
                    if(isNetworkWrite == true){
               		     IValue value = ((obj).getAttributeValue(CalixB6Device.ATTR_MODEL));
               		     String model = null;
               		     if (value != null){
               		         try {
               		                       model = (String)value.convertTo(String.class, null);
               		               } catch (MappingException e) {
               		                       // TODO Auto-generated catch block
               		                       e.printStackTrace();
               		               }
               		     }
            		       if(!B6SecurityHelper.isB6write(model)){
            		    	   isNetworkWrite =false;
                           }
                    }
                    
//                    menuItem1.setEnabled(isNetworkWrite);
                    
                    menuItem10_1.setEnabled(isNetworkWrite);
                    menuItem10_2.setEnabled(isNetworkWrite);
                    menuItem10_3.setEnabled(isNetworkWrite);
                    
                    menuItem11_1.setEnabled(isNetworkWrite);
                    menuItem11_2.setEnabled(isNetworkWrite);
                    menuItem11_3.setEnabled(isNetworkWrite);
                    
                    popup.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
        return tree;
    }
    
    @Override
    public void tableSelectionChanged(TableSelectionEvent pEvent) {
    	((CalixPanelController) getPanelController()).possiblySetupMenus(this);
        if (m_disconnectAction != null && m_connectAction != null) {
            m_disconnectAction.setEnabled(false);
            m_connectAction.setEnabled(false);
            m_resynchAlarmAction.setEnabled(false);
            boolean connect = false;
            boolean disconnect = false;
            OSBaseObject[] objects = getCurrentObjects();
            if (objects != null && objects.length > 0) {
                for (int iCount = 0; iCount < objects.length; iCount++) {
                    if (((BaseEMSDevice) objects[iCount]).getConnectionState() > BaseEMSDevice.DISCONNECTED) {
                        connect = true;
                    } else {
                        disconnect = true;
                    }
                }
                if (connect && !disconnect) {
                    m_disconnectAction.setEnabled(true);
                    m_resynchAlarmAction.setEnabled(true);
                } else if (disconnect && !connect) {
                    m_connectAction.setEnabled(true);
                }
             }
          }
    }
    
    @Override
    protected TableCellRenderer getTableHeaderRenderer(int i, IParameterHolder param) {
        if (CalixB6Device.ATTR_REGION.equals(param.getName())) {
            TableCellRenderer render = super.getTableHeaderRenderer(i, param);
            ((MetaTableHeaderRenderer) render).setValue("Network Group");
            return render;
        }else if(CalixB6Device.ATTR_USER_NAME.equals(param.getName())) {
            TableCellRenderer render = super.getTableHeaderRenderer(i, param);
            ((MetaTableHeaderRenderer) render).setValue("CLIUSERNAME");
            return render;
        }
        return super.getTableHeaderRenderer(i, param);
    }
    
    private OSBaseObject getObjectAtRow(int row) {
        IParameterHolder[] rowParam = super.m_tableSorter.getObjectAtRow(row);
        if (rowParam != null && rowParam.length > 0) {
            return ((ParameterHolder) rowParam[0]).getObject();
        }
        return null;
    }
    
    private void connectDisconnect(final ActionEvent evt, final String status) {
        final IDatabase db = getPanelController().getActionExecutor().getDatabase();
         new CalixActionExecutor(db) {
                 @Override
                 protected void setupAction(ActionEvent evt2, CalixBaseAction pAction, OSBaseObject object) throws MappingException {
                     BaseEMSDevice pNetwork = (BaseEMSDevice)object;
                     pAction.setAttributeValue(EMSBseriesConnAction.ATTR_NETWORK, pNetwork.getName());
                     pAction.setAttributeValue(EMSBseriesConnAction.ATTR_CONNECTTYPE, status);
                     pAction.setIdentityValue(pNetwork.getIdentityValue());
                 }

                 @Override
                 protected CalixBaseAction getAction(final ActionEvent evt) {
                     try {
                         return (CalixBaseAction) TypeRegistry.getInstance().getRecordType(EMSBseriesConnAction.TYPE_NAME).newInstance();
                     } catch (Exception ex) {
                         Code.warning(ex);
                     }
                     return null;
                 }

                 @Override
                 protected boolean confirmAction(ActionEvent pEvent) {
                     return true;
                 }

                 @Override
                 protected Collection executeAction(CalixBaseAction action,
                         OSBaseObject pObject) throws PersistenceException {
                     Collection col = super.executeAction(action, pObject);
                     
                     if ( "disconnect".equals(status) ){
                         Code.warning("Refresh alarm panel to clean up possible alarms belonging to this network");
                         CalixCit.getCalixCitInstance().getMainView().getAlarmPanel().refreshAlarms();
                     }
                     return col;
                 }
                 
             }.standardPanelExecute(evt, this, "Network");
      }
    
    private AbstractAction getConnectionAction(final String connectionName){
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final OSBaseObject[] selObjects = getCurrentObjects();
                if (selObjects == null || selObjects.length == 0) {
                    CalixMessageUtils.showErrorMessage("Select a network in the table and try again");
                    return;
                }
                connectDisconnect(e, connectionName);
            }
        };
        action.putValue(Action.NAME, connectionName);
        action.putValue(Action.ACTION_COMMAND_KEY, connectionName);
        return action;
    }
    
    private AbstractAction getChangePwdAction(final String attrName){
        
        AbstractAction passwdAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if more than one row is selected on the table
                final OSBaseObject[] selObjects = getCurrentObjects();
                if (selObjects == null || selObjects.length == 0) {
                    CalixMessageUtils.showErrorMessage("Select a network in the table and try again");
                    return;
                }
                
                NetworkDetailsPanel.PasswordDialog dlg = null;
                if (selObjects.length == 1){
                    dlg = new NetworkDetailsPanel.PasswordDialog(CalixCit.getCalixCitInstance(), (BaseEMSDevice) selObjects[0], attrName);
                } else {
                    dlg = new NetworkDetailsPanel.PasswordDialog(CalixCit.getCalixCitInstance(), "B6 devices", attrName);
                }
                dlg.setVisible(true);
                
                if (!dlg.getStatus()) {
                    return;
                }
                try {
                    for (int i = 0; i < selObjects.length; i++){
                        selObjects[i].setAttributeValue(attrName, dlg.getClearTextPassword());
                    }
                    // save the contents now
                    ((EMSPanelController) getPanelController()).handleApplyChangesNoConfirm();
                } catch (MappingException mex) {
                    Code.warning(mex);
                }
            }
        };
        return passwdAction;
    }
    
    
    private AbstractAction getTemplateResultAction(final OSBaseObject network, final String recordType){
        
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e1) {
            	TLVB6TemplateQueryScope queryScope = new TLVB6TemplateQueryScope(); 
    			Collection values = null;
            	   CalixB6Device b6deviceObj = (CalixB6Device)network;
                   IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                   String ip1 = null;
                   try {
                   	if(ip !=null){
                   		 ip1 = (String) ip.convertTo(String.class, null);   						
                   	}  
						}
					 catch (MappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

    			try {
    				RecordType templateRecordType = TypeRegistry.getInstance().getRecordType("GetB6TemplateResult");
    				//RecordType b6RecordType = TypeRegistry.getInstance().getRecordType(recordType);
    				OSDatabase db = (OSDatabase) EMSGui.getInstance().getReadOnlyDatabase();
    				TransactionHistory history = null;
    				boolean wasActive = true;
    				try {
    					if (!db.isActive()) {
    						wasActive = false;
    					} else {
    						db.rollback();
    					}
    					db.begin();
    					history = ((TLVDatabase) db).createTransactionHistory();
    					queryScope.setIPAddress1(ip1);
    					queryScope.setTemplateSource("GUI");
    					queryScope.setTemplateRecordType(recordType);
    					queryScope.setTemplateId("");
    					queryScope.addInterval(new PrefixInterval((TreeValue)templateRecordType.getIdentityAttribute().getFirstType().convertFrom("1", "TL1")));
    					values = db.getAllObjects(queryScope, templateRecordType);
    					db.commit();
    					if (!wasActive) {
    						db.begin();
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    					CalixMessageUtils.showHWErrorException(history);
    					try {
    						db.rollback();
    					} catch (Exception cex) {
    						Code.warning(cex);
    					}
    				}
    				if (wasActive && !db.isActive()) {
    					try {
    						db.begin();
    					} catch (Throwable ignore) {
    					}
    				}
    			} catch (Exception ex) {
    				Code.warning("Unable to load B6 Template records", ex);
    			}
    			
    			
    			//panel
    			if(values == null || values.isEmpty()){
    				Code.warning("There are no template records for B6 device:"+ip1);
    				CalixMessageUtils.showErrorMessage("There are no template records for B6 device:"+ip1);
    			}else{    	
    				if((values.toArray())[0] != null){
        				if((values.toArray())[0] instanceof B6FPCMasterTemplate){
        					B6FPCMasterTemplate obj = (B6FPCMasterTemplate) (values.toArray())[0];
            				B6FPCMasterTemplateCreateAction b6create = new B6FPCMasterTemplateCreateAction(getPanelController());
            				B6FPCMasterTemplateCreateDialog dialog = b6create.new B6FPCMasterTemplateCreateDialog();
        					dialog.CreateDetailDialog(obj);
        				}else if((values.toArray())[0] instanceof B6DSLBoundedTemplate){
        					B6DSLBoundedTemplate obj = (B6DSLBoundedTemplate) (values.toArray())[0];
        					B6DSLBoundedTemplateCreateAction b6create = new B6DSLBoundedTemplateCreateAction(getPanelController());
        					B6DSLBoundedTemplateCreateDialog dialog = b6create.new B6DSLBoundedTemplateCreateDialog();
        					dialog.CreateDetailDialog(obj);
        				}
    				}
    			}
            }
        };
        action.putValue(Action.NAME, "CHECK LAST RESULT");;
        return action;
    }
    
    private AbstractAction getB6CommandAction(final OSBaseObject network, final String commandType){
        AbstractAction action = null;
        if("showdsl_port_status".equals(commandType)){
        	action = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {

                	SwingUtilities.invokeLater(new Runnable() {
                		public void run() {
                     	   final CalixB6Device b6deviceObj = (CalixB6Device)network;
                			if ( !b6deviceObj.isConnected() ){
                				CalixMessageUtils.showErrorMessage(b6deviceObj.getName()+" is disconnected, Please connect it first.");
                				return;
                			}
                			
                			
                			final IDatabase db = CalixCit.getCalixCitInstance().getCreateDatabase();
                			
                			B6CommandResultData data = null;
                			B6CommandResultData command = B6CommandResultData.newInstance();
                	        try {
								BaseEMSObject.setIdentityValue(command, "B6CommandResultData-"+b6deviceObj.getName());
							} catch (MappingException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
        				
            				//query if B6CommandResultData for this network exists
                        	try {                		                		
                				if (!db.isActive())
                				    db.begin();					        			
                				 data = (B6CommandResultData) db.load(TypeRegistry.getInstance().getRecordType("B6CommandResultData"),command.getIdentityValue());
                				db.commit();
                			} catch (Exception e1) {
                				Code.warning("Ignore loading error for B6CommandResultData.");
                			}
                			
                        	if(data != null){
                    			try {
                    				//clear result panel data
                    				data.setAttributeValue("importFlag", 1);
                    				//make sure it can be updated
                					data.setAttributeValue(B6CommandResultData.ATTR_STARTTIME, "");
                					data.setAttributeValue(B6CommandResultData.ATTR_ENDTIME, "");
                					data.setAttributeValue(B6CommandResultData.ATTR_STATUS, "");
                					data.setAttributeValue(B6CommandResultData.ATTR_RESPONSE, "");
                    			} catch (MappingException e1) {
                    				// TODO Auto-generated catch block
                    				e1.printStackTrace();
                    			}                   	
                    			try {                		                		
                    				if (!db.isActive())
                    					db.begin();					        			
                    				db.update(data);
                    				db.commit();
                    			} catch (Exception e1) {
                    				Code.warning("Ignore updating error for B6CommandResultData.");
                    			}
                        	}

  			
                			 IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                             String ip1 = null;
                             try {
                             	if(ip !=null){
                             		 ip1 = (String) ip.convertTo(String.class, null);   						
                             	}  
          						}
          					 catch (MappingException e1) {
          						// TODO Auto-generated catch block
          						e1.printStackTrace();
          					}

                			final JDialog mainPanel = new JDialog();
                			mainPanel.setIconImage(ImageUtils.getImage("symbol.gif"));
                			mainPanel.setSize(500,550);
                			mainPanel.setLocationRelativeTo(null);
                			mainPanel.setTitle(commandType);
                			
                			JTabbedPane container = new JTabbedPane();
                			JPanel container1 = new JPanel();
                			JPanel container2 = new JPanel();
                			container.addTab("General", container1);
                			container.addTab("Result", container2);
                			
                			container1.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
                			container1.setBackground(Color.white);
                	    	
                	    	JLabel labelNetworkName = new JLabel();
                	        labelNetworkName.setHorizontalAlignment(4);
                	        labelNetworkName.setHorizontalTextPosition(4);
                	        labelNetworkName.setText("Network Name:");
                	        
                	        JTextField txtNetworkName= new JTextField();
                	        txtNetworkName.setHorizontalAlignment(2);
                	        txtNetworkName.setText(b6deviceObj.getName());
                	        txtNetworkName.setEditable(false);
                	        
                	    	JLabel labelNetworkIP = new JLabel();
                	    	labelNetworkIP.setHorizontalAlignment(4);
                	    	labelNetworkIP.setHorizontalTextPosition(4);
                	    	labelNetworkIP.setText("Network IP:");
                	    	
                	        JTextField txtNetworkIP= new JTextField();
                	        txtNetworkIP.setHorizontalAlignment(2);
                	        txtNetworkIP.setText(ip1);
                	        txtNetworkIP.setEditable(false);
                	        
                	    	JLabel labelRequest = new JLabel();
                	    	labelRequest.setHorizontalAlignment(4);
                	    	labelRequest.setHorizontalTextPosition(4);
                	    	labelRequest.setText("Request:");
                	    	
                	        JTextField txtRequest= new JTextField();
                	        txtRequest.setHorizontalAlignment(2);
                	        txtRequest.setText(commandType);
                	        txtRequest.setEditable(false);
                			
                	    	JLabel labelSpace = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");

                			                	                       	        
                	        JButton execute = new JButton("Execute");                	        
                	        execute.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                                	SwingUtilities.invokeLater(new Runnable() {
                                		public void run() {                            				                            				
                                    		B6CommandResultData command = B6CommandResultData.newInstance();
                                            IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                                            String ip1 = null;
                                            try {
                                            	if(ip !=null){
                                            		 ip1 = (String) ip.convertTo(String.class, null);   						
                                            	}  
                            						}
                            					 catch (MappingException e1) {
                            						// TODO Auto-generated catch block
                            						e1.printStackTrace();
                            					}
                                    		try {
                                                BaseEMSObject.setIdentityValue(command, "B6CommandResultData-"+b6deviceObj.getName());
                            					command.setAttributeValue(B6CommandResultData.ATTR_NETWORKIP, ip1);
                            					command.setAttributeValue(B6CommandResultData.ATTR_NETWORKNAME, b6deviceObj.getName());
                            					command.setAttributeValue(B6CommandResultData.ATTR_COMMANDTYPE, commandType);
                            					//make sure it can be updated
                            					command.setAttributeValue(B6CommandResultData.ATTR_STARTTIME, UUID.randomUUID().toString());
                            				} catch (MappingException e1) {
                            					// TODO Auto-generated catch block
                            					e1.printStackTrace();
                            				}

                                    		B6CommandResultData data = null;
                            				final IDatabase db = CalixCit.getCalixCitInstance().getCreateDatabase();
                            				
                            				//query if B6CommandResultData for this network exists
                                        	try {                		                		
                                				if (!db.isActive())
                                				    db.begin();					        			
                                				 data = (B6CommandResultData) db.load(TypeRegistry.getInstance().getRecordType("B6CommandResultData"),command.getIdentityValue());
                                				db.commit();
                                			} catch (Exception e1) {
                                				Code.warning("Ignore loading error for B6CommandResultData.");
                                			}
                                        	//if B6CommandResultData exists, update it, if not, create a new one. 
                            				try {
                        						if (!db.isActive())
                        						    db.begin();	
                        						if(data != null){
                        							db.update(command);
                        						}else{
                        							db.create(command);
                        						}
                        						db.commit();
                        					} catch (Exception e1) {
                        						// TODO Auto-generated catch block
                        						e1.printStackTrace();
                        					} 
                                        	
                                        }
                                	});
                	            
                	            }
                	        });
                	        
                	        JButton closeButton1 = new JButton("Close");                	        
                	        closeButton1.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	            	mainPanel.dispose();
                	            
                	            }
                	        });
                	        JButton closeButton2 = new JButton("Close");                	        
                	        closeButton2.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	            	mainPanel.dispose();
                	            
                	            }
                	        });
                	        
                	        JPanel actionPanel = new JPanel();
                	        actionPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
                	        actionPanel.setBackground(Color.white);
                	        
                	        actionPanel.add(execute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        actionPanel.add(closeButton1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        
                	        container1.add(labelNetworkName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtNetworkName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        container1.add(labelNetworkIP, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtNetworkIP, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        container1.add(labelRequest, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtRequest, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	             
                	        container1.add(labelSpace, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 330), null));
                	        container1.add(actionPanel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

                	      

                	       
                	        container2.setBackground(Color.white);       
                	        container2.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
                	        JLabel labelStatus= new JLabel();
                	    	labelStatus.setHorizontalAlignment(4);
                	    	labelStatus.setHorizontalTextPosition(4);
                	    	labelStatus.setText("Status:");
                	    	
                	        final JTextField txtStatus= new JTextField();
                	        txtStatus.setHorizontalAlignment(2);
                	        txtStatus.setEditable(false);
                	        
                	    	JLabel labelStartTime= new JLabel();
                	    	labelStartTime.setHorizontalAlignment(4);
                	    	labelStartTime.setHorizontalTextPosition(4);
                	    	labelStartTime.setText("Start Time:");
                	    	
                	        final JTextField txtStartTime= new JTextField();
                	        txtStartTime.setHorizontalAlignment(2);
                	        txtStartTime.setEditable(false);
                	        
                	    	JLabel labelEndTime= new JLabel();
                	    	labelEndTime.setHorizontalAlignment(4);
                	    	labelEndTime.setHorizontalTextPosition(4);
                	    	labelEndTime.setText("End Time:");
                	    	
                	        final JTextField txtEndTime= new JTextField();
                	        txtEndTime.setHorizontalAlignment(2);
                	        txtEndTime.setEditable(false);
                	        
                	    	JLabel labelResponse = new JLabel();
                	    	labelResponse.setHorizontalAlignment(4);
                	    	labelResponse.setHorizontalTextPosition(4);
                	    	labelResponse.setText("Response:");
                	    	
                	    	final JTextArea txtResponse =new JTextArea("",20,20);
                			txtResponse.setEditable(false);
                			txtResponse.setLineWrap(true);
                			txtResponse.setBorder(new LineBorder(null,0)); 
                			JScrollPane responsePane =new JScrollPane(txtResponse);
                			responsePane.setBorder(BorderFactory.createLineBorder(LookAndFeelMgr.getControlColor()));
                			responsePane.setOpaque(false); 
                			responsePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                			
                			JLabel warningLabel = new JLabel(CalixConstants.B6_SWITCH_TAB_WARNING);
                			warningLabel.setFont(new Font("Verdana",Font.ITALIC, 13));
            			    JPanel actionPanel2 = new JPanel();
            			    actionPanel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
            			    actionPanel2.setBackground(Color.white);
                  	        
            			    actionPanel2.add(warningLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ALIGN_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            			    actionPanel2.add(closeButton2, new GridConstraints(1, 0, 1, 1, GridConstraints.ALIGN_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                  	        
                			container2.add(labelStatus, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(txtStatus, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                			container2.add(labelStartTime, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(txtStartTime, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                			container2.add(labelEndTime, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(txtEndTime, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                			container2.add(labelResponse, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(responsePane, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                			
                			container2.add(actionPanel2, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

                			
                	        container.addChangeListener(new ChangeListener() {

								@Override
								public void stateChanged(ChangeEvent e) {
				               		B6CommandResultData data = null;
				                   	try {
				                		B6CommandResultData command = B6CommandResultData.newInstance();
				                        try {
											BaseEMSObject.setIdentityValue(command, "B6CommandResultData-"+b6deviceObj.getName());
										} catch (MappingException e2) {
											Code.warning("Ignore loading error for B6CommandResultData.");
										}                	
				                		
				        				IDatabase db = CalixCit.getCalixCitInstance().getCreateDatabase();
				        				if (!db.isActive())
				        				    db.begin();
				        				data = (B6CommandResultData) db.load(TypeRegistry.getInstance().getRecordType("B6CommandResultData"),command.getIdentityValue());
				        				db.commit();
				        			} catch (PersistenceException e1) {
				        				Code.warning("Ignore loading error for B6CommandResultData.");
				           			}
				                   	if(data == null){
					    				//CalixMessageUtils.showMessage("There is no result for "+b6deviceObj.getName()+", please execute GET DSL-PORT-STATUS first!");
				                   	}else{
				                   		String responseDetail = null;
				                   		String statusDetail = null;
				                   		String startTime = null;
				                   		String endTime = null;
				                   		
						            	try {
											IValue responseValue = data.getAttributeValue(B6CommandResultData.ATTR_RESPONSE);
											if(responseValue != null){
												responseDetail = (String) responseValue.convertTo(String.class, null);
											}    
											IValue statusValue = data.getAttributeValue(B6CommandResultData.ATTR_STATUS);
											if(statusValue != null){
												statusDetail = (String) statusValue.convertTo(String.class, null);
											}   
											IValue startTimeValue = data.getAttributeValue(B6CommandResultData.ATTR_STARTTIME);
											if(startTimeValue != null){
												startTime = (String) startTimeValue.convertTo(String.class, null);
											}
											IValue endTimeValue = data.getAttributeValue(B6CommandResultData.ATTR_ENDTIME);
											if(endTimeValue != null){
												endTime = (String) endTimeValue.convertTo(String.class, null);
											}
											txtStatus.setText(statusDetail);
									        txtStartTime.setText(startTime);
									        txtEndTime.setText(endTime);
									        txtResponse.setText(responseDetail);
										} catch (MappingException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}    
				                   	}
				                   	
				                   }
                	
                	        });             		                			
                			mainPanel.add(container);
                			mainPanel.setModal(true);
                			mainPanel.setFocusable(true);
                			mainPanel.setVisible(true);
                		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
                	});
                	
                
					
				}
        		
        	};
        }else{
            action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	SwingUtilities.invokeLater(new Runnable() {
                		public void run() {
                     	   CalixB6Device b6deviceObj = (CalixB6Device)network;
                			if ( !b6deviceObj.isConnected() ){
                				CalixMessageUtils.showErrorMessage(b6deviceObj.getName()+" is disconnected, Please connect it first.");
                				return;
                			}
                	        CalixCit gui = CalixCit.getCalixCitInstance();
                	        gui.getProgressDialog().showIt();
                	        gui.getProgressDialog().setMessage("Waiting response...");
                	        gui.getProgressDialog().setRangeProperties(0,0,0,15);
                			
                			String responseDetail = null;
                        	TLVB6CommandActionQueryScope queryScope = new TLVB6CommandActionQueryScope(); 
                			Collection values = null;
                               IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                               String ip1 = null;
                               try {
                               	if(ip !=null){
                               		 ip1 = (String) ip.convertTo(String.class, null);   						
                               	}  
            						}
            					 catch (MappingException e1) {
            						// TODO Auto-generated catch block
            						e1.printStackTrace();
            					}

                			try {
                				RecordType templateRecordType = TypeRegistry.getInstance().getRecordType("B6CommandResult");
                				
                				//RecordType b6RecordType = TypeRegistry.getInstance().getRecordType(recordType);
                				OSDatabase db = (OSDatabase) EMSGui.getInstance().getReadOnlyDatabase();
                				TransactionHistory history = null;
                				boolean wasActive = true;
                				try {
                					if (!db.isActive()) {
                						wasActive = false;
                					} else {
                						db.rollback();
                					}
                					db.begin();
                					history = ((TLVDatabase) db).createTransactionHistory();
                					queryScope.setNetworkIP(ip1);
                					queryScope.setCommandType(commandType);
                					queryScope.setNetworkName(b6deviceObj.getName());
                					queryScope.setPort("");
                					queryScope.setVlan("");

                					queryScope.addInterval(new PrefixInterval((TreeValue)templateRecordType.getIdentityAttribute().getFirstType().convertFrom("1", "TL1")));
                					values = db.getAllObjects(queryScope, templateRecordType);
                					gui.getProgressDialog().hideIt();
                					
                		           if(!values.isEmpty()){
                		            	B6CommandResult obj = (B6CommandResult) (values.toArray())[0];
                		            	IValue responseValue = obj.getAttributeValue(B6CommandResult.ATTR_RESPONSE);
                		            	if(responseValue != null){
                		            		responseDetail = (String) responseValue.convertTo(String.class, null);
                		            	}    

                		            }
                					db.commit();
                					if (!wasActive) {
                						db.begin();
                					}              					
                	    			if(responseDetail!=null && !responseDetail.isEmpty()){
                	    				b6CommandResultDailog(b6deviceObj.getName(),ip1, commandType,responseDetail);
                	    			}else{
                	    				CalixMessageUtils.showMessage("There is no response for "+b6deviceObj.getName());
                	    			}
                				} catch (Exception e1) {
                					e1.printStackTrace();
                					CalixMessageUtils.showHWErrorException(history);
                					try {
                						db.rollback();
                					} catch (Exception cex) {
                						Code.warning(cex);
                					}
                				}
                				if (wasActive && !db.isActive()) {
                					try {
                						db.begin();
                					} catch (Throwable ignore) {
                					}
                				}
                			} catch (Exception ex) {
                				Code.warning("Unable to execute command", ex);
                			}finally{
                				 gui.getProgressDialog().hideIt();
                			}
                		}
                	});
                	
                }
            };
        }


        action.putValue(Action.NAME, commandType);
        return action;
    }
    
    private AbstractAction getPortB6CommandAction(final OSBaseObject network, final String commandType){
        AbstractAction action = null;
            action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	SwingUtilities.invokeLater(new Runnable() {
                		public void run() {
                     	   final CalixB6Device b6deviceObj = (CalixB6Device)network;
                			if ( !b6deviceObj.isConnected() ){
                				CalixMessageUtils.showErrorMessage(b6deviceObj.getName()+" is disconnected, Please connect it first.");
                				return;
                			}
                			
                			 IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                             String ip1 = null;
                             try {
                             	if(ip !=null){
                             		 ip1 = (String) ip.convertTo(String.class, null);   						
                             	}  
          						}
          					 catch (MappingException e1) {
          						// TODO Auto-generated catch block
          						e1.printStackTrace();
          					}

                			final JDialog mainPanel = new JDialog();
                			mainPanel.setIconImage(ImageUtils.getImage("symbol.gif"));
                			mainPanel.setSize(500,450);
                			mainPanel.setLocationRelativeTo(null);
                			mainPanel.setTitle(commandType);
                			
                			JTabbedPane container = new JTabbedPane();
                			JPanel container1 = new JPanel();
                			JPanel container2 = new JPanel();
                			container.addTab("General", container1);
                			container.addTab("Result", container2);
                			
                			container1.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
                			container1.setBackground(Color.white);
                	    	
                			container2.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
                	    	JLabel labelNetworkName = new JLabel();
                	        labelNetworkName.setHorizontalAlignment(4);
                	        labelNetworkName.setHorizontalTextPosition(4);
                	        labelNetworkName.setText("Network Name:");
                	        
                	        JTextField txtNetworkName= new JTextField();
                	        txtNetworkName.setHorizontalAlignment(2);
                	        txtNetworkName.setText(b6deviceObj.getName());
                	        txtNetworkName.setEditable(false);
                	        
                	    	JLabel labelNetworkIP = new JLabel();
                	    	labelNetworkIP.setHorizontalAlignment(4);
                	    	labelNetworkIP.setHorizontalTextPosition(4);
                	    	labelNetworkIP.setText("Network IP:");
                	    	
                	        JTextField txtNetworkIP= new JTextField();
                	        txtNetworkIP.setHorizontalAlignment(2);
                	        txtNetworkIP.setText(ip1);
                	        txtNetworkIP.setEditable(false);
                			
                	    	JLabel labelDSLPort = new JLabel();
                	    	labelDSLPort.setHorizontalAlignment(4);
                	    	labelDSLPort.setHorizontalTextPosition(4);
                	    	labelDSLPort.setText("DSL Port:");
                	        
                	        final JTextField txtDSLPort= new JTextField();
                	        txtDSLPort.setHorizontalAlignment(2);
                	        
            	        	JLabel labelDSLVLAN = new JLabel();
                	        labelDSLVLAN.setHorizontalAlignment(4);
                	        labelDSLVLAN.setHorizontalTextPosition(4);
                	        labelDSLVLAN.setText("DSL VLAN:");
                	        
                	        String[] VLANStrings = { "Dynamic(VLAN 1)", "Dynamic(VLAN 2)", "Dynamic(VLAN 3)", "Dynamic(VLAN 4)" };
                	        final JComboBox VLANJComboBox = new JComboBox(VLANStrings);
                			                	                       	      
                	                        	  	
                	    	JLabel labelSpace = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");
                	    	
                	        JButton execute = new JButton("Execute");
                	        final B6CommandResult result = B6CommandResult.newInstance();
                	        
                	        execute.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                                	SwingUtilities.invokeLater(new Runnable() {
                                		public void run() {
                                			String port = txtDSLPort.getText();
                                			if(port == null || port.trim().equals("")){
        					    				CalixMessageUtils.showMessage("Missing DSL Port, please enter a Integer value between 1-48.");
        					    				return;
                                			}
                                    	    try {
                            					if(!((Integer.parseInt(port, 10)<=48 && Integer.parseInt(port, 10)>=1))){
            					    				CalixMessageUtils.showMessage("DSL Port should be a number between 1-48.");
            					    				return;
                            					}
                            				} catch (NumberFormatException e) {
        					    				CalixMessageUtils.showMessage("DSL Port should be a number between 1-48.");
        					    				return;
                            				}
                                			String responseDetail = null;
                                        	TLVB6CommandActionQueryScope queryScope = new TLVB6CommandActionQueryScope(); 
                                			Collection values = null;
                                               IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                                               String ip1 = null;
                                               try {
                                               	if(ip !=null){
                                               		 ip1 = (String) ip.convertTo(String.class, null);   						
                                               	}  
                            						}
                            					 catch (MappingException e1) {
                            						// TODO Auto-generated catch block
                            						e1.printStackTrace();
                            					}

                                			try {
                                				RecordType templateRecordType = TypeRegistry.getInstance().getRecordType("B6CommandResult");
                                				OSDatabase db = (OSDatabase) EMSGui.getInstance().getReadOnlyDatabase();
                                				TransactionHistory history = null;
                                				boolean wasActive = true;
                                				try {
                                					if (!db.isActive()) {
                                						wasActive = false;
                                					} else {
                                						db.rollback();
                                					}
                                					db.begin();
                                					history = ((TLVDatabase) db).createTransactionHistory();
                                					queryScope.setNetworkIP(ip1);
                                					queryScope.setCommandType(commandType);
                                					queryScope.setNetworkName(b6deviceObj.getName());
                                					queryScope.setPort(txtDSLPort.getText());
                                					if("Show PPPoE Interface info".equals(commandType)){
                                						queryScope.setVlan(VLANJComboBox.getSelectedItem().toString());
                                					}else{
                                						queryScope.setVlan("");
                                					}

                                					queryScope.addInterval(new PrefixInterval((TreeValue)templateRecordType.getIdentityAttribute().getFirstType().convertFrom("1", "TL1")));
                                					values = db.getAllObjects(queryScope, templateRecordType);
                                					
                                		           if(!values.isEmpty()){
                                		            	B6CommandResult obj = (B6CommandResult) (values.toArray())[0];
                                		            	IValue responseValue = obj.getAttributeValue(B6CommandResult.ATTR_RESPONSE);
                                		            	if(responseValue != null){
                                		            		responseDetail = (String) responseValue.convertTo(String.class, null);
                                		            	}    

                                		            }
                                		           if(responseDetail != null){
                                    		           result.setAttributeValue(B6CommandResult.ATTR_RESPONSE, responseDetail);
                                		           }
                                					db.commit();
                                					if (!wasActive) {
                                						db.begin();
                                					}              					
                                	    			if(responseDetail ==null || responseDetail.isEmpty()){
                                	    				CalixMessageUtils.showMessage("There is no response for "+b6deviceObj.getName());
                                	    			}
                                				} catch (Exception e1) {
                                					e1.printStackTrace();
                                					CalixMessageUtils.showHWErrorException(history);
                                					try {
                                						db.rollback();
                                					} catch (Exception cex) {
                                						Code.warning(cex);
                                					}
                                				}
                                				if (wasActive && !db.isActive()) {
                                					try {
                                						db.begin();
                                					} catch (Throwable ignore) {
                                					}
                                				}
                                			} catch (Exception ex) {
                                				Code.warning("Unable to execute command", ex);
                                			}
                                		}
                                	});
                	            
                	            }
                	        });
                	        
                	        JButton closeButton1 = new JButton("Close");
                	        JButton closeButton2 = new JButton("Close");
                	        closeButton1.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	            	mainPanel.dispose();
                	            }
                	        });
                	        closeButton2.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	            	mainPanel.dispose();
                	            }
                	        });
                	        
                	        JPanel actionPanel = new JPanel();
                	        actionPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
                	        actionPanel.setBackground(Color.white);
                	        actionPanel.add(execute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        actionPanel.add(closeButton1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        
                	        container1.add(labelNetworkName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtNetworkName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        container1.add(labelNetworkIP, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtNetworkIP, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        container1.add(labelDSLPort, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtDSLPort, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        if("Show PPPoE Interface info".equals(commandType)){
                    	        container1.add(labelDSLVLAN, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                    	        container1.add(VLANJComboBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                    	        container1.add(labelSpace, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 250), null));
                    	        container1.add(actionPanel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        }else{
                    	        container1.add(labelSpace, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 250), null));
                    	        container1.add(actionPanel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        }
                	        

                	        
                	       
                	        container2.setBackground(Color.white);                			        
                	    	final JTextArea txtResponse =new JTextArea("",20,20);
                			txtResponse.setEditable(false);
                			txtResponse.setLineWrap(true);
                			txtResponse.setBorder(new LineBorder(null,0)); 
                			JScrollPane responsePane =new JScrollPane(txtResponse);
                			responsePane.setBorder(BorderFactory.createLineBorder(LookAndFeelMgr.getControlColor()));
                			responsePane.setOpaque(false); 
                			responsePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                			
                			JLabel warningLabel = new JLabel(CalixConstants.B6_SWITCH_TAB_WARNING);
                			warningLabel.setFont(new Font("Verdana",Font.ITALIC, 13));
                			
                	    	JLabel labelSpace2 = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");

                	    	JLabel labelSpace3 = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");
                	    	
                			container2.add(labelSpace2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(responsePane, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400,-1), null));
                			container2.add(labelSpace3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

                			container2.add(warningLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(closeButton2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container.addChangeListener(new ChangeListener() {

								@Override
								public void stateChanged(ChangeEvent e) {
									 String response = null;
			     		            	try {
											IValue responseValue = result.getAttributeValue(B6CommandResult.ATTR_RESPONSE);
											if(responseValue != null){
												response = (String) responseValue.convertTo(String.class, null);
											}
										} catch (MappingException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}    
			                	        
									txtResponse.setText(response);
								}
                	
                	        });             		                			
                			mainPanel.add(container);
                			mainPanel.setModal(true);
                			mainPanel.setFocusable(true);
                			mainPanel.setVisible(true);
                		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
                	});
                	
                }
            };
        


        action.putValue(Action.NAME, commandType);
        return action;
    }
    
    private AbstractAction getDSCToolsCommandAction(final OSBaseObject network, final String commandType){
        AbstractAction action = null;
            action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	SwingUtilities.invokeLater(new Runnable() {
                		public void run() {
                     	   final CalixB6Device b6deviceObj = (CalixB6Device)network;
                			if ( !b6deviceObj.isConnected() ){
                				CalixMessageUtils.showErrorMessage(b6deviceObj.getName()+" is disconnected, Please connect it first.");
                				return;
                			}
                			
	               			 IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
	                         String ip1 = null;
	                         try {
	                         	if(ip !=null){
	                         		 ip1 = (String) ip.convertTo(String.class, null);   						
	                         	}  
	      						}
	      					 catch (MappingException e1) {
	      						// TODO Auto-generated catch block
	      						e1.printStackTrace();
	      					}
                			
                			final JDialog mainPanel = new JDialog();
                			mainPanel.setIconImage(ImageUtils.getImage("symbol.gif"));
                			mainPanel.setSize(500,450);
                			mainPanel.setLocationRelativeTo(null);
                			mainPanel.setTitle(commandType);
                			
                			JTabbedPane container = new JTabbedPane();
                			JPanel container1 = new JPanel();
                			JPanel container2 = new JPanel();
                			container.addTab("General", container1);
                			container.addTab("Result", container2);
                			
                			container1.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
                			container1.setBackground(Color.white);
                	    	
                			container2.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
                	    	JLabel labelNetworkName = new JLabel();
                	        labelNetworkName.setHorizontalAlignment(4);
                	        labelNetworkName.setHorizontalTextPosition(4);
                	        labelNetworkName.setText("Network Name:");
                	        
                	        JTextField txtNetworkName= new JTextField();
                	        txtNetworkName.setHorizontalAlignment(2);
                	        txtNetworkName.setText(b6deviceObj.getName());
                	        txtNetworkName.setEditable(false);
                	        
                	    	JLabel labelNetworkIP = new JLabel();
                	    	labelNetworkIP.setHorizontalAlignment(4);
                	    	labelNetworkIP.setHorizontalTextPosition(4);
                	    	labelNetworkIP.setText("Network IP:");
                	    	
                	        JTextField txtNetworkIP= new JTextField();
                	        txtNetworkIP.setHorizontalAlignment(2);
                	        txtNetworkIP.setText(ip1);
                	        txtNetworkIP.setEditable(false);
                			
                	    	JLabel labelShowCustomer = new JLabel();
                	    	labelShowCustomer.setHorizontalAlignment(4);
                	    	labelShowCustomer.setHorizontalTextPosition(4);
                	    	labelShowCustomer.setText("show");
                	        
                	        final JTextField txtShowCommand= new JTextField();
                	        txtShowCommand.setHorizontalAlignment(2);
                			                	                       	      
                	                        	  	
                	    	JLabel labelSpace = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");
                	    	
                	        JButton execute = new JButton("Execute");
                	        final B6CommandResult result = B6CommandResult.newInstance();
                	        
                	        execute.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                                	SwingUtilities.invokeLater(new Runnable() {
                                		public void run() {
                                			String showCommand = txtShowCommand.getText();
                                			String responseDetail = null;
                                        	TLVB6CommandActionQueryScope queryScope = new TLVB6CommandActionQueryScope(); 
                                			Collection values = null;
                                               IValue ip = b6deviceObj.getAttributeValue(CalixB6Device.ATTR_IPADRESS);
                                               String ip1 = null;
                                               try {
                                               	if(ip !=null){
                                               		 ip1 = (String) ip.convertTo(String.class, null);   						
                                               	}  
                            						}
                            					 catch (MappingException e1) {
                            						// TODO Auto-generated catch block
                            						e1.printStackTrace();
                            					}

                                			try {
                                				RecordType templateRecordType = TypeRegistry.getInstance().getRecordType("B6CommandResult");
                                				OSDatabase db = (OSDatabase) EMSGui.getInstance().getReadOnlyDatabase();
                                				TransactionHistory history = null;
                                				boolean wasActive = true;
                                				try {
                                					if (!db.isActive()) {
                                						wasActive = false;
                                					} else {
                                						db.rollback();
                                					}
                                					db.begin();
                                					history = ((TLVDatabase) db).createTransactionHistory();
                                					queryScope.setNetworkIP(ip1);
                                					queryScope.setCommandType("show "+showCommand);
                                					queryScope.setNetworkName(b6deviceObj.getName());
                                					queryScope.setPort("");
                                					queryScope.setVlan("");

                                					queryScope.addInterval(new PrefixInterval((TreeValue)templateRecordType.getIdentityAttribute().getFirstType().convertFrom("1", "TL1")));
                                					values = db.getAllObjects(queryScope, templateRecordType);
                                					
                                		           if(!values.isEmpty()){
                                		            	B6CommandResult obj = (B6CommandResult) (values.toArray())[0];
                                		            	IValue responseValue = obj.getAttributeValue(B6CommandResult.ATTR_RESPONSE);
                                		            	if(responseValue != null){
                                		            		responseDetail = (String) responseValue.convertTo(String.class, null);
                                		            	}    

                                		            }
                                		           if(responseDetail != null){
                                    		           result.setAttributeValue(B6CommandResult.ATTR_RESPONSE, responseDetail);
                                		           }
                                					db.commit();
                                					if (!wasActive) {
                                						db.begin();
                                					}              					
                                	    			if(responseDetail ==null || responseDetail.isEmpty()){
                                	    				CalixMessageUtils.showMessage("There is no response for "+b6deviceObj.getName());
                                	    			}
                                				} catch (Exception e1) {
                                					e1.printStackTrace();
                                					CalixMessageUtils.showHWErrorException(history);
                                					try {
                                						db.rollback();
                                					} catch (Exception cex) {
                                						Code.warning(cex);
                                					}
                                				}
                                				if (wasActive && !db.isActive()) {
                                					try {
                                						db.begin();
                                					} catch (Throwable ignore) {
                                					}
                                				}
                                			} catch (Exception ex) {
                                				Code.warning("Unable to execute command", ex);
                                			}
                                		}
                                	});
                	            
                	            }
                	        });
                	        
                	        JButton closeButton1 = new JButton("Close");
                	        JButton closeButton2 = new JButton("Close");
                	        closeButton1.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	            	mainPanel.dispose();
                	            }
                	        });
                	        closeButton2.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	            	mainPanel.dispose();
                	            }
                	        });
                	        
                	        JPanel actionPanel = new JPanel();
                	        actionPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
                	        actionPanel.setBackground(Color.white);
                	        actionPanel.add(execute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        actionPanel.add(closeButton1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        
                	        container1.add(labelNetworkName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtNetworkName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));

                	        container1.add(labelNetworkIP, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtNetworkIP, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        container1.add(labelShowCustomer, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container1.add(txtShowCommand, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                	        
                	        container1.add(labelSpace, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 250), null));
                	       
                	        container1.add(actionPanel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

                	        
                	       
                	        container2.setBackground(Color.white);                			        
                	    	final JTextArea txtResponse =new JTextArea("",20,20);
                			txtResponse.setEditable(false);
                			txtResponse.setLineWrap(true);
                			txtResponse.setBorder(new LineBorder(null,0)); 
                			JScrollPane responsePane =new JScrollPane(txtResponse);
                			responsePane.setBorder(BorderFactory.createLineBorder(LookAndFeelMgr.getControlColor()));
                			responsePane.setOpaque(false); 
                			responsePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                			
                			JLabel warningLabel = new JLabel(CalixConstants.B6_SWITCH_TAB_WARNING);
                			warningLabel.setFont(new Font("Verdana",Font.ITALIC, 13));
                			
                	    	JLabel labelSpace2 = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");
                	    	
                	    	JLabel labelSpace3 = new JLabel();
                	    	labelSpace.setHorizontalAlignment(4);
                	    	labelSpace.setHorizontalTextPosition(4);
                	    	labelSpace.setText("");

                			container2.add(labelSpace2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(responsePane, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
                			container2.add(labelSpace3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

                			container2.add(warningLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                			container2.add(closeButton2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
                	        container.addChangeListener(new ChangeListener() {

								@Override
								public void stateChanged(ChangeEvent e) {
									 String response = null;
			     		            	try {
											IValue responseValue = result.getAttributeValue(B6CommandResult.ATTR_RESPONSE);
											if(responseValue != null){
												response = (String) responseValue.convertTo(String.class, null);
											}
										} catch (MappingException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}    
			                	        
									txtResponse.setText(response);
								}
                	
                	        });             		                			
                			mainPanel.add(container);
                			mainPanel.setModal(true);
                			mainPanel.setFocusable(true);
                			mainPanel.setVisible(true);
                		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
                	});
                	
                }
            };
        


        action.putValue(Action.NAME, commandType);
        return action;
    }
    
    private void b6CommandResultDailog(String networkName, String networkIP, String request, String response){
    	JDialog dialog = new JDialog();
    	dialog.setIconImage(ImageUtils.getImage("symbol.gif"));
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    	panel.setBackground(Color.white);
    	
    	JLabel labelNetworkName = new JLabel();
        labelNetworkName.setHorizontalAlignment(4);
        labelNetworkName.setHorizontalTextPosition(4);
        labelNetworkName.setText("Network Name:");
        
        JTextField txtNetworkName= new JTextField();
        txtNetworkName.setHorizontalAlignment(2);
        txtNetworkName.setText(networkName);
        
    	JLabel labelNetworkIP = new JLabel();
    	labelNetworkIP.setHorizontalAlignment(4);
    	labelNetworkIP.setHorizontalTextPosition(4);
    	labelNetworkIP.setText("Network IP:");
    	
        JTextField txtNetworkIP= new JTextField();
        txtNetworkIP.setHorizontalAlignment(2);
        txtNetworkIP.setText(networkIP);
    	 
    	JLabel labelRequest = new JLabel();
    	labelRequest.setHorizontalAlignment(4);
    	labelRequest.setHorizontalTextPosition(4);
    	labelRequest.setText("Request:");
    	
        JTextField txtRequest= new JTextField();
        txtRequest.setHorizontalAlignment(2);
        txtRequest.setText(request);
        
    	JLabel labelResponse = new JLabel();
    	labelResponse.setHorizontalAlignment(4);
    	labelResponse.setHorizontalTextPosition(4);
    	labelResponse.setText("Response:");
    	
    	JTextArea txtResponse =new JTextArea(response,20,20);
		txtResponse.setEditable(false);
		txtResponse.setLineWrap(true);
		txtResponse.setBorder(new LineBorder(null,0)); 
		JScrollPane responsePane =new JScrollPane(txtResponse);
		responsePane.setBorder(BorderFactory.createLineBorder(LookAndFeelMgr.getControlColor()));
		responsePane.setOpaque(false); 
		responsePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
        
        panel.add(labelNetworkName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtNetworkName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelNetworkIP, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtNetworkIP, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelRequest, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtRequest, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        		
        panel.add(labelResponse, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(responsePane, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        dialog.add(panel);
        dialog.setSize(500, 450);
        //dialog.setResizable(false); //cannot change size
        /*int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height)/2;
        dialog.setLocation(x, y);*/
        GuiUtil.centerWindow(dialog);
        dialog.setModal(true);
        dialog.setFocusable(true);
        dialog.setTitle("B6 CLI Command Response");
        dialog.setVisible(true);

    	
    }
    
    /*private void b6CommandResultDataDailog(String networkName,String networkIP,String request,String status, String startTime,String endTime,String response){
    	JDialog dialog = new JDialog();
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
    	panel.setBackground(Color.white);
    	
    	JLabel labelNetworkName = new JLabel();
        labelNetworkName.setHorizontalAlignment(4);
        labelNetworkName.setHorizontalTextPosition(4);
        labelNetworkName.setText("Network Name:");
        
        JTextField txtNetworkName= new JTextField();
        txtNetworkName.setHorizontalAlignment(2);
        txtNetworkName.setText(networkName);
        
    	JLabel labelNetworkIP = new JLabel();
    	labelNetworkIP.setHorizontalAlignment(4);
    	labelNetworkIP.setHorizontalTextPosition(4);
    	labelNetworkIP.setText("Network IP:");
    	
        JTextField txtNetworkIP= new JTextField();
        txtNetworkIP.setHorizontalAlignment(2);
        txtNetworkIP.setText(networkIP);
    	 
    	JLabel labelRequest = new JLabel();
    	labelRequest.setHorizontalAlignment(4);
    	labelRequest.setHorizontalTextPosition(4);
    	labelRequest.setText("Request:");
    	
        JTextField txtRequest= new JTextField();
        txtRequest.setHorizontalAlignment(2);
        txtRequest.setText(request);
        
    	JLabel labelStatus= new JLabel();
    	labelStatus.setHorizontalAlignment(4);
    	labelStatus.setHorizontalTextPosition(4);
    	labelStatus.setText("Status:");
    	
        JTextField txtStatus= new JTextField();
        txtStatus.setHorizontalAlignment(2);
        txtStatus.setText(status);
        
    	JLabel labelStartTime= new JLabel();
    	labelStartTime.setHorizontalAlignment(4);
    	labelStartTime.setHorizontalTextPosition(4);
    	labelStartTime.setText("Start Time:");
    	
        JTextField txtStartTime= new JTextField();
        txtStartTime.setHorizontalAlignment(2);
        txtStartTime.setText(startTime);
        
    	JLabel labelEndTime= new JLabel();
    	labelEndTime.setHorizontalAlignment(4);
    	labelEndTime.setHorizontalTextPosition(4);
    	labelEndTime.setText("End Time:");
    	
        JTextField txtEndTime= new JTextField();
        txtEndTime.setHorizontalAlignment(2);
        txtEndTime.setText(endTime);
        
    	JLabel labelResponse = new JLabel();
    	labelResponse.setHorizontalAlignment(4);
    	labelResponse.setHorizontalTextPosition(4);
    	labelResponse.setText("Response:");
    	
    	JTextArea txtResponse =new JTextArea(response,20,20);
		txtResponse.setEditable(false);
		txtResponse.setLineWrap(true);
		txtResponse.setBorder(new LineBorder(null,0)); 
		JScrollPane responsePane =new JScrollPane(txtResponse);
		responsePane.setBorder(BorderFactory.createLineBorder(LookAndFeelMgr.getControlColor()));
		responsePane.setOpaque(false); 
		responsePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
        
        panel.add(labelNetworkName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtNetworkName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelNetworkIP, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtNetworkIP, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelRequest, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtRequest, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        	
        panel.add(labelStatus, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtStatus, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelStartTime, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtStartTime, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelEndTime, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(txtEndTime, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        panel.add(labelResponse, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        panel.add(responsePane, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(400, -1), null));
        
        dialog.add(panel);
        dialog.setSize(500, 550);
        //dialog.setResizable(false); //cannot change size
        GuiUtil.centerWindow(dialog);
        dialog.setModal(true);
        dialog.setFocusable(true);
        dialog.setTitle("B6 CLI Command Response");
        dialog.setVisible(true);    	
    }*/
    
    private AbstractAction getCutthoughtAction(final OSBaseObject network, final int cutThroughMode){
        // Begin bug 51355 fix by James Wang 20111219
        AbstractAction action = null;        
        if (cutThroughMode == 1){
            action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BseriesDeviceModuleInitImpl impl = (BseriesDeviceModuleInitImpl) DeviceModuleImpls.getInstance().getDeviceImpl(BseriesDeviceModuleInitImpl.DEVICE_TYPE_OBJ);
                    impl.cutThroughTL1((BaseEMSDevice) network, cutThroughMode);
                }
            };                        
            //action.putValue(Action.NAME, "CUT-THROUGH TELNET");
        }else if(cutThroughMode == 2){
            // action.putValue(Action.NAME, "CUT-THROUGH SSH");
            action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    TLVDatabase db = ((TLVDatabase)getPanelController().getDatabase());
                        BseriesDeviceModuleInitImpl impl = (BseriesDeviceModuleInitImpl) DeviceModuleImpls.getInstance().getDeviceImpl(BseriesDeviceModuleInitImpl.DEVICE_TYPE_OBJ);
                        impl.cutThroughWeb((BaseEMSDevice) network);
                }
            };
            action.putValue(Action.NAME, "CUT-THROUGH WEB");
            // End bug 51355 fix by James Wang 20111219
        }else{
        	String command = null;
        	switch(cutThroughMode){
        	case 3: command = "Show BLC Configuration"; break;
        	case 4: command = "Show EPS Information"; break;
        	case 5: command = "Show EPS Map(All)"; break;
        	case 6: command = "Show EPS Map(Local)"; break;
        	case 7: command = "Show Modem MAC Address"; break;
        	case 8: command = "Show Voltage & Temp"; break;
        	case 9: command = "SHOW-BOUNDING-GROUPS"; break;        	
        	}
            action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BseriesDeviceModuleInitImpl impl = (BseriesDeviceModuleInitImpl) DeviceModuleImpls.getInstance().getDeviceImpl(BseriesDeviceModuleInitImpl.DEVICE_TYPE_OBJ);
                    impl.cutThroughTL1((BaseEMSDevice) network, cutThroughMode);
                }
            };                        
            action.putValue(Action.NAME, command);
        }
        return action;
    }
    
    
    private AbstractAction getAuditLogAction(final OSBaseObject network){
        AbstractAction action = null;
    	final BaseEMSDevice b6DeviceObj = (BaseEMSDevice)network;

        action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
               	Properties devProp = new Properties();
            	IValue oltKey= b6DeviceObj.getIPAddress1();
            	String sOltIpaddress ="";
				try {
					sOltIpaddress = (String) oltKey.convertTo(String.class, null);
					devProp.put("name", sOltIpaddress);
				} catch (MappingException e1) {
					e1.printStackTrace();
				}
            	
            	JDialog dialog = new JDialog(GuiUtil.getTopFrame(),true);
                mouNavigatorUI = new MOUNavigatorUI(dialog);
                mouNavigatorUI.setCurrentDialog(dialog);	
                mouNavigatorUI.setData(devProp);
            	dialog.getContentPane().add(mouNavigatorUI);
                dialog.setSize(600, 400);
                dialog.setResizable(true);
                dialog.toFront();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setModal(true);
                dialog.setLocationRelativeTo(GuiUtil.getTopFrame());
                dialog.setTitle("Audit log for: " + sOltIpaddress);
            	dialog.setVisible(true);
            }
        };
        action.putValue(Action.NAME, "AUDIT LOG");;
        return action;
    }

    @Override
    protected Collection<EMSRegion> getApplicableRegions() {
        return EMSInit.getEMSRoot(getPanelController().getDatabase()).getNetworkGroups();
    }
    private AbstractAction getGponOntDisplay(final OSBaseObject network){
    	final BaseEMSDevice b6DeviceObj = (BaseEMSDevice)network;
        AbstractAction action = new AbstractAction() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	ViewGponOntPopup gpontPopUp = ViewGponOntPopup.getViewGponOntPopup();
            	gpontPopUp.setProperties(b6DeviceObj);
            	
            	gpontPopUp.setVisible(true);
            }
        };
        action.putValue(Action.NAME, "VIEW GPON ONT's");
        return action;
    }
    private AbstractAction addGponOnt(final OSBaseObject network){
    	final BaseEMSDevice b6DeviceObj = (BaseEMSDevice)network;
        AbstractAction action = new AbstractAction() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	Properties devProp = new Properties();
            	try {
        		   
    				IValue swVersion= b6DeviceObj.getAttributeValue(CalixB6Device.ATTR_SWVERSION);
    				String sSWVersion = (String) swVersion.convertTo(String.class, null);
    				
    				IValue oltKey= b6DeviceObj.getIPAddress1();
    				String sOltIpaddress = (String) oltKey.convertTo(String.class, null);
    				
    				devProp.put("name", sOltIpaddress);
    				devProp.put("oltKey", sOltIpaddress);
    				devProp.put("entitySoftwareRev", sSWVersion);
    				AddGponOntPopup addGponOntPopup = new AddGponOntPopup();
                	
    				addGponOntPopup.setProperties(devProp);
    				//addGponOntPopup.setVisible(true);
    			} catch (MappingException ex) {
    				// TODO Auto-generated catch block
    				ex.printStackTrace();
    			}
            }
        };
        action.putValue(Action.NAME, "ADD ONT");
        return action;
    }

    private AbstractAction viewDiscoveredGponOnt(final OSBaseObject network){
    	final BaseEMSDevice b6DeviceObj = (BaseEMSDevice)network;
        AbstractAction action = new AbstractAction() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	Properties devProp = new Properties();
            	try {
        		   
    				IValue swVersion= b6DeviceObj.getAttributeValue(CalixB6Device.ATTR_SWVERSION);
    				String sSWVersion = (String) swVersion.convertTo(String.class, null);
    				
    				IValue oltKey= b6DeviceObj.getIPAddress1();
    				String sOltIpaddress = (String) oltKey.convertTo(String.class, null);
    				
    				devProp.put("name", sOltIpaddress);
    				devProp.put("oltKey", sOltIpaddress);
    				devProp.put("entitySoftwareRev", sSWVersion);
    				GponDiscoveredONTsPopup gponDiscoveredONTsPopup = new GponDiscoveredONTsPopup();
    				gponDiscoveredONTsPopup.setProperties(devProp);
                	//gponDiscoveredONTsPopup.setVisible(true);
    			} catch (MappingException ex) {
    				// TODO Auto-generated catch block
    				ex.printStackTrace();
    			}
            	
            }
        };
        action.putValue(Action.NAME, "VIEW DISCOVERED ONT");
        return action;
    }
    
    public class BseriesUserComboEditor extends BaseUserComboEditor{
        
    	protected BseriesUserComboEditor.BseriresProfileComboModel m_model;


        public BseriesUserComboEditor() {
            m_model = (BseriesUserComboEditor.BseriresProfileComboModel) newModel();
            m_comp.setModel(m_model);
            m_comp.setSelectedIndex(-1);
        }

        protected ComboBoxModel newModel() {
            return new  BseriesUserComboEditor.BseriresProfileComboModel();
        }


    	public class BseriresProfileComboModel extends BaseUserComboEditor.BaseProfileComboModel{
    		@Override
            public void setValue(IParameterHolder param) throws ValueException {
                m_param = param;
                CalixB6Device obj = (CalixB6Device)((ParameterHolder) param).getObject();
                if(obj != null && obj.getParentDisplayName() != null&& !obj.getParentDisplayName().equals("")){
                	RecordType emsNetworkType = TypeRegistry.getInstance().getRecordType(getTypeName());
                	IValueType chassisAttrType = emsNetworkType.getAttribute(BaseEMSDevice.ATTR_REGION).getFirstType();
                	try {
						m_comp.setSelectedItem(chassisAttrType.convertFrom("CHASSIS-"+obj.getParentDisplayName(), null));
						return;
					} catch (MappingException e) {
						Code.warning(e);
					}
                	
                }
                if (param != null && param.getValue() instanceof IValue) {
                    m_param = param;
                    setValue((IValue) param.getValue());
                } else if (getEmptyObject() != null) {
                    m_comp.setSelectedItem(getEmptyObject());
                } else {
                    m_comp.setSelectedItem(null);
                }
    		}
    	
    	}
    }
    
    public class BseriesAddressCellRenderer extends RegionAddressCellRenderer {

        public BseriesAddressCellRenderer() {
            super();
        }


        /**
         * Override the string conversion process for the ip address.
         *
         * @param obj an IValue object
         * @return a string representation.
         */
        @Override
        public String getString(Object obj) {
            //System.out.println("IPADDR Cell rendering donig conversation");
            try {
                Object value = null;
                if (obj != null) {

                    if (obj instanceof IParameterHolder) {
//                    	if(obj instanceof ParameterHolder){
//                    		CalixB6Device b6 = (CalixB6Device)((ParameterHolder) obj).getObject();
//                    		if(b6 != null)
//                    			value = b6.getParentDisplayName();
//                    	} else
                    		value = ((IParameterHolder) obj).getValue();
                    		if(value instanceof IValue){
                    			String valueString = ((IValue) value).convertTo(String.class, null);
                    			if (valueString.startsWith("CHASSIS-")){
	                                	if(obj instanceof ParameterHolder){
	                            		CalixB6Device b6 = (CalixB6Device)((ParameterHolder) obj).getObject();
	                            		if(b6 != null&&! b6.isModified())
	                            			value = b6.getParentDisplayName();
	                            	}
                    			}
                    		}
                    }

                    if (value == null && obj instanceof ParameterHolder) {
                        Attribute attr = ((ParameterHolder) obj).getAttribute();
                        if (attr != null && attr.isFixedValue()) {
                            value = attr.getDefaultValue();
                        }

                        //handle IValue cases too.
                        if ((value == null) && (obj instanceof IValue)) {
                            value = obj;
                        }

                    }
                    if (value instanceof IValue) {
                        // convert it into string
                        String valueString = ((IValue) value).convertTo(String.class, null);
                        if (valueString.startsWith("REG-")) {
                            valueString = valueString.substring(4, valueString.length());
                        }
//                        else if (valueString.startsWith("CHASSIS-")) {
//                        		valueString = valueString.substring(8, valueString.length());
//                              }
                        return valueString;
                    }//endif
                    else if(value instanceof String){
                    	String chassisName = new StringBuilder("CHASSIS-").append((String)value).toString() ;
                    	return chassisName;
                    }
                }//endif obj !=null
            } catch (Exception ex) {
                Code.warning(ex);
            }
            return null;
        }//getTableCellRenderer

    }//BseriesAddressCellRenderer

    @Override
    protected TableCellRenderer getCellRenderer(int i, IParameterHolder param) throws com.objectsavvy.base.gui.panels.ValueException {
        if (param != null && BaseEMSDevice.ATTR_REGION.equals(param.getName())) {
            return m_bseriesCellRenderer;
        } else if (param != null && BaseEMSDevice.ATTR_SECURITY.equals(param.getName())) {
            return m_secureIconCellRenderer;
        }

        return (super.getCellRenderer(i, param));
    }

    @Override
    protected TableCellRenderer getFilterCellRenderer(int i, IParameterHolder param) throws ValueException {
        if (param != null && BaseEMSDevice.ATTR_REGION.equals(param.getName())) {
            return m_bseriesCellRenderer;
        }
        return (super.getCellRenderer(i, param));
    }
}
