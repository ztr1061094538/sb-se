package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IAnnouncementPoliciesEditBiz;
import com.tg.enterprise.dao.AnnouncementPoliciesEditMapper;
import com.tg.enterprise.model.AnnouncementPoliciesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 通知公告 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-12			新增
 */ 
@Service("AnnouncementPoliciesEditBizImpl")
public class AnnouncementPoliciesEditBizImpl implements IAnnouncementPoliciesEditBiz
{
	
	@Autowired
	private AnnouncementPoliciesEditMapper announcementPoliciesEditMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public AnnouncementPoliciesEdit selectById(Long id)
	{
		return announcementPoliciesEditMapper.selectById(id);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<AnnouncementPoliciesEdit> selectList(AnnouncementPoliciesEdit announcementPoliciesEdit)
	{
		return announcementPoliciesEditMapper.selectList(announcementPoliciesEdit);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<AnnouncementPoliciesEdit> selectForPage(AnnouncementPoliciesEdit announcementPoliciesEdit, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(announcementPoliciesEditMapper.selectList(announcementPoliciesEdit));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(AnnouncementPoliciesEdit announcementPoliciesEdit)
	{
		return announcementPoliciesEditMapper.insert(announcementPoliciesEdit);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<AnnouncementPoliciesEdit> announcementPoliciesEditList)
	{
		return announcementPoliciesEditMapper.insertBatch(announcementPoliciesEditList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(AnnouncementPoliciesEdit announcementPoliciesEdit)
	{
		return announcementPoliciesEditMapper.update(announcementPoliciesEdit);
	}

	/**
	 * 条件删除		
	 */
	@Override
	public int delete(AnnouncementPoliciesEdit announcementPoliciesEdit)
	{
		return announcementPoliciesEditMapper.delete(announcementPoliciesEdit);
	}
	
	/**
	 * 根据主键删除		
	 */
	@Override
	public int deleteById(Long id)
	{
		return announcementPoliciesEditMapper.deleteById(id);
	}
	
	
	/**
     * 批量删除
     */
    @Override
    public int delByIds(List<Long> ids)
	{
        return announcementPoliciesEditMapper.delByIds(ids);
    }

	@Override
	public int delById(Long id) {
		return announcementPoliciesEditMapper.deleteById(id);
	}
}