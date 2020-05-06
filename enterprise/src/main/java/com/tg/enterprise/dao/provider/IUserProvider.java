package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.User;
import org.apache.ibatis.jdbc.SQL;


public class IUserProvider {

	public String queryUser(final User user)
	{
		return new SQL() {
			{
				SELECT("*");
				FROM("t_user_info");
				if (user != null)
				{
					if(user.getId() != null)
					{
						WHERE("id = #{user.id}");
					}
					if(user.getLoginName() != null)
					{
						WHERE("loginName = #{user.loginName}");
					}
					if (user.getLoginPass() != null)
					{
						WHERE("loginPass = #{user.loginPass}");
					}
				}
			}
		}.toString();
	}

	public String selectForPage(final User user)
	{
		return new SQL() {
			{
				SELECT("id,loginName, description, create_time");
				FROM("t_user_info");
				if (user != null)
				{
					if(user.getLoginName() != null)
					{
						WHERE("loginName LIKE CONCAT('%',#{loginName},'%')");
					}
				}
			}
		}.toString();
	}
}
