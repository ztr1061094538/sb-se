package com.tg.lygem2.vo.crud;

import lombok.Data;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@Data
public class ExposeMenu {
    private Long id;
    private String url;
    private String name;
    private String iconCls;
    private Long parentId;
    private String enabled;
    private Long sortId;
    private String parentName;
    private Long type;
    private boolean localRoleIsEnadle;

    public String getParentName() {
        if(parentName == null)
        return "";
        return parentName;
    }
}
