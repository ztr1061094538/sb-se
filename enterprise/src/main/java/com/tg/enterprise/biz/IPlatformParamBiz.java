package com.tg.enterprise.biz;

import com.tg.enterprise.model.PlatformParam;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 上传参数 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-12			新增
 */ 
public interface IPlatformParamBiz
{
	/**
	 * 根据主键ID查询	
	 */
	PlatformParam selectById(String name);

	/**
	 * 全查列表	
	 */
	List<PlatformParam> selectList(PlatformParam platformParam);

	/**
	 * 条件分页列表
	 */
	PageInfo<PlatformParam> selectForPage(PlatformParam platformParam, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(PlatformParam platformParam);

	/**
	 * 批量插入
	 */
	int insertBatch(List<PlatformParam> platformParamList);

	/**
	 * 更新		
	 */
	int update(PlatformParam platformParam);




}