package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EfficiencyDataTarget;
import org.apache.ibatis.jdbc.SQL;


public class EfficiencyDataTargetProvider {

    public String queryPageList(final EfficiencyDataTarget entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("efficiency_data_target t1");
                if (entity != null) {
                    if (entity.getUpload_date() != null) {
                        WHERE("upload_date=#{upload_date}");
                    }
                    if (entity.getEnterprise_id() != null) {
                        WHERE("enterprise_id=#{enterprise_id}");
                    }
                }
            }
        }.toString();
    }

    public String update(final EfficiencyDataTarget entity) {
        return new SQL() {
            {
                UPDATE("efficiency_data_target");
                if (entity != null) {
                    if (entity.getData_value() != null) {
                        SET("data_value=#{data_value}");
                    }
                    if (entity.getRemark() != null) {
                        SET("remark=#{remark}");
                    }
                    if (entity.getEfficiency_data() != null) {
                        SET("efficiency_data=#{efficiency_data}");
                    }
                    if(entity.getUnit()!=null){
                        SET("unit=#{unit}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
