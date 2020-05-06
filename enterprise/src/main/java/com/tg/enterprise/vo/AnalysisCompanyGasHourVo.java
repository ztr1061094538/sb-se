package com.tg.enterprise.vo;

/**
 * Created by huangjianbo on 2018/4/12
 */
public class AnalysisCompanyGasHourVo {
    private Integer enterprise_id;
    private Long startTime;
    private Long endTime;

    public Integer getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(Integer enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
