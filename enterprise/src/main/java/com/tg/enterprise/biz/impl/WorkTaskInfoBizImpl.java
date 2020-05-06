package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IWorkTaskInfoBiz;
import com.tg.enterprise.dao.WorkTaskInfoMapper;
import com.tg.enterprise.model.WorkTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 工作任务 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-29
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-29			新增
 */ 
@Service("WorkTaskInfoBizImpl")
public class WorkTaskInfoBizImpl implements IWorkTaskInfoBiz
{
	
	@Autowired
	private WorkTaskInfoMapper workTaskInfoMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public WorkTaskInfo selectById(Long id)
	{
		return workTaskInfoMapper.selectById(id);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<WorkTaskInfo> selectList(WorkTaskInfo workTaskInfo)
	{
		return workTaskInfoMapper.selectList(workTaskInfo);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<WorkTaskInfo> selectForPage(WorkTaskInfo workTaskInfo, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(workTaskInfoMapper.selectList(workTaskInfo));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(WorkTaskInfo workTaskInfo)
	{
		return workTaskInfoMapper.insert(workTaskInfo);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<WorkTaskInfo> workTaskInfoList)
	{
		return workTaskInfoMapper.insertBatch(workTaskInfoList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(WorkTaskInfo workTaskInfo)
	{
		return workTaskInfoMapper.update(workTaskInfo);
	}

	/**
	 * 条件删除		
	 */
	@Override
	public int delete(WorkTaskInfo workTaskInfo)
	{
		return workTaskInfoMapper.delete(workTaskInfo);
	}
	
	/**
	 * 根据主键删除		
	 */
	@Override
	public int deleteById(Long id)
	{
		return workTaskInfoMapper.deleteById(id);
	}
	
	
	/**
     * 批量删除
     */
    @Override
    public int delByIds(List<Long> ids)
	{
        return workTaskInfoMapper.delByIds(ids);
    }

	@Override
	public WorkTaskInfo selectByTime(Long time) {
		return workTaskInfoMapper.selectByTime(time);
	}
}