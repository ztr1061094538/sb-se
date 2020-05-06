package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyProcessTargetProvider;
import com.tg.enterprise.model.EnergyEquipment;
import com.tg.enterprise.model.EnergyProcessTarget;
import com.tg.enterprise.vo.EnergyProcessVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EnergyProcessTargetMapper {

    @Select("select * from energy_process_target where id = #{id}")
    EnergyProcessTarget selectById(@Param("id") Long id);

    @Insert("insert into energy_process_target (process_id,data_value,remark,enterprise_id,upload_date)"+
            "values (#{entity.process_id},#{entity.data_value},#{entity.remark},#{entity.enterprise_id},#{entity.upload_date})")
    int add(@Param("entity") EnergyProcessTarget entity);

    @SelectProvider(type = EnergyProcessTargetProvider.class, method = "queryPageList")
    List<EnergyProcessTarget> queryPageList(EnergyProcessTarget entity);

    @SelectProvider(type = EnergyProcessTargetProvider.class, method = "queryList")
    List<EnergyProcessTarget>  queryList(EnergyProcessTarget enity);

    @SelectProvider(type = EnergyProcessTargetProvider.class, method = "queryEquitList")
    List<EnergyEquipment> queryEquitList(EnergyEquipment entity);

    @UpdateProvider(type=EnergyProcessTargetProvider.class,method="update")
    int update(EnergyProcessTarget entity);

    @UpdateProvider(type=EnergyProcessTargetProvider.class,method="updateProcee")
    int updateProcee(EnergyProcessVO entity);

    @SelectProvider(type=EnergyProcessTargetProvider.class,method="selectByProcee")
    EnergyProcessTarget selectByProcee(EnergyProcessVO entity);

    @Delete("<script>DELETE  from energy_process_target  where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
    int delByIds(@Param("ids") List<Long> ids);

    @SelectProvider(type = EnergyProcessTargetProvider.class, method = "selectListByProcess")
    List<EnergyProcessTarget> selectListByProcess(EnergyProcessVO energyProcessVO);
}
