package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyExpertBiz;
import com.tg.enterprise.dao.EnergyExpertMapper;
import com.tg.enterprise.model.EnergyExpert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangjianbo on 2018/3/7
 */
@Service
public class EnergyExpertImpl implements IEnergyExpertBiz{

    @Autowired
    private EnergyExpertMapper mapper;
    @Override
    public EnergyExpert selectById(int id) {
        return mapper.selectById(id);
    }

    @Override
    public int add(EnergyExpert entity) {
        return mapper.add(entity);
    }

    @Override
    public PageInfo<EnergyExpert> queryPageList(EnergyExpert entity, Integer offset, Integer count) {
        PageHelper.startPage(offset, count, true);
        List<EnergyExpert> list = mapper.queryPageList(entity);
        PageInfo<EnergyExpert> info = new PageInfo<EnergyExpert>(list);
        return info;
    }

    @Override
    public int update(EnergyExpert entity) {
        return mapper.update(entity);
    }

    @Override
    public int delByIds(List<Integer> ids) {
        return mapper.delByIds(ids);
    }
}
