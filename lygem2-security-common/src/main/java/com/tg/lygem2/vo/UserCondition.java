package com.tg.lygem2.vo;

import lombok.Data;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-12
 **/
@Data
public class UserCondition {
	private String name;
	private String address;
	private String enabled;
	private String username;
	private String city;
	private int currentPage;
	private int pageSize;
	private Integer user_type;// 1：政府账号；2：企业账号；
}
