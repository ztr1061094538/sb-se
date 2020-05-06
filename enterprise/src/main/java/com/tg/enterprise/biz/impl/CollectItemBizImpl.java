package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.ICollectItemBiz;
import com.tg.enterprise.dao.CollectItemMapper;
import com.tg.enterprise.model.CollectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectItemBizImpl implements ICollectItemBiz {
	@Autowired
	private CollectItemMapper mapper;

	@Override
	public List<CollectItem> getList(CollectItem collectItem) {
		return mapper.getList(collectItem);
	}

	@Override
	public CollectItem selectByCode(String code) {
		return mapper.selectByCode(code);
	}

}
