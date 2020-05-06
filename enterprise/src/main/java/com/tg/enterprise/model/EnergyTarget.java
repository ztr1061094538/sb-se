package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 能源总量指标
 */
@Getter
@Setter
public class EnergyTarget {
    private Long id;
    private String energy_type;
    private BigDecimal purchase;
    private BigDecimal supply;
    private BigDecimal opening_inventory;
    private BigDecimal ending_inventory;
    private BigDecimal energy_consumption;
    private String remark;
    private Long upload_date;
    private Integer enterprise_id;
    private String energy_type_name;
    private String energy_type_unit;
}
