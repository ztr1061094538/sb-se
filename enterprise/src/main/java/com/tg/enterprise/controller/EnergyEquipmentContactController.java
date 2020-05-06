package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.*;
import com.tg.enterprise.model.*;
import com.tg.enterprise.util.CollectItemStatTypeEnum;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用能设备采集数据关联定义列表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/energyEquipmentContact")
public class EnergyEquipmentContactController {

	@Autowired
    private IEnergyEquipmentContactBiz energyEquipmentContactBiz;
	
	@Autowired
	private IEnterpriseBiz enterpriseBiz;
	
	@Autowired
	private ICollectItemBiz collectItemBiz;
	
	@Autowired
	private ICollectItemUsageBiz collectItemUsageBiz;
	
	@Autowired
	private ICollectSystemTypeBiz collectSystemTypeBiz;
	
	@Autowired
	private IEnergyProtocolTypeBiz energyProtocolTypeBiz;
	
	@Autowired
	private IProcessCodeUnitBiz processCodeUnitBiz;
	
	@Autowired
	private IProcessCodeBiz processCodeBiz;
	
	@Autowired
	private IEnergyEquipmentBiz energyEquipmentBiz;

	@Autowired
	private ITerminalBiz terminalBiz;


    @ApiOperation(value = "用能设备采集数据关联定义列表条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<EnergyEquipmentContactVO> selectForPage(@RequestBody PageRequestVO<EnergyEquipmentContactVO> energyEquipmentContact, HttpServletRequest request) throws Exception {
        PageResponseVO<EnergyEquipmentContactVO> ResponseVO = new PageResponseVO<EnergyEquipmentContactVO>(ErrorCode.OK, "ok");
		User userVO = SessionUtil.getUser(request.getSession());
		energyEquipmentContact.getParams().setCompanyId(userVO.getCid());
		PageInfo<EnergyEquipmentContactVO> queryPageList = energyEquipmentContactBiz.selectForPage(energyEquipmentContact.getParams(), energyEquipmentContact.getPageIndex(), energyEquipmentContact.getPageSize());
		Map<String,String> terminalIdMap = new HashMap<>();
		if(!queryPageList.getList().isEmpty()){
			Set<String> terminalIds = new HashSet<>();
			for (EnergyEquipmentContactVO vo : queryPageList.getList()) {
				if (vo.getTerminalId()!=null&&!vo.getTerminalId().equals("")) {
					String[] split = vo.getTerminalId().split(",");
					terminalIds.addAll(Arrays.asList(split));
				}
			}
			TerminalReqVo reqVo = new TerminalReqVo();
			reqVo.setTerminalIds(terminalIds);
			List<Terminal> terminals = terminalBiz.selectByParamService(reqVo);
			terminalIdMap.putAll(terminals.stream().collect(Collectors.toMap(Terminal::getTerminalId,Terminal::getName,(k1,k2)->k2)));
		}
		for (EnergyEquipmentContactVO vo : queryPageList.getList()) {
			String collectTypeName = collectItemBiz.selectByCode(vo.getCollectType()).getName();
	    	String energyUseName = collectItemUsageBiz.selectByCode(vo.getEnergyUse()).getName();
	    	String ernergyName = energyProtocolTypeBiz.selectByCode(vo.getCategory()).getName();
	    	String equipment_name = "";
	    	if (vo.getEquipmentId() == null) {
	    		 equipment_name = "无";
			}else {
		    	equipment_name = energyEquipmentBiz.selectById(vo.getEquipmentId()).getEquipment_name();
			}
	    	ProcessCode processCode = processCodeBiz.selectByCode(vo.getCode(),vo.getCompanyId());
	    	String codeName = processCode.getName();
	    	String unitName;
	    	ProcessCodeUnit selectByCode = processCodeUnitBiz.selectByCode(vo.getCode_unit(),vo.getCompanyId(),processCode.getId());
	    	if (selectByCode!=null) {
	    		 unitName = selectByCode.getNameUnit();
			}else {
				 unitName ="";
			}
    		String inputTypeName = collectSystemTypeBiz.selectByCode(vo.getInputType()).getName();
    		vo.setCategoryName(ernergyName);
    		vo.setCodeName(codeName);
    		vo.setCodeUnitName(unitName);
    		vo.setInputTypeName(inputTypeName);
    		vo.setEquipmentName(equipment_name);
    		vo.setEnergyUseName(energyUseName);
    		vo.setCollectTypeName(collectTypeName);
    		if(vo.getStatType() != null){
				CollectItemStatTypeEnum collectItemStatTypeEnum = CollectItemStatTypeEnum.parse(vo.getStatType());
				if(collectItemStatTypeEnum != null){
					vo.setStatTypeName(collectItemStatTypeEnum.getName());
				}
			}
    		if (vo.getTerminalId()!=null&&!vo.getTerminalId().equals("")) {
        		String[] split = vo.getTerminalId().split(",");
        		StringBuilder sb = new StringBuilder();
        		for (int i = 0; i < split.length; i++) {
					String terminalName = terminalIdMap.get(split[i]);
					if(terminalName.startsWith("#")) {
						sb.append(new StringBuilder(terminalName).deleteCharAt(0));
					} else {
						sb.append(terminalName);
					}
				}
        		vo.setTerminalName(sb.toString());
			}else {
				vo.setTerminalName("");
			}
		}
		
		ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }


    @ApiOperation(value = "用能设备采集数据关联定义列表新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    @RecordChange
    public CommonResponse add(@RequestBody EnergyEquipmentContactVO energyEquipmentContact, HttpServletRequest request) {
        try {
    		User userVO = SessionUtil.getUser(request.getSession());
    		energyEquipmentContact.setCompanyId(userVO.getCid());
			Enterprise enterprise = enterpriseBiz.selectById(userVO.getCid());
    		energyEquipmentContact.setIndustryCode(enterprise.getIndustry_code());
    		if(energyEquipmentContact.getCode_unit() == null){
				energyEquipmentContact.setCode_unit("00");
			}
    		if (energyEquipmentContact.getEquipmentCode().length()!= 4) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"用能设备编码必须添加4位");
			}
			EnergyEquipmentContact energyEquipmentContactQueryVO = new EnergyEquipmentContact();
			energyEquipmentContactQueryVO.setCode(energyEquipmentContact.getCode());
			energyEquipmentContactQueryVO.setEquipmentCode(energyEquipmentContact.getEquipmentCode());
			energyEquipmentContactQueryVO.setCode_unit(energyEquipmentContact.getCode_unit());
			energyEquipmentContactQueryVO.setCompanyId(enterprise.getId());
			energyEquipmentContactQueryVO.setCategory(energyEquipmentContact.getCategory());
    		List<EnergyEquipmentContact> selectByCode = energyEquipmentContactBiz.selectList(energyEquipmentContactQueryVO);
    		if (selectByCode.size() > 0) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"用能设备编码已存在");
			}
    		setProductName(energyEquipmentContact);
            energyEquipmentContactBiz.insert(energyEquipmentContact);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    public void setProductName(EnergyEquipmentContact energyEquipmentContact)
    {
    	StringBuilder sb = new StringBuilder();
    	String name = "全厂";
    	if (!"0000".equals(energyEquipmentContact.getEquipmentCode()))
		{
			if(energyEquipmentContact.getEquipmentId() != null){
				name = energyEquipmentBiz.selectById(energyEquipmentContact.getEquipmentId()).getEquipment_name();
			}else{
				name = energyEquipmentContact.getEquipmentCode();
			}
		}
    	else if (!"00".equals(energyEquipmentContact.getCode_unit()))
    	{
			ProcessCode processCode = processCodeBiz.selectByCode(energyEquipmentContact.getCode(),energyEquipmentContact.getCompanyId());
    		name = processCodeUnitBiz.selectByCode(energyEquipmentContact.getCode_unit(), energyEquipmentContact.getCompanyId(),processCode.getId()).getNameUnit();
    	}
    	else if (!"00".equals(energyEquipmentContact.getCode()))
    	{
    		name = processCodeBiz.selectByCode(energyEquipmentContact.getCode(),energyEquipmentContact.getCompanyId()).getName();
    	}
    	sb.append(name).append("-");
    	String collectTypeName = collectItemBiz.selectByCode(energyEquipmentContact.getCollectType()).getName();
    	sb.append(collectTypeName).append("-");
    	String ernergyName = energyProtocolTypeBiz.selectByCode(energyEquipmentContact.getCategory()).getName();
    	sb.append(ernergyName).append("-");
    	String inputTypeName = collectSystemTypeBiz.selectByCode(energyEquipmentContact.getInputType()).getName();
    	sb.append(inputTypeName);
    	energyEquipmentContact.setName(sb.toString());
    }

    @ApiOperation(value = "用能设备采集数据关联定义列表更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
//    @RecordChange
    public CommonResponse update(@RequestBody EnergyEquipmentContact energyEquipmentContact, HttpServletRequest request)
    {
        try 
        {
        	User userVO = SessionUtil.getUser(request.getSession());
        	energyEquipmentContact.setCompanyId(userVO.getCid());
        	setProductName(energyEquipmentContact);
            energyEquipmentContactBiz.update(energyEquipmentContact);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	@ApiOperation(value = "用能设备采集数据关联定义列表批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
//	@RecordChange
    public CommonResponse delByIds(@RequestBody List<Long> ids, HttpServletRequest request)
	{
        try 
        {
            energyEquipmentContactBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
}