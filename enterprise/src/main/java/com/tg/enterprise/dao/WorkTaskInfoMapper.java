package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.WorkTaskInfoProvider;
import com.tg.enterprise.model.WorkTaskInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 工作任务 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-29
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-29			新增
 */ 

public interface WorkTaskInfoMapper {
	
	/**
	 * 根据主键ID查询
	 */
	@Select("select * from work_task_info where id = #{id}")
	WorkTaskInfo selectById(@Param("id") Long id);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = WorkTaskInfoProvider.class, method = "selectList")
	List<WorkTaskInfo> selectList(WorkTaskInfo workTaskInfo);
	
	/**
	 * 插入		
	 */
	@Insert("insert into work_task_info" + 
			"(title,taskTimeStart,state,time,order_num,top_time) values " +
			"(#{workTaskInfo.title}," +
			"#{workTaskInfo.taskTimeStart}," +
			"#{workTaskInfo.state}," +
			"#{workTaskInfo.time}," +
			"#{workTaskInfo.order_num}," +
			"#{workTaskInfo.top_time})")
	int insert(@Param("workTaskInfo") WorkTaskInfo workTaskInfo);
	
	/**
	 * 批量插入
	 */
	@Insert("<script>insert into work_task_info" + 
			"( title, taskTimeStart, state, time,order_num,top_time) values " +
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
			"#{item.title}," +
			"#{item.taskTimeStart}," +
			"#{item.state}," +
			"#{item.time}," +
			"#{item.order_num}," +
			"#{item.top_time}</foreach>" +
			"</script>")
	int insertBatch(@Param("list") List<WorkTaskInfo> workTaskInfoList);
	
	/**
	 * 更新		
	 */
	@UpdateProvider(type = WorkTaskInfoProvider.class,method= "update")
	int update(WorkTaskInfo workTaskInfo);

	/**
	 * 条件删除	
	 */
    @DeleteProvider(type = WorkTaskInfoProvider.class, method = "delete")
	int delete(WorkTaskInfo workTaskInfo);

	/**
	 * 根据主键ID删除		
	 */
	@Delete("delete from work_task_info where id = #{id}")
	int deleteById(@Param("id") Long id);
	
	/**
	 * 批量删除		
	 */
	@Delete("<script>delete from work_task_info where id in " +
			"<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	int delByIds(@Param("list") List<Long> list);

	@Select("select * from work_task_info where time = #{time}")
	WorkTaskInfo selectByTime(@Param("time") Long time);
}