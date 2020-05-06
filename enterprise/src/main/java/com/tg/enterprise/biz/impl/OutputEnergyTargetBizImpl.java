package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IOutputEnergyTargetBiz;
import com.tg.enterprise.dao.OutputEnergyTargetMapper;
import com.tg.enterprise.model.OutputEnergyTarget;
import com.tg.enterprise.vo.OutputEnergyQueryVO;
import com.tg.enterprise.vo.TargetQueryVO;
import com.tg.enterprise.vo.TargetRespVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OutputEnergyTargetBizImpl implements IOutputEnergyTargetBiz {

    @Resource
    private OutputEnergyTargetMapper mapper;


    @Override
    public OutputEnergyTarget selectById(String product, int enterprise_id, Long upload_date) {
        return mapper.selectById(product,enterprise_id,upload_date);
    }

    @Override
    public int add(OutputEnergyTarget entity) {
        return mapper.add(entity);
    }

    @Override
    public PageInfo<OutputEnergyTarget> queryPageList(OutputEnergyTarget entity, Integer offset, Integer count) {
        PageHelper.startPage(offset,count,true);
        List<OutputEnergyTarget> list = mapper.queryPageList(entity);
        PageInfo<OutputEnergyTarget> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public List<OutputEnergyTarget> queryList(OutputEnergyQueryVO queryVO) {
        return mapper.queryList(queryVO);
    }

    @Override
    public List<OutputEnergyTarget> queryList(OutputEnergyTarget entity) {
        return mapper.queryPageList(entity);
    }


    @Override
    public int update(OutputEnergyTarget entity) {
        return mapper.update(entity);
    }

    @Override
    public int delByIds(List<Long> ids) {
        return mapper.delByIds(ids);
    }

    @Override
    public List<TargetRespVO> selectOutputList(TargetQueryVO targetQueryVO) {
        return mapper.selectOutputList(targetQueryVO);
    }

    @Override
    public TargetRespVO selectSumOutput(TargetQueryVO targetQueryVO) {
        return mapper.selectSumOutput(targetQueryVO);
    }
}
