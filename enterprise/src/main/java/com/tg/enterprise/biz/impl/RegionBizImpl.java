package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IRegionBiz;
import com.tg.enterprise.dao.RegionMapper;
import com.tg.enterprise.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionBizImpl implements IRegionBiz
{
	@Autowired
	private RegionMapper mapper;
	
	@Override
	public List<Region> selectByPCode(String pcode)
	{
		return mapper.selectByPCode(pcode);
	}

	@Override
	public Region selectByCode(String code) 
	{
		return mapper.selectByCode(code);
	}

}
