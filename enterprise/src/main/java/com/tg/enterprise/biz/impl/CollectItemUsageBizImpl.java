package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.ICollectItemUsageBiz;
import com.tg.enterprise.dao.CollectItemUsageMapper;
import com.tg.enterprise.model.CollectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectItemUsageBizImpl implements ICollectItemUsageBiz
{
	@Autowired
	private CollectItemUsageMapper mapper;

	@Override
	public List<CollectResponse> getList() 
	{
		return mapper.getList();
	}

	@Override
	public CollectResponse selectByCode(String code)
	{
		return mapper.selectByCode(code);
	}
	
}
