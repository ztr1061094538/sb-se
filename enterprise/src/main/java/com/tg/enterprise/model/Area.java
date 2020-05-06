package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Area {

	private String code;
	private String name;
	private String parent_code;
	private String coordinate;
	private String description;
	private Integer is_del;
	private Integer type;
}
