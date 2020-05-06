package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by huangjianbo on 2018/3/7
 * 节能专家库表
 */
@Setter
@Getter
public class EnergyExpert {

    private Integer id;
    private Integer enterprise_id;
    private String expertName;   //专家姓名
    private String education;    //学历
    private String academicTitle;    //职称
    private Integer gender;    //性别（0：男，1：女）
    private String headImage;    //头像
    private String expertIntroduce;    // 专家介绍
    private String expertInstitution;    //专家所属机构
    private String expertTel;    //专家电话
    private String headImage1;    // url  用于显示图片
    private String idNumber; //身份证账号
    private String birthday; //出生年月
    private String domain;  //所属领域
    private String remark; //备注


}
