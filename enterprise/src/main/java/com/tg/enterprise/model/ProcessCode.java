package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 生产工序表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
@Getter
@Setter
public class ProcessCode{

	private Long id; //
	private String code; // 生产工序编码
	private String name; // 生产工序名称
	private Integer companyId; // 企业ID
	private String remark; // 内容
	private String industryCode; // 所属行业，如炼钢
}