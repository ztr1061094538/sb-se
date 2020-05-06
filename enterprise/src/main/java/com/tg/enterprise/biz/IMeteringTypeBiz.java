package com.tg.enterprise.biz;

import com.tg.enterprise.model.MeteringType;

import java.util.List;

public interface IMeteringTypeBiz {

	MeteringType selectById(Integer id);

	List<MeteringType> selectAll();
}
