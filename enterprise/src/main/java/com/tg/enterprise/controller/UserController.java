package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnterpriseBiz;
import com.tg.enterprise.biz.IUserService;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.MD5UtilTool;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-12-05
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IEnterpriseBiz enterpriseBiz;

    @ApiOperation(value = "角色列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<User> selectForPage(@RequestBody PageRequestVO<User> requestVO) {
        PageResponseVO<User> pageResponseVO = new PageResponseVO();
        PageInfo<User> queryPageList = userService.selectForPage(requestVO.getParams(), requestVO.getPageIndex(), requestVO.getPageSize());
        pageResponseVO.setRows(queryPageList.getList());
        pageResponseVO.setTotal(queryPageList.getTotal());
        return pageResponseVO;
    }

    @ApiOperation(value = "添加角色", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse add(@RequestBody User user) {
        User user1 = userService.selectByLoginName(user.getLoginName());
        if (user1 != null) {
            return new CommonResponse(ErrorCode.INVALID_PARAM, "用户名已存在");
        }
        if (!user.getLoginPass().equals(user.getVerifyLoginPass())) {
            return new CommonResponse(ErrorCode.PASSWORD_ERROR, "两次输入的密码不相同");
        }
        user.setLoginPass(MD5UtilTool.MD5(user.getLoginPass()));
        user.setRole("1");
        user.setCid(enterpriseBiz.getList(null).get(0).getId());
        userService.insert(user);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "修改角色", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody User user) {
        User user1 = userService.selectByLoginName(user.getLoginName());
        if (user1 != null && !user1.getId().equals(user.getId())) {
            return new CommonResponse(ErrorCode.LOGIN_NAME_HAS_EXISTED, "用户名已存在");
        }
        if (!user.getLoginPass().equals(user.getVerifyLoginPass())) {
            return new CommonResponse(ErrorCode.PASSWORD_ERROR, "两次输入的密码不相同");
        }
        user.setLoginPass(MD5UtilTool.MD5(user.getLoginPass()));
        userService.update(user);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "修改角色", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResponse update(@RequestParam("id") Long id) {
        userService.delete(id);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

}
