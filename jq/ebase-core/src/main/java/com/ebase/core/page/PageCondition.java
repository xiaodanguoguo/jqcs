package com.ebase.core.page;

import java.util.Map;

/**
 * <p>
 * 分页参数传递
 * </p>
 *
 * @project core-api
 * @class PageCondition
 */
public class PageCondition {
	
	private int pageSize;
	
	private int pageNum;
	
	private Map<String, Object> params;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
