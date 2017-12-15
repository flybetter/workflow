package com.calix.bseries.server.ana.common.error;


/**
 * 
 * @author tben
 *
 */
public class AnaException extends Exception{
	private static final long serialVersionUID = 5676180385439875609L;
	private String errorCode;
	
	public AnaException(String errorCode){
		super();
		this.errorCode=errorCode;
	}
	
	
	public AnaException(String errorCode,Throwable e){
		super();
	}
	
	
}
