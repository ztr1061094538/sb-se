package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by hebin on 2018/3/22
 */
@Getter
@Setter
public class AnalysisCompanySteamHour {
    private Integer enterprise_id;

    private Long upload_date;

    private BigDecimal data_value;

}
