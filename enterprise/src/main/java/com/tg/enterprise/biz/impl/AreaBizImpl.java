package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IAreaBiz;
import com.tg.enterprise.dao.AreaMapper;
import com.tg.enterprise.model.Area;
import com.tg.enterprise.vo.CodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaBizImpl implements IAreaBiz {

	@Autowired
	private AreaMapper areaMapper;

	@Override
	public List<CodeVO> queryList(Area area) {
		return areaMapper.queryList(area);
	}

	@Override
	public Area getCode(String code) {
		return areaMapper.getCode(code);
	}

	@Override
	public List<CodeVO> getAreaName(String parentCode) {
		return areaMapper.getAreaName(parentCode);
	}

	@Override
	public CodeVO getAreaNameList(String code) {
		return areaMapper.getAreaNameList(code);
	}

}
