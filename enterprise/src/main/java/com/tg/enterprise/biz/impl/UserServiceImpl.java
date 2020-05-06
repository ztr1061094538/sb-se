package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IUserService;
import com.tg.enterprise.dao.IUserMapper;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.MD5UtilTool;
import com.tg.enterprise.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserMapper iUserMapper;

	@Override
	public CommonResponse checkUser(String loginName, String loginPass) {
		CommonResponse commonResponse = new CommonResponse();
		User params = new User();
		params.setLoginName(loginName);
		params.setLoginPass(loginPass);
		List<User> user = iUserMapper.query(params);
		if (user.isEmpty()) {
			commonResponse.setCode(ErrorCode.INVALID_ACCOUNT);
			commonResponse.setMsg("用户不存在，或者密码不正确");
		} else {
			commonResponse.setCode(ErrorCode.OK);
		}
		return commonResponse;
	}
	
	@Override
	public User getUser(String loginName, String loginPass) {
		User params = new User();
		params.setLoginName(loginName);
		params.setLoginPass(loginPass);
		List<User> user = iUserMapper.query(params);
		if (user.isEmpty()) {
			return null;
		} else {
			return user.get(0);
		}
	}

	@Override
	public boolean changePassword(Long id, String newpwd) {
		newpwd = MD5UtilTool.MD5(newpwd);
		return iUserMapper.resetPassword(id, newpwd);
	}

	@Override
	public PageInfo<User> selectForPage(User user, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true);
		return new PageInfo<>(iUserMapper.selectForPage(user));
	}

	@Override
	public User selectByLoginName(String loginName) {
		return iUserMapper.selectByLoginName(loginName);
	}

	@Override
	public int insert(User user) {
		return iUserMapper.insert(user);
	}

	@Override
	public int update(User user) {
		return iUserMapper.update(user);
	}

	@Override
	public int delete(Long id) {
		return iUserMapper.delete(id);
	}
}
