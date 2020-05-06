package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseQueryVO {
    private Integer type;
	private String name;
	private Integer orderByName; // 1，正序；-1，倒序；
    private String province;
    private String city;
    private String district;
    private String manage_code;
    private Integer is_del;// 0未删除    1已删除
    private Integer enterprise_id;//企业id
    private Long upload_date; // 重点用能单位上报数据状态表 格式yyyyMM

}
