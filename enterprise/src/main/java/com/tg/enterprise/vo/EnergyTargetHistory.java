package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 能源总量指标
 */
@Getter
@Setter
public class EnergyTargetHistory 
{
    private String energy_type_name;
    private String energy_type_unit;
    private BigDecimal purchase;
    private BigDecimal supply;
    private BigDecimal opening_inventory;
    private BigDecimal ending_inventory;
    private BigDecimal energy_consumption;
    private String remark;
    private Long upload_date;
}
