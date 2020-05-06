package com.tg.lygem2.controller.biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import com.tg.lygem2.bean.User;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.vo.MenuCondition;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.crud.ExposeMenu;
import com.tg.lygem2.vo.crud.LoginMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@RestController
@RequestMapping("/menus")
@Api("Menu操作")
public class MenuController extends BaseController {

    @PostMapping("/insert")
    @ApiOperation(value = "Menu添加", notes = "")
    @Transactional
    public Result<?> insertMenu(@RequestBody ExposeMenu menu) {
        if (menuTooleService.saveMenu(menu))
            return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "MENU添加成功");
        else
            return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "MENU添加失败");
    }

    @PostMapping("/update")
    @ApiOperation(value = "Menu更新", notes = "")
    @Transactional
    public Result<?> updateMenu(@RequestBody ExposeMenu menu) {
        if (menuTooleService.updateMenu(menu))
            return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "MENU更新成功");
        else
            return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "MENU更新失败");
    }

    @PostMapping("/findMenuByRoleID")
    @ApiOperation(value = "单个Role下面的Menu", notes = "")
    @Transactional
    public String findMenuByRoleID(@RequestBody String roleId) throws JsonProcessingException {
        //查出当前用户下的Role
        List<LoginMenu> menuListOFRoleID = menuTooleService.findMenuListOFRoleID(menuTooleService.findMenuOFRoleForRoleID(Integer.parseInt(roleId)));
        //查出整个Menu的列表
        List<LoginMenu> loginMenuList = menuTooleService.findAllMenu(MenuCondition.instance());
        ArrayList<LoginMenu> loginMenuArrayList = new ArrayList<>();
        for (LoginMenu loginMenu:loginMenuList) {
            for (LoginMenu localMenu:menuListOFRoleID) {
                if(localMenu.getId().equals(loginMenu.getId())){
                    loginMenu.setLocalRoleIsEnadle(true);
                    break;
                }else {
                    loginMenu.setLocalRoleIsEnadle(false);
                }
            }
            loginMenuArrayList.add(loginMenu);
        }

        return objectMapper.writeValueAsString(new Result<List<LoginMenu>>(ErrorCodeMsgEnum.SUCESS.getCode(), "查询成功",menuTooleService.getLoginMenus(loginMenuArrayList)));

    }

    @PostMapping("/getAllMenu")
    @ApiOperation(value = "查询所有Menu", notes = "")
    @Transactional
    public String findAllMenu(@RequestBody MenuCondition menuCondition) throws JsonProcessingException {
        List<LoginMenu> allMenu = menuTooleService.findAllMenu(menuCondition);
        PageInfo<LoginMenu> exposeMenuPageInfo = new PageInfo<>(allMenu);
        return objectMapper.writeValueAsString(new Result<List<LoginMenu>>(ErrorCodeMsgEnum.SUCESS.getCode(), "查询成功", allMenu, exposeMenuPageInfo.getTotal()));
    }

    //查找当前用户下Menu菜单
    @PostMapping("/findByParentID")
    @ApiOperation(value = "查询菜单树形结构", notes = "")
    @Transactional
    public String findByParentId(@RequestHeader("cid")String cid) throws JsonProcessingException {
        List<String> authentication = null;
        if(StringUtils.isNotBlank(cid)){
            User user = userService.selectByCommpany(cid);
            authentication = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        }else {
            authentication = this.getAuthentication();
        }
        List<LoginMenu> loginMenuList = menuTooleService.getLoginMenuList(authentication);
        List<LoginMenu> loginMenus = menuTooleService.getLoginMenus(loginMenuList);
        Result<List<LoginMenu>> success = new Result<>(0, "成功返回信息", loginMenus);
        return objectMapper.writeValueAsString(success);
    }

    //查找当前用户下所拥有的权限属性列表
    @PostMapping("/findTreeMenu")
    @ApiOperation(value = "菜单管理查询按钮及菜单树形结构", notes = "")
    @Transactional
    public String findAllByTree() throws JsonProcessingException {
        List<LoginMenu> loginMenuList = menuTooleService.findAllMenu(MenuCondition.instance());
        List<LoginMenu> loginMenus = menuTooleService.getLoginMenus(loginMenuList);
        Result<List<LoginMenu>> success = new Result<>(0, "成功返回信息", loginMenus);
        return objectMapper.writeValueAsString(success);
    }
}