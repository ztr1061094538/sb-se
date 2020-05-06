package com.tg.lygem2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TreeVO {
	@ApiModelProperty(value = "显示名称")
	private String title;
	@ApiModelProperty(value = "值")
	private String key;
	@ApiModelProperty(value = "属性")
	private String attr;
	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "下级")
	private List<TreeVO> children;
	@ApiModelProperty(value = "监测点类型")
	private String monitor_type;

	public TreeVO(String title, String key, String type) {
		this.title = title;
		this.key = key;
		this.type = type;
	}

	public TreeVO() {
	}
}
