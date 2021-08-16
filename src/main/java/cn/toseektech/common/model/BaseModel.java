package cn.toseektech.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.toseektech.common.utils.CommonUtil;

public abstract class BaseModel {
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
	
	
	public <T> T transfer(Class<T> clazz) {
		return CommonUtil.copy(this, clazz);
	}

}
