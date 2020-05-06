package com.tg.lygem2.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CirVO<T> {
	private CirDataVO<T> chartData = new CirDataVO<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JSONObject object;
}
