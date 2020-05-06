package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-19
 **/
@Setter
@Getter
@NoArgsConstructor
public class BmpResulter implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private Object data;
    private List<Object> listData;
    private String key;

    public BmpResulter(int status, String msg, Object data, List<Object> listData , String key) {
        this.code = status;
        this.msg = msg;
        this.data = data;
        this.listData = listData;
        this.key = key;
    }

    public static BmpResulter newinstance(int status, String msg, Object data){
        return new BmpResulter(status,msg,data,null,null);
    }

    public static BmpResulter newinstance(int status, String msg, Object data,List<Object> listData){
        return new BmpResulter(status,msg,data,listData,null);
    }

    public static BmpResulter newinstance(int status,String msg,String key){
        return new BmpResulter(status,msg,null,null,key);
    }

}
