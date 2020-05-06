package com.tg.enterprise.vo;

import java.util.List;

public class SysDictionaryVO<T> {

	private String typeCode;
	private List<T> list;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
