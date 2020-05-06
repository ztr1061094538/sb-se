package com.tg.enterprise.dao;

import com.tg.enterprise.model.Region;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionMapper 
{
	@Select("select code,fullName,pcode from region where pcode=#{pcode}")
	List<Region> selectByPCode(@Param("pcode") String pcode);
	
	@Select("select code,fullName,pcode from region where code=#{code}")
	Region selectByCode(@Param("code") String code);
}
