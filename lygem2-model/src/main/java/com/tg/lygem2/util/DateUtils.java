package com.tg.lygem2.util;

import java.util.Calendar;

public class DateUtils 
{
	private static final String DATE_SPLIT = "-";
	private static final String TIME_SPLIT = ":";
	private static final String BLANK_SPLIT = " ";
	
	private static String zeroFill(int num, int length)
	{
		int index = 1;
		int before = num;
		while (num >= 10)
		{
			num = num/10;
			index++;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = index; i < length; i ++)
		{
			sb.append("0");
		}
		sb.append(before);
		return sb.toString();
	}
	
	public static Calendar parseMDHMSLongToCalender(Long mdhmsTime)
	{
		String mdhmsTimeString = String.valueOf(mdhmsTime);
		int index = mdhmsTimeString.length() - 10;
		String year = mdhmsTimeString.substring(0,index);
		String month = mdhmsTimeString.substring(index, index+2);
		index +=2;
		String date = mdhmsTimeString.substring(index, index+2);
		index +=2;
		String hour = mdhmsTimeString.substring(index, index+2);
		index +=2;
		String minute = mdhmsTimeString.substring(index, index+2);
		index +=2;
		String sec = mdhmsTimeString.substring(index, index+2);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), 
				Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(sec));
		return calendar;
	}
	
	/**
	 * 返回yyyyMMddHHmmss类型
	 * @param calendar
	 * @return
	 */
	public static String getYMDHMSLongFromCalendar(Calendar calendar)
	{
		int year = calendar.get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder(year);
		int month = calendar.get(Calendar.MONTH) + 1;
		sb.append(zeroFill(month, 2));
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		sb.append(zeroFill(date, 2));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		sb.append(zeroFill(hour, 2));
		int minute = calendar.get(Calendar.MINUTE);
		sb.append(zeroFill(minute, 2));
		int sec = calendar.get(Calendar.SECOND);
		sb.append(zeroFill(sec, 2));
		return sb.toString();
	}
	
	/**
	 * 返回yyyy-MM-dd HH:mm:ss类型
	 * @param calendar
	 * @return
	 */
	public static String getYMDHMSDateFromCalendar(Calendar calendar)
	{
		int year = calendar.get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder(year).append(DATE_SPLIT);
		int month = calendar.get(Calendar.MONTH) + 1;
		sb.append(zeroFill(month, 2)).append(DATE_SPLIT);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		sb.append(zeroFill(date, 2)).append(BLANK_SPLIT);
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		sb.append(zeroFill(hour, 2)).append(TIME_SPLIT);
		int minute = calendar.get(Calendar.MINUTE);
		sb.append(zeroFill(minute, 2)).append(TIME_SPLIT);
		int sec = calendar.get(Calendar.SECOND);
		sb.append(zeroFill(sec, 2));
		return sb.toString();
	}
	
	/**
	 * 返回yyyyMMddHH类型
	 * @param calendar
	 * @return
	 */
	public static String getYMDHLongFromCalendar(Calendar calendar)
	{
		int year = calendar.get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder(year);
		int month = calendar.get(Calendar.MONTH) + 1;
		sb.append(zeroFill(month, 2));
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		sb.append(zeroFill(date, 2));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		sb.append(zeroFill(hour, 2));
		return sb.toString();
	}
	
	/**
	 * 返回yyyyMMdd类型
	 * @param calendar
	 * @return
	 */
	public static String getYMDLongFromCalendar(Calendar calendar)
	{
		int year = calendar.get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder(year);
		int month = calendar.get(Calendar.MONTH) + 1;
		sb.append(zeroFill(month, 2));
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		sb.append(zeroFill(date, 2));
		return sb.toString();
	}
	
	/**
	 * 返回yyyy-MM-dd类型
	 * @param calendar
	 * @return
	 */
	public static String getYMDDateFromCalendar(Calendar calendar)
	{
		int year = calendar.get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder(year).append(DATE_SPLIT);
		int month = calendar.get(Calendar.MONTH) + 1;
		sb.append(zeroFill(month, 2)).append(DATE_SPLIT);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		sb.append(zeroFill(date, 2));
		return sb.toString();
	}
}
