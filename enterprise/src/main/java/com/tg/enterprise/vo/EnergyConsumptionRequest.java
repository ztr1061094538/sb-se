package com.tg.enterprise.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyConsumptionRequest<T> 
{
	private String key;
	//2,按照年，1，按照月
	private int queryType;
	//按照年，2018；按照月传2018-05
	private String queryTime;
	//行业编码或者地区编码
	private T code;

	//ENERGY("004","energy"),POWER("3300","power"),WATER("A0101","water"),GAS("1500","gas"),STEAM("3200","steam");
	private String dataCode;

	private Integer enterpriseId;
}
