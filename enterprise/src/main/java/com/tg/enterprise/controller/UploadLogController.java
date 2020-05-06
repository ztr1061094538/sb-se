package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.vo.CommonPageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.biz.IUploadLogBiz;
import com.tg.enterprise.model.UploadLog;
import com.tg.enterprise.vo.UploadLogVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/uploadLogs/")
public class UploadLogController 
{
	@Autowired
	private IUploadLogBiz uploadLogBiz;

	@ApiOperation(value = "国家平台交互日志列表", consumes = "application/json", response = PageResponseVO.class)
	@PostMapping(value="query", consumes="application/json")
	public PageResponseVO<UploadLogVO> query(@RequestBody CommonPageRequestVO request)
	{
		PageInfo<UploadLog> results =  uploadLogBiz.query(request.getPageIndex(), request.getPageSize());
		List<UploadLogVO> list = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (UploadLog uploadLog : results.getList())
		{
			UploadLogVO logVO = new UploadLogVO();
			logVO.setId(uploadLog.getId());
			logVO.setStatus(uploadLog.isStatus());
			logVO.setAction(uploadLog.getAction());
			logVO.setUploadTime(format.format(uploadLog.getUploadTime()));
			logVO.setSuccess(uploadLog.isSuccess());
			logVO.setRetryTime(uploadLog.getRetryTime());
			logVO.setRetryFlag(uploadLog.getRetryTime()>0);
			list.add(logVO);
		}
		PageResponseVO<UploadLogVO> returnValue = new PageResponseVO<>(ErrorCode.OK, "ok");
		returnValue.setRows(list);
		returnValue.setTotal(results.getTotal());
		return returnValue;
	}
	
}
