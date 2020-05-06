package com.tg.enterprise.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-12-16
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EnergyUnitEnum {
    ENERGY("004","吨标准煤"),POWER("3300","千瓦时"),WATER("A0101","立方米"),GAS("1500","立方米"),STEAM("3200","万千焦耳"),COAL("0101","吨"),OTHER("other","吨标准煤");

    private String dataCode;

    private String unit;

    public static EnergyUnitEnum parse(String dataCode) throws IllegalAccessException
    {
        if (dataCode.equals(EnergyUnitEnum.ENERGY.getDataCode()))
        {
            return ENERGY;
        }
        else if (dataCode.equals(EnergyUnitEnum.POWER.getDataCode()))
        {
            return POWER;
        }
        else if (dataCode.equals(EnergyUnitEnum.WATER.getDataCode()))
        {
            return WATER;
        }
        else if (dataCode.equals(EnergyUnitEnum.GAS.getDataCode()))
        {
            return GAS;
        }
        else if (dataCode.equals(EnergyUnitEnum.STEAM.getDataCode()))
        {
            return STEAM;
        }
        else if(dataCode.equals(EnergyUnitEnum.COAL.getDataCode())){
            return COAL;
        }
        else if(dataCode.equals(EnergyUnitEnum.OTHER.getDataCode())){
            return OTHER;
        }
        throw new IllegalAccessException("不存在的dataCode"+dataCode);
    }
}
