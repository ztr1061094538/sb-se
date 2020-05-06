package com.tg.lygem2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PageRequestVO<T> {
	@ApiModelProperty(value="页码",required=true)
	private int pageIndex;
	@ApiModelProperty(value="每页记录数",required=true)
	private int pageSize;
	@ApiModelProperty(value="查询参数",required=true)
	private T params;
}
