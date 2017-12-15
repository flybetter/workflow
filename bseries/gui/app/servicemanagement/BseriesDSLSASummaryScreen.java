
/*
 * DSLSASummaryScreen.java
 *
 * Created on November 27, 2007, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.gui.app.servicemanagement;


import java.util.List;

import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.IValuePanel;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.TreeValue;
import com.objectsavvy.base.persistence.meta.TreeValueType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.app.servicemanagement.serviceactivation.DSLServiceSummaryPanel;
import com.occam.ems.client.app.servicemanagement.serviceactivation.FiberServiceSummaryPanel;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import java.util.Properties;
/**
 *
 * @author brao
 */
public class BseriesDSLSASummaryScreen extends BasePanel implements IValuePanel{
    private static DSLServiceSummaryPanel dslServiceSummaryPanel = null;
    private javax.swing.JScrollPane spScrollPane = null;
    /**
     * Creates a new instance of BseriesDSLSASummaryScreen
     */
    public BseriesDSLSASummaryScreen() {
    	dslServiceSummaryPanel = new DSLServiceSummaryPanel();
        setLayout(new java.awt.BorderLayout());
        spScrollPane = new javax.swing.JScrollPane();       
         spScrollPane.setMaximumSize(new java.awt.Dimension(600, 600));
        spScrollPane.setMinimumSize(new java.awt.Dimension(600, 600));
        spScrollPane.setPreferredSize(new java.awt.Dimension(600, 600));
        spScrollPane.setViewportView(dslServiceSummaryPanel);
        add(spScrollPane, java.awt.BorderLayout.CENTER);        
    }
 
    public static DSLServiceSummaryPanel getDSLServiceSummaryInstance(){
    	return dslServiceSummaryPanel;
    }
    private TreeValue getEMSDeviceAid(TreeValue networkAid){
        TreeValue val = null;
        TreeValueType treeType = TypeRegistry.getInstance().getTreeType("EMSAid");
        try {
            val = (TreeValue) treeType.convertFrom(new String[]{"CMS", networkAid.getComponent("networkId").convertTo(String.class, null)}, null);
        } catch (MappingException e) {
            e.printStackTrace();
        }
        return val;
    }

    public void populateFieldsFromData(IParameterHolder[][] pParams) throws ValueException{
        System.out.println("Fiber Summary Screen called");       
        if (pParams.length == 0) {
            System.out.println("Empty params");                
            return ;
         }
		try {
			OSBaseObject ntwkObj = (OSBaseObject) super.getObjects().iterator()
					.next();
			OSBaseObject object = (OSBaseObject) getPanelController()
					.getDatabase().load(
							TypeRegistry.getInstance().getRecordType(
									"CalixB6Device"),
							getEMSDeviceAid((TreeValue) ntwkObj
									.getIdentityValue()));

			String ipAddress = object.getAttributeValue("IPAddress1",
					String.class);
			String deviceType = object.getAttributeValue("Model", String.class);
			String swVersion = object.getAttributeValue("SWVersion",
					String.class);
			System.out.println("Network " + ipAddress + " typeName"
					+ deviceType);

			Properties props = new Properties();
			// props.setProperty(ConfigUIConstants.DEVICE_PROPS_NAME,ipAddress);
			props.setProperty(ServiceMgmtConstants.IPADDRESS, ipAddress);
			props.setProperty(ServiceMgmtConstants.DEVICE_PROPS_TYPE, deviceType);
			// props.setProperty(ConfigUIConstants.DEVICE_PROPS_HOSTNAME,ipAddress);
			props.setProperty(ConfigUIConstants.DEVICE_PROPS_SOFTWARE_VER,
					swVersion);
			props.setProperty(ServiceMgmtConstants.INVOKED_FROM,
					ServiceMgmtConstants.EQUIPMENT_INVENTORY);
			dslServiceSummaryPanel.getAllCopperPorts(props);
		} catch (Exception e) {
			Code.warning(e);

		}

    }

    public void populateDataFromFields(List<OSBaseAction> pActionList) throws ValueException{
       
    }
}
