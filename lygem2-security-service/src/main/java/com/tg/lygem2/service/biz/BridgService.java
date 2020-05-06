package com.tg.lygem2.service.biz;

import com.tg.lygem2.vo.LoginUser;
import com.tg.lygem2.vo.UserOfRole;
import com.tg.lygem2.vo.crud.RoleOfAllMenuIds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-11
 **/
@Service
public class BridgService extends BasicService {

    /**
     * UserOFROLE
     */
    public boolean saveRegisterUserOfRole(LoginUser user) {
        List<Integer> roles = user.getRoleIds();
        roles.stream().forEach(role -> bridgeMapper.saveUserOfRole(UserOfRole.instance(null, user.getId(), role)));
        return true;
    }

    public boolean updateUserOfRole(LoginUser user) {
        bridgeMapper.deleteForUserId(user.getId());
        if (saveRegisterUserOfRole(user))
            return true;
        return false;
    }

    public boolean saveInsertUserOfRole(UserOfRole userOfRole) {
        bridgeMapper.saveUserOfRole(userOfRole);
        return true;
    }

    @CacheEvict(cacheNames="allMenu", key="'lygem2@2020'")
    public boolean saveRegisterRoleOfMenu(RoleOfAllMenuIds roleOfAllMenu) {
        List<Long> menuIds = roleOfAllMenu.getMenuIds();
        menuIds.stream().forEach(ids -> bridgeMapper.saveRoleOfMenu(ids, roleOfAllMenu.getId()));
        return true;
    }

    @CacheEvict(cacheNames="allMenu", key="'lygem2@2020'")
    public boolean updateRoleOFMenu(RoleOfAllMenuIds roleOfAllMenu) {
        bridgeMapper.deleteForRoleId(roleOfAllMenu.getId());
        saveRegisterRoleOfMenu(roleOfAllMenu);
        return true;
    }
    public boolean delByUserIds(List<Integer> list) {
    	bridgeMapper.delByUserIds(list);
		return true;
    }

    public boolean deleteRoleOFMenu(List<Integer> list) {
        bridgeMapper.deleteForRoleIds(list);
        return true;
    }

    public boolean deleteUserOfRole(List<Integer> list) {
        bridgeMapper.deleteUserOfRole(list);
        return true;
    }

}