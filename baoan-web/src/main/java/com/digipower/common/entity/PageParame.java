package com.digipower.common.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PageParame implements Serializable {
	private Integer page;
	
	private Integer limit;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public PageParame(Integer page, Integer limit) {
		super();
		this.page = page;
		this.limit = limit;
	}

	public PageParame() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
