package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TerminalTreeNodeVo {
	private Integer id;
	private Integer parentId;
	private String name;
	private String terminalId;
	private Integer enterpriseId;
	private String dataCode;
	private BigDecimal rate; 
	private String seeType;
	private String seeTypeName;
	private String dataCodeName;
	private Integer metering_id;
	private String metering_name;
	List<TerminalTreeNodeVo> children;
}
