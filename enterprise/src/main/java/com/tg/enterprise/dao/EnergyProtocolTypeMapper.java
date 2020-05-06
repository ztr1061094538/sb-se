package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyProtocolTypeProvider;
import com.tg.enterprise.model.EnergyType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EnergyProtocolTypeMapper {

    @SelectProvider(type = EnergyProtocolTypeProvider.class, method = "queryList")
    List<EnergyType> queryList(EnergyType entity);
    
    @Select("select * from energy_type where code =#{code} AND type = 2")
    EnergyType selectByCode(@Param("code") String code);

    @Update("update energy_type set zbxs = #{entity.zbxs} where code = #{entity.code}")
    int update(@Param("entity") EnergyType entity);
}
