package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeteringInstrument {

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
	private Long lately_calibration;
	private String inspection_organization;
	private Long next_calibration;
	private String not_calibration;
	private String installation_site;
	private Integer install_org;
	private Long install_date;
	private Integer usr_system;
	private Integer measure_state;
	private Long measure_state_date;
	private String enterprise_code;
	private Integer enterprise_id;
	private Integer is_del;
	private String terminal_id;
	private String file_path;

}
