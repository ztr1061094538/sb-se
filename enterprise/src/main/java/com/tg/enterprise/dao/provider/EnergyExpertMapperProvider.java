package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergyExpert;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by Administrator on 2018/5/14.
 */
public class EnergyExpertMapperProvider {
    public String queryPageList(final EnergyExpert entity) {
        return new SQL() {
            {
                SELECT("id,expert_name as expertName,education,academic_title as academicTitle,gender,head_image as headImage,expert_introduce as expertIntroduce,expert_institution as expertInstitution,expert_tel as expertTel,id_number as idNumber,birthday as birthday,domain as domain");
                FROM("t_energy_expert");
                if (entity != null) {
                    if (entity.getId() != null) {
                        WHERE("id=#{id}");
                    }
                    if (entity.getEnterprise_id() != null) {
                        WHERE("enterprise_id=#{enterprise_id}");
                    }
                    if (entity.getExpertName() != null) {
                        WHERE("expert_name  LIKE CONCAT('%',#{expertName},'%')");
                    }
                    if (entity.getEducation() != null) {
                        WHERE("education=#{education}");
                    }
                    if (entity.getAcademicTitle() != null) {
                        WHERE("academic_title=#{academicTitle}");
                    }
                    if (entity.getGender() != null) {
                        WHERE("gender=#{gender}");
                    }
                    if (entity.getHeadImage() != null) {
                        WHERE("head_image =#{headImage}");
                    }
                    if (entity.getExpertInstitution() != null) {
                        WHERE("expert_institution =#{expertInstitution}");
                    }
                    if (entity.getExpertIntroduce() != null) {
                        WHERE("expert_introduce =#{expertIntroduce}");
                    }
                    if (entity.getExpertTel() != null) {
                        WHERE("expert_tel =#{expertTel}");
                    }
                    if (entity.getIdNumber() != null) {
                        WHERE("id_number =#{idNumber}");
                    }
                    if (entity.getBirthday() != null) {
                        WHERE("birthday =#{birthday}");
                    }
                    if (entity.getDomain() != null) {
                        WHERE("domain =#{domain}");
                    }
                    if (entity.getRemark() != null) {
                        WHERE("remark =#{remark}");
                    }
                }
                WHERE("is_del = 0");
            }
        }.toString();
    }

    public String update(final EnergyExpert entity) {
        return new SQL() {
            {
                UPDATE("t_energy_expert");
                if (entity != null) {
                    if (entity.getId() != null) {
                        SET("id=#{id}");
                    }
                    if (entity.getExpertName() != null) {
                        SET("expert_name=#{expertName}");
                    }
                    if (entity.getEducation() != null) {
                        SET("education=#{education}");
                    }
                    if (entity.getAcademicTitle() != null) {
                        SET("academic_title=#{academicTitle}");
                    }
                    if (entity.getGender() != null) {
                        SET("gender=#{gender}");
                    }
                    if (entity.getHeadImage() != null) {
                        SET("head_image =#{headImage}");
                    }
                    if (entity.getExpertInstitution() != null) {
                        SET("expert_institution =#{expertInstitution}");
                    }
                    if (entity.getExpertIntroduce() != null) {
                        SET("expert_introduce =#{expertIntroduce}");
                    }
                    if (entity.getExpertTel() != null) {
                        SET("expert_tel =#{expertTel}");
                    }
                    if (entity.getIdNumber() != null) {
                        SET("id_number =#{idNumber}");
                    }
                    if (entity.getBirthday() != null) {
                        SET("birthday =#{birthday}");
                    }
                    if (entity.getDomain() != null) {
                        SET("domain =#{domain}");
                    }
                    if(entity.getRemark() != null) {
                        SET("remark = #{remark}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
