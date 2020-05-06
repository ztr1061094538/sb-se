package com.tg.lygem2.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 企业表 VO
 * 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-28 =================Modify Record=================
 * @Modifier @date @Content企业 panxinchao 2018-12-28 新增
 */
@Getter
@Setter
public class Company {
	private Integer company_id; // 企业唯一id
	private String name; // 企业名称
	private Integer org_id; // 所属机构id
	private Integer is_del; // 是否删除，0：未删除；1：已删除。
}