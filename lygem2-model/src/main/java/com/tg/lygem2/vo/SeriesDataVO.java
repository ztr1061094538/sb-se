package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SeriesDataVO<T> {

	private String title;

	private String yzTitle;

	private List<CirKeyValueVO<T>> series = new ArrayList<>();
}
