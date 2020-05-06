package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSONArray;
import com.tg.enterprise.biz.ICollectHistoryBiz;
import com.tg.enterprise.model.CollectHistory;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collectHistory")
public class CollectHistoryController
{
	@Autowired
	private ICollectHistoryBiz collectHistoryBiz;

	@ApiOperation("能源总量指标历史记录")
	@GetMapping("/getEnergyTargetHistory")
	public ListResponseVO<EnergyTargetHistory> getEnergyTargetHistory(@RequestParam(value ="companyId",required=false) Integer companyId, @RequestParam("uploadDate") long uploadDate, HttpServletRequest request)
	{
		User userVO = SessionUtil.getUser(request.getSession());
		Integer enterpriseId =userVO.getCid();
		if(companyId==null){
			companyId= enterpriseId;
		}
		List<EnergyTargetHistory> result = new ArrayList<>();
		CollectHistory historyVO = collectHistoryBiz.getCollectHistory(uploadDate, companyId);
		if (historyVO != null)
		{
			String energyTarget = historyVO.getEnergy_target();
			if (!StringUtils.isEmpty(energyTarget))
			{
				result = JSONArray.parseArray(energyTarget, EnergyTargetHistory.class);
			}
		}
		ListResponseVO<EnergyTargetHistory> returnVal =  new ListResponseVO<>(ErrorCode.OK, "ok",result);
		return returnVal;
	}

	@ApiOperation("能源总量指标总和")
	@GetMapping("/getEnergyTargetHistorySum")
	public ResponseVO<BigDecimal> getEnergyTargetHistorySum(@RequestParam(value ="companyId",required=false) Integer companyId, @RequestParam("uploadDate") long uploadDate, HttpServletRequest request)
	{
		User userVO = SessionUtil.getUser(request.getSession());
		Integer enterpriseId =userVO.getCid();
		if(companyId==null){
			companyId= enterpriseId;
		}
		BigDecimal total = BigDecimal.ZERO;
		CollectHistory historyVO = collectHistoryBiz.getCollectHistory(uploadDate, companyId);
		if (historyVO != null)
		{
			total = historyVO.getTotal();
		}
		ResponseVO<BigDecimal> returnVal =  new ResponseVO<>(ErrorCode.OK, "ok",total);
		return returnVal;
	}

	@ApiOperation("能效数据指标历史记录")
	@GetMapping("/getEfficiencyDataTargetHistory")
	public ListResponseVO<EfficiencyDataTargetHistory> getEfficiencyDataTargetHistory(
            @RequestParam(value ="companyId",required=false) Integer companyId, @RequestParam("uploadDate") long uploadDate, HttpServletRequest request)
	{
		User userVO = SessionUtil.getUser(request.getSession());
		Integer enterpriseId =userVO.getCid();
		if(companyId==null){
			companyId= enterpriseId;
		}
		List<EfficiencyDataTargetHistory> result = new ArrayList<>();
		CollectHistory historyVO = collectHistoryBiz.getCollectHistory(uploadDate, companyId);
		if (historyVO != null)
		{
			String content = historyVO.getEfficiency_data_target();
			if (!StringUtils.isEmpty(content))
			{
				result = JSONArray.parseArray(content, EfficiencyDataTargetHistory.class);
			}
		}
		return new ListResponseVO<>(ErrorCode.OK, "ok", result);
	}

	@ApiOperation("产量产值指标历史记录")
	@GetMapping("/getOutputEnergyTargetHistory")
	public ListResponseVO<OutputEnergyTargetHistory> getOutputEnergyTargetHistory(
            @RequestParam(value = "companyId",required=false) Integer companyId, @RequestParam("uploadDate") long uploadDate, HttpServletRequest request)
	{
		User userVO = SessionUtil.getUser(request.getSession());
		Integer enterpriseId =userVO.getCid();
		if(companyId==null){
			companyId= enterpriseId;
		}
		List<OutputEnergyTargetHistory> result = new ArrayList<>();
		CollectHistory historyVO = collectHistoryBiz.getCollectHistory(uploadDate, companyId);
		if (historyVO != null)
		{
			String content = historyVO.getOutput_energy_target();
			if (!StringUtils.isEmpty(content))
			{
				result = JSONArray.parseArray(content, OutputEnergyTargetHistory.class);
			}
		}
		return new ListResponseVO<>(ErrorCode.OK, "ok", result);
	}

	@ApiOperation("重点工序/设备能源消费量指标历史记录")
	@GetMapping("/getEnergyProcessTargetHistory")
	public ListResponseVO<EnergyProcessTargetHistory> getEnergyProcessTargetHistory(
            @RequestParam(value ="companyId",required=false) Integer companyId, @RequestParam("uploadDate") long uploadDate, HttpServletRequest request)
	{
		User userVO = SessionUtil.getUser(request.getSession());
		Integer enterpriseId =userVO.getCid();
		if(companyId==null){
			companyId= enterpriseId;
		}
		List<EnergyProcessTargetHistory> result = new ArrayList<>();
		CollectHistory historyVO = collectHistoryBiz.getCollectHistory(uploadDate, companyId);
		if (historyVO != null)
		{
			String content = historyVO.getEnergy_process_target();
			if (!StringUtils.isEmpty(content))
			{
				result = JSONArray.parseArray(content, EnergyProcessTargetHistory.class);
			}
		}
		return new ListResponseVO<>(ErrorCode.OK, "ok", result);
	}
}
