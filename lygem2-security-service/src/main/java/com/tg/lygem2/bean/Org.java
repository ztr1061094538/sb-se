package com.tg.lygem2.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 机构表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-28
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-28			新增
 */ 
@Getter
@Setter
public class Org{

	private Integer org_id; // 机构id
	private String name; // 机构名称
	private Integer parent_id; // 父类机构id
	private Integer type; // 级别
	private String description; // 描述
	private Integer is_del; // 是否删除，0：未删除；1：已删除。
}