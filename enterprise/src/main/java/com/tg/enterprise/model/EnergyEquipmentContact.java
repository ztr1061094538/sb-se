package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用能设备采集数据关联定义列表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
@Getter
@Setter
public class EnergyEquipmentContact {
	private Long id; //
	private String name;
	private String equipmentCode; // 用能设备编码
	private Integer equipmentId; // 用能设备名称
	private Integer companyId; // 企业ID
	private String remark; // 备注
	private String code;
	private String code_unit; // 所属工序及工序单元
	private String collectType; // 采集数据分类
	private String category; // 能源/能耗/能效/产品
	private String energyUse; // 能源用途
	private String industryCode; // 所属行业，如炼钢
	private String inputType;
	private String terminalId;
	private Integer statType;
	private List<String> codeList;
	
}