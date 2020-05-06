package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 工作任务 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-29
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-29			新增
 */ 
@Getter
@Setter
public class WorkTaskInfo{

	private Long id; // 自增id
	private String title; // 标题
	private String taskTimeStart; // 通知时间
	private Integer state; // 状态，0：未填报；1：已填报 2：已超时
	private Long time; // 创建时间
	private Integer order_num; //提醒（0：不提醒 1：提醒）
	private Long top_time; //提醒时间
}