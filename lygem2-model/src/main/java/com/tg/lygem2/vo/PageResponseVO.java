package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseVO<T> extends CommonResponse {

	public PageResponseVO() {
	}

	public PageResponseVO(int code, String msg) {
		super(code, msg);
	}

	private long total;
	private List<T> rows;
}
