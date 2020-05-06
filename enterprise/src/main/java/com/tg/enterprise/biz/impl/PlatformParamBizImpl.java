package com.tg.enterprise.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.PlatformParam;
import com.tg.enterprise.biz.IPlatformParamBiz;
import com.tg.enterprise.dao.PlatformParamMapper;
import java.util.List;


/**
 * 上传参数 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-12			新增
 */ 
@Service("PlatformParamBizImpl")
public class PlatformParamBizImpl implements IPlatformParamBiz
{
	
	@Autowired
	private PlatformParamMapper platformParamMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public PlatformParam selectById(String name)
	{
		return platformParamMapper.selectById(name);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<PlatformParam> selectList(PlatformParam platformParam)
	{
		return platformParamMapper.selectList(platformParam);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<PlatformParam> selectForPage(PlatformParam platformParam, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(platformParamMapper.selectList(platformParam));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(PlatformParam platformParam)
	{
		return platformParamMapper.insert(platformParam);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<PlatformParam> platformParamList)
	{
		return platformParamMapper.insertBatch(platformParamList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(PlatformParam platformParam)
	{
		return platformParamMapper.update(platformParam);
	}
	

}