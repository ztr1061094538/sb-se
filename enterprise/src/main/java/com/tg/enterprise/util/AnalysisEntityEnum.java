package com.tg.enterprise.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public enum AnalysisEntityEnum 
{
	TERMINAL("terminal"),COMPANY("company"),INDUSTRY("industry"),DISTRICT("district"),CITY("city");
	
	private String table_sufix;
	
}
