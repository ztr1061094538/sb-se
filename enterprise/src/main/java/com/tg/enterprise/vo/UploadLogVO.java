package com.tg.enterprise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UploadLogVO 
{
	private int id;

	@ApiModelProperty(value = "上传类别")
	private String action;
	@ApiModelProperty(value = "0：正常，1：补传")
	private boolean status;
	@ApiModelProperty(value = "是否成功")
	private boolean success;
	@ApiModelProperty(value = "上传时间")
	private String uploadTime;
	@ApiModelProperty(value = "重试次数")
	private int retryTime;
	@ApiModelProperty(value = "是否重传")
	private boolean retryFlag;
}
