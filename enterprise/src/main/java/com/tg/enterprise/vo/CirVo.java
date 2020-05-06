package com.tg.enterprise.vo;

/**
 * Created by huangjianbo on 2018/4/3
 */
public class CirVo<T> {
    private String key;

    private CirDataVO<T> chartData = new CirDataVO<T>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CirDataVO<T> getChartData() {
        return chartData;
    }

    public void setChartData(CirDataVO<T> chartData) {
        this.chartData = chartData;
    }
}
