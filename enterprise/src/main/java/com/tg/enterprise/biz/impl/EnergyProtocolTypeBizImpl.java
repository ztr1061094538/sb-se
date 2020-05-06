package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyProtocolTypeBiz;
import com.tg.enterprise.dao.EnergyProtocolTypeMapper;
import com.tg.enterprise.model.EnergyType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnergyProtocolTypeBizImpl implements IEnergyProtocolTypeBiz {

    @Resource
    private EnergyProtocolTypeMapper mapper;

    @Override
    public List<EnergyType> queryList(EnergyType enity) {
        return mapper.queryList(enity);
    }

    @Override
    public int update(EnergyType enity) {
        return mapper.update(enity);
    }

    @Override
    public PageInfo<EnergyType> pageQueryList(EnergyType enity, Integer offset, Integer count) {
        PageHelper.startPage(offset, count, true);
        return new PageInfo<>(mapper.queryList(enity));
    }

	@Override
	public EnergyType selectByCode(String code)
	{
		return mapper.selectByCode(code);
	}
}
