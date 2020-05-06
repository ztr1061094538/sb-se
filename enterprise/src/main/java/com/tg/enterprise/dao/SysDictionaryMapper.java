package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.SysDictionaryMapperProvider;
import com.tg.enterprise.model.SysDictionary;
import com.tg.enterprise.vo.CodeNode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;


public interface SysDictionaryMapper {

	@SelectProvider(type = SysDictionaryMapperProvider.class, method = "queryList")
	List<CodeNode> queryList(SysDictionary sysDictionary);

	@SelectProvider(type = SysDictionaryMapperProvider.class, method = "getCodeNode")
	CodeNode getCodeNode(SysDictionary sysDictionary);

	@SelectProvider(type = SysDictionaryMapperProvider.class, method = "getCode")
	SysDictionary getCode(String sys_item_code);

	@SelectProvider(type = SysDictionaryMapperProvider.class, method = "getByParentCode")
	List<SysDictionary> getByParentCode(String item);
	
	@SelectProvider(type = SysDictionaryMapperProvider.class, method = "getNameByCode")
	List<CodeNode> getNameByCode(String classifyCode); 
	
	@SelectProvider(type = SysDictionaryMapperProvider.class, method = "getNameListByCode")
	CodeNode getNameListByCode(String code);

	@Insert("insert into sys_dictionary(code,parent_code,name,code_no,sort) values(#{entity.code},#{entity.parent_code},#{entity.name},#{entity.code_no},#{entity.sort})")
	int add(@Param("entity") SysDictionary entity);

	@UpdateProvider(type=SysDictionaryMapperProvider.class,method="update")
	int update(SysDictionary entity);
}
