package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 生产工序单元表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
@Getter
@Setter
public class ProcessCodeUnit{

	private Long id; //
	private String codeUnit; // 生产工序单元编码
	private String nameUnit; // 生产工序单元名称
	private Integer companyId; // 企业ID
	private Long cid; // 生产工序编码ID
	private java.util.Date commDate; // 投产日期
	private String designedCapacity; // 生产能力
	private String remark; // 备注
	private String industryCode; // 所属行业，如炼钢
}