package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 产量产值指标
 */
@Getter
@Setter
public class OutputEnergyTarget {
    private Long id;
    private String product;
    private String unit;
    private BigDecimal yield_value;
    private BigDecimal output_value;
    private BigDecimal unit_yield;
    private String remark;
    private Integer enterprise_id;
    private Long upload_date;
    //页面是否可以删除
    private String type;
    //产品名称
    private String product_name;
    private Long del_ids;
    private String product1;
}
