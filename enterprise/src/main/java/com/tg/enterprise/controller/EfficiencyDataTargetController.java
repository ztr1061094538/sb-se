package com.tg.enterprise.controller;

import com.tg.enterprise.biz.IEfficiencyDataTargetBiz;
import com.tg.enterprise.config.VerifyLastMonth;
import com.tg.enterprise.model.EfficiencyDataTarget;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/efficiencyDataTarget")
@Slf4j
public class EfficiencyDataTargetController {

    @Resource
    private IEfficiencyDataTargetBiz biz;


    @ApiOperation(value = "新增能效数据指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse add(@RequestBody EfficiencyDataTarget entity, HttpServletRequest request) {
        User userVO = SessionUtil.getUser(request.getSession());
        Integer enterpriseId = userVO.getCid();
        entity.setEnterprise_id(enterpriseId);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Long month = Long.valueOf(format.format(date));
        entity.setUpload_date(month);
        biz.add(entity);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "获取能效数据指标", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getList", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<EfficiencyDataTarget> getList(@RequestBody PageRequestVO<EfficiencyDataTarget> quertyVO) {
        EfficiencyDataTarget data = quertyVO.getParams();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Long month = Long.valueOf(format.format(date));
        data.setUpload_date(month);
        data.setEnterprise_id(quertyVO.getParams().getEnterprise_id());
        PageResponseVO<EfficiencyDataTarget> responseVO = new PageResponseVO<EfficiencyDataTarget>(ErrorCode.OK, "ok");
        try{
            List<EfficiencyDataTarget> page = biz.queryList(data);
            responseVO.setRows(page);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new PageResponseVO<EfficiencyDataTarget>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return responseVO;
    }

    @ApiOperation(value = "修改能效数据指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse update(@RequestBody EfficiencyDataTarget entity) {
        biz.update(entity);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "删除能效数据指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse delByIds(@RequestBody List<Long> ids) {
        biz.delByIds(ids);
        return new CommonResponse(ErrorCode.OK, "ok");
    }
}
