package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyEquipmentBiz;
import com.tg.enterprise.dao.EnergyEquipmentMapper;
import com.tg.enterprise.model.EnterpriseEnergyEquipment;
import com.tg.enterprise.vo.EnterpriseEnergyEquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyEquipmentBizImpl implements IEnergyEquipmentBiz {
	@Autowired
	EnergyEquipmentMapper mapper;

	@Override
	public EnterpriseEnergyEquipment selectById(int id) {
		return mapper.selectById(id);
	}

	@Override
	public int add(EnterpriseEnergyEquipment energyEquipment) {
		return mapper.add(energyEquipment);
	}

	@Override
	public int update(EnterpriseEnergyEquipment energyEquipment) {
		return mapper.update(energyEquipment);
	}

	@Override
	public int delByIds(List<Integer> ids) {
		return mapper.delByIds(ids);
	}

	@Override
	public PageInfo<EnterpriseEnergyEquipment> queryPageList(EnterpriseEnergyEquipmentVO energyEquipment, Integer offset, Integer count) {
		PageHelper.startPage(offset, count, true);
		List<EnterpriseEnergyEquipment> list = mapper.queryPageList(energyEquipment);
		PageInfo<EnterpriseEnergyEquipment> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public Integer selectSumbyEid() {
		return mapper.selectSumbyEid();
	}

	@Override
	public List<EnterpriseEnergyEquipment> getList(EnterpriseEnergyEquipmentVO energyEquipment) {
		return mapper.queryPageList(energyEquipment);
	}

}
