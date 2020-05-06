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
public enum CollectItemStatTypeEnum {

    ONLINE(0,"实时"),DAY(1,"日"),MONTH(2,"月"),YEAR(3,"年");

    private Integer type;
    private String name;
    public static CollectItemStatTypeEnum parse(Integer dateCode) {
        for (CollectItemStatTypeEnum value : CollectItemStatTypeEnum.values()) {
            if(dateCode.equals(value.getType())) {
                return value;
            }
        }
        return null;
    }
}
