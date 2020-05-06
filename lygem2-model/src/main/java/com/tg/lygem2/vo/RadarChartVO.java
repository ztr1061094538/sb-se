package com.tg.lygem2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel
@Getter
@Setter
public class RadarChartVO<T> {

	@ApiModelProperty("雷达图名称")
	private String name;
	@ApiModelProperty("雷达图各区域值")
	private List<T> value;
	@ApiModelProperty("雷达图各区域名称")
	private List<String> text;
	@ApiModelProperty("雷达图各区域最大值")
	private List<T> max;
	@ApiModelProperty("雷达图类型")
	private String type;
}
