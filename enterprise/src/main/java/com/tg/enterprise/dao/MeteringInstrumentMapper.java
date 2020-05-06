package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.MeteringInstrumentMapperProvider;
import com.tg.enterprise.model.MeteringInstrument;
import com.tg.enterprise.vo.MeteringInstrumentQueryVO;
import com.tg.enterprise.vo.MeteringRespVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MeteringInstrumentMapper {
	
	@Select("select * from metering_instrument where id = #{id}")
	MeteringInstrument selectById(@Param("id") int id);
	
	@Insert("insert into metering_instrument (metering_name,metering_type,metering_level,metering_parameter,"
			+ "data_code,data_code_calculate,data_code_ratio,manu_facturer,type_specification,accuracy_level,"
			+ "measure_range,manage_code,calibration_state,calibration_cycle,lately_calibration,"
			+ "inspection_organization,next_calibration,not_calibration,installation_site,"
			+ "install_org,install_date,usr_system,measure_state,measure_state_date,enterprise_code,terminal_id,enterprise_id,file_path) "
			+"values (#{metering.metering_name},#{metering.metering_type},#{metering.metering_level},"
			+ "#{metering.metering_parameter},#{metering.data_code},#{metering.data_code_calculate},"
			+ "#{metering.data_code_ratio},#{metering.manu_facturer},#{metering.type_specification},"
			+ "#{metering.accuracy_level},#{metering.measure_range},#{metering.manage_code},"
			+ "#{metering.calibration_state},#{metering.calibration_cycle},#{metering.lately_calibration},"
			+ "#{metering.inspection_organization},#{metering.next_calibration},#{metering.not_calibration},"
			+ "#{metering.installation_site},#{metering.install_org},#{metering.install_date},#{metering.usr_system},"
			+ "#{metering.measure_state},#{metering.measure_state_date},#{metering.enterprise_code},#{metering.terminal_id},#{metering.enterprise_id},#{metering.file_path})")
	//@SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="metering.id",before=false,resultType=int.class)
	int add(@Param("metering") MeteringInstrument metering);
	
	@SelectProvider(type = MeteringInstrumentMapperProvider.class, method = "queryList")
	List<MeteringInstrument> queryPageList(MeteringInstrumentQueryVO metering);
	
	@UpdateProvider(type=MeteringInstrumentMapperProvider.class,method="update")
	int update(MeteringInstrument metering);
	
	@Update("<script>update metering_instrument set is_del = 1 where id in "
			+ "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach></script>")
	int delByIds(@Param("ids") List<Integer> ids);

	@Select("select * from metering_instrument where enterprise_id = #{enterprise_id} and is_del=0 order by id desc")
	List<MeteringInstrument> selectByEnterpriseId(@Param("enterprise_id") int enterprise_id);

	@Select("<script>" +
			"select * from metering_instrument where is_del= 0 " +
			"<if test='startTime !=null||endTime!=null'>" +
			"and next_calibration BETWEEN #{startTime} and #{endTime}" +
			"</if>" +
			"</script>"
	)
	List<MeteringInstrument> getOvertimeEquipment(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
	
	@Select("<script>select * from metering_instrument where id in "
			+ "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach></script>")
	List<MeteringInstrument> selectByIds(@Param("ids") List<Integer> ids);

	@Select("select metering_level as name,count(id) as num from metering_instrument where is_del = 0 group by metering_level")
    List<MeteringRespVO> selectByMeteringLevel();

	@Select("select metering_type as name,count(id) as num from metering_instrument where is_del = 0 group by metering_type")
	List<MeteringRespVO> selectByMeteringType();

	@Select("select count(*) from metering_instrument where is_del = 0")
	Integer getTotal();
}
