package com.tg.lygem2.vo;

import lombok.Data;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-22
 **/
@Data
public class MenuCondition {
    public String id;
    public String enabled;
    public String name;
    public int currentPage;
    public int pageSize;
    //定义是否分页
    public Boolean isPage;
    //定义是否拿菜单   0==menu  1==button
    public String isMenu;

    public static MenuCondition instance() {
        MenuCondition menuCondition = new MenuCondition();
        menuCondition.setIsPage(false);
        return menuCondition;
    }
}
