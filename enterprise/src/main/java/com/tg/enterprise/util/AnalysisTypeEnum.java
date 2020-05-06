package com.tg.enterprise.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public enum AnalysisTypeEnum 
{
	HOUR("hour",0),DATE("date",1),MONTH("month",2),YEAR("year",3);
	
	private String tableSuffix;
	
	private int type;
	
	public static AnalysisTypeEnum parse(int type)
	{
		if (HOUR.getType() == type)
		{
			return HOUR;
		}
		if (DATE.getType() == type)
		{
			return DATE;
		}
		if (MONTH.getType() == type)
		{
			return MONTH;
		}
		if (YEAR.getType() == type)
		{
			return YEAR;
		}
		throw new IllegalArgumentException("type not found");
	}
	
	public static int getIndex(AnalysisTypeEnum type, long time)
	{
		if (type == AnalysisTypeEnum.MONTH)
		{
			return (int) (time%100 - 1);
		}
		if (type == AnalysisTypeEnum.DATE)
		{
			return (int) (time%100 - 1);
		}
		return -1;
	}
}
