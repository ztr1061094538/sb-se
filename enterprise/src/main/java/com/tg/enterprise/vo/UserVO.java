package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserVO 
{
	private int id;

	private String p_id;
	
	private int role_id;
	
	private boolean admin;
	
	private List<Integer> permission_id;
	private int account_type;
}
