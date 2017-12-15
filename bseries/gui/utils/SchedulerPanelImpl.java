package com.calix.bseries.gui.utils;

import com.calix.ems.gui.panels.layout.scheduler.NonRecurScheduleOptionsPanel;
import com.calix.ems.gui.panels.layout.scheduler.ScheduleOptionsPanel;
import com.occam.ems.client.util.gui.SchedulerPanelProxy;
import com.occam.ems.client.util.gui.SchedulerPanelUtil;


import org.apache.log4j.Logger;

/**
 * User: hzhang
 * Date: 2/22/2012
 */
public class SchedulerPanelImpl implements SchedulerPanelProxy {
    
    private static Logger logger = Logger.getLogger(SchedulerPanelImpl.class);
    private static Object mainPanel = null;
    private static SchedulerPanelImpl instance = new SchedulerPanelImpl();
    public static SchedulerPanelImpl getInstance() {
        return instance;
    }
    
    
    public Object getSchedulerPanelInstance(int taskType){
        switch (taskType){
            case SchedulerPanelUtil.RECURRING_TASK : return createRecurringSchedulePanel();
            case SchedulerPanelUtil.NON_RECURRING_TASK : return createNonRecurringSchedulePanel();
            default:
                return createNonRecurringSchedulePanel();
        }
    }
    
    private Object createRecurringSchedulePanel(){
        ScheduleOptionsPanel m_ScheduleOptions = new ScheduleOptionsPanel();
        m_ScheduleOptions.addScheduleOptions(ScheduleOptionsPanel.RECURRING_TYPE_DAILY);
        m_ScheduleOptions.addScheduleOptions(ScheduleOptionsPanel.RECURRING_TYPE_MONTHLY);
        mainPanel = m_ScheduleOptions.getPanel();
        return m_ScheduleOptions;
    }
    private Object createNonRecurringSchedulePanel(){
        NonRecurScheduleOptionsPanel m_ScheduleOptions = new NonRecurScheduleOptionsPanel();
        m_ScheduleOptions.setNonRecurringOnly();
         mainPanel = m_ScheduleOptions.getPanel();
        return m_ScheduleOptions;
    }
    
    public Object getSchedulerMainPanel(){
        return  mainPanel;
    }
}
