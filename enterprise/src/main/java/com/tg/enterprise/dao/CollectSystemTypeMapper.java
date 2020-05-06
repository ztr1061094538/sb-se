package com.tg.enterprise.dao;

import com.tg.enterprise.model.CollectResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectSystemTypeMapper {
	@Select("select * from collect_system_type")
	List<CollectResponse> getList();
	
	@Select("select * from collect_system_type where code =#{code}")
	CollectResponse selectByCode(@Param("code") String code);
}
