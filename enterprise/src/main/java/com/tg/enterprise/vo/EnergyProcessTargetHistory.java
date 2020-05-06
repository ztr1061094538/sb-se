package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EnergyProcessTargetHistory 
{
	private String name;
	
	private String energyTypeName;
	
	private String unit;
	
	private BigDecimal data_value;
	
	private String remark;
}
