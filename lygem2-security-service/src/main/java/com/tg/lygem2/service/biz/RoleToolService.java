package com.tg.lygem2.service.biz;

import com.github.pagehelper.PageHelper;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.RoleCondition;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@Service
public class RoleToolService extends BasicService {

    public boolean registerRole(Role role) {
        roleMapper.saveRole(role);
        return true;
    }

    public boolean updateRole(Role role) {
        roleMapper.updateRole(role);
        return true;
    }

    public List<Role> findAll(RoleCondition roleCondition) {
        if(roleCondition.getIsPage()){
            PageHelper.startPage(roleCondition.getCurrentPage(),roleCondition.getPageSize(),true);
        }
        return roleMapper.findAll(roleCondition);
    }

    public boolean deleteRole(List<Integer> list) {
        roleMapper.deleteRole(list);
        return true;
    }

}
