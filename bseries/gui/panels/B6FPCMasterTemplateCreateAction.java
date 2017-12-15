package com.calix.bseries.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;







import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;





import com.calix.bseries.gui.model.B6DSLBoundedTemplate;
import com.calix.bseries.gui.model.B6FPCMasterTemplate;
import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.EMSGuiUtils;
import com.calix.ems.model.EMSUser;
import com.calix.system.gui.components.panels.CalixSingleObjectFormPanel;
import com.calix.system.gui.exceptions.CalixCreateException;
import com.calix.system.gui.model.BaseEMSObject;
import com.objectsavvy.base.gui.panels.BasePanelController;
import com.objectsavvy.base.gui.panels.ParameterHolder;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.Attribute;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.IValueType;
public class B6FPCMasterTemplateCreateAction extends B6TemplateCreateAction{
        public static final int PANEL_WIDTH = 650;
        public static final int PANEL_HEIGHT = 650;
        protected OSBaseObject m_newObj = null;
        protected OSBaseObject resultObj = null;
        protected String m_operation = null;
        public B6FPCMasterTemplateCreateAction(BasePanelController pController, OSBaseObject obj, String operation) {
        super(operation, pController);
        m_newObj = obj;
        m_operation = operation;
		if (operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_ADD)) {
			putValue("Name", B6FPCMasterTemplate.ADD_TAB_NAME);
		} else if (operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_MODIFY)) {
			putValue("Name", B6FPCMasterTemplate.MOD_TAB_NAME);
		} else if (operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_DELETE)) {
			putValue("Name", B6FPCMasterTemplate.DEL_TAB_NAME);
		}
    }
        
        public B6FPCMasterTemplateCreateAction(BasePanelController pController) {
        super(pController);

    }
    protected String getTypeName() {
        return "B6FPCMasterTemplate";
    }
    
    protected String getComponentName() {
        return "B6TemplateID";
    }
    public void doCreate() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					final B6FPCMasterTemplateCreateDialog dialog = new B6FPCMasterTemplateCreateDialog();
					dialog.createDialog(PANEL_WIDTH, PANEL_HEIGHT);
					JDialog mainDialog = templteCombinedPanel(dialog, "B6FPCMasterTemplate", true);
					 if(m_operation != null){
						if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_ADD)){
							mainDialog.setTitle(B6FPCMasterTemplate.ADD_TITLE_NAME);
						}else if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_MODIFY)){
							mainDialog.setTitle(B6FPCMasterTemplate.MOD_TITLE_NAME);
						}else if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_DELETE)){
							mainDialog.setTitle(B6FPCMasterTemplate.DEL_TITLE_NAME);
						}
						mainDialog.setVisible(true);
				    }

				} catch (CalixCreateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
                }
        });

 
    }
    
	static JTextField hostNameComp = null;
	static JTextField operationComp = null;
	static JTextField ipaddressComp = null;
	static JTextField tSourceComp = null;
	static JTextField cmsUserNameComp = null;
	static JTextField startTimeComp = null;
	static JTextField endTimeComp = null;
	static JTextField resultComp = null;
	static JTextField userIpComp = null;
	static JTextField serviceTypeComp = null;
	static JComboBox tpstcModeComp=null;
	
    final class B6FPCMasterTemplateCreateDialog extends GlobalExProfileBaseCreateDialog {
    	B6FPCMasterTemplateCreateDialog() {
            super(m_operation);
        }

    	B6FPCMasterTemplateCreateDialog(OSBaseObject pNewObj) {
            super("Check Result");
            resultObj = pNewObj;
        }
    	        
        protected String validateData(OSBaseObject pNewObj) {
                /*
                try {
                        String priServer = null;
                        IValue priServVal = pNewObj.getAttributeValue("pri-server");
                        if(priServVal != null) {
                                priServer = priServVal.convertTo(String.class, null);
                        }
                        
                                String secServer = null;
                                IValue secServVal = pNewObj.getAttributeValue("sec-server");
                                if(secServVal != null) {
                                        secServer = secServVal.convertTo(String.class, null);
                                }
                                
                                if(priServer != null && secServer != null) {
                                        if(priServer.trim().equals(secServer.trim())) {
                                                return "Secondary Config Server should be different from Primary Config Server.";
                                        }
                                }
                        } catch (MappingException e) {
                                 Code.warning(e);
                        }*/
            return super.validateData(pNewObj);
        }
        
        protected String getHeadingCaption() {
            return "B6 FPCMaster Template";
        }
        protected CalixSingleObjectFormPanel createObjectFormPanel() {
        	B6FPCMasterTemplate pObject= B6FPCMasterTemplate.newInstance();

            if(resultObj != null){
            	pObject = (B6FPCMasterTemplate) resultObj;
            }else{
                try {
    				pObject.setAttributeValue("IPAddress1", m_newObj.getAttributeValue("IPAddress1"));
    				pObject.setAttributeValue("operation", m_operation);
    				pObject.setAttributeValue("device_host_name", ((CalixB6Device)m_newObj).getName());
    				
    	            EMSUser user = EMSGuiUtils.getCurrentUser(EMSInit.getReadonlyDatabase());
    	            pObject.setAttributeValue("CMSUserName", user.getName());
    	            
    	            InetAddress addr = InetAddress.getLocalHost();
    	            pObject.setAttributeValue("UserIp", addr.getHostAddress());
    	          

    			} catch (MappingException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            CalixSingleObjectFormPanel formPanel = new B6FPCMasterTemplateFormPanel(m_controller, getTypeName(), pObject, null, m_networkIdentity);
            formPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            formPanel.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            formPanel.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            return formPanel;
        }
        final class B6FPCMasterTemplateFormPanel extends GlobalExProfileFormPanel {
        	B6FPCMasterTemplateFormPanel(BasePanelController pController, String pTypeName,
                                           OSBaseObject pObject, String[] pAttrs, IValue parentAid) {
                super(pController, pTypeName, pObject, pAttrs, parentAid);
            }
        	
        	//override doLayout, if not, change tab, will resize panel to make it abnormal
        	 @Override
        	    public void doLayout() {
        	        super.doLayout();
        	        Dimension size = new Dimension(PANEL_WIDTH,PANEL_HEIGHT);
        	        setMinimumSize(size);
        	        setPreferredSize(size);
        	 }
        	
        	 @Override
             protected void setupComponent(Component value, ParameterHolder pHolder) {
        				String model="B6-216";
        		 		if(m_newObj!=null){
        		 			model=((CalixB6Device)m_newObj).getModel();
        		 		}else {
        		 			String vlan=((B6FPCMasterTemplate) resultObj).getMatch_Vlan();
        		 			if(null==vlan){
        		 				model="???";
        		 			}
        		 		}
                    	 if(m_operation != null){
                        	 if( m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_ADD)){
                            	 setVisible(B6FPCMasterTemplate.ATTR_ACTIVATE_DSL_PORT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IPMASK_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IPDG_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_KEY_INFO_OLD, false);
                            	//CMS-15595 just for B6216
                            	 if(!model.equals("B6-216")){
                                	 setVisible(B6FPCMasterTemplate.ATTR_MATCH_VLAN, false);
                                	 setVisible(B6FPCMasterTemplate.ATTR_TPSTC_MODE, false);
                            	 }
                        	 }else if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_MODIFY)){
                            	 setVisible(B6FPCMasterTemplate.ATTR_IPMASK_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IPDG_INT, false);
                            	//CMS-15595 just for B6216
                            	 if(!model.equals("B6-216")){
                            		 setVisible(B6FPCMasterTemplate.ATTR_TPSTC_MODE, false);
                                	 setVisible(B6FPCMasterTemplate.ATTR_MATCH_VLAN, false);
                            	 }
                        	 }else if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_DELETE)){
                            	 setVisible(B6FPCMasterTemplate.ATTR_PVC_SERVICE_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_ACTIVATE_SERVICE_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_ACTIVATE_DSL_PORT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_MAC_LEARNING_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_MAC_LIMIT_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IGMP_GROUP_LIMIT_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_MAC_ADDRESS_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IP_ADDRESS_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IPMASK_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_IPDG_INT, false);
                            	 setVisible(B6FPCMasterTemplate.ATTR_KEY_INFO_OLD, false);
                            	 if(!model.equals("B6-216")){
    	                           	 setVisible(B6DSLBoundedTemplate.ATTR_MATCH_VLAN, false);
    	                           	 setVisible(B6DSLBoundedTemplate.ATTR_TPSTC_MODE, false);
    	                         	} 
                        	 }
                     }
                    	 setVisible(B6FPCMasterTemplate.ATTR_RESULT, false);
                    	 setVisible(B6FPCMasterTemplate.ATTR_STARTTIME, false);
                    	 setVisible(B6FPCMasterTemplate.ATTR_ENDTIME, false);
            

        	 }
        	 
             @Override
             protected JComponent getEditingComponent(IValueType pType, Attribute attr) throws ValueException {
            	 String attrName = attr.getName();
            	if (B6FPCMasterTemplate.ATTR_DEVICE_HOST_NAME.equals(attrName)) {
            		hostNameComp = (JTextField) super.getEditingComponent(pType, attr);
            		hostNameComp.setEditable(false);
 					return hostNameComp;
 				} else if(B6FPCMasterTemplate.ATTR_OPERATION.equals(attrName)){
 					operationComp = (JTextField) super.getEditingComponent(pType, attr);
 					operationComp.setEditable(false);
 					return operationComp;
 				} else if(B6FPCMasterTemplate.ATTR_IPADDRESS1.equals(attrName)){
 					ipaddressComp = (JTextField) super.getEditingComponent(pType, attr);
 					ipaddressComp.setEditable(false);
 					return ipaddressComp;
 				} else if(B6FPCMasterTemplate.ATTR_TEMPLATESOURCE.equals(attrName)){
 					tSourceComp = (JTextField) super.getEditingComponent(pType, attr);
 					tSourceComp.setEditable(false);
 					return tSourceComp;
 				} else if(B6FPCMasterTemplate.ATTR_CMSUSERNAME.equals(attrName)){
 					cmsUserNameComp = (JTextField) super.getEditingComponent(pType, attr);
 					cmsUserNameComp.setEditable(false);
 					return cmsUserNameComp;
 				} else if(B6FPCMasterTemplate.ATTR_STARTTIME.equals(attrName)){
 					startTimeComp = (JTextField) super.getEditingComponent(pType, attr);
 					startTimeComp.setEditable(false);
 					return startTimeComp;
 				}else if(B6FPCMasterTemplate.ATTR_ENDTIME.equals(attrName)){
 					endTimeComp = (JTextField) super.getEditingComponent(pType, attr);
 					endTimeComp.setEditable(false);
 					return endTimeComp;
 				}else if(B6FPCMasterTemplate.ATTR_RESULT.equals(attrName)){
 					resultComp = (JTextField) super.getEditingComponent(pType, attr);
 					resultComp.setEditable(false);
 					return resultComp;
 				}else if(B6FPCMasterTemplate.ATTR_USERIP.equals(attrName)){
 					userIpComp = (JTextField) super.getEditingComponent(pType, attr);
 					userIpComp.setEditable(false);
 					return userIpComp;
 				}else if(B6FPCMasterTemplate.ATTR_SERVICE_TYPE.equals(attrName)){
 					serviceTypeComp = (JTextField) super.getEditingComponent(pType, attr);
 					serviceTypeComp.setEditable(false);
 	 				return serviceTypeComp;	
 				}else {
 					return super.getEditingComponent(pType, attr);
 				}
             }
                  
                    
        	/*
                   @Override
                    public void doLayout() {
                        super.doLayout();
                        setDesiredWidth(500);
                        repaint();
                    }
                
            @Override
            protected void addComponent(JLabel pLabel, final JComponent pValue, JLabel pUnit, IParameterHolder pHolder) throws ValueException {
                if ("AeOntEnabled".equalsIgnoreCase(((ParameterHolder) pHolder).getAttribute().getName())) {
                                        super.addComponent(pLabel, pValue, pUnit, pHolder);
                                        // Information at bottom of the panel.
                                        JLabel label = new JLabel("**Secondary config filename is not applicable for AEONTs");
                                        int infoLabelY = 160;
                                        // Calculate the y position bases on the last component in the panel.
                                        Component c = m_content.getComponent(m_content.getComponentCount() - 1);
                                        if (c != null && c.getBounds() != null) {
                                                infoLabelY = c.getBounds().y + 20;
                                        }
                                        m_content.add(label);
                                        m_content.add(new JLabel());
                                        label.setBounds(0, infoLabelY, 520, 20);
                                        label.setFont(LookAndFeelMgr.getItalicUserFont());
                                label.setForeground(LookAndFeelMgr.getDialogBackgroundColor());
                                } else {
                                        super.addComponent(pLabel, pValue, pUnit, pHolder);
                                }
            }*/
        }
        
        public void CreateDetailDialog(B6FPCMasterTemplate obj){
        	//obj.nullAttributeValue(obj.getAttribute("ID"));  
        	
        	  IValue value = obj.getAttributeValue(B6FPCMasterTemplate.ATTR_OPERATION);
              try {
             	if(value !=null){
             		m_operation = (String) value.convertTo(String.class, null);   						
             	}  
  				}
  			 catch (MappingException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
			B6FPCMasterTemplateCreateDialog dialog = new B6FPCMasterTemplateCreateDialog(obj);
			try {
				dialog.createDialog(PANEL_WIDTH, PANEL_HEIGHT);
			} catch (CalixCreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				id = BaseEMSObject.getIdentityStrVal(obj);
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDialog mainDialog = templteCombinedPanel(dialog, "B6FPCMasterTemplate", isTemplateExecutable(obj));
			 if(m_operation != null){
				if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_ADD)){
					mainDialog.setTitle(B6FPCMasterTemplate.ADD_TITLE_NAME);
				}else if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_MODIFY)){
					mainDialog.setTitle(B6FPCMasterTemplate.MOD_TITLE_NAME);
				}else if(m_operation.equalsIgnoreCase(B6FPCMasterTemplate.OPERATION_DELETE)){
					mainDialog.setTitle(B6FPCMasterTemplate.DEL_TITLE_NAME);
				}else{
					mainDialog.setTitle("B6 FPCMaster Template");
				}
				mainDialog.setVisible(true);
		    }			
        }
             
    }
}