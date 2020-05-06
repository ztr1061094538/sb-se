package com.tg.enterprise.vo;

import lombok.Data;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-11-26
 **/
@Data
public class EnergyTableRespVO {
    private String month;
    private String power;
    private String water;
    private String gas;
    private String steam;
    private String coal;
    private String other;

}
