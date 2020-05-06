package com.tg.lygem2.bean.mapper;

import com.tg.lygem2.bean.mapper.provider.MenuMapperProvider;
import com.tg.lygem2.vo.Menu;
import com.tg.lygem2.vo.MenuCondition;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.crud.ExposeMenu;
import com.tg.lygem2.vo.crud.LoginMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
public interface MenuMapper {

    @Select("SELECT * FROM menu")
    @Results({
            @Result(property = "roles", column = "id", many = @Many(select = "com.tg.lygem2.bean.mapper.MenuMapper.findRole"))
    })
    public List<Menu> findAll();

    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\trole\n" +
            "WHERE\n" +
            "\tid IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tROLEID\n" +
            "\t\tFROM\n" +
            "\t\t\trole_of_menu\n" +
            "\t\tWHERE\n" +
            "\t\t\tMENUID = #{mid}\n" +
            "\t)")
    public List<Role> findRole(String mid);


    @Insert("INSERT INTO menu (URL, NAME, ICONCLS, PARENTID, ENABLED, TYPE, SORTID)VALUES (#{menu.url},#{menu.name},#{menu.iconCls},#{menu.parentId},#{menu.enabled},#{menu.type},#{menu.sortId})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="menu.id",before=false,resultType=Long.class)
    public int saveMenu(@Param("menu") ExposeMenu menu);

    @Update("UPDATE menu SET type=#{menu.type},URL=#{menu.url},SORTID=#{menu.sortId}, NAME=#{menu.name}, ICONCLS=#{menu.iconCls}, PARENTID=#{menu.parentId}, ENABLED=#{menu.enabled} WHERE (ID=#{menu.id})")
    public int updateMenu(@Param("menu") ExposeMenu menu);

    @Select("select * from menu where id = #{menuid} and enabled = 1")
    public LoginMenu selectMenu(Long menuid);

    @SelectProvider(type = MenuMapperProvider.class,method = "getMenuList")
    public List<LoginMenu> getAllMenu(MenuCondition menuCondition);

    @Select("<script>SELECT * FROM menu WHERE type = 0 AND id IN(SELECT menuid FROM role_of_menu WHERE roleId IN " +
            "(SELECT id FROM role WHERE role. NAME IN " +
            "<foreach item='name' index='index' collection='roleNames' open='(' separator=',' close=')'>" +
            "#{name}" +
            "</foreach>" +
            "))</script>")
    public List<LoginMenu> findLoginMenu(@Param("roleNames") List<String> roleNames);
}