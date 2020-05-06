package com.tg.enterprise.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergyType;
import com.tg.enterprise.biz.IEnergyTypeBiz;
import com.tg.enterprise.dao.EnergyTypeMapper;
import java.util.List;


/**
 * 能源类型 Dao接口实现
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */
@Service("IEnergyTypeBizImpl")
public class IEnergyTypeBizImpl implements IEnergyTypeBiz
{

    @Autowired
    private EnergyTypeMapper energyTypeMapper;

    /**
     * 根据主键ID查询
     */
    @Override
    public EnergyType selectByCode(java.lang.String code)
    {
        return energyTypeMapper.selectByCode(code);
    }

    /**
     * 全查列表
     */
    @Override
    public List<EnergyType> selectList(EnergyType energyType)
    {
        return energyTypeMapper.selectList(energyType);
    }

    /**
     * 条件分页列表
     */
    @Override
    public PageInfo<EnergyType> selectForPage(EnergyType energyType, Integer offset, Integer count)
    {
        PageHelper.startPage(offset, count, true);
        return new PageInfo<>(energyTypeMapper.selectList(energyType));
    }

    /**
     * 插入
     */
    @Override
    public int insert(EnergyType energyType)
    {
        return energyTypeMapper.insert(energyType);
    }

    /**
     * 批量插入
     */
    @Override
    public int insertBatch(List<EnergyType> energyTypeList)
    {
        return energyTypeMapper.insertBatch(energyTypeList);
    }

    /**
     * 更新
     */
    @Override
    public int update(EnergyType energyType)
    {
        return energyTypeMapper.update(energyType);
    }

    /**
     * 条件删除
     */
    @Override
    public int delete(EnergyType energyType)
    {
        return energyTypeMapper.delete(energyType);
    }

    /**
     * 根据主键删除
     */
    @Override
    public int deleteByCode(java.lang.String code)
    {
        return energyTypeMapper.deleteById(code);
    }


    /**
     * 批量删除
     */
    @Override
    public int delByIds(List<java.lang.String> codes)
    {
        return energyTypeMapper.delByIds(codes);
    }

    @Override
    public List<EnergyType> getEnergyName() {
        return energyTypeMapper.getEnergyName();
    }
}