package com.tg.enterprise.vo;

public class PageRequestVO<T> extends CommonPageRequestVO {

	private T params;

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}
}
