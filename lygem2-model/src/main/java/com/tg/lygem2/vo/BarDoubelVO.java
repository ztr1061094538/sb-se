package com.tg.lygem2.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 2个折线图VO
 * @param <T>
 */
@Getter
@Setter
@ApiModel
public class BarDoubelVO<T> {
	private List<ChartDataVO<T>> chartDataCleanInfo = new ArrayList<>();
	private List<ChartDataVO<T>> chartDataOnlineInfo = new ArrayList<>();
}
