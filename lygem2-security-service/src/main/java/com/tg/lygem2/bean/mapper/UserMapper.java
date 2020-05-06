package com.tg.lygem2.bean.mapper;

import com.tg.lygem2.bean.User;
import com.tg.lygem2.bean.mapper.provider.UserMapperProvider;
import com.tg.lygem2.vo.LoginUser;
import com.tg.lygem2.vo.Role;
import com.tg.lygem2.vo.UserCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
public interface UserMapper {

    @Select("select * from user where enabled = 1 AND  username = #{name}")
    public User findByUsername(String name);
    
    @Select("select * from user where username = #{name}")
    public User selectByUsername(String name);
    
    @Select("select * from user where enabled = 1 AND  phone = #{phone}")
    public User selectByUserPhone(String phone);

    @Select("select * from user where enabled = 1 AND  ENTERPRISE_ID = #{cid}")
    public User selectByCommpany(String cid);

    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\trole\n" +
            "WHERE\n" +
            "\tid IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tROLEID\n" +
            "\t\tFROM\n" +
            "\t\t\tuser_of_role\n" +
            "\t\tWHERE\n" +
            "\t\t\tUSERID = #{uid}\n" +
            "\t)")
    public List<Role> findRole(int uid);


    @Insert("INSERT INTO user( NAME , PHONE ,TELEPHONE ,ADDRESS ,ENABLED , USERNAME , PASSWORD , EMAIL , SEX , PROVINCE , CITY ," +
            " DISTRICT , ENTERPRISE_ID,user_type,area_id ) VALUES ( #{user.name} , #{user.phone} , #{user.telephone} , #{user.address} ," +
            " #{user.enabled} , #{user.username} , #{user.password}, #{user.email} , #{user.sex}, #{user.province}, #{user.city}," +
            " #{user.district} ,  #{user.enterprise_id},  #{user.user_type},  #{user.area_id})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="user.id",before=false,resultType=Integer.class)
    public int saveUser(@Param("user") LoginUser user);

    @Update("UPDATE user SET NAME=#{user.name},AREA_ID=#{user.area_id}, PHONE=#{user.phone}, TELEPHONE=#{user.telephone}, " +
            "ADDRESS=#{user.address}, ENABLED=#{user.enabled}, USERNAME=#{user.username},EMAIL=#{user.email}, " +
            "SEX=#{user.sex}, PROVINCE=#{user.province}, CITY=#{user.city}, DISTRICT=#{user.district}, " +
            "ENTERPRISE_ID=#{user.enterprise_id},user_type=#{user.user_type} WHERE (ID=#{user.id})")
    public int updateUser(@Param("user") LoginUser user);

    @SelectProvider(method = "getUserList",type = UserMapperProvider.class)
    public List<LoginUser> findAll(UserCondition userCondition);

    @Update("UPDATE user SET password = #{user.password} WHERE (ID=#{user.id})")
    public int updatePassword(@Param("user") LoginUser user);
    
	@Delete("<script>delete from user where id in " +
			"<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	public int delByIds(@Param("list") List<Integer> list);

    @Select("select * from user where id = #{userId}")
    User findUserById(Integer userId);

}