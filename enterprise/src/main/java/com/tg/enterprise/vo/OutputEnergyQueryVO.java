package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutputEnergyQueryVO {

    private Integer enterprise_id;
    private Long upload_date;
    private List<String> list;

}
