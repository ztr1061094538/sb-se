package com.tg.enterprise.dao;

import java.math.BigInteger;
import java.util.List;

import com.tg.enterprise.dao.provider.IUserProvider;
import com.tg.enterprise.model.User;
import org.apache.ibatis.annotations.*;


public interface IUserMapper {
	
    @Select("select * from t_user_info")
    List<User> getAll();

	@SelectProvider(type= IUserProvider.class,method="queryUser")
    List<User> query(@Param("user") User user);
    
    @Delete("delete from t_user_info where id = #{id}")
    int delete(@Param("id") Long id);

    @Update("update t_user_info set loginPass = #{password} where id = #{id}")
    boolean resetPassword(@Param("id") Long id, @Param("password") String password);

    @SelectProvider(type= IUserProvider.class,method="selectForPage")
    List<User> selectForPage(User user);

    @Select("select * from t_user_info where loginName = #{loginName}")
    User selectByLoginName(@Param("loginName") String loginName);

    @Insert("insert into t_user_info(loginName,loginPass,cid,role,create_time,description) values(#{user.loginName},#{user.loginPass},#{user.cid},#{user.role},#{user.create_time},#{user.description})")
    int insert(@Param("user") User user);

    @Update("update t_user_info set loginName = #{user.loginName},loginPass = #{user.loginPass} where id = #{user.id}")
    int update(@Param("user") User user);
}
