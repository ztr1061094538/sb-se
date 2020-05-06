package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.vo.EnterpriseQueryVO;
import org.apache.ibatis.jdbc.SQL;


/**
 * Created by Administrator on 2018/1/31.
 */
public class EnterpriseMapperProvider {
    public String queryPageList(final EnterpriseQueryVO entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("enterprise t1");
                if (entity != null) {
                    if (entity.getName() != null) {
                        WHERE("t1.name like CONCAT('%',#{name},'%')");
                    }

                    if (entity.getEnterprise_id() != null) {
                        WHERE("id = #{enterprise_id}");
                    }
                    if(entity.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (entity.getDistrict()!=null) {
                    	WHERE("district=#{district}");
                    }
                    if(entity.getCity()!=null){
                        WHERE("city=#{city}");
                    }
                    if(entity.getProvince()!=null){
                        WHERE("province=#{province}");
                    }
                    if(entity.getManage_code()!=null){
                        WHERE("manage_code=#{manage_code}");
                    }
                    if (null != entity.getOrderByName()) {
                        if (entity.getOrderByName() == 1) {
                            ORDER_BY("t1.firstPinyinName asc");
                        } else {
                            ORDER_BY("t1.firstPinyinName desc");
                        }
                    }
                }
                WHERE("t1.is_del = 0");
            }
        }.toString();
    }
    public String update(final Enterprise entity) {
        return new SQL() {
            {
                UPDATE("enterprise");
                if (entity != null) {
                    if(entity.getName() !=null){
                        SET("name=#{name}");
                    }
                    if(entity.getType_code() !=null){
                        SET("type_code=#{type_code}");
                    }
                    if(entity.getCode() !=null){
                        SET("code=#{code}");
                    }
                    if(entity.getIndustry_code() !=null){
                        SET("industry_code=#{industry_code}");
                    }
                    if(entity.getCenter() !=null){
                        SET("center=#{center}");
                    }
                    if(entity.getEnergy_consume_level() !=null){
                        SET("energy_consume_level=#{energy_consume_level}");
                    }
                    if(entity.getJgzh() != null) {
                        SET("jgzh = #{jgzh}");
                    }
                    if(entity.getLatitude() !=null){
                        SET("latitude=#{latitude}");
                    }
                    if(entity.getLongitude() !=null){
                        SET("longitude=#{longitude}");
                    }
                    if(entity.getPhone() !=null){
                        SET("phone=#{phone}");
                    }
                    if(entity.getFax() !=null){
                        SET("fax=#{fax}");
                    }
                    if(entity.getEmail() !=null){
                        SET("email=#{email}");
                    }
                    if(entity.getAddress() !=null){
                        SET("address=#{address}");
                    }
                    if(entity.getZip_code() !=null){
                        SET("zip_code=#{zip_code}");
                    }
                    if(entity.getUrl() !=null){
                        SET("url=#{url}");
                    }
                    if(entity.getField_code() !=null){
                        SET("field_code=#{field_code}");
                    }
                    if(entity.getField_name() !=null){
                        SET("field_name=#{field_name}");
                    }
                    if(entity.getCorporation_name() !=null){
                        SET("corporation_name=#{corporation_name}");
                    }
                    if(entity.getRegister_date() !=null){
                        SET("register_date=#{register_date}");
                    }
                    if(entity.getRegister_principal() !=null){
                        SET("register_principal=#{register_principal}");
                    }
                    if(entity.getEnergy_office() !=null){
                        SET("energy_office=#{energy_office}");
                    }
                    if(entity.getEnergy_official() !=null){
                        SET("energy_official=#{energy_official}");
                    }
                    if(entity.getEnergy_official_position() !=null){
                        SET("energy_official_position=#{energy_official_position}");
                    }
                    if(entity.getEnergy_official_phone() !=null){
                        SET("energy_official_phone=#{energy_official_phone}");
                    }
                    if(entity.getEnergy_pass() !=null){
                        SET("energy_pass=#{energy_pass}");
                    }
                    if(entity.getType_code() !=null){
                        SET("energy_resp_name=#{energy_resp_name}");
                    }
                    if(entity.getEnergy_resp_phone() !=null){
                        SET("energy_resp_phone=#{energy_resp_phone}");
                    }
                    if(entity.getEnergy_resp_name() !=null) {
						SET("energy_resp_name=#{energy_resp_name}");
					}
                    if(entity.getPass_date() !=null){
                        SET("pass_date =#{pass_date}");
                    }
                    if(entity.getPass_org() !=null){
                        SET("pass_org=#{pass_org}");
                    }
                    if(entity.getProduction_line() !=null){
                        SET("production_line=#{production_line}");
                    }
                    if(entity.getMajor_product() !=null){
                        SET("major_product=#{major_product}");
                    }
                    if(entity.getRemark() !=null){
                        SET("remark=#{remark}");
                    }
                    if(entity.getProvince() !=null){
                        SET("province=#{province}");
                    }
                    if(entity.getCity() !=null){
                        SET("city=#{city}");
                    }
                    if(entity.getDistrict() !=null){
                        SET("district=#{district}");
                    }
                    if (entity.getIs_sx() != null) {
						SET("is_sx=#{is_sx}");
					}
                    if (entity.getIs_energySupply() != null) {
                    	SET("is_energySupply=#{is_energySupply}");
					}
                    if (entity.getIs_power() != null) {
                    	SET("is_power=#{is_power}");
					}
                    if (entity.getCompany_charger() != null) {
						SET("company_charger=#{company_charger}");
					}
                    if (entity.getEnergy_scale() !=null) {
						SET("energy_scale=#{energy_scale}");
					}
                    if (entity.getEnterprise_data() !=null) {
                    	SET("enterprise_data=#{enterprise_data}");
					}
                    if (entity.getEnterprise_introduce() !=null) {
                    	SET("enterprise_introduce=#{enterprise_introduce}");
					}
                    if (entity.getFirstPinyinName() != null)
                    {
                    	SET("firstPinyinName = #{firstPinyinName}");
                    }
                    if (entity.getManage_code() != null) {
                    	SET("manage_code =#{manage_code}");
					}
                    if (entity.getOwned_group_name() != null) {
                    	SET("owned_group_name =#{owned_group_name}");
					}
                    if (entity.getEnergy_inspect_name() != null) {
                    	SET("energy_inspect_name =#{energy_inspect_name}");
					}
                    if (entity.getEnergy_inspect_phone() != null) {
                    	SET("energy_inspect_phone =#{energy_inspect_phone}");
					}
                    if (entity.getArea() != null) {
                    	SET("area =#{area}");
					}
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
