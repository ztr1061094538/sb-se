package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IMeteringInstrumentBiz;
import com.tg.enterprise.biz.IMeteringTypeBiz;
import com.tg.enterprise.model.MeteringInstrument;
import com.tg.enterprise.model.MeteringType;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.constant.UserTypeEnum;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计量器具管理
 * 
 * @author 扶文祥
 *
 */
@RestController
@RequestMapping("/metering/")
@Slf4j
public class MeteringController {

	@Autowired
	private IMeteringInstrumentBiz iMeteringInstrumentBiz;

	@Autowired
	private IMeteringTypeBiz meteringTypeBiz;

	@Autowired
	private ThreadLocal<Result<Object>> threadLocal;

	@ApiOperation(value = "根据id查询计量器具", consumes = "application/json", response = ResponseVO.class)
	@RequestMapping(value = "/getDetailById", consumes = "application/json", method = RequestMethod.POST)
	public ResponseVO<MeteringInstrumentVO> getDetailById(@RequestBody Integer id) {
		MeteringInstrument m = iMeteringInstrumentBiz.selectById(id);
		MeteringInstrumentVO vo = new MeteringInstrumentVO();
		MeteringType meteringType = meteringTypeBiz.selectById(m.getMetering_type());
		vo.setType_name(meteringType.getMetering_type());
		try {
			PropertyUtils.copyProperties(vo, m);
			/*List<String> filenames = new ArrayList<>();
			if(StringUtils.isNotEmpty(m.getFile_path())){
				filenames.add(m.getFile_path());
			}
			ListResponseVO<OssInfo> ossInfos =  iossService.getObjectInfo(filenames);
			Map<String,OssInfo> downloadUrlMap = new HashMap<>();
			if (ossInfos.getCode() == ErrorCode.OK){
				for (OssInfo ossInfo : ossInfos.getData()){
					downloadUrlMap.put(ossInfo.getFileName(), ossInfo);

				}
				if(!downloadUrlMap.isEmpty()){

					vo.setFile_path(downloadUrlMap.get(m.getFile_path()).getDownloadUrl());
				}
			}*/

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		vo.setInstall_date1(DateUtil.getDate(m.getInstall_date()));
		vo.setLately_calibration1(DateUtil.getDate(m.getLately_calibration()));
		vo.setMeasure_state_date1(DateUtil.getDate(m.getMeasure_state_date()));
		vo.setNext_calibration1(DateUtil.getDate(m.getNext_calibration()));
		return new ResponseVO<MeteringInstrumentVO>(ErrorCode.OK, "ok", vo);
	}

	@ApiOperation(value = "新增计量器具", consumes = "application/json", response = CommonResponse.class)
	@RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
	public CommonResponse add(@RequestBody MeteringInstrumentVO data, HttpServletRequest request) {
		List<MeteringType> meteringTypes = meteringTypeBiz.selectAll();
		Map<Integer, String> meteringTypeMap = new HashMap<>();
		for (MeteringType meteringType : meteringTypes) {
			meteringTypeMap.put(meteringType.getId(), meteringType.getMetering_type());
		}
		if(!meteringTypeMap.containsKey(data.getMetering_type())) {
			return new CommonResponse(ErrorCode.INVALID_PARAM, "计量器具类型填写错误，请修改");
		}
		if(data.getMetering_level() != 1 && data.getMetering_level() != 2 && data.getMetering_level() != 3) {
			return new CommonResponse(ErrorCode.INVALID_PARAM, "计量器具等级填写错误，请修改");
		}

		MeteringInstrument metering = new MeteringInstrument();
		try {
			PropertyUtils.copyProperties(metering, data);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		metering.setInstall_date(DateUtil.getLongTime(data.getInstall_date1()));
		metering.setLately_calibration(DateUtil.getLongTime(data.getLately_calibration1()));
		metering.setMeasure_state_date(DateUtil.getLongTime(data.getMeasure_state_date1()));
		metering.setNext_calibration(DateUtil.getLongTime(data.getNext_calibration1()));
		ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
		if(user != null) {
			metering.setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
		}

		int i = iMeteringInstrumentBiz.add(metering);
		if (i <= 0) {
			return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, "插入失败");
		}
		return new CommonResponse(ErrorCode.OK, "ok");
	}

	@ApiOperation(value = "获取计量器具列表", consumes = "application/json", response = PageResponseVO.class)
	@RequestMapping(value = "/getList", consumes = "application/json", method = RequestMethod.POST)
	public PageResponseVO<MeteringInstrumentVO> getList(@RequestBody PageRequestVO<MeteringInstrumentQueryVO> data) {
		ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
		if(user.getUser_type() == UserTypeEnum.company.getType() || user.getUser_type() == UserTypeEnum.company_read.getType()) {
			data.getParams().setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
		}
		PageInfo<MeteringInstrument> page = iMeteringInstrumentBiz.queryPageList(data.getParams(), data.getPageIndex(),
				data.getPageSize());
		List<MeteringType> meteringTypes = meteringTypeBiz.selectAll();
		Map<Integer, String> meteringTypeMap = new HashMap<>();
		for (MeteringType meteringType : meteringTypes) {
			meteringTypeMap.put(meteringType.getId(), meteringType.getMetering_type());
		}
		if (null == page) {
			return new PageResponseVO<MeteringInstrumentVO>(ErrorCode.BG_GETDATA_ERROR, "获取列表失败");
		}
		List<MeteringInstrumentVO> voList = new ArrayList<MeteringInstrumentVO>();
		if (page.getTotal() > 0) {
			for (MeteringInstrument m : page.getList()) {
				MeteringInstrumentVO vo = new MeteringInstrumentVO();
				try {
					PropertyUtils.copyProperties(vo, m);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				vo.setType_name(meteringTypeMap.get(m.getMetering_type()));
				vo.setInstall_date1(DateUtil.getDate(m.getInstall_date()));
				vo.setLately_calibration1(DateUtil.getDate(m.getLately_calibration()));
				vo.setMeasure_state_date1(DateUtil.getDate(m.getMeasure_state_date()));
				vo.setNext_calibration1(DateUtil.getDate(m.getNext_calibration()));
				voList.add(vo);
			}
		}
		PageResponseVO<MeteringInstrumentVO> responseVO = new PageResponseVO<MeteringInstrumentVO>(ErrorCode.OK, "ok");
		responseVO.setRows(voList);
		responseVO.setTotal(page.getTotal());
		return responseVO;
	}

	@ApiOperation(value = "修改计量器具", consumes = "application/json", response = CommonResponse.class)
	@RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
	public CommonResponse update(@RequestBody MeteringInstrumentVO data) {
		List<MeteringType> meteringTypes = meteringTypeBiz.selectAll();
		Map<Integer, String> meteringTypeMap = new HashMap<>();
		for (MeteringType meteringType : meteringTypes) {
			meteringTypeMap.put(meteringType.getId(), meteringType.getMetering_type());
		}
		if(!meteringTypeMap.containsKey(data.getMetering_type())) {
			return new CommonResponse(ErrorCode.INVALID_PARAM, "计量器具类型填写错误，请修改");
		}
		if(data.getMetering_level() != 1 && data.getMetering_level() != 2 && data.getMetering_level() != 3) {
			return new CommonResponse(ErrorCode.INVALID_PARAM, "计量器具等级填写错误，请修改");
		}
		MeteringInstrument metering = new MeteringInstrument();
		try {
			PropertyUtils.copyProperties(metering, data);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		metering.setInstall_date(DateUtil.getLongTime(data.getInstall_date1()));
		metering.setLately_calibration(DateUtil.getLongTime(data.getLately_calibration1()));
		metering.setMeasure_state_date(DateUtil.getLongTime(data.getMeasure_state_date1()));
		metering.setNext_calibration(DateUtil.getLongTime(data.getNext_calibration1()));

		int i = iMeteringInstrumentBiz.update(metering);
		if (i <= 0) {
			return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, "修改失败");
		}
		return new CommonResponse(ErrorCode.OK, "ok");
	}

	@ApiOperation(value = "删除计量器具", consumes = "application/json", response = CommonResponse.class)
	@RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
	public CommonResponse delByIds(@RequestBody List<Integer> ids) {
		int i = iMeteringInstrumentBiz.delByIds(ids);
		if (i <= 0) {
			return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, "删除失败");
		}
		return new CommonResponse(ErrorCode.OK, "ok");
	}

	@ApiOperation(value = "计量器具类型下拉", response = ResponseVO.class)
	@RequestMapping(value = "/getMeteringTypes", method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getMeteringTypes() {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> list = new ArrayList<CodeVO>();
		List<MeteringType> meteringTypes = meteringTypeBiz.selectAll();
		for (MeteringType meteringType : meteringTypes) {
			CodeVO codeVO = new CodeVO();
			codeVO.setCode(meteringType.getId()+"");
			codeVO.setName(meteringType.getMetering_type());
			list.add(codeVO);
		}
		data.setList(list);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

}
