package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IEntTypeBiz;
import com.tg.enterprise.dao.EntTypeMapper;
import com.tg.enterprise.model.EntType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntTypeBizImpl implements IEntTypeBiz
{
	@Autowired
	private EntTypeMapper mapper;
	
	@Override
	public List<EntType> queryAll()
	{
		return mapper.queryAll();
	}

	@Override
	public EntType selectById(String code) 
	{
		return mapper.selectById(code);
	}

	@Override
	public String selectNameById(String code) {
		return mapper.selectNameById(code);
	}

}
