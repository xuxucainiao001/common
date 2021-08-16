package cn.toseektech.common.model;

import com.github.pagehelper.PageHelper;

public abstract class Pageable<T> {
	
	private Integer pageNum;
	
	private Integer pageSize;
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@SuppressWarnings("unchecked")
	T enablePage() {
		PageHelper.startPage(this.pageNum, this.pageSize);
		return (T) this;
	}
	
}






