package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeteringInstrumentQueryVO {

	private String metering_name;
	private String metering_type;

	private String terminal_id;
	
	private Integer enterprise_id;
	
	private String dataCode;
}
