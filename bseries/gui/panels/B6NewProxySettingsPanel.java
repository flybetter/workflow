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

public class B6NewProxySettingsPanel extends CalixFormGeneratorPanel {	
	public static final String ATTR_IPADDRESS1 = "ProxyServerA";
	public static final String ATTR_PORT1 = "PortA";
	public static final String ATTR_STATUS1 = "StatusA";
	
	public static final String ATTR_IPADDRESS2 = "ProxyServerB";
	public static final String ATTR_PORT2 = "PortB";
	public static final String ATTR_STATUS2 = "StatusB";
		
	private JComponent ipAddress1FormatJ;
	private JComponent port1FormatJ;
	private JComponent status1FormatJ;
	
	private JComponent ipAddress2FormatJ;
	private JComponent port2FormatJ;
	private JComponent status2FormatJ;
		
	protected AbstractAction m_SyncDeviceAction = null;
	

	        
    public B6NewProxySettingsPanel() {
        super("default B6 Proxy Setting", "DEFAULT B6 Proxy Setting", null, true);
        m_customNonVisual = true;
        setDesiredWidth(CalixConstants.DEFAULT_FORM_WIDTH);
        setIdentified(false);      
    }

    @Override
    protected RecordType getRecordType() {
        return TypeRegistry.getInstance().getRecordType("B6NewProxySettings");
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
                            	 setEnable(ipAddress1FormatJ, true);
                            	 setEnable(port1FormatJ, true);
                            	 setEnable(status1FormatJ, false);
                            	 
                            	 setEnable(ipAddress2FormatJ, true);
                            	 setEnable(port2FormatJ, true);
                            	 setEnable(status2FormatJ, false);
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
					if(ATTR_IPADDRESS1.equals(holder.getName()) && holder.isModified()){
						String address1 = this.getCurrentObject().getAttributeValue(ATTR_IPADDRESS1, String.class);
						if(address1 != null && !address1.trim().equals("")){
							boolean isValid = true;
							
			        	    try {
								if(!ipCheck(address1)){
									isValid = false;
								}
							} catch (NumberFormatException e) {
								isValid = false;
							}
			        	    
		                	if(!isValid){
		                        CalixMessageUtils.showErrorMessage("ProxyServerA must be in a proper format");
								throw new ValueException("ProxyServerA must be in a proper format", 2);
		                	}			        	    
						}
//						else{
//							 	CalixMessageUtils.showErrorMessage("ProxyServerA must be not null");
//								throw new ValueException("ProxyServerA must be not null", 2);
//						}
	                    break;
					}else if(ATTR_IPADDRESS2.equals(holder.getName()) && holder.isModified()){
						String address2 = this.getCurrentObject().getAttributeValue(ATTR_IPADDRESS2, String.class);
						if(address2 != null && !address2.trim().equals("")){
							boolean isValid = true;
			        	    try {
								if(!ipCheck(address2)){
									isValid = false;
								}
							} catch (NumberFormatException e) {
								isValid = false;
							}
			        	    
		                	if(!isValid){
		                        CalixMessageUtils.showErrorMessage("ProxyServerB must be in a proper format");
								throw new ValueException("ProxyServerB must be in a proper format", 2);
		                	}			        	    
						}
//						else{
//							 	CalixMessageUtils.showErrorMessage("ProxyServerB must be not null");
//								throw new ValueException("ProxyServerB must be not null", 2);
//						}
	                    break;
					}else if(ATTR_PORT1.equals(holder.getName()) && holder.isModified()){
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
		                        CalixMessageUtils.showErrorMessage("PortA must be between 1000-65535");
								throw new ValueException("PortA must be between 1000-65535", 2);
		                	}			        	    
						}
//						else{
//						 	CalixMessageUtils.showErrorMessage("PortA must be not null");
//							throw new ValueException("PortA must be not null", 2);
//						}					
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
		                        CalixMessageUtils.showErrorMessage("PortB must be between 1000-65535");
								throw new ValueException("PortB must be between 1000-65535", 2);
		                	}			        	    
						}
//						else{
//						 	CalixMessageUtils.showErrorMessage("PortB must be not null");
//							throw new ValueException("PortB must be not null", 2);
//						}
	                    break;
					}
				}
			
		}
	}
  
    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            
            if (text.matches(regex)) {
                
                return true;
            } else {
                
                return false;
            }
        }
        return false;
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