package com.tg.enterprise.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public enum OutputProduct {

    GROSS_OUTPUT("gycz","工业总产值","万元"),INDUSTRIAL_VALUE("gyzj","工业增加值","万元");
    private String code;
    private String name;
    private String unit;
    public static OutputProduct parse(String dataCode){
       for(OutputProduct entity:OutputProduct.values()){
           if(dataCode.equals(entity.getCode())){
               return entity;
           }
       }
       return null;
    }
}
