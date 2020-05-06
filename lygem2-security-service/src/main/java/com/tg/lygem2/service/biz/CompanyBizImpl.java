package com.tg.lygem2.service.biz;

import com.tg.lygem2.bean.Company;
import com.tg.lygem2.bean.mapper.CompanyMapper;
import com.tg.lygem2.service.ICompanyBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CompanyBizImpl")
public class CompanyBizImpl implements ICompanyBiz {

	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public Company selectByid(Integer company_id) {
		return companyMapper.selectByid(company_id);
	}

}