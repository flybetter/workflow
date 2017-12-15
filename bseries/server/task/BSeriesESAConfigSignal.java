package com.calix.bseries.server.task;

import java.util.HashMap;

import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;

public class BSeriesESAConfigSignal extends BSeriesTaskSignal {
	
    private OccamProtocolRequestResponse occamProtocolRequestResponse;
    

	private String esaHostIp;
    private String esaSwitchLoginName;
    private String esaSwitchLoginPass;
    private String esaDbFileName;
    private boolean isEsaReganerate = false;
    private HashMap esaDomainPropsMap;
    private String taskId;
    private String taskName;
    private String httpsAuthentication;
    private int accessLevel;


	private long requestId;

	public BSeriesESAConfigSignal(){
		super();
		setType(BSeriesTaskSignal.SIG_TYPE_B6_ESA_CONFIGURATION_REQ);
	}
	
    public OccamProtocolRequestResponse getOccamProtocolRequestResponse() {
        return occamProtocolRequestResponse;
    }

    public void setOccamProtocolRequestResponse(OccamProtocolRequestResponse occamProtocolRequestResponse) {
        this.occamProtocolRequestResponse = occamProtocolRequestResponse;
    }
    
    public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
    public String getEsaHostIp() {
		return esaHostIp;
	}

	public void setEsaHostIp(String esaHostIp) {
		this.esaHostIp = esaHostIp;
	}

	public String getEsaSwitchLoginName() {
		return esaSwitchLoginName;
	}

	public void setEsaSwitchLoginName(String esaSwitchLoginName) {
		this.esaSwitchLoginName = esaSwitchLoginName;
	}

	public String getEsaSwitchLoginPass() {
		return esaSwitchLoginPass;
	}

	public void setEsaSwitchLoginPass(String esaSwitchLoginPass) {
		this.esaSwitchLoginPass = esaSwitchLoginPass;
	}

	public String getEsaDbFileName() {
		return esaDbFileName;
	}

	public void setEsaDbFileName(String esaDbFileName) {
		this.esaDbFileName = esaDbFileName;
	}

	public boolean isEsaReganerate() {
		return isEsaReganerate;
	}

	public void setEsaReganerate(boolean isEsaReganerate) {
		this.isEsaReganerate = isEsaReganerate;
	}

	public HashMap getEsaDomainPropsMap() {
		return esaDomainPropsMap;
	}

	public void setEsaDomainPropsMap(HashMap esaDomainPropsMap) {
		this.esaDomainPropsMap = esaDomainPropsMap;
	}

	public String getHttpsAuthentication() {
		return httpsAuthentication;
	}

	public void setHttpsAuthentication(String httpsAuthentication) {
		this.httpsAuthentication = httpsAuthentication;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	
}
