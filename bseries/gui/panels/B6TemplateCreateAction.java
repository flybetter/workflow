package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.model.persistence.tlv.TLVB6TemplateQueryScope;
import com.calix.bseries.gui.utils.B6SecurityHelper;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.EMSGui;
import com.calix.ems.gui.EMSGuiUtils;
import com.calix.ems.model.EMSRoot;
import com.calix.ems.model.EMSUser;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.components.create.CalixSingleFormCreateDialog;
import com.calix.system.gui.components.panels.CalixAidFormPanel;
import com.calix.system.gui.components.panels.CalixSingleObjectFormPanel;
import com.calix.system.gui.components.util.ImageUtils;
import com.calix.system.gui.database.TLVDatabase;
import com.calix.system.gui.lookandfeel.LookAndFeelMgr;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.BaseEMSObject;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.panels.CalixPanelController;
import com.calix.system.gui.util.CalixMessageUtils;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.objectsavvy.base.gui.panels.*;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.OSDatabase;
import com.objectsavvy.base.persistence.PrefixInterval;
import com.objectsavvy.base.persistence.TransactionHistory;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.*;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public abstract class B6TemplateCreateAction extends AbstractAction{
	private static final int MAX_PROFILE_NAME_LEN = 30;
    protected CalixPanelController m_controller = null;
    protected String id = null;
    
    public static final String OPERATION_ADD = "ADD";
	public static final String OPERATION_MODIFY = "MOD";
	public static final String OPERATION_DELETE = "DEL";
	
	public static final String ATTR_STARTTIME = "StartTime";
	public static final String ATTR_ENDTIME = "EndTime";
	public static final String ATTR_RESULT = "Result";
	public static final String ATTR_RESPONSEDETAIL = "ResponseDetail";
	
	public static final String ATTR_DEVICE_HOST_NAME = "device_host_name";
	public static final String ATTR_OPERATION = "operation";
	public static final String ATTR_IPADDRESS1 = "IPAddress1";
	public static final String ATTR_KEY_INFO = "key_info";
	public static final String ATTR_PVC_SERVICE_INT = "pvc_service_int";
	public static final String ATTR_DSL_PROFILE_NAME = "dsl_profile_name";
	public static final String ATTR_ACCESS_PROFILE_NAME_INT = "access_profile_name_int";
	public static final String ATTR_PVC_SERVICE_INT_ERROR = "Pvc Service Int should be in the format vpi/vci, where vpi is in range [0,15] and vci in range [32,511]";
	public static final String ATTR_KEY_INFO_REEOR = "Key Info should have a space in between two strings";	
	public static String[] responseInfo = new String[3];
	public static String result = null;	
		
    public B6TemplateCreateAction(String pActionName, BasePanelController pController) {
        super(pActionName);
        m_controller = (CalixPanelController) pController;
    }
    
    public B6TemplateCreateAction(BasePanelController pController) {
        super("B6 Template");
        m_controller = (CalixPanelController) pController;
    }

    protected abstract String getTypeName();
    protected abstract String getComponentName();
    
    protected  String getParentName(){
    	return null;
    }

    public abstract void doCreate();

    @Override
    public void actionPerformed(ActionEvent e) {
        doCreate();
    }

    protected class GlobalExProfileBaseCreateDialog extends CalixSingleFormCreateDialog {

        public GlobalExProfileBaseCreateDialog(String pTitle) {
            super(m_controller, pTitle, TypeRegistry.getInstance().getRecordType(getTypeName()));
        }
        
        public void onCreate() {
       	 
       	 final IDatabase db = CalixCit.getCalixCitInstance().getCreateDatabase();
       		try {                		                		
       			if (!db.isActive())
       				db.begin();		
       			db.create(getNewObject());
       			db.commit();
       		} catch (Exception e1) {
       			e1.printStackTrace();
       		}
    	 
/*            try {
               OSBaseObject obj = getNewObject();
               if ( obj != null ) {
                   DefaultProvisioningCreateHandler handler = new DefaultProvisioningCreateHandler(m_db,this);
                   handler.handleCreateSingleObject(obj);
               }
           }catch(Exception ex) {
           }*/
       }

        @Override
        protected OSBaseObject getNewObject() {
        	 if (CalixMessageUtils.showYesNoDialog( "Do you want to create a new B6 Template?") == JOptionPane.YES_OPTION){
        		 OSBaseObject m_newObj = getFormPanel().getObject();
                 // validate first, no error if return null
                 
                 //if this obj is from M6 detail panel, will reset CMS User Name and User IP
                 try {
     				EMSUser user = EMSGuiUtils.getCurrentUser(EMSInit.getReadonlyDatabase());
     				m_newObj.setAttributeValue("CMSUserName", user.getName());
     				
     				InetAddress addr = InetAddress.getLocalHost();
     				m_newObj.setAttributeValue("UserIp", addr.getHostAddress());
     			} catch (UnknownHostException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			} catch (PersistenceException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			} catch (MappingException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
                 
                 if(validateData(m_newObj) != null) {
                 	CalixMessageUtils.showErrorMessage(validateData(m_newObj));
                     return null;
                 }
                 try {
                	 id = UUID.randomUUID().toString();
                     BaseEMSObject.setIdentityValue(m_newObj, id);
                 } catch (Exception ex) {
                     Code.warning(ex);
                 }
                 return m_newObj;
        	 }else {
        		 return null;
        	 }
        	
           
        }

        protected String validateData(OSBaseObject pNewObj) {
            StringBuilder buf = new StringBuilder();
            
            String str=syntacticCheckForCreate(pNewObj,buf);
            if(null!=str && str.length()>0){
            	return str;
            }

            
            IValue value1 = pNewObj.getAttributeValue(ATTR_DEVICE_HOST_NAME);
            String networkName = null;
            try {
            	if(value1 !=null){
            		networkName = (String) value1.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            if(networkName == null || networkName.equalsIgnoreCase("")){
            	return "Empty B6 Network Name!";
            }
            
            IValue value2 = pNewObj.getAttributeValue(ATTR_IPADDRESS1);
            String networkIP = null;
            try {
            	if(value2 !=null){
            		networkIP = (String) value2.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            if(networkIP == null || networkIP.equalsIgnoreCase("")){
            	return "Empty B6 Network IP Address!";
            }
            
            IValue value3 = pNewObj.getAttributeValue(ATTR_OPERATION);
            String operation = null;
            try {
            	if(value3 !=null){
            		operation = (String) value3.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

            if(operation == null || !(operation.equalsIgnoreCase(OPERATION_ADD) || operation.equalsIgnoreCase(OPERATION_MODIFY) || operation.equalsIgnoreCase(OPERATION_DELETE))){
            	return "Operation can only be ADD, MOD or DEL!";
            }
            
            IValue valuepvc = pNewObj.getAttributeValue(ATTR_PVC_SERVICE_INT);
            String pvc = null;
            try {
            	if(valuepvc !=null){
            		pvc = (String) valuepvc.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            if(pvc != null && !operation.equalsIgnoreCase(OPERATION_DELETE)){
            	if(pvc.trim().equals("")){
            		return "Missing required: Pvc Service Int";
            	}
            	if(pvc.split("/").length != 2){
            		return ATTR_PVC_SERVICE_INT_ERROR;
            	}
        		String vpi=pvc.split("/")[0];
        		String vci=pvc.split("/")[1];
        	    try {
					if(!(Integer.parseInt(vpi, 10)>=0 && Integer.parseInt(vpi, 10)<=15) ||
							!(Integer.parseInt(vci, 10)>=32 && Integer.parseInt(vci, 10)<=511)){
						return ATTR_PVC_SERVICE_INT_ERROR;
					}
				} catch (NumberFormatException e) {
					return ATTR_PVC_SERVICE_INT_ERROR;
				}
            }
            
            IValue valueKey = pNewObj.getAttributeValue(ATTR_KEY_INFO);
            String keyInfo = null;
            try {
            	if(valueKey !=null){
            		keyInfo = (String) valueKey.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            if(keyInfo != null){
            	if(keyInfo.trim().equals("")){
            		return "Missing required: Key Info";
            	}
            	if(keyInfo.split(" ").length<2){
            		return ATTR_KEY_INFO_REEOR;
            	}
            }
            
            IValue valueDslPName = pNewObj.getAttributeValue(ATTR_DSL_PROFILE_NAME);
            String dslPName = null;
            try {
            	if(valueDslPName !=null){
            		dslPName = (String) valueDslPName.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            if(dslPName != null && !operation.equalsIgnoreCase(OPERATION_DELETE)){
            	if(dslPName.trim().equals("")){
            		return "Missing required: Dsl Profile Name";
            	}

            }
            
            IValue valueAccessPName = pNewObj.getAttributeValue(ATTR_ACCESS_PROFILE_NAME_INT);
            String accessPName = null;
            try {
            	if(valueAccessPName !=null){
            		accessPName = (String) valueAccessPName.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            if(accessPName != null && !operation.equalsIgnoreCase(OPERATION_DELETE)){
            	if(accessPName.trim().equals("")){
            		return "Missing required: Access Profile Name Int";
            	}

            }
            
            return null;
        }
        protected String syntacticCheckForCreate(OSBaseObject obj,  StringBuilder buf) {
            IValue valueop = obj.getAttributeValue(ATTR_OPERATION);
            String operation = null;
            try {
            	if(valueop !=null){
            		operation = (String) valueop.convertTo(String.class, null);   						
            	}  
					}
				 catch (MappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

        	
            Attribute[] attrs = obj.getAttributes();
            int i;
            boolean bRequired = false;
            IValue value = null;

            for (i = 0; i < attrs.length; i++) {
                value = obj.getAttributeValue(attrs[i]);
                bRequired = false;

                //required field check
                bRequired = attrs[i].getDomainBoolValue(DomainValues.DOMAIN_TLV, DomainValues.REQUIRED_FOR_CREATE, false);
                bRequired = bRequired && attrs[i].getDomainBoolValue(DomainValues.DOMAIN_GUI, DomainValues.VISIBLE, true);
                if (bRequired) {
                    if (value == null) {
                        String fieldName = attrs[i].getName();
                        String field = fieldName;
                        if (attrs[i].getDescription() != null)
                            fieldName = attrs[i].getDescription();
                        else if (attrs[i].getFirstType().getDescription() != null)
                            fieldName = attrs[i].getFirstType().getDescription();
                        
                        if("com.calix.bseries.gui.model.B6DSLBoundedTemplate".equals(obj.getClass().getName())){
                        	if(operation!=null && operation.equalsIgnoreCase(OPERATION_MODIFY)){
	                        	if("pvc_service_int".equals(field)){
	                        		continue;
	                        	}
                        	}
                        	if(operation!=null && operation.equalsIgnoreCase(OPERATION_DELETE)){
	                        	if("dsl_profile_name".equals(field) || "key_info".equals(field)
	                        			||"pvc_service_int".equals(field) ||"access_profile_name_int".equals(field)){
	                        		continue;
	                        	}
                        	}

                        }
                        if("com.calix.bseries.gui.model.B6FPCMasterTemplate".equals(obj.getClass().getName())){
                        	if(operation!=null && operation.equalsIgnoreCase(OPERATION_DELETE)){
	                        	if("pvc_service_int".equals(field) || "dsl_profile_name".equals(field) 
	                        			|| "access_profile_name_int".equals(field)){
	                        		continue;
	                        	}
                        	}

                        }
                         buf.append("Missing required field: " + fieldName+ "\n");
                    }
                    continue;
                }
            }
            return buf.toString();
        }

        @Override
        public boolean keepDialogOnFailure() {
            return true;
        }

        @Override
        protected CalixAidFormPanel createAidPanel() {
            return null;
        }

        @Override
        protected CalixSingleObjectFormPanel createFormPanel() {
            CalixSingleObjectFormPanel formPanel = createObjectFormPanel();
//            formPanel.setPreferredSize(new Dimension(700, 700));
//            formPanel.setMinimumSize(new Dimension(700, 700));
//            formPanel.setMaximumSize(new Dimension(700, 700));
            return formPanel;
        }
        protected Dimension getPanelSize() {
            return new Dimension(700,700);
        }

        protected CalixSingleObjectFormPanel createObjectFormPanel() {
            return new GlobalExProfileFormPanel(m_controller, getTypeName(), null, null, m_networkIdentity);
        }

        protected class GlobalExProfileFormPanel extends CalixSingleObjectFormPanel {
            protected TreeValue m_parentAid;
            protected TreeValueType m_aidType;
            protected JComponent m_childComp;
            protected JComponent idComponent;
            public GlobalExProfileFormPanel(BasePanelController pController, String pTypeName,
                                               OSBaseObject pObject, String[] pAttrs, IValue parentAid) {
                super(pController, pTypeName, pObject, pAttrs);
                m_parentAid = (TreeValue) parentAid;
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

            @Override
            protected void addComponent(JLabel pLabel, JComponent pValue, JLabel pUnit, IParameterHolder pHolder) throws ValueException {
                if (pHolder != null && ((ParameterHolder) pHolder).getAttribute() != null
                        && ((ParameterHolder) pHolder).getAttribute().isKey()) {
                    pValue.setEnabled(true);
                }
                super.addComponent(pLabel, pValue, pUnit, pHolder);
            }

            @Override
            protected JComponent getEditingComponent(IValueType pType, Attribute attr) throws ValueException {
                JComponent comp = super.getEditingComponent(pType, attr);
              /*  if (attr.isKey() && comp == null) {
                    NamedScalarComponent[] childComps = ((TreeValueType) pType).getComponents();
                    BaseScalarValueType scalarType = childComps[0].getType();
                    m_childComp = new JTextField();
                    //IValueModel model = new ConstrainedStringModel(scalarType, null, m_childComp);
                    IValueModel model = new UserRangeModel(scalarType, null, m_childComp);
                    ((JTextField)m_childComp).setDocument((OSDocument) model);
                    m_aidType = (TreeValueType) attr.getFirstType();
                    
//                    if (comp!=null && "ID".equals(attr.getName()) && comp instanceof JTextField && comp.isEnabled()==true){
                	m_childComp.addFocusListener(
            			new FocusListener(){
							@Override
                            public void focusGained(FocusEvent e) {
								// Do nothing here
							}
							@Override
                            public void focusLost(FocusEvent e) {
								Component component = e.getComponent();
								if(component!=null && component instanceof JTextField){
									String id = ((JTextField)component).getText();
									if(id!=null && id.length()>MAX_PROFILE_NAME_LEN){
										CalixMessageUtils.showMessage("Template ID should not be longer than " + MAX_PROFILE_NAME_LEN + ".");
									}
								}
							}                				
            			}
                	);
//                    }
                	if(attr.getName().equals("ID")){
            			idComponent = m_childComp; 
            		}
                    return m_childComp;
                }
                if(attr.getName().equals("ID")){
        			idComponent = comp; 
        		}*/
                return comp;
            }
            
            private boolean validateID(){
            	if(m_childComp!=null && m_childComp instanceof JTextField){
            		String id = ((JTextField)m_childComp).getText();
            		if(id!=null && id.length()>MAX_PROFILE_NAME_LEN){
            			return false;
            		}
            	}
            	return true;
            }

            protected TreeValue getAid() throws ValueException, MappingException {
                // String id = ((JTextField)m_childComp).getText();
                IValueModel model = BasePanel.getCompModel(m_childComp);
                ((OSDocument)model).clearModelData();
                IValue childRelativeAid = model.getValue();
                IValue[] parentValues = m_parentAid.getAllComponents();
                IValue[] childValues = new IValue[parentValues.length + 1];
                System.arraycopy(parentValues, 0, childValues, 0, parentValues.length);
                childValues[parentValues.length] = childRelativeAid;
                TreeValue resValue = (TreeValue) m_aidType.convertFrom(childValues, null);
                if (m_parentAid != null) {
                    com.calix.system.gui.tlv.TLVUtils.setNetworkComponent(resValue, m_parentAid);
                }
                return resValue;
            }
            
            protected  IValue getParentAid(){
            	return null;
            }

            protected boolean isAidValid() {
                try {
                    IValueModel model = BasePanel.getCompModel(m_childComp);
                    ((OSDocument) model).clearModelData();
                    return (model.getValue() != null);
                } catch (ValueException ex) {
                    Code.warning(ex);
                    return false;
                }
            }
            @Override
			public void populateFieldsFromData(IParameterHolder[][] params)
					throws ValueException {
				super.populateFieldsFromData(params);
				if(idComponent != null){
					((JTextField)idComponent).setText(Integer.toString(setglobalAddressId()));
				}
			}

        	
            @SuppressWarnings("unchecked")
            private Integer setglobalAddressId(){
                try {
                    IDatabase db = getPanelController().getDatabase();

                    OSBaseObject emsRoot = db.load(TypeRegistry.getInstance().getRecordType("EMSRoot"), CalixCit.getCalixCitInstance().getNetworkIdentity());
                    Collection<OSBaseObject> profiles = emsRoot.getAttributeValueCollection(getTypeName());

                    Integer globalId = 1;
                    if ( null == profiles || profiles.isEmpty() ){
                        return globalId;
                    }

                    // get current localId set
                    long limit = 1000;
                    HashSet<Integer> idSet = new HashSet<Integer>();
                    for (Iterator<OSBaseObject> iter = profiles.iterator(); iter.hasNext();) {
                        OSBaseObject obj = iter.next();
                        if ( null == obj || null == obj.getIdentityValue()){
                            continue;
                        }
						if (null != getParentName() && getParentAid() != null) {
							Integer parentID = (Integer) ((TreeValue) obj.getIdentityValue()).getComponent(getParentName()).convertTo(Integer.class,null);
							Integer mvrID = (Integer) ((TreeValue) getParentAid()).getComponent(getParentName()).convertTo(Integer.class,null);
							if (mvrID != null && parentID != null && parentID.intValue()== mvrID.intValue()) {
								idSet.add((Integer) ((TreeValue) obj.getIdentityValue()).getComponent(getComponentName()).convertTo(Integer.class, null));
							}
						} else {
							idSet.add((Integer) ((TreeValue) obj.getIdentityValue()).getComponent(getComponentName()).convertTo(Integer.class, null));
						}
                    }
                    if ( false == idSet.contains(globalId)){
                        return globalId;
                    }
                    Integer  idCandidate = globalId -1;
                    while(idCandidate>0){
                        if ( false == idSet.contains(idCandidate) ){
                            return idCandidate;
                        }
                        idCandidate --;
                    }

                     idCandidate = globalId+1;
                    while(idCandidate<=limit){
                        if ( false == idSet.contains(idCandidate) ){
                            return idCandidate;
                        }
                        idCandidate++;
                    }
                   
                } catch (Exception e) {
                    Code.warning(e);
                }
                return -1;
            }

			@Override
			protected void setupComponent(Component value,
					ParameterHolder pHolder) {
				String attrName = null;
				if (pHolder != null && pHolder.getAttribute() != null) {
					attrName = pHolder.getAttribute().getName();
				}
				if ("AeOntEnabled".equals(attrName)) {
					super.setupComponent(value, pHolder);
					if (value instanceof JComboBox) {
						((JComboBox)value).setSelectedItem("Y");
					}
				} else {
					super.setupComponent(value, pHolder);
				}
			}
			

        }
    }
    
	protected boolean isTemplateExecutable(BaseEMSObject obj){
		//add security
        if (!B6SecurityHelper.isAction()) {
        	return false;
        }
        IValue value = null;
        String networkName= null;
        if(obj != null){
       	  value = ((obj).getAttributeValue("device_host_name"));
            if (value != null){
                try {
                	networkName = (String)value.convertTo(String.class, null);
                           } catch (MappingException e) {
                                   // TODO Auto-generated catch block
                                   e.printStackTrace();
                           }
            }
       }
        
        if(networkName != null){

    		boolean activated = false;
   	 	IDatabase db = EMSInit.getReadonlyDatabase();
   		BaseEMSDevice B6 = null;
           try {             
               if (!db.isActive()) {
                   db.begin();
                   activated = true;
               }
               EMSRoot root = EMSInit.getReadonlyEMSRoot(db);
               Collection<BaseEMSDevice> allB6s = root.getB6Networks();                                        
               Iterator<BaseEMSDevice> networkIter = allB6s.iterator();
               while (networkIter.hasNext()) {
                   BaseEMSDevice network = networkIter.next();
                   if (networkName.equals(network.getNetworkName())){
                       B6 = network;
                       break;
                   }
               }                       
               db.rollback();
           } catch (Exception pex) {
               Code.warning(pex);
           } finally {
               if (activated)
                   try {
                       db.rollback();
                   } catch (Exception ignore) {
                   }
           }   
           if(B6 != null){

           		   String model = null;
                     
                   	   value = (B6).getAttributeValue("Model");
                   	   if (value != null){
                   		    try {
                   		                  model = (String)value.convertTo(String.class, null);
                   		          } catch (MappingException e) {
                   		                  // TODO Auto-generated catch block
                   		                  e.printStackTrace();
                   		          }
                   		}
                      
                      
                      
               		try {
  						Class cls = Class
  								.forName("com.calix.bseries.gui.utils.B6SecurityHelper");
  						Method method = cls
  								.getMethod("isB6write", String.class);
  						Boolean b6write = (Boolean) method.invoke(
  								cls.newInstance(), model);
  						method = cls.getMethod("isReadPriviledge",
  								OSBaseObject.class);
  						Boolean isRead = (Boolean) method.invoke(
  								cls.newInstance(), B6);
  						if (!b6write || isRead) {
  							return false;
  						}
  					} catch (Exception e) {
  						// TODO: handle exception
  					} 
                  
           }
   	
        }
        return true;
	}
	
	public JDialog templteCombinedPanel(final GlobalExProfileBaseCreateDialog dialog, final String recordType, Boolean executable){

                    
         
         JPanel actionPanel = new JPanel();
         actionPanel.setLayout((LayoutManager) new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

         JButton executeButton = new JButton();
         JButton closeButton = new JButton();
         executeButton.setText("Execute");     
         executeButton.setEnabled(executable);
         closeButton.setText("Close");
         
	        executeButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	dialog.onCreate();            	
	            	}
	        });
	        

         actionPanel.add(executeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
         actionPanel.add(closeButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
         
         dialog.getContentPane().add(actionPanel, BorderLayout.SOUTH);                   
         
			final JDialog mainPanel = new JDialog();
			mainPanel.setSize(700,700);
			mainPanel.setLocationRelativeTo(null);
			
			JTabbedPane  container = new JTabbedPane();
			Container container1 = dialog.getContentPane();

			JPanel container2 = new JPanel();

			
			container2.setLayout((LayoutManager) new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));

	    	JLabel labelResult = new JLabel();
	    	labelResult.setHorizontalAlignment(4);
	    	labelResult.setHorizontalTextPosition(4);
	    	labelResult.setText("Result");
	    	labelResult.setForeground(Color.white);
	        
	        final JTextField txtResult= new JTextField();
	        txtResult.setHorizontalAlignment(2);
	        txtResult.setEditable(false);
	        
	    	JLabel labelStartTime = new JLabel();
	    	labelStartTime.setHorizontalAlignment(4);
	    	labelStartTime.setHorizontalTextPosition(4);
	    	labelStartTime.setText("Strat Time");
	    	labelStartTime.setForeground(Color.white);
	        
	        final JTextField txtStartTime= new JTextField();
	        txtStartTime.setHorizontalAlignment(2);
	        txtStartTime.setEditable(false);
	        
	    	JLabel labelEndTime = new JLabel();
	    	labelEndTime.setHorizontalAlignment(4);
	    	labelEndTime.setHorizontalTextPosition(4);
	    	labelEndTime.setText("End Time");
	    	labelEndTime.setForeground(Color.white);
	        
	        final JTextField txtEndTime= new JTextField();
	        txtEndTime.setHorizontalAlignment(2);
	        txtEndTime.setEditable(false);
	        
	    	JLabel labelResponse = new JLabel();
	    	labelResponse.setHorizontalAlignment(4);
	    	labelResponse.setHorizontalTextPosition(4);
	    	labelResponse.setText("Response");
	    	labelResponse.setForeground(Color.white);
	    	
	    	final JTextArea txtResponse =new JTextArea("",30,20);
			txtResponse.setEditable(false);
			txtResponse.setLineWrap(true);
			txtResponse.setBorder(new LineBorder(null,0)); 
			JScrollPane responsePane =new JScrollPane(txtResponse);
			responsePane.setBorder(BorderFactory.createLineBorder(LookAndFeelMgr.getControlColor()));
			responsePane.setOpaque(false); 
			responsePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			JButton close2 = new JButton();
			close2.setText("Close");
			close2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	mainPanel.dispose();
	            	}
	        });
			
			JLabel warningLabel = new JLabel(CalixConstants.B6_SWITCH_TAB_WARNING);
			warningLabel.setFont(new Font("Verdana",Font.ITALIC, 13));
			warningLabel.setForeground(Color.white);
			
			container2.add(labelResult, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
			container2.add(txtResult, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(600, -1), null));
	        
			container2.add(labelStartTime, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
			container2.add(txtStartTime, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(600, -1), null));
	        
			container2.add(labelEndTime, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
			container2.add(txtEndTime, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(600, -1), null));
	        
			container2.add(labelResponse, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
			container2.add(responsePane, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(600, -1), null));
			
			container2.add(warningLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
			container2.add(close2, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

			container.addTab("General", container1);
			container.addTab("Result", container2);
			
			
	        container.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {	
					BaseEMSObject obj = null;
					Collection values = null;
					if(id != null){								
		            	TLVB6TemplateQueryScope queryScope = new TLVB6TemplateQueryScope(); 
		    			try {
		    				RecordType templateRecordType = TypeRegistry.getInstance().getRecordType("GetB6TemplateResult");
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
		    					queryScope.setIPAddress1("");
		    					queryScope.setTemplateSource("");
		    					queryScope.setTemplateRecordType(recordType);
		    					queryScope.setTemplateId(id);
		    					queryScope.addInterval(new PrefixInterval((TreeValue)templateRecordType.getIdentityAttribute().getFirstType().convertFrom("1", "TL1")));
		    					values = db.getAllObjects(queryScope, templateRecordType);
		    					db.commit();
		    					if (wasActive) {
		    						db.begin();
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
		    				Code.warning("Unable to load B6 Template records", ex);
		    			}
		    			
		    			if(values == null || values.isEmpty()){
		    				Code.warning("There are no template record for id"+id);
		    			}else{
		    				if((values.toArray())[0] != null){
		    					obj = (BaseEMSObject) ((values.toArray())[0]);
		    				}
		    			}				    			
		    								
		    			if(obj == null ){
		    				Code.warning("There are no template records for id:"+id);
		    			}else{    	
			                   IValue resultValue = obj.getAttributeValue(ATTR_RESULT);
			                   IValue sTimeValue = obj.getAttributeValue(ATTR_STARTTIME);
			                   IValue eTimeValue = obj.getAttributeValue(ATTR_ENDTIME);
			                   IValue responseValue = obj.getAttributeValue(ATTR_RESPONSEDETAIL);			                   
			                   String sTime = null;		
			                   String eTime = null;	
			                   String response = null;	
			                   try {
			                   	if(resultValue !=null){
			                   		responseInfo[0] = (String) resultValue.convertTo(String.class, null);
			                   		if(responseInfo[0].equals("IN PROCESS")){
			                   			eTime = null;
			                   			responseInfo[1] = null;
			                   		}
			                   	}  
			                   	if(sTimeValue !=null){
			                   		sTime = (String) sTimeValue.convertTo(String.class, null);   						
			                   	}  
			                   	if(eTimeValue !=null){
			                   		eTime = (String) eTimeValue.convertTo(String.class, null);   						
			                   	}  
			                   	if (responseValue != null) {									
			                   		response = (String) responseValue.convertTo(String.class, null);
								}
			                   	if(responseValue ==null){			
			                   	    new Thread(new Runnable(){ 
			                   		            @Override 
			                   		            public void run(){ 				                   		            	
			                   		            	while(responseInfo[0].equals("IN PROCESS")){ 			                   		            		
			                   		                    try { 				                   		                    	
			                   		                    	responseInfo=getResponseData(recordType);			                   		                    	
			                   		                    	txtResult.setText(responseInfo[0]);
			                   		                    	txtEndTime.setText(responseInfo[1]);
			                   		                    	txtResponse.setText(responseInfo[2]);	 			                   							 
			                   		                        Thread.sleep(1000); 
			                   		                    } catch (InterruptedException e) { 			                   		                       
			                   		                        e.printStackTrace(); 
			                   		                    } 			                   		                     			                   		                   
			                   		                }  ;
			                   		                txtResult.setText(responseInfo[0]);
	                   		                    	txtEndTime.setText(responseInfo[1]);
	                   		                    	txtResponse.setText(responseInfo[2]); 
			                   		            }  
			                   		        }).start(); 	
			                   	}else{
			                   		response = (String) responseValue.convertTo(String.class, null);		                   		
			                   		txtResult.setText(responseInfo[0]);
			                   		txtResponse.setText(response);
			                   		txtEndTime.setText(eTime);				                   		
			                   	}
									}
								 catch (MappingException e1) {									
									e1.printStackTrace();
								}			                   
			                   txtStartTime.setText(sTime);	        						    				
		    			}
					}
				}
	
	        });  
	     
	        closeButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	mainPanel.dispose();
	            	}
	        });
	        

			mainPanel.add(container);
			mainPanel.setModal(true);
			mainPanel.setFocusable(true);   
			mainPanel.setIconImage(ImageUtils.getImage("symbol.gif"));
			return mainPanel;
     
	}
	
	public String[] getResponseData(final String recordType){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BaseEMSObject obj = null;
					Collection values = null;
					if (id != null) {
						TLVB6TemplateQueryScope queryScope = new TLVB6TemplateQueryScope();
						try {
							RecordType templateRecordType = TypeRegistry.getInstance().getRecordType("GetB6TemplateResult");
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
								queryScope.setIPAddress1("");
								queryScope.setTemplateSource("");
								queryScope.setTemplateRecordType(recordType);
								queryScope.setTemplateId(id);
								queryScope.addInterval(new PrefixInterval((TreeValue) templateRecordType.getIdentityAttribute().getFirstType().convertFrom("1", "TL1")));
								values = db.getAllObjects(queryScope,templateRecordType);
								db.commit();
								if (wasActive) {
									db.begin();
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
							Code.warning("Unable to load B6 Template records",ex);
						}

						if (values == null || values.isEmpty()) {
							Code.warning("There are no template record for id"+ id);
						} else {
							if ((values.toArray())[0] != null) {
								obj = (BaseEMSObject) ((values.toArray())[0]);
							}
						}

						if (obj == null) {
							Code.warning("There are no template records for id:"+ id);
						} else {
							IValue resultValue = obj.getAttributeValue(ATTR_RESULT);
							IValue sTimeValue = obj.getAttributeValue(ATTR_STARTTIME);
							IValue eTimeValue = obj.getAttributeValue(ATTR_ENDTIME);
							IValue responseValue = obj.getAttributeValue(ATTR_RESPONSEDETAIL);
							String sTime = null;
							try {
								if (resultValue != null) {									
									responseInfo[0] = (String) resultValue.convertTo(String.class, null);									
								} 
								if (sTimeValue != null) {
									sTime = (String) sTimeValue.convertTo(String.class, null);
								}
								if (eTimeValue != null) {									
									responseInfo[1] = (String) eTimeValue.convertTo(String.class, null);									
								}
								if (responseValue != null) {									
									responseInfo[2] = (String) responseValue.convertTo(String.class, null);									
								}
							} catch (MappingException e1) {
								e1.printStackTrace();
							}
						}
					}

					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}).start();
		return responseInfo;
	}
		
}
