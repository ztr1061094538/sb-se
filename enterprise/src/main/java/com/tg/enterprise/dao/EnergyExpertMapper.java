package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyExpertMapperProvider;
import com.tg.enterprise.model.EnergyExpert;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */
public interface EnergyExpertMapper {

    @Select("select id,enterprise_id,expert_name as expertName,education,academic_title as academicTitle," +
            "gender,head_image as headImage,expert_introduce as expertIntroduce," +
            "expert_institution as expertInstitution,expert_tel as expertTel, " +
            "id_number as idNumber,birthday as birthday,domain as domain,remark from t_energy_expert where id = #{id}")
    EnergyExpert selectById(@Param("id") int id);

    @Insert("insert into t_energy_expert (enterprise_id,expert_name,education,academic_title,gender,head_image,expert_introduce,expert_institution,expert_tel,id_number,birthday,domain,remark)"+
            "values (#{entity.enterprise_id},#{entity.expertName},#{entity.education},#{entity.academicTitle},#{entity.gender},#{entity.headImage},#{entity.expertIntroduce},#{entity.expertInstitution},#{entity.expertTel},#{entity.idNumber},#{entity.birthday},#{entity.domain},#{entity.remark})")
    int add(@Param("entity") EnergyExpert entity);

    @SelectProvider(type = EnergyExpertMapperProvider.class, method = "queryPageList")
    List<EnergyExpert> queryPageList(EnergyExpert entity);

    @UpdateProvider(type=EnergyExpertMapperProvider.class,method="update")
    int update(EnergyExpert entity);

    @Update("<script>update t_energy_expert set is_del = 1 where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
    int delByIds(@Param("ids") List<Integer> ids);
}
