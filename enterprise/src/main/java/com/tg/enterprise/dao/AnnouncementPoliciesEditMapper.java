package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.AnnouncementPoliciesEditProvider;
import com.tg.enterprise.model.AnnouncementPoliciesEdit;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 通知公告 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-12			新增
 */ 

public interface AnnouncementPoliciesEditMapper {
	
	/**
	 * 根据主键ID查询
	 */
	@Select("select * from t_announcement_policies_edit where id = #{id}")
	AnnouncementPoliciesEdit selectById(@Param("id") Long id);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = AnnouncementPoliciesEditProvider.class, method = "selectList")
	List<AnnouncementPoliciesEdit> selectList(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 插入
	 */
	@Insert("insert into t_announcement_policies_edit" +
			"(publish_id,title,content,type,editor_id,edit_time,attachment,filename,source, order_num, top_time) values " +
			"(#{announcementPoliciesEdit.publish_id}," +
			"#{announcementPoliciesEdit.title}," +
			"#{announcementPoliciesEdit.content}," +
			"#{announcementPoliciesEdit.type}," +
			"#{announcementPoliciesEdit.editor_id}," +
			"#{announcementPoliciesEdit.edit_time}," +
			"#{announcementPoliciesEdit.attachment}," +
			"#{announcementPoliciesEdit.filename}," +
			"#{announcementPoliciesEdit.source}," +
			"#{announcementPoliciesEdit.order_num}," +
			"#{announcementPoliciesEdit.top_time})")
	int insert(@Param("announcementPoliciesEdit") AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 批量插入
	 */
	@Insert("<script>insert into t_announcement_policies_edit" +
			"( publish_id, title, content, type, editor_id, edit_time, attachment, filename, source, order_num, top_time) values " +
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" +
			"#{item.publish_id}," +
			"#{item.title}," +
			"#{item.content}," +
			"#{item.type}," +
			"#{item.editor_id}," +
			"#{item.edit_time}," +
			"#{item.attachment}," +
			"#{item.filename}," +
			"#{item.source}," +
			"#{item.order_num}," +
			"#{item.top_time}</foreach>" +
			"</script>")
	int insertBatch(@Param("list") List<AnnouncementPoliciesEdit> announcementPoliciesEditList);

	/**
	 * 更新
	 */
	@UpdateProvider(type = AnnouncementPoliciesEditProvider.class,method= "update")
	int update(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 条件删除
	 */
    @DeleteProvider(type = AnnouncementPoliciesEditProvider.class, method = "delete")
	int delete(AnnouncementPoliciesEdit announcementPoliciesEdit);

	/**
	 * 根据主键ID删除		
	 */
	@Delete("delete from t_announcement_policies_edit where id = #{id}")
	int deleteById(@Param("id") Long id);
	
	/**
	 * 批量删除		
	 */
	@Delete("<script>delete from t_announcement_policies_edit where id in " +
			"<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	int delByIds(@Param("list") List<Long> list);
}