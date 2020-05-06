package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.AnnouncementPoliciesEdit;
import org.apache.ibatis.jdbc.SQL;

/**
 * 通知公告 Provider
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-12			新增
 */ 
public class AnnouncementPoliciesEditProvider {

	/**
	 * 全查列表
	 */
    public String selectList(final AnnouncementPoliciesEdit announcementPoliciesEdit) {
        return new SQL() {
            {
                SELECT("*");
                FROM("t_announcement_policies_edit");
                if (announcementPoliciesEdit != null) {
                    if (announcementPoliciesEdit.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (announcementPoliciesEdit.getPublish_id() != null) {
                        WHERE("publish_id = #{publish_id}");
                    }
                    if (announcementPoliciesEdit.getTitle() != null) {
                        WHERE("title = #{title}");
                    }
                    if (announcementPoliciesEdit.getContent() != null) {
                        WHERE("content = #{content}");
                    }
                    if (announcementPoliciesEdit.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (announcementPoliciesEdit.getEditor_id() != null) {
                        WHERE("editor_id = #{editor_id}");
                    }
                    if (announcementPoliciesEdit.getEdit_time() != null) {
                        WHERE("edit_time = #{edit_time}");
                    }
                    if (announcementPoliciesEdit.getAttachment() != null) {
                        WHERE("attachment = #{attachment}");
                    }
                    if (announcementPoliciesEdit.getFilename() != null) {
                        WHERE("fileName = #{filename}");
                    }
                    if (announcementPoliciesEdit.getSource() != null) {
                        WHERE("source = #{source}");
                    }
                    if(announcementPoliciesEdit.getOrder_num() != null) {
                        WHERE("order_num = #{order_num}");
                    }
                    if(announcementPoliciesEdit.getIds() != null && !announcementPoliciesEdit.getIds().isEmpty()) {
                        StringBuilder sb = new StringBuilder("id in (");
                        for (Long str : announcementPoliciesEdit.getIds()) {
                            sb.append("'").append(str).append("'").append(",");
                        }
                        sb.deleteCharAt(sb.length()-1).append(")");
                        WHERE(sb.toString());
                    }

                    ORDER_BY("top_time desc, edit_time desc");
                }
            }
        }.toString();
    }
	
	/**
	 * 更新
	 */
	public String update(final AnnouncementPoliciesEdit announcementPoliciesEdit) {
        return new SQL() {
            {
                UPDATE("t_announcement_policies_edit");
                if (announcementPoliciesEdit != null) {
                    if (announcementPoliciesEdit.getId() != null) {
                        SET("id = #{id}");
                    }
                    if (announcementPoliciesEdit.getPublish_id() != null) {
                        SET("publish_id = #{publish_id}");
                    }
                    if (announcementPoliciesEdit.getTitle() != null) {
                        SET("title = #{title}");
                    }
                    if (announcementPoliciesEdit.getContent() != null) {
                        SET("content = #{content}");
                    }
                    if (announcementPoliciesEdit.getType() != null) {
                        SET("type = #{type}");
                    }
                    if (announcementPoliciesEdit.getEditor_id() != null) {
                        SET("editor_id = #{editor_id}");
                    }
                    if (announcementPoliciesEdit.getEdit_time() != null) {
                        SET("edit_time = #{edit_time}");
                    }
                    if (announcementPoliciesEdit.getAttachment() != null) {
                        SET("attachment = #{attachment}");
                    }
                    if (announcementPoliciesEdit.getFilename() != null) {
                        SET("fileName = #{filename}");
                    }
                    if (announcementPoliciesEdit.getSource() != null) {
                        SET("source = #{source}");
                    }
                    if (announcementPoliciesEdit.getOrder_num() != null) {
                        SET("order_num = #{order_num}");
                    }
                        SET("top_time = #{top_time}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
	
	/**
	 * 条件删除
	 */
	public String delete(final AnnouncementPoliciesEdit announcementPoliciesEdit) {
        return new SQL() {
            {
                DELETE_FROM("t_announcement_policies_edit");
                if (announcementPoliciesEdit != null) {
                    if (announcementPoliciesEdit.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (announcementPoliciesEdit.getPublish_id() != null) {
                        WHERE("publish_id = #{publish_id}");
                    }
                    if (announcementPoliciesEdit.getTitle() != null) {
                        WHERE("title = #{title}");
                    }
                    if (announcementPoliciesEdit.getContent() != null) {
                        WHERE("content = #{content}");
                    }
                    if (announcementPoliciesEdit.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (announcementPoliciesEdit.getEditor_id() != null) {
                        WHERE("editor_id = #{editor_id}");
                    }
                    if (announcementPoliciesEdit.getEdit_time() != null) {
                        WHERE("edit_time = #{edit_time}");
                    }
                    if (announcementPoliciesEdit.getAttachment() != null) {
                        WHERE("attachment = #{attachment}");
                    }
                    if (announcementPoliciesEdit.getFilename() != null) {
                        WHERE("fileName = #{filename}");
                    }
                    if (announcementPoliciesEdit.getSource() != null) {
                        WHERE("source = #{source}");
                    }
                }
            }
        }.toString();
    }

}
