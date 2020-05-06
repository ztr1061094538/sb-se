package com.tg.enterprise.vo;

public class LineVO<T>
{
	private String key;
	
	private ChartDataVO<T> chartData ;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ChartDataVO<T> getChartData() {
		return chartData;
	}

	public void setChartData(ChartDataVO<T> chartData) {
		this.chartData = chartData;
	}
}
