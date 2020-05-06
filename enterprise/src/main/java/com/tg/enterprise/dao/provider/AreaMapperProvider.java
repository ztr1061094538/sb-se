package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.Area;
import org.apache.ibatis.jdbc.SQL;

public class AreaMapperProvider {

	public String queryList(final Area area) {
		return new SQL() {
			{
				SELECT("code,name,parent_code,type");
				FROM("area");
				if (area != null) {
					if (area.getName() != null) {
						WHERE("name like CONCAT('%',#{name},'%')");
					}
					if (area.getCode() != null) {
						WHERE("code = #{code}");
					}
					if (area.getParent_code() != null) {
						WHERE("parent_code = #{parent_code}");
					}
					if (area.getType() !=null) {
						WHERE("type = #{type}");
					}
				}
				WHERE("is_del = 0");
			}
		}.toString();
	}
	
	public String getAreaName(final String parentCode) {
		return new SQL() {
			{
				SELECT("code,name");
				FROM("area");
				if (parentCode != null) {
						WHERE("parent_code = #{parentCode}");
				}
				WHERE("is_del = 0");
			}
		}.toString();
	}
	
	public String getAreaNameList(final String code) {
		return new SQL() {
			{
				SELECT("code,name");
				FROM("area");
				if (code != null) {
						WHERE("code = #{code}");
				}
				WHERE("is_del = 0");
			}
		}.toString();
	}
}
