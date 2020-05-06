package com.tg.enterprise.biz.impl;

import com.tg.enterprise.biz.ISysDictionaryBiz;
import com.tg.enterprise.dao.SysDictionaryMapper;
import com.tg.enterprise.model.SysDictionary;
import com.tg.enterprise.vo.CodeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictionaryBizImpl implements ISysDictionaryBiz {

	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;

	@Override
	public List<CodeNode> queryList(SysDictionary sysDictionary) {
		return sysDictionaryMapper.queryList(sysDictionary);
	}

	@Override
	public CodeNode getCodeNode(SysDictionary sysDictionary) {
		return sysDictionaryMapper.getCodeNode(sysDictionary);
	}

	@Override
	public SysDictionary getCode(String sys_item_code) {
		return sysDictionaryMapper.getCode(sys_item_code);
	}

	@Override
	public List<SysDictionary> getByParentCode(String item) {
		return sysDictionaryMapper.getByParentCode(item);
	}

	@Override
	public List<CodeNode> getNameByCode(String classifyCode) {
		return sysDictionaryMapper.getNameByCode(classifyCode);
	}

	@Override
	public CodeNode getNameListByCode(String code) {
		return sysDictionaryMapper.getNameListByCode(code);
	}

	@Override
	public int add(SysDictionary entity) {
		return sysDictionaryMapper.add(entity);
	}

	@Override
	public int update(SysDictionary entity) {
		return sysDictionaryMapper.update(entity);
	}


}
