package com.tg.lygem2.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserTypeEnum {
	admin(0, "系统管理员"), group(1, "集团账号"), company(2, "企业账号"),company_read(3,"企业普通账号");

	private int type;
	private String name;

	public static UserTypeEnum parse(int type) {
		for (UserTypeEnum entity : UserTypeEnum.values()) {
			if (entity.getType() == type) {
				return entity;
			}
		}
		return null;
	}
}
