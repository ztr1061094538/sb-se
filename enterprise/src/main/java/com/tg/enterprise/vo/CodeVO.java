package com.tg.enterprise.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeVO {

	private String code;
	private String name;
	private String parent_code;
	private Integer type;

	public CodeVO(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
