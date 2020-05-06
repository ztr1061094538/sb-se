package com.tg.lygem2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUser {
    public Integer id;
    public String name;
    public String phone;
    public String telephone;
    public String address;
    public boolean enabled;
    public String username;
    public String email;
    public String sex;
    public String province;
    public String city;
    public String district;
    public String enterprise_id;
    public Integer area_id;
    
    public Integer user_type;//0：系统管理员；1：政府账号；2：企业账号；
}