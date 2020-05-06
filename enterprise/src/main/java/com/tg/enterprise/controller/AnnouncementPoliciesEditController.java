package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IAnnouncementPoliciesEditBiz;
import com.tg.enterprise.biz.IWorkTaskInfoBiz;
import com.tg.enterprise.model.AnnouncementPoliciesEdit;
import com.tg.enterprise.model.User;
import com.tg.enterprise.model.WorkTaskInfo;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 通知公告 VO
 *
 * @author fuwenxiang
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2019-06-12
 * =================Modify Record=================
 * @Modifier @date			@Content fuwenxiang			2019-06-12			新增
 */

@Slf4j
@RestController
@RequestMapping("/announcementPoliciesEdit")
public class AnnouncementPoliciesEditController {

    @Autowired
    private IAnnouncementPoliciesEditBiz announcementPoliciesEditBiz;

    @Autowired
    private IWorkTaskInfoBiz workTaskInfoBiz;

    @Value("${tg.getFile.path}")
    private String getFilePath;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private DateTimeFormatter df3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @ApiOperation(value = "通知公告/政策法规政府端详情", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public ResponseVO<AnnouncementPoliciesEdit> selectById(@RequestParam Long id) {
        AnnouncementPoliciesEdit announcementPoliciesEdit;
        try {
            announcementPoliciesEdit = announcementPoliciesEditBiz.selectById(id);
            if (announcementPoliciesEdit != null) {
                if (StringUtils.isNotBlank(announcementPoliciesEdit.getAttachment())) {
                    String[] split = announcementPoliciesEdit.getAttachment().split(",");
                    StringBuilder sb = new StringBuilder();
                    for (String s : split) {
                        sb.append(getFilePath).append(s).append(",");
                    }
                    announcementPoliciesEdit.setAttachmentPath(sb.substring(0,sb.length()-1));
                }
                String edit_time_str = DateUtil.getLocalDateStr(announcementPoliciesEdit.getEdit_time(), df);
                announcementPoliciesEdit.setEdit_time_str(edit_time_str);
            }
        } catch (Exception e) {
            log.error("selectById", e);
            return new ResponseVO<AnnouncementPoliciesEdit>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<AnnouncementPoliciesEdit>(ErrorCode.OK, "ok", announcementPoliciesEdit);
    }


    @ApiOperation(value = "通知公告/政策法规管理列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<AnnouncementPoliciesEdit> selectForPage(@RequestBody PageRequestVO<AnnouncementPoliciesEdit> announcementPoliciesEdit) {
        PageResponseVO<AnnouncementPoliciesEdit> ResponseVO = new PageResponseVO<AnnouncementPoliciesEdit>(ErrorCode.OK, "ok");
        PageInfo<AnnouncementPoliciesEdit> queryPageList = announcementPoliciesEditBiz.selectForPage(announcementPoliciesEdit.getParams(), announcementPoliciesEdit.getPageIndex(), announcementPoliciesEdit.getPageSize());
        for (AnnouncementPoliciesEdit edit : queryPageList.getList()) {
            Date edit_time = edit.getEdit_time();
            String edit_time_str = DateUtil.getLocalDateStr(edit_time, df3);
            edit.setEdit_time_str(edit_time_str);
        }

        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }

    @ApiOperation(value = "通知列表", consumes = "application/json", response = ListResponseVO.class)
    @RequestMapping(value = "/selectList", consumes = "application/json", method = RequestMethod.POST)
    public ListResponseVO<AnnouncementPoliciesEdit> selectList(@RequestBody AnnouncementPoliciesEdit announcementPoliciesEdit) {
        ListResponseVO<AnnouncementPoliciesEdit> ResponseVO = new ListResponseVO<AnnouncementPoliciesEdit>(ErrorCode.OK, "ok");
       List<AnnouncementPoliciesEdit> queryPageList = announcementPoliciesEditBiz.selectList(announcementPoliciesEdit);
        for (AnnouncementPoliciesEdit edit : queryPageList) {
            Date edit_time = edit.getEdit_time();
            String edit_time_str = DateUtil.getLocalDateStr(edit_time, df3);
            edit.setEdit_time_str(edit_time_str);
        }
        ResponseVO.setData(queryPageList);
        return ResponseVO;
    }

    @ApiOperation(value = "通知总数", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getTotal", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<Integer> getTotal() {
        ResponseVO<Integer> ResponseVO = new ResponseVO<Integer>(ErrorCode.OK, "ok");
        AnnouncementPoliciesEdit announcementPoliciesEdit = new AnnouncementPoliciesEdit();
        announcementPoliciesEdit.setOrder_num(1);
        List<AnnouncementPoliciesEdit> queryPageList = announcementPoliciesEditBiz.selectList(announcementPoliciesEdit);
        WorkTaskInfo workTaskInfo = new WorkTaskInfo();
        workTaskInfo.setOrder_num(1);
        List<WorkTaskInfo> workTaskInfos = workTaskInfoBiz.selectList(workTaskInfo);
        ResponseVO.setData(queryPageList.size() + workTaskInfos.size());
        return ResponseVO;
    }



    @ApiOperation(value = "通知公告/政策法规新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody AnnouncementPoliciesEdit announcementPoliciesEdit, HttpServletRequest request) {
        try {
            User user = SessionUtil.getUser(request.getSession());
            announcementPoliciesEdit.setEditor_id(user.getId());
            announcementPoliciesEdit.setEdit_time(new Date());
            announcementPoliciesEdit.setOrder_num(0);
            announcementPoliciesEditBiz.insert(announcementPoliciesEdit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "通知公告/政策法规编辑接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody AnnouncementPoliciesEdit announcementPoliciesEdit, HttpServletRequest request) {
        try {
            User user = SessionUtil.getUser(request.getSession());
            announcementPoliciesEdit.setEditor_id(user.getId());
            announcementPoliciesEdit.setEdit_time(new Date());
            announcementPoliciesEditBiz.update(announcementPoliciesEdit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "通知公告/政策法规置顶接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/top", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse top(@RequestBody AnnouncementPoliciesEdit announcementPoliciesEdit) {
        try {
            announcementPoliciesEdit.setOrder_num(1);
            announcementPoliciesEdit.setTop_time(Long.valueOf(DateUtil.getLocalDateStr(new Date(), df2)));
            announcementPoliciesEditBiz.update(announcementPoliciesEdit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "通知公告/政策法规取消置顶接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/cancelTop", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse cancelTop(@RequestBody AnnouncementPoliciesEdit announcementPoliciesEdit) {
        try {
            announcementPoliciesEdit.setOrder_num(0);
            announcementPoliciesEdit.setTop_time(null);
            announcementPoliciesEditBiz.update(announcementPoliciesEdit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "通知公告/政策法规批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Long> ids) {
        try {
            announcementPoliciesEditBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }



}