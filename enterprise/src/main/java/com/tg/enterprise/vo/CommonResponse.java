package com.tg.enterprise.vo;

public class CommonResponse 
{
	private int code = 0;
	
	private String msg = "";
	
	public CommonResponse()
	{
		
	}

	public CommonResponse(int code, String msg) 
	{
		this.code = code;
		this.msg = msg;
	}

	public int getCode() 
	{
		return code;
	}

	public void setCode(int code) 
	{
		this.code = code;
	}

	public String getMsg() 
	{
		return msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
}
