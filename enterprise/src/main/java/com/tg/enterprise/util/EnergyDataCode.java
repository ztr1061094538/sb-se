package com.tg.enterprise.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public enum EnergyDataCode 
{
	ENERGY("004","energy"),POWER("3300","power"),WATER("A0101","water"),GAS("1500","gas"),STEAM("3200","steam"),COAL("0101","coal"),
	FUELOIL("2200","fueloil"),
	OTHER("other","other");
	
	private String dataCode;
	
	private String table_sufix;
	
	public static EnergyDataCode parse(String dataCode) throws IllegalAccessException
	{
		if (dataCode.equals(EnergyDataCode.ENERGY.getDataCode()))
		{
			return ENERGY;
		}
		else if (dataCode.equals(EnergyDataCode.POWER.getDataCode()))
		{
			return POWER;
		}
		else if (dataCode.equals(EnergyDataCode.WATER.getDataCode()))
		{
			return WATER;
		}
		else if (dataCode.equals(EnergyDataCode.GAS.getDataCode()))
		{
			return GAS;
		}
		else if (dataCode.equals(EnergyDataCode.STEAM.getDataCode()))
		{
			return STEAM;
		}
		else if(dataCode.equals(EnergyDataCode.COAL.getDataCode())){
			return COAL;
		}
		else if(dataCode.equals(EnergyDataCode.OTHER.getDataCode())){
			return OTHER;
		}
		if (dataCode.equals(EnergyDataCode.FUELOIL.getDataCode()))
		{
			return FUELOIL;
		}

		throw new IllegalAccessException("不存在的dataCode"+dataCode);
	}
}
