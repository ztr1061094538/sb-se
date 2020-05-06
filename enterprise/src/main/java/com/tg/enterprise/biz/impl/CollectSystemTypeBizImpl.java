package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.ICollectSystemTypeBiz;
import com.tg.enterprise.dao.CollectSystemTypeMapper;
import com.tg.enterprise.model.CollectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectSystemTypeBizImpl implements ICollectSystemTypeBiz {
	@Autowired
	private CollectSystemTypeMapper mapper;

	@Override
	public List<CollectResponse> getList() {
		return mapper.getList();
	}

	@Override
	public CollectResponse selectByCode(String code) {
		return mapper.selectByCode(code);
	}

}
