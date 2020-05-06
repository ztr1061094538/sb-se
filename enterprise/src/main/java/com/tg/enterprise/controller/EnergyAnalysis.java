package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tg.enterprise.biz.IEnergyBiz;
import com.tg.enterprise.biz.IEnergyTargetBiz;
import com.tg.enterprise.biz.IOutputEnergyTargetBiz;
import com.tg.enterprise.biz.IOutputEnergyWarningBiz;
import com.tg.enterprise.model.AnalysisCompany;
import com.tg.enterprise.model.AnalysisTerminal;
import com.tg.enterprise.model.OutputEnergyWarning;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-11-25
 **/
@RestController
@RequestMapping("/energyAnalysis")
@Slf4j
public class EnergyAnalysis {

    @Autowired
    IEnergyTargetBiz energyTargetBiz;

    @Autowired
    IOutputEnergyTargetBiz outputEnergyTargetBiz;

    @Autowired
    IEnergyBiz energyBiz;

    @Autowired
    IOutputEnergyWarningBiz outputEnergyWarningBiz;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy年MM月");

    @ApiOperation(value = "近六个月产量与能耗曲线", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getOutputAndConsumptionChart", method = RequestMethod.GET)
    public ResponseVO<LineVO<BigDecimal>> getOutputAndConsumptionChart(@RequestParam(name = "enterprise_id", required = true) Integer enterprise_id) {
        ResponseVO<LineVO<BigDecimal>> responseVO = new ResponseVO();
        if (enterprise_id == null || enterprise_id.equals("")) {
            log.info("近六个月产量与能耗曲线   enterprise_id 空");
            return responseVO;
        }

        LineVO<BigDecimal> result = new LineVO<>();
        ChartDataVO<BigDecimal> chartVo = new ChartDataVO<BigDecimal>();
        List<String> categories = new ArrayList<String>();
        List<ChartKeyValuePairVO<BigDecimal>> series = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> consumptionPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> yieldPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> outputPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> consumptions = new ArrayList<>();
        List<BigDecimal> yields = new ArrayList<>();
        List<BigDecimal> outputs = new ArrayList<>();


        List<Integer> idList = new ArrayList<>();
        idList.add(enterprise_id);
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(enterprise_id + "");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(c.getTime());

        c.add(Calendar.MONTH, -5);
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(c.getTime());

        TargetQueryVO targetQueryVO = new TargetQueryVO();
        targetQueryVO.setEnterpriseIds(idList);
        targetQueryVO.setStartTime(DateUtil.getYMLongTime(startTime.getTime()));
        targetQueryVO.setEndTime(DateUtil.getYMLongTime(endTime.getTime()));

        //能耗
        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        String tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
        energyQueryVO.setTableName(tableName);
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(startTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(endTime.getTime()));
        energyQueryVO.setTerminalIdSet(terminalIdSet);
        List<AnalysisCompany> companyList = energyBiz.getCompanyList(energyQueryVO);
        //List<TargetRespVO> consumptionList = energyTargetBiz.selectSumConsumption(targetQueryVO);
        Map<Long, BigDecimal> dateConsumptionMap = new HashMap<>();
        for (AnalysisCompany analysisCompany : companyList) {
            dateConsumptionMap.put(analysisCompany.getUpload_date(), analysisCompany.getData_value().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));
        }

        //产量产值
        List<TargetRespVO> outputList = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        Map<Long, TargetRespVO> dateOutputMap = new HashMap<>();
        for (TargetRespVO targetRespVO : outputList) {
            dateOutputMap.put(targetRespVO.getUploadTime(), targetRespVO);
        }

        while (!startTime.after(endTime)) {
            String uploadTime = DateUtil.getLocalDateStr(startTime.getTime(), formatter);
            categories.add(uploadTime);
            consumptions.add(dateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime())));
            TargetRespVO targetRespVO = dateOutputMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            if (targetRespVO == null) {
                yields.add(null);
                outputs.add(null);
            } else {
                yields.add(targetRespVO.getSumYield());
                outputs.add(targetRespVO.getSumOutput());
            }

            startTime.add(Calendar.MONTH, 1);
        }

        consumptionPairVO.setName("能耗");
        consumptionPairVO.setData(consumptions);
        yieldPairVO.setName("产量");
        yieldPairVO.setData(yields);
        outputPairVO.setName("产值");
        outputPairVO.setData(outputs);
        series.add(consumptionPairVO);
        series.add(yieldPairVO);
        series.add(outputPairVO);
        chartVo.setSeries(series);
        chartVo.setCategories(categories);
        List<String> yzTitles = new ArrayList<>();
        yzTitles.add("吨标煤");
        yzTitles.add("吨");
        yzTitles.add("万元");
        chartVo.setYzTitles(yzTitles);
        result.setChartData(chartVo);
        responseVO.setData(result);
        return responseVO;
    }

    @ApiOperation(value = "近六个月能耗-产量表格数据", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getYieldTable", method = RequestMethod.POST)
    public PageResponseVO<JSONArray> getYieldTable(@RequestParam(name = "enterprise_id", required = true) Integer enterprise_id) {
        PageResponseVO<JSONArray> pageResponseVO = new PageResponseVO<>();
        if (enterprise_id == null || enterprise_id.equals("")) {
            log.info("近六个月能耗-产量表格数据   enterprise_id  is null");
            return pageResponseVO;
        }
        List<Integer> idList = new ArrayList<>();
        idList.add(enterprise_id);
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(enterprise_id + "");

        Calendar c = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        endTime.setTime(c.getTime());

        Calendar lastEndTime = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        lastEndTime.setTime(c.getTime());

        Calendar lastStartTime = Calendar.getInstance();
        c.add(Calendar.MONTH, -5);
        lastStartTime.setTime(c.getTime());

        c.add(Calendar.YEAR, 1);
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(c.getTime());

        TargetQueryVO targetQueryVO = new TargetQueryVO();
        //targetQueryVO.setEnterpriseId();
        targetQueryVO.setStartTime(DateUtil.getYMLongTime(startTime.getTime()));
        targetQueryVO.setEndTime(DateUtil.getYMLongTime(endTime.getTime()));
        targetQueryVO.setEnterpriseIds(idList);
        //能耗
        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        String tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
        energyQueryVO.setTableName(tableName);
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(startTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(endTime.getTime()));
        energyQueryVO.setTerminalIdSet(terminalIdSet);
        List<AnalysisCompany> companyList = energyBiz.getCompanyList(energyQueryVO);
        //List<TargetRespVO> consumptionList = energyTargetBiz.selectSumConsumption(targetQueryVO);
        Map<Long, BigDecimal> dateConsumptionMap = new HashMap<>();
        for (AnalysisCompany analysisCompany : companyList) {
            dateConsumptionMap.put(analysisCompany.getUpload_date(), analysisCompany.getData_value().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));
        }


        //产量产值
        List<TargetRespVO> outputList = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        Map<Long, TargetRespVO> dateOutputMap = new HashMap<>();
        for (TargetRespVO targetRespVO : outputList) {
            dateOutputMap.put(targetRespVO.getUploadTime(), targetRespVO);
        }

        targetQueryVO.setStartTime(DateUtil.getYMLongTime(lastStartTime.getTime()));
        targetQueryVO.setEndTime(DateUtil.getYMLongTime(lastEndTime.getTime()));

        //去年能耗
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(lastStartTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(lastEndTime.getTime()));
        List<AnalysisCompany> lastCompanyList = energyBiz.getCompanyList(energyQueryVO);
        Map<Long, BigDecimal> lastDateConsumptionMap = new HashMap<>();
        for (AnalysisCompany analysisCompany : lastCompanyList) {
            lastDateConsumptionMap.put(analysisCompany.getUpload_date(), analysisCompany.getData_value().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));
        }

        //去年产量产值
        List<TargetRespVO> lastOutputList = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        Map<Long, TargetRespVO> lastDateOutputMap = new HashMap<>();
        for (TargetRespVO targetRespVO : lastOutputList) {
            lastDateOutputMap.put(targetRespVO.getUploadTime(), targetRespVO);
        }


        JSONArray jsonArray = new JSONArray();
        JSONArray consumptionArray = new JSONArray();
        JSONArray yieldArray = new JSONArray();
        JSONArray ratioArray = new JSONArray();
        while (!startTime.after(endTime)) {
            JSONObject consumptionObject = new JSONObject();
            JSONObject yieldObject = new JSONObject();
            JSONObject ratioObject = new JSONObject();

            String uploadTime = DateUtil.getLocalDateStr(startTime.getTime(), formatter1);
            BigDecimal consumption = dateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime())) == null ? BigDecimal.valueOf(0) :
                    dateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            BigDecimal lastConsumption = lastDateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime())) == null ? BigDecimal.valueOf(0) :
                    lastDateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            JSONObject object = new JSONObject();
            object.put("thisYear", consumption);
            object.put("lastYear", lastConsumption);
            consumptionObject.put(uploadTime, object);
            TargetRespVO targetRespVO = dateOutputMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            TargetRespVO lastTargetRespVO = lastDateOutputMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjectOfRatio = new JSONObject();
            if (targetRespVO == null) {
                jsonObject.put("thisYear", "-");
                jsonObjectOfRatio.put("thisYear", "-");
            } else {
                jsonObject.put("thisYear", targetRespVO.getSumYield());
                jsonObjectOfRatio.put("thisYear", targetRespVO.getSumYield().compareTo(BigDecimal.valueOf(0)) == 0 ?
                        "-" : consumption.divide(targetRespVO.getSumYield(), 1, BigDecimal.ROUND_HALF_UP));
            }
            if (lastTargetRespVO == null) {
                jsonObject.put("lastYear", "-");
                jsonObjectOfRatio.put("lastYear", "-");
            } else {
                jsonObject.put("lastYear", lastTargetRespVO.getSumYield());
                jsonObjectOfRatio.put("lastYear", lastTargetRespVO.getSumYield().compareTo(BigDecimal.valueOf(0)) == 0 ?
                        "-" : consumption.divide(lastTargetRespVO.getSumYield(), 1, BigDecimal.ROUND_HALF_UP));
            }
            yieldObject.put(uploadTime, jsonObject);
            ratioObject.put(uploadTime, jsonObjectOfRatio);

            consumptionArray.add(consumptionObject);
            yieldArray.add(yieldObject);
            ratioArray.add(ratioObject);
            startTime.add(Calendar.MONTH, 1);
        }

        jsonArray.add(consumptionArray);
        jsonArray.add(yieldArray);
        jsonArray.add(ratioArray);
        pageResponseVO.setRows(jsonArray.toJavaList(JSONArray.class));
        pageResponseVO.setTotal(jsonArray.size());
        return pageResponseVO;
    }

    @ApiOperation(value = "近六个月能耗-产值表格数据", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getOutputTable", method = RequestMethod.POST)
    public PageResponseVO<JSONArray> getOutputTable(@RequestParam(name = "enterprise_id", required = true) Integer enterprise_id) {
        PageResponseVO<JSONArray> pageResponseVO = new PageResponseVO<>();
        if(enterprise_id==null || enterprise_id.equals("")){
            log.info("近六个月能耗-产值表格数据   enterprise_id is null");
            return  pageResponseVO;
        }
        List<Integer> idList = new ArrayList<>();
        idList.add(enterprise_id);
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(enterprise_id + "");
        Calendar c = Calendar.getInstance();

        Calendar endTime = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        endTime.setTime(c.getTime());

        Calendar lastEndTime = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        lastEndTime.setTime(c.getTime());

        Calendar lastStartTime = Calendar.getInstance();
        c.add(Calendar.MONTH, -5);
        lastStartTime.setTime(c.getTime());

        c.add(Calendar.YEAR, 1);
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(c.getTime());

        TargetQueryVO targetQueryVO = new TargetQueryVO();
        //targetQueryVO.setEnterpriseId();
        targetQueryVO.setStartTime(DateUtil.getYMLongTime(startTime.getTime()));
        targetQueryVO.setEndTime(DateUtil.getYMLongTime(endTime.getTime()));
        targetQueryVO.setEnterpriseIds(idList);
        //能耗
        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        String tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
        energyQueryVO.setTableName(tableName);
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(startTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(endTime.getTime()));
        energyQueryVO.setTerminalIdSet(terminalIdSet);
        List<AnalysisCompany> companyList = energyBiz.getCompanyList(energyQueryVO);
        Map<Long, BigDecimal> dateConsumptionMap = new HashMap<>();
        for (AnalysisCompany analysisCompany : companyList) {
            dateConsumptionMap.put(analysisCompany.getUpload_date(), analysisCompany.getData_value().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));
        }

        //产量产值
        List<TargetRespVO> outputList = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        Map<Long, TargetRespVO> dateOutputMap = new HashMap<>();
        for (TargetRespVO targetRespVO : outputList) {
            dateOutputMap.put(targetRespVO.getUploadTime(), targetRespVO);
        }

        targetQueryVO.setStartTime(DateUtil.getYMLongTime(lastStartTime.getTime()));
        targetQueryVO.setEndTime(DateUtil.getYMLongTime(lastEndTime.getTime()));

        //去年能耗
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(lastStartTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(lastEndTime.getTime()));
        List<AnalysisCompany> lastCompanyList = energyBiz.getCompanyList(energyQueryVO);
        Map<Long, BigDecimal> lastDateConsumptionMap = new HashMap<>();
        for (AnalysisCompany analysisCompany : lastCompanyList) {
            lastDateConsumptionMap.put(analysisCompany.getUpload_date(), analysisCompany.getData_value().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));
        }

        //去年产量产值
        List<TargetRespVO> lastOutputList = outputEnergyTargetBiz.selectOutputList(targetQueryVO);
        Map<Long, TargetRespVO> lastDateOutputMap = new HashMap<>();
        for (TargetRespVO targetRespVO : lastOutputList) {
            lastDateOutputMap.put(targetRespVO.getUploadTime(), targetRespVO);
        }

        JSONArray jsonArray = new JSONArray();
        JSONArray consumptionArray = new JSONArray();
        JSONArray outputArray = new JSONArray();
        JSONArray ratioArray = new JSONArray();
        while (!startTime.after(endTime)) {
            JSONObject consumptionObject = new JSONObject();
            JSONObject outputObject = new JSONObject();
            JSONObject ratioObject = new JSONObject();

            String uploadTime = DateUtil.getLocalDateStr(startTime.getTime(), formatter1);
            BigDecimal consumption = dateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime())) == null ? BigDecimal.valueOf(0) :
                    dateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            BigDecimal lastConsumption = lastDateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime())) == null ? BigDecimal.valueOf(0) :
                    lastDateConsumptionMap.get(DateUtil.getYMLongTime(startTime.getTime()));

            JSONObject object = new JSONObject();
            object.put("thisYear", consumption);
            object.put("lastYear", lastConsumption);
            consumptionObject.put(uploadTime, object);
            TargetRespVO targetRespVO = dateOutputMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            TargetRespVO lastTargetRespVO = lastDateOutputMap.get(DateUtil.getYMLongTime(startTime.getTime()));
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjectOfRatio = new JSONObject();
            if (targetRespVO == null) {
                jsonObject.put("thisYear", "-");
                jsonObjectOfRatio.put("thisYear", "-");
            } else {
                jsonObject.put("thisYear", targetRespVO.getSumOutput());
                jsonObjectOfRatio.put("thisYear", targetRespVO.getSumOutput().compareTo(BigDecimal.valueOf(0)) == 0 ?
                        "-" : consumption.divide(targetRespVO.getSumOutput(), 1, BigDecimal.ROUND_HALF_UP));
            }
            if (lastTargetRespVO == null) {
                jsonObject.put("lastYear", "-");
                jsonObjectOfRatio.put("lastYear", "-");
            } else {
                jsonObject.put("lastYear", lastTargetRespVO.getSumOutput());
                jsonObjectOfRatio.put("lastYear", lastTargetRespVO.getSumOutput().compareTo(BigDecimal.valueOf(0)) == 0 ?
                        "-" : consumption.divide(lastTargetRespVO.getSumOutput(), 1, BigDecimal.ROUND_HALF_UP));
            }

            outputObject.put(uploadTime, jsonObject);
            ratioObject.put(uploadTime, jsonObjectOfRatio);
            consumptionArray.add(consumptionObject);
            outputArray.add(outputObject);
            ratioArray.add(ratioObject);
            startTime.add(Calendar.MONTH, 1);
        }

        jsonArray.add(consumptionArray);
        jsonArray.add(outputArray);
        jsonArray.add(ratioArray);
        pageResponseVO.setRows(jsonArray.toJavaList(JSONArray.class));
        pageResponseVO.setTotal(jsonArray.size());
        return pageResponseVO;
    }

    @ApiOperation(value = "各能源月能耗曲线", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getConsumptionChartsByEnergy", method = RequestMethod.GET)
    public ResponseVO<LineVO<BigDecimal>> getConsumptionChartsByEnergy(@RequestParam(name = "enterprise_id", required = true) Integer enterprise_id) throws IllegalAccessException {
        ResponseVO<LineVO<BigDecimal>> responseVO = new ResponseVO();

        if(enterprise_id==null || enterprise_id.equals("")){
            log.info("各能源月能耗曲线   enterprise_id is null");
            return  responseVO;
        }
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(enterprise_id + "");
        LineVO<BigDecimal> result = new LineVO<>();
        ChartDataVO<BigDecimal> chartVo = new ChartDataVO<BigDecimal>();
        List<String> categories = new ArrayList<String>();
        List<ChartKeyValuePairVO<BigDecimal>> series = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> powerPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> waterPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> gasPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> steamPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> coalPairVO = new ChartKeyValuePairVO<>();
        ChartKeyValuePairVO<BigDecimal> otherPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> powerList = new ArrayList<>();
        List<BigDecimal> waterList = new ArrayList<>();
        List<BigDecimal> gasList = new ArrayList<>();
        List<BigDecimal> steamList = new ArrayList<>();
        List<BigDecimal> coalList = new ArrayList<>();
        List<BigDecimal> otherList = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(c.getTime());

        c.set(Calendar.MONTH, 0);
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(c.getTime());

        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(startTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(endTime.getTime()));
        energyQueryVO.setTerminalIdSet(terminalIdSet);
        List<AnalysisCompany> companyPowerList = new ArrayList<>();
        List<AnalysisCompany> companyWaterList = new ArrayList<>();
        List<AnalysisCompany> companyGasList = new ArrayList<>();
        List<AnalysisCompany> companySteamList = new ArrayList<>();
        List<AnalysisCompany> companyCoalList = new ArrayList<>();
        List<AnalysisCompany> companyOtherList = new ArrayList<>();
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            String tableName;
            switch (value) {
                case ENERGY:
                    break;
                case POWER:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyPowerList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case WATER:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyWaterList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case GAS:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyGasList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case STEAM:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companySteamList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case COAL:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyCoalList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case OTHER:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyOtherList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                default:
            }
        }

        Map<Long, AnalysisCompany> powerMap = new HashMap<>();
        Map<Long, AnalysisCompany> waterMap = new HashMap<>();
        Map<Long, AnalysisCompany> gasMap = new HashMap<>();
        Map<Long, AnalysisCompany> steamMap = new HashMap<>();
        Map<Long, AnalysisCompany> coalMap = new HashMap<>();
        Map<Long, AnalysisCompany> otherMap = new HashMap<>();
        getMap(companyPowerList, powerMap);
        getMap(companyWaterList, waterMap);
        getMap(companyGasList, gasMap);
        getMap(companySteamList, steamMap);
        getMap(companyCoalList, coalMap);
        getMap(companyOtherList, otherMap);


        while (!startTime.after(endTime)) {
            String date = DateUtil.getLocalDateStr(startTime.getTime(), formatter1).split("年")[1];
            categories.add(date.startsWith("0") ? date.substring(1) : date);
            Long uploadTime = DateUtil.getYMLongTime(startTime.getTime());
            powerList.add(powerMap.get(uploadTime) == null ? null : powerMap.get(uploadTime).getData_value());
            waterList.add(waterMap.get(uploadTime) == null ? null : waterMap.get(uploadTime).getData_value());
            gasList.add(gasMap.get(uploadTime) == null ? null : gasMap.get(uploadTime).getData_value());
            steamList.add(steamMap.get(uploadTime) == null ? null : steamMap.get(uploadTime).getData_value());
            coalList.add(coalMap.get(uploadTime) == null ? null : coalMap.get(uploadTime).getData_value());
            otherList.add(otherMap.get(uploadTime) == null ? null : otherMap.get(uploadTime).getData_value());
            startTime.add(Calendar.MONTH, 1);
        }

        powerPairVO.setName("电(千瓦时)");
        powerPairVO.setData(powerList);
        waterPairVO.setName("水(立方米)");
        waterPairVO.setData(waterList);
        gasPairVO.setName("气(立方米)");
        gasPairVO.setData(gasList);
        steamPairVO.setName("热(万千焦耳)");
        steamPairVO.setData(steamList);
        coalPairVO.setName("煤(吨)");
        coalPairVO.setData(coalList);
        otherPairVO.setName("其他(吨标准煤)");
        otherPairVO.setData(otherList);

        series.add(powerPairVO);
        series.add(waterPairVO);
        series.add(gasPairVO);
        series.add(steamPairVO);
        series.add(coalPairVO);
        series.add(otherPairVO);
        chartVo.setCategories(categories);
        chartVo.setSeries(series);
        /*List<String> yztitles = new ArrayList<>();
        yztitles.add("单位(千瓦时)");
        yztitles.add("单位(吨)");
        yztitles.add("单位(立方米)");
        yztitles.add("单位(吨)");
        yztitles.add("单位(吨标准煤)");
        chartVo.setYzTitles(yztitles);*/
        result.setChartData(chartVo);
        responseVO.setData(result);
        return responseVO;
    }

    private void getMap(List<AnalysisCompany> companyPowerList, Map<Long, AnalysisCompany> powerMap) {
        for (AnalysisCompany analysisCompany : companyPowerList) {
            powerMap.put(analysisCompany.getUpload_date(), analysisCompany);
        }
    }

    @ApiOperation(value = "各能耗月表格数据", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getConsumptionTableByEnergy", method = RequestMethod.POST)
    public PageResponseVO<EnergyTableRespVO> getConsumptionTableByEnergy(@RequestParam(name = "enterprise_id", required = true) Integer enterprise_id) throws IllegalAccessException {
        PageResponseVO<EnergyTableRespVO> pageResponseVO = new PageResponseVO<>();
        if(enterprise_id==null || enterprise_id.equals("")){
            log.info("各能耗月表格数据   enterprise_id  is  null");
            return  pageResponseVO;
        }
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(enterprise_id + "");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(c.getTime());

        c.set(Calendar.MONTH, 0);
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(c.getTime());

        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        energyQueryVO.setStartDate(DateUtil.getYMLongTime(startTime.getTime()));
        energyQueryVO.setEndDate(DateUtil.getYMLongTime(endTime.getTime()));
        energyQueryVO.setTerminalIdSet(terminalIdSet);
        List<AnalysisCompany> companyPowerList = new ArrayList<>();
        List<AnalysisCompany> companyWaterList = new ArrayList<>();
        List<AnalysisCompany> companyGasList = new ArrayList<>();
        List<AnalysisCompany> companySteamList = new ArrayList<>();
        List<AnalysisCompany> companyCoalList = new ArrayList<>();
        List<AnalysisCompany> companyOtherList = new ArrayList<>();
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            String tableName;
            switch (value) {
                case ENERGY:
                    break;
                case POWER:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyPowerList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case WATER:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyWaterList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case GAS:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyGasList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case STEAM:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companySteamList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case COAL:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyCoalList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                case OTHER:
                    tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(value.getDataCode()), AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
                    energyQueryVO.setTableName(tableName);
                    companyOtherList = energyBiz.getCompanyList(energyQueryVO);
                    break;
                default:
            }
        }

        Map<Long, AnalysisCompany> powerMap = new HashMap<>();
        Map<Long, AnalysisCompany> waterMap = new HashMap<>();
        Map<Long, AnalysisCompany> gasMap = new HashMap<>();
        Map<Long, AnalysisCompany> steamMap = new HashMap<>();
        Map<Long, AnalysisCompany> coalMap = new HashMap<>();
        Map<Long, AnalysisCompany> otherMap = new HashMap<>();
        getMap(companyPowerList, powerMap);
        getMap(companyWaterList, waterMap);
        getMap(companyGasList, gasMap);
        getMap(companySteamList, steamMap);
        getMap(companyCoalList, coalMap);
        getMap(companyOtherList, otherMap);

        List<EnergyTableRespVO> respVOList = new ArrayList<>();
        while (!startTime.after(endTime)) {
            EnergyTableRespVO energyTableRespVO = new EnergyTableRespVO();
            String date = DateUtil.getLocalDateStr(startTime.getTime(), formatter1).split("年")[1];
            energyTableRespVO.setMonth(date.startsWith("0") ? date.substring(1) : date);
            Long uploadTime = DateUtil.getYMLongTime(startTime.getTime());
            energyTableRespVO.setPower(powerMap.get(uploadTime) == null ? "-" : powerMap.get(uploadTime).getData_value() + "");
            energyTableRespVO.setWater(waterMap.get(uploadTime) == null ? "-" : waterMap.get(uploadTime).getData_value() + "");
            energyTableRespVO.setGas(gasMap.get(uploadTime) == null ? "-" : gasMap.get(uploadTime).getData_value() + "");
            energyTableRespVO.setSteam(steamMap.get(uploadTime) == null ? "-" : steamMap.get(uploadTime).getData_value() + "");
            energyTableRespVO.setCoal(coalMap.get(uploadTime) == null ? "-" : coalMap.get(uploadTime).getData_value() + "");
            energyTableRespVO.setOther(otherMap.get(uploadTime) == null ? "-" : otherMap.get(uploadTime).getData_value() + "");
            respVOList.add(energyTableRespVO);
            startTime.add(Calendar.MONTH, 1);
        }

        pageResponseVO.setTotal(respVOList.size());
        pageResponseVO.setRows(respVOList);
        return pageResponseVO;
    }

    @ApiOperation(value = "能耗预警", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getConsumptionWarningTable", method = RequestMethod.POST)
    public ResponseVO<JSONObject> getConsumptionWarningTable(@RequestParam(name = "enterprise_id", required = true) Integer enterprise_id) throws IllegalAccessException {
        ResponseVO<JSONObject> responseVO = new ResponseVO<>();

        if (enterprise_id == null || enterprise_id.equals("")) {
            log.info("能耗预警   enterprise_id 空");
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Long endTime = DateUtil.getYMLongTime(c.getTime());

        c.set(Calendar.MONTH, 0);
        Long startTime = DateUtil.getYMLongTime(c.getTime());
        List<Integer> idList = new ArrayList<>();
        idList.add(enterprise_id);
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(enterprise_id + "");
        //总能耗
        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        energyQueryVO.setStartDate(startTime);
        energyQueryVO.setEndDate(endTime);
        String tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.ENERGY, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
        energyQueryVO.setTableName(tableName);
        energyQueryVO.setTerminalIdSet(terminalIdSet);
        AnalysisTerminal terminalConsumption = energyBiz.getSumTerminalConsumption(energyQueryVO) == null ?
                new AnalysisTerminal() :
                energyBiz.getSumTerminalConsumption(energyQueryVO);

        //总产值
        TargetQueryVO targetQueryVO = new TargetQueryVO();
        targetQueryVO.setStartTime(startTime);
        targetQueryVO.setEndTime(endTime);
        targetQueryVO.setEnterpriseIds(idList);
        TargetRespVO sumOutput = outputEnergyTargetBiz.selectSumOutput(targetQueryVO);

        //官方设定能耗
        OutputEnergyWarning warning = outputEnergyWarningBiz.selectByYearAndCid(Long.valueOf((startTime + "").substring(0, 4)), idList.get(0));

        JSONObject object = new JSONObject();
        JSONObject sumObject = new JSONObject();
        sumObject.put("target", warning == null ? "-" : warning.getTotal_cons());
        sumObject.put("warning", warning == null ? "-" : warning.getTotal_cons().multiply(warning.getTotal_cons_per()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
        BigDecimal actual = terminalConsumption.getData_value() == null ? null : terminalConsumption.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
        sumObject.put("actual", actual == null ? "-" : actual);
        sumObject.put("state", (warning == null || actual == null) ? "-" :
                (actual.compareTo(warning.getTotal_cons()) > 0 ? "超标" :
                        (actual.compareTo(warning.getTotal_cons().multiply(warning.getTotal_cons_per()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP)) > 0 ? "预警" : "正常")));
        object.put("sum", sumObject);

        JSONObject unitObject = new JSONObject();
        unitObject.put("target", warning == null ? "-" : warning.getUnit_cons());
        unitObject.put("warning", warning == null ? "-" : warning.getUnit_cons().multiply(warning.getUnit_cons_per()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
        unitObject.put("actual", (actual == null || sumOutput == null || sumOutput.getSumOutput().compareTo(BigDecimal.valueOf(0)) == 0) ? "-" : actual.divide(sumOutput.getSumOutput(), 1, BigDecimal.ROUND_HALF_UP));
        unitObject.put("state", (actual == null || sumOutput == null || warning == null || sumOutput.getSumOutput().compareTo(BigDecimal.valueOf(0)) == 0) ? "-"
                : (actual.divide(sumOutput.getSumOutput(), 1, BigDecimal.ROUND_HALF_UP).compareTo(warning.getUnit_cons()) > 0 ? "超标" :
                (actual.divide(sumOutput.getSumOutput(), 1, BigDecimal.ROUND_HALF_UP).compareTo(warning.getUnit_cons().multiply(warning.getUnit_cons_per()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP)) > 0 ? "预警" : "正常")));
        object.put("unit", unitObject);

        responseVO.setData(object);
        return responseVO;
    }

}
