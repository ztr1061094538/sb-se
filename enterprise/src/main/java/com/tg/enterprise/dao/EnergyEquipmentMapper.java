package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyEquipmentProvider;
import com.tg.enterprise.model.EnterpriseEnergyEquipment;
import com.tg.enterprise.vo.EnterpriseEnergyEquipmentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EnergyEquipmentMapper {
	@Select("select * from enterprise_energy_equipment where id = #{id}")
	EnterpriseEnergyEquipment selectById(@Param("id") int id);

	@Insert("insert into enterprise_energy_equipment(enterprise_id,area_code,industry_code," +
			"equipment_name,equipment_type,equipment_num,equipment_power,is_weedout,start_year,"+
			"installation_site,person,phone,equipment_picture,remark,equipment_survey,equipment_operation,elimination_update)"+
			"values (#{energyEquipment.enterprise_id},#{energyEquipment.area_code},#{energyEquipment.industry_code},"+
			"#{energyEquipment.equipment_name},#{energyEquipment.equipment_type},#{energyEquipment.equipment_num},#{energyEquipment.equipment_power},"+
			"#{energyEquipment.is_weedout},#{energyEquipment.start_year},#{energyEquipment.installation_site},#{energyEquipment.person},"+
			"#{energyEquipment.phone},#{energyEquipment.equipment_picture},#{energyEquipment.remark},#{energyEquipment.equipment_survey},#{energyEquipment.equipment_operation},"+
			"#{energyEquipment.elimination_update})")
	int add(@Param("energyEquipment") EnterpriseEnergyEquipment energyEquipment);
	
	@UpdateProvider(method = "update", type = EnergyEquipmentProvider.class)
	int update(EnterpriseEnergyEquipment energyEquipment);
	
	
	@Update("<script>update enterprise_energy_equipment set is_del = 1 where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
	int delByIds(@Param("ids") List<Integer> ids);
	
	@SelectProvider(method = "queryPageList", type = EnergyEquipmentProvider.class)
	List<EnterpriseEnergyEquipment> queryPageList(EnterpriseEnergyEquipmentVO energyEquipment);
	
	@Select("SELECT SUM(equipment_num) FROM enterprise_energy_equipment WHERE is_del = 0")
	Integer selectSumbyEid();
	
	
}
