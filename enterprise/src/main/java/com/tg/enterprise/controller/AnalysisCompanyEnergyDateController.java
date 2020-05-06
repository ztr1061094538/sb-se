package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tg.enterprise.biz.IAnalysisCompanyHourBiz;
import com.tg.enterprise.biz.IEnterpriseBiz;
import com.tg.enterprise.model.EnergyType;
import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.util.EnergyTypeEnum;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.adapt.VerifySecurity;
import com.tg.lygem2.constant.UserTypeEnum;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangjianbo on 2018/4/11
 */
@RestController
@RequestMapping("/companyAnalysis/")
@Slf4j
public class AnalysisCompanyEnergyDateController {

    @Autowired
    private IAnalysisCompanyHourBiz iAnalysisCompanyHourBiz;

    @Autowired
    private IEnterpriseBiz enterpriseBiz;

    @Autowired
    private ThreadLocal<Result<Object>> threadLocal;

    private static final int COMPANY_TYPE = 1;

    @VerifySecurity
    @ApiOperation(value = "企业当日实时能耗", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/chart/line/date",method = RequestMethod.GET)
    public ResponseVO<LineVO<Double>> getDataByCompany(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name="enterprise_id", required = false) Integer enterprise_id,
            @RequestParam(name = "type", required = false) String type) throws IllegalAccessException {
        LineVO<Double> result = new LineVO<>();
        ChartDataVO<Double> chartDataVO = new ChartDataVO<>();
        Long nowDate = DateUtil.getYMDLongTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd号");
        Long startTime = Long.valueOf(String.valueOf(nowDate).concat("00"));
        Long endTime = Long.valueOf(String.valueOf(nowDate).concat("23"));
        result.setKey(key);
        EnergyDataCode dataCode = EnergyDataCode.parse(type);
        List<Integer> idList = new ArrayList<>();
        idList.add(enterprise_id);
       /* Enterprise  enterprise = iEnterpriseBiz.selectById(enterprise_id);
        if(enterprise == null) {
        	new ResponseVO<LineVO<Double>>(ErrorCode.INNER_ERROR, "企业不存在", result);
        }*/
        String energyTypeName = "";
        switch (dataCode)
        {
            case POWER:
            	energyTypeName = "电量";
                chartDataVO = iAnalysisCompanyHourBiz.getDataByPower(idList,type,startTime,endTime,nowDate);
                break;
            case WATER:
                chartDataVO = iAnalysisCompanyHourBiz.getDataByWater(idList,type,startTime,endTime,nowDate);
             	energyTypeName = "水量";
                break;
            case GAS:
                chartDataVO = iAnalysisCompanyHourBiz.getDataByGas(idList,type,startTime,endTime,nowDate);
             	energyTypeName = "天然气";
                break;
            case STEAM:
                chartDataVO = iAnalysisCompanyHourBiz.getDataBySteam(idList,type,startTime,endTime,nowDate);
             	energyTypeName = "热量";
                break;
            case FUELOIL:
                chartDataVO = iAnalysisCompanyHourBiz.getDataByFueloil(idList,type,startTime,endTime,nowDate);
                energyTypeName = "燃料油";
                break;
            default:
                break;
        }
        EnterpriseQueryVO enterpriseReq = new EnterpriseQueryVO();
        enterpriseReq.setEnterprise_id(enterprise_id);
        List<Enterprise> list = enterpriseBiz.getList(enterpriseReq);
        chartDataVO.setTitle(sdf.format(new Date())+ list.get(0).getName() +energyTypeName+"曲线图");
        result.setChartData(chartDataVO);
        return new ResponseVO<LineVO<Double>>(ErrorCode.OK, "ok", result);
    }


    @ApiOperation(value = "当日实时能耗表格数据", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/chart/table/date",method = RequestMethod.POST)
    public PageResponseVO<JSONObject> getTableDataByCompany(
            @RequestBody PageRequestVO<AnalysisCompanyHourVo> data) throws IllegalAccessException {
        PageResponseVO<JSONObject> responseVO = new PageResponseVO<JSONObject>();
        Integer enterprise_id = data.getParams().getEnterprise_id();
        if (enterprise_id == null || enterprise_id.equals("")) {
            log.info("能耗预警   enterprise_id 空");
            return responseVO;
        }
        List<JSONObject> list = new ArrayList<>();
        Long nowDate = DateUtil.getYMDLongTime(new Date());
        Long startTime = Long.valueOf(String.valueOf(nowDate).concat("00"));
        Long endTime = Long.valueOf(String.valueOf(nowDate).concat("23"));
        EnergyDataCode dataCode = EnergyDataCode.parse(data.getParams().getType());
        List<Integer> idList = new ArrayList<>();
        idList.add(enterprise_id);

        switch (dataCode)
        {
            case POWER:
                list = iAnalysisCompanyHourBiz.getTableDataByPower(idList,data.getParams().getType(),startTime,endTime,nowDate);
                break;
            case WATER:
                list = iAnalysisCompanyHourBiz.getTableDataByWater(idList,data.getParams().getType(),startTime,endTime,nowDate);
                break;
            case GAS:
                list = iAnalysisCompanyHourBiz.getTableDataByGas(idList,data.getParams().getType(),startTime,endTime,nowDate);
                break;
            case STEAM:
                list = iAnalysisCompanyHourBiz.getTableDataBySteam(idList,data.getParams().getType(),startTime,endTime,nowDate);
                break;
            case FUELOIL:
                list = iAnalysisCompanyHourBiz.getTableDataByfueloil(idList,data.getParams().getType(),startTime,endTime,nowDate);
                break;
            default:
                break;
        }
        responseVO.setCode(0);
        responseVO.setMsg("ok");
        responseVO.setTotal(list.size());
        responseVO.setRows(list);
        return responseVO;
    }

    @ApiOperation(value = "能源种类下拉（有实时能耗）", response = ResponseVO.class)
    @RequestMapping(value = "/getEnergyTypes", method = RequestMethod.GET)
    public ResponseVO<SysDictionaryVO<CodeVO>> getEnergyTypes() {
        SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
        List<CodeVO> list = new ArrayList<CodeVO>();
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            if(EnergyTypeEnum.ENERGY.getDataCode().equals(value.getDataCode()) ||
                    EnergyTypeEnum.COAL.getDataCode().equals(value.getDataCode()) ||
                    EnergyTypeEnum.OTHER.getDataCode().equals(value.getDataCode())) {
                continue;
            }
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(value.getDataCode());
            codeVO.setName(value.getName());
            list.add(codeVO);
        }
        data.setList(list);
        return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
    }

    @ApiOperation(value = "能源种类下拉（无综合能耗）", response = ResponseVO.class)
    @RequestMapping(value = "/getEnergyTypeList", method = RequestMethod.GET)
    public ResponseVO<SysDictionaryVO<CodeVO>> getEnergyTypeList() {
        SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
        List<CodeVO> list = new ArrayList<CodeVO>();
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            if(EnergyTypeEnum.ENERGY.getDataCode().equals(value.getDataCode())) {
                continue;
            }
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(value.getDataCode());
            codeVO.setName(value.getName());
            list.add(codeVO);
        }
        data.setList(list);
        return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
    }

    @ApiOperation(value = "能源种类下拉", response = ResponseVO.class)
    @RequestMapping(value = "/getAllEnergyTypes", method = RequestMethod.GET)
    public ResponseVO<SysDictionaryVO<CodeVO>> getAllEnergyTypes() {
        SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
        List<CodeVO> list = new ArrayList<CodeVO>();
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(value.getDataCode());
            codeVO.setName(value.getName());
            list.add(codeVO);
        }
        data.setList(list);
        return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
    }

}
