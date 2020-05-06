package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EfficiencyDataTarget;

import java.util.List;

public interface IEfficiencyDataTargetBiz {
    EfficiencyDataTarget selectById(Long id);

    int add(EfficiencyDataTarget entity);

    PageInfo<EfficiencyDataTarget> queryPageList(EfficiencyDataTarget entity, Integer offset, Integer count);

    List<EfficiencyDataTarget> queryList(EfficiencyDataTarget entity);

    int update(EfficiencyDataTarget entity);

    int delByIds(List<Long> ids);
}
