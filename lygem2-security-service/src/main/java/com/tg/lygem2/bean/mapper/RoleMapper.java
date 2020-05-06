package com.tg.lygem2.bean.mapper;

import com.tg.lygem2.bean.mapper.provider.RoleMapperProvider;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.RoleCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
public interface RoleMapper {


    @Insert("INSERT INTO role (NAME, NAMEZH) VALUES (#{role.name}, #{role.nameZh})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "role.id", before = false, resultType = Integer.class)
    public int saveRole(@Param("role") Role role);

    @Update("UPDATE role SET NAME = #{role.name}, NAMEZH = #{role.nameZh} WHERE ID = #{role.id}")
    public void updateRole(@Param("role") Role role);

    @SelectProvider(method = "getRoleList",type = RoleMapperProvider.class)
    public List<Role> findAll(RoleCondition role);

    @Select("select * from role where name = #{name}")
    public List<Role> findAllRoleByName(String name);

    @Select("select * from role where id = #{id}")
    public Role findAllRoleById(Integer id);

    @Delete("<script>delete from role where id in " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void deleteRole(@Param("list") List<Integer> list);
}
