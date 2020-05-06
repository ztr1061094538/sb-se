package com.tg.enterprise.dao;

import com.tg.enterprise.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 工作通知-任务表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author jikai.sun
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * jikai.sun			2018-12-05			新增
 */ 

public interface ProductMapper{
	
	/**
	 * 根据主键ID查询
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	Product		
	 */
	@Select("select * from product where id = #{id}")
	Product selectById(@Param("id") Integer id);

	@Select("SELECT time FROM `product` where enterprise_id=#{enterpriseId} GROUP BY time ORDER BY time DESC LIMIT 1 ")
	String selectUpList(@Param("enterpriseId") Integer enterpriseId);
	/**
	 * 全查列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	List<Product>		
	 */
	@Select("<script>select * from product where  1 = 1" + 
	"<if test=\"product.id != null \"> AND id = #{product.id}</if>" +
	"<if test=\"product.enterprise_id != null \"> AND enterprise_id = #{product.enterprise_id}</if>" +
	"<if test=\"product.name != null and product.name != '' \"> AND name = #{product.name}</if>" +
	"<if test=\"product.remark != null and product.remark != '' \"> AND remark = #{product.remark}</if>" +
	"</script>")
	List<Product> selectList(@Param("product") Product product);
	
	/**
	 * 条件分页列表
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	List<Product>		
	 */
	@Select("<script>select * from product where  1 = 1" + 
	"<if test=\"product.id != null \"> AND id = #{product.id}</if>" +
	"<if test=\"product.enterprise_id != null \"> AND enterprise_id = #{product.enterprise_id}</if>" +
	"<if test=\"product.name != null and product.name != '' \"> AND name like  CONCAT('%',#{product.name},'%')</if>" +
	"<if test=\"product.remark != null and product.remark != '' \"> AND remark like CONCAT('%',#{product.remark},'%')</if>" +
	"</script>")
	List<Product> selectForPage(@Param("product") Product product);
	
	/**
	 * 插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Insert("insert into product" + 
	"(enterprise_id,name,remark) values " + 
	"(#{product.enterprise_id}," +
	"#{product.name}," +
	"#{product.remark})")
	int insert(@Param("product") Product product);
	
	/**
	 * 批量插入
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param	productList		参数含义
	 * @return	int						插入成功所影响的行数	
	 */
	@Insert("<script>insert into product" + 
	"( enterprise_id, name, remark) values " + 
	"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
	"#{item.enterprise_id}," +
	"#{item.name}," +
	"#{item.remark}</foreach>" + 
	"</script>")
	int insertBatch(@Param("list") List<Product> productList);
	
	/**
	 * 更新
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Update("<script>update product" + 
	"<trim prefix='set' suffixOverrides=','> " +
	"<if test=\"product.enterprise_id != null \">enterprise_id = #{product.enterprise_id},</if>" +
	"<if test=\"product.name != null \">name = #{product.name},</if>" +
	"<if test=\"product.remark != null \">remark = #{product.remark}</if>" +
	"</trim>" +
	"where id = #{product.id} " +
	"</script>")
	int update(@Param("product") Product product);

	/**
	 * 条件删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	product		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Delete("<script>delete from product where  1 = 1" + 
	"<if test=\"product.id != null \"> AND id = #{product.id}</if>" +
	"<if test=\"product.enterprise_id != null \"> AND enterprise_id = #{product.enterprise_id}</if>" +
	"<if test=\"product.name != null and product.name != '' \"> AND name = #{product.name}</if>" +
	"<if test=\"product.remark != null and product.remark != '' \"> AND remark = #{product.remark}</if>" +
	"</script>")
	int delete(@Param("product") Product product);

	/**
	 * 根据主键ID删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Delete("delete from product where id = #{id}")
	int deleteById(@Param("id") Integer id);
	
	/**
	 * 批量删除
	 * @author jikai.sun 
	 * @date 2018-12-05
	 * @param 	list		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Delete("<script>delete from product where id in " +
	"<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
	"#{id}" +
	"</foreach>" +
	"</script>")
	int delByIds(@Param("list") List<Integer> list);

	@Select("<script>select * from product where id in " +
			"<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	List<Product> selectByCompanyIds(@Param("ids") List<Integer> companyId);
}