package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IEnergyBiz;
import com.tg.enterprise.dao.EnergyMapper;
import com.tg.enterprise.model.AnalysisCompany;
import com.tg.enterprise.model.AnalysisTerminal;
import com.tg.enterprise.util.AnalysisEntityEnum;
import com.tg.enterprise.util.AnalysisTypeEnum;
import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.util.EnergyTypeRouter;
import com.tg.enterprise.vo.EnergyQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */
@Service
public class EnergyBizImpl implements IEnergyBiz {
    @Autowired
    private EnergyMapper mapper;
    
	@Override
	public List<AnalysisTerminal> getTermianlList(EnergyQueryVO queryVO)
	{
		return mapper.getTermianlList(queryVO );
	}

	@Override
	public List<AnalysisCompany> getCompanyList(EnergyQueryVO queryVO)
	{
		return mapper.getCompanyList(queryVO );
	}

	@Override
	public AnalysisTerminal getSumTerminalConsumption(EnergyQueryVO energyQueryVO) {
		return mapper.getSumTerminalConsumption(energyQueryVO);
	}

}
