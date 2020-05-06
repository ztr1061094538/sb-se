package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IIndustryBiz;
import com.tg.enterprise.dao.IndustryMapper;
import com.tg.enterprise.model.Industry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryBizImpl implements IIndustryBiz
{
	@Autowired
	private IndustryMapper mapper;
	
	@Override
	public List<Industry> queryAll() {
		return mapper.queryAll();
	}

	@Override
	public Industry selectById(String code) {
		return mapper.selectById(code);
	}

}
