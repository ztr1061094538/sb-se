package com.tg.lygem2.bean.mapper;

import com.tg.lygem2.vo.UserLoginLogs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业用电日表 VO
 * 
 * @Copyright 苏州太谷电力股份有限公司
 * @author 霍腾腾
 * @date 2019-01-24 =================Modify Record=================
 * @Modifier @date @Content 霍腾腾 2019-01-24 新增
 */

public interface UserLoginLogsMapper {

	/**
	 * 插入
	 */
	@Insert("insert into user_login_logs" + "(login_name,org_id,company_id,login_time,login_ip) values "
			+ "(#{userLoginLogs.login_name}," + "#{userLoginLogs.org_id}," + "#{userLoginLogs.company_id},"
			+ "#{userLoginLogs.login_time}," + "#{userLoginLogs.login_ip})")
	int insert(@Param("userLoginLogs") UserLoginLogs userLoginLogs);

	/**
	 * 批量插入
	 */
	@Insert("<script>insert into user_login_logs" + "( login_name, org_id, company_id, login_time, login_ip) values "
			+ "<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + "#{item.login_name},"
			+ "#{item.org_id}," + "#{item.company_id}," + "#{item.login_time}," + "#{item.login_ip}</foreach>"
			+ "</script>")
	int insertBatch(@Param("list") List<UserLoginLogs> userLoginLogsList);

}