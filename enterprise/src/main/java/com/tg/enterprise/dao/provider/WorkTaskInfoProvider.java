package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.WorkTaskInfo;
import org.apache.ibatis.jdbc.SQL;

/**
 * 工作任务 Provider
 *
 * @author fuwenxiang
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2019-11-29
 * =================Modify Record=================
 * @Modifier            @date			@Content fuwenxiang			2019-11-29			新增
 */
public class WorkTaskInfoProvider {

    /**
     * 全查列表
     */
    public String selectList(final WorkTaskInfo workTaskInfo) {
        return new SQL() {
            {
                SELECT("*");
                FROM("work_task_info");
                if (workTaskInfo != null) {
                    if (workTaskInfo.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (workTaskInfo.getTitle() != null) {
                        WHERE("title = #{title}");
                    }
                    if (workTaskInfo.getTaskTimeStart() != null) {
                        WHERE("taskTimeStart = #{taskTimeStart}");
                    }
                    if (workTaskInfo.getState() != null) {
                        WHERE("state = #{state}");
                    }
                    if (workTaskInfo.getTime() != null) {
                        WHERE("time = #{time}");
                    }
                    if (workTaskInfo.getOrder_num() != null) {
                        WHERE("order_num = #{order_num}");
                    }
                    if (workTaskInfo.getTop_time() != null) {
                        WHERE("top_time = #{top_time}");
                    }
                }
                ORDER_BY("top_time desc,time desc");
            }
        }.toString();
    }

    /**
     * 更新
     */
    public String update(final WorkTaskInfo workTaskInfo) {
        return new SQL() {
            {
                UPDATE("work_task_info");
                if (workTaskInfo != null) {
                    if (workTaskInfo.getId() != null) {
                        SET("id = #{id}");
                    }
                    if (workTaskInfo.getTitle() != null) {
                        SET("title = #{title}");
                    }
                    if (workTaskInfo.getTaskTimeStart() != null) {
                        SET("taskTimeStart = #{taskTimeStart}");
                    }
                    if (workTaskInfo.getState() != null) {
                        SET("state = #{state}");
                    }
                    if (workTaskInfo.getTime() != null) {
                        SET("time = #{time}");
                    }
                    if (workTaskInfo.getOrder_num() != null) {
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
    public String delete(final WorkTaskInfo workTaskInfo) {
        return new SQL() {
            {
                DELETE_FROM("work_task_info");
                if (workTaskInfo != null) {
                    if (workTaskInfo.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (workTaskInfo.getTitle() != null) {
                        WHERE("title = #{title}");
                    }
                    if (workTaskInfo.getTaskTimeStart() != null) {
                        WHERE("taskTimeStart = #{taskTimeStart}");
                    }
                    if (workTaskInfo.getState() != null) {
                        WHERE("state = #{state}");
                    }
                    if (workTaskInfo.getTime() != null) {
                        WHERE("time = #{time}");
                    }
                    if (workTaskInfo.getOrder_num() != null) {
                        WHERE("order_num = #{order_num}");
                    }
                    if (workTaskInfo.getTop_time() != null) {
                        WHERE("top_time = #{top_time}");
                    }
                }
            }
        }.toString();
    }

}
