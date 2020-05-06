package com.tg.enterprise.vo;

import lombok.Data;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-12-04
 **/
@Data
public class EnterpriseRespVO {

    private String name;//mingc
    private String type;//企业类型
    private String code;//统一社会信用代码
    private String industry;//所属领域
    private String energy_scale; //用能规模
    private Integer equipmentNum;//主要能耗设备数量
}
