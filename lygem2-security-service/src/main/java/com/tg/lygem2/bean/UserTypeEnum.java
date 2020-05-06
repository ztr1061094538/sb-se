package com.tg.lygem2.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserTypeEnum {

	admin(0, "系统管理员"), gov(1, "政府账号"), company(2, "企业账号");
	private int type;
	private String name;

	public static UserTypeEnum parse(int code) {
		for (UserTypeEnum entity : UserTypeEnum.values()) {
			if (code == entity.getType()) {
				return entity;
			}
		}
		return null;
	}
}
