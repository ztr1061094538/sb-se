package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by huangjianbo on 2018/4/12
 */
@Getter
@Setter
public class AnalysisCompanyPowerHourVo {
    private List<Integer> enterpriseIds;
    private Integer enterprise_id;
    private Long startTime;
    private Long endTime;
    private Long upload_date;
    
}
