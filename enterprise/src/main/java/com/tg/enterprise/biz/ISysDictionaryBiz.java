package com.tg.enterprise.biz;


import com.tg.enterprise.model.SysDictionary;
import com.tg.enterprise.vo.CodeNode;

import java.util.List;

public interface ISysDictionaryBiz {

	List<CodeNode> queryList(SysDictionary sysDictionary);

	CodeNode getCodeNode(SysDictionary sysDictionary);

	SysDictionary getCode(String sys_item_code);

	List<SysDictionary> getByParentCode(String item);
	
	List<CodeNode> getNameByCode(String classifyCode);
	
	CodeNode getNameListByCode(String code);

	int add(SysDictionary entity);

	int update(SysDictionary entity);
	
	
	
}
