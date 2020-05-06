package com.tg.enterprise.controller;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

import com.tg.enterprise.model.AnnouncementPoliciesEdit;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.adapt.VerifySecurity;
import com.tg.lygem2.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tg.enterprise.model.WorkTaskInfo;
import com.tg.enterprise.biz.IWorkTaskInfoBiz;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 工作任务 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-29
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-29			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/workTaskInfo")
public class WorkTaskInfoController{
	
	@Autowired
    private IWorkTaskInfoBiz workTaskInfoBiz;

    @Autowired
    private ThreadLocal<Result<Object>> threadLocal;

    private DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	
	@ApiOperation(value = "工作任务单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<WorkTaskInfo> selectById(@RequestBody Long id) {
        WorkTaskInfo workTaskInfo = new WorkTaskInfo();
        try {
            workTaskInfo = workTaskInfoBiz.selectById(id);
        } catch (Exception e) {
            log.error("selectById", e);
            return  new ResponseVO<WorkTaskInfo>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<WorkTaskInfo>(ErrorCode.OK, "ok", workTaskInfo);
    }

    @VerifySecurity
    @ApiOperation(value = "工作任务条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<WorkTaskInfo> selectForPage(@RequestBody PageRequestVO<WorkTaskInfo> workTaskInfo) {
        PageResponseVO<WorkTaskInfo> ResponseVO = new PageResponseVO<WorkTaskInfo>(ErrorCode.OK, "ok");
        PageInfo<WorkTaskInfo> queryPageList = workTaskInfoBiz.selectForPage(workTaskInfo.getParams(), workTaskInfo.getPageIndex(), workTaskInfo.getPageSize());
        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }

    @ApiOperation(value = "工作任务条件分页列表", consumes = "application/json", response = ListResponseVO.class)
    @RequestMapping(value = "/selectList", consumes = "application/json", method = RequestMethod.POST)
    public ListResponseVO<WorkTaskInfo> selectList(@RequestBody WorkTaskInfo workTaskInfo) {
        ListResponseVO<WorkTaskInfo> ResponseVO = new ListResponseVO<WorkTaskInfo>(ErrorCode.OK, "ok");
        List<WorkTaskInfo> queryPageList = workTaskInfoBiz.selectList(workTaskInfo);
        ResponseVO.setData(queryPageList);
        return ResponseVO;
    }


    @ApiOperation(value = "工作任务新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody WorkTaskInfo workTaskInfo) {
        try {
            workTaskInfoBiz.insert(workTaskInfo);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "工作任务更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody WorkTaskInfo workTaskInfo) {
        try {
            workTaskInfoBiz.update(workTaskInfo);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	
	@ApiOperation(value = "工作任务批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Long> ids) {
        try {
            workTaskInfoBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "通知公告/政策法规置顶接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/top", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse top(@RequestBody WorkTaskInfo workTaskInfo) {
        try {
            workTaskInfo.setOrder_num(1);
            workTaskInfo.setTop_time(Long.valueOf(DateUtil.getLocalDateStr(new Date(), df2)));
            workTaskInfoBiz.update(workTaskInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "通知公告/政策法规取消置顶接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/cancelTop", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse cancelTop(@RequestBody WorkTaskInfo workTaskInfo) {
        try {
            workTaskInfo.setOrder_num(0);
            workTaskInfo.setTop_time(null);
            workTaskInfoBiz.update(workTaskInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

}