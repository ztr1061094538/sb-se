package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.UploadLog;

import java.util.List;

public interface IUploadLogBiz 
{
	PageInfo<UploadLog> query(int pageNum, int pageSize);

    List<UploadLog> queryList();
}
