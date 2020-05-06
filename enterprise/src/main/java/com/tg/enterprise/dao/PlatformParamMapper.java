package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.PlatformParamProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import com.tg.enterprise.model.PlatformParam;
import java.util.List;

/**
 * 上传参数 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-12			新增
 */ 

public interface PlatformParamMapper{
	
	/**
	 * 根据主键ID查询
	 */
	@Select("select * from platform_param where name = #{name}")
	PlatformParam selectById(@Param("name") String name);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = PlatformParamProvider.class, method = "selectList")
	List<PlatformParam> selectList(@Param("platformParam") PlatformParam platformParam);
	
	/**
	 * 插入		
	 */
	@Insert("insert into platform_param" + 
			"(name,value) values " +
			"(#{platformParam.name},#{platformParam.value})")
	int insert(@Param("platformParam") PlatformParam platformParam);
	
	/**
	 * 批量插入
	 */
	@Insert("<script>insert into platform_param" + 
			"(name, value) values " +
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
			"#{item.name}," +
			"#{item.value}</foreach>" +
			"</script>")
	int insertBatch(@Param("list") List<PlatformParam> platformParamList);
	
	/**
	 * 更新		
	 */
	@UpdateProvider(type = PlatformParamProvider.class,method= "update")
	int update(PlatformParam platformParam);

}