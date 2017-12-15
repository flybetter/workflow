package com.calix.bseries.server.task;
/**
 * BSeries Network Upgrade Response Signal in CMS
 * @author jawang
 *
 */
public class BSeriesReloadResponseSignal extends BSeriesTaskSignal{
    //James
    private String restoreConfig;
    private String taskName;
    private String jobID;
    public String getRestoreConfig() {
        return restoreConfig;
    }

    public void setRestoreConfig(String restoreConfig) {
        this.restoreConfig = restoreConfig;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }
    
}
