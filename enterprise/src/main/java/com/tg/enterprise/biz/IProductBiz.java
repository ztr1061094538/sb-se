package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.Product;

import java.util.List;

/**
 * 工作通知-任务表 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author jikai.sun
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * jikai.sun			2018-12-05			新增
 */ 
public interface IProductBiz
{
	/**
	 * 根据主键ID查询
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	Product		
	 */
	Product selectById(Integer id);


	String selectUpList(Integer enterpriseId);

	/**
	 * 全查列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	List<Product>		
	 */
	List<Product> selectList(Product product);

	/**
	 * 条件分页列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<Product>		
	 */
	PageInfo<Product> selectForPage(Product product, Integer offset, Integer count);

	/**
	 * 插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	int insert(Product product);

	/**
	 * 批量插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param	productList		参数含义
	 */
	int insertBatch(List<Product> productList);

	/**
	 * 更新
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	int update(Product product);

	/**
	 * 条件删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	int delete(Product product);

	/**
	 * 根据主键ID删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int deleteById(Integer id);
	
	/**
	 * 根据主键ID删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	ids		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int delByIds(List<Integer> ids);
}