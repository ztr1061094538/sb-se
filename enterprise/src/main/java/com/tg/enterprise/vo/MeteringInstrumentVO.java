package com.tg.enterprise.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MeteringInstrumentVO {

	private Integer id;
	private String metering_name;
	private Integer metering_type;
	private Integer metering_level;
	private String metering_parameter;
	private String data_code;
	private Integer data_code_calculate;
	private Integer data_code_ratio;
	private String manu_facturer;
	private String type_specification;
	private String accuracy_level;
	private String measure_range;
	private String manage_code;
	private Integer calibration_state;
	private String calibration_cycle;
	private String inspection_organization;
	private String not_calibration;
	private String installation_site;
	private Integer install_org;
	private Integer usr_system;
	private Integer measure_state;
	private String enterprise_code;
	private Integer enterprise_id;
	private String terminal_id;
	private String enterprise_name;
	private String file_path;
	private String downURL;
	private String type_name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lately_calibration1;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date next_calibration1;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date install_date1;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date measure_state_date1;
}
