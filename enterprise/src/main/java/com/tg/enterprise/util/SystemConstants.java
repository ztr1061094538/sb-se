package com.tg.enterprise.util;

public class SystemConstants {
	/**
	 * 已生效、已激活、已删除等，表示肯定。
	 */
	public static final int ENABLE = 1;
	/**
	 * 未生效、已激活、已删除等，表示否定。
	 */
	public static final int DISABLE = 0;
	
	/**
	 * 未生效、已激活、已删除等，表示否定。
	 */
	public static final long CN95598_YUANDONG_COMPANYID = 1518l;
	
	/**
	 * 运行状态-0.有效运行  1.无效运行  2.停机  4.异常
	 */
	public static final int RUNNING_STATUS_VAILD = 0;
	public static final int RUNNING_STATUS_INVAILD = 1;
	public static final int RUNNING_STATUS_STOP = 2;
	public static final int RUNNING_STATUS_ERROR = 3;
	
	
	/**
	 * 监测点根节点默认上级ID
	 */
	public final static int MONITOR_ROOT_PARENT_DEFAULT_VALUE = -1;
	/**
	 * 监测点树监测点类型
	 */
	public final static String MONITOR_TREE_TYPE_MONITOR = "monitor";
	
	public final static String[][] POWER_TYPE = new String[][] {
		
	};
	
	public final static String SESSION_KEY = "com.tg.feecable.user";
	
	
	/**
	 * token起始符
	 */
	public static final String REDIS_TOKEN = "yuandong:token_";
	
	
	public static final String APP_KEY ="769a1c3505a0661e15d9104c";
	public static final String MASTER_SECRET = "bd2153ec810d0b7e6a06c805";
	
	/**
	 * 配网
	 */
	public static final int MONITOR_SEETYPE_DMS = 0;
	/**
	 * 变压器
	 */
	public static final int MONITOR_SEETYPE_TRANSFORMER = 1;
	/**
	 * 虚拟节点
	 */
	public static final int MONITOR_SEETYPE_INVENTEDNODE = 2;
	/**
	 * 三相四线
	 */
	public static final int MONITOR_WIREWAY_SANXIANGSIXIANG = 1;
	/**
	 * session的过期时间单位秒
	 */
	public static final int SESSION_EXPIRE_TIME = 60 * 30;
	
	
	
}
