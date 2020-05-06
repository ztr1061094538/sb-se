package com.tg.enterprise.biz;

import com.alibaba.fastjson.JSONObject;
import com.tg.enterprise.vo.ChartDataVO;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.AnalysisCompanyVo;

import java.util.List;

/**
 * Created by huangjianbo on 2018/4/11
 */
public interface IAnalysisCompanyHourBiz {


    ChartDataVO<Double> getDataByPower(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    ChartDataVO<Double> getDataByWater(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    ChartDataVO<Double> getDataBySteam(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    ChartDataVO<Double> getDataByFueloil(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    ChartDataVO<Double> getDataByGas(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    List<JSONObject> getTableDataByPower(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    List<JSONObject> getTableDataByWater(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    List<JSONObject> getTableDataBySteam(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    List<JSONObject> getTableDataByfueloil(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);

    List<JSONObject> getTableDataByGas(List<Integer> enterprise_ids, String type, Long startTime, Long endTime, Long nowDate);



    /*List<JSONObject> getTableDataByEnergy(Integer enterprise_id, String type, Long startTime, Long endTime, Long nowDate);*/

    /*List<JSONObject> getAllHourDate(PageRequestVO<AnalysisCompanyVo> data);*/
}
