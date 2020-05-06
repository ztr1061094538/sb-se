package com.tg.lygem2.service.biz;

import com.github.pagehelper.PageHelper;
import com.tg.lygem2.bean.Company;
import com.tg.lygem2.bean.Org;
import com.tg.lygem2.bean.User;
import com.tg.lygem2.bean.UserTypeEnum;
import com.tg.lygem2.bean.mapper.CompanyMapper;
import com.tg.lygem2.vo.LoginUser;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.UserCondition;
import com.tg.lygem2.vo.UserOfRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
@Service
public class UserToolService extends BasicService {
	
	@Autowired
	private CompanyMapper companyMapper;

    public boolean registerUser(LoginUser user) {
        this.userMapper.saveUser(user);
        return true;
    }

    public boolean isExistUser(String userName) {
        if (findUser(userName) != null) {
            return true;
        } else {
            return false;
        }
    }

    public User findUser(String userName) {
        return this.userMapper.selectByUsername(userName);
    }

    public User selectUser(String userName) {
        return this.userMapper.selectByUsername(userName);
    }
    
    public User selectByUserPhone(String phone) {
        return this.userMapper.selectByUserPhone(phone);
    }

    public User selectByCommpany(String cid) 
    {
    	User user = this.userMapper.selectByCommpany(cid);
    	if (user != null)
    	{
    		user.setRoles(this.userMapper.findRole(user.getId()));
    	}
        return user;
    }

    public boolean registerRole(List<Role> role) {
        role.stream().forEach(r -> {
            this.roleMapper.saveRole(r);
        });
        return true;
    }


    public boolean updateUser(LoginUser loginUser) {
        userMapper.updateUser(loginUser);
        return true;
    }

    public boolean updatePassword(LoginUser user) {
        userMapper.updatePassword(user);
        return true;
    }
    
    public User findUserById(Integer userId) {
        return this.userMapper.findUserById(userId);
    }

    public List<LoginUser> findAll(UserCondition userCondition) {
        List<Company> companyList = companyMapper.getAll();
        Map<String, String> companyMap = new HashMap<String, String>();
        if (null != companyList && !companyList.isEmpty()) {
            for (Company c : companyList) {
                companyMap.put(c.getCompany_id().toString(), c.getName());
            }
        }
        PageHelper.startPage(userCondition.getCurrentPage(), userCondition.getPageSize(), true);
        List<LoginUser> all = userMapper.findAll(userCondition);
        all.stream().forEach(loginUser -> {
			if (StringUtils.isNotBlank(loginUser.getEnterprise_id()) && companyMap.containsKey(loginUser.getEnterprise_id())) {
				loginUser.setCompanyName(companyMap.get(loginUser.getEnterprise_id()));
			}
            List<Role> roleByUserId = this.findRoleByUserId(loginUser.getId());
            if (roleByUserId != null) loginUser.setRoles(roleByUserId);
        });
        return all;
    }

    public List<Role> findRoleByUserId(Integer userid) {
        List<UserOfRole> roleByUserId = bridgeMapper.findRoleByUserId(userid);
        ArrayList<Role> roles = new ArrayList<>();
        roleByUserId.stream().forEach(userOfRole -> {
            Role allRoleById = roleMapper.findAllRoleById(userOfRole.getRoleId());
            if (allRoleById != null) roles.add(allRoleById);
        });
        return roles;
    }
    
    public boolean delByIds(List<Integer> list) {
    	userMapper.delByIds(list);
		return true;
	}

    public List<User> findUserByRoleIds(List<Integer> list) {
        List<UserOfRole> userByRoleId = bridgeMapper.findUserByRoleIds(list);
        ArrayList<User> users = new ArrayList<>();
        userByRoleId.stream().forEach(userOfRole -> {
            User allUserById = userMapper.findUserById(userOfRole.getUserId());
            if (allUserById != null) users.add(allUserById);
        });
        return users;
    }
}