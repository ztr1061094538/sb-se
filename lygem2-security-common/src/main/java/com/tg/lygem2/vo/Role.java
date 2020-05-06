package com.tg.lygem2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2292783430485333566L;
	private Integer id;
    private String name;
    private String nameZh;
}
