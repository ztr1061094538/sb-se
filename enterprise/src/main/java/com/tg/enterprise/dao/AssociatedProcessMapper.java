package com.tg.enterprise.dao;

import com.tg.enterprise.model.AssociatedProcess;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 工作通知-任务表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author jikai.sun
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * jikai.sun			2018-12-05			新增
 */ 

public interface AssociatedProcessMapper {
	
	/**
	 * 根据主键ID查询
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	AssociatedProcess		
	 */
	@Select("select * from associated_process where id = #{id}")
	AssociatedProcess selectById(@Param("id") Integer id);
	
	/**
	 * 全查列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	List<AssociatedProcess>		
	 */
	@Select("<script>select * from associated_process where  1 = 1" + 
	"<if test=\"associatedProcess.id != null \"> AND id = #{associatedProcess.id}</if>" +
	"<if test=\"associatedProcess.proportion != null \"> AND proportion = #{associatedProcess.proportion}</if>" +
	"<if test=\"associatedProcess.uid != null \"> AND uid = #{associatedProcess.uid}</if>" +
	"<if test=\"associatedProcess.pid != null \"> AND pid = #{associatedProcess.pid}</if>" +
	"</script>")
	List<AssociatedProcess> selectList(@Param("associatedProcess") AssociatedProcess associatedProcess);
	
	/**
	 * 条件分页列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	List<AssociatedProcess>		
	 */
	@Select("<script>select * from associated_process where  1 = 1" + 
	"<if test=\"associatedProcess.id != null \"> AND id = #{associatedProcess.id}</if>" +
	"<if test=\"associatedProcess.proportion != null \"> AND proportion = #{associatedProcess.proportion}</if>" +
	"<if test=\"associatedProcess.uid != null \"> AND uid = #{associatedProcess.uid}</if>" +
	"<if test=\"associatedProcess.pid != null \"> AND pid = #{associatedProcess.pid}</if>" +
	"</script>")
	List<AssociatedProcess> selectForPage(@Param("associatedProcess") AssociatedProcess associatedProcess);
	
	/**
	 * 插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Insert("insert into associated_process" + 
	"(proportion,uid,pid) values " + 
	"(#{associatedProcess.proportion}," +
	"#{associatedProcess.uid}," +
	"#{associatedProcess.pid})")
	int insert(@Param("associatedProcess") AssociatedProcess associatedProcess);
	
	/**
	 * 批量插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param	associatedProcessList		参数含义
	 * @return	int						插入成功所影响的行数	
	 */
	@Insert("<script>insert into associated_process" + 
	"( proportion, uid, pid) values " + 
	"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
	"#{item.proportion}," +
	"#{item.uid}," +
	"#{item.pid}</foreach>" + 
	"</script>")
	int insertBatch(@Param("list") List<AssociatedProcess> associatedProcessList);
	
	/**
	 * 更新
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Update("<script>update associated_process" + 
	"<trim prefix='set' suffixOverrides=','> " +
	"<if test=\"associatedProcess.proportion != null \">proportion = #{associatedProcess.proportion},</if>" +
	"<if test=\"associatedProcess.uid != null \">uid = #{associatedProcess.uid},</if>" +
	"<if test=\"associatedProcess.pid != null \">pid = #{associatedProcess.pid}</if>" +
	"</trim>" +
	"where id = #{associatedProcess.id} " +
	"</script>")
	int update(@Param("associatedProcess") AssociatedProcess associatedProcess);

	/**
	 * 条件删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Delete("<script>delete from associated_process where  1 = 1" + 
	"<if test=\"associatedProcess.id != null \"> AND id = #{associatedProcess.id}</if>" +
	"<if test=\"associatedProcess.proportion != null \"> AND proportion = #{associatedProcess.proportion}</if>" +
	"<if test=\"associatedProcess.uid != null \"> AND uid = #{associatedProcess.uid}</if>" +
	"<if test=\"associatedProcess.pid != null \"> AND pid = #{associatedProcess.pid}</if>" +
	"</script>")
	int delete(@Param("associatedProcess") AssociatedProcess associatedProcess);

	/**
	 * 根据主键ID删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Delete("delete from associated_process where id = #{id}")
	int deleteById(@Param("id") String id);
	
	/**
	 * 批量删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	list		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Delete("<script>delete from associated_process where id in " +
	"<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
	"#{id}" +
	"</foreach>" +
	"</script>")
	int delByIds(@Param("list") List<Integer> list);
}