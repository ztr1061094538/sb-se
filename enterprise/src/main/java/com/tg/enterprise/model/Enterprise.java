package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/1/30.
 * 企业信息
 */
@Getter
@Setter
public class Enterprise implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8057602671315685279L;
	
	private Integer id;//序号
    private String area;//所属区域
    private String name; //单位名称
    private Integer type;//1：企业；2：集团
    private String type_code;//单位类别
    private String code;  //社会信用代码
    private String industry_code; //行业编码
    private Integer center;
    private Integer energy_consume_level;
    private Integer jgzh;
    private BigDecimal latitude; //经度
    private BigDecimal longitude; //纬度
    private String energy_scale; //用能规模
    private Integer is_sx; //是否涉限
    private String phone;//联系电话
    private String fax; //传真
    private String email; //企业邮箱
    private String address;//企业地址
    private String zip_code; //邮编
    private String url;
    private String field_code;
    private String field_name;
    private String corporation_name;
    private Long register_date;
    private Integer register_principal;
    private String energy_office;
    private String energy_official;
    private String energy_official_position;
    private String energy_official_phone;
    private Integer energy_pass;
    private String energy_resp_name;//能管员
    private String energy_resp_phone;//能管负责人电话
    private Long pass_date;
    private String pass_org;
    private String production_line;
    private String major_product;//主要产品及业务
    private String remark;
    private String province;
    private String city;
    private String district;
    private String company_charger;
    private Integer is_del;
    private String enterprise_introduce;//单位介绍
    private String enterprise_data;//企业附件资料
    private String firstPinyinName;
    private Integer is_energySupply;
    private Integer is_power;
    private String manage_code;
    private Integer equipment_total = 0;
    private String owned_group_name;//所属集团名称
    private String energy_inspect_name;//能源监测项目负责人姓名
    private String energy_inspect_phone;//能源监测项目能管负责人电话
}