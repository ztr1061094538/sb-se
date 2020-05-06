package com.tg.enterprise.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChartDataVO <T>
{
	private List<String> categories = new ArrayList<>();
	
	private List<ChartKeyValuePairVO<T>> series = new ArrayList<>();
	
	private String title;
	
	private String yzTitle;

	private List<String> yzTitles;

	@JsonInclude(Include.NON_NULL)
	private BigDecimal warning;
}
