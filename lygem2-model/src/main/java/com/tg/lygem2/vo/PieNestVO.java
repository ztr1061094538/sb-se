package com.tg.lygem2.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * 嵌套饼图
 * 
 * @author 霍腾腾
 *
 * @param <T>
 */
@Getter
@Setter
public class PieNestVO<T> {

	private PieNestDataVO<T> chartData = new PieNestDataVO<>();

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JSONObject object;
}
