package com.tg.enterprise.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EnergyProcessTarget {
    private Long id;
    private Long process_id;
    private BigDecimal data_value;
    private String remark;
    private Integer enterprise_id;
    private Long upload_date;

    public EnergyProcessTarget(EnergyProcessTarget energyProcessTarget) {

    }
}
