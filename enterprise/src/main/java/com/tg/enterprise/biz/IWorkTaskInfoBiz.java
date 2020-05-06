package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.WorkTaskInfo;

import java.util.List;

/**
 * 工作任务 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-29
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-29			新增
 */ 
public interface IWorkTaskInfoBiz
{
	/**
	 * 根据主键ID查询	
	 */
	WorkTaskInfo selectById(Long id);

	/**
	 * 全查列表	
	 */
	List<WorkTaskInfo> selectList(WorkTaskInfo workTaskInfo);

	/**
	 * 条件分页列表
	 */
	PageInfo<WorkTaskInfo> selectForPage(WorkTaskInfo workTaskInfo, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(WorkTaskInfo workTaskInfo);

	/**
	 * 批量插入
	 */
	int insertBatch(List<WorkTaskInfo> workTaskInfoList);

	/**
	 * 更新		
	 */
	int update(WorkTaskInfo workTaskInfo);

	/**
	 * 条件删除		
	 */
	int delete(WorkTaskInfo workTaskInfo);

	/**
	 * 根据主键ID删除	
	 */
	int deleteById(Long id);
	
	/**
	 * 根据主键ID删除		
	 */
	int delByIds(List<Long> ids);

	WorkTaskInfo selectByTime(Long time);
}