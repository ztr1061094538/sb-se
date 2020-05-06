package com.tg.enterprise.biz;


import com.tg.enterprise.model.EntType;

import java.util.List;

public interface IEntTypeBiz 
{
	List<EntType> queryAll();
	
	EntType selectById(String code);

	String selectNameById(String code);
}
