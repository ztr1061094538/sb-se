package com.tg.enterprise.biz;

import com.tg.enterprise.model.OutputEnergyWarning;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 产量与能耗预警设置 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-12-05			新增
 */ 
public interface IOutputEnergyWarningBiz
{
	/**
	 * 根据主键ID查询	
	 */
	OutputEnergyWarning selectByYearAndCid(Long year,Integer enterprise_id);

	/**
	 * 全查列表	
	 */
	List<OutputEnergyWarning> selectList(OutputEnergyWarning outputEnergyWarning);

	/**
	 * 条件分页列表
	 */
	PageInfo<OutputEnergyWarning> selectForPage(OutputEnergyWarning outputEnergyWarning, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(OutputEnergyWarning outputEnergyWarning);

	/**
	 * 批量插入
	 */
	int insertBatch(List<OutputEnergyWarning> outputEnergyWarningList);

	/**
	 * 更新		
	 */
	int update(OutputEnergyWarning outputEnergyWarning);

	/**
	 * 条件删除		
	 */
	int delete(OutputEnergyWarning outputEnergyWarning);

	/**
	 * 根据主键ID删除	
	 */
	int deleteById(Integer id);
	
	/**
	 * 根据主键ID删除		
	 */
	int delByIds(List<Integer> ids);
}