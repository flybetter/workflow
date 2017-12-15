package com.calix.bseries.gui.tool;

import com.calix.bseries.gui.model.EMSBseriesRangeDiscoveryAction;
import com.calix.bseries.util.IPUtils;
import com.calix.system.gui.components.dialogs.CalixDialog;
import com.calix.system.gui.util.CalixMessageUtils;
import com.calix.system.gui.util.IpAddressUtils;
import com.objectsavvy.base.persistence.TxPersistEvent;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.BaseScalarValue;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.util.debug.Code;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JPanel;

public class BseriesRangeDiscoveryDialog extends CalixDialog {
    BseriesRangeDiscoveryPanel bseriesRangeDiscoveryPanel;
    private static final int MAX_THREAD_NUM = 5;
    
    public BseriesRangeDiscoveryDialog(Frame parent) {
        super(parent, null, true);
        setTitle("Range Discovery");
        init();
        setResizable(true);
        setSize(590, 600);
        setVisible(true);
    }

    @Override
    protected JPanel createMainPanel(){
        if(bseriesRangeDiscoveryPanel == null){
            bseriesRangeDiscoveryPanel = new BseriesRangeDiscoveryPanel();
        }
        return bseriesRangeDiscoveryPanel;
    }
    
    @Override
    public void handleOk(ActionEvent a) {

        RangeDiscoverySender rangeDiscoverySender = null;
        try {
            Vector vector = bseriesRangeDiscoveryPanel.getTableData();
            HashSet<String> includes = new HashSet<String>();
            HashSet<String> excludes = new HashSet<String>();
            for (int i = 0; i < vector.size(); i++){
                Vector<Object> objs = (Vector<Object>)vector.get(i);
                String[] ipRange = ((String)objs.get(0)).split("/");
                String startIp = ipRange[0];
                String endIp = ipRange[1];
                String netMask = (String)objs.get(1);
                boolean excluded = (Boolean)objs.get(2);
                
                if (excluded){
                    excludes.addAll(IPUtils.getIPsInRange(startIp, endIp, netMask));
                } else {
                    includes.addAll(IPUtils.getIPsInRange(startIp, endIp, netMask));
                }
            }
            includes.removeAll(excludes);
            
            if (includes.isEmpty()){
                throw new Exception("No IP in range(s) to discover");
            }
            
            BseriesRangeDiscoveryDialog.this.handleCancel(null);
            
            ArrayList<TxPersistEvent> totalEntries = new ArrayList<TxPersistEvent>(includes.size());
            int j = 0;
            for (String ip : includes){
                totalEntries.add(new TxPersistEvent(j++, this, TxPersistEvent.EVENT_ACTION, 
                        TypeRegistry.getInstance().getRecordType("EMSBseriesRangeDiscoveryAction"), IpAddressUtils.ipScalarToTreeValue((BaseScalarValue)IpAddressUtils.getIpAddressAsValue(ip))));
            }
            int threadNum = MAX_THREAD_NUM;
            if (includes.size() < MAX_THREAD_NUM ){
            	threadNum = includes.size();
            }
            rangeDiscoverySender = new RangeDiscoverySender(threadNum);
            Properties pro = new Properties();
            pro.setProperty(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWBSERIES, String.valueOf(bseriesRangeDiscoveryPanel.getAllowBseries()));
            pro.setProperty(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWEXA, String.valueOf(bseriesRangeDiscoveryPanel.getAllowExa()));
	    pro.setProperty(EMSBseriesRangeDiscoveryAction.ATTR_AUTOCHASSIS, String.valueOf(bseriesRangeDiscoveryPanel.getWhetherAutoChassis()));
            rangeDiscoverySender.process(totalEntries, bseriesRangeDiscoveryPanel.getReadCommunity(), pro);
        } catch (Exception e) {
            CalixMessageUtils.showMessageAndWait(e.getMessage());
        } finally {
            if (rangeDiscoverySender != null){
                try {
                    rangeDiscoverySender.cleanUp();
                } catch (PersistenceException e) {
                    Code.warning(e.getMessage(), e);
                }
            }
        }
    }


    public static void main(String[] args){
        new BseriesRangeDiscoveryDialog(null);
    }
    
}
