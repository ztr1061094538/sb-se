package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.AreaMapperProvider;
import com.tg.enterprise.model.Area;
import com.tg.enterprise.vo.CodeVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface AreaMapper {

	@SelectProvider(type = AreaMapperProvider.class, method = "queryList")
	List<CodeVO> queryList(Area area);
	@Select("select * from area where code = #{code}")
	Area getCode(String code);
	
	@SelectProvider(type = AreaMapperProvider.class, method = "getAreaName")
	List<CodeVO> getAreaName(String parentCode);
	
	@SelectProvider(type = AreaMapperProvider.class, method = "getAreaNameList")
	CodeVO getAreaNameList(String code);
}
