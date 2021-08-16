package cn.toseektech.common.exception;

import cn.toseektech.common.enums.ResponseEnum;

public class BussinessException extends RuntimeException{
	
		
	private String errorMessage;
	
	private String errorCode = ResponseEnum.BUSSINESS_ERROR.getCode();

	/**
	 * 
	 */
	private static final long serialVersionUID = -5374215648947183763L;
	
	public BussinessException(String errorMessage) {
		super(errorMessage);
		this.errorMessage=errorMessage;
	}
	
	public BussinessException(String errorCode,String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage=errorMessage;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

}
