package com.tg.lygem2.controller.biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import com.tg.lygem2.bean.User;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.RoleCondition;
import com.tg.lygem2.vo.crud.RoleOfAllMenuIds;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@RestController
@RequestMapping("/roles")
@Api("权限操作")
public class RoleController extends BaseController {

    @PostMapping("/register")
    @ApiOperation(value = "权限注册", notes = "")
    @Transactional
    public Result<?> registerRole(@RequestBody RoleOfAllMenuIds roleOfAllMenu) {
        if (roleToolService.registerRole(roleOfAllMenu)) {
            bridgService.saveRegisterRoleOfMenu(roleOfAllMenu);
            return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "权限注册成功");
        }
        return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "权限注册失败");
    }

    @PostMapping("/update")
    @ApiOperation(value = "权限更新", notes = "")
    @Transactional
    public Result<?> updateRole(@RequestBody RoleOfAllMenuIds roleOfAllMenu) {
        if (roleToolService.updateRole(roleOfAllMenu)) {
            if (roleOfAllMenu.getIsChangMenuIds()) {
                bridgService.updateRoleOFMenu(roleOfAllMenu);
            }
            return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), ErrorCodeMsgEnum.SUCESS.getMsg());
        }
        return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "权限更新失败");
    }

    @PostMapping("/getAll")
    @ApiOperation(value = "查询所有权限", notes = "")
    @Transactional
    public String findAll(@RequestBody RoleCondition roleCondition) throws JsonProcessingException {
        List<Role> all = roleToolService.findAll(roleCondition);
        PageInfo<Role> objectPageInfo = new PageInfo<>(all);
        return objectMapper.writeValueAsString(new Result<List<Role>>(ErrorCodeMsgEnum.SUCESS.getCode(), ErrorCodeMsgEnum.SUCESS.getMsg(), all, objectPageInfo.getTotal()));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除角色", notes = "")
    @Transactional
    public Result<?> delete(@RequestBody List<Integer> list) throws JsonProcessingException {
        List<User> users = userService.findUserByRoleIds(list);
        if (users != null && users.size() > 0) {
            return Result.instance(ErrorCodeMsgEnum.PARAMS_ERROR.getCode(), "该角色已绑定用户，无法删除");
        }
        if (roleToolService.deleteRole(list)) {
            bridgService.deleteRoleOFMenu(list);
            bridgService.deleteUserOfRole(list);
            return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "角色删除成功");
        }
        return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "角色删除失败");
    }

}