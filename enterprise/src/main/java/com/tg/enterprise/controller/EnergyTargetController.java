package com.tg.enterprise.controller;

import com.tg.enterprise.biz.IEnergyProtocolTypeBiz;
import com.tg.enterprise.biz.IEnergyTargetBiz;
import com.tg.enterprise.config.VerifyLastMonth;
import com.tg.enterprise.model.EnergyTarget;
import com.tg.enterprise.model.EnergyType;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 能源总量指标
 */
@RestController
@RequestMapping("/energyTarget")
@Slf4j
public class EnergyTargetController {

    @Resource
    private IEnergyTargetBiz biz;

    @Resource
    private IEnergyProtocolTypeBiz energyType;

    @ApiOperation(value = "新增上月能源总量指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse add(@RequestBody EnergyTarget entity, HttpServletRequest request) {
        User userVO = SessionUtil.getUser(request.getSession());
        Integer enterpriseId =userVO.getCid();
        entity.setEnterprise_id(enterpriseId);
        BigDecimal sum = new BigDecimal("0");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Long month = Long.valueOf(format.format(date));
        entity.setUpload_date(month);
        sum =sum.add(entity.getPurchase()).add(entity.getOpening_inventory());
        sum=sum.subtract(entity.getSupply()).subtract(entity.getEnding_inventory());
        entity.setEnergy_consumption(sum);
        EnergyTarget target = biz.selectById(entity.getEnergy_type(),entity.getUpload_date(),enterpriseId);
        if(target!=null){
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, "该月份能源品种已经存在");
        }
        biz.add(entity);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "获取企业当月能源总量指标", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getEnergyTarget", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<EnergyAllVO> getEnergyTarget(@RequestBody PageRequestVO<EnergyTarget> entity, HttpServletRequest request) {

        EnergyTarget data = entity.getParams();
        User userVO = SessionUtil.getUser(request.getSession());
        Integer enterpriseId =userVO.getCid();
        data.setEnterprise_id(enterpriseId);
        List<EnergyTarget> list = biz.queryList(data);
        BigDecimal sum = new BigDecimal("0");
        for (EnergyTarget temp:list) {
            sum =sum.add(temp.getPurchase()).add(temp.getOpening_inventory());
            sum=sum.subtract(temp.getSupply()).subtract(temp.getEnding_inventory());
        }
        EnergyAllVO vo = new EnergyAllVO();
        vo.setData_value(sum);
        return new ResponseVO<>(ErrorCode.OK, "ok",vo);
    }

    @ApiOperation(value = "获取能源总量指标", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getList", consumes = "application/json", method = RequestMethod.POST)
    public PageTableTitleVO<EnergyTarget> getList(@RequestBody PageRequestVO<EnergyTarget> entity, HttpServletRequest request) {
        PageTableTitleVO<EnergyTarget> responseVO = new PageTableTitleVO<>(ErrorCode.OK, "ok");
        EnergyTarget data = entity.getParams();
        User userVO = SessionUtil.getUser(request.getSession());
        Integer enterpriseId =userVO.getCid();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Long month = Long.valueOf(format.format(date));
        data.setUpload_date(month);
        try{
            data.setEnterprise_id(enterpriseId);
            List<EnergyTarget> list = biz.queryList(data);
            List<EnergyType> types = energyType.queryList(null);
            Map<String,EnergyType> map = new HashMap<>();
            for (EnergyType type:types ) {
                map.put(type.getCode(),type);
            }
            BigDecimal sum = new BigDecimal("0");
            for (EnergyTarget target:list ) {
                String name = map.get(target.getEnergy_type()).getName();
                String unit = map.get(target.getEnergy_type()).getUnit();
                target.setEnergy_type_name(name);
                target.setEnergy_type_unit(unit);
                sum = sum.add(target.getEnergy_consumption());
            }
            Map<String,String> value_map = new HashMap<>();
            value_map.put("data_value",sum.toString());
            responseVO.setRows(list);
            responseVO.setMap(value_map);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new PageTableTitleVO<EnergyTarget>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return responseVO;
    }

    @ApiOperation(value = "修改能源总量指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse update(@RequestBody EnergyTarget entity) {
        BigDecimal sum = new BigDecimal("0");
        sum =sum.add(entity.getPurchase()).add(entity.getOpening_inventory());
        sum=sum.subtract(entity.getSupply()).subtract(entity.getEnding_inventory());
        entity.setEnergy_consumption(sum);
        biz.update(entity);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "删除能源总量指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse delByIds(@RequestBody List<Long> ids) {

        biz.delByIds(ids);
        return new CommonResponse(ErrorCode.OK, "ok");
    }
}
