package com.calix.bseries.gui.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import com.calix.bseries.gui.model.B6ProxySettingsAction;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.gui.components.panels.CalixFormGeneratorPanel;
import com.calix.system.gui.lookandfeel.LookAndFeelMgr;
import com.calix.system.gui.model.CalixBaseAction;
import com.calix.system.gui.util.CalixActionExecutor;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.ParameterHolder;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.Attribute;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

public class B6ProxySettingsPanel extends CalixFormGeneratorPanel {
	public static final String ATTR_ISPROXYSERVER = "IsProxyServer";
	
	public static final String ATTR_IPADDRESS1 = "IpAddress1";
	public static final String ATTR_PORT1 = "Port1";
	public static final String ATTR_STATUS1 = "Status1";
	
	public static final String ATTR_IPADDRESS2 = "IpAddress2";
	public static final String ATTR_PORT2 = "Port2";
	public static final String ATTR_STATUS2 = "Status2";
	
	public static final String ATTR_IPADDRESS3 = "IpAddress3";
	public static final String ATTR_PORT3 = "Port3";
	public static final String ATTR_STATUS3 = "Status3";
	
	private JComponent ipAddress1FormatJ;
	private JComponent port1FormatJ;
	private JComponent status1FormatJ;
	
	private JComponent ipAddress2FormatJ;
	private JComponent port2FormatJ;
	private JComponent status2FormatJ;
	
	private JComponent ipAddress3FormatJ;
	private JComponent port3FormatJ;
	private JComponent status3FormatJ;
	
	protected AbstractAction m_SyncDeviceAction = null;
	

	        
    public B6ProxySettingsPanel() {
        super("default B6 Proxy Setting", "DEFAULT B6 Proxy Setting", null, true);
        m_customNonVisual = true;
        setDesiredWidth(CalixConstants.DEFAULT_FORM_WIDTH);
        setIdentified(false);      
    }

    @Override
    protected RecordType getRecordType() {
        return TypeRegistry.getInstance().getRecordType("B6ProxySettings");
    }
    
    @Override
    protected JComponent getEditingComponent(IValueType type,   Attribute attr) throws ValueException {
        JComponent comp = super.getEditingComponent(type, attr);
        if (ATTR_IPADDRESS1.equals(attr.getName())) {
        	ipAddress1FormatJ = comp;
        }else if(ATTR_PORT1.equals(attr.getName())){
        	port1FormatJ = comp;
        }else if(ATTR_STATUS1.equals(attr.getName())){
        	status1FormatJ = comp;
        }else if (ATTR_IPADDRESS2.equals(attr.getName())) {
        	ipAddress2FormatJ = comp;
        }else if(ATTR_PORT2.equals(attr.getName())){
        	port2FormatJ = comp;
        }else if(ATTR_STATUS2.equals(attr.getName())){
        	status2FormatJ = comp;
        }else if (ATTR_IPADDRESS3.equals(attr.getName())) {
        	ipAddress3FormatJ = comp;
        }else if(ATTR_PORT3.equals(attr.getName())){
        	port3FormatJ = comp;
        }else if(ATTR_STATUS3.equals(attr.getName())){
        	status3FormatJ = comp;
        }else{
        	return super.getEditingComponent(type, attr);
        }        
        return comp;            
    }
    
	@Override
    public ArrayList<JMenuItem> buildActionMenuItems() {
		m_SyncDeviceAction = getConnectionAction("Sync Device");
        ArrayList<JMenuItem> menuItems = super.buildActionMenuItems();       
            menuItems.add(new JMenuItem(m_SyncDeviceAction));        	       
        return menuItems;
    }
	
	@Override
	public void populateFieldsFromData(IParameterHolder[][] params)
			throws ValueException {
		super.populateFieldsFromData(params);
		IParameterHolder aiparameterholder[] = null;
		if(params != null && params.length > 0){
            	for(int ii=0; ii<params.length; ii++){
            		  aiparameterholder = params[ii];
                      
                      if (aiparameterholder[0] instanceof ParameterHolder) {
                          ParameterHolder holder = (ParameterHolder) aiparameterholder[0];
                          OSBaseObject osb = holder.getObject();
                          if(null != osb){
                               IValue isProxyValue= osb.getAttributeValue(ATTR_ISPROXYSERVER);
                               Integer isProxy = null;
                               try {
                               	if(isProxyValue !=null){
                               		isProxy = (Integer) isProxyValue.convertTo(Integer.class, null);   						
                               	}  
            						}
            					 catch (MappingException e) {
            						// TODO Auto-generated catch block
            						e.printStackTrace();
            					}
                               if(null!=isProxy  && isProxy==1 ){
                            	 setEnable(ipAddress1FormatJ, true);
                            	 setEnable(port1FormatJ, true);
                            	 setEnable(status1FormatJ, false);
                            	 
                            	 setEnable(ipAddress2FormatJ, true);
                            	 setEnable(port2FormatJ, true);
                            	 setEnable(status2FormatJ, false);
                            	 
                            	 setEnable(ipAddress3FormatJ, true);
                            	 setEnable(port3FormatJ, true);
                            	 setEnable(status3FormatJ, false);            	
                               }else{
                              	 setEnable(ipAddress1FormatJ, false);
                              	 setEnable(port1FormatJ, false);
                              	 setEnable(status1FormatJ, false);
                              	 
                              	 setEnable(ipAddress2FormatJ, false);
                              	 setEnable(port2FormatJ, false);
                              	 setEnable(status2FormatJ, false);
                              	 
                              	 setEnable(ipAddress3FormatJ, false);
                              	 setEnable(port3FormatJ, false);
                              	 setEnable(status3FormatJ, false);  
                               }

                           }
                      }	
            	}
              
		}
		
	}
	
    @Override
    public void populateDataFromFields(java.util.List<OSBaseAction> pActions) throws ValueException {	
    	super.populateDataFromFields(pActions);
    	for (int iCount = 0; iCount < m_params.length; iCount++) {
				ParameterHolder holder = ((ParameterHolder) m_params[iCount]);
				if(holder!= null){
					OSBaseObject obj = this.getCurrentObject();
					if(ATTR_PORT1.equals(holder.getName()) && holder.isModified()){
						String port1 = this.getCurrentObject().getAttributeValue(ATTR_PORT1, String.class);
						if(port1 != null && !port1.trim().equals("")){
							boolean isValid = true;
			        	    try {
								if(!((Integer.parseInt(port1)>=1000 && Integer.parseInt(port1, 10)<=65535))){
									isValid = false;
								}
							} catch (NumberFormatException e) {
								isValid = false;
							}
			        	    
		                	if(!isValid){
		                        CalixMessageUtils.showErrorMessage("Port1 must be null or number between 1000-65535");
								throw new ValueException("Port1 must be null or number between 1000-65535", 2);
		                	}			        	    
						}
	                    break;
					}else if(ATTR_PORT2.equals(holder.getName()) && holder.isModified()){
						String port2 = this.getCurrentObject().getAttributeValue(ATTR_PORT2, String.class);
						if(port2 != null && !port2.trim().equals("")){
							boolean isValid = true;
			        	    try {
								if(!((Integer.parseInt(port2)>=1000 && Integer.parseInt(port2, 10)<=65535))){
									isValid = false;
								}
							} catch (NumberFormatException e) {
								isValid = false;
							}
			        	    
		                	if(!isValid){
		                        CalixMessageUtils.showErrorMessage("Port2 must be null or number between 1000-65535");
								throw new ValueException("Port2 must be null or number between 1000-65535", 2);
		                	}			        	    
						}
	                    break;
					}else if(ATTR_PORT3.equals(holder.getName()) && holder.isModified()){
						String port3 = this.getCurrentObject().getAttributeValue(ATTR_PORT3, String.class);
						if(port3 != null && !port3.trim().equals("")){
							boolean isValid = true;
			        	    try {
								if(!((Integer.parseInt(port3)>=1000 && Integer.parseInt(port3, 10)<=65535))){
									isValid = false;
								}
							} catch (NumberFormatException e) {
								isValid = false;
							}
			        	    
		                	if(!isValid){
		                        CalixMessageUtils.showErrorMessage("Port3 must be null or number between 1000-65535");
								throw new ValueException("Port3 must be null or number between 1000-65535", 2);
		                	}			        	    
						}
	                    break;
					}			
				}
			
		}
	}

	 @Override
     protected void setupComponent(Component value, ParameterHolder pHolder) {
		 if(pHolder.getAttribute().getName().equals(ATTR_ISPROXYSERVER)) {
             final JComboBox actionClassSelection = (JComboBox) value;
             actionClassSelection.addActionListener(new ActionListener() {
                 @Override
                public void actionPerformed(ActionEvent e) {
                     JComboBox eTrafficClassSelection = (JComboBox) e.getSource();
                     handleIsProxyServerChange(eTrafficClassSelection);
                 }
             });
         }else{
        	 super.setupComponent(value, pHolder);
         }
	 }
	 
     private void handleIsProxyServerChange(JComboBox trafficClassSelection) {
        try {
             String selectedActionClass = (String) trafficClassSelection.getSelectedItem();
             if (selectedActionClass != null && selectedActionClass.equals("Y")) {            	 
            	 setEnable(ipAddress1FormatJ, true);
            	 setEnable(port1FormatJ, true);
            	 setEnable(status1FormatJ, false);
            	 
            	 setEnable(ipAddress2FormatJ, true);
            	 setEnable(port2FormatJ, true);
            	 setEnable(status2FormatJ, false);
            	 
            	 setEnable(ipAddress3FormatJ, true);
            	 setEnable(port3FormatJ, true);
            	 setEnable(status3FormatJ, false);            	 
             }else{
            	 setEnable(ipAddress1FormatJ, false);
            	 ((JTextField)ipAddress1FormatJ).setText("");
            	 setEnable(port1FormatJ, false);
            	 ((JTextField)port1FormatJ).setText("");
            	 setEnable(status1FormatJ, false);
            	 ((JTextField)status1FormatJ).setText("");
            	 
            	 setEnable(ipAddress2FormatJ, false);
            	 ((JTextField)ipAddress2FormatJ).setText("");
            	 setEnable(port2FormatJ, false);
            	 ((JTextField)port2FormatJ).setText("");
            	 setEnable(status2FormatJ, false);
            	 ((JTextField)status2FormatJ).setText("");
            	 
            	 setEnable(ipAddress3FormatJ, false);
            	 ((JTextField)ipAddress3FormatJ).setText("");
            	 setEnable(port3FormatJ, false);
            	 ((JTextField)port3FormatJ).setText("");
            	 setEnable(status3FormatJ, false);
            	 ((JTextField)status3FormatJ).setText("");
             }
         } catch (Exception ex) {
             Code.warning(ex);
         }
     }
     
     protected void setEnable(JComponent comp, boolean enable) {
         comp.setEnabled(enable);
         if(enable) {
             comp.setForeground(LookAndFeelMgr.getDialogBackgroundColor());
             comp.setBackground(LookAndFeelMgr.getControlColor());
         } else {
             comp.setForeground(LookAndFeelMgr.getDialogDisabledBackgroundColor());  // LookAndFeelMgr.getTextDisabledColor()
             comp.setBackground(LookAndFeelMgr.getControlHighlightColor());
         }
     }
     
     private AbstractAction getConnectionAction(final String connectionName){
         AbstractAction action = new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 proxyAction(e, connectionName);
             }
         };
         action.putValue(Action.NAME, connectionName);
         action.putValue(Action.ACTION_COMMAND_KEY, connectionName);
         return action;
     }
     
     private void proxyAction(final ActionEvent evt, final String status) {
         final IDatabase db = getPanelController().getActionExecutor().getDatabase();
          new CalixActionExecutor(db) {
                  @Override
                  protected void setupAction(ActionEvent evt2, CalixBaseAction pAction, OSBaseObject object) throws MappingException {
                      //BaseEMSDevice pNetwork = (BaseEMSDevice)object;
                      //pAction.setAttributeValue(EMSBseriesConnAction.ATTR_NETWORK, pNetwork.getName());
                      pAction.setAttributeValue(B6ProxySettingsAction.ATTR_ACTIONTYPE, status);
                      pAction.setIdentityValue(object.getIdentityValue());
                  }

                  @Override
                  protected CalixBaseAction getAction(final ActionEvent evt) {
                      try {
                          return (CalixBaseAction) TypeRegistry.getInstance().getRecordType(B6ProxySettingsAction.TYPE_NAME).newInstance();
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
                      
                      return col;
                  }
                  
              }.standardPanelExecute(evt, this, "Sync Device");
       }
	 
}