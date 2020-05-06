package com.tg.lygem2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class CommonResponse 
{
	@ApiModelProperty(value="0:成功，其他:失败")
	private int code = 0;

	@ApiModelProperty(value="ok:成功，其他:报错信息")
	private String msg = "";

	public CommonResponse() {

	}

	public CommonResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static CommonResponse fail(int errorCode, String msg) {
		return new CommonResponse(errorCode, msg);
	}
}
