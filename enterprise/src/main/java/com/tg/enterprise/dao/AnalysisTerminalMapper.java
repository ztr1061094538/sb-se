package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.AnalysisTerminalMapperProvider;
import com.tg.enterprise.model.AnalysisTerminalData;
import com.tg.enterprise.vo.AnalysisTerminalQueryVO;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface AnalysisTerminalMapper 
{
	@SelectProvider(type = AnalysisTerminalMapperProvider.class, method = "getTerminalDataByDate")
	List<AnalysisTerminalData> getTerminalDataByDate(AnalysisTerminalQueryVO queryVO);
}
