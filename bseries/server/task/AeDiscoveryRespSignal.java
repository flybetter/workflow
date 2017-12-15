package com.calix.bseries.server.task;

public class AeDiscoveryRespSignal extends BSeriesDiscoveryResponseSignal {
	private String description;
	private String model;
    private String macAddress;
    private String port;
    
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public AeDiscoveryRespSignal() {
		super();
		setType(BSeriesTaskSignal.SIG_TYPE_AE_DISCOVERY_RESP);
	}

}
