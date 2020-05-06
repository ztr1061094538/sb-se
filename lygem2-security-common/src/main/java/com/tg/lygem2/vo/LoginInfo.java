package com.tg.lygem2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {

	private String login_name;// 登录用户名
	private List<String> roles = new ArrayList<>();// 用户角色
	private BasicUser user_detail;// 用户详情
}
