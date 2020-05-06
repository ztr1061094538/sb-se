package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergyEquipment;
import com.tg.enterprise.model.EnergyProcessTarget;
import com.tg.enterprise.util.SQLUtils;
import com.tg.enterprise.vo.EnergyProcessVO;
import org.apache.ibatis.jdbc.SQL;


public class EnergyProcessTargetProvider {

    public String queryPageList(final EnergyProcessTarget entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_process_target");
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

    public String queryList(final EnergyProcessTarget entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_process_target");
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

    public String queryEquitList(final EnergyEquipment entity) {
        return new SQL() {
            {
                SELECT("id,companyId,name,category");
                FROM("energy_equipment_contact");
                if (entity != null) {
                    if (entity.getCompanyId() != null) {
                        WHERE("companyId=#{companyId}");
                    }
                    if (entity.getInputType() != null) {
                        WHERE("inputType=#{inputType}");
                    }
                }
            }
        }.toString();
    }


    public String update(final EnergyProcessTarget entity) {
        return new SQL() {
            {
                UPDATE("energy_process_target");
                if (entity != null) {
                    if (entity.getData_value() != null) {
                        SET("data_value=#{data_value}");
                    }
                    if (entity.getRemark() != null) {
                        SET("remark=#{remark}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String updateProcee(final EnergyProcessVO entity) {
        return new SQL() {
            {
                UPDATE("energy_process_target");
                if (entity != null) {
                    if (entity.getData_value() != null) {
                        SET("data_value=#{data_value}");
                    }
                    if (entity.getRemark() != null) {
                        SET("remark=#{remark}");
                    }
                }
                WHERE("process_id = #{process_id} and enterprise_id=#{companyId} and upload_date=#{upload_date}");
            }
        }.toString();
    }

    public String selectByProcee(final EnergyProcessVO entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_process_target");
                if (entity != null) {
                    if (entity.getUpload_date() != null) {
                        WHERE("upload_date=#{upload_date}");
                    }
                    if (entity.getCompanyId() != null) {
                        WHERE("enterprise_id=#{companyId}");
                    }
                    if(entity.getProcess_id()!=null){
                        WHERE("process_id=#{process_id}");
                    }
                }
            }
        }.toString();
    }

    public String selectListByProcess(final EnergyProcessVO entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_process_target");
                if (entity != null) {
                    if (entity.getUpload_date() != null) {
                        WHERE("upload_date=#{upload_date}");
                    }
                    if (entity.getCompanyId() != null) {
                        WHERE("enterprise_id=#{companyId}");
                    }
                    if(entity.getProcess_id()!=null){
                        WHERE("process_id=#{process_id}");
                    }
                    if(entity.getProcess_ids() != null) {
                        StringBuilder sb = new StringBuilder("process_id in (");
                        for (Long str : entity.getProcess_ids()) {
                            sb.append("'").append(str).append("'").append(",");
                        }
                        sb.deleteCharAt(sb.length()-1).append(")");
                        WHERE(sb.toString());
                    }
                }
            }
        }.toString();
    }
}
