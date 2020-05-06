package com.tg.enterprise.biz;

import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.model.Terminal;
import com.tg.enterprise.vo.SankeyData;

import java.util.List;

public interface IAnalysisTerminalBiz 
{
	SankeyData getSankey(List<Terminal> terminals, Long date, EnergyDataCode dataCode);
}
