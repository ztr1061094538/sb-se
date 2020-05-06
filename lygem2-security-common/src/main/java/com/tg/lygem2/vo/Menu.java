package com.tg.lygem2.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Data
public class Menu implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6814169476404793920L;
	private Long id;
    private String url;
    private String name;
    private String iconCls;
    private Long parentId;
    private List<Role> roles;
    private List<Menu> children;
    private String enabled;
}
