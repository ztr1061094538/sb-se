package com.tg.enterprise.vo;

import lombok.Data;

import java.util.Set;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-11-26
 **/
@Data
public class EnergyQueryVO {
    private String tableName;
    private Long startDate;
    private Long endDate;
    private Long upload_date;
    private Set<String> terminalIdSet;

}
