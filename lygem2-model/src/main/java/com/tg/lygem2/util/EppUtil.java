package com.tg.lygem2.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class EppUtil {

	/**
	 * 
	 * @param map
	 * @param flag
	 *            true 正序；false 倒序
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean flag) {
		Map<K, V> result = new LinkedHashMap<>();
		Stream<Entry<K, V>> st = map.entrySet().stream();
		if (flag) {
			st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
		} else {
			st.sorted(Comparator.comparing(e -> ((Entry<K, V>) e).getValue()).reversed())
					.forEach(e -> result.put(e.getKey(), e.getValue()));
		}
		return result;
	}

	public static String generateMN() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 24);
	}

	/**
	 * 获取两个日期的时间差，天
	 * 
	 * @param startTime
	 *            yyyyMMddHHmmss
	 * @param endTime
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static long getDaysBetweenTimes(Long startTime, Long endTime) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime start = LocalDateTime.parse(startTime + "", df);
		LocalDateTime end = LocalDateTime.parse(endTime + "", df);
		Duration dur = Duration.between(start, end);
		return dur.toDays();
	}

	/**
	 * 获取两个日期的时间差，xx小时xx分钟
	 * 
	 * @param startTime
	 *            yyyyMMddHHmmss
	 * @param endTime
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static String getMinutesBetweenTimes(Long startTime, Long endTime) {
		String hour = "";
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime start = LocalDateTime.parse(startTime + "", df);
		LocalDateTime end = LocalDateTime.parse(endTime + "", df);
		Duration dur = Duration.between(start, end);
		hour = dur.toMinutes() / 60 + "小时" + dur.toMinutes() % 60 + "分钟";
		return hour;
	}

	/**
	 * 日期增加天数
	 * 
	 * @param time
	 * @param days
	 * @return
	 */
	public static Long addDays(Long time, int days) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime base = LocalDateTime.parse(time + "", df);
		return Long.valueOf(base.plusDays(days).format(df));
	}

	public static void main(String args[]) {
		System.out.println(getMinutesBetweenTimes(20200313000000L, 20200313155830L));

		Set<String> mn = new HashSet<>();
		for (int i = 0; i < 10000; i++) {
			mn.add(generateMN());
		}

		Map<Long, Integer> map = new HashMap<>();
		map.put(1L, 0);
		map.put(2L, 10);
		map.put(3L, 5);
		Map<Long, Integer> res = sortByValue(map, true);
		for (Long id : res.keySet()) {
			System.out.println(id);
		}
	}
}
