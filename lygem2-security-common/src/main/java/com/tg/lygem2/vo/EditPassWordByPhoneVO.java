package com.tg.lygem2.vo;

import lombok.Data;

@Data
public class EditPassWordByPhoneVO {
	public String phone;// 手机号
	public String sms_code;// 短信验证码
	public String new_password;// 新密码
	public String new_password_again;//// 再次输入新密码
}
