package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by huangjianbo on 2018/5/11
 */
@Getter
@Setter
public class MeteringCheckCount {

    private Integer metering_type_id;
    private Integer getTime;
    private Integer checked_ok;
    private Integer checked_error;
}
