package com.calix.bseries.gui.model.persistence.tlv;

import com.objectsavvy.base.persistence.BaseQueryScope;


public class TLVB6CommandActionQueryScope extends BaseQueryScope{

	    private String CommandType;
	    private String NetworkName;
	    private String NetworkIP;
	    private String Request;
	    private String Response;
	    private String Port;
	    private String Vlan;

	    public String getCommandType()
	    {
	        return CommandType;
	    } 

	    public String getNetworkIP()
	    {
	        return NetworkIP;
	    } 
	    public String getNetworkName()
	    {
	        return NetworkName;
	    } 
	    
	    public String getRequest()
	    {
	        return Request;
	    } 
	    
	    public String getResponse()
	    {
	        return Response;
	    } 
	    
	    public String getPort()
	    {
	        return Port;
	    } 
	   
	    public void setCommandType(String commandType) {
	        this.CommandType = commandType;
	    }


	    public void setNetworkName(String networkName)
	    {
	        this.NetworkName = networkName;
	    }
	    public void setNetworkIP(String networkIP)
	    {
	        this.NetworkIP = networkIP;
	    } 
	    
	    public void setRequest(String request)
	    {
	        this.Request = request;
	    }
	    
	    public void setResponse(String response)
	    {
	        this.Response = response;
	    }
	    
	    public void setPort(String string)
	    {
	        this.Port = string;
	    }

		public String getVlan() {
			return Vlan;
		}

		public void setVlan(String Vlan) {
			this.Vlan = Vlan;
		}
}
