package com.tg.enterprise.dao;

import com.tg.enterprise.model.CollectHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CollectHistoryMapper 
{
	@Select("select * from collect_history where uploadTime = #{uploadTime} and enterpiseId = #{enterpiseId}")
	CollectHistory getCollectHistory(@Param("uploadTime") long uploadTime, @Param("enterpiseId") int enterpiseId);
}
