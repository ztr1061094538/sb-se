package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnengyTypeProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import com.tg.enterprise.model.EnergyType;
import java.util.List;

/**
 * 能源类型 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */

public interface EnergyTypeMapper{

    /**
     * 根据主键ID查询
     */
    @Select("select * from energy_type where code = #{code}")
    EnergyType selectByCode(@Param("code") java.lang.String code);

    /**
     * 全查列表
     */
    @SelectProvider(type = EnengyTypeProvider.class, method = "selectList")
    List<EnergyType> selectList(EnergyType energyType);

    /**
     * 插入		
     */
    @Insert("insert into energy_type" +
            "(name,pcode,unit,classCode,nhzbdw,type,zbckz,zbxs,dwzbxs) values " +
            "(#{energyType.name}," +
            "#{energyType.pcode}," +
            "#{energyType.unit}," +
            "#{energyType.classcode}," +
            "#{energyType.nhzbdw}," +
            "#{energyType.type}," +
            "#{energyType.zbckz}," +
            "#{energyType.zbxs}," +
            "#{energyType.dwzbxs})")
    int insert(@Param("energyType") EnergyType energyType);

    /**
     * 批量插入
     */
    @Insert("<script>insert into energy_type" +
            "( name, pcode, unit, classCode, nhzbdw, type, zbckz, zbxs, dwzbxs) values " +
            "<foreach collection='list' item='item' open='(' separator='),(' close=')'>" +
            "#{item.name}," +
            "#{item.pcode}," +
            "#{item.unit}," +
            "#{item.classcode}," +
            "#{item.nhzbdw}," +
            "#{item.type}," +
            "#{item.zbckz}," +
            "#{item.zbxs}," +
            "#{item.dwzbxs}</foreach>" +
            "</script>")
    int insertBatch(@Param("list") List<EnergyType> energyTypeList);

    /**
     * 更新		
     */
    @UpdateProvider(type = EnengyTypeProvider.class,method= "update")
    int update(@Param("energyType") EnergyType energyType);

    /**
     * 条件删除	
     */
    @DeleteProvider(type = EnengyTypeProvider.class, method = "delete")
    int delete(@Param("energyType") EnergyType energyType);

    /**
     * 根据主键ID删除		
     */
    @Delete("delete from energy_type where code = #{code}")
    int deleteById(@Param("code") java.lang.String code);

    /**
     * 批量删除		
     */
    @Delete("<script>delete from energy_type where code in " +
            "<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
            "#{code}" +
            "</foreach>" +
            "</script>")
    int delByIds(@Param("list") List<java.lang.String> list);

    @Select("select * from energy_type where type = 2")
    List<EnergyType> getEnergyName();
}