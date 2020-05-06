package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.util.MonitorUtil;
import com.tg.enterprise.biz.ITerminalBiz;
import com.tg.enterprise.dao.TerminalMapper;
import com.tg.enterprise.model.Terminal;
import com.tg.enterprise.vo.TerminalReqVo;
import com.tg.enterprise.vo.TerminalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalBizImpl implements ITerminalBiz {
	@Autowired
	private TerminalMapper mapper;
	
	@Override
	public Boolean insertService(Terminal entity) throws Exception {
		if (entity.getSeeType() == null) {
			entity.setSeeType(MonitorUtil.MONITOR_SEETYPE_DEVICE);
		}
        return (mapper.insert(entity) == 1) ? true : false;
	}

	@Override
	public Boolean updateService(Terminal entity) throws Exception {
		 return (mapper.update(entity) == 1) ? true : false;
	}

	@Override
	public Boolean deleteService(List<Integer> idList) throws Exception {
        return (mapper.deletes(idList) == idList.size()) ? true : false;
	}

	@Override
	public Boolean deleteService(Integer id) throws Exception {
        return (mapper.delete(id) == 1) ? true : false;
	}

	@Override
	public Terminal selectByIdService(Integer id) throws Exception {
        return mapper.selectById(id);
	}

	@Override
	public PageInfo<Terminal> selectByParamService(TerminalReqVo param, Integer pageNum, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize, true);
		List<Terminal> list = mapper.selectBySelective(param);
		PageInfo<Terminal> info = new PageInfo<Terminal>(list);
		return info;
	}

	@Override
	public List<Terminal> selectByParamService(TerminalReqVo param) {
		  return mapper.selectBySelective(param);
	}

	@Override
	public List<TerminalVo> selectList(TerminalReqVo param) {
		return mapper.selectList(param);
	}

}
