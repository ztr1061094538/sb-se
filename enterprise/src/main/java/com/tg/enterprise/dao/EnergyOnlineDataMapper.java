package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyOnlineDataMapperProvider;
import com.tg.enterprise.model.EnergyOnlineData;
import com.tg.enterprise.vo.EnergyOnlineDataQueryVO;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 电能在线数据
 *
 */
public interface EnergyOnlineDataMapper 
{
	@SelectProvider(type= EnergyOnlineDataMapperProvider.class,method="query")
	List<EnergyOnlineData> queryOnlineEnergy(EnergyOnlineDataQueryVO queryVO);
	
	@SelectProvider(type=EnergyOnlineDataMapperProvider.class,method="queryNewestData")
	List<EnergyOnlineData> queryNewestData(EnergyOnlineDataQueryVO queryVO);
}
