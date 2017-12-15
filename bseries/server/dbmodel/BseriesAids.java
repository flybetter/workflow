/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

/**
 * Class BseriesAids.
 * 
 * @version $Revision$ $Date$
 */
public class BseriesAids extends com.calix.system.server.dbmodel.DeviceAidMap {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field instance
     */
    private static BseriesAids instance;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Based on TLV type integer and name (some TLV type share the
     * same integer), to reurn the address compress mask
     * 
     * @param aidTlvType
     */
    public byte[] getAddressCompressMask(int aidTlvType)
    {
        switch (aidTlvType) {
        }
        return null;
    } //-- byte[] getAddressCompressMask(int) 

    /**
     * Based on TLV type integer and name (some TLV type share the
     * same integer), to reurn the address range TLV
     * 
     * @param objectTlvType
     * @param typeName
     */
    public byte[] getAddressRangeTLV(int objectTlvType, String typeName)
    {
        byte[] retBytes = null;
        switch (objectTlvType) {
        case 13234:  //EMSBseriesConnAction
           retBytes = new byte[]{0,74,0,12,0,16,0,8,15,113,0,4};
           break;
        case 13236:  //EMSBseriesRangeDiscoveryAction
           retBytes = new byte[]{0,74,0,12,0,16,0,8,15,113,0,4};
           break;
        case 13515:  //CCLConnectionAction
            retBytes = new byte[]{0,74,0,12,0,16,0,8,15,113,0,4};
            break;
        case 13516:  //BseriesNotificationObject
            retBytes = new byte[]{0,74,0,12,0,16,0,8,15,113,0,4};
            break;
        }
        return retBytes;
    } //-- byte[] getAddressRangeTLV(int, String) 

    /**
     * Method getBodyBytes
     * 
     * @param objectTlvType
     * @param strAid
     * @param aidTlvType
     */
    public byte[] getBodyBytes(int objectTlvType, int aidTlvType, String strAid)
    {
        byte[] bodyBytes = null;
        switch (aidTlvType) {
             case 0x0E78:
                  //EMSAid
                  bodyBytes = strAid.getBytes();
                  break;
             case 0x0F71:
                  //tNetwork-Aid
                  bodyBytes = new byte[0];
                  break;
             case 0x20BE:
                  //tSNMPDeviceProfile-Aid
                  bodyBytes = new byte[4];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// SNMPDeviceProfileNumber
                  break;
             case 0x20C5:
                  //tUnknownAid
                  bodyBytes = strAid.getBytes();
                  break;
             case 0x265B:
                  //tEx-VLANAid
                  bodyBytes = new byte[4];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-VlanId
                  break;
             case 0x34F4:
                  //tB6-GEPortAid
                  bodyBytes = new byte[0];
                  break;

             case 0x3B45:
                  //tEx-EthOamPmProfAid
                  bodyBytes = new byte[4];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-EthOamPmProfId
                  break;
             case 0x3B46:
                  //tEx-EthOamMepAid
                  bodyBytes = new byte[8];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-EthOamMegId
                  writeBytes(bodyBytes, 4, strAid, 1, CalixU32);// sEx-EthOamMepId
                  break;
             case 0x3B48:
                  //tEx-EthOamMegAid
                  bodyBytes = new byte[4];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-EthOamMegId
                  break;
             case 0x3B4F:
                  //tEx-EthOamMipAid
                  bodyBytes = new byte[8];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-EthOamMegId
                  writeBytes(bodyBytes, 4, strAid, 1, CalixU32);// sEx-EthOamMipId
                  break;
             case 0x3B52:
                  //tEx-EthOamRemoteMepAid
                  bodyBytes = new byte[8];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-EthOamMegId
                  writeBytes(bodyBytes, 4, strAid, 1, CalixU32);// sEx-EthOamRemoteMepId
                  break;
             case 0x3B54:
                  //tEx-EthOamCfgAid
                  bodyBytes = new byte[4];
                  writeBytes(bodyBytes, 0, strAid, 0, CalixU32);// sEx-EthOamCfgId
                  break;
             case 0x39A4:
             	  //BSeriesAid 
            	  if(strAid!= null && strAid.length()>8){
            		  strAid = strAid.substring(strAid.length()-8);
            	  }
                  bodyBytes = strAid.getBytes();
            default:
            // show error message
        }
        return bodyBytes;
    } //-- byte[] getBodyBytes(int, int, String) 

    /**
     * Method getInstance
     */
    public static BseriesAids getInstance()
    {
        if (instance == null) {
            synchronized(BseriesAids.class) {
                if (instance == null) {
                    instance = new BseriesAids();
                }
            }
        }
        return instance;
    } //-- BseriesAids getInstance() 

    /**
     * Returns the minimum accepted value of the last Aid
     * component.
     * 
     * @param aidTlvType
     */
    public int getRangeMin(int aidTlvType)
    {
        return getRangeMin(aidTlvType, Integer.MAX_VALUE);
    } //-- int getRangeMin(int) 

    /**
     * Returns the minimum accepted value of the given Aid
     * component
     * 
     * @param aidTlvType
     * @param compIndex
     */
    public int getRangeMin(int aidTlvType, int compIndex)
    {
        switch (aidTlvType) {
            case 0x20BE:  //tSNMPDeviceProfile-Aid
                //NetworkId-SNMPDeviceProfileNumber
                return 1;
            case 0x3B45:  //tEx-EthOamPmProfAid
                //NetworkId-sEx-EthOamPmProfId
                return 1;
            case 0x3B46:  //tEx-EthOamMepAid
                //NetworkId-sEx-EthOamMegId-sEx-EthOamMepId
                return 1;
            case 0x3B48:  //tEx-EthOamMegAid
                //NetworkId-sEx-EthOamMegId
                return 1;
            case 0x3B4F:  //tEx-EthOamMipAid
                //NetworkId-sEx-EthOamMegId-sEx-EthOamMipId
                return 1;
            case 0x3B52:  //tEx-EthOamRemoteMepAid
                //NetworkId-sEx-EthOamMegId-sEx-EthOamRemoteMepId
                return 1;
            case 0x3B54:  //tEx-EthOamCfgAid
                //NetworkId-sEx-EthOamCfgId
                return 1;
        }
        return 0;
    } //-- int getRangeMin(int, int) 

    /**
     * Method getStringAid
     * 
     * @param objectTlvType
     * @param bodyBytes
     * @param aidTlvType
     */
    public String getStringAid(int objectTlvType, int aidTlvType, byte[] bodyBytes)
    {
        int objectTypeFromAddr = -1;
        switch (aidTlvType) {
             case 0x0E78:
                  //EMSAid
                  return getTL1String(bodyBytes, 0, CalixString);// Id
             case 0x0F71:
                  //tNetwork-Aid
                  return getAIDString(new String[] {
                  });
             case 0x20BE:
                  //tSNMPDeviceProfile-Aid
                  return getTL1String(bodyBytes, 0, CalixU32);// SNMPDeviceProfileNumber
             case 0x20C5:
                  //tUnknownAid
                  return getAIDString(new String[] {
                      getTL1String(bodyBytes, 0, CalixString),// OSString
                      getTL1String(bodyBytes, 2, CalixString),// OSString
                  });
             case 0x265B:
                 //tEx-VLANAid
                 return getTL1String(bodyBytes, 0, CalixU32);// sEx-VlanId

            case 0x3B45:
                 //tEx-EthOamPmProfAid
                 return getTL1String(bodyBytes, 0, CalixU32);// sEx-EthOamPmProfId
            case 0x3B46:
                 //tEx-EthOamMepAid
                 return getAIDString(new String[] {
                     getTL1String(bodyBytes, 0, CalixU32),// sEx-EthOamMegId
                     getTL1String(bodyBytes, 4, CalixU32),// sEx-EthOamMepId
                 });
            case 0x3B48:
                 //tEx-EthOamMegAid
                 return getTL1String(bodyBytes, 0, CalixU32);// sEx-EthOamMegId
            case 0x3B4F:
                 //tEx-EthOamMipAid
                 return getAIDString(new String[] {
                     getTL1String(bodyBytes, 0, CalixU32),// sEx-EthOamMegId
                     getTL1String(bodyBytes, 4, CalixU32),// sEx-EthOamMipId
                 });
            case 0x3B52:
                 //tEx-EthOamRemoteMepAid
                 return getAIDString(new String[] {
                     getTL1String(bodyBytes, 0, CalixU32),// sEx-EthOamMegId
                     getTL1String(bodyBytes, 4, CalixU32),// sEx-EthOamRemoteMepId
                 });
            case 0x3B54:
                 //tEx-EthOamCfgAid
                 return getTL1String(bodyBytes, 0, CalixU32);// sEx-EthOamCfgId
            default:
            // show error message
        }
        return null;
    } //-- String getStringAid(int, int, byte) 

    /**
     * Method getTL1String
     * 
     * @param bytes
     * @param type
     * @param offset
     */
    public String getTL1String(byte[] bytes, int offset, int type)
    {
        return getTL1String(bytes, offset, type, null);
    } //-- String getTL1String(byte, int, int) 

    /**
     * Method writeBytes
     * 
     * @param bodyBytes
     * @param type
     * @param compIndex
     * @param aid
     * @param offset
     */
    public int writeBytes(byte[] bodyBytes, int offset, String aid, int compIndex, int type)
    {
        return writeBytes(bodyBytes, offset, aid, compIndex, type, null);
    } //-- int writeBytes(byte, int, String, int, int) 

    /**
     * Method writeBytes
     * 
     * @param bodyBytes
     * @param prefix
     * @param type
     * @param compIndex
     * @param aid
     * @param offset
     */
    public int writeBytes(byte[] bodyBytes, int offset, String aid, int compIndex, int type, String prefix)
    {
        String comp = getAidComponent(aid, compIndex);
        int written = -1;
        if (comp != null) {
            if (prefix != null && comp.startsWith(prefix) ) {
                comp = comp.substring(prefix.length());
            }
            written = 4;
            switch (type) {
                case CalixString:
                    System.arraycopy(comp.getBytes(), 0, bodyBytes, offset, comp.length());
                    written = comp.length();
                    break;
                case CalixU32:
                    copyBytes(bodyBytes, offset, comp, 4);
                    written = 4;
                    break;
                default:
                    written = writeBytesForUnknownEnums(bodyBytes, offset, comp, type);
            }
        }
        return written;
    } //-- int writeBytes(byte, int, String, int, int, String) 

    /**
     * Method getTL1String
     * 
     * @param bytes
     * @param prefix
     * @param type
     * @param offset
     */
    private String getTL1String(byte[] bytes, int offset, int type, String prefix)
    {
        if (prefix == null) {
            prefix = "";
        }
        switch (type) {
            case CalixString:
                {
                    int lastIndex = offset;
                    while((lastIndex <= (bytes.length - 1)) && (bytes[lastIndex] != 0))
                        ++lastIndex;
                    if (lastIndex <= offset)
                        return "";
                    return prefix + new String(bytes, offset, lastIndex - offset);
                }
            case CalixU32:
                return prefix + getStrValueOf(bytes, offset, CalixU32);
            default: return getTL1StringForUnknownEnums(bytes, offset, type);
        }
    } //-- String getTL1String(byte, int, int, String) 

}
