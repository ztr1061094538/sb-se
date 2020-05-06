package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergyExpert;

import java.util.List;

/**
 * Created by huangjianbo on 2018/3/7
 */
public interface IEnergyExpertBiz {

    EnergyExpert selectById(int id);

    int add(EnergyExpert entity);

    PageInfo<EnergyExpert> queryPageList(EnergyExpert entity, Integer offset, Integer count);

    int update(EnergyExpert entity);

    int delByIds(List<Integer> ids);
}
