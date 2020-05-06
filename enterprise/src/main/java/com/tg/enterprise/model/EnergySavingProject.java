package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 节能项目 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-19
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-19			新增
 */ 
@Getter
@Setter
public class EnergySavingProject{

	private Integer id; // 主键id
	private Integer enterprise_id;//企业id
	private String name; // 节能项目名称
	private String remark; // 节能项目说明
	private Long start_date; // 生效-开始时间
	private Long end_date; // 生效-结束时间
	private String terminal_ids; // 关联监测点id，多个用英文，分隔
	private String filenames; // 附件名称，多个逗号分隔
	private String urls; // 上传返回文件名称，多个逗号分隔
	private String filePaths;
}