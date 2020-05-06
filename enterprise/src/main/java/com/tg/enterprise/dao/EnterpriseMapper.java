package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnterpriseMapperProvider;
import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.vo.EnterpriseQueryVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */
public interface EnterpriseMapper {
    @Select("select * from enterprise where id = #{id}")
    Enterprise selectById(@Param("id") int id);

    @Insert("insert into enterprise(name,type,type_code,code,industry_code," +
            "latitude,longitude,province,city,district,energy_scale,is_sx,phone,fax,email,address,zip_code," +
            "energy_resp_name,energy_resp_phone,is_energySupply,is_power," +
            "major_product,enterprise_introduce,enterprise_data,firstPinyinName,manage_code,owned_group_name,energy_inspect_name,energy_inspect_phone,area)"+
            "values (#{entity.name},#{entity.type},#{entity.type_code},#{entity.code}," +
            "#{entity.industry_code},#{entity.latitude},#{entity.longitude},#{entity.province},#{entity.city},#{entity.district}," +
            "#{entity.energy_scale},#{entity.is_sx},#{entity.phone}," +
            "#{entity.fax},#{entity.email},#{entity.address},#{entity.zip_code}," +
            "#{entity.energy_resp_name},#{entity.energy_resp_phone},#{entity.is_energySupply},#{entity.is_power}," +
    		"#{entity.major_product},#{entity.enterprise_introduce},#{entity.enterprise_data},#{entity.firstPinyinName},#{entity.manage_code},#{entity.owned_group_name}," +
            "#{entity.energy_inspect_name},#{entity.energy_inspect_phone},#{entity.area})")
    int add(@Param("entity") Enterprise entity);

    @SelectProvider(type = EnterpriseMapperProvider.class, method = "queryPageList")
    List<Enterprise> queryPageList(EnterpriseQueryVO entity);

    @UpdateProvider(type= EnterpriseMapperProvider.class,method="update")
    int update(Enterprise entity);
    
    @Update("<script>update enterprise set is_del = 1 where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
    int delByIds(@Param("ids") List<Integer> ids);

    @Select("select id from enterprise where type = #{type} and is_del = 0")
    List<Integer> getIdListByType(@Param("type") int type);
}
