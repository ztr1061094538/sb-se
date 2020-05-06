package com.tg.enterprise.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjianbo on 2018/4/3
 */
public class CirDataVO<T> {
    private String title;

    private String yzTitle;

    private List<CirKeyValueVo> seriesData = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYzTitle() {
        return yzTitle;
    }

    public void setYzTitle(String yzTitle) {
        this.yzTitle = yzTitle;
    }

    public List<CirKeyValueVo> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<CirKeyValueVo> seriesData) {
        this.seriesData = seriesData;
    }
}
