package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergySavingProject;
import org.apache.ibatis.jdbc.SQL;

/**
 * 节能项目 Provider
 *
 * @author fuwenxiang
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2019-11-19
 * =================Modify Record=================
 * @Modifier @date			@Content fuwenxiang			2019-11-19			新增
 */
public class EnergySavingProjectProvider {

    /**
     * 全查列表
     */
    public String selectList(final EnergySavingProject energySavingProject) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_saving_project");
                if (energySavingProject != null) {
                    if (energySavingProject.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (energySavingProject.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (energySavingProject.getName() != null) {
                        WHERE("name = #{name}");
                    }
                    if (energySavingProject.getRemark() != null) {
                        WHERE("remark = #{remark}");
                    }
                    if (energySavingProject.getStart_date() != null) {
                        WHERE("start_date = #{start_date}");
                    }
                    if (energySavingProject.getEnd_date() != null) {
                        WHERE("end_date = #{end_date}");
                    }
                    if (energySavingProject.getTerminal_ids() != null) {
                        WHERE("terminal_ids = #{terminal_ids}");
                    }
                    if (energySavingProject.getFilenames() != null) {
                        WHERE("filenames = #{filenames}");
                    }
                    if (energySavingProject.getUrls() != null) {
                        WHERE("urls = #{urls}");
                    }
                }
            }
        }.toString();
    }

    /**
     * 更新
     */
    public String update(final EnergySavingProject energySavingProject) {
        return new SQL() {
            {
                UPDATE("energy_saving_project");
                if (energySavingProject != null) {
                    if (energySavingProject.getId() != null) {
                        SET("id = #{id}");
                    }
                    if (energySavingProject.getName() != null) {
                        SET("name = #{name}");
                    }
                    if (energySavingProject.getRemark() != null) {
                        SET("remark = #{remark}");
                    }
                    if (energySavingProject.getStart_date() != null) {
                        SET("start_date = #{start_date}");
                    }
                    if (energySavingProject.getEnd_date() != null) {
                        SET("end_date = #{end_date}");
                    }
                    if (energySavingProject.getTerminal_ids() != null) {
                        SET("terminal_ids = #{terminal_ids}");
                    }
                    if (energySavingProject.getFilenames() != null) {
                        SET("filenames = #{filenames}");
                    }
                    if (energySavingProject.getUrls() != null) {
                        SET("urls = #{urls}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }

    /**
     * 条件删除
     */
    public String delete(final EnergySavingProject energySavingProject) {
        return new SQL() {
            {
                DELETE_FROM("energy_saving_project");
                if (energySavingProject != null) {
                    if (energySavingProject.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (energySavingProject.getName() != null) {
                        WHERE("name = #{name}");
                    }
                    if (energySavingProject.getRemark() != null) {
                        WHERE("remark = #{remark}");
                    }
                    if (energySavingProject.getStart_date() != null) {
                        WHERE("start_date = #{start_date}");
                    }
                    if (energySavingProject.getEnd_date() != null) {
                        WHERE("end_date = #{end_date}");
                    }
                    if (energySavingProject.getTerminal_ids() != null) {
                        WHERE("terminal_ids = #{terminal_ids}");
                    }
                    if (energySavingProject.getFilenames() != null) {
                        WHERE("filenames = #{filenames}");
                    }
                    if (energySavingProject.getUrls() != null) {
                        WHERE("urls = #{urls}");
                    }
                }
            }
        }.toString();
    }

}
