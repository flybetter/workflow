package com.calix.bseries.server.turnuptool.service;

public interface ErrorMsgAware {

	public final static String SUCCESS = "success";
	public final static String FAILURE = "failure";
	public final static String SUCCESS_MSG = "Command completed successfully";
	public final static String CONNECT_FAILED = "connect failed";
	public final static String CONNECT_FAILED_MSG = "connect device failed,can not get deviceType";
	public final static String IP_INVALID_ = "IP is invalid";
	public final static String VNE_EXISTED = "VNE has been created in CMS";
	public final static String NETWORKNAME_EXISTED = "NetWorkName Already exists in CMS";
	public final static String VNE_NOT_EXISTED = "VNE not exists in CMS";
	public final static String SERVICE_INTERNAL_EXCEPTION = "service internal exception:";
	public final static String DEVICE_TYPE = "deviceType:";
}
