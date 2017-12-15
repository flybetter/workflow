package com.calix.bseries.gui.model.persistence.tlv;

import com.objectsavvy.base.persistence.BaseQueryScope;


public class TLVB6TemplateQueryScope extends BaseQueryScope{

	    private String IPAddress1;
	    private String TemplateSource;
	    private String TemplateRecordType;
	    private String TemplateId;

	    public String getIPAddress1()
	    {
	        return IPAddress1;
	    } 

	    public String getTemplateSource()
	    {
	        return TemplateSource;
	    } 
	    public String getTemplateRecordType()
	    {
	        return TemplateRecordType;
	    } 
	    
	    public String getTemplateId()
	    {
	        return TemplateId;
	    } 
	   

	    public void setIPAddress1(String IPAddress) {
	        this.IPAddress1 = IPAddress;
	    }


	    public void setTemplateSource(String templateSource) {
	        this.TemplateSource = templateSource;
	    }
	    public void setTemplateRecordType(String templateRecordType) {
	        this.TemplateRecordType = templateRecordType;
	    }
	    
	    public void setTemplateId(String templateId) {
	        this.TemplateId = templateId;
	    }
}
