package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IProductBiz;
import com.tg.enterprise.dao.ProductMapper;
import com.tg.enterprise.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@Service("ProductBizImpl")
public class ProductBizImpl implements IProductBiz
{
	
	@Autowired
	private ProductMapper productMapper;

	/**
	 * 根据主键ID查询
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	Product		
	 */
	@Override
	public Product selectById(Integer id)
	{
		return productMapper.selectById(id);
	}

	@Override
	public String selectUpList(Integer enterpriseId) {
        return productMapper.selectUpList(enterpriseId);
	}

	/**
	 * 全查列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	List<Product>		
	 */
	@Override
	public List<Product> selectList(Product product)
	{
		return productMapper.selectList(product);
	}
	
	/**
	 * 条件分页列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<Product>		
	 */
	@Override
	public PageInfo<Product> selectForPage(Product product, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(productMapper.selectForPage(product));
	}

	/**
	 * 插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Override
	public int insert(Product product)
	{
		return productMapper.insert(product);
	}

	/**
	 * 批量插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param	productList		参数含义
	 */
	@Override
	public int insertBatch(List<Product> productList)
	{
		return productMapper.insertBatch(productList);
	}

	/**
	 * 更新
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Override
	public int update(Product product)
	{
		return productMapper.update(product);
	}

	/**
	 * 条件删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Override
	public int delete(Product product)
	{
		return productMapper.delete(product);
	}
	
	/**
	 * 根据主键ID删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Override
	public int deleteById(Integer id)
	{
		return productMapper.deleteById(id);
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
        return productMapper.delByIds(ids);
    }
}