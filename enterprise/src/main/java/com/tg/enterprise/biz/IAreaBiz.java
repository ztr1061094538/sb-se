package com.tg.enterprise.biz;


import com.tg.enterprise.model.Area;
import com.tg.enterprise.vo.CodeVO;

import java.util.List;

public interface IAreaBiz {

	List<CodeVO> queryList(Area area);

	Area getCode(String code);
	
	List<CodeVO> getAreaName(String parentCode);
	
	CodeVO getAreaNameList(String code);
}
