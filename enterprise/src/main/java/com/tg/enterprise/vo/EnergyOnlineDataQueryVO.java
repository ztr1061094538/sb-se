package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnergyOnlineDataQueryVO 
{
	private String terminalId;
	
	private Long startTime;
	
	private Long endTime;
	
	private Long uploadDate;
	
	private List<String> ids;
	
	private String energyCode;
	
	private String orderName;
	
	private String orderType;
}
