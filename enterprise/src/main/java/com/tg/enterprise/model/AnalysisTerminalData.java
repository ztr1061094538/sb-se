package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AnalysisTerminalData 
{
	private String terminal_id;
	
	private Long upload_date;
	
	private BigDecimal data_value;
}
