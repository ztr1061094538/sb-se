package com.tg.enterprise.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EnterpriseEnergyEquipmentVO {
	private Integer id;
	private Integer enterprise_id;
	private String area_code;
	private String industry_code;
	private String equipment_name;
	private String equipment_type;
	private Integer equipment_num;
	private String equipment_power;
	private Integer is_weedout;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date start_year1;
	private String installation_site;
	private String person;
	private String phone;
	private String equipment_picture;
	private String remark;
	private String equipment_survey;
	private String equipment_operation;
	private String elimination_update;
    private String area_name;
    private String industry_name;
    private String enterprise_name;
	private String equipment_picture1;
	private Integer equipment_num1;
}
