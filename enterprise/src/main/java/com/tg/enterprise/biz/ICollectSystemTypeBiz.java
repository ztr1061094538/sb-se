package com.tg.enterprise.biz;


import com.tg.enterprise.model.CollectResponse;

import java.util.List;

public interface ICollectSystemTypeBiz {
	List<CollectResponse> getList();
	
	CollectResponse selectByCode(String code);
}
