/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.process.ANACommandProcess;
import com.calix.ems.core.CMSobjectRequestSignal;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6Template.
 * 
 * @version $Revision$ $Date$
 */
public class B6Template extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * CMS User Name
     */
    public String m_CMSUserName;

    /**
     * End Time
     */
    public String m_EndTime;

    /**
     * End Time Long
     */
    public Long m_EndTimeLong;

    /**
     * B6 IPAddress
     */
    public String m_IPAddress1;

    /**
     * Field m_Name
     */
    public com.calix.system.server.dbmodel.ICMSAid m_Name;

    /**
     * Response
     */
    public String m_ResponseDetail;

    /**
     * Result
     */
    public String m_Result;

    /**
     * Strat Time
     */
    public String m_StartTime;

    /**
     * Strat Time Long
     */
    public Long m_StartTimeLong;

    /**
     * Template Source
     */
    public String m_TemplateSource;

    /**
     * User Ip
     */
    public String m_UserIp;

    /**
     * B6 Network Name
     */
    public String m_device_host_name;

    /**
     * ImportFlag
     */
    public Integer m_importFlag;

    /**
     * Operation
     */
    public String m_operation;

    /**
     * Service
     */
    public String m_service;

    /**
     * Service type
     */
    public String m_service_type;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6Template";


      //----------------/
     //- Constructors -/
    //----------------/

    public B6Template() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6Template()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method copyFields
     * 
     * @param obj1
     */
    protected void copyFields(CMSObject obj1)
    {
        if( obj1 instanceof B6Template ) {
            super.copyFields(obj1);
            B6Template obj = (B6Template)obj1;
            setCMSUserName((String)Helper.copy(obj.getCMSUserName()));
            setEndTime((String)Helper.copy(obj.getEndTime()));
            setEndTimeLong((Long)Helper.copy(obj.getEndTimeLong()));
            setIPAddress1((String)Helper.copy(obj.getIPAddress1()));
            setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
            setResponseDetail((String)Helper.copy(obj.getResponseDetail()));
            setResult((String)Helper.copy(obj.getResult()));
            setStartTime((String)Helper.copy(obj.getStartTime()));
            setStartTimeLong((Long)Helper.copy(obj.getStartTimeLong()));
            setTemplateSource((String)Helper.copy(obj.getTemplateSource()));
            setUserIp((String)Helper.copy(obj.getUserIp()));
            setdevice_host_name((String)Helper.copy(obj.getdevice_host_name()));
            setimportFlag((Integer)Helper.copy(obj.getimportFlag()));
            setoperation((String)Helper.copy(obj.getoperation()));
            setservice((String)Helper.copy(obj.getservice()));
            setservice_type((String)Helper.copy(obj.getservice_type()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCMSUserName
     */
    public String getCMSUserName()
    {
        return this.m_CMSUserName;
    } //-- String getCMSUserName() 

    /**
     * Method getEndTime
     */
    public String getEndTime()
    {
        return this.m_EndTime;
    } //-- String getEndTime() 

    /**
     * Method getEndTimeLong
     */
    public Long getEndTimeLong()
    {
        return this.m_EndTimeLong;
    } //-- Long getEndTimeLong() 

    /**
     * Method getIPAddress1
     */
    public String getIPAddress1()
    {
        return this.m_IPAddress1;
    } //-- String getIPAddress1() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getName
     */
    public com.calix.system.server.dbmodel.ICMSAid getName()
    {
        return this.m_Name;
    } //-- com.calix.system.server.dbmodel.ICMSAid getName() 

    /**
     * Method getResponseDetail
     */
    public String getResponseDetail()
    {
        return this.m_ResponseDetail;
    } //-- String getResponseDetail() 

    /**
     * Method getResult
     */
    public String getResult()
    {
        return this.m_Result;
    } //-- String getResult() 

    /**
     * Method getStartTime
     */
    public String getStartTime()
    {
        return this.m_StartTime;
    } //-- String getStartTime() 

    /**
     * Method getStartTimeLong
     */
    public Long getStartTimeLong()
    {
        return this.m_StartTimeLong;
    } //-- Long getStartTimeLong() 

    /**
     * Method getTemplateSource
     */
    public String getTemplateSource()
    {
        return this.m_TemplateSource;
    } //-- String getTemplateSource() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getUserIp
     */
    public String getUserIp()
    {
        return this.m_UserIp;
    } //-- String getUserIp() 

    /**
     * Method getdevice_host_name
     */
    public String getdevice_host_name()
    {
        return this.m_device_host_name;
    } //-- String getdevice_host_name() 

    /**
     * Method getimportFlag
     */
    public Integer getimportFlag()
    {
        return this.m_importFlag;
    } //-- Integer getimportFlag() 

    /**
     * Method getoperation
     */
    public String getoperation()
    {
        return this.m_operation;
    } //-- String getoperation() 

    /**
     * Method getservice
     */
    public String getservice()
    {
        return this.m_service;
    } //-- String getservice() 

    /**
     * Method getservice_type
     */
    public String getservice_type()
    {
        return this.m_service_type;
    } //-- String getservice_type() 

    /**
     * Method populateAttributeFromTLV
     * 
     * @param tlv
     * @param from_version
     */
    public void populateAttributeFromTLV(TLV tlv, SwVersionNo from_version)
    {
        if (tlv == null)
            return;
        switch (tlv.getType()) {
            case 0x3500:
                if (m_device_host_name == null) {
                    m_device_host_name = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3501:
                if (m_IPAddress1 == null) {
                    m_IPAddress1 = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3502:
                if (m_TemplateSource == null) {
                    m_TemplateSource = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3503:
                if (m_CMSUserName == null) {
                    m_CMSUserName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3504:
                if (m_UserIp == null) {
                    m_UserIp = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3505:
                if (m_service == null) {
                    m_service = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3506:
                if (m_service_type == null) {
                    m_service_type = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3507:
                if (m_operation == null) {
                    m_operation = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3517:
                if (m_StartTime == null) {
                    m_StartTime = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3518:
                if (m_EndTime == null) {
                    m_EndTime = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3519:
                if (m_Result == null) {
                    m_Result = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x351A:
                if (m_ResponseDetail == null) {
                    m_ResponseDetail = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x351C:
                if (m_StartTimeLong == null) {
                    m_StartTimeLong = TLVHelper.getLongValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x351D:
                if (m_EndTimeLong == null) {
                    m_EndTimeLong = TLVHelper.getLongValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3546:
                if (m_importFlag == null) {
                    m_importFlag = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
        }
        super.populateAttributeFromTLV(tlv, from_version);
    } //-- void populateAttributeFromTLV(TLV, SwVersionNo) 

    /**
     * Method populateTLVFromAttributes
     * 
     * @param tlv
     * @param to_version
     */
    public void populateTLVFromAttributes(TLV tlv, SwVersionNo to_version)
    {
        if (tlv == null)
            return;
        super.populateTLVFromAttributes(tlv, to_version);
        TLVHelper.addEmbeddedTLV(tlv, 0x3503, m_CMSUserName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3518, m_EndTime);
        TLVHelper.addEmbeddedTLV(tlv, 0x351D, m_EndTimeLong);
        TLVHelper.addEmbeddedTLV(tlv, 0x3501, m_IPAddress1);
        TLVHelper.addEmbeddedTLV(tlv, 0x351A, m_ResponseDetail);
        TLVHelper.addEmbeddedTLV(tlv, 0x3519, m_Result);
        TLVHelper.addEmbeddedTLV(tlv, 0x3517, m_StartTime);
        TLVHelper.addEmbeddedTLV(tlv, 0x351C, m_StartTimeLong);
        TLVHelper.addEmbeddedTLV(tlv, 0x3502, m_TemplateSource);
        TLVHelper.addEmbeddedTLV(tlv, 0x3504, m_UserIp);
        TLVHelper.addEmbeddedTLV(tlv, 0x3500, m_device_host_name);
        TLVHelper.addEmbeddedTLV(tlv, 0x3546, m_importFlag);
        TLVHelper.addEmbeddedTLV(tlv, 0x3507, m_operation);
        TLVHelper.addEmbeddedTLV(tlv, 0x3505, m_service);
        TLVHelper.addEmbeddedTLV(tlv, 0x3506, m_service_type);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCMSUserName
     * 
     * @param CMSUserName
     */
    public void setCMSUserName(String CMSUserName)
    {
        this.m_CMSUserName = CMSUserName;
    } //-- void setCMSUserName(String) 

    /**
     * Method setEndTime
     * 
     * @param EndTime
     */
    public void setEndTime(String EndTime)
    {
        this.m_EndTime = EndTime;
    } //-- void setEndTime(String) 

    /**
     * Method setEndTimeLong
     * 
     * @param EndTimeLong
     */
    public void setEndTimeLong(Long EndTimeLong)
    {
        this.m_EndTimeLong = EndTimeLong;
    } //-- void setEndTimeLong(Long) 

    /**
     * Method setIPAddress1
     * 
     * @param IPAddress1
     */
    public void setIPAddress1(String IPAddress1)
    {
        this.m_IPAddress1 = IPAddress1;
    } //-- void setIPAddress1(String) 

    /**
     * Method setIdentityValue
     * 
     * @param Name
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid Name)
    {
        this.m_Name = (com.calix.system.server.dbmodel.ICMSAid)Name;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setName
     * 
     * @param Name
     */
    public void setName(com.calix.system.server.dbmodel.ICMSAid Name)
    {
        this.m_Name = Name;
    } //-- void setName(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setResponseDetail
     * 
     * @param ResponseDetail
     */
    public void setResponseDetail(String ResponseDetail)
    {
        this.m_ResponseDetail = ResponseDetail;
    } //-- void setResponseDetail(String) 

    /**
     * Method setResult
     * 
     * @param Result
     */
    public void setResult(String Result)
    {
        this.m_Result = Result;
    } //-- void setResult(String) 

    /**
     * Method setStartTime
     * 
     * @param StartTime
     */
    public void setStartTime(String StartTime)
    {
        this.m_StartTime = StartTime;
    } //-- void setStartTime(String) 

    /**
     * Method setStartTimeLong
     * 
     * @param StartTimeLong
     */
    public void setStartTimeLong(Long StartTimeLong)
    {
        this.m_StartTimeLong = StartTimeLong;
    } //-- void setStartTimeLong(Long) 

    /**
     * Method setTemplateSource
     * 
     * @param TemplateSource
     */
    public void setTemplateSource(String TemplateSource)
    {
        this.m_TemplateSource = TemplateSource;
    } //-- void setTemplateSource(String) 

    /**
     * Method setTypeName
     * 
     * @param typeName
     */
    public boolean setTypeName(String typeName)
    {
        return false;
    } //-- boolean setTypeName(String) 

    /**
     * Method setUserIp
     * 
     * @param UserIp
     */
    public void setUserIp(String UserIp)
    {
        this.m_UserIp = UserIp;
    } //-- void setUserIp(String) 

    /**
     * Method setdevice_host_name
     * 
     * @param device_host_name
     */
    public void setdevice_host_name(String device_host_name)
    {
        this.m_device_host_name = device_host_name;
    } //-- void setdevice_host_name(String) 

    /**
     * Method setimportFlag
     * 
     * @param importFlag
     */
    public void setimportFlag(Integer importFlag)
    {
        this.m_importFlag = importFlag;
    } //-- void setimportFlag(Integer) 

    /**
     * Method setoperation
     * 
     * @param operation
     */
    public void setoperation(String operation)
    {
        this.m_operation = operation;
    } //-- void setoperation(String) 

    /**
     * Method setservice
     * 
     * @param service
     */
    public void setservice(String service)
    {
        this.m_service = service;
    } //-- void setservice(String) 

    /**
     * Method setservice_type
     * 
     * @param service_type
     */
    public void setservice_type(String service_type)
    {
        this.m_service_type = service_type;
    } //-- void setservice_type(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6Template ) {
            super.updateFields(obj1);
            B6Template obj = (B6Template)obj1;
           if (obj.getCMSUserName() != null )
               setCMSUserName((String)Helper.copy(obj.getCMSUserName()));
           if (obj.getEndTime() != null )
               setEndTime((String)Helper.copy(obj.getEndTime()));
           if (obj.getEndTimeLong() != null )
               setEndTimeLong((Long)Helper.copy(obj.getEndTimeLong()));
           if (obj.getIPAddress1() != null )
               setIPAddress1((String)Helper.copy(obj.getIPAddress1()));
           if (obj.getName() != null )
               setName((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getName()));
           if (obj.getResponseDetail() != null )
               setResponseDetail((String)Helper.copy(obj.getResponseDetail()));
           if (obj.getResult() != null )
               setResult((String)Helper.copy(obj.getResult()));
           if (obj.getStartTime() != null )
               setStartTime((String)Helper.copy(obj.getStartTime()));
           if (obj.getStartTimeLong() != null )
               setStartTimeLong((Long)Helper.copy(obj.getStartTimeLong()));
           if (obj.getTemplateSource() != null )
               setTemplateSource((String)Helper.copy(obj.getTemplateSource()));
           if (obj.getUserIp() != null )
               setUserIp((String)Helper.copy(obj.getUserIp()));
           if (obj.getdevice_host_name() != null )
               setdevice_host_name((String)Helper.copy(obj.getdevice_host_name()));
           if (obj.getimportFlag() != null )
               setimportFlag((Integer)Helper.copy(obj.getimportFlag()));
           if (obj.getoperation() != null )
               setoperation((String)Helper.copy(obj.getoperation()));
           if (obj.getservice() != null )
               setservice((String)Helper.copy(obj.getservice()));
           if (obj.getservice_type() != null )
               setservice_type((String)Helper.copy(obj.getservice_type()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
    //BEGIN CODE
    public static final String STATUS_RUNNING="IN PROCESS";
    // copy object doesn't require exactly the same class
    public void copyInto(CMSObject object) {
        // copy values except references from object into this object
        if( this!=object) {
            updateFields(object);

            // reset the TLV cache
            setRawObjectTlvBytes(null);

        }
    }
    private Map<String,String> cacheParam=new HashMap<String,String>();
    
    public void addParam(String key,String value){
    	cacheParam.put(key, value);
    }
    
    public String getParam(String key){
    	return cacheParam.get(key);
    }
    
	public void setconvertName(String convertName) {
		this.m_Name = new EMSAid(convertName);
	} // -- void setconvertName(String)
	
	public String getconvertName() {
		return this.m_Name.toString();
	} // -- String getconvertName()
	
	/**
     * Function:getServiceDesc<br/> 
     * @author Tony Ben 
     * @return 
     * @since JDK 1.6
     */
    public String getServiceDesc(){
    	return "";
    }
    /**
     * Function:getTemplateName<br/>  
     * @author Tony Ben 
     * @return 
     * @since JDK 1.6
     */
    public String getTemplateName(){
    	return "";
    }
    
    
    private static final Logger log = Logger.getLogger(B6Template.class);

    @Override
    public void postRequest(int requestType) {
    	if(!isBeingImported()){
        	switch(requestType){
            case CMSobjectRequestSignal.CREATE:
            	 final B6Template template = this;
                 Thread t = new Thread(new Runnable() {

                     public void run() {
                     	 log.info("Start excute B6 Template for "+template.getdevice_host_name());
                         ANACommandProcess.getInstance().executeCommand(template);
                         log.info("Finishing excuting B6 Template for "+template.getdevice_host_name());
                     }
                 });
                 t.start();
                break;
        	 }
    	}       
    }
    
    protected boolean isBeingImported() {
        try {
               Integer value = (Integer) this.getAttributeValue("importFlag");
               if (value !=null && value == 1) return true ;
        }catch (Exception e) {
            log.warn("Could not check if the import flag is set because of exception ",e);
        }
        return false ;
    }
    //END CODE
}
