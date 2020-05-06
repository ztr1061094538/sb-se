package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnterpriseEnergyEquipment;
import com.tg.enterprise.vo.EnterpriseEnergyEquipmentVO;
import org.apache.ibatis.jdbc.SQL;

public class EnergyEquipmentProvider {
	public String queryPageList(final EnterpriseEnergyEquipmentVO energyEquipment) {
		return new SQL() {
			{
				SELECT("*");
				FROM("enterprise_energy_equipment");
				if (energyEquipment != null) {
					if (energyEquipment.getEnterprise_id()!= null) {
						WHERE("enterprise_id =#{enterprise_id}");
					}
					if(energyEquipment.getIs_weedout() != null){
						WHERE("is_weedout = #{is_weedout}");
					}
				}
                WHERE("is_del = 0");
			}
		}.toString();
	}
	
	 public String update(final EnterpriseEnergyEquipment energyEquipment) {
	        return new SQL() {
	            {
	                UPDATE("enterprise_energy_equipment");
	                if (energyEquipment != null) {
	                    if(energyEquipment.getEnterprise_id() !=null){
	                        SET("enterprise_id=#{enterprise_id}");
	                    }
	                    if(energyEquipment.getArea_code() !=null){
	                        SET("area_code=#{area_code}");
	                    }
	                    if(energyEquipment.getIndustry_code() !=null){
	                        SET("industry_code=#{industry_code}");
	                    }
	                    if(energyEquipment.getEquipment_name() !=null){
	                        SET("equipment_name=#{equipment_name}");
	                    }
	                    if(energyEquipment.getEquipment_type() !=null){
	                        SET("equipment_type=#{equipment_type}");
	                    }
	                    if(energyEquipment.getEquipment_num()!=null){
	                        SET("equipment_num=#{equipment_num}");
	                    }
	                    if(energyEquipment.getEquipment_power() !=null){
	                        SET("equipment_power=#{equipment_power}");
	                    }
	                    if(energyEquipment.getIs_weedout() !=null){
	                        SET("is_weedout=#{is_weedout}");
	                    }
	                    if(energyEquipment.getStart_year() !=null){
	                        SET("start_year=#{start_year}");
	                    }
	                    if(energyEquipment.getInstallation_site() !=null){
	                        SET("installation_site=#{installation_site}");
	                    }
	                    if(energyEquipment.getPerson() !=null){
	                        SET("person=#{person}");
	                    }
	                    if(energyEquipment.getPhone() !=null){
	                        SET("phone=#{phone}");
	                    }
	                    if(energyEquipment.getEquipment_picture() !=null){
	                        SET("equipment_picture=#{equipment_picture}");
	                    }
	                    if(energyEquipment.getRemark() !=null){
	                        SET("remark=#{remark}");
	                    }
	                    if(energyEquipment.getEquipment_survey() !=null){
	                        SET("equipment_survey=#{equipment_survey}");
	                    }
	                    if(energyEquipment.getEquipment_operation() !=null){
	                        SET("equipment_operation=#{equipment_operation}");
	                    }
	                    if(energyEquipment.getElimination_update() !=null){
	                        SET("elimination_update=#{elimination_update}");
	                    }
	                }
	                WHERE("id = #{id}");
	            }
	        }.toString();
	    }
}
