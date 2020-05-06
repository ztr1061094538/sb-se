package com.tg.enterprise.biz;

import com.tg.enterprise.model.EnergyOnlineData;
import com.tg.enterprise.vo.EnergyOnlineDataQueryVO;

import java.util.List;

/**
 * EM2 平台 Online表数据
 * @author Administrator
 *
 */
public interface IEnergyOnlineDataBiz {
	public List<EnergyOnlineData> queryPower(EnergyOnlineDataQueryVO queryVo);
	public List<EnergyOnlineData> querySteam(EnergyOnlineDataQueryVO queryVo);
	public List<EnergyOnlineData> queryWater(EnergyOnlineDataQueryVO queryVo);
	public List<EnergyOnlineData> queryGas(EnergyOnlineDataQueryVO queryVo);
	/**
	 * 查询最新的一条监测点记录
	 * @param queryVo
	 * @return
	 */
	public List<EnergyOnlineData> queryNewestData(EnergyOnlineDataQueryVO queryVo);
}
