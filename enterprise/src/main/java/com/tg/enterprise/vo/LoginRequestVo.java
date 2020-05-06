package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestVo {
	private String loginName;	//登录账号
	private String loginPass;	//登录密码
	private String imageCode; //验证码

}
