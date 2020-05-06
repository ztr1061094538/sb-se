package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IStandardTrainingBiz;
import com.tg.enterprise.dao.StandardTrainingMapper;
import com.tg.enterprise.model.StandardTraining;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 标准规范/培训课件 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-13
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-13			新增
 */ 
@Service("StandardTrainingEditBizImpl")
public class StandardTrainingBizImpl implements IStandardTrainingBiz
{
	
	@Autowired
	private StandardTrainingMapper standardTrainingMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public StandardTraining selectById(Long id)
	{
		return standardTrainingMapper.selectById(id);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<StandardTraining> selectList(StandardTraining standardTrainingEdit)
	{
		return standardTrainingMapper.selectList(standardTrainingEdit);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<StandardTraining> selectForPage(StandardTraining standardTrainingEdit, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(standardTrainingMapper.selectList(standardTrainingEdit));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(StandardTraining standardTrainingEdit)
	{
		return standardTrainingMapper.insert(standardTrainingEdit);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<StandardTraining> standardTrainingEditList)
	{
		return standardTrainingMapper.insertBatch(standardTrainingEditList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(StandardTraining standardTrainingEdit)
	{
		return standardTrainingMapper.update(standardTrainingEdit);
	}

	/**
	 * 条件删除		
	 */
	@Override
	public int delete(StandardTraining standardTrainingEdit)
	{
		return standardTrainingMapper.delete(standardTrainingEdit);
	}
	
	/**
	 * 根据主键删除		
	 */
	@Override
	public int deleteById(Long id)
	{
		return standardTrainingMapper.deleteById(id);
	}
	
	
	/**
     * 批量删除
     */
    @Override
    public int delByIds(List<Long> ids)
	{
        return standardTrainingMapper.delByIds(ids);
    }
}