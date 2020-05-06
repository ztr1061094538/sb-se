package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.AnalysisCompanyHourProvider;
import com.tg.enterprise.model.AnalysisCompanyPowerHour;
import com.tg.enterprise.vo.AnalysisCompanyPowerHourVo;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.List;

public interface AnalysisCompanyPowerHourMapper {

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByPower")
    List<AnalysisCompanyPowerHour> getDataByPower(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByPowerByMonth")
    List<AnalysisCompanyPowerHour> getDataByPowerByMonth(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getTotalByPower")
    BigDecimal getTotalByPower(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
    
    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getTotalByEnergy")
    BigDecimal getTotalByEnergy(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
    
    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getTotalYearByEnergy")
    BigDecimal getTotalYearByEnergy(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
    
    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getMonthDataByPower")
    BigDecimal getMonthDataByPower(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
}
