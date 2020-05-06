package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.Terminal;
import com.tg.enterprise.vo.TerminalReqVo;
import com.tg.enterprise.vo.TerminalVo;

import java.util.List;

public interface ITerminalBiz {
	  /**
     * 新增
     */
    public Boolean insertService(Terminal entity) throws Exception;

    /**
     * 更新
     */
    public Boolean updateService(Terminal entity) throws Exception;

    /**
     * 批量删除
     */
    public Boolean deleteService(List<Integer> idList) throws Exception;

    /**
     * 删除（单个）
     */
    public Boolean deleteService(Integer id) throws Exception;

    /**
     * 根据ID查询对象
     */
    public Terminal selectByIdService(Integer id) throws Exception;

    /**
     * 根据查询条件查询对象（有分页）
     */
    public PageInfo<Terminal> selectByParamService(TerminalReqVo param, Integer pageNum, Integer pageSize) throws Exception;

    /**
     * 根据查询条件查询对象（无分页）
     */
    public List<Terminal> selectByParamService(TerminalReqVo param);

    List<TerminalVo> selectList(TerminalReqVo param);
}
