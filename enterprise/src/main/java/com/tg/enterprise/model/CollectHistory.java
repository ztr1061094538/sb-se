package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CollectHistory 
{
	private int enterpiseId;
	
	private String energy_target;
	
	private String efficiency_data_target;
	
	private String output_energy_target;
	
	private String energy_process_target;
	
	private BigDecimal total;
	
	private long uploadTime;
}
