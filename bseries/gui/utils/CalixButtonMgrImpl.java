package com.calix.bseries.gui.utils;

import com.calix.system.gui.components.menu.CalixButtonMgr;
import com.occam.ems.client.util.gui.CalixButtonMgrProxy;

import org.apache.log4j.Logger;

/**
 * 
 * @author jawang
 * 
 */
public class CalixButtonMgrImpl implements CalixButtonMgrProxy {

    
    private static Logger logger = Logger.getLogger(CalixButtonMgrImpl.class);

    @Override
    public void disableContextCreateButton() {
        CalixButtonMgr.getInstance().disableContextCreateButton();        
    }

    @Override
    public void disableDeleteButton() {
        CalixButtonMgr.getInstance().disableDeleteButton();        
    }

    @Override
    public void disableActionButton() {
        CalixButtonMgr.getInstance().disableActionButton();        
    }

    @Override
    public void disableRevertButton() {
        CalixButtonMgr.getInstance().disableRevertButton();        
    }

    @Override
    public void disableRefreshButton() {
        CalixButtonMgr.getInstance().disableRefreshButton();        
    }

    @Override
    public void disableApplyButton() {
        CalixButtonMgr.getInstance().disableApplyButton();        
    }
    
}
