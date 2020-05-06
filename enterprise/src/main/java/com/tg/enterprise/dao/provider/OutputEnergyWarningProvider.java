package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.OutputEnergyWarning;
import org.apache.ibatis.jdbc.SQL;

/**
 * 产量与能耗预警设置 Provider
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-12-05			新增
 */ 
public class OutputEnergyWarningProvider {

	/**
	 * 全查列表
	 */
    public String selectList(final OutputEnergyWarning outputEnergyWarning) {
        return new SQL() {
            {
                SELECT("*");
                FROM("output_energy_warning");
                if (outputEnergyWarning != null) {
                    if (outputEnergyWarning.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (outputEnergyWarning.getYear() != null) {
                        WHERE("year = #{year}");
                    }
                    if (outputEnergyWarning.getTotal_cons() != null) {
                        WHERE("total_cons = #{total_cons}");
                    }
                    if (outputEnergyWarning.getUnit_cons() != null) {
                        WHERE("unit_cons = #{unit_cons}");
                    }
                    if (outputEnergyWarning.getTotal_cons_per() != null) {
                        WHERE("total_cons_per = #{total_cons_per}");
                    }
                    if (outputEnergyWarning.getUnit_cons_per() != null) {
                        WHERE("unit_cons_per = #{unit_cons_per}");
                    }
                }
            }
        }.toString();
    }
	
	/**
	 * 更新
	 */
	public String update(final OutputEnergyWarning outputEnergyWarning) {
        return new SQL() {
            {
                UPDATE("output_energy_warning");
                if (outputEnergyWarning != null) {
                    if (outputEnergyWarning.getId() != null) {
                        SET("id = #{id}");
                    }
                    if (outputEnergyWarning.getYear() != null) {
                        SET("year = #{year}");
                    }
                    if (outputEnergyWarning.getTotal_cons() != null) {
                        SET("total_cons = #{total_cons}");
                    }
                    if (outputEnergyWarning.getUnit_cons() != null) {
                        SET("unit_cons = #{unit_cons}");
                    }
                    if (outputEnergyWarning.getTotal_cons_per() != null) {
                        SET("total_cons_per = #{total_cons_per}");
                    }
                    if (outputEnergyWarning.getUnit_cons_per() != null) {
                        SET("unit_cons_per = #{unit_cons_per}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
	
	/**
	 * 条件删除
	 */
	public String delete(final OutputEnergyWarning outputEnergyWarning) {
        return new SQL() {
            {
                DELETE_FROM("output_energy_warning");
                if (outputEnergyWarning != null) {
                    if (outputEnergyWarning.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (outputEnergyWarning.getYear() != null) {
                        WHERE("year = #{year}");
                    }
                    if (outputEnergyWarning.getTotal_cons() != null) {
                        WHERE("total_cons = #{total_cons}");
                    }
                    if (outputEnergyWarning.getUnit_cons() != null) {
                        WHERE("unit_cons = #{unit_cons}");
                    }
                    if (outputEnergyWarning.getTotal_cons_per() != null) {
                        WHERE("total_cons_per = #{total_cons_per}");
                    }
                    if (outputEnergyWarning.getUnit_cons_per() != null) {
                        WHERE("unit_cons_per = #{unit_cons_per}");
                    }
                }
            }
        }.toString();
    }

}
