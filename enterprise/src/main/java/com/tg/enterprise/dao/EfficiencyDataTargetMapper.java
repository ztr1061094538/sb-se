package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EfficiencyDataTargetProvider;
import com.tg.enterprise.model.EfficiencyDataTarget;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EfficiencyDataTargetMapper {

    @Select("select * from efficiency_data_target where id = #{id}")
    EfficiencyDataTarget selectById(@Param("id") Long id);

    @Insert("insert into efficiency_data_target (efficiency_data,unit,data_value,remark,upload_date,enterprise_id)"+
            "values (#{entity.efficiency_data},#{entity.unit},#{entity.data_value},#{entity.remark},#{entity.upload_date},#{entity.enterprise_id})")
    int add(@Param("entity") EfficiencyDataTarget entity);

    @SelectProvider(type = EfficiencyDataTargetProvider.class, method = "queryPageList")
    List<EfficiencyDataTarget> queryPageList(EfficiencyDataTarget entity);

    @UpdateProvider(type=EfficiencyDataTargetProvider.class,method="update")
    int update(EfficiencyDataTarget entity);

    @Delete("<script>DELETE  from efficiency_data_target  where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
    int delByIds(@Param("ids") List<Long> ids);
}
