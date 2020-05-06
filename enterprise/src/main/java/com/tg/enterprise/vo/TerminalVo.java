package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TerminalVo {
	private Integer id;
	private Integer parentId;
	private String name;
	private String terminalId;
	private Integer enterpriseId;
	private String dataCode;
	private BigDecimal rate; 
	private Integer metering_id;
	private String metering_name;
	private String seeType;
	private String seeTypeName;
	private String dataCodeName;
	private String metering_type_name;//计量器具类型名称
	private String next_calibration;//下一次检定校准时间
	//监测点数值
	private BigDecimal value;
}
