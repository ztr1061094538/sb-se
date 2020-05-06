package com.tg.enterprise.biz;


import com.tg.enterprise.model.CollectItem;

import java.util.List;

public interface ICollectItemBiz  {
	List<CollectItem> getList(CollectItem collectItem);
	
	CollectItem selectByCode(String code);
}
