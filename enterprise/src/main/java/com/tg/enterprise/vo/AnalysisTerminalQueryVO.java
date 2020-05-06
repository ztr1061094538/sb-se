package com.tg.enterprise.vo;

import com.tg.enterprise.util.EnergyDataCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnalysisTerminalQueryVO 
{
	private List<String> terminalIds;
	
	private Long startTime;
	
	private Long endTime;
	
	private Long uploadTime;
	
	private EnergyDataCode dataCode;
}
