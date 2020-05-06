package com.tg.enterprise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(value = "分页返回类")
public class PageResponseVO<T> extends CommonResponse {

	public PageResponseVO() {
	}

	public PageResponseVO(int code, String msg) {
		super(code, msg);
	}
	
	private long nextPage;
	private long total;
	@ApiModelProperty(value = "数据")
	private List<T> rows;

	private long num;
	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}
	
	public long getNextPage() {
		return nextPage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
