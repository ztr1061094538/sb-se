package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.ProcessCode;

import java.util.List;

/**
 * 生产工序表 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
public interface IProcessCodeBiz
{
	/**
	 * 根据主键ID查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	ProcessCode		
	 */
	ProcessCode selectById(Long id);
	
    ProcessCode selectByCode(String code, int enterprise_id);

	/**
	 * 全查列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	List<ProcessCode>		
	 */
	List<ProcessCode> selectList(ProcessCode processCode);

	/**
	 * 条件分页列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<ProcessCode>		
	 */
	PageInfo<ProcessCode> selectForPage(ProcessCode processCode, Integer offset, Integer count);

	/**
	 * 插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	int insert(ProcessCode processCode);

	/**
	 * 批量插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param	processCodeList		参数含义
	 */
	int insertBatch(List<ProcessCode> processCodeList);

	/**
	 * 更新
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	int update(ProcessCode processCode);

	/**
	 * 条件删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCode		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	int delete(ProcessCode processCode);

	/**
	 * 根据主键ID删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int deleteById(Long id);
	
	/**
	 * 根据主键ID删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	ids		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int delByIds(List<Long> ids);
}