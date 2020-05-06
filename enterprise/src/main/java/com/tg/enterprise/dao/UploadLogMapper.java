package com.tg.enterprise.dao;

import com.tg.enterprise.model.UploadLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UploadLogMapper 
{
	@Select("select * from upload_log order by id desc")
	List<UploadLog> query();
}
