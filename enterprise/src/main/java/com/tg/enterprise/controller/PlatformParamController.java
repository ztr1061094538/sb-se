package com.tg.enterprise.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tg.enterprise.model.PlatformParam;
import com.tg.enterprise.biz.IPlatformParamBiz;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 上传参数 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-12			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/platformParam/")
public class PlatformParamController{
	
	@Autowired
    private IPlatformParamBiz platformParamBiz;

    /*@ApiOperation(value = "上传参数列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectList", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<List<PlatformParam>>  selectList() {
        ResponseVO<List<PlatformParam>> responseVO = new ResponseVO<List<PlatformParam>>(ErrorCode.OK, "ok");
        List<PlatformParam> platformParams = platformParamBiz.selectList(null);
        responseVO.setData(platformParams);
        return responseVO;
    }*/

    @ApiOperation(value = "上传参数列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    public String  selectList() {
        ResponseVO<List<PlatformParam>> responseVO = new ResponseVO<List<PlatformParam>>(ErrorCode.OK, "ok");
        List<PlatformParam> platformParams = platformParamBiz.selectList(null);
        JSONObject object = new JSONObject();
        for (PlatformParam platformParam : platformParams) {
            object.put(platformParam.getName(), platformParam.getValue() == null ? "" : platformParam.getValue());
        }
        return object.toJSONString();
    }


    @ApiOperation(value = "上传参数新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody PlatformParam platformParam) {
        try {
            platformParamBiz.insert(platformParam);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "上传参数更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody JSONObject object) {
        try {
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                PlatformParam platformParam = new PlatformParam();
                platformParam.setName(entry.getKey());
                platformParam.setValue(entry.getValue()+"");
                PlatformParam param = platformParamBiz.selectById(entry.getKey());
                if(param != null) {
                    platformParamBiz.update(platformParam);
                } else {
                    platformParamBiz.insert(platformParam);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
}