package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 能效数据指标
 */
@Getter
@Setter
public class EfficiencyDataTarget {
    private Long id;
    private String efficiency_data;
    private String unit;
    private BigDecimal data_value;
    private String remark;
    private Long upload_date;
    private Integer enterprise_id;
}
