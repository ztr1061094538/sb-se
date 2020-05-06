package com.tg.enterprise.vo;

public class ResponseVO<T> extends CommonResponse {

	private T data;

	public ResponseVO()
	{
		
	}
	
	public ResponseVO(int code, String msg)
	{
		super(code,msg);
	}
	
	public ResponseVO(int code, String msg, T data)
	{
		super(code,msg);
		this.data = data;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
