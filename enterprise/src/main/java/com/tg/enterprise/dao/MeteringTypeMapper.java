package com.tg.enterprise.dao;

import com.tg.enterprise.model.MeteringType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeteringTypeMapper {
	
	@Select("select * from metering_type where id=#{id}")
	MeteringType selectById(@Param("id") Integer id);

	@Select("select * from metering_type")
    List<MeteringType> selectAll();

}
