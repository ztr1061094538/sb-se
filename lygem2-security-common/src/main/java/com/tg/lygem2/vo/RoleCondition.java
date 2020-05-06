package com.tg.lygem2.vo;

import lombok.Data;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-12
 **/
@Data
public class RoleCondition {
    public String name;
    public String desc;
    public int currentPage;
    public int pageSize;
    public Boolean isPage;
}
