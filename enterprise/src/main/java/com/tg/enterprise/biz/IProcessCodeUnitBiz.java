package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.ProcessCodeUnit;

import java.util.List;

/**
 * 生产工序单元表 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
public interface IProcessCodeUnitBiz
{
	/**
	 * 根据主键ID查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	ProcessCodeUnit		
	 */
	ProcessCodeUnit selectById(Long id);

	/**
	 * 根据code查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	ProcessCodeUnit		
	 */
	ProcessCodeUnit selectByCode(String code, int enterprise_id, Long cid);
	
	/**
	 * 全查列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	List<ProcessCodeUnit>		
	 */
	List<ProcessCodeUnit> selectList(ProcessCodeUnit processCodeUnit);

	/**
	 * 条件分页列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<ProcessCodeUnit>		
	 */
	PageInfo<ProcessCodeUnit> selectForPage(ProcessCodeUnit processCodeUnit, Integer offset, Integer count);

	/**
	 * 插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	int insert(ProcessCodeUnit processCodeUnit);

	/**
	 * 批量插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param	processCodeUnitList		参数含义
	 */
	int insertBatch(List<ProcessCodeUnit> processCodeUnitList);

	/**
	 * 更新
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	int update(ProcessCodeUnit processCodeUnit);

	/**
	 * 条件删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	processCodeUnit		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	int delete(ProcessCodeUnit processCodeUnit);

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