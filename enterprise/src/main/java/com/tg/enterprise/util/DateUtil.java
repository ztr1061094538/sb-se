package com.tg.enterprise.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"; //时分秒格式
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat sdfYM = new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
	public static final String MONITOR_DATE_FORMAT = "yyyyMMdd";
	/**
	 * 获取当月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Long getFisrtDateOfMonth(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return Long.parseLong(dateSdf.format(calendar.getTime()));
	}

	/**
	 * 获取过去31天
	 * @param date
	 * @return
	 */
	public static Long getOldThreeOne(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-30);
		Date date1 = calendar.getTime();
		return Long.parseLong(dateSdf.format(date1));
	}
	/**
	 * 获取过去10天
	 * @param date
	 * @return
	 */
	public static Long getAfter(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-10);
		Date date1 = calendar.getTime();
		return Long.parseLong(sdf.format(date1));
	}
	/**
	 * 获取以后10天
	 * @param date
	 * @return
	 */
	public static Long getNext(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,10);
		Date date1 = calendar.getTime();
		return Long.parseLong(sdf.format(date1));
	}
	/**
	 * 获取过去31天 Date格式
	 * @param date
	 * @return
	 */
	public static Date getOldThreeOneForDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-30);
		Date date1 = calendar.getTime();
		return date1;
	}
	/**
	 * 获取当月最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Long getLastDateOfMonth(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return Long.parseLong(dateSdf.format(calendar.getTime()));
	}
	/**
	 * Date转,年，月,日,long类型时间
	 *
	 * @param date
	 * @return
	 */
	public static Long getYMDLongTime(Date date) {
		if (null == date) {
			return null;
		}
		String time = dateSdf.format(date);
		return Long.valueOf(time);
	}

	/**
	 * Date转,年，月,long类型时间
	 *
	 * @param date
	 * @return
	 */
	public static Long getYMLongTime(Date date) {
		if (null == date) {
			return null;
		}
		String time = sdfYM.format(date);
		return Long.valueOf(time);
	}

	/**
	 * Date转long类型时间
	 * 
	 * @param date
	 * @return
	 */
	public static Long getLongTime(Date date) {
		if (null == date) {
			return null;
		}
		String time = sdf.format(date);
		return Long.valueOf(time);
	}

	/**
	 * long类型时间转Date
	 * 
	 * @param l
	 * @return
	 */
	public static Date getDate(Long l) {
		if (null == l  || l == 0) {
			return null;
		}
		Date date = new Date();
		try {
			date = sdf.parse(String.valueOf(l));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * long类型时间转Date
	 *
	 * @param l
	 * @return
	 */
	public static Date getYMDDate(Long l) {
		if (null == l  || l == 0) {
			return null;
		}
		Date date = new Date();
		try {
			date = dateSdf.parse(String.valueOf(l));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * long类型时间转Date
	 *
	 * @param l
	 * @return
	 */
	public static Date getYMDate(Long l) {
		if (null == l  || l == 0) {
			return null;
		}
		Date date = new Date();
		try {
			date = sdfYM.parse(String.valueOf(l));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * long类型时间转Date
	 *
	 * @param l
	 * @return
	 */
	public static Date getYDate(Long l) {
		if (null == l  || l == 0) {
			return null;
		}
		Date date = new Date();
		try {
			date = sdfY.parse(String.valueOf(l));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * java.util.Date添加天数
	 * @param d
	 * @param days
	 * @return
	 */
	public static Date addDays(Date d, int days) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(d.getTime());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		c.set(year, month, date + days);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 根据字符串添加一天返回字符串
	 * @param time
	 * @param days
	 * @return
	 */
	public static String addDays(String time,String format,int days){
		Date temp = str2Date(time, format);
		Date end = addDays(temp,days);
		return date2Str(end,format);
	}

	/**
	 * 日期字符串格式化成Date
	 * @param time
	 * @param format
	 * @return
	 */
	public static Date str2Date(String time, String format) {
		if (StringUtils.isBlank(time)){
			return null;
		}
		format = StringUtils.isNotBlank(format) ? format : DEFAULT_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(time);
			return date;
		}catch (ParseException e) {
			return null;
		}
	}

	/**
	 * java.util.date 转换成 自定义格式的字符串日期格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 传入日期获取该月的第一天和最后一天
	 * @param
	 * @return
	 */
	public static Long[] getFirstAndLastDayByMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		//Date date = java.sql.Date.valueOf(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = df.format(cal.getTime());

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = df.format(cal.getTime());

		Long [] days = new Long[]{Long.parseLong(firstDay), Long.parseLong(lastDay)};
		return days;
	}

	/**
	 * 传入日期获取上个月的第一和最后一天
	 * @param
	 * @return
	 */
	public static Long[] getLastMonthStartEndDay(Date date){
	//	Date date = java.sql.Date.valueOf(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//java.text.SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.MONTH, -1);
		Date currentDate = cal.getTime();
		//String lastMonthDay = sd.format(currentDate);
		return getFirstAndLastDayByMonth(currentDate);
	}

	/**
	 * 获取上月
	 * @param day
	 * @return
	 */
	public static String getLastMonth(String day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return new SimpleDateFormat("yyyyMM").format(cal.getTime());
	}

	/**
	 * 获取下月
	 * @param day
	 * @return
	 */
	public static String getNextMonth(String day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		return new SimpleDateFormat("yyyyMM").format(cal.getTime());
	}

	/**
	 * 获取指定时间前后月份
	 * @param day
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static String getMonth(String day,int num) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return new SimpleDateFormat("yyyyMM").format(cal.getTime());
	}

	public static String getLocalDateStr(Date date, DateTimeFormatter formatter) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

		return formatter.format(localDateTime);
	}

}
