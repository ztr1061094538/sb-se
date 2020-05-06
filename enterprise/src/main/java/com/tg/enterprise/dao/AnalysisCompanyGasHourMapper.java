package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.AnalysisCompanyHourProvider;
import com.tg.enterprise.model.AnalysisCompanyGasHour;
import com.tg.enterprise.vo.AnalysisCompanyPowerHourVo;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.List;

public interface AnalysisCompanyGasHourMapper {

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByGas")
    List<AnalysisCompanyGasHour> getDataByGas(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getDataByGasByMonth")
    List<AnalysisCompanyGasHour> getDataByGasByMonth(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);

    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getTotalByGas")
    BigDecimal getTotalByGas(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
    
    @SelectProvider(type = AnalysisCompanyHourProvider.class, method = "getMonthDataByGas")
    BigDecimal getMonthDataByGas(AnalysisCompanyPowerHourVo analysisCompanyPowerHourVo);
}
