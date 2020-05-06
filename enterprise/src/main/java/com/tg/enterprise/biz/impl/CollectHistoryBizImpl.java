package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.ICollectHistoryBiz;
import com.tg.enterprise.dao.CollectHistoryMapper;
import com.tg.enterprise.model.CollectHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectHistoryBizImpl implements ICollectHistoryBiz
{
	@Autowired
	private CollectHistoryMapper mapper;
	@Override
	public CollectHistory getCollectHistory(long uploadTime, int enterpiseId)
	{
		return mapper.getCollectHistory(uploadTime, enterpiseId);
	}

}
