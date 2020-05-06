package com.tg.enterprise.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tg.enterprise.model.OutputEnergyWarning;
import com.tg.enterprise.biz.IOutputEnergyWarningBiz;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 产量与能耗预警设置 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-12-05			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/outputEnergyWarning")
public class OutputEnergyWarningController{
	
	@Autowired
    private IOutputEnergyWarningBiz outputEnergyWarningBiz;
	
	
	@ApiOperation(value = "产量与能耗预警设置单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectByYear", method = RequestMethod.GET)
    public ResponseVO<OutputEnergyWarning> selectById(@RequestBody OutputEnergyWarning energyWarning) {
       OutputEnergyWarning outputEnergyWarning = new OutputEnergyWarning();
        try {
            outputEnergyWarning = outputEnergyWarningBiz.selectByYearAndCid(energyWarning.getYear(),energyWarning.getEnterprise_id());
        } catch (Exception e) {
            log.error("selectById", e);
            return  new ResponseVO<OutputEnergyWarning>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<OutputEnergyWarning>(ErrorCode.OK, "ok", outputEnergyWarning);
    }


    @ApiOperation(value = "产量与能耗预警设置条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<OutputEnergyWarning> selectForPage(@RequestBody PageRequestVO<OutputEnergyWarning> outputEnergyWarning) {
        PageResponseVO<OutputEnergyWarning> ResponseVO = new PageResponseVO<OutputEnergyWarning>(ErrorCode.OK, "ok");
        PageInfo<OutputEnergyWarning> queryPageList = outputEnergyWarningBiz.selectForPage(outputEnergyWarning.getParams(), outputEnergyWarning.getPageIndex(), outputEnergyWarning.getPageSize());
        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }


    @ApiOperation(value = "产量与能耗预警设置新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody OutputEnergyWarning outputEnergyWarning) {
        try {
            outputEnergyWarningBiz.insert(outputEnergyWarning);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "产量与能耗预警设置更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody OutputEnergyWarning outputEnergyWarning) {
        try {
            OutputEnergyWarning warning = outputEnergyWarningBiz.selectByYearAndCid(outputEnergyWarning.getYear(), outputEnergyWarning.getEnterprise_id());
            if(warning == null) {
                outputEnergyWarningBiz.insert(outputEnergyWarning);
                return new CommonResponse(ErrorCode.OK, "ok");
            }
            outputEnergyWarningBiz.update(outputEnergyWarning);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	
	@ApiOperation(value = "产量与能耗预警设置批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Integer> ids) {
        try {
            outputEnergyWarningBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
}