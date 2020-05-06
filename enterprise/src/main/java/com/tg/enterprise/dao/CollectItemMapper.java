package com.tg.enterprise.dao;

import com.tg.enterprise.model.CollectItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectItemMapper {
	@Select("select * from collect_item where type =#{collectItem.type}")
	List<CollectItem> getList(@Param("collectItem") CollectItem collectItem);

	@Select("select * from collect_item where code =#{code} and type = 1")
	CollectItem selectByCode(@Param("code") String code);
}
