package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.biz.IMeteringInstrumentBiz;
import com.tg.enterprise.dao.MeteringInstrumentMapper;
import com.tg.enterprise.model.MeteringInstrument;
import com.tg.enterprise.vo.MeteringInstrumentQueryVO;
import com.tg.enterprise.vo.MeteringRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeteringInstrumentBizImpl implements IMeteringInstrumentBiz {

	@Autowired
	private MeteringInstrumentMapper meteringInstrumentMapper;

	@Override
	public MeteringInstrument selectById(int id) {
		return meteringInstrumentMapper.selectById(id);
	}

	@Override
	public int add(MeteringInstrument metering) {
		return meteringInstrumentMapper.add(metering);
	}

	@Override
	public int update(MeteringInstrument metering) {
		return meteringInstrumentMapper.update(metering);
	}

	@Override
	public int delByIds(List<Integer> ids) {
		return meteringInstrumentMapper.delByIds(ids);
	}

	@Override
	public List<MeteringInstrument> selectByEnterpriseId(int enterprise_id) {
		return meteringInstrumentMapper.selectByEnterpriseId(enterprise_id);
	}

	@Override
	public PageInfo<MeteringInstrument> getOvertimeEquipment(int pageIndex, int pageSize) {

		PageHelper.startPage(pageIndex, pageSize, true);
		Long startTime= DateUtil.getAfter(new Date());
		Long endTime = DateUtil.getNext(new Date());
		List<MeteringInstrument> list = meteringInstrumentMapper.getOvertimeEquipment(startTime,endTime);
		PageInfo<MeteringInstrument> info = new PageInfo<MeteringInstrument>(list);
		return info;
	}

	@Override
	public PageInfo<MeteringInstrument> queryPageList(MeteringInstrumentQueryVO metering, Integer offset, Integer count) {
		PageHelper.startPage(offset, count, true);
		List<MeteringInstrument> list = meteringInstrumentMapper.queryPageList(metering);
		PageInfo<MeteringInstrument> info = new PageInfo<MeteringInstrument>(list);
		return info;
	}
	
	@Override
	public List<MeteringInstrument> queryList(MeteringInstrumentQueryVO metering) {
		List<MeteringInstrument> list = meteringInstrumentMapper.queryPageList(metering);
		return list;
	}

	@Override
	public List<MeteringInstrument> selectByIds(List<Integer> ids) 
	{
		return meteringInstrumentMapper.selectByIds(ids);
	}

	@Override
	public List<MeteringRespVO> selectByMeteringLevel() {
		return meteringInstrumentMapper.selectByMeteringLevel();
	}

    @Override
    public List<MeteringRespVO> selectByMeteringType() {
        return meteringInstrumentMapper.selectByMeteringType();
    }

	@Override
	public Integer getTotal() {
		return meteringInstrumentMapper.getTotal();
	}
}
