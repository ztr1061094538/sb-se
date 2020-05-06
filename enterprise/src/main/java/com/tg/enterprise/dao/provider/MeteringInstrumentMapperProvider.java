package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.MeteringInstrument;
import com.tg.enterprise.vo.MeteringInstrumentQueryVO;
import org.apache.ibatis.jdbc.SQL;

public class MeteringInstrumentMapperProvider {

	public String queryList(final MeteringInstrumentQueryVO metering) {
		return new SQL() {
			{
				SELECT("*");
				FROM("metering_instrument t1");
				if (metering != null) {
					if (metering.getMetering_name() != null) {
						WHERE("t1.metering_name like CONCAT('%',#{metering_name},'%')");
					}
					if (metering.getMetering_type() != null) {
						WHERE("t1.metering_type = #{metering_type}");
					}
					if(metering.getTerminal_id() !=null){
						WHERE("t1.terminal_id like CONCAT('%',#{terminal_id},'%')");
					}
					if(metering.getEnterprise_id() !=null){
						WHERE("t1.enterprise_id = #{enterprise_id}");
					}
					if(metering.getDataCode() !=null){
						WHERE("t1.data_code = #{dataCode}");
					}
				}
				WHERE("t1.is_del = 0");
			}
		}.toString();
	}

	public String update(final MeteringInstrument metering) {
		return new SQL() {
			{
				UPDATE("metering_instrument");
				if (metering != null) {
					if (metering.getMetering_name() != null) {
						SET("metering_name = #{metering_name}");
					}
					if (metering.getMetering_type() != null) {
						SET("metering_type = #{metering_type}");
					}
					if (metering.getMetering_level() != null) {
						SET("metering_level = #{metering_level}");
					}
					if (metering.getMetering_parameter() != null) {
						SET("metering_parameter = #{metering_parameter}");
					}
					if (metering.getData_code() != null) {
						SET("data_code = #{data_code}");
					}
					if (metering.getData_code_calculate() != null) {
						SET("data_code_calculate = #{data_code_calculate}");
					}
					if (metering.getData_code_ratio() != null) {
						SET("data_code_ratio = #{data_code_ratio}");
					}
					if (metering.getManu_facturer() != null) {
						SET("manu_facturer = #{manu_facturer}");
					}
					if (metering.getType_specification() != null) {
						SET("type_specification = #{type_specification}");
					}
					if (metering.getAccuracy_level() != null) {
						SET("accuracy_level = #{accuracy_level}");
					}
					if (metering.getMeasure_range() != null) {
						SET("measure_range = #{measure_range}");
					}
					if (metering.getManage_code() != null) {
						SET("manage_code = #{manage_code}");
					}
					if (metering.getCalibration_state() != null) {
						SET("calibration_state = #{calibration_state}");
					}
					if (metering.getCalibration_cycle() != null) {
						SET("calibration_cycle = #{calibration_cycle}");
					}
					if (metering.getLately_calibration() != null) {
						SET("lately_calibration = #{lately_calibration}");
					}
					if (metering.getInspection_organization() != null) {
						SET("inspection_organization = #{inspection_organization}");
					}
					if (metering.getNext_calibration() != null) {
						SET("next_calibration = #{next_calibration}");
					}
					if (metering.getNot_calibration() != null) {
						SET("not_calibration = #{not_calibration}");
					}
					if (metering.getInstallation_site() != null) {
						SET("installation_site = #{installation_site}");
					}
					if (metering.getInstall_org() != null) {
						SET("install_org = #{install_org}");
					}
					if (metering.getInstall_date() != null) {
						SET("install_date = #{install_date}");
					}
					if (metering.getUsr_system() != null) {
						SET("usr_system = #{usr_system}");
					}
					if (metering.getMeasure_state() != null) {
						SET("measure_state = #{measure_state}");
					}
					if (metering.getMeasure_state_date() != null) {
						SET("measure_state_date = #{measure_state_date}");
					}
					if (metering.getEnterprise_code() != null) {
						SET("enterprise_code = #{enterprise_code}");
					}
					if (metering.getEnterprise_id() != null) {
						SET("enterprise_id = #{enterprise_id}");
					}
					if (metering.getTerminal_id() != null) {
						SET("terminal_id = #{terminal_id}");
					}
					if (metering.getFile_path() != null) {
						SET("file_path = #{file_path}");
					}
				}
				WHERE("id = #{id}");
			}
		}.toString();
	}
}
