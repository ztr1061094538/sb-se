package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnterpriseEnergyEquipment;
import com.tg.enterprise.vo.EnterpriseEnergyEquipmentVO;

import java.util.List;

public interface IEnergyEquipmentBiz {
	EnterpriseEnergyEquipment selectById(int id);

	int add(EnterpriseEnergyEquipment energyEquipment);
	
	int update(EnterpriseEnergyEquipment energyEquipment);
	
	int delByIds(List<Integer> ids);
	
	PageInfo<EnterpriseEnergyEquipment> queryPageList(EnterpriseEnergyEquipmentVO energyEquipment, Integer offset, Integer count);
	
	Integer selectSumbyEid();
	
	List<EnterpriseEnergyEquipment> getList(EnterpriseEnergyEquipmentVO energyEquipment);

}
