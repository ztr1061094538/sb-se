package com.tg.enterprise.vo;

import java.util.ArrayList;
import java.util.List;

public class ChartKeyValuePairVO<T>
{
	private String name;
	
	private List<T> data = new ArrayList<T>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public ChartKeyValuePairVO(){

	}
	public ChartKeyValuePairVO(String name)
	{
		this.name =name;
	}
}
