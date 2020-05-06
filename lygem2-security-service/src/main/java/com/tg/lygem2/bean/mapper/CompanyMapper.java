package com.tg.lygem2.bean.mapper;

import com.tg.lygem2.bean.Company;
import com.tg.lygem2.bean.Org;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompanyMapper {

	@Select("select id as company_id,name from enterprise where is_del = 0")
	List<Company> getAll();

	@Select("select * from org where is_del = 0")
	List<Org> getAllOrg();

	@Select("select * from enterprise where id = #{company_id} and is_del = 0")
	Company selectByid(@Param("company_id") Integer company_id);
}
