package com.tg.enterprise.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-11-20
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EnergyTypeEnum {

    ENERGY("004","综合能耗"),POWER("3300","电"),WATER("A0101","水"),GAS("1500","天然气"),STEAM("3200","热"),COAL("0101","煤炭"),
    FUELOIL("2200","燃料油"),
    OTHER("other","其它");

    private String dataCode;

    private String name;

    public static EnergyTypeEnum parse(String dateCode) {
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            if(dateCode.equals(value.getDataCode())) {
                return value;
            }
        }
        return null;
    }
}
