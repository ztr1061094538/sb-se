package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by hebin on 2018/4/3
 */
@Setter
@Getter
public class PageTableTitleVO<T>  extends CommonResponse{
    private long total;
    private List<T> rows;
    private Map<String,String> map;

    public PageTableTitleVO(int code, String msg) {
        super(code,msg);
    }
    
    public static PageTableTitleVO fail(int errorCode,String msg)
	{
		return new PageTableTitleVO(errorCode,msg);
	}
}
