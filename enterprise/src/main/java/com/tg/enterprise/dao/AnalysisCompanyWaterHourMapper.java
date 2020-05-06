package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.AnalysisCompanyHourProvider;
import com.tg.enterprise.model.AnalysisCompanySteamHour;
import com.tg.enterprise.model.AnalysisCompanyWaterHour;
import com.tg.enterprise.vo.AnalysisCompanyPowerHourVo;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.List;

public interface AnalysisCompanyWaterHourMapper {

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByWater")
    List<AnalysisCompanyWaterHour> getDataByWater(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByWaterByMonth")
    List<AnalysisCompanyWaterHour> getDataByWaterByMonth(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataBySteam")
    List<AnalysisCompanySteamHour> getDataBySteam(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByFueloil")
    List<AnalysisCompanySteamHour> getDataByFueloil(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);


    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataBySteamByMonth")
    List<AnalysisCompanySteamHour> getDataBySteamByMonth(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getTotalByWater")
    BigDecimal getTotalByWater(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getTotalBySteam")
    BigDecimal getTotalByWSteam(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
    
    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getMonthDataByWater")
    BigDecimal getMonthDataByWater(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
    
    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getMonthDataByWSteam")
    BigDecimal getMonthDataByWSteam(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);


}
