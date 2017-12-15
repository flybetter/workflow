package com.calix.bseries.server.rangediscovery;

import java.io.IOException;

import org.snmp4j.smi.OID;

import java.util.Map;

import com.calix.bseries.server.dbmodel.B6Settings;
import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.database.ICMSDatabase;
import com.calix.ems.exception.EMSException;
import com.calix.system.common.log.Log;
import com.calix.system.common.protocol.tlv.ResultCode;
import com.calix.system.server.dbmodel.GenericCMSAid;
import com.calix.system.server.dbmodel.SystemTlvConstants;
import com.calix.system.server.rangediscovery.RangeDiscoveryHandlerA;

public class B6RangeDiscoveryHandler extends RangeDiscoveryHandlerA {
	
	private static String SYSNAMEOID = ".1.3.6.1.2.1.1.5.0";
	
    @Override
    public void process(String ip, String sysDesc) throws Exception {
        ICMSDatabase database = null;
        DbTransaction txn = null;
        try {
            database = DatabaseManager.getCMSDatabase();
            txn = database.getReadTransaction();
            txn.begin();
            B6Settings b6setting = (B6Settings)database.readSingletonCMSObject(B6Settings.TYPE_NAME, txn);
            
            CalixB6Device calixB6Device = new CalixB6Device();
            calixB6Device.setDbIdentity("NTWK-" + getB6DeviceName(ip));
            calixB6Device.setIPAddress1(ip);
            if(getCommunity() != null){
                calixB6Device.setReadCommunity(getCommunity());            	
            }else{
                calixB6Device.setReadCommunity(b6setting.getreadCommunity());
            }
            calixB6Device.setWriteCommunity(b6setting.getwriteCommunity());
            calixB6Device.setNetworkLoginName(b6setting.getCliUsername());
            calixB6Device.setNetworkLoginPassword(b6setting.getCliPassword());
            calixB6Device.setWebUsername(b6setting.getWebUsername());
            calixB6Device.setWebPassword(b6setting.getWebPassword());
            calixB6Device.setEnablePassword(b6setting.getEnablePassword());
            calixB6Device.setRegion("REG-autodiscovered");
            calixB6Device.setprofile(new GenericCMSAid(SystemTlvConstants.tSNMPDeviceProfileAid, "51"));
            calixB6Device.setAutoConnect(1);
            // CMS-9837  set default time zone as GMT -8, refer to C7's implementation.
            calixB6Device.setTimeZone(420);
            // CMS-5339 set default position info to B6 networks.
            calixB6Device.setXOffset(Integer.valueOf(100));
            calixB6Device.setYOffset(Integer.valueOf(100));
            calixB6Device.setWidth(Integer.valueOf(100));
            calixB6Device.setHeight(Integer.valueOf(50));
            
            // CMS-10035, set default value is 1 for new discovered B6
            if(getAutoChassis()!=null){
                calixB6Device.setkeepChassis(getAutoChassis());             	
            }else{
                calixB6Device.setkeepChassis(new Integer(1)); 
            }
            
            calixB6Device.doCreate(txn);
            txn.commit();
            
        }  finally{
            if (txn != null && txn.isActive())
                txn.abort();
        }
    }
    
    private String getB6DeviceName(String deviceIp) throws EMSException {
    	String sysName = null;
    	SimpleSnmpClient client = new SimpleSnmpClient(deviceIp);
        try {
			client.start();
			if (Log.jobs().isDebugEnabled()){
				Log.jobs().debug("send SNMP Get sysName request to " + deviceIp);
			}
			if(getCommunity() != null){
				sysName = client.getAsString(new OID(SYSNAMEOID), getCommunity());				
			}else{
				sysName = client.getAsString(new OID(SYSNAMEOID));				
			}
			if (sysName == null) {
				sysName = "";
			}
			//CMS-15380
			// Replace all "-" with "_".
			/*if (sysName.indexOf("-") != -1) {
				sysName = sysName.replaceAll("-", "_");
			}*/
			// Replace all non-word character with " ".
			if (!sysName.matches("^[a-z0-9A-Z_ \\.]+$")) {
	    		// \w A word character: [a-zA-Z_0-9] 
	    		// \W A non-word character: [^\w] 
	    		sysName = sysName.replaceAll("[\\W&&[^ ]&&[^\\.||^\\-]]", " ");
	    	}
            if (Log.jobs().isDebugEnabled()){
            	Log.jobs().debug("get sysName response: " + sysName);
            }
			
		} catch (IOException e) {
			Log.jobs().error(e.getMessage(), e);
			throw new EMSException(ResultCode._RcFail_, e.getMessage());
		} catch (Exception e) {
			Log.jobs().error(e.getMessage(), e);
            throw new EMSException(ResultCode._RcFail_, e.getMessage());
        } finally{
            try {
                client.stop();
            } catch (IOException e) {
                Log.jobs().error(e.getMessage(), e);
            }
        }
    	//return sysName + "_" + deviceIp;
    	return sysName;
    }
	@Override
	public void process(String ip, Map<String, String> sysGroup)
			throws Exception {
		String sysDesc = sysGroup.get(SYSDESC_NAME);
		this.process(ip, sysDesc);
	}
	
	@Override
	public String getDeviceName(){
		return this.DEVICE_NAME_B6;
	}

}
