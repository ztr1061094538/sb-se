package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EnergyOnlineData {
    private String terminal_id;
    private Long upload_date;
    private BigDecimal data_value;
    private String pic_path;
    private Integer valid;
}
