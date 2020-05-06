package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.AnnouncementPoliciesEdit;

import java.util.List;

/**
 * 通知公告 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-12			新增
 */ 
public interface IAnnouncementPoliciesEditBiz
{
	/**
	 * 根据主键ID查询	
	 */
	AnnouncementPoliciesEdit selectById(Long id);

	/**
	 * 全查列表	
	 */
	List<AnnouncementPoliciesEdit> selectList(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 条件分页列表
	 */
	PageInfo<AnnouncementPoliciesEdit> selectForPage(AnnouncementPoliciesEdit announcementPoliciesEdit, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 批量插入
	 */
	int insertBatch(List<AnnouncementPoliciesEdit> announcementPoliciesEditList);

	/**
	 * 更新		
	 */
	int update(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 条件删除		
	 */
	int delete(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 根据主键ID删除	
	 */
	int deleteById(Long id);
	
	/**
	 * 根据主键ID删除		
	 */
	int delByIds(List<Long> ids);

    int delById(Long id);
}