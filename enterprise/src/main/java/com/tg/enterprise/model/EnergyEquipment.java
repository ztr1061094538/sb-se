package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyEquipment {
    private Long id;
    private Integer companyId;
    private String name;//设备名称
    private String category;//能源品种
    private String inputType;
}
