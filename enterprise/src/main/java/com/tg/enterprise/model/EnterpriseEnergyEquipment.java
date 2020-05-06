package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnterpriseEnergyEquipment {
	private Integer id;
	private Integer enterprise_id;
	private String area_code;
	private String industry_code;
	private String equipment_name;
	private String equipment_type;
	private Integer equipment_num;
	private String equipment_power;
	private Integer is_weedout;
	private Long start_year;
	private String installation_site;
	private String person;
	private String phone;
	private String equipment_picture;
	private String remark;
	private String equipment_survey;
	private String equipment_operation;
	private String elimination_update;
	private Integer is_del;	
}
