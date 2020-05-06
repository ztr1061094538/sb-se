package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 工作通知-任务表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author jikai.sun
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * jikai.sun			2018-12-05			新增
 */ 
@Getter
@Setter
public class Product {

	private Integer id; //
	private Integer enterprise_id; // 企业id
	private String name; // 名称
	private String remark; //
}