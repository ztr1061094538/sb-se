package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/5/16.
 */
@Getter
@Setter
public class AnalysisCompany {
    private Integer enterprise_id;
    private Long upload_date;
    private BigDecimal data_value = new BigDecimal(0);
}
