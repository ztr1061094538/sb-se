package com.tg.enterprise.model;

public class SysDictionary {

	private String code;
	private String parent_code;
	private String name;
	private String code_no;
	private Integer is_del;
	private Integer sort;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode_no() {
		return code_no;
	}

	public void setCode_no(String code_no) {
		this.code_no = code_no;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
