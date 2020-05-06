package com.tg.lygem2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOfRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public static UserOfRole instance(Integer id,Integer userId,Integer roleId){
        return new UserOfRole(id,userId,roleId);
    }
}
