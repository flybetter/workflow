package com.calix.bseries.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class IPUtils {
    /**
     * @param ip
     * @return the 32-bit binary representation of ip 
     */
    public static String getAddrBin(String ip){
        StringTokenizer st = new StringTokenizer(ip, ".");
        if (st.countTokens() != 4){
            throw new RuntimeException("invalid IP address: " + ip);
        }
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens()){
            StringBuffer sb1 = new StringBuffer(Integer.toBinaryString(Integer.parseInt(st.nextToken())));
            while (sb1.length() < 8){
                sb1.insert(0, 0);
            }
            sb.append(sb1);
        }
        return sb.toString();
    }
    
    
    /**
     * @param ip
     * @param netMask
     * @return the network address of this ip
     * @throws Exception 
     */
    public static String getNetworkAddr(String ip, String netMask) throws Exception{
    	if ( !isValidIp(ip) ){
    		throw new Exception("invalid IP ddress: " + ip);
    	}
    	if ( !isValidIp(netMask) ){
    		throw new Exception("invalid IP ddress: " + netMask);
    	}
    	
        StringTokenizer ipTokens = new StringTokenizer(ip, ".");
        StringTokenizer netmaskTokens = new StringTokenizer(netMask, ".");
        StringBuffer netAddr = new StringBuffer();
        int count = 0;
        while (count++ < 3){
            netAddr.append(Integer.parseInt(ipTokens.nextToken()) & Integer.parseInt(netmaskTokens.nextToken())).append(".");
        }
        netAddr.append(Integer.parseInt(ipTokens.nextToken()) & Integer.parseInt(netmaskTokens.nextToken()));
            
        return netAddr.toString();
    }
    
    public static boolean isValidIp (String ip){
    	StringTokenizer ipTokens = new StringTokenizer(ip, ".");
        if (ipTokens.countTokens() != 4){
            return false;
        }
        
        while (ipTokens.hasMoreTokens()){
        	String token = ipTokens.nextToken();
        	int comp = -1;
        	try {
				comp = Integer.parseInt(token);
			} catch (NumberFormatException e) {
				return false;
			}
			
			if (comp < 0 || comp > 255){
				return false;
			}
        }
    	return true;
    }
    
    /**
     * @param ip
     * @return the long representation of ip, this function is the reverse of convertAddr()
     */
    public static long getAddrLong (String ip){
        String bin = getAddrBin(ip);
        return Long.parseLong(bin, 2);
    }
    
    /**
     * @param addrLong
     * @return the ip converted from long representation, this function is the reverse of getAddrLong()
     */
    public static String convertAddr(long addrLong){
        StringBuffer binStrBuffer = new StringBuffer(Long.toBinaryString(addrLong));
        while (binStrBuffer.length() < 32){
            binStrBuffer.insert(0, 0);
        }
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i + 8 <= 24; i = i + 8){
            sb.append(Integer.parseInt(binStrBuffer.substring(i, i+8),2));
            sb.append(".");
        }
        sb.append(Integer.parseInt(binStrBuffer.substring(24),2));
        return sb.toString();
    }
    
    /**
     * @param startIp, inclusive
     * @param endIp, inclusive
     * @param netmask
     * @return the ip list within the specified range [startIp, endIp]
     * @throws Exception
     *         If startIp and endIp are in the different network, Exception will be thrown
     *         If startIp is greater than endIp, Exception will be thrown
     */
    public static ArrayList<String> getIPsInRange(String startIp, String endIp, String netMask, boolean checkNetworkAddr) throws Exception{
        ArrayList<String> ipArray = new ArrayList<String>();
        
        //check if they are in the same sub-network
        if (checkNetworkAddr && !getNetworkAddr(startIp, netMask).equals(getNetworkAddr(endIp, netMask))){
            throw new Exception(startIp + " and " + endIp + " have different network address");
        }
        
        long startIpL = getAddrLong(startIp);
        long endIpL = getAddrLong(endIp);
        
        //check if endIp > startIp
        if (checkNetworkAddr && startIpL > endIpL){
            throw new Exception(startIp + " is greater than " + endIp );
        }
        
        while (startIpL <= endIpL){
            ipArray.add(convertAddr(startIpL++));
        }
        return ipArray;
    }
    
    public static ArrayList<String> getIPsInRange(String startIp, String endIp, String netMask) throws Exception{
        return getIPsInRange(startIp, endIp, netMask, false);
    }
    
    
    public  static void main(String[] args) throws Exception{
//        System.out.println(IPUtils.convertIP2Binary("10.11.43.23"));
//        System.out.print(IPUtils.convertToEightBinaryBits("10"));
//        System.out.print(IPUtils.convertToEightBinaryBits("11"));
//        System.out.print(IPUtils.convertToEightBinaryBits("42"));
//        System.out.print(IPUtils.convertToEightBinaryBits("23"));
//        System.out.println(IPUtils.getNetworkAddr("10.11.42.23", "255.255.255.0"));
//        System.out.println(IPUtils.getAddrLong("10.11.42.23"));
//        System.out.println(IPUtils.getAddrLong("10.11.42.24"));
//        System.out.println(IPUtils.convertAddr(168503831));
        System.out.println(getIPsInRange("10.11.42.1", "10.11.42.220", "255.255.255.0"));
    }
    
    
}
