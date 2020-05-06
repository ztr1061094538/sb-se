package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 标准规范/培训课件 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-13
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-13			新增
 */ 
@Getter
@Setter
public class StandardTraining {

	private Long id; // 自增id
	private String title; // 标题
	private Integer type; // 类型，0：标准规范；1：培训课件
	private java.util.Date edit_time; // 最后一次编辑时间
	private String attachment; // 附件
	private Long editor_id; // 最后编辑人id
	private Integer order_num; //置顶（0：不置顶 1：置顶）
	private Long top_time; //置顶时间
	private String filename;//附件名称

	private String attachmentPath;//附件路径
	private String edit_time_str;//发布时间
}