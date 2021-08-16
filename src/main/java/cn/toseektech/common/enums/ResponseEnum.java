package cn.toseektech.common.enums;

public enum ResponseEnum {
	
	SUCCESS("200","success"),
	
	/*  业务处理异常   MethodArgumentNotValidException  */
	VALIDATE_ERROR("5001","validate error"),
	
	/*  业务处理异常   BussinessException  */
	BUSSINESS_ERROR("5002","bussiness error"),
	
	/*  后台系统异常  RuntimeException || Exception*/
	SYSTEM_ERROR("5003","system error"),
	
	/*   sentinel限流异常    */
	LIMIT_ERROR("5004","limit block error"),
	
	/*  登录异常    */
	LOGIN_ERROR("5005","no login"),
	
	/*  用户ip访问次数过多异常   */
	IP_LIMIT_ERROR("5006","ip visited too many");
	
	
	private String code;
	
	private String message;
	
	private ResponseEnum(String code,String message) {
		this.code=code;
		this.message=message;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}

}
