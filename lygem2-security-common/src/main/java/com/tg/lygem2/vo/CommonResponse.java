package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
	private int code = 0;

	private String msg = "";

	public CommonResponse() {

	}

	public CommonResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
