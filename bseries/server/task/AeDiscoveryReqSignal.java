package com.calix.bseries.server.task;

public class AeDiscoveryReqSignal extends BSeriesTaskSignal {
	
	private String port;
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public AeDiscoveryReqSignal() {
		super();
		setType(BSeriesTaskSignal.SIG_TYPE_AE_DISCOVERY_REQ);
	}   
}
