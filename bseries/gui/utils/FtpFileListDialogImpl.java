package com.calix.bseries.gui.utils;

import org.apache.log4j.Logger;

import bsh.This;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.panels.layout.ftp.FtpFileListDialog;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.model.CalixCit;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.util.gui.FtpFileListDialogProxy;

import java.awt.Dialog;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jawang
 * 
 */
public class FtpFileListDialogImpl implements FtpFileListDialogProxy {

    
    private static Logger logger = Logger.getLogger(FtpFileListDialogImpl.class);
    private FtpFileListDialog dialog = null;
//    private static FtpFileListDialogImpl instance = new FtpFileListDialogImpl();

//    public static FtpFileListDialogImpl getInstance() {
//        return instance;
//    }

    /**
     * Get FTPOption 
     * @return
     * @Author: jawang
     * @Date: May 2, 2012
     */
    private OSBaseObject getEMSBARFtpInfo() {
        IDatabase idb = CalixCit.getCalixCitInstance().getDatabase();
        boolean isActive = false;
        OSBaseObject rtn = null;
        
        try {
            if (!idb.isActive()) {
                isActive = false;
                idb.begin();
            }
            
            EMSRoot root = EMSInit.getReadonlyEMSRoot(idb);
            if (root != null) {
                rtn = (OSBaseObject) root.getAttributeValue(FtpFileListDialogProxy.FTPOPTION);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            Code.warning(e);
        } finally {
            if (!isActive) {
                try {
                    // Keep idb status just as the status before this time. see
                    // EMSInt.
                    idb.rollback();
                } catch (Exception ex) {
                    Code.warning(ex);
                }
            }
        }
        return rtn;
    }
    /**
     * Get FTPOption 
     * @return
     * @Author: jawang
     * @Date: May 2, 2012
     */
    private OSBaseObject getAESettingsFtpInfo() {
        IDatabase idb = CalixCit.getCalixCitInstance().getDatabase();
        boolean isActive = false;
        OSBaseObject rtn = null;
        
        try {
            if (!idb.isActive()) {
                isActive = false;
                idb.begin();
            }
            
            EMSRoot root = EMSInit.getReadonlyEMSRoot(idb);
            if (root != null) {
                rtn = (OSBaseObject) root.getAttributeValue("AeSettings");
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            Code.warning(e);
        } finally {
            if (!isActive) {
                try {
                    // Keep idb status just as the status before this time. see
                    // EMSInt.
                    idb.rollback();
                } catch (Exception ex) {
                    Code.warning(ex);
                }
            }
        }
        return rtn;
    }
    public boolean CreateTFtpFileListDialog(Dialog dialog) {
    	OSBaseObject tftpOptions = getAESettingsFtpInfo();
        String host = null;
        String user = null;
        String passwd = null;
        
        if (tftpOptions != null) {
            try {

                IValue value = tftpOptions.getAttributeValue("PrimaryFtpIP");
                if (value != null)
                    host = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("TFTP host is Null.");
                    return false;
                }

                value = tftpOptions.getAttributeValue("PrimaryFtpUser");
                if (value != null)
                    user = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("TFTP user is Null.");
                    return false;
                }

                value = tftpOptions.getAttributeValue("PrimaryFtpPwd");
                if (value != null)
                    passwd = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("TFTP passwd is Null.");
                    return false;
                }

            } catch (MappingException e) {
                Code.warning("Unable to get the default ftp user info", e);
                return false;
            }
        } else {
            logger.error("FTP Option is Null.");
            return false;
        }

        this.dialog = new FtpFileListDialog(dialog, true);
        // set host/user/password
        this.dialog.setHost(host);
        this.dialog.setUser(user);
        this.dialog.setPassword(passwd);
        this.dialog.setDisplayFile(true);
        this.dialog.setVisible(true);
        return true;
    }
    /**
     * Create FTP file list dialog based on CMS package
     */
    public boolean CreateFtpFileListDialog(Dialog dialog) {

        OSBaseObject ftpOptions = getEMSBARFtpInfo();
        String host = null;
        String user = null;
        String passwd = null;
        
        if (ftpOptions != null) {
            try {

                IValue value = ftpOptions.getAttributeValue("Host");
                if (value != null)
                    host = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("FTP host is Null.");
                    return false;
                }

                value = ftpOptions.getAttributeValue("UserName");
                if (value != null)
                    user = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("FTP user is Null.");
                    return false;
                }

                value = ftpOptions.getAttributeValue("Password");
                if (value != null)
                    passwd = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("FTP passwd is Null.");
                    return false;
                }

            } catch (MappingException e) {
                Code.warning("Unable to get the default ftp user info", e);
                return false;
            }
        } else {
            logger.error("FTP Option is Null.");
            return false;
        }

        this.dialog = new FtpFileListDialog(dialog, true);
        // set host/user/password
        this.dialog.setHost(host);
        this.dialog.setUser(user);
        this.dialog.setPassword(passwd);
        this.dialog.setDisplayFile(true);
        this.dialog.setVisible(true);
        return true;
    }

    public boolean isOKPressed() {
        return this.dialog.isOKPressed();
    }
    
    public String getReturnPath() {
        return this.dialog.getReturnPath();
    }

	@Override
	public Map getftpServerInfo() {
		// TODO Auto-generated method stub
        OSBaseObject ftpOptions = getEMSBARFtpInfo();
        String host = null;
        String user = null;
        String passwd = null;
        Map ftpServerInfoMap = new HashMap<String, String>();
        
        if (ftpOptions != null) {
            try {

                IValue value = ftpOptions.getAttributeValue("Host");
                if (value != null)
                    host = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("FTP host is Null.");
                }

                value = ftpOptions.getAttributeValue("UserName");
                if (value != null)
                    user = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("FTP user is Null.");
                }

                value = ftpOptions.getAttributeValue("Password");
                if (value != null)
                    passwd = (String) value.convertTo(String.class, "EMS");
                else {
                    logger.error("FTP passwd is Null.");
                }
                
        		ftpServerInfoMap.put("Host", host);
        		ftpServerInfoMap.put("User", user);
        		ftpServerInfoMap.put("Password", passwd);

            } catch (MappingException e) {
                Code.warning("Unable to get the default ftp user info", e);
            }
        } else {
            logger.error("FTP Option is Null.");
        }
		return ftpServerInfoMap;
	}
    
}
