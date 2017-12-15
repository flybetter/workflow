/*

 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.calix.ems.database.AbstractCMSDatabase;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.DBUtil;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.CMSObject;
import com.calix.system.server.dbmodel.Helper;
import com.calix.system.server.dbmodel.SwVersionNo;

/**
 * Class GetB6TemplateResult.
 * 
 * @version $Revision$ $Date$
 */
public class GetB6TemplateResult extends B6Template {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field m_TemplateId
     */
    public String m_TemplateId;

    /**
     * Field m_TemplateRecordType
     */
    public String m_TemplateRecordType;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "GetB6TemplateResult";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public GetB6TemplateResult() {
        super();
    } //-- com.calix.bseries.server.dbmodel.GetB6TemplateResult()


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
        if( obj1 instanceof GetB6TemplateResult ) {
            super.copyFields(obj1);
            GetB6TemplateResult obj = (GetB6TemplateResult)obj1;
            setTemplateId((String)Helper.copy(obj.getTemplateId()));
            setTemplateRecordType((String)Helper.copy(obj.getTemplateRecordType()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getTemplateId
     */
    public String getTemplateId()
    {
        return this.m_TemplateId;
    } //-- String getTemplateId() 

    /**
     * Method getTemplateRecordType
     */
    public String getTemplateRecordType()
    {
        return this.m_TemplateRecordType;
    } //-- String getTemplateRecordType() 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.GetB6TemplateResult;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

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
            case 0x351F:
                if (m_TemplateRecordType == null) {
                    m_TemplateRecordType = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3556:
                if (m_TemplateId == null) {
                    m_TemplateId = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x3556, m_TemplateId);
        TLVHelper.addEmbeddedTLV(tlv, 0x351F, m_TemplateRecordType);
    } //-- void populateTLVFromAttributes(TLV, SwVersionNo) 

    /**
     * Method setTemplateId
     * 
     * @param TemplateId
     */
    public void setTemplateId(String TemplateId)
    {
        this.m_TemplateId = TemplateId;
    } //-- void setTemplateId(String) 

    /**
     * Method setTemplateRecordType
     * 
     * @param TemplateRecordType
     */
    public void setTemplateRecordType(String TemplateRecordType)
    {
        this.m_TemplateRecordType = TemplateRecordType;
    } //-- void setTemplateRecordType(String) 

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
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof GetB6TemplateResult ) {
            super.updateFields(obj1);
            GetB6TemplateResult obj = (GetB6TemplateResult)obj1;
           if (obj.getTemplateId() != null )
               setTemplateId((String)Helper.copy(obj.getTemplateId()));
           if (obj.getTemplateRecordType() != null )
               setTemplateRecordType((String)Helper.copy(obj.getTemplateRecordType()));
        }
    } //-- void updateFields(CMSObject) 
 // BEGIN CODE
    
    @Override
    public Collection doQuery(DbTransaction tx, String filter) throws EMSException
    
    {
   	   Collection resultCollection = new ArrayList();
    	if(getTemplateId() != null && !getTemplateId().trim().equals("")){
    		if(getTemplateRecordType().equalsIgnoreCase("B6FPCMasterTemplate")){
    	   	    ResultSet resultSet = null;
       	        C7Database db = C7Database.getInstance();
       	        String sql = "select  * from b6fpcmastertemplate where  dbidentity = '" + getTemplateId()+"'";
       	        try{
       	            db.beginTransaction();
       	            resultSet = db.executeQuery(sql);
       	            db.commitTransaction();
       	    		while(resultSet.next()){
       	    			B6FPCMasterTemplate template = new B6FPCMasterTemplate();
       	    			template.setconvertName(resultSet.getString("dbidentity"));
       	    			template.setTemplateSource(resultSet.getString("templatesource"));
       	    			template.setCMSUserName(resultSet.getString("cmsusername"));
       	    			template.setUserIp(resultSet.getString("userip"));
       	    			template.setStartTime(resultSet.getString("starttime"));
       	    			template.setEndTime(resultSet.getString("endtime"));
       	    			template.setStartTimeLong(resultSet.getLong("starttimelong"));
       	    			template.setEndTimeLong(resultSet.getLong("endtimelong"));
       	    			template.setResult(resultSet.getString("result"));
       	    			template.setIPAddress1(resultSet.getString("ipaddress1"));
       	    			template.setResponseDetail(resultSet.getString("responsedetail"));
       	    			template.setservice(resultSet.getString("service"));
       	    			template.setservice_type(resultSet.getString("service_type"));
       	    			template.setoperation(resultSet.getString("operation"));
       	    			template.setdevice_host_name(resultSet.getString("device_host_name"));
       	    			template.setdsl_interface_number(resultSet.getInt("dsl_interface_number"));
       	    			template.setdsl_profile_name(resultSet.getString("dsl_profile_name"));
       	    			template.setaccess_profile_name_int(resultSet.getString("access_profile_name_int"));
       	    			template.setservice_number_int(resultSet.getInt("service_number_int"));
       	    			template.setpvc_service_int(resultSet.getString("pvc_service_int"));
       	    			template.setactivate_service_int(resultSet.getInt("activate_service_int"));
       	    			template.setactivate_dsl_port(resultSet.getInt("activate_dsl_port"));
       	    			template.setkey_info(resultSet.getString("key_info"));
       	    			template.setmac_learning_int(resultSet.getString("mac_learning_int"));
       	    			template.setmac_limit_int(resultSet.getString("mac_limit_int"));
       	    			template.setigmp_group_limit_int(resultSet.getString("igmp_group_limit_int"));
       	    			template.setmac_address_int(resultSet.getString("mac_address_int"));
       	    			template.setip_address_int(resultSet.getString("ip_address_int"));
       	    			template.setipmask_int(resultSet.getString("ipmask_int"));
       	    			template.setkey_info_old(resultSet.getString("key_info_old"));
       	    		    //CMS-15595 just for B6216
//       	    			if(resultSet.getString("tpstc_mode")!=null){
//       	    				if(resultSet.getString("tpstc_mode").equals("auto")){
//       	    					template.settpstc_mode("0");
//       	    				}else if(resultSet.getString("tpstc_mode").equals("ptm")){
//       	    					template.settpstc_mode("1");
//       	    				}else if(resultSet.getString("tpstc_mode").equals("atm")){
//       	    					template.settpstc_mode("2");
//       	    				}else {
//       	    					template.settpstc_mode(resultSet.getString("tpstc_mode"));
//       	    				}
//       	    			}else{
//       	    				template.settpstc_mode(resultSet.getString("tpstc_mode"));
//       	    			}
       	    			template.settpstc_mode(resultSet.getString("tpstc_mode"));
		    			template.setmatch_vlan(resultSet.getString("match_vlan"));
       	    			
       	    			resultCollection.add(template);
       	    		}
       	        }catch(Exception ex){
       	            db.rollbackTransaction();
       	        }finally{
       	            try{
       	            	DBUtil.closeQuietly(resultSet);
       	            }catch(Exception ex){} 
       	            db.close();
       	        }	
    		}else if(getTemplateRecordType().equalsIgnoreCase("B6DSLBoundedTemplate")){
    			 ResultSet resultSet = null;
    		        C7Database db = C7Database.getInstance();
    		        String sql = "select  * from B6DSLBoundedTemplate where  dbidentity = '" + getTemplateId()+"'";
    		        try{
    		            db.beginTransaction();
    		            resultSet = db.executeQuery(sql);
    		            db.commitTransaction();
    		    		while(resultSet.next()){
    		    			B6DSLBoundedTemplate template = new B6DSLBoundedTemplate();
    		    			template.setconvertName(resultSet.getString("dbidentity"));
    		    			template.setdevice_host_name(resultSet.getString("device_host_name"));
    		    			template.setoperation(resultSet.getString("operation"));
    		    			template.setIPAddress1(resultSet.getString("ipaddress1"));
    		    			template.setTemplateSource(resultSet.getString("templatesource"));
    		    			template.setCMSUserName(resultSet.getString("cmsusername"));
    		    			template.setUserIp(resultSet.getString("userip"));
    		    			template.setStartTime(resultSet.getString("starttime"));
    		    			template.setEndTime(resultSet.getString("endtime"));
    		    			template.setStartTimeLong(resultSet.getLong("starttimelong"));
    		    			template.setEndTimeLong(resultSet.getLong("endtimelong"));
    		    			template.setResult(resultSet.getString("result"));
    		    			template.setservice(resultSet.getString("service"));
    		    			template.setservice_type(resultSet.getString("service_type"));    			
    		    			template.setdsl_interface_number(resultSet.getInt("dsl_interface_number"));
    		    			template.setdsl_interface_number_2(resultSet.getInt("dsl_interface_number_2"));
    		    			template.setdsl_profile_name(resultSet.getString("dsl_profile_name"));
    		    			template.setkey_info(resultSet.getString("key_info"));
    		    			template.setbonding_group_number(resultSet.getInt("bonding_group_number"));
    		    			template.setservice_number_int(resultSet.getInt("service_number_int"));
    		    			template.setpvc_service_int(resultSet.getString("pvc_service_int"));
    		    			template.setactive_dsl_port(resultSet.getString("active_dsl_port"));
    		    			template.setaccess_profile_name_int(resultSet.getString("access_profile_name_int"));    			
    		    			template.setResponseDetail(resultSet.getString("responsedetail"));
    		    			template.setactivate_service_int(resultSet.getString("activate_service_int"));
    		    			template.setmac_learning_int(resultSet.getString("mac_learning_int"));
    		    			template.setmac_limit_int(resultSet.getString("mac_limit_int"));
    		    			template.setigmp_group_limit_int(resultSet.getString("igmp_group_limit_int"));
    		    			template.setmac_address_int(resultSet.getString("mac_address_int"));
    		    			template.setip_address_int(resultSet.getString("ip_address_int"));
    		    			template.setipmask_int(resultSet.getString("ipmask_int"));
    		    			//CMS-15595 just for B6216
//    		    			if(resultSet.getString("tpstc_mode")!=null){
//    		    				if(resultSet.getString("tpstc_mode").equals("auto")){
//    		    					template.settpstc_mode("0");
//    		    				}else if(resultSet.getString("tpstc_mode").equals("ptm")){
//    		    					template.settpstc_mode("1");
//    		    				}else if(resultSet.getString("tpstc_mode").equals("atm")){
//    		    					template.settpstc_mode("2");
//    		    				}else {
//    		    					template.settpstc_mode(resultSet.getString("tpstc_mode"));
//    		    				}
//    		    			}else{
//    		    				template.settpstc_mode(resultSet.getString("tpstc_mode"));
//    		    			}
    		    			template.settpstc_mode(resultSet.getString("tpstc_mode"));
    		    			template.setmatch_vlan(resultSet.getString("match_vlan"));
    		    			    		    			
    		    			resultCollection.add(template);
    		    		}
    		        }catch(Exception ex){
    		            db.rollbackTransaction();
    		        }finally{
    		            try{
    		            	DBUtil.closeQuietly(resultSet);
    		            }catch(Exception ex){} 
    		            db.close();
    		        }					
    			
    		}
 				
    			
    	}else{
    		 //String filterStr = "ipaddress1 = '" + getIPAddress1()+"' and templatesource = '"+getTemplateSource()+"'";
    		//Support query M6 or GUI
    		 String filterStr = "ipaddress1 = '" + getIPAddress1()+"'";
    		
    	   	 String getTemplateRecordType = getTemplateRecordType();
    	    
    	   	 if(getTemplateRecordType.equals("B6FPCMasterTemplate")){
    		     AbstractCMSDatabase db = (AbstractCMSDatabase) tx.getDatabase();
    		     Collection retVal = db.getFilterCollection(B6FPCMasterTemplate.class,
    		                                                filterStr,
    		                                                tx,
    		                                                0,
    		                                                100000);
    		     B6FPCMasterTemplate templateObj = null;
    		 	 for (Iterator<? extends Object> iter = retVal.iterator(); iter.hasNext();) {
    					Object obj = iter.next();
    					if (obj instanceof B6FPCMasterTemplate) {
    						if(templateObj !=null){
    							if(((B6FPCMasterTemplate)obj).getStartTimeLong()>templateObj.getStartTimeLong()){
    								templateObj = (B6FPCMasterTemplate) obj;
    							}
    						}else{
    							templateObj = (B6FPCMasterTemplate) obj;
    		
    						}
    					
    					}
    				}
    		 	resultCollection.add(templateObj);
    	   		
    	   	 }else if(getTemplateRecordType.equals("B6DSLBoundedTemplate")){
    		     AbstractCMSDatabase db = (AbstractCMSDatabase) tx.getDatabase();
    		     Collection retVal = db.getFilterCollection(B6DSLBoundedTemplate.class,
    		                                                filterStr,
    		                                                tx,
    		                                                0,
    		                                                100000);
    		     B6DSLBoundedTemplate templateObj = null;
    		 	 for (Iterator<? extends Object> iter = retVal.iterator(); iter.hasNext();) {
    					Object obj = iter.next();
    					if (obj instanceof B6DSLBoundedTemplate) {
    						if(templateObj !=null){
    							if(((B6DSLBoundedTemplate)obj).getStartTimeLong()>templateObj.getStartTimeLong()){
    								templateObj = (B6DSLBoundedTemplate) obj;
    							}
    						}else{
    							templateObj = (B6DSLBoundedTemplate) obj;
    		
    						}
    					
    					}
    				}
    		 	resultCollection.add(templateObj);
    	   	 }

    	}

	   	
	 	return resultCollection;
    }
    
// END CODE
}