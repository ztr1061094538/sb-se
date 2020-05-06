package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IStandardTrainingBiz;
import com.tg.enterprise.model.StandardTraining;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 标准规范/培训课件 VO
 *
 * @author fuwenxiang
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2019-06-13
 * =================Modify Record=================
 * @Modifier            @date			@Content fuwenxiang			2019-06-13			新增
 */

@Slf4j
@RestController
@RequestMapping("/standardTraining")
public class StandardTrainingController {

    @Autowired
    private IStandardTrainingBiz standardTrainingBiz;

    @Value("${tg.getFile.path}")
    private String getFilePath;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    @ApiOperation(value = "标准规范/培训课件单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public ResponseVO<StandardTraining> selectById(@RequestParam("id") Long id) {
        StandardTraining standardTraining;
        try {
            standardTraining = standardTrainingBiz.selectById(id);
            if (standardTraining != null) {
                if (standardTraining.getAttachment() != null) {
                    String attachmentPath = getFilePath + standardTraining.getAttachment();
                    standardTraining.setAttachmentPath(attachmentPath);
                }
                String edit_time_str = DateUtil.getLocalDateStr(standardTraining.getEdit_time(), df);
                standardTraining.setEdit_time_str(edit_time_str);
            }
        } catch (Exception e) {
            log.error("selectById", e);
            return new ResponseVO<StandardTraining>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<StandardTraining>(ErrorCode.OK, "ok", standardTraining);
    }


    @ApiOperation(value = "标准规范/培训课件条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<StandardTraining> selectForPage(@RequestBody PageRequestVO<StandardTraining> standardTraining) {
        PageResponseVO<StandardTraining> ResponseVO = new PageResponseVO<StandardTraining>(ErrorCode.OK, "ok");
        PageInfo<StandardTraining> queryPageList = standardTrainingBiz.selectForPage(standardTraining.getParams(), standardTraining.getPageIndex(), standardTraining.getPageSize());
        for (StandardTraining training : queryPageList.getList()) {
            Date publish_time = training.getEdit_time();
            String edit_time_str = DateUtil.getLocalDateStr(publish_time, df);
            training.setEdit_time_str(edit_time_str);
        }
        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }


    @ApiOperation(value = "标准规范/培训课件新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody StandardTraining standardTraining, HttpServletRequest request) {
        try {
            User user = SessionUtil.getUser(request.getSession());
            standardTraining.setEditor_id(user.getId());
            standardTraining.setEdit_time(new Date());
            standardTraining.setOrder_num(0);
            standardTrainingBiz.insert(standardTraining);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "标准规范/培训课件更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody StandardTraining standardTraining, HttpServletRequest request) {
        try {
            User user = SessionUtil.getUser(request.getSession());
            standardTraining.setEditor_id(user.getId());
            standardTraining.setEdit_time(new Date());
            standardTrainingBiz.update(standardTraining);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

//    @ApiOperation(value = "标准规范/培训课件删除接口", consumes = "application/json", response = CommonResponse.class)
//    @RequestMapping(value = "/delById", method = RequestMethod.GET)
//    public CommonResponse delById(@RequestParam Long id) {
//        try {
//            standardTrainingBiz.deleteById(id);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, SysConstants.ERROR_MESSAGE);
//        }
//        return new CommonResponse(ErrorCode.SUCESS, "ok");
//    }

    @ApiOperation(value = "标准规范/培训课件置顶接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/top", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse top(@RequestBody StandardTraining standardTraining) {
        try {
            standardTraining.setOrder_num(1);
            standardTraining.setTop_time(Long.valueOf(DateUtil.getLocalDateStr(new Date(), df2)));
            standardTrainingBiz.update(standardTraining);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "标准规范/培训课件取消置顶接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/cancelTop", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse cancelTop(@RequestBody StandardTraining standardTraining) {
        try {
            standardTraining.setOrder_num(0);
            standardTraining.setTop_time(null);
            standardTrainingBiz.update(standardTraining);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "标准规范/培训课件批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Long> ids) {
        try {
            standardTrainingBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

}