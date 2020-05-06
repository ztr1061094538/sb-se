package com.tg.lygem2.service;

import com.tg.lygem2.vo.UserLoginLogs;

import java.util.List;

/**
 * 企业用电日表 Dao接口
 * 
 * @Copyright 苏州太谷电力股份有限公司
 * @author 霍腾腾
 * @date 2019-01-24 =================Modify Record=================
 * @Modifier @date @Content 霍腾腾 2019-01-24 新增
 */
public interface IUserLoginLogsBiz {

	/**
	 * 插入
	 */
	int insert(UserLoginLogs userLoginLogs);

	/**
	 * 批量插入
	 */
	int insertBatch(List<UserLoginLogs> userLoginLogsList);

}