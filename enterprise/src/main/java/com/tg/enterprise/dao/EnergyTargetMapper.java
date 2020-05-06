package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyTargetProvider;
import com.tg.enterprise.model.EnergyTarget;
import com.tg.enterprise.vo.TargetQueryVO;
import com.tg.enterprise.vo.TargetRespVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EnergyTargetMapper {

    @Select("select * from energy_target where energy_type=#{energy_type} and upload_date = #{upload_date} and enterprise_id=#{enterprise_id}")
    EnergyTarget selectById(@Param("energy_type") String energy_type, @Param("upload_date") Long upload_date, @Param("enterprise_id") Integer enterprise_id);

    @Insert("insert into energy_target (energy_type,purchase,supply,opening_inventory,ending_inventory,energy_consumption,remark,upload_date,enterprise_id)"+
            "values (#{entity.energy_type},#{entity.purchase},#{entity.supply},#{entity.opening_inventory},#{entity.ending_inventory},#{entity.energy_consumption},#{entity.remark},#{entity.upload_date},#{entity.enterprise_id})")
    int add(@Param("entity") EnergyTarget entity);

    @SelectProvider(type = EnergyTargetProvider.class, method = "queryPageList")
    List<EnergyTarget> queryPageList(EnergyTarget entity);

    @UpdateProvider(type=EnergyTargetProvider.class,method="update")
    int update(EnergyTarget entity);

    @Delete("<script>DELETE  from energy_target  where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
    int delByIds(@Param("ids") List<Long> ids);

    @SelectProvider(type = EnergyTargetProvider.class, method = "selectSumConsumption")
    List<TargetRespVO> selectSumConsumption(TargetQueryVO targetQueryVO);
}
