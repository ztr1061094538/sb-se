package com.tg.enterprise.biz;


import com.tg.enterprise.model.Region;

import java.util.List;

public interface IRegionBiz 
{
	List<Region> selectByPCode(String pcode);
	
	Region selectByCode(String code);
}
