package com.tg.lygem2.service.biz;

import com.github.pagehelper.PageHelper;
import com.tg.lygem2.vo.MenuCondition;
import com.tg.lygem2.vo.RoleOfMenu;
import com.tg.lygem2.vo.crud.ExposeMenu;
import com.tg.lygem2.vo.crud.LoginMenu;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@Service
public class MenuTooleService extends BasicService {

	@CacheEvict(cacheNames="allMenu", key="'lygem2@2020'")
    public boolean saveMenu(ExposeMenu menu) {
		if (null == menu.getSortId()) {
			menu.setSortId(0L);
		}
        menuMapper.saveMenu(menu);
        return true;
    }

	@CacheEvict(cacheNames="allMenu", key="'lygem2@2020'")
    public boolean updateMenu(ExposeMenu menu) {
        menuMapper.updateMenu(menu);
        return true;
    }

    public List<RoleOfMenu> findMenuOFRoleForRoleID(Integer roleid) {
        return bridgeMapper.findRoleIDList(roleid);
    }

    public List<LoginMenu> findAllMenu(MenuCondition menuCondition) {
        if(menuCondition.getIsPage()){
            PageHelper.startPage(menuCondition.getCurrentPage(), menuCondition.getPageSize(), true);
        }
        return menuMapper.getAllMenu(menuCondition);
    }

    public List<LoginMenu> findMenuListOFRoleID(List<RoleOfMenu> roleOfMenuList) {
        ArrayList<LoginMenu> menus = new ArrayList<>();
        for (RoleOfMenu roleOfMenu :
                roleOfMenuList) {
            LoginMenu loginMenu = menuMapper.selectMenu(roleOfMenu.getMenuId());
            if(loginMenu != null) menus.add(loginMenu);
        }
        return menus;
    }


    //树形结构
    public List<LoginMenu> getLoginMenuList(List<String> roleName) {
        if(roleName != null && roleName.size() >0){
            return menuMapper.findLoginMenu(roleName);
        }
        return  null;
    }

    public List<LoginMenu> getLoginMenus(List<LoginMenu> menuList) {
        ArrayList<LoginMenu> loginMenus = new ArrayList<>();
        List<LoginMenu> subMenu = getSubMenu(menuList);
        List<LoginMenu> collect = subMenu.stream().sorted((x, y) -> x.getSortId().compareTo(y.getSortId())).collect(Collectors.toList());
        for (LoginMenu loginMenu : collect) {
            LoginMenu child = getChild(loginMenu, menuList);
            loginMenus.add(child);
        }
        return loginMenus;
    }

    public List<LoginMenu> getSubMenu(List<LoginMenu> menuList) {
        ArrayList<LoginMenu> menus = new ArrayList<>();
        for (LoginMenu m1 :
                menuList) {
            if (m1.getParentId() != null && m1.getParentId().equals(0L)) {
                menus.add(m1);
            }
        }
        return menus;
    }

    public LoginMenu getChild(LoginMenu loginMenu, List<LoginMenu> menuList) {
        if (loginMenu != null) {
            ArrayList<LoginMenu> loginMenus = new ArrayList<>();
            for (LoginMenu menu : menuList) {
                if (menu.getParentId() != null && menu.getParentId().toString().equals(loginMenu.getId().toString())) {
                    menu.setParentName(loginMenu.getName());
                    loginMenus.add(menu);
                }
            }
            List<LoginMenu> collect = loginMenus.stream().sorted((x, y) -> x.getSortId().compareTo(y.getSortId())).collect(Collectors.toList());
            loginMenu.setChildren(collect == null || collect.size() == 0 ? null : collect);
            for (LoginMenu loginMenu1 : loginMenus) {
                getChild(loginMenu1, menuList);
            }
        }
        return loginMenu;
    }
}