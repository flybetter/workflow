package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.model.B6Settings;
import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.bseries.gui.utils.B6GUIUtils;
import com.calix.bseries.gui.utils.B6SecurityHelper;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.EMSConfig;
import com.calix.ems.gui.EMSGuiUtils;
import com.calix.ems.gui.util.BseriesWebPanelHttpSessionKeepAliveUtil;
import com.calix.ems.gui.web.EmbeddedInventoryPanel;
import com.calix.ems.model.EMSRoot;
import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.components.controls.CalixScrollPane;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.tlv.TLVUtils;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.panels.BasePanel;
import com.objectsavvy.base.gui.panels.IParameterHolder;
import com.objectsavvy.base.gui.panels.IValuePanel;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.TransactionNotInProgressException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.TreeValue;
import com.objectsavvy.base.persistence.meta.TreeValueType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.webrenderer.swing.BrowserFactory;
import com.webrenderer.swing.IBrowserCanvas;
import com.webrenderer.swing.event.BrowserAdapter;
import com.webrenderer.swing.event.BrowserEvent;
import com.webrenderer.swing.event.NetworkAdapter;
import com.webrenderer.swing.event.NetworkEvent;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;

import javax.swing.BorderFactory;


public class BseriesCutThroughWebPanel extends BasePanel implements IValuePanel {
    private CalixScrollPane m_graphPanel = null;
    protected IBrowserCanvas m_browser = null;
    
    private String staticScript;
    private String dynamicScript=null;
    private static final String Occam_B6_AUTOLOGIN_JS_DIR = "/config/gui/bseries/scripts/";
    private static final String SKIP_BROWSER_CHECK = "browserCheckOff=1";
    private static final String PAGE_LOGIN = "login.html";
    private static final String PROXY_HTTPS_PORT_NAME = "CS_proxy_https_port";
    private final String PROXY_PORT_NAME = "CS_proxy_port";
    private static final String ATTR_PROFILE = "profile";
    public static final String ATTR_PROXY_FLAG = "ProxyFlag";

    
    public BseriesCutThroughWebPanel(){
        m_browser = getBrowser();
        m_browser.showScrollBars(true);
        m_browser.setJavascriptEnabled(true);
        m_browser.allowPopups(true);
        m_browser.disableHTTPSDialog(true);
        m_browser.enableCookies();
        m_browser.disableCache();

        m_graphPanel = new CalixScrollPane(m_browser.getComponent());
        m_graphPanel.setOpaque(false);
        m_graphPanel.getViewport().setOpaque(false);
        m_graphPanel.getViewport().setBorder(null);
        m_graphPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.add(m_graphPanel, BorderLayout.CENTER);
        this.setName("Embedded Browser");
        this.setBorder(null);

        m_browser.addBrowserListener(new BrowserAdapter() {
            @Override
            public void onBeforeNavigate(BrowserEvent browserEvent) {
                String url = browserEvent.getURL();
                if(url.contains(PAGE_LOGIN) && !url.contains(SKIP_BROWSER_CHECK)) {
                    String sep = url.contains("?") ? "&" : "?";
                    url = url + sep + SKIP_BROWSER_CHECK;
                    m_browser.loadURL(url);
                }              
                browserEvent.blockLoad();
                if(url.contains("/main.html")){
                	// The url contains main.html means login has been finished. so we can linked to exactly ethernet port if the linkback needed.
                	String b6GePortNumber = (String) EmbeddedInventoryPanel.getParamValue(EmbeddedInventoryPanel.PARAM_B6GE_PORT);
                	if(b6GePortNumber !=null && !"".equals(b6GePortNumber)&& b6GePortNumber.matches("^[0-9]+$")){
                		url = url.replace("/main.html", "/?content=/ethernet/eth_port.html?port=")+EmbeddedInventoryPanel.getParamValue(EmbeddedInventoryPanel.PARAM_B6GE_PORT);
                		Code.warning("#### B6 before Navigation in handling. URL: "+ url );
                		m_browser.loadURL(url);
                		EmbeddedInventoryPanel.setLinkBackSign(false);
                	}
                }
             }
        });
        m_browser.addNetworkListener(new NetworkAdapter() {
            @Override 
            public void onHTTPResponse(NetworkEvent e){
                if(e.getStatus()==200 && e.getURL().contains(PAGE_LOGIN)) {
                    //CMS-4358 B6 session timeout happened.
                    Code.warning("#### B6 Session timed out. Re-login using JS!:" + e.getStatusText() +";" + e.getStatus() + " for URL:" + e.getURL() );
                    if(staticScript!=null && dynamicScript!=null ) {
                        m_browser.executeScriptWithReturn(staticScript + dynamicScript);
                    }
                }
            }
        });
        try {
            staticScript = getStaticScript();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void populateDataFromFields(java.util.List pActions) throws ValueException {
        CalixCit.getCalixCitInstance().showToolbar(true);
    }

    // Begin bug 51355 fix by James Wang 20111219
    @Override
    public void populateFieldsFromData(IParameterHolder[][] params) throws ValueException {
        CalixCit.getCalixCitInstance().showToolbar(false);
        if (super.getObjects() != null && super.getObjects().size() > 0) {
            OSBaseObject localObj = (OSBaseObject) super.getObjects().iterator().next();
            EMSRoot root = EMSInit.getReadonlyEMSRoot(getPanelController().getDatabase());
            CalixB6Device ntwkObj = (CalixB6Device)root.getNetworkElement(TLVUtils.extractNetworkComponentString(localObj.getIdentityValue()));
//            CalixB6Device deviceObj = (CalixB6Device) db.load(TypeRegistry.getInstance().getRecordType(CalixB6Device.TYPE_NAME), 
//                  getEMSDeviceAid((TreeValue)ntwkObj.getIdentityValue()));
            populateFieldsFromData(ntwkObj);
        }
    }

    void populateFieldsFromData(CalixB6Device deviceObj) {
        boolean isActivited = false;
        IDatabase db = CalixCit.getCalixCitInstance().getReadOnlyDatabase();
        try {
            if (!db.isActive()) {
                db.begin();
                isActivited = true;
            }
            
            //check if it is connected, if not, disallow the cut-through and auto-login per Shiming
            if (!deviceObj.isConnected()) {
                throw new Exception("B6 is not connected yet, Please go to B6 Detail panel and connect it first.");
            }
            
            String ip = deviceObj.getIPAddress1().convertTo(String.class, DomainValues.DOMAIN_GUI);
            
            // modify the implementation of web cut-through privilege for CMS-8910 by dchen at 11/07/2013
            int privilege = B6GUIUtils.getB6Priviledge();
            
            // Get web user name and password from deviceObj which saved in table CalixB6Device.
            // If login CMS as administrator role, we use the web user name and password from the device its own.
            // If login CMS as guest role, we use the web user name and password saved in table CalixB6Device when web user of device is "Guest".
            // If login CMS as guest role, we use the web user "Guest" and default password in B6GUIUtils.java when web user of device isn't "Guest".
            // If login CMS as privileged role, we use the web user name and password saved in table ClaixB6Device when web user of device is "Privileged".
            // If login CMS as privileged role, we use the web user "Privileged" and default password in B6GUIUtils.java when web user of device is "Administrator".
            // If login CMS as privileged role, we use the web user "Guest" and default password in B6GUIUtils.java when web user of device is "Guest".
            // If web user not in "Administrator","Guest" or "Privileged", we just use the name and password saved in table CalixB6Device.
            
            String loginName = deviceObj.getWebUserName();
            String loginPwd = deviceObj.getWebPassword();
            Code.warning("Auth from B6device is username: [" + loginName + "] and password : [" + loginPwd + "].");
            
            
            
        	 boolean isguest = false;
	    	 if (B6SecurityHelper.isReadPriviledge(deviceObj) || !B6SecurityHelper.isAction()) {
	    		 isguest = true;
	    	 }
	    	 if(isguest == false){
	        	 IValue value = ((deviceObj).getAttributeValue(CalixB6Device.ATTR_MODEL));
	        	 String model = null;
	        	 if (value != null){
	        	     try {
	        	                   model = (String)value.convertTo(String.class, null);
	        	           } catch (MappingException e) {
	        	                   // TODO Auto-generated catch block
	        	                   e.printStackTrace();
	        	           }
	        	 }
	
	
	        	   if(!B6SecurityHelper.isB6write(model)){
	        	           isguest = true;
	        	           }
	    	 }

            
            
            
            if (loginName != null) {
            	
            	if (loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_ADMINISTRATOR_USERNAME) || loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_PRIVILEGED_USERNAME) || loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_GUEST_USERNAME)) {
            		
            		if (B6GUIUtils.B6_SECURITY_GUEST == privilege || isguest == true) {
            			
            			if (!loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_GUEST_USERNAME)) {
            				loginPwd = B6GUIUtils.B6_SECURITY_GUEST_PASSWD;
            				loginName = B6GUIUtils.B6_SECURITY_GUEST_USERNAME;
            			} else if(loginPwd == null){
            				Code.warning("User role is guest and the passwd is null,set it with default passwd.");
            				loginPwd = B6GUIUtils.B6_SECURITY_GUEST_PASSWD;
            			} 
            			    			
            		} else if (B6GUIUtils.B6_SECURITY_PRIVILEGED == privilege) {
            			
            			if (loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_ADMINISTRATOR_USERNAME)) {
            				loginPwd = B6GUIUtils.B6_SECURITY_PRIVILEGED_PASSWD;
            				loginName = B6GUIUtils.B6_SECURITY_PRIVILEGED_USERNAME;
            			} else if (loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_GUEST_USERNAME)){
            				loginPwd = B6GUIUtils.B6_SECURITY_GUEST_PASSWD;
            				loginName = B6GUIUtils.B6_SECURITY_GUEST_USERNAME;
            			} else if(loginPwd == null){
            				Code.warning("User role is privilege and the passwd is null,set it with default passwd.");
            				loginPwd = B6GUIUtils.B6_SECURITY_PRIVILEGED_PASSWD;
            			}
//            		} else if (B6GUIUtils.B6_SECURITY_ADMINISTRATOR == privilege){
//					    if (loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_PRIVILEGED_USERNAME)) {
//            				loginPwd = B6GUIUtils.B6_SECURITY_PRIVILEGED_PASSWD;
////            				loginName = B6GUIUtils.B6_SECURITY_PRIVILEGED_USERNAME;
//            			} else if (loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_GUEST_USERNAME)){
//            				loginPwd = B6GUIUtils.B6_SECURITY_GUEST_PASSWD;
////            				loginName = B6GUIUtils.B6_SECURITY_GUEST_USERNAME;
//            			} else if(loginPwd == null){
//            				Code.warning("User role is administrator and the passwd is null,set it with default passwd.");
//            				loginPwd = B6GUIUtils.B6_SECURITY_ADMINISTRATOR_PASSWD;
//            			}
            		}else if(loginPwd == null){
            			Code.warning("User role is adminiscrator and the passwd is null,set it with default passwd.");
            			loginPwd = B6GUIUtils.B6_SECURITY_ADMINISTRATOR_PASSWD;
            		}
            	} else {
            		Code.warning("Current web user NOT default users in B6.");
            		Code.warning("Use user [" + loginName + "] to login B6.");
            	} 	
            	
            } else {	
                OSBaseObject  b6Settings = db.load(B6Settings.getRecordType(), EMSGuiUtils.getAid(B6Settings.getRecordType(), "B6Setting"), IDatabase.ReadOnly);;
                if(b6Settings!=null){
                    loginName = b6Settings.getAttributeValue(B6Settings.ATTR_WEBUSERNAME,String.class);
                    if (loginPwd == null || loginPwd.length() == 0){
                        loginPwd = b6Settings.getAttributeValue(B6Settings.ATTR_WEBPASSWORD,String.class);
                        Code.warning("Auth from B6settings is username: [" + loginName + "] and password : [" + loginPwd + "].");
                        if(loginPwd == null){
                        	Code.warning("Get b6settings, but the password is null, set it with the default value");
                        	if(loginName != null){
                        		if(loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_GUEST_USERNAME)){
                        			loginPwd = B6GUIUtils.B6_SECURITY_GUEST_PASSWD;
                        		}
                        		if(loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_PRIVILEGED_USERNAME)){
                        			loginPwd = B6GUIUtils.B6_SECURITY_PRIVILEGED_PASSWD;
                        		}
                        		if(loginName.equalsIgnoreCase(B6GUIUtils.B6_SECURITY_ADMINISTRATOR_USERNAME)){
                        			loginPwd = B6GUIUtils.B6_SECURITY_ADMINISTRATOR_PASSWD;
                        		}
                        	} else {
                        		Code.warning("The username is null or it is non of the default users in B6.");
                        	}
                        }
                    }             	
                }else{
            		Code.warning("Can not get b6setting. Please check your B6 setting. ");
                }
            }
            
            Code.warning("Web use is [" + loginName + "] and password is [" + loginPwd + "].");
            // end CMS-8910 by dchen at 11/07/2013
            
            String level = convertName2Level(loginName);
            
            StringBuffer urlStringBuffer = null;
            StringBuffer urlStringForKeepAlive = null;
            //Pass read only db
            if (isByProxy(deviceObj, db)){
                urlStringBuffer = new StringBuffer(getProxyProtocol());
                urlStringBuffer.append(EMSConfig.getLoginInfo().getServer());
                urlStringBuffer.append(":");
                urlStringBuffer.append(getProxyPort());
                urlStringBuffer.append("/main_init.html/?pxip=");
                urlStringBuffer.append(ip);
                if (isDeviceUseHttps(deviceObj)){
                    urlStringBuffer.append("&isSecure=true");
                }
                dynamicScript = getDymanicScript(ip,level,loginPwd, true);
            }else {
                if (isDeviceUseHttps(deviceObj)){
                    urlStringBuffer = new StringBuffer("https://");
                }else {
                    urlStringBuffer = new StringBuffer("http://");
                }
                urlStringBuffer.append(ip);
                urlStringBuffer.append("/main_init.html");
                dynamicScript = getDymanicScript(ip,level,loginPwd, false);
            }
            urlStringForKeepAlive = new StringBuffer(urlStringBuffer.toString());
            int start = urlStringForKeepAlive.indexOf("main_init.html");
            urlStringForKeepAlive.replace(start, start+14, "main.html");
            Code.warning("urlStringForKeepAlive is : " + urlStringForKeepAlive.toString());
            Code.warning("url:" + urlStringBuffer.toString());
            m_browser.loadURL(urlStringBuffer.toString());
            BseriesWebPanelHttpSessionKeepAliveUtil.getInstance().registerWebPanel(m_browser, urlStringForKeepAlive.toString());
            Thread.currentThread().sleep(2000);
            Code.warning("dynamic JavaScript: " + dynamicScript);
            m_browser.executeScriptWithReturn(staticScript + dynamicScript); 
        }  catch (Exception e) {
            Code.warning(e);
            CalixMessageUtils.showErrorMessage("Unable to launch browser. \n" + e.getMessage());
        } finally {
            if (isActivited) {
                try {
                    db.rollback();
                } catch (TransactionNotInProgressException e) {
                    Code.warning(e);
                }
            }
        }
    }
    // End bug 51355 fix by James Wang 20111219
    
    public void setObjects(Collection<? extends Object> pObjects) {
        super.setObjects(pObjects);
    }
    
    private TreeValue getEMSDeviceAid(TreeValue networkAid){
        TreeValue val = null;
        TreeValueType treeType = TypeRegistry.getInstance().getTreeType("EMSAid");
        try {
            val = (TreeValue) treeType.convertFrom(new String[]{"CMS", networkAid.getComponent("networkId").convertTo(String.class, null)}, null);
        } catch (MappingException e) {
            e.printStackTrace();
        }
        return val;
    }

    private String getDymanicScript(String ip, String username, String password, boolean byProxy) {
        if (byProxy){
            return "submitLogin(\"/auth\" ,\""+ username +"\", \""+ password +"\");\n";
        }else {
            if(EMSConfig.getLoginInfo().getSecure()){
                return "submitLogin(\"https://"+ ip + "/auth\" ,\""+ username +"\", \""+ password +"\");\n";
            }else {
                return "submitLogin(\"http://"+ ip + "/auth\" ,\""+ username +"\", \""+ password +"\");\n";
            }
        }
    }
    
    private String getStaticScript() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(loadFile(Occam_B6_AUTOLOGIN_JS_DIR + "login.js"));
        sb.append(loadFile(Occam_B6_AUTOLOGIN_JS_DIR + "cookies.js"));
        sb.append(loadFile(Occam_B6_AUTOLOGIN_JS_DIR + "MochiKit.js"));
        return sb.toString();
    }

    private String loadFile(String fileName) throws Exception {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                sb.append(line);
                sb.append("\n");
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }
        return sb.toString();
    }
    
    private String convertName2Level(String loginName){
        String level = null;
        if ("Guest".equals(loginName)){
            level="0";
        }else if ("Priviledged".equals(loginName)){
            level = "1";
        }else if ("Administrator".equals(loginName)){
            level = "2";
        }
        return level;
    }
    
    // Begin bug 51355 fix by James Wang 20111213
    //Use read only db
    private boolean isByProxy(BaseEMSDevice ntwkObj, IDatabase db) {
        boolean isProxy = true;
        EMSRoot root = EMSInit.getReadonlyEMSRoot(db);
        OSBaseObject profileObj = root.getInternalSNMPProfile(ntwkObj.getAttributeValue(ATTR_PROFILE));
        if (profileObj != null) {
            Object proxyFlag = profileObj.getAttributeValue(ATTR_PROXY_FLAG, String.class, null);
            if (proxyFlag == null || "0".equals(proxyFlag)) {
                isProxy = false;
            }
        }
        return isProxy;
    }
    
    public void destoryBrowser(){
        if (m_browser != null){
            BrowserFactory.destroyBrowser(m_browser);
            BseriesWebPanelHttpSessionKeepAliveUtil.getInstance().unregisterWebPanel(m_browser);
        }
        m_browser = null;
    }
    // End bug 51355 fix by James Wang 20111213
    
    private String getProxyProtocol(){
        if(EMSConfig.getLoginInfo().getSecure()){
            return "https://";
        }else {
            return "http://";
        }
    }
    
    private String getProxyPort(){
        if(EMSConfig.getLoginInfo().getSecure()){
            return System.getProperty(PROXY_HTTPS_PORT_NAME, "28443");
        }else {
            return System.getProperty(PROXY_PORT_NAME, "28080");
        }
    }
    
    private boolean isDeviceUseHttps(BaseEMSDevice ntwkObj){
        return true;
    }
    
    protected IBrowserCanvas getBrowser(){
        return BrowserFactory.spawnMozilla();
    }
    
    public static void main(String[] args){
        BseriesCutThroughWebPanel occamViewThirdPartyWebPanel = new BseriesCutThroughWebPanel();
        System.out.println(occamViewThirdPartyWebPanel.getDymanicScript("10.10.10.1", "abc", "123", true));
    }
}
