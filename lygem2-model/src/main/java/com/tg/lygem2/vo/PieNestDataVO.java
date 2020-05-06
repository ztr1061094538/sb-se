package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieNestDataVO<T> {

	private SeriesDataVO<T> seriesDataOuter;// 外环

	private SeriesDataVO<T> seriesDataInner;// 内环
}
