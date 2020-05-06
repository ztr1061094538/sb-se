package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TerminalTreeEnergyNodeVo{
	private Integer id;
	private Integer parentId;
	private String name;
	private String terminalId;
	private Integer enterpriseId;
	private String dataCode;
	private BigDecimal rate; 
	private String seeType;
	private String seeTypeName;
	private String metering_name;
	private String dataCodeName;
	private String warning;
	private String childWarning;
	private String metering_type_name;//计量器具类型名称
	private String next_calibration;//下一次检定校准时间
	private BigDecimal value;
	private BigDecimal diffValue;
	List<TerminalTreeEnergyNodeVo> children;

	public BigDecimal getDiffValue() {
		BigDecimal sum = new BigDecimal(0);
		for (TerminalTreeEnergyNodeVo x:
		children) {
			sum = sum.add(x.getValue() == null ? new BigDecimal(0) : x.getValue());
		}
		BigDecimal date = this.getValue() == null ? new BigDecimal(0) : this.getValue();
		return date.subtract(sum);
	}

	public void setValue(BigDecimal value) {
		if(value == null){
			this.value = new BigDecimal(0);
		}
		this.value = value;
	}
}
