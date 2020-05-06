package com.tg.enterprise.dao;

import com.tg.enterprise.model.EntType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EntTypeMapper 
{
	@Select("select * from ent_type")
	List<EntType> queryAll();
	
	@Select("select * from ent_type where code = #{code}")
	EntType selectById(@Param("code") String code);

	@Select("select name from ent_type where code = #{code}")
	String selectNameById(@Param("code") String code);
}
