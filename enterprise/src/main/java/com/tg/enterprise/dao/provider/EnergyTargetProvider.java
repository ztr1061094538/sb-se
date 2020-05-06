package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergyTarget;
import com.tg.enterprise.vo.TargetQueryVO;
import org.apache.ibatis.jdbc.SQL;


public class EnergyTargetProvider {

    public String queryPageList(final EnergyTarget entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_target");
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

    public String selectSumConsumption(final TargetQueryVO entity) {
        return new SQL() {
            {
                SELECT("upload_date as uploadTime, sum(energy_consumption) as sumConsumption");
                FROM("energy_target");
                if (entity != null) {
                    if (entity.getStartTime() != null && entity.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }

    public String update(final EnergyTarget entity) {
        return new SQL() {
            {
                UPDATE("energy_target");
                if (entity != null) {
                    if (entity.getPurchase() != null) {
                        SET("purchase=#{purchase}");
                    }
                    if (entity.getRemark() != null) {
                        SET("remark=#{remark}");
                    }
                    if (entity.getEnergy_type() != null) {
                        SET("energy_type=#{energy_type}");
                    }
                    if(entity.getSupply()!=null){
                        SET("supply=#{supply}");
                    }
                    if(entity.getOpening_inventory() !=null ){
                        SET("opening_inventory=#{opening_inventory}");
                    }
                    if(entity.getEnding_inventory()!=null){
                        SET("ending_inventory=#{ending_inventory}");
                    }
                    if(entity.getEnergy_consumption()!=null){
                        SET("energy_consumption=#{energy_consumption}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
