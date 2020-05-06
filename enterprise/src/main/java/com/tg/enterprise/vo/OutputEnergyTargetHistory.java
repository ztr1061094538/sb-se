package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OutputEnergyTargetHistory 
{
	private String product_name;
	private String unit;
	private BigDecimal yield_value;
	private BigDecimal output_value;
	private BigDecimal unit_yield;
	private String remark;
}
