package com.tg.enterprise.controller;

import com.tg.enterprise.biz.*;
import com.tg.enterprise.biz.impl.ProductBizImpl;
import com.tg.enterprise.config.ServerContext;
import com.tg.enterprise.model.*;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典接口
 * 
 * @author 霍腾腾
 *
 */
@RestController
@RequestMapping("/dict/")
@Slf4j
public class SysDictionaryController {

	@Autowired
	private IAreaBiz areaBiz;

	@Resource
	private ISysDictionaryBiz sysDictionaryBiz;

	@Resource
	private ServerContext serverContext;

	@Resource
	private IEnergyProtocolTypeBiz energyType;

	@Resource
	private IEnergyEquipmentBiz energyEquipmentBiz;

	@Resource
	private IProcessCodeBiz processCodeBiz;

	@Resource
	private ICollectItemBiz collectItemBiz;

	@Resource
	private ICollectItemUsageBiz collectItemUsageBiz;

	@Resource
	private ICollectSystemTypeBiz collectSystemTypeBiz;

	@Resource
	private IProcessCodeUnitBiz processCodeUnitBiz;

	@Autowired
	private ITerminalBiz terminalBiz;

	@Autowired
	private ProductBizImpl productBiz;
	@Autowired
	private IIndustryBiz industryBiz;
	@Autowired
	private IEntTypeBiz entTypeBiz;
	@Autowired
	private IRegionBiz regionBiz;

	@Autowired
	private IEnterpriseBiz enterpriseBiz;

	@Resource
	private IEnergyEquipmentContactBiz energyEquipmentContactBiz;

	@Value("${em2.config1711.region}")
	String region;
//	@ApiOperation(value = "获取地区", consumes = "application/json", response = CommonResponse.class)
//	@RequestMapping(value = "/getArea",method = RequestMethod.GET)
//	public ResponseVO<SysDictionaryVO<CodeVO>> getArea(String key, HttpServletRequest request)
//	{
//		if (org.apache.commons.lang3.StringUtils.isEmpty(key)) {
//			return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.INVALID_PARAM, "参数不能为空");
//		}
//		UserVO userVO = SessionUtil.getUser(request.getSession());
//		List<CodeVO> codeNodes = new ArrayList<>();
//		if(userVO!=null){
//			Area area = new Area();
//			if (StringUtils.isNotBlank(userVO.getDistrict()))
//			{
//				area.setParent_code(userVO.getDistrict());
//			}
//			else if(StringUtils.isNotBlank(userVO.getCity())){
//				area.setParent_code(userVO.getCity());
//			}else if(StringUtils.isNotBlank(userVO.getProvince())){
//				area.setParent_code(userVO.getProvince());
//			}
//			Area area1 =areaBiz.getCode(area.getParent_code());
//			CodeVO codeVO = new CodeVO();
//			codeVO.setCode(area1.getCode());
//			codeVO.setName(area1.getName());
//			codeNodes.add(codeVO);
//			List<CodeVO> codes=  areaBiz.queryList(area);
//			for (CodeVO code:codes) {
//				codeNodes.add(code);
//			}
//		}
//		SysDictionaryVO<CodeVO> dict= new SysDictionaryVO<>();
//		dict.setList(codeNodes);
//		dict.setTypeCode(key);
//		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", dict);
//	}

	@ApiOperation(value = "新增数据字典", consumes = "application/json", response = CommonResponse.class)
	@RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
	public CommonResponse add(@RequestBody SysDictionary vo){
		try {
			sysDictionaryBiz.add(vo);
			serverContext.initDict();
			return new CommonResponse(ErrorCode.OK, "ok");
		}catch (Exception e){
			log.error(e.getMessage(),e);
			return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
		}
	}

	@ApiOperation(value = "更新数据字典", consumes = "application/json", response = CommonResponse.class)
	@RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
	public CommonResponse update(@RequestBody SysDictionary vo){

		try {
			sysDictionaryBiz.update(vo);
			serverContext.initDict();
			return new CommonResponse(ErrorCode.OK, "ok");
		}catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
		}
	}

	@ApiOperation(value = "获取地区")
	@RequestMapping(value = "/getAreaList",method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getAreaList()
	{
		SysDictionaryVO<CodeVO> dict= new SysDictionaryVO<>();
		List<CodeVO> list = new ArrayList<>();
		List<Region> codeNodes =  regionBiz.selectByPCode(region);
		for (Region codeNode:codeNodes ) {
			CodeVO codeVO = new CodeVO();
			codeVO.setCode(codeNode.getCode());
			codeVO.setName(codeNode.getFullName());
			list.add(codeVO);
		}
		dict.setList(list);
		return new ResponseVO<>(ErrorCode.OK, "ok", dict);
	}

	@ApiOperation(value = "获取行业")
	@RequestMapping(value = "/getIndustry",method = RequestMethod.GET)
	public ResponseVO<List<CodeTreeVO>> getIndustry()
	{
		List<Industry> codeNodes =  industryBiz.queryAll();
		List<CodeTreeVO> list = getIndustryDataTree(codeNodes,"0",3);
		return new ResponseVO<>(ErrorCode.OK, "ok",list);
	}

	@ApiOperation(value = "获取企业类型")
	@RequestMapping(value = "/getEntType",method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getEntType()
	{
		SysDictionaryVO<CodeVO> dict= new SysDictionaryVO<>();
		List<CodeVO> list = new ArrayList<>();
		List<EntType> codeNodes =  entTypeBiz.queryAll();
		for (EntType codeNode:codeNodes ) {
			CodeVO codeVO = new CodeVO();
			codeVO.setCode(codeNode.getCode());
			codeVO.setName(codeNode.getName());
			list.add(codeVO);
		}
		dict.setList(list);
		return new ResponseVO<>(ErrorCode.OK, "ok", dict);
	}

	private List<CodeTreeVO> getIndustryDataTree(List<Industry> codeNodes,String pCode,int needLevel){
		List<CodeTreeVO> list = new ArrayList<>();
		if(needLevel <= 0){
			return list;
		}
		for (Industry industry :
				codeNodes) {
			if (pCode.equals(industry.getPcode())) {
				CodeTreeVO codeTreeVO = new CodeTreeVO(industry.getCode(), industry.getFullName(), getIndustryDataTree(codeNodes, industry.getCode(), needLevel - 1));
				list.add(codeTreeVO);
			}
		}
		return list;
	}

//	@ApiOperation(value = "获取地区", consumes = "application/json", response = CommonResponse.class)
//	@RequestMapping(value = "/getSubAreas",method = RequestMethod.GET)
//	public ResponseVO<SysDictionaryVO<CodeVO>> getSubArea(HttpServletRequest request, String key)
//	{
//		if (org.apache.commons.lang3.StringUtils.isEmpty(key)) {
//			return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.INVALID_PARAM, "参数不能为空");
//		}
//		UserVO userVO = SessionUtil.getUser(request.getSession());
//		String code = "";
//		if (userVO != null) {
//			if(StringUtils.isNotBlank(userVO.getDistrict())){
//				code = userVO.getDistrict();
//			}else if(StringUtils.isNotBlank(userVO.getCity())){
//				code = userVO.getCity();
//			}else if(StringUtils.isNotBlank(userVO.getProvince())){
//				code = userVO.getProvince();
//			}
//		}
//		Area area = new Area();
//		area.setParent_code(code);
//		List<CodeVO>  codeNodes =  areaBiz.queryList(area);
//		SysDictionaryVO<CodeVO> dict= new SysDictionaryVO<>();
//		dict.setList(codeNodes);
//		dict.setTypeCode(key);
//		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", dict);
//	}

	@ApiOperation(value = "查询数据字典", response = ResponseVO.class)
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getList(String key,String code) {
		if (StringUtils.isEmpty(key)||StringUtils.isEmpty(code)) {
			return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.INVALID_PARAM, "参数不能为空");
		}
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> list = new ArrayList<CodeVO>();
		List<CodeNode> codeNodeList = SysDictUtil.getChildListByPId(code);
		for (CodeNode codeNode:codeNodeList ) {
			CodeVO codeVO = new CodeVO();
			codeVO.setCode(codeNode.getCode());
			codeVO.setName(codeNode.getName());
			list.add(codeVO);
		}
		data.setTypeCode(key);
		data.setList(list);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

//	@ApiOperation(value = "查询地区字典", response = ResponseVO.class)
//	@RequestMapping(value = "/getNewArea", method = RequestMethod.GET)
//	public ResponseVO<SysDictionaryVO<CodeVO>> getNewArea(String key, HttpServletRequest request) {
//		if (StringUtils.isEmpty(key)) {
//			return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.INVALID_PARAM, "参数不能为空");
//		}
//		UserVO userVO = SessionUtil.getUser(request.getSession());
//		List<CodeVO> codeNodes = new ArrayList<>();
//		if(userVO!=null){
//			Area area = new Area();
//			if (StringUtils.isNotBlank(userVO.getDistrict()))
//			{
//				area.setParent_code(userVO.getDistrict());
//			}
//			else if(StringUtils.isNotBlank(userVO.getCity())){
//				area.setParent_code(userVO.getCity());
//			}else if(StringUtils.isNotBlank(userVO.getProvince())){
//				area.setParent_code(userVO.getProvince());
//			}
//			Area area1 =areaBiz.getCode(area.getParent_code());
//			if(area1 != null) {
//				CodeVO codeVO = new CodeVO();
//				codeVO.setCode(area1.getCode());
//				codeVO.setName(area1.getName());
//				codeNodes.add(codeVO);
//				List<CodeVO> codes=  areaBiz.queryList(area);
//				for (CodeVO code:codes) {
//					codeNodes.add(code);
//				}
//			}
//		}
//		SysDictionaryVO<CodeVO> dict= new SysDictionaryVO<>();
//		dict.setList(codeNodes);
//		dict.setTypeCode(key);
//		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", dict);
//	}


//	@ApiOperation(value = "查询数据字典", response = ResponseVO.class)
//	@RequestMapping(value = "/getListByArea", method = RequestMethod.GET)
//	public ResponseVO<SysDictionaryVO<CodeVO>> getListByArea(String key, HttpServletRequest request) {
//		if (StringUtils.isEmpty(key)) {
//			return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.INVALID_PARAM, "参数不能为空");
//		}
//		UserVO userVO = SessionUtil.getUser(request.getSession());
//		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
//		List<CodeVO> list = new ArrayList<CodeVO>();
//		List<CodeVO> list2 = new ArrayList<CodeVO>();
//		List<CodeNode> codeNodeList = SysDictUtil.getChildListByPId("000200000");
//		for (CodeNode codeNode:codeNodeList ) {
//			 CodeVO codeVO = new CodeVO();
//			 if (codeNode.getName().contains("万")) {
//				 codeVO.setCode(codeNode.getCode());
//				 codeVO.setName(codeNode.getName());
//				 list.add(codeVO);
//			 }else {
//				 codeVO.setCode(codeNode.getCode());
//				 codeVO.setName(codeNode.getName());
//				 list2.add(codeVO);
//			}
//		}
//		if (StringUtils.isNotBlank(userVO.getCity())||StringUtils.isNotBlank(userVO.getDistrict())) {
//				data.setList(list);
//			} else if (StringUtils.isNotBlank(userVO.getProvince())) {
//				data.setList(list2);
//			}
//		data.setTypeCode(key);
//		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
//	}

	@GetMapping("queryEnergyTypeList")
	@ApiOperation(value = "下拉能源品种", consumes = "application/json", response = ResponseVO.class)
	public ResponseVO<SysDictionaryVO<CodeVO>> queryEnergyTypeList(String key) {
		SysDictionaryVO<CodeVO> codeDict = new SysDictionaryVO<>();
		List<CodeVO> list = new ArrayList<>();
		List<EnergyType> protocolTypes = energyType.queryList(null);
		for (EnergyType entity:protocolTypes) {
			CodeVO node = new CodeVO();
			node.setCode(entity.getCode());
			node.setName(entity.getName());
			list.add(node);
		}
		codeDict.setTypeCode(key);
		codeDict.setList(list);
		return new ResponseVO<>(ErrorCode.OK, "ok", codeDict);
	}


	@GetMapping("queryProductList")
	@ApiOperation(value = "产品下拉", consumes = "application/json", response = ResponseVO.class)
	public ResponseVO<SysDictionaryVO<CodeVO>> queryProductList(String key, HttpServletRequest request) {
		User userVO = SessionUtil.getUser(request.getSession());
		Integer enterpriseId =userVO.getCid();
		Product product = new Product();
		product.setEnterprise_id(enterpriseId);
		List<Product> products = productBiz.selectList(product);
		List<CodeVO> nodeArrayList = new ArrayList<>();
		for (Product p:products) {
			CodeVO codeDictNode = new CodeVO();
			codeDictNode.setCode(p.getId().toString());
			codeDictNode.setName(p.getName());
			nodeArrayList.add(codeDictNode);
		}
		SysDictionaryVO<CodeVO> dict= new SysDictionaryVO<>();
		dict.setList(nodeArrayList);
		dict.setTypeCode(key);
		return new ResponseVO<>(ErrorCode.OK, "ok", dict);
	}


	@ApiOperation(value = "获取用能设备", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getDataNameList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getDataNameList(HttpServletRequest request) {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		EnergyEquipmentContact energyEquipmentContact = new EnergyEquipmentContact();
		User userVO = SessionUtil.getUser(request.getSession());
		energyEquipmentContact.setCompanyId(userVO.getCid());
		List<EnergyEquipmentContact> selectList = energyEquipmentContactBiz.selectList(energyEquipmentContact);
		for (EnergyEquipmentContact e : selectList) {
			CodeVO vo = new CodeVO();
			vo.setCode(e.getId().toString());
			vo.setName(e.getName());
			voList.add(vo);
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}


	@ApiOperation(value = "获取公司集合", response = ResponseVO.class)
	@RequestMapping(value = "/getCompanies", method = RequestMethod.POST)
	public ResponseVO<SysDictionaryVO<CodeVO>> getcompanies() {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList = new ArrayList<CodeVO>();
		//查所有企业
		EnterpriseQueryVO enterpriseReq = new EnterpriseQueryVO();
		enterpriseReq.setType(1);//所有企业
		enterpriseReq.setIs_del(0);
		List<Enterprise> list = enterpriseBiz.getList(enterpriseReq);
		if (!list.isEmpty()) {
			for (Enterprise enterprise : list) {
				CodeVO vo = new CodeVO();
				vo.setCode(enterprise.getId() + "");
				vo.setName(enterprise.getName());
				voList.add(vo);
			}
		}
		data.setList(voList);
		return new ResponseVO<>(ErrorCode.OK, "ok", data);
	}


	@ApiOperation(value = "获取生产工序", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getEnergyList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getEnergyList(HttpServletRequest request) {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		User userVO = SessionUtil.getUser(request.getSession());
		ProcessCode processCode = new ProcessCode();
		processCode.setCompanyId(userVO.getCid());
		List<ProcessCode> selectList = processCodeBiz.selectList(processCode);
		for (ProcessCode processCode2 : selectList) {
			CodeVO vo = new CodeVO();
			vo.setCode(processCode2.getCode());
			vo.setName(processCode2.getName());
			voList.add(vo);
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

	@ApiOperation(value = "获取工序单元", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getProcessUnitList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getProcessUnitList(HttpServletRequest request,Long cid) {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		ProcessCode processCode = processCodeBiz.selectById(cid);
		if("00".equals(processCode.getCode())){
			//全厂不存在生产工序单元
			return new ResponseVO<>(ErrorCode.OK, "ok", data);
		}else{
			List<CodeVO> voList=new ArrayList<CodeVO>();
			//其他的生产工序，添加默认00 生产工序单元供选择
			voList.add(new CodeVO("00","全部"));
			User userVO = SessionUtil.getUser(request.getSession());
			ProcessCodeUnit processCodeQueryVO = new ProcessCodeUnit();
			processCodeQueryVO.setCompanyId(userVO.getCid());
			processCodeQueryVO.setCid(cid);
			List<ProcessCodeUnit> selectList = processCodeUnitBiz.selectList(processCodeQueryVO);
			for (ProcessCodeUnit processCode2 : selectList) {
				CodeVO vo = new CodeVO();
				vo.setCode(processCode2.getCodeUnit());
				vo.setName(processCode2.getNameUnit());
				voList.add(vo);
			}
			data.setList(voList);
			return new ResponseVO<>(ErrorCode.OK, "ok", data);
		}
	}

	@ApiOperation(value = "获取采集数据项", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getCollectItemList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getCollectItemList() {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		CollectItem collectItem = new CollectItem();
		collectItem.setType(1);
		List<CollectItem> list = collectItemBiz.getList(collectItem);
		for (CollectItem e : list) {
			CodeVO vo = new CodeVO();
			vo.setCode(e.getCode());
			vo.setName(e.getName());
			voList.add(vo);
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

	@ApiOperation(value = "获取能源用途", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getCollectItemUsageList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getCollectItemUsageList() {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		List<CollectResponse> list = collectItemUsageBiz.getList();
		for (CollectResponse e : list) {
			CodeVO vo = new CodeVO();
			vo.setCode(e.getCode());
			vo.setName(e.getName());
			voList.add(vo);
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

	@ApiOperation(value = "获取采集系统分类", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getCollectSystemTypeList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getCollectSystemTypeList() {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		List<CollectResponse> list = collectSystemTypeBiz.getList();
		for (CollectResponse e : list) {
			CodeVO vo = new CodeVO();
			vo.setCode(e.getCode());
			vo.setName(e.getName());
			voList.add(vo);
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

	@ApiOperation(value = "获取用能设备", consumes = "application/json", response = ResponseVO.class)
	@RequestMapping(value = "/getTerminalList", consumes = "application/json",method = RequestMethod.POST)
	public ResponseVO<SysDictionaryVO<CodeVO>> getTerminalList(@RequestBody TerminalReqVo terminalReqVo,HttpServletRequest request) {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		List<Terminal> selectByParamService = terminalBiz.selectByParamService(terminalReqVo);
		if (!selectByParamService.isEmpty()) {
			for (Terminal terminal : selectByParamService) {
				CodeVO vo = new CodeVO();
				vo.setCode(terminal.getTerminalId());
				vo.setName(terminal.getName());
				voList.add(vo);
			}
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

	@ApiOperation(value = "获取用能设备", /*consumes = "application/json",*/ response = ResponseVO.class)
	@RequestMapping(value = "/getEquipmentList", /*consumes = "application/json",*/ method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getEquipmentList(HttpServletRequest request) {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		EnterpriseEnergyEquipmentVO energyEquipment = new EnterpriseEnergyEquipmentVO();
		User userVO = SessionUtil.getUser(request.getSession());
		energyEquipment.setEnterprise_id(userVO.getCid());
		energyEquipment.setIs_weedout(0);
		List<EnterpriseEnergyEquipment> list = energyEquipmentBiz.getList(energyEquipment);
		for (EnterpriseEnergyEquipment e : list) {
			CodeVO vo = new CodeVO();
			vo.setCode(e.getId().toString());
			vo.setName(e.getEquipment_name());
			voList.add(vo);
		}
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}

	@ApiOperation(value = "上报数据的频率")
	@RequestMapping(value = "/getCollectStatType", method = RequestMethod.GET)
	public ResponseVO<SysDictionaryVO<CodeVO>> getStatTyep(HttpServletRequest request) {
		SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
		List<CodeVO> voList=new ArrayList<CodeVO>();
		voList.add(new CodeVO(CollectItemStatTypeEnum.ONLINE.getType().toString(),CollectItemStatTypeEnum.ONLINE.getName()));
		voList.add(new CodeVO(CollectItemStatTypeEnum.DAY.getType().toString(),CollectItemStatTypeEnum.DAY.getName()));
		voList.add(new CodeVO(CollectItemStatTypeEnum.MONTH.getType().toString(),CollectItemStatTypeEnum.MONTH.getName()));
		voList.add(new CodeVO(CollectItemStatTypeEnum.YEAR.getType().toString(),CollectItemStatTypeEnum.YEAR.getName()));
		data.setList(voList);
		return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
	}
}
