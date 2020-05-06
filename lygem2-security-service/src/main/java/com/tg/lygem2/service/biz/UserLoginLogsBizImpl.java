package com.tg.lygem2.service.biz;

import com.tg.lygem2.bean.mapper.UserLoginLogsMapper;
import com.tg.lygem2.service.IUserLoginLogsBiz;
import com.tg.lygem2.vo.UserLoginLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业用电日表 Dao接口实现
 * 
 * @Copyright 苏州太谷电力股份有限公司
 * @author 霍腾腾
 * @date 2019-01-24 =================Modify Record=================
 * @Modifier @date @Content 霍腾腾 2019-01-24 新增
 */
@Service("UserLoginLogsBizImpl")
public class UserLoginLogsBizImpl implements IUserLoginLogsBiz {

	@Autowired
	private UserLoginLogsMapper userLoginLogsMapper;

	/**
	 * 插入
	 */
	@Override
	public int insert(UserLoginLogs userLoginLogs) {
		return userLoginLogsMapper.insert(userLoginLogs);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<UserLoginLogs> userLoginLogsList) {
		return userLoginLogsMapper.insertBatch(userLoginLogsList);
	}

}