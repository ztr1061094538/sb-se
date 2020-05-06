package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.vo.EnterpriseQueryVO;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */
public interface IEnterpriseBiz 
{
    Enterprise selectById(int id);

    List<Enterprise> getList(EnterpriseQueryVO entity);

    PageInfo<Enterprise> queryPageList(EnterpriseQueryVO entity, Integer offset, Integer count);

    int add(Enterprise entity);

    int update(Enterprise entity);

    int delByIds(List<Integer> ids);

    List<Integer> getIdListByType(int type);
}
