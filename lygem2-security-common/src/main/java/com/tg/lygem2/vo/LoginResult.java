package com.tg.lygem2.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResult 
{
	private String authName;
	
	private String auth;
	
	private Integer userType;
	
	private String enterpriseId;
}
