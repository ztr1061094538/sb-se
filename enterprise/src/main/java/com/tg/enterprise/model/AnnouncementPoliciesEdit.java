package com.tg.enterprise.model;

import lombok.Data;

import java.util.List;

/**
 * 通知公告 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-12			新增
 */ 
@Data
public class AnnouncementPoliciesEdit {

	private Long id; // 自增id
	private Long publish_id; // 原id（已发布记录id）
	private String title; // 标题
	private String content; // 内容
	private Integer type; // 类型，0：通知公告；1：政策法规
	private Long editor_id; // 最后编辑人id
	private java.util.Date edit_time; // 编辑时间
	private String attachment; // 附件
	private String filename; // 附件名称
	private String source; // 来源
	private Integer order_num; //置顶（0：不置顶 1：置顶）
	private Long top_time; //置顶时间

	private String attachmentPath;//附件地址
	private Integer canPublish;//0:可发布 1：不可发布
	private String edit_time_str;//最后编辑时间

	private List<Long> ids;
}