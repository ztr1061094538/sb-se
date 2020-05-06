package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @program: em2-parent
 * @author: jikai.sun
 * @create: 2018-08-23
 **/
@Getter
@Setter
public class TreeMap {

    private String source;
    private String target;
    private BigDecimal value;

}
