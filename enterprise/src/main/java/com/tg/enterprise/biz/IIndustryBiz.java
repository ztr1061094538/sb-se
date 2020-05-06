package com.tg.enterprise.biz;

import com.tg.enterprise.model.Industry;

import java.util.List;

public interface IIndustryBiz 
{
	List<Industry> queryAll();
	
	Industry selectById(String code);
}
