package cn.toseektech.common.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.toseektech.common.enums.ResponseEnum;

public class ResponseVO<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5877467450584380851L;

	private String code = ResponseEnum.SUCCESS.getCode();

	private String message= ResponseEnum.SUCCESS.getMessage();
    
	private T content;

	public ResponseVO() {
	}
	
	public ResponseVO(T content) {
		this.content=content;
	}
	
	public ResponseVO<T> message(String message){
		this.message=message;
		return this;
	}
	
	public ResponseVO<T> code(String code){
		this.code=code;
		return this;
	}
	
	public boolean isSuccess() {
		return ResponseEnum.SUCCESS.getCode().equals(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
	
	public static <T> ResponseVO<T> success(T content) {
		return new ResponseVO<>(content).code(ResponseEnum.SUCCESS.getCode()).message(ResponseEnum.SUCCESS.getMessage());
	}
	
	public static <T> ResponseVO<T> success() {
		ResponseVO<T> r= new ResponseVO<>();
		r.setCode(ResponseEnum.SUCCESS.getCode());
		r.setMessage(ResponseEnum.SUCCESS.getMessage());
		return r;
	}
}
