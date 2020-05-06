package com.tg.enterprise.biz;


import com.tg.enterprise.model.CollectHistory;

public interface ICollectHistoryBiz
{
	CollectHistory getCollectHistory(long uploadTime, int enterpiseId);
}
