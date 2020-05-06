package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.OutputEnergyTarget;
import com.tg.enterprise.vo.OutputEnergyQueryVO;
import com.tg.enterprise.vo.TargetQueryVO;
import com.tg.enterprise.vo.TargetRespVO;

import java.util.List;

public interface IOutputEnergyTargetBiz {
    OutputEnergyTarget selectById(String product, int enterprise_id, Long upload_date);

    int add(OutputEnergyTarget entity);

    PageInfo<OutputEnergyTarget> queryPageList(OutputEnergyTarget entity, Integer offset, Integer count);

    List<OutputEnergyTarget> queryList(OutputEnergyQueryVO queryVO);

    List<OutputEnergyTarget> queryList(OutputEnergyTarget entity);

    int update(OutputEnergyTarget entity);

    int delByIds(List<Long> ids);

    List<TargetRespVO> selectOutputList(TargetQueryVO targetQueryVO);

    TargetRespVO selectSumOutput(TargetQueryVO targetQueryVO);
}
