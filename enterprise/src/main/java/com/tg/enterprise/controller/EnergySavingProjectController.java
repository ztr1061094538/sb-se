package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyBiz;
import com.tg.enterprise.biz.IEnergySavingProjectBiz;
import com.tg.enterprise.biz.IOutputEnergyTargetBiz;
import com.tg.enterprise.model.AnalysisCompany;
import com.tg.enterprise.model.AnalysisTerminal;
import com.tg.enterprise.model.EnergySavingProject;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 节能项目 VO
 *
 * @author fuwenxiang
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2019-11-19
 * =================Modify Record=================
 * @Modifier            @date			@Content fuwenxiang			2019-11-19			新增
 */

@Slf4j
@RestController
@RequestMapping("/energySavingProject")
public class EnergySavingProjectController {

    @Autowired
    private IEnergySavingProjectBiz energySavingProjectBiz;

    @Value("${tg.getFile.path}")
    private String getFilePath;

    @Value("${tg.getPicture.path}")
    private String getPicturePath;

    @Autowired
    private IEnergyBiz energyBiz;

    @Autowired
    private IOutputEnergyTargetBiz outputEnergyTargetBiz;

    @ApiOperation(value = "节能项目单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<EnergySavingProject> selectById(@RequestBody Integer id) {
        EnergySavingProject energySavingProject = new EnergySavingProject();
        try {
            energySavingProject = energySavingProjectBiz.selectById(id);
            if (energySavingProject != null && StringUtils.isNotBlank(energySavingProject.getUrls())) {
                String urls = energySavingProject.getUrls();
                String[] split = urls.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : split) {
                    sb.append(getFilePath).append(s).append(",");
                }
                energySavingProject.setFilePaths(sb.substring(0, sb.length() - 1));
            }
        } catch (Exception e) {
            log.error("selectById", e);
            return new ResponseVO<EnergySavingProject>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<EnergySavingProject>(ErrorCode.OK, "ok", energySavingProject);
    }


    @ApiOperation(value = "节能项目条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<EnergySavingProject> selectForPage(@RequestBody PageRequestVO<EnergySavingProject> energySavingProject) {
        PageResponseVO<EnergySavingProject> ResponseVO = new PageResponseVO<EnergySavingProject>(ErrorCode.OK, "ok");
        PageInfo<EnergySavingProject> queryPageList = energySavingProjectBiz.selectForPage(energySavingProject.getParams(), energySavingProject.getPageIndex(), energySavingProject.getPageSize());
        if (queryPageList.getList() != null && !queryPageList.getList().isEmpty()) {
            for (EnergySavingProject savingProject : queryPageList.getList()) {
                if (StringUtils.isNotBlank(savingProject.getUrls())) {
                    String urls = savingProject.getUrls();
                    String[] split = urls.split(",");
                    StringBuilder sb = new StringBuilder();
                    for (String s : split) {
                        sb.append(getFilePath).append(s).append(",");
                    }
                    savingProject.setFilePaths(sb.substring(0, sb.length() - 1));
                }
            }
        }
        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }


    @ApiOperation(value = "节能项目新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody EnergySavingProject energySavingProject) {
        try {
            energySavingProjectBiz.insert(energySavingProject);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "节能项目更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody EnergySavingProject energySavingProject) {
        try {
            energySavingProjectBiz.update(energySavingProject);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "节能项目批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Integer> ids) {
        try {
            energySavingProjectBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "节能量核算接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/accounting", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<JSONObject> accounting(@RequestBody Integer id) throws ParseException {
        ResponseVO<JSONObject> responseVO = new ResponseVO<>();
        JSONObject object = new JSONObject();
        EnergySavingProject energySavingProject = energySavingProjectBiz.selectById(id);
        if (energySavingProject == null) {
            return new ResponseVO<>(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        String terminal_ids = energySavingProject.getTerminal_ids();
        Long start_date = energySavingProject.getStart_date();
        Long end_date = energySavingProject.getEnd_date();
        String lastMonth = DateUtil.getLastMonth((start_date + "").substring(0, 6));
        String nextMonth = DateUtil.getNextMonth((end_date + "").substring(0, 6));
        String lastYearMonth = DateUtil.getMonth(start_date + "", -12).substring(0, 6);
        String nextYearMonth = DateUtil.getMonth(end_date + "", 12).substring(0, 6);

        //综合能耗
        String tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        energyQueryVO.setUpload_date(Long.valueOf(lastMonth));
        energyQueryVO.setTableName(tableName);
        List<AnalysisCompany> beforeDatas = energyBiz.getCompanyList(energyQueryVO);
        BigDecimal beforeCons = BigDecimal.valueOf(0);
        if (beforeDatas != null && beforeDatas.size() > 0) {
            beforeCons = beforeDatas.get(0).getData_value();
        }

        energyQueryVO.setUpload_date(Long.valueOf(nextMonth));
        BigDecimal afterCons = BigDecimal.valueOf(0);
        List<AnalysisCompany> afterDatas = energyBiz.getCompanyList(energyQueryVO);
        if (afterDatas != null && afterDatas.size() > 0) {
            afterCons = afterDatas.get(0).getData_value();
        }
        JSONObject consObject = new JSONObject();
        consObject.put("before", beforeCons);
        consObject.put("after", afterCons);
        object.put("sumConsumption", consObject);

        //产量
        TargetQueryVO targetQueryVO = new TargetQueryVO();
        targetQueryVO.setStartTime(Long.valueOf(lastMonth));
        targetQueryVO.setEndTime(Long.valueOf(lastMonth));
        BigDecimal beforeOutput = BigDecimal.valueOf(0);
        List<TargetRespVO> beforeOutputs = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        if (beforeOutputs != null && beforeOutputs.size() > 0) {
            beforeOutput = beforeOutputs.get(0).getSumYield();
        }

        targetQueryVO.setStartTime(Long.valueOf(nextMonth));
        targetQueryVO.setEndTime(Long.valueOf(nextMonth));
        BigDecimal afterOutput = BigDecimal.valueOf(0);
        List<TargetRespVO> afterOutputs = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        if (afterOutputs != null && afterOutputs.size() > 0) {
            afterOutput = afterOutputs.get(0).getSumYield();
        }
        JSONObject yieldObject = new JSONObject();
        yieldObject.put("before", beforeOutput);
        yieldObject.put("after", afterOutput);
        object.put("sumYield", yieldObject);

        //单位产品能耗
        JSONObject unitYieldObject = new JSONObject();
        unitYieldObject.put("before", "-");
        unitYieldObject.put("after", "-");
        if (beforeOutput.compareTo(BigDecimal.valueOf(0)) != 0) {
            unitYieldObject.put("before", beforeCons.divide(beforeOutput, 2, BigDecimal.ROUND_HALF_UP));
        }
        if (afterOutput.compareTo(BigDecimal.valueOf(0)) != 0) {
            unitYieldObject.put("after", afterCons.divide(afterOutput, 2, BigDecimal.ROUND_HALF_UP));
        }
        object.put("unitYield", unitYieldObject);

        JSONObject monthObject = new JSONObject();
        JSONObject yearObject = new JSONObject();
        String[] split = terminal_ids.split(",");
        Set<String> terminalIdSet = new HashSet<>(Arrays.asList(split));
        EnergyQueryVO queryVO = new EnergyQueryVO();
        if (StringUtils.isNotBlank(terminal_ids)) {
            tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.TERMINAL);
            queryVO.setTerminalIdSet(terminalIdSet);
        } else {
            //没有关联检测点 取关口表数据
            tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
        }
        queryVO.setTableName(tableName);
        queryVO.setUpload_date(Long.valueOf(lastMonth));
        AnalysisTerminal beforeData = energyBiz.getSumTerminalConsumption(queryVO);

        queryVO.setUpload_date(Long.valueOf(nextMonth));
        AnalysisTerminal afterData = energyBiz.getSumTerminalConsumption(queryVO);

        //月节能量
        monthObject.put("before", 0);
        monthObject.put("after", "-");
        if (afterData != null && beforeData != null) {
            monthObject.put("after", afterData.getData_value().subtract(beforeData.getData_value()));
        }
        object.put("monthEnergySaving", monthObject);

        //年节能量
        yearObject.put("before", 0);
        yearObject.put("after", "-");
        if (Long.valueOf(nextYearMonth) >= DateUtil.getYMLongTime(new Date())) {
            queryVO.setUpload_date(null);
            queryVO.setStartDate(Long.valueOf(lastYearMonth));
            queryVO.setEndDate(Long.valueOf(lastMonth));
            AnalysisTerminal beforeYearData = energyBiz.getSumTerminalConsumption(queryVO);

            queryVO.setStartDate(Long.valueOf(nextMonth));
            queryVO.setEndDate(Long.valueOf(nextYearMonth));
            AnalysisTerminal nextYearData = energyBiz.getSumTerminalConsumption(queryVO);
            if (nextYearData != null && beforeYearData != null) {
                yearObject.put("after", nextYearData.getData_value().subtract(beforeYearData.getData_value()));
            }
        }
        object.put("yearEnergySaving", yearObject);

        responseVO.setData(object);
        return responseVO;
    }

}