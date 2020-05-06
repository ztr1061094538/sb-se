package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyProcessTargetBiz;
import com.tg.enterprise.dao.EnergyProcessTargetMapper;
import com.tg.enterprise.model.EnergyEquipment;
import com.tg.enterprise.model.EnergyProcessTarget;
import com.tg.enterprise.vo.EnergyProcessVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnergyProcessTargetBizImpl implements IEnergyProcessTargetBiz {

    @Resource
    private EnergyProcessTargetMapper mapper;

    @Override
    public EnergyProcessTarget selectById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public int add(EnergyProcessTarget entity) {
        return mapper.add(entity);
    }

    @Override
    public PageInfo<EnergyProcessTarget> queryPageList(EnergyProcessTarget entity, Integer offset, Integer count) {
        PageHelper.startPage(offset,count,true);
        List<EnergyProcessTarget> list = mapper.queryPageList(entity);
        PageInfo<EnergyProcessTarget> info = new PageInfo<EnergyProcessTarget>(list);
        return info;
    }

    @Override
    public List<EnergyProcessTarget> queryList(EnergyProcessTarget enity) {
        return mapper.queryList(enity);
    }

    @Override
    public List<EnergyEquipment> queryEquitList(EnergyEquipment entity) {
        List<EnergyEquipment> list = mapper.queryEquitList(entity);
        return list;
    }

    @Override
    public int update(EnergyProcessTarget entity) {
        return mapper.update(entity);
    }

    @Override
    public int updateProcess(EnergyProcessVO entity) {
        return mapper.updateProcee(entity);
    }

    @Override
    public EnergyProcessTarget selectByProcess(EnergyProcessVO entity) {
        return mapper.selectByProcee(entity);
    }

    @Override
    public int delByIds(List<Long> ids) {

        return mapper.delByIds(ids);
    }

    @Override
    public List<EnergyProcessTarget> selectListByProcess(EnergyProcessVO energyProcessVO) {
        return mapper.selectListByProcess(energyProcessVO);
    }
}
