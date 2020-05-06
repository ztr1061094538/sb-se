package com.tg.lygem2.bean.mapper;

import com.tg.lygem2.vo.RoleOfMenu;
import com.tg.lygem2.vo.UserOfRole;
import com.tg.lygem2.vo.crud.RoleOfAllMenuIds;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
public interface BridgeMapper {

    @Insert("INSERT INTO role_of_menu (MENUID, ROLEID) VALUES (#{menuId}, #{roleId})")
    public int saveRoleOfMenu(@Param("menuId") Long menuId, @Param("roleId") Integer roleId);

    @Delete("delete from role_of_menu where roleid = #{roleid}")
    public int deleteForRoleId(Integer roleId);

    @Insert("INSERT INTO user_of_role (USERID, ROLEID)VALUES (#{userOfRole.userId}, #{userOfRole.roleId})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "userOfRole.id", before = false, resultType = Integer.class)
    public int saveUserOfRole(@Param("userOfRole") UserOfRole userOfRole);

    @Update("UPDATE user_of_role SET USERID=#{userOfRole.userId},ROLEID=#{userOfRole.roleId} WHERE ID=#{userOfRole.id}")
    public int updateUserOfRole(@Param("userOfRole") UserOfRole userOfRole);

    @Update("delete from user_of_role where userid = #{userid}")
    public int deleteForUserId(Integer userid);

    @Update("UPDATE role_of_menu SET MENUID=#{roleOfMenu.menuId},ROLEID=#{roleOfMenu.roleId} WHERE ID=#{roleOfMenu.id}")
    public int updateRoleOFMenu(@Param("roleOfMenu") RoleOfAllMenuIds role);

    @Select("select * from role_of_menu where ROLEID = #{roleid}")
    public List<RoleOfMenu> findRoleIDList(Integer roleid);

    @Select("select * from user_of_role where userid = #{userid}")
    public List<UserOfRole> findRoleByUserId(Integer userid);
    
	@Delete("<script>delete from user_of_role where userid in " +
			"<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	public int delByUserIds(@Param("list") List<Integer> list);


    @Select("<script>select * from user_of_role where roleid in " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<UserOfRole> findUserByRoleIds(@Param("list") List<Integer> list);

    @Delete("<script>delete from user_of_role where roleid in " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void deleteUserOfRole(@Param("list") List<Integer> list);

    @Delete("<script>delete from role_of_menu where roleid in " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void deleteForRoleIds(@Param("list") List<Integer> list);
}