package com.tg.enterprise.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.OutputEnergyWarning;
import com.tg.enterprise.biz.IOutputEnergyWarningBiz;
import com.tg.enterprise.dao.OutputEnergyWarningMapper;
import java.util.List;


/**
 * 产量与能耗预警设置 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-12-05			新增
 */ 
@Service("OutputEnergyWarningBizImpl")
public class OutputEnergyWarningBizImpl implements IOutputEnergyWarningBiz
{
	
	@Autowired
	private OutputEnergyWarningMapper outputEnergyWarningMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public OutputEnergyWarning selectByYearAndCid(Long year ,Integer enterprise_id)
	{
		return outputEnergyWarningMapper.selectByYearAndCid(year,enterprise_id);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<OutputEnergyWarning> selectList(OutputEnergyWarning outputEnergyWarning)
	{
		return outputEnergyWarningMapper.selectList(outputEnergyWarning);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<OutputEnergyWarning> selectForPage(OutputEnergyWarning outputEnergyWarning, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(outputEnergyWarningMapper.selectList(outputEnergyWarning));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(OutputEnergyWarning outputEnergyWarning)
	{
		return outputEnergyWarningMapper.insert(outputEnergyWarning);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<OutputEnergyWarning> outputEnergyWarningList)
	{
		return outputEnergyWarningMapper.insertBatch(outputEnergyWarningList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(OutputEnergyWarning outputEnergyWarning)
	{
		return outputEnergyWarningMapper.update(outputEnergyWarning);
	}

	/**
	 * 条件删除		
	 */
	@Override
	public int delete(OutputEnergyWarning outputEnergyWarning)
	{
		return outputEnergyWarningMapper.delete(outputEnergyWarning);
	}
	
	/**
	 * 根据主键删除		
	 */
	@Override
	public int deleteById(Integer id)
	{
		return outputEnergyWarningMapper.deleteById(id);
	}
	
	
	/**
     * 批量删除
     */
    @Override
    public int delByIds(List<Integer> ids)
	{
        return outputEnergyWarningMapper.delByIds(ids);
    }
}