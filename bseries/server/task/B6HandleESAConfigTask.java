package com.calix.bseries.server.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.adventnet.management.transaction.ConnectionPool;
import com.calix.ems.jms.JMSUtilities;
import com.calix.ems.scheduled.ScheduleConstants;
import com.calix.ems.server.process.CMSProcess;

import com.occam.ems.be.app.configuration.ConfigServerMainBE;
import com.occam.ems.be.app.configuration.DBHandler;
import com.occam.ems.be.app.fault.PlaceHolderDefines;
import com.occam.ems.be.app.security.AuthenticationUtil;
import com.occam.ems.be.mo.Authentication;
import com.occam.ems.be.util.BEUtil;
import com.occam.ems.be.util.ESAUpdateUtility;
import com.occam.ems.common.defines.OccamStaticDef;
import com.occam.ems.common.mo.servicemanagement.ESADomainProps;
import com.occam.ems.common.proxy.util.PureUtils;
import com.occam.ems.common.util.OccamUtils;
import com.occam.ems.common.util.servicemanagement.ServiceMgmtDBConstants;
import com.occam.ems.server.DataBaseAPI;
import com.occam.ems.server.ObjectAlreadyPresentException;
import com.occam.ems.server.UnableToDeleteException;



public class B6HandleESAConfigTask extends AbstractBSeriesTask {

    private static final Logger logger = Logger.getLogger(B6HandleESAConfigTask.class);
    private final String DEFAULT_HTTP_AUTHENTICATION = "razor123";
    private final String DEFAULT_HTTP_ACCESSLEVEL = "2";
    private String operationResult = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
    private String taskName=null;
    private String taskId=null;
    private String esaHostIp=null;
    private String esaSwitchLoginName=null;
    private String esaSwitchLoginPass=null;
    private String esaDbFileName=null;
    private File esaDBfileObj =null;
    private File LogFileObj =null;
    private String sTaskDirectoryPath;
    private String domainMapFileAbsPath;
    private boolean isEsaReganerate = false;
    private StringBuffer resultMessage = new StringBuffer();
    private HashMap esaDomainPropsMap=null;
    private int domainId = 1;
    private String sSwitchDBFilePath = null;
	private String httpAccessLevel = null;
	private String ftpUserName = null;
	private String ftpUserPassword = null;
	private String httpsAuthentication = null;
	private String domainIdStr = null;
	private List propList = new ArrayList();
	private Process proc = null;
	private String sDebug;
	private Properties detailProperties = new Properties();
	
	
    public B6HandleESAConfigTask(BSeriesTaskSignal signal){
    	BSeriesESAConfigSignal esaSignal = (BSeriesESAConfigSignal)signal;
    	taskName = esaSignal.getTaskName();
    	taskId = esaSignal.getTaskId();
    	esaHostIp= esaSignal.getEsaHostIp();
    	esaSwitchLoginName= esaSignal.getEsaSwitchLoginName();
    	esaSwitchLoginPass= esaSignal.getEsaSwitchLoginPass();
    	esaDbFileName = esaSignal.getEsaDbFileName();
    	isEsaReganerate = esaSignal.isEsaReganerate();
    	esaDomainPropsMap = esaSignal.getEsaDomainPropsMap();
    	httpsAuthentication = esaSignal.getHttpsAuthentication();
    	httpAccessLevel =String.valueOf(esaSignal.getAccessLevel()) ;
    	logger.info("Http Access Level : "+httpAccessLevel);
    	sSwitchDBFilePath = ESAUpdateUtility.getInstance().getPropsResourceBundle().getString("METASWITCH_DB_REMOTE_FILE_PATH");
    	logger.info("Switch DB file path : "+sSwitchDBFilePath);
    	ftpUserName = ESAUpdateUtility.getInstance().getPropsResourceBundle().getString("FTP_USER_LOGIN_NAME");
    	logger.info("FTP user name:"+ftpUserName);
    	ftpUserPassword = ESAUpdateUtility.getInstance().getPropsResourceBundle().getString("FTP_USER_PASSWORD");
    	logger.info("FTP user password:"+ftpUserPassword);
    	sDebug = ESAUpdateUtility.getInstance().getPropsResourceBundle().getString("DEBUG");
    	logger.info("Debug Mode  :"+sDebug);
    }
    
	@Override
	protected String getOperationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BSeriesTaskSignal getResponseSignal() {
		BseriesESAConfigRespSignal respSignal = new BseriesESAConfigRespSignal();
		respSignal.setTaskName(taskName);
		respSignal.setTaskId(taskId);
		return respSignal;
	}
	@Override
	public void execute() {
		if(!validateProperties()){
			return;
		}
		logger.info("SchedulerTask.setProperties() : Group Name is ESA Configuration");
		//String sLogFilePath = ESAUpdateUtility.getInstance().getPropsResourceBundle().getString("LOG_FILE_PATH");
		String sdomainMapFilePath = PureUtils.rootDir+File.separator+"occamViewEMS"+File.separator+"esa"+File.separator+
									ESAUpdateUtility.getTaskDirName(taskName)+File.separator+OccamStaticDef.DOMAIN_MAP_FILENAME;
		logger.info("SchedulerTask.setProperties() : ESA Domain Map File Path :: "+sdomainMapFilePath);
		ESAUpdateUtility.getInstance().deleteDomainMapFile(sdomainMapFilePath);
	//	deleteESADomainProps(taskName);
		addESADomainProps(esaDomainPropsMap,taskName);


    	String sTaskDirectoryPath = PureUtils.rootDir+File.separator+"occamViewEMS"+File.separator+"esa"+File.separator+ESAUpdateUtility.getTaskDirName(taskName)+File.separator;
    	File tmpEsaDBfileObj = new File(sTaskDirectoryPath);
    	tmpEsaDBfileObj.mkdirs();
		File esaDBfileObj = new File(tmpEsaDBfileObj,OccamStaticDef.ESA_DB_FILENAME);	
		File LogFile = new File(tmpEsaDBfileObj,ESAUpdateUtility.getTaskDirName(taskName)+".log");
    	//if the esa Db file doesnt exists, then download the DB to the file from Metaswitch
		boolean result1  = true;
		String sCommand;
		try
		{
//			StringBuffer resultMsg = new StringBuffer(); 
//			String msg = "";
			//fix for bug 1352
			/*
			if(!esaDBfileObj.exists())
			{
				sCommand = esaConfigProvider.buildDbDownloadCommand(BaseProps,esaDBfileObj,LogFile);
				logger.debug("ScheduleTask : executeAction : DB Download command(1) : "+sCommand);
				result1 = esaConfigProvider.executeCommand(sCommand);
				msg = esaConfigProvider.getResultMessage().toString();
			}//else if(esaDBfileObj.exists() && regenDB.equalsIgnoreCase("true"))//if esa DB file exist but the user selected to regenerate the DB
			else if(regenDB.equalsIgnoreCase("true"))//if esa DB file exist but the user selected to regenerate the DB
			{
				sCommand = esaConfigProvider.buildDbDownloadCommand(BaseProps,esaDBfileObj,LogFile);
				logger.debug("ScheduleTask : executeAction : DB Download command(2) : "+sCommand);
				result1 = esaConfigProvider.executeCommand(sCommand);
				msg = esaConfigProvider.getResultMessage().toString();
			}
			*/
			sCommand = buildDbDownloadCommand(esaDBfileObj,LogFile);
			logger.info("ScheduleTask : executeAction : DB Download command : "+sCommand);
			result1 = executeCommand(sCommand);
			if(result1){
		    	Set keys = esaDomainPropsMap.keySet();
		    	Iterator it = keys.iterator();
		    	Properties domainProp;
		    	HashMap detailMap = new HashMap();
		    	int succeedCount = 0;
		    	while(it.hasNext()){
		    		domainProp = (Properties)esaDomainPropsMap.get(it.next());
		    		logger.info("SchedulerTask.addESADomainProps() : ESA Domain Properties : "+domainProp.toString());
		    		String deviceName = (String)domainProp.get(OccamStaticDef.ESA_BLC);
		    		boolean isSuccess = configureDevice(taskName,deviceName);
		    		//temp solution, encouter error, quit.
		    		Properties resultPro = new Properties();
		    		if(!isSuccess){
		    			resultPro.setProperty(ScheduleConstants.ESA_RESULT_SINGALE, ScheduleConstants.JOB_STATUS_FAILED);
		    		}else{
		    			succeedCount++;
		    			resultPro.setProperty(ScheduleConstants.ESA_RESULT_SINGALE, ScheduleConstants.JOB_STATUS_SUCCESSFUL);
		    		}
		    		resultPro.setProperty(ScheduleConstants.ESA_DETAILS_SINGLE, resultMessage.toString());
		    		detailMap.put(deviceName, resultPro);
		    	}
		    	detailProperties.put(ScheduleConstants.ESA_DETAILS_MAP, detailMap);
		    	detailProperties.setProperty(ScheduleConstants.ESA_SUCCEED_COUNT, String.valueOf(succeedCount));
		    	if(succeedCount == 0 ){
		    		operationResult = ScheduleConstants.JOB_STATUS_FAILED;
		    	}else if(succeedCount == keys.size()){
		    		operationResult = ScheduleConstants.JOB_STATUS_SUCCESSFUL;
		    	}else{
		    		operationResult = ScheduleConstants.JOB_STATUS_PARTIAL;
		    	}	
			}else{
					operationResult = ScheduleConstants.JOB_STATUS_FAILED;
					detailProperties.setProperty(ScheduleConstants.ESA_DETAILS, resultMessage.toString());
				}
			detailProperties.setProperty(ScheduleConstants.ESA_RESULT, operationResult);
		}
		catch(Exception e)
		{
			logger.error("Exception while executing the DB Regeneartion command",e);
		}
    
		
	}
	
	
	public boolean configureDevice(String taskName,  String deviceName) {
		logger.info("ESAUpdateConfigProvider : ESA Domain Configuration : Configuration execution started for the Task " 
				+taskName+" on Device "+deviceName);
		try{
			Thread.sleep(5000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		String sCommand;
		boolean result1 = true;
		boolean result2 = false;
		String domainName = "";
		
		resultMessage.replace(0,resultMessage.length(),"");
		
		try{

			List propList = getDomainProperties(deviceName,taskName);
			if(propList == null||propList.size() == 0){
				return false;
			}
			ESADomainProps esaDomainProps = (ESADomainProps)propList.get(0);
			domainName = esaDomainProps.getDomainName();
			logger.debug("ESAUpdateConfigProvider : configureDevice : Domain name "+domainName);
			/** checking for the device (simulator (or)  occam device)**/
			boolean isSimulator = DBHandler.getInstance().isSimulatorDevice(deviceName);
			/**checking whether the task is aborted  **/
			logger.debug("ESAUpdateConfigProvider : configureDevice : aborted policy list .. :"+ConfigServerMainBE.abortTaskList);
			if(ConfigServerMainBE.abortTaskList.contains(taskName)){
				logger.debug("ESAUpdateConfigProvider : configureDevice : aborted the task "+taskName+" for the device "+deviceName);
				resultMessage.append("Aborted, as the user selected to abort the ESA Configuration task");
				/** for the occam devices only abort event has to be generated **/
				if(!(isSimulator)){
					//OccamConfigEventGenerator.emitEvent(device.getDeviceName(),OccamStaticDef.ESA_CONFIG_TASK_ABORTED,resultMessage.toString());
					Properties evtProps = new Properties();
					evtProps.setProperty(PlaceHolderDefines.TASK_NAME, taskName);
					evtProps.setProperty(PlaceHolderDefines.ESA_DOMAIN_NAME, domainName);
					evtProps.setProperty(PlaceHolderDefines.ESA_BLC, deviceName);
					BEUtil.emitEvent(deviceName,OccamStaticDef.ESA_CONFIG_TASK_ABORTED, 
									OccamStaticDef.ESA_CONFIG_TASK_TRAPOID, null, OccamStaticDef.INVALID_INT, 
									resultMessage.toString(), null, evtProps);
				}
				resultMessage.append("ESA configuraiton task :"+taskName+"abord");
				return false;
			}
			
			
			sTaskDirectoryPath = PureUtils.rootDir+File.separator+"occamViewEMS"+File.separator+"esa"+File.separator+ESAUpdateUtility.getTaskDirName(taskName)+File.separator;
			esaDBfileObj = new File(sTaskDirectoryPath+OccamStaticDef.ESA_DB_FILENAME);		
			LogFileObj = new File(sTaskDirectoryPath+ESAUpdateUtility.getTaskDirName(taskName)+".log");
			//String tempStr = null;
//			for(int i = 0; i < attribute.length; i++){	
//				logger.debug("ESAUpdateConfigProvider : configureDevice : attribute["+i+"] "+attribute[i]);
//				taskAttribute = attribute[i];							
//			}
//			Properties taskProperties = taskAttribute.getProperties();
			//logger.debug("ESAUpdateConfigProvider : configureDevice : Task Properties : "+taskProperties.toString());
			//System.out.println("ESAUpdateConfigProvider : configureDevice : Task Properties : "+taskProperties.toString());
			//createDomainMapFile(taskProperties);
			//ArrayList domainPropList = getDomainProperties(deviceName,taskName);
		//	logger.debug("ESAUpdateConfigProvider : configureDevice : Domain Property List Size : "+domainPropList.size());
			domainIdStr = esaDomainProps.getDomainId();
//			for(int i = 0 ; i < domainPropList.size() ; i++ )
//			{
//				ESADomainProps prop = (ESADomainProps)domainPropList.get(i);
//				domainIdStr = prop.getDomainId();
//				logger.debug("ESAUpdateConfigProvider : configureDevice : ESA Domain ID is : "+domainIdStr);				
//			}
		    domainMapFileAbsPath = ESAUpdateUtility.getInstance().createDomainMapFile(deviceName,taskName,sTaskDirectoryPath,esaDomainProps);
			//String dbRegenResult = taskProperties.getProperty(OccamStaticDef.ESA_DB_REGEN_RESULT);
			//resultMessage.append(taskProperties.getProperty(OccamStaticDef.ESA_DB_REGEN_RESULT_MSG));
			//fix for bug 1352
//			regenDB = taskProperties.getProperty(OccamStaticDef.ESA_DELETE_DB_REGENERATE_NOW);
//			if(regenDB == null || regenDB.equals(""))
//			{
//				logger.debug("ScheduleTask : executeAction : Regenerate DB is null || empty. Setting the value false");
//				regenDB = "false";
//			}
//			
//			if(dbRegenResult.equalsIgnoreCase("SUCCESS"))
//			{
//				result1 = true;
//			}
//			else
//			{
//				result1 = false;
//				taskResult = false;
//			}

				sCommand = buildCommand(deviceName);
				logger.debug("ESAUpdateConfigProvider : configureDevice : Configuration command : "+sCommand);
				result2 = executeCommand(sCommand);

//			logger.debug("ESAUpdateConfigProvider : configureDevice : attribute[0].getIdentifier() :: "+attribute[0].getIdentifier());
			logger.debug("ESAUpdateConfigProvider : configureDevice : resultMessage :: "+resultMessage.toString().trim());

//			if(result1 && result2){				
//				logger.debug("ESAUpdateConfigProvider : configureDevice : Both Command Successful : attribute[0] "+attribute[0]);
//				attributeresult = new AttributeResult(attribute[0].getIdentifier(),0,resultMessage.toString().trim());
//				Properties evtProps = new Properties();
//				evtProps.setProperty(PlaceHolderDefines.TASK_NAME, taskName);
//				evtProps.setProperty(PlaceHolderDefines.ESA_DOMAIN_NAME, domainName);
//				evtProps.setProperty(PlaceHolderDefines.ESA_BLC, deviceName);
//				BEUtil.emitEvent(deviceName,OccamStaticDef.ESA_CONFIG_TASK_SUCCESS, 
//								OccamStaticDef.ESA_CONFIG_TASK_TRAPOID, null, OccamStaticDef.INVALID_INT, 
//								resultMessage.toString(), null, evtProps);
//			}
//			else{
//				logger.debug("ESAUpdateConfigProvider : configureDevice : Any one Command Failed : attribute[0] "+attribute[0]);
//				attributeresult = new AttributeResult(attribute[0].getIdentifier(),-1,resultMessage.toString().trim());
//				Properties evtProps = new Properties();
//				evtProps.setProperty(PlaceHolderDefines.TASK_NAME, taskName);
//				evtProps.setProperty(PlaceHolderDefines.ESA_DOMAIN_NAME, domainName);
//				evtProps.setProperty(PlaceHolderDefines.ESA_BLC, deviceName);
//				BEUtil.emitEvent(deviceName,OccamStaticDef.ESA_CONFIG_TASK_FAILED, 
//								OccamStaticDef.ESA_CONFIG_TASK_TRAPOID, null, OccamStaticDef.INVALID_INT, 
//								resultMessage.toString(), null, evtProps);
//			}
		}catch(Exception ex){
			logger.error("Exception in ESAUpdateConfigProvider : configureDevice",ex);
			//ex.printStackTrace();
		}
		finally
		{
			if(proc != null)
			{
				proc.destroy();
			}
			/** if the final result is not successful add the taskname to the failedtasklist
			 * so that taskExecution on devices can be checked before execution **/
//			if(!(taskResult)){
//				if(!(ConfigServerMainBE.failedTaskList.contains(taskName))){
//					ConfigServerMainBE.failedTaskList.add(taskName);	
//				}
//			}
		}
		return result2;
	}

	private String buildCommand( String deviceName){		
		String command = "";
		String httpsAuthProfile = "";
		String accessLevel = "";
	//	String hostIpAdd = prop.getProperty(OccamStaticDef.ESA_HOST_IP);
		try
		{
//			Authentication authenticationObj = AuthenticationUtil.getAuthProfile(deviceName, AuthenticationUtil.AUTH_TYPE_HTTPS);
			if(this.httpsAuthentication != null){
				httpsAuthProfile = httpsAuthentication;
			}else{
				httpsAuthProfile = DEFAULT_HTTP_AUTHENTICATION;
			}
			logger.debug("ESAUpdateConfigProvider : buildCommand : httpsAuthProfile = "+httpsAuthProfile);
			//httpsAuthProfile = "razor123";
			if(httpAccessLevel!= null&&(httpAccessLevel.equals("2")||httpAccessLevel.equals("3"))){
				accessLevel = httpAccessLevel;
			}else{
				accessLevel = DEFAULT_HTTP_ACCESSLEVEL;
			}
		}
		catch(Exception e)
		{
			logger.error("ESAUpdateConfigProvider : buildCommand : " +
					"Error while getting the Https Authentication Profile for the device "+deviceName,e);
			//e.printStackTrace();
		}
		if(sDebug.equalsIgnoreCase("true"))
		{
			//command = "python esm.py -e "+deviceName+" -a "+httpsAuthProfile+" -l "+httpAccessLevel+" -i "+hostIpAdd
			//+" -E "+domainId+" -M "+domainMapFileAbsPath+" -F "+esaDBfileObj.getAbsolutePath()
			//+" -y "+ftpUserName+" -u "+ftpUserPassword+" -L "+LogFileObj.getAbsolutePath();
			//Fix for bug 1620
			command = "python esm.py -e "+deviceName+" -a "+httpsAuthProfile+" -l "+accessLevel+" -i "+OccamUtils.getLocalHostIP()
			+" -E "+domainIdStr+" -M "+domainMapFileAbsPath+" -F "+esaDBfileObj.getAbsolutePath()
			+" -y "+ftpUserName+" -u "+ftpUserPassword+" -L "+LogFileObj.getAbsolutePath();
		}
		else
		{
			
			//command = "python esm.py -e "+deviceName+" -a "+httpsAuthProfile+" -l "+httpAccessLevel+" -i "+hostIpAdd
			//+" -E "+domainId+" -M "+domainMapFileAbsPath+" -F "+esaDBfileObj.getAbsolutePath()
			//+" -y "+ftpUserName+" -u "+ftpUserPassword;
			//Fix for bug 1620
			command = "python esm.py -e "+deviceName+" -a "+httpsAuthProfile+" -l "+accessLevel+" -i "+OccamUtils.getLocalHostIP()
			+" -E "+domainIdStr+" -M "+domainMapFileAbsPath+" -F "+esaDBfileObj.getAbsolutePath()
			+" -y "+ftpUserName+" -u "+ftpUserPassword;
		}
		//fix for bug 1352
		if(isEsaReganerate)
		{
			command = command+" -R";
		}
		return command;
	}
	
	public boolean executeCommand(String command) throws Exception
	{		 
		String tempStr = null;
		String temp;	
		boolean taskResult = false;
		String esmDir = PureUtils.rootDir+File.separator+"occamViewEMS"+File.separator+"esm";
		logger.debug("ESAUpdateConfigProvider : executeCommand : ESA Dir is "+esmDir);
		proc = Runtime.getRuntime().exec(command,null,new File(esmDir));
		int processResult = proc.waitFor();
		logger.debug("ESAUpdateConfigProvider : executeCommand : Process Result = "+processResult);
		if(processResult == 0)
		{
			logger.debug("ESAUpdateConfigProvider : executeCommand : Command terminated NORMALLY");
			InputStreamReader errorStreamReader = new InputStreamReader(proc.getErrorStream());
			BufferedReader in = new BufferedReader(errorStreamReader);
			String lastLine = null;
			while((tempStr = in.readLine())!= null){
				//tempStr = in.readLine();
				//System.out.println("tempStr : "+tempStr);
				logger.debug("tempStr : "+tempStr);
				logger.info("");
				if(!tempStr.equals(""))
				{
					resultMessage.append(tempStr+"\n");	
					lastLine = tempStr;
				}
			}
			tempStr = lastLine;
			in.close();
			//System.out.println("tempStr : "+tempStr);
			logger.debug("tempStr : "+tempStr);
			//System.out.println("resultMessage : "+resultMessage);
			logger.debug("resultMessage : "+resultMessage);
			if(findTaskStatus(tempStr)){
				taskResult = true;						
			}
			else{
				taskResult = false;
			}
			
		}
		if(processResult != 0){
			logger.debug("ESAUpdateConfigProvider : executeCommand : Command terminated ABRUPTLY");
			InputStreamReader errorStreamReader = new InputStreamReader(proc.getErrorStream());
			BufferedReader inError = new BufferedReader(errorStreamReader);
			String error = null;
			//StringBuffer errorBuf = new StringBuffer();
			if(resultMessage.length() != 0)
			{
				resultMessage.append("\n");
			}
			while((error = inError.readLine()) != null){
				resultMessage.append(error+"\n");
			}
			//System.out.println("error : "+error);
			logger.debug("ESAUpdateConfigProvider : executeCommand : error : "+error);
			System.out.println("resultMessage : "+resultMessage);
			//setResultMessage(resultMessage.toString());
			logger.debug("ESAUpdateConfigProvider : executeCommand : resultMessage : "+resultMessage);
			//resultMessage = errorBuf;
			taskResult = false;
			
		}
		proc.destroy();
		return taskResult;
	}
	
	private boolean findTaskStatus(String str){
		logger.debug("ESAUpdateConfigProvider : findTaskStatus : Last line : "+str);
		StringTokenizer token = new StringTokenizer(str," ");
		String tStr = "";
		while(token.hasMoreElements()){
			tStr = token.nextToken();
		}
		if(tStr.trim().equalsIgnoreCase(OccamStaticDef.SUCCESS)){
			return true;
		}
		else{
			return false;
		}
	
	}
	
	
	public String buildDbDownloadCommand(File esaDBfile,File logFile){	
		String command = null;
		if(esaHostIp != null && esaDbFileName != null){
			String dbFileName = sSwitchDBFilePath+File.separator+esaDbFileName;
			//dbFileName = sSwitchDBFilePath+File.separator+"sub_gw_subs_chad.xml";
			if(sDebug.equalsIgnoreCase("true"))
			{
				command = "python esm.py -y "+ftpUserName+" -u "+ftpUserPassword+" -m "+esaHostIp+" -f "
				+dbFileName+" -n "+esaSwitchLoginName+" -p "+esaSwitchLoginPass+" -w "+esaDBfile.getAbsolutePath()
				+" -L "+logFile.getAbsolutePath();
			}
			else
			{
				command = "python esm.py -y "+ftpUserName+" -u "+ftpUserPassword+" -m "+esaHostIp+" -f "
				+dbFileName+" -n "+esaSwitchLoginName+" -p "+esaSwitchLoginPass+" -w "+esaDBfile.getAbsolutePath();
			}
		}
		return command;
	}
	
	private List getDomainProperties(String esaBLC, String taskName)
	{
		List resultList = new ArrayList();
		if(this.propList != null && this.propList.size() > 0){
			Iterator itr = this.propList.iterator();
			while(itr.hasNext()){
				ESADomainProps propObject = (ESADomainProps)itr.next();
				if(propObject.getTaskName().equals(taskName)&&propObject.getEsaBLC().equals(esaBLC)){
					resultList.add(propObject);
				}
			}
		}
		return resultList;
//		
//		Properties props = new Properties();
//		ESADomainProps esaDomainProp = new ESADomainProps();
//		props.setProperty(ServiceMgmtDBConstants.COLUMN_ESA_BLC, esaBLC);
//		props.setProperty(ServiceMgmtDBConstants.COLUMN_ESA_TASK_NAME, taskName);
//		List propList = DataBaseAPI.getInstance().getObjects(ServiceMgmtDBConstants.TABLE_ESA_DOMAIN_PROPS, props);
//		return propList;
		
	
	}
	
	private String getDomainName(String esaBLC, String taskName)
	{
		Properties props = new Properties();
		ArrayList domainPropList = new ArrayList();
		props.setProperty(ServiceMgmtDBConstants.COLUMN_ESA_BLC, esaBLC);
		props.setProperty(ServiceMgmtDBConstants.COLUMN_ESA_TASK_NAME, taskName);
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String dName = "";
		try{
			/** get the connection pool object **/
			ConnectionPool connectionPool = ConnectionPool.getInstance();
			/** getting the connection **/
			Connection connection = connectionPool.getConnection();
			String strQuery = "SELECT DOMAINNAME from  ESADomainProps where TASKNAME = ? and ESABLC = ?";
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1,taskName);
			pstmt.setString(2,esaBLC);			
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				dName = resultSet.getString("DOMAINNAME");
			}
			logger.debug("ESAUpdateConfigProvider : getDomainName : Domain Name : "+dName);
		}catch(SQLException sq){
			logger.error("ESAUpdateConfigProvider : getDomainName : Failed to get ESA domain name : "+sq);
		
		}finally{
			try
			{
				if(pstmt != null){
					pstmt.close();
				}
				if(resultSet != null){
					resultSet.close();
				}
			}
			catch(SQLException s)
			{
				logger.error("ESAUpdateConfigProvider : getDomainName : Error while closing database resources");
			}
		}
		return dName;
	}
	
	 private void addESADomainProps(HashMap domainMaps,String tName)
	    {
	    	logger.info("SchedulerTask.addESADomainProps() : Processing the ESA Domain Properties");
		    	Set keys = domainMaps.keySet();
		    	Iterator it = keys.iterator();
		    	Properties domainProp;
		    	List propList = new ArrayList();
		    	while(it.hasNext()){
		    		domainProp = (Properties)domainMaps.get(it.next());
		    		logger.info("SchedulerTask.addESADomainProps() : ESA Domain Properties : "+domainProp.toString());
		    		ESADomainProps esaProp = getESADomainProps(domainProp,tName,domainId);
		    		this.propList.add(esaProp);
		    		domainId++;
		    	}
//		    	if(propList != null&& !propList.isEmpty()){
//		    		try {
//						DataBaseAPI.getInstance().addObjects(propList, true);
//					}  catch (Throwable e) {
//						logger.error("Exception occured when adding esaDomainProps from DB ", e);
//					}
//		    	}

	    }
	
//    private void deleteESADomainProps(String taskName){
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		Properties props = new Properties();
//		try {
//			if(taskName != null){
//				props.put(ServiceMgmtDBConstants.COLUMN_ESA_TASK_NAME, taskName);
//				List esaPropList = DataBaseAPI.getInstance().getObjects(ServiceMgmtDBConstants.TABLE_ESA_DOMAIN_PROPS, props);
//				/** get the connection pool object **/
//				if(esaPropList != null){
//						DataBaseAPI.getInstance().deleteObjects(esaPropList);
//				
//				}			
//			}
//
//		} catch (Throwable e) {
//			logger.error("Exception occured when deleting esaDomainProps from DB ", e);
//		}
//
//    }
    
    private ESADomainProps getESADomainProps(Properties prop,String taskName,int Id)
    {
	    ESADomainProps oneEsaProps = new ESADomainProps();
	    if(taskName!= null){
	    	oneEsaProps.setTaskName(taskName);
	    }
	    oneEsaProps.setMemberBLCs((String)prop.get(OccamStaticDef.ESA_MEMBER_BLC));
	    oneEsaProps.setEsaBLC((String)prop.get(OccamStaticDef.ESA_BLC));
	    oneEsaProps.setDomainName((String)prop.get(OccamStaticDef.ESA_DOMAIN_NAME));
	    oneEsaProps.setDomainId(Integer.toString(Id));
	    return oneEsaProps;
    }
    
    private boolean validateProperties(){
    	if(taskName != null&&taskId != null&&esaHostIp!= null&&esaSwitchLoginName != null&&esaSwitchLoginPass!= null&&esaDbFileName != null&&esaDomainPropsMap != null&& sSwitchDBFilePath!=null&&httpAccessLevel != null&&ftpUserName != null&&ftpUserPassword != null){
    		return true;
    	}else{
    		return false;
    	}
    }

	@Override
	 protected void handleResponse() {
		BseriesESAConfigRespSignal signal = (BseriesESAConfigRespSignal)getResponseSignal();
        
//        List error = reqRespObj.getErrorInfo();
//        if( != null && !error.isEmpty()) {
//            StringBuffer sb = new StringBuffer();
//            for(Iterator it = error.iterator(); it.hasNext();) {
//                sb.append(it.next()).append(";");
//            }
//            sb.setLength(sb.length() - 1);
//            signal.setError(resultMessage.toString());
//        }
        if(detailProperties != null&&!detailProperties.isEmpty()){
        	signal.setDetailProps(detailProperties);
        }
        JMSUtilities.publishObjectMessage(JMSUtilities.getIpcQueueType(CMSProcess.CMS_SERVER_ID), signal);
    }
}
