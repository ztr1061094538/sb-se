package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IUploadLogBiz;
import com.tg.enterprise.dao.UploadLogMapper;
import com.tg.enterprise.model.UploadLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadLogBizImpl implements IUploadLogBiz
{
	@Autowired
	private UploadLogMapper uploadLogMapper; 
	
	@Override
	public PageInfo<UploadLog> query(int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		List<UploadLog> logs = uploadLogMapper.query();
		PageInfo<UploadLog> result = new PageInfo<>(logs);
		return result;
	}

	@Override
	public List<UploadLog> queryList() {
		return uploadLogMapper.query();
	}

}
