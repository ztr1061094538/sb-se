package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	private Long id;			//用户ID
	private String loginName;	//登录账号
	private String loginPass;	//登录密码
	private String realName;	//真实姓名
	public int sex;
	public String email;
	public Integer cid;
	private String role;//0 管理员 1 普通员工
	public Long create_time;
	private String description;//描述
	private String verifyLoginPass;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", loginName='" + loginName + '\'' +
				", loginPass='" + loginPass + '\'' +
				", realName='" + realName + '\'' +
				", sex=" + sex +
				", email='" + email + '\'' +
				'}';
	}
}
