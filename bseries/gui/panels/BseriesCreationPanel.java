package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.model.B6Settings;
import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.panels.layout.NetworkPanel;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.BaseEMSObject;
import com.calix.system.gui.tlv.TLVUtils;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class BseriesCreationPanel extends NetworkPanel {
    protected JLabel m_webLoginNameLabel = new JLabel("Web Username");
    protected JLabel m_webLoginPasswordLabel = new JLabel("Web Password");

    protected JTextField m_webLoginNameField = new JTextField();
    protected JPasswordField m_webLoginPasswordField = new JPasswordField();
    
    protected JLabel m_networkEnablePasswordLabel = new JLabel("Enable Password");
    protected JPasswordField m_networkEnablePasswordField = new JPasswordField();
    
    private JComponent[] visibleCompListForNetwork = new JComponent[]{m_networkLoginNameLabel, m_networkLoginNameField, m_networkLoginPasswordLabel, m_networkLoginPasswordField,
            m_readCommunityLabel, m_writeCommunityLabel, m_readCommunityTextField, m_writeCommunityTextField};
    

    public BseriesCreationPanel(BaseEMSDevice pNetwork, IDatabase db,
            RecordType pNetworkType) {
        super(pNetwork, db, pNetworkType);
    }

    public BseriesCreationPanel(BaseEMSDevice pNetwork, IDatabase db) {
        super(pNetwork, db);
    }

    public BseriesCreationPanel(IDatabase db, RecordType pNetworkType) {
        super(db, pNetworkType);
    }

    public BseriesCreationPanel(IDatabase db) {
        super(db);
    }

    @Override
    protected void jbInit(RecordType pNetworkType) {
        super.jbInit(pNetworkType);
        if(pNetworkType == TypeRegistry.getInstance().getRecordType("CalixB6Chassis"))
        {
        	m_ipAddr1Label.setVisible(false);
        	m_ipaddr1_Field.setVisible(false);
        	m_webLoginNameLabel.setVisible(false);
        	m_webLoginNameField.setVisible(false);
        	m_webLoginPasswordLabel.setVisible(false);
        	m_webLoginPasswordField.setVisible(false);
        	m_networkEnablePasswordLabel.setVisible(false);
        	m_networkEnablePasswordField.setVisible(false);
        	m_egcInfoLabel.setVisible(false);
        	m_egcStatPanel.setVisible(false);
        	m_autoConnectLabel.setVisible(false);
        	m_autoConnectComboBox.setVisible(false);
        	m_networkNameLabel.setText("Series Number");
        	
        } else {
        	m_networkLoginNameLabel.setText("CLI Username");
 	        m_networkLoginPasswordLabel.setText("CLI Password");
 	        for (JComponent comp : visibleCompListForNetwork){
 	            comp.setVisible(true);
 	        }
 	        populateDataFromSetting();
        }
	       

    }

    @Override
    public void handleOk(ActionEvent a) {
    	if(m_networkType == TypeRegistry.getInstance().getRecordType(CalixB6Device.TYPE_NAME)){
    		 super.handleOk(a);

    	        boolean activatedHere = false;
    	        try {
    	            if(m_webLoginNameField.getText() != null && m_webLoginNameField.getText().length() > 0){
    	                m_network.setAttributeValue(CalixB6Device.ATTR_WEBUSERNAME, m_webLoginNameField.getText().trim());
    	            }
    	            if(m_webLoginPasswordField.getPassword() != null && m_webLoginPasswordField.getPassword().length > 0){
    	                m_network.setAttributeValue(CalixB6Device.ATTR_WEBPASSWORD, new String(m_webLoginPasswordField.getPassword()));
    	            }
    	            if(m_networkEnablePasswordField.getPassword() != null && m_networkEnablePasswordField.getPassword().length > 0){
    	                m_network.setAttributeValue(CalixB6Device.ATTR_ENABLEPASSWORD, new String(m_networkEnablePasswordField.getPassword()));
    	            }
    	            if ( !m_database.isActive() ) {
    	                m_database.begin();
    	                activatedHere = true;
    	            }
    	            EMSRoot root = EMSInit.getReadonlyEMSRoot(m_database);
    	            OSBaseObject profileObj = root.getInternalSNMPProfile("B6");
    	            m_network.setAttributeValue(CalixB6Device.ATTR_PROFILE, profileObj.getIdentityValue());
    	        } catch (Exception e) {
    	            Code.warning(e);
    	        } finally {
    	            if (activatedHere){
    	                try {
    	                    m_database.rollback();
    	                }catch(Exception e) {
    	                    Code.warning(e);
    	                }
    	            }
    	        }
    	}else{
    		try {
    			  	if(!validateSeriesNumber(this.m_networkNameField.getText()))
    			  		return;
    			    if (m_network == null) {
    	                m_network = (BaseEMSDevice) TypeRegistry.getInstance().getRecordType(getTypeName()).newInstance();
    	                String networkName = this.m_networkNameField.getText();
    	                String seriesNumber = networkName;
    	                if (!networkName.startsWith(BaseEMSObject.getPrefix("BaseCMSNetwork")))
    	               //     networkName = BaseEMSObject.getPrefix("BaseCMSNetwork") + "-" + networkName;
    	                	networkName = CalixB6Chassis.CHASSIS_PREFIX + networkName;
    	                m_network.setIdentityValue(m_network.getIdentityAttribute().getFirstType().convertFrom(networkName, "EMS"));
    	                m_network.setAttributeValue(CalixB6Chassis.ATTR_SERIAL_NUMBER, seriesNumber);
    	                m_network.setAttributeValue(CalixB6Chassis.ATTR_DISPLAY_NAME, seriesNumber);
    	                TLVUtils.setNetworkComponent(m_network.getIdentityValue(),EMSInit.getRootIdentity());
    	            }
				      if (isEditMode())
			                m_network.setAttributeValue(BaseEMSDevice.ATTR_REGION, BaseEMSObject.getPrefix("EMSRegion") + "-" + this.m_locationField.getText());
			            else
			                m_network.setAttributeValue(BaseEMSDevice.ATTR_REGION, BaseEMSObject.getPrefix("EMSRegion") + "-" + this.m_locationComboBox.getSelectedItem());
				      TLVUtils.setNetworkComponent(m_network.getIdentityValue(),EMSInit.getRootIdentity());
				      this.m_isOkPressed = true;
				      this.dispose();
			} catch (MappingException e) {
				 Code.warning(e);
			} catch (Exception e) {
				 Code.warning(e);
			}
    	}
       
    }

    @Override
    protected void addWebLoginCompnents() {
        m_mainPanel.addPair(m_webLoginNameLabel, m_webLoginNameField);
        m_mainPanel.addPair(m_webLoginPasswordLabel, m_webLoginPasswordField);
        m_mainPanel.addPair(m_networkEnablePasswordLabel, m_networkEnablePasswordField);
    }
    
    protected boolean validateSeriesNumber(String seriresNumber){
    	Pattern pattern=Pattern.compile("[0-9]{8,12}");
    	Matcher matcher = pattern.matcher(seriresNumber);
    	if(matcher.matches())
    		return true;
    	else
    	{
    		CalixMessageUtils.showErrorMessage("Please input the serires number with right format,\n It should be 8 to 12 digits number.");
    		return false;
    	}
    }
    
    private void populateDataFromSetting(){
        // Set default values for password, read/write communities
        EMSRoot root = EMSInit.getReadonlyEMSRoot(m_database);
        OSBaseObject setting = (OSBaseObject) root.getAttributeValue(EMSRoot.ATTR_B6SETTINGS);
        if (setting != null) {
            //web user name
            String value = setting.getAttributeValue(B6Settings.ATTR_WEBUSERNAME, String.class);
            if (value != null){
                m_webLoginNameField.setText(value);
            }
            //web password
            value = setting.getAttributeValue(B6Settings.ATTR_WEBPASSWORD, String.class);
            if (value != null){
                m_webLoginPasswordField.setText(value);
            }
            //cli user name
            value = setting.getAttributeValue(B6Settings.ATTR_CLIUSERNAME, String.class);
            if (value != null){
                m_networkLoginNameField.setText(value);
            }
            //cli password
            value = setting.getAttributeValue(B6Settings.ATTR_CLIPASSWORD, String.class);
            if (value != null){
                m_networkLoginPasswordField.setText(value);
            }
            //read community
            value = setting.getAttributeValue(B6Settings.ATTR_READCOMMUNITY, String.class);
            if (value != null){
                m_readCommunityTextField.setText(value);
            }
            //write community
            value = setting.getAttributeValue(B6Settings.ATTR_WRITECOMMUNITY, String.class);
            if (value != null){
                m_writeCommunityTextField.setText(value);
            }
            //enable password
            value = setting.getAttributeValue(B6Settings.ATTR_ENABLEPASSWORD, String.class);
            if (value != null){
            	m_networkEnablePasswordField.setText(value);
            }
        }
    }
    
}
