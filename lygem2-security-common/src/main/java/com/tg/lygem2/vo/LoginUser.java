package com.tg.lygem2.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser extends BasicUser {

    @JSONField(serialize = false)
    private String password;

    @JSONField(serialize = false)
    private List<Integer> roleIds;

    private List<Role> roles;

    private Boolean changPs;
    
    private String companyName;
//    private Boolean isChangRoleIds;
}