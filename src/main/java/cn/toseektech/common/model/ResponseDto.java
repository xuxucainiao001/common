package cn.toseektech.common.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.toseektech.common.enums.ResponseEnum;
import cn.toseektech.translation.annotation.Translate;




/**
 * 
 * @ClassName:  ResponseDto   
 * @Description:  
 * @author: xuxu
 * @date:   2021年6月8日 上午9:14:43    
 * @param <T>  
 * @Copyright:
 * @see #cn.toseektech.common.model.ResponseVO<T>
 */

@Deprecated
public class ResponseDto<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5877467450584380851L;

	private String code = ResponseEnum.SUCCESS.getCode();

	private String message= ResponseEnum.SUCCESS.getMessage();
    
	@Translate
	private T content;

	public ResponseDto() {
	}
	
	public ResponseDto(T content) {
		this.content=content;
	}
	
	public ResponseDto<T> message(String message){
		this.message=message;
		return this;
	}
	
	public ResponseDto<T> code(String code){
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
}
