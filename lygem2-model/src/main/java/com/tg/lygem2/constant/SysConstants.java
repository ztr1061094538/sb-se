package com.tg.lygem2.constant;

import java.math.BigDecimal;

public class SysConstants {

	/**
	 * 是否删除，已删除
	 */
	public static final int ISDEL_YES = 1;

	/**
	 * 是否删除，未删除
	 */
	public static final int ISDEL_NO = 0;

	/**
	 * 设备上报周期，10分钟
	 */
	public static final int UPLOAD_PERIOD = 10;

	/**
	 * 每小时上报点位数
	 */
	public static final int UPLOAD_POINTS_PER_HOUR = 60 / UPLOAD_PERIOD;

	/**
	 * 每天上报点位数
	 */
	public static final int UPLOAD_POINTS_PER_DAY = 24 * 60 / UPLOAD_PERIOD;

	/**
	 * 最大查询数
	 */
	public static final int BATCH_QUERY_NUM = 500;

	/**
	 * 最大插入数
	 */
	public static final int BATCH_INSERT_NUM = 500;

	/**
	 * 监测设备厂家，0：太谷；1：大贞
	 */
	public static final int DEVICE_MANUFACTURER_TAIGU = 0;

	/**
	 * 监测设备厂家，0：太谷；1：大贞
	 */
	public static final int DEVICE_MANUFACTURER_DAZHEN = 1;

	/**
	 * 告警状态未恢复
	 */
	public static final int WARN_STATE_NOT_RECOVERY = 0;

	/**
	 * 告警状态已恢复
	 */
	public static final int WARN_STATE_RECOVERY = 1;

	/**
	 * 缺省的清洗周期设置-天
	 */
	public static final int DEFAULT_CLEAN_PARAM_DAY = 90;

	/**
	 * 缺省的清洗周期设置-小时
	 */
	public static final int DEFAULT_CLEAN_PARAM_HOUR = 90;

	/**
	 * 缺省的排放超标设置-油烟
	 */
	public static final BigDecimal DEFAULT_DISCHARGE_PARAM_SMOKE = new BigDecimal("1.0");

	/**
	 * 缺省的排放超标设置-voc
	 */
	public static final BigDecimal DEFAULT_DISCHARGE_PARAM_VOC = new BigDecimal("10.0");

	/**
	 * 缺省的排放超标设置-pm25
	 */
	public static final BigDecimal DEFAULT_DISCHARGE_PARAM_PM25 = new BigDecimal("5.0");

	/**
	 * 清洁度计算默认预设谷值
	 */
	public static final BigDecimal CLEAN_STATUS_VALLEY_VALUE = BigDecimal.ZERO;

	/**
	 * 清洁度计算干净范围值 >60%
	 */
	public static final BigDecimal CLEAN_STATUS_CLEANING_VALUE = new BigDecimal("60");

	/**
	 * 清洁度计算较脏范围值 >10% && <=60%
	 */
	public static final BigDecimal CLEAN_STATUS_DIRTY_VALUE = new BigDecimal("10");

	/**
	 * 清洁度计算太脏范围值 >0% && <=10%
	 */
	public static final BigDecimal CLEAN_STATUS_TOODIRTY_VALUE = BigDecimal.ZERO;

	/**
	 * 设备是否在线：0,不在线;1,在线
	 */
	public static final int MONITOR_ONLINE = 1;

	/**
	 * 设备是否在线：0,不在线;1,在线
	 */
	public static final int MONITOR_OFFLINE = 0;

	/**
	 * 查询分类类型：0,区域;1,菜系
	 */
	public static final int ORG_TYPE = 0;

	/**
	 * 查询分类类型：0,区域;1,菜系
	 */
	public static final int CATERING_STYLE_TYPE = 1;

	/**
	 * 推送类型-web
	 */
	public static final int EVENT_TYPE_WEB = 0;

	/**
	 * 推送类型-app
	 */
	public static final int EVENT_TYPE_APP = 1;

	/**
	 * 推送类型-wechat
	 */
	public static final int EVENT_TYPE_WECHAT = 2;

	/**
	 * 推送类型-message
	 */
	public static final int EVENT_TYPE_MESSAGE = 3;

	public static final String START_TIME="000000";
	public static final String END_TIME="235959";
	public static final String YES="是";
	public static final String NO="否";
}
