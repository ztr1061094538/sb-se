package com.tg.enterprise.biz;

import com.tg.enterprise.model.EnergyType;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 能源类型 Dao接口
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */
public interface IEnergyTypeBiz
{
    /**
     * 根据主键ID查询
     */
    EnergyType selectByCode(java.lang.String code);

    /**
     * 全查列表
     */
    List<EnergyType> selectList(EnergyType energyType);

    /**
     * 条件分页列表
     */
    PageInfo<EnergyType> selectForPage(EnergyType energyType, Integer offset, Integer count);

    /**
     * 插入
     */
    int insert(EnergyType energyType);

    /**
     * 批量插入
     */
    int insertBatch(List<EnergyType> energyTypeList);

    /**
     * 更新
     */
    int update(EnergyType energyType);

    /**
     * 条件删除
     */
    int delete(EnergyType energyType);

    /**
     * 根据主键ID删除
     */
    int deleteByCode(java.lang.String code);

    /**
     * 根据主键ID删除
     */
    int delByIds(List<java.lang.String> codes);

    List<EnergyType> getEnergyName();
}