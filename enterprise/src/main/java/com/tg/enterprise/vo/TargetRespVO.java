package com.tg.enterprise.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-11-25
 **/
@Data
public class TargetRespVO {

    private Long uploadTime;
    private BigDecimal sumConsumption;
    private BigDecimal sumYield;
    private BigDecimal sumOutput;
}
