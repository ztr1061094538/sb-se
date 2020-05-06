package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IProcessCodeUnitBiz;
import com.tg.enterprise.dao.ProcessCodeUnitMapper;
import com.tg.enterprise.model.ProcessCodeUnit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 生产工序单元表 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
@Service("ProcessCodeUnitBizImpl")
public class ProcessCodeUnitBizImpl implements IProcessCodeUnitBiz
{
	
	@Resource
	private ProcessCodeUnitMapper processCodeUnitMapper;
	
	/**
	 * 根据主键ID查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	ProcessCodeUnit		
	 */
	@Override
	public ProcessCodeUnit selectById(Long id)
	{
		return processCodeUnitMapper.selectById(id);
	}

	/**
	 * 全查列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	List<ProcessCodeUnit>		
	 */
	@Override
	public List<ProcessCodeUnit> selectList(ProcessCodeUnit processCodeUnit)
	{
		return processCodeUnitMapper.selectList(processCodeUnit);
	}
	
	/**
	 * 条件分页列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<ProcessCodeUnit>		
	 */
	@Override
	public PageInfo<ProcessCodeUnit> selectForPage(ProcessCodeUnit processCodeUnit, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(processCodeUnitMapper.selectForPage(processCodeUnit));
	}

	/**
	 * 插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Override
	public int insert(ProcessCodeUnit processCodeUnit)
	{
		return processCodeUnitMapper.insert(processCodeUnit);
	}

	/**
	 * 批量插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param	processCodeUnitList		参数含义
	 */
	@Override
	public int insertBatch(List<ProcessCodeUnit> processCodeUnitList)
	{
		return processCodeUnitMapper.insertBatch(processCodeUnitList);
	}

	/**
	 * 更新
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Override
	public int update(ProcessCodeUnit processCodeUnit)
	{
		return processCodeUnitMapper.update(processCodeUnit);
	}

	/**
	 * 条件删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Override
	public int delete(ProcessCodeUnit processCodeUnit)
	{
		return processCodeUnitMapper.delete(processCodeUnit);
	}
	
	/**
	 * 根据主键ID删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Override
	public int deleteById(Long id)
	{
		return processCodeUnitMapper.deleteById(id);
	}
	
	
	/**
     * 根据主键ID批量删除
     * @author panxinchao 
	 * @date 2018-12-05
     * @param 	ids		主键ID
     * @return	int		删除成功所影响的行数
     */
    @Override
    public int delByIds(List<Long> ids)
	{
        return processCodeUnitMapper.delByIds(ids);
    }

	@Override
	public ProcessCodeUnit selectByCode(String code, int enterprise_id,Long cid)
	{
		return processCodeUnitMapper.selectByCode(code, enterprise_id, cid);
	}
}