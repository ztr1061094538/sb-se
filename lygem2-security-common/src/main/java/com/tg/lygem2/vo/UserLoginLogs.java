package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 企业用电日表 VO
 * 
 * @Copyright 苏州太谷电力股份有限公司
 * @author 霍腾腾
 * @date 2019-01-24 =================Modify Record=================
 * @Modifier @date @Content 霍腾腾 2019-01-24 新增
 */
@Getter
@Setter
public class UserLoginLogs {

	private Integer id; //
	private String login_name; //
	private Integer org_id; // 机构id
	private Integer company_id; // 企业id
	private Long login_time; // 登录时间，yyyyMMddHHmmss
	private String login_ip; // 登录ip
}