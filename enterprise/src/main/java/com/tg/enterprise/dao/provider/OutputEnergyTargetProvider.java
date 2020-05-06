package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.OutputEnergyTarget;
import com.tg.enterprise.vo.OutputEnergyQueryVO;
import com.tg.enterprise.vo.TargetQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;


public class OutputEnergyTargetProvider {

    public String queryPageList(final OutputEnergyTarget entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("output_energy_target");
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

    public String queryList(final OutputEnergyQueryVO entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("output_energy_target");
                if (entity != null) {
                    if (entity.getUpload_date() != null) {
                        WHERE("upload_date=#{upload_date}");
                    }
                    if (entity.getEnterprise_id() != null) {
                        WHERE("enterprise_id=#{enterprise_id}");
                    }
                    if(entity.getList()!=null&&!entity.getList().isEmpty()) {
                        StringBuilder sb = new StringBuilder("product in ('");
                        sb.append(String.join("','", entity.getList())).append("')");
                        WHERE(sb.toString());
                    }else{
                        WHERE("product!='gycz' and product!='gyzj'");
                    }
                }
            }
        }.toString();
    }

    public String update(final OutputEnergyTarget entity) {
        return new SQL() {
            {
                UPDATE("output_energy_target");
                if (entity != null) {
                    if (entity.getYield_value() != null) {
                        SET("yield_value=#{yield_value}");
                    }
                    if (entity.getRemark() != null) {
                        SET("remark=#{remark}");
                    }
                    if (entity.getOutput_value() != null) {
                        SET("output_value=#{output_value}");
                    }
                    if(entity.getUnit_yield()!=null){
                        SET("unit_yield=#{unit_yield}");
                    }
                }
                WHERE("product = #{product} and enterprise_id =#{enterprise_id} and upload_date =#{upload_date}");
            }
        }.toString();
    }

    public String selectOutputList(final TargetQueryVO entity) {
        return new SQL() {
            {
                SELECT("upload_date as uploadTime, sum(yield_value) as sumYield, sum(output_value) as sumOutput");
                FROM("output_energy_target");
                if (entity != null) {
                    if (entity.getStartTime() != null && entity.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                    if(entity.getUpload_date() != null) {
                        WHERE("upload_date = #{upload_date}");
                    }
                    if( !CollectionUtils.isEmpty(entity.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(entity.getEnterpriseIds(), ",") + ")");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }

    public String selectSumOutput(final TargetQueryVO entity) {
        return new SQL() {
            {
                SELECT("sum(yield_value) as sumYield, sum(output_value) as sumOutput");
                FROM("output_energy_target");
                if (entity != null) {
                    if (entity.getStartTime() != null && entity.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                    if(entity.getUpload_date() != null) {
                        WHERE("upload_date = #{upload_date}");
                    }
                    if( !CollectionUtils.isEmpty(entity.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(entity.getEnterpriseIds(), ",") + ")");
                    }
                }
            }
        }.toString();
    }
}
