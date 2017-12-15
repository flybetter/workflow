package com.calix.bseries.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.calix.ems.gui.panels.layout.scheduler.ScheduleTaskHelper;
import com.calix.ems.gui.panels.layout.scheduler.ScheduledTaskNetworkTreePanel;
import com.calix.ems.gui.panels.layout.scheduler.ScheduledTaskNetworkUpgradePanel;
import com.calix.ems.gui.panels.layout.scheduler.ScheduledTaskPanel;
import com.calix.system.gui.components.dialogs.CalixDialog;
import com.calix.system.gui.model.CalixCit;
import com.objectsavvy.base.gui.util.GuiUtil;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.util.gui.SelectDevicesListPanelIfc;
import com.occam.ems.client.ccl.ServiceMgmtCustomerIfc;
import com.occam.ems.client.ccl.ServiceMgmtSessionManager;
import com.occam.ems.client.util.ClientUtil;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.client.util.ResourceBundleConstants;
import com.occam.ems.common.dataclasses.ServiceMgmtRequestResponse;
import com.occam.ems.common.defines.APINames;
import com.occam.ems.common.util.ResourceBundleUtil;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;

/**
 * User: hzhang
 * Date: 2/22/2012 
 */
public class InventoryTreeDialog extends CalixDialog implements ServiceMgmtCustomerIfc {
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private ScheduledTaskNetworkTreePanel treePanel;
	private int m_networkType;
	private String m_networkTypeStr;
    private SelectDevicesListPanelIfc parentPanel;
    private List<String[]> b6List;
    private boolean isActive = true;
    private IDatabase idb; 
    private Boolean isSingleMode=false;
	
	public InventoryTreeDialog(SelectDevicesListPanelIfc panel, Frame parent, boolean isSingleMode) {
		super(parent, null, true);
		this.isSingleMode = isSingleMode;
		this.parentPanel = panel; 
		
		m_networkTypeStr = parentPanel.getFilterCriteria();
		if (m_networkTypeStr == null){
			m_networkTypeStr = ScheduleTaskHelper.DEVICE_TYPE_B6;
		}
		m_networkType = ScheduleTaskHelper.getNetworkType(m_networkTypeStr);
        setTitle("Select Devices: ");
        
        init();
        setResizable(true);
        setSize(380, 600);
       // setVisible(true);
        
	}

    @Override
    protected JPanel createMainPanel(){
        if(mainPanel == null){
        	mainPanel = new JPanel();
        	initMainPanel();
        }
        return mainPanel;
    }
    
	private void initMainPanel() {
		idb = CalixCit.getCalixCitInstance().getDatabase();
		opendb(idb);
		treePanel = new ScheduledTaskNetworkTreePanel(idb, null,
				ScheduledTaskNetworkUpgradePanel.TYPE_NAME,
				ScheduledTaskPanel.DIALOG_TYPE_CREATE, m_networkType);
        mainPanel.setOpaque(false);
        mainPanel.setMinimumSize(new Dimension(250, 300));
        mainPanel.setPreferredSize(new Dimension(250, 300));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Network Selection"));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(treePanel, BorderLayout.CENTER);
        addMessagePanel();
	}

	public void addMessagePanel() {
		String message = parentPanel.getValidationMessage();
		ResourceBundleUtil rsb_text = ResourceBundleUtil.getInstance(ResourceBundleConstants.MAPS_TEXT);
		JScrollPane scrollPane = new JScrollPane();
		JTextArea   messTextArea = new JTextArea();
		messTextArea.setText(message);
		messTextArea.setCaretPosition(0);
		Font font = messTextArea.getFont().deriveFont(Font.BOLD);
		messTextArea.setFont(font);
		scrollPane.setViewportView(messTextArea);
		scrollPane.setPreferredSize(new Dimension(200,70));
		scrollPane.setMinimumSize(new Dimension(200,70));
		messTextArea.setEditable(false);
		messTextArea.setPreferredSize(null);
		messTextArea.setWrapStyleWord(true);
		scrollPane.setBorder(new TitledBorder(rsb_text.getString("INVENTORYMANAGERUI.MESSAGE")));
		mainPanel.add(scrollPane,BorderLayout.SOUTH);

		if(messTextArea.getText().length()==0)
			scrollPane.setVisible(false);
		else
			scrollPane.setVisible(true);
        
	}    
	private void opendb(IDatabase idb) {
    	try {
			if(!idb.isActive()){
				isActive = false;
				idb.begin();
			};
		} catch (PersistenceException e) {
			e.printStackTrace();
			Code.warning(e);
		}
	}

	private void rollbackDb(IDatabase idb) {
		if(!isActive){
            try {
            	//Keep idb status just as the status before this time. see EMSInt. 
                idb.rollback();
            } catch (Exception ex) {
                Code.warning(ex);
            }
		}
	}

	@Override
    public void handleOk(ActionEvent a) {
    	HashMap<String, List<String>> map = treePanel.populateMapFromDialog();
        if (map.size() == 0){
            JOptionPane.showMessageDialog(GuiUtil.getTopFrame(),"Please select a device","Select Devices",JOptionPane.ERROR_MESSAGE);
            return;
        }
        getB6Inventory(map);
    }
    
    private void getB6Inventory(HashMap<String, List<String>> map) {
        ServiceMgmtRequestResponse req = new ServiceMgmtRequestResponse();
        req.setAPIName(APINames.GET_B6_INVENTORY);
        req.setParams(map);
        ServiceMgmtSessionManager.getInstance().placeRequest(req,this);
        System.out.println("Sending request to Get B6 Inventory");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deliverResponse(long requestID,
			ServiceMgmtRequestResponse res) {
		if(res.getAPIName().equals(APINames.GET_B6_INVENTORY)){
			b6List = (ArrayList<String[]>)res.getData();
			




			if(isSingleMode){
				if(b6List.size() != 1){
					ClientUtil.showPopUpMessage("Select Device", "More than one device is selected. ", 1);
					return;
				}
			}
			
            List<Properties> devices = new ArrayList<Properties>();
            if (b6List != null && b6List.size() > 0){
                for(int i=0; i< b6List.size(); i++){
                    String[] deviceInfo = (String[])b6List.get(i);
                    if(ScheduleTaskHelper.DEVICE_TYPE_B6.equals(m_networkTypeStr) || ClientUtil.isB6NodeSupported(deviceInfo[1], deviceInfo[2], m_networkTypeStr)){
                                    if (deviceInfo.length == ServiceMgmtConstants.GET_B6_INVENTORY_STRING_LENGTH) {
                                            Properties pro = new Properties();
                                            pro.put(ConfigUIConstants.DEVICE_PROPS_HOSTNAME,
                                                            deviceInfo[0]);
                                            pro.put(ConfigUIConstants.DEVICE_PROPS_IP_ADDRESS,
                                                            deviceInfo[0]);
                                            pro.put(ConfigUIConstants.DEVICE_PROPS_NAME,
                                                            deviceInfo[0]);
                                            pro.put(ConfigUIConstants.DEVICE_PROPS_DISPLAYNAME,
                                                            deviceInfo[0]);
                                            if (deviceInfo[1] != null)
                                                pro.put(ConfigUIConstants.DEVICE_PROPS_TYPE,
                                                            deviceInfo[1]);
                                            if (deviceInfo[2] != null)
                                            pro.put(ConfigUIConstants.DEVICE_PROPS_SOFTWARE_VER,
                                                            deviceInfo[2]);
                                            devices.add(pro);
                                    }
                    }
                   else 
                    	continue;
                }
            }
            dispose();            
            this.parentPanel.setDeviceList(devices);

		}
	//	dispose();
		rollbackDb(idb); 
	}
        
        public Object getTreePanelInstance(){
            return treePanel;
        }

}
