package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.StandardTrainingProvider;
import com.tg.enterprise.model.StandardTraining;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 标准规范/培训课件 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-13
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-13			新增
 */ 

public interface StandardTrainingMapper {
	
	/**
	 * 根据主键ID查询
	 */
	@Select("select * from t_standard_training where id = #{id}")
	StandardTraining selectById(@Param("id") Long id);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = StandardTrainingProvider.class, method = "selectList")
	List<StandardTraining> selectList(StandardTraining standardTraining);
	
	/**
	 * 插入		
	 */
	@Insert("insert into t_standard_training" +
			"(title,type,edit_time,attachment,editor_id, order_num ,top_time,filename) values " +
			"(#{standardTraining.title}," +
			"#{standardTraining.type}," +
			"#{standardTraining.edit_time}," +
			"#{standardTraining.attachment}," +
			"#{standardTraining.editor_id}," +
			"#{standardTraining.order_num}," +
			"#{standardTraining.top_time}," +
			"#{standardTraining.filename})")
	int insert(@Param("standardTraining") StandardTraining standardTraining);
	
	/**
	 * 批量插入
	 */
	@Insert("<script>insert into t_standard_training" +
			"(title, type, edit_time, attachment, editor_id, order_num, top_time,filename) values " +
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" +
			"#{item.title}," +
			"#{item.type}," +
			"#{item.edit_time}," +
			"#{item.attachment}," +
			"#{item.editor_id}," +
			"#{item.order_num}," +
			"#{item.top_time}," +
			"#{item.filename}</foreach>" +
			"</script>")
	int insertBatch(@Param("list") List<StandardTraining> standardTrainingList);
	
	/**
	 * 更新		
	 */
	@UpdateProvider(type = StandardTrainingProvider.class,method= "update")
	int update(StandardTraining standardTraining);

	/**
	 * 条件删除	
	 */
    @DeleteProvider(type = StandardTrainingProvider.class, method = "delete")
	int delete(StandardTraining standardTraining);

	/**
	 * 根据主键ID删除		
	 */
	@Delete("delete from t_standard_training where id = #{id}")
	int deleteById(@Param("id") Long id);
	
	/**
	 * 批量删除		
	 */
	@Delete("<script>delete from t_standard_training where id in " +
			"<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	int delByIds(@Param("list") List<Long> list);
}