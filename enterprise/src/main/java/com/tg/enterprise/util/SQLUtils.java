package com.tg.enterprise.util;

import java.util.List;

public class SQLUtils 
{
	public static String getInSql(String coloumnName, List<String> values)
	{
		StringBuilder sb = new StringBuilder(coloumnName).append(" in (");
		for (int i = 0; i < values.size(); i ++)
		{
			sb.append("'").append(values.get(i)).append("'");
			if (i == values.size() - 1)
			{
				sb.append(")");
			}
			else
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
