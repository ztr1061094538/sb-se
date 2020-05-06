package com.tg.enterprise.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 企业信息
 */
@Getter
@Setter
public class EnterpriseVO {
    private Integer id;
    private String name;
    private String type_code;
    private String code;
    private String industry_code;
    private Integer center;
    private Integer energy_consume_level;
    private Integer jgzh;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String area; //所属区域
    private String energy_scale; //用能规模
    private Integer is_sx; //是否涉限
    private String phone;
    private String fax;
    private String email;
    private String address;
    private String zip_code;
    private String url;
    private String field_code;
    private String field_name;
    private String corporation_name;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date register_date1;
    private Integer register_principal;
    private String energy_office;
    private String energy_official;
    private String energy_official_position;
    private String energy_official_phone;
    private Integer energy_pass;
    private String energy_resp_name;
    private String energy_resp_phone;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pass_date1;
    private String pass_org;
    private String production_line;
    private String major_product;
    private String enterprise_introduce;//单位介绍
    private String enterprise_data;//企业附件资料
    private String remark;
    private String province;
    private String city;
    private Integer is_energySupply;
    private Integer is_power;
    private String district;
    private String type_name;
    private String area_name;
    private String industry_name;
    private String scale_name;
    private String enterprise_data1;
    private String province_name;
    private String city_name;
    private String district_name;
    private String manage_code;
    private String manage_name;
    private Integer equipment_total = 0;
    private String owned_group_name;//所属集团名称
    private String energy_inspect_name;//能源监测项目负责人姓名
    private String energy_inspect_phone;//能源监测项目能管负责人电话
    private List<String> industryCodeArr;//多级行业数组
}