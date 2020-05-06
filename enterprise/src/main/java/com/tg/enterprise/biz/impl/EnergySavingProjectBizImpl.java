package com.tg.enterprise.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergySavingProject;
import com.tg.enterprise.biz.IEnergySavingProjectBiz;
import com.tg.enterprise.dao.EnergySavingProjectMapper;
import java.util.List;


/**
 * 节能项目 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-19
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-19			新增
 */ 
@Service("EnergySavingProjectBizImpl")
public class EnergySavingProjectBizImpl implements IEnergySavingProjectBiz
{
	
	@Autowired
	private EnergySavingProjectMapper energySavingProjectMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public EnergySavingProject selectById(Integer id)
	{
		return energySavingProjectMapper.selectById(id);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<EnergySavingProject> selectList(EnergySavingProject energySavingProject)
	{
		return energySavingProjectMapper.selectList(energySavingProject);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<EnergySavingProject> selectForPage(EnergySavingProject energySavingProject, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(energySavingProjectMapper.selectList(energySavingProject));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(EnergySavingProject energySavingProject)
	{
		return energySavingProjectMapper.insert(energySavingProject);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<EnergySavingProject> energySavingProjectList)
	{
		return energySavingProjectMapper.insertBatch(energySavingProjectList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(EnergySavingProject energySavingProject)
	{
		return energySavingProjectMapper.update(energySavingProject);
	}

	/**
	 * 条件删除		
	 */
	@Override
	public int delete(EnergySavingProject energySavingProject)
	{
		return energySavingProjectMapper.delete(energySavingProject);
	}
	
	/**
	 * 根据主键删除		
	 */
	@Override
	public int deleteById(Integer id)
	{
		return energySavingProjectMapper.deleteById(id);
	}
	
	
	/**
     * 批量删除
     */
    @Override
    public int delByIds(List<Integer> ids)
	{
        return energySavingProjectMapper.delByIds(ids);
    }
}