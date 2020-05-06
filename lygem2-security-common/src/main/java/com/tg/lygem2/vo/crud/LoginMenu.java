package com.tg.lygem2.vo.crud;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-18
 **/
@Getter
@Setter
public class LoginMenu extends ExposeMenu{
    @JSONField
    private List<LoginMenu> children;
}
