package com.tg.enterprise.dao;

import com.tg.enterprise.model.Industry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IndustryMapper 
{
	@Select("select code,fullName,pcode from industry")
	List<Industry> queryAll();
	
	@Select("select code,fullName,pcode from industry where code = #{code}")
	Industry selectById(@Param("code") String code);
}
