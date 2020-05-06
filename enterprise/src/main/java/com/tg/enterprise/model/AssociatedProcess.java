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
public class AssociatedProcess {

	private Integer id; // 主键
	private Integer proportion; // 百分比
	private Long uid; // 工序ID
	private Integer pid; // 产品ID

	public AssociatedProcess() {
	}

	public AssociatedProcess(Long uid, Integer pid) {
		this.uid = uid;
		this.pid = pid;
	}
}