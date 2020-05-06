package com.tg.lygem2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 如果children是null,则不返还该字段 省市区级联Vo
 * 
 * @author 霍腾腾
 *
 */
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel
public class CascadeNode {
	@ApiModelProperty(value = "显示名称")
	private String label;
	@ApiModelProperty(value = "值")
	private String value;
	@ApiModelProperty(value = "值")
	private String key;// key取值value
	@ApiModelProperty(value = "是否选中")
	private String selected;
	@ApiModelProperty(value = "下级")
	private List<CascadeNode> children;
	@ApiModelProperty(value = "")
	private String attr;

	public CascadeNode() {
		super();
	}

	public CascadeNode(String label, String value) {
		super();
		this.label = label;
		this.value = value;
		this.key = value;
	}

}
