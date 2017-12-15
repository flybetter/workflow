/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/


import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.common.CommonDateTimeUtils;
import com.calix.bseries.server.ana.process.ANACommandProcess;
import com.calix.ems.core.CMSobjectRequestSignal;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.exception.SemanticException;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6CommandResultData.
 * 
 * @version $Revision$ $Date$
 */
public class B6CommandResultData extends BaseEMSObject {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_CommandType
     */
    public String m_CommandType;

    /**
     * Field m_EndTime
     */
    public String m_EndTime;

    /**
     * Field m_ID
     */
    public com.calix.system.server.dbmodel.ICMSAid m_ID;

    /**
     * Field m_NetworkIP
     */
    public String m_NetworkIP;

    /**
     * Field m_NetworkName
     */
    public String m_NetworkName;

    /**
     * Field m_Request
     */
    public String m_Request;

    /**
     * Field m_Response
     */
    public String m_Response;

    /**
     * Field m_StartTime
     */
    public String m_StartTime;

    /**
     * Field m_Status
     */
    public String m_Status;

    /**
     * ImportFlag
     */
    public Integer m_importFlag;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6CommandResultData";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6CommandResultData() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6CommandResultData()


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
        if( obj1 instanceof B6CommandResultData ) {
            super.copyFields(obj1);
            B6CommandResultData obj = (B6CommandResultData)obj1;
            setCommandType((String)Helper.copy(obj.getCommandType()));
            setEndTime((String)Helper.copy(obj.getEndTime()));
            setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
            setNetworkIP((String)Helper.copy(obj.getNetworkIP()));
            setNetworkName((String)Helper.copy(obj.getNetworkName()));
            setRequest((String)Helper.copy(obj.getRequest()));
            setResponse((String)Helper.copy(obj.getResponse()));
            setStartTime((String)Helper.copy(obj.getStartTime()));
            setStatus((String)Helper.copy(obj.getStatus()));
            setimportFlag((Integer)Helper.copy(obj.getimportFlag()));
            setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getCommandType
     */
    public String getCommandType()
    {
        return this.m_CommandType;
    } //-- String getCommandType() 

    /**
     * Method getEndTime
     */
    public String getEndTime()
    {
        return this.m_EndTime;
    } //-- String getEndTime() 

    /**
     * Method getID
     */
    public com.calix.system.server.dbmodel.ICMSAid getID()
    {
        return this.m_ID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getID() 

    /**
     * Method getIdentityValue
     */
    public com.calix.system.server.dbmodel.ICMSAid getIdentityValue()
    {
        return this.m_ID;
    } //-- com.calix.system.server.dbmodel.ICMSAid getIdentityValue() 

    /**
     * Method getNetworkIP
     */
    public String getNetworkIP()
    {
        return this.m_NetworkIP;
    } //-- String getNetworkIP() 

    /**
     * Method getNetworkName
     */
    public String getNetworkName()
    {
        return this.m_NetworkName;
    } //-- String getNetworkName() 

    /**
     * Method getRequest
     */
    public String getRequest()
    {
        return this.m_Request;
    } //-- String getRequest() 

    /**
     * Method getResponse
     */
    public String getResponse()
    {
        return this.m_Response;
    } //-- String getResponse() 

    /**
     * Method getStartTime
     */
    public String getStartTime()
    {
        return this.m_StartTime;
    } //-- String getStartTime() 

    /**
     * Method getStatus
     */
    public String getStatus()
    {
        return this.m_Status;
    } //-- String getStatus() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6CommandResultData;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getimportFlag
     */
    public Integer getimportFlag()
    {
        return this.m_importFlag;
    } //-- Integer getimportFlag() 

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
            case 0x353E:
                if (m_CommandType == null) {
                    m_CommandType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x353F:
                if (m_NetworkName == null) {
                    m_NetworkName = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3540:
                if (m_NetworkIP == null) {
                    m_NetworkIP = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3541:
                if (m_StartTime == null) {
                    m_StartTime = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3542:
                if (m_EndTime == null) {
                    m_EndTime = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3543:
                if (m_Status == null) {
                    m_Status = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3544:
                if (m_Request == null) {
                    m_Request = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3545:
                if (m_Response == null) {
                    m_Response = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3547:
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
        TLVHelper.addEmbeddedTLV(tlv, 0x353E, m_CommandType);
        TLVHelper.addEmbeddedTLV(tlv, 0x3542, m_EndTime);
        TLVHelper.addEmbeddedTLV(tlv, 0x3540, m_NetworkIP);
        TLVHelper.addEmbeddedTLV(tlv, 0x353F, m_NetworkName);
        TLVHelper.addEmbeddedTLV(tlv, 0x3544, m_Request);
        TLVHelper.addEmbeddedTLV(tlv, 0x3545, m_Response);
        TLVHelper.addEmbeddedTLV(tlv, 0x3541, m_StartTime);
        TLVHelper.addEmbeddedTLV(tlv, 0x3543, m_Status);
        TLVHelper.addEmbeddedTLV(tlv, 0x3547, m_importFlag);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setCommandType
     * 
     * @param CommandType
     */
    public void setCommandType(String CommandType)
    {
        this.m_CommandType = CommandType;
    } //-- void setCommandType(String) 

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
     * Method setID
     * 
     * @param ID
     */
    public void setID(com.calix.system.server.dbmodel.ICMSAid ID)
    {
        this.m_ID = ID;
    } //-- void setID(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setIdentityValue
     * 
     * @param ID
     */
    public boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid ID)
    {
        this.m_ID = (com.calix.system.server.dbmodel.ICMSAid)ID;
        return true;
    } //-- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid) 

    /**
     * Method setNetworkIP
     * 
     * @param NetworkIP
     */
    public void setNetworkIP(String NetworkIP)
    {
        this.m_NetworkIP = NetworkIP;
    } //-- void setNetworkIP(String) 

    /**
     * Method setNetworkName
     * 
     * @param NetworkName
     */
    public void setNetworkName(String NetworkName)
    {
        this.m_NetworkName = NetworkName;
    } //-- void setNetworkName(String) 

    /**
     * Method setRequest
     * 
     * @param Request
     */
    public void setRequest(String Request)
    {
        this.m_Request = Request;
    } //-- void setRequest(String) 

    /**
     * Method setResponse
     * 
     * @param Response
     */
    public void setResponse(String Response)
    {
        this.m_Response = Response;
    } //-- void setResponse(String) 

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
     * Method setStatus
     * 
     * @param Status
     */
    public void setStatus(String Status)
    {
        this.m_Status = Status;
    } //-- void setStatus(String) 

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
     * Method setimportFlag
     * 
     * @param importFlag
     */
    public void setimportFlag(Integer importFlag)
    {
        this.m_importFlag = importFlag;
    } //-- void setimportFlag(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6CommandResultData ) {
            super.updateFields(obj1);
            B6CommandResultData obj = (B6CommandResultData)obj1;
           if (obj.getCommandType() != null )
               setCommandType((String)Helper.copy(obj.getCommandType()));
           if (obj.getEndTime() != null )
               setEndTime((String)Helper.copy(obj.getEndTime()));
           if (obj.getID() != null )
               setID((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getID()));
           if (obj.getNetworkIP() != null )
               setNetworkIP((String)Helper.copy(obj.getNetworkIP()));
           if (obj.getNetworkName() != null )
               setNetworkName((String)Helper.copy(obj.getNetworkName()));
           if (obj.getRequest() != null )
               setRequest((String)Helper.copy(obj.getRequest()));
           if (obj.getResponse() != null )
               setResponse((String)Helper.copy(obj.getResponse()));
           if (obj.getStartTime() != null )
               setStartTime((String)Helper.copy(obj.getStartTime()));
           if (obj.getStatus() != null )
               setStatus((String)Helper.copy(obj.getStatus()));
           if (obj.getimportFlag() != null )
               setimportFlag((Integer)Helper.copy(obj.getimportFlag()));
           if (obj.getIdentityValue() != null )
               setIdentityValue((com.calix.system.server.dbmodel.ICMSAid)Helper.copy(obj.getIdentityValue()));
        }
    } //-- void updateFields(CMSObject) 
 // BEGIN CODE
    private static final Logger log = Logger.getLogger(B6CommandResultData.class);
        
    @Override
    protected void processUpdate(DbTransaction tx) throws SemanticException, EMSException {
    	if(!isBeingImported()){
        	 this.setStartTime(CommonDateTimeUtils.getUSDateTime());
    	     this.setStatus("In Progress" );
    	     //if set null,cannot update
    	     this.setResponse("");
    	     this.setEndTime("");
    	}
    	 super.processUpdate(tx);
    }
    
    @Override
    protected CMSObject processLoad(DbTransaction tx) throws SemanticException, EMSException {
    	B6CommandResultData obj = null;
    	try {
			obj = (B6CommandResultData) super.processLoad(tx);
		} catch (Exception e) {
			log.warn("Ignore load B6CommandResultData obj error.");
		}
       
        return obj;
    }
    
    @Override
    protected void processCreate(DbTransaction tx) throws SemanticException, EMSException {
    	if(!isBeingImported()){
   	   	 this.setStartTime(CommonDateTimeUtils.getUSDateTime());
   	     this.setStatus("In Progress" );
    	}
    	 super.processCreate(tx);
    }

    public void postRequest (int requestType){
    	if(!isBeingImported()){
            switch(requestType ){
            case CMSobjectRequestSignal.CREATE:
    	         final B6CommandResultData createData = this;
    	         Thread createThread = new Thread(new Runnable() {
                    public void run() {
           	         log.info("Start excute get dsl-port-status for "+createData.getNetworkName());
                        ANACommandProcess.getInstance().executeCommand(createData);
                        log.info("Finishing excuting B6 Template for "+createData.getNetworkName());
                    }
                });
    	       createThread.start();
               break;      
            case CMSobjectRequestSignal.UPDATE:
    	         final B6CommandResultData updateData = this;
    	         Thread updateThread = new Thread(new Runnable() {
                     public void run() {
            	         log.info("Start excute get dsl-port-status for "+updateData.getNetworkName());
                         ANACommandProcess.getInstance().executeCommand(updateData);
                         log.info("Finishing excuting B6 Template for "+updateData.getNetworkName());
                     }
                 });
    	         updateThread.start();
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
