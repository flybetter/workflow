package com.calix.bseries.gui.navtree;

import com.calix.bseries.gui.connection.CalixMainSocketClient;
import com.calix.system.gui.model.CalixCitConfig;
import com.calix.system.gui.navtree.DeviceFilterImpl;
import com.objectsavvy.base.gui.util.OSTreeComboBoxItem;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.common.proxy.util.PureClientUtils;

import javax.swing.tree.DefaultTreeModel;

public class BseriesDeviceFilterImpl extends DeviceFilterImpl {
    
    @Override
    public void initFilterModel() {
        try {
            m_filterModel = CalixCitConfig.getInstance().getFilterModel();
            OSTreeComboBoxItem emsDevice = getNodeByName("B6");
            OSTreeComboBoxItem emsChassis = getNodeByName("CalixB6Chassis");
            OSTreeComboBoxItem base = new OSTreeComboBoxItem("root", OSTreeComboBoxItem.FOLDER);            

            if(emsDevice != null) {
                base.add(emsDevice);
            }
            if(emsChassis != null) {
                base.add(emsChassis);
            }
            m_filterModel = new DefaultTreeModel(base);
            //Initial for temp
        	if(!(PureClientUtils.commonSocket instanceof CalixMainSocketClient))
        		PureClientUtils.initial(new CalixMainSocketClient());
        } catch (Exception e) {
            Code.warning(e);
        }
    }
    
}
