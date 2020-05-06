package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVO<T> extends CommonResponse {

	private T data;

	public ResponseVO() {

	}

	public ResponseVO(int code, String msg) {
		super(code, msg);
	}

	public ResponseVO(int code, String msg, T data) {
		super(code, msg);
		this.data = data;
	}

}
