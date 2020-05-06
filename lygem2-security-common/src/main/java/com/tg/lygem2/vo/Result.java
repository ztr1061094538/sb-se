package com.tg.lygem2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-29
 **/
@Data
@AllArgsConstructor
public class Result<T> {
    private int code = 0;
    public String msg;
    private T restluLis = null;
    private Long total;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    
    public Result(int code, String msg, T restluLis) {
        this.code = code;
        this.msg = msg;
        this.restluLis = restluLis;
    }

    public static Result<Object> instance(int code, String msg) {
        return new Result<Object>(code, msg);
    }


	public Result() {
		super();
	}

}
