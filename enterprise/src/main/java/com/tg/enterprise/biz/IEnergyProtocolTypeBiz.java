package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergyType;

import java.util.List;


public interface IEnergyProtocolTypeBiz {

    List<EnergyType> queryList(EnergyType enity);

    int update(EnergyType enity);

    PageInfo<EnergyType> pageQueryList(EnergyType enity, Integer offset, Integer count);

    EnergyType selectByCode(String code);
}
