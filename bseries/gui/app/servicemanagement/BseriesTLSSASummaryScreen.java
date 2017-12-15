
/*
 * DSLSASummaryScreen.java
 *
 * Created on November 27, 2007, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.calix.bseries.gui.app.servicemanagement;

//import com.adventnet.nms.startclient.AbstractBaseNmsPanel;
//import com.adventnet.nms.util.NmsUiAPI;
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
import com.occam.ems.client.app.servicemanagement.serviceactivation.TLSServiceSummaryPanel;
import com.occam.ems.client.util.ConfigUIConstants;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtConstants;
import com.occam.ems.common.xml.message.Notification;
import com.occam.ems.common.xml.message.impl.NotificationImpl;

import java.util.Properties;

/**
 *
 * @author brao
 */
public class BseriesTLSSASummaryScreen extends BasePanel implements IValuePanel{
    private static TLSServiceSummaryPanel tlsServiceSummaryPanel = null;
    /**
     * Creates a new instance of BseriesTLSSASummaryScreen
     */
    public BseriesTLSSASummaryScreen() {
        tlsServiceSummaryPanel = new TLSServiceSummaryPanel();
         setLayout(new java.awt.BorderLayout());
        javax.swing.JScrollPane spScrollPane = new javax.swing.JScrollPane();       
         spScrollPane.setMaximumSize(new java.awt.Dimension(600, 600));
        spScrollPane.setMinimumSize(new java.awt.Dimension(600, 600));
        spScrollPane.setPreferredSize(new java.awt.Dimension(600, 600));
        spScrollPane.setViewportView(tlsServiceSummaryPanel);
        add(spScrollPane, java.awt.BorderLayout.CENTER); 
        

    }
 
    public static TLSServiceSummaryPanel getTLSServiceSummaryInstance(){
        return tlsServiceSummaryPanel;
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
               OSBaseObject ntwkObj = (OSBaseObject) super.getObjects().iterator().next();
               OSBaseObject object = (OSBaseObject) getPanelController().getDatabase().load(TypeRegistry.getInstance().getRecordType("CalixB6Device"), 
                        getEMSDeviceAid((TreeValue)ntwkObj.getIdentityValue()));
            
              String ipAddress = object.getAttributeValue("IPAddress1",String.class);
               String deviceType = object.getAttributeValue("Model",String.class);               
                String swVersion = object.getAttributeValue("SWVersion",String.class);
                System.out.println("Network " + ipAddress + " typeName" + deviceType);
           
                Properties props = new Properties();
             //   props.setProperty(ConfigUIConstants.DEVICE_PROPS_NAME,ipAddress);
                props.setProperty(ServiceMgmtConstants.IPADDRESS,ipAddress);
                props.setProperty(ConfigUIConstants.DEVICE_PROPS_TYPE,deviceType);
             //   props.setProperty(ConfigUIConstants.DEVICE_PROPS_HOSTNAME,ipAddress);
                props.setProperty(ConfigUIConstants.DEVICE_PROPS_SOFTWARE_VER,swVersion);
                props.put(ServiceMgmtConstants.INVOKED_FROM_INVENTORY,ServiceMgmtConstants.TRUE);
                tlsServiceSummaryPanel.setDeviceList(props);
                tlsServiceSummaryPanel.getAllTLSActivatedPorts();
            }  catch (Exception e) {
            Code.warning(e);
            
        }
    }

    public void populateDataFromFields(List<OSBaseAction> pActionList) throws ValueException{
       
    }
}
