package com.tg.enterprise.biz;

import com.tg.enterprise.model.EnergySavingProject;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 节能项目 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-19
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-19			新增
 */ 
public interface IEnergySavingProjectBiz
{
	/**
	 * 根据主键ID查询	
	 */
	EnergySavingProject selectById(Integer id);

	/**
	 * 全查列表	
	 */
	List<EnergySavingProject> selectList(EnergySavingProject energySavingProject);

	/**
	 * 条件分页列表
	 */
	PageInfo<EnergySavingProject> selectForPage(EnergySavingProject energySavingProject, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(EnergySavingProject energySavingProject);

	/**
	 * 批量插入
	 */
	int insertBatch(List<EnergySavingProject> energySavingProjectList);

	/**
	 * 更新		
	 */
	int update(EnergySavingProject energySavingProject);

	/**
	 * 条件删除		
	 */
	int delete(EnergySavingProject energySavingProject);

	/**
	 * 根据主键ID删除	
	 */
	int deleteById(Integer id);
	
	/**
	 * 根据主键ID删除		
	 */
	int delByIds(List<Integer> ids);
}