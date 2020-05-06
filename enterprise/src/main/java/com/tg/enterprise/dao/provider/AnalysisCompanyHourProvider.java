package com.tg.enterprise.dao.provider;

import com.tg.enterprise.vo.AnalysisCompanyPowerHourVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;

/**
 * Created by huangjianbo on 2018/4/12
 */
public class AnalysisCompanyHourProvider {

    public String getDataByPower(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("upload_date, sum(data_value) as data_value");
                FROM("analysis_company_power_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if(!CollectionUtils.isEmpty(analysisCompanyPowerHour.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(analysisCompanyPowerHour.getEnterpriseIds(), ",") + ")");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }
    public String getDataByPowerByMonth(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("*");
                FROM("analysis_company_power_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getTotalByPower(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_power_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getDataByWater(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("upload_date, sum(data_value) as data_value");
                FROM("analysis_company_water_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if(!CollectionUtils.isEmpty(analysisCompanyPowerHour.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(analysisCompanyPowerHour.getEnterpriseIds(), ",") + ")");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }
    public String getDataByWaterByMonth(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("*");
                FROM("analysis_company_water_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getTotalByWater(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_water_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getDataBySteam(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("upload_date, sum(data_value) as data_value");
                FROM("analysis_company_steam_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if(!CollectionUtils.isEmpty(analysisCompanyPowerHour.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(analysisCompanyPowerHour.getEnterpriseIds(), ",") + ")");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }

    /**
     * 新增燃料油查询
     * @param analysisCompanyPowerHour
     * @return
     */
    public String getDataByFueloil(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("upload_date, sum(data_value) as data_value");
                FROM("analysis_company_fueloil_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if(!CollectionUtils.isEmpty(analysisCompanyPowerHour.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(analysisCompanyPowerHour.getEnterpriseIds(), ",") + ")");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }





    public String getDataBySteamByMonth(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("*");
                FROM("analysis_company_steam_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getTotalBySteam(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_steam_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getDataByGas(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("upload_date, sum(data_value) as data_value");
                FROM("analysis_company_gas_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if(!CollectionUtils.isEmpty(analysisCompanyPowerHour.getEnterpriseIds())) {
                        WHERE("enterprise_id in (" + StringUtils.join(analysisCompanyPowerHour.getEnterpriseIds(), ",") + ")");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
                GROUP_BY("upload_date");
            }
        }.toString();
    }
    public String getDataByGasByMonth(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("*");
                FROM("analysis_company_gas_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    public String getTotalByGas(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_gas_hour");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    
    public String getTotalByEnergy(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_energy_month");
                if (analysisCompanyPowerHour != null) {
                	
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getUpload_date()!= null ) {
                        WHERE("upload_date = #{upload_date}");
                    }else {
                    	WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    
    public String getTotalYearByEnergy(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_energy_month");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    
    public String getMonthDataByPower(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("sum(data_value)");
                FROM("analysis_company_power_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    
    public String getMonthDataByWater(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("SUM(data_value)");
                FROM("analysis_company_water_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    
    public String getMonthDataByGas(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("SUM(data_value)");
                FROM("analysis_company_gas_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
    
    public String getMonthDataByWSteam(final AnalysisCompanyPowerHourVo analysisCompanyPowerHour) {
        return new SQL() {
            {
                SELECT("SUM(data_value)");
                FROM("analysis_company_steam_date");
                if (analysisCompanyPowerHour != null) {
                    if (analysisCompanyPowerHour.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (analysisCompanyPowerHour.getStartTime() != null && analysisCompanyPowerHour.getEndTime() != null) {
                        WHERE("upload_date between #{startTime} and #{endTime}");
                    }
                }
            }
        }.toString();
    }
}
