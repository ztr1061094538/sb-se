package com.tg.lygem2.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ResponseVO<T> extends CommonResponse {
	@ApiModelProperty(value="详情")
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

	public static ResponseVO<Object> fail(int errorCode,String msg)
	{
		return new ResponseVO<Object>(errorCode,msg);
	}

}
