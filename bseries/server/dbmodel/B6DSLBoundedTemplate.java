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

import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.common.CommonDateTimeUtils;
import com.calix.bseries.server.ana.process.ANACommandProcess;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.DBUtil;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.exception.SemanticException;
import com.calix.ems.security.SecurityCheckerBuilder;
import com.calix.ems.util.EMSUtils;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6DSLBoundedTemplate.
 * 
 * @version $Revision$ $Date$
 */
public class B6DSLBoundedTemplate extends B6Template {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Access Profile Name Int
     */
    public String m_access_profile_name_int;

    /**
     * Activate Service Int
     */
    public String m_activate_service_int;

    /**
     * Active Dsl Port
     */
    public String m_active_dsl_port;

    /**
     * Bonding Group Number
     */
    public Integer m_bonding_group_number;

    /**
     * Dsl Interface Number
     */
    public Integer m_dsl_interface_number;

    /**
     * Dsl Interface Number 2
     */
    public Integer m_dsl_interface_number_2;

    /**
     * Dsl Profile Name
     */
    public String m_dsl_profile_name;

    /**
     * Igmp Group Limit Int
     */
    public String m_igmp_group_limit_int;

    /**
     * Ip Address Int
     */
    public String m_ip_address_int;

    /**
     * Ipdg Int
     */
    public String m_ipdg_int;

    /**
     * Ipmask Int
     */
    public String m_ipmask_int;

    /**
     * KeyInfo
     */
    public String m_key_info;

    /**
     * Mac Address Int
     */
    public String m_mac_address_int;

    /**
     * Mac Learning Int
     */
    public String m_mac_learning_int;

    /**
     * Mac Limit Int
     */
    public String m_mac_limit_int;

    /**
     * match_vlan
     */
    public String m_match_vlan;

    /**
     * Pvc Service Int
     */
    public String m_pvc_service_int;

    /**
     * Service Number Int
     */
    public Integer m_service_number_int;

    /**
     * tpstc_mode
     */
    public String m_tpstc_mode;

    /**
     * Field TYPE_NAME
     */
    public static String TYPE_NAME = "B6DSLBoundedTemplate";

    /**
     * Field flowID
     */
    public static final int flowID = 1;


      //----------------/
     //- Constructors -/
    //----------------/

    public B6DSLBoundedTemplate() {
        super();
    } //-- com.calix.bseries.server.dbmodel.B6DSLBoundedTemplate()


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
        if( obj1 instanceof B6DSLBoundedTemplate ) {
            super.copyFields(obj1);
            B6DSLBoundedTemplate obj = (B6DSLBoundedTemplate)obj1;
            setaccess_profile_name_int((String)Helper.copy(obj.getaccess_profile_name_int()));
            setactivate_service_int((String)Helper.copy(obj.getactivate_service_int()));
            setactive_dsl_port((String)Helper.copy(obj.getactive_dsl_port()));
            setbonding_group_number((Integer)Helper.copy(obj.getbonding_group_number()));
            setdsl_interface_number((Integer)Helper.copy(obj.getdsl_interface_number()));
            setdsl_interface_number_2((Integer)Helper.copy(obj.getdsl_interface_number_2()));
            setdsl_profile_name((String)Helper.copy(obj.getdsl_profile_name()));
            setigmp_group_limit_int((String)Helper.copy(obj.getigmp_group_limit_int()));
            setip_address_int((String)Helper.copy(obj.getip_address_int()));
            setipdg_int((String)Helper.copy(obj.getipdg_int()));
            setipmask_int((String)Helper.copy(obj.getipmask_int()));
            setkey_info((String)Helper.copy(obj.getkey_info()));
            setmac_address_int((String)Helper.copy(obj.getmac_address_int()));
            setmac_learning_int((String)Helper.copy(obj.getmac_learning_int()));
            setmac_limit_int((String)Helper.copy(obj.getmac_limit_int()));
            setmatch_vlan((String)Helper.copy(obj.getmatch_vlan()));
            setpvc_service_int((String)Helper.copy(obj.getpvc_service_int()));
            setservice_number_int((Integer)Helper.copy(obj.getservice_number_int()));
            settpstc_mode((String)Helper.copy(obj.gettpstc_mode()));
        }
    } //-- void copyFields(CMSObject) 

    /**
     * Method getTlvType
     */
    public int getTlvType()
    {
        return BseriesTlvConstants.B6DSLBoundedTemplate;
    } //-- int getTlvType() 

    /**
     * Method getTypeName
     */
    public String getTypeName()
    {
        return TYPE_NAME;
    } //-- String getTypeName() 

    /**
     * Method getaccess_profile_name_int
     */
    public String getaccess_profile_name_int()
    {
        return this.m_access_profile_name_int;
    } //-- String getaccess_profile_name_int() 

    /**
     * Method getactivate_service_int
     */
    public String getactivate_service_int()
    {
        return this.m_activate_service_int;
    } //-- String getactivate_service_int() 

    /**
     * Method getactive_dsl_port
     */
    public String getactive_dsl_port()
    {
        return this.m_active_dsl_port;
    } //-- String getactive_dsl_port() 

    /**
     * Method getbonding_group_number
     */
    public Integer getbonding_group_number()
    {
        return this.m_bonding_group_number;
    } //-- Integer getbonding_group_number() 

    /**
     * Method getdsl_interface_number
     */
    public Integer getdsl_interface_number()
    {
        return this.m_dsl_interface_number;
    } //-- Integer getdsl_interface_number() 

    /**
     * Method getdsl_interface_number_2
     */
    public Integer getdsl_interface_number_2()
    {
        return this.m_dsl_interface_number_2;
    } //-- Integer getdsl_interface_number_2() 

    /**
     * Method getdsl_profile_name
     */
    public String getdsl_profile_name()
    {
        return this.m_dsl_profile_name;
    } //-- String getdsl_profile_name() 

    /**
     * Method getigmp_group_limit_int
     */
    public String getigmp_group_limit_int()
    {
        return this.m_igmp_group_limit_int;
    } //-- String getigmp_group_limit_int() 

    /**
     * Method getip_address_int
     */
    public String getip_address_int()
    {
        return this.m_ip_address_int;
    } //-- String getip_address_int() 

    /**
     * Method getipdg_int
     */
    public String getipdg_int()
    {
        return this.m_ipdg_int;
    } //-- String getipdg_int() 

    /**
     * Method getipmask_int
     */
    public String getipmask_int()
    {
        return this.m_ipmask_int;
    } //-- String getipmask_int() 

    /**
     * Method getkey_info
     */
    public String getkey_info()
    {
        return this.m_key_info;
    } //-- String getkey_info() 

    /**
     * Method getmac_address_int
     */
    public String getmac_address_int()
    {
        return this.m_mac_address_int;
    } //-- String getmac_address_int() 

    /**
     * Method getmac_learning_int
     */
    public String getmac_learning_int()
    {
        return this.m_mac_learning_int;
    } //-- String getmac_learning_int() 

    /**
     * Method getmac_limit_int
     */
    public String getmac_limit_int()
    {
        return this.m_mac_limit_int;
    } //-- String getmac_limit_int() 

    /**
     * Method getmatch_vlan
     */
    public String getmatch_vlan()
    {
        return this.m_match_vlan;
    } //-- String getmatch_vlan() 

    /**
     * Method getpvc_service_int
     */
    public String getpvc_service_int()
    {
        return this.m_pvc_service_int;
    } //-- String getpvc_service_int() 

    /**
     * Method getservice_number_int
     */
    public Integer getservice_number_int()
    {
        return this.m_service_number_int;
    } //-- Integer getservice_number_int() 

    /**
     * Method gettpstc_mode
     */
    public String gettpstc_mode()
    {
//    	if (this.m_tpstc_mode==null&&this.m_match_vlan!=null){
//    		return "ptm";
//    	}else if(this.m_tpstc_mode!=null&&this.m_tpstc_mode.equals("0")){
//    		return "auto";
//    	}else if(this.m_tpstc_mode!=null&&this.m_tpstc_mode.equals("1")){
//    		return "ptm";
//    	}else if(this.m_tpstc_mode!=null&&this.m_tpstc_mode.equals("2")){
//    		return "atm";
//    	}else{
//    		return this.m_tpstc_mode;
//    	}
    	if(this.m_tpstc_mode==null&&this.m_match_vlan!=null){
    		return "1";
    	}else{
    		return this.m_tpstc_mode;	
    	}
    } //-- String gettpstc_mode() 

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
            case 0x3523:
                if (m_dsl_interface_number == null) {
                    m_dsl_interface_number = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3524:
                if (m_dsl_interface_number_2 == null) {
                    m_dsl_interface_number_2 = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3525:
                if (m_dsl_profile_name == null) {
                    m_dsl_profile_name = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3526:
                if (m_key_info == null) {
                    m_key_info = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3527:
                if (m_bonding_group_number == null) {
                    m_bonding_group_number = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3528:
                if (m_service_number_int == null) {
                    m_service_number_int = TLVHelper.getIntegerValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3529:
                if (m_pvc_service_int == null) {
                    m_pvc_service_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x352A:
                if (m_access_profile_name_int == null) {
                    m_access_profile_name_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x352B:
                if (m_activate_service_int == null) {
                    m_activate_service_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x352C:
                if (m_active_dsl_port == null) {
                    m_active_dsl_port = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x352D:
                if (m_mac_learning_int == null) {
                    m_mac_learning_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x352E:
                if (m_mac_limit_int == null) {
                    m_mac_limit_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x352F:
                if (m_igmp_group_limit_int == null) {
                    m_igmp_group_limit_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3530:
                if (m_mac_address_int == null) {
                    m_mac_address_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3531:
                if (m_ip_address_int == null) {
                    m_ip_address_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3532:
                if (m_ipmask_int == null) {
                    m_ipmask_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3533:
                if (m_ipdg_int == null) {
                    m_ipdg_int = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3564:
                if (m_tpstc_mode == null) {
//                	String temp=TLVHelper.getStringValueOfTLV(tlv );
//                	if(temp.equals("0")){
//                		m_tpstc_mode="auto";
//                	}else if(temp.equals("1")){
//                		m_tpstc_mode="ptm";
//                	}else if(temp.equals("2")){
//                		m_tpstc_mode="atm";
//                	}else{
//                		m_tpstc_mode=temp;
//                	}
                    m_tpstc_mode = TLVHelper.getStringValueOfTLV(tlv );
                    return;
                }
                break;
            case 0x3565:
                if (m_match_vlan == null) {
                    m_match_vlan = TLVHelper.getStringValueOfTLV(tlv );
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
        TLVHelper.addEmbeddedTLV(tlv, 0x352A, m_access_profile_name_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x352B, m_activate_service_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x352C, m_active_dsl_port);
        TLVHelper.addEmbeddedTLV(tlv, 0x3527, m_bonding_group_number);
        TLVHelper.addEmbeddedTLV(tlv, 0x3523, m_dsl_interface_number);
        TLVHelper.addEmbeddedTLV(tlv, 0x3524, m_dsl_interface_number_2);
        TLVHelper.addEmbeddedTLV(tlv, 0x3525, m_dsl_profile_name);
        TLVHelper.addEmbeddedTLV(tlv, 0x352F, m_igmp_group_limit_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3531, m_ip_address_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3533, m_ipdg_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3532, m_ipmask_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3526, m_key_info);
        TLVHelper.addEmbeddedTLV(tlv, 0x3530, m_mac_address_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x352D, m_mac_learning_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x352E, m_mac_limit_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3565, m_match_vlan);
        TLVHelper.addEmbeddedTLV(tlv, 0x3529, m_pvc_service_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3528, m_service_number_int);
        TLVHelper.addEmbeddedTLV(tlv, 0x3564, m_tpstc_mode);
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
     * Method setaccess_profile_name_int
     * 
     * @param access_profile_name_int
     */
    public void setaccess_profile_name_int(String access_profile_name_int)
    {
        this.m_access_profile_name_int = access_profile_name_int;
    } //-- void setaccess_profile_name_int(String) 

    /**
     * Method setactivate_service_int
     * 
     * @param activate_service_int
     */
    public void setactivate_service_int(String activate_service_int)
    {
        this.m_activate_service_int = activate_service_int;
    } //-- void setactivate_service_int(String) 

    /**
     * Method setactive_dsl_port
     * 
     * @param active_dsl_port
     */
    public void setactive_dsl_port(String active_dsl_port)
    {
        this.m_active_dsl_port = active_dsl_port;
    } //-- void setactive_dsl_port(String) 

    /**
     * Method setbonding_group_number
     * 
     * @param bonding_group_number
     */
    public void setbonding_group_number(Integer bonding_group_number)
    {
        this.m_bonding_group_number = bonding_group_number;
    } //-- void setbonding_group_number(Integer) 

    /**
     * Method setdsl_interface_number
     * 
     * @param dsl_interface_number
     */
    public void setdsl_interface_number(Integer dsl_interface_number)
    {
        this.m_dsl_interface_number = dsl_interface_number;
    } //-- void setdsl_interface_number(Integer) 

    /**
     * Method setdsl_interface_number_2
     * 
     * @param dsl_interface_number_2
     */
    public void setdsl_interface_number_2(Integer dsl_interface_number_2)
    {
        this.m_dsl_interface_number_2 = dsl_interface_number_2;
    } //-- void setdsl_interface_number_2(Integer) 

    /**
     * Method setdsl_profile_name
     * 
     * @param dsl_profile_name
     */
    public void setdsl_profile_name(String dsl_profile_name)
    {
        this.m_dsl_profile_name = dsl_profile_name;
    } //-- void setdsl_profile_name(String) 

    /**
     * Method setigmp_group_limit_int
     * 
     * @param igmp_group_limit_int
     */
    public void setigmp_group_limit_int(String igmp_group_limit_int)
    {
        this.m_igmp_group_limit_int = igmp_group_limit_int;
    } //-- void setigmp_group_limit_int(String) 

    /**
     * Method setip_address_int
     * 
     * @param ip_address_int
     */
    public void setip_address_int(String ip_address_int)
    {
        this.m_ip_address_int = ip_address_int;
    } //-- void setip_address_int(String) 

    /**
     * Method setipdg_int
     * 
     * @param ipdg_int
     */
    public void setipdg_int(String ipdg_int)
    {
        this.m_ipdg_int = ipdg_int;
    } //-- void setipdg_int(String) 

    /**
     * Method setipmask_int
     * 
     * @param ipmask_int
     */
    public void setipmask_int(String ipmask_int)
    {
        this.m_ipmask_int = ipmask_int;
    } //-- void setipmask_int(String) 

    /**
     * Method setkey_info
     * 
     * @param key_info
     */
    public void setkey_info(String key_info)
    {
        this.m_key_info = key_info;
    } //-- void setkey_info(String) 

    /**
     * Method setmac_address_int
     * 
     * @param mac_address_int
     */
    public void setmac_address_int(String mac_address_int)
    {
        this.m_mac_address_int = mac_address_int;
    } //-- void setmac_address_int(String) 

    /**
     * Method setmac_learning_int
     * 
     * @param mac_learning_int
     */
    public void setmac_learning_int(String mac_learning_int)
    {
        this.m_mac_learning_int = mac_learning_int;
    } //-- void setmac_learning_int(String) 

    /**
     * Method setmac_limit_int
     * 
     * @param mac_limit_int
     */
    public void setmac_limit_int(String mac_limit_int)
    {
        this.m_mac_limit_int = mac_limit_int;
    } //-- void setmac_limit_int(String) 

    /**
     * Method setmatch_vlan
     * 
     * @param match_vlan
     */
    public void setmatch_vlan(String match_vlan)
    {
        this.m_match_vlan = match_vlan;
    } //-- void setmatch_vlan(String) 

    /**
     * Method setpvc_service_int
     * 
     * @param pvc_service_int
     */
    public void setpvc_service_int(String pvc_service_int)
    {
        this.m_pvc_service_int = pvc_service_int;
    } //-- void setpvc_service_int(String) 

    /**
     * Method setservice_number_int
     * 
     * @param service_number_int
     */
    public void setservice_number_int(Integer service_number_int)
    {
        this.m_service_number_int = service_number_int;
    } //-- void setservice_number_int(Integer) 

    /**
     * Method settpstc_mode
     * 
     * @param tpstc_mode
     */
    public void settpstc_mode(String tpstc_mode)
    {
        this.m_tpstc_mode = tpstc_mode;
    } //-- void settpstc_mode(String) 

    /**
     * Method updateFields
     * 
     * @param obj1
     */
    protected void updateFields(CMSObject obj1)
    {
        if( obj1 instanceof B6DSLBoundedTemplate ) {
            super.updateFields(obj1);
            B6DSLBoundedTemplate obj = (B6DSLBoundedTemplate)obj1;
           if (obj.getaccess_profile_name_int() != null )
               setaccess_profile_name_int((String)Helper.copy(obj.getaccess_profile_name_int()));
           if (obj.getactivate_service_int() != null )
               setactivate_service_int((String)Helper.copy(obj.getactivate_service_int()));
           if (obj.getactive_dsl_port() != null )
               setactive_dsl_port((String)Helper.copy(obj.getactive_dsl_port()));
           if (obj.getbonding_group_number() != null )
               setbonding_group_number((Integer)Helper.copy(obj.getbonding_group_number()));
           if (obj.getdsl_interface_number() != null )
               setdsl_interface_number((Integer)Helper.copy(obj.getdsl_interface_number()));
           if (obj.getdsl_interface_number_2() != null )
               setdsl_interface_number_2((Integer)Helper.copy(obj.getdsl_interface_number_2()));
           if (obj.getdsl_profile_name() != null )
               setdsl_profile_name((String)Helper.copy(obj.getdsl_profile_name()));
           if (obj.getigmp_group_limit_int() != null )
               setigmp_group_limit_int((String)Helper.copy(obj.getigmp_group_limit_int()));
           if (obj.getip_address_int() != null )
               setip_address_int((String)Helper.copy(obj.getip_address_int()));
           if (obj.getipdg_int() != null )
               setipdg_int((String)Helper.copy(obj.getipdg_int()));
           if (obj.getipmask_int() != null )
               setipmask_int((String)Helper.copy(obj.getipmask_int()));
           if (obj.getkey_info() != null )
               setkey_info((String)Helper.copy(obj.getkey_info()));
           if (obj.getmac_address_int() != null )
               setmac_address_int((String)Helper.copy(obj.getmac_address_int()));
           if (obj.getmac_learning_int() != null )
               setmac_learning_int((String)Helper.copy(obj.getmac_learning_int()));
           if (obj.getmac_limit_int() != null )
               setmac_limit_int((String)Helper.copy(obj.getmac_limit_int()));
           if (obj.getmatch_vlan() != null )
               setmatch_vlan((String)Helper.copy(obj.getmatch_vlan()));
           if (obj.getpvc_service_int() != null )
               setpvc_service_int((String)Helper.copy(obj.getpvc_service_int()));
           if (obj.getservice_number_int() != null )
               setservice_number_int((Integer)Helper.copy(obj.getservice_number_int()));
           if (obj.gettpstc_mode() != null )
               settpstc_mode((String)Helper.copy(obj.gettpstc_mode()));
        }
    } //-- void updateFields(CMSObject) 
    //BEGIN CODE
   	private static final String TEMPLATE_NAME="FPC_MASTER_DSL_BONDED.template";
    private static final String SERVICE_DESC="FPC_MASTER_DSL_BONDED services";


     
       protected ArrayList checkUsage(Collection objs){
           ArrayList result = new ArrayList();
           return result;
       }



     /*  public String getDesc() {
           return getname();
       }*/


       public boolean isEMSObject(){
           return true;
       }

       public Collection getSecurityCheckers(int action)  {
           return SecurityCheckerBuilder.EMSCheckers;
       }



       private long profileId = -1;
       public long getProfileId() {
           if (profileId == -1) {
               String idStr = getIdentityValue() == null ? null : getIdentityValue().toString();
               profileId = EMSUtils.hashCode(idStr);
           }
           return profileId;
       }

       public void setProfileId(long id) {
           profileId = id;
       }

       public String getProfileName() {
           return getIdentityValue() == null ? null : getIdentityValue().toString();
       }

       public boolean updatedLocalProfileDesc(CMSObject localProfile){
           return true;
       }


   	@Override
   	public String getServiceDesc() {
   		// TODO Auto-generated method stub
   		return SERVICE_DESC;
   	}


   	@Override
   	public String getTemplateName() {
   		// TODO Auto-generated method stub
   		return TEMPLATE_NAME;
   	}
   	

    public void processCreate(DbTransaction tx) throws SemanticException, EMSException
    {
    	if(!isBeingImported()){
        	setStartTimeLong(new Long(System.currentTimeMillis()));
          	setStartTime(CommonDateTimeUtils.getUSDateTime());
          	//if come from check result panel, should set response detail to null
          	setResult(STATUS_RUNNING);
          	setResponseDetail(null);
          	setTemplateSource("GUI");
          	setservice_type("HSI");
          	if(getservice_number_int() != null){
          		setservice(getservice_number_int().toString());
          	}else{
          		setservice("1");
          	}
    	}
      	super.processCreate(tx);
     // tx.getDatabase().createCMSObject(this, tx);
     // setRawObjectTlvBytes(null);     // reset the object tlv    	     	
    }
  
    public Collection doQuery(DbTransaction tx, String filter) throws EMSException {
		Collection coll = new ArrayList();
        ResultSet resultSet = null;
        C7Database db = C7Database.getInstance();
        //select  * from b6fpcmastertemplate order by starttimelong desc limit 100;
        String sql = "select  * from B6DSLBoundedTemplate order by starttimelong desc limit "+CalixConstants.B6TEMPLATE_LOG_QUERY_MAX;
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
//    			if(resultSet.getString("tpstc_mode")!=null){
//    				if(resultSet.getString("tpstc_mode").equals("auto")){
//    					template.settpstc_mode("0");
//    				}else if(resultSet.getString("tpstc_mode").equals("ptm")){
//    					template.settpstc_mode("1");
//    				}else if(resultSet.getString("tpstc_mode").equals("atm")){
//    					template.settpstc_mode("2");
//    				}else {
//    					template.settpstc_mode(resultSet.getString("tpstc_mode"));
//    				}
//    			}else{
//    				template.settpstc_mode(resultSet.getString("tpstc_mode"));
//    			}
    			template.settpstc_mode(resultSet.getString("tpstc_mode"));
    			template.setmatch_vlan(resultSet.getString("match_vlan"));		
    			coll.add(template);
    		}
        }catch(Exception ex){
            db.rollbackTransaction();
        }finally{
            try{
            	DBUtil.closeQuietly(resultSet);
            }catch(Exception ex){} 
            db.close();
        }					
		

        return coll;
    		
   }


       //END Code
}