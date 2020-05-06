package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.MeteringCheckCountProvider;
import com.tg.enterprise.model.MeteringType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by huangjianbo on 2018/5/11
 */
public interface MeteringCheckCountMapper {

    @SelectProvider(type = MeteringCheckCountProvider.class, method = "queryPageList")
    List<MeteringType> queryPageList(MeteringType params);

}
