package com.calix.bseries.server.task;

import java.util.HashMap;
import java.util.Vector;

import java.util.HashMap;

/**
 * BSeries Network Upgrade Response Signal in CMS
 * @author jawang
 *
 */
public class BSeriesNetworkUpgradeResponseSignal extends BSeriesTaskSignal{
    //James
    //TO be added
	    private String jobID;
	    private String ftpIp;
	    private String ftpUser;
	    private String ftpPassword;
	    private String path;
	    
	    private boolean m_ReloadDeviceFlag;
	    private boolean m_SaveConfigurationFlag;
	    private boolean m_ForceUpgradeFlag;
	    private String gponOntListToUpgrade;
	    private String gponOntUpgradeType;
	    
	    private String gponActivateAction;
	    private String gponDownloadAction;
            private HashMap gponOntObjs;
	    
	    
	    private String gponRepositoryPath;
	    public String getGponRepositoryPath() {
			return gponRepositoryPath;
		}
		public void setGponRepositoryPath(String gponRepositoryPath) {
			this.gponRepositoryPath = gponRepositoryPath;
		}

		public boolean getReloadDeviceFlag() {
            return m_ReloadDeviceFlag;
        }
        public void setReloadDeviceFlag(boolean reloadDeviceFlag) {
            this.m_ReloadDeviceFlag = reloadDeviceFlag;
        }
        public boolean getSaveConfigurationFlag() {
            return m_SaveConfigurationFlag;
        }
        public void setSaveConfigurationFlag(boolean saveConfigurationFlag) {
            this.m_SaveConfigurationFlag = saveConfigurationFlag;
        }
        public boolean getForceUpgradeFlag() {
            return m_ForceUpgradeFlag;
        }
        public void setForceUpgradeFlag(boolean forceUpgradeFlag) {
            this.m_ForceUpgradeFlag = forceUpgradeFlag;
        }
        public String getJobID() {
			return jobID;
		}
		public void setJobID(String jobID) {
			this.jobID = jobID;
		}
//		private String path;
		public String getFtpIP() {
			return ftpIp;
		}
		public void setFtpIP(String ftpIp) {
			this.ftpIp = ftpIp;
		}
		public String getFtpUser() {
			return ftpUser;
		}
		public void setFtpUser(String ftpUser) {
			this.ftpUser = ftpUser;
		}
		public String getFtpPassword() {
			return ftpPassword;
		}
		public void setFtpPassword(String ftpPassword) {
			this.ftpPassword = ftpPassword;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}

    public HashMap getGponOntObjs() {
        return gponOntObjs;
    }

    public void setGponOntObjs(HashMap gponOntObjs) {
        this.gponOntObjs = gponOntObjs;
    }
		public String getGponOntListToUpgrade() {
			return gponOntListToUpgrade;
		}
		public void setGponOntListToUpgrade(String gponOntListToUpgrade) {
			this.gponOntListToUpgrade = gponOntListToUpgrade;
		}
		public String getGponOntUpgradeType() {
				return gponOntUpgradeType;
			}
		public void setGponOntUpgradeType(String gponOntUpgradeType) {
				this.gponOntUpgradeType = gponOntUpgradeType;
			}
		 public String getGponActivateAction() {
				return gponActivateAction;
			}
		public void setGponActivateAction(String gponActivateAction) {
				this.gponActivateAction = gponActivateAction;
			}
			
		public String getGponDownloadAction() {
				return gponDownloadAction;
			}
		public void setGponDownloadAction(String gponDownloadAction) {
				this.gponDownloadAction = gponDownloadAction;
			}

}
