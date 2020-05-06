package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyMapperProvider;
import com.tg.enterprise.model.AnalysisCompany;
import com.tg.enterprise.model.AnalysisTerminal;
import com.tg.enterprise.vo.EnergyQueryVO;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


public interface EnergyMapper {
        @SelectProvider(type = EnergyMapperProvider.class, method = "queryListByUploadDate")
        List<AnalysisTerminal> getTermianlList(EnergyQueryVO queryVO);
        
        @SelectProvider(type = EnergyMapperProvider.class, method = "queryListByUploadDate")
        List<AnalysisCompany> getCompanyList(EnergyQueryVO queryVO);

        @SelectProvider(type = EnergyMapperProvider.class, method = "getSumTerminalConsumption")
        AnalysisTerminal getSumTerminalConsumption(EnergyQueryVO energyQueryVO);
}
