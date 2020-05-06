package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.StandardTraining;

import java.util.List;

/**
 * 标准规范/培训课件 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-13
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-13			新增
 */ 
public interface IStandardTrainingBiz
{
	/**
	 * 根据主键ID查询	
	 */
	StandardTraining selectById(Long id);

	/**
	 * 全查列表	
	 */
	List<StandardTraining> selectList(StandardTraining standardTrainingEdit);

	/**
	 * 条件分页列表
	 */
	PageInfo<StandardTraining> selectForPage(StandardTraining standardTrainingEdit, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(StandardTraining standardTrainingEdit);

	/**
	 * 批量插入
	 */
	int insertBatch(List<StandardTraining> standardTrainingEditList);

	/**
	 * 更新		
	 */
	int update(StandardTraining standardTrainingEdit);

	/**
	 * 条件删除		
	 */
	int delete(StandardTraining standardTrainingEdit);

	/**
	 * 根据主键ID删除	
	 */
	int deleteById(Long id);
	
	/**
	 * 根据主键ID删除		
	 */
	int delByIds(List<Long> ids);
}