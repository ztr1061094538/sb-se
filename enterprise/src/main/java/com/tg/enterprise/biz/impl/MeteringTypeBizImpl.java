package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IMeteringTypeBiz;
import com.tg.enterprise.dao.MeteringInstrumentMapper;
import com.tg.enterprise.dao.MeteringTypeMapper;
import com.tg.enterprise.model.MeteringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeteringTypeBizImpl implements IMeteringTypeBiz {

	@Autowired
	private MeteringTypeMapper meteringTypeMapper;

	@Override
	public MeteringType selectById(Integer id) {
		return meteringTypeMapper.selectById(id);
	}

    @Override
    public List<MeteringType> selectAll() {
        return meteringTypeMapper.selectAll();
    }
}
