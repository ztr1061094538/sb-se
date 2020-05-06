package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.SysDictionary;
import org.apache.ibatis.jdbc.SQL;

public class SysDictionaryMapperProvider {

	public String queryList(final SysDictionary sysDictionary) {
		return new SQL() {
			{
				SELECT("code,parent_code,name,code_no");
				FROM("sys_dictionary");
				if (sysDictionary != null) {
					if (sysDictionary.getName() != null) {
						WHERE("name like CONCAT('%',#{name},'%')");
					}
					if (sysDictionary.getCode() != null) {
						WHERE("code = #{code}");
					}
					if (sysDictionary.getParent_code() != null) {
						WHERE("parent_code = #{parent_code}");
					}
				}
				WHERE("is_del = 0");
			}
		}.toString();
	}

	public String getCodeNode(final SysDictionary sysDictionary) {
		return new SQL() {
			{
				SELECT("code,parent_code,name,code_no");
				FROM("sys_dictionary");
				if (sysDictionary != null) {
					if (sysDictionary.getName() != null) {
						WHERE("name like CONCAT('%',#{name},'%')");
					}
					if (sysDictionary.getCode() != null) {
						WHERE("code = #{code}");
					}
					if (sysDictionary.getParent_code() != null) {
						WHERE("parent_code = #{parent_code}");
					}
				}
				WHERE("is_del = 0");
			}
		}.toString();
	}

	public String getCode(final String sys_item_code) {
		return new SQL() {
			{
				SELECT("code,parent_code,name,code_no");
				FROM("sys_dictionary");
				if (sys_item_code != null) {
					if (sys_item_code != null) {
						WHERE("code = #{sys_item_code}");
					}
				}
				WHERE("is_del = 0");
			}
		}.toString();
	}
	public String getByParentCode(final String item) {
		return new SQL() {
			{
				SELECT("code,parent_code,name,code_no");
				FROM("sys_dictionary");
					if (item != null) {
						WHERE("parent_code = #{item}");
					}
				WHERE("is_del = 0");
			}
		}.toString();
	}
	
	public String getNameByCode(final String classifyCode) {
		return new SQL() {
			{
				SELECT("code,name");
				FROM("sys_dictionary");
				if (classifyCode !=null) {
					WHERE("parent_code= #{classifyCode}");
				}
				WHERE("is_del = 0 ");
			}
		}.toString();
	}
	
	public String getNameListByCode(final String code) {
		return new SQL() {
			{
				SELECT("code,name");
				FROM("sys_dictionary");
				if (code !=null) {
					WHERE("code= #{code}");
				}
				WHERE("is_del = 0 ");
			}
		}.toString();
	}
	
	public String getNameListByCodes(final String code) {
		return new SQL() {
			{
				SELECT("code,name");
				FROM("sys_dictionary");
				if (code !=null) {
					WHERE("code= #{code}");
				}
				WHERE("is_del = 0 ");
			}
		}.toString();
	}

	public String update(final SysDictionary entity){
		return new SQL(){
			{
				UPDATE("sys_dictionary");
				if (entity != null) {
					if (entity.getName() != null) {
						SET("name=#{name}");
					}
					if (entity.getParent_code() != null) {
						SET("parent_code=#{parent_code}");
					}
					if(entity.getCode_no()!=null){
						SET("code_no=#{code_no}");
					}
				}
				WHERE("code = #{code}");
			}
		}.toString();
	}
}
