package com.tg.enterprise.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/2/10.
 */
@Data
@NoArgsConstructor
public class EnergyType {
    private java.lang.String code; // 能耗编码
    private java.lang.String name; // 名称
    private java.lang.String pcode; // 父编码
    private java.lang.String unit; // 单位
    private java.lang.String classcode; // 大类编码
    private java.lang.String nhzbdw; // 能耗折标单位
    private java.lang.Integer type; // type=1是分类；type=2是分类+分项
    private java.lang.String zbckz; // 折标量参考值
    private java.math.BigDecimal zbxs; // 实际折标系数
    private java.math.BigDecimal dwzbxs; // 单位折标系数
    private Integer category;//类型 0：其他 1：煤 2：电 3：气

    public EnergyType(EnergyType energyType) {

    }
}
