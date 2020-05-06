package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnterpriseBiz;
import com.tg.enterprise.dao.EnterpriseMapper;
import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.vo.EnterpriseQueryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */
@Service
public class EnterpriseBizImpl implements IEnterpriseBiz
{
    @Resource
    private EnterpriseMapper mapper;

    @Override
    public Enterprise selectById(int id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Enterprise> getList(EnterpriseQueryVO entity) {
        return mapper.queryPageList(entity);
    }

    @Override
    public PageInfo<Enterprise> queryPageList(EnterpriseQueryVO entity, Integer offset, Integer count) {
        PageHelper.startPage(offset, count, true);
        List<Enterprise> list = mapper.queryPageList(entity);
        PageInfo<Enterprise> info = new PageInfo<Enterprise>(list);
        return info;
    }

    @Override
    public int add(Enterprise entity) {
        return mapper.add(entity);
    }

    @Override
    public int update(Enterprise entity) {
        return mapper.update(entity);
    }

    @Override
    public int delByIds(List<Integer> ids) {
        return mapper.delByIds(ids);
    }

    @Override
    public List<Integer> getIdListByType(int type) {
        return mapper.getIdListByType(type);
    }
}
