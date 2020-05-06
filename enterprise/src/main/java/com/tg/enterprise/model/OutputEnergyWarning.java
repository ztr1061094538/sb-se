package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 产量与能耗预警设置 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-12-05			新增
 */ 
@Getter
@Setter
public class OutputEnergyWarning{

	private Integer id; // 主键id
	private Long year; //
	private Integer enterprise_id;
	private java.math.BigDecimal total_cons; // 能耗总量
	private java.math.BigDecimal unit_cons; // 单位能耗
	private java.math.BigDecimal total_cons_per; // 能耗总量预警范围
	private java.math.BigDecimal unit_cons_per; // 单位能耗预警范围
}