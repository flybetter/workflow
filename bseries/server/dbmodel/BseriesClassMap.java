/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;
import java.util.HashMap;

/**
 * Class BseriesClassMap.
 * 
 * @version $Revision$ $Date$
 */
public class BseriesClassMap extends BseriesTlvConstants 
implements DeviceClassMap
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field instance
     */
    private static BseriesClassMap instance;

    /**
     * Field map
     */
    private HashMap map;


      //----------------/
     //- Constructors -/
    //----------------/

    protected BseriesClassMap() {
        map = new HashMap();
        map.put("B6AONTDHCPProfile", "com.calix.bseries.server.dbmodel.B6AONTDHCPProfile");
        map.put("B6AONTHPNADetails", "com.calix.bseries.server.dbmodel.B6AONTHPNADetails");
        map.put("B6AONTLPortSettings", "com.calix.bseries.server.dbmodel.B6AONTLPortSettings");
        map.put("B6AONTMGCPSD", "com.calix.bseries.server.dbmodel.B6AONTMGCPSD");
        map.put("B6AONTProfile", "com.calix.bseries.server.dbmodel.B6AONTProfile");
        map.put("B6AONTSIPSD", "com.calix.bseries.server.dbmodel.B6AONTSIPSD");
        map.put("B6AONTServiceDetails", "com.calix.bseries.server.dbmodel.B6AONTServiceDetails");
        map.put("B6AONTVPortSettings", "com.calix.bseries.server.dbmodel.B6AONTVPortSettings");
        map.put("B6AccessProfile", "com.calix.bseries.server.dbmodel.B6AccessProfile");
        map.put("B6BLCPOTSProfile", "com.calix.bseries.server.dbmodel.B6BLCPOTSProfile");
        map.put("B6BLCPOTSService", "com.calix.bseries.server.dbmodel.B6BLCPOTSService");
        map.put("B6BLCSIPProfile", "com.calix.bseries.server.dbmodel.B6BLCSIPProfile");
        map.put("B6BLCSIPService", "com.calix.bseries.server.dbmodel.B6BLCSIPService");
        map.put("B6BVIPortDetails", "com.calix.bseries.server.dbmodel.B6BVIPortDetails");
        map.put("B6BinderDetails", "com.calix.bseries.server.dbmodel.B6BinderDetails");
        map.put("B6BinderGroup", "com.calix.bseries.server.dbmodel.B6BinderGroup");
        map.put("B6CommandAction", "com.calix.bseries.server.dbmodel.B6CommandAction");
        map.put("B6CommandResult", "com.calix.bseries.server.dbmodel.B6CommandResult");
        map.put("B6CommandResultData", "com.calix.bseries.server.dbmodel.B6CommandResultData");
        map.put("B6DHCPIpHost", "com.calix.bseries.server.dbmodel.B6DHCPIpHost");
        map.put("B6DSLBoundedTemplate", "com.calix.bseries.server.dbmodel.B6DSLBoundedTemplate");
        map.put("B6DSLFiberPortServiceDetails", "com.calix.bseries.server.dbmodel.B6DSLFiberPortServiceDetails");
        map.put("B6DSLProfile", "com.calix.bseries.server.dbmodel.B6DSLProfile");
        map.put("B6DSLServiceSpecificDetails", "com.calix.bseries.server.dbmodel.B6DSLServiceSpecificDetails");
        map.put("B6EndSubscriber", "com.calix.bseries.server.dbmodel.B6EndSubscriber");
        map.put("B6FPCMasterTemplate", "com.calix.bseries.server.dbmodel.B6FPCMasterTemplate");
        map.put("B6NewProxySettings", "com.calix.bseries.server.dbmodel.B6NewProxySettings");
        map.put("B6OSData", "com.calix.bseries.server.dbmodel.B6OSData");
        map.put("B6OccamGponOLT", "com.calix.bseries.server.dbmodel.B6OccamGponOLT");
        map.put("B6OccamGponOnt", "com.calix.bseries.server.dbmodel.B6OccamGponOnt");
        map.put("B6PSDProfile", "com.calix.bseries.server.dbmodel.B6PSDProfile");
        map.put("B6PWE3Service", "com.calix.bseries.server.dbmodel.B6PWE3Service");
        map.put("B6ProxySettings", "com.calix.bseries.server.dbmodel.B6ProxySettings");
        map.put("B6ProxySettingsAction", "com.calix.bseries.server.dbmodel.B6ProxySettingsAction");
        map.put("B6ScheduledTask", "com.calix.bseries.server.dbmodel.B6ScheduledTask");
        map.put("B6ServiceAssociation", "com.calix.bseries.server.dbmodel.B6ServiceAssociation");
        map.put("B6ServicePackage", "com.calix.bseries.server.dbmodel.B6ServicePackage");
        map.put("B6ServiceScheduler", "com.calix.bseries.server.dbmodel.B6ServiceScheduler");
        map.put("B6Settings", "com.calix.bseries.server.dbmodel.B6Settings");
        map.put("B6Subscriber", "com.calix.bseries.server.dbmodel.B6Subscriber");
        map.put("B6SubscriberAssociation", "com.calix.bseries.server.dbmodel.B6SubscriberAssociation");
        map.put("B6T1PortDetails", "com.calix.bseries.server.dbmodel.B6T1PortDetails");
        map.put("B6TLSProfile", "com.calix.bseries.server.dbmodel.B6TLSProfile");
        map.put("B6TLSService", "com.calix.bseries.server.dbmodel.B6TLSService");
        map.put("B6Template", "com.calix.bseries.server.dbmodel.B6Template");
        map.put("B6VideoService", "com.calix.bseries.server.dbmodel.B6VideoService");
        map.put("B6VoiceServiceDetails", "com.calix.bseries.server.dbmodel.B6VoiceServiceDetails");
        map.put("B6_GePort", "com.calix.bseries.server.dbmodel.B6_GePort");
        map.put("BseriesDataService", "com.calix.bseries.server.dbmodel.BseriesDataService");
        map.put("BseriesNotificationObject", "com.calix.bseries.server.dbmodel.BseriesNotificationObject");
        map.put("CCLConnectionAction", "com.calix.bseries.server.dbmodel.CCLConnectionAction");
        map.put("CalixB6Chassis", "com.calix.bseries.server.dbmodel.CalixB6Chassis");
        map.put("CalixB6Device", "com.calix.bseries.server.dbmodel.CalixB6Device");
        map.put("EMSBseriesConnAction", "com.calix.bseries.server.dbmodel.EMSBseriesConnAction");
        map.put("EMSBseriesRangeDiscoveryAction", "com.calix.bseries.server.dbmodel.EMSBseriesRangeDiscoveryAction");
        map.put("GetB6TemplateResult", "com.calix.bseries.server.dbmodel.GetB6TemplateResult");
    } //-- com.calix.bseries.server.dbmodel.BseriesClassMap()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method getCMSObjectForTLV
     * 
     * @param tlvType
     * @param from_version
     * @param tlv
     */
    public CMSObject getCMSObjectForTLV(int tlvType, TLV tlv, SwVersionNo from_version)
    {
        int descValue = -1, tempType = -1;
        CMSObject obj = null;
        switch (tlvType) {
            case 0x33B1: obj = new com.calix.bseries.server.dbmodel.CalixB6Device(); // CalixB6Device
                break;
            case 0x33B2: obj = new com.calix.bseries.server.dbmodel.EMSBseriesConnAction(); // EMSBseriesConnAction
                break;
            case 0x33B3: obj = new com.calix.bseries.server.dbmodel.B6Settings(); // B6Settings
                break;
            case 0x33B4: obj = new com.calix.bseries.server.dbmodel.EMSBseriesRangeDiscoveryAction(); // EMSBseriesRangeDiscoveryAction
                break;
            case 0x34CB: obj = new com.calix.bseries.server.dbmodel.CCLConnectionAction(); // CCLConnectionAction
                break;
            case 0x34CC: obj = new com.calix.bseries.server.dbmodel.BseriesNotificationObject(); // BseriesNotificationObject
                break;
            case 0x34CD: obj = new com.calix.bseries.server.dbmodel.CalixB6Chassis(); // CalixB6Chassis
                break;
            case 0x34CE: obj = new com.calix.bseries.server.dbmodel.B6ScheduledTask(); // B6ScheduledTask
                break;
            case 0x34CF: obj = new com.calix.bseries.server.dbmodel.BseriesDataService(); // BseriesDataService
                break;
            case 0x34D0: obj = new com.calix.bseries.server.dbmodel.B6OSData(); // B6OSData
                break;
            case 0x34D1: obj = new com.calix.bseries.server.dbmodel.B6VideoService(); // B6VideoService
                break;
            case 0x34D2: obj = new com.calix.bseries.server.dbmodel.B6PWE3Service(); // B6PWE3Service
                break;
            case 0x34D3: obj = new com.calix.bseries.server.dbmodel.B6TLSService(); // B6TLSService
                break;
            case 0x34D4: obj = new com.calix.bseries.server.dbmodel.B6ServicePackage(); // B6ServicePackage
                break;
            case 0x34D5: obj = new com.calix.bseries.server.dbmodel.B6Subscriber(); // B6Subscriber
                break;
            case 0x34D6: obj = new com.calix.bseries.server.dbmodel.B6BinderGroup(); // B6BinderGroup
                break;
            case 0x34D7: obj = new com.calix.bseries.server.dbmodel.B6ServiceAssociation(); // B6ServiceAssociation
                break;
            case 0x34D8: obj = new com.calix.bseries.server.dbmodel.B6AONTDHCPProfile(); // B6AONTDHCPProfile
                break;
            case 0x34D9: obj = new com.calix.bseries.server.dbmodel.B6VoiceServiceDetails(); // B6VoiceServiceDetails
                break;
            case 0x34DA: obj = new com.calix.bseries.server.dbmodel.B6BinderDetails(); // B6BinderDetails
                break;
            case 0x34DB: obj = new com.calix.bseries.server.dbmodel.B6AONTHPNADetails(); // B6AONTHPNADetails
                break;
            case 0x34DC: obj = new com.calix.bseries.server.dbmodel.B6AONTServiceDetails(); // B6AONTServiceDetails
                break;
            case 0x34DD: obj = new com.calix.bseries.server.dbmodel.B6TLSProfile(); // B6TLSProfile
                break;
            case 0x34DE: obj = new com.calix.bseries.server.dbmodel.B6ServiceScheduler(); // B6ServiceScheduler
                break;
            case 0x34DF: obj = new com.calix.bseries.server.dbmodel.B6SubscriberAssociation(); // B6SubscriberAssociation
                break;
            case 0x34E0: obj = new com.calix.bseries.server.dbmodel.B6AccessProfile(); // B6AccessProfile
                break;
            case 0x34E1: obj = new com.calix.bseries.server.dbmodel.B6DSLFiberPortServiceDetails(); // B6DSLFiberPortServiceDetails
                break;
            case 0x34E2: obj = new com.calix.bseries.server.dbmodel.B6DSLServiceSpecificDetails(); // B6DSLServiceSpecificDetails
                break;
            case 0x34E3: obj = new com.calix.bseries.server.dbmodel.B6BLCPOTSService(); // B6BLCPOTSService
                break;
            case 0x34E4: obj = new com.calix.bseries.server.dbmodel.B6BLCPOTSProfile(); // B6BLCPOTSProfile
                break;
            case 0x34E5: obj = new com.calix.bseries.server.dbmodel.B6AONTMGCPSD(); // B6AONTMGCPSD
                break;
            case 0x34E6: obj = new com.calix.bseries.server.dbmodel.B6AONTSIPSD(); // B6AONTSIPSD
                break;
            case 0x34E7: obj = new com.calix.bseries.server.dbmodel.B6BVIPortDetails(); // B6BVIPortDetails
                break;
            case 0x34E8: obj = new com.calix.bseries.server.dbmodel.B6BLCSIPService(); // B6BLCSIPService
                break;
            case 0x34E9: obj = new com.calix.bseries.server.dbmodel.B6AONTProfile(); // B6AONTProfile
                break;
            case 0x34EA: obj = new com.calix.bseries.server.dbmodel.B6OccamGponOnt(); // B6OccamGponOnt
                break;
            case 0x34EB: obj = new com.calix.bseries.server.dbmodel.B6OccamGponOLT(); // B6OccamGponOLT
                break;
            case 0x34ED: obj = new com.calix.bseries.server.dbmodel.B6AONTVPortSettings(); // B6AONTVPortSettings
                break;
            case 0x34EE: obj = new com.calix.bseries.server.dbmodel.B6T1PortDetails(); // B6T1PortDetails
                break;
            case 0x34EF: obj = new com.calix.bseries.server.dbmodel.B6BLCSIPProfile(); // B6BLCSIPProfile
                break;
            case 0x34F0: obj = new com.calix.bseries.server.dbmodel.B6PSDProfile(); // B6PSDProfile
                break;
            case 0x34F1: obj = new com.calix.bseries.server.dbmodel.B6DSLProfile(); // B6DSLProfile
                break;
            case 0x34F2: obj = new com.calix.bseries.server.dbmodel.B6AONTLPortSettings(); // B6AONTLPortSettings
                break;
            case 0x34F3: obj = new com.calix.bseries.server.dbmodel.B6_GePort(); // B6_GePort
                break;
            case 0x34F5: obj = new com.calix.bseries.server.dbmodel.B6EndSubscriber(); // B6EndSubscriber
                break;
            case 0x34FE: obj = new com.calix.bseries.server.dbmodel.B6FPCMasterTemplate(); // B6FPCMasterTemplate
                break;
            case 0x351E: obj = new com.calix.bseries.server.dbmodel.GetB6TemplateResult(); // GetB6TemplateResult
                break;
            case 0x3522: obj = new com.calix.bseries.server.dbmodel.B6DSLBoundedTemplate(); // B6DSLBoundedTemplate
                break;
            case 0x3536: obj = new com.calix.bseries.server.dbmodel.B6CommandAction(); // B6CommandAction
                break;
            case 0x353A: obj = new com.calix.bseries.server.dbmodel.B6CommandResult(); // B6CommandResult
                break;
            case 0x353D: obj = new com.calix.bseries.server.dbmodel.B6CommandResultData(); // B6CommandResultData
                break;
            case 0x3548: obj = new com.calix.bseries.server.dbmodel.B6ProxySettings(); // B6ProxySettings
                break;
            case 0x3554: obj = new com.calix.bseries.server.dbmodel.B6ProxySettingsAction(); // B6ProxySettingsAction
                break;
            case 0x3558: obj = new com.calix.bseries.server.dbmodel.B6NewProxySettings(); // B6NewProxySettings
            	break;
            default:
                // spit out error message
        }
        if (obj != null)
            obj.setTlvType(tlvType);
        return obj;
    } //-- CMSObject getCMSObjectForTLV(int, TLV, SwVersionNo) 

    /**
     * Method getClassForType
     * 
     * @param name
     */
    public Class getClassForType(String name)
    {
        String className = null;
        if (map != null)
            className = (String)map.get(name);
        try {
            if (className != null)
                return Class.forName(className, false, this.getClass().getClassLoader());
        } catch (Exception ignore) {}
        return null;
    } //-- Class getClassForType(String) 

    /**
     * Method getDeviceType
     */
    public int getDeviceType()
    {
        return DeviceClassMap.DEVICE_MODULE_BSERIES;
    } //-- int getDeviceType() 

    /**
     * Method getInstance
     */
    public static BseriesClassMap getInstance()
    {
        if (instance == null) {
            synchronized(BseriesClassMap.class) {
                if (instance == null) {
                    instance = new BseriesClassMap();
                }
            }
        }
        return instance;
    } //-- BseriesClassMap getInstance() 

    /**
     * Method isObjectTypeVersionSupported
     * 
     * @param tlvType
     * @param version
     */
    public boolean isObjectTypeVersionSupported(int tlvType, SwVersionNo version)
    {
        switch (tlvType) {
        }
        return true;
    } //-- boolean isObjectTypeVersionSupported(int, SwVersionNo) 

}
