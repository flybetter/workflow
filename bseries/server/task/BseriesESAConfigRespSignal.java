package com.calix.bseries.server.task;

import java.util.Properties;

public class BseriesESAConfigRespSignal extends BSeriesTaskSignal {

    private String taskId;
    private String taskName;
    private String resultMessage;
    private Properties detailProps;
    
    public BseriesESAConfigRespSignal(){
    	super();
    	this.setType(BSeriesTaskSignal.SIG_TYPE_B6_ESA_CONFIGURATION_RESP);
    }
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public Properties getDetailProps() {
		return detailProps;
	}

	public void setDetailProps(Properties detailProps) {
		this.detailProps = detailProps;
	}
   
    
}
