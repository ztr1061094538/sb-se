package com.tg.enterprise.vo;

import java.util.List;

public class ListResponseVO<T>
{
	private int code;
	
	private String msg;
	
	private List<T> data;

	
	public ListResponseVO(int code, String msg)
	{
		super();
		this.code = code;
		this.msg = msg;
	}

	public ListResponseVO(int code, String msg, List<T> data) 
	{
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
