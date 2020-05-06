package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyTargetBiz;
import com.tg.enterprise.dao.EnergyTargetMapper;
import com.tg.enterprise.model.EnergyTarget;
import com.tg.enterprise.vo.TargetQueryVO;
import com.tg.enterprise.vo.TargetRespVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnergyTargetBizImpl implements IEnergyTargetBiz {
    @Resource
    private EnergyTargetMapper mapper;


    @Override
    public EnergyTarget selectById(String energy_type, Long upload_date, Integer enterprise_id) {
        return mapper.selectById(energy_type,upload_date,enterprise_id);
    }

    @Override
    public int add(EnergyTarget entity) {
        return mapper.add(entity);
    }

    @Override
    public PageInfo<EnergyTarget> queryPageList(EnergyTarget entity, Integer offset, Integer count) {
        PageHelper.startPage(offset,count,true);
        List<EnergyTarget> list = mapper.queryPageList(entity);
        PageInfo<EnergyTarget> info = new PageInfo<EnergyTarget>(list);
        return info;
    }

    @Override
    public List<EnergyTarget> queryList(EnergyTarget entity) {
        return mapper.queryPageList(entity);
    }

    @Override
    public int update(EnergyTarget entity) {
        return mapper.update(entity);
    }

    @Override
    public int delByIds(List<Long> ids) {
        return mapper.delByIds(ids);
    }

    @Override
    public List<TargetRespVO> selectSumConsumption(TargetQueryVO targetQueryVO) {
        return mapper.selectSumConsumption(targetQueryVO);
    }
}
