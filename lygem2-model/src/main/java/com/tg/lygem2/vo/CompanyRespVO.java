package com.tg.lygem2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class CompanyRespVO {
	@ApiModelProperty(value = "企业唯一id")
	private Integer company_id;
	@ApiModelProperty(value = "企业名称")
	private String name;
	@ApiModelProperty(value = "所属机构id")
	private Integer org_id;
	@ApiModelProperty(value = "餐饮风格，菜系，000010001")
	private String catering_style;
	@ApiModelProperty(value = "餐饮企业规模，000090001")
	private String catering_scale;
	@ApiModelProperty(value = "企业性质code，000080001")
	private String property_code;
	@ApiModelProperty(value = "省份编码")
	private Integer province;
	@ApiModelProperty(value = "城市编码")
	private Integer city;
	@ApiModelProperty(value = "县区编码")
	private Integer district;
	@ApiModelProperty(value = "企业地址")
	private String address;
	@ApiModelProperty(value = "纬度")
	private String latitude;
	@ApiModelProperty(value = "经度")
	private String longitude;
	@ApiModelProperty(value = "法人")
	private String corporation_name;
	@ApiModelProperty(value = "企业联系人")
	private String contact;
	@ApiModelProperty(value = "企业联系电话")
	private String phone;
	@ApiModelProperty(value = "社会信用代码(法人代码)")
	private String xinyong_code;
	@ApiModelProperty(value = "总人数")
	private Integer people;
	@ApiModelProperty(value = "面积")
	private String acreage;
	@ApiModelProperty(value = "是否删除，0：未删除；1：已删除")
	private Integer is_del;

	@ApiModelProperty(value = "机构名称")
	private String org_name;
	@ApiModelProperty(value = "企业性质名称")
	private String property_name;
	@ApiModelProperty(value = "省份编码(名称)")
	private String province_name;
	@ApiModelProperty(value = "城市编码(名称)")
	private String city_name;
	@ApiModelProperty(value = "县区编码(名称)")
	private String district_name;
	@ApiModelProperty(value = "餐饮企业规模名称")
	private String catering_scale_name;
	@ApiModelProperty(value = "餐饮风格，菜系名称")
	private String catering_style_name;
	@ApiModelProperty(value = "净化设施数量")
	private Integer facilities_num;
	@ApiModelProperty(value = "净化器数量")
	private Integer purifier_num;
	@ApiModelProperty(value = "风机数量")
	private Integer fan_num;
	@ApiModelProperty(value = "风机在线数量")
	private Integer fan_online_num;
	@ApiModelProperty(value = "净化器在线数量")
	private Integer purifier_online_num;
	@ApiModelProperty(value = "风机离线数量")
	private Integer fan_offline_num;
	@ApiModelProperty(value = "净化器离线数量")
	private Integer purifier_offline_num;
	@ApiModelProperty(value = "当前油烟浓度")
	private BigDecimal smoke_density;
	@ApiModelProperty(value = "设备异常已申报总数")
	private Integer offline_declare_total;
	@ApiModelProperty(value = "设备异常未申报总数")
	private Integer offline_undeclare_total;
	@ApiModelProperty(value = "超标已申报总数")
	private Integer exceed_declare_total;
	@ApiModelProperty(value = "超标未申报总数")
	private Integer exceed_undeclare_total;
	@ApiModelProperty(value = "油烟超标总数")
	private Integer exceed_smoke_num;
	@ApiModelProperty(value = "非甲烷超标总数")
	private Integer exceed_voc_num;
	@ApiModelProperty(value = "颗粒物超标总数")
	private Integer exceed_pm25_num;
	@ApiModelProperty(value = "超标设备总数")
	private Integer exceed_device_num;
	@ApiModelProperty(value = "油烟超标code")
	private String smoke_warn_code;
	@ApiModelProperty(value = "非甲烷超标code")
	private String voc_warn_code;
	@ApiModelProperty(value = "颗粒物超标code")
	private String pm25_warn_code;

}
