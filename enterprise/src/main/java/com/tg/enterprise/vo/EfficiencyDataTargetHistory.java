package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 能效数据指标
 */
@Getter
@Setter
public class EfficiencyDataTargetHistory 
{
	private int enterprise_id;
    private String efficiency_data;
    private String unit;
    private BigDecimal data_value;
    private String remark;
}
