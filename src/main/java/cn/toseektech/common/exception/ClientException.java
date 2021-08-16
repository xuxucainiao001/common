package cn.toseektech.common.exception;

public class ClientException extends RuntimeException{
	
		
	private String errorMessage;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5374215648947183763L;
	
	public ClientException(String errorMessage) {
		super(errorMessage);
		this.errorMessage=errorMessage;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

}