package com.calix.bseries.server.task;

public class BSeriesNetworkBackupResponseSignal extends BSeriesTaskSignal{
	    private String jobID;
	    private String ip;
	    private String ftpUser;
	    private String ftpPassword;
	    public String getJobID() {
			return jobID;
		}
		public void setJobID(String jobID) {
			this.jobID = jobID;
		}
		private String path;
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
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

}
