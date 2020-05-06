package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergySavingProjectProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import com.tg.enterprise.model.EnergySavingProject;
import java.util.List;

/**
 * 节能项目 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-19
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-19			新增
 */ 

public interface EnergySavingProjectMapper{
	
	/**
	 * 根据主键ID查询
	 */
	@Select("select * from energy_saving_project where id = #{id}")
	EnergySavingProject selectById(@Param("id") Integer id);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = EnergySavingProjectProvider.class, method = "selectList")
	List<EnergySavingProject> selectList(EnergySavingProject energySavingProject);
	
	/**
	 * 插入		
	 */
	@Insert("insert into energy_saving_project" + 
			"(name,remark,start_date,end_date,terminal_ids,filenames,urls) values " + 
			"(#{energySavingProject.name}," +
			"#{energySavingProject.remark}," +
			"#{energySavingProject.start_date}," +
			"#{energySavingProject.end_date}," +
			"#{energySavingProject.terminal_ids}," +
			"#{energySavingProject.filenames}," +
			"#{energySavingProject.urls})")
	int insert(@Param("energySavingProject") EnergySavingProject energySavingProject);
	
	/**
	 * 批量插入
	 */
	@Insert("<script>insert into energy_saving_project" + 
			"( name, remark, start_date, end_date, terminal_ids, filenames, urls) values " + 
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
			"#{item.name}," +
			"#{item.remark}," +
			"#{item.start_date}," +
			"#{item.end_date}," +
			"#{item.terminal_ids}," +
			"#{item.filenames}," +
			"#{item.urls}</foreach>" + 
			"</script>")
	int insertBatch(@Param("list") List<EnergySavingProject> energySavingProjectList);
	
	/**
	 * 更新		
	 */
	@UpdateProvider(type = EnergySavingProjectProvider.class,method= "update")
	int update(EnergySavingProject energySavingProject);

	/**
	 * 条件删除	
	 */
    @DeleteProvider(type = EnergySavingProjectProvider.class, method = "delete")
	int delete(EnergySavingProject energySavingProject);

	/**
	 * 根据主键ID删除		
	 */
	@Delete("delete from energy_saving_project where id = #{id}")
	int deleteById(@Param("id") Integer id);
	
	/**
	 * 批量删除		
	 */
	@Delete("<script>delete from energy_saving_project where id in " +
			"<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	int delByIds(@Param("list") List<Integer> list);
}