package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergyEquipment;
import com.tg.enterprise.model.EnergyProcessTarget;
import com.tg.enterprise.vo.EnergyProcessVO;

import java.util.List;

public interface IEnergyProcessTargetBiz {
    EnergyProcessTarget selectById(Long id);

    int add(EnergyProcessTarget entity);

    PageInfo<EnergyProcessTarget> queryPageList(EnergyProcessTarget entity, Integer offset, Integer count);

    List<EnergyProcessTarget>  queryList(EnergyProcessTarget enity);

    List<EnergyEquipment> queryEquitList(EnergyEquipment entity);

    int update(EnergyProcessTarget entity);

    int updateProcess(EnergyProcessVO entity);

    EnergyProcessTarget selectByProcess(EnergyProcessVO entity);

    int delByIds(List<Long> ids);

    List<EnergyProcessTarget> selectListByProcess(EnergyProcessVO energyProcessVO);
}
