package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.OutputEnergyWarningProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import com.tg.enterprise.model.OutputEnergyWarning;
import java.util.List;

/**
 * 产量与能耗预警设置 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-12-05			新增
 */ 

public interface OutputEnergyWarningMapper{
	
	/**
	 * 根据年份和公司id   必传项
	 */
	@Select("select * from output_energy_warning where year = #{year} and enterprise_id=#{enterprise_id}")
	OutputEnergyWarning selectByYearAndCid(@Param("year") Long year,@Param("enterprise_id") Integer enterprise_id);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = OutputEnergyWarningProvider.class, method = "selectList")
	List<OutputEnergyWarning> selectList(OutputEnergyWarning outputEnergyWarning);
	
	/**
	 * 插入		
	 */
	@Insert("insert into output_energy_warning" + 
			"(year,total_cons,unit_cons,total_cons_per,unit_cons_per) values " + 
			"(#{outputEnergyWarning.year}," +
			"#{outputEnergyWarning.total_cons}," +
			"#{outputEnergyWarning.unit_cons}," +
			"#{outputEnergyWarning.total_cons_per}," +
			"#{outputEnergyWarning.unit_cons_per})")
	int insert(@Param("outputEnergyWarning") OutputEnergyWarning outputEnergyWarning);
	
	/**
	 * 批量插入
	 */
	@Insert("<script>insert into output_energy_warning" + 
			"( year, total_cons, unit_cons, total_cons_per, unit_cons_per) values " + 
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
			"#{item.year}," +
			"#{item.total_cons}," +
			"#{item.unit_cons}," +
			"#{item.total_cons_per}," +
			"#{item.unit_cons_per}</foreach>" + 
			"</script>")
	int insertBatch(@Param("list") List<OutputEnergyWarning> outputEnergyWarningList);
	
	/**
	 * 更新		
	 */
	@UpdateProvider(type = OutputEnergyWarningProvider.class,method= "update")
	int update(OutputEnergyWarning outputEnergyWarning);

	/**
	 * 条件删除	
	 */
    @DeleteProvider(type = OutputEnergyWarningProvider.class, method = "delete")
	int delete(OutputEnergyWarning outputEnergyWarning);

	/**
	 * 根据主键ID删除		
	 */
	@Delete("delete from output_energy_warning where id = #{id}")
	int deleteById(@Param("id") Integer id);
	
	/**
	 * 批量删除		
	 */
	@Delete("<script>delete from output_energy_warning where id in " +
			"<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	int delByIds(@Param("list") List<Integer> list);
}