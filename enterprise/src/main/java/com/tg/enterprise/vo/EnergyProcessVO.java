package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EnergyProcessVO {
    private Integer enterprise_id;
    private Long process_id; //工序id
    private List<Long> process_ids;
    private String name;   //设备名称
    private String energyTypeName;//能源品种
    private String unit;//能源品种单位
    private Integer companyId;
    private BigDecimal data_value;
    private String remark;
    private Long upload_date;
    //前端
    private Integer id;
}
