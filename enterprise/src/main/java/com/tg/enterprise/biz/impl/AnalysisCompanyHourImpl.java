package com.tg.enterprise.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.tg.enterprise.biz.IAnalysisCompanyHourBiz;
import com.tg.enterprise.dao.AnalysisCompanyGasHourMapper;
import com.tg.enterprise.dao.AnalysisCompanyPowerHourMapper;
import com.tg.enterprise.dao.AnalysisCompanyWaterHourMapper;
import com.tg.enterprise.model.AnalysisCompanyGasHour;
import com.tg.enterprise.model.AnalysisCompanyPowerHour;
import com.tg.enterprise.model.AnalysisCompanySteamHour;
import com.tg.enterprise.model.AnalysisCompanyWaterHour;
import com.tg.enterprise.util.Constant;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huangjianbo on 2018/4/11
 */
@Service
public class AnalysisCompanyHourImpl implements IAnalysisCompanyHourBiz {

    @Autowired
    private AnalysisCompanyGasHourMapper gasHourMapper;
    @Autowired
    private AnalysisCompanyPowerHourMapper powerHourMapper;
    @Autowired
    private AnalysisCompanyWaterHourMapper waterHourMapper;

    /*@Autowired
    private AnalysisCompanyEnergyMapper energyMapper;*/

    @Override
    //@ClearDataSource
    public ChartDataVO<Double> getDataByPower(List<Integer> enterprise_ids, String type, Long startTime, Long endTime,Long nowDate) {
        List<ChartKeyValuePairVO<Double>> chartKeyValuePairVOs = new ArrayList<>();
        ChartDataVO<Double> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
            categories.add((i < 10 ? "0" + i : i + "")+":00");
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanyPowerHour> list = powerHourMapper.getDataByPower(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanyPowerHour> map = new HashMap<>();
        for(AnalysisCompanyPowerHour model : list){
            map.put(model.getUpload_date(),model);
        }
        ChartKeyValuePairVO<Double> chartKeyValuePairVO = new ChartKeyValuePairVO<>();
        chartKeyValuePairVO.setName("");
        BigDecimal totalValue = new BigDecimal("0");
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanyPowerHour analysisCompanyPowerHour = map.get(getTime);
            if(null!=analysisCompanyPowerHour&&analysisCompanyPowerHour.getData_value()!=null){
                chartKeyValuePairVO.getData().add(analysisCompanyPowerHour.getData_value().doubleValue());
                totalValue = totalValue.add(analysisCompanyPowerHour.getData_value());
            }else {
                chartKeyValuePairVO.getData().add(null);
            }

        }
        chartKeyValuePairVOs.add(chartKeyValuePairVO);
        chartDataVO.setTitle("电量曲线");
        chartDataVO.setYzTitle("单位(千瓦时)");
        if(list.size() >0) {
            chartDataVO.setWarning(totalValue.divide(BigDecimal.valueOf(list.size()),1,BigDecimal.ROUND_HALF_UP));
        } else {
            chartDataVO.setWarning(BigDecimal.valueOf(0));
        }
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(chartKeyValuePairVOs);
        return chartDataVO;
    }

    @Override
    //@ClearDataSource
    public ChartDataVO<Double> getDataByWater(List<Integer> enterprise_ids, String type, Long startTime, Long endTime,Long nowDate) {
        List<ChartKeyValuePairVO<Double>> chartKeyValuePairVOs = new ArrayList<>();
        ChartDataVO<Double> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
            categories.add((i < 10 ? "0" + i : i + "")+":00");
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanyWaterHour> list = waterHourMapper.getDataByWater(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanyWaterHour> map = new HashMap<>();
        for(AnalysisCompanyWaterHour model : list){
            map.put(model.getUpload_date(),model);
        }
        ChartKeyValuePairVO<Double> chartKeyValuePairVO = new ChartKeyValuePairVO<>();
        chartKeyValuePairVO.setName("");
        BigDecimal totalValue = new BigDecimal("0");
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanyWaterHour analysisCompanyWaterHour = map.get(getTime);
            if(null!=analysisCompanyWaterHour&&analysisCompanyWaterHour.getData_value()!=null){
                    chartKeyValuePairVO.getData().add(analysisCompanyWaterHour.getData_value().doubleValue());
                    totalValue = totalValue.add(analysisCompanyWaterHour.getData_value());
            }else {
                chartKeyValuePairVO.getData().add(null);
            }

        }
        chartKeyValuePairVOs.add(chartKeyValuePairVO);
        chartDataVO.setTitle("水量");
        chartDataVO.setYzTitle("单位(立方米)");
        if(list.size() >0) {
            chartDataVO.setWarning(totalValue.divide(BigDecimal.valueOf(list.size()),1,BigDecimal.ROUND_HALF_UP));
        } else {
            chartDataVO.setWarning(BigDecimal.valueOf(0));
        }
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(chartKeyValuePairVOs);
        return chartDataVO;
    }

    @Override
    public ChartDataVO<Double> getDataBySteam(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<ChartKeyValuePairVO<Double>> chartKeyValuePairVOs = new ArrayList<>();
        ChartDataVO<Double> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
            categories.add((i < 10 ? "0" + i : i + "")+":00");
        }
        Integer types = 4;
        //EnterpriseValue enterpriseValue = iEnterpriseValueBiz.getByTypeAndEnterpriseId(enterprise_id,types);
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanySteamHour> list = waterHourMapper.getDataBySteam(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanySteamHour> map = new HashMap<>();
        for(AnalysisCompanySteamHour model : list){
            map.put(model.getUpload_date(),model);
        }
        ChartKeyValuePairVO<Double> chartKeyValuePairVO = new ChartKeyValuePairVO<>();
        chartKeyValuePairVO.setName("");
        BigDecimal totalValue = new BigDecimal("0");
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanySteamHour analysisCompanyWaterHour = map.get(getTime);
            if(null!=analysisCompanyWaterHour&&analysisCompanyWaterHour.getData_value()!=null){
                chartKeyValuePairVO.getData().add(analysisCompanyWaterHour.getData_value().doubleValue());
                totalValue = totalValue.add(analysisCompanyWaterHour.getData_value());
            }else {
                chartKeyValuePairVO.getData().add(null);
            }

        }
        chartKeyValuePairVOs.add(chartKeyValuePairVO);
        chartDataVO.setTitle("蒸汽用量");
        chartDataVO.setYzTitle("单位(万千焦耳)");
        if(list.size() >0) {
            chartDataVO.setWarning(totalValue.divide(BigDecimal.valueOf(list.size()),1,BigDecimal.ROUND_HALF_UP));
        } else {
            chartDataVO.setWarning(BigDecimal.valueOf(0));
        }
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(chartKeyValuePairVOs);
        return chartDataVO;
    }


    @Override
    public ChartDataVO<Double> getDataByFueloil(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<ChartKeyValuePairVO<Double>> chartKeyValuePairVOs = new ArrayList<>();
        ChartDataVO<Double> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
            categories.add((i < 10 ? "0" + i : i + "")+":00");
        }
        Integer types = 4;
        //EnterpriseValue enterpriseValue = iEnterpriseValueBiz.getByTypeAndEnterpriseId(enterprise_id,types);
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanySteamHour> list = waterHourMapper.getDataByFueloil(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanySteamHour> map = new HashMap<>();
        for(AnalysisCompanySteamHour model : list){
            map.put(model.getUpload_date(),model);
        }
        ChartKeyValuePairVO<Double> chartKeyValuePairVO = new ChartKeyValuePairVO<>();
        chartKeyValuePairVO.setName("");
        BigDecimal totalValue = new BigDecimal("0");
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanySteamHour analysisCompanyWaterHour = map.get(getTime);
            if(null!=analysisCompanyWaterHour&&analysisCompanyWaterHour.getData_value()!=null){
                chartKeyValuePairVO.getData().add(analysisCompanyWaterHour.getData_value().doubleValue());
                totalValue = totalValue.add(analysisCompanyWaterHour.getData_value());
            }else {
                chartKeyValuePairVO.getData().add(null);
            }

        }
        chartKeyValuePairVOs.add(chartKeyValuePairVO);
        chartDataVO.setTitle("蒸汽用量");
        chartDataVO.setYzTitle("单位(万千焦耳)");
        if(list.size() >0) {
            chartDataVO.setWarning(totalValue.divide(BigDecimal.valueOf(list.size()),1,BigDecimal.ROUND_HALF_UP));
        } else {
            chartDataVO.setWarning(BigDecimal.valueOf(0));
        }
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(chartKeyValuePairVOs);
        return chartDataVO;
    }


    @Override
    public ChartDataVO<Double> getDataByGas(List<Integer> enterprise_ids, String type, Long startTime, Long endTime,Long nowDate) {
        List<ChartKeyValuePairVO<Double>> chartKeyValuePairVOs = new ArrayList<>();
        ChartDataVO<Double> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
            categories.add((i < 10 ? "0" + i : i + "")+":00");
        }
        Integer types = 3;
        //EnterpriseValue enterpriseValue = iEnterpriseValueBiz.getByTypeAndEnterpriseId(enterprise_id,types);
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanyGasHour> list = gasHourMapper.getDataByGas(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanyGasHour> map = new HashMap<>();
        for(AnalysisCompanyGasHour model : list){
            map.put(model.getUpload_date(),model);
        }
        ChartKeyValuePairVO<Double> chartKeyValuePairVO = new ChartKeyValuePairVO<>();
        chartKeyValuePairVO.setName("");
        BigDecimal totalValue = new BigDecimal("0");
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanyGasHour analysisCompanyGasHour = map.get(getTime);
            if(null!=analysisCompanyGasHour&&analysisCompanyGasHour.getData_value()!=null){
                chartKeyValuePairVO.getData().add(analysisCompanyGasHour.getData_value().doubleValue());
                totalValue = totalValue.add(analysisCompanyGasHour.getData_value());
            }else {
                chartKeyValuePairVO.getData().add(null);
            }

        }
        chartKeyValuePairVOs.add(chartKeyValuePairVO);
        chartDataVO.setTitle("用气量");
        chartDataVO.setYzTitle("单位(立方米)");
        if(list.size() >0) {
            chartDataVO.setWarning(totalValue.divide(BigDecimal.valueOf(list.size()),1,BigDecimal.ROUND_HALF_UP));
        } else {
            chartDataVO.setWarning(BigDecimal.valueOf(0));
        }
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(chartKeyValuePairVOs);
        return chartDataVO;
    }

    @Override
    public List<JSONObject> getTableDataByPower(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<JSONObject>  list = new ArrayList<>();
        JSONObject result = new JSONObject(new LinkedHashMap<>());
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanyPowerHour> analysisCompanyPowerHourList = powerHourMapper.getDataByPower(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanyPowerHour> map = new HashMap<>();
        for(AnalysisCompanyPowerHour model:analysisCompanyPowerHourList){
            map.put(model.getUpload_date(),model);
        }
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanyPowerHour analysisCompanyPowerHour = map.get(getTime);
            if(null!=analysisCompanyPowerHour&&analysisCompanyPowerHour.getData_value()!=null){
                BigDecimal bigDecimal = analysisCompanyPowerHour.getData_value();
                result.put(uploadTime.concat(":00"),bigDecimal);
                totalValue = totalValue.add(bigDecimal);
            }else{
                result.put(uploadTime.concat(":00"),null);
            }
        }
        result.put("unit","单位(千瓦时)");
        if(analysisCompanyPowerHourList.size() >0) {
            result.put("rangeValue",totalValue.divide(BigDecimal.valueOf(analysisCompanyPowerHourList.size()),1,BigDecimal.ROUND_HALF_UP)+"");
        } else {
            result.put("rangeValue","");
        }
        result.put("name",Constant.DESC.POWER+"(千瓦时)");
        list.add(result);
        return list;
    }

    @Override
    public List<JSONObject> getTableDataByWater(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<JSONObject>  list = new ArrayList<>();
        JSONObject result = new JSONObject(new LinkedHashMap<>());
        List<String> times = new ArrayList<String>();
        Integer types = 2;
        //EnterpriseValue enterpriseValue = iEnterpriseValueBiz.getByTypeAndEnterpriseId(enterprise_id,types);
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanyWaterHour> analysisCompanyWaterHourList = waterHourMapper.getDataByWater(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanyWaterHour> map = new HashMap<>();
        for(AnalysisCompanyWaterHour model:analysisCompanyWaterHourList){
            map.put(model.getUpload_date(),model);
        }
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanyWaterHour analysisCompanyWaterHour = map.get(getTime);
            if(null!=analysisCompanyWaterHour && analysisCompanyWaterHour.getData_value()!=null){
                BigDecimal bigDecimal = analysisCompanyWaterHour.getData_value();
                result.put(uploadTime.concat(":00"),bigDecimal);
                totalValue = totalValue.add(bigDecimal);
            }else{
                result.put(uploadTime.concat(":00"),null);
            }
        }
        result.put("unit","单位(吨)");
        if(analysisCompanyWaterHourList.size() >0) {
            result.put("rangeValue",totalValue.divide(BigDecimal.valueOf(analysisCompanyWaterHourList.size()),1,BigDecimal.ROUND_HALF_UP)+"");
        } else {
            result.put("rangeValue","");
        }
        result.put("name",Constant.DESC.WATER+"(吨)");
        list.add(result);
        return list;
    }

    @Override
    public List<JSONObject> getTableDataBySteam(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<JSONObject>  list = new ArrayList<>();
        JSONObject result = new JSONObject(new LinkedHashMap<>());
        List<String> times = new ArrayList<String>();
        Integer types = 4;
        //EnterpriseValue enterpriseValue = iEnterpriseValueBiz.getByTypeAndEnterpriseId(enterprise_id,types);
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanySteamHour> analysisCompanyWaterHourList = waterHourMapper.getDataBySteam(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanySteamHour> map = new HashMap<>();
        for(AnalysisCompanySteamHour model:analysisCompanyWaterHourList){
            map.put(model.getUpload_date(),model);
        }
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanySteamHour analysisCompanyWaterHour = map.get(getTime);
            if(null!=analysisCompanyWaterHour&& analysisCompanyWaterHour.getData_value()!=null){
                BigDecimal bigDecimal = analysisCompanyWaterHour.getData_value();
                result.put(uploadTime.concat(":00"),bigDecimal);
                totalValue = totalValue.add(bigDecimal);
            }else{
                result.put(uploadTime.concat(":00"),null);
            }
        }
        result.put("unit","单位(万千焦耳)");
        if(analysisCompanyWaterHourList.size() >0) {
            result.put("rangeValue",totalValue.divide(BigDecimal.valueOf(analysisCompanyWaterHourList.size()),1,BigDecimal.ROUND_HALF_UP)+"");
        } else {
            result.put("rangeValue","");
        }
        result.put("name",Constant.DESC.STEAM+"(万千焦耳)");
        list.add(result);
        return list;
    }


    @Override
    public List<JSONObject> getTableDataByfueloil(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<JSONObject>  list = new ArrayList<>();
        JSONObject result = new JSONObject(new LinkedHashMap<>());
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        List<AnalysisCompanySteamHour> analysisCompanyWaterHourList = waterHourMapper.getDataByFueloil(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanySteamHour> map = new HashMap<>();
        for(AnalysisCompanySteamHour model:analysisCompanyWaterHourList){
            map.put(model.getUpload_date(),model);
        }
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanySteamHour analysisCompanyWaterHour = map.get(getTime);
            if(null!=analysisCompanyWaterHour&& analysisCompanyWaterHour.getData_value()!=null){
                BigDecimal bigDecimal = analysisCompanyWaterHour.getData_value();
                result.put(uploadTime.concat(":00"),bigDecimal);
                totalValue = totalValue.add(bigDecimal);
            }else{
                result.put(uploadTime.concat(":00"),null);
            }
        }
        result.put("unit","单位(万千焦耳)");
        if(analysisCompanyWaterHourList.size() >0) {
            result.put("rangeValue",totalValue.divide(BigDecimal.valueOf(analysisCompanyWaterHourList.size()),1,BigDecimal.ROUND_HALF_UP)+"");
        } else {
            result.put("rangeValue","");
        }
        result.put("name",Constant.DESC.FUELOIL+"(万千焦耳)");
        list.add(result);
        return list;
    }


    @Override
    //@ClearDataSource
    public List<JSONObject> getTableDataByGas(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate) {
        List<JSONObject>  list = new ArrayList<>();
        JSONObject result = new JSONObject(new LinkedHashMap<>());
        List<String> times = new ArrayList<String>();
        Integer types = 3;
        //EnterpriseValue enterpriseValue = iEnterpriseValueBiz.getByTypeAndEnterpriseId(enterprise_id,types);
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
        }
        AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo = new AnalysisCompanyPowerHourVo();
        analysisCompanyPowerHourVo.setEnterpriseIds(enterprise_ids);
        analysisCompanyPowerHourVo.setStartTime(startTime);
        analysisCompanyPowerHourVo.setEndTime(endTime);
        //DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<AnalysisCompanyGasHour> analysisCompanyGasHourList = gasHourMapper.getDataByGas(analysisCompanyPowerHourVo);
        Map<Long,AnalysisCompanyGasHour> map = new HashMap<>();
        for(AnalysisCompanyGasHour model:analysisCompanyGasHourList){
            map.put(model.getUpload_date(),model);
        }
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            AnalysisCompanyGasHour analysisCompanyGasHour = map.get(getTime);
            if(null!=analysisCompanyGasHour && analysisCompanyGasHour.getData_value()!=null){
                BigDecimal bigDecimal = analysisCompanyGasHour.getData_value();
                result.put(uploadTime.concat(":00"),bigDecimal);
                totalValue = totalValue.add(bigDecimal);
            }else{
                result.put(uploadTime.concat(":00"),null);
            }
        }
        result.put("unit","单位(立方米)");
        if(analysisCompanyGasHourList.size() >0) {
            result.put("rangeValue",totalValue.divide(BigDecimal.valueOf(analysisCompanyGasHourList.size()),1,BigDecimal.ROUND_HALF_UP)+"");
        } else {
            result.put("rangeValue","");
        }
        result.put("name",Constant.DESC.GAS+"(立方米)");
        list.add(result);
        return list;
    }

    /*@Override
    //@ClearDataSource
    public List<JSONObject> getTableDataByEnergy(Integer enterprise_id, String type, Long startTime, Long endTime, Long nowDate) {
        List<JSONObject>  list = new ArrayList<>();
        JSONObject result = new JSONObject();
        List<String> times = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            times.add((i < 10 ? "0" + i : i + ""));
        }
        AnalysisCompanyEnergyQueryVO analysisCompanyEnergyQueryVO = new AnalysisCompanyEnergyQueryVO();
        analysisCompanyEnergyQueryVO.setEnterpriseId(enterprise_id);
        analysisCompanyEnergyQueryVO.setStart(startTime);
        analysisCompanyEnergyQueryVO.setEnd(endTime);
        DynamicDataSourceContextHolder.setDataSourceType("sharding");
        List<EnterpriseEnergyAnalysisDate> enterpriseEnergyAnalysisDates = energyMapper.queryCompanyHourForEnergy(analysisCompanyEnergyQueryVO);
        Map<Long,EnterpriseEnergyAnalysisDate> map = new HashMap<>();
        for(EnterpriseEnergyAnalysisDate model:enterpriseEnergyAnalysisDates){
            map.put(model.getUpload_date(),model);
        }
        for(String uploadTime:times){
            Long getTime = Long.valueOf(String.valueOf(nowDate).concat(uploadTime));
            EnterpriseEnergyAnalysisDate enterpriseEnergyAnalysisDate = map.get(getTime);
            if(null!=enterpriseEnergyAnalysisDate && enterpriseEnergyAnalysisDate.getData_value()!=null){
                BigDecimal bigDecimal = enterpriseEnergyAnalysisDate.getData_value();
                result.put(uploadTime.concat(":00"),bigDecimal);
            }else{
                result.put(uploadTime.concat(":00"),null);
            }
        }
        result.put("unit","暂无");
        result.put("name",Constant.DESC.ENERGY);
        list.add(result);
        return list;
    }*/

    /*@Override
    //@ClearDataSource
    public List<JSONObject> getAllHourDate(PageRequestVO<AnalysisCompanyVo> data){

        Long nowDate = DateUtil.getYMDLongTime(DateUtil.str2Date(data.getParams().getNowData(),DateUtil.MONITOR_DATE_FORMAT));
        Long startTime = Long.valueOf(String.valueOf(nowDate).concat(StringUtils.isNotBlank(data.getParams().getParamStartTime())?data.getParams().getParamStartTime():"00"));
        Long endTime = Long.valueOf(String.valueOf(nowDate).concat(StringUtils.isNotBlank(data.getParams().getParamEndTime())?data.getParams().getParamEndTime():"23"));
        JSONObject jsonObject1 = this.getTableDataByPower(data.getParams().getEnterprise_id(), Constant.DESC.POWER, startTime, endTime, nowDate).get(0);
        JSONObject jsonObject2 = this.getTableDataByWater(data.getParams().getEnterprise_id(), Constant.DESC.WATER, startTime, endTime, nowDate).get(0);
        JSONObject jsonObject3 = this.getTableDataByGas(data.getParams().getEnterprise_id(), Constant.DESC.GAS, startTime, endTime, nowDate).get(0);
        JSONObject jsonObject4 = this.getTableDataBySteam(data.getParams().getEnterprise_id(), Constant.DESC.STEAM, startTime, endTime, nowDate).get(0);
        JSONObject jsonObject5 = this.getTableDataByEnergy(data.getParams().getEnterprise_id(), Constant.DESC.ENERGY, startTime, endTime, nowDate).get(0);
        return Arrays.asList(jsonObject1,jsonObject2,jsonObject3,jsonObject4,jsonObject5);
    }*/


}
