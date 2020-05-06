package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergyTarget;
import com.tg.enterprise.vo.TargetQueryVO;
import com.tg.enterprise.vo.TargetRespVO;

import java.util.List;

public interface IEnergyTargetBiz {
    EnergyTarget selectById(String energy_type, Long upload_date, Integer enterprise_id);

    int add(EnergyTarget entity);

    PageInfo<EnergyTarget> queryPageList(EnergyTarget entity, Integer offset, Integer count);

    List<EnergyTarget> queryList(EnergyTarget entity);

    int update(EnergyTarget entity);

    int delByIds(List<Long> ids);

    List<TargetRespVO> selectSumConsumption(TargetQueryVO targetQueryVO);
}
