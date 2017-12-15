package com.calix.bseries.gui.app.servicemanagement;

import com.calix.bseries.util.BseriesConstants;
import com.calix.system.gui.events.ActiveDbEvent;
import com.calix.system.gui.events.DbChangeEvent;
import com.calix.system.gui.events.DbChangeEventDispatcher;
import com.calix.system.gui.events.IActiveDbEventListener;
import com.calix.system.gui.events.IEventNotificationFilter;
import com.occam.ems.client.app.servicemanagement.bindergroupmanager.BinderGroupManagerScreen;

/**
 * User: hzhang
 * Date: 12/30/2011
 */
public class BseriesBinderGroupManager extends BinderGroupManagerScreen implements IActiveDbEventListener{
    
    private static final long serialVersionUID = 1L;
    private IEventNotificationFilter m_DbChgFilter = new DbChgFilter();
    
    public BseriesBinderGroupManager(){
        super();
        DbChangeEventDispatcher.getInstance().removeFilterListener(this, m_DbChgFilter);
        DbChangeEventDispatcher.getInstance().addFilterListener(this, m_DbChgFilter);
    }
    
    @Override
    public void processEvents(ActiveDbEvent[] pEvents) {
        binderGroupManager.placeRequestToFetchBinderGroups();
    }
    
    private static class DbChgFilter implements IEventNotificationFilter {
        @Override
        public boolean acceptEvent(ActiveDbEvent pEvent) {
            DbChangeEvent event = (DbChangeEvent)pEvent;
            if((event.getRecordType()!= null) &&
                    (BseriesConstants.DBNOTIFICATION_CCLCONNECTION_ACTION.equals(event.getRecordType().getTypeName()))){
                return true;
            }
            return false;
        }
    }
}
