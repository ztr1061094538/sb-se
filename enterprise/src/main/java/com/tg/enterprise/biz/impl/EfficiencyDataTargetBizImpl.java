package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEfficiencyDataTargetBiz;
import com.tg.enterprise.dao.EfficiencyDataTargetMapper;
import com.tg.enterprise.model.EfficiencyDataTarget;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EfficiencyDataTargetBizImpl implements IEfficiencyDataTargetBiz {

    @Resource
    private EfficiencyDataTargetMapper mapper;

    @Override
    public EfficiencyDataTarget selectById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public int add(EfficiencyDataTarget entity) {
        return mapper.add(entity);
    }

    @Override
    public PageInfo<EfficiencyDataTarget> queryPageList(EfficiencyDataTarget entity, Integer offset, Integer count) {
        PageHelper.startPage(offset,count,true);
        List<EfficiencyDataTarget> list = mapper.queryPageList(entity);
        PageInfo<EfficiencyDataTarget> info = new PageInfo<EfficiencyDataTarget>(list);
        return info;
    }

    @Override
    public List<EfficiencyDataTarget> queryList(EfficiencyDataTarget entity) {
        return  mapper.queryPageList(entity);
    }

    @Override
    public int update(EfficiencyDataTarget entity) {
        return mapper.update(entity);
    }

    @Override
    public int delByIds(List<Long> ids) {
        return mapper.delByIds(ids);
    }
}
