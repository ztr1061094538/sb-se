package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/5/16.
 */
@Getter
@Setter
public class AnalysisTerminal {
    private String terminal_id;
    private long upload_date;
    private BigDecimal data_value;
}
