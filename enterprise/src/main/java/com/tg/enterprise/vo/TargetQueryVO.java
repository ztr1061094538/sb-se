package com.tg.enterprise.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-11-25
 **/
@Data
public class TargetQueryVO {

    private Long startTime;
    private Long endTime;
    private Integer enterpriseId;
    private Long upload_date;
    private List<Integer> enterpriseIds;

}
