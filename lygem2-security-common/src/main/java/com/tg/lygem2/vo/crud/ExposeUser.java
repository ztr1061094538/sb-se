package com.tg.lygem2.vo.crud;

import com.alibaba.fastjson.annotation.JSONField;
import com.tg.lygem2.vo.BasicUser;
import com.tg.lygem2.vo.UserOfRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class ExposeUser extends BasicUser {

    @JSONField(serialize = false)
    private String password;

    @JSONField(serialize = false)
    private List<UserOfRole> roles;

}