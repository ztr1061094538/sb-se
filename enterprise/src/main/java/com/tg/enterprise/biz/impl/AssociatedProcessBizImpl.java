package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IAssociatedProcessBiz;
import com.tg.enterprise.dao.AssociatedProcessMapper;
import com.tg.enterprise.model.AssociatedProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 工作通知-任务表 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author jikai.sun
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * jikai.sun			2018-12-05			新增
 */ 
@Service("AssociatedProcessBizImpl")
public class AssociatedProcessBizImpl implements IAssociatedProcessBiz
{
	
	@Autowired
	private AssociatedProcessMapper associatedProcessMapper;
	
	/**
	 * 根据主键ID查询
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	AssociatedProcess		
	 */
	@Override
	public AssociatedProcess selectById(Integer id)
	{
		return associatedProcessMapper.selectById(id);
	}

	/**
	 * 全查列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	List<AssociatedProcess>		
	 */
	@Override
	public List<AssociatedProcess> selectList(AssociatedProcess associatedProcess)
	{
		return associatedProcessMapper.selectList(associatedProcess);
	}
	
	/**
	 * 条件分页列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<AssociatedProcess>		
	 */
	@Override
	public PageInfo<AssociatedProcess> selectForPage(AssociatedProcess associatedProcess, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(associatedProcessMapper.selectForPage(associatedProcess));
	}

	/**
	 * 插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Override
	public int insert(AssociatedProcess associatedProcess)
	{
		return associatedProcessMapper.insert(associatedProcess);
	}

	/**
	 * 批量插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param	associatedProcessList		参数含义
	 */
	@Override
	public int insertBatch(List<AssociatedProcess> associatedProcessList)
	{
		return associatedProcessMapper.insertBatch(associatedProcessList);
	}

	/**
	 * 更新
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Override
	public int update(AssociatedProcess associatedProcess)
	{
		return associatedProcessMapper.update(associatedProcess);
	}

	/**
	 * 条件删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	associatedProcess		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Override
	public int delete(AssociatedProcess associatedProcess)
	{
		return associatedProcessMapper.delete(associatedProcess);
	}
	
	/**
	 * 根据主键ID删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Override
	public int deleteById(String id)
	{
		return associatedProcessMapper.deleteById(id);
	}
	
	
	/**
     * 根据主键ID批量删除
     * @author jikai.sun 
	 * @date 2018-12-05
     * @param 	ids		主键ID
     * @return	int		删除成功所影响的行数
     */
    @Override
    public int delByIds(List<Integer> ids)
	{
        return associatedProcessMapper.delByIds(ids);
    }
}