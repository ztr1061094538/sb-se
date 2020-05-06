package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TerminalReqVo {
	private Integer id;
	private Integer parentId;
	private String name;
	private String terminalId;
	private Integer enterpriseId;
	private String dataCode;
	private BigDecimal rate; 
	private Integer seeType;
	private Integer metering_id;
	private List<Integer> ids;
	private Set<String> terminalIds;
}
