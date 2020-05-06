package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Terminal {
	private Integer id;
	private Integer parentId;
	private String name;
	private String terminalId;
	private Integer enterpriseId;
	private String dataCode;
	private BigDecimal rate; 
	private String seeType;
	private Integer metering_id;
}
