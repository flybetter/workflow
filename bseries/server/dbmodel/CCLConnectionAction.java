/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//BEGIN IMPORTS
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.occam.ems.common.proxy.startnms.CalixMainSocketServerFE;
import com.occam.ems.common.proxy.util.PureServerUtilsFE;
import com.occam.ems.fe.ccl.ServiceMgmtSessionFE;
import com.calix.ems.core.CMSSignal;
import com.calix.ems.core.CMSobjectRequestSignal;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.server.dbmodel.BaseEMSAction;
//END IMPORTS
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;


/**
 * Class CCLConnectionAction.
 * 
 * @version $Revision$ $Date$
 */
public class CCLConnectionAction extends BaseEMSAction {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Serializable binary data
     */
    public byte[] m_binaryPackage;

    /**
     * Field m_isMultiPle
     */
    public Integer m_isMultiPle;

    /**
     * Field m_num
     */
    public Integer m_num;

    /**
     * Field m_reqKey
     */
    public Integer m_reqKey;

    /**
     * Field m_requestId
     */
    public String m_requestId;

    /**
     * Field m_totalNum
     */
    public Integer m_totalNum;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "CCLConnectionAction";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public CCLConnectionAction() {
        super();
    } //-- com.calix.bseries.server.dbmodel.CCLConnectionAction()


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
        if( obj1 instanceof CCLConnectionAction ) {
            super.copyFields(obj1);
            CCLConnectionAction obj = (CCLConnectionAction)obj1;
            setbinaryPackage((byte[])Helper.copy(obj.getbinaryPackage()));
            setisMultiPle((Integer)Helper.copy(obj.getisMultiPle()));
            setnum((Integer)Helper.copy(obj.getnum()));
            setreqKey((Integer)Helper.copy(obj.getreqKey()));
            setrequestId((String)Helper.copy(obj.getrequestId()));
            settotalNum((Integer)Helper.copy(obj.gettotalNum()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.CCLConnectionAction;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getbinaryPackage
     */
    public byte[] getbinaryPackage()
    {
        return Helper.getByteArray(this.m_binaryPackage);
    } //-- byte[] getbinaryPackage() 

    /**
     * Method getisMultiPle
     */
    public Integer getisMultiPle()
    {
        return this.m_isMultiPle;
    } //-- Integer getisMultiPle() 

    /**
     * Method getnum
     */
    public Integer getnum()
    {
        return this.m_num;
    } //-- Integer getnum() 

    /**
     * Method getreqKey
     */
    public Integer getreqKey()
    {
        return this.m_reqKey;
    } //-- Integer getreqKey() 

    /**
     * Method getrequestId
     */
    public String getrequestId()
    {
        return this.m_requestId;
    } //-- String getrequestId() 

    /**
     * Method gettotalNum
     */
    public Integer gettotalNum()
    {
        return this.m_totalNum;
    } //-- Integer gettotalNum() 

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
            case 0x33E3:
                if (m_isMultiPle == null) {
                    m_isMultiPle = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E4:
                if (m_totalNum == null) {
                    m_totalNum = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E5:
                if (m_num == null) {
                    m_num = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33E6:
                if (m_reqKey == null) {
                    m_reqKey = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F7:
                if (m_requestId == null) {
                    m_requestId = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x33F8:
                if (m_binaryPackage == null) {
                    m_binaryPackage = TLVHelper.getBitStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x33F8, m_binaryPackage);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E3, m_isMultiPle);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E5, m_num);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E6, m_reqKey);
        TLVHelper.addEmbeddedTLV(tlv, 0x33F7, m_requestId);
        TLVHelper.addEmbeddedTLV(tlv, 0x33E4, m_totalNum);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

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
     * Method setbinaryPackage
     * 
     * @param binaryPackage
     */
    public void setbinaryPackage(byte[] binaryPackage)
    {
        this.m_binaryPackage = binaryPackage;
    } //-- void setbinaryPackage(byte) 

    /**
     * Method setisMultiPle
     * 
     * @param isMultiPle
     */
    public void setisMultiPle(Integer isMultiPle)
    {
        this.m_isMultiPle = isMultiPle;
    } //-- void setisMultiPle(Integer) 

    /**
     * Method setnum
     * 
     * @param num
     */
    public void setnum(Integer num)
    {
        this.m_num = num;
    } //-- void setnum(Integer) 

    /**
     * Method setreqKey
     * 
     * @param reqKey
     */
    public void setreqKey(Integer reqKey)
    {
        this.m_reqKey = reqKey;
    } //-- void setreqKey(Integer) 

    /**
     * Method setrequestId
     * 
     * @param requestId
     */
    public void setrequestId(String requestId)
    {
        this.m_requestId = requestId;
    } //-- void setrequestId(String) 

    /**
     * Method settotalNum
     * 
     * @param totalNum
     */
    public void settotalNum(Integer totalNum)
    {
        this.m_totalNum = totalNum;
    } //-- void settotalNum(Integer) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof CCLConnectionAction ) {
            super.updateFields(obj1);
            CCLConnectionAction obj = (CCLConnectionAction)obj1;
           if (obj.getbinaryPackage() != null )
               setbinaryPackage((byte[])Helper.copy(obj.getbinaryPackage()));
           if (obj.getisMultiPle() != null )
               setisMultiPle((Integer)Helper.copy(obj.getisMultiPle()));
           if (obj.getnum() != null )
               setnum((Integer)Helper.copy(obj.getnum()));
           if (obj.getreqKey() != null )
               setreqKey((Integer)Helper.copy(obj.getreqKey()));
           if (obj.getrequestId() != null )
               setrequestId((String)Helper.copy(obj.getrequestId()));
           if (obj.gettotalNum() != null )
               settotalNum((Integer)Helper.copy(obj.gettotalNum()));
        }
    } //-- void updateFields(CMSObject) 
    //BEGIN CODE
    private static final Logger log = Logger.getLogger(CCLConnectionAction.class);
	private final static int maxLengthBinary = 20000; // keep it consistent with CalixMainSocketClient.
    Integer requestId = 0;
    private static int reqId = 1;
    public int getFlowId(CMSSignal sig) {
        if (sig != null && sig instanceof CMSobjectRequestSignal) { //This check is required because the responcesignal also comes here !!
            requestId = sig.getRequestor().getRequestTag();
            
        }
        return super.getFlowId(sig);
    }
    public static Boolean initial = false;
    public void doExecute(DbTransaction tx, Integer nbSessionID) throws EMSException {
    	synchronized(initial) {
	    	if(!initial) {
		          try{
		        	  new com.occam.ems.fe.ccl.CCLProcessFE().callMain(null);
		        	  initial = true;
			        }catch(Exception e) {
			        	log.error(e);
			        }
		    	}
    	}
    	String moduleId = getrequestId();
    	
    	byte[] data = getbinaryPackage();
    	if(data == null && moduleId == null) {
    		log.warn("Request parameter error.");
    	}
    	
    	byte[] result;
    	Properties pro = new Properties();
		pro.put(ServiceMgmtSessionFE.ISMULTIPLE, this.getisMultiPle());
    	pro.put(ServiceMgmtSessionFE.TOTALNUM, this.gettotalNum());
    	pro.put(ServiceMgmtSessionFE.NUM, this.getnum());
    	pro.put(ServiceMgmtSessionFE.REQKEY, this.getreqKey());  

    	result = ((CalixMainSocketServerFE)PureServerUtilsFE.serverSocketFE).processMessageSync(data,nbSessionID.toString() + ":" + generateReqId(),moduleId , nbSessionID.toString(), pro);
    	
    	setbinaryPackage(result);
    	this.setRawObjectTlvBytes(null);
    }
    
    @Override
	public void postEMSProcessTaskExecute(int requestType, ArrayList loadedObjects) {
		if (loadedObjects.size() == 1 && loadedObjects.get(0) != null) {
			CCLConnectionAction tmpAction = new CCLConnectionAction();
			tmpAction.copyFields((CCLConnectionAction) loadedObjects.get(0));
			byte[] binaryArray = tmpAction.getbinaryPackage();
			if(binaryArray!= null){
				int arrayLength = binaryArray.length;
				if (arrayLength > maxLengthBinary) {
					loadedObjects.remove(0);
					for (int j = 0; j < arrayLength / maxLengthBinary; j++) {
						byte[] tmpByte = new byte[maxLengthBinary];
						System.arraycopy(binaryArray, j * maxLengthBinary, tmpByte,
								0, maxLengthBinary);
						CCLConnectionAction subAction = new CCLConnectionAction();
						subAction.copyFields(tmpAction);
						subAction.setbinaryPackage(tmpByte);
						loadedObjects.add(subAction);
					}

					int choppedLength = arrayLength % maxLengthBinary;
					if(choppedLength > 0){
						byte[] lastByte = new byte[choppedLength];
						System.arraycopy(binaryArray, (arrayLength / maxLengthBinary)
								* maxLengthBinary, lastByte, 0, choppedLength);
						tmpAction.setbinaryPackage(lastByte);
						loadedObjects.add(tmpAction);
					}
					log.debug("CCLConnectionAction is chopped. ");
				}
			}
		}
	}

	private static synchronized int generateReqId() {
    	if(reqId < Integer.MAX_VALUE)
    		reqId += 1;
    	else
    		reqId = 1;
    	return reqId;
    }
    
    public boolean isEMSObject(){
    	return true;
    }
    //END CODE
}
