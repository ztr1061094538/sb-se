package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 设备档案 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */ 
@Getter
@Setter
public class Device{

	private Integer id; // 主键id
	private Integer enterprise_id;
	private String device_num; // 设备编号
	private String name; // 设备名称
	private String type; // 设备类型
	private String address; // 设备位置
	private Long begin_date; // 投运日期
	private Integer state; // 设备状态 0 停运 1 使用中

	private String typeName;
}