package com.tg.lygem2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChartDataVO<T> {
	private List<String> categories = new ArrayList<>(); // 横坐标 ["00:00", "00:30", "01:00", "01:30", "02:00", "02:30",
															// "03:00", "03:30", "04:00", "04:30"],

	private List<ChartKeyValuePairVO<T>> series = new ArrayList<>();

	private String title;// 标题

	private String yzTitle;// 单位

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<List<AxisVO>> markArea;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal warning; // 阈值

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal average; // 均值
}
