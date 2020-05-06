package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IEnergyOnlineDataBiz;
import com.tg.enterprise.dao.EnergyOnlineDataMapper;
import com.tg.enterprise.model.EnergyOnlineData;
import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.vo.EnergyOnlineDataQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyOnlineDataBizImpl implements IEnergyOnlineDataBiz {

	@Autowired
	EnergyOnlineDataMapper mapper;
	
	@Override
	public List<EnergyOnlineData> queryPower(EnergyOnlineDataQueryVO queryVo) {
		queryVo.setEnergyCode(EnergyDataCode.POWER.getDataCode());
		return mapper.queryOnlineEnergy(queryVo);
	}

	@Override
	public List<EnergyOnlineData> querySteam(EnergyOnlineDataQueryVO queryVo) {
		queryVo.setEnergyCode(EnergyDataCode.STEAM.getDataCode());
		return mapper.queryOnlineEnergy(queryVo);
	}

	@Override
	public List<EnergyOnlineData> queryWater(EnergyOnlineDataQueryVO queryVo) {
		queryVo.setEnergyCode(EnergyDataCode.WATER.getDataCode());
		return mapper.queryOnlineEnergy(queryVo);
	}

	@Override
	public List<EnergyOnlineData> queryGas(EnergyOnlineDataQueryVO queryVo) {
		queryVo.setEnergyCode(EnergyDataCode.GAS.getDataCode());
		return mapper.queryOnlineEnergy(queryVo);
	}
	
	@Override
	public List<EnergyOnlineData> queryNewestData(EnergyOnlineDataQueryVO queryVo) {
		return mapper.queryNewestData(queryVo);
	}

}
