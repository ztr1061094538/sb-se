package com.tg.enterprise.biz;


import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.User;
import com.tg.enterprise.vo.CommonResponse;

import java.util.List;

public interface IUserService {

	 CommonResponse checkUser(String loginName, String loginPass);
	 User getUser(String loginName, String loginPass);

    boolean changePassword(Long id, String newpwd);

    PageInfo<User> selectForPage(User user, int pageIndex, int pageSize);

    User selectByLoginName(String loginName);

    int insert(User user);

    int update(User user);

    int delete(Long id);
}
