package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.IMeteringCheckCountBiz;
import com.tg.enterprise.dao.MeteringCheckCountMapper;
import com.tg.enterprise.model.MeteringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangjianbo on 2018/5/11
 */
@Service
public class MeteringCheckCountImpl implements IMeteringCheckCountBiz {

    @Autowired
    private MeteringCheckCountMapper mapper;

    @Override
    public List<MeteringType> getAllMeteringType() {
    	return mapper.queryPageList(null);
    }

}
