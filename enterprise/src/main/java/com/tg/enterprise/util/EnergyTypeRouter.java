package com.tg.enterprise.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */
public class EnergyTypeRouter 
{
	public static List<EnergyDataCode> getDataCodes()
	{
		List<EnergyDataCode> dataCodes = new ArrayList<>();
		dataCodes.add(EnergyDataCode.ENERGY);
		dataCodes.add(EnergyDataCode.STEAM);
		dataCodes.add(EnergyDataCode.GAS);
		dataCodes.add(EnergyDataCode.WATER);
		dataCodes.add(EnergyDataCode.POWER);
		return dataCodes;
	}
	
	public static List<EnergyDataCode> getDataCodesWithEnergy()
	{
		List<EnergyDataCode> dataCodes = new ArrayList<>();
		//dataCodes.add(EnergyDataCode.STEAM);
		dataCodes.add(EnergyDataCode.GAS);
		dataCodes.add(EnergyDataCode.WATER);
		dataCodes.add(EnergyDataCode.POWER);
		dataCodes.add(EnergyDataCode.COAL);
		dataCodes.add(EnergyDataCode.OTHER);
		return dataCodes;
	}
	
    public static String getOnlineTableName(EnergyDataCode code){
        String tableName="t_online_data_"+code.getTable_sufix();
        return tableName;
    }
    
    public static String getAnalysisTableName(EnergyDataCode dataCode,AnalysisTypeEnum type,AnalysisEntityEnum entity)
    {
    	return new StringBuilder("analysis_").append(entity.getTable_sufix()).append("_").append(dataCode.getTable_sufix()).append("_").append(type.getTableSuffix()).toString();
    }
    
    public static String getDynamicColumnName(AnalysisEntityEnum entity) {
    	switch (entity) {
		case TERMINAL:
			return "terminal_id";
		case COMPANY:
			return "enterprise_id";
		case INDUSTRY:
			return "industry_code";
		case DISTRICT:
			return "district";
		case CITY:
			return "city";
		default:
			return "terminal_id";
		}
    }
    /**
     * 获取在redis中上次统计数据
     * @param dataCode 能源编码
     * @param type
     * @param entity
     * @param primaryKey
     * @return
     */
    public static String getRedisKey(EnergyDataCode dataCode,AnalysisTypeEnum type,AnalysisEntityEnum entity,String primaryKey)
    {
    	//TODO 
    	return null;
    }
}
