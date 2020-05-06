package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.MeteringInstrument;
import com.tg.enterprise.vo.MeteringInstrumentQueryVO;
import com.tg.enterprise.vo.MeteringRespVO;

import java.util.List;

public interface IMeteringInstrumentBiz {

	MeteringInstrument selectById(int id);

	int add(MeteringInstrument metering);

	PageInfo<MeteringInstrument> queryPageList(MeteringInstrumentQueryVO metering, Integer offset, Integer count);

	List<MeteringInstrument> queryList(MeteringInstrumentQueryVO metering);
	
	int update(MeteringInstrument metering);

	int delByIds(List<Integer> ids);

	List<MeteringInstrument> selectByEnterpriseId(int enterprise_id);
	
    PageInfo<MeteringInstrument> getOvertimeEquipment(int pageIndex, int pageSize);
    
    List<MeteringInstrument> selectByIds(List<Integer> ids);

    List<MeteringRespVO> selectByMeteringLevel();

    List<MeteringRespVO> selectByMeteringType();

	Integer getTotal();
}
