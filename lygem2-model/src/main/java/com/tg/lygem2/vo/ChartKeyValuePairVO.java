package com.tg.lygem2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL) 
public class ChartKeyValuePairVO<T> {
	private String name; // 表示
	private String type; // 双坐标
	private List<T> data = new ArrayList<T>(); // 时间段数据
	
	private String stack; // 柱状图多层
}
