package com.tg.enterprise.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import com.tg.enterprise.biz.ISysDictionaryBiz;
import com.tg.enterprise.config.ServerContext;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.constant.UserTypeEnum;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tg.enterprise.model.Device;
import com.tg.enterprise.biz.IDeviceBiz;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备档案 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController{
	
	@Autowired
    private IDeviceBiz deviceBiz;

    @Resource
    private ISysDictionaryBiz sysDictionaryBiz;

    @Autowired
    private ThreadLocal<Result<Object>> threadLocal;
	
	@ApiOperation(value = "设备档案单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public ResponseVO<Device> selectById(@RequestParam("id") Integer id) {
        Device device = new Device();
        try {
            device = deviceBiz.selectById(id);
            device.setTypeName(ServerContext.dictMap.get(device.getType()).getName());
        } catch (Exception e) {
            log.error("selectById", e);
            return  new ResponseVO<Device>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<Device>(ErrorCode.OK, "ok", device);
    }


    @ApiOperation(value = "设备档案条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<Device> selectForPage(@RequestBody PageRequestVO<Device> device) {
        PageResponseVO<Device> ResponseVO = new PageResponseVO<Device>(ErrorCode.OK, "ok");
        ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
        if(user.getUser_type() == UserTypeEnum.company.getType() || user.getUser_type() == UserTypeEnum.company_read.getType()) {
            device.getParams().setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
        }
        PageInfo<Device> queryPageList = deviceBiz.selectForPage(device.getParams(), device.getPageIndex(), device.getPageSize());
        if(queryPageList.getList() != null && !queryPageList.getList().isEmpty()) {
            for (Device device1 : queryPageList.getList()) {
                if(device1.getType() != null) {
                    CodeNode codeNode = ServerContext.dictMap.get(device1.getType());
                    device1.setTypeName(codeNode == null ? "" : codeNode.getName());
                }
            }
        }
        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }


    @ApiOperation(value = "设备档案新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody Device device) {
        try {
            ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
            device.setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
            deviceBiz.insert(device);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "设备档案更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody Device device) {
        try {
            deviceBiz.update(device);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	
	@ApiOperation(value = "设备档案批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Integer> ids) {
        try {
            deviceBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
}