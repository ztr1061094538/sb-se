package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EnergyTargetHistoryRecords 
{
	private List<EnergyTargetHistory> rows;
	
	private BigDecimal sum;
}
