package com.tg.lygem2.vo.crud;

import com.tg.lygem2.vo.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@Getter
@Setter
public class RoleOfAllMenuIds extends Role {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7952336691168976819L;
	private List<Long> menuIds;
    private Boolean isChangMenuIds;
}
