package com.tg.enterprise.controller;

import com.tg.enterprise.biz.IAreaBiz;
import com.tg.enterprise.biz.IEnergyBiz;
import com.tg.enterprise.biz.IEnergyTypeBiz;
import com.tg.enterprise.biz.IEnterpriseBiz;
import com.tg.enterprise.model.AnalysisCompany;
import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 能耗统计app
 *
 * @author dell
 */
@RestController
@RequestMapping(value = "/consumption/")
@Slf4j
public class EnergyConsumptionController {
    @Autowired
    private IEnergyBiz energyBiz;

    @Autowired
    private IEnterpriseBiz enterpriseBiz;

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHH");

    @RequestMapping(value = "chart/line", method = RequestMethod.GET)
    public ResponseVO<LineVO<BigDecimal>> enterpriseEnergyConsumptionAnalysisLine(EnergyConsumptionRequest<String> data, HttpServletRequest request) throws IllegalAccessException, ParseException {
        EnergyDataCode dataCode = EnergyDataCode.parse(data.getDataCode());
        AnalysisTypeEnum type = AnalysisTypeEnum.parse(data.getQueryType());

        EnterpriseQueryVO enterpriseReq = new EnterpriseQueryVO();
        enterpriseReq.setEnterprise_id(data.getEnterpriseId());
        enterpriseReq.setIs_del(0);
        List<Enterprise> enterprises = enterpriseBiz.getList(enterpriseReq);
        if (enterprises.isEmpty()) {
            return new ResponseVO<>(ErrorCode.INVALID_PARAM, "企业不存在");
        }

        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(data.getEnterpriseId() + "");
        Enterprise enterprise = enterprises.get(0);
        EnergyQueryVO queryVO = new EnergyQueryVO();
        EnergyQueryVO beforeQueryVO = new EnergyQueryVO();
        String tableName = EnergyTypeRouter.getAnalysisTableName(dataCode, type, AnalysisEntityEnum.COMPANY);
        queryVO.setTableName(tableName);
        beforeQueryVO.setTableName(tableName);
        queryVO.setTerminalIdSet(terminalIdSet);
        beforeQueryVO.setTerminalIdSet(terminalIdSet);
        List<String> categories = new ArrayList<>();
        List<String> times = new ArrayList<String>();
        List<String> time2 = new ArrayList<>();
        List<Long> getTime = new ArrayList<>();
        List<String> title = new ArrayList<>();
        getTime(data.getQueryTime(), type, categories, times, time2, getTime, title);
        queryVO.setStartDate(getTime.get(0));
        queryVO.setEndDate(getTime.get(1));
        beforeQueryVO.setStartDate(getTime.get(2));
        beforeQueryVO.setEndDate(getTime.get(3));
        List<AnalysisCompany> datas1 = energyBiz.getCompanyList(queryVO);
        List<AnalysisCompany> datas2 = energyBiz.getCompanyList(beforeQueryVO);
        LineVO<BigDecimal> lines = new LineVO<>();
        List<ChartKeyValuePairVO<BigDecimal>> series = new ArrayList<>();
        ChartDataVO<BigDecimal> chartData = new ChartDataVO<>();
        chartData.setCategories(categories);
        lines.setKey(data.getKey());


        ChartKeyValuePairVO<BigDecimal> series1 = getDateSeries(datas1, times, data.getQueryTime(), data.getDataCode());
        String lastDate = title.get(0);
        ChartKeyValuePairVO<BigDecimal> series2 = getDateSeries(datas2, time2, lastDate, data.getDataCode());
        series.add(series2);
        series.add(series1);
        chartData.setYzTitle("单位（" + EnergyUnitEnum.parse(data.getDataCode()).getUnit() + ")");
        switch (dataCode) {
            case ENERGY:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "综合用能势态分析");
                break;
            case GAS:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "用气势态分析");
                break;
            case POWER:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "用电势态分析");
                break;
            case COAL:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "用煤势态分析");
                break;
            case WATER:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "用水势态分析");
                break;
            case STEAM:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "用热势态分析");
                break;
            case OTHER:
                chartData.setTitle(data.getQueryTime() + enterprise.getName() + "其他能源势态分析");
                break;
            default:
                break;
        }
        chartData.setSeries(series);
        chartData.setCategories(categories);
        lines.setChartData(chartData);
        return new ResponseVO<LineVO<BigDecimal>>(ErrorCode.OK, "ok", lines);
    }


    @RequestMapping(value = "chart/table", method = RequestMethod.POST, consumes = "application/json")
    public PageTableTitleVO<LinkedHashMap<String, String>> enterpriseTable(@RequestBody PageRequestVO<EnergyConsumptionRequest<String>> data) throws Exception {
        EnterpriseQueryVO enterpriseReq = new EnterpriseQueryVO();
        enterpriseReq.setEnterprise_id(data.getParams().getEnterpriseId());
        enterpriseReq.setIs_del(0);
        List<Enterprise> enterprises = enterpriseBiz.getList(enterpriseReq);
        if (enterprises.isEmpty()) {
            return new PageTableTitleVO<>(ErrorCode.INVALID_PARAM, "企业不存在");
        }
        Set<String> terminalIdSet = new HashSet<>();
        terminalIdSet.add(data.getParams().getEnterpriseId() + "");
        Enterprise enterprise = enterprises.get(0);
        EnergyDataCode dataCode = EnergyDataCode.parse(data.getParams().getDataCode());
        AnalysisTypeEnum type = AnalysisTypeEnum.parse(data.getParams().getQueryType());
        EnergyQueryVO queryVO = new EnergyQueryVO();
        EnergyQueryVO beforeQueryVO = new EnergyQueryVO();
        queryVO.setTerminalIdSet(terminalIdSet);
        beforeQueryVO.setTerminalIdSet(terminalIdSet);
        String tableName = EnergyTypeRouter.getAnalysisTableName(dataCode, type, AnalysisEntityEnum.COMPANY);
        queryVO.setTableName(tableName);
        beforeQueryVO.setTableName(tableName);
        String lastTime;
        String timeTitle;
        if (type == AnalysisTypeEnum.MONTH) {
            int endMonth = 12;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdfY = new SimpleDateFormat("yyyy年");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(data.getParams().getQueryTime()));
            timeTitle = sdfY.format(cal.getTime());
            long theDate = Long.parseLong(sdf.format(cal.getTime()));
            queryVO.setStartDate(theDate * 100);
            queryVO.setEndDate(theDate * 100 + endMonth);
            cal.add(Calendar.MONTH, -1);
            lastTime = sdf.format(cal.getTime());
            long lastDayDate = Long.parseLong(lastTime);
            beforeQueryVO.setStartDate(lastDayDate * 100);
            beforeQueryVO.setEndDate(lastDayDate * 100 + endMonth);
        } else if (type == AnalysisTypeEnum.DATE) {
            int endDate = 31;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy年MM月");
            String nowdate = data.getParams().getQueryTime().replace("-", "");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(nowdate));
            timeTitle = sdfYM.format(cal.getTime());
            long theDate = Long.parseLong(sdf.format(cal.getTime()));
            queryVO.setStartDate(theDate * 100);
            queryVO.setEndDate(theDate * 100 + endDate);
            cal.add(Calendar.MONTH, -1);
            lastTime = sdf1.format(cal.getTime());
            long lastDayDate = Long.parseLong(sdf.format(cal.getTime()));
            beforeQueryVO.setStartDate(lastDayDate * 100);
            beforeQueryVO.setEndDate(lastDayDate * 100 + endDate);
        } else if (type == AnalysisTypeEnum.HOUR) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy年MM月dd日");
            String nowdate = data.getParams().getQueryTime().replace("-", "");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(nowdate));
            timeTitle = sdfYMD.format(cal.getTime());
            long theDate = Long.parseLong(sdf.format(cal.getTime()));
            queryVO.setStartDate(theDate * 100 + 1L);
            LocalDateTime dateTime = LocalDateTime.parse(nowdate+ "01", df);
            LocalDateTime plusHours = dateTime.plusHours(23L);
            queryVO.setEndDate(Long.valueOf(df.format(plusHours)));
            cal.add(Calendar.DAY_OF_MONTH, -1);
            lastTime = sdf1.format(cal.getTime());
            long lastDayDate = Long.parseLong(sdf.format(cal.getTime()));
            beforeQueryVO.setStartDate(lastDayDate * 100 + 1L);
            LocalDateTime dateTime1 = LocalDateTime.parse(lastDayDate+ "01", df);
            LocalDateTime plusHours1 = dateTime1.plusHours(23L);
            beforeQueryVO.setEndDate(Long.valueOf(df.format(plusHours1)));
        } else {
            return new PageTableTitleVO<>(ErrorCode.INVALID_PARAM, "查询参数异常");
        }
        List<AnalysisCompany> datas = energyBiz.getCompanyList(queryVO);
        List<AnalysisCompany> beforeDatas = energyBiz.getCompanyList(beforeQueryVO);
        List<BigDecimal> sumList = new ArrayList<>();
        List<String> title = new ArrayList<>();
        rateList(dataCode.getDataCode(), datas, beforeDatas, sumList, title);
        List<LinkedHashMap<String, String>> mapList = getMap(type, datas, beforeDatas, enterprise.getName(), data.getParams().getQueryTime(), lastTime, dataCode);
        PageTableTitleVO<LinkedHashMap<String, String>> result = new PageTableTitleVO<>(ErrorCode.OK, "ok");
        Map<String, String> energyAnalysis = new HashMap<>();
        energyAnalysis.put("title", timeTitle + enterprise.getName() + title.get(0) + "能源势态分析");
        energyAnalysis.put("energyTitle", title.get(0) + "总值");
        energyAnalysis.put("yzTitle", EnergyUnitEnum.parse(dataCode.getDataCode()).getUnit());
        energyAnalysis.put("energy", dataCode == EnergyDataCode.ENERGY ?
                sumList.get(0).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).toString() :
                sumList.get(0).toString());
        if (datas.size() > 0 && beforeDatas.size() > 0) {
            BigDecimal d = sumList.get(0).subtract(sumList.get(1)).divide(sumList.get(1), 4, RoundingMode.HALF_UP);
            BigDecimal bf = d.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            energyAnalysis.put("annularGrowth", bf + "");
        } else {
            energyAnalysis.put("annularGrowth", "N");
        }
        result.setMap(energyAnalysis);
        result.setRows(mapList);
        return result;
    }

    private void getTime(String uploadMonth, AnalysisTypeEnum type, List<String> categories, List<String> times, List<String> time2, List<Long> getTime, List<String> time) throws ParseException {
        if (type == AnalysisTypeEnum.MONTH) {
            int endMonth = 12;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(uploadMonth));
            long theDate = Long.parseLong(sdf.format(cal.getTime()));
            getTime.add(theDate * 100);
            getTime.add(theDate * 100 + endMonth);
            cal.add(Calendar.MONTH, -1);
            String lastYear = cal.get(Calendar.YEAR) + "";
            time.add(lastYear);
            long lastDayDate = Long.parseLong(sdf.format(cal.getTime()));
            getTime.add(lastDayDate * 100);
            getTime.add(lastDayDate * 100 + endMonth);
            for (int i = 1; i <= endMonth; i++) {
                categories.add((i < 10 ? "0" + i : i + ""));
                times.add(uploadMonth + (i < 10 ? "0" + i : i + ""));
                time2.add(lastYear + (i < 10 ? "0" + i : i + ""));
            }
        } else if (type == AnalysisTypeEnum.DATE) {
            int endDate = 31;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            String nowdate = uploadMonth.replace("-", "");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(nowdate));
            endDate = cal.getActualMaximum(Calendar.DATE);
            long theDate = Long.parseLong(sdf.format(cal.getTime()));
            getTime.add(theDate * 100);
            getTime.add(theDate * 100 + endDate);
            cal.add(Calendar.MONTH, -1);
            int lastEndDate = cal.getActualMaximum(Calendar.DATE);
            endDate = endDate >= lastEndDate ? endDate : lastEndDate;
            String lastMonth = sdf.format(cal.getTime());
            time.add(sdf1.format(cal.getTime()));
            long lastDayDate = Long.parseLong(sdf.format(cal.getTime()));
            getTime.add(lastDayDate * 100);
            getTime.add(lastDayDate * 100 + endDate);
            for (int i = 1; i <= endDate; i++) {
                categories.add((i < 10 ? "0" + i : i + ""));
                times.add(nowdate + (i < 10 ? "0" + i : i + ""));
                time2.add(lastMonth + (i < 10 ? "0" + i : i + ""));
            }
        } else if (type == AnalysisTypeEnum.HOUR) {
            int endHours = 24;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String nowdate = uploadMonth.replace("-", "");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(nowdate));
            long theDate = Long.parseLong(sdf.format(cal.getTime()));
            getTime.add(theDate * 100 + 1L);
            LocalDateTime dateTime = LocalDateTime.parse(nowdate+ "01", df);
            LocalDateTime plusHours = dateTime.plusHours(23L);
            getTime.add(Long.valueOf(df.format(plusHours)));
            cal.add(Calendar.DAY_OF_MONTH, -1);
            time.add(sdf1.format(cal.getTime()));
            long lastDayDate = Long.parseLong(sdf.format(cal.getTime()));
            getTime.add(lastDayDate * 100 + 1L);
            LocalDateTime dateTime1 = LocalDateTime.parse(lastDayDate+ "01", df);
            LocalDateTime plusHours1 = dateTime1.plusHours(23L);
            getTime.add(Long.valueOf(df.format(plusHours1)));
            for (int i = 1; i <= endHours; i++) {
                categories.add((i < 10 ? "0" + i + ":00" : i + ":00"));
                if(i == endHours) {
                    times.add(df.format(plusHours));
                    time2.add(df.format(plusHours1));
                } else {
                    times.add(nowdate + (i < 10 ? "0" + i : i + ""));
                    time2.add(lastDayDate + (i < 10 ? "0" + i : i + ""));
                }
            }
        }

    }

    /**
     * 企业表格数据map
     *
     * @param datas
     * @param beforeDatas
     * @param name
     * @return
     */
    private List<LinkedHashMap<String, String>> getMap(AnalysisTypeEnum type, List<AnalysisCompany> datas, List<AnalysisCompany> beforeDatas, String name, String time1, String time2, EnergyDataCode dataCode) {
        List<LinkedHashMap<String, String>> mapList = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
        String timeUnit;
        Integer end;
        switch (type) {
            case MONTH:
                timeUnit = "月";
                end = 12;
                break;
            case DATE:
                timeUnit = "号";
                end = 31;
                break;
            case HOUR:
                timeUnit = "点";
                end = 23;
                break;
            default:
                timeUnit = "月";
                end = 12;
                break;
        }

        for (AnalysisCompany analysisData : datas) {
            String getDate = analysisData.getUpload_date() % 100 + "";
            if (!map1.containsKey(getDate)) {
                map1.put(getDate, dataCode == EnergyDataCode.ENERGY ?
                        analysisData.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).toString() :
                        analysisData.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            } else {
                BigDecimal sum = new BigDecimal(map1.get(getDate)).add(dataCode == EnergyDataCode.ENERGY ?
                        analysisData.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP) :
                        analysisData.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP));
                map1.put(getDate, sum.toString());
            }
        }
        for (AnalysisCompany analysisData : beforeDatas) {
            String getDate = analysisData.getUpload_date() % 100 + "";
            if (!map2.containsKey(getDate)) {
                map2.put(getDate, dataCode == EnergyDataCode.ENERGY ?
                        analysisData.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).toString() :
                        analysisData.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            } else {
                BigDecimal sum = new BigDecimal(map1.get(getDate)).add(dataCode == EnergyDataCode.ENERGY ?
                        analysisData.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP) :
                        analysisData.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP));
                map2.put(getDate, sum.toString());
            }
        }
        LinkedHashMap<String, String> linkedHashMap1 = new LinkedHashMap<>();
        LinkedHashMap<String, String> linkedHashMap2 = new LinkedHashMap<>();
        for (Integer i = 1; i <= end; i++) {
            linkedHashMap1.put(i + timeUnit, map1.getOrDefault(i + "", "--"));
            linkedHashMap2.put(i + timeUnit, map2.getOrDefault(i + "", "--"));
        }
        if(type == AnalysisTypeEnum.HOUR) {
            linkedHashMap1.put(24 + timeUnit, map1.getOrDefault(0+"", "--"));
            linkedHashMap2.put(24 + timeUnit, map2.getOrDefault(0+"", "--"));
        }
        if (datas.size() > 0) {
            linkedHashMap1.put("name", name);
            linkedHashMap1.put("date", time1);
        }
        if (beforeDatas.size() > 0) {
            linkedHashMap2.put("name", name);
            linkedHashMap2.put("date", time2);
        }
        if (!map1.isEmpty()) {
            mapList.add(linkedHashMap1);
        }
        if (!map2.isEmpty()) {
            mapList.add(linkedHashMap2);
        }
        return mapList;
    }

    private ChartKeyValuePairVO<BigDecimal> getDateSeries(List<AnalysisCompany> list, List<String> times, String date, String dataCode) {
        Map<Long, BigDecimal> map = new HashMap<>();
        for (AnalysisCompany analysisHour : list) {
            if (!map.containsKey(analysisHour.getUpload_date())) {
                map.put(analysisHour.getUpload_date(), (EnergyDataCode.ENERGY.getDataCode().equals(dataCode)) ?
                        analysisHour.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP) :
                        analysisHour.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                BigDecimal sum = map.get(analysisHour.getUpload_date()).add((EnergyDataCode.ENERGY.getDataCode().equals(dataCode)) ?
                        analysisHour.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP) :
                        analysisHour.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP));
                map.put(analysisHour.getUpload_date(), sum);
            }
        }
        ChartKeyValuePairVO<BigDecimal> chartKeyValuePairVO = new ChartKeyValuePairVO<>();
        chartKeyValuePairVO.setName(date);
        chartKeyValuePairVO.setData(new ArrayList<>());
        for (String uploadTime : times) {
            BigDecimal dataValue = map.get(Long.valueOf(uploadTime));
            if (dataValue != null) {
                chartKeyValuePairVO.getData().add(dataValue);
            } else {
                chartKeyValuePairVO.getData().add(null);
            }
        }
        return chartKeyValuePairVO;
    }

    private void rateList(String code, List<AnalysisCompany> datas, List<AnalysisCompany> beforeDatas, List<BigDecimal> sumList, List<String> title) {
        BigDecimal sum1 = new BigDecimal("0");
        BigDecimal sum2 = new BigDecimal("0");
        String title1 = EnergyTypeEnum.parse(code).getName();

        for (AnalysisCompany analysiData : datas) {
            sum1 = sum1.add(analysiData.getData_value());
        }
        for (AnalysisCompany analysiData : beforeDatas) {
            sum2 = sum2.add(analysiData.getData_value());
        }
        sumList.add(sum1);
        sumList.add(sum2);
        title.add(title1);
    }


    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(EnergyConsumptionRequest<String> data, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, ParseException {
        Enterprise enterprise = enterpriseBiz.getList(null).get(0);
        if (enterprise == null) {
            return;
        }
        EnergyDataCode dataCode = EnergyDataCode.parse(data.getDataCode());
        AnalysisTypeEnum type = AnalysisTypeEnum.parse(data.getQueryType());
        StringBuilder sb = new StringBuilder(enterprise.getName());
        SimpleDateFormat sdf1;
        SimpleDateFormat sdf2;
        String reportName;
        String timeUnit;
        Integer end;
        switch (type) {
            case HOUR:
                sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
                sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                reportName = "日报";
                timeUnit = "点";
                end = 23;
                break;
            case DATE:
                sdf1 = new SimpleDateFormat("yyyy年MM月");
                sdf2 = new SimpleDateFormat("yyyy-MM");
                reportName = "月报";
                timeUnit = "号";
                end = 31;
                break;
            case MONTH:
                sdf1 = new SimpleDateFormat("yyyy年");
                sdf2 = new SimpleDateFormat("yyyy");
                reportName = "年报";
                timeUnit = "月";
                end = 12;
                break;
            default:
                sdf1 = new SimpleDateFormat("yyyy年");
                sdf2 = new SimpleDateFormat("yyyy");
                reportName = "年报";
                timeUnit = "月";
                end = 12;
        }
        String queryTime = sdf1.format(sdf2.parse(data.getQueryTime()));
        sb.append("_").append(queryTime);
        switch (dataCode) {
            case ENERGY:
                sb.append("综合用能势态分析报表");
                break;
            case GAS:
                sb.append("用气势态分析报表");
                break;
            case POWER:
                sb.append("用电势态分析报表");
                break;
            case COAL:
                sb.append("用热势态分析报表");
                break;
            case WATER:
                sb.append("用水势态分析报表");
                break;
            case STEAM:
                sb.append("用热势态分析报表");
                break;
            case OTHER:
                sb.append("其他能源势态分析报表");
                break;
            default:
                break;
        }

        sb.append(".xlsx");
        try {
            // 模板位置，输出流
            response.reset();
            //导出excel文件，设置文件名
            String filename = URLEncoder.encode(sb.toString(), "UTF-8");
            //设置下载头
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            InputStream inputStream = this.getClass().getResourceAsStream("/templates/energyAnalysis.xlsx");
            ServletOutputStream os = response.getOutputStream();
            Map<String, Object> model = new HashMap<>();

            EnergyQueryVO queryVO = new EnergyQueryVO();
            EnergyQueryVO beforeQueryVO = new EnergyQueryVO();
            String tableName = EnergyTypeRouter.getAnalysisTableName(dataCode, type, AnalysisEntityEnum.COMPANY);
            queryVO.setTableName(tableName);
            beforeQueryVO.setTableName(tableName);

            if (type == AnalysisTypeEnum.MONTH) {
                int endMonth = 12;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(data.getQueryTime()));
                long theDate = Long.parseLong(sdf.format(cal.getTime()));
                queryVO.setStartDate(theDate * 100);
                queryVO.setEndDate(theDate * 100 + endMonth);
            } else if (type == AnalysisTypeEnum.DATE) {
                int endDate = 31;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                String nowdate = data.getQueryTime().replace("-", "");
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(nowdate));
                long theDate = Long.parseLong(sdf.format(cal.getTime()));
                queryVO.setStartDate(theDate * 100);
                queryVO.setEndDate(theDate * 100 + endDate);
            } else if (type == AnalysisTypeEnum.HOUR) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String nowdate = data.getQueryTime().replace("-", "");
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(nowdate));
                long theDate = Long.parseLong(sdf.format(cal.getTime()));
                queryVO.setStartDate(theDate * 100 + 1L);
                LocalDateTime dateTime = LocalDateTime.parse(nowdate+ "01", df);
                LocalDateTime plusHours = dateTime.plusHours(23L);
                queryVO.setEndDate(Long.valueOf(df.format(plusHours)));
            } else {
                return;
            }
            List<AnalysisCompany> datas = energyBiz.getCompanyList(queryVO);
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            for (AnalysisCompany analysisData : datas) {
                String getDate = analysisData.getUpload_date() % 100 + "";
                if (!map.containsKey(getDate)) {
                    map.put(getDate, dataCode == EnergyDataCode.ENERGY ?
                            analysisData.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP).toString() :
                            analysisData.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                } else {
                    BigDecimal sum = new BigDecimal(map.get(getDate)).add(dataCode == EnergyDataCode.ENERGY ?
                            analysisData.getData_value().divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP) :
                            analysisData.getData_value().setScale(2, BigDecimal.ROUND_HALF_UP));
                    map.put(getDate, sum.toString());
                }
            }

            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
            for (Integer i = 1; i <= end; i++) {
                linkedHashMap.put(i + timeUnit, map.getOrDefault(i + "", "--"));
            }
            if(type == AnalysisTypeEnum.HOUR) {
                linkedHashMap.put(24 + timeUnit, map.getOrDefault(0 + "", "--"));
            }

            model.put("reportName", reportName);
            model.put("energyType", EnergyTypeEnum.parse(data.getDataCode()).getName());
            model.put("unit", EnergyUnitEnum.parse(data.getDataCode()).getUnit());
            model.put("queryTime", queryTime);
            model.put("dataList", linkedHashMap);
            JxlsUtils.exportExcel(inputStream, os, model);
            os.close();
            inputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
