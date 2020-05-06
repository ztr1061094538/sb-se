package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IProcessCodeBiz;
import com.tg.enterprise.dao.ProcessCodeMapper;
import com.tg.enterprise.model.ProcessCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 生产工序表 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
@Service("ProcessCodeBizImpl")
public class ProcessCodeBizImpl implements IProcessCodeBiz
{
	
	@Resource
	private ProcessCodeMapper processCodeMapper;
	
	/**
	 * 根据主键ID查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	ProcessCode		
	 */
	@Override
	public ProcessCode selectById(Long id)
	{
		return processCodeMapper.selectById(id);
	}

	/**
	 * 全查列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	List<ProcessCode>		
	 */
	@Override
	public List<ProcessCode> selectList(ProcessCode processCode)
	{
		return processCodeMapper.selectList(processCode);
	}
	
	/**
	 * 条件分页列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<ProcessCode>		
	 */
	@Override
	public PageInfo<ProcessCode> selectForPage(ProcessCode processCode, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(processCodeMapper.selectForPage(processCode));
	}

	/**
	 * 插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Override
	public int insert(ProcessCode processCode)
	{
		return processCodeMapper.insert(processCode);
	}

	/**
	 * 批量插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param	processCodeList		参数含义
	 */
	@Override
	public int insertBatch(List<ProcessCode> processCodeList)
	{
		return processCodeMapper.insertBatch(processCodeList);
	}

	/**
	 * 更新
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Override
	public int update(ProcessCode processCode)
	{
		return processCodeMapper.update(processCode);
	}

	/**
	 * 条件删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Override
	public int delete(ProcessCode processCode)
	{
		return processCodeMapper.delete(processCode);
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
		return processCodeMapper.deleteById(id);
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
        return processCodeMapper.delByIds(ids);
    }

	@Override
	public ProcessCode selectByCode(String code,int enterprise_id) {
		return processCodeMapper.selectByCode(code, enterprise_id);
	}
}