package com.tg.enterprise.vo;

import java.util.ArrayList;
import java.util.List;

public class BarVO <T>
{
	private String key;
	
	private List<ChartDataVO<T>> chartData = new ArrayList<>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<ChartDataVO<T>> getChartData() {
		return chartData;
	}

	public void setChartData(List<ChartDataVO<T>> chartData) {
		this.chartData = chartData;
	}
}
