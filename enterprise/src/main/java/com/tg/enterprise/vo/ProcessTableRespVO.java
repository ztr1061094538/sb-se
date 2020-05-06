package com.tg.enterprise.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-12-03
 **/
@Data
public class ProcessTableRespVO {

    private String processName;
    private String water;
    private String power;
    private String gas;
    private String coal;
    private String other;
}
