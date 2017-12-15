package com.calix.bseries.gui.navtree;

import com.calix.ems.gui.EMSGui;
import com.calix.ems.gui.panels.EMSPanelController;
import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.navtree.NavControllerImpl;
import com.calix.system.gui.navtree.NavigationController;
import com.calix.system.gui.tlv.TLVUtils;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;

import javax.swing.event.TreeSelectionEvent;
import java.util.Collection;
import java.util.Iterator;

public class BseriesNavControllerImpl extends NavControllerImpl {

    public BseriesNavControllerImpl(NavigationController controller) {
        super(controller);
    }

    @Override
    public void onTreeSelect(TreeSelectionEvent event) {
        //cut through to EWI
        super.onTreeSelect(event);
    }

    @Override
    protected void postTreeSelectionChange() {
       Collection objects = m_controller.getSelection().getSelectedObjects();
       if(objects != null && !objects.isEmpty()) {
           m_controller.getTopPanel().getFilterPanel().setVisible(false);
           ((EMSPanelController) ((EMSGui)m_controller.getCitInstance()).getMainView().getPanelController()).enableiMSStaticMenu(false);
       }
    }

    /**
     * Generate an  EMSAID from passed in string values. Currently only for Regions.
     * @param pNetwork   NetworkID portion of AID. "CMS" for region AID.
     * @param pAddress   EMSAid String value. REGION Name String e.g. "REG-root"
     * @param pRecordType
     * @param pPortDescStr  useless for EMSAID type. null is fine.
     * @return
     */
    public IValue createAid(String pNetwork, String pAddress, String pRecordType, String pPortDescStr) {
        // try to match
        RecordType recordType = TypeRegistry.getInstance().getRecordType(pRecordType);
        for (Iterator iter = recordType.getIdentityAttribute().getValueTypeConstraint().getTypes(); iter.hasNext();) {
            IValueType aidType = (IValueType) iter.next();
            try {
                IValue aid = null;
                if (aidType.restrictsType(TypeRegistry.getInstance().getType("EMSAid"))) {
                    aid = aidType.convertFrom(pAddress, DomainValues.DOMAIN_EMS);
                } else {
                    aid = aidType.convertFrom(pAddress, DomainValues.DOMAIN_TL1);
                }
                if (aid != null) {
                    TLVUtils.setNetworkComponent(aid, pNetwork);
                    return aid;
                }
            } catch (MappingException mex) {
            }
        }
        return null;
    }
}
