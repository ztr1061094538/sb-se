package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线图VO
 * 
 * @author 霍腾腾
 *
 * @param <T>
 */
@Getter
@Setter
public class BarVO<T> {
	private List<ChartDataVO<T>> chartData = new ArrayList<>();
}
