package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.StandardTraining;
import org.apache.ibatis.jdbc.SQL;

/**
 * 标准规范/培训课件 Provider
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-06-13
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-06-13			新增
 */ 
public class StandardTrainingProvider {

	/**
	 * 全查列表
	 */
    public String selectList(final StandardTraining standardTraining) {
        return new SQL() {
            {
                SELECT("*");
                FROM("t_standard_training");
                if (standardTraining != null) {
                    if (standardTraining.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (standardTraining.getTitle() != null) {
                        WHERE("title = #{title}");
                    }
                    if (standardTraining.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (standardTraining.getEdit_time() != null) {
                        WHERE("edit_time = #{edit_time}");
                    }
                    if (standardTraining.getAttachment() != null) {
                        WHERE("attachment = #{attachment}");
                    }
                    if (standardTraining.getEditor_id() != null) {
                        WHERE("editor_id = #{editor_id}");
                    }
                    if (standardTraining.getFilename() != null) {
                        WHERE("filename = #{filename}");
                    }
                    ORDER_BY("top_time desc, edit_time desc");
                }
            }
        }.toString();
    }
	
	/**
	 * 更新
	 */
	public String update(final StandardTraining standardTraining) {
        return new SQL() {
            {
                UPDATE("t_standard_training");
                if (standardTraining != null) {
                    if (standardTraining.getId() != null) {
                        SET("id = #{id}");
                    }
                    if (standardTraining.getTitle() != null) {
                        SET("title = #{title}");
                    }
                    if (standardTraining.getType() != null) {
                        SET("type = #{type}");
                    }
                    if (standardTraining.getEdit_time() != null) {
                        SET("edit_time = #{edit_time}");
                    }
                    if (standardTraining.getAttachment() != null) {
                        SET("attachment = #{attachment}");
                    }
                    if (standardTraining.getEditor_id() != null) {
                        SET("editor_id = #{editor_id}");
                    }
                    if (standardTraining.getOrder_num() != null) {
                        SET("order_num = #{order_num}");
                    }
                    if (standardTraining.getFilename() != null) {
                        SET("filename = #{filename}");
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
	public String delete(final StandardTraining standardTraining) {
        return new SQL() {
            {
                DELETE_FROM("t_standard_training");
                if (standardTraining != null) {
                    if (standardTraining.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (standardTraining.getTitle() != null) {
                        WHERE("title = #{title}");
                    }
                    if (standardTraining.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (standardTraining.getEdit_time() != null) {
                        WHERE("edit_time = #{edit_time}");
                    }
                    if (standardTraining.getAttachment() != null) {
                        WHERE("attachment = #{attachment}");
                    }
                    if (standardTraining.getEditor_id() != null) {
                        WHERE("editor_id = #{editor_id}");
                    }
                    if (standardTraining.getOrder_num() != null) {
                        WHERE("order_num = #{order_num}");
                    }
                    if (standardTraining.getTop_time() != null) {
                        WHERE("top_time = #{top_time}");
                    }
                    if (standardTraining.getFilename() != null) {
                        WHERE("filename = #{filename}");
                    }
                }
            }
        }.toString();
    }

}
